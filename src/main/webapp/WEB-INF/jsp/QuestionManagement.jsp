<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Question Management</title>
</head>

<body>
<%@ include file="header.jsp" %>

<div>
    <table border="1">
        <thead>
        <tr>
            <th>Category</th>
            <th>Question Description</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${categoryList}" var="category">
            <c:forEach items="${quizList}" var="quiz">
                <!-- Display questions that belong to this category -->
                <c:if test="${quiz.category_id == category.id}">
                    <tr>
                        <td>${category.category}</td>
                        <td>${quiz.question}</td>
                        <td>
                            <form method="get" action="/question-edit">
                                <input hidden="hidden" name="quiz_id" value="${quiz.id}">
                                <button type="submit">Edit</button>
                            </form>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>

        </c:forEach>
        </tbody>
    </table>
    <form method="get" action="/add-question">
        <button type="submit">Add New Question</button>
    </form>
</div>
</body>
</html>