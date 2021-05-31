<%@ page import="com.webapp.Dispatch"%>
<%@ page import="com.webapp.Staff"%>
<%@ page import="com.webapp.Product"%>
<%@ page import="java.util.ArrayList"%>

<% ArrayList<Dispatch> dispatches =  (ArrayList<Dispatch>) request.getAttribute("dispatches"); %>
<table style=width:100%;>
<tr>
<th>ID</th>
 <th>Product Name</th>
  <th>Staff Name</th>
  <th>Dispatch Quantity</th>
   <th>TIme</th>

  </tr>
 <% for(Dispatch dispatch:dispatches){ %>
    <tr>
    <td> <%= dispatch.id %></td>
    <td> <%= dispatch.product.name %></td>
    <td> <%= dispatch.staff.name %></td>
    <td> <%= dispatch.quantity %></td>
    <td> <%= dispatch.timestamp %></td>

   </tr>
<%}%>
</table>

