package com.tinder.dao;

import com.tinder.ConnectionPool.DataSource;
import com.tinder.model.Credentials;
import com.tinder.model.User;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl userDAOImpl;
    private final HikariDataSource basicDataSource;

    private UserDAOImpl() {
        basicDataSource = DataSource.getDataSource();
    }

    public static UserDAOImpl getInstance() {
        if (userDAOImpl == null) {
            userDAOImpl = new UserDAOImpl();
        }
        return userDAOImpl;
    }

    @Override
    public Optional<User> getUserById(int userId) throws SQLException {
        User user = null;
        Connection con = basicDataSource.getConnection();

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
    public List<User> getAllLikedUsersByUserId(int userId) throws SQLException {
        Connection con = basicDataSource.getConnection();

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
    public List<User> getAllUsersWithoutLikesByUserId(int userId) throws SQLException {
        Connection con = basicDataSource.getConnection();

        List<User> users = new ArrayList<>();

        try {
            Statement stmt = Objects.requireNonNull(con).createStatement();
            String sql = "" +
                    "select *\n" +
                    "from users t1\n" +
                    "where id <> " + userId + "\n" +
                    "  and t1.id not in (\n" +
                    "    select t2.id_users_to\n" +
                    "    from likes t2\n" +
                    "    where t2.id_users_from = " + userId + ")";
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
    public int addUser(User user) throws SQLException {
        Connection con = basicDataSource.getConnection();
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
    public int getUserByCredentials(Credentials credentials) throws SQLException {
        int id = 0;
        Connection con = basicDataSource.getConnection();

        try {
            String sql = "Select id from users where email = ? and hash_pwd = ?";
            PreparedStatement stmt = Objects.requireNonNull(con).prepareStatement(sql);
            stmt.setString(1, credentials.getEmail());
            stmt.setString(2, credentials.getPass());
            ResultSet resultSet = stmt.executeQuery();
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
}
