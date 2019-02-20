package com.mg1986.snake.controllers;

import com.mg1986.snake.views.*;

public class MenuController {

    private String currentMenuType;
    private BaseMenu currentMenu;
    public static final String TITLE_MENU = "TITLE_MENU";
    public static final String CONTROLS_MENU = "CONTROLS_MENU";
    public static final String RESTART_MENU = "RESTART_MENU";

    //------------------------------------------------------------------------------------------------------------------
    public MenuController(ApplicationPanel applicationPanel, ApplicationController applicationController) {
        this.currentMenuType = TITLE_MENU;
        createMenu(TITLE_MENU, applicationPanel, applicationController);
    }

    //------------------------------------------------------------------------------------------------------------------
    public String getCurrentMenuType() {
        return currentMenuType;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void setCurrentMenuType(String newMenu) {
        currentMenuType = newMenu;
    }

    //------------------------------------------------------------------------------------------------------------------
    public BaseMenu getCurrentMenu() {
        return currentMenu;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void setCurrentMenu(BaseMenu newMenu) {
        currentMenu = newMenu;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void createMenu(String menuType, ApplicationPanel applicationPanel, ApplicationController applicationController) {

        applicationPanel.removeAll();

        BaseMenu newMenu = new BaseMenu();

        switch(menuType) {
            case TITLE_MENU:
                newMenu = new TitleMenu();
                break;
            case CONTROLS_MENU:
                newMenu = new ControlsMenu();
                break;
            case RESTART_MENU:
                newMenu = new RestartMenu();
                break;
        }

        newMenu.setFocusable(true);
        newMenu.addKeyListener(applicationController);
        newMenu.requestFocus();
        applicationPanel.add(newMenu);
        setCurrentMenuType(menuType);
        setCurrentMenu(newMenu);
        updateView();
    }

    //------------------------------------------------------------------------------------------------------------------
    public void updateView() {
        currentMenu.revalidate();
        currentMenu.repaint();
        currentMenu.sync();
    }

    //------------------------------------------------------------------------------------------------------------------
    public void updateView(BaseMenu baseMenu) {
        baseMenu.revalidate();
        baseMenu.repaint();
        baseMenu.sync();
    }
}
