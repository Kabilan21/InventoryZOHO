package com.webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomePage extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Staff staff = null;
        StaffHandler staffHandler = null;
        try {
            staffHandler = new StaffHandler();
            Cookie cookies[] = req.getCookies();
            for (Cookie c:cookies
            ) {
                if(c.getName().equals("staffID")){
                    staff = staffHandler.getStaffByID(Integer.parseInt(c.getValue()));

                }

            }
        if(staff == null){
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }else{
            req.getSession().setAttribute("staff",staff);
            req.setAttribute("staff",staff.name);
            req.getRequestDispatcher("home.jsp").forward(req,resp);
        }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
