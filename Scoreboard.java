import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * Scoreboard class -
 */

public class Scoreboard extends JPanel {

    public  JLabel score;
    private BufferedImage appleIcon;

    private static final int boardWidth = 303;
    private static final int boardHeight = 75;

    public Scoreboard() {

        setBackground(Color.BLACK);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(24, 131, 215)));
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setMaximumSize(getPreferredSize());

        try {
            appleIcon = ImageIO.read(new File("img/apple.png"));
        } catch (IOException ex) {
            System.out.println(ex);
        }

        JLabel appleIconImage = new JLabel(new ImageIcon(appleIcon));
        add(appleIconImage);
        score  = new JLabel(" x 000", SwingConstants.RIGHT);
        score.setFont(new Font("Century Gothic", score.getFont().getStyle(), 25));
        score.setForeground(Color.WHITE);
        add(score);

        setVisible(true);
    }
}

