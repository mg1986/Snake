package com.mg1986.snake.ui;

import java.awt.*;
import javax.swing.*;

/**
 * Author: Matthew Gray
 * Last Modified: 02/20/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.ApplicationPanel class -
 */

public class RestartPanel extends BasePanel {

    //------------------------------------------------------------------------------------------------------------------
    public RestartPanel(int width, int height) {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(getPreferredSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(175));
        addJLabel("You Lose!", 75);
        add(Box.createVerticalStrut(15));
        addJLabel("[Press ENTER to restart game]", 25);
        setVisible(true);

        revalidate();
        repaint();
        sync();
    }
}
