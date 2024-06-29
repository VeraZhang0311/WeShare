package com.xidian.carpool.wscore;

import com.xidian.carpool.annotation.WSController;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Logger;

public class MessageComponentScan implements ApplicationListener<ContextRefreshedEvent> {

    Logger logger = Logger.getLogger(MessageComponentScan.class.getName());

    MessageDispatcher messageDispatcher;

    public MessageComponentScan(MessageDispatcher messageDispatcher){
        this.messageDispatcher = messageDispatcher;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("init message component ...");

        Map<String, Object> wsBeans = event.getApplicationContext().getBeansWithAnnotation(WSController.class);
        wsBeans.values().forEach((obj)->{
            WSController wsController = obj.getClass().getAnnotation(WSController.class);
            String prefix = wsController.value();

            Method[] methods = obj.getClass().getMethods();
            for(Method method : methods){
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                if(requestMapping != null){
                    String[] urls = requestMapping.value();
                    if(urls != null && urls.length > 0){
                        for(String url : urls){
                            messageDispatcher.addMethod(prefix + url, obj, method);
                            logger.info(String.format("Mapped [%s-%s.%s]", prefix + url, obj.getClass().getName(), method.getName()));
                        }
                    }
                }
            }
        });

    }
}
