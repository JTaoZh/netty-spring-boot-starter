package com.jtao.server.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Collection;
import java.util.HashMap;

public class TcpRepository {
    private HashMap<String, ChannelHandlerContext> map = new HashMap<>();

    public void add(String channelKey, ChannelHandlerContext ctx){
        map.put(channelKey, ctx);
    }

    public void delete(String channelKey){
        map.remove(channelKey);
    }

    public ChannelHandlerContext find(String channelKey){
        return map.get(channelKey);
    }

    public Collection<ChannelHandlerContext> findAll(){
        return map.values();
    }

    public int size(){
        return map.size();
    }
}
