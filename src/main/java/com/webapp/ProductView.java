package com.webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/product")
public class ProductView extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ProductHandler productHandler = new ProductHandler();
            ArrayList<Product> products = productHandler.getAllProducts();
            req.setAttribute("products",products);
            req.getRequestDispatcher("products.jsp").forward(req,resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ProductHandler productHandler = new ProductHandler();
            Product product = new Product();
            product.name = req.getParameter("name");
            product.price = Integer.parseInt(req.getParameter("price"));
            product.quantity = Integer.parseInt(req.getParameter("quantity"));
            product.description = req.getParameter("name");
           product =  productHandler.insertProduct(product);
           Staff staff =(Staff) req.getSession().getAttribute("staff");
           if(product != null){
               ReceiveHandler receiveHandler = new ReceiveHandler(new StaffHandler(),new ProductHandler());
               Receive receive = new Receive(staff,product,product.quantity);
               receiveHandler.insert(receive);
               resp.getWriter().println("Successfully added...");
           }else{
               resp.getWriter().println("unable to add the product...");
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
