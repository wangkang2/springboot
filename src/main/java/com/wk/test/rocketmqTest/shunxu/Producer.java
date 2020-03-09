package com.wk.test.rocketmqTest.shunxu;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Producer {

    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("shunxu_producer");
        producer.setNamesrvAddr("10.32.16.179:9876");
        try {
            producer.start();
            List<Order> orders = getOrders();
            for(Order order:orders){
                String uuid = UUID.randomUUID().toString();
                Message message = new Message("shunxuTopic", "order",uuid,("顺序消费：订单号_"+order.getOrderId()+" 订单状态_"+order.getOrderStatus()).getBytes());
                SendResult sendResult = producer.send(message, (list, message1, obj) -> {
                    int value = obj.hashCode();
                    if(value < 0){
                        value = Math.abs(value);
                    }
                    //取obj的hashCode的绝对值，然后对list.size()进行取余，得到目标队列在list的下标
                    value = value % list.size();
                    return list.get(value);
                },order.getOrderId(),10000);//通过订单ID来获取对应的队列,保证每个订单的顺序是创建-支付-完成
                System.out.println(sendResult);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }finally {
            producer.shutdown();
        }
    }

    public static List<Order> getOrders(){
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setOrderId("111");
        order.setOrderStatus("创建");
        orders.add(order);

        order = new Order();
        order.setOrderId("111");
        order.setOrderStatus("支付");
        orders.add(order);

        order = new Order();
        order.setOrderId("222");
        order.setOrderStatus("创建");
        orders.add(order);

        order = new Order();
        order.setOrderId("333");
        order.setOrderStatus("创建");
        orders.add(order);

        order = new Order();
        order.setOrderId("111");
        order.setOrderStatus("完成");
        orders.add(order);

        order = new Order();
        order.setOrderId("333");
        order.setOrderStatus("支付");
        orders.add(order);

        order = new Order();
        order.setOrderId("222");
        order.setOrderStatus("支付");
        orders.add(order);

        order = new Order();
        order.setOrderId("333");
        order.setOrderStatus("完成");
        orders.add(order);

        order = new Order();
        order.setOrderId("222");
        order.setOrderStatus("完成");
        orders.add(order);

        return orders;

    }

}
