package com.wk.test.rocketmqTest.transaction;

import com.wk.entity.User;
import com.wk.service.UserService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MQTest {

    @Autowired
    private UserService userService;
    @Autowired
    private Producer producer;

    @Test
    public void ss() throws MQClientException {
        User user = userService.findUserById(12);
        producer.exeutorTransaction(user);
    }
}
