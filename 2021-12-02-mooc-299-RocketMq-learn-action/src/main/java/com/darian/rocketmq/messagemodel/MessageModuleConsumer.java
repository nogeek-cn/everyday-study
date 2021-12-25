package com.darian.rocketmq.messagemodel;

import com.darian.rocketmq.constants.Const;
import org.apache.log4j.net.SocketNode;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
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
public class MessageModuleConsumer {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        System.out.println();

        DefaultMQPushConsumer consumer_1 = getConsumer("consumer_1");
        System.out.println("consumer1 start");

        DefaultMQPushConsumer consumer_2 = getConsumer("consumer_2");
        System.out.println("consumer2 start");


        TimeUnit.SECONDS.sleep( 10);
        consumer_1.shutdown();
        System.out.println("consumer_1 shutdown");
        consumer_2.shutdown();
        System.out.println("consumer_2 shutdown");

        System.out.println();
    }

    public static DefaultMQPushConsumer getConsumer(String consumerName) throws MQClientException {
        String groupName = "test_module_consumer_name";
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(Const.NAMESRV_ADDR_SINGLE);
        consumer.subscribe(Const.TEST_MESSAGE_MODULE_TOPIC, "*");
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // consumer.setMessageModel(MessageModel.BROADCASTING);

        // The consumer group[test_module_consumer_name] has been created before, specify another name please.
        //See http://rocketmq.apache.org/docs/faq/ for further details.
        // 同一个进程的 需要设置 setInstanceName
        consumer.setInstanceName(consumerName);

        consumer.registerMessageListener(new CustomerMessageListener(consumerName));
        consumer.start();
        return consumer;
    }

    public static class CustomerMessageListener implements MessageListenerConcurrently {

        private final String consumerName;

        public CustomerMessageListener(String consumerName) {
            this.consumerName = consumerName;
        }


        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            try {
                MessageExt messageExt = msgs.get(0);
                String messageExtTopic = messageExt.getTopic();
                String tags = messageExt.getTags();
                String keys = messageExt.getKeys();
                String msgBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);


                System.out.println(String.format("[consumerName:%s][topic:%s][tags:%s][keys:%s][msgBody:%s]",
                        consumerName, messageExtTopic, tags, keys, msgBody));
            } catch (Exception e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
