package com.darian.javafx_0007;

import com.darian.utils.ImageUtils;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  18:13
 */
public class ScreenNode extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // 默认浏览器打开网页
        HostServices host = getHostServices();
        host.showDocument("www.baidu.com");

        // 路径
//        URL resource = getClass().getClassLoader().getResource("");
//        System.out.println(resource.toExternalForm());

        Button button = new Button("按钮");
        button.setPrefWidth(200);
        button.setPrefHeight(200);

        // 光标的格式
        button.setCursor(Cursor.MOVE);

        // 一个布局类
        Group group = new Group();
        group.getChildren().add(button);

        // 跟节点 的 会跟着 Scene 变大而变大
        Scene screen = new Scene(group);

        // 光标的格式
//        screen.setCursor(Cursor.CLOSED_HAND);
        // 一个图片的光标
        screen.setCursor(new ImageCursor(new Image(ImageUtils.getImageUrl())));

        // screen 基本特殊处理比较少
        primaryStage.setScene(screen);

        primaryStage.setTitle("javafx");
        primaryStage.setHeight(800);
        primaryStage.setWidth(1200);


        primaryStage.show();
//        Platform.exit();
    }
}

//  +------------------------------------------------------------+
//  |         stage                                              |
//  |     +----------------------------------------------+       |
//  |     |        scene                                 |       |
//  |     |     +----------------------------------+     |       |
//  |     |     |      node                        |     |       |
//  |     |     |                                  |     |       |
//  |     |     +----------------------------------+     |       |
//  |     |                                              |       |
//  |     +----------------------------------------------+       |
//  |                                                            |
//  +------------------------------------------------------------+