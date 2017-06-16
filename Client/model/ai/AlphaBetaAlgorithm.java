package model.ai;

import model.type.Board;
import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPositions;
import model.type.piece.Piece;


import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by InNovaTioN on 15/06/2017.
 */
public class AlphaBetaAlgorithm
{
    public AlphaBetaAlgorithm()
    {

    }

    public Node calcMove(Board beginStatus, int maxdepth) // max depth should be an even num
    {
        this.root = new Node(null, beginStatus.cloneBoard(), EPieceColors.WHITE, null, null);
        allState = new HashSet<>();
        AlphaBeta(root, maxdepth, -99999, 99999, false);
        return this.root.getResult();
    }

    public int AlphaBeta(Node node, int depth, int alpha, int beta, boolean isMaximizingPlayer)
    {
        GenChildNode(node);
        if (0 == depth || node.isTerminalNode()) // depth = 0 or node is terminal node
        {
            return Evaluate(node.getStatus());
        }
        if (isMaximizingPlayer)
        {
            int v = -Node.Value.INFINITY;
            ArrayList<Node> children = node.getChildren();
            for (Node child : children)
            {
               v = Max(v, AlphaBeta(child, depth - 1, alpha, beta, false));
               alpha =  Max(alpha, v);
               if (beta <= alpha)
                   break;
            }
            node.setValue(v);
            return v;
        }
        else
        {
            int v = Node.Value.INFINITY;
            ArrayList<Node> children = node.getChildren();
            for (Node child : children)
            {
                v = Min(v, AlphaBeta(child, depth - 1, alpha, beta, true));
                beta =  Min(beta, v);
                if (beta <= alpha)
                    break;
            }
            node.setValue(v);
            return v;
        }
    }

    int Max(int a, int b)
    {
        return a > b ? a : b;
    }

    int Min(int a, int b)
    {
        return a < b ? a : b;
    }
    int Evaluate(Board board)
    {
        int computer = 0;
        int human = 0;
        for (int i  = 0; i < 8; ++i)
        {
            for (int j = 0; j < 8; ++j)
            {
                Piece p = board.getPiece(EPositions.getPosition(i, j));
                if (null != p)
                {
                    if (p.getColor() == EPieceColors.BLACK)
                    {
                        computer += p.getHeuristicValue();
                    }
                    else
                    {
                        human += p.getHeuristicValue();
                    }
                }

            }
        }
        return human - computer;
    }

    void GenChildNode(Node parent)
    {
        Board status = parent.getStatus();
        for (int i = 0; i < 8; ++i)
        {
            for (int j = 0; j < 8; ++j)
            {
                Piece p = status.getPiece(EPositions.getPosition(i, j));
                if (null != p  && p.getColor() != parent.getTurn())
                {
                    ArrayList<EPositions> movable = p.suggestMoving();
                    for (EPositions position : movable)
                    {
                        Board newStatus = status.cloneBoard();
                        newStatus.move(EPositions.getPosition(i, j), position);
                        if (!this.allState.contains(newStatus))
                        {
                            Node node = new Node(parent, newStatus,
                                    parent.getTurn() == EPieceColors.WHITE ?  EPieceColors.BLACK : EPieceColors.WHITE ,
                                    EPositions.getPosition(i, j), position);
                            parent.AddChild(node);
                            allState.add(newStatus);
                        }

//                        if (p.getType() == EPieces.PAWN)
//                        {
//                            if ((6 == i && node.getTurn() == EPieceColors.WHITE) ||
//                                    (1 == i && node.getTurn() == EPieceColors.BLACK))
//                            {
//                                newStatus = newStatus.getClone();
//                                newStatus.setPiece(position, new Queen(EPieces.QUEEN, position, node.getTurn(), null, null));
//                            }
//                        }
                    }
                }
            }

        }
    }
    private HashSet<Board> allState;
    private Node root;

}
