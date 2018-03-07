import java.awt.Color;;
import java.util.ArrayList;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * Snake class -
 */

public class Snake {

    public ArrayList<SnakeSegment> snakeBody;

    public Snake (int headX, int headY) {
        snakeBody = new ArrayList<>();
        SnakeSegment head = new SnakeSegment(headX, headY, Color.GREEN);
        snakeBody.add(head);
    }

    public void incrementSnake(int headX, int headY) {

        for (int idx = 0; idx < snakeBody.size(); idx++) {
            SnakeSegment segment = snakeBody.get(idx);
            if (idx == 0) {
                segment.setPreviousX(segment.getCurrentX());
                segment.setPreviousY(segment.getCurrentY());
                segment.setCurrentX(headX);
                segment.setCurrentY(headY);
            } else {
                SnakeSegment previousSegment = snakeBody.get(idx - 1);
                segment.setPreviousX(segment.getCurrentX());
                segment.setPreviousY(segment.getCurrentY());
                segment.setCurrentX(previousSegment.getPreviousX());
                segment.setCurrentY(previousSegment.getPreviousY());
            }

            int currentX = segment.getCurrentX();
            int currentY = segment.getCurrentY();
            int previousX = segment.getPreviousX();
            int previousY = segment.getPreviousY();

            if (currentX > previousX) {
                segment.setDirection("RIGHT");
            } else if (currentX < previousX) {
                segment.setDirection("LEFT");
            } else if (currentY < previousY) {
                segment.setDirection("UP");
            } else if (currentY > previousY) {
                segment.setDirection("DOWN");
            }
        }
    }

    public void addSegment(int numSegmentsToAdd) {
        int snakeSize = snakeBody.size();
        if (snakeSize > 0) {

            for (int counter = 1; counter <= numSegmentsToAdd; counter++) {
                SnakeSegment lastSegment = snakeBody.get(snakeSize - 1);
                int x = lastSegment.getCurrentX();
                int y = lastSegment.getCurrentY();

                String segmentDirection = lastSegment.getDirection();
                if (segmentDirection.equals("RIGHT")) {
                    x = x - 9;
                } else if (segmentDirection.equals("LEFT")) {
                    x = x + 9;
                } else if (segmentDirection.equals("UP")) {
                    y = y - 9;
                } else if (segmentDirection.equals("DOWN")) {
                    y = y + 9;
                }

                SnakeSegment newSegment = new SnakeSegment(x, y, Color.GREEN);
                snakeBody.add(newSegment);
            }
        }
    }
}
