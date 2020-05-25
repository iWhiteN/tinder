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
        System.out.println("onOpen " + session.getId());
        session.getUserProperties().put("id", id);
    }

    @OnMessage
    public void onMessage(Session session, MessageSocket messageSocket) throws SQLException {
        System.out.println("onMessage \n" + messageSocket.toString());

        String messageId = session.getUserProperties().get("id").toString();
        messagesService.setMessage(messageId, messageSocket);
        messagesService.sendMessage(messageSocket, session);

    }

    @OnClose
    public void onClose(Session session) {
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
    }
}