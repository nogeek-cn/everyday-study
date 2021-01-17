package com.darian.interrupt;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * interrupt() 对 WAITING、TIMED_WAITING 的线程都为抛出异常吗？
 * 否定的，
 */
public class InterruptThreadDemo {
    public static void main(String[] args) {

        // 对 park 住的线程执行 interrupt 方法
        interruptParkThread();

        // 对 wait 中的线程执行 interrupt 方法
        interruptWaitThread();

        // 对 wait time 中的线程执行 interrupt 方法
        interruptWaitTimeThread();

        // 对 sleep 中的线程执行 interrupt 方法
        interruptSleepThread();

        // 对 join 中的线程执行 interrupt 方法
        interruptJoinThread();

        //[main]:[RUNNABLE]				 interrupt ->[park-thread]: before [WAITING]
        //[main]:[RUNNABLE]				 interrupt ->[park-thread]: after [WAITING]
        //[main]:[RUNNABLE]				 interrupt ->[wait-thread]: before [WAITING]
        //[main]:[RUNNABLE]				 interrupt ->[wait-thread]: after [WAITING]
        //[wait-thread]:[RUNNABLE]			[interruptWaitThread][error]java.lang.InterruptedException
        //
        //[main]:[RUNNABLE]				 interrupt ->[wait-time-thread]: before [WAITING]
        //[main]:[RUNNABLE]				 interrupt ->[wait-time-thread]: after [WAITING]
        //[wait-time-thread]:[RUNNABLE]			[interruptWaitTimeThread][error]java.lang.InterruptedException
        //
        //[main]:[RUNNABLE]				 interrupt ->[sleep-thread]: before [TIMED_WAITING]
        //[main]:[RUNNABLE]				 interrupt ->[sleep-thread]: after [TIMED_WAITING]
        //[sleep-thread]:[RUNNABLE]			[interruptSleepThread][error]java.lang.InterruptedException: sleep interrupted
        //
        //[main]:[RUNNABLE]				 interrupt ->[join-thread]: before [WAITING]
        //[main]:[RUNNABLE]				 interrupt ->[join-thread]: after [WAITING]
        //[join-thread]:[RUNNABLE]			[interruptJoinThread][error]java.lang.InterruptedException
    }

    static void interruptThread(Thread thread) {
        thread.start();
        // 保证线程的状态已经 run 之后
        sleep_second(1);
        System.out.println(
                getCurrentThreadNameAndState() + "\t\t interrupt ->" + "[" + thread.getName() + "]: before [" + thread.getState() + "]");
        thread.interrupt();
        System.out.println(
                getCurrentThreadNameAndState() + "\t\t interrupt ->" + "[" + thread.getName() + "]: after [" + thread.getState() + "]");
    }

    static void sleep_second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void interruptParkThread() {
        interruptThread(getParkThread());
    }

    static void interruptWaitThread() {
        interruptThread(getWaitThread());
    }

    static void interruptSleepThread() {
        interruptThread(getSleepThread());
    }

    static void interruptJoinThread() {
        interruptThread(getJoinThread());
    }

    static void interruptWaitTimeThread() {
        interruptThread(getWaitTimeThread());
    }

    static Thread getParkThread() {
        return new Thread(() -> {
            try {
                LockSupport.park();
            } catch (Exception e) {
                System.out.println("\t[interruptParkThread][error]" + e + "\n");
            }
        }, "park-thread");
    }

    static Thread getWaitTimeThread() {
        return new Thread(() -> {
            Object lock = new Object();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (Exception e) {
                    System.out.println(getCurrentThreadNameAndState() + "\t[interruptWaitTimeThread][error]" + e + "\n");
                }
            }
        }, "wait-time-thread");
    }

    static Thread getWaitThread() {
        return new Thread(() -> {
            Object lock = new Object();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (Exception e) {
                    System.out.println(getCurrentThreadNameAndState() + "\t[interruptWaitThread][error]" + e + "\n");
                }
            }
        }, "wait-thread");
    }

    static Thread getJoinThread() {
        Thread sleepThread = getSleepThread();
        sleepThread.start();
        return new Thread(() -> {
            try {
                sleepThread.join();
            } catch (InterruptedException e) {
                System.out.println(getCurrentThreadNameAndState() + "\t[interruptJoinThread][error]" + e + "\n");
            }

        }, "join-thread");
    }

    static Thread getSleepThread() {
        return new Thread(() -> {
            try {
                TimeUnit.DAYS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(getCurrentThreadNameAndState() + "\t[interruptSleepThread][error]" + e + "\n");
            }
        }, "sleep-thread");
    }

    static String getCurrentThreadNameAndState() {
        return "[" + Thread.currentThread().getName() + "]:[" + Thread.currentThread().getState() + "]\t\t";
    }
}