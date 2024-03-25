package com.darian.jvm;


import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.Consumer;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/3/20  18:00
 */
public class WeiDianMain {

    public static void main(String[] args) throws Exception {
//        testMemoryPool();

//        testMemoryPoolMultiple();
        runMQ();
    }

    private static void testMemoryPool() throws InterruptedException {
        MemoryPool memoryPool = new MemoryPool(1);
        // 写入一个字符串
        System.out.println(memoryPool.write("message1".getBytes()));
        // 超过限制写入不进去
        System.out.println(memoryPool.write("message2".getBytes()));
        // 释放出来一块内存，这块内存的值打印出来
        System.out.println(new String(memoryPool.take()));
        // 调整队列大小
        memoryPool.reSize(2);
        // 同时写入三个，有两个写入失败
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        executorService.submit(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(memoryPool.write("message3".getBytes()));
        });
        executorService.submit(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(memoryPool.write("message4".getBytes()));
        });
        executorService.submit(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(memoryPool.write("message5".getBytes()));
        });
        countDownLatch.countDown();
        // 上边会有一个添加失败

        TimeUnit.SECONDS.sleep(5);


        System.out.println(new String(memoryPool.take()));
        System.out.println(new String(memoryPool.take()));

        executorService.shutdown();

    }

    private static void testMemoryPoolMultiple() throws InterruptedException {
        MemoryPoolMultiple memoryPoolMultiple = new MemoryPoolMultiple(1, 1);
        boolean write = memoryPoolMultiple.write("xxxxxx".getBytes());
        System.out.println(new String(memoryPoolMultiple.take(0)));


        System.out.println(memoryPoolMultiple.listSize());
        // 扩容
        memoryPoolMultiple.addPool(1);
        System.out.println(memoryPoolMultiple.listSize());

        memoryPoolMultiple.reSize(60);
        for (int i = 0; i < 30; i++) {
            memoryPoolMultiple.write(UUID.randomUUID().toString().getBytes());
        }

        new Thread(() -> {
            for (int i = 1; i < 60; i++) {
                try {
                    byte[] take = memoryPoolMultiple.take(0);
                    System.out.println("[take][0][count][" + i + "][" + new String(take) + "]");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 1; i < 60; i++) {
                try {
                    byte[] take = memoryPoolMultiple.take(1);
                    System.out.println("[take][1][count][" + i + "][" + new String(take) + "]");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }


    private static void runMQ() throws InterruptedException {
        MemoryPoolMultiple memoryPoolMultiple = new MemoryPoolMultiple(4, 200);

        MQProducer mqProducer1 = new MQProducer(memoryPoolMultiple);
        MQProducer mqProducer2 = new MQProducer(memoryPoolMultiple);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        scheduler.scheduleAtFixedRate(() -> {
            mqProducer1.sendMsg("[mqProducer1][msg]" + UUID.randomUUID().toString());
            mqProducer2.sendMsg("[mqProducer2][msg]" + UUID.randomUUID().toString());
        }, 1, 900, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(() -> {
            mqProducer1.sendMsg("[mqProducer1][msg]" + UUID.randomUUID().toString());
            mqProducer2.sendMsg("[mqProducer2][msg]" + UUID.randomUUID().toString());
        }, 1, 1000, TimeUnit.MILLISECONDS);

        new MQConsumer("mqConsumer1", memoryPoolMultiple, 0, 1).receiveMsg();
        new MQConsumer("mqConsumer2", memoryPoolMultiple, 2).receiveMsg();
        new MQConsumer("mqConsumer3", memoryPoolMultiple, 2).receiveMsg();
        new MQConsumer("mqConsumer4", memoryPoolMultiple, 3).receiveMsg();


        TimeUnit.SECONDS.sleep(60);
    }
}


class MemoryPool {

    private final LinkedBlockingQueue<byte[]> pool;
    private int maxSize;

    public MemoryPool(int maxSize) {
        this.maxSize = maxSize;
        this.pool = new LinkedBlockingQueue<>();
    }

    /**
     * 申请一块内存并写入内存
     */
    public boolean write(byte[] mem) {
        if (pool.size() >= maxSize) {
            System.err.println("队列超过了最大长度");
            return false;
        }
        return pool.offer(mem);
    }

    /**
     * 释放一块内存
     */
    public byte[] take() throws InterruptedException {
        return pool.take();
    }

    public void reSize(int size) {
        if (maxSize < size) {
            maxSize = size;
        }
    }
}

class MemoryPoolMultiple {
    private ArrayList<MemoryPool> memoryPoolList;
    private int poolMaxSize;

    /**
     * @param multipleSize 多少个队列
     * @param poolMaxSize  队列长度
     */
    public MemoryPoolMultiple(int multipleSize, int poolMaxSize) {
        this.memoryPoolList = new ArrayList<MemoryPool>(multipleSize);
        this.poolMaxSize = poolMaxSize;
        for (int i = 0; i < multipleSize; i++) {
            this.memoryPoolList.add(new MemoryPool(poolMaxSize));
        }
    }

    public boolean write(byte[] mem) {
        int index = ThreadLocalRandom.current().nextInt(memoryPoolList.size());
        return memoryPoolList.get(index).write(mem);
    }

    public byte[] take(int index) throws InterruptedException {
        return memoryPoolList.get(index).take();
    }

    public byte[] take(int index, Consumer<byte[]> ack) throws InterruptedException {
        byte[] take = take(index);
        ack.accept(take);
        return take;
    }

    public void reSize(int poolMaxSize) {
        this.poolMaxSize = poolMaxSize;
        for (MemoryPool memoryPool : memoryPoolList) {
            memoryPool.reSize(poolMaxSize);
        }
    }

    public void addPool(int addSize) {
        for (int i = 0; i < addSize; i++) {
            memoryPoolList.add(new MemoryPool(poolMaxSize));
        }
    }

    public int listSize() {
        return memoryPoolList.size();
    }
}


class MQProducer {
    private MemoryPoolMultiple memoryPoolMultiple;

    public MQProducer(MemoryPoolMultiple memoryPoolMultiple) {
        this.memoryPoolMultiple = memoryPoolMultiple;
    }

    public boolean sendMsg(String msg) {
        return memoryPoolMultiple.write(msg.getBytes());
    }
}

class MQConsumer {

    private String name;
    private MemoryPoolMultiple memoryPoolMultiple;
    private int[] consumerIndex;

    private ThreadPoolExecutor executor;

    public MQConsumer(String name, MemoryPoolMultiple memoryPoolMultiple, int... consumerIndex) {
        this.name = name;
        this.memoryPoolMultiple = memoryPoolMultiple;
        this.consumerIndex = consumerIndex;
        this.executor = new ThreadPoolExecutor(
                consumerIndex.length,
                consumerIndex.length,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    public void receiveMsg() throws InterruptedException {
        for (int index : consumerIndex) {
            executor.execute(() -> {
                while (true) {
                    try {
                        byte[] take = memoryPoolMultiple.take(index, get -> {
                            System.out.println("ack:" + new String(get));
                        });

                        System.out.println(
                                String.format("[%s][%s][consumer][%s]",
                                        this.name,
                                        Thread.currentThread().getName(),
                                        new String(take)
                                )
                        );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    }
}