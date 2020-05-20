package com.tinder.websocket;

import com.tinder.model.Message;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;

@ServerEndpoint(
        value = "/chat/{username}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class)
public class ChatEndpoint {

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {
        session.getUserProperties().put("userName", username);
//        Message message = new Message();
//        message.setFrom(userName);
//        message.setContent("Connected!");
//        sendMessageForAll(message, session);
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException {
        message.setFrom(session.getUserProperties().get("userName").toString());
        sendMessageForAll(message, session);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
//        Message message = new Message();
//        message.setFrom(session.getUserProperties().get("userName").toString());
//        message.setContent("Disconnected!");
//        sendMessageForAll(message, session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
    }

    private void sendMessageForAll(Message message, Session session) {
        Set<Session> openSessions = session.getOpenSessions();
        openSessions.forEach(s -> {
            try {
                if (s.isOpen()) {
                    s.getBasicRemote().sendObject(message);
                }
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });

    }

}