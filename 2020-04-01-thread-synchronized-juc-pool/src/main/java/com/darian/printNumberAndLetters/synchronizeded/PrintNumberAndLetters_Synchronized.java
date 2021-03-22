package com.darian.printNumberAndLetters.synchronizeded;

public class PrintNumberAndLetters_Synchronized {

    public static void main(String[] args) {
        Object lock = new Object();
        new PrintNumber(lock).start();
        new PrintLetters(lock).start();
    }
}

class PrintNumber extends Thread {

    private final Object lock;

    public PrintNumber(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            for (int i = 0; i < 26; i++) {
                System.out.print(i + 1);
                lock.notify();
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class PrintLetters extends Thread {
    private final Object lock;

    public PrintLetters(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            char charA = 'A';
            for (int i = 0; i < 26; i++) {
                System.out.println((char) (charA + i));
                lock.notify();
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

//-----------------
//-----------------