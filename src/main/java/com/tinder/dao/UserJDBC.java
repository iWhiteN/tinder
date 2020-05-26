package com.tinder.dao;

import com.tinder.config.DataSource;
import com.tinder.model.User;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public User getUserById() {
        return null;
    }

    @Override
    public List<User> getAllLikedUsersByUserId(int userId) {
        return null;
    }

    @Override
    public List<User> getAllUsersWithoutLikesByUserId(int userId) {
        System.out.println(userId);
        Connection con = null;
        try {
            con = basicDataSource.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        List<User> users = new ArrayList<>();

        try {
            Statement stmt = Objects.requireNonNull(con).createStatement();
            String sql = "Select * from users t1 left join likes t2 on t1.id = t2.id_users_from where t2.id_users_to is null and t2.id_users_from = " + userId;
            System.out.println(sql);
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User(resultSet.getInt("id"), resultSet.getString("nick_name"),resultSet.getString("email"), resultSet.getString("hash_pwd"), resultSet.getTimestamp("last_connect").toInstant(), resultSet.getString("avatar_url"));
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("Users ->" + users);
        return users;
    }

    @Override
    public void addUser(User user) {

    }
}
