package com.darian.qrgpt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.darian.ownposter.OwnerPosterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/10/24  18:17
 */
public class QRGptMain {

    private static String RESOURCES_PATH = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources";

    private static Logger LOGGER = LoggerFactory.getLogger(QRGptMain.class);

    public static void main(String[] args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> body = new HashMap<>();
        body.put("url", "https://nogeek.cn");
        body.put("prompt", "Stacks of gold coins");


        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(
                "https://www.qrgpt.io/api/generate",
                body,
                String.class
        );
//        System.out.println(stringResponseEntity);
        JSONObject bodyObj = JSON.parseObject(stringResponseEntity.getBody());
        String imageUrl = bodyObj.getString("image_url");
        System.out.println(imageUrl);

        BufferedImage img = ImageIO.read(new URL(imageUrl));


        String domainStaticPath = RESOURCES_PATH
                + File.separator + "static"
                + File.separator + "domain";
        String saveFilePath = domainStaticPath
                + File.separator + "out"
                + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + ".png";

        generateSaveFile(img, saveFilePath);
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
}
