package com.darian.controller;

//运行主类
public class DeadLockDemo {
    public static void main(String[] args) {
        DeadLock t1 = new DeadLock(true);
        DeadLock t2 = new DeadLock(false);

        t1.start();
        t2.start();
    }
}

//定义锁对象
class MyLock {
    public static Object obj1 = new Object();
    public static Object obj2 = new Object();
}

//死锁代码
class DeadLock extends Thread {
    private boolean flag;

    DeadLock(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag) {
            while (true) {
                synchronized (MyLock.obj1) {
                    System.out.println(Thread.currentThread().getName() + " [Thread{true}] 获得 [obj1] 锁 ");
                    synchronized (MyLock.obj2) {
                        System.out.println(Thread.currentThread().getName() + " [Thread{true}] 获得 [obj2] 锁 ");
                    }
                }
            }
        } else {
            while (true) {
                synchronized (MyLock.obj2) {
                    System.out.println(Thread.currentThread().getName() + " [Thread{false}] 获得 [obj2] 锁 ");
                    synchronized (MyLock.obj1) {
                        System.out.println(Thread.currentThread().getName() + " [Thread{false}] 获得 [obj1] 锁 ");
                    }
                }
            }
        }
    }
}

