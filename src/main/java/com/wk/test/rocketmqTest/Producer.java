package com.wk.test.rocketmqTest;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public class Producer {
    public static void main(String[] args) {
        //定义生产者名称
        DefaultMQProducer producer = new DefaultMQProducer("quickstart_product");
        //连接rocketMQ的namesrv地址
        producer.setNamesrvAddr("10.32.16.179:9876");
        try {
            producer.start();
            //1.主题，一般在服务器设置好，不能从代码中新建。2.标签。3.发送内容。

            String dept = "{\n" +
                    "\"officeCode\":\"510184DY1666\",\n" +
                    "\"officeName\":\"测试科室\",\n" +
                    "\"organizCode\":\"73238834-6\",\n" +
                    "\"pycode\":\"csks\"\n" +
                    "}";

            String personnel = "{\n" +
                    "\"personId\":\"0123456\",\n" +
                    "\"personname\":\"王康\",\n" +
                    "\"cardnum\":\"142602199210150031\",\n" +
                    "\"birthday\":\"1992-10-15\",\n" +
                    "\"gender\":\"1\",\n" +
                    "\"mobile\":\"18635725831\",\n" +
                    "\"officeCode\":\"510184DY1666\",\n" +
                    "\"organizCode\":\"73238834-6\"\n" +
                    "}";

            String team = "{\n" +
                    "\"teamCode\":\"06797354\",\n" +
                    "\"teamName\":\"测试团队\",\n" +
                    "\"orgCode\":\"73238834-6\",\n" +
                    "}";

            String teamDoctor = "{\n" +
                    "\"userId\":\"0123456\",\n" +
                    "\"manageUnitId\":\"06797354\"\n" +
                    "}";

            Message message = new Message("DataTransfer", "teamDoctor_d", teamDoctor.getBytes("UTF-8"));
            SendResult sendResult = producer.send(message,10000);
            System.out.println(sendResult);

        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }
}
