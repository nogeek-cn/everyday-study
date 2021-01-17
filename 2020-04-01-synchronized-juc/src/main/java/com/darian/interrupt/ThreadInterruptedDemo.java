package com.darian.interrupt;

package com.darian.Interrupt;

import java.util.concurrent.TimeUnit;

public class ThreadInterrupteDemo {
    public static void main(String[] args) throws InterruptedException {
        interrupt2();
    }

    public static void interrupt2() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    // 抛出该异常，会将复位表示设置为 false
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.interrupt(); // 将复位表示设置位 true
        System.out.println("before:" + thread.isInterrupted());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("after:" + thread.isInterrupted());   // false
    }

    public static void interrupt1() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted) {
                    System.out.println("before:" + interrupted);
                    Thread.interrupted(); // 对线程进行复位，中断标识为 false
                    System.out.println("after:" + Thread.currentThread().isInterrupted());
                }
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt(); // 设置中断标识，中断标识为 true
    }
}
