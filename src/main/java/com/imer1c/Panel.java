package com.imer1c;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    public static final int NODE_SIZE = 48;

    private final List<Node> selectedNodes = new ArrayList<>();
    private final Grid grid = new Grid();
    private boolean leftPressed, enterPressed;
    private PathFinder finder;
    private int delay = 10;

    public Panel()
    {
        Dimension dim = new Dimension((Grid.NODES_HORIZONTALLY + 1) * NODE_SIZE, (Grid.NODES_VERTICALLY + 1) * NODE_SIZE);

        setMinimumSize(dim);
        setPreferredSize(dim);
        setMaximumSize(dim);

        RenderingThread thread = new RenderingThread(this);
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Node[][] nodes = this.grid.getNodes();

        for (int i = 0; i < nodes.length; i++) {
            Node[] nodesVertical = nodes[i];

            for (int j = 0; j < nodesVertical.length; j++) {
                this.drawNode(i, j, grid.getNode(i, j), (Graphics2D) g);
            }
        }

        if (delay-- <= 0)
        {
            delay = 10;

            if (finder == null)
            {
                return;
            }

            finder.search();
        }
    }

    private void drawNode(int x, int y, Node n, Graphics2D g)
    {
        g.setColor(n.getType().color);
        g.fillRect(x * NODE_SIZE, y * NODE_SIZE, NODE_SIZE, NODE_SIZE);

        g.setColor(Color.BLACK);
        g.drawRect(x * NODE_SIZE, y * NODE_SIZE, NODE_SIZE, NODE_SIZE);

        g.drawString(String.valueOf(n.x), x * NODE_SIZE + 2, y * NODE_SIZE + 13);
        g.drawString(String.valueOf(n.y), x * NODE_SIZE + 2, y * NODE_SIZE + 32);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = (e.getX() - 8) / NODE_SIZE;
        int y = (e.getY() - 31) / NODE_SIZE;

        Node n = this.grid.getNode(x, y);

        if (e.getButton() == MouseEvent.BUTTON2)
        {
            this.grid.setEndNode(n);
            return;
        }
        else if (e.getButton() == MouseEvent.BUTTON3)
        {
            this.grid.setStartNode(n);
            return;
        }

        n.click();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            this.leftPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.selectedNodes.clear();

        if (e.getButton() == MouseEvent.BUTTON1)
        {
            this.leftPressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!this.leftPressed)
        {
            return;
        }

        int x = (e.getX() - 8) / NODE_SIZE;
        int y = (e.getY() - 31) / NODE_SIZE;

        Node n = this.grid.getNode(x, y);

        if (n == null || this.selectedNodes.contains(n))
        {
            return;
        }

        this.selectedNodes.add(n);
        n.click();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10)
        {
            if (this.grid.getStart() == null || this.grid.getEnd() == null)
            {
                return;
            }

            if (finder == null)
            {
                finder = new PathFinder(this.grid);
                finder.init();
            }
        }

        if (e.getKeyCode() == 8)
        {
            finder = null;

            for (int i = 0; i <= Grid.NODES_HORIZONTALLY; i++) {
                for (int j = 0; j <= Grid.NODES_VERTICALLY; j++) {
                    Node node = this.grid.getNode(i, j);

                    if (node.getType() == Node.Type.USED || node.getType() == Node.Type.PATH)
                    {
                        node.setType(Node.Type.FREE);
                        node.closed = false;
                        node.g = 0;
                        node.h = 0;
                        node.score = 0;
                        node.parent = null;
                    }
                }
            }

            repaint();
        }
    }
}
