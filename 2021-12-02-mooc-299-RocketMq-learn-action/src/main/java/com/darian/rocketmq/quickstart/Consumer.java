package com.darian.rocketmq.quickstart;

import com.darian.rocketmq.constants.Const;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/***
 *
 * @author darian1996
 */
public class Consumer {

    // 1s 5s 10s 30s
    public static void main(String[] args) throws MQClientException, InterruptedException {
        String consumerGroup = "test_quick_consumer_group";
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);

        consumer.setNamesrvAddr(Const.NAMESRV_ADDR_SINGLE);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        String topic = Const.TEST_QUICK_TOPIC;
        consumer.subscribe(topic,
                "*");

        consumer.setConsumeThreadMax(1);

        // 设置最大的重试次数
        consumer.setMaxReconsumeTimes(3);
        // BROADCASTING("BROADCASTING"),
        //    CLUSTERING("CLUSTERING");
        // 默认
//        consumer.setMessageModel(MessageModel.CLUSTERING);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt messageExt = msgs.get(0);
                try {
                    String messageExtTopic = messageExt.getTopic();
                    String tags = messageExt.getTags();
                    String keys = messageExt.getKeys();
                    String msgBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);

                    if (ThreadLocalRandom.current().nextBoolean()) {
                        throw new RuntimeException("失败了 .... ... ");
                    }

                    System.out.println(String.format("[topic:%s][tags:%s][keys:%s][msgBody:%s]",
                            messageExtTopic, tags, keys, msgBody));
                } catch (Exception e) {
                    e.printStackTrace();
                    int reconsumeTimes = messageExt.getReconsumeTimes();

                    System.err.println(String.format("[%s][reconsumeTimes:%s]",
                            System.currentTimeMillis(), reconsumeTimes));
                    if (reconsumeTimes > 3) {
                        //  记录日志
                        //  做补偿处理
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();


        TimeUnit.MINUTES.sleep(30);

    }
}
