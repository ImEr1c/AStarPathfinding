package com.imer1c;

import javax.swing.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Panel pane = new Panel();
        frame.addMouseListener(pane);
        frame.addMouseMotionListener(pane);
        frame.addKeyListener(pane);
        frame.setContentPane(pane);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}