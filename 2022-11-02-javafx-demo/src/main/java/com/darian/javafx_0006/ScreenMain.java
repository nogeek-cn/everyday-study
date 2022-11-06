package com.darian.javafx_0006;

import com.darian.utils.PrintUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static com.darian.utils.PrintUtils.printThread;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  18:03
 */
public class ScreenMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 获取主屏幕
        Screen primaryScreen = Screen.getPrimary();

        // 整个屏幕的宽度和高度
        Rectangle2D primaryScreenBounds = primaryScreen.getBounds();
        // 用户可视屏幕的宽度和高度
        Rectangle2D primaryScreenVisualBounds = primaryScreen.getVisualBounds();

        // minX MaxX 就是左边和右边
        // minY maxY 就是上边和下边
        printThread("[primaryScreenBounds]:       \t" +  primaryScreenBounds.toString());
        printThread("[primaryScreenVisualBounds]: \t" +  primaryScreenVisualBounds.toString());


        // 就是像素每英寸
        double dpi = primaryScreen.getDpi();
        printThread("当前屏幕dpi:" + dpi);

        Platform.exit();
    }
}

//[JavaFX Application Thread][[primaryScreenBounds]:       	Rectangle2D [minX = 0.0, minY=0.0, maxX=1512.0, maxY=982.0, width=1512.0, height=982.0]]
//[JavaFX Application Thread][[primaryScreenVisualBounds]: 	Rectangle2D [minX = 0.0, minY=38.0, maxX=1512.0, maxY=910.0, width=1512.0, height=872.0]]