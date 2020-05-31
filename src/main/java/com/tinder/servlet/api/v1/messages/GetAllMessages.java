package com.tinder.servlet.api.v1.messages;

import com.tinder.service.MessagesService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/api/v1/getAllMessages")
public class GetAllMessages extends HttpServlet {
    private final MessagesService messagesService = MessagesService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String messagesId = request.getParameter("messagesId");
        try {
            String allMessagesByMessagesId = messagesService.getAllMessagesByMessagesId(messagesId);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(allMessagesByMessagesId);
            out.flush();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
