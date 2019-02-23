package com.mg1986.snake.model;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.Snake class -
 */

public class Snake {

    // Snake body
    private ArrayList<SnakeElement> snakeBody;

    //------------------------------------------------------------------------------------------------------------------
    public Snake() {}

    //------------------------------------------------------------------------------------------------------------------
    public Snake (int headX, int headY) {
        snakeBody = new ArrayList<>();
        SnakeHead head = new SnakeHead(headX, headY, Color.GREEN);
        snakeBody.add(head);
        addSegment(2);
    }

    //------------------------------------------------------------------------------------------------------------------
    public ArrayList<SnakeElement> getSnakeBody() {
        return snakeBody;
    }

    //------------------------------------------------------------------------------------------------------------------
    public int getSnakeBodySize() {
        return snakeBody.size();
    }

    //------------------------------------------------------------------------------------------------------------------
    public SnakeHead getSnakeHead() {
        return (SnakeHead) snakeBody.get(0);
    }

    //------------------------------------------------------------------------------------------------------------------
    public void incrementSnake(int headX, int headY) {

        for (int idx = 0; idx < getSnakeBodySize(); idx++) {
            SnakeElement segment = getSnakeBody().get(idx);
            if (idx == 0) {
                segment.setPreviousX(segment.getCurrentX());
                segment.setPreviousY(segment.getCurrentY());
                segment.setCurrentX(headX);
                segment.setCurrentY(headY);
            } else {
                SnakeElement previousSegment = getSnakeBody().get(idx - 1);
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

    //------------------------------------------------------------------------------------------------------------------
    public void addSegment(int numSegmentsToAdd) {
        int snakeSize = getSnakeBodySize();
        if (snakeSize > 0) {

            for (int counter = 1; counter <= numSegmentsToAdd; counter++) {
                SnakeElement lastSegment = getSnakeBody().get(snakeSize - 1);
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

                SnakeElement newSegment = new SnakeElement(x, y, Color.GREEN);
                getSnakeBody().add(newSegment);
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public boolean selfIntersection(int x, int y) {

        boolean selfIntersection = false;

        for (int idx = 0; idx < getSnakeBodySize(); idx++) {
            if (idx > 0) {
                SnakeElement SnakeElement = getSnakeBody().get(idx);
                if (x == SnakeElement.getCurrentX() && y == SnakeElement.getCurrentY()) {
                    selfIntersection = true;
                }
            }
        }

        return selfIntersection;
    }
}
