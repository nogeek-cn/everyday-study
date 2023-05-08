package com.darian.javafx_0019;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.function.Consumer;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/12/24  23:22
 */
public class StackPaneMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button1 = new Button("button1");
        Button button2 = new Button("button2");
        Button button3 = new Button("button3");
        Button button4 = new Button("button4");
        Button button5 = new Button("button5");

        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: #EE6AA7");

        stackPane.setPadding(new Insets(10));
        stackPane.setAlignment(Pos.BOTTOM_LEFT);
        // 设置外边距
        StackPane.setMargin(button1, new Insets(100));

        stackPane.getChildren().addAll(button1, button2, button3, button4, button5);
        stackPane.getChildren().forEach(node -> {
            System.out.println(Button.class.cast(node).getText());
        });

        Scene scene = new Scene(stackPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("javaFX");
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}
