<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Header/Navbar Section -->
<nav>
    <ul>
        <li><a href="/home">Home</a></li>

        <!-- Login/Logout logic based on session -->
        <c:choose>
            <c:when test="${not empty user}">
                <!-- If user is logged in -->
                <li><a href="/logout">Logout</a></li>
                <c:choose>
                    <c:when test="${user.role.id == 2}">
                        <!-- If user is logged in -->
                        <li><a href="/user-management">User Management Page</a></li>
                        <li><a href="/result-management">Quiz Result Management Page</a></li>
                        <li><a href="/question-management">Question Management Page</a></li>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${sessionScope.prevQuiz and (pageContext.request.requestURI !='/continue-quiz' and pageContext.request.requestURI !='/quiz') }">
                        <!-- If user is logged in -->
                        <li><a href="/continue-quiz">Continue Quiz</a></li>
                    </c:when>
                </c:choose>
            </c:when>
            <c:otherwise>
                <!-- If user is not logged in -->
                <li><a href="/login">Login</a></li>
                <li><a href="/create-account">Register</a></li>
            </c:otherwise>
        </c:choose>

        <!-- Contact Us link -->
        <li><a href="#">Contact Us</a></li>
    </ul>
</nav>