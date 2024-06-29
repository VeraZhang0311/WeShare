package com.xidian.carpool.wscore.task;

import cn.hutool.cron.task.Task;
import com.xidian.carpool.wscore.MessagePoster;
import com.xidian.carpool.wscore.session.DistributeSessionManager;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class DistributeMessageTask implements Task {

    DistributeSessionManager distributeSessionManager;

    MessagePoster messagePoster;

    public DistributeMessageTask(DistributeSessionManager distributeSessionManager, MessagePoster messagePoster) {
        this.distributeSessionManager = distributeSessionManager;
        this.messagePoster = messagePoster;
    }


    @Override
    public void execute() {

        Set<String> localIds = distributeSessionManager.getLocalClientIds();
        for(String id : localIds){
            if(distributeSessionManager.existsDistributeMessage(id)){
                WebSocketSession session = distributeSessionManager.getLocalSessions(id);
                List<String> msgList = distributeSessionManager.consumeDistributeMessage(id, 10);
                if(session != null && msgList.size() > 0){
                    for(String msg : msgList){
                        try {
                            messagePoster.sendMessage(session, msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
