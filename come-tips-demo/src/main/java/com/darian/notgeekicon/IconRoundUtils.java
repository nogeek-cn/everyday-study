package com.darian.notgeekicon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/11/30  18:18
 */
public class IconRoundUtils {

    public static void main(String[] args) throws Exception {

        int cornerRadius = 150;

        String domainStaticPath = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "static"
                + File.separator + "domain";

        String srcImageFile = domainStaticPath
                + File.separator + "out"
                + File.separator + "NotGeekAvatar.png";

        String outImageFile = domainStaticPath
                + File.separator + "out"
                + File.separator + "NotGeekAvatarRound.png";
        String affix = srcImageFile.substring(srcImageFile.length()-3);

        BufferedImage bi1 = ImageIO.read(new File(srcImageFile));

        // 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
        BufferedImage image = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1
                .getHeight());

        Graphics2D g2 = image.createGraphics();
        image = g2.getDeviceConfiguration().createCompatibleImage(bi1.getWidth(), bi1.getHeight(), Transparency.TRANSLUCENT);
        g2 = image.createGraphics();
        g2.setComposite(AlphaComposite.Clear);
        g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
        g2.setClip(shape);
        // 使用 setRenderingHint 设置抗锯齿
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0,bi1.getWidth(), bi1.getHeight(), cornerRadius, cornerRadius);
        g2.setComposite(AlphaComposite.SrcIn);
        g2.drawImage(bi1, 0, 0, bi1.getWidth(), bi1.getHeight(), null);
        g2.dispose();
        ImageIO.write(image, affix, new File(outImageFile));
    }
}
