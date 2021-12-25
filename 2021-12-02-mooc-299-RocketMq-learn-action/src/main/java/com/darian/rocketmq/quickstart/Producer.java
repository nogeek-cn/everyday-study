package com.darian.rocketmq.quickstart;

import com.darian.rocketmq.constants.Const;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
        String topic = Const.TEST_QUICK_TOPIC;
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
            // 集成灰度等等
//            message.putUserProperty("gray-version", "version-1.2.0");
            // 发送消息
            // 各种细粒度的异常
            // 2.1 同步发送消息
            SendResult syncSendResult = producer.send(message);
            System.err.println(syncSendResult);

            // 2.2 异步发送消息
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult asyncSendResult) {
                    //
                    System.out.println(String.format("[msgId:%s][status:%s]",
                            asyncSendResult.getMsgId(), asyncSendResult.getSendStatus()));
                }

                @Override
                public void onException(Throwable e) {
                    e.printStackTrace();
                    System.err.println(" --- 发送失败：");
                }
            });

            // 4. 延迟消息
            // private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
            // 等级是 下标+1
            message.setDelayTimeLevel(1);
            SendResult delaySendResult = producer.send(message);
            System.err.println(delaySendResult);

            // 4. 指定队列

            SendResult selectQueueSendResult = producer.send(message, new MessageQueueSelector() {
                // mqs 就是 Topic 下的所有的队列
                // arg 就是 后边传进来的 send 的第三个参数 arg
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    MessageQueue messageQueue = mqs.get((Integer) (arg));
                    // todo: 队列选择器
                    return messageQueue;
                }
                // 就是 select 函数的 Object
            }, message.hashCode() % 3);

            System.err.println(selectQueueSendResult);
        }


        TimeUnit.MINUTES.sleep(5);
        producer.shutdown();
    }
}
