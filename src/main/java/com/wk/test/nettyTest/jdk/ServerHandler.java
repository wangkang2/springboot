package com.wk.test.nettyTest.jdk;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Request request = (Request)msg;
		System.out.println("Server : " + request.getId() + ", " + request.getName() + ", " + request.getInfo());
		Response response = new Response();
		response.setId(request.getId());
		response.setName("response" + request.getName());
		response.setResponseMessage("响应内容" + request.getInfo());
		ctx.writeAndFlush(response);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	
	
}
