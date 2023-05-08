package com.darian.javafx_0016;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/12/24  22:46
 */
public class BorderPaneMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Button button = new Button("Button");

        AnchorPane anchorPane1 = new AnchorPane();
        anchorPane1.setPrefHeight(100);
        anchorPane1.setPrefWidth(100);
        anchorPane1.setStyle("-fx-background-color: #EE6AA7");

        AnchorPane anchorPane2 = new AnchorPane();
        anchorPane2.setPrefHeight(100);
        anchorPane2.setPrefWidth(100);
        anchorPane2.setStyle("-fx-background-color: #98FB98");

        AnchorPane anchorPane3 = new AnchorPane();
        anchorPane3.setPrefHeight(100);
        anchorPane3.setPrefWidth(100);
        anchorPane3.setStyle("-fx-background-color: #A0522D");

        AnchorPane anchorPane4 = new AnchorPane();
        anchorPane4.setPrefHeight(100);
        anchorPane4.setPrefWidth(100);
        anchorPane4.setStyle("-fx-background-color: #1E90FF");

        AnchorPane anchorPane5 = new AnchorPane();
        anchorPane5.setPrefHeight(100);
        anchorPane5.setPrefWidth(100);
        anchorPane5.setStyle("-fx-background-color: #EEEE00");

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #B23AEE");
        borderPane.setTop(anchorPane1);
        borderPane.setBottom(anchorPane2);
        borderPane.setLeft(anchorPane3);
        borderPane.setRight(anchorPane4);
        borderPane.setCenter(anchorPane5);

        // 内边距
        borderPane.setPadding(new Insets(10));
        // 外边距
        BorderPane.setMargin(anchorPane1, new Insets(10));

//        borderPane.setTop(button);
//        // 对齐方式
//        BorderPane.setAlignment(button, Pos.BOTTOM_CENTER);
//        BorderPane.setAlignment(button, Pos.CENTER);


        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("javaFX");
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}
