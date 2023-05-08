package com.darian.javafx_0018;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/12/24  23:13
 */
public class GridPaneMain extends Application {
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

        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #EE6AA7");

        // 设置组件在 GridPane 的第几列第几行
        gridPane.add(button1, 0,0);
        gridPane.add(button2, 1,0);
        gridPane.add(button3, 2,0);
        gridPane.add(button4, 3,0);
        gridPane.add(button5, 0,1);
        gridPane.add(button6, 1,1);
        gridPane.add(button7, 2,1);
        gridPane.add(button8, 3,1);

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.setPadding(new Insets(10));
//        GridPane.setMargin(button1, new Insets(10));
//        gridPane.setAlignment(Pos.CENTER);

//        GridPane.setConstraints(button1, 1, 1);
//        gridPane.getChildren().add(button1);

        GridPane.setRowIndex(button1, 0);
        GridPane.setColumnIndex(button1, 1);
        gridPane.getChildren().add(button1);

        // 设置第一列间距
        gridPane.getColumnConstraints().add(new ColumnConstraints(100));
        // 设置第一行间距
        gridPane.getRowConstraints().add(new RowConstraints(50));

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("javaFX");
        primaryStage.setWidth(300);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}
