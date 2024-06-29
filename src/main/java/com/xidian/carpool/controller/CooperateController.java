package com.xidian.carpool.controller;

import com.alibaba.fastjson.JSON;
import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.Cooperate;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.service.CooperateService;
import com.xidian.carpool.service.InfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cooperate")
public class CooperateController {
    private final static Logger logger = Logger.getLogger(CooperateController.class);
    @Autowired
    CooperateService cooperateService;
    @Autowired
    InfoService infoService;

    /**
     * 车主新建拼车订单
     *
     * @param cooperate 创建的拼车订单
     * @return BaseResponse
     */
    @RequestMapping(value = "/newCooperate", method = RequestMethod.POST)
    public BaseResponse newCooperate(@RequestBody Cooperate cooperate) {
        BaseResponse baseResponse = cooperateService.newCooperate(cooperate);
        logger.info("/newCooperate Request:{\"Cooperate\":" + JSON.toJSONString(cooperate) + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 车主更新拼车订单，主要是对订单状态和定位进行更新
     *
     * @param cooperate 被更新的拼车订单
     * @return BaseResponse
     */
    @RequestMapping(value = "/updateCooperate/{openid}", method = RequestMethod.POST)
    public BaseResponse updateCooperate(@PathVariable String openid, @RequestBody Cooperate cooperate) {
        BaseResponse baseResponse = cooperateService.updateCooperate(openid, cooperate);
        logger.info("/updateCooperate Request:{\"Cooperate\":" + JSON.toJSONString(cooperate) + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 通过订单id获取订单详情
     *
     * @param id 被更新的拼车订单
     * @return BaseResponse Data:CooperateView
     */
    @RequestMapping(value = "/getCooperate/{id}", method = RequestMethod.POST)
    public BaseResponse getCooperate(@PathVariable int id) {
        BaseResponse baseResponse = cooperateService.getCooperatesByID(id);
        logger.info("/getCooperate Request:{\"id\":" + id + "},Response:" + JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * 通过订单id列表获取订单详情，同时通过openid列表获得用户详细信息
     *
     * @param idList     拼车订单的id列表
     * @param openidList 用户openid列表
     * @return BaseResponse Data:Map<String, Object>
     */
    @RequestMapping(value = "/getCooperateList", method = RequestMethod.POST)
    public BaseResponse getCooperateList(@RequestParam("idList") List<Integer> idList, @RequestParam("openidList") List<String> openidList) {
        Map<String, Object> map = new HashMap<>();
        map.put("cooperateList", cooperateService.getCooperatesByID(idList).getData());
        map.put("userList", infoService.getBaseUserInfo(openidList).getData());
        logger.info("/getCooperateList Request:{\"idList\":" + idList + "}");
        return new BaseResponse(ConstantCode.SUCCESS, map);
    }
}
