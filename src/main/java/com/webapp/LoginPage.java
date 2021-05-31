package com.webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "login",urlPatterns ="/login" )
public class LoginPage extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String staffID = null;
        Cookie cookies[] = req.getCookies();
        for (Cookie c:cookies
        ) {
            if(c.getName().equals("staffID")){
                staffID = c.getValue();
            }
        }
        if(staffID != null){
            try {
                StaffHandler staffHandler = new StaffHandler();
                Staff staff = staffHandler.getStaffByID(Integer.parseInt(staffID));
                req.getSession().setAttribute("staff",staff);
                resp.sendRedirect("home");

            } catch (Exception e) {
                e.printStackTrace();
            }


        }else
        {
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StaffHandler staffHandler = new StaffHandler();
            Staff staff = new Staff();
            staff.name = req.getParameter("username");
            staff.password = req.getParameter("password");
            staff =  staffHandler.auth(staff);
            if(staff != null){
                resp.getWriter().print("logged in...");
                Cookie cookie = new Cookie("staffID", String.valueOf(staff.id));
                resp.addCookie(cookie);
                req.getSession().setAttribute("staff",staff);
                req.getRequestDispatcher("main.html").forward(req,resp);
            }
            else {
                resp.getWriter().print("couldn't find account with the given credentials...");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
