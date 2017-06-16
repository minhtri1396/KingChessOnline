package model.ai;

import model.type.Board;
import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPositions;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by InNovaTioN on 16/06/2017.
 */
public class Node
{
    public Node(Node parent, Board status, EPieceColors turn, EPositions from, EPositions to)
    {
        this.parent = parent;
        this.status = status;
        this.turn = turn;
        this.from = from;
        this.to = to;
    }

    public void AddChild(Node child)
    {
        children.add(child);
    }

    public boolean isTerminalNode()
    {
        return 0 == this.children.size();
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

    private Node parent;
    private Board status;
    private int value;
    private EPieceColors turn;
    private EPositions from;
    private EPositions to;
    private ArrayList<Node> children = new ArrayList<>();

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
        this.value = value;
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
        public static final int INFINITY = 999999;
    }
}
