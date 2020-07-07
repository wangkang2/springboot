package com.wk.test.nettyTest.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
@ChannelHandler.Sharable
public class DelimiterBasedFrameEncoder extends MessageToByteEncoder<String> {

    private String delimiter;

    public DelimiterBasedFrameEncoder(String delimiter){
        this.delimiter = delimiter;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String message, ByteBuf byteBuf) throws Exception {
        channelHandlerContext.writeAndFlush(Unpooled.wrappedBuffer((message + delimiter).getBytes()));
    }

}
