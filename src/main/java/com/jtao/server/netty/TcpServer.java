package com.jtao.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TcpServer extends Thread{

    private int tcpPort;
    private List<ChannelHandler> channelHandlers;
    private int parentThread;
    private int childThread;
    private TcpRepository tcpRepository;

    public TcpServer() {
    }

    public TcpServer(int tcpPort,
                     List<ChannelHandler> channelHandlers,
                     int parentThread,
                     int childThread,
                     TcpRepository tcpRepository) {
        this.tcpPort = tcpPort;
        this.channelHandlers = channelHandlers;
        this.parentThread = parentThread;
        this.childThread = childThread;
        this.tcpRepository = tcpRepository;
    }

    private Logger logger = LoggerFactory.getLogger(TcpServer.class);

    @Override
    public void run(){
        NioEventLoopGroup parentGroup = new NioEventLoopGroup(parentThread);
        NioEventLoopGroup childGroup = new NioEventLoopGroup(childThread);
        ServerBootstrap b = new ServerBootstrap()
                .group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        channelHandlers.forEach(pipeline::addLast);
                    }
                });
        b.option(ChannelOption.SO_BACKLOG, 128);
        b.childOption(ChannelOption.TCP_NODELAY, true);
        try {
            ChannelFuture cf = b.bind(tcpPort).sync();
            logger.info("tcp server running on " + tcpPort);
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }

        childGroup.shutdownGracefully();
        parentGroup.shutdownGracefully();
    }

    public String putMsg(String channelKey, byte[] msg){
        Channel channel = tcpRepository.find(channelKey);
        if (channel == null){
            return "No channel";
        } else {
            channel.writeAndFlush(Unpooled.wrappedBuffer(msg));
            return "Ok";
        }
    }

    public void putAll(byte[] msg) {
        tcpRepository.findAll()
                .forEach(channel -> channel.writeAndFlush(Unpooled.wrappedBuffer(msg)));
    }
}
