package com.tinder.service;

import com.google.gson.Gson;
import com.tinder.dao.MessagesDAOImpl;
import com.tinder.model.Message;
import com.tinder.model.MessageSocket;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MessagesService {
    private static MessagesService messagesService;
    private final MessagesDAOImpl messagesDAOImpl;
    private final Gson gson;

    private MessagesService() {
        messagesDAOImpl = MessagesDAOImpl.getInstance();
        gson = new Gson();
    }

    public static MessagesService getInstance() {
        if (messagesService == null) {
            messagesService = new MessagesService();
        }
        return messagesService;
    }

    public String getAllMessagesByMessagesId(String messagesId) throws SQLException {
        Optional<List<Message>> allMessagesByMessagesId = messagesDAOImpl.getAllMessagesByMessagesId(Integer.parseInt(messagesId));
        return gson.toJson(allMessagesByMessagesId.get());
    }

    public List<Optional<Message>> getPartMessagesByMessagesIdAndTImeSend(int messagesId, LocalDateTime timeSend) throws SQLException {
        return messagesDAOImpl.getPartMessagesByMessagesIdAndTImeSend(messagesId, Timestamp.valueOf(timeSend));
    }

    public void setMessage(Session session, MessageSocket messageSocket) throws SQLException, IOException, EncodeException {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        Set<Session> openSessions = session.getOpenSessions();
        String messageId = session.getUserProperties().get("id").toString();
        messageSocket.setDatetime(timestamp);

        messagesDAOImpl.setMessage(Integer.parseInt(messageId), messageSocket);

        for (Session ses : openSessions) {
            String id = ses.getUserProperties().get("id").toString();
            if (ses.isOpen() && id.equals(messageId)) {
                ses.getBasicRemote().sendObject(messageSocket);
            }

        }
    }

    public Optional<Integer> getMessagesId(String idFrom, String idTo) throws SQLException {
        return messagesDAOImpl.getMessagesId(Integer.parseInt(idFrom), Integer.parseInt(idTo));
    }

    public int setMessagesId(String idFrom, String idTo) throws SQLException {
        messagesDAOImpl.setMessagesId(Integer.parseInt(idFrom + idTo),
                Integer.parseInt(idFrom),
                Integer.parseInt(idTo), Timestamp.valueOf(LocalDateTime.now()));

        return Integer.parseInt(idFrom + idTo);
    }
}
