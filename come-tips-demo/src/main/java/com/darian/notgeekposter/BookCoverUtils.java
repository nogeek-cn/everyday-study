package com.darian.notgeekposter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/***
 * 1604 * 2096
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/1/16  22:41
 */
public class BookCoverUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(BookCoverUtils.class);
    /**
     * Java 测试图片叠加方法
     */
    public static BufferedImage overlyingImageTest(String bookName, String author) {

        /**
         * 注意-产品要求：
         *          字体-自定义了一个字体
         *          大小-60
         *          颜色-148,0,0
         */

        String fontFileClassPath = "static" + File.separator + "domain" + File.separator + "maozidongziti.ttf";
        String sourceFileClassPath = "static" + File.separator + "domain" + File.separator + "mingYan.png";

        LOGGER.debug("fontFileClassPath: " + fontFileClassPath);
        LOGGER.debug("sourceFileClassPath: " + sourceFileClassPath);


        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource(fontFileClassPath).getInputStream());

            BufferedImage resultImg = new BufferedImage(1640, 2096, BufferedImage.TYPE_INT_RGB);
            // 获取Graphics2D对象，用于在图像上进行绘制操作
            Graphics2D blackG2d = (Graphics2D) resultImg.getGraphics();
            // 设置背景颜色为黑色
            Color blackColor = Color.BLACK;
            blackG2d.setBackground(blackColor);
            blackG2d.clearRect(0, 0, resultImg.getWidth(), resultImg.getHeight());


            Graphics2D fontGraphics = resultImg.createGraphics();

            // 设置字体的渐变色
            GradientPaint fontGradientPaint = new GradientPaint(
                    20, 230, new Color(255, 255, 255),
                    620, 350, new Color(255, 255, 255),
                    true);

            int fontSize = resultImg.getWidth() / (bookName.length() + 1);
            // 字体和字体大小
            font = font.deriveFont(Font.PLAIN, fontSize);


            fontGraphics.setPaint(fontGradientPaint);
            fontGraphics.setFont(font);
            fontGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            fontGraphics.drawString(bookName,
                    (resultImg.getWidth() - Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(bookName)) / 2,
                    (resultImg.getHeight() + fontSize) / 2
            );

            fontSize = 50;
            font = font.deriveFont(Font.PLAIN, fontSize);
            fontGraphics.setFont(font);

            fontGraphics.drawString(author,
                    (resultImg.getWidth() - Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(author)) / 2,
                    (resultImg.getHeight() - fontSize * 3)
            );

            return resultImg;
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
        String bookName = "个人教练";
//                bookName =  "技术产品商业";
//        mingYan = "架构师的个人哲学与认知先于架构师所谈的架构原则";
        String author = "不止极客";


        String domainStaticPath = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "static"
                + File.separator + "domain";

        String saveFilePath = domainStaticPath
                + File.separator + "out"
                + File.separator + "bookCover.png";
        LOGGER.debug("saveFilePath: " + saveFilePath);

        BufferedImage buffImg = overlyingImageTest(bookName, author);
        // 输出水印图片
        generateSaveFile(buffImg, saveFilePath);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(buffImg, "png", os);
            byte[] fileByte = os.toByteArray();
            String imageBase64Str = "data:image/png;base64," + org.apache.commons.codec.binary.Base64.encodeBase64String(fileByte);
            System.out.println();
            System.out.println(imageBase64Str);
            System.out.println();
        } catch (Exception e) {
            LOGGER.info("[DomainImageUtils.overlyingImageAndSaveTest]" + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        overlyingImageAndSaveTest();
    }
}
