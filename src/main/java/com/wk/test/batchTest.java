package com.wk.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@SpringBootTest
@RunWith(SpringRunner.class)
public class batchTest {

    @Test
    public  void qqq() throws InterruptedException {
        List<Integer> idList = new ArrayList<>();
        for(int i = 0;i<100;i++){
            idList.add(i);
        }

        int threadNum = 10;
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        int perSize = idList.size()/threadNum;
        for(int i =0;i<threadNum;i++){
            MultiThread multiThread = new MultiThread();
            multiThread.setIdList(idList.subList(i*perSize,(i+1)*perSize));
            multiThread.setCountDownLatch(countDownLatch);
            executorService.submit(multiThread);
        }
        countDownLatch.await();
        executorService.shutdown();
        long end = System.currentTimeMillis();
        System.out.println(end-start);

    }

    class MultiThread extends Thread{
        private List<Integer> idList;
        private CountDownLatch countDownLatch;

        @Override
        public void run(){
            try {
                execute(idList);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(countDownLatch!=null){
                    countDownLatch.countDown();
                }
            }
        }

        public void execute(List<Integer> idList) throws InterruptedException {
            for(Integer id:idList){
                System.out.println(id);
                Thread.sleep(1000);
            }
        }

        public List<Integer> getIdList() {
            return idList;
        }

        public void setIdList(List<Integer> idList) {
            this.idList = idList;
        }

        public CountDownLatch getCountDownLatch() {
            return countDownLatch;
        }

        public void setCountDownLatch(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }
    }

}
