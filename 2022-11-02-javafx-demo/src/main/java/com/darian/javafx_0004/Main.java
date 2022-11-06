package com.darian.javafx_0004;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  17:03
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 设置 透明度， 0 完全透明，1 不透明
        primaryStage.setOpacity(0.8);
        // 一直设置为第一层屏幕上
        primaryStage.setAlwaysOnTop(true);

        // 设置初始的位置，左上角开始算起，X
        primaryStage.setX(300);
        primaryStage.setY(300);

        // 监听器
        primaryStage.xProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(String.format("[xProperty.listener][oldValue][%s][newValue][%s]", oldValue, newValue));
        });
        primaryStage.yProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(String.format("[yProperty.listener][oldValue][%s][newValue][%s]", oldValue, newValue));
        });


        primaryStage.show();
    }
}
