package com.darian.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Path;
import java.sql.Time;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/***
 * https://googlechromelabs.github.io/chrome-for-testing/#stable
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/9/11  14:54
 */
public class SeleniumTest {


    public static void main(String[] args) throws InterruptedException {
        WebDriver webDriver = getDriver();
        // 最大化
        webDriver.manage().window().maximize();

        // 03-web元素定位ID
//        testElementId(webDriver);

//        04-Java自动化-name与class定位
//        testElementName(webDriver);

//        05-元素定位方式xpath，css
//        testXpath(webDriver);
//        testCss(webDriver);

//        06-actions模拟鼠标滑动事件
//        testActions(webDriver);

//        07-Action拖拽百度地图自动生成路线
//        testDraganDrop(webDriver);

//        08-浏览器窗口切换
//        testSwitchWindows(webDriver);

//      09-iframe窗口切换
//        testSwitchIframe(webDriver);

//        10-设置隐式等待时间
        testImplicitlyWait(webDriver);

//        11-设置显式等待
        testTimeOut(webDriver);

        TimeUnit.SECONDS.sleep(5);
        webDriver.close();
        webDriver.quit();
    }

    private static void testTimeOut(WebDriver webDriver) {
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        WebElement frame = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("layui-layer-iframe1")));


        webDriver.switchTo().frame(frame);
        webDriver.findElement(By.id("username")).sendKeys("33333333");
    }

    private static void testImplicitlyWait(WebDriver webDriver) {
        webDriver.get("https://www.juhe.cn");
        // 设置最长等待时间位 10s
        webDriver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);

        WebElement frame = webDriver.findElement(By.id("layui-layer-iframe1"));

        webDriver.switchTo().frame(frame);
        webDriver.findElement(By.id("username")).sendKeys("33333333");

    }


    private static void testSwitchIframe(WebDriver webDriver) {
        webDriver.get("https://login.anjuke.com/login/form");
        WebElement iframeLoginIfm = webDriver.findElement(By.id("iframeLoginIfm"));
        webDriver.switchTo().frame(iframeLoginIfm);

        webDriver.findElement(By.id("phoneIpt")).sendKeys("133344455");
    }




    private static void testElementId(WebDriver webDriver) {
        // 打开网页
        webDriver.get("https://baidu.com");

        webDriver.findElement(By.id("kw")).sendKeys("Hello world ");
        webDriver.findElement(By.id("su")).click();
    }

    private static void testElementName(WebDriver webDriver) {
        // 打开网页
        webDriver.get("https://baidu.com");

        webDriver.findElement(By.name("wd")).sendKeys("Hello world name");
        webDriver.findElement(By.className("s_btn")).click();
    }

    private static void testXpath(WebDriver webDriver) throws InterruptedException {
        webDriver.get("https://ctrip.com");
        TimeUnit.SECONDS.sleep(1);
        webDriver.findElement(By.xpath("//a@class=\"cui_nav_has\" and text()=\"酒店\"")).click();
    }

    private static void testCss(WebDriver webDriver) throws InterruptedException {
        webDriver.get("https://ctrip.com");
        TimeUnit.SECONDS.sleep(1);
        webDriver.findElement(By.xpath("li[id=\"cui_nav_hotel\"]>a[class=\"cui_nav_has\"]")).click();
    }


    private static void testActions(WebDriver webDriver) throws InterruptedException {
        Actions actions = new Actions(webDriver);
        webDriver.get("https://baidu.com");
        WebElement webElement = webDriver.findElement(By.linkText("更多"));
        // 移动到某个位之上上
        actions.moveToElement(webElement).pause(3000);


        WebElement mp3Link = webDriver.findElement(By.xpath("//a[@name=\"tj_mp3\"]"));

        actions.moveToElement(mp3Link).click().perform();

    }


    private static void testDraganDrop(WebDriver webDriver) {
        webDriver.get("https://map.baidu.com");
        WebElement mask = webDriver.findElement(By.id("mask"));
        Actions actions = new Actions(webDriver);
        // 右键，然后 暂停三秒
        actions.moveToElement(mask).contextClick().pause(3000);

        //
        WebElement cmitemStart = webDriver.findElement(By.id("cmitem_start"));

        actions.moveToElement(cmitemStart).click();
        // 点击并且保持住
        actions.clickAndHold();
        // 移动到
        actions.moveByOffset(200, 100);
        // 释放
        actions.release();

        actions.contextClick();

        actions.pause(3000);
        WebElement end = webDriver.findElement(By.id("cmitem_end"));
        actions.moveToElement(end).click();
        actions.perform();
    }


    private static void testSwitchWindows(WebDriver webDriver) {
        webDriver.get("https://baidu.com");

        webDriver.findElement(By.linkText("新闻")).click();

        Actions actions = new Actions(webDriver);
        String currentWindow = webDriver.getWindowHandle();

        Set<String> windowHandles = webDriver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            if (windowHandle != currentWindow) {
                webDriver.switchTo().window(windowHandle);
                actions.pause(1000);
            }
        }

        webDriver.findElement(By.id("ww")).sendKeys("helle world , charlk");
        webDriver.findElement(By.id("s_btn_wr")).click();

        actions.pause(1000);
        // 切换到第一个窗口
        webDriver.switchTo().window(currentWindow);
        webDriver.findElement(By.id("kw")).sendKeys("hahhahhahhha");
        webDriver.findElement(By.id("su")).click();

    }

    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir")
                        + File.separator + "src"
                        + File.separator + "main"
                        + File.separator + "resources"
                        + File.separator + "chromedriver");
        WebDriver webDriver = new ChromeDriver();
        return webDriver;
    }

}
