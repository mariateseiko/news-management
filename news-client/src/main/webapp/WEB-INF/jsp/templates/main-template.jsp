<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <tiles:importAttribute name="title" />
    <title><fmt:message key="page.title.news.portal"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/tiles.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/main.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/multiple-select.css'/>"/>
</head>
<body>
    <div class="page-tile">
        <div class="header-tile">
            <tiles:insertAttribute name="header"/>
        </div>
        <div class="content-tile">
            <div class="body-tile">
                <tiles:insertAttribute name="body"/>
            </div>
        </div>
        <div class="footer-tile">
            <tiles:insertAttribute name="footer"/>
        </div>
    </div>
</body>
</html>
