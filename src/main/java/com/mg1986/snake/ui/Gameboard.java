package com.mg1986.snake.ui;

import java.awt.*;
import javax.swing.Timer;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.security.SecureRandom;
import java.awt.event.ActionListener;
import com.mg1986.snake.components.Snake;
import com.mg1986.snake.components.Apple;
import com.mg1986.snake.components.Scoreboard;
import com.mg1986.snake.components.SnakeSegment;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.GameMenu class -
 */

public class Gameboard extends BasePanel implements ActionListener, KeyListener {

    private boolean gameOver = false;
    private boolean gamePaused = false;
    private static final int boardWidth = 480;
    private static final int boardHeight = 640;
    private final int moveInterval = 16;
    private Snake snake;
    private ArrayList<SnakeSegment> snakeBody;
    private Apple apple;
    private int gameSpeed;
    private ArrayList<Integer> xPositions;
    private ArrayList<Integer> yPositions;
    private SecureRandom secureRandom;
    private Scoreboard scoreboard;
    private GameMenu gameMenu;
    private Timer timer;

    //------------------------------------------------------------------------------------------------------------------
    public Gameboard () {}

    //------------------------------------------------------------------------------------------------------------------
    public Gameboard (GameMenu inputGameMenu, Scoreboard inputScoreboard) {

        secureRandom = new SecureRandom();
        xPositions = createXYArrayList(moveInterval, boardWidth);
        yPositions = createXYArrayList(moveInterval, boardHeight);

        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        setSize(boardWidth, boardHeight);
        setVisible(true);
        scoreboard = inputScoreboard;

        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setMaximumSize(getPreferredSize());

        snake = new Snake(boardWidth/2, boardHeight/2);
        snakeBody = snake.snakeBody;

        apple = new Apple(selectRandomIndex(xPositions), selectRandomIndex(yPositions));

        gameSpeed = 150;

        gameMenu = inputGameMenu;

        timer = new Timer(gameSpeed, this);
        timer.start();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void keyReleased(KeyEvent  keyEvent) {}

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void keyPressed(KeyEvent  keyEvent) {

        int keyEventCode = keyEvent.getKeyCode();
        SnakeSegment head = snake.snakeBody.get(0);
        String direction = head.getDirection();

        switch(keyEventCode){
            case KeyEvent.VK_RIGHT:
                if (!direction.equals("LEFT") && !gamePaused) head.setDirection("RIGHT");
                break;
            case KeyEvent.VK_LEFT:
                if (!direction.equals("RIGHT") && !gamePaused) head.setDirection("LEFT");
                break;
            case KeyEvent.VK_UP:
                if (!direction.equals("DOWN") && !gamePaused) head.setDirection("UP");
                break;
            case KeyEvent.VK_DOWN:
                if (!direction.equals("UP") && !gamePaused) head.setDirection("DOWN");
                break;
            case KeyEvent.VK_P:
                if (!gamePaused) {
                    pauseGame();
                } else {
                    unpauseGame();
                }
                break;
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            case KeyEvent.VK_ENTER:
                if (gameOver) gameMenu.startGame();
                break;
        }

        this.revalidate();
        this.repaint();
        this.sync();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {

        if (!gameOver) {

            SnakeSegment head = snake.snakeBody.get(0);
            String direction = head.getDirection();
            int x = head.getCurrentX();
            int y = head.getCurrentY();
            switch(direction){
                case "RIGHT":
                    x = x + moveInterval;
                    break;
                case "LEFT":
                    x = x - moveInterval;
                    break;
                case "UP":
                    y = y - moveInterval;
                    break;
                case "DOWN":
                    y = y + moveInterval;
                    break;
            }
            gameOver = detectCollision(x, y, snake);
            snake.incrementSnake(x, y);
        }

        this.revalidate();
        this.repaint();
        this.sync();
    }

    //------------------------------------------------------------------------------------------------------------------
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw com.mg1986.Snake.Apple object
        g.drawImage(apple.appleImage, apple.getCurrentX(), apple.getCurrentY(), this);

        // Draw com.mg1986.Snake.Snake object
        for (int idx = 0; idx < snakeBody.size(); idx++) {

            SnakeSegment snakeSegment = snakeBody.get(idx);

            int x = snakeSegment.getCurrentX();
            int y = snakeSegment.getCurrentY();
            int segmentSize = SnakeSegment.segmentSize;
            String direction = snakeSegment.getDirection();

            if (idx == 0) {
                switch(direction){
                    case "RIGHT":
                        g.drawImage(snakeSegment.snakeHeadRight, x, y, this);
                        break;
                    case "LEFT":
                        g.drawImage(snakeSegment.snakeHeadLeft, x, y, this);
                        break;
                    case "UP":
                        g.drawImage(snakeSegment.snakeHeadUp, x, y, this);
                        break;
                    case "DOWN":
                        g.drawImage(snakeSegment.snakeHeadDown, x, y, this);
                        break;
                }
            } else {

                int slitherGap = 1;
                int modulusValue1 = 4;
                int modulusValue2 = 2;
                int divideValue = 10;
                g.setColor(snakeSegment.getColor());

                if (direction.equals("RIGHT") || direction.equals("LEFT")) {
                    if (y == 0) {
                        if ((x / divideValue) % modulusValue1 == 0 || (x / divideValue) % modulusValue2 == 0) {
                            g.drawRect(x, y + slitherGap, segmentSize, segmentSize);
                        } else {
                            g.drawRect(x, y, segmentSize, segmentSize);
                        }
                    } else if (y == 624) {
                        if ((x / divideValue) % modulusValue1 == 0 || (x / divideValue) % modulusValue2 == 0) {
                            g.drawRect(x, y - slitherGap, segmentSize, segmentSize);
                        } else {
                            g.drawRect(x, y, segmentSize, segmentSize);
                        }
                    } else {
                        if ((x / divideValue) % modulusValue1 == 0) {
                            g.drawRect(x, y + slitherGap, segmentSize, segmentSize);
                        } else if ((x / divideValue) % modulusValue2 == 0) {
                            g.drawRect(x, y - slitherGap, segmentSize, segmentSize);
                        } else {
                            g.drawRect(x, y, segmentSize, segmentSize);
                        }
                    }
                } else if (direction.equals("UP") || direction.equals("DOWN")) {

                    if (x == 0) {
                        if ((y / divideValue) % modulusValue1 == 0 || (y / divideValue) % modulusValue2 == 0) {
                            g.drawRect(x + slitherGap, y, segmentSize, segmentSize);
                        }  else {
                            g.drawRect(x, y, segmentSize, segmentSize);
                        }
                    } else if (x == 464) {
                        if ((y / divideValue) % modulusValue1 == 0 || (y / divideValue) % modulusValue2 == 0) {
                            g.drawRect(x - slitherGap, y, segmentSize, segmentSize);
                        }  else {
                            g.drawRect(x, y, segmentSize, segmentSize);
                        }
                    } else {
                        if ((y / divideValue) % modulusValue1 == 0) {
                            g.drawRect(x + slitherGap, y, segmentSize, segmentSize);
                        } else if ((y / divideValue) % modulusValue2 == 0) {
                            g.drawRect(x - slitherGap, y, segmentSize, segmentSize);
                        } else {
                            g.drawRect(x, y, segmentSize, segmentSize);
                        }
                    }
                }
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private boolean detectCollision(int x, int y, Snake snake) {

        boolean appleCollision = (x == apple.getCurrentX() && y == apple.getCurrentY());
        boolean outOfBounds = (x < 0 || x > boardWidth - moveInterval || y < 0 || y > boardHeight - moveInterval);
        boolean selfIntersection = false;

        for (int idx = 0; idx < snakeBody.size(); idx++) {
            if (idx > 0) {
                SnakeSegment snakeSegment = snakeBody.get(idx);
                if (x == snakeSegment.getCurrentX() && y == snakeSegment.getCurrentY()) {
                    selfIntersection = true;
                }
            }
        }

        boolean gameOver = false;

        if (outOfBounds || selfIntersection) {

            gameMenu.createRestartGameMenu(this);
            gameMenu.requestFocus();
            gameOver = true;

        } else if (appleCollision) {

            if (apple.getAppleCount() <= 10) {
                snake.addSegment(2);
                if (gameSpeed > 50) gameSpeed = gameSpeed - 3;
            } else if (apple.getAppleCount() > 10 && apple.getAppleCount() <= 40) {
                snake.addSegment(2);
                if (gameSpeed > 50) gameSpeed = gameSpeed - 2;
            } else if (apple.getAppleCount() > 40 && apple.getAppleCount() <= 50) {
                snake.addSegment(2);
                if (gameSpeed > 50) gameSpeed = gameSpeed - 1;
            } else if (apple.getAppleCount() > 50) {
                snake.addSegment(1);
                if (gameSpeed > 45 && apple.getAppleCount() <= 100 &&apple.getAppleCount() % 10 == 0) {
                    gameSpeed = gameSpeed - 1;
                }
            }

            relocateApple();
            scoreboard.score.setText(" x " + apple.getFormattedAppleCount());

            timer.stop();
            timer = new Timer(gameSpeed, this);
            timer.start();
        }
        return gameOver;
    }

    //------------------------------------------------------------------------------------------------------------------
    private ArrayList<Integer> createXYArrayList (int multiple, int maxNumber) {
        maxNumber = maxNumber - multiple;
        ArrayList<Integer> possiblePositions = new ArrayList<>();
        for (int x = 0; x <= maxNumber; x = x + multiple) {
            possiblePositions.add(x);
        }

        return possiblePositions;
    }

    //------------------------------------------------------------------------------------------------------------------
    private int selectRandomIndex(ArrayList<Integer> possiblePositions) {
        int randomIndex = secureRandom.nextInt(possiblePositions.size());
        return possiblePositions.get(randomIndex);
    }

    //------------------------------------------------------------------------------------------------------------------
    private void relocateApple () {

        boolean collision = false;

        int appleX = selectRandomIndex(xPositions);
        int appleY = selectRandomIndex(yPositions);

        for (SnakeSegment snakeSegment : snakeBody) {

            int segmentX = snakeSegment.getCurrentX();
            int segmentY = snakeSegment.getCurrentY();

            if ((appleX == segmentX) && (appleY == segmentY)) collision = true;
        }

        if (!collision) {
            apple.setCurrentX(appleX);
            apple.setCurrentY(appleY);
        } else {
            relocateApple();
        }

        apple.incrementAppleCount();
    }

    //------------------------------------------------------------------------------------------------------------------
    private void pauseGame() {
        gameMenu.addJLabel (this,"Paused", 35);
        timer.stop();
        gamePaused = true;
    }

    //------------------------------------------------------------------------------------------------------------------
    private void unpauseGame() {
        removeAll();
        timer.start();
        gamePaused = false;
    }
}