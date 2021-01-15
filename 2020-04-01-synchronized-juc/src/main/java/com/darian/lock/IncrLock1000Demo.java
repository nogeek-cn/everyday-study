package com.darian.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IncrLock1000Demo {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10; j++) {
                new Thread(() -> IncrLock1000.incr()).start();
            }
        }

        Thread.sleep(3000);
        System.out.println(IncrLock1000.count);
    }
}

class IncrLock1000 {
    public static int count = 0;

    // 可重入锁
    private static Lock lock = new ReentrantLock();

    public synchronized static void incr() {
        lock.lock();// 获得锁(互斥锁)
        count++;
        doubleIncr();
        lock.unlock(); // 释放锁
    }

    public static void doubleIncr() {
        lock.lock();// 再次争抢锁：不需要再次抢占所，而是只增加重入的次数
        count++;
        lock.unlock(); // 释放锁
    }
}