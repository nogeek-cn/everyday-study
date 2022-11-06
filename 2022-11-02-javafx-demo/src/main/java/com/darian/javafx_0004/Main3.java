package com.darian.javafx_0004;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  17:20
 */
public class Main3 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 模态化窗口，就是这个窗口不关闭，其他的窗口没法子操作
        Stage stage1 = new Stage();
        stage1.setTitle("stage1");


        Stage stage2 = new Stage();
        stage2.setTitle("stage2");
        //
        stage2.initOwner(stage1);
        stage2.initModality(Modality.WINDOW_MODAL);


        Stage stage3 = new Stage();
        // 就是 Application 同一个应用程序的其他窗口就没法子用了
        stage3.initModality(Modality.APPLICATION_MODAL);
        stage3.setTitle("stage3");


        stage2.show();
        stage1.show();
        stage3.show();
    }
}
