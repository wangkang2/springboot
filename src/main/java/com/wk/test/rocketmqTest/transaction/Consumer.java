package com.wk.test.rocketmqTest.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

public class Consumer {
    public static void main(String[] args) throws MQClientException {
        //定义消费者名称，MQ往消费者推送
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("transaction_consumer");
        //连接rocketMQ的namesrv地址（此次为集群）
        consumer.setNamesrvAddr("10.32.16.179:9876");
        //新订阅组第一次启动，从头消费到尾，后续从上次的消费进度继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //广播模式
        //consumer.setMessageModel(MessageModel.BROADCASTING);
        //订阅的主题和标签（*代表所有标签）
        consumer.subscribe("TransactionTopic", "*");
        //消费者监听
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            MessageExt msg = msgs.get(0);
            try {
                String topic = msg.getTopic();
                String msgbody = new String(msg.getBody(), "UTF-8");
                String tag = msg.getTags();
                System.out.println("topic:" + topic + " msgbody:" + msgbody + " tag:" + tag);
                //dosomething...业务处理
            } catch (Exception e) {
                e.printStackTrace();
                //重试3次扔不成功则不继续重试
                if(msg.getReconsumeTimes() == 3){
                    //记录日志或进行持久化操作。
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                //MQ发送失败重试机制，1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            //消息处理成功
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }
}
