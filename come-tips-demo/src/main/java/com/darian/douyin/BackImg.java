package com.darian.douyin;

import com.darian.notgeekicon.IconAvatarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/6/16  上午6:17
 */
public class BackImg {

    private static Logger LOGGER = LoggerFactory.getLogger(IconAvatarUtils.class);


    /**
     * Java 测试图片叠加方法
     */
    public static BufferedImage overlyingImageTest() {
//        String domainStaticPath = System.getProperty("user.dir")
//                + File.separator + "oss-center-application"
//                + File.separator + "src"
//                + File.separator + "main"
//                + File.separator + "resources"
//                + File.separator + "static"
//                + File.separator + "domain";

        /**
         * 注意-产品要求：
         *          字体-自定义了一个字体
         *          大小-60
         *          颜色-148,0,0
         */

        String fontFileClassPath = "static" + File.separator + "domain" + File.separator + "SourceHanSansCN-Bold.ttf";
        String sourceFileClassPath = "static" + File.separator + "domain" + File.separator + "background.png";

        LOGGER.debug("fontFileClassPath: " + fontFileClassPath);
        LOGGER.debug("sourceFileClassPath: " + sourceFileClassPath);

        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource(fontFileClassPath).getInputStream());
            int fontsize = (int) (640 / 9);
            // 字体和字体大小
            font = font.deriveFont(Font.PLAIN, fontsize);
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
                    320 - fontsize, 320, new Color(246, 170, 242),
                    640 - fontsize, 320, new Color(0, 251, 239));


            fontGraphics.setPaint(fontGradientPaint);
            fontGraphics.setFont(font);
//            fontGraphics.shear(0.1, -0.2);// 倾斜画布
            fontGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            List<String> printStringList = new ArrayList<>();

            printStringList.add("不止");
            printStringList.add("极客");
            LOGGER.debug("printStringList:" + printStringList);


            // 画字体
            fontGraphics.setPaint(fontGradientPaint);


            // x 左右，y 上下
//            fontGraphics.drawString("不止", 107, 320);
            String drawContent = "微信搜 不止极客";
            fontGraphics.drawString(
                    drawContent,
                    (imageWidth - Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(drawContent)) / 2,
                    Toolkit.getDefaultToolkit().getFontMetrics(font).getDescent()  * 6
            );


            fontsize = (int) (640 / 25);
            // 字体和字体大小
            font = font.deriveFont(Font.PLAIN, fontsize);
            fontGraphics.setFont(font);
            drawContent = "曾是技术负责人 公众号";
            fontGraphics.drawString(
                    drawContent,
                    (imageWidth - Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(drawContent) - 63),
                    Toolkit.getDefaultToolkit().getFontMetrics(font).getDescent()  * 5 + 140
            );
            drawContent = "关注我 进交流群";
            fontGraphics.drawString(
                    drawContent,
                    (imageWidth - Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(drawContent) - 63) ,
                    Toolkit.getDefaultToolkit().getFontMetrics(font).getDescent() * 9 + 140
            );


//            Stroke dash = new BasicStroke(10.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.5f, null, 0f);
//            fontGraphics.setStroke(dash);

//            fontGraphics.drawOval(20, 20, 600, 600);

            fontGraphics.dispose();

            bufferImage = bufferImage.getSubimage(0, 0, imageWidth, 360);
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
        String domainStaticPath = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "static"
                + File.separator + "domain";

        String saveFilePath = domainStaticPath
                + File.separator + "out"
                + File.separator + "NotGeekDouYin.png";
        LOGGER.debug("saveFilePath: " + saveFilePath);

        BufferedImage buffImg = overlyingImageTest();
        // 输出水印图片
        generateSaveFile(buffImg, saveFilePath);
    }

    public static void main(String[] args) {
        overlyingImageAndSaveTest();
    }

}
