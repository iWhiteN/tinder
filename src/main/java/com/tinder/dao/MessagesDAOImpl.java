package com.tinder.dao;

import com.tinder.config.DataSource;
import com.tinder.model.Message;
import com.tinder.model.MessageSocket;
import com.tinder.model.User;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessagesDAOImpl implements MessagesDAO {
    private static MessagesDAOImpl messagesDAOImpl;
    private final HikariDataSource basicDataSource;

    private MessagesDAOImpl() {
        basicDataSource = DataSource.getDataSource();
    }

    public static MessagesDAOImpl getInstance() {
        if (messagesDAOImpl == null) {
            messagesDAOImpl = new MessagesDAOImpl();
        }
        return messagesDAOImpl;
    }

    @Override
    public List<Optional<Message>> getAllMessagesByMessagesId(int messagesId) throws SQLException {
        List<Optional<Message>> messages = new ArrayList<>();
        Connection connection = basicDataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select m.id,\n" +
                        "       m.datetime_send,\n" +
                        "       m.message,\n" +
                        "       u1.id,\n" +
                        "       u1.nick_name,\n" +
                        "       u1.email,\n" +
                        "       u1.last_connect,\n" +
                        "       u1.hash_pwd,\n" +
                        "       u1.avatar_url,\n" +
                        "       u2.id,\n" +
                        "       u2.nick_name,\n" +
                        "       u2.email,\n" +
                        "       u2.last_connect,\n" +
                        "       u2.hash_pwd,\n" +
                        "       u2.avatar_url\n" +
                        "from messages m\n" +
                        "         left join users u1 on m.id_users_from = u1.id\n" +
                        "         left join users u2 on m.id_users_to = u2.id\n" +
                        "where m.id = " + messagesId + "\n" +
                        "order by m.datetime_send"
        );
        while (resultSet.next()) {
            messages.add(Optional.ofNullable(
                    Message.builder()
                            .messagesId(resultSet.getInt("m.id"))
                            .from(User.builder()
                                    .avatarUrl(resultSet.getString("u1.avatar_url"))
                                    .lastLogin(resultSet.getTimestamp("u1.last_connect"))
                                    .email(resultSet.getString("u1.message"))
                                    .name(resultSet.getString("u1.nick_name"))
                                    .pass(resultSet.getString("u1.hash_pwd"))
                                    .userId(resultSet.getInt("u1.id"))
                                    .build())
                            .to(User.builder()
                                    .avatarUrl(resultSet.getString("u2.avatar_url"))
                                    .lastLogin(resultSet.getTimestamp("u2.last_connect"))
                                    .email(resultSet.getString("u2.message"))
                                    .name(resultSet.getString("u2.nick_name"))
                                    .pass(resultSet.getString("u2.hash_pwd"))
                                    .userId(resultSet.getInt("u2.id"))
                                    .build())
                            .content(resultSet.getString("m.message"))
                            .datetimeSend(resultSet.getTimestamp("m.datetime_send"))
                            .build()));
        }
        statement.close();
        connection.close();
        return messages;
    }

    @Override
    public List<Optional<Message>> getPartMessagesByMessagesIdAndTImeSend(int messagesId, Timestamp timeSend) throws SQLException {
        List<Optional<Message>> messages = new ArrayList<>();
        Connection connection = basicDataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select m.id,\n" +
                        "       m.datetime_send,\n" +
                        "       m.message,\n" +
                        "       u1.id,\n" +
                        "       u1.nick_name,\n" +
                        "       u1.email,\n" +
                        "       u1.last_connect,\n" +
                        "       u1.hash_pwd,\n" +
                        "       u1.avatar_url,\n" +
                        "       u2.id,\n" +
                        "       u2.nick_name,\n" +
                        "       u2.email,\n" +
                        "       u2.last_connect,\n" +
                        "       u2.hash_pwd,\n" +
                        "       u2.avatar_url\n" +
                        "from messages m\n" +
                        "         left join users u1 on m.id_users_from = u1.id\n" +
                        "         left join users u2 on m.id_users_to = u2.id\n" +
                        "where m.id = " + messagesId + "\n" +
                        "and m.datetime_send >= " + timeSend + "\n" +
                        "order by m.datetime_send"
        );
        while (resultSet.next()) {
            messages.add(Optional.ofNullable(
                    Message.builder()
                            .messagesId(resultSet.getInt("m.id"))
                            .from(User.builder()
                                    .avatarUrl(resultSet.getString("u1.avatar_url"))
                                    .lastLogin(resultSet.getTimestamp("u1.last_connect"))
                                    .email(resultSet.getString("u1.message"))
                                    .name(resultSet.getString("u1.nick_name"))
                                    .pass(resultSet.getString("u1.hash_pwd"))
                                    .userId(resultSet.getInt("u1.id"))
                                    .build())
                            .to(User.builder()
                                    .avatarUrl(resultSet.getString("u2.avatar_url"))
                                    .lastLogin(resultSet.getTimestamp("u2.last_connect"))
                                    .email(resultSet.getString("u2.message"))
                                    .name(resultSet.getString("u2.nick_name"))
                                    .pass(resultSet.getString("u2.hash_pwd"))
                                    .userId(resultSet.getInt("u2.id"))
                                    .build())
                            .content(resultSet.getString("m.message"))
                            .datetimeSend(resultSet.getTimestamp("m.datetime_send"))
                            .build()));
        }
        statement.close();
        connection.close();
        return messages;
    }

    @Override
    public void setMessage(int messageId, MessageSocket messageSocket, Timestamp datetimeSend) throws SQLException {
        Connection connection = basicDataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeQuery(
                "insert into messages (id_users_from,\n" +
                        "                      id_users_to,\n" +
                        "                      datetime_send,\n" +
                        "                      id_messages,\n" +
                        "                      message,)\n" +
                        "values (" + messageSocket.getFrom() + ",\n" +
                        "        " + messageSocket.getFrom() + ",\n" +
                        "        " + datetimeSend + ",\n" +
                        "        " + messageId + ",\n" +
                        "        " + messageSocket.getContent() + ")"
        );
    }

    @Override
    public Optional<Integer> getMessagesId(int from, int to) throws SQLException {
        Connection connection = basicDataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select id_messages\n" +
                        "from messages\n" +
                        "where id_users_from = " + from + "\n" +
                        "and id_users_to = " + to
        );
        Optional<Integer> result = Optional.empty();
        while (resultSet.next()) {
            result = Optional.of(resultSet.getInt("id_messages"));
        }
        return result;
    }

    @Override
    public void setMessagesId(int messagesId, int from, int to, Timestamp datetime) throws SQLException {
        Connection connection = basicDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into messages (id_messages, id_users_from, id_users_to, datetime_send) values(?,?,?,?)");
        preparedStatement.setInt(1, messagesId);
        preparedStatement.setInt(2, from);
        preparedStatement.setInt(3, to);
        preparedStatement.setTimestamp(4, datetime);
        preparedStatement.execute();
    }
}