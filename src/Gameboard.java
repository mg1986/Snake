import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.ArrayList;

public class Gameboard extends JPanel implements ActionListener, KeyListener {

    private boolean gameOver = false;
    private static final int boardWidth = 310;
    private static final int boardHeight = 510;
    private int xCenter = boardWidth/2;
    private int yCenter = boardHeight/2;
    private final int moveInterval = 10;
    private Snake snake;
    private ArrayList<SnakeSegment> snakeBody;
    private Apple apple;
    private int GAME_SPEED = 100;
    private ArrayList<Integer> xPositions;
    private ArrayList<Integer> yPositions;

    public Gameboard () {
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);
        setSize(boardWidth, boardHeight);
        setVisible(true);

        snake = new Snake(160, 260);
        snakeBody = snake.snakeBody;
        apple = new Apple(100, 100);
        xPositions = createXYArrayList(moveInterval, boardWidth);
        yPositions = createXYArrayList(moveInterval, boardHeight);
        Timer timer = new Timer(GAME_SPEED, this);
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
        int x = head.getCurrentX();
        int y = head.getCurrentY();

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
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {

        boolean gameOver = false;

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
        g.setColor(apple.getAppleColor());
        g.fillRect(apple.getCurrentX(), apple.getCurrentY(), Apple.segmentSize, Apple.segmentSize);

        // Draw Snake object
        for (int idx = 0; idx < snakeBody.size(); idx++) {

            SnakeSegment snakeSegment = snakeBody.get(idx);

            int x = snakeSegment.getCurrentX();
            int y = snakeSegment.getCurrentY();
            int segmentSize = SnakeSegment.segmentSize;

            g.setColor(snakeSegment.getColor());

            if (idx == 0) {
                g.fillRect(x, y, segmentSize, segmentSize);
            } else {
                g.drawRect(x, y, segmentSize, segmentSize);
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private boolean detectCollision(int x, int y, Snake snake) {

        boolean appleCollision = (x == apple.getCurrentX() && y == apple.getCurrentY());
        boolean outOfBounds = (x < 0 || x > boardWidth || y < 0 || y > boardHeight);
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
            System.out.println("Game Over, You Lose!");
            gameOver = true;
        } else if (appleCollision) {
            apple.incrementAppleCount();
            snake.addSegment();
            System.out.println(apple.getAppleCount());
        }

        return gameOver;
    }

    private ArrayList<Integer> createXYArrayList (int multiple, int maxNumber) {
        ArrayList<Integer> possiblePositions = new ArrayList<>();

        for (int x = 0; x <= maxNumber; x += multiple) {
            possiblePositions.add(x);
        }

        return possiblePositions;

    }

    //------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(boardWidth, boardHeight);
        jFrame.setVisible(true);
        jFrame.setBackground(Color.black);

        Gameboard gameBoard = new Gameboard();
        jFrame.add(gameBoard);
        gameBoard.requestFocus();
    }
}
