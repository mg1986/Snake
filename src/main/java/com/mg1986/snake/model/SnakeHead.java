package com.mg1986.snake.model;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Author: Matthew Gray
 * Last Modified: 3/1/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.snake.SnakeElement class
 */

public class SnakeHead extends SnakeElement {

    // SnakeHead BufferedImages for each direction
    public BufferedImage snakeHeadRight;
    public BufferedImage snakeHeadLeft;
    public BufferedImage snakeHeadUp;
    public BufferedImage snakeHeadDown;

    //------------------------------------------------------------------------------------------------------------------
    // SnakeHead constructor - Takes X and Y coordinate for starting position. Starting direction is always "UP"
    public SnakeHead(int segmentX, int segmentY) {
       currentX = segmentX;
       currentY = segmentY;
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
