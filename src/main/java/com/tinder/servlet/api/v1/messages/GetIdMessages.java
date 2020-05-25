package com.tinder.servlet.api.v1.messages;

import com.tinder.service.MessagesService;
import com.tinder.utils.JsonConverterJackson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@WebServlet("/api/v1/getIdMessages")
public class GetIdMessages extends HttpServlet {
    private final MessagesService messagesService = MessagesService.getInstance();
    private final JsonConverterJackson jsonConverterJackson = JsonConverterJackson.getInstance();

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
            Map<String, Integer> resultJson = Map.of("messageId", messageId);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(jsonConverterJackson.toJson(resultJson));
            out.flush();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
