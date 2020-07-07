package com.wk.test.nettyTest.common;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class NettyServerTest {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerTest.class);

    public static void main(String[] args) {

        //实例化两个线程组
        //处理服务器与客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //进行网络通信（读写）
        EventLoopGroup workerGroup = new NioEventLoopGroup(10);
        //配置容器，配置相关信息
        ServerBootstrap bootstrap = new ServerBootstrap()
                //1.设置reactor线程
                .group(bossGroup,workerGroup)
                //2.设置channel通道的类型，这里是TCP
                .channel(NioServerSocketChannel.class)
                //3.设置监听端口
                .localAddress(new InetSocketAddress(8090))
                //4.设置通道的选项
                .option(ChannelOption.SO_BACKLOG, 1024)         //设置TCP缓冲区
                .childOption(ChannelOption.SO_KEEPALIVE, true) //心跳检测保持连接
                //5.配发事件处理器流水线
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        String delimiter = "_$";
                        //定义拆包粘包的解码器
                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.wrappedBuffer(delimiter.getBytes())));
                        //解码器
                        socketChannel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                        //编码器
                        socketChannel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                        //自定义的编码器，输出信息后面加上分隔符
                        socketChannel.pipeline().addLast(new DelimiterBasedFrameEncoder(delimiter));
                        //自定义事件处理器
                        socketChannel.pipeline().addLast(new NettyServerHandler());


                    }
                });

        try {
            //6.绑定servr,这里使用了sync方法，直到绑定成功为止
            ChannelFuture channelFuture = bootstrap.bind().sync();
            logger.info("服务器启动开启监听端口：{}",8090);
            //7.等待关闭，直到channel关闭为止
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //8.Netty优雅退出
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
