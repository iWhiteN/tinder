package com.tinder.servlet.api.v1.users;

import com.google.gson.Gson;
import com.tinder.model.User;
import com.tinder.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import static com.tinder.utils.CookieReader.readCookie;

@WebServlet("/api/v1/getAllWithoutLikes")
public class GetAllUsersWithoutLikes extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        int userId = readCookie(req, "userId").getAsInt();
        try {
            if (userId == -1) {
                resp.sendError(400, "Wrong user id");
            }
            List<User> allUsersWithoutLikesByUserId = userService.getAllUsersWithoutLikesByUserId(userId);
            String usersJsonString = this.gson.toJson(allUsersWithoutLikesByUserId);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(usersJsonString);
            out.flush();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
