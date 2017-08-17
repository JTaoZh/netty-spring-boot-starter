package com.jtao.server.netty;

import io.netty.channel.ChannelHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "com.jtao.server.netty")
public class TcpServerProperties {

    private int tcpPort = 12345;
    private List<ChannelHandler> channelHandlers;
    private int parentThread = 0;
    private int childThread = 0;

    public TcpServerProperties(
            int tcpPort,
            List<ChannelHandler> channelHandlers,
            int parentThread,
            int childThread) {
        this.tcpPort = tcpPort;
        this.channelHandlers = channelHandlers;
        this.parentThread = parentThread;
        this.childThread = childThread;
    }

    public TcpServerProperties() {
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public List<ChannelHandler> getChannelHandlers() {
        return channelHandlers;
    }

    public void setChannelHandlers(List<ChannelHandler> channelHandlers) {
        this.channelHandlers = channelHandlers;
    }

    public int getParentThread() {
        return parentThread;
    }

    public void setParentThread(int parentThread) {
        this.parentThread = parentThread;
    }

    public int getChildThread() {
        return childThread;
    }

    public void setChildThread(int childThread) {
        this.childThread = childThread;
    }
}
