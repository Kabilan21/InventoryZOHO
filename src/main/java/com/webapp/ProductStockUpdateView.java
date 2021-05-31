package com.webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product/update/stock")
public class ProductStockUpdateView extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ProductHandler productHandler = new ProductHandler();
            StaffHandler staffHandler = new StaffHandler();
            Product product = productHandler.getProductByID(Integer.parseInt(req.getParameter("id")));
            ReceiveHandler receiveHandler = new ReceiveHandler(staffHandler,productHandler);
            DispatchHandler dispatchHandler  = new DispatchHandler(staffHandler,productHandler);
            Staff staff =(Staff) req.getSession().getAttribute("staff");
            if(Integer.parseInt(req.getParameter("status")) == 1){
                product.quantity += Integer.parseInt(req.getParameter("quantity"));
                productHandler.updateProduct(product);
                System.out.println(product.toString());
                Receive receive = new Receive(staff,product,Integer.parseInt(req.getParameter("quantity")));
                receiveHandler.insert(receive);
                resp.getWriter().println("added successfully..");
            }else{
                if(product.quantity < Integer.parseInt(req.getParameter("quantity"))){
                    resp.getWriter().println(String.format("Only %d %s were available",product.quantity,product.name));
                }else
                {
                    product.quantity -= Integer.parseInt(req.getParameter("quantity"));
                    productHandler.updateProduct(product);
                    Dispatch dispatch = new Dispatch(staff,product,Integer.parseInt(req.getParameter("quantity")));
                    dispatchHandler.insert(dispatch);
                    resp.getWriter().println("dispatched successfully..");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
