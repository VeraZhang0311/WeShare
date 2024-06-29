package com.xidian.carpool.controller;

import com.alibaba.fastjson.JSON;
import com.xidian.carpool.entity.Child;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.service.ChildService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/child")
public class ChildController {
    private final static Logger logger = Logger.getLogger(ChildController.class);
    @Autowired
    ChildService childService;

    /**
     * 对学生进行注册，存入数据库
     *
     * @param child 学生信息
     * @return BaseResponse Data:学生ID
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResponse register(@RequestBody Child child) {
        BaseResponse baseResponse = childService.register(child);
        logger.info("/register Request:{\"Child\":" + JSON.toJSONString(child) + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }
}
