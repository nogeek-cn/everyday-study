package com.darian.synchronizedDemo;

public class SynchronizedDemo {


    // 锁的范围
    // 实例锁
    public static void main(String[] args) {

    }

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