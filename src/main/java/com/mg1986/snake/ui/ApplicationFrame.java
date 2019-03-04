package com.mg1986.snake.ui;

import javax.swing.*;

/**
 * Author: Matthew Gray
 * Last Modified: 3/3/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.snake.ui.ApplicationFrame class
 */

public class ApplicationFrame extends JFrame {

    //------------------------------------------------------------------------------------------------------------------
    // ApplicationFrame constructor - Takes height and width as inputs
    public ApplicationFrame (int applicationWidth, int applicationHeight) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(applicationWidth, applicationHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
