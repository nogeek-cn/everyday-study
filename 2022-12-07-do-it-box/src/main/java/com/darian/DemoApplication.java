package com.darian;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.SimpleFormatter;

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

        // 知道了按钮
        Button quickButton = new Button();
        quickButton.setLayoutX(400);
        quickButton.setLayoutY(30);
        quickButton.setText("知道啦！退下吧！");
        quickButton.setFont(Font.font(60));
        quickButton.setStyle("-fx-text-fill: linear-gradient(to right, #e44219, #005ff3);");

//        #F6AAF2 #8CC9F3 #00FBEF
        quickButton.setStyle("-fx-text-fill: linear-gradient(to right, #F6AAF2, #8CC9F3, #00FBEF);");
        quickButton.setOnAction(event -> {
            LOGGER.info("[quickButton.onAction]... ... ... ... ... ... ... ... ");
            Platform.exit();
        });
        group.getChildren().add(quickButton);

        // 主标题，标签
        Label mainTitleLabel = new Label("番茄工作法提示你：");
        mainTitleLabel.setLayoutX(130);
        mainTitleLabel.setLayoutY(180);
        mainTitleLabel.setFont(Font.font(60));
        mainTitleLabel.setStyle("-fx-text-fill: linear-gradient(to right, #e44219, #005ff3);"
                + "-fx-alignment: center");
        group.getChildren().add(mainTitleLabel);


        // 副标题，标签
        String dateTimeString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(new Date().toInstant());
        Label subtitleLabel = new Label("\t已经工作了 20 分钟了，请休息两分钟继续工作！！！"
                + "\n\n\n \t\t\t\t\t\t\t\t\t"
                + dateTimeString);
        subtitleLabel.setLayoutX(130);
        subtitleLabel.setLayoutY(320);
        subtitleLabel.setFont(Font.font(40));
        // 背景颜色渐变
//        subtitleLabel.setStyle("-fx-background-color: linear-gradient(to right, #e44219, #005ff3)");
        subtitleLabel.setStyle("-fx-text-fill: linear-gradient(to right, #e44219, #005ff3)");
        group.getChildren().add(subtitleLabel);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
//        SpringApplication.run(DemoApplication.class, args);
    }
}
