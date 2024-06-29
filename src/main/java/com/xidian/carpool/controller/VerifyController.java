package com.xidian.carpool.controller;

import com.alibaba.fastjson.JSON;
import com.xidian.carpool.entity.Verify;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.service.VerifyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/verify")
public class VerifyController {
    @Autowired
    VerifyService verifyService;
    private final static Logger logger = Logger.getLogger(VerifyController.class);

    /**
     * 获取所有爱心车主申请记录
     *
     * @return BaseResponse Data:List<Verify>
     */
    @RequestMapping(value = "/queryVerify", method = RequestMethod.POST)
    public BaseResponse queryVerify() {
        BaseResponse baseResponse = verifyService.queryVerify();
        logger.info("/queryVerify Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 对爱心车主的申请进行处理
     *
     * @param status 处理结果 true通过 false拒绝
     * @param id     申请id
     * @return BaseResponse
     */
    @RequestMapping(value = "/confirmVerify", method = RequestMethod.POST)
    public BaseResponse confirmVerify(@RequestParam("status") boolean status, @RequestParam("id") int id) {
        BaseResponse baseResponse = verifyService.confirmVerify(id, status);
        logger.info("/confirmVerify Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 家长申请升级为爱心车主
     *
     * @param verify 申请信息对象
     * @return BaseResponse
     */
    @RequestMapping(value = "/addVerify", method = RequestMethod.POST)
    public BaseResponse addVerify(@RequestBody Verify verify) {
        BaseResponse baseResponse = verifyService.addVerify(verify);
        logger.info("/addVerify Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 家长驾驶证与车辆图片上传接口
     *
     * @param openid 家长openid
     * @param type   上传的哪一种静态资源，一共四种
     * @param file   图片文件
     * @return BaseResponse
     */
    @RequestMapping(value = "/uploadUpgrade", method = RequestMethod.POST)
    public BaseResponse uploadUpgrade(@RequestParam("openid") String openid,
                                      @RequestParam("type") int type,
                                      @RequestParam("file") MultipartFile file) {
        BaseResponse baseResponse = verifyService.saveUploadUpgrade(openid, type, file);
        logger.info("/uploadUpgrade Request:{\"openid\":" + openid + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }
    /**
     * 家长添加车辆图片上传接口
     *
     * @param openid 家长openid
     * @param type   上传的哪一种静态资源，一共三种
     * @param file   图片文件
     * @return BaseResponse
     */
    @RequestMapping(value = "/addCar", method = RequestMethod.POST)
    public BaseResponse addCar(@RequestParam("openid") String openid,
                                      @RequestParam("type") int type,
                                      @RequestParam("file") MultipartFile file) {
        BaseResponse baseResponse = verifyService.saveUploadCar(openid, type, file);
        logger.info("/uploadUpgrade Request:{\"openid\":" + openid + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }
}
