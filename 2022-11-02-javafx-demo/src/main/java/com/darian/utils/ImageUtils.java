package com.darian.utils;

import java.io.File;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  18:28
 */
public class ImageUtils {

    public static String getImageUrl() {
        String userDir = System.getProperty("user.dir");

        String imageUrl = "file:" + userDir
                + File.separator + "src" + File.separator + "main" + File.separator + "resources"
                + File.separator + "static" + File.separator + "icon"
                + File.separator + "favicon.ico";
        return imageUrl;
    }
}
