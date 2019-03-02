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

/**
 * Author: Matthew Gray
 * Last Modified: 3/1/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.model.ApplicationController class
 */

public class ApplicationController implements ActionListener, KeyListener {

    public Apple apple;
    public Snake snake;
    private boolean gameOver;
    private boolean gamePaused;
    private Gameboard gameboard;
    private Scoreboard scoreboard;
    private BasePanel currentMenu;
    private String currentMenuType;
    private static final int APPLE_INCREMENT_VALUE = 1;
    private static final int SNAKE_INCREMENT_VALUE = 2;
    private static final String TITLE_MENU = "TITLE_MENU";
    private static final String CONTROLS_MENU = "CONTROLS_MENU";
    private static final String RESTART_MENU = "RESTART_MENU";
    private static final int applicationWidth = 540;
    private static final int applicationHeight = 805;
    private static final int boardWidth = applicationWidth;
    private static final int boardHeight = 720;
    private final int moveInterval = 18;
    private ArrayList<Integer> xPositions;
    private ArrayList<Integer> yPositions;
    private SecureRandom secureRandom;
    private ApplicationPanel applicationPanel;
    private final int startingGameSpeed = 150;
    private int gameSpeed;
    private Timer timer;

    //------------------------------------------------------------------------------------------------------------------
    // startApplication() - Sets up UI components and populates X and Y position ArrayLists.
    public void startApplication() {

        ApplicationFrame applicationFrame = new ApplicationFrame(applicationWidth, applicationHeight);
        applicationPanel = new ApplicationPanel(applicationWidth, applicationHeight, this);
        applicationPanel.setBounds(0, 0, applicationWidth, applicationHeight);
        applicationFrame.add(applicationPanel);
        applicationFrame.pack();

        gameOver = true;
        gamePaused = false;
        secureRandom = new SecureRandom();
        xPositions = createXYArrayList(moveInterval, boardWidth);
        yPositions = createXYArrayList(moveInterval, boardHeight);

        createMenu(TITLE_MENU, applicationPanel, this);
    }

    //------------------------------------------------------------------------------------------------------------------
    // startGame() - Sets up UI components for game, creates new Apple and Snake and starts game timer.
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
    // keyPressed() - Logic for KeyListener interface implementation
    @Override
    public void keyPressed(KeyEvent keyEvent) {

        int keyEventCode = keyEvent.getKeyCode();
        if (keyEventCode == KeyEvent.VK_Q) { System.exit(0); }

        if (!gameOver) { // In game KeyListener logic
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
        } else { // Menu KeyListener
            if (keyEventCode == VK_ENTER) {
                switch (currentMenuType) {
                    case TITLE_MENU:
                        applicationPanel.removeAll();
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
    //  actionPerformed() - ActionListener interface implementation logic
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
                applicationPanel.remove(gameboard);
                createMenu(RESTART_MENU, applicationPanel, this);
            }

            snake.incrementSnake(x, y);
            updateView(gameboard);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // detectCollision() - Takes test X and Y coordinate checks: snake in bounds, snake hasn't self intersected. and
    //                     if snake head has intersected the Apple. If the apple is intersected, score is increased.
    //                     If other scenarios happen, gameOver is set to True.
    private void detectCollision(int x, int y) {

        boolean outOfBounds = (x < 0 || x > boardWidth - moveInterval || y < 0 || y > boardHeight - moveInterval);

        if (outOfBounds || snake.selfIntersection(x, y)) {
            gameOver = true;
        } else if (apple.appleCollision(x, y)) {

            // Get apple count, relocate apple, increment score, increment snake
            int appleCount = scoreboard.getScore();
            relocateApple ();
            scoreboard.setScore(appleCount + APPLE_INCREMENT_VALUE);
            snake.addSegment(SNAKE_INCREMENT_VALUE);

            // Reset timer with new game speed
            int gameSpeed = calculateGameSpeed(appleCount);
            timer.stop();
            timer = new Timer(gameSpeed, this);
            timer.start();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // calculateGameSpeed() - Sets game speed based off current score
    private int calculateGameSpeed(int appleCount) {
        int newGameSpeed = startingGameSpeed;

        if (gameSpeed > 50) {
            if (appleCount <= 10) {
                newGameSpeed = gameSpeed - 3;
            } else if (appleCount <= 40) {
                newGameSpeed = gameSpeed - 2;
            } else if (appleCount <= 50) {
                newGameSpeed = gameSpeed - 1;
            }
        }

        return newGameSpeed;
    }

    //------------------------------------------------------------------------------------------------------------------
    // relocateApple() - Relocate apple to randomly chosen X and Y coordinates
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

        updateView(gameboard);
    }

    //------------------------------------------------------------------------------------------------------------------
    // createXYArrayList() - Creates an ArrayList of all possible X or Y positions on the game board
    private ArrayList<Integer> createXYArrayList(int multiple, int maxNumber) {
        maxNumber = maxNumber - multiple;
        ArrayList<Integer> possiblePositions = new ArrayList<>();
        for (int x = 0; x <= maxNumber; x = x + multiple) {
            possiblePositions.add(x);
        }

        return possiblePositions;
    }

    //------------------------------------------------------------------------------------------------------------------
    // selectRandomIndex - Picks random index number between 0 and length of input ArrayList
    private int selectRandomIndex(ArrayList<Integer> possiblePositions) {
        int randomIndex = secureRandom.nextInt(possiblePositions.size());
        return possiblePositions.get(randomIndex);
    }

    //------------------------------------------------------------------------------------------------------------------
    // pauseGame() - Stops timer and pauses game
    private boolean pauseGame(Gameboard gameboard) {
        gameboard.addJLabel("Paused", 35);
        updateView(gameboard);
        timer.stop();
        return true;
    }

    //------------------------------------------------------------------------------------------------------------------
    // unpauseGame() - Starts timer and resumes game
    private boolean unpauseGame(Gameboard gameboard) {
        gameboard.removeAll();
        updateView(gameboard);
        timer.start();
        return false;
    }

    //------------------------------------------------------------------------------------------------------------------
    // createMenu() - Creates menu based on the menuType string and is placed on the input BasePanel
    public void createMenu(String menuType, BasePanel basePanel, ApplicationController applicationController) {

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
    // updateView - Refreshes and repaints current BasePanel
    public void updateView() {
        currentMenu.revalidate();
        currentMenu.repaint();
        currentMenu.sync();
    }

    //------------------------------------------------------------------------------------------------------------------
    // updateView() - Refreshes and repaints input BasePanel
    public void updateView(BasePanel basePanel) {
        basePanel.revalidate();
        basePanel.repaint();
        basePanel.sync();
    }
}