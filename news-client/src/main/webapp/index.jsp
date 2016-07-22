<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<c:url value="/controller?command=view_list?page=1" var="newsList"/>
<c:redirect url="${newsList}"/>
<tiles:insertDefinition name="newsList"/>