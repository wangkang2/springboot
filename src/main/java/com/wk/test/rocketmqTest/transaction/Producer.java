package com.wk.test.rocketmqTest.transaction;

import com.alibaba.fastjson.JSONObject;
import com.wk.entity.User;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Component
public class Producer {

    @Autowired
    private TransactionListenerImpl transactionListener;

    public void exeutorTransaction(User user){

        TransactionMQProducer producer = new TransactionMQProducer("transaction_producer");
        producer.setNamesrvAddr("10.32.16.179:9876");
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("transaction_thread");
                return thread;
            }
        });
        producer.setSendMsgTimeout(10000);
        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener);
        try {
            producer.start();
            user.setDesc("年龄加一");
            Message message = new Message("TransactionTopic","transaction","key", (JSONObject.toJSONString(user)).getBytes());
            Map<String, Object> map = new HashMap<>();
            map.put("1", "测试参数1");
            map.put("2","测试参数2");
            SendResult sendResult = producer.sendMessageInTransaction(message,map);
            System.out.println(sendResult);
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }
}
