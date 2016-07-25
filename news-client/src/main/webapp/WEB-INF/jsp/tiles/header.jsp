<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<div class="header">
    <c:url value="/controller?command=view_list&page=1" var="index"/>
    <a href="${index}"><div class="title"><h1><fmt:message key="header.news.admin"/></h1></div></a>
    <div class="lang-panel">
     <div class="lang"><a href="?lang=en">EN</a> <a href="?lang=ru">RU</a></div></div>
</div>
