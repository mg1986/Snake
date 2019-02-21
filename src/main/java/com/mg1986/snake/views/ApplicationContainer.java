package com.mg1986.snake.views;

import com.mg1986.snake.controllers.ApplicationController;

import java.awt.*;
import javax.swing.*;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.ApplicationContainer class -
 */

public class ApplicationContainer extends BasePanel {

    //------------------------------------------------------------------------------------------------------------------
    public ApplicationContainer(int width, int height, ApplicationController applicationController) {

        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(applicationController);
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(getPreferredSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(true);
    }
}
