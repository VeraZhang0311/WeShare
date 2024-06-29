package com.xidian.carpool.controller;

import com.alibaba.fastjson.JSON;
import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.mapper.ParentMapper;
import com.xidian.carpool.service.InfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {
    private final static Logger logger = Logger.getLogger(InfoController.class);
    @Autowired
    InfoService infoService;

    /**
     * 获取近七天以及未完成的订单
     *
     * @return BaseResponse Data:List<CooperateView>
     */
    @RequestMapping(value = "/getNearCooperate", method = RequestMethod.POST)
    public BaseResponse getNearCooperate() {
        BaseResponse baseResponse = infoService.getNearCooperate();
        logger.info("/getNearCooperate Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 获取用户全部相关信息
     *
     * @param openid 用户openid
     * @return BaseResponse Data:Map<String, Object>
     */
    @RequestMapping(value = "/getUserInfo/{openid}", method = RequestMethod.POST)
    public BaseResponse getUserInfo(@PathVariable String openid) {
        BaseResponse baseResponse = infoService.getUserInfo(openid);
        logger.info("/getUserInfo Request:{\"openid\":" + openid + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 获取家长全部相关信息
     *
     * @param openid 用户openid
     * @return BaseResponse Data:Map<String, Object>
     * @see #getUserInfo(String)
     */
    @Deprecated
    @RequestMapping(value = "/getParentInfo/{openid}", method = RequestMethod.POST)
    public BaseResponse getParentInfo(@PathVariable String openid) {
        //BaseResponse baseResponse = infoService.getParentInfo(openid);
        BaseResponse baseResponse = new BaseResponse(ConstantCode.API_REFUSED);
        logger.info("/getParentInfo Request:{\"openid\":" + openid + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 获取孩子全部相关信息
     *
     * @param openid 用户openid
     * @return BaseResponse Data:Map<String, Object>
     * @see #getUserInfo(String)
     */
    @Deprecated
    @RequestMapping(value = "/getChildInfo/{openid}", method = RequestMethod.POST)
    public BaseResponse getChildInfo(@PathVariable String openid) {
        //BaseResponse baseResponse = infoService.getChildInfo(openid);
        BaseResponse baseResponse = new BaseResponse(ConstantCode.API_REFUSED);
        logger.info("/getChildInfo Request:{\"openid\":" + openid + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }
}
