<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quiz Result Management</title>
</head>

<body>
<%@ include file="header.jsp" %>
<div>
    <form method="get" action="/result-management">
        <label for="userId">Filter by User:</label>
        <select name="userId" id="userId">
            <option value="">All Users</option>
            <c:forEach items="${allProfile}" var="user">
                <option value="${user.id}"
                        <c:if test="${user.id == param.userId}">selected</c:if>>
                        ${user.firstname} ${user.lastname}
                </option>
            </c:forEach>
        </select>

        <label for="categoryId">Filter by Category:</label>
        <select name="categoryId" id="categoryId">
            <option value="">All Categories</option>
            <c:forEach items="${allCategory}" var="category">
                <option value="${category.id}"
                        <c:if test="${category.id == param.categoryId}">selected</c:if>>
                        ${category.category}
                </option>
            </c:forEach>
        </select>

        <button type="submit">Filter</button>
    </form>
    <table border="1">
        <thead>
        <tr>
            <th>Taken Time</th>
            <th>Category</th>
            <th>User Full Name</th>
            <th>Score</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageOfAttempts}" var="attempt">
            <tr>
                <td>${attempt.timestamp}</td>

                <td>
                    <c:set var="categoryid" value="0"/>
                    <c:set var="correct" value="0"/>
                    <c:forEach items="${attempt.attempt_questions}" var="question">
                        <c:set var="category_id" value="${question.key.category_id}"/>
                        <c:if test="${question.value.is_answer}">
                            <c:set var="correct" value="${correct+1}"/>
                        </c:if>
                    </c:forEach>
                        ${categoryMap[category_id]}
                </td>

                <td>${profileMap[attempt.profile_id].firstname} ${profileMap[attempt.profile_id].lastname}</td>
                <td>${correct}/3
                </td>
                <td>
                    <form method="get" action="/quiz-result">
                        <input hidden="hidden" name="attempt_id" value="${attempt.id}">
                        <button type="submit">View Result</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Pagination controls -->
    <div>
        <!-- Previous page link -->
        <c:if test="${currentPage > 1}">
            <a href="/result-management?page=${currentPage-1}
                    <c:if test='${not empty param.userId}'> &userId=${param.userId}</c:if>
                    <c:if test='${not empty param.categoryId}'> &categoryId=${param.categoryId}</c:if>">
            Previous</a>
        </c:if>

        <!-- Display page numbers -->
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span>${i}</span> <!-- Current page -->
                </c:when>
                <c:otherwise>
                    <a href="/result-management?page=${i}
                    <c:if test='${not empty param.userId}'> &userId=${param.userId}</c:if>
                    <c:if test='${not empty param.categoryId}'> &categoryId=${param.categoryId}</c:if>">
                    ${i}
                    </a> <!-- Other pages -->
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <!-- Next page link -->
        <c:if test="${currentPage < totalPages}">
            <a href="/result-management?page=${currentPage + 1}
                <c:if test='${not empty param.userId}'> &userId=${param.userId}</c:if>
                <c:if test='${not empty param.categoryId}'> &categoryId=${param.categoryId}</c:if>">

            Next</a>
        </c:if>
    </div>
</div>
</body>
</html>