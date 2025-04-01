<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Home</title>
</head>

<body>
<%@ include file="header.jsp" %>
<%@ include file="message.jsp" %>
<div>
    <p>Quiz Categories</p>
    <c:forEach items="${category}" var="obj">
        <div>
            <p>${obj.category}</p>
            <form method="get" action="/quiz">
                <input hidden="hidden" name="id" value="${obj.id}">
                <button>Get Quiz for ${obj.category}</button>
            </form>
        </div>
    </c:forEach>
</div>
<div>
    <p>Recent Quiz</p>
    <c:choose>
        <c:when test="${empty attempts}">
            <p>No Quiz Attempted Before</p>
        </c:when>
        <c:otherwise>
        <table border="1">
            <thead>
            <tr>
                <th>
                    Attempt ID
                </th>
                <th>
                    Attempt Date
                </th>
                <th>
                    Category
                </th>
                <th>
                    Action
                </th>
            </tr>
            </thead>
            <c:forEach items="${attempts}" var="attempt">

                <tr>
                    <td>
                            ${attempt.id}
                    </td>
                    <td>
                            ${attempt.timestamp}
                    </td>
                    <td>
                        <c:set var="categoryid" value="0"/>
                        <c:forEach items="${attempt.attempt_questions}" var="question">
                            <c:set var="category_id" value="${question.key.category_id}"/>
                        </c:forEach>
                            ${categoryMap[category_id]}
                    </td>
                    <td>
                        <form method="get" action="/quiz-result">
                            <input hidden="hidden" name="attempt_id" value="${attempt.id}">
                            <button>View Result</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>