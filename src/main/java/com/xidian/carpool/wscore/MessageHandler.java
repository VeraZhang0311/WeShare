package com.xidian.carpool.wscore;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.logging.Logger;


public class MessageHandler extends TextWebSocketHandler {

    private final String charset = "UTF-8";

    Logger logger = Logger.getLogger(MessageHandler.class.getName());

    MessageContext messageContext;

    public MessageHandler(MessageContext messageContext) {
        this.messageContext = messageContext;
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // TODO Auto-generated method stub
        String msg = message.getPayload();
        logger.info("handlerText===========>" + msg);

        JSONObject result = null;
        Object stamp = null;    //时间戳，用来标识返回结果

        //json格式，需要处理特殊字符问题
        if (JSONUtil.isJson(msg)) {
            JSONObject json = JSONUtil.parseObj(msg);
            stamp = json.get("stamp");
            result = this.messageContext.getMessageDispatcher().dispatch(json, session);
        }

        String response;

        if (result == null) {
            response = "404";
        } else {
            result.put("stamp", stamp);
            response = String.valueOf(result);
        }

        session.sendMessage(new TextMessage(response.getBytes(charset)));
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        this.messageContext.getSessionManager().disconnect(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        this.messageContext.getSessionManager().disconnect(session);
    }
}
