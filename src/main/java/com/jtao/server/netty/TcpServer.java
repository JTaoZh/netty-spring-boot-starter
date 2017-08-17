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
import java.util.Map;

public class TcpServer extends Thread{

    private int tcpPort;
    private List<ChannelHandler> channelHandlers;
    private int parentThread;
    private int childThread;
    private Map<Object, Object> option;
    private Map<Object, Object> childOption;
    private TcpRepository tcpRepository;

    public TcpServer() {
    }

    public TcpServer(int tcpPort,
                     List<ChannelHandler> channelHandlers,
                     int parentThread,
                     int childThread,
                     Map<Object, Object> option,
                     Map<Object, Object> childOption,
                     TcpRepository tcpRepository) {
        this.tcpPort = tcpPort;
        this.channelHandlers = channelHandlers;
        this.parentThread = parentThread;
        this.childThread = childThread;
        this.option = option;
        this.childOption = childOption;
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
        option.forEach((k,v)->b.option((ChannelOption<Object>) k, v));
        childOption.forEach((k,v)->b.childOption((ChannelOption<Object>) k, v));
//        b.option(ChannelOption.SO_BACKLOG, 128);
//        b.childOption(ChannelOption.TCP_NODELAY, true);
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
