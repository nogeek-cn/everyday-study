package com.darian.mq;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/3/23  22:31
 */
public class MqConsumer {

    private MqBroker mqBroker;

    public MqConsumer() {
    }

    public void setMqBroker(MqBroker mqBroker) {
        this.mqBroker = mqBroker;
    }

    public void consumerOneMsq(MqMsg mqMsg) {
        System.out.println("consumer msg: " + mqMsg);
        mqBroker.ack(mqMsg);
    }
}
