package com.darian.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/17  上午1:57
 */
public class AtomicIncrDemo {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new AtomicIncrThread().start();
        }

        Thread.sleep(1000);
        System.out.println(AtomicIncrThread.atomicInteger.get());
    }
}

class AtomicIncrThread extends Thread {

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void run() {
        atomicInteger.incrementAndGet();
    }
}
