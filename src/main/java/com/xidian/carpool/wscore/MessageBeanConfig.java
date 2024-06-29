package com.xidian.carpool.wscore;

import com.xidian.carpool.wscore.session.DistributeSessionManager;
import com.xidian.carpool.wscore.session.MultiSessionManager;
import com.xidian.carpool.wscore.session.SessionManager;
import com.xidian.carpool.wscore.session.SingleSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class MessageBeanConfig {


    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MessageConfig messageConfig;

    @Bean
    public SessionManager sessionManager(){
        String mode = messageConfig.getSessionMode();
        if("single".equals(mode)){
            return new SingleSessionManager();
        }else if("multi".equals(mode)){
            return new MultiSessionManager();
        }else if("distribute".equals(mode)){
            return new DistributeSessionManager(applicationContext.getBean(StringRedisTemplate.class));
        }else{
            return new SingleSessionManager();
        }
    }

    @Bean
    public MessageDispatcher messageDispatcher(){
        return new MessageDispatcher();
    }

    @Bean
    public MessagePoster messageServer(SessionManager sessionManager){
        return new MessagePoster(sessionManager);
    }

    @Bean
    public MessageContext messageContext(SessionManager sessionManager, MessageDispatcher messageDispatcher, MessagePoster messagePoster){
        return new MessageContext(sessionManager, messageDispatcher, messagePoster);
    }



    @Bean
    public MessageHandler messageHandler(MessageContext messageContext){
        return new MessageHandler(messageContext);
    }



    @Bean
    public MessageComponentScan messageComponentScan(MessageDispatcher messageDispatcher){
        return new MessageComponentScan(messageDispatcher);
    }

}
