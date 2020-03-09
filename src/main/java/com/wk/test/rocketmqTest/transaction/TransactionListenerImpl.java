package com.wk.test.rocketmqTest.transaction;

import com.alibaba.fastjson.JSONObject;
import com.wk.entity.User;
import com.wk.service.UserService;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TransactionListenerImpl implements TransactionListener {

    private AtomicInteger transactionIndex = new AtomicInteger(0);

    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    @Autowired
    private UserService userService;

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            String body = new String(message.getBody(),"UTF-8");
            User user = JSONObject.parseObject(body,User.class);
            Map<String, Object> map = (Map<String, Object>) o;
            System.out.println(map);
            userService.updateUser(user);
//            int value = transactionIndex.getAndIncrement();
//            int status = value % 3;
//            localTrans.put(message.getTransactionId(), status);
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            e.printStackTrace();
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
//        Integer status = localTrans.get(messageExt.getMsgId());
//        if(null != status){
//            switch (status){
//                case 0:
//                    return LocalTransactionState.UNKNOW;
//                case 1:
//                    return LocalTransactionState.COMMIT_MESSAGE;
//                case 2:
//                    return LocalTransactionState.ROLLBACK_MESSAGE;
//            }
//        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
