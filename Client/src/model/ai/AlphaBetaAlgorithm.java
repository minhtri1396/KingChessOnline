package model.ai;

import model.type.Board;
import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPositions;
import model.type.piece.Piece;
import java.util.ArrayList;

public class AlphaBetaAlgorithm
{
    public AlphaBetaAlgorithm()
    {

    }

    public Node calcMove(Board beginStatus, int maxdepth) // max depth should be an even num
    {
        this.maxdepth = maxdepth;
        this.root = new Node(null, beginStatus.cloneForAI(), EPieceColors.WHITE,
                null, null, Node.Type.MIN, 0);
        GenChildNode(root);
        ArrayList<Node> children = root.getChildren();
        for (Node child : children)
        {
            if (!child.isTerminalNode())
            {
                AlphaBeta(child);
                child.setValue(child.getMaxValueChildren());
                child.clearChildren();
            }
            else
            {
                child.setValue(1000);
            }
            child.getParent().setValue(child.getValue());
        }
        return this.root.getResult();
    }

    public int AlphaBeta(Node node)
    {
        GenChildNode(node);
        ArrayList<Node> children = node.getChildren();
        for (Node child : children)
        {
            if (child.getDepth() < this.maxdepth) // if cur depth is not max depth
            {
                if (!child.isTerminalNode()) // if child is not terminal node (2 kings are survive)
                {
                    AlphaBeta(child);
                    if (child.getType() == Node.Type.MAX)
                    {
                        child.setValue(child.getMinValueChildren());
                    } else
                    {
                        child.setValue(child.getMaxValueChildren());
                    }
                    child.clearChildren();
                }
                else // this node, which is terminal node cannot expand
                {
                    if (child.getTurn() == EPieceColors.BLACK) //of computer
                    { // cur depth is not max depth but cannot expand, that mean human cannot move anymore
                        child.setValue(1000);
                    }
                    else
                    {
                        child.setValue(-1000);
                    }
                }
            }
            else // if cur depth is max depth => get heuristic value of node
            {
                child.setValue(Evaluate(child.getStatus()));
            }
            //back track for parent node
            child.getParent().setValue(child.getValue());

            if (Prunning(child)) // check alpha beta prunning
            {
                break;
            }
        }
        return 0;
    }

    public boolean Prunning(Node node)
    {
        if (2 < node.getDepth())
        {
            if (1 < node.getChildrenSize())
            {
                if (node.getType() == Node.Type.MAX)
                {
                    if (node.getValue() > node.getParent().getParent().getValue())
                    {
                        return true;
                    }
                }
                else
                {
                    if (node.getValue() < node.getParent().getParent().getValue())
                    {
                        return true;
                    }
                }
            }
        }
        else if (node.getParent().getParent().getValue() != 5000)
        {
            if (node.getValue() < node.getParent().getParent().getValue())
            {
                return true;
            }
        }
        return false;
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
        return computer - human;
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
                        Board newStatus = status.cloneForAI();
                        newStatus.moveByAI(EPositions.getPosition(i, j), position);
                            Node node = new Node(parent, newStatus,
                                    parent.getTurn() == EPieceColors.WHITE ?  EPieceColors.BLACK : EPieceColors.WHITE ,
                                    EPositions.getPosition(i, j), position,
                                    parent.getType() == Node.Type.MAX ? Node.Type.MIN : Node.Type.MAX,
                                    parent.getDepth() + 1);
                            parent.AddChild(node);
                    }
                }
            }
        }
    }
    private Node root;
    private int maxdepth;

}
