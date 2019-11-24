package com.gupao.edu.vip.course.chaper4.handler;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
* Title: HelloServerHandler
* Description:  服务端业务逻辑
* Version:1.0.0  
* @author baymax
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
    /*
     * 收到消息时，返回信息
     */

    List<byte[]> datas = new ArrayList<>();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)
            throws Exception {
        ByteBuf heapBuffer = null;
        try {


        // 收到消息直接打印输出
        System.out.println("服务端接受的消息 : " + msg);
        if("quit".equals(msg)){//服务端断开的条件
            ctx.close();
        }
        byte[] data = new byte[1024 * 1024 ];
//        datas.add(data);
         heapBuffer = ctx.alloc().heapBuffer();

        heapBuffer.writeBytes(data);


        // 返回客户端消息
        ctx.channel().writeAndFlush(heapBuffer+"\n");

        }finally {
            heapBuffer.release();
        }
    }

    /*
     * 建立连接时，返回消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
        ctx.writeAndFlush("客户端"+ InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ \n");
        super.channelActive(ctx);
    }
}
