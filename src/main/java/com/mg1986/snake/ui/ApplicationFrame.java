package com.mg1986.snake.ui;

import javax.swing.*;

public class ApplicationFrame extends JFrame {

    public ApplicationFrame (int applicationWidth, int applicationHeight) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(applicationWidth, applicationHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
