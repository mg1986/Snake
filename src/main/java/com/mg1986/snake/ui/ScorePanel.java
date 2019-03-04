package com.mg1986.snake.ui;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Author: Matthew Gray
 * Last Modified: 3/3/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.snake.ui.ScorePanel class
 */

public class ScorePanel extends BasePanel {

    // Game score
    private int score;

    // Score JLabel
    private JLabel scoreLabel;

    // Apple image JLabel
    private JLabel appleIconImage;

    // Apple BufferedImage icon
    private BufferedImage appleIcon;

    //------------------------------------------------------------------------------------------------------------------
    // ScorePanel constructor -
    public ScorePanel(int width, int height) {

        score = 0;

        setBackground(Color.BLACK);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(24, 131, 215)));
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(getPreferredSize());

        try {
            appleIcon = ImageIO.read(ScorePanel.class.getResourceAsStream("/img/apple.png"));
        } catch (IOException ex) {
           ex.printStackTrace();
            System.out.println(ex);
        }

        appleIconImage = new JLabel(new ImageIcon(appleIcon));
        add(appleIconImage);

        scoreLabel = new JLabel(" x 000", SwingConstants.RIGHT);
        scoreLabel.setFont(new Font("Century Gothic", scoreLabel.getFont().getStyle(), 35));
        scoreLabel.setForeground(Color.WHITE);
        add(scoreLabel);

        setVisible(true);
    }

    //------------------------------------------------------------------------------------------------------------------
    // getScore() -  Get current game score
    public int getScore() {
        return this.score;
    }

    //------------------------------------------------------------------------------------------------------------------
    // setScore() - Set current game score
    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText(" x " + String.format ("%03d", score));
    }
}

