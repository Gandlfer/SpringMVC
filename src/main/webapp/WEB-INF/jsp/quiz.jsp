
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quiz</title>
</head>

<body>
<%@ include file="header.jsp" %>
<div>

    <form method="post" action="/quiz">
        <c:forEach items="${sessionScope.questions}" var="question" varStatus="loop">
            <p>${question.question}</p>
            <%-- Dynamically render the choices --%>
            <c:forEach items="${question.choiceList}" var="choice" varStatus="loop">
                <div>
                    <input type="radio"
                           name="selectedChoiceId_${question.id}"
                           value="${choice.id}"
                           required
                    />

                        ${choice.answer}
                </div>
            </c:forEach>

        </c:forEach>

        <%-- Button to submit quiz --%>
        <button type="submit">submit</button>

    </form>
</div>
</body>
</html>
