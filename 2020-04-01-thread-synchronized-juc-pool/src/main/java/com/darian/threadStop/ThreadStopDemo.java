package com.darian.threadStop;

import java.util.concurrent.TimeUnit;

public class ThreadStopDemo {
    public static void main(String[] args) {
        InterruptThreadDemo interruptThreadDemo = new InterruptThreadDemo();
        interruptThreadDemo.start();

        ThreadUtils.sleepSecond(1);
        interruptThreadDemo.interrupt();
    }
}

class InterruptThreadDemo extends Thread {

    private int i;

    public InterruptThreadDemo() {
        setName("InterruptThreadDemo");
    }

    @Override
    public void run() {
        // 表示中断标记
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(ThreadUtils.getThread() + (i++));
        }
    }
}

class ThreadUtils {
    public static void sleepSecond(long second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getThread() {
        return "[" + Thread.currentThread().getName() + "]";
    }

    public static void printNameAndState(Thread thread) {
        System.out.println(String.format("[%s][%s]", thread.getName(), thread.getState()));
    }
}
