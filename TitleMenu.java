import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * GameMenu class -
 */

public class TitleMenu extends JPanel {

    private static final int menuWidth = 300;
    private static final int menuHeight = 475;
    private Apple apple;
    private Snake snake;
    private ArrayList<SnakeSegment> snakeBody;
    private int y;


    //------------------------------------------------------------------------------------------------------------------
    public TitleMenu () {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(menuWidth, menuHeight));
        setMaximumSize(getPreferredSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(true);

        //Timer timer = new Timer(150, this);
        //timer.start();
        y = 270;
        apple = new Apple(230, y);
        snake = new Snake(180, y);
        snakeBody = snake.snakeBody;
        revalidate();
        repaint();
    }

    //------------------------------------------------------------------------------------------------------------------
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Apple object
        g.drawImage(apple.appleImage, apple.getCurrentX(), apple.getCurrentY(), this);

        // Draw Snake object
        SnakeSegment snakeSegment = snakeBody.get(0);
        int segmentSize = SnakeSegment.segmentSize;

        g.drawImage(snakeSegment.snakeHeadRight, 180, y, this);
        g.setColor(snakeSegment.getColor());

        for(int x = 170; x >= 40; x = x - 10) {
            g.drawRect(x, y, segmentSize, segmentSize);
        }

        revalidate();
        repaint();
    }
}
