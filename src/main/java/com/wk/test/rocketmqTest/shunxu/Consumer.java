package com.wk.test.rocketmqTest.shunxu;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Consumer {

    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("shunxuConsumer");
        consumer.setNamesrvAddr("10.32.16.179:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe("shunxuTopic","*");
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                    consumeOrderlyContext.setAutoCommit(true);
                    for(MessageExt messageExt:list){
                        try {
                            System.out.println(new String(messageExt.getBody(),"UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }
}
