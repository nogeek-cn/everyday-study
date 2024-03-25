package com.darian.mq.balance.consumer;

import com.darian.mq.MqConsumer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/3/23  22:43
 */
public class RandomMqConsumerBalance implements MqConsumerBalance {
    @Override
    public MqConsumer getConsumer(List<MqConsumer> consumerList) {
        return consumerList.get(
                ThreadLocalRandom.current().nextInt(0, consumerList.size()));
    }
}
