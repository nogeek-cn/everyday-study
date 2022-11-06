package com.darian.javafx_0005;

import com.darian.utils.CatchException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

import static com.darian.utils.PrintUtils.printThread;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  17:28
 */
public class PlatformMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        printThread("[PlatformMain.start][start]....");

        Platform.runLater(() -> {
            // 这是一个队列，在你空闲的时候帮你更新你的 UI 界面，这个是同一个线程里边的。
            printThread("[Platform.runLater][run]....");
            int i = 1;
            while (i <= 10) {
                CatchException.run(() -> TimeUnit.SECONDS.sleep(1));

                printThread("i = " + i);
                i++;
            }
            // 下载任务不能放在这里，避免卡死，以后有专门的多任务处理
        });

        printThread("[PlatformMain.start][end]....");

        Platform.exit();
    }
}

// [JavaFX Application Thread][[PlatformMain.start][start]....]
//[JavaFX Application Thread][[PlatformMain.start][end]....]
//[JavaFX Application Thread][[Platform.runLater][run]....]
