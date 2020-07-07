package com.wk.test.nettyTest.common;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClientTest {

    private static final Logger logger = LoggerFactory.getLogger(NettyClientTest.class);

    public static void main(String[] args) {
        //客户端只需要定义一个接收请求和处理IO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        //客户端是bootstrap，其他和服务端配置大同小异
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                //允许小包发送
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        String delimiter = "_$";
                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.wrappedBuffer(delimiter.getBytes())));
                        //解码器
                        socketChannel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                        //编码器
                        socketChannel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                        socketChannel.pipeline().addLast(new DelimiterBasedFrameEncoder(delimiter));
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });
        try {
            //连接服务器地址
            ChannelFuture future = bootstrap.connect("127.0.0.1",8090).sync();
            logger.info("客户端启动成功");
            //发送信息
            future.channel().writeAndFlush("你好啊").sync();
            future.channel().writeAndFlush("我很好").sync();
            future.channel().writeAndFlush("还行吧").sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //优雅关闭
            group.shutdownGracefully();
        }
    }
}
