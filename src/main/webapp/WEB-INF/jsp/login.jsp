<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<html>
<head>
    <title>Login</title>
</head>

<body>
<%@ include file="header.jsp" %>
<%@ include file="message.jsp" %>
<%-- div is for grouping items --%>
<div>
    <form method="post" action="/login">
        <div>
            <label>Username</label>
            <input type="email" name="username" required>
        </div>
        <div>
            <label>Password</label>
            <input type="password" name="password" required>
        </div>
        <button type="submit">Submit</button>
    </form>

    <a href="http://localhost:8080/create-account">Create Account</a>

</div>
</body>

</html>