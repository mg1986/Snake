package com.mg1986.snake;

import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel {

    public void sync () {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("inux") || osName.contains("unix")) {
            Toolkit.getDefaultToolkit().sync();
        }
    }
}
