package com.tinder.servlet.api.v1.messages;

import com.google.gson.Gson;
import com.tinder.service.MessagesService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/api/v1/getIdMessages")
public class GetIdMessages extends HttpServlet {
    private final MessagesService messagesService = MessagesService.getInstance();
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String idFrom = request.getParameter("idFrom");
        String idTo = request.getParameter("idTo");
        int messageId;
        try {
            Optional<Integer> messagesIdOptional = messagesService.getMessagesId(idFrom, idTo);
            if (messagesIdOptional.isPresent()) {
                messageId = messagesIdOptional.get();
            } else {
                messageId = messagesService.setMessagesId(idFrom, idTo);
            }
            Map<String, Integer> resultJson = new HashMap<>();
            resultJson.put("messageId", messageId);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(new Gson().toJson(resultJson));
            out.flush();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
