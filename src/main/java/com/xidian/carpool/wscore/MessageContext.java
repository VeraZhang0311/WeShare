package com.xidian.carpool.wscore;


import com.xidian.carpool.wscore.session.SessionManager;

public class MessageContext {

    SessionManager sessionManager;

    MessageDispatcher messageDispatcher;

    MessagePoster messagePoster;

    public MessageContext(SessionManager sessionManager, MessageDispatcher messageDispatcher, MessagePoster messagePoster) {
        this.sessionManager = sessionManager;
        this.messageDispatcher = messageDispatcher;
        this.messagePoster = messagePoster;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public MessageDispatcher getMessageDispatcher() {
        return messageDispatcher;
    }

    public MessagePoster getMessagePoster() {
        return messagePoster;
    }
}
