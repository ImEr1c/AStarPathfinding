package com.imer1c;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class PathFinder {
    private final Queue<Node> openSet = new PriorityQueue<>(500);
    private final Grid grid;
    private final Node start, end;
    private Node bestNode;
    private float bestScore = Float.MAX_VALUE;
    private int counter = 1;
    private boolean finished;

    public PathFinder(Grid grid) {
        this.grid = grid;
        this.start = grid.getStart();
        this.end = grid.getEnd();
    }
/*
    public void search()
    {
        if (this.start == null || this.end == null)
        {
            return;
        }

        this.start.h = this.start.distanceTo(end);
        this.start.g = 0;
        this.start.score = this.start.h;
        this.start.was = true;

        openSet.offer(this.start);

        int counter = 1;
        Node bestNode = this.start;
        int bestNodeScore = Integer.MAX_VALUE;

        while (!this.openSet.isEmpty())
        {
            Node node = this.openSet.poll();

            counter++;

            System.out.println(node);

            node.counter = counter;
            node.closed = true;

            if (node.equals(end))
            {
                bestNode = node;
                break;
            }

       /*     node.h = node.distanceTo(end);
            node.g = node.parent == null ? node.distanceTo(start) : node.parent.g + node.distanceTo(node.parent);
            node.score = node.g + node.h;*/
/*
            if (node.score < bestNodeScore) {
                System.out.println("Best node: " + node.x + " " + node.y);
                bestNode = node;
                bestNodeScore = node.h;
            }

            searchNode(node);
        }

        System.out.println("break");

        List<Node> path = this.computePath(bestNode);

        for (Node node : path) {
            node.setType(Node.Type.PATH);
        }
    }

    public void searchNode(Node n)
    {
        this.checkAndAdd(n, this.getNeighbor(n, 0, 1));
        this.checkAndAdd(n, this.getNeighbor(n, 1, 0));
        this.checkAndAdd(n, this.getNeighbor(n, -1, 0));
        this.checkAndAdd(n, this.getNeighbor(n, 0, -1));
        this.checkAndAdd(n, this.getNeighbor(n, 1, 1));
        this.checkAndAdd(n, this.getNeighbor(n, -1, -1));
        this.checkAndAdd(n, this.getNeighbor(n, -1, 1));
        this.checkAndAdd(n, this.getNeighbor(n, 1, -1));
        /*
        Node deltaNode = Node.ZERO;

        if (n.parent != null)
        {
            deltaNode = n.subtract(n.parent);
        }

        if (deltaNode.x >= 0)
        {
            this.checkAndAdd(n, this.getNeighbor(n, 1, 0));
        }

        if (deltaNode.x <= 0)
        {
            this.checkAndAdd(n, this.getNeighbor(n, -1, 0));
        }

        if (deltaNode.y >= 0)
        {
            this.checkAndAdd(n, this.getNeighbor(n, 0, 1));
        }

        if (deltaNode.y <= 0)
        {
            this.checkAndAdd(n, this.getNeighbor(n, 0, -1));
        }*/
    /*}
    private void checkAndAdd(Node parent, Node n)
    {
        if (n == null || n.closed)
        {
            return;
        }

        if (n.getType() != Node.Type.START) {
            n.setType(Node.Type.USED);
        }

        if (n.getType() == Node.Type.WALL)
        {
            return;
        }

        int g = parent.g + parent.distanceTo(n);

        if (g < n.g || !n.was)
        {
            n.parent = parent;
            n.g = g;
            n.score = n.g + n.distanceTo(end);

            n.was = true;
            this.openSet.offer(n);
        }
    }
    */

    public void computePath(Node bestNode)
    {
        while (bestNode != null)
        {
            if (bestNode.getType() != Node.Type.START && bestNode.getType() != Node.Type.END) {
                bestNode.setType(Node.Type.PATH);
            }

            System.out.println(bestNode);

            bestNode = bestNode.parent;
        }
    }

    private Node getNeighbor(Node n, int x, int y)
    {
        return this.grid.getNode(n.x + x, n.y + y);
    }

    public void init()
    {
        start.g = 0;
        start.h = start.distanceTo(end);
        start.score = start.g + start.h;

        this.openSet.add(start);

        bestNode = start;
        bestScore = Float.MAX_VALUE;
    }

    public void search() {

        if (finished ||counter > 100000) {
            return;
        }

        Node node = this.openSet.poll();

        if (node == null)
        {
            return;
        }

        if (node.equals(end)) {
            bestNode = node;
            finished = true;
            finish();
        }

        counter++;
        node.counter = counter;
        node.closed = true;

        if (node.getType() != Node.Type.START && node.getType() != Node.Type.END) {
            node.setType(Node.Type.USED);
        }

        if (node.score < bestScore) {
            bestScore = node.score;
            bestNode = node;
        }

        this.searchNode(node);
    }

    public void finish()
    {
        System.out.println("FINISHED");
        computePath(bestNode);
    }

    private void searchNode(Node node) {
        Node deltaNode = Node.ZERO;

        if (node.parent != null)
        {
            deltaNode = node.subtract(node.parent);
        }

        if (deltaNode.x >= 0)
        {
            this.checkNode(this.getNeighbor(node, 1, 0), node);
        }

        if (deltaNode.x <= 0)
        {
            this.checkNode(this.getNeighbor(node, -1, 0), node);
        }

        if (deltaNode.y >= 0)
        {
            this.checkNode(this.getNeighbor(node, 0, 1), node);
        }

        if (deltaNode.y <= 0)
        {
            this.checkNode(this.getNeighbor(node, 0, -1), node);
        }
    }

    private void checkNode(Node node, Node parent)
    {
        if (node == null || parent == null || node.getType() == Node.Type.WALL || node.equals(parent) || node.closed)
        {
            return;
        }

        float g = parent.g + parent.distanceTo(node);

        if (!this.openSet.contains(node) || g < node.g)
        {
            node.g = g;
            node.h = node.distanceTo(end);
            node.score = node.g + node.h;
            node.parent = parent;

            if (!this.openSet.contains(node))
            {
                this.openSet.add(node);
            }
        }
    }
}
