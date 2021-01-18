
package com.darian.matchService.oldThreadAlwaysRun;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 匹配程序的的线程一直 run() 和 sleep()
 */
public class MatchServiceThreadAlwaysRunDemo {

    public static void main(String[] args) {
        MatchServiceThreadAlwaysRunService matchServiceThreadAlwaysRun = new MatchServiceThreadAlwaysRunService();
        new AddUserToQueueExecutorService(matchServiceThreadAlwaysRun).start();
    }
}

class AddUserToQueueExecutorService extends Thread {

    private AtomicInteger atomicInteger = new AtomicInteger();

    private ExecutorService addExecutorService = Executors.newSingleThreadExecutor();

    private final MatchServiceThreadAlwaysRunService matchServiceThreadAlwaysRun;

    public AddUserToQueueExecutorService(MatchServiceThreadAlwaysRunService matchServiceThreadAlwaysRun) {
        this.matchServiceThreadAlwaysRun = matchServiceThreadAlwaysRun;
    }

    private volatile int nextSleep_times = MatchServiceThreadAlwaysRunService.max_wait_to_match_mill_seconds * 2;

    @Override
    public void run() {
        while (true) {
            addExecutorService.submit(() -> {
                int user_index = atomicInteger.incrementAndGet();
                String uuidToString = UUID.randomUUID().toString().replace("-", "");
                matchServiceThreadAlwaysRun.addUserToMatchQueue(new MatchUser(uuidToString));

                if (user_index % 3 == 1 || user_index % 3 == 2) {
                    nextSleep_times = MatchServiceThreadAlwaysRunService.max_wait_to_match_mill_seconds / 2;
                } else {
                    nextSleep_times = MatchServiceThreadAlwaysRunService.max_wait_to_match_mill_seconds * 2;
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

class MatchServiceThreadAlwaysRunService {

    public static int max_wait_to_match_mill_seconds = 1000;

    private ExecutorService matchTaskExecutor = Executors.newSingleThreadScheduledExecutor();

    private ScheduledExecutorService monitorExecutorService = Executors.newSingleThreadScheduledExecutor();

    private ConcurrentLinkedQueue<MatchUser> matchQueue = new ConcurrentLinkedQueue();

    MatchServiceThreadAlwaysRunService() {
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
        // 后来又实现了一个 Atomic，原子递增 queueSize()
        if (matchQueue.size() > 100) {
            System.err.println("匹配人数过多");
        }
        matchUser.setAddQueueTime(System.currentTimeMillis());
        System.out.println(String.format("[%s][%s]加入队列...", Thread.currentThread().getName(), matchUser));
        matchQueue.add(matchUser);
    }

    private void matchWorker() {
        boolean matchSuccess = false;

        MatchUser firstUser = matchQueue.poll();
        if (Objects.nonNull(firstUser)) {
            MatchUser secondUser = matchQueue.poll();
            if (Objects.nonNull(secondUser)) {
                matchSuccess = true;
                sendToTwoPeople(firstUser, secondUser);
            } else {
                // 第二个为空
                // 1000 ms 还没匹配上
                if (System.currentTimeMillis() - firstUser.getAddQueueTime() > MatchServiceThreadAlwaysRunService.max_wait_to_match_mill_seconds) {
                    // 匹配机器人
                    sendToTwoPeople(firstUser, new MatchUser("机器人", System.currentTimeMillis()));
                } else {
                    // 重新加到队列中
                    matchQueue.add(firstUser);
                }
            }
        }
        // 说明队列为 空
        if (!matchSuccess) {
            try {
                // 睡眠 300 ms
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                // 终止，重新复位
                Thread.currentThread().interrupt();
            }
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