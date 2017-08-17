package com.jtao.server.netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(TcpServer.class)
@EnableConfigurationProperties({TcpServerProperties.class})
public class TcpServerAutoConfigure {

    @Autowired
    private TcpServerProperties properties;

    @Bean(name = "tcpServer")
    @ConditionalOnMissingBean
    public TcpServer tcpServer(){
        return new TcpServer(
                properties.getTcpPort(),
                properties.getChannelHandlers(),
                properties.getParentThread(),
                properties.getChildThread(),
                tcpRepository()
        );
    }

    @Bean(name = "tcpRepository")
    @ConditionalOnMissingBean
    public TcpRepository tcpRepository(){
        return new TcpRepository();
    }

}
