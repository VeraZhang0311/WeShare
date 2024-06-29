package com.xidian.carpool.wscontroller;

import com.xidian.carpool.annotation.WSController;
import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.Message;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.mapper.WSMapper;
import com.xidian.carpool.wscore.MessagePoster;
import com.xidian.carpool.wscore.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WSController("/log")
public class LoginController {
    @Autowired
    SessionManager sessionManager;
    @Autowired
    WSMapper mapper;

    @RequestMapping("/login")
    public BaseResponse login(Map map, WebSocketSession session) {
        String uid = String.valueOf(map.get("uid"));
        if (uid == null || uid.isEmpty()) {
            return new BaseResponse(ConstantCode.UID_NULL);
        }
        sessionManager.connect(uid, session);
        List<Message> offlineMsg = mapper.getOfflineMsg(uid);
        mapper.delOfflineMsh(uid);
        return new BaseResponse(ConstantCode.LOGIN_SUCCESS, offlineMsg);
    }
}
