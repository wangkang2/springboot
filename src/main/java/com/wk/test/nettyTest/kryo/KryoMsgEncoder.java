package com.wk.test.nettyTest.kryo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class KryoMsgEncoder extends MessageToByteEncoder<Request> {

    private Serializer serializer = KryoSerializerFactory.getSerializer(Request.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Request request, ByteBuf byteBuf) throws Exception {
        byte[] body = serializer.serialize(request);
        int headLength = body.length;
        //相当于消息头
        byteBuf.writeInt(headLength);
        //相当于消息体
        byteBuf.writeBytes(body);
    }
}
