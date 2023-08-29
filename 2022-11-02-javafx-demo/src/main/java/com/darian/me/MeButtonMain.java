package com.darian.me;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import static com.darian.utils.PrintUtils.printThread;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  19:52
 */
public class MeButtonMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {



        Button button1 = new Button();
        button1.setText("第一个按钮，这是第一个按钮");

        // "#8FBC8F" 代表颜色； 55 代表透明度
//        Paint.valueOf("#8FBC8F55");
        // 颜色，圆角弧度， Insets ： 是按钮到边框的距离
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("#8FBC8F"), new CornerRadii(20), new Insets(10, 20, 30, 50));
        // 设置背景图
        Background background = new Background(backgroundFill);
        button1.setBackground(background);

        // 设置边框, 颜色，边框
        BorderStroke borderStroke = new BorderStroke(Paint.valueOf("#8A2BE2"), BorderStrokeStyle.DASHED, new CornerRadii(30), new BorderWidths(5));
        Border border = new Border(borderStroke);
        button1.setBorder(border);

        button1.setOnAction(event -> {
            printThread("[button1.setOnAction]:[source]" + event.getSource());

            // 变大
            button1.setPrefHeight(button1.getPrefHeight() + 10);
        });


        Button button2 = new Button();
        button2.setLayoutX(650);

        button2.setText("button2");

        Group group = new Group();
        group.getChildren().add(button1);
        group.getChildren().add(button2);

        Scene scene = new Scene(group);

        scene.getStylesheets().add("css/ElementFX.css");

        primaryStage.setScene(scene);

        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setTitle("javafx");
        primaryStage.show();
    }
}
