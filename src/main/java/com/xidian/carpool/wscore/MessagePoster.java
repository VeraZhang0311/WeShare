package com.xidian.carpool.wscore;

import cn.hutool.json.JSONObject;
import com.xidian.carpool.entity.Message;
import com.xidian.carpool.mapper.WSMapper;
import com.xidian.carpool.wscore.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 消息发送
 */
public class MessagePoster {
    @Autowired
    WSMapper mapper;
    private final static String default_charset = "UTF-8";
    private final static String stamp_default = "stamp";
    private final static String stamp_broadcast = "broadcast";

    SessionManager sessionManager;

    public MessagePoster(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * 广播，邮戳默认为broadcast
     */
    public void broadcast(Object parameter) throws IOException {
        sendToAll(parameter, stamp_broadcast);
    }

    public void sendMessage(WebSocketSession session, String msg) throws IOException {
        session.sendMessage(new TextMessage(msg.getBytes(default_charset)));
    }

    public void sendToOne(Object parameter, WebSocketSession session) throws IOException {
        sendToOne(parameter, stamp_default, session);
    }

    public void sendToOne(Object parameter, String stamp, WebSocketSession session) throws IOException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("data", parameter);
        jsonObj.put("stamp", stamp);
        sendMessage(session, jsonObj.toString());
    }

    public void sendToOne(Object parameter, String stamp, String id) throws IOException {
        List<WebSocketSession> sessions = sessionManager.getSession(id);
        if (sessions.size() == 0) {
            //存储在mysql中
            Map map = (Map) parameter;
            Message message = new Message((String) map.get("fromUID"), (String) map.get("toUID"), (String) map.get("data"), (int) map.get("cooperateID"));
            mapper.addMessage(message);
        } else {
            for (WebSocketSession tmp : sessions) {
                sendToOne(parameter, stamp, tmp);
            }
        }
    }

    private void sendToAll(Object parameter, String stamp) throws IOException {
        List<WebSocketSession> sessions = sessionManager.getAllSessions();
        for (WebSocketSession session : sessions) {
            sendToOne(parameter, stamp, session);
        }
    }


}
