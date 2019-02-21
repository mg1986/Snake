package com.mg1986.snake.controllers;

import com.mg1986.snake.models.Apple;
import com.mg1986.snake.models.Snake;
import com.mg1986.snake.models.SnakeHead;
import com.mg1986.snake.views.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.SecureRandom;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class ApplicationController implements ActionListener, KeyListener {

    public Apple apple;
    public Snake snake;

    private boolean gameOver;
    private boolean gamePaused;

    private String currentMenuType;
    private BasePanel currentMenu;
    public static final String TITLE_MENU = "TITLE_MENU";
    public static final String CONTROLS_MENU = "CONTROLS_MENU";
    public static final String GAME_BOARD = "GAME_BOARD";
    public static final String RESTART_MENU = "RESTART_MENU";
    private static final int applicationWidth = 480;
    private static final int applicationHeight = 715;
    private static final int boardWidth = 480;
    private static final int boardHeight = 640;
    private final int moveInterval = 16;
    private ArrayList<Integer> xPositions;
    private ArrayList<Integer> yPositions;
    private final int startingGameSpeed = 150;
    private int gameSpeed;
    private SecureRandom secureRandom;
    private Gameboard gameboard;
    private Scoreboard scoreboard;
    private ApplicationContainer applicationContainer;
    private Timer timer;

    //------------------------------------------------------------------------------------------------------------------
    public ApplicationController() {

        applicationContainer = new ApplicationContainer(applicationWidth, applicationHeight, this);
        secureRandom = new SecureRandom();
        xPositions = createXYArrayList(moveInterval, boardWidth);
        yPositions = createXYArrayList(moveInterval, boardHeight);
        gameOver = false;
        gamePaused = false;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void startApplication() {

        JFrame jFrame = new JFrame("Snake");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(applicationWidth, applicationHeight);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        applicationContainer.setBounds(0, 0, applicationWidth, applicationHeight);
        jFrame.add(applicationContainer);
        jFrame.pack();

        gameOver = setGameOver(true);
        createMenu(TITLE_MENU, applicationContainer, this);
    }

    //------------------------------------------------------------------------------------------------------------------
    public void startGame() {

        gameSpeed = startingGameSpeed;
        gameOver = setGameOver(false);
        applicationContainer.removeAll();

        apple = new Apple(selectRandomIndex(xPositions), selectRandomIndex(yPositions));
        snake = new Snake(boardWidth / 2, boardHeight / 2);

        scoreboard = new Scoreboard();
        applicationContainer.add(scoreboard);
        gameboard = new Gameboard(apple, snake, this);
        applicationContainer.add(gameboard);
        gameboard.requestFocus();

        timer = new Timer(gameSpeed, this);
        timer.start();
        updateView();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void keyPressed(KeyEvent keyEvent) {

        int keyEventCode = keyEvent.getKeyCode();

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
                case VK_Q:
                    System.exit(0);
                    break;
            }
            updateView();
        } else {
            if (keyEventCode == VK_ENTER) {
                switch (getCurrentMenuType()) {
                    case TITLE_MENU:
                        createMenu(CONTROLS_MENU, applicationContainer, this);
                        break;
                    case CONTROLS_MENU:
                        startGame();
                        setCurrentMenuType(RESTART_MENU);
                        break;
                    case RESTART_MENU:
                        applicationContainer.removeAll();
                        startGame();
                        break;
                }
                updateView();
            } else if (keyEventCode == KeyEvent.VK_Q) {
                System.exit(0);
            }

            updateView();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        updateView(gameboard);
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
                updateView();
            }
            snake.incrementSnake(x, y);
            updateView(gameboard);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private void detectCollision(int x, int y) {

        boolean outOfBounds = (x < 0 || x > boardWidth - moveInterval || y < 0 || y > boardHeight - moveInterval);

        if (outOfBounds || snake.selfIntersection(x, y)) {
            this.gameOver = setGameOver(true);

        } else if (apple.appleCollision(x, y)) {

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

            // Reset timer with new game speed
            timer.stop();
            timer = new Timer(gameSpeed, this);
            timer.start();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public ArrayList<Integer> createXYArrayList(int multiple, int maxNumber) {
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
    private boolean pauseGame(Gameboard gameboard) {
        gameboard.addJLabel("Paused", 35);
        updateView(gameboard);
        timer.stop();
        return true;
    }

    //------------------------------------------------------------------------------------------------------------------
    private boolean unpauseGame(Gameboard gameboard) {
        gameboard.removeAll();
        timer.start();
        return false;
    }

    //------------------------------------------------------------------------------------------------------------------
    private boolean setGameOver(boolean gameStatus) {
        return gameStatus;
    }

    //------------------------------------------------------------------------------------------------------------------
    public String getCurrentMenuType() {
        return currentMenuType;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void setCurrentMenuType(String newMenu) {
        currentMenuType = newMenu;
    }

    //------------------------------------------------------------------------------------------------------------------
    public BasePanel getCurrentMenu() {
        return currentMenu;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void setCurrentMenu(BasePanel newMenu) {
        currentMenu = newMenu;
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
            case GAME_BOARD:
                newMenu = new Gameboard(apple, snake, this);
                break;
            case RESTART_MENU:
                newMenu = new RestartPanel();
                break;
        }

        newMenu.setFocusable(true);
        newMenu.addKeyListener(applicationController);
        basePanel.add(newMenu);
        newMenu.requestFocus();
        setCurrentMenuType(menuType);
        setCurrentMenu(newMenu);
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