package com.mg1986.snake.ui;

import java.awt.*;
import javax.swing.*;

/**
 * Author: Matthew Gray
 * Last Modified: 3/3/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.snake.ui.BasePanel class
 */

public class BasePanel extends JPanel {

    //------------------------------------------------------------------------------------------------------------------
    // sync() - Addresses bug in UNIX/LINUX graphics
    public void sync () {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("inux") || osName.contains("unix")) {
            Toolkit.getDefaultToolkit().sync();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // addJLabel() - Add JLabel to Panel
    public void addJLabel (String message, int fontSize) {

        JLabel jLabel = new JLabel(message);
        jLabel.setForeground(Color.WHITE);
        jLabel.setFont(new Font("Century Gothic", jLabel.getFont().getStyle(), fontSize));
        jLabel.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.add(jLabel);
    }
}
