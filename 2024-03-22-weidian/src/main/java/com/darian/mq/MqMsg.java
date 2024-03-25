package com.darian.mq;

import java.util.Arrays;
import java.util.Objects;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/3/23  22:39
 */
public class MqMsg {

    private String msgId;

    private int queueIndex;

    private byte[] msgBody;


    public MqMsg(String msgId, byte[] msgBody) {
        this.msgId = msgId;
        this.msgBody = msgBody;
    }


    public int getQueueIndex() {
        return queueIndex;
    }

    public void setQueueIndex(int queueIndex) {
        this.queueIndex = queueIndex;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public byte[] getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(byte[] msgBody) {
        this.msgBody = msgBody;
    }


    @Override
    public String toString() {
        return "MqMsg{" +
                "msgId='" + msgId + '\'' +
                ", queueIndex=" + queueIndex +
                ", msgBody=" + Arrays.toString(msgBody) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MqMsg mqMsg = (MqMsg) o;
        return queueIndex == mqMsg.queueIndex && Objects.equals(msgId, mqMsg.msgId) && Arrays.equals(msgBody, mqMsg.msgBody);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(msgId, queueIndex);
        result = 31 * result + Arrays.hashCode(msgBody);
        return result;
    }
}
