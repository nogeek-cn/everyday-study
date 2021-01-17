package com.darian.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  下午12:19
 */
public class CallableDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        CallableThread callableThread = new CallableThread();

        Future<String> future = executorService.submit(callableThread);
        /***
         * 这里可以写其它的业务
         * 去写其它东西
         */
        String returnValue = future.get(); // 这个地方再阻塞
        System.out.println(returnValue);
        executorService.shutdown();
    }

}


/***
 * 当你想要异步的线程执行你的某一个逻辑，那么在这个运行结束以后
 * 我想要拿到子线程运行的结果
 */
class CallableThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "darain" + 1;
    }
}

