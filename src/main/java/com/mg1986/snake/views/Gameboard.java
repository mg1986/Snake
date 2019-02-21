package com.mg1986.snake.views;

import java.awt.*;

import com.mg1986.snake.controllers.ApplicationController;
import com.mg1986.snake.models.Apple;
import com.mg1986.snake.models.Snake;
import com.mg1986.snake.models.SnakeHead;
import com.mg1986.snake.models.SnakeElement;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.ApplicationContainer class -
 */

public class Gameboard extends BasePanel {

    private Apple apple;
    private Snake snake;
    private static final int boardWidth = 480;
    private static final int boardHeight = 640;
    private static final int slitherGap = 1;
    private static final int modulusValue1 = 4;
    private static final int modulusValue2 = 2;
    private static final int divideValue = 10;

    //------------------------------------------------------------------------------------------------------------------
    public Gameboard (Apple apple, Snake snake, ApplicationController applicationController) {
        this.apple = apple;
        this.snake = snake;
        setFocusable(true);
        addKeyListener(applicationController);
        setVisible(true);
        setBackground(Color.BLACK);
        setSize(boardWidth, boardHeight);
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setMaximumSize(getPreferredSize());
        requestFocus();
    }

    //------------------------------------------------------------------------------------------------------------------
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw com.mg1986.Snake.Apple object
        g.drawImage(apple.getAppleImage(), apple.getCurrentX(), apple.getCurrentY(), this);

        // Draw com.mg1986.Snake.Snake object
        for (int idx = 0; idx < snake.getSnakeBodySize(); idx++) {

            SnakeElement SnakeElement = snake.getSnakeBody().get(idx);

            int x = SnakeElement.getCurrentX();
            int y = SnakeElement.getCurrentY();
            int segmentSize = SnakeElement.segmentSize;
            String direction = SnakeElement.getDirection();

            if (idx == 0) {
                SnakeHead snakeHead = (SnakeHead) SnakeElement;
                switch(direction){
                    case "RIGHT":
                        g.drawImage(snakeHead.snakeHeadRight, x, y, this);
                        break;
                    case "LEFT":
                        g.drawImage(snakeHead.snakeHeadLeft, x, y, this);
                        break;
                    case "UP":
                        g.drawImage(snakeHead.snakeHeadUp, x, y, this);
                        break;
                    case "DOWN":
                        g.drawImage(snakeHead.snakeHeadDown, x, y, this);
                        break;
                }
            } else {
                g.setColor(SnakeElement.getColor());

                if (direction.equals("RIGHT") || direction.equals("LEFT")) {
                    if (y == 0) {
                        if ((x / divideValue) % modulusValue1 == 0 || (x / divideValue) % modulusValue2 == 0) {
                            g.drawRect(x, y + slitherGap, segmentSize, segmentSize);
                        } else {
                            g.drawRect(x, y, segmentSize, segmentSize);
                        }
                    } else if (y == 624) {
                        if ((x / divideValue) % modulusValue1 == 0 || (x / divideValue) % modulusValue2 == 0) {
                            g.drawRect(x, y - slitherGap, segmentSize, segmentSize);
                        } else {
                            g.drawRect(x, y, segmentSize, segmentSize);
                        }
                    } else {
                        if ((x / divideValue) % modulusValue1 == 0) {
                            g.drawRect(x, y + slitherGap, segmentSize, segmentSize);
                        } else if ((x / divideValue) % modulusValue2 == 0) {
                            g.drawRect(x, y - slitherGap, segmentSize, segmentSize);
                        } else {
                            g.drawRect(x, y, segmentSize, segmentSize);
                        }
                    }
                } else if (direction.equals("UP") || direction.equals("DOWN")) {

                    if (x == 0) {
                        if ((y / divideValue) % modulusValue1 == 0 || (y / divideValue) % modulusValue2 == 0) {
                            g.drawRect(x + slitherGap, y, segmentSize, segmentSize);
                        }  else {
                            g.drawRect(x, y, segmentSize, segmentSize);
                        }
                    } else if (x == 464) {
                        if ((y / divideValue) % modulusValue1 == 0 || (y / divideValue) % modulusValue2 == 0) {
                            g.drawRect(x - slitherGap, y, segmentSize, segmentSize);
                        }  else {
                            g.drawRect(x, y, segmentSize, segmentSize);
                        }
                    } else {
                        if ((y / divideValue) % modulusValue1 == 0) {
                            g.drawRect(x + slitherGap, y, segmentSize, segmentSize);
                        } else if ((y / divideValue) % modulusValue2 == 0) {
                            g.drawRect(x - slitherGap, y, segmentSize, segmentSize);
                        } else {
                            g.drawRect(x, y, segmentSize, segmentSize);
                        }
                    }
                }
            }
        }
    }
}
