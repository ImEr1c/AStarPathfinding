package com.imer1c;

import java.awt.*;

public class Node implements Comparable<Node> {
    public static final Node ZERO = new Node(0, 0);
    public final int x;
    public final int y;
    public int counter;
    public float score, g, h;
    public Node parent;
    public boolean closed;
    private Type type = Type.FREE;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void click()
    {
        if (this.type == Type.WALL)
        {
            this.type = Type.FREE;
        }
        else if (this.type == Type.FREE)
        {
            this.type = Type.WALL;
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public float distanceTo(Node n)
    {
        int x = this.x - n.x;
        int y = this.y - n.y;

        return (float) Math.sqrt(x * x + y * y);
    }

    public Node subtract(Node n)
    {
        return new Node(this.x - n.x, this.y - n.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node node))
        {
            return false;
        }

        return node.x == this.x && node.y == this.y;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        if (this.score < o.score)
        {
            return -1;
        }

        if (this.score > o.score)
        {
            return 1;
        }

        return this.counter - o.counter;
    }

    public enum Type {
        WALL(Color.BLACK),
        FREE(Color.WHITE),
        USED(Color.YELLOW),
        PATH(Color.BLUE),
        START(Color.GREEN),
        END(Color.RED);

        public final Color color;

        Type(Color color) {
            this.color = color;
        }
    }
}
