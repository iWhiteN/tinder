package com.tinder.servlet.api.v1.users;

import com.google.gson.Gson;
import com.tinder.model.Credentials;
import com.tinder.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.tinder.utils.CookieReader.readCookie;

@WebServlet("/api/v1/login")
public class UserLogin extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Credentials credentials = gson.fromJson(req.getReader(), Credentials.class);

        int userId = readCookie(req, "userId").getAsInt();
        if (userId == -1) {
            resp.sendError(400, "Wrong user id");
        }

        boolean result = userService.authorizeUser(userId, credentials);

        if(result) {
            resp.setStatus(200);
        } else {
            resp.setStatus(400);
        }
    }
}
