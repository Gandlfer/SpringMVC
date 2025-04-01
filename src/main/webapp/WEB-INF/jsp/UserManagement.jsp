<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>User Management</title>
</head>

<body>
<%@ include file="header.jsp" %>
<div>
    <table border="1">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
            <th>Role</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageOfUsers}" var="user">
            <tr>
                <td>${user.firstname}</td>
                <td>${user.lastname}</td>
                <td>${user.username}</td>
                <td>${user.role.role}</td>
                <td>
                <form method="post" action="/suspend-user">
                    <input type="hidden" name="page" value="${currentPage}">
                    <input type="hidden" name="profile_id" value="${user.id}">
                    <button type="submit">
                    <c:choose>
                        <c:when test="${user.is_suspended}">
                            Suspended
                        </c:when>
                        <c:otherwise>
                            Active
                        </c:otherwise>
                    </c:choose>
                    </button>
                </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Pagination controls -->
    <div >
        <!-- Previous page link -->
        <c:if test="${currentPage > 1}">
            <a href="/user-management?page=${currentPage-1}">Previous</a>
        </c:if>

        <!-- Display page numbers -->
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span>${i}</span> <!-- Current page -->
                </c:when>
                <c:otherwise>
                    <a href="/user-management?page=${i}">${i}</a> <!-- Other pages -->
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <!-- Next page link -->
        <c:if test="${currentPage < totalPages}">
            <a href="/user-management?page=${currentPage + 1}">Next</a>
        </c:if>
    </div>
</div>
</body>
</html>