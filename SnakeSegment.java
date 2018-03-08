import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * SnakeSegment class -
 */

public class SnakeSegment extends Square {

    private int previousX;
    private int previousY;
    private String segmentDirection;
    public BufferedImage snakeHeadRight;
    public BufferedImage snakeHeadLeft;
    public BufferedImage snakeHeadUp;
    public BufferedImage snakeHeadDown;

    //------------------------------------------------------------------------------------------------------------------
    public SnakeSegment () {}

    //------------------------------------------------------------------------------------------------------------------
    public SnakeSegment (int segmentX, int segmentY, Color color) {
       this.currentX = segmentX;
       this.currentY = segmentY;
       this.segmentColor = color;
       this.segmentDirection = "UP";
        try {
            snakeHeadRight = ImageIO.read(new File("img/snake_head_right.png"));
            snakeHeadLeft = ImageIO.read(new File("img/snake_head_left.png"));
            snakeHeadUp = ImageIO.read(new File("img/snake_head_up.png"));
            snakeHeadDown = ImageIO.read(new File("img/snake_head_down.png"));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public int getPreviousX() { return this.previousX; }

    //------------------------------------------------------------------------------------------------------------------
    public void setPreviousX(int x) { this.previousX = x; }

    //------------------------------------------------------------------------------------------------------------------
    public int getPreviousY() { return this.previousY; }

    //------------------------------------------------------------------------------------------------------------------
    public void setPreviousY(int y) { this.previousY = y; }

    //------------------------------------------------------------------------------------------------------------------
    public String getDirection() { return this.segmentDirection; }

    //------------------------------------------------------------------------------------------------------------------
    public void setDirection(String direction) { this.segmentDirection = direction; }

}