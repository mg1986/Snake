import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Author: Matthew Gray
 * Last Modified: 3/7/2018
 * Copyright (C) 2018 Matthew Gray
 * GameMenu class -
 */

public class GameMenu extends JPanel implements KeyListener {

    private final String MAIN_MENU = "MAIN_MENU";
    private final String CONTROLS_MENU = "CONTROL_MENU";
    private final String RESTART_GAME_MENU = "RESTART_GAME_MENU";
    private static final int menuWidth = 300;
    private static final int menuHeight = 475;
    private String currentMenu;

    //------------------------------------------------------------------------------------------------------------------
    public GameMenu () {

        setBackground(Color.BLACK);

        setFocusable(true);
        addKeyListener(this);

        setPreferredSize(new Dimension(menuWidth, menuHeight));
        setMaximumSize(getPreferredSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        createTitleMenu();
        currentMenu = MAIN_MENU;

        setVisible(true);
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

        if (keyEventCode == KeyEvent.VK_ENTER) {
                switch (currentMenu) {
                    case MAIN_MENU:
                        createControlsMenu();
                        currentMenu = CONTROLS_MENU;
                        break;
                    case CONTROLS_MENU:
                        startGame();
                        currentMenu = RESTART_GAME_MENU;
                        break;
                    case RESTART_GAME_MENU:
                        startGame();
                        break;
                }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public void addJLabel (JPanel jPanel, String message, int fontSize) {

        JLabel jLabel = new JLabel(message);
        jLabel.setForeground(Color.WHITE);
        jLabel.setFont(new Font("Century Gothic", jLabel.getFont().getStyle(), fontSize));
        jLabel.setAlignmentX( Component.CENTER_ALIGNMENT );
        jPanel.add(jLabel);
    }

    //------------------------------------------------------------------------------------------------------------------
    private void  createTitleMenu() {
        removeAll();
        TitleMenu titleMenu = new TitleMenu();
        titleMenu.setPreferredSize(new Dimension(menuWidth, menuHeight));
        titleMenu.setMaximumSize(getPreferredSize());
        titleMenu.setLayout(new BoxLayout(titleMenu, BoxLayout.Y_AXIS));
        titleMenu.setBackground(Color.BLACK);
        titleMenu.add(Box.createVerticalStrut(125));
        addJLabel(titleMenu, "SNAKE", 75);
        addJLabel(titleMenu, "A game by Matt Gray 2018", 15);
        titleMenu.add(Box.createVerticalStrut(75));
        addJLabel(titleMenu, "[Press ENTER]", 20);
        titleMenu.setVisible(true);
        add(titleMenu);

        revalidate();
        repaint();
    }

    //------------------------------------------------------------------------------------------------------------------
    private void createControlsMenu() {
        removeAll();
        JPanel controlsMenu = new JPanel();
        controlsMenu.setPreferredSize(new Dimension(menuWidth, menuHeight));
        controlsMenu.setMaximumSize(getPreferredSize());
        controlsMenu.setLayout(new BoxLayout(controlsMenu, BoxLayout.Y_AXIS));
        controlsMenu.setBackground(Color.BLACK);
        controlsMenu.add(Box.createVerticalStrut(75));
        addJLabel(controlsMenu, "Controls", 50);
        controlsMenu.add(Box.createVerticalStrut(40));
        addJLabel(controlsMenu, "Navigation:   Arrow keys", 20);
        controlsMenu.add(Box.createVerticalStrut(20));
        addJLabel(controlsMenu, "Pause:   p key", 20);
        controlsMenu.add(Box.createVerticalStrut(20));
        addJLabel(controlsMenu, "Quit:   q key", 20);
        controlsMenu.add(Box.createVerticalStrut(30));
        addJLabel(controlsMenu, "[Press ENTER to play]", 20);
        controlsMenu.setVisible(true);
        add(controlsMenu);

        revalidate();
        repaint();
    }

    //------------------------------------------------------------------------------------------------------------------
    public void createRestartGameMenu(Gameboard gameboard) {

        remove(gameboard);
        JPanel restartGameMenu = new JPanel();
        restartGameMenu.setPreferredSize(new Dimension(menuWidth, 400));
        restartGameMenu.setMaximumSize(getPreferredSize());
        restartGameMenu.setLayout(new BoxLayout(restartGameMenu, BoxLayout.Y_AXIS));
        restartGameMenu.setBackground(Color.BLACK);
        add(restartGameMenu);
        restartGameMenu.add(Box.createVerticalStrut(125));
        addJLabel(restartGameMenu, "You Lose!", 50);
        restartGameMenu.add(Box.createVerticalStrut(15));
        addJLabel(restartGameMenu, "[Press ENTER to restart game]", 15);
        restartGameMenu.setVisible(true);

        revalidate();
        repaint();
    }

    //------------------------------------------------------------------------------------------------------------------
    public void startGame() {

        removeAll();
        Scoreboard scoreboard = new Scoreboard();
        add(scoreboard);
        Gameboard gameboard = new Gameboard(this, scoreboard);
        add(gameboard);
        gameboard.requestFocus();

        revalidate();
        repaint();
    }
}
