package com.wk.test.nettyTest.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Handler implements Runnable {

    //通道
    final SocketChannel channel;
    //绑定到选择器的选择键
    final SelectionKey selectionKey;
    //定义输入输出缓冲区
    ByteBuffer inputBuffer = ByteBuffer.allocate(102400);
    ByteBuffer outputBuffer = ByteBuffer.allocate(102400);
    static final boolean READING = true, WRITING = false;
    //初始化定义可读就绪
    boolean status = READING;

    Handler(Selector selector, SocketChannel c) throws IOException {
        channel = c;
        //非阻塞
        c.configureBlocking(false);
        //这里将通道注册到选择器上，本应后面的int是 1（读）,4（写）,8（连接）,16（可连接）的
        //这种操作貌似是判断JDK的selector有没有立即返回或报错，并不引起任何实质操作。
        //https://github.com/netty/netty/issues/1836 这个讨论问题的地址，外国友人貌似也搞不懂，似乎是个JDK NIO的BUG
        selectionKey = channel.register(selector, 0);
        //选择键将本身也就是Handler附加
        selectionKey.attach(this);
        //定义当前选择键是读就绪状态
        selectionKey.interestOps(SelectionKey.OP_READ);
        //唤醒选择器
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (status) {
                read();
            } else {
                write();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void read() throws IOException {
        channel.read(inputBuffer);
        //一系列逻辑判定和处理
        status = WRITING;
        selectionKey.interestOps(SelectionKey.OP_WRITE);
    }

    public void write() throws IOException {
        channel.write(outputBuffer);
        //判定写操作执行完毕后，关闭selectKey
        selectionKey.cancel();
    }
}
