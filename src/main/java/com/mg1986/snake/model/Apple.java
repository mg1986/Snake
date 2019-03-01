package com.mg1986.snake.model;

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

    private BufferedImage appleImage;

    //------------------------------------------------------------------------------------------------------------------
    public Apple (int appleX, int appleY) {
        this.currentX = appleX;
        this.currentY = appleY;

        try {
            appleImage = ImageIO.read(Apple.class.getResourceAsStream("/img/apple_small.png"));
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public BufferedImage getAppleImage() {
        return this.appleImage;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void setAppleImage(String bufferedImagePath) {
        try {
            appleImage = ImageIO.read(Apple.class.getResourceAsStream(bufferedImagePath));
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public boolean appleCollision(int x, int y) {
        return x == getCurrentX() && y == getCurrentY();
    }
}
