package com.darian.condition;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  上午4:31
 */
public class ConditionNodeListDemo {
    // 使用公平锁
    static ReentrantLock reentrantLock = new ReentrantLock(true);

    static Condition condition = reentrantLock.newCondition();

    // 线程数量
    static int threadCount = 6;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CompletionService completionService = new ExecutorCompletionService(executorService);

        String thisThreadName = Thread.currentThread().getName();

        for (int i = 0; i < threadCount; i++) {
            completionService.submit(new DemoThread(), null);
        }

        sleep_seconds(5);
        System.out.println("\n\n线程池线程都在 await() 中，现在开始唤醒线程\n\n");

        System.out.println("[" + thisThreadName + "]\t状态(signal(0))：\taqs_list:\t\t[ "
                + getAQSAllThreadNameList() + " ]");
        System.out.println("[" + thisThreadName + "]\t状态(signal(0))：\tcondition_list:\t[ "
                + getConditionAllThreadNameList() + " ]");

        for (int i = 0; i < threadCount; i++) {
            reentrantLock.lock();
            condition.signal();
            System.out.println("[" + thisThreadName + "]\t状态(signal(" + (i + 1) + "))：\taqs_list:\t\t[ "
                    + getAQSAllThreadNameList() + " ]");
            System.out.println("[" + thisThreadName + "]\t状态(signal(" + (i + 1) + "))：\tcondition_list:\t[ "
                    + getConditionAllThreadNameList() + " ]");
            reentrantLock.unlock();
            sleep_seconds(1);
        }

        int count = 0;
        while (count < threadCount) { // 等待所有任务完成
            if (completionService.poll() != null) {
                count++;
            }
        }
        executorService.shutdown();

        // 线程池线程都在 await() 中，现在开始唤醒线程
        //
        //
        //[main]	状态(signal(0))：	aqs_list:		[ (null thread) ]
        //[main]	状态(signal(0))：	condition_list:	[ pool-1-thread-4, pool-1-thread-6, pool-1-thread-1, pool-1-thread-2, pool-1-thread-5, pool-1-thread-3 ]
        //[main]	状态(signal(1))：	aqs_list:		[ (null thread), pool-1-thread-4 ]
        //[main]	状态(signal(1))：	condition_list:	[ pool-1-thread-6, pool-1-thread-1, pool-1-thread-2, pool-1-thread-5, pool-1-thread-3 ]
        //[pool-1-thread-4]		运行结束
        //[main]	状态(signal(2))：	aqs_list:		[ (null thread), pool-1-thread-6 ]
        //[main]	状态(signal(2))：	condition_list:	[ pool-1-thread-1, pool-1-thread-2, pool-1-thread-5, pool-1-thread-3 ]
        //[pool-1-thread-6]		运行结束
        //[main]	状态(signal(3))：	aqs_list:		[ (null thread), pool-1-thread-1 ]
        //[main]	状态(signal(3))：	condition_list:	[ pool-1-thread-2, pool-1-thread-5, pool-1-thread-3 ]
        //[pool-1-thread-1]		运行结束
        //[main]	状态(signal(4))：	aqs_list:		[ (null thread), pool-1-thread-2 ]
        //[main]	状态(signal(4))：	condition_list:	[ pool-1-thread-5, pool-1-thread-3 ]
        //[pool-1-thread-2]		运行结束
        //[main]	状态(signal(5))：	aqs_list:		[ (null thread), pool-1-thread-5 ]
        //[main]	状态(signal(5))：	condition_list:	[ pool-1-thread-3 ]
        //[pool-1-thread-5]		运行结束
        //[main]	状态(signal(6))：	aqs_list:		[ (null thread), pool-1-thread-3 ]
        //[main]	状态(signal(6))：	condition_list:	[  ]
        //[pool-1-thread-3]		运行结束
    }

    public static class DemoThread extends Thread {

        /**
         * 争抢到锁
         * 暂停 3 s
         * 释放锁
         */
        @Override
        public void run() {
            String thisThreadName = Thread.currentThread().getName();

            System.out.println("[" + thisThreadName + "]竞争到锁之前： \t aqs_list:\t\t\t[ " + getAQSAllThreadNameList() + " ]");
            System.out.println("[" + thisThreadName + "]竞争到锁之前： \t condition_list:\t[ " + getConditionAllThreadNameList() + " ]");
            reentrantLock.lock();
            System.out.println("[" + thisThreadName + "]竞争到锁之后： \t aqs_list:\t\t\t[ " + getAQSAllThreadNameList() + " ]");
            System.out.println("[" + thisThreadName + "]竞争到锁之后： \t condition_list:\t[ " + getConditionAllThreadNameList() + " ]");

            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("[" + Thread.currentThread().getName() + "]\t\t运行结束");
                reentrantLock.unlock();
            }
        }
    }

    public static void sleep_seconds(long second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拿到 Condition 中的所有节点的 threadNamList
     *
     * @return
     */
    public static String getConditionAllThreadNameList() {
        try {

            Field firstWaiterField = ConditionObject.class.getDeclaredField("firstWaiter");
            firstWaiterField.setAccessible(true);

            Object firstWaiter = firstWaiterField.get(condition);

            List<String> threadNameList = getAllConditionThreadNameListByHead(firstWaiter, new ArrayList<>());

            return threadNameList.stream().collect(Collectors.joining(", "));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 传入 AQS Node 拿到，从 node 开始到最后一位的 threadNameList
     *
     * @param node           起始节点
     * @param threadNameList
     * @return
     */
    public static List<String> getAllConditionThreadNameListByHead(Object node, List<String> threadNameList) {
        try {
            if (node == null) {
                return threadNameList;
            }
            threadNameList.add(getThread(node));

            Class<?> aClass = node.getClass();
            Field nextField = aClass.getDeclaredField("nextWaiter");
            nextField.setAccessible(true);

            Object next = nextField.get(node);

            return getAllConditionThreadNameListByHead(next, threadNameList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return threadNameList;

    }

    /**
     * 拿到 AQS 中的所有节点的 threadNamList
     *
     * @return
     */
    public static String getAQSAllThreadNameList() {
        try {
            Field syncField = ReentrantLock.class.getDeclaredField("sync");
            syncField.setAccessible(true);
            Object sync = syncField.get(reentrantLock);

            Field headField = AbstractQueuedSynchronizer.class.getDeclaredField("head");
            headField.setAccessible(true);

            Object head = headField.get(sync);

            List<String> threadNameList = getAllAQSThreadNameListByHead(head, new ArrayList<>());

            return threadNameList.stream().collect(Collectors.joining(", "));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 传入 AQS Node 拿到，从 node 开始到最后一位的 threadNameList
     *
     * @param node           起始节点
     * @param threadNameList
     * @return
     */
    public static List<String> getAllAQSThreadNameListByHead(Object node, List<String> threadNameList) {
        try {
            if (node == null) {
                return threadNameList;
            }
            threadNameList.add(getThread(node));

            Class<?> aClass = node.getClass();
            Field nextField = aClass.getDeclaredField("next");
            nextField.setAccessible(true);

            Object next = nextField.get(node);

            return getAllAQSThreadNameListByHead(next, threadNameList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return threadNameList;

    }

    /**
     * 根据 AQS.Node 拿到 Node 的 Thread
     *
     * @param node
     * @return
     */
    public static String getThread(Object node) {
        try {
            if (node == null) {
                return "(null node)";
            }
            Class<?> aClass = node.getClass();
            Field threadField = aClass.getDeclaredField("thread");
            threadField.setAccessible(true);

            Thread thread = (Thread) threadField.get(node);
            if (thread == null) {
                return "(null thread)";
            }
            return thread.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}