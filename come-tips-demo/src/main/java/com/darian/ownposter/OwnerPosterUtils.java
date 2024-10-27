package com.darian.ownposter;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/7/16  17:20
 */

public class OwnerPosterUtils {

    private static String RESOURCES_PATH = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources";

    private static Logger LOGGER = LoggerFactory.getLogger(OwnerPosterUtils.class);

    private static boolean isEnglish(Character character) {
        return (character >= 'a' && character <= 'z')
                || (character >= 'A' && character <= 'Z');
    }

    private static List<Character> FU_HAO_LIST = Arrays.asList('。', ';', '；', '，', '！', '？', '”', ',', '：');

    /**
     * Java 测试图片叠加方法
     */
    public static BufferedImage overlyingImageTest(boolean addUnsplashImg, String mingYan) {

        /**
         * 注意-产品要求：
         *          字体-自定义了一个字体
         *          大小-60
         *          颜色-148,0,0
         */

        String maoFontFileClassPath = "static" + File.separator + "domain" + File.separator + "maozidongziti.ttf";
        String hanSonFontFileClassPath = "static" + File.separator + "domain" + File.separator + "SourceHanSansCN-Bold.ttf";
        String pingFangFileClassPath = "static" + File.separator + "domain" + File.separator + "PingFangMedium.ttf";
        String sourceFileClassPath = "static" + File.separator + "domain" + File.separator + "mingYan.png";
        String noGeekCnDomainClassPath = "static" + File.separator + "domain" + File.separator + "out" + File.separator + "NoGeekCNDomain.png";


        LOGGER.debug("fontFileClassPath: " + maoFontFileClassPath);
        LOGGER.debug("sourceFileClassPath: " + sourceFileClassPath);


        try {
//            boolean addUnsplashImg = true;

            Font font = Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource(pingFangFileClassPath).getInputStream());
            BufferedImage bufferImage = ImageIO.read(new ClassPathResource(sourceFileClassPath).getInputStream());
            // 获取图片的宽度
            int imageWidth = bufferImage.getWidth();
            // 获取图片的高度
            int imageHeight = bufferImage.getHeight();
            LOGGER.debug(String.format("[bufferImage][imageWidth][%s][imageHeight][%s]", imageWidth, imageHeight));

            int fontSize = imageWidth / 25;
            int wenZiWeight = imageWidth * 90 / 100;

            // 字体和字体大小
            font = font.deriveFont(Font.PLAIN, fontSize);

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
            int y = fontSize * 5 / 2;


            if (addUnsplashImg) {
                // https://source.unsplash.com/1280x600/?technology

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<UnsplashImgResponse> forObject =
                        restTemplate.getForEntity("https://api.unsplash.com/photos/random/?client_id=hnk-fslShmHIGg8EE-IXCUVF25Bj9ubP6CIfFEIVb44"
                                        + "&query=compute, software, code",
//                                + "&query=white,technology",
                                UnsplashImgResponse.class);
                System.out.println(forObject);
                UnsplashImgResponse unsplashImgResponse = forObject.getBody();
                String posterCoverUrl = unsplashImgResponse.getUrls().getRaw() + "&w=1028&h=600";
                System.out.println(posterCoverUrl);
                BufferedImage postAvgBuffer = ImageIO.read(new URL(posterCoverUrl));
                fontGraphics.drawImage(postAvgBuffer,
                        0,
                        0,
                        imageWidth,
                        postAvgBuffer.getHeight(),
                        null);
                y = y + 600;
            }


            List<String> printStringList = new ArrayList<>();

            String[] mingYanSplit = mingYan.split("\n");

            for (String otherMingYan : mingYanSplit) {
                otherMingYan = otherMingYan.trim();
//                String otherMingYan = mingYan;
                while (true) {
                    int spilt = 0;
                    for (int i = 1; i <= otherMingYan.length(); i++) {

                        String beforeString = otherMingYan.substring(0, i);
                        int beforeStringWidth = Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(beforeString);
                        if (beforeStringWidth > wenZiWeight) {
                            break;
                        }
                        spilt = i;
                    }

                    List<Character> notfirstList = FU_HAO_LIST;
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
                    if (otherMingYanWeight <= wenZiWeight) {
                        if (Objects.nonNull(otherMingYan) && otherMingYan.length() > 1) {
                            printStringList.add(otherMingYan);
                        }
                        break;
                    }
                }
            }


            // x 左右，y 上下

            for (String printString : printStringList) {
                fontGraphics.drawString(printString, imageWidth / 20, y);
                y = y + fontSize * 3 / 2;
            }

            // 画一条分割线
            Stroke dash = new BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.5f, null, 0f);
            fontGraphics.setStroke(dash);
            fontGraphics.drawLine((imageWidth - wenZiWeight) / 2, y, wenZiWeight + ((imageWidth - wenZiWeight) / 2), y);

            y = y + fontSize * 3 / 2;

            fontSize = imageWidth / 7;
            font = Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource(hanSonFontFileClassPath).getInputStream());
            // 字体和字体大小
            font = font.deriveFont(Font.PLAIN, fontSize);
            fontGraphics.setFont(font);
            y = y + fontSize * 5 / 6;

            String footer = "不止极客";
            fontGraphics.drawString(footer,
                    imageWidth / 2 - Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(footer) / 2,
                    y);
            y = y + fontSize / 2;


//            fontSize = 22;
//            font = font.deriveFont(Font.PLAIN, fontSize);
//            fontGraphics.setFont(font);
//            footer = "https://notgeek.cn";
//            fontGraphics.drawString(footer,
//                    310 - Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(footer) / 2,
//                    y);
//            y = y + 22;


            // 写商标log
            String logImgPath = "static"
                    + File.separator + "domain"
                    + File.separator + "out"
                    + File.separator + "NotGeekAvatar.png";

            int logBufferedImageWidth = 185;
            int logBufferedImageHeight = 185;

            BufferedImage logBufferedImage = ImageIO.read(new ClassPathResource(logImgPath).getInputStream());
            fontGraphics.drawImage(logBufferedImage,
                    imageWidth / 24,
                    y - logBufferedImageHeight - fontSize / 3,
                    logBufferedImageWidth,
                    logBufferedImageHeight,
                    null);

//            String qrCodeImgPath = "static"
//                    + File.separator + "domain"
//                    + File.separator + "into"
//                    + File.separator + "notgeekCNQRCode.png";
//                    ImageIO.read(new ClassPathResource(qrCodeImgPath).getInputStream());

            BufferedImage qrCodeBufferedImage = getBufferedImage("http://nogeek.cn");
            int qrCodeBufferedImageWidth = 182;
            int qrCodeBufferedImageHeight = 182;
            fontGraphics.drawImage(qrCodeBufferedImage,
                    imageWidth - imageWidth / 24 - qrCodeBufferedImageWidth,
                    y - qrCodeBufferedImageHeight - fontSize / 3,
                    qrCodeBufferedImageWidth,
                    qrCodeBufferedImageHeight,
                    null);


            // nogeek 域名
//            int domainBannerFontSize = imageWidth / 50;
//            font = font.deriveFont(Font.PLAIN, domainBannerFontSize);
//            fontGraphics.setFont(font);
//            fontGraphics.setStroke(new BasicStroke(2.0f));
//            String domainBannerString = "https://nogeek.cn";
//            fontGradientPaint = new GradientPaint(
//                    domainBannerFontSize, 320, new Color(246, 170, 242),
//                    domainBannerFontSize + Toolkit.getDefaultToolkit().getFontMetrics(font).stringWidth(domainBannerString), 320, new Color(0, 251, 239));
//            fontGraphics.setPaint(fontGradientPaint);
//            fontGraphics.drawString(domainBannerString, domainBannerFontSize * 2, domainBannerFontSize * 3 / 2);
//
//            fontGradientPaint = new GradientPaint(
//                    0, 0, new Color(0, 0, 0),
//                    0, 320, new Color(0, 0, 0));
//            fontGraphics.setPaint(fontGradientPaint);
//            GlyphVector gv = font.createGlyphVector(fontGraphics.getFontRenderContext(), domainBannerString);
//            Shape shape = gv.getOutline();
//            fontGraphics.setStroke(new BasicStroke(2.0f));
//            fontGraphics.translate(domainBannerFontSize * 2, domainBannerFontSize * 3 / 2);
//            fontGraphics.draw(shape);

            fontGraphics.dispose();


            // 裁剪
            bufferImage = bufferImage.getSubimage(0, 0, imageWidth, y);
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
        mingYan = "“架构师的个人哲学与认知先于架构师所谈的架构原则。”去年就看到了这句话，今天才有一点感悟。我们通常是看到了他怎么做事的，再说他是一个什么样的人。而这个人为什么要这么做事的根因因为他是一个什么样的人。“个人哲学与认知”就是“他是什么样的人”；“架构原则”=“怎么做事”。有的人喜欢稳定有的人喜欢折腾其实是不同的人的个人哲学与认知的外化表现。";
        mingYan = "在每个案件里，你从不同的角度去看，可能都会得到完全不同的所谓主观真相，你所代表的立场不同，你所追求的主观真相和结果自然也有所不同，而案件本身的客观真相，永远也无法复原，或者说：我们永远无法得知在过去的某个时空里，这个案件到底是如何发生的？每个诉讼参与人所做的，无非就是通过一系列的证据和说理，尽全力，无限逼近客观真相。亦或是，无限逼近那个我方苦苦追求所期望的与我方利益或立场相符的真相，而这个无限逼近真相近身肉搏的过程，我们一般称之为“诉讼”。在这个过程中，有着无数的不确定性，这也正是诉讼两个字本身最大的魅力。 \n  -- 律师老韩";
        mingYan = "成年人的世界中，没有明确同意，就是不同意。 \n \n 这是潜规则，俗称成年人的必修课。";
        mingYan = "重新回顾自己当初走上程序员的阴差阳错，被调剂专业的被迫选择。\n很多人只看了大学毕业生大厂高薪工作，那种难度跟考清华北大的难度差不多。\n\n 其实很多人不适合程序员。更别提现在程序员越来越严峻的 35 岁危机。\n 我做程序员就没那么顺心，原因就是自己不“狗”，上不能当老板的“舔狗”，下不能当咬死牛马的“猎狗”。 \n\n 如果从人的一生去看，选一个值得一生去深耕的行业，医生未必不是一个好的职业，越老越吃香。门槛高也是优势。";
        mingYan = "        我的职业生涯就没那么顺心顺意，根本原因就是自己虽然“狼性足够”，但“狗性不足”，上不能当老板的“舔狗”，下不能当咬死牛马的“猎狗”。";
        mingYan = "勿以身贵而贱人，\n勿以独见而违众，\n勿以辩说为必然。\n" +
                "\n" +
                "（《六韬·龙韬·立将》）";
        mingYan = "本职工作没做完，回避自己的本职工作是自卑！" + "\n" +
                "本质工作没做完，对别人的工作指指点点是自负！" + "\n" + "\n" +
                "而职场中，自卑自负经常集于一身。";


        mingYan = "\n\n 技术、产品、管理、商业" + "\n"+ "\n"+ "\n"+ "\n"
                + "微码产品：https://mcode.net"+ "\n"
                + "软件方法：https://umlcn.com"+ "\n"
                + "不止极客：https://nogeek.cn"+ "\n";

//        boolean addUnsplashImg = true;
        boolean addUnsplashImg = false;
        ;

        String domainStaticPath = RESOURCES_PATH
                + File.separator + "static"
                + File.separator + "domain";

        String saveFilePath = domainStaticPath
                + File.separator + "out"
                + File.separator + "ownerposter.png";
        LOGGER.debug("saveFilePath: " + saveFilePath);

        BufferedImage buffImg = overlyingImageTest(addUnsplashImg, mingYan);
        // 输出水印图片
        generateSaveFile(buffImg, saveFilePath);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(buffImg, "png", os);
            byte[] fileByte = os.toByteArray();
            String imageBase64Str = "data:image/png;base64," + org.apache.commons.codec.binary.Base64.encodeBase64String(fileByte);
            System.out.println();
//            System.out.println(imageBase64Str);
            System.out.println();
        } catch (Exception e) {
            LOGGER.info("[DomainImageUtils.overlyingImageAndSaveTest]" + e.getMessage(), e);
        }
    }


    public static class UnsplashImgResponse implements Serializable {
        private UnsplashImgResponseURLS urls;

        public UnsplashImgResponseURLS getUrls() {
            return urls;
        }

        public void setUrls(UnsplashImgResponseURLS urls) {
            this.urls = urls;
        }
    }

    public static class UnsplashImgResponseURLS implements Serializable {
        private String raw;

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }
    }

    public static void main(String[] args) {
        overlyingImageAndSaveTest();
    }

    //CODE_WIDTH：二维码宽度，单位像素
    private static final int CODE_WIDTH = 400;
    //CODE_HEIGHT：二维码高度，单位像素
    private static final int CODE_HEIGHT = 400;
    //FRONT_COLOR：二维码前景色，0x000000 表示黑色
    private static final int FRONT_COLOR = 0xFFFFFF;
    //BACKGROUND_COLOR：二维码背景色，0xFFFFFF 表示白色
    //演示用 16 进制表示，和前端页面 CSS 的取色是一样的，注意前后景颜色应该对比明显，如常见的黑白
    private static final int BACKGROUND_COLOR = 0x000000;

    //核心代码-生成二维码
    private static BufferedImage getBufferedImage(String content) throws WriterException {
        //com.google.zxing.EncodeHintType：编码提示类型,枚举类型
        Map<EncodeHintType, Object> hints = new HashMap();
        //EncodeHintType.CHARACTER_SET：设置字符编码类型
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //EncodeHintType.ERROR_CORRECTION：设置误差校正
        //ErrorCorrectionLevel：误差校正等级，L = ~7% correction、M = ~15% correction、Q = ~25% correction、H = ~30% correction
        //不设置时，默认为 L 等级，等级不一样，生成的图案不同，但扫描的结果是一样的
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //EncodeHintType.MARGIN：设置二维码边距，单位像素，值越小，二维码距离四周越近
        hints.put(EncodeHintType.MARGIN, 1);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_HEIGHT, hints);
        BufferedImage bufferedImage = new BufferedImage(CODE_WIDTH, CODE_HEIGHT, BufferedImage.TYPE_INT_BGR);
        for (int x = 0; x < CODE_WIDTH; x++) {
            for (int y = 0; y < CODE_HEIGHT; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? FRONT_COLOR : BACKGROUND_COLOR);
            }
        }
        return bufferedImage;
    }
}
