package com.darian.waitnotify;

import java.util.LinkedList;
import java.util.Queue;

public class WaitNotifyProducerAndConsumerDemo {
    public static void main(String[] args) {
        Queue<String> stringQueue = new LinkedList<>();
        ProducerThread producerThread = new ProducerThread(stringQueue, 5);
        ConsumerThread consumerThread = new ConsumerThread(stringQueue);

        producerThread.start();
        consumerThread.start();
    }
}

class ProducerThread extends Thread {

    private final Queue<String> stringQueue;

    private final int maxSize;

    ProducerThread(Queue<String> stringQueue, int maxSize) {
        this.stringQueue = stringQueue;
        this.maxSize = maxSize;
        setName("ProducerThread");
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            synchronized (stringQueue) {
                while (stringQueue.size() >= maxSize) {
                    try {
                        stringQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String generateMsg = "生产消息" + (i++);
                System.out.println(String.format("[%s]:[%s]",
                        Thread.currentThread().getName(), generateMsg));
                stringQueue.add(generateMsg);
                stringQueue.notify();
            }
        }
    }
}

class ConsumerThread extends Thread {
    private final Queue<String> stringQueue;

    ConsumerThread(Queue<String> stringQueue) {
        this.stringQueue = stringQueue;
        setName("ConsumerThread");
    }

    @Override
    public void run() {
        while (true) {
            int i = 0;
            synchronized (stringQueue) {
                while (stringQueue.isEmpty()) {
                    try {
                        stringQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String getMsg = stringQueue.remove();

                System.out.println(String.format("[%s]:消费到消息[%s]",
                        Thread.currentThread().getName(), getMsg));
                stringQueue.notify();
            }
        }
    }
}