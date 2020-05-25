package com.tinder.dao;

import com.tinder.model.Message;
import com.tinder.model.MessageSocket;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MessagesDAO {
    List<Optional<Message>> getAllMessagesByMessagesId(int messagesId) throws SQLException;

    List<Optional<Message>> getPartMessagesByMessagesIdAndTImeSend(int messagesId, Date timeSend) throws SQLException;

    void setMessage(int messagesId, MessageSocket messageSocket, Date datetimeSend) throws SQLException;

    Optional<Integer> getMessagesId(int from, int to) throws SQLException;

    void setMessagesId(int messagesId, int from, int to) throws SQLException;
}
