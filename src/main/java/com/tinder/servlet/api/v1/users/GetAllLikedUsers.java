package com.tinder.servlet.api.v1.users;

import com.google.gson.Gson;
import com.tinder.model.User;
import com.tinder.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.tinder.utils.CookieReader.readCookie;

@WebServlet("/api/v1/getAllLikedUsers")
public class GetAllLikedUsers extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = readCookie(req, "userId").getAsInt();
        if (userId == -1) {
            resp.sendError(400, "Wrong user id");
        }
        List<User> allLikedUsersByUserId = userService.getAllLikedUsersByUserId(userId);
        String usersJsonString = this.gson.toJson(allLikedUsersByUserId);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(usersJsonString);
        out.flush();
    }
}
