<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<div class="centered-form" style="width: 500px">
    <div class="errorCode">${pageContext.errorData.statusCode}</div>
    <div class="errorMessage">
        <c:if test="${pageContext.errorData.statusCode == 404}">
           <fmt:message key="error.404"/>
        </c:if>
        <c:if test="${pageContext.errorData.statusCode >= 500}">
            <fmt:message key="error.message.500"/>
        </c:if>
    </div>
</div>
