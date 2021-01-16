package com.darian.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  上午4:29
 */
public class LockAndLockInterruptiblyDemo {

    static Lock lock_lock = new ReentrantLock(true);

    static Lock lock_Interruptibly_lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {

        // 对 正在争抢 lock 的线程进行 interrupt
        interruptLock();

        // 对 正在争抢 lockInterruptibly 的线程  interrupt
        interruptLockInterruptibly();

        // 控制台打印：
        //[ThreadLockInterruptibly]:争抢锁异常：java.lang.InterruptedException
    }

    static void interruptLockInterruptibly() {
        ThreadLockInterruptibly threadLockInterruptibly1 = new ThreadLockInterruptibly();
        threadLockInterruptibly1.start();
        sleep_second(1);
        ThreadLockInterruptibly threadLockInterruptibly2 = new ThreadLockInterruptibly();
        threadLockInterruptibly2.start();

        threadLockInterruptibly2.interrupt();
    }

    static void interruptLock() {
        ThreadLock threadLock1 = new ThreadLock();
        threadLock1.start();

        sleep_second(1);
        ThreadLock threadLock2 = new ThreadLock();
        threadLock2.start();
        threadLock2.interrupt();
    }

    public static class ThreadLock extends Thread {
        @Override
        public void run() {
            lock_lock.lock();
            // 这里不解锁
        }
    }

    public static class ThreadLockInterruptibly extends Thread {
        @Override
        public void run() {
            try {
                lock_Interruptibly_lock.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println("[ThreadLockInterruptibly]:争抢锁异常：" + e);
            }
            // 这列不解锁
        }
    }

    static void sleep_second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
