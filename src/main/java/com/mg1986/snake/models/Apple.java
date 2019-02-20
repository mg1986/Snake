package com.mg1986.snake.models;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.Apple class -
 */

public class Apple extends BaseElement {

    private int appleCount;
    private BufferedImage appleImage;
    private final int APPLE_COUNTER_INCREMENT_VALUE = 1;

    //------------------------------------------------------------------------------------------------------------------
    public Apple (int appleX, int appleY) {
        this.currentX = appleX;
        this.currentY = appleY;
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
    public BufferedImage getAppleImage() {
        return this.appleImage;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void incrementAppleCount() { appleCount += 1;};

    //------------------------------------------------------------------------------------------------------------------
    public void relocateApple(int newX, int newY) {

        setCurrentX(newX);
        setCurrentY(newY);
        incrementAppleCount();
    }

    //------------------------------------------------------------------------------------------------------------------
    public String getFormattedAppleCount() { return String.format ("%03d", getAppleCount()); }

    //------------------------------------------------------------------------------------------------------------------
    public boolean appleCollision(int x, int y) {
        return x == getCurrentX() && y == getCurrentY();
    }
}
