<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Question ${question.id}</title>
</head>

<body>
<%@ include file="header.jsp" %>


<div>
    <form method="post" action="/question-edit">
        <div>
            Suspended:
            <input type="hidden" name="suspended" value="false" />
            <input type="checkbox" name="suspended" value="true"
            ${question.suspended ? 'checked' : ''} />

        </div>
        <div>
            Question:
            <input type="text" name="question" value="${question.question}" placeholder="${question.question}" required/>
        </div>

        <div>
            Choices:
            <c:forEach items="${question.choiceList}" var="choice">
                <div>
                    <input type="radio" id="choice_${choice.id}" name="correctChoiceId" value="${choice.id}"
                        ${choice.id == question.correct.id ? 'checked' : ''} />
                    <input type="text" name="choice_${choice.id}" value="${choice.answer}" placeholder="${choice.answer}" required/>
                </div>
            </c:forEach>
        </div>
        <input hidden="hidden" name="id" value="${question.id}">
        <input hidden="hidden" name="suspended" value="${question.suspended}">
        <button type="submit">Submit</button>
    </form>
</div>
</body>
</html>