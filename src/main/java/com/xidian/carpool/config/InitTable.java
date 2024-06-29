package com.xidian.carpool.config;

import com.xidian.carpool.mapper.InitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class InitTable implements BeanPostProcessor {
    @Autowired
    InitMapper mapper;
    /**
     * 初始化数据库
     * @see InitMapper
     */
    @PostConstruct
    public void InitTables() {
        mapper.initCarTable("car");
        mapper.initChildTable("child");
        mapper.initCooperateTable("cooperate");
        mapper.initCPCTable("cooperateParentChild");
        mapper.initLicenseTable("license");
        mapper.initParentTable("parent");
        mapper.initPCTable("parentChild");
        mapper.initDriverCarTable("driverCar");
        mapper.initVerifyTable("verify");
        mapper.initApplyJoinTable("applyJoin");
        mapper.initUnreceivedMessagesTable("unreceivedMessages");
        if (mapper.existTable("cooperateView", "carpoolLearn") == 0)
            mapper.initCooperateView("cooperateView");
        if (mapper.existTrigger("updateTime", "carpoolLearn") == 0)
            mapper.updateTimeTrigger("updateTime");
        if (mapper.existTrigger("addChildName", "carpoolLearn") == 0)
            mapper.addChildNameTrigger("addChildName");
    }
}
