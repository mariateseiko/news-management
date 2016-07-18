<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
    <c:url var="loginUrl" value="/j_spring_security_check"/>
    <form class="centered-form" action="${loginUrl}" method="post">
        <c:if test="${not empty errorMessage}">
            <div class="error">
                <spring:message code="${errorMessage}"/>
            </div>
        </c:if>
        <div class="form-element">
            <div class="form-label"><spring:message code="index.login"/></div>
            <input type="text" name="username"/>
        </div>
        <div class="form-element">
            <div class="form-label"><spring:message code="index.password"/></div>
            <input type="password" name="password"/>
        </div>
        <button class="submit-button" type="submit"><spring:message code="index.signin"/></button>
    </form>
</div>