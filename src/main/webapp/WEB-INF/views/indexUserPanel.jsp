<%--
  Created by IntelliJ IDEA.
  User: Тарас
  Date: 25.09.2019
  Time: 1:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User MENU</title>
</head>
<body>
<H1>USER MENU</H1>
<a href="${pageContext.request.contextPath}/logout"><br/>Logout</a>
<a href="${pageContext.request.contextPath}/servlet/getAllItems"><br/>List Of Item</a>
<a href="${pageContext.request.contextPath}/servlet/bucket"><br/>List Of Items In Bucket</a>
<a href="${pageContext.request.contextPath}/servlet/showAllOrders"><br/>List Of Orders</a>
</body>
</html>
