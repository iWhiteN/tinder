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
import java.util.Optional;

@WebServlet("/api/v1/getUserById")
public class GetUserById extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Optional<User> user = userService.getUserById(Integer.parseInt(req.getParameter("id")));
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if (user.isPresent()) {
            String usersJsonString = this.gson.toJson(user);
            out.print(usersJsonString);
        } else {
            resp.setStatus(400);
            out.println("id not found");
        }
        out.flush();
    }
}
