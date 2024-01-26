package com.darian.skiplist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.*;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/1/26  15:36
 */
public class ContractMain {
    private static Logger LOGGER = LoggerFactory.getLogger(ContractMain.class);
    static int COUNT = 10;

    /**
     * 卖出。价格升序，时间升序
     */
    static volatile ConcurrentSkipListSet<SkipUtils.SetObject> SELL_LIST = new ConcurrentSkipListSet<>(SkipUtils.COMPARATOR_ASC);

    /**
     * 买入。价格降序，时间升序
     */
    static volatile ConcurrentSkipListSet<SkipUtils.SetObject> BUY_LIST = new ConcurrentSkipListSet<>(SkipUtils.COMPARATOR_DESC);


    public static void main(String[] args) throws Exception {
        new BuyListService().run();
        new SellListService().run();

        new OrderMerge().run();

        TimeUnit.SECONDS.sleep(10);
    }

    public static class OrderMerge {
        static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        public void run() throws Exception {
            while (true) {
                Future<?> submit = executor.submit(new MergeRunnable());
                submit.get();
            }
        }

        public static class MergeRunnable implements Callable<Boolean> {
            @Override
            public Boolean call() {
                if (BUY_LIST.isEmpty() || SELL_LIST.isEmpty()) {
                    return false;
                }
                SkipUtils.SetObject buyObject = BUY_LIST.pollFirst();
                SkipUtils.SetObject sellObject = SELL_LIST.pollFirst();
                if (Objects.nonNull(sellObject) && Objects.nonNull(buyObject)) {
                    if (buyObject.getPrice() >= sellObject.getPrice()) {
                        LOGGER.info(String.format("[sellObject][%s][buyObject][%s][成交]", sellObject, buyObject));
                        return true;
                    } else {
//                        LOGGER.warn(String.format("[sellObject][%s][buyObject][%s][无法成交]", sellObject, buyObject));
                        SELL_LIST.add(sellObject);
                        BUY_LIST.add(buyObject);
                    }
                }
                return false;
            }
        }
    }

    public static class BuyListService {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        public void run() {
            executor.scheduleAtFixedRate(() -> {
                // 每秒查询一下买单
                ConcurrentSkipListSet<SkipUtils.SetObject> concurrentSkipListSetDesc = new ConcurrentSkipListSet<SkipUtils.SetObject>(SkipUtils.COMPARATOR_DESC);
                for (int i = 0; i < COUNT; i++) {
                    concurrentSkipListSetDesc.add(ConcurrentSkipListSetMain.getObj(true));
                }
                BUY_LIST = concurrentSkipListSetDesc;
                LOGGER.info("BUY_LIST refresh");
            }, 1, 1, TimeUnit.SECONDS);
        }
    }

    public static class SellListService {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        public void run() {
            executor.scheduleAtFixedRate(() -> {
                // 每秒查询一下卖单
                ConcurrentSkipListSet<SkipUtils.SetObject> concurrentSkipListSetAsc = new ConcurrentSkipListSet<SkipUtils.SetObject>(SkipUtils.COMPARATOR_ASC);
                for (int i = 0; i < COUNT; i++) {
                    concurrentSkipListSetAsc.add(ConcurrentSkipListSetMain.getObj(false));
                }
                SELL_LIST = concurrentSkipListSetAsc;
                LOGGER.info("SELL_LIST refresh");
            }, 1, 1, TimeUnit.SECONDS);
        }
    }


}
