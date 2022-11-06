package com.darian.javafx_0005;

import com.darian.utils.PrintUtils;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.stage.Stage;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  17:48
 */
public class Main2 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // 是否支持 3D 效果
        PrintUtils.printThread("是否支持 3D 效果:" + Platform.isSupported(ConditionalFeature.SCENE3D));
        PrintUtils.printThread("是否支持 FXML 效果:" + Platform.isSupported(ConditionalFeature.FXML));
        // 还可以看很多成果

        // false：关闭窗口，不会退出程序的；只有 Platform.exit(); 退出程序
        Platform.setImplicitExit(false);


        primaryStage.show();

        Platform.exit();
    }
}
