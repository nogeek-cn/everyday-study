package com.darian.mq.balance.brokerQueue;

import com.darian.mq.MqBroker;
import com.darian.mq.MqMsg;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/3/23  23:31
 */
public class RandomMqBrokerQueueBalance implements MqBrokerQueueBalance {
    @Override
    public int getBrokerQueue(List<LinkedBlockingQueue<MqMsg>> brokerQueueList) {
        return
                ThreadLocalRandom.current().nextInt(
                        0,
                        brokerQueueList.size()

                );
    }
}
