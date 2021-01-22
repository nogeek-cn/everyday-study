package com.darian.checkLogStatus;

import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CheckLogStatusTest {

    static ExecutorService executorService = Executors.newCachedThreadPool();

    static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        doCheckLoginStatus();
    }

    public static void doCheckLoginStatus() {
        int count = 5000;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                oneRequest();
            });
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
    }


    public static void oneRequest() {
        System.out.println(Thread.currentThread().getName());
        String forObject = restTemplate.getForObject("http://localhost:8080/login/checkNewLoginStatus", String.class);
        System.out.println(forObject);
    }
}