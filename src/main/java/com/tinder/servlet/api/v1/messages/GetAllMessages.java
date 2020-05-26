package com.tinder.servlet.api.v1.messages;

import com.tinder.model.Message;
import com.tinder.service.MessagesService;
import com.tinder.utils.JsonConverterJackson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/api/v1/getAllMessages")
public class GetAllMessages extends HttpServlet {
    private final MessagesService messagesService = MessagesService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String messagesId = request.getParameter("messagesId");
        try {
            String allMessagesByMessagesId = messagesService.getAllMessagesByMessagesId(messagesId);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(allMessagesByMessagesId);
            out.flush();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
