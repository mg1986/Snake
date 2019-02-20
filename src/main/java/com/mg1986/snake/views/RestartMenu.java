package com.mg1986.snake.views;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.ApplicationPanel class -
 */

public class RestartMenu extends BaseMenu {

    public int menuHeightUpdated = 400;
    //------------------------------------------------------------------------------------------------------------------
    public RestartMenu() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(menuWidth, menuHeightUpdated));
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
