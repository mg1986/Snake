package com.mg1986.snake.controllers;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.security.SecureRandom;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mg1986.snake.models.Apple;
import com.mg1986.snake.models.Snake;
import com.mg1986.snake.models.SnakeHead;
import com.mg1986.snake.views.BaseMenu;
import com.mg1986.snake.views.Gameboard;
import com.mg1986.snake.views.Scoreboard;
import com.mg1986.snake.views.ApplicationPanel;

import static java.awt.event.KeyEvent.*;

public class ApplicationController implements ActionListener, KeyListener {

    public Apple apple;
    public Snake snake;
    public MenuController menuController;

    private boolean gameOver;
    private boolean gamePaused;

    private static final int applicationWidth = 480;
    private static final int applicationHeight = 715;
    private static final int boardWidth = 480;
    private static final int boardHeight = 640;
    private final int moveInterval = 16;
    private ArrayList<Integer> xPositions;
    private ArrayList<Integer> yPositions;
    private int gameSpeed = 150;
    private SecureRandom secureRandom;
    private Gameboard gameboard;
    private Scoreboard scoreboard;
    private ApplicationPanel applicationPanel;
    private Timer timer;
    public String gameStatus;

    //------------------------------------------------------------------------------------------------------------------
    public ApplicationController() {

        applicationPanel = new ApplicationPanel(applicationWidth, applicationHeight, this);
        secureRandom = new SecureRandom();
        xPositions = createXYArrayList(moveInterval, boardWidth);
        yPositions = createXYArrayList(moveInterval, boardHeight);
        apple = new Apple(selectRandomIndex(xPositions), selectRandomIndex(yPositions));
        snake = new Snake(boardWidth/2, boardHeight/2);
        gameOver = false;
        gamePaused = false;
        gameStatus = "N";
        menuController = new MenuController(applicationPanel, this);
    }

    //------------------------------------------------------------------------------------------------------------------
    public void startApplication() {

        JFrame jFrame = new JFrame("Snake");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(applicationWidth, applicationHeight);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        applicationPanel.setBounds(0, 0, applicationWidth, applicationHeight);
        jFrame.add(applicationPanel);
        jFrame.pack();

        menuController.createMenu(MenuController.TITLE_MENU, applicationPanel, this);
    }

    //------------------------------------------------------------------------------------------------------------------
    public void startGame() {

        gameStatus = "Y";
        applicationPanel.removeAll();

        scoreboard = new Scoreboard();
        applicationPanel.add(scoreboard);
        gameboard = new Gameboard(apple, snake, this);
        applicationPanel.add(gameboard);

        timer = new Timer(gameSpeed, this);
        timer.start();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void keyPressed(KeyEvent keyEvent) {

        int keyEventCode = keyEvent.getKeyCode();

        if (gameStatus.equals("Y")) {
            SnakeHead head = snake.getSnakeHead();
            String direction = head.getDirection();

            switch (keyEventCode) {
                case VK_RIGHT:
                    if (!direction.equals("LEFT") && !gamePaused) head.setDirection("RIGHT");
                    break;
                case VK_LEFT:
                    if (!direction.equals("RIGHT") && !gamePaused) head.setDirection("LEFT");
                    break;
               case VK_UP:
                    if (!direction.equals("DOWN") && !gamePaused) head.setDirection("UP");
                    break;
                case VK_DOWN:
                    if (!direction.equals("UP") && !gamePaused) head.setDirection("DOWN");
                    break;
                case VK_P:
                    if (!gamePaused) {
                        pauseGame(gameboard);
                    } else {
                        unpauseGame(gameboard);
                    }
                    break;
                case VK_Q:
                    System.exit(0);
                    break;
                case VK_ENTER:
                    if (gameOver) startGame();
                    break;
            }
            menuController.updateView(gameboard);
        } else {
            if (keyEventCode == VK_ENTER) {
                switch (menuController.getCurrentMenuType()) {
                    case MenuController.TITLE_MENU:
                        menuController.createMenu(MenuController.CONTROLS_MENU, applicationPanel, this);
                        break;
                    case MenuController.CONTROLS_MENU:
                        startGame();
                        menuController.setCurrentMenuType(MenuController.RESTART_MENU);
                        break;
                    case MenuController.RESTART_MENU:
                        startGame();
                        break;
                }
            } else if (keyEventCode == KeyEvent.VK_Q) {
                System.exit(0);
            }

            menuController.updateView();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {

        if (!gameOver) {

            SnakeHead head = snake.getSnakeHead();
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
            gameOver = detectCollision(x, y);
            snake.incrementSnake(x, y);
            menuController.updateView(gameboard);
        }

        menuController.updateView();
    }



    //------------------------------------------------------------------------------------------------------------------
    private boolean detectCollision(int x, int y) {

        boolean gameOver = false;

        boolean outOfBounds = (x < 0 || x > boardWidth - moveInterval || y < 0 || y > boardHeight - moveInterval);

        if (outOfBounds || snake.selfIntersection(x, y)) {

            menuController.createMenu(MenuController.RESTART_MENU, applicationPanel, this);
            applicationPanel.requestFocus();
            gameOver = true;

        } else if (apple.appleCollision(x, y)) {

            apple.incrementAppleCount();
            int appleCount = apple.getAppleCount();

            if (appleCount <= 10) {
                snake.addSegment(2);
                if (gameSpeed > 50) gameSpeed = gameSpeed - 3;
            } else if (appleCount <= 40) {
                snake.addSegment(2);
                if (gameSpeed > 50) gameSpeed = gameSpeed - 2;
            } else if (appleCount <= 50) {
                snake.addSegment(2);
                if (gameSpeed > 50) gameSpeed = gameSpeed - 1;
            } else {
                snake.addSegment(1);
                // Make game slightly faster every 10th apple over 50
                if (gameSpeed > 45 && appleCount <= 100 && appleCount % 10 == 0) {
                    gameSpeed = gameSpeed - 1;
                }
            }

            apple.relocateApple(selectRandomIndex(xPositions), selectRandomIndex(yPositions));
            scoreboard.score.setText(" x " + apple.getFormattedAppleCount());

            timer.stop();
            timer = new Timer(gameSpeed, this);
            timer.start();
        }

        return gameOver;
    }

    //------------------------------------------------------------------------------------------------------------------
    public ArrayList<Integer> createXYArrayList (int multiple, int maxNumber) {
        maxNumber = maxNumber - multiple;
        ArrayList<Integer> possiblePositions = new ArrayList<>();
        for (int x = 0; x <= maxNumber; x = x + multiple) {
            possiblePositions.add(x);
        }

        return possiblePositions;
    }

    //------------------------------------------------------------------------------------------------------------------
    public int selectRandomIndex(ArrayList<Integer> possiblePositions) {
        int randomIndex = secureRandom.nextInt(possiblePositions.size());
        return possiblePositions.get(randomIndex);
    }

    //------------------------------------------------------------------------------------------------------------------
    private void pauseGame(Gameboard gameboard) {
        gameboard.addJLabel ("Paused", 35);
        timer.stop();
        gamePaused = true;
    }

    //------------------------------------------------------------------------------------------------------------------
    private void unpauseGame(Gameboard gameboard) {
        gameboard.removeAll();
        timer.start();
        gamePaused = false;
    }
}
