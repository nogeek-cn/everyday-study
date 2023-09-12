package com.darian.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/9/11  16:56
 */
public class ZhiTanTest {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir")
                        + File.separator + "src"
                        + File.separator + "main"
                        + File.separator + "resources"
                        + File.separator + "chromedriver");
        WebDriver webDriver = new ChromeDriver();
        // 最大化
        webDriver.manage().window().maximize();

        Actions actions = new Actions(webDriver);
        webDriver.get("https://www.ztan.art/h5/#/pages/home/tologin?goback=1");
        actions.pause(1000);
        webDriver.findElement(By.className("c-login")).click();

        List<WebElement> elements = webDriver.findElements(By.className("uni-input-input"));

        WebElement phone = elements.get(0);
        phone.click();
        phone.sendKeys("11111");

        TimeUnit.SECONDS.sleep(1);

        WebElement password = elements.get(1);
        password.click();
        password.sendKeys("xxxxxxx");
        TimeUnit.SECONDS.sleep(3);


        webDriver.findElement(By.className("to-login")).click();
        TimeUnit.SECONDS.sleep(2);

        webDriver.get("https://www.ztan.art/h5/#/pages/chat/tag?id=144");
        TimeUnit.SECONDS.sleep(1);


        WebElement element = webDriver.findElement(By.className("add-btn"));
        element.click();
        TimeUnit.SECONDS.sleep(1);


        webDriver.findElement(By.className("uni-textarea-textarea"))
                .sendKeys("bv 起飞啦，准备好子弹，马上进行上车，上车上车。加油，起飞");
        TimeUnit.SECONDS.sleep(1);


        webDriver.findElement(By.className("s-pub"))
                        .click();
        TimeUnit.SECONDS.sleep(10);
        webDriver.quit();
    }
}
