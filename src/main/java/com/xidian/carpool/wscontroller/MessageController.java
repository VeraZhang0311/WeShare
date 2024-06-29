package com.xidian.carpool.wscontroller;

import com.xidian.carpool.annotation.WSController;
import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.wscore.MessagePoster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

@WSController("/msg")
public class MessageController {
    @Autowired
    MessagePoster messagePoster;

    @RequestMapping("/sendToOne")
    public BaseResponse sendToOne(Map map, WebSocketSession session) throws IOException {
        messagePoster.sendToOne(map, "stamp", (String) map.get("toUID"));
        return new BaseResponse(ConstantCode.SEND_SUCCESS, map.get("index"));
    }


    @RequestMapping("/broadcast")
    public void broadcast() {
        System.out.println("msg-----broadcast---------");
        try {
            messagePoster.broadcast(null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
