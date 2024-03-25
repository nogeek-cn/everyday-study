package com.darian.mq.balance.consumer;

import com.darian.mq.MqConsumer;

import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/3/23  23:58
 */
public class OrderlyMqConsumerBalance implements MqConsumerBalance{
    @Override
    public MqConsumer getConsumer(List<MqConsumer> consumerList) {
        return consumerList.get(0);
    }
}
