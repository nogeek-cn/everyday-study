package com.gupao.edu.vip.course.gctuning;


import com.gupao.edu.vip.course.gctuning.consumer.Consumer;
import com.gupao.edu.vip.course.gctuning.producer.Producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author baymax
 */
public class BlockingQueueTest {
 
    public static void main(String[] args) throws InterruptedException {
        // 声明一个缓存队列
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(100);



        // 借助Executors
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 500; i++) {
            Producer producer = new Producer(queue);
            service.execute(producer);
        }

        for(int i = 0; i < 100; i++){
            Consumer consumer = new Consumer(queue);
            service.execute(consumer);
        }




        // 执行10s
//        producer1.stop();
//        producer2.stop();
//        producer3.stop();
 
//        Thread.sleep(2000);
//        // 退出Executor
//        service.shutdown();
    }
}