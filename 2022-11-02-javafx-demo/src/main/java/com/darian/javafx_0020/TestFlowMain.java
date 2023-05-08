package com.darian.javafx_0020;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.stage.Stage;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/12/25  13:15
 */
public class TestFlowMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        Text text1 = new Text("这里是javaFX的学习视频\n");
        Text text2 = new Text("javaFX视频");
        Text text3 = new Text("Hello World");

        text1.setFont(Font.font(40));
        text1.setFill(Paint.valueOf("#FF82AB"));

        text2.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
        text2.setFont(Font.font("Helvetica", FontWeight.BLACK, 20));

        TextFlow textFlow = new TextFlow();
        textFlow.setStyle("-fx-background-color: #EECFA1");
        textFlow.getChildren().addAll(text1, text2, text3);

        textFlow.setPadding(new Insets(10));
        textFlow.setTextAlignment(TextAlignment.RIGHT);
        // 设置行间距
        textFlow.setLineSpacing(30);

        Text text4 = new Text("sdfsdfsdsdfslfjlskjdlkfjaskdjfkjlfs");
        TextFlow textFlow2 = new TextFlow();
        textFlow2.getChildren().add(text4);

        AnchorPane.setTopAnchor(textFlow, 100.0);
        AnchorPane.setLeftAnchor(textFlow, 100.0);

        anchorPane.getChildren().addAll(textFlow, textFlow2);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("javaFX");
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(String.format("[AnchorPane][getWidth][%s]", anchorPane.getWidth()));
            System.out.println(String.format("[textFlow][getWidth][%s]", textFlow.getWidth()));
            System.out.println(String.format("[textFlow2][getWidth][%s]", textFlow2.getWidth()));
        });
    }
}
