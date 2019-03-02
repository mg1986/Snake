package com.mg1986.snake.model;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Author: Matthew Gray
 * Last Modified: 3/1/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.model.Apple class
 */

public class Apple extends BaseElement {

    // Apple BufferedImage icon
    private BufferedImage appleImage;

    //------------------------------------------------------------------------------------------------------------------
    // Apple constructor() - Takes starting X and Y coordinates as arguments
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
    // getAppleImage() - Returns Apple BufferedImage icon
    public BufferedImage getAppleImage() {
        return this.appleImage;
    }

    //------------------------------------------------------------------------------------------------------------------
    // setAppleImage() - Takes String url to BufferedImage icone for apple
    public void setAppleImage(String bufferedImagePath) {
        try {
            appleImage = ImageIO.read(Apple.class.getResourceAsStream(bufferedImagePath));
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // appleCollision() - Takes test X and Y coordinated and returns true if apple is currently located at those
    public boolean appleCollision(int x, int y) {
        return x == getCurrentX() && y == getCurrentY();
    }
}
