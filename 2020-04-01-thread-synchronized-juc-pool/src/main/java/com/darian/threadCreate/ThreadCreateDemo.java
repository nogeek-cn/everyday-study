package com.darian.threadCreate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static com.darian.threadCreate.ThreadUtils.getThread;

public class ThreadCreateDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 继承 Thread
        ThreadThread threadThread = new ThreadThread();
        threadThread.start();

        // 带返回值的线程
        FutureTask<String> stringFutureTask = new FutureTask<>(new CallableThread());
        stringFutureTask.run();
        System.out.println(stringFutureTask.get());

        // 实现 Runnable
        Thread runnableThread = new Thread(new RunnableThread());
        runnableThread.start();
    }

}

class RunnableThread implements Runnable {
    @Override
    public void run() {
        System.out.println(getThread());
    }

}

class ThreadThread extends Thread {

    @Override
    public void run() {
        System.out.println(getThread());
    }
}

class CallableThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println(getThread());
        return "success";
    }
}

class ThreadUtils {
    public static String getThread() {
        return "[" + Thread.currentThread().getName() + "]";
    }
}