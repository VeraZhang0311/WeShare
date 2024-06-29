package com.xidian.carpool.controller;

import com.alibaba.fastjson.JSON;
import com.xidian.carpool.entity.*;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.service.ParentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/parent")
public class ParentController {
    private final static Logger logger = Logger.getLogger(ParentController.class);
    @Autowired
    ParentService parentService;

    /**
     * 家长注册
     *
     * @param parent 家长信息对象
     * @return BaseResponse
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResponse register(@RequestBody Parent parent) {
        BaseResponse baseResponse = parentService.register(parent);
        logger.info("/register Request:{\"Parent\":" + JSON.toJSONString(parent) + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 家长添加孩子，孩子数据已经在数据库的话是加不进去的
     *
     * @param parentOpenid 家长openid
     * @param child        孩子信息对象
     * @return BaseResponse
     */
    @RequestMapping(value = "/addchild/{parentOpenid}", method = RequestMethod.POST)
    public BaseResponse addChild(@PathVariable String parentOpenid, @RequestBody Child child) {
        BaseResponse baseResponse = parentService.addChild(parentOpenid, child);
        logger.info("/addChild Request:{\"Child\":" + JSON.toJSONString(child) + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }


    /**
     * 绑定孩子，孩子与家长均已在数据库
     *
     * @param parentOpenid 家长openid
     * @param childOpenid  孩子在数据库中的openid
     * @return BaseResponse
     */
    @RequestMapping(value = "/bond", method = RequestMethod.POST)
    public BaseResponse bond(@RequestParam("parentOpenid") String parentOpenid, @RequestParam("childOpenid") String childOpenid) {
        BaseResponse baseResponse = parentService.bond(parentOpenid, childOpenid);
        logger.info("/bond Request:{\"childOpenid\":" + childOpenid + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 将家长升级为车主，
     * 此接口由审核通过时调用，
     * 家长申请升级为车主使用的见see also
     *
     * @param license 家长的驾驶证信息
     * @return BaseResponse
     * @see VerifyController#addVerify(Verify)
     */
    @RequestMapping(value = "/upgrade", method = RequestMethod.POST)
    public BaseResponse upgrade(@RequestBody License license) {
        BaseResponse baseResponse = parentService.upgrade(license);
        logger.info("/upgrade Request:{\"License\":" + JSON.toJSONString(license) + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 家长添加车辆
     *
     * @param parentOpenid 家长的驾驶证信息
     * @param car          车辆信息对象
     * @return BaseResponse
     */
    @RequestMapping(value = "/addCar/{parentOpenid}", method = RequestMethod.POST)
    public BaseResponse addcar(@PathVariable String parentOpenid, @RequestBody Car car) {
        BaseResponse baseResponse = parentService.addCar(parentOpenid, car);
        logger.info("/addCar Request:{\"Car\":" + JSON.toJSONString(car) + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 用户更新头像地址
     *
     * @param openid    用户openid
     * @param avatarUrl 头像地址
     * @return BaseResponse
     */
    @RequestMapping(value = "/updateAvatarUrl", method = RequestMethod.POST)
    public BaseResponse updateAvatarUrl(@RequestParam("openid") String openid, @RequestParam("avatarUrl") String avatarUrl) {
        BaseResponse baseResponse = parentService.updateAvatarUrl(openid, avatarUrl);
        logger.info("/updateAvatarUrl Request:{\"openid\":" + openid + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 查询家长是否有未处理的升级申请
     *
     * @param openid 用户openid
     * @return BaseResponse
     */
    @RequestMapping(value = "/verifying", method = RequestMethod.POST)
    public BaseResponse verifying(@RequestParam("openid") String openid) {
        BaseResponse baseResponse = parentService.verifying(openid);
        logger.info("/verifying Request:{\"openid\":" + openid + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }
}
