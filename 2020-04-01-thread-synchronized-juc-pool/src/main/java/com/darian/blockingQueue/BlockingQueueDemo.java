package com.darian.blockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  上午5:14
 */
public class BlockingQueueDemo {

    /**
     * max_size = Integer.Max_value
     */
    static BlockingQueue<String> stringBlockingQueue = new LinkedBlockingDeque<>();

    public static void main(String[] args) {
        stringBlockingQueue.add(""); // 满了就会报错，
        stringBlockingQueue.offer(""); // 返回成功状态
        try {
            stringBlockingQueue.put(""); // 阻塞式的设置这个值。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
