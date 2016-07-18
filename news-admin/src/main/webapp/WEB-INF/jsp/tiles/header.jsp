<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="header">
    <spring:url value="/news/list" var="index"/>
    <a href="${index}"><div class="title"><h1><spring:message code="header.news.admin"/></h1></div></a>
    <div class="lang-panel"> <sec:authorize access="isAuthenticated()">
        <spring:message code="header.hello"/>, <sec:authentication  property="principal.username" />
        <a href="<c:url value="/j_spring_security_logout" />">
            <button class="logout">
                <spring:message code="header.logout"/>
            </button></a>
        <br/>
        <br/>
    </sec:authorize>
     <div class="lang"><a href="?lang=en">EN</a> <a href="?lang=ru">RU</a></div></div>
</div>
