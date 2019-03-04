package com.mg1986.snake;

import com.mg1986.snake.controllers.ApplicationController;

/**
 * Author: Matthew Gray
 * Last Modified: 3/3/2019
 * Copyright (C) 2018 Matthew Gray
 * com.mg1986.snake.Application class - Application starting point
 */

public class Application {

    //------------------------------------------------------------------------------------------------------------------
    // main() - Entry point for application
    public static void main(String[] args) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.startApplication();
    }
}

