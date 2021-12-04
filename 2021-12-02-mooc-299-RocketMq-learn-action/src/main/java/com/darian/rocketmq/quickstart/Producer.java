package com.darian.rocketmq.quickstart;

import com.darian.rocketmq.constants.Const;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/***
 *
 *  @author darian1996
 */
public class Producer {
    public static void main(String[] args)
            throws MQClientException,
            MQBrokerException,
            RemotingException,
            InterruptedException {
        // producerGroup
        String producerGroup = "test_quick_producer_name";
        producerGroup = "hlj-sale-center-producer";
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(Const.NAMESRV_ADDR_SINGLE);
        // rocketmq.remoting.exception.RemotingTooMuchRequestException: sendDefaultImpl call timeout
        producer.setSendMsgTimeout(60000);
        producer.start();

        // 主题
        String topic = "darian1996_test_quick_topic";
        // 标签
        String tag = "TagA";
        // 用户自定义的  key, 唯一标识
        String key = "";
        // 消息内容实体 byte[]
        String body = "Hello, rocketMQ";

        for (int i = 0; i < 5; i++) {
            // 创建消息
            Message message = new Message(topic,
                    tag,
                    key + "-" + i,
                    (body + "-" + i).getBytes());
            // 发送消息
            // 各种细粒度的异常
            SendResult sendResult = producer.send(message);
            System.err.println(sendResult);
        }

        producer.shutdown();
    }
}
