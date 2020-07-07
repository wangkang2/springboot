package com.wk.test.nettyTest.jdk;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClientTest {

    private static final Logger logger = LoggerFactory.getLogger(NettyClientTest.class);

    private static class SingletonHolder {
        static final NettyClientTest instance = new NettyClientTest();
    }

    public static NettyClientTest getInstance() {
        return SingletonHolder.instance;
    }

    private EventLoopGroup group;
    private Bootstrap b;
    private ChannelFuture cf;

    private NettyClientTest() {
        group = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                        sc.pipeline().addLast(new ObjectEncoder());
                        //超时handler（当服务器端与客户端在指定时间以上没有任何进行通信，则会关闭响应的通道，主要为减小服务端资源占用）
                        sc.pipeline().addLast(new ReadTimeoutHandler(5));
                        sc.pipeline().addLast(new ClientHandler());
                    }
                });
    }

    public void connect() {
        try {
            this.cf = b.connect("127.0.0.1", 8090).sync();
            System.out.println("远程服务器已经连接, 可以进行数据交换..");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ChannelFuture getChannelFuture() {

        if (this.cf == null) {
            this.connect();
        }
        if (!this.cf.channel().isActive()) {
            this.connect();
        }

        return this.cf;
    }

    public static void main(String[] args) throws InterruptedException {
        final NettyClientTest c = NettyClientTest.getInstance();
        ChannelFuture future = c.getChannelFuture();

        Request request = new Request();
        request.setId("1");
        request.setName("上杉绘梨衣");
        request.setInfo("04.24，和Sakura去东京天空树，世界上最暖和的地方在天空树的顶上。");
        future.channel().writeAndFlush(request).sync();

        Request request2 = new Request();
        request2.setId("2");
        request2.setName("上杉绘梨衣");
        request2.setInfo("04.26，和Sakura去明治神宫，有人在那里举办婚礼。");
        future.channel().writeAndFlush(request2);

        Request request3 = new Request();
        request3.setId("3");
        request3.setName("上杉绘梨衣");
        request3.setInfo("04.25，和Sakura去迪士尼，鬼屋很可怕，但是有Sakura在，所以不可怕。");
        future.channel().writeAndFlush(request3);

        Request request4 = new Request();
        request4.setId("4");
        request4.setName("上杉绘梨衣");
        request4.setInfo("Sakura最好了。");
        future.channel().writeAndFlush(request4);

        future.channel().closeFuture().sync();

    }
}
