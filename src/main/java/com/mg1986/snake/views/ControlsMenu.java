package com.mg1986.snake.views;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.ApplicationPanel class -
 */

public class ControlsMenu extends BaseMenu {

    //------------------------------------------------------------------------------------------------------------------
    public ControlsMenu() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(menuWidth, menuHeight));
        setMaximumSize(getPreferredSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(100));
        addJLabel("Controls", 100);
        add(Box.createVerticalStrut(30));
        addJLabel("Navigation:   Arrow keys", 30);
        add(Box.createVerticalStrut(25));
        addJLabel("Pause:   p key", 30);
        add(Box.createVerticalStrut(25));
        addJLabel("Quit:   q key", 30);
        add(Box.createVerticalStrut(25));
        addJLabel("[Press ENTER to play]", 35);
        setVisible(true);

        revalidate();
        repaint();
        sync();

    }
}
