package com.darian.javafx_0015;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/12/24  21:27
 */
public class SetManagedSetVisibleSetOpacity
        extends Application {


    // 位置被占
    static boolean button3_is_managed = false;
    // 位置不被占
    static boolean Button3_is_visible = false;
    // 位置不被占
    static Integer button3_opacity_value = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button1 = new Button("button1");
        Button button2 = new Button("button2");
        Button button3 = new Button("button3");
        Button button4 = new Button("button4");

        Button button5 = new Button("button3.setManaged(false);");
        Button button6 = new Button("button3.setVisible(false);");
        Button button7 = new Button("button3.setOpacity(0);");

//        button3.setManaged(false);
//        button3.setVisible(false);
//        button3.setOpacity(0);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: #ffffff");

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(20));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(button1, button2, button3, button4);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(button5, button6, button7);

        AnchorPane.setTopAnchor(vBox, 100.0);
        AnchorPane.setLeftAnchor(vBox, 20.0);
        anchorPane.getChildren().addAll(hBox, vBox);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.setTitle("javaFX");
        primaryStage.show();


        button5.setOnAction(ae -> {
            button3.setManaged(button3_is_managed);
            Print.print(hBox);
            button3_is_managed = !button3_is_managed;
            button5.setText("button3.setManaged(" + button3_is_managed + ");");
        });

        button6.setOnAction(ae -> {
            button3.setVisible(Button3_is_visible);
            Print.print(hBox);
            Button3_is_visible = !Button3_is_visible;
            button6.setText("button3.setVisible(" + Button3_is_visible + ");");
        });
        button7.setOnAction(ae -> {
            button3.setOpacity(button3_opacity_value);
            Print.print(hBox);
            if (button3_opacity_value == 0) {
                button3_opacity_value = 1;
            } else {
                button3_opacity_value = 0;
            }
            button7.setText("button3.setOpacity(" + button3_opacity_value + ");");
        });
    }

}

class Print {
    static void print(HBox hBox) {
        System.out.println("当前Hbox里的子组件数量=" + hBox.getChildren().size());
    }
}