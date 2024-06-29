package com.xidian.carpool.wscore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket配置
 * Created by lb on 2018/1/2.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    MessageConfig messageConfig;

    @Autowired
    MessageHandler messageHandler;

    @Bean
    public ServerEndpointExporter serverEndpointExporter(ApplicationContext context) {
        return new ServerEndpointExporter();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandler, messageConfig.getServerRoot()).addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("*");
    }

}
