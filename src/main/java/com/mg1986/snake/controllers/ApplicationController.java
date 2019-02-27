package com.mg1986.snake.controllers;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import com.mg1986.snake.ui.*;
import java.security.SecureRandom;
import com.mg1986.snake.model.Apple;
import com.mg1986.snake.model.Snake;
import static java.awt.event.KeyEvent.*;
import com.mg1986.snake.model.SnakeHead;
import com.mg1986.snake.model.SnakeElement;

public class ApplicationController implements ActionListener, KeyListener {

    public Apple apple;
    public Snake snake;

    private boolean gameOver;
    private boolean gamePaused;

    private BasePanel currentMenu;
    private String currentMenuType;
    private static final String TITLE_MENU = "TITLE_MENU";
    private static final String CONTROLS_MENU = "CONTROLS_MENU";
    private static final String RESTART_MENU = "RESTART_MENU";
    private static final int applicationWidth = 540;
    private static final int applicationHeight = 810;
    private static final int boardWidth = applicationWidth;
    private static final int boardHeight = 720;
    private final int moveInterval = 18;
    private ArrayList<Integer> xPositions;
    private ArrayList<Integer> yPositions;
    private SecureRandom secureRandom;
    private Gameboard gameboard;
    private Scoreboard scoreboard;
    private ApplicationFrame applicationFrame;
    private ApplicationPanel applicationPanel;
    private final int startingGameSpeed = 150;
    private int gameSpeed;
    private Timer timer;

    //------------------------------------------------------------------------------------------------------------------
    public void startApplication() {

        applicationFrame = new ApplicationFrame(applicationWidth, applicationHeight);
        applicationPanel = new ApplicationPanel(applicationWidth, applicationHeight, this);
        applicationPanel.setBounds(0, 0, applicationWidth, applicationHeight);
        applicationFrame.add(applicationPanel);
        applicationFrame.pack();

        secureRandom = new SecureRandom();
        xPositions = createXYArrayList(moveInterval, boardWidth);
        yPositions = createXYArrayList(moveInterval, boardHeight);
        gameOver = true;
        gamePaused = false;

        createMenu(TITLE_MENU, applicationPanel, this);
    }

    //------------------------------------------------------------------------------------------------------------------
    public void startGame() {

        gameSpeed = startingGameSpeed;
        gameOver = false;

        apple = new Apple(selectRandomIndex(xPositions), selectRandomIndex(yPositions));
        snake = new Snake(boardWidth / 2, boardHeight / 2);

        applicationPanel.removeAll();
        scoreboard = new Scoreboard(boardWidth, 85);
        applicationPanel.add(scoreboard);
        gameboard = new Gameboard(boardWidth, boardHeight, apple, snake, this);
        applicationPanel.add(gameboard);
        gameboard.requestFocus();

        timer = new Timer(gameSpeed, this);
        timer.start();
        updateView();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void keyPressed(KeyEvent keyEvent) {

        int keyEventCode = keyEvent.getKeyCode();
        if (keyEventCode == KeyEvent.VK_Q) { System.exit(0); }

        if (!gameOver) {
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
                        gamePaused = pauseGame(gameboard);
                    } else {
                        gamePaused = unpauseGame(gameboard);
                    }
                    break;
            }
        } else {
            if (keyEventCode == VK_ENTER) {
                switch (currentMenuType) {
                    case TITLE_MENU:
                        createMenu(CONTROLS_MENU, applicationPanel, this);
                        break;
                    case CONTROLS_MENU:
                        startGame();
                        currentMenuType = RESTART_MENU;
                        break;
                    case RESTART_MENU:
                        startGame();
                        break;
                }
            }
            updateView();
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
            switch (direction) {
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

            detectCollision(x, y);
            if (gameOver) {
                timer.stop();
                createMenu(RESTART_MENU, gameboard, this);
            }

            snake.incrementSnake(x, y);
            updateView(gameboard);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private void detectCollision(int x, int y) {

        boolean outOfBounds = (x < 0 || x > boardWidth - moveInterval || y < 0 || y > boardHeight - moveInterval);

        if (outOfBounds || snake.selfIntersection(x, y)) {
            gameOver = true;
        } else if (apple.appleCollision(x, y)) {
            int appleCount = apple.getAppleCount();
            relocateApple ();
            scoreboard.score.setText(" x " + apple.getFormattedAppleCount());

            int gameSpeed = calculateGameSpeed(appleCount);
            int snakeSegments = calculateSnakeSegments(appleCount);
            snake.addSegment(snakeSegments);

            // Reset timer with new game speed
            timer.stop();
            timer = new Timer(gameSpeed, this);
            timer.start();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private int calculateGameSpeed( int appleCount) {
        int newGameSpeed = startingGameSpeed;

        if (gameSpeed > 50) {
            if (appleCount <= 10) {
                newGameSpeed = gameSpeed - 3;
            } else if (appleCount <= 40) {
                newGameSpeed = gameSpeed - 2;
            } else if (appleCount <= 50) {
                newGameSpeed = gameSpeed - 1;
            }
        } else if (gameSpeed < 50 && (appleCount <= 100 && appleCount % 10 == 0)) {
            newGameSpeed = gameSpeed - 1;
        }

        return newGameSpeed;
    }

    //------------------------------------------------------------------------------------------------------------------
    private int calculateSnakeSegments (int appleCount) {
        int snakeSegments;

        if (appleCount <= 50) {
            snakeSegments = 2;
        } else {
            snakeSegments = 1;
        }

        return snakeSegments;
    }

    //------------------------------------------------------------------------------------------------------------------
    private void relocateApple () {

        apple.setCurrentX(selectRandomIndex(xPositions));
        apple.setCurrentY(selectRandomIndex(yPositions));

        for (SnakeElement snakeSegment : snake.getSnakeBody()) {

            int segmentX = snakeSegment.getCurrentX();
            int segmentY = snakeSegment.getCurrentY();

            if (apple.appleCollision(segmentX, segmentY)) {
                relocateApple();
            }
        }

        apple.incrementAppleCount();
        updateView(gameboard);
    }

    //------------------------------------------------------------------------------------------------------------------
    private ArrayList<Integer> createXYArrayList(int multiple, int maxNumber) {
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
    private boolean pauseGame(Gameboard gameboard) {
        gameboard.addJLabel("Paused", 35);
        updateView(gameboard);
        timer.stop();
        return true;
    }

    //------------------------------------------------------------------------------------------------------------------
    private boolean unpauseGame(Gameboard gameboard) {
        gameboard.removeAll();
        updateView(gameboard);
        timer.start();
        return false;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void createMenu(String menuType, BasePanel basePanel, ApplicationController applicationController) {

        basePanel.removeAll();

        BasePanel newMenu = new BasePanel();

        switch(menuType) {
            case TITLE_MENU:
                newMenu = new TitlePanel();
                break;
            case CONTROLS_MENU:
                newMenu = new ControlsPanel();
                break;
            case RESTART_MENU:
                newMenu = new RestartPanel(boardWidth, boardHeight);
                break;
        }

        newMenu.setFocusable(true);
        newMenu.addKeyListener(applicationController);
        basePanel.add(newMenu);
        newMenu.requestFocus();
        currentMenuType = menuType;
        currentMenu = newMenu;
        updateView();
    }

    //------------------------------------------------------------------------------------------------------------------
    public void updateView() {
        currentMenu.revalidate();
        currentMenu.repaint();
        currentMenu.sync();
    }

    //------------------------------------------------------------------------------------------------------------------
    public void updateView(BasePanel basePanel) {
        basePanel.revalidate();
        basePanel.repaint();
        basePanel.sync();
    }
}