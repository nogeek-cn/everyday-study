package com.darian.mq.main;

import com.darian.mq.MqBroker;
import com.darian.mq.MqConsumer;
import com.darian.mq.MqMsg;
import com.darian.mq.MqProvider;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/3/23  23:45
 */
public class Main {
    public static void main(String[] args) {
        MqBroker mqBroker = new MqBroker();

        for (int i = 0; i < 4; i++) {
            MqConsumer mqConsumer = new MqConsumer();
            mqConsumer.setMqBroker(mqBroker);
            mqBroker.addMqConsumer(mqConsumer);
        }

        mqBroker.startSend();


        MqProvider mqProvider1 = new MqProvider(mqBroker);
        MqProvider mqProvider2 = new MqProvider(mqBroker);
        MqProvider mqProvider3 = new MqProvider(mqBroker);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
                    mqProvider1.sendMsq(new MqMsg(UUID.randomUUID().toString(), "hello world, mqProvider1".getBytes()));
                    mqProvider2.sendMsq(new MqMsg(UUID.randomUUID().toString(), "hello world, mqProvider2".getBytes()));
                    mqProvider3.sendMsq(new MqMsg(UUID.randomUUID().toString(), "hello world, mqProvider3".getBytes()));
                }, 1,
                1,
                TimeUnit.SECONDS);

    }
}
