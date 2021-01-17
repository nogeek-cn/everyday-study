package com.darian.CountDownLatch;

import java.util.concurrent.CountDownLatch;

/***
 * 一堆子线程等待父线程运行完之后，在运行。
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  下午9:53
 */
public class CountDownLatchChildrenWaitMainThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new CountDownLatchWaitThread(i, countDownLatch).start();
        }

        Thread.sleep(1000);
        countDownLatch.countDown();
        System.out.println(String.format("[%s]countDown_after....", Thread.currentThread().getName()));
    }
}



class CountDownLatchWaitThread extends Thread {
    private final CountDownLatch countDownLatch;

    CountDownLatchWaitThread(int i, CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
        setName("CountDownLatchThread-" + i);
    }


    @Override
    public void run() {
        System.out.println(String.format("[%s]await_before....", Thread.currentThread().getName()));

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("[%s]await_after....", Thread.currentThread().getName()));
    }

}
