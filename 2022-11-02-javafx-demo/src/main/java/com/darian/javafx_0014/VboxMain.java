package com.darian.javafx_0014;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/12/14  07:50
 */
public class VboxMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button1 = new Button("button1");
        Button button2 = new Button("button2");
        Button button3 = new Button("button3");
//        Button button4 = new Button("button4");
//        Button button5 = new Button("button5");
//        Button button6 = new Button("button6");
//        Button button7 = new Button("button7");


        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: #AEEEEE");

        // 没有控件的时候，没有宽高是看不到的。
        // HBox 是垂直拆分的。
        HBox hBox = new HBox();
        hBox.setPrefHeight(400);
        hBox.setPrefWidth(400);

        // 设置内边距, 都是 10 的边距了。
        hBox.setPadding(new Insets(10));
        // 每个子组件之间的边距
        hBox.setSpacing(10);

        // 设置某个子组件的外边距
        HBox.setMargin(button1, new Insets(10));

        // 居中显示
        hBox.setAlignment(Pos.CENTER);


        // 这个就是水平布局了。
        // 超过长度，按钮会挤在一块，每个按钮都会变窄
//        hBox.getChildren().addAll(button1, button2, button3, button4, button5, button6, button7);
        hBox.getChildren().addAll(button1, button2, button3);

        hBox.setStyle("-fx-background-color: #CD2990");


//        VBox vBox = new VBox();
//        vBox.setPrefHeight(400);
//        vBox.setPrefWidth(400);
//        // 这个就是垂直布局了。
//        // 超过长度，按钮会挤在一块，每个按钮都会变窄
////        hBox.getChildren().addAll(button1, button2, button3, button4, button5, button6, button7);
//        vBox.getChildren().addAll(button1, button2, button3);
//
//        vBox.setStyle("-fx-background-color: #CD2990");

        anchorPane.getChildren().add(hBox);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}
