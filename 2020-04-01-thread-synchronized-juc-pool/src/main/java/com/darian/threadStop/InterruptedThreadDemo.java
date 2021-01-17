package com.darian.threadStop;

import java.util.concurrent.TimeUnit;

public class InterruptedThreadDemo {

    public static void main(String[] args) {
        SleepDemo sleepDemo = new SleepDemo();
        sleepDemo.start();

        sleep_seconds(1);
        sleepDemo.interrupt();
        //java.lang.InterruptedException: sleep interrupted
        //	at java.lang.Thread.sleep(Native Method)
        //	at java.lang.Thread.sleep(Thread.java:340)
        //	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        //	at com.darian.threadStop.SleepDemo.run(InterruptedThreadDemo.java:33)
    }

    private static void sleep_seconds(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            // JVM 层抛出的异常，用来响应异常
            e.printStackTrace();

            // 可以处理，可以复位，可以抛异常

            // 可以复位
            Thread.interrupted();
        }
    }
}

class SleepDemo extends Thread {
    public SleepDemo() {
        setName("SleepDemo");
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TimeUnit.SECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
