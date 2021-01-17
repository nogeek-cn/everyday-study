package com.darian.threadpool;

import org.springframework.ui.context.Theme;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/17  下午5:39
 */
public class ThreadPoolCallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<String> stringFuture = executorService.submit(new CallableThread());

        System.out.println(stringFuture.get());
    }
}

class CallableThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return String.format("[%s]call.......", Thread.currentThread().getName());
    }
}
