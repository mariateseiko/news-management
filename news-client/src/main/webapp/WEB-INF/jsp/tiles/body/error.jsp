<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<div class="centered-form" style="width: 500px">
    <div class="errorCode">${errorCode}</div>
    <div class="errorMessage">
        <c:if test="${not empty errorMessage}">
            <fmt:message key="${errorMessage}"/>
        </c:if>
        <c:if test="${not empty exception}">
            ${exception}
        </c:if>
    </div>
</div>
