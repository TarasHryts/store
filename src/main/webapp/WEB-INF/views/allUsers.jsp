<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="users" scope="request" type="java.util.List<mate.academy.shop.model.User>"/>
<jsp:useBean id="greeting" scope="request" type="java.lang.String"/>
<%--
  Created by IntelliJ IDEA.
  User: Тарас
  Date: 17.09.2019
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>
Hello, ${greeting}, Welcome to All Users page!
Users:
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Orders</th>

    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.id}"/>
            </td>
            <td>
                <c:out value="${user.name}"/>
            </td>
            <td>
                <a href="/storeDB_war_exploded/servlet/showAllOrders?user_id=${user.id}">Show All Orders</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
