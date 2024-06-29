package com.xidian.carpool.controller;

import com.alibaba.fastjson.JSON;
import com.xidian.carpool.entity.CooperateParentChild;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.service.CooperateParentChildService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cpc")
public class CooperateParentChildController {
    private final static Logger logger = Logger.getLogger(CooperateParentChildController.class);
    @Autowired
    CooperateParentChildService cooperateParentChildService;

    /**
     * 学生加入某订单
     *
     * @param cpc 加入订单的的cpc
     * @return BaseResponse
     */
    @RequestMapping(value = "/joinCooperate", method = RequestMethod.POST)
    public BaseResponse joinCooperate(@RequestBody CooperateParentChild cpc) {
        BaseResponse baseResponse = cooperateParentChildService.joinCooperate(cpc);
        logger.info("/joinCooperate Request:{\"cpc\":" + JSON.toJSONString(cpc) + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 车主或学生对cpc附属订单进行更新
     *
     * @param cpc 被更新的cpc
     * @return BaseResponse
     */
    @RequestMapping(value = "/updateCPC", method = RequestMethod.POST)
    public BaseResponse updateCPC(@RequestBody CooperateParentChild cpc) {
        BaseResponse baseResponse = cooperateParentChildService.updateCPC(cpc);
        logger.info("/updateCPC Request:{\"cpc\":" + JSON.toJSONString(cpc) + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 家长或学生发出拼车申请
     *
     * @param cpc 申请的cpc
     * @return BaseResponse
     */
    @RequestMapping(value = "/applyCooperate", method = RequestMethod.POST)
    public BaseResponse applyCooperate(@RequestBody CooperateParentChild cpc) {
        BaseResponse baseResponse = cooperateParentChildService.applyCooperate(cpc);
        logger.info("/applyCooperate Request:{\"cpc\":" + JSON.toJSONString(cpc) + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 车主对拼车申请进行处理
     *
     * @param applyCPCid 申请的cpc记录ID
     * @param fromState  申请的cpc的原状态
     * @param toState    要将cpc修改为toState状态
     * @return BaseResponse Data:Bool 是否修改成功
     */
    @RequestMapping(value = "/handleApply", method = RequestMethod.POST)
    public BaseResponse handleApply(@RequestParam("id") int applyCPCid, @RequestParam("fromState") int fromState, @RequestParam("toState") int toState) {
        BaseResponse baseResponse = cooperateParentChildService.handleApply(applyCPCid, fromState, toState);
        logger.info("/handleApply Request:{\"cpcid\":" + applyCPCid + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }
}
