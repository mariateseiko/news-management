<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}" scope="session"/>
<div class="header">
    <c:url value="/controller?command=view_list&page=1" var="index"/>
    <c:url value="/controller?command=change_locale" var="changeLocale"/>
    <a href="${index}"><div class="title"><h1><fmt:message key="header.news.admin"/></h1></div></a>
    <div class="lang-panel">
        <form action="${changeLocale}" method="post">
            <div class="lang">
                <button class="button-link" name="locale" value="en" type="submit">EN</button>
                <button class="button-link" name="locale" value="ru" type="submit">RU</button>
            </div>
        </form>
    </div>
</div>
