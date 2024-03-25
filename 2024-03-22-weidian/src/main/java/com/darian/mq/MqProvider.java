package com.darian.mq;

import com.darian.mq.balance.brokerQueue.MqBrokerQueueBalance;
import com.darian.mq.balance.brokerQueue.RandomMqBrokerQueueBalance;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/3/23  22:31
 */
public class MqProvider {

    private MqBroker mqBroker;

    private MqBrokerQueueBalance mqBrokerQueueBalance;

    public MqProvider(MqBroker mqBroker) {
        this.mqBroker = mqBroker;
        RandomMqBrokerQueueBalance randomMqBrokerQueueBalance = new RandomMqBrokerQueueBalance();
        this.mqBrokerQueueBalance = randomMqBrokerQueueBalance;
    }

    public MqProvider(MqBroker mqBroker, MqBrokerQueueBalance mqBrokerQueueBalance) {
        this.mqBroker = mqBroker;
        this.mqBrokerQueueBalance = mqBrokerQueueBalance;
    }


    public void sendMsq(MqMsg mqMsg) {
        List<LinkedBlockingQueue<MqMsg>> queueList = mqBroker.getQueueList();
        int queueIndex = mqBrokerQueueBalance.getBrokerQueue(queueList);

        mqMsg.setQueueIndex(queueIndex);
        queueList.get(queueIndex).add(mqMsg);

    }
}
