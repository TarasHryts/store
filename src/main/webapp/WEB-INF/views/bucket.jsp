<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Тарас
  Date: 18.09.2019
  Time: 1:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>bucket</title>
</head>
<body>
<h1>Your Bucket Entry</h1>
<h2>
    <a href="/store_war_exploded/servlet/makeOrders">To Order</a>
</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Action</th>

    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>
                <c:out value="${item.id}" />
            </td>
            <td>
                <c:out value="${item.name}" />
            </td>
            <td>
                <c:out value="${item.price}" />
            </td>
            <td>
                <a href="/store_war_exploded/servlet/deleteItemFromBucket?item_id=${item.id}">Delete From Bucket</a>
            </td>

        </tr>
    </c:forEach>
</table>
<a href="/store_war_exploded/servlet/getAllItems">Сontinue Shopping</a>

</body>
</html>
