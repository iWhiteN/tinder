package com.tinder.dao;

import com.tinder.model.Message;
import com.tinder.model.MessageSocket;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MessagesDAO {
    List<Optional<Message>> getAllMessagesByMessagesId(int messagesId) throws SQLException;

    List<Optional<Message>> getPartMessagesByMessagesIdAndTImeSend(int messagesId, Timestamp timeSend) throws SQLException;

    void setMessage(int messagesId, MessageSocket messageSocket, Timestamp datetimeSend) throws SQLException;

    Optional<Integer> getMessagesId(int from, int to) throws SQLException;

    void setMessagesId(int messagesId, int from, int to,  Timestamp datetime) throws SQLException;
}
