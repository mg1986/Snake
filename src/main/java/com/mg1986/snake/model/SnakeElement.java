package com.mg1986.snake.model;

import java.awt.*;

/**
 * Author: Matthew Gray
 * Last Modified: 3/1/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.snake.model.SnakeElement class
 */

public class SnakeElement extends BaseElement {

    // String representing direction of SnakeElement
    public String segmentDirection;

    //------------------------------------------------------------------------------------------------------------------
    // SnakeElement constructor - No args constructor
    public SnakeElement() {}

    //------------------------------------------------------------------------------------------------------------------
    // SnakeElement constructor - Takes starting X and Y coordinates and Color for SnakeElement border
    public SnakeElement (int segmentX, int segmentY, Color color) {
       currentX = segmentX;
       currentY = segmentY;
       segmentColor = color;
       segmentDirection = "UP";
    }

    //------------------------------------------------------------------------------------------------------------------
    // getDirection() - Returns String of current SnakeElement direction
    public String getDirection() { return segmentDirection; }

    //------------------------------------------------------------------------------------------------------------------
    // setDirection() -  Sets String of SnakeElement direction
    public void setDirection(String direction) { segmentDirection = direction; }
}
