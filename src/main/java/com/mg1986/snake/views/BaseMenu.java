package com.mg1986.snake.views;

import java.awt.*;
import javax.swing.*;

public class BaseMenu extends JPanel {

    public static final int menuWidth = 480;
    public static final int menuHeight = 715;

    public void sync () {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("inux") || osName.contains("unix")) {
            Toolkit.getDefaultToolkit().sync();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public void addJLabel (String message, int fontSize) {

        JLabel jLabel = new JLabel(message);
        jLabel.setForeground(Color.WHITE);
        jLabel.setFont(new Font("Century Gothic", jLabel.getFont().getStyle(), fontSize));
        jLabel.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.add(jLabel);
    }
}
