<%--
  Created by IntelliJ IDEA.
  User: Тарас
  Date: 25.09.2019
  Time: 1:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>indexAdminPanel</title>
</head>
<body>
<H1>ADMIN MENU</H1>
<a href="${pageContext.request.contextPath}/logout"><br/>Logout</a>
<a href="${pageContext.request.contextPath}/allItemsAdmin"><br/>List Of Item</a>
<a href="${pageContext.request.contextPath}/servlet/getAllUsers"><br/>List Of Users</a>
</body>
</html>
