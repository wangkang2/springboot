package com.wk.test.zookeeperTest;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CuratorBaseTest {

    private static final String CONNECT_PATH = "10.32.16.179:2181,10.32.16.179:2182,10.32.16.179:2183";

    //会话超时时间，5秒不使用自动释放连接
    private static final int SESSION_OUTTIME = 5000;

    //连接超时时间
    private static final int CONNECTION_OUTTIME = 5000;

    @Test
    public void curatorBaseTest() throws Exception {

        //重试策略 间隔1秒重试，重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        //curator工厂构建
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_PATH)
                .sessionTimeoutMs(SESSION_OUTTIME)
                .connectionTimeoutMs(CONNECTION_OUTTIME)
                .retryPolicy(retryPolicy)
                .build();
        //启动客户端
        client.start();


        //创建节点
//        client.create()
//                .creatingParentContainersIfNeeded() //递归创建节点
//                .withMode(CreateMode.PERSISTENT) //持久化节点
//                .forPath("/p1/p2","test".getBytes()); //节点路径和内容

        //删除节点
//        client.delete()
//                .guaranteed() //强制保证删除
//                .deletingChildrenIfNeeded() //递归删除节点
//                .forPath("/pb"); //删除节点路径

        //查看节点内容
//        byte[] bytes = client.getData()
//                .forPath("/p1/p2");
//        System.out.println(new String(bytes));

        //修改节点内容
        //client.setData().forPath("/p1/p2", "test4".getBytes());

        //回调函数
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        client.create()
//                .creatingParentContainersIfNeeded()
//                .withMode(CreateMode.PERSISTENT)
//                .inBackground((curatorFramework, curatorEvent) -> {
//                    System.out.println(curatorEvent.getResultCode());
//                    System.out.println(curatorEvent.getType());
//                    System.out.println(Thread.currentThread().getName());
//                },executorService)
//                .forPath("/pb");
//        //线程暂不关闭才能看到异步效果
//        Thread.sleep(10000);


    }
}
