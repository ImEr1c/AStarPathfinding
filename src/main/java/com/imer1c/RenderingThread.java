package com.imer1c;

import javax.swing.*;

public class RenderingThread extends Thread {
    private final JPanel panel;

    public RenderingThread(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        double interval = 1000000000 / 60;
        double next = System.nanoTime();

        while (panel != null)
        {
            this.panel.repaint();

            long current = System.nanoTime();

            long left = (long) (Math.min(0, next - current));

            left /= 1000000;

            next += interval;

            try {
                Thread.sleep(left);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
