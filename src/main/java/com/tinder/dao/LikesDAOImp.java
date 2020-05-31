package com.tinder.dao;

import com.tinder.ConnectionPool.DataSource;
import com.tinder.enums.TypeLikes;
import com.tinder.model.User;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LikesDAOImp implements LikesDAO {
    private static LikesDAOImp likesDAOImp;
    private final HikariDataSource basicDataSource;

    private LikesDAOImp() {
        basicDataSource = DataSource.getDataSource();
    }

    public static LikesDAOImp getInstance() {
        if (likesDAOImp == null) {
            likesDAOImp = new LikesDAOImp();
        }
        return likesDAOImp;
    }

    @Override
    public void setLike(int from, int to, TypeLikes type) throws SQLException {
        Connection connection = basicDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into likes (id_users_from," +
                        "                  id_users_to," +
                        "                  type_action) values(?,?,?)");
        preparedStatement.setInt(1, from);
        preparedStatement.setInt(2, to);
        preparedStatement.setString(3, type.getTitle());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
}