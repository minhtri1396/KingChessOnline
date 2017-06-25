package model.ai;

import model.type.Board;
import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPositions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Node
{
    public Node(Node parent, Board status, EPieceColors turn, EPositions from, EPositions to, Type type, int depth)
    {
        this.parent = parent;
        this.status = status;
        this.turn = turn;
        this.from = from;
        this.to = to;
        this.type = type;
        this.depth = depth;
    }

    public void AddChild(Node child)
    {
        children.add(child);
    }

    public boolean isTerminalNode()
    {
        return !(this.status.isKingSurvive(EPieceColors.BLACK) && this.status.isKingSurvive(EPieceColors.WHITE));
    }

    public Node getResult()
    {
        Node node = null;
        // find min value
        ArrayList<Integer> candidate = new ArrayList<>();
        for (int i = 0; i < this.children.size(); ++i)
        {
            if (this.children.get(i).value == this.value)
            {
                candidate.add(i);
            }
        }
        Random rand = new Random();
        int index = candidate.get(rand.nextInt(candidate.size()));
        return this.children.get(index);
    }

    public void sortChildren()
    {
        if (this.turn == EPieceColors.WHITE) // sort ascending
        {
            Collections.sort(this.children, (Node o1, Node o2) -> o1.value - o2.value);
        }
        else
        {
            Collections.sort(this.children, (Node o1, Node o2) -> o2.value - o1.value);
        }
    }

    public int getMinValueChildren()
    {
        Node min = Collections.min(this.children, (Node o1, Node o2) -> o1.value - o2.value);
        return min.value;
    }

    public int getMaxValueChildren()
    {
        Node max = Collections.max(this.children, (Node o1, Node o2) -> o1.value - o2.value);
        return max.value;
    }

    public void clearChildren()
    {
        this.children.clear();
    }

    public int getChildrenSize()
    {
        return this.children.size();
    }

    private Node parent;
    private Board status;
    private int value = Value.DEFAULT;
    private EPieceColors turn;
    private EPositions from;
    private EPositions to;
    private Type type;
    private int depth;
    private ArrayList<Node> children = new ArrayList<>();

    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    public int getDepth()
    {
        return depth;
    }

    public void setDepth(int depth)
    {
        this.depth = depth;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public Board getStatus()
    {
        return status;
    }

    public void setStatus(Board status)
    {
        this.status = status;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        if (this.type == Type.MAX)
        {
            if ((this.value > value && Value.DEFAULT != this.value) || Value.DEFAULT == this.value)
                this.value = value;
        }
        else
        {
            if ((this.value < value && Value.DEFAULT != this.value) || Value.DEFAULT == this.value)
                this.value = value;
        }
    }

    public EPieceColors getTurn()
    {
        return turn;
    }

    public void setTurn(EPieceColors turn)
    {
        this.turn = turn;
    }

    public EPositions getFrom()
    {
        return from;
    }

    public void setFrom(EPositions from)
    {
        this.from = from;
    }

    public EPositions getTo()
    {
        return to;
    }

    public void setTo(EPositions to)
    {
        this.to = to;
    }

    public ArrayList<Node> getChildren()
    {
        return children;
    }

    public void setChildren(ArrayList<Node> children)
    {
        this.children = children;
    }

    public class Value
    {
        public static final int DEFAULT = 5000;
    }

    public enum Type
    {
        MIN,
        MAX
    }

}
