package com.tinder.dao;

import com.tinder.enums.TypeLikes;

import java.sql.SQLException;

public interface LikesDAO {
    void setLike(int from, int to, TypeLikes type) throws SQLException;
}
