package com.tinder.servlet.api.v1.users;

import com.google.gson.Gson;
import com.tinder.model.User;
import com.tinder.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/v1/newUser")
public class NewUser extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            User user = gson.fromJson(req.getReader(), User.class);
            int id = userService.addUser(user);
            Cookie cookie = new Cookie("userId", String.valueOf(id));
            cookie.setMaxAge(30 * 24 * 60 * 60);
            cookie.setPath("/");
            resp.addCookie(cookie);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
