package com.tinder.servlet.api.v1.users;

import com.google.gson.Gson;
import com.tinder.model.User;
import com.tinder.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

@WebServlet("/users/getAllWithoutLikes")
public class GetAllUsersWithoutLikes extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = readCookie(req, "userId").getAsInt();
        if (userId == -1) {
            resp.sendError(500, "Wrong user id");
        }
        List<User> allUsersWithoutLikesByUserId = userService.getAllUsersWithoutLikesByUserId(1);
        String usersJsonString = new Gson().toJson(allUsersWithoutLikesByUserId);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(usersJsonString);
        out.flush();
    }

    public OptionalInt readCookie(HttpServletRequest request, String key) {
        return OptionalInt.of(Arrays.stream(request.getCookies())
                .filter(c -> key.equals(c.getName()))
                .map(Cookie::getValue)
                .mapToInt(Integer::parseInt)
                .findAny()
                .orElse(-1));
    }
}
