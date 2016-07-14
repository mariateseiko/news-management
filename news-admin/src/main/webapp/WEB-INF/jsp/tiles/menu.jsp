<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <ul class="nav">
        <spring:url value="/news/list" var="newsList"/>
        <li><a href="${newsList}">News List</a></li>
        <spring:url value="/news/add" var="newsAdd"/>
        <li><a href="${newsAdd}">Add news</a></li>
        <spring:url value="/author/manage" var="authors"/>
        <li><a href="${authors}">Add/Update Authors</a></li>
        <spring:url value="/tag/manage" var="tags"/>
        <li><a href="${tags}">Add/Update Tags</a></li>
    </ul>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src=<c:url value="/assets/js/menu.js"/>></script>