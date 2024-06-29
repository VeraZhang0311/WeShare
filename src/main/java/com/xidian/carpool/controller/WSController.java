package com.xidian.carpool.controller;

import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.wscore.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ws")
public class WSController {
    @Autowired
    SessionManager sessionManager;

    /**
     * 获取目前WebSocket在线的openid列表
     *
     * @return BaseResponse Data:Map<String, Integer>
     */
    @RequestMapping("/listClients")
    public BaseResponse index() {
        Map<String, Integer> result = new HashMap<>();
        for (String key : sessionManager.getAllClientIds()) {
            result.put(key, sessionManager.getSession(key).size());
        }
        return new BaseResponse(ConstantCode.SUCCESS, result);
    }
}
