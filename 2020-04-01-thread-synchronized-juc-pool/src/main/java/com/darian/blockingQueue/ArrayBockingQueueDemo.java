package com.darian.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/***
 * 阻塞队列阻塞，实现线程的等待
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  下午1:44
 */
public class ArrayBockingQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        queue.add("x");
        queue.offer("x");
        try {
            queue.put("x");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //****************************************
        queue.remove();
        queue.peek();
        try {
            queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
