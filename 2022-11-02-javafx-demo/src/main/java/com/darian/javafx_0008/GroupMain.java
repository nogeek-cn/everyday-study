package com.darian.javafx_0008;

import com.darian.utils.PrintUtils;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  19:23
 */
public class GroupMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button1 = new Button("b1");
        button1.setLayoutX(0);
        button1.setLayoutY(10);
        button1.setPrefHeight(50);
        button1.setPrefWidth(50);

        Button button2 = new Button("b2");
        button2.setLayoutX(100);
        button2.setLayoutY(10);
        button2.setPrefHeight(50);
        button2.setPrefWidth(50);

        Button button3 = new Button("b3");
        button3.setLayoutX(200);
        button3.setLayoutY(10);
        button3.setPrefHeight(50);
        button3.setPrefWidth(50);




        javafx.scene.Group group = new javafx.scene.Group();
        // 添加组件
        group.getChildren().addAll(button1, button2, button3);

        group.getChildren().addListener((ListChangeListener<Node>) c -> {
            PrintUtils.printThread("当前子组件的数量" + c.getList().size());
        });

        final int[] i = {0};
        //  button1 的点击添加一个按钮
        button1.setOnAction(event -> {
            Button button4 = new Button("button4" + "-" + (i[0]++));
            button4.setLayoutX(200 + (i[0] * 100));
            button4.setLayoutY(10);
            group.getChildren().add(button4);
        });

        // 检查是否存在，只能检查左上角的位置
        System.out.println("是否存在[0,10]" + group.contains(0, 10));


        // 自动自动设置大小关掉
//        group.setAutoSizeChildren(false);
        // 移除所有组件
//        group.getChildren().clear();
        // 移除一个元素
//        group.getChildren().remove(0);
        // 设置透明状态
//        group.setOpacity(0.5);


        // 把所有的元素都拿出来。
        Object[] objects = group.getChildren().toArray();
        System.out.println(Arrays.stream(objects).collect(Collectors.toList()));

        Scene scene = new Scene(group);
        primaryStage.setScene(scene);

        primaryStage.setWidth(800);
        primaryStage.setHeight(400);
        primaryStage.setTitle("javafx");
        primaryStage.show();
    }
}
