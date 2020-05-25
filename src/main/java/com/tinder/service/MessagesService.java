package com.tinder.service;

import com.tinder.dao.MessagesDAOImpl;
import com.tinder.model.Message;
import com.tinder.model.MessageSocket;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MessagesService {
    private static MessagesService messagesService;
    private final MessagesDAOImpl messagesDAOImpl;

    private MessagesService() {
        messagesDAOImpl = MessagesDAOImpl.getInstance();
    }

    public static MessagesService getInstance() {
        if (messagesService == null) {
            messagesService = new MessagesService();
        }
        return messagesService;
    }

    public List<Optional<Message>> getAllMessagesByMessagesId(int messagesId) throws SQLException {
        return messagesDAOImpl.getAllMessagesByMessagesId(messagesId);
    }

    public List<Optional<Message>> getPartMessagesByMessagesIdAndTImeSend(int messagesId, Date timeSend) throws SQLException {
        return messagesDAOImpl.getPartMessagesByMessagesIdAndTImeSend(messagesId, timeSend);
    }

    public void setMessage(String messageId, MessageSocket messageSocket) throws SQLException {
        messagesDAOImpl.setMessage(Integer.parseInt(messageId), messageSocket, new Date());
    }

    public void sendMessage(MessageSocket messageSocket, Session session) {
        String messageId = session.getUserProperties().get("id").toString();
        Set<Session> openSessions = session.getOpenSessions();
        openSessions.forEach(ses -> {
            String id = ses.getUserProperties().get("id").toString();
            try {
                if (ses.isOpen() && id.equals(messageId)) {
                    ses.getBasicRemote().sendObject(messageSocket);
                }
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    public Optional<Integer> getMessagesId(String idFrom, String idTo) throws SQLException {
        return messagesDAOImpl.getMessagesId(Integer.parseInt(idFrom), Integer.parseInt(idTo));
    }

    public int setMessagesId(String idFrom, String idTo) throws SQLException {
        messagesDAOImpl.setMessagesId(Integer.parseInt(idFrom + idTo),
                Integer.parseInt(idFrom),
                Integer.parseInt(idTo));

        return Integer.parseInt(idFrom + idTo);
    }
}
