package com.darian.javafx_0009;

import com.darian.javafx_0008.GroupMain;
import com.darian.utils.PrintUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.darian.utils.PrintUtils.printThread;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  19:52
 */
public class ButtonMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Button button1 = new Button();
        button1.setText("第一个按钮");
        button1.setLayoutY(30);
        button1.setLayoutX(100);
        button1.setPrefWidth(500);
        button1.setPrefHeight(200);
        // 设置字体
        button1.setFont(Font.font("sans-serif", 40));
        // 设置字体的颜色
        button1.setTextFill(Paint.valueOf("#CD0000"));

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
        button2.setPrefHeight(300);
        button2.setPrefWidth(300);
        button2.setLayoutX(650);
        button2.setText("button2");

        // 直接查询 jfx 的 style 就行了
        // 设置 CSS 格式, 直接设置格式
        button2.setStyle(
                "-fx-background-color: #7CCD7C;"
                        + "-fx-background-radius: 30;"
                        + "-fx-text-fill: #399389;");


        Group group = new Group();
        group.getChildren().add(button1);
        group.getChildren().add(button2);

        Scene scene = new Scene(group);
        primaryStage.setScene(scene);

        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setTitle("javafx");
        primaryStage.show();
    }
}
