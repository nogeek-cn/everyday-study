package com.darian.notgeekposter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/7/16  17:20
 */

public class PosterUtils {


    private static Logger LOGGER = LoggerFactory.getLogger(PosterUtils.class);


    /**
     * Java 测试图片叠加方法
     */
    public static BufferedImage overlyingImageTest(String mingYan, String author) {

        /**
         * 注意-产品要求：
         *          字体-自定义了一个字体
         *          大小-60
         *          颜色-148,0,0
         */

        String fontFileClassPath = "static" + File.separator + "domain" + File.separator + "maozidongziti.ttf";
        String sourceFileClassPath = "static" + File.separator + "domain" + File.separator + "background.png";

        LOGGER.debug("fontFileClassPath: " + fontFileClassPath);
        LOGGER.debug("sourceFileClassPath: " + sourceFileClassPath);

        int fontSize = 50;
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource(fontFileClassPath).getInputStream());
            // 字体和字体大小
            font = font.deriveFont(Font.PLAIN, fontSize);
            BufferedImage bufferImage = ImageIO.read(new ClassPathResource(sourceFileClassPath).getInputStream());

            // 获取图片的宽度
            int imageWidth = bufferImage.getWidth();
            // 获取图片的高度
            int imageHeight = bufferImage.getHeight();
            LOGGER.debug(String.format("[bufferImage][imageWidth][%s][imageHeight][%s]", imageWidth, imageHeight));

            //
            Graphics2D fontGraphics = bufferImage.createGraphics();

            // 设置字体的渐变色
            GradientPaint fontGradientPaint = new GradientPaint(
                    20, 230, new Color(255, 255, 255),
                    620, 350, new Color(255, 255, 255),
                    true);


            fontGraphics.setPaint(fontGradientPaint);
            fontGraphics.setFont(font);
            fontGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 画字体
            fontGraphics.setPaint(fontGradientPaint);
            int y = 75;


            List<String> printStringList = new ArrayList<>();

            String otherMingYan = mingYan;
            while (true) {
                int spilt = 0;
                for (int i = 1; i <= otherMingYan.length(); i++) {
                    String beforeString = otherMingYan.substring(0, i);
                    int beforeStringWidth = Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(beforeString);
                    if (beforeStringWidth > 550) {
                        break;
                    }
                    spilt = i;
                }
                printStringList.add(otherMingYan.substring(0, spilt));
                otherMingYan = otherMingYan.substring(spilt, otherMingYan.length());


                int otherMingYanWeight = Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(otherMingYan);
                if (otherMingYanWeight <= 550) {
                    printStringList.add(otherMingYan);
                    break;
                }
            }




            // x 左右，y 上下

            for (String printString : printStringList) {
                fontGraphics.drawString(printString, 25, y);
                y = y + fontSize * 3 / 2;
            }

            author = "——" + author;
            fontGraphics.drawString(author,
                    575 - Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(author),
                    y);



            fontSize = fontSize / 3;
            font = font.deriveFont(Font.PLAIN, fontSize);
            fontGraphics.setFont(font);
            y = y + fontSize * 3 / 2;
            y = y + fontSize * 3 / 2;
            fontGraphics.drawString("不止极客", 250, y);
            y = y + fontSize * 1 / 1;
            fontGraphics.dispose();


            // 裁剪
            bufferImage = bufferImage.getSubimage(0, 0, 600, y);
            return bufferImage;
        } catch (Exception e) {
            LOGGER.error("[DomainImageUtils.overlyingImageTest]", e);
        }
        return null;
    }


    /**
     * 输出图片
     *
     * @param buffImg  图像拼接叠加之后的BufferedImage对象
     * @param savePath 图像拼接叠加之后的保存路径
     */
    public static void generateSaveFile(BufferedImage buffImg, String savePath) {
        int temp = savePath.lastIndexOf(".") + 1;
        try {
            File outFile = new File(savePath);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            ImageIO.write(buffImg, savePath.substring(temp), outFile);
            LOGGER.info("ImageIO write...");
        } catch (IOException e) {
            LOGGER.error("[DomainImageUtils.generateSaveFile]", e);
        }
    }


    public static void overlyingImageAndSaveTest() {
        String mingYan =  "我知道我不知道";
        mingYan = "架构师的个人哲学与认知先于架构师所谈的架构原则";
        String author = "苏格拉底";


        String domainStaticPath = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "static"
                + File.separator + "domain";

        String saveFilePath = domainStaticPath
                + File.separator + "out"
                + File.separator + "mao.png";
        LOGGER.debug("saveFilePath: " + saveFilePath);

        BufferedImage buffImg = overlyingImageTest(mingYan, author);
        // 输出水印图片
        generateSaveFile(buffImg, saveFilePath);
    }

    public static void main(String[] args) {
        overlyingImageAndSaveTest();
    }

}
