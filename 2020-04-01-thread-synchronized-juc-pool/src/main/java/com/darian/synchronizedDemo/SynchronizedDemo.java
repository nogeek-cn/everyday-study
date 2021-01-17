package com.darian.synchronizedDemo;

import org.openjdk.jol.info.ClassLayout;


public class SynchronizedDemo {

    // 锁的范围
    // 实例锁
    public static void main(String[] args) {
        Object lock = new Object();
        SynchronizedLock synchronizedLock1 = new SynchronizedLock(lock);
        SynchronizedLock synchronizedLock2 = new SynchronizedLock(lock);
        new Thread(() -> synchronizedLock1.doSomething(), "thread-1").start();
        new Thread(() -> synchronizedLock2.doSomething(), "thread-2").start();
    }
}

class SynchronizedLock {
    private final Object lock;

    SynchronizedLock(Object lock) {this.lock = lock;}

    public void doSomething() {
        synchronized (lock) {
            System.out.println(String.format("[%s][%s]doSomething()...",
                    System.currentTimeMillis(),
                    Thread.currentThread().getName()
                    )
            );

        }
    }
}

class SynchronizedUsed {

    synchronized void demo() {

    }

    synchronized static void demo_1() {

    }

    Object obj = new Object();

    void demo03() {
        synchronized (obj) {
            // 线程安全问题
        }
    }
}