package com.jtao.server.netty;

import io.netty.channel.Channel;

import java.util.Collection;
import java.util.HashMap;

public class TcpRepository {
    private HashMap<String, Channel> map = new HashMap<>();

    public void add(String channelKey, Channel channel){
        map.put(channelKey, channel);
    }

    public void delete(String channelKey){
        map.remove(channelKey);
    }

    public Channel find(String channelKey){
        return map.get(channelKey);
    }

    public Collection<Channel> findAll(){
        return map.values();
    }

    public int size(){
        return map.size();
    }
}
