package com.darian.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/17  下午5:29
 */
public class ThreadPoolExecutorMonitor extends ThreadPoolExecutor {
    public ThreadPoolExecutorMonitor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        // 这里拿出来 traceId
        super.execute(() -> {
            // 这里设置 traceId
            command.run();
        });
    }

    @Override
    public Future<?> submit(Runnable task) {
        // 这里拿出来 traceId
        return super.submit(() -> {
            // 这里设置 traceId
            task.run();
        });
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        this.getActiveCount();
//        super.toString();
        // 这里可以打印，线
        System.out.println("beforeExecute");
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        System.out.println("afterExecute");
        super.afterExecute(r, t);
    }
}
