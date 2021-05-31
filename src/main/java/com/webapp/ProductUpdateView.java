package com.webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product/update")
public class ProductUpdateView extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ProductHandler productHandler = new ProductHandler();
            Product product = productHandler.getProductByID(Integer.parseInt(req.getParameter("id")));
            if( Integer.parseInt(req.getParameter("delete")) == 1){
                productHandler.removeProductByID(product.id);
                resp.getWriter().println("Product removed Successfully ...");
            }else{
                product.description  = req.getParameter("description");
                product.name = req.getParameter("name");
                product.price = Integer.parseInt(req.getParameter("price")) ;
                System.out.println(product.toString());
                if(product.id == -1){
                    resp.getWriter().println("Product might be deleted...");
                }else{
                    productHandler.updateProduct(product);
                    resp.getWriter().println("Updated product successfully...");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Unsuccessful...");
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("id"));
    }
}
