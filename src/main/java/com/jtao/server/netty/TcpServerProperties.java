package com.jtao.server.netty;

import io.netty.channel.ChannelHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "com.jtao.server.netty")
public class TcpServerProperties {

    private int tcpPort = 12345;
    private List<ChannelHandler> channelHandlers;
    private int parentThread = 0;
    private int childThread = 0;
    private Map<Object, Object> option;
    private Map<Object, Object> childOption;

    public TcpServerProperties(
            int tcpPort,
            List<ChannelHandler> channelHandlers,
            int parentThread,
            int childThread,
            Map<Object, Object> option,
            Map<Object, Object> childOption) {
        this.tcpPort = tcpPort;
        this.channelHandlers = channelHandlers;
        this.parentThread = parentThread;
        this.childThread = childThread;
        this.option = option;
        this.childOption = childOption;
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

    public Map<Object, Object> getOption() {
        return option;
    }

    public void setOption(Map<Object, Object> option) {
        this.option = option;
    }

    public Map<Object, Object> getChildOption() {
        return childOption;
    }

    public void setChildOption(Map<Object, Object> childOption) {
        this.childOption = childOption;
    }
}
