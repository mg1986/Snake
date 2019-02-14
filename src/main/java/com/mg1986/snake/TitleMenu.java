package com.mg1986.snake;

import java.awt.*;
import javax.swing.*;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.GameMenu class -
 */

public class TitleMenu extends BasePanel {

    private static final int menuWidth = 480;
    private static final int menuHeight = 715;

    //------------------------------------------------------------------------------------------------------------------
    public TitleMenu () {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(menuWidth, menuHeight));
        setMaximumSize(getPreferredSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(true);

        revalidate();
        repaint();
        sync();
    }

    //------------------------------------------------------------------------------------------------------------------
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // y coordinate all menu decorations drawn on
        int y = 385;

        // Draw com.mg1986.Snake.Apple
        Apple apple = new Apple(440, y);
        g.drawImage(apple.appleImage, apple.getCurrentX(), apple.getCurrentY(), this);

        // Draw com.mg1986.Snake.Snake
        SnakeSegment snakeHead = new SnakeSegment(380, y, Color.GREEN);
        g.drawImage(snakeHead.snakeHeadRight, snakeHead.getCurrentX(), snakeHead.getCurrentY(), this);
        g.setColor(snakeHead.getColor());

        for(int x = 364; x >= 40; x = x - 16) {
            g.drawRect(x, y, 15, 15);
        }

        revalidate();
        repaint();
        sync();
    }
}
