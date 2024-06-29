package com.xidian.carpool.wscore.task;

import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.CronUtil;
import com.xidian.carpool.wscore.MessageConfig;
import com.xidian.carpool.wscore.MessagePoster;
import com.xidian.carpool.wscore.session.DistributeSessionManager;
import com.xidian.carpool.wscore.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskInit implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MessageConfig messageConfig;

    @Autowired
    SessionManager sessionManager;

    @Autowired
    MessagePoster messagePoster;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if(StrUtil.isNotBlank(messageConfig.getMsgPoolKey())){
            CronUtil.schedule("*/30 * * * *",
                    new RedisMessageTask(sessionManager, messagePoster, applicationContext.getBean(StringRedisTemplate.class), messageConfig.getMsgPoolKey()));
            log.info("Init RedisMessageTask.");
        }

        if("distribute".equals(messageConfig.getSessionMode())){
            CronUtil.schedule("*/30 * * * *",
                    new DistributeMessageTask((DistributeSessionManager) sessionManager, messagePoster));
            log.info("Init DistributeMessageTask.");
        }

        CronUtil.start();
    }

}
