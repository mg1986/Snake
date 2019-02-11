package com.mg1986.snake;

import java.awt.Color;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.Square class -
 */

public class Square {

    public static final int segmentSize = 15;
    public int currentX;
    public int currentY;
    public Color segmentColor;

    //------------------------------------------------------------------------------------------------------------------
    public Square () {}

    //------------------------------------------------------------------------------------------------------------------
    public Square (int segmentX, int segmentY, Color color) {
       this.currentX = segmentX;
       this.currentY = segmentY;
       this.segmentColor = color;
    }

    //------------------------------------------------------------------------------------------------------------------
    public int getCurrentX() { return this.currentX; }

    //------------------------------------------------------------------------------------------------------------------
    public void setCurrentX(int x) { this.currentX = x; }

    //------------------------------------------------------------------------------------------------------------------
    public int getCurrentY() { return this.currentY; }

    //------------------------------------------------------------------------------------------------------------------
    public void setCurrentY(int y) { this.currentY = y; }

    //------------------------------------------------------------------------------------------------------------------
    public Color getColor() { return this.segmentColor; }

    //------------------------------------------------------------------------------------------------------------------
    public void setColor(Color color) { this.segmentColor = color; }
}
