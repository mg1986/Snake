package com.mg1986.snake.components;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.Apple class -
 */

public class Apple extends Square {

    private int appleCount;
    public BufferedImage appleImage;

    //------------------------------------------------------------------------------------------------------------------
    public Apple () {}

    //------------------------------------------------------------------------------------------------------------------
    public Apple (int segmentX, int segmentY) {
        this.currentX = segmentX;
        this.currentY = segmentY;
        this.appleCount = 0;
        try {
            appleImage = ImageIO.read(Apple.class.getResourceAsStream("/img/apple_small.png"));
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public int getAppleCount() { return this.appleCount;}

    //------------------------------------------------------------------------------------------------------------------
    public String getFormattedAppleCount() { return String.format ("%03d", appleCount); }

    //------------------------------------------------------------------------------------------------------------------
    public void incrementAppleCount() {
        this.appleCount = this.appleCount + 1;
    }
}
