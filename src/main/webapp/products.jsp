<%@ page import="com.webapp.Product"%>
<%@ page import="java.util.ArrayList"%>

<% ArrayList<Product> products =  (ArrayList<Product>) request.getAttribute("products"); %>
<table style=width:100%;>
<tr>
<th>ID</th>
 <th>Product Name</th>
  <th>Product Quantity</th>
  <th>Product Price (in Rs)</th>
  </tr>
 <% for(Product product:products){ %>
    <tr onclick='setCurrentProduct(<%= product.id %>,"<%= product.name %>",<%= product.price %>,<%= product.quantity %>,"<%= product.description %>")'>
    <td> <%= product.id %></td>
    <td> <%= product.name %></td>
    <td> <%= product.quantity %></td>
    <td> <%= product.price %></td>
    <td><button class="btn btn-light"> update </button></td>
   </tr>
<%}%>
</table>

