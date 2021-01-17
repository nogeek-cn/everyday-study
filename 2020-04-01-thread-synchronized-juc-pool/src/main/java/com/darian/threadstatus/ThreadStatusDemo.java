package com.darian.threadstatus;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.darian.threadstatus.ThreadUtils.printNameAndState;
import static com.darian.threadstatus.ThreadUtils.sleepSecond;

/**
 * <p>Java 线程 6 种</p>
 * <p> 1. new </p>
 * <p> 2. runable : 就绪 运行 ，OS 调度 </p>
 * <p> 3. waiting </p>
 * <p> 4. time_waiting </p>
 * <p> 5. blocked </p>
 * <p> 6. terminated </p>
 *
 * <p></p>
 * <p> 操作系统 5 种 </p>
 *
 *
 *
 * <p></p>
 * jps
 * jstack 6552
 */
public class ThreadStatusDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SleepAnyTimeThread sleepAnyTimeThread = new SleepAnyTimeThread();
        sleepAnyTimeThread.start();

        WaitThread waitThread = new WaitThread();
        waitThread.start();

        BlockedDemo blockedDemo1 = new BlockedDemo(1);
        blockedDemo1.start();
        BlockedDemo blockedDemo2 = new BlockedDemo(2);
        blockedDemo2.start();

        sleepSecond(1);
        printNameAndState(sleepAnyTimeThread);
        printNameAndState(waitThread);
        printNameAndState(blockedDemo1);
        printNameAndState(blockedDemo2);

        //[SleepAnyTimeThread][TIMED_WAITING]
        //[WaitThread][WAITING]
        //[BlockedDemo-1][TIMED_WAITING]
        //[BlockedDemo-2][BLOCKED]
    }
}

class BlockedDemo extends Thread {
    public static Object synLock = WaitThread.class;

    public BlockedDemo(int i) {
        setName("BlockedDemo-" + i);
    }

    @Override
    public void run() {
        synchronized (synLock) {
            while (true) {
                sleepSecond(1000);
            }
        }
    }
}

class WaitThread extends Thread {

    public static Object synLock = WaitThread.class;

    public WaitThread() {
        setName("WaitThread");
    }

    @Override
    public void run() {
        synchronized (synLock) {
            try {
                synLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SleepAnyTimeThread extends Thread {
    public SleepAnyTimeThread() {
        setName("SleepAnyTimeThread");
    }

    @Override
    public void run() {
        while (true) {
            sleepSecond(100);
        }
    }
}

class ThreadUtils {
    public static void sleepSecond(long second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getThread() {
        return "[" + Thread.currentThread().getName() + "]";
    }

    public static void printNameAndState(Thread thread) {
        System.out.println(String.format("[%s][%s]", thread.getName(), thread.getState()));
    }
}