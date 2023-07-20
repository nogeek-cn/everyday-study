package com.darian.ownposter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/7/16  17:20
 */

public class OwnerPosterUtils {


    private static Logger LOGGER = LoggerFactory.getLogger(OwnerPosterUtils.class);

    private static boolean isEnglish(Character character) {
        return (character >= 'a' && character <= 'z')
                || (character >= 'A' && character <= 'Z');
    }


    /**
     * Java 测试图片叠加方法
     */
    public static BufferedImage overlyingImageTest(String mingYan) {

        /**
         * 注意-产品要求：
         *          字体-自定义了一个字体
         *          大小-60
         *          颜色-148,0,0
         */

        String maoFontFileClassPath = "static" + File.separator + "domain" + File.separator + "maozidongziti.ttf";
        String hanSonFontFileClassPath = "static" + File.separator + "domain" + File.separator + "SourceHanSansCN-Bold.ttf";
        String sourceFileClassPath = "static" + File.separator + "domain" + File.separator + "mingYan.png";

        LOGGER.debug("fontFileClassPath: " + maoFontFileClassPath);
        LOGGER.debug("sourceFileClassPath: " + sourceFileClassPath);

        int fontSize = 22;
        try {

            Font font = Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource(hanSonFontFileClassPath).getInputStream());
            BufferedImage bufferImage = ImageIO.read(new ClassPathResource(sourceFileClassPath).getInputStream());
            // 字体和字体大小
            font = font.deriveFont(Font.PLAIN, fontSize);
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
//            GradientPaint fontGradientPaint = new GradientPaint(
//                    20, 230, new Color(246, 170, 242),
//                    620, 350, new Color(0, 251, 239),
//                    true);


            fontGraphics.setPaint(fontGradientPaint);
            fontGraphics.setFont(font);
            fontGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 画字体
            fontGraphics.setPaint(fontGradientPaint);
            int y = 50;


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

                List<Character> notfirstList = Arrays.asList('。', '，', '！', '？');
                while (spilt < otherMingYan.length()
                        && notfirstList.contains(otherMingYan.charAt(spilt))) {
                    spilt = spilt - 1;
                }

                while (spilt < otherMingYan.length()
                        && spilt > 1) {
                    Character per = otherMingYan.charAt(spilt - 1);
                    Character next = otherMingYan.charAt(spilt);
                    if (isEnglish(per) && isEnglish(next)) {
                        spilt = spilt - 1;
                    } else {
                        break;
                    }

                }

                printStringList.add(otherMingYan.substring(0, spilt));
                otherMingYan = otherMingYan.substring(spilt);


                int otherMingYanWeight = Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(otherMingYan);
                if (otherMingYanWeight <= 550) {
                    if (Objects.nonNull(otherMingYan) && otherMingYan.length() > 1) {
                        printStringList.add(otherMingYan);
                    }
                    break;
                }
            }


            // x 左右，y 上下

            for (String printString : printStringList) {
                fontGraphics.drawString(printString, 25, y);
                y = y + fontSize * 3 / 2;
            }


            fontSize = 120;
            font = Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource(maoFontFileClassPath).getInputStream());
            // 字体和字体大小
            font = font.deriveFont(Font.PLAIN, fontSize);
            fontGraphics.setFont(font);
            y = y + 100;

            String footer = "不止极客";
            fontGraphics.drawString(footer,
                    310 - Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(footer) / 2,
                    y);
            y = y + 66;


//            fontSize = 22;
//            font = font.deriveFont(Font.PLAIN, fontSize);
//            fontGraphics.setFont(font);
//            footer = "https://notgeek.cn";
//            fontGraphics.drawString(footer,
//                    310 - Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(footer) / 2,
//                    y);
//            y = y + 22;

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
        String mingYan = "我知道我不知道";
//        mingYan = "罗翔老师说：\"如果你整天不读书，不运动，不节制消费，不反省自律，无兴趣、无爱好、无目标、无期望，生活是不会变好的。\" 这并非空洞的哲学，而是切实的智慧。";

        mingYan = "事实，是独立于人的判断的客观存在。观点，是我们对一个事实的看法。立场，是被位置和利益影响的观点。信仰，是一套完全自洽的逻辑体系。当一个人“屁股决定脑袋”的时候，你应该做的事情，是对他说：it's good for you. 反过来，我们也要时刻反省自己。我说的话，我的表达，是事实，还是观点，还是立场，还是信仰？-- 刘润。      我觉得他说得对，是立场。";


        String domainStaticPath = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "static"
                + File.separator + "domain";

        String saveFilePath = domainStaticPath
                + File.separator + "out"
                + File.separator + "ownerposter.png";
        LOGGER.debug("saveFilePath: " + saveFilePath);

        BufferedImage buffImg = overlyingImageTest(mingYan);
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
