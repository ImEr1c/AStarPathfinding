package com.imer1c;

public class Grid {

    public static final int NODES_HORIZONTALLY = 32;
    public static final int NODES_VERTICALLY = 16;

    private final Node[][] nodes;
    private Node start, end;

    public Grid() {
        this.nodes = new Node[NODES_HORIZONTALLY + 1][NODES_VERTICALLY + 1];

        for (int i = 0; i < this.nodes.length; i++) {
            Node[] nodesVertical = this.nodes[i];

            for (int j = 0; j < nodesVertical.length; j++) {
                this.nodes[i][j] = new Node(i, j);
            }
        }
    }

    public Node getNode(int x, int y)
    {
        if (!areCoordinatesValid(x, y))
        {
            return null;
        }

        return this.nodes[x][y];
    }

    private boolean areCoordinatesValid(int x, int y)
    {
        return x >= 0 && x <= NODES_HORIZONTALLY && y >= 0 && y <= NODES_VERTICALLY;
    }

    public void setStartNode(Node n)
    {
        if (this.start != null)
        {
            this.start.setType(Node.Type.FREE);
        }

        n.setType(Node.Type.START);
        this.start = n;
    }

    public void setEndNode(Node n)
    {
        if (this.end != null)
        {
            this.end.setType(Node.Type.FREE);
        }

        n.setType(Node.Type.END);
        this.end = n;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public Node[][] getNodes() {
        return nodes;
    }
}
