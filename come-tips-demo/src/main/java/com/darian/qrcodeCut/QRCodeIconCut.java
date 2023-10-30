package com.darian.qrcodeCut;

import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/10/27  16:21
 */
public class QRCodeIconCut {


    private static String RESOURCES_PATH = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources";

    public static void main(String[] args) throws IOException {
        String domainStaticPath = RESOURCES_PATH + File.separator
                + "static" + File.separator
                + "domain";

        String saveFilePath = domainStaticPath + File.separator
                + "qr" + File.separator
                + "10.png";

        String sourceFileClassPath = "static" + File.separator +
                "domain" + File.separator +
                "qr" + File.separator
                + "10.png";

        String iconFileClassPath = "static" + File.separator +
                "domain" + File.separator +
                "qr" + File.separator
                + "icon.png";


        BufferedImage bufferImage = ImageIO.read(new ClassPathResource(sourceFileClassPath).getInputStream());

        Graphics2D fontGraphics = bufferImage.createGraphics();


        BufferedImage logBufferedImage = ImageIO.read(new ClassPathResource(iconFileClassPath).getInputStream());

        fontGraphics.drawImage(logBufferedImage,
                (bufferImage.getWidth() - 115) / 2,
                (bufferImage.getHeight() - 115) / 2,
                115,
                115,
                null);


        // 第一个字段是 宽度
        Stroke dash = new BasicStroke(10f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.5f, null, 0f);
        fontGraphics.setStroke(dash);
        fontGraphics.setColor(Color.white);

        int rectWidth = 120; //矩形宽
        int rectHeight = 120; //矩形高度
        double arcw = 10; //角弧宽度
        double arch = 10; //角弧高度
        fontGraphics.draw(new RoundRectangle2D.Double(
                //矩形起始坐标x  从左上角
                (bufferImage.getWidth() - 120) / 2,
                //矩形起始坐标y  从左上角
                (bufferImage.getHeight() - 120) / 2,
                rectWidth,
                rectHeight,
                arcw,
                arch));


        fontGraphics.dispose();


        int temp = saveFilePath.lastIndexOf(".") + 1;
        File outFile = new File(saveFilePath);
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        ImageIO.write(bufferImage, saveFilePath.substring(temp), outFile);
        System.out.println("save ....");
    }
}
