package com.darian.javafx_0013;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/9  23:55
 */

public class VobxMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button1 = new Button("button1");
        button1.setLayoutX(100);
        Button button2 = new Button("button2");
        Button button3 = new Button("button3");
        Button button4 = new Button("button4");

        AnchorPane anchorPane1 = new AnchorPane();
        AnchorPane anchorPane2 = new AnchorPane();
        anchorPane1.setStyle("-fx-background-color: #9BCD9B");

        anchorPane2.setStyle("-fx-background-color: #FF3E96");

        // 这个可以设置宽度
        anchorPane2.setPrefWidth(100);
        anchorPane2.setPrefHeight(200);

        anchorPane2.getChildren().add(button1);
        AnchorPane.setRightAnchor(button1, 0.0);
        AnchorPane.setBottomAnchor(button1, 0.0);

        // 不受他的父级的管理，并在这个位置消失
//        button1.setManaged(false);
        // 看不见，但是还在这个位置上
//        button1.setVisible(true);
        // 透明度
        button1.setOpacity(1);

        anchorPane1.getChildren().add(anchorPane2);

        Scene scene = new Scene(anchorPane1);
        primaryStage.setScene(scene);
        primaryStage.setTitle("javafx");
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();


        AnchorPane.setTopAnchor(anchorPane2, 0.0);
        AnchorPane.setLeftAnchor(anchorPane2, 0.0);
        AnchorPane.setRightAnchor(anchorPane2, anchorPane1.getWidth() / 2);
        AnchorPane.setBottomAnchor(anchorPane2, anchorPane1.getHeight() / 2);

        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            AnchorPane.setBottomAnchor(anchorPane2, anchorPane1.getHeight() / 2);
        });
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            AnchorPane.setRightAnchor(anchorPane2, anchorPane1.getWidth() / 2);
        });

    }
}
