package com.darian.javafx_0013;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/8  22:21
 */

public class HBoxVBoxMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    // 13.10
    @Override
    public void start(Stage primaryStage) throws Exception {
        // UI 分类：绝对布局/相对布局

        Button button1 = new Button("button1");
        button1.setLayoutX(100);
//        button1.setLayoutY(100);
        Button button2 = new Button("button2");
//        button2.setLayoutX(200);
//        button2.setLayoutY(100);

        Button button3 = new Button("button3");
        Button button4 = new Button("button4");

        // 这个是绝对布局，可以绝对布局，也可以相对布局
        AnchorPane anchorPane = new AnchorPane();
        // AP 管理 button 的时候，button 自己的像素就无效了
        // 上边设置 距离它的父空间 距离 10 个像素
        AnchorPane.setTopAnchor(button1, 10.0);
        // 距离左边10 个像素
//        AnchorPane.setLeftAnchor(button1, 10.0);
//        AnchorPane.setRightAnchor(button1, 100.0);
//        AnchorPane.setBottomAnchor(button1, 200.0);
        // 直接托管了 button 的宽度

        // 设置的是 边框 + 内边距
//        anchorPane.setPadding(new Insets(10));

//        AnchorPane.setTopAnchor(button2, 10.0);
//        AnchorPane.setLeftAnchor(button2, 100.0);


        // AnchorPane 和 Group 结合起来
        Group group1 = new Group();
        group1.getChildren().addAll(button1, button2);
        Group group2 = new Group();
        group2.getChildren().addAll(button3, button4);

        AnchorPane.setTopAnchor(group1, 100.0);

//        anchorPane.getChildren().addAll(button1, button2);
        anchorPane.getChildren().addAll(group1, group2);

        anchorPane.setStyle("-fx-background-color: #FF3E96");
        anchorPane.setOnMouseClicked(event -> {
            System.out.println("[anchorPane.setOnMouseClicked]");
        });
        // group 只能算一个容器，不能算一个布局。
//        Group group = new Group();
//        group.setStyle("-fx-background-color: #FF3E96");
//        group.setOnMouseClicked(event -> {
//            System.out.println("[group.setOnMouseClicked]");
//        });
//        Scene scene = new Scene(group);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("javafx");
        primaryStage.setHeight(800);
        primaryStage.setWidth(1200);
        primaryStage.show();
    }
}
