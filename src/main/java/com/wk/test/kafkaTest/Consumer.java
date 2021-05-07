package com.wk.test.kafkaTest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Author: WANGKANG
 * @Date: 2021/5/7 11:46
 * @Description:
 */
@Component
public class Consumer {
        // 消费监听
        @KafkaListener(topics = {"test_topic"})
        public void onMessage1(ConsumerRecord<?, ?> record){
            // 消费的哪个topic、partition的消息,打印出消息内容
            System.out.println("简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
        }

}
