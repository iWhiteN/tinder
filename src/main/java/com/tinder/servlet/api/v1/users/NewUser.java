package com.tinder.servlet.api.v1.users;

import com.google.gson.Gson;
import com.tinder.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/v1/newUser")
public class NewUser extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        User user = User.builder()
//                .build();
//        req.getReader().read()
//        User user = gson.fromJson(c, User.class);
    }
}
