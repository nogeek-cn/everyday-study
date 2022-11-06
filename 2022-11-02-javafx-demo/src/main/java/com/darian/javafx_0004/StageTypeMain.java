package com.darian.javafx_0004;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  17:10
 */
public class StageTypeMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
//        Stage stage1 = new Stage();
//        stage1.setTitle("stage1");
//        // 默认
//        stage1.initStyle(StageStyle.DECORATED);
//        stage1.show();
//
//        Stage stage2 = new Stage();
//        stage2.setTitle("stage2");
//        // 完全透明的窗口
//        stage2.initStyle(StageStyle.TRANSPARENT);
//        stage2.show();
//
//        Stage stage3 = new Stage();
//        stage3.setTitle("stage3");
//        // 白色的背景，不带装饰
//        stage3.initStyle(StageStyle.UTILITY);
//        stage3.show();

        Stage stage4 = new Stage();
        stage4.setTitle("stage4");
        //
        stage4.initStyle(StageStyle.UNIFIED);
        stage4.show();

        Stage stage5 = new Stage();
        stage5.setTitle("stage5");
        //
        stage5.initStyle(StageStyle.UNDECORATED);
        stage5.show();

        // 退出所有的窗口
        Platform.exit();

        primaryStage.show();
    }
}
