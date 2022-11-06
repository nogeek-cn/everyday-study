package com.darian.javafx_0002;


import javafx.application.Application;
import javafx.stage.Stage;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/2  23:43
 */
public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("[Application.start]。。。");

        stage.setTitle("JavaFX测试窗口");
        stage.show();
    }
}
