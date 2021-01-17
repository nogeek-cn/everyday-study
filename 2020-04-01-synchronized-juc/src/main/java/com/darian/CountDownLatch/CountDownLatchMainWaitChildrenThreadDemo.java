package com.darian.CountDownLatch;

import java.util.concurrent.CountDownLatch;

/***
 * 这个是主线程等待 一堆子线程任务结束
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  下午9:36
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        int countDownLatchCount = 10;
        // 计数器的个数
        CountDownLatch countDownLatch = new CountDownLatch(countDownLatchCount);

        for (int i = 0; i < countDownLatchCount; i++) {
            new CountDownLatchThread(i, countDownLatch).start();
        }

        System.out.println(String.format("[%s]before_await....", Thread.currentThread().getName()));
        // 等待信号量全部释放，实现线程池的并发等待
        countDownLatch.await();
        System.out.println(String.format("[%s]after_await....", Thread.currentThread().getName()));
    }
}

class CountDownLatchThread extends Thread {
    private final CountDownLatch countDownLatch;

    CountDownLatchThread(int i, CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
        setName("CountDownLatchThread-" + i);
    }


    @Override
    public void run() {
        System.out.println(String.format("[%s]run....", Thread.currentThread().getName()));
        countDownLatch.countDown();
    }
}


