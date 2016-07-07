<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="link">BACK</div>
<div class="content">
    <div class="news">
        <div class="float-left" style="font-weight: bold">${newsDTO.news.title}</div>
        <div class="float-left author">by ${newsDTO.author.name}</div>
        <div class="float-right"><fmt:formatDate type="date"
                                                 value="${newsDTO.news.creationDate}"/></div>
        <br/>
        <div class="news-short" align="justify">
            ${newsDTO.news.fullText}
        </div>
    </div>
    <c:forEach var="comment" items="${newsDTO.comments}">
        <div class="comment">
            <spring:url value="/comment/delete/${newsDTO.news.id}/${comment.id}" var="deleteUrl" />
            <div class="float-left"><fmt:formatDate type="date"
                                                    value="${comment.creationDate}"/></div><br/>
            <div class=comment-text><form:form cssStyle="margin-bottom: 0" action="${deleteUrl}" method="post"><button class="comment-delete-button"
                                            >&#10006</button>${comment.commentText}</div></form:form>
        </div>
    </c:forEach>
    <c:url var="addUrl" value="/comment/add"/>

    <form:form action="${addUrl}" class="comment-form" modelAttribute="comment" method="post">
        <form:hidden path="newsId" value="${newsDTO.news.id}"/>
        <form:textarea path="commentText" class="input-comment" rows="5" />
        <br/>
        <input type="submit" value="Post" class="submit-button"/>
    </form:form>

    <spring:url value="/news/message/${newsDTO.news.previousId}" var="prev"/>
    <c:if test="${not empty newsDTO.news.previousId && newsDTO.news.previousId != 0}"><div class="link prev"><a href="${prev}">PREVIOUS</a></div></c:if>
    <spring:url value="/news/message/${newsDTO.news.nextId}" var="next"/>
    <c:if test="${not empty newsDTO.news.nextId && newsDTO.news.nextId != 0}"><div class="link prev next"><a href="${next}">NEXT</a></div></c:if>
</div>