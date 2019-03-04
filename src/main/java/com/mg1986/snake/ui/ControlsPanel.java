package com.mg1986.snake.ui;

import java.awt.*;
import javax.swing.*;

/**
 * Author: Matthew Gray
 * Last Modified: 3/3/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.snake.ui.ControlsPanel class
 */

public class ControlsPanel extends BasePanel {

    // Panel Height
    private static final int menuWidth = 480;

    // Panel Width
    private static final int menuHeight = 715;

    //------------------------------------------------------------------------------------------------------------------
    // ControlsPanel constructor -
    public ControlsPanel() {

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
    }
}
