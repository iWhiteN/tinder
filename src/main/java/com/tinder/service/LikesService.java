package com.tinder.service;

import com.tinder.dao.LikesDAOImp;
import com.tinder.enums.TypeLikes;

import java.sql.SQLException;

public class LikesService {
    private static LikesService likesService;
    private final LikesDAOImp likesDAOImp;

    private LikesService() {
        likesDAOImp = LikesDAOImp.getInstance();
    }

    public static LikesService getInstance() {
        if (likesService == null) {
            likesService = new LikesService();
        }
        return likesService;
    }

    public void setLike(String from, String to, String typeLikes) throws SQLException {
        int fromParse = Integer.parseInt(from);
        int toParse = Integer.parseInt(to);
        TypeLikes typeLikesParse = typeLikes.equals(TypeLikes.LIKE.getTitle()) ? TypeLikes.LIKE : TypeLikes.DISLIKE;

        likesDAOImp.setLike(fromParse, toParse, typeLikesParse);
    }
}
