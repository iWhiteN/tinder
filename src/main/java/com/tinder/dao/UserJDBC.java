package com.tinder.dao;

import com.tinder.config.DataSource;
import com.tinder.model.Credentials;
import com.tinder.model.User;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserJDBC  implements UserDAO{
    private static UserJDBC userJDBC;
    private final HikariDataSource basicDataSource;

    private UserJDBC() {
        basicDataSource = DataSource.getDataSource();
    }

    public static UserJDBC getInstance() {
        if (userJDBC == null) {
            userJDBC = new UserJDBC();
        }
        return userJDBC;
    }

    @Override
    public Optional<User> getUserById(int userId) {
        User user = null;
        Connection con = getConnect();

        try {
            Statement stmt = Objects.requireNonNull(con).createStatement();
            String sql = "Select id, nick_name, email, hash_pwd, last_connect, avatar_url from users where id = " + userId;
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                user = User.builder()
                        .userId(resultSet.getInt("id"))
                        .email(resultSet.getString("email"))
                        .name(resultSet.getString("nick_name"))
                        .pass(resultSet.getString("hash_pwd"))
                        .lastLogin(resultSet.getTimestamp("last_connect"))
                        .avatarUrl(resultSet.getString("avatar_url"))
                        .build();
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAllLikedUsersByUserId(int userId) {
        Connection con = getConnect();

        List<User> users = new ArrayList<>();

        try {
            Statement stmt = Objects.requireNonNull(con).createStatement();
            String sql = "Select * from users t1 left join likes t2 on t1.id = t2.id_users_to where t2.id_users_from = " + userId;
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                users.add(
                        User.builder()
                                .userId(resultSet.getInt("id"))
                                .email(resultSet.getString("email"))
                                .name(resultSet.getString("nick_name"))
                                .pass(resultSet.getString("hash_pwd"))
                                .lastLogin(resultSet.getTimestamp("last_connect"))
                                .avatarUrl(resultSet.getString("avatar_url"))
                                .build());
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getAllUsersWithoutLikesByUserId(int userId) {
        Connection con = getConnect();

        List<User> users = new ArrayList<>();

        try {
            Statement stmt = Objects.requireNonNull(con).createStatement();
            String sql = "Select * from users t1 where not exists (select 1 from likes t2 where t1.id = t2.id_users_to) and t1.id <> " + userId;
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                users.add(
                        User.builder()
                        .userId(resultSet.getInt("id"))
                        .email(resultSet.getString("email"))
                        .name(resultSet.getString("nick_name"))
                        .pass(resultSet.getString("hash_pwd"))
                        .lastLogin(resultSet.getTimestamp("last_connect"))
                        .avatarUrl(resultSet.getString("avatar_url"))
                        .build());
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public int addUser(User user) {
        Connection con = getConnect();
        int result = 0;
        try {
            PreparedStatement preparedStatement = Objects.requireNonNull(con).prepareStatement("Insert into users (nick_name, email, hash_pwd, last_connect, avatar_url) values (?, ?, ?, ?, ?) returning id");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPass());
            preparedStatement.setTimestamp(4, new Timestamp(user.getLastLogin().getTime()));
            preparedStatement.setString(5, user.getAvatarUrl());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1);
            preparedStatement.close();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public int getUserByCredentials(Credentials credentials) {
        int id = 0;
        Connection con = getConnect();

        try {
            Statement stmt = Objects.requireNonNull(con).createStatement();
            String sql = "Select id from users where email = " + credentials.getEmail() + " and hash_pwd = " + credentials.getPass();
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    private Connection getConnect () {
        Connection con = null;
        try {
            con = basicDataSource.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }
}
