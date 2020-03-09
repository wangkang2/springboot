package com.wk.test.rocketmqTest;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class Producer {
    public static void main(String[] args) {
        //定义生产者名称
        DefaultMQProducer producer = new DefaultMQProducer("quickstart_product");
        //连接rocketMQ的namesrv地址（这里是集群）
        producer.setNamesrvAddr("10.32.16.179:9876");
        //发送失败重试3次
        producer.setSendMsgTimeout(5000);
        try {
            producer.start();
            //1.主题，一般在服务器设置好，不能从代码中新建。2.标签。3.发送内容。
            for(int i=0;i<10;i++){
                Message message = new Message("TopicQuickStart","Tag1",("生产者重试"+i).getBytes());
                producer.sendOneway(message);
            }

        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        }finally {
            producer.shutdown();
        }
    }
}
