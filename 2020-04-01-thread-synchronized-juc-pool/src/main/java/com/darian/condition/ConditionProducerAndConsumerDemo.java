package com.darian.condition;


import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.darian.condition.ThreadUtils.getThreadName;

/***
 *
 * Condition 实现 生产者和消费者
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  上午4:33
 */
public class ConditionProducerAndConsumerDemo {

    static Queue<String> queue = new LinkedList<>();

    static ReentrantLock reentrantLock = new ReentrantLock(true);

    static Condition condition = reentrantLock.newCondition();

    static int maxSize = 3;

    public static void main(String[] args) throws InterruptedException {
        Producer producer = new Producer(queue, maxSize, reentrantLock, condition);
        Consumer consumer = new Consumer(queue, maxSize, reentrantLock, condition);

        producer.start();
        consumer.start();
        //[producer]生产消息：1
        //[producer]生产消息：2
        //[producer]生产消息：3
        //[consumer]消费者：生产者消息内容1
        //[consumer]消费者：生产者消息内容2
        //[consumer]消费者：生产者消息内容3
        //[consumer]消费者队列空了，先等待
        //[producer]生产消息：4
        //[producer]生产消息：5
        //[producer]生产消息：6
        //[producer]生产队列满了，先等待
        //[consumer]消费者：生产者消息内容4
        //[consumer]消费者：生产者消息内容5
        //[consumer]消费者：生产者消息内容6
        //[consumer]消费者队列空了，先等待
    }


}

class Producer extends Thread {
    private Queue<String> msgQueue;

    private int maxSize;

    private Lock lock;

    private Condition condition;

    public Producer(Queue<String> msgQueue, int maxSize, Lock lock, Condition condition) {
        this.msgQueue = msgQueue;
        this.maxSize = maxSize;
        this.lock = lock;
        this.condition = condition;
        setName("producer");
    }

    @Override
    public void run() {
        int i = 0;

        while (true) {
            i++;
            lock.lock();  // synchronized
            while (msgQueue.size() >= maxSize) {
                System.out.println(getThreadName() + "生产队列满了，先等待");
                try {
                    condition.await(); // 阻塞线程并释放锁 wait
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(getThreadName() + "生产消息：" + i);
            msgQueue.add("生产者消息内容" + i);
            condition.signal(); // 唤醒阻塞状态下的线程
            lock.unlock();
        }
    }
}

class Consumer extends Thread {
    private Queue<String> msgQueue;

    private int maxSize;

    private Lock lock;

    private Condition condition;

    public Consumer(Queue<String> msgQueue, int maxSize, Lock lock, Condition condition) {
        this.msgQueue = msgQueue;
        this.maxSize = maxSize;
        this.lock = lock;
        this.condition = condition;
        setName("consumer");
    }

    @Override
    public void run() {

        while (true) {
            lock.lock();
            if (msgQueue.isEmpty()) {
                System.out.println(getThreadName() + "消费者队列空了，先等待");
                try {
                    condition.await(); // 阻塞线程并释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(getThreadName() + "消费者：" + msgQueue.remove());
            condition.signal(); // 唤醒阻塞状态下的线程
            lock.unlock();
        }
    }
}

class ThreadUtils {
    public static String getThreadName() {
        return "[" + Thread.currentThread().getName() + "]";
    }
}