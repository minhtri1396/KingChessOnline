package controller.ui.match;

import controller.ui.UIController;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import helper.ImageManager;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.IResult;
import model.type.Board;
import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPositions;
import model.type.piece.Piece;
import view.match.UIBoardView;

public class UIBoardController extends UIController {
    
    private static final Color[] CellColor = new Color[] {
        new Color(202, 137, 62),
        new Color(229, 188, 139)
    };
    
    private final JPanel boardPanel;
    private final  JPanel leftIndexs;
    private final JPanel topIndexs;
    private final JPanel rightIndexs;
    private final  JPanel bottomIndexs;
    
    private final EPieceColors playerColor;
    private final Board board;
    private final UIBoardCellController[] boardCells;
    private boolean isLockedMoving;
    private IResult.ResponseReceiver movingCallback;
    private IResult.ResponseReceiver attackCallback;
    private IResult.Responser doneCallback;
    
    public UIBoardController(EPieceColors playerColor, Board board) {
        this(playerColor, board, new UIBoardView());
    }
    
    public UIBoardController(EPieceColors playerColor, Board board, UIBoardView uiBoardView) {
        super(uiBoardView);
        this.playerColor = playerColor;
        this.board = board;
        this.boardCells = new UIBoardCellController[64];
        this.isLockedMoving = this.playerColor == EPieceColors.BLACK;
        
        this.boardPanel = (JPanel)super.findViewById("BoardPanel");
        this.leftIndexs = (JPanel)super.findViewById("LeftIndexs");
        this.topIndexs = (JPanel)super.findViewById("TopIndexs");
        this.rightIndexs = (JPanel)super.findViewById("RightIndexs");
        this.bottomIndexs = (JPanel)super.findViewById("BottomIndexs");
        
        this.setCells();
        this.putPiecesOnBoard();
    }
    
    public void setMovingListener(IResult.ResponseReceiver movingCallback) {
        this.movingCallback = movingCallback;
    }
    
    public void setAttackListener(IResult.ResponseReceiver attackCallback) {
        this.attackCallback = attackCallback;
    }
    
    public void setDoneListener(IResult.Responser doneCallback) {
        this.doneCallback = doneCallback;
    }
    
    public void requireMoving() {
        this.isLockedMoving = false;
    }
    
    private void setCells() {
        boolean posColor;
        boolean prePosColor = true; // 1
        for (int iRow = 0; iRow < 8; ++iRow) {
            posColor = !prePosColor;
            prePosColor = posColor;
            leftIndexs.add(this.createIndexLabel((8 - iRow) + ""));
            rightIndexs.add(this.createIndexLabel((8 - iRow) + ""));
            for (int iCol = 0; iCol < 8; ++iCol) {
                boardPanel.add(this.createCell(
                        iRow * 8 + iCol,
                        CellColor[posColor ? 1 : 0])
                );
                posColor = !posColor;
            }
        }
        
        for (int i = 0; i < 8; ++i) {
            topIndexs.add(this.createIndexLabel(Character.toString((char)(65 + i))));
            bottomIndexs.add(this.createIndexLabel(Character.toString((char)(65 + i))));
        }
    }
    
    private JPanel createCell(int cellID, Color color) {
        UIBoardCellController cellController = new UIBoardCellController(cellID, color);
        cellController.setSize(SizeDefine.BOARD_CELL);
        cellController.addMouseClickListener((Object obj) -> {
            if (!this.isLockedMoving) {
                UIBoardCellController cell = (UIBoardCellController)obj;
                this.processPieceMovingAt(cell.getId());
            }
        });
        
        boardCells[cellID] = cellController;
        
        return cellController.getContentView();
    }
    
    private JLabel createIndexLabel(String index) {
        JLabel indexLabel = new JLabel(index, SwingConstants.CENTER);
        indexLabel.setFont(FontDefine.CELL_INDEX);
        indexLabel.setForeground(ColorDefine.MATCH_CELL_INDEX);
        return indexLabel;
    }
    
    private void putPiecesOnBoard() {
        Piece piece;
        for (int i = 0; i < 64; ++i) {
            piece = board.getPiece(EPositions.getPosition(i));
            if (piece != null) {
                this.setPieceImageAt(i, ImageManager.Instance.getImageSrcFrom(
                        piece.getImagePath(),
                        50,
                        50)
                );
            }
        }
    }
    
    private EPositions prevClickedPos;
    private ArrayList<EPositions> suggestions = null;
    private void processPieceMovingAt(int clickedCellID) {
        EPositions clickedPosition = EPositions.getPosition(clickedCellID);
        
        if (prevClickedPos != clickedPosition) {
            boolean isShouldSuggest = true, isAttacked = false;
            // Clear all previous suggestions
            if (suggestions != null) {
                for (EPositions suggestion : suggestions) {
                    if (clickedCellID == suggestion.rawValue()) {
                        isShouldSuggest = false;
                        if (board.getPiece(suggestion) != null) { // actacked
                            // User actacked a piece
                            isAttacked = true;
                            if (this.attackCallback != null) {
                                this.attackCallback.receiveResult(new Piece[] {
                                        board.getPiece(prevClickedPos),
                                        board.getPiece(suggestion)
                                });
                            }
                        }
                        this.move(prevClickedPos, suggestion);
                        this.isLockedMoving = true;
                        if (!isAttacked && this.movingCallback != null) { // moved a piece
                            this.movingCallback.receiveResult(new Object[] {
                                board.getPiece(suggestion),
                                prevClickedPos,
                                suggestion
                            });
                        }
                        
                        if (doneCallback != null) {
                            doneCallback.response();
                        }
                    }
                    this.uncheckAt(suggestion.rawValue());
                }
                prevClickedPos = null;
                suggestions = null;
            }

            // Get all possible suggestions
            Piece piece = board.getPiece(clickedPosition);
            if (isShouldSuggest && piece != null && piece.getColor() == this.playerColor) {
                prevClickedPos = EPositions.getPosition(clickedCellID);
                suggestions = piece.suggestMoving();
                suggestions.forEach((pos) -> {
                    this.checkAt(pos.rawValue());
                });
            }
        }
    }
    
    public void move(EPositions prevPos, EPositions pos) {
        this.board.move(prevPos, pos);
        this.setPieceImageAt(pos.rawValue(), this.getPieceImageAt(prevPos.rawValue()));
        this.clearPieceImageAt(prevPos.rawValue());
    }
    
    public void moveByAI(EPositions prevPos, EPositions pos) {
        boolean isAttacked = false;
        
        if (board.getPiece(pos) != null) { // actacked
            // User actacked a piece
            isAttacked = true;
            if (this.attackCallback != null) {
                this.attackCallback.receiveResult(new Piece[] {
                        board.getPiece(prevPos),
                        board.getPiece(pos)
                });
            }
        }

        this.board.move(prevPos, pos);
        this.setPieceImageAt(pos.rawValue(), this.getPieceImageAt(prevPos.rawValue()));
        this.clearPieceImageAt(prevPos.rawValue());
        
        if (!isAttacked && this.movingCallback != null) { // moved a piece
            this.movingCallback.receiveResult(new Object[] {
                board.getPiece(pos),
                prevPos,
                pos
            });
        }
    }
    
    private void setPieceImageAt(int cellID, ImageIcon image) {
        UIBoardCellController boardCellController = boardCells[cellID];
        if (boardCellController != null) {
            boardCellController.setPieceImage(image);
        }
    }
    
    private ImageIcon getPieceImageAt(int cellID) {
        UIBoardCellController boardCellController = boardCells[cellID];
        if (boardCellController != null) {
            return boardCellController.getPieceImage();
        }
        return null;
    }
    
    private void clearPieceImageAt(int cellID) {
        UIBoardCellController boardCellController = boardCells[cellID];
        if (boardCellController != null) {
            boardCellController.clearPieceImage();
        }
    }
    
    private void checkAt(int cellID) {
        UIBoardCellController boardCellController = boardCells[cellID];
        if (boardCellController != null) {
            boardCellController.check();
        }
    }
    
    private void uncheckAt(int cellID) {
        UIBoardCellController boardCellController = boardCells[cellID];
        if (boardCellController != null) {
            boardCellController.uncheck();
        }
    }
    
    public Board getBoard() {
        return board;
    }
    
}
