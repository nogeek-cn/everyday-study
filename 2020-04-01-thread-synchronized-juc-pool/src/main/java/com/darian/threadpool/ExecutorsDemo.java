package com.darian.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/17  下午3:56
 */
public class ExecutorsDemo {
    public static void main(String[] args) {
        // 创建固定线程数量的线程池
        Executors.newFixedThreadPool(10);

        // 创建只有一个核心线程的线程池
        Executors.newSingleThreadExecutor();

        // 提供缓存的一个线程池
        Executors.newCachedThreadPool();

        // 创建一个可以延期执行的线程池，中间件用它实现心跳检测，定时任务
        Executors.newScheduledThreadPool(2);


        FixedThreadPoolUse.use();
    }
}

class FixedThreadPoolUse {

    public static void use() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new PoolOneThread());
        }
    }
}

class PoolOneThread extends Thread {

    @Override
    public void run() {
        System.out.println(String.format("[%s]run.......", Thread.currentThread().getName()));
    }
}