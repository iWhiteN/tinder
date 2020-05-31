package com.tinder.servlet.api.v1.users;

import com.google.gson.Gson;
import com.tinder.model.Credentials;
import com.tinder.model.User;
import com.tinder.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/api/v1/login")
public class UserLogin extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Credentials credentials = gson.fromJson(req.getReader(), Credentials.class);

        Optional<User> user = userService.authorizeUser(credentials);

        if (user.isPresent()) {
            Cookie cookie = new Cookie("userId", String.valueOf(user.get().getUserId()));
            cookie.setMaxAge(30 * 24 * 60 * 60);
            cookie.setPath("/");
            resp.addCookie(cookie);

            Cookie authCookie = new Cookie("auth", "true");
            authCookie.setPath("/");
            resp.addCookie(authCookie);
            resp.setStatus(200);
        } else {
            resp.setStatus(400);
        }
    }
}
