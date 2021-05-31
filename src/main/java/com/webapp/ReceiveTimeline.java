package com.webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/receive")
public class ReceiveTimeline  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ReceiveHandler receiveHandler = new ReceiveHandler(new StaffHandler(),new ProductHandler());
            req.setAttribute("receives",receiveHandler.getAllData());
            req.getRequestDispatcher("receive_timeline.jsp").forward(req,resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
