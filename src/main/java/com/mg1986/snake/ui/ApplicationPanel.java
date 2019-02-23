package com.mg1986.snake.ui;

import com.mg1986.snake.controllers.ApplicationController;

import java.awt.*;
import javax.swing.*;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.ApplicationPanel class -
 */

public class ApplicationPanel extends BasePanel {

    //------------------------------------------------------------------------------------------------------------------
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
