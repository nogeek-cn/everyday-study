package com.darian.rocketmq.messagemodel;

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
public class MessageModelProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        String group_name = "test_module_producer_name";

        DefaultMQProducer producer = new DefaultMQProducer(group_name);
        producer.setNamesrvAddr(Const.NAMESRV_ADDR_SINGLE);
        // rocketmq.remoting.exception.RemotingTooMuchRequestException: sendDefaultImpl call timeout
        producer.setSendMsgTimeout(60000);
        producer.start();


        for (int i = 0; i < 10; i++) {
            Message msg = new Message(Const.TEST_MESSAGE_MODULE_TOPIC,
                    "tagA",
                    ("信息内容"+ i).getBytes());

            SendResult sendResult = producer.send(msg);
            System.out.println(sendResult);
        }

        TimeUnit.SECONDS.sleep(10);
        producer.shutdown();
    }
}
