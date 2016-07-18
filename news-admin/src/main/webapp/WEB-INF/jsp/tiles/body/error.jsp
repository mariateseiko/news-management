<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="centered-form">
    <div class="errorCode">${errorCode}</div>
    <div class="errorMessage">
        <spring:message code="${errorMessage}"/>
    </div>
</div>
