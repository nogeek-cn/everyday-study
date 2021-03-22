package com.darian.printNumberAndLetters.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintNumberAndLetters_Condition {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new PrintNumber(lock, condition).start();
        new PrintChars(lock, condition).start();
    }

}

class PrintNumber extends Thread {
    private final Lock lock;

    private final Condition condition;

    PrintNumber(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
        setName("PrintNumber");
    }

    @Override
    public void run() {
        lock.lock();
        for (int i = 0; i < 26; i++) {
            System.out.print(i + 1);
            condition.signal();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }
}

class PrintChars extends Thread {
    private final Lock      lock;
    private final Condition condition;

    PrintChars(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
        setName("PrintChars");
    }

    @Override
    public void run() {
        lock.lock();
        for (int i = 0; i < 26; i++) {
            System.out.println((char) ('A' + i));
            condition.signal();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }
}