package com.mg1986.snake.ui;

import java.awt.*;
import javax.swing.*;
import com.mg1986.snake.model.Apple;
import com.mg1986.snake.model.SnakeHead;

/**
 * Author: Matthew Gray
 * Last Modified: 3/3/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.snake.ui.TitlePanel class
 */

public class TitlePanel extends BasePanel {

    // Panel Height
    private static final int menuWidth = 480;

    // Panel Width
    private static final int menuHeight = 715;

    //------------------------------------------------------------------------------------------------------------------
    // TitlePanel constructor -
    public TitlePanel() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(menuWidth, menuHeight));
        setMaximumSize(getPreferredSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(true);
        add(Box.createVerticalStrut(175));
        addJLabel("SNAKE", 125);
        addJLabel("A game by Matt Gray 2018", 25);
        add(Box.createVerticalStrut(80));
        addJLabel("[Press ENTER]", 30);
    }

    //------------------------------------------------------------------------------------------------------------------
    // paintComponent() - Animate panel
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Y coordinate all menu decorations drawn on
        int y = 385;

        // Draw Apple
        Apple apple = new Apple(440, y);
        g.drawImage(apple.getAppleImage(), apple.getCurrentX(), apple.getCurrentY(), this);

        // Draw Snake
        SnakeHead snakeHead = new SnakeHead(380, y);
        g.drawImage(snakeHead.snakeHeadRight, snakeHead.getCurrentX(), snakeHead.getCurrentY(), this);
        g.setColor(Color.GREEN);

        int segmentSize = snakeHead.segmentSize;
        for(int x = 364; x >= 40; x = x - segmentSize) {
            g.drawRect(x, y, segmentSize, segmentSize);
        }
    }
}
