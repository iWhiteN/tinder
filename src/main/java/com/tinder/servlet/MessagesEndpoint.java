package com.tinder.servlet;

import com.tinder.model.MessageSocket;
import com.tinder.service.MessagesService;
import com.tinder.utils.MessageSocketDecoder;
import com.tinder.utils.MessageSocketEncoder;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.sql.SQLException;

@ServerEndpoint(
        value = "/messages/{id}",
        decoders = MessageSocketDecoder.class,
        encoders = MessageSocketEncoder.class)
public class MessagesEndpoint {
    private final MessagesService messagesService = MessagesService.getInstance();

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        session.getUserProperties().put("id", id);
    }

    @OnMessage
    public void onMessage(Session session, MessageSocket messageSocket) throws SQLException {
        messagesService.setMessage(session, messageSocket);
    }

    @OnClose
    public void onClose(Session session) {
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
    }
}