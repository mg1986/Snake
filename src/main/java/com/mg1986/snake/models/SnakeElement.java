package com.mg1986.snake.models;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.SnakeElement class -
 */

public class SnakeElement extends BaseElement {

    public String segmentDirection;

    //------------------------------------------------------------------------------------------------------------------
    public SnakeElement () { }

    //------------------------------------------------------------------------------------------------------------------
    public SnakeElement (int segmentX, int segmentY, Color color) {
       currentX = segmentX;
       currentY = segmentY;
       segmentColor = color;
       segmentDirection = "UP";
    }

    //------------------------------------------------------------------------------------------------------------------
    public String getDirection() { return segmentDirection; }

    //------------------------------------------------------------------------------------------------------------------
    public void setDirection(String direction) { segmentDirection = direction; }

}
