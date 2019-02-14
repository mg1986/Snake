package com.mg1986.snake;

import com.mg1986.snake.GameMenu;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.Application class - Starting point for game. Creates JFrame and adds GameMemu object to it.
 */

public class Application {

    //------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        startGame();
    }

    //------------------------------------------------------------------------------------------------------------------
    private static void startGame() {
        JFrame jFrame = new JFrame("Snake");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(480, 715);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);

        GameMenu gameMenu = new GameMenu();
        gameMenu.setBounds(0, 0, 480, 715);

        jFrame.add(gameMenu);
        jFrame.pack();
        jFrame.setVisible(true);
    }

}

