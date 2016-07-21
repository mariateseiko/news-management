<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="centered-form" style="width: 500px">
    <div class="errorCode">${errorCode}</div>
    <div class="errorMessage">
        <c:if test="${not empty errorMessage}">
            <spring:message code="${errorMessage}"/>
        </c:if>
        <c:if test="${not empty exception}">
            ${exception}
        </c:if>
    </div>
</div>
