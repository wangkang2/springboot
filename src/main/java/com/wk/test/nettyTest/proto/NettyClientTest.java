package com.wk.test.nettyTest.proto;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
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
                        sc.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                        sc.pipeline().addLast(new ProtobufDecoder(Request.MessageRequest.getDefaultInstance()));

                        sc.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                        sc.pipeline().addLast(new ProtobufEncoder());
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

        Request.MessageRequest.Builder builder =Request.MessageRequest.newBuilder();
        builder.setId(1);
        builder.setName("上杉绘梨衣");
        builder.setInfo("04.24，和Sakura去东京天空树，世界上最暖和的地方在天空树的顶上。");
        future.channel().writeAndFlush(builder.build()).sync();

        Request.MessageRequest.Builder builder2 =Request.MessageRequest.newBuilder();
        builder2.setId(2);
        builder2.setName("上杉绘梨衣");
        builder2.setInfo("04.26，和Sakura去明治神宫，有人在那里举办婚礼。");
        future.channel().writeAndFlush(builder2.build());

        Request.MessageRequest.Builder builder3 =Request.MessageRequest.newBuilder();
        builder3.setId(3);
        builder3.setName("上杉绘梨衣");
        builder3.setInfo("04.25，和Sakura去迪士尼，鬼屋很可怕，但是有Sakura在，所以不可怕。");
        future.channel().writeAndFlush(builder3.build());

        Request.MessageRequest.Builder builder4 =Request.MessageRequest.newBuilder();
        builder4.setId(4);
        builder4.setName("上杉绘梨衣");
        builder4.setInfo("Sakura最好了。");
        future.channel().writeAndFlush(builder4.build());

        future.channel().closeFuture().sync();

    }
}
