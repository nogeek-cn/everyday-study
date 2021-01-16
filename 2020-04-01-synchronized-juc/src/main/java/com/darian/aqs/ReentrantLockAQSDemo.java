package com.darian.aqs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  上午4:29
 */
public class ReentrantLockAQSDemo {

    // 使用公平锁
    static ReentrantLock STATIC_REENTRANT_LOCK = new ReentrantLock(true);

    // 线程数量
    static int STATIC_THREAD_COUNT = 6;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(STATIC_THREAD_COUNT);
        CompletionService completionService = new ExecutorCompletionService(executorService);

        printActionReentrantLockToString("竞争开始状态");

        for (int i = 0; i < STATIC_THREAD_COUNT; i++) {
            completionService.submit(new DemoThread(), null);
        }

        int count = 0;
        while (count < STATIC_THREAD_COUNT) { // 等待所有任务完成
            if (completionService.poll() != null) {
                count++;
            }
        }
        printActionReentrantLockToString("竞争结束状态");
        executorService.shutdown();

        //[main           ] 	 竞争开始状态 	 aqs_state:[0] 	 aqs_eo_thread:[(null thread)] 	 aqs_list:[]
        //[pool-1-thread-1] 	 竞争到锁之前 	 aqs_state:[0] 	 aqs_eo_thread:[(null thread)] 	 aqs_list:[]
        //[pool-1-thread-1] 	 竞争到锁之后 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-1] 	 aqs_list:[]
        //[pool-1-thread-2] 	 竞争到锁之前 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-1] 	 aqs_list:[]
        //[pool-1-thread-5] 	 竞争到锁之前 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-1] 	 aqs_list:[(null thread),(pool-1-thread-2)]
        //[pool-1-thread-4] 	 竞争到锁之前 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-1] 	 aqs_list:[(null thread),(pool-1-thread-2:SIGNAL), (pool-1-thread-5)]
        //[pool-1-thread-6] 	 竞争到锁之前 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-1] 	 aqs_list:[(null thread),(pool-1-thread-2:SIGNAL), (pool-1-thread-5:SIGNAL), (pool-1-thread-4)]
        //[pool-1-thread-3] 	 竞争到锁之前 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-1] 	 aqs_list:[(null thread),(pool-1-thread-2), (pool-1-thread-5:SIGNAL), (pool-1-thread-4:SIGNAL), (pool-1-thread-6)]
        //[pool-1-thread-1] 	 竞争解锁以后 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-2] 	 aqs_list:[(null thread),(pool-1-thread-5:SIGNAL), (pool-1-thread-4:SIGNAL), (pool-1-thread-6:SIGNAL), (pool-1-thread-3)]
        //[pool-1-thread-2] 	 竞争到锁之后 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-2] 	 aqs_list:[(null thread),(pool-1-thread-5:SIGNAL), (pool-1-thread-4:SIGNAL), (pool-1-thread-6:SIGNAL), (pool-1-thread-3)]
        //[pool-1-thread-5] 	 竞争到锁之后 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-5] 	 aqs_list:[(null thread),(pool-1-thread-4:SIGNAL), (pool-1-thread-6:SIGNAL), (pool-1-thread-3)]
        //[pool-1-thread-2] 	 竞争解锁以后 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-5] 	 aqs_list:[(null thread),(pool-1-thread-4:SIGNAL), (pool-1-thread-6:SIGNAL), (pool-1-thread-3)]
        //[pool-1-thread-4] 	 竞争到锁之后 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-4] 	 aqs_list:[(null thread),(pool-1-thread-6:SIGNAL), (pool-1-thread-3)]
        //[pool-1-thread-5] 	 竞争解锁以后 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-4] 	 aqs_list:[(null thread),(pool-1-thread-6:SIGNAL), (pool-1-thread-3)]
        //[pool-1-thread-6] 	 竞争到锁之后 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-6] 	 aqs_list:[(null thread),(pool-1-thread-3)]
        //[pool-1-thread-4] 	 竞争解锁以后 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-6] 	 aqs_list:[(null thread),(pool-1-thread-3)]
        //[pool-1-thread-3] 	 竞争到锁之后 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-3] 	 aqs_list:[(null thread)]
        //[pool-1-thread-6] 	 竞争解锁以后 	 aqs_state:[1] 	 aqs_eo_thread:[pool-1-thread-3] 	 aqs_list:[(null thread)]
        //[pool-1-thread-3] 	 竞争解锁以后 	 aqs_state:[0] 	 aqs_eo_thread:[(null thread)] 	 aqs_list:[(null thread)]
        //[main           ] 	 竞争结束状态 	 aqs_state:[0] 	 aqs_eo_thread:[(null thread)] 	 aqs_list:[(null thread)]

    }

    public static class DemoThread extends Thread {

        /**
         * 争抢到锁
         * 暂停 3 s
         * 释放锁
         */
        @Override
        public void run() {
            printActionReentrantLockToString("竞争到锁之前");
            STATIC_REENTRANT_LOCK.lock();
            printActionReentrantLockToString("竞争到锁之后");
            sleep_seconds(2);
            STATIC_REENTRANT_LOCK.unlock();
            printActionReentrantLockToString("竞争解锁以后");

        }
    }

    private static void sleep_seconds(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印 actionString 的 ReentrantLock 的状态
     *
     * @param actionString
     */
    public static void printActionReentrantLockToString(String actionString) {
        String thisThreadName = getThreadName(Thread.currentThread());

        String reentrantLockToString = String.format("[%s] \t %s \t aqs_state:[%s] \t aqs_eo_thread:[%s] \t aqs_list:[%s]",
                thisThreadName,
                actionString,
                getReentrantLockState(STATIC_REENTRANT_LOCK),
                getReentrantLockExclusiveOwnerThread(STATIC_REENTRANT_LOCK),
                getReentrantLockAllThreadNameList(STATIC_REENTRANT_LOCK));
        System.out.println(reentrantLockToString);
    }

    public static String getReentrantLockExclusiveOwnerThread(ReentrantLock reentrantLock) {
        return getAQSExclusiveOwnerThread(getAQSByReentrantLock(reentrantLock));
    }

    public static Integer getReentrantLockState(ReentrantLock reentrantLock) {
        return getAQSState(getAQSByReentrantLock(reentrantLock));
    }

    public static AbstractQueuedSynchronizer getAQSByReentrantLock(ReentrantLock reentrantLock) {
        try {
            Field syncField = ReentrantLock.class.getDeclaredField("sync");
            syncField.setAccessible(true);
            AbstractQueuedSynchronizer abstractQueuedSynchronizer = (AbstractQueuedSynchronizer) syncField.get(reentrantLock);
            return abstractQueuedSynchronizer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAQSExclusiveOwnerThread(AbstractQueuedSynchronizer aqs) {
        try {
            Field exclusiveOwnerThreadField = AbstractOwnableSynchronizer.class.getDeclaredField("exclusiveOwnerThread");
            exclusiveOwnerThreadField.setAccessible(true);
            Thread exclusiveOwnerThread = (Thread) exclusiveOwnerThreadField.get(aqs);
            if (Objects.nonNull(exclusiveOwnerThread)) {
                return exclusiveOwnerThread.getName();
            }
            return "(null thread)";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Integer getAQSState(AbstractQueuedSynchronizer aqs) {
        try {
            Field stateField = AbstractQueuedSynchronizer.class.getDeclaredField("state");
            stateField.setAccessible(true);

            int state = (int) stateField.get(aqs);
            return state;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @return
     */
    public static String getReentrantLockAllThreadNameList(ReentrantLock reentrantLock) {
        return getAQSThreadNameList(getAQSByReentrantLock(reentrantLock));
    }

    /**
     * 拿到 AQS 中的所有节点的 threadNamList
     *
     * @param aqs
     * @return
     */
    public static String getAQSThreadNameList(AbstractQueuedSynchronizer aqs) {
        try {
            Field headField = AbstractQueuedSynchronizer.class.getDeclaredField("head");
            headField.setAccessible(true);

            Object head = headField.get(aqs);

            List<String> threadNameList = getAllThreadNameListByHead(head, new ArrayList<>());

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
    public static List<String> getAllThreadNameListByHead(Object node, List<String> threadNameList) {
        try {
            if (node == null) {
                return threadNameList;
            }
            threadNameList.add(getThread(node));

            Class<?> aClass = node.getClass();
            Field nextField = aClass.getDeclaredField("next");
            nextField.setAccessible(true);

            Object next = nextField.get(node);

            return getAllThreadNameListByHead(next, threadNameList);
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
            String threadName = getThreadName(thread);

            Field waitStatusField = aClass.getDeclaredField("waitStatus");
            waitStatusField.setAccessible(true);
            int waitStatus = (int) waitStatusField.get(node);
            String waitStatusStr = "";
            if (waitStatus == 1) {
                waitStatusStr = "CANCELLED";
            } else if (waitStatus == -1) {
                waitStatusStr = "SIGNAL";
            } else if (waitStatus == -2) {
                waitStatusStr = "CONDITION";
            } else if (waitStatus == -3) {
                waitStatusStr = "PROPAGATE";
            }
            String threadNameAndWaitStatus = Arrays.asList(threadName, waitStatusStr)
                    .stream()
                    .filter(str -> str != null && !"".equals(str))
                    .map(String::valueOf)
                    .collect(Collectors.joining(":", "(", ")"));
            return threadNameAndWaitStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getThreadName(Thread thread) {
        String thisThreadName = thread.getName();
        while (thisThreadName.length() < 15) {
            thisThreadName = thisThreadName + " ";
        }
        return thisThreadName;
    }

}