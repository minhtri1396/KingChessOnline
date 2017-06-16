package controller.ui.match;

import controller.ui.UIAlertController;
import controller.ui.UIController;
import main.App;
import model.ai.AlphaBetaAlgorithm;
import model.ai.Node;
import model.type.Board;
import model.type.Player;
import model.type.Time;
import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPieces;
import model.type.enumeration.EPositions;
import model.type.piece.Piece;
import view.match.UIBoardView;
import view.match.UIMatchInfoView;
import view.match.UIMatchView;

public class UIMatchController extends UIController {
    
    private final UIBoardController uiBoardController;
    private final UIMatchInfoController uiMatchInfoController;
    private final EPieceColors playerColor;
    private final EPieceColors enemyColor;
    private Player enemy;
    
    private final UIAlertController uiExitAlertController;
    
    public UIMatchController(EPieceColors playerColor, Board board, String roomName, Player enemy) {
        super(new UIMatchView());
        
        uiMatchInfoController = new UIMatchInfoController((UIMatchInfoView)super.findViewById("UIMatchInfoView"));
        uiMatchInfoController.setRoomName(roomName);
        uiMatchInfoController.setDrawButtonEnable(false);
        
        uiBoardController = new UIBoardController(playerColor, board, (UIBoardView)super.findViewById("UIBoardView"));
        
        this.playerColor = playerColor;
        this.enemyColor = playerColor == EPieceColors.WHITE ? EPieceColors.BLACK : EPieceColors.WHITE;
        
        this.uiExitAlertController = new UIAlertController();
        
        setEventListeners();
        
        this.enemy = enemy;
    }
    
    public void start() {
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
            invokeAI();
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
        
        uiMatchInfoController.setTimeUpListener(() -> {
            timeUp();
        });
    }
    
    private void actacked(Piece actack, Piece beActacked) {
        Time time = uiMatchInfoController.getTime(playerColor);
        this.saveAttackingHistory(actack.getColor(), actack.getType(), actack.getPosition(),
                        beActacked.getColor(), beActacked.getType(), beActacked.getPosition(), time);
        uiMatchInfoController.pauseTimer(playerColor);
        uiMatchInfoController.runTimer(enemyColor);
    }
    
    private void moved(Piece piece, EPositions fromPos, EPositions toPos) {
        Time time = uiMatchInfoController.getTime(playerColor);
        this.saveMovingHistory(piece.getType(), piece.getColor(), fromPos, toPos, time);
        uiMatchInfoController.pauseTimer(playerColor);
        uiMatchInfoController.runTimer(enemyColor);
    }
    
    private void exitMatch() {
        uiMatchInfoController.stopTimer(playerColor);
        uiMatchInfoController.stopTimer(enemyColor);
        App.getInstance().close();
    }
    
    private void timeUp() {
        uiMatchInfoController.addHistory("  Time up");
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
    }
    
    private void invokeAI() {
        new Thread(() -> {
            Board board = uiBoardController.getBoard();
            AlphaBetaAlgorithm al = new AlphaBetaAlgorithm();
            Node res = al.calcMove(board, 3);
            uiBoardController.moveByAI(res.getFrom(), res.getTo());
            
            uiMatchInfoController.pauseTimer(enemyColor);
            uiBoardController.requireMoving();
            uiMatchInfoController.runTimer(playerColor);
        }).start();
    }
    
}
