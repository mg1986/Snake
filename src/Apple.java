import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * Apple class -
 */

public class Apple extends Square {

    public final Color appleColor = Color.RED;
    public int appleCount = 0;
    private final int APPLE_COUNTER_INCREMENT_VALUE = 1;
    public BufferedImage appleImage;


    public Apple (int segmentX, int segmentY) {
        this.currentX = segmentX;
        this.currentY = segmentY;
        this.appleCount = 0;
        try {
            appleImage = ImageIO.read(new File("img/apple_small.png"));
        } catch (IOException ex) {
            System.out.println("No Apple Icon");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public String getFormattedAppleCount() { return String.format ("%03d", appleCount); }

    //------------------------------------------------------------------------------------------------------------------
    public int getAppleCount() { return this.appleCount;}

    //------------------------------------------------------------------------------------------------------------------
    public void incrementAppleCount() {
        this.appleCount = this.appleCount + APPLE_COUNTER_INCREMENT_VALUE;
    }
}
