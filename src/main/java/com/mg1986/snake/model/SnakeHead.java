package com.mg1986.snake.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.SnakeElement class -
 */

public class SnakeHead extends SnakeElement {

    public BufferedImage snakeHeadRight;
    public BufferedImage snakeHeadLeft;
    public BufferedImage snakeHeadUp;
    public BufferedImage snakeHeadDown;

    //------------------------------------------------------------------------------------------------------------------
    public SnakeHead(int segmentX, int segmentY, Color color) {
       currentX = segmentX;
       currentY = segmentY;
       segmentColor = color;
       segmentDirection = "UP";
        try {
            snakeHeadRight = ImageIO.read(SnakeHead.class.getResourceAsStream("/img/snake_head_right.png"));
            snakeHeadLeft = ImageIO.read(SnakeHead.class.getResourceAsStream("/img/snake_head_left.png"));
            snakeHeadUp = ImageIO.read(SnakeHead.class.getResourceAsStream("/img/snake_head_up.png"));
            snakeHeadDown = ImageIO.read(SnakeHead.class.getResourceAsStream("/img/snake_head_down.png"));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
