package com.darian.javafx_0011;

import com.darian.utils.PrintUtils;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.stage.Stage;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  20:41
 */
public class QuickButton extends Application {
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

        button1.setOnAction(event -> {
            PrintUtils.printThread("[button1.setOnKeyPressed]");
            play();
        });

        Group group = new Group();
        group.getChildren().add(button1);

        Scene scene = new Scene(group);
        // 只能给 Scene 设置快捷键

        // 第一种方式（MAC 可能有问题）
        KeyCodeCombination kc1 = new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN);
        // 绑定在 button1 的 点击事件
        Mnemonic mnemonic1 = new Mnemonic(button1, kc1);
        scene.addMnemonic(mnemonic1);

        // 第二种方式
        KeyCharacterCombination kc2 = new KeyCharacterCombination("O", KeyCombination.ALT_DOWN);
        Mnemonic mnemonic2 = new Mnemonic(button1, kc2);
        scene.addMnemonic(mnemonic2);

        // 第三种方式
        KeyCodeCombination kc3 = new KeyCodeCombination(KeyCode.valueOf("K"), KeyCombination.ALT_DOWN);
        Mnemonic mnemonic3 = new Mnemonic(button1, kc3);
        scene.addMnemonic(mnemonic3);

        // 第四种方式(最常用), MAC 的兼容性可能有问题
        KeyCodeCombination kccb = new KeyCodeCombination(KeyCode.valueOf("Y"), KeyCombination.CONTROL_DOWN);
        scene.getAccelerators().put(kccb, () -> {
            PrintUtils.printThread("kccb 的快捷键被触发");
            // 还是在 UI 线程中去设置的
            play();

            // 触发一次回调。
            button1.fire();
        });

        primaryStage.setScene(scene);

        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setTitle("javafx");
        primaryStage.show();
    }


    /**
     * 把触发的方法封装出去
     */
    public static void play() {
        PrintUtils.printThread("[play]play.............." );
    }
}
