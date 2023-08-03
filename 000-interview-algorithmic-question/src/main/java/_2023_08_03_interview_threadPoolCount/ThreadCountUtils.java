package _2023_08_03_interview_threadPoolCount;

import ch.qos.logback.core.util.TimeUtil;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/8/3  16:52
 */
public class ThreadCountUtils {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CountThreadPoolExecutor executor = new CountThreadPoolExecutor(
                3,
                3,
                3,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(100000),
                new java.util.concurrent.ThreadPoolExecutor.AbortPolicy());

        int count = 10_0000;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            Future<?> submit = executor.submit(() -> {
                countDownLatch.countDown();
            });
//            System.out.println(submit.get());
        }


        countDownLatch.await();
        System.out.println();
        System.out.println(CountThreadPoolExecutor.CONCURRENT_HASH_MAP);

    }

    public static class CountThreadPoolExecutor extends ThreadPoolExecutor {


        public static ConcurrentHashMap<String, AtomicInteger> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();


        public CountThreadPoolExecutor(int corePoolSize,
                                       int maximumPoolSize,
                                       long keepAliveTime,
                                       TimeUnit unit,
                                       BlockingQueue<Runnable> workQueue,
                                       RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(() -> {
                task.run();
                String threadName = Thread.currentThread().getName();


                AtomicInteger count = CONCURRENT_HASH_MAP.getOrDefault(threadName, new AtomicInteger(0));
                count.incrementAndGet();
                CONCURRENT_HASH_MAP.put(threadName, count);
                return count;
            });
        }
    }

}
