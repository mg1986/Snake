import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * GameMenu class -
 */

public class Gameboard extends JPanel implements ActionListener, KeyListener {

    private boolean gameOver = false;
    private boolean gamePaused = false;
    private static final int boardWidth = 300;
    private static final int boardHeight = 400;
    private final int moveInterval = 10;
    private Snake snake;
    private ArrayList<SnakeSegment> snakeBody;
    private Apple apple;
    private int gameSpeed;
    private final int gameSpeedIncrementValue = 3;
    private ArrayList<Integer> xPositions;
    private ArrayList<Integer> yPositions;
    private SecureRandom secureRandom;
    private Scoreboard scoreboard;
    private GameMenu gameMenu;
    private Timer timer;
    private JLabel jLabel;

    public Gameboard () {}

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

        setPreferredSize(new Dimension(303, boardHeight ));
        setMaximumSize(getPreferredSize());

        snake = new Snake(160, 260);
        snakeBody = snake.snakeBody;

        apple = new Apple(selectRandomIndex(xPositions), selectRandomIndex(yPositions));

        gameSpeed = 125;

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
        boolean snakeBodySizeEqualsOne = (snakeBody.size() == 1);
        SnakeSegment head = snake.snakeBody.get(0);
        String direction = head.getDirection();

        switch(keyEventCode){
            case KeyEvent.VK_RIGHT:
                if (!direction.equals("LEFT") || snakeBodySizeEqualsOne) head.setDirection("RIGHT");
                break;
            case KeyEvent.VK_LEFT:
                if (!direction.equals("RIGHT") || snakeBodySizeEqualsOne) head.setDirection("LEFT");
                break;
            case KeyEvent.VK_UP:
                if (!direction.equals("DOWN") || snakeBodySizeEqualsOne) head.setDirection("UP");
                break;
            case KeyEvent.VK_DOWN:
                if (!direction.equals("UP") || snakeBodySizeEqualsOne) head.setDirection("DOWN");
                break;
            case KeyEvent.VK_P:
                if (!gamePaused) {
                    addJLabel ("Paused", 25);
                    timer.stop();
                    gamePaused = true;
                } else if (gamePaused) {
                    remove(jLabel);
                    timer.start();
                    gamePaused = false;
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
    }

    //------------------------------------------------------------------------------------------------------------------
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Apple object
        g.drawImage(apple.appleImage, apple.getCurrentX(), apple.getCurrentY(), this);

        // Draw Snake object
        for (int idx = 0; idx < snakeBody.size(); idx++) {

            SnakeSegment snakeSegment = snakeBody.get(idx);

            int x = snakeSegment.getCurrentX();
            int y = snakeSegment.getCurrentY();
            int segmentSize = SnakeSegment.segmentSize;

            if (idx == 0) {
                String direction = snakeSegment.getDirection();
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
                g.setColor(snakeSegment.getColor());
                g.drawRect(x, y, segmentSize, segmentSize);
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

            relocateApple ();

            apple.incrementAppleCount();
            scoreboard.score.setText(" x " + apple.getFormattedAppleCount());
            scoreboard.repaint();

            if (apple.getAppleCount() <= 25) {
                snake.addSegment(2);
                if (gameSpeed > 50) gameSpeed = gameSpeed - gameSpeedIncrementValue;
            } else {
                snake.addSegment(1);
            }

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
    }

    //------------------------------------------------------------------------------------------------------------------
    public void addJLabel (String message, int fontSize) {
        jLabel = new JLabel(message, JLabel.CENTER);
        jLabel.setForeground(Color.WHITE);
        jLabel.setBackground(Color.BLACK);
        jLabel.setFont(new Font("Century Gothic", jLabel.getFont().getStyle(), fontSize));
        jLabel.setAlignmentX( Component.CENTER_ALIGNMENT );
        add(jLabel);
    }
}
