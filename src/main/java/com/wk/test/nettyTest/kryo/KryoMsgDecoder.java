package com.wk.test.nettyTest.kryo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

@ChannelHandler.Sharable
public class KryoMsgDecoder extends ByteToMessageDecoder {

    private static final int HEAD_LENGTH = 4;
    private Serializer serializer = KryoSerializerFactory.getSerializer(Request.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        //TCP头长度为4，若可读长度小于4则报文有问题
        if(byteBuf.readableBytes() < HEAD_LENGTH){
            return;
        }

        //标记读取的指针的位置
        byteBuf.markReaderIndex();
        //获取消息头，也就是长度
        int dataLength = byteBuf.readInt();
        if(dataLength <=0){
            //长度不对则当前消息有问题，关闭通道
            channelHandlerContext.close();
        }
        //长度小于真实长度则重新加载读取指针
        if(byteBuf.readableBytes() < dataLength){
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] body = new byte[dataLength];
        byteBuf.readBytes(body);
        Request request = serializer.deserialize(body);
        list.add(request);
    }
}
