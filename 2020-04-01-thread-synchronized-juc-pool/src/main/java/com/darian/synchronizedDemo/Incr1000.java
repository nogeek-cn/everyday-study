package com.darian.synchronizedDemo;

import java.util.concurrent.TimeUnit;

public class Incr1000 {

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new IncrThread().start();
        }
        SleepUtils.sleep_MILLISECONDSs(3000);
        System.out.println(IncrThread.count);
    }
}

class IncrThread extends Thread {
    public static int count;

    @Override
    public void run() {
        incr();
    }

    public synchronized static void incr() {
        SleepUtils.sleep_MILLISECONDSs(1);
        count++;
    }


}

class SleepUtils {
    public static void sleep_MILLISECONDSs(long seconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

