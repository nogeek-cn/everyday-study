package com.darian;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:META-INF/spring/demo-context.properties")
@ImportResource({"classpath:META-INF/spring/*.xml"})
public class DemoApplication extends Application {

    private static Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOGGER.info("[DemoApplication.start]... ... ... ... ... ... ... ... ");
        // 一直置于页面顶部
        primaryStage.setAlwaysOnTop(true);

        // 设置宽高
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);

        // title
        primaryStage.setTitle("just do it box");

        //
        Group group = new Group();
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        // 标签
        Label label1 = new Label("番茄工作法提示你：");
        label1.setLayoutX(130);
        label1.setLayoutY(130);
        label1.setFont(Font.font(60));
        label1.setStyle("-fx-text-fill: linear-gradient(to right, #e44219, #005ff3)");
        group.getChildren().add(label1);


        Label label2 = new Label("已经工作了 20 分钟了，请休息两分钟继续工作！！！");
        label2.setLayoutX(180);
        label2.setLayoutY(300);
        label2.setFont(Font.font(40));
        // 背景颜色渐变
//        label2.setStyle("-fx-background-color: linear-gradient(to right, #e44219, #005ff3)");

        label2.setStyle("-fx-text-fill: linear-gradient(to right, #e44219, #005ff3)");
        group.getChildren().add(label2);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
//        SpringApplication.run(DemoApplication.class, args);
    }
}
