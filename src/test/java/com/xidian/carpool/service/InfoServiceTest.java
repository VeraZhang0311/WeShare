package com.xidian.carpool.service;

import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.base.BaseResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InfoServiceTest {

    @Autowired
    InfoService infoService;

    @Test
    void getNearCooperate() {
        BaseResponse baseResponse = infoService.getNearCooperate();
        assert baseResponse.getCode() == ConstantCode.SUCCESS.getCode();
    }
}