package com.darian.nameFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class NameFactoryTest {

    // console out:

    //[ThreadMonitor-5][getMultiSpaceLine]cost:[643]ms
    //[ThreadMonitor-3][getMultiSpeeeLine]cost:[654]ms
    //[ThreadMonitor-2][getMultiSpaceLine]cost:[1176]ms
    //[ThreadMonitor-4][getMultiSpaceLine]cost:[1572]ms
    //[main]resultMap:[{longPulls=1.0, inBulls=2.0, shortBull=1.0, inBears=1.0}], cost:[1636]ms

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Map<String, Double> resultMap = new HashMap<>();

        CountDownLatch countDownLatch = new CountDownLatch(4);

        Future<Double> shortBullFeature = executorService.submit(() -> {
            Double shortBull = MultiSpaceLineUtils.getMultiSpaceLine("", 1L, 1, 1);
            countDownLatch.countDown();
            return shortBull;
        });

        Future<Double> inBullsFeature = executorService.submit(() -> {
            Double inBulls = MultiSpaceLineUtils.getMultiSpeeeLine("", 1L, 1, 1);
            countDownLatch.countDown();
            return inBulls;
        });

        Future<Double> inBearsFeature = executorService.submit(() -> {
            Double inBears = MultiSpaceLineUtils.getMultiSpaceLine("", 1L, 1, 1);
            countDownLatch.countDown();
            return inBears;
        });

        Future<Double> longPullsFeature = executorService.submit(() -> {
            Double longPulls = MultiSpaceLineUtils.getMultiSpaceLine("", 1L, 1, 1);
            countDownLatch.countDown();
            return longPulls;
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            resultMap.put("shortBull", shortBullFeature.get());
            resultMap.put("inBulls", inBullsFeature.get());
            resultMap.put("inBears", inBearsFeature.get());
            resultMap.put("longPulls", longPullsFeature.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(String.format("[%s]resultMap:[%s], cost:[%s]ms", Thread.currentThread().getName(), resultMap, (end - start)));
    }

    private static final ExecutorService executorService =
            new ThreadPoolExecutor(10,
                    50,
                    60L,
                    TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(),
                    new ThreadFactory() {
                        protected final AtomicInteger mThreadNum = new AtomicInteger(1);

                        @Override
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(r);
                            t.setDaemon(true);
                            t.setName("ThreadMonitor-" + mThreadNum.incrementAndGet());
                            return t;
                        }
                    });

}

class MultiSpaceLineUtils {
    public static Double getMultiSpaceLine(String tableName, long time, int numDay, int type) {
        long start = System.currentTimeMillis();
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            long end = System.currentTimeMillis();
            System.out.println(String.format("[%s][getMultiSpaceLine]cost:[%s]ms", Thread.currentThread().getName(), (end - start)));
        }

        return Double.valueOf(1);
    }

    @Deprecated
    public static Double getMultiSpeeeLine(String tableName, long time, int numDay, int type) {
        long start = System.currentTimeMillis();
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            long end = System.currentTimeMillis();
            System.out.println(String.format("[%s][getMultiSpeeeLine]cost:[%s]ms", Thread.currentThread().getName(), (end - start)));
        }
        return Double.valueOf(2);
    }
}

