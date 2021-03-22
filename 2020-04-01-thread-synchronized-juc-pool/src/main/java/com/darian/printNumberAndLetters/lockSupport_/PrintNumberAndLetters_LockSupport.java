package com.darian.printNumberAndLetters.lockSupport_;

import java.util.concurrent.locks.LockSupport;

public class PrintNumberAndLetters_LockSupport {

    public static void main(String[] args) {
        PrintNumber printNumber = new PrintNumber();
        PrintLetters printLetters = new PrintLetters(printNumber);
        printNumber.setLockThread(printLetters);

        printNumber.start();
        printLetters.start();
    }
}

class PrintLetters extends Thread {
    public final Thread lockThread;

    PrintLetters(Thread lockThread) {
        this.lockThread = lockThread;
    }

    @Override
    public void run() {
        for (int i = 0; i < 26; i++) {
            System.out.print(i + 1);
            LockSupport.unpark(lockThread);
            LockSupport.park();
        }
    }
}

class PrintNumber extends Thread {

    public void setLockThread(Thread lockThread) {
        this.lockThread = lockThread;
    }

    public Thread lockThread;

    @Override
    public void run() {
        for (int i = 0; i < 26; i++) {
            LockSupport.park();
            System.out.println((char) ('A' + i));
            LockSupport.unpark(lockThread);
        }
    }
}