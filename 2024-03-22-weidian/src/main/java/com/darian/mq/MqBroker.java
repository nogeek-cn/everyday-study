package com.darian.mq;

import com.darian.mq.balance.consumer.MqConsumerBalance;
import com.darian.mq.balance.consumer.RandomMqConsumerBalance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/3/23  22:30
 */
public class MqBroker {

    private List<LinkedBlockingQueue<MqMsg>> queueList;

    private List<MqConsumer> mqConsumerList;

    private MqConsumerBalance mqConsumerBalance;

    public MqBroker() {
        List<LinkedBlockingQueue<MqMsg>> queueList = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            queueList.add(new LinkedBlockingQueue<>());
        }
        MqConsumerBalance mqConsumerBalance = new RandomMqConsumerBalance();
        this.queueList = queueList;
        this.mqConsumerList = new ArrayList<>();
        this.mqConsumerBalance = mqConsumerBalance;
    }

    public MqBroker(List<LinkedBlockingQueue<MqMsg>> queueList,
                    MqConsumerBalance mqConsumerBalance) {
        this.queueList = queueList;
        this.mqConsumerList = new ArrayList<>();
        this.mqConsumerBalance = mqConsumerBalance;
    }



    public void addMqConsumer(MqConsumer consumer) {
        this.mqConsumerList.add(consumer);
    }
    public void startSend() {
        ExecutorService executorService = Executors.newFixedThreadPool(queueList.size());
        for (LinkedBlockingQueue<MqMsg> mqMsgs : queueList) {
            executorService.execute(() -> {
                while (true) {
                    MqMsg mqMsg = mqMsgs.peek();
                    if (mqMsg == null) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    sendToConsumer(mqMsg);
                }
            });
        }
    }




    private void sendToConsumer(MqMsg mqMsg) {
        MqConsumer consumer = mqConsumerBalance.getConsumer(mqConsumerList);
        consumer.consumerOneMsq(mqMsg);
    }


    public void ack(MqMsg mqMsg) {
        System.out.println("MqBroker ack " + mqMsg);
        int queueIndex = mqMsg.getQueueIndex();
        LinkedBlockingQueue<MqMsg> mqMsgQueue = queueList.get(queueIndex);
        mqMsgQueue.remove(mqMsg);
    }

    public List<LinkedBlockingQueue<MqMsg>> getQueueList() {
        return queueList;
    }
}
