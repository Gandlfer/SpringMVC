<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
</head>

<body>
<%@ include file="header.jsp" %>
<%@include file="message.jsp"%>
<%-- div is for grouping items --%>
<div>
    <form method="post" action="/create-account">
        <div>
            <label>Username</label>
            <input type="email" name="username">
        </div>
        <div>
            <label>Password</label>
            <input type="password" name="password">
        </div>
        <div>
            <label>First Name</label>
            <input type="text" name="firstname">
        </div>
        <div>
            <label>Last Name</label>
            <input type="text" name="lastname">
        </div>
        <button type="submit">Submit</button>
    </form>
</div>
</body>

</html>