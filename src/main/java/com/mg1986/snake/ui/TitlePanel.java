package com.mg1986.snake.ui;

import java.awt.*;
import javax.swing.*;

import com.mg1986.snake.model.Apple;
import com.mg1986.snake.model.SnakeHead;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.ApplicationPanel class -
 */

public class TitlePanel extends BasePanel {

    //------------------------------------------------------------------------------------------------------------------
    public TitlePanel() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(menuWidth, menuHeight));
        setMaximumSize(getPreferredSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(true);
        add(Box.createVerticalStrut(175));
        addJLabel("SNAKE", 125);
        addJLabel("A game by Matt Gray 2018", 20);
        add(Box.createVerticalStrut(80));
        addJLabel("[Press ENTER]", 30);

        revalidate();
        repaint();
        sync();
    }

    //------------------------------------------------------------------------------------------------------------------
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // y coordinate all menu decorations drawn on
        int y = 385;

        // Draw Apple
        Apple apple = new Apple(440, y);
        g.drawImage(apple.getAppleImage(), apple.getCurrentX(), apple.getCurrentY(), this);

        // Draw Snake
        SnakeHead snakeHead = new SnakeHead(380, y, Color.GREEN);
        g.drawImage(snakeHead.snakeHeadRight, snakeHead.getCurrentX(), snakeHead.getCurrentY(), this);
        g.setColor(snakeHead.getColor());

        int segmentSize = snakeHead.segmentSize;
        for(int x = 364; x >= 40; x = x - segmentSize) {
            g.drawRect(x, y, segmentSize, segmentSize);
        }

        revalidate();
        repaint();
        sync();
    }
}
