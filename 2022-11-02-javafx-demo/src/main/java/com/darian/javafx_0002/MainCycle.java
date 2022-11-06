package com.darian.javafx_0002;

import javafx.application.Application;
import javafx.stage.Stage;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/3  11:50
 */
public class MainCycle extends Application {
    //[Application.main()][main]
    //[Application.init()][JavaFX-Launcher]
    //[Application.start()][JavaFX Application Thread]
    //[Application.stop()][JavaFX Application Thread]

    public static void main(String[] args) {
        System.out.println("[Application.main()]" + getCurrentThreadName());
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("[Application.start()]" + getCurrentThreadName());

        primaryStage.setTitle("test");
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        System.out.println("[Application.init()]" + getCurrentThreadName());
        super.init();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("[Application.stop()]" + getCurrentThreadName());
        super.stop();
    }

    public static String getCurrentThreadName() {
        return "[" + Thread.currentThread().getName() + "]";
    }
}
