<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add new Quiz</title>
</head>

<body>
<%@ include file="header.jsp" %>


<div>
    <form method="post" action="/add-question">
        <div>
            Suspended:
            <input type="hidden" name="suspended" value="false" />
            <input type="checkbox" name="suspended" value=true />

        </div>
        <div>
            Category Type:
            <select name="category_id" required>
                <c:forEach items="${categoryList}" var="category">
                    <option value="${category.id}">${category.category}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            Question:
            <input type="text" name="question"  placeholder="Enter your question" required/>
        </div>

        <div>
            Choices:
            <c:forEach var="i" begin="1" end="4">
                <div>
                    <input type="radio" name="correctChoice" value="${i}" required/>
                    <input type="text" name="choice_${i}" placeholder="Choice ${i}" required/>
                </div>
            </c:forEach>
        </div>
        <button type="submit">Submit</button>
    </form>
</div>
</body>
</html>