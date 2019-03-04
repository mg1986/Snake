package com.mg1986.snake.model;

import java.awt.Color;

/**
 * Author: Matthew Gray
 * Last Modified: 3/1/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.snake.model.BaseElement class
 */

public class BaseElement {

    public final int segmentSize = 17;
    public int currentX;
    public int currentY;
    public int previousX;
    public int previousY;
    public Color segmentColor;

    //------------------------------------------------------------------------------------------------------------------
    public int getCurrentX() { return this.currentX; }

    //------------------------------------------------------------------------------------------------------------------
    public void setCurrentX(int x) { this.currentX = x; }

    //------------------------------------------------------------------------------------------------------------------
    public int getCurrentY() { return this.currentY; }

    //------------------------------------------------------------------------------------------------------------------
    public void setCurrentY(int y) { this.currentY = y; }

    //------------------------------------------------------------------------------------------------------------------
    public int getPreviousX() { return this.previousX; }

    //------------------------------------------------------------------------------------------------------------------
    public void setPreviousX(int x) { this.previousX = x; }

    //------------------------------------------------------------------------------------------------------------------
    public int getPreviousY() { return this.previousY; }

    //------------------------------------------------------------------------------------------------------------------
    public void setPreviousY(int y) { this.previousY = y; }

    //------------------------------------------------------------------------------------------------------------------
    public Color getColor() { return this.segmentColor; }

    //------------------------------------------------------------------------------------------------------------------
    public void setColor(Color color) { this.segmentColor = color; }
}
