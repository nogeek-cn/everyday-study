package _2021_12_25_interview_concurrent_rel_queue;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/***
 *
 * @author darian1996
 */
@SpringBootApplication
public class ApplicationTest {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ApplicationTest.class);
        GoodService goodService = run.getBean(GoodService.class);

        goodService.subscribe(1L, 3090L);
        goodService.subscribe(1L, 3091L);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(goodService.getSubscribe(1L));
        System.out.println(goodService.getAllGoodSubscribe());
    }
}


interface GoodService {

    /**
     * 订阅
     *
     * @param goodId
     * @param userId
     */
    void subscribe(Long goodId, Long userId);

    /**
     * 返回单个商品的订阅总数
     *
     * @param goodId
     * @return
     */
    Long getSubscribe(Long goodId);


    /**
     * 返回所有商品的订阅总数
     *
     * @return
     */
    Long getAllGoodSubscribe();

}

@Service
class GoodServiceImpl implements GoodService {

    private final UserGoodProcessor userGoodProcessor;

    public GoodServiceImpl() {
        AllGoodSubProcessor allGoodSubProcessor = new AllGoodSubProcessor();
        allGoodSubProcessor.start();
        GoodSubProcessor goodSubProcessor = new GoodSubProcessor(allGoodSubProcessor);
        goodSubProcessor.start();
        UserGoodRelProcessor userGoodRelProcessor = new UserGoodRelProcessor(goodSubProcessor);
        userGoodRelProcessor.start();
        userGoodProcessor = userGoodRelProcessor;
    }

    @Override
    public void subscribe(Long goodId, Long userId) {
        if (!UserGoodContext.GOOD_SUB_HOLD.containsKey(goodId)) {
            throw new RuntimeException("非法商品Id:" + goodId);
        }

        Request request = new Request();
        request.setGoodId(goodId);
        request.setUserId(userId);

        userGoodProcessor.processorRequest(request);
    }

    @Override
    public Long getSubscribe(Long goodId) {
        return UserGoodContext.GOOD_SUB_HOLD.get(goodId);
    }

    @Override
    public Long getAllGoodSubscribe() {
        return UserGoodContext.All_GOOD_SUB.get();
    }
}

class UserGoodContext {

    public volatile static Set<UserGoodRel> USER_GOOD_REL_HOLD = new ConcurrentSkipListSet<>();

    // 转化为数据库存储时，行锁并发可以不考虑
    public static Map<Long, Long> GOOD_SUB_HOLD = new ConcurrentHashMap<>();

    public static AtomicLong All_GOOD_SUB = new AtomicLong(0);


    static {
        GOOD_SUB_HOLD.put(1L, 0L);
        GOOD_SUB_HOLD.put(2L, 0L);
        GOOD_SUB_HOLD.put(3L, 0L);
        GOOD_SUB_HOLD.put(4L, 0L);
        GOOD_SUB_HOLD.put(5L, 0L);
    }
}


interface UserGoodProcessor {

    /**
     * processor
     *
     * @param request
     */
    void processorRequest(Request request);
}

@RequiredArgsConstructor
class AllGoodSubProcessor
        extends Thread
        implements UserGoodProcessor {


    LinkedBlockingQueue<Request> linkedBlockingQueue = new LinkedBlockingQueue<>();


    @Override
    public void processorRequest(Request request) {
        linkedBlockingQueue.add(request);
    }


    @Override
    public void run() {
        while (true) {
            try {
                Request request = linkedBlockingQueue.take();
                System.out.println("[" + Thread.currentThread().getName() + "] " + "print Data:" + request);
                UserGoodContext.All_GOOD_SUB.incrementAndGet();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

@RequiredArgsConstructor
class GoodSubProcessor
        extends Thread
        implements UserGoodProcessor {

    LinkedBlockingQueue<Request> linkedBlockingQueue = new LinkedBlockingQueue<>();
    private final UserGoodProcessor nextProcess;

    @Override
    public void processorRequest(Request request) {
        linkedBlockingQueue.add(request);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = linkedBlockingQueue.take();

                System.out.println("[" + Thread.currentThread().getName() + "] " + "print Data:" + request);


                Long subCount = UserGoodContext.GOOD_SUB_HOLD.get(request.getGoodId());

                if (Objects.isNull(subCount)) {
                    System.out.println("[" + Thread.currentThread().getName() + "] " + "Good not contains" + request);
                    return;
                } else {
                    subCount++;
                    UserGoodContext.GOOD_SUB_HOLD.put(request.getGoodId(), subCount);
                    nextProcess.processorRequest(request);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

@RequiredArgsConstructor
class UserGoodRelProcessor
        extends Thread
        implements UserGoodProcessor {

    LinkedBlockingQueue<Request> linkedBlockingQueue = new LinkedBlockingQueue<>();
    private final UserGoodProcessor nextProcess;

    @Override
    public void processorRequest(Request request) {
        linkedBlockingQueue.add(request);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = linkedBlockingQueue.take();

                UserGoodRel userGoodRel = new UserGoodRel();
                userGoodRel.setGoodId(request.getGoodId());
                userGoodRel.setUserId(request.getUserId());

                System.out.println("[" + Thread.currentThread().getName() + "] " + "print Data:" + request);
                if (UserGoodContext.USER_GOOD_REL_HOLD.contains(userGoodRel)) {
                    System.out.println("[" + Thread.currentThread().getName() + "] " + "skip");
                    return;
                } else {

                    nextProcess.processorRequest(request);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


@Data
class Request {
    private Long goodId;

    private Long userId;
}


@Data
class Good {
    private Long id;
}


@Data
class UserGoodRel {
    private Long goodId;

    private Long userId;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserGoodRel) {
            return Objects.equals(goodId, ((UserGoodRel) obj).getGoodId())
                    && Objects.equals(userId, ((UserGoodRel) obj).getUserId());
        }
        return false;
    }
}