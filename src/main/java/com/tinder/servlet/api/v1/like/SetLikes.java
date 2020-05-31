package com.tinder.servlet.api.v1.like;

import com.tinder.service.LikesService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/api/v1/setLikes")
public class SetLikes extends HttpServlet {
    private final LikesService likesService = LikesService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String idFrom = request.getParameter("idFrom");
        String idTo = request.getParameter("idTo");
        String typeLikes = request.getParameter("typeLikes");

        try {
            likesService.setLike(idFrom, idTo, typeLikes);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
