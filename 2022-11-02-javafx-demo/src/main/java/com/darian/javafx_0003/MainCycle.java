package com.darian.javafx_0003;


import com.darian.utils.ImageUtils;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/3  13:30
 */
public class MainCycle extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("javafx");
        String imageUrl = ImageUtils.getImageUrl();

        System.out.println(imageUrl);
        // 设置图标
        primaryStage.getIcons().add(new Image(imageUrl));

        // 最小化
//        primaryStage.setIconified(true);
        // 最大化
//        primaryStage.setMaximized(false);
        // 关闭窗口
//        primaryStage.close();

        primaryStage.setHeight(200);
        primaryStage.setWidth(200);
//        primaryStage.setMaxHeight(500);
//        primaryStage.setMinHeight(100);
//        primaryStage.setMaxWidth(500);
//        primaryStage.setMinWidth(100);
        // 固定大小:false 不可改变窗口
//        primaryStage.setResizable(false);

//        System.out.println("宽度：" + primaryStage.getWidth());
//        System.out.println("高度：" + primaryStage.getHeight());

        // 高度监听器
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(String.format("[heightProperty][oldValue][%s][newValue][%s]", oldValue, newValue));
            // 随着窗口变化设置 一些页面组件的自适应
        });
        // 宽度监听器
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(String.format("[widthProperty][oldValue][%s][newValue][%s]", oldValue, newValue));
        });

        // 设置全屏，必须 setScene 才能全屏
        primaryStage.setFullScreen(true);
        // 设置桌布等等的功能
        primaryStage.setScene(new Scene(new Group()));


        primaryStage.show();

        // 没有设置 宽和高，只能在 show() 方法之后才能获得宽和高
    }
}
