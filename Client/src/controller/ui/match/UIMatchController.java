package controller.ui.match;

import controller.ui.UIAlertController;
import controller.ui.UIController;
import controller.ui.room.UIPawnPromotionController;
import helper.ThreadPool;
import java.util.Random;
import main.App;
import model.IResult;
import model.ai.AlphaBetaAlgorithm;
import model.ai.Node;
import model.type.Board;
import model.type.Time;
import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPieces;
import model.type.enumeration.EPositions;
import model.type.piece.Pawn;
import model.type.piece.Piece;
import view.match.UIBoardView;
import view.match.UIMatchInfoView;
import view.match.UIMatchView;

public class UIMatchController extends UIController {
    
    private final UIBoardController uiBoardController;
    private final UIMatchInfoController uiMatchInfoController;
    private final EPieceColors playerColor;
    private final EPieceColors enemyColor;
    
    private final UIAlertController uiExitAlertController;
    private boolean isFinished;
    
    public UIMatchController(EPieceColors playerColor, Board board, String roomName) {
        super(new UIMatchView());
        
        uiMatchInfoController = new UIMatchInfoController((UIMatchInfoView)super.findViewById("UIMatchInfoView"));
        uiMatchInfoController.setRoomName(roomName);
        uiMatchInfoController.setDrawButtonEnable(false);
        
        uiBoardController = new UIBoardController(playerColor, board, (UIBoardView)super.findViewById("UIBoardView"));
        
        this.playerColor = playerColor;
        this.uiMatchInfoController.setPlayerColor(playerColor);
        this.enemyColor = playerColor == EPieceColors.WHITE ? EPieceColors.BLACK : EPieceColors.WHITE;
        
        this.uiExitAlertController = new UIAlertController();
        
        setEventListeners();
        
        isFinished = false;
    }
    
    private void start() {
        uiMatchInfoController.runTimer(EPieceColors.WHITE);
        if (playerColor == EPieceColors.BLACK) {
            uiMatchInfoController.setDrawButtonEnable(false);
        }
    }
    
    private void setEventListeners() {
        uiBoardController.setAttackListener((Object obj) -> {
            Piece[] piece = (Piece[])obj; // 0: attack; 1: be attacked
            this.actacked(piece[0], piece[1]);
        });
        
        uiBoardController.setMovingListener((Object obj) -> {
            Object[] objs = (Object[])obj; // 0: piece 1: fromPos; 2: toPos
            Piece piece = (Piece)objs[0];
            EPositions fromPos = (EPositions)objs[1];
            EPositions toPos = (EPositions)objs[2];
            this.moved(piece, fromPos, toPos);
        });
        
        uiBoardController.setDoneListener(() -> {
            if (!isFinished) {
                invokeAI();
            }
        });
        
        uiMatchInfoController.setExitButtonListener(() -> {
            uiExitAlertController.setTitle("THOÁT VÁN ĐẤU");
            uiExitAlertController.setContent("Bạn chắc chắn muốn thoát ván đấu này?");
            uiExitAlertController.centerWindow();
            uiExitAlertController.show();
        });
        
        uiExitAlertController.setOKButtonListener(() -> {
            exitMatch();
        });
        
        uiMatchInfoController.setTimeUpListener((obj) -> {
            UITimerController timerController = (UITimerController)obj;
            
            UIAlertController alertController = new UIAlertController();
            alertController.setHideCancelButton(true);
            alertController.centerWindow();
            alertController.setOKButtonListener(() -> {
                exitMatch();
            });
            alertController.setTitle("Hết giờ");
            
            if (timerController.getColor() == playerColor) {
                uiMatchInfoController.addHistory(String.format("  Time up: %s win", enemyColor));
                alertController.setContent("Tổng thời gian 2 tiếng của bạn đã hết. Bạn thua ván này!");
            } else {
                uiMatchInfoController.addHistory(String.format("  Time up: %s win", playerColor));
                alertController.setContent("Tổng thời gian 2 tiếng của AI đã hết. Bạn thắng ván này!");
            }
            
            alertController.show();
        });
    }
    
    private void actacked(Piece attack, Piece beAttacked) {
        Time time = uiMatchInfoController.getTime(attack.getColor());
        
        boolean isCanPromotePawn = false;
        if (attack.getType() == EPieces.PAWN) {
            if (((Pawn)attack).isCanPromote(beAttacked.getPosition())) {
                isCanPromotePawn = true;
            }
        }
        
        if (beAttacked.getType() != EPieces.KING && isCanPromotePawn) {
            if (attack.getColor() == playerColor) { // player moved
                isFinished = true;
                displayUpgradePawnScreen((promotion) -> {
                    uiBoardController.replace((EPieces)promotion, beAttacked.getPosition(), playerColor);

                    this.saveAttackingHistory(attack.getColor(), attack.getType(), attack.getPosition(),
                            beAttacked.getColor(), beAttacked.getType(), beAttacked.getPosition(), time);
                    uiMatchInfoController.pauseTimer(attack.getColor());
                    uiMatchInfoController.runTimer(beAttacked.getColor());
                    isFinished = false;
                    invokeAI();
                });
            } else { // AI moved
                promotePawnRandomly(beAttacked.getPosition());
                
                this.saveAttackingHistory(attack.getColor(), attack.getType(), attack.getPosition(),
                        beAttacked.getColor(), beAttacked.getType(), beAttacked.getPosition(), time);
                uiMatchInfoController.pauseTimer(attack.getColor());
                uiMatchInfoController.runTimer(beAttacked.getColor());
            }
        } else {
            this.saveAttackingHistory(attack.getColor(), attack.getType(), attack.getPosition(),
                            beAttacked.getColor(), beAttacked.getType(), beAttacked.getPosition(), time);
            uiMatchInfoController.pauseTimer(attack.getColor());
            uiMatchInfoController.runTimer(beAttacked.getColor());
        }
    }
    
    private void moved(Piece piece, EPositions fromPos, EPositions toPos) {
        Time time = uiMatchInfoController.getTime(piece.getColor());
        
        boolean isCanPromotePawn = false;
        if (piece.getType() == EPieces.PAWN) {
            if (((Pawn)piece).isCanPromote()) {
                isCanPromotePawn = true;
            }
        }
        
        if (isCanPromotePawn) {
            if (piece.getColor() == playerColor) { // player moved
                isFinished = true;
                displayUpgradePawnScreen((promotion) -> {
                    uiBoardController.replace((EPieces)promotion, toPos, playerColor);
                    
                    this.saveMovingHistory(piece.getType(), piece.getColor(), fromPos, toPos, time);
                    uiMatchInfoController.pauseTimer(piece.getColor());
                    uiMatchInfoController.runTimer(piece.getColor() == EPieceColors.WHITE ? EPieceColors.BLACK : EPieceColors.WHITE);
                    isFinished = false;
                    invokeAI();
                });
            } else { // AI moved
                promotePawnRandomly(toPos);
                
                this.saveMovingHistory(piece.getType(), piece.getColor(), fromPos, toPos, time);
                uiMatchInfoController.pauseTimer(piece.getColor());
                uiMatchInfoController.runTimer(piece.getColor() == EPieceColors.WHITE ? EPieceColors.BLACK : EPieceColors.WHITE);
            }
        } else {
            this.saveMovingHistory(piece.getType(), piece.getColor(), fromPos, toPos, time);
            uiMatchInfoController.pauseTimer(piece.getColor());
            uiMatchInfoController.runTimer(piece.getColor() == EPieceColors.WHITE ? EPieceColors.BLACK : EPieceColors.WHITE);
        }
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
    
    private void promotePawnRandomly(EPositions pos) {
        EPieces promotion;
        
        Random rand = new Random(System.currentTimeMillis());
        int randomNum = rand.nextInt(4) + 1;
        
        switch(randomNum) {
            case 1:
                promotion = EPieces.BISHOP;
                break;
            case 2:
                promotion = EPieces.KNIGHT;
                break;
            case 3:
                promotion = EPieces.QUEEN;
                break;
            default:
                promotion = EPieces.ROOK;
        }
        
        uiBoardController.replace(promotion, pos, enemyColor);
    }
    
    private void exitMatch() {
        uiMatchInfoController.stopTimer(playerColor);
        uiMatchInfoController.stopTimer(enemyColor);
        App.getInstance().close();
    }
    
    private void saveMovingHistory(EPieces pieceType, EPieceColors color,
            EPositions fromPosition, EPositions toPosition, Time time) {
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
        uiMatchInfoController.addHistory(
                String.format("  %s: %s at %s attacks %s %s at %s (at %s)",
                        color, pieceType, pos, // attack
                        attackedColor, attackedPieceType, attackedPos, // be attacked
                        time
                )
        );
        
        if (attackedPieceType == EPieces.KING) {
            isFinished = true;
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
    
    private void invokeAI() {
        ThreadPool.BUILDER.execute(() -> {
            Board board = uiBoardController.getBoard();
            AlphaBetaAlgorithm al = new AlphaBetaAlgorithm();
            Node res = al.calcMove(board, 4);
            uiBoardController.moveByAI(res.getFrom(), res.getTo());
            
            uiBoardController.requireMoving();
        });
    }
    
    public void show() {
        this.start();
        App.getInstance().show(uiView, String.format(
                "%s - %s",
                App.getInstance().getPlayer().getName(), playerColor),
                () -> {
                    uiExitAlertController.setTitle("THOÁT VÁN ĐẤU");
                    uiExitAlertController.setContent("Bạn chắc chắn muốn thoát ván đấu này?");
                    uiExitAlertController.centerWindow();
                    uiExitAlertController.show();
                }
        );
    }
    
}
