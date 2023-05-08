package com.darian.javafx_0017;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/12/24  23:03
 */
public class FlowPaneMain extends Application {
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
        Button button6 = new Button("button6");
        Button button7 = new Button("button7");
        Button button8 = new Button("button8");

        FlowPane flowPane = new FlowPane();
        flowPane.setStyle("-fx-background-color: #EE6AA7");
        flowPane.setPadding(new Insets(10));
        // 设置水平间距
        flowPane.setHgap(10);
        // 设置垂直间距
        flowPane.setVgap(10);
        // 默认方向，这是垂直，默认是水平
        flowPane.setOrientation(Orientation.VERTICAL);
        FlowPane.setMargin(button1, new Insets(10));
        // 对齐方式
        flowPane.setAlignment(Pos.CENTER);

        flowPane.getChildren().addAll(button1, button2, button3, button4, button5, button6, button7, button8);

        Scene scene = new Scene(flowPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("javaFX");
        primaryStage.setWidth(300);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}
