package com.tinder.dao;

import com.tinder.enums.TypeLikes;

public class LikesDAOImp implements LikesDAO {
    @Override
    public List<User> getUsersLikes() {
        return null;
    }

    @Override
    public boolean setLike(User userFrom, User userTo, TypeLikes type) {
        return false;
    }
}