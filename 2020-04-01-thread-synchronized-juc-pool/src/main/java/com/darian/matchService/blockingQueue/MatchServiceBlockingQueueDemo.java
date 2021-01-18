package com.darian.matchService.blockingQueue;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模仿 线程池里的 worker 的 实现，实现阻塞，等待
 */
public class MatchServiceBlockingQueueDemo {
    public static void main(String[] args) {
        MatchServiceBlockingQueueService matchServiceThreadAlwaysRun = new MatchServiceBlockingQueueService();
        new AddUserToQueueExecutorService(matchServiceThreadAlwaysRun).start();
    }
}

class AddUserToQueueExecutorService extends Thread {

    private AtomicInteger atomicInteger = new AtomicInteger();

    private ExecutorService addExecutorService = Executors.newSingleThreadExecutor();

    private final MatchServiceBlockingQueueService matchServiceThreadAlwaysRun;

    public AddUserToQueueExecutorService(
            MatchServiceBlockingQueueService matchServiceThreadAlwaysRun) {
        this.matchServiceThreadAlwaysRun = matchServiceThreadAlwaysRun;
    }

    private volatile int nextSleep_times = MatchServiceBlockingQueueService.max_wait_to_match_mill_seconds * 2;

    @Override
    public void run() {
        while (true) {
            addExecutorService.submit(() -> {
                int user_index = atomicInteger.incrementAndGet();
                String uuidToString = UUID.randomUUID().toString().replace("-", "");
                matchServiceThreadAlwaysRun.addUserToMatchQueue(new MatchUser(uuidToString));

                if (user_index % 3 == 1 || user_index % 3 == 2) {
                    nextSleep_times = MatchServiceBlockingQueueService.max_wait_to_match_mill_seconds / 2;
                } else {
                    nextSleep_times = MatchServiceBlockingQueueService.max_wait_to_match_mill_seconds * 2;
                }

            });
            try {
                TimeUnit.MILLISECONDS.sleep(nextSleep_times);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class MatchServiceBlockingQueueService {

    public static int max_wait_to_match_mill_seconds = 1000;

    private ExecutorService matchTaskExecutor = Executors.newSingleThreadScheduledExecutor();

    private ScheduledExecutorService monitorExecutorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * 最大容量 10000
     */
    private LinkedBlockingQueue<MatchUser> matchQueue = new LinkedBlockingQueue<>(10000);

    MatchServiceBlockingQueueService() {
        // 监控
        monitorExecutorService.scheduleAtFixedRate(() -> {
            System.out.println(String.format("[%s]matchQueue:size:%s",
                    Thread.currentThread().getName(),
                    matchQueue.size()));
            // 监控 分钟级监控
        }, 0, 1, TimeUnit.MINUTES);

        matchTaskExecutor.execute(() -> {
            while (true) {
                matchWorker();
            }
        });
    }

    /**
     * 将人给放入到匹配队列
     *
     * @param matchUser
     */
    public void addUserToMatchQueue(MatchUser matchUser) {
        if (matchQueue.size() > 100) {
            System.err.println("匹配人数过多");
        }
        matchUser.setAddQueueTime(System.currentTimeMillis());
        System.out.println(String.format("[%s][%s]加入队列...", Thread.currentThread().getName(), matchUser));
        matchQueue.add(matchUser);
    }

    private void matchWorker() {
        MatchUser firstUser = null;
        try {
            firstUser = matchQueue.take();
        } catch (InterruptedException e) {

            // 终止，重新复位，可以复位，可以不复位，
            Thread.interrupted();
        }
        if (Objects.nonNull(firstUser)) {
            MatchUser secondUser = null;
            try {
                secondUser = matchQueue.poll(MatchServiceBlockingQueueService.max_wait_to_match_mill_seconds, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                // 被中止，需要重新把 第一个人放到阻塞队列之中
                matchQueue.add(firstUser);

                // 终止，重新复位，可以复位，可以不复位，
                Thread.interrupted();
            }
            if (Objects.isNull(secondUser)) {
                secondUser = new MatchUser("机器人", System.currentTimeMillis());
            }
            sendToTwoPeople(firstUser, secondUser);
        }
    }

    private void sendToTwoPeople(MatchUser firstUser, MatchUser secondUser) {

        System.out.println(String.format("[%s][%s]匹配成功...发送成功消息...", Thread.currentThread().getName(), firstUser));
        System.out.println(String.format("[%s][%s]匹配成功...发送成功消息...", Thread.currentThread().getName(), secondUser));
    }
}

@Data
class MatchUser {
    private String userName;

    private long addQueueTime;

    public MatchUser(String userName) {
        this.userName = userName;
    }

    public MatchUser(String userName, long addQueueTime) {
        this.userName = userName;
        this.addQueueTime = addQueueTime;
    }
}