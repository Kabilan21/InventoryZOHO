package com.webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/searchProduct")
public class SearchProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("searchText");
        ProductHandler productHandler = null;
        try {
            productHandler = new ProductHandler();
            ArrayList<Product> products =  productHandler.searchProductByName(name);
            req.setAttribute("products",products);
            if(products.isEmpty()){
                resp.getWriter().println("<h4> No product found in the given name... </h4> ");
            }else{
                req.getRequestDispatcher("products.jsp").forward(req,resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
