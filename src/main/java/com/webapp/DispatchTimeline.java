package com.webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dispatch")
public class DispatchTimeline extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DispatchHandler dispatchHandler = new DispatchHandler(new StaffHandler(),new ProductHandler());
            req.setAttribute("dispatches",dispatchHandler.getAllData());
            req.getRequestDispatcher("hello.jsp").forward(req,resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
