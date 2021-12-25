package com.darian.rocketmq.pullConsumer;

import com.darian.rocketmq.constants.Const;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/***
 *
 * @author darian1996
 */
public class PullConsumerProducer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        String group = Const.TEST_PULL_CONSUMER_PRODUCER_NAME;
        DefaultMQProducer producer = new DefaultMQProducer(group);
        producer.setNamesrvAddr(Const.NAMESRV_ADDR_SINGLE);
        producer.setSendMsgTimeout(6000);

        producer.start();

        for (int i = 0; i < 15; i++) {
            Message msg = new Message(Const.TEST_PULL_CONSUMER_TOPIC,
                    "TAGA",
                    ("测试pull" + i).getBytes());

            SendResult sendResult = producer.send(msg);
            System.out.println(sendResult);
            TimeUnit.SECONDS.sleep(1);
        }


        producer.shutdown();

    }
}
