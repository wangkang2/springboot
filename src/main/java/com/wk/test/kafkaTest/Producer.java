package com.wk.test.kafkaTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

/**
 * @Author: WANGKANG
 * @Date: 2021/5/7 11:10
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate ;

    @Test
    public void sendKafkaTest() throws ExecutionException, InterruptedException {
        System.out.println("开始发送kafka消息");
        kafkaTemplate.send("test_topic","你好，kafka").get();
    }
}
