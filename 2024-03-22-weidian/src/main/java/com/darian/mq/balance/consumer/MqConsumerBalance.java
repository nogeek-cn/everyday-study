package com.darian.mq.balance.consumer;

import com.darian.mq.MqConsumer;

import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/3/23  22:42
 */
public interface MqConsumerBalance {
    MqConsumer getConsumer(List<MqConsumer> consumerList);
}
