<%@ page import="com.webapp.Receive"%>
<%@ page import="com.webapp.Staff"%>
<%@ page import="com.webapp.Product"%>
<%@ page import="java.util.ArrayList"%>

<% ArrayList<Receive> receives =  (ArrayList<Receive>) request.getAttribute("receives"); %>
<table style=width:100%;>
<tr>
<th>ID</th>
 <th>Product Name</th>
  <th>Staff Name</th>
  <th>Receive Quantity</th>
  <th>Time</th>
  </tr>
 <% for(Receive receive:receives){ %>
    <tr>
    <td> <%= receive.id %></td>
    <td> <%= receive.product.name %></td>
    <td> <%= receive.staff.name %></td>
    <td> <%= receive.quantity %></td>
     <td> <%= receive.timestamp %></td>
   </tr>
<%}%>
</table>

