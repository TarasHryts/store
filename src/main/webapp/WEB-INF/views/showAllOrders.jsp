<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Тарас
  Date: 18.09.2019
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>showAllOrders</title>
</head>
<body>
<h1>ORDERS</h1>
<table border="1">
    <tr>
        <th>User Id</th>
        <th>User Name</th>
        <th>Order Id</th>
        <th>Action</th>

    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.userId}"/>
            </td>
            <td>
                <c:out value="${user.name}"/>
            </td>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <a href="/shop_war_exploded/servlet/deleteOrder?order_id=${order.id}">Delete Order</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="/shop_war_exploded/servlet/getAllItems">Сontinue Shopping</a>

</body>
</html>
