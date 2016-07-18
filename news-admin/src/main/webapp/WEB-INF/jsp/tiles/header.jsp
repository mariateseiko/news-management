<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header">


    <div class="title"><h1> News Portal</h1></div>
    <div class="lang-panel"> <sec:authorize access="hasRole('ROLE_ADMIN')">
    Hello, <sec:authentication  property="principal.username" />
        <a href="<c:url value="/j_spring_security_logout" />"><button class="logout">Logout</button></a>
        <br/>
        <br/>
    </sec:authorize>
     <div class="lang">EN RU</div></div>
</div>
