package controller.ui.match;

import controller.communication.IPeerMessageListener;
import controller.communication.MessagesManager;
import controller.ui.UIAlertController;
import controller.ui.UIController;
import controller.ui.room.UIPawnPromotionController;
import helper.Converter;
import helper.ThreadPool;
import main.App;
import model.IResult;
import model.communication.client_server.AsyncUpdateRoom;
import model.communication.client_server.ConnectingServer;
import model.communication.peer.ConnectingPeerServer;
import model.type.Board;
import model.type.Player;
import model.type.Time;
import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPieces;
import model.type.enumeration.EPositions;
import model.type.piece.Pawn;
import model.type.piece.Piece;
import view.match.UIBoardView;
import view.match.UIMatchInfoView;
import view.match.UIMatchView;

public class UIOnlineMatchController extends UIController implements IPeerMessageListener {
    
    private final UIBoardController uiBoardController;
    private final UIMatchInfoController uiMatchInfoController;
    private final EPieceColors playerColor;
    private final EPieceColors enemyColor;
    
    private Player enemy;
    private AsyncUpdateRoom asyncUpdateRoom;
    
    private final UIAlertController uiAlertWaiting;
    
    private final UIAlertController uiRawAlertController;
    private final UIAlertController uiRawResultAlertController;
    private final UIAlertController uiExitAlertController;
    
    private IResult.Responser exitListener;
    
    public UIOnlineMatchController(EPieceColors playerColor, Board board, String roomName, Player enemy) {
        super(new UIMatchView());
        
        uiMatchInfoController = new UIMatchInfoController((UIMatchInfoView)super.findViewById("UIMatchInfoView"));
        uiMatchInfoController.setRoomName(roomName);
        
        uiBoardController = new UIBoardController(playerColor, board, (UIBoardView)super.findViewById("UIBoardView"));
        
        this.playerColor = playerColor;
        this.uiMatchInfoController.setPlayerColor(playerColor);
        this.enemyColor = playerColor == EPieceColors.WHITE ? EPieceColors.BLACK : EPieceColors.WHITE;
        
        this.uiAlertWaiting = new UIAlertController();
        this.uiAlertWaiting.setOKButtonTitle("Hủy");
        this.uiAlertWaiting.setHideCancelButton(true);
        this.uiAlertWaiting.centerWindow();
        
        this.uiRawAlertController = new UIAlertController();
        this.uiRawAlertController.centerWindow();
        
        this.uiExitAlertController = new UIAlertController();
        this.uiExitAlertController.centerWindow();
        
        this.uiRawResultAlertController = new UIAlertController();
        this.uiRawResultAlertController.setHideCancelButton(true);
        this.uiRawResultAlertController.centerWindow();
        
        setEventListeners();
        
        this.enemy = enemy;
    }
    
    private void setEventListeners() {
        uiBoardController.setAttackListener((Object obj) -> {
            Piece[] piece = (Piece[])obj; // 0: attack; 1: be attacked
            this.actacked(piece[0], piece[1]);
        });
        
        uiAlertWaiting.setOKButtonListener(() -> {
            exitMatch();
        });
        
        uiBoardController.setMovingListener((Object obj) -> {
            Object[] objs = (Object[])obj; // 0: piece 1: fromPos; 2: toPos
            Piece piece = (Piece)objs[0];
            EPositions fromPos = (EPositions)objs[1];
            EPositions toPos = (EPositions)objs[2];
            this.moved(piece, fromPos, toPos);
        });
        
        uiMatchInfoController.setDrawButtonListener(() -> {
            uiMatchInfoController.setDrawButtonEnable(false);
            uiRawAlertController.setTitle("ĐỀ NGHỊ HÒA");
            uiRawAlertController.setContent("Bạn chắc chắn muốn đề nghị hòa ván đấu này?");
            uiRawAlertController.show();
        });
        
        uiMatchInfoController.setExitButtonListener(() -> {
            uiExitAlertController.setTitle("THOÁT VÁN ĐẤU");
            uiExitAlertController.setContent("Bạn chắc chắn muốn thoát ván đấu này, bạn sẽ bị xét thua khi làm như vậy?");
            uiExitAlertController.setNote("SHOULD_REQUEST");
            uiExitAlertController.setHideCancelButton(false);
            uiExitAlertController.show();
        });
        
        uiMatchInfoController.setTimeUpListener((obj) -> {
            UITimerController timerController = (UITimerController)obj;
            if (timerController.getColor() == playerColor) {
                uiMatchInfoController.addHistory(String.format("  Time up: %s win", enemyColor));
                UIAlertController alertController = new UIAlertController();

                alertController.setOKButtonListener(() -> {
                    exitMatch();
                });

                alertController.setTitle("Hết giờ");
                alertController.setContent("Tổng thời gian 2 tiếng của bạn đã hết. Bạn thua ván này!");
                alertController.setHideCancelButton(true);
                alertController.centerWindow();
                alertController.show();
                
                ThreadPool.BUILDER.execute(() -> {
                    if (ConnectingPeerServer.CONNECTOR.create(enemy.getIp(), enemy.getPort())) {
                        ConnectingPeerServer.CONNECTOR.sendMessage(MessagesManager.PeerMessageType.TIMEUP, null);
                    }
                });
            }
        });
        
        uiRawAlertController.setOKButtonListener(() -> {
            requestToDrawMatch();
        });
        
        uiRawAlertController.setCancelButtonListener(() -> {
            uiMatchInfoController.setDrawButtonEnable(true);
        });
        
        uiRawResultAlertController.setOKButtonListener(() -> {
            if (uiRawResultAlertController.getNote().equals("EXIT")) {
                exitMatch();
            }
            uiMatchInfoController.setDrawButtonEnable(true);
        });
        
        uiExitAlertController.setOKButtonListener(() -> {
            if (uiExitAlertController.getNote().equals("SHOULD_REQUEST")) {
                requestExitMatch();
            }
            exitMatch();
        });
    }
    
    private void actacked(Piece attack, Piece beAttacked) {
        Time time = uiMatchInfoController.getTime(playerColor);
        
        boolean isCanPromotePawn = false;
        if (attack.getType() == EPieces.PAWN) {
            if (((Pawn)attack).isCanPromote(beAttacked.getPosition())) {
                isCanPromotePawn = true;
            }
        }
        
        if (beAttacked.getType() != EPieces.KING && isCanPromotePawn) {
            displayUpgradePawnScreen((promotion) -> {
                asyncSendAttackingMessage(new Object[] {
                    attack.getPosition(), beAttacked.getPosition(),
                    attack.getType(), beAttacked.getType(),
                    time.getHour(), time.getMin(), time.getSec(),
                    promotion
                });
                
                uiBoardController.replace((EPieces)promotion, beAttacked.getPosition(), playerColor);
                
                this.saveAttackingHistory(attack.getColor(), attack.getType(), attack.getPosition(),
                        beAttacked.getColor(), beAttacked.getType(), beAttacked.getPosition(), time);
                uiMatchInfoController.pauseTimer(playerColor);
                uiMatchInfoController.runTimer(enemyColor);
            });
        } else {
            asyncSendAttackingMessage(new Object[] {
                attack.getPosition(), beAttacked.getPosition(),
                attack.getType(), beAttacked.getType(),
                time.getHour(), time.getMin(), time.getSec(),
                null
            });

            this.saveAttackingHistory(attack.getColor(), attack.getType(), attack.getPosition(),
                    beAttacked.getColor(), beAttacked.getType(), beAttacked.getPosition(), time);
            uiMatchInfoController.pauseTimer(playerColor);
            uiMatchInfoController.runTimer(enemyColor);
        }
    }
    
    private void asyncSendAttackingMessage(Object[] msg) {
        ThreadPool.BUILDER.execute(() -> {
            if (ConnectingPeerServer.CONNECTOR.create(this.enemy.getIp(), this.enemy.getPort())) {
                ConnectingPeerServer.CONNECTOR.sendMessage(MessagesManager.PeerMessageType.ATTACKING, msg);
            } else {
                this.alertPeerDisconnection();
            }
        });
    }
    
    private void moved(Piece piece, EPositions fromPos, EPositions toPos) {
        Time time = uiMatchInfoController.getTime(playerColor);
        
        boolean isCanPromotePawn = false;
        if (piece.getType() == EPieces.PAWN) {
            if (((Pawn)piece).isCanPromote()) {
                isCanPromotePawn = true;
            }
        }
        
        if (isCanPromotePawn) {
            displayUpgradePawnScreen((promotion) -> {
                asyncSendMovingMessage(new Object[] {
                    fromPos, toPos,
                    piece.getType(),
                    time.getHour(), time.getMin(), time.getSec(),
                    promotion
                });
                
                uiBoardController.replace((EPieces)promotion, toPos, playerColor);
                
                this.saveMovingHistory(piece.getType(), piece.getColor(), fromPos, toPos, time);
                uiMatchInfoController.pauseTimer(playerColor);
                uiMatchInfoController.runTimer(enemyColor);
            });
        } else {
            asyncSendMovingMessage(new Object[] {
                fromPos, toPos,
                piece.getType(),
                time.getHour(), time.getMin(), time.getSec(),
                null
            });

            this.saveMovingHistory(piece.getType(), piece.getColor(), fromPos, toPos, time);
            uiMatchInfoController.pauseTimer(playerColor);
            uiMatchInfoController.runTimer(enemyColor);
        }
    }
    
    private void asyncSendMovingMessage(Object[] msg) {
        ThreadPool.BUILDER.execute(() -> {
            if (ConnectingPeerServer.CONNECTOR.create(this.enemy.getIp(), this.enemy.getPort())) {
                ConnectingPeerServer.CONNECTOR.sendMessage(MessagesManager.PeerMessageType.MOVING, msg);
            } else {
                this.alertPeerDisconnection();
            }
        });
    }
    
    private void displayUpgradePawnScreen(IResult.ResponseReceiver callback) {
        UIPawnPromotionController pawnPromotionController = new UIPawnPromotionController();
        
        pawnPromotionController.setButtonListener((obj) -> {
            if (callback != null) {
                callback.receiveResult(obj);
            }
        });
        
        pawnPromotionController.centerWindow();
        pawnPromotionController.show();
    }
    
    private void requestToDrawMatch() {
        uiAlertWaiting.setTitle("Đợi kết quả");
        uiAlertWaiting.setContent("Đợi kết quả đề nghị hòa...");
        uiAlertWaiting.setHideOKButton(true);
        uiAlertWaiting.show();
        
        ThreadPool.BUILDER.execute(() -> {
            if (ConnectingPeerServer.CONNECTOR.create(this.enemy.getIp(), this.enemy.getPort())) {
                ConnectingPeerServer.CONNECTOR.sendMessage(MessagesManager.PeerMessageType.RAW, null);
            } else {
                this.alertPeerDisconnection();
            }
        });
        
    }
    
    private void exitMatch() {
        if (ConnectingServer.CONNECTOR.create(App.SERVER_IP, App.SERVER_PORT)) {
            ConnectingServer.CONNECTOR.sendMessage(
                    MessagesManager.MessageType.QUIT_ROOM,
                    App.getInstance().getRoom().getId()
            );
        }
        
        if (exitListener != null) {
            exitListener.response();
        }
        
        uiMatchInfoController.stopTimer(playerColor);
        uiMatchInfoController.stopTimer(enemyColor);
        App.getInstance().close();
    }
    
    private void requestExitMatch() {
        ThreadPool.BUILDER.execute(() -> {
            if (ConnectingPeerServer.CONNECTOR.create(this.enemy.getIp(), this.enemy.getPort())) {
                ConnectingPeerServer.CONNECTOR.sendMessage(MessagesManager.PeerMessageType.EXIT, null);
            }
        });
    }

    @Override
    public Object hasRequestMessage(Object content) {
        Object[] info = (Object[])content;
        
        MessagesManager.PeerMessageType msgType = MessagesManager.PeerMessageType
                .getType(Converter.toInt((byte[])info[0]));
        
        switch(msgType) {
            case ATTACKING:
                hasAttackingMessage(info);
                break;
            case EXIT:
                hasExitMessage(info);
                break;
            case MOVING:
                hasMovingMessage(info);
                break;
            case RAW_REPLY:
                hasRawReplyMessage(info);
                break;
            case RAW:
                hasRawMessage(info);
                break;
            case JOIN:
                return hasJoinMessage(info);
            case TIMEUP:
                hasTimeUpMessage(info);
                break;
            default:
        }
        
        return null;
    }
    
    private void hasAttackingMessage(Object[] info) {
        EPositions fromPos = EPositions.getPosition(Converter.toInt((byte[])info[1]));
        EPositions toPos = EPositions.getPosition(Converter.toInt((byte[])info[2]));
        EPieces pieceType = EPieces.getPiece(Converter.toInt((byte[])info[3]));
        EPieces attackedPieceType = EPieces.getPiece(Converter.toInt((byte[])info[4]));
        Time time = new Time(
                Converter.toInt((byte[])info[5]), // hour
                Converter.toInt((byte[])info[6]), // min
                Converter.toInt((byte[])info[7]) // sec
        );
        uiBoardController.move(fromPos, toPos);
        this.saveAttackingHistory(
                enemyColor, pieceType, fromPos,
                playerColor, attackedPieceType, toPos,
                time
        );
        
        if (attackedPieceType != EPieces.KING && info.length == 9) { // promote pawn
            uiBoardController.replace(EPieces.getPiece(Converter.toInt((byte[])info[8])), toPos, enemyColor);
            uiBoardController.verifyKing();
            uiBoardController.alertChecking();
        }
        
        uiMatchInfoController.pauseTimer(enemyColor);
        uiMatchInfoController.setTime(enemyColor, time.getHour(), time.getMin(), time.getSec());
        uiBoardController.requireMoving();
        uiMatchInfoController.runTimer(playerColor);
    }
    
    private void hasExitMessage(Object[] info) {
        uiExitAlertController.setTitle("THOÁT VÁN ĐẤU");
        uiExitAlertController.setContent("Đối thủ đã thoát, ván đấu sẽ kết thúc với phần thắng thuộc về bạn");
        uiExitAlertController.centerWindow();
        uiExitAlertController.setNote("");
        uiExitAlertController.setHideCancelButton(true);
        uiExitAlertController.show();
    }
    
    private void hasMovingMessage(Object[] info) {
        EPositions fromPos = EPositions.getPosition(Converter.toInt((byte[])info[1]));
        EPositions toPos = EPositions.getPosition(Converter.toInt((byte[])info[2]));
        EPieces pieceType = EPieces.getPiece(Converter.toInt((byte[])info[3]));
        Time time = new Time(
                Converter.toInt((byte[])info[4]), // hour
                Converter.toInt((byte[])info[5]), // min
                Converter.toInt((byte[])info[6]) // sec
        );
        uiBoardController.move(fromPos, toPos);
        this.saveMovingHistory(
                pieceType,
                enemyColor,
                fromPos,
                toPos,
                time
        );
        
        if (info.length == 8) { // promote pawn
            uiBoardController.replace(EPieces.getPiece(Converter.toInt((byte[])info[7])), toPos, enemyColor);
            uiBoardController.verifyKing();
            uiBoardController.alertChecking();
        }
        
        uiMatchInfoController.pauseTimer(enemyColor);
        uiMatchInfoController.setTime(enemyColor, time.getHour(), time.getMin(), time.getSec());
        uiBoardController.requireMoving();
        uiMatchInfoController.runTimer(playerColor);
    }
    
    private void hasRawReplyMessage(Object[] info) {
        uiRawResultAlertController.setTitle("KẾT QUẢ ĐỀ NGHỊ HÒA");
        if ((byte)info[1] == 1) {
            uiRawResultAlertController.setNote("EXIT");
            uiRawResultAlertController.setContent("Đối thủ đã đồng ý hòa, ván đấu sẽ kết thúc!");
        } else {
            uiRawResultAlertController.setNote("");
            uiRawResultAlertController.setContent("Đối thủ không đồng ý hòa, ván đấu sẽ được tiếp tục!");
        }
        uiAlertWaiting.close();
        uiRawResultAlertController.show();
    }
    
    private void hasRawMessage(Object[] info) {
        UIAlertController alertController = new UIAlertController();
        
        alertController.setOKButtonListener(() -> {
            ThreadPool.BUILDER.execute(() -> {
                if (ConnectingPeerServer.CONNECTOR.create(this.enemy.getIp(), this.enemy.getPort())) {
                    ConnectingPeerServer.CONNECTOR.sendMessage(MessagesManager.PeerMessageType.RAW_REPLY, (byte)1);
                    exitMatch();
                } else {
                    this.alertPeerDisconnection();
                }
            });
        });
        
        alertController.setCancelButtonListener(() -> {
            ThreadPool.BUILDER.execute(() -> {
                if (ConnectingPeerServer.CONNECTOR.create(this.enemy.getIp(), this.enemy.getPort())) {
                    ConnectingPeerServer.CONNECTOR.sendMessage(MessagesManager.PeerMessageType.RAW_REPLY, (byte)0);
                } else {
                    this.alertPeerDisconnection();
                }
            });
        });
        
        alertController.setTitle("ĐỀ NGHỊ HÒA");
        alertController.setContent("Đối thủ đã đề nghị hòa, bạn có đồng ý hòa?");
        alertController.centerWindow();
        alertController.show();
    }
    
    private synchronized boolean hasJoinMessage(Object[] info) {
        int requestedRoomID = Converter.toInt((byte[])info[4]);
        if (App.getInstance().getRoom() == null 
                || requestedRoomID != App.getInstance().getRoom().getId() 
                || App.getInstance().getRoom().getNumberPlayers() == 2) {
//            return Converter.toString((byte[])info[2]).equals(enemy.getIp()); // use for enemy connect again
            return false;
        }
        asyncUpdateRoom = null;
        if (ConnectingServer.CONNECTOR.create(App.SERVER_IP, App.SERVER_PORT)) {
            ConnectingServer.CONNECTOR.sendMessage(
                    MessagesManager.MessageType.START_MATCH,
                    App.getInstance().getRoom().getId()
            );
        }
        
        enemy = new Player(
                Converter.toInt((byte[])info[1]),
                Converter.toString((byte[])info[2]),
                Converter.toInt((byte[])info[3])
        );
        App.getInstance().getRoom().setNumberPlayers((byte)2);
        uiAlertWaiting.close();
        App.getInstance().setEnabled(true);
        App.getInstance().setVisible(true);
        uiMatchInfoController.runTimer(EPieceColors.WHITE);
        
        return true;
    }
    
    private void hasTimeUpMessage(Object[] info) {
        uiMatchInfoController.addHistory(String.format("  Time up: %s win", playerColor));
        UIAlertController alertController = new UIAlertController();

        alertController.setOKButtonListener(() -> {
            exitMatch();
        });

        alertController.setTitle("Hết giờ");
        alertController.setContent("Tổng thời gian 2 tiếng của đối thủ đã hết. Bạn thắng ván này!");
        alertController.setHideCancelButton(true);
        alertController.centerWindow();
        alertController.show();
    }
    
    private void saveMovingHistory(EPieces pieceType, EPieceColors color,
            EPositions fromPosition, EPositions toPosition, Time time) {
        
        uiMatchInfoController.setDrawButtonEnable(color != playerColor);
        
        uiMatchInfoController.addHistory(
                String.format("  %s: %s moves from %s to %s (at %s)",
                    color,
                    pieceType,
                    fromPosition,
                    toPosition,
                    time
                )
        );
    }
    
    private void saveAttackingHistory(EPieceColors color, EPieces pieceType, EPositions pos,
            EPieceColors attackedColor, EPieces attackedPieceType, EPositions attackedPos, Time time) {
        uiMatchInfoController.setDrawButtonEnable(color != playerColor);
        
        uiMatchInfoController.addHistory(
                String.format("  %s: %s at %s attacks %s %s at %s (at %s)",
                        color, pieceType, pos, // attack
                        attackedColor, attackedPieceType, attackedPos, // be attacked
                        time
                )
        );
        
        if (attackedPieceType == EPieces.KING) {
            UIAlertController alertController = new UIAlertController();
            
            alertController.setOKButtonListener(() -> {
                exitMatch();
            });
            
            alertController.setHideCancelButton(true);
            alertController.centerWindow();
            if (color == playerColor) {
                alertController.setTitle("Thắng");
            } else {
                alertController.setTitle("Thua");
            }
            
            alertController.setContent("Vua đã bị giết, trò chơi kết thúc");
            alertController.show();
        }
    }
    
    public void show() {
        if (enemy == null) {
            asyncUpdateRoom = new AsyncUpdateRoom(App.getInstance().getRoom());
            asyncUpdateRoom.start();
            uiAlertWaiting.setTitle("Đợi đối thủ");
            uiAlertWaiting.setContent("Bạn cần đợi đối thủ vào phòng để bắt đầu ván đấu...");
            uiAlertWaiting.show();
            App.getInstance().setEnabled(false);
        } else { // play with player
            boolean isCanJoin = false;
            if (ConnectingPeerServer.CONNECTOR.create(enemy.getIp(), enemy.getPort())) {
                Player player = App.getInstance().getPlayer();
                isCanJoin = (boolean)ConnectingPeerServer.CONNECTOR.sendMessage(MessagesManager.PeerMessageType.JOIN, new Object[] {
                    App.getInstance().getRoom(),
                    player
                });
                if (isCanJoin) {
                    uiMatchInfoController.runTimer(EPieceColors.WHITE);
                    if (playerColor == EPieceColors.BLACK) {
                        uiMatchInfoController.setDrawButtonEnable(false);
                    }
                }
            }
            
            if (!isCanJoin) {
                uiExitAlertController.setTitle("Không vào được phòng");
                uiExitAlertController.setContent("Ván đấu đã đủ người hoặc không còn tồn tại. Bạn hãy chọn ván đấu khác!");
                uiExitAlertController.centerWindow();
                uiExitAlertController.setNote("");
                uiExitAlertController.setHideCancelButton(true);
                uiExitAlertController.show();
            }
        }
        App.getInstance().show(uiView, String.format(
                "%s - %s",
                App.getInstance().getPlayer().getName(), playerColor),
                () -> { // exit callback
                    uiExitAlertController.setTitle("THOÁT VÁN ĐẤU");
                    uiExitAlertController.setContent("Bạn chắc chắn muốn thoát ván đấu này, bạn sẽ bị xét thua khi làm như vậy?");
                    uiExitAlertController.setNote("SHOULD_REQUEST");
                    uiExitAlertController.setHideCancelButton(false);
                    uiExitAlertController.show();
                }
        );
    }
    
    private void alertPeerDisconnection() {
        uiExitAlertController.setTitle("Mất kết nối");
        uiExitAlertController.setContent("Đã mất kết nối với đối thủ, ván đấu sẽ kết thúc!");
        uiExitAlertController.centerWindow();
        uiExitAlertController.setNote("");
        uiExitAlertController.setHideCancelButton(true);
        uiAlertWaiting.close();
        uiExitAlertController.show();
    }
    
    public void setExitListener(IResult.Responser listener) {
        exitListener = listener;
    }
    
}
