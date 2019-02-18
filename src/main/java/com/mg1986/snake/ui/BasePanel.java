package com.mg1986.snake.ui;

import java.awt.*;
import javax.swing.*;

public class BasePanel extends JPanel {

    public void sync () {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("inux") || osName.contains("unix")) {
            Toolkit.getDefaultToolkit().sync();
        }
    }
}
