package com.mg1986.snake.model;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Author: Matthew Gray
 * Last Modified: 3/1/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.snake.model.Snake class
 */

public class Snake {

    // Snake head - ArrayList<SnakeElements> representing snake body
    private SnakeHead head;

    // Snake body - ArrayList<SnakeElements> representing snake body
    private ArrayList<SnakeElement> body;

    //------------------------------------------------------------------------------------------------------------------
    // Snake constructor - Takes starting X and Y coordinates to place SnakeHead
    public Snake (int headX, int headY) {
        head = new SnakeHead(headX, headY);
        body = new ArrayList<>();
        body.add(head);
        addSegment(2);
    }

    //------------------------------------------------------------------------------------------------------------------
    // getBody() - Returns ArrayList of SnakeElements
    public ArrayList<SnakeElement> getBody() {
        return body;
    }

    //------------------------------------------------------------------------------------------------------------------
    // getBodySize() - Returns size of body ArrayList
    public int getBodySize() {
        return body.size();
    }

    //------------------------------------------------------------------------------------------------------------------
    // getHead() - Returns SnakeHead
    public SnakeHead getHead() {
        return (SnakeHead) body.get(0);
    }

    //------------------------------------------------------------------------------------------------------------------
    // incrementSnake() - Moves head to new position. Also sets next segment X, Y to previous segment X, Y
    public void incrementSnake(int headX, int headY) {

        for (int idx = 0; idx < getBodySize(); idx++) {
            SnakeElement segment = getBody().get(idx);
            if (idx == 0) {
                segment.setPreviousX(segment.getCurrentX());
                segment.setPreviousY(segment.getCurrentY());
                segment.setCurrentX(headX);
                segment.setCurrentY(headY);
            } else {
                SnakeElement previousSegment = getBody().get(idx - 1);
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
    // addSegment() -  Add segments to snake body
    public void addSegment(int numSegmentsToAdd) {
        int snakeSize = getBodySize();
        if (snakeSize > 0) {

            for (int counter = 1; counter <= numSegmentsToAdd; counter++) {
                SnakeElement lastSegment = getBody().get(snakeSize - 1);
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
                getBody().add(newSegment);
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // selfIntersection - Returns true if SnakeHead intersects test X, Y coordinates
    public boolean selfIntersection(int x, int y) {

        boolean selfIntersection = false;

        for (int idx = 0; idx < getBodySize(); idx++) {
            if (idx > 0) {
                SnakeElement SnakeElement = getBody().get(idx);
                if (x == SnakeElement.getCurrentX() && y == SnakeElement.getCurrentY()) {
                    selfIntersection = true;
                }
            }
        }

        return selfIntersection;
    }
}
