<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Quiz Result</title>
</head>

<body>
<%@ include file="header.jsp" %>
<div>

        <c:forEach items="${attempt.attempt_questions}" var="entry" >
            <p><b>Question: ${entry.key.question}</b></p>
            <ul>
                <c:forEach items="${entry.key.choiceList}" var="choice">
                    <li>${choice.answer}</li>
                </c:forEach>
            </ul>
            <p>Your Answer: ${entry.value.answer}</p>
            <c:choose>
                <c:when test = "${entry.value.is_answer}">
                    <p>Your Answer is Correct!</p>
                </c:when>
                <c:otherwise>
                    <p>Your Answer is Wrong! Correct answer is ${entry.key.correct.answer}</p>
                </c:otherwise>
            </c:choose>
            <br/>
        </c:forEach>

    <p><b>Results:</b></p>
    <c:choose>
        <c:when test = "${correct>=2}">
            <p>You passed with the score of ${correct}/3!</p>
        </c:when>
        <c:otherwise>
            <p>You failed with the score with ${correct}/3!</p>
        </c:otherwise>
    </c:choose>
    <form method="get" action="/home">
        <button type="submit">Take another quiz</button>
    </form>
</div>
</body>

</html>