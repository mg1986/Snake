package com.mg1986.snake.ui;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.Snake.Scoreboard class -
 */

public class Scoreboard extends BasePanel {

    public JLabel score;
    private BufferedImage appleIcon;
    private static final int boardWidth = 603;
    private static final int boardHeight = 75;

    public Scoreboard() {

        setBackground(Color.BLACK);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(24, 131, 215)));
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setMaximumSize(getPreferredSize());

        try {
            appleIcon = ImageIO.read(Scoreboard.class.getResourceAsStream("/img/apple.png"));
        } catch (IOException ex) {
           ex.printStackTrace();
            System.out.println(ex);
        }

        JLabel appleIconImage = new JLabel(new ImageIcon(appleIcon));
        add(appleIconImage);
        score  = new JLabel(" x 000", SwingConstants.RIGHT);
        score.setFont(new Font("Century Gothic", score.getFont().getStyle(), 35));
        score.setForeground(Color.WHITE);
        add(score);

        setVisible(true);
    }
}

