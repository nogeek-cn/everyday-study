package com.darian.javafx_0010;

import com.darian.utils.PrintUtils;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.darian.utils.PrintUtils.printThread;
import static javafx.scene.input.MouseEvent.*;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  20:15
 */

public class DoubleClickMain extends Application {
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

        // 单击事件
        button1.setOnAction(event -> {
            printThread("[button1.setOnAction][单击事件]");
        });

        // 双击事件
        button1.addEventHandler(MOUSE_CLICKED, event -> {
            printThread("鼠标按键：" + event.getButton().name());
            // SECONDARY ： 右键
            // 点击次数是 2 次 &* PRIMARY: 左键
            if (event.getClickCount() == 2 && MouseButton.PRIMARY.name().equals(event.getButton().name())) {
                printThread("[button1.addEventHandler][双击事件]&&[左键]");
            }
        });


        // 键盘按键

        // 按键按下的事件
        button1.setOnKeyPressed(keyEvent -> {
            printThread("按键按下：" + keyEvent.getCode().getName());

            // 监听某个按键
            if (KeyCode.A.getName().equals(keyEvent.getCode().getName())) {
                printThread("按下AAAAAAAAAAAAA");
            }
        });
        // 按键释放的事件
        button1.setOnKeyReleased(keyEvent -> {
            printThread("按键释放：" + keyEvent.getCode().getName());
        });

        Group group = new Group();
        group.getChildren().add(button1);

        Scene scene = new Scene(group);
        primaryStage.setScene(scene);

        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setTitle("javafx");
        primaryStage.show();
    }
}
