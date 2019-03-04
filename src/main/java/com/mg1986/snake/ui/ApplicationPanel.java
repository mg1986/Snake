package com.mg1986.snake.ui;

import java.awt.*;
import javax.swing.*;
import com.mg1986.snake.controllers.ApplicationController;

/**
 * Author: Matthew Gray
 * Last Modified: 3/3/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.snake.ui.ApplicationPanel class
 */

public class ApplicationPanel extends BasePanel {

    //------------------------------------------------------------------------------------------------------------------
    // ApplicationPanel constructor - Takes height, width, and KeyListener as inputs
    public ApplicationPanel(int width, int height, ApplicationController applicationController) {

        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(applicationController);
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(getPreferredSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(true);
    }
}
