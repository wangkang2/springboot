package com.wk.test.zookeeperTest;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class BaseTest {

    private static final String CONNECT_PATH = "10.32.16.179:2181,10.32.16.179:2182,10.32.16.179:2183";

    private static final int SESSION_OUTTIME = 5000;

    private static final CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(CONNECT_PATH, SESSION_OUTTIME, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if(Event.KeeperState.SyncConnected == event.getState()){
                    if(Event.EventType.None == event.getType()){
                        connectedSemaphore.countDown();
                        System.out.println("zk 已经建立连接");
                    }
                }
            }
        });

        connectedSemaphore.await();

    }
}
