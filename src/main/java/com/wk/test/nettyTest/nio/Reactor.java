package com.wk.test.nettyTest.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

class Reactor implements Runnable {

    //选择器
    final Selector selector;
    //服务端通道
    final ServerSocketChannel serverChannel;

    //构造函数初始化
    Reactor(int port) throws IOException {
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        //绑定连接
        serverChannel.socket().bind(new InetSocketAddress(port));
        //非阻塞
        serverChannel.configureBlocking(false);
        //将服务端的通道绑定到选择器上面，并定义事件为接收连接时间
        //OP_ACCEPT:接收连接就绪事件，服务端监听到客户端，可接收连接 1<<4
        //OP_CONNECT:连接就绪事件，表示客户端与服务端建立连接成功 1<<3
        //OP_READ:读就绪事件，表示通道中有可读数据，可执行读操作 1<<0
        //OP_WRITE:写就绪事件，表示可以向通道写数据 1<<2
        SelectionKey selectionKey = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        //选择键通过attach方法附加一个对象
        selectionKey.attach(new Acceptor());

    }



    @Override
    public void run() {
        //不中断的线程则循环，interrupted方法，判断线程是否中断，并能释放已经中断的线程
        while (!Thread.interrupted()){
            try {
                //这里每一个request封装一个channel,所有的channel注册在一个选择器上，selector选择器不断轮询查看可读状态
                selector.select();
                //获取选择器的选择键集合
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectedKey = iterator.next();
                    //attachement方法可以获取attach方法附加的对象，这里就是前面附加进来的Handler对象，也就是事件处理类
                    Runnable r = (Runnable) selectedKey.attachment();
                    if(r!=null){
                        r.run();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Acceptor implements Runnable{

        @Override
        public void run() {
            try {
                //获取已连接上的channel通道
                SocketChannel channel = serverChannel.accept();
                if(channel!=null){
                    //自定义Handler，事件处理类，将通道绑定到选择器上面
                    new Handler(selector,channel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


