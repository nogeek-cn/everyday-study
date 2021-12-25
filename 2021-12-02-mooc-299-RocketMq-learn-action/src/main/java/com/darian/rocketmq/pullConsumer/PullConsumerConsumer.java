package com.darian.rocketmq.pullConsumer;

import com.darian.rocketmq.constants.Const;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/***
 *
 * @author darian1996
 */
public class PullConsumerConsumer {
    // key: 指定的队列， value: 为这个队列的拉取数据的最后位置
    private static final Map<MessageQueue, Long> OFFSE_TABLE = new HashMap();

    public static void main(String[] args) throws MQClientException {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("please_rename_unique_group_name_5");
        consumer.setNamesrvAddr(Const.NAMESRV_ADDR_SINGLE);
        consumer.start();

        // 从 TEST_PULL_CONSUMER_TOPIC 这个主题去获取所有的队列（默认会有4 个队列）
        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues(Const.TEST_PULL_CONSUMER_TOPIC);


        // 遍历所有的队列，
        for (MessageQueue mq : mqs) {
            System.out.printf("Consume from the queue: %s%n", mq);
            SINGLE_MQ:
            while (true) {
                try {
                    PullResult pullResult =
                            consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                    System.out.printf("%s%n", pullResult);
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            break SINGLE_MQ;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        consumer.shutdown();
    }

    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = (Long)OFFSE_TABLE.get(mq);
        return offset != null ? offset : 0L;
    }

    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        OFFSE_TABLE.put(mq, offset);
    }
}
