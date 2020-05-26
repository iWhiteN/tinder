package com.tinder.service;

import com.tinder.dao.LikesDAOImp;
import com.tinder.enums.TypeLikes;
import com.tinder.utils.JsonConverterJackson;

import java.sql.SQLException;

public class LikesService {
    private static LikesService likesService;
    private final LikesDAOImp likesDAOImp;
    private final JsonConverterJackson jsonConverterJackson;

    private LikesService() {
        likesDAOImp = LikesDAOImp.getInstance();
        jsonConverterJackson = JsonConverterJackson.getInstance();
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
