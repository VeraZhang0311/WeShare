package com.xidian.carpool.wscore.session;

import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.mapper.WSMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一个用户对应一个session
 */
public class SingleSessionManager implements SessionManager {

    @Autowired
    WSMapper mapper;
    protected ConcurrentHashMap<String, WebSocketSession> clientSessions = new ConcurrentHashMap();


    @Override
    public List<WebSocketSession> getSession(String id) {
        List<WebSocketSession> sessions = new ArrayList<>();
        if (clientSessions.containsKey(id)) {
            sessions.add(clientSessions.get(id));
        }
        return sessions;
    }

    @Override
    public List<WebSocketSession> getAllSessions() {
        return new ArrayList<>(clientSessions.values());
    }

    @Override
    public Set<String> getAllClientIds() {
        return new HashSet<>(clientSessions.keySet());
    }

    @Override
    public boolean connect(String id, WebSocketSession session) {
        if (!clientSessions.containsKey(id)) {
            clientSessions.put(id, session);
        }
        //从数据库拿取此人不在线时的消息数据
        return true;
    }


    @Override
    public boolean disconnect(WebSocketSession session) {
        if (clientSessions.containsValue(session)) {
            Iterator<Map.Entry<String, WebSocketSession>> iterator = clientSessions.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, WebSocketSession> entry = iterator.next();
                if (entry.getValue().getId().equals(session.getId())) {
                    removeClientSession(entry.getKey());
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public boolean disconnect(String id) {
        if (clientSessions.containsKey(id)) {
            removeClientSession(id);
        }
        return true;
    }

    @Override
    public void clearAll() {
        Iterator<WebSocketSession> iterator = clientSessions.values().iterator();
        while (iterator.hasNext()) {
            try {
                iterator.next().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        clientSessions.clear();
    }

    private boolean removeClientSession(String id) {
        WebSocketSession session = clientSessions.remove(id);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
