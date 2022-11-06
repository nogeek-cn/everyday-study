package com.darian.javafx_0012;

import com.darian.utils.PrintUtils;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

import static com.darian.utils.PrintUtils.printThread;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  21:00
 */
public class TextPassword extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.setTitle("javafx");
        primaryStage.setHeight(800);
        primaryStage.setWidth(1200);


        // 输入框
        TextField textField = new TextField();
        // 设置文本
//        textField.setText("这是文本");
        textField.setLayoutX(100);
        textField.setLayoutY(100);
        textField.setFont(Font.font(50));
        textField.setPromptText("请输入一段文字");
        // 去除焦点
        textField.setFocusTraversable(false);

        // 文本监听
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            printThread(String.format("[textField.textProperty][%s][%s]", oldValue, newValue));
            // 限制长度
            // 超过 7 个字符, 设置成老的值
            if (Objects.nonNull(newValue) && newValue.length() > 7) {
                textField.setText(oldValue);
            }
        });

        // 选择了哪些文本
        textField.selectedTextProperty().addListener((observable, oldValue, newValue) -> {
            printThread(String.format("[textField.selectedTextProperty][%s][%s]", oldValue, newValue));
        });

        // 文本框的单机事件
        textField.setOnMouseClicked(event -> {
            printThread("[textField.setOnMouseClicked]");
        });

        // 格式
//        textField.setStyle("");

        // 放上去一段时间
        Tooltip tooltip = new Tooltip("这是提示");
        tooltip.setFont(Font.font(60));
        textField.setTooltip(tooltip);

        group.getChildren().add(textField);


        // 密码框
        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(100);
        passwordField.setLayoutY(200);
        passwordField.setFont(Font.font(50));
        passwordField.setPromptText("请输入密码");

        // 不能使用
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue) && newValue.contains("a")) {
                PrintUtils.printThread("请不要输入字符a");
            }
        });
        group.getChildren().add(passwordField);


        // 标签
        Label label = new Label("我是一个标签");
        label.setLayoutX(100);
        label.setLayoutY(300);
        label.setFont(Font.font(40));
        label.setOnMouseClicked(event -> {
            PrintUtils.printThread("[label.setOnMouseClicked]");
        });

        group.getChildren().add(label);

        primaryStage.show();
    }
}
