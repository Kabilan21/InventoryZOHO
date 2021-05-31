<%@ page import="com.webapp.Product"%>
<%@ page import="java.util.ArrayList"%>


<% ArrayList<Product> products =  (ArrayList<Product>) request.getAttribute("products"); %>
<table style=width:100%;>
<tr>
<th>ID</th>
 <th>Product Name</th>
  <th>Product Quantity</th>
  <th>Product Price</th>
  </tr>
 <% for(Product product:products){ %>
    <tr>
    <td> <%= product.id %></td>
    <td> <%= product.name %></td>
    <td> <%= product.price %></td>
    <td> <%= product.quantity %></td>
   </tr>
<%}%>
</table>

