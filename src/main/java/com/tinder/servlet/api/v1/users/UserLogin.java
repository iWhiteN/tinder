package com.tinder.servlet.api.v1.users;

import com.google.gson.Gson;
import com.tinder.model.Credentials;
import com.tinder.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/v1/login")
public class UserLogin extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Credentials credentials = gson.fromJson(req.getReader(), Credentials.class);
            int result = userService.authorizeUser(credentials);
            if (result != 0) {
                Cookie cookie = new Cookie("userId", String.valueOf(result));
                cookie.setMaxAge(30 * 24 * 60 * 60);
                cookie.setPath("/");
                resp.addCookie(cookie);
            } else {
                resp.setStatus(400);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
