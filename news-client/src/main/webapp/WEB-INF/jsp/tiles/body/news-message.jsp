<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>

<c:set value="${param.page}" var="page"/>
<c:if test="${empty param.page}">
    <c:set value="1" var="page"/>
</c:if>
<c:url value="/controller?command=view_list&page=${page}" var="back"/>
<div class="link"><a href="${back}"><fmt:message key="button.back"/></a></div>
<div class="content">
    <div class="news">
        <div class="float-left" style="font-weight: bold"><c:out value="${newsDTO.news.title}"/></div>
        <div class="float-left author">
            <fmt:message key="label.by"/>
            <c:out value="${newsDTO.author.name}"/>
        </div>
        <div class="float-right"><fmt:formatDate type="date"
                                                 value="${newsDTO.news.creationDate}"/></div>
        <br/>
        <div class="news-short" align="justify">
            <c:out value="${newsDTO.news.fullText}"/>
        </div>
    </div>
    <c:forEach var="comment" items="${newsDTO.comments}">
        <div class="comment">
            <div class="float-left"><fmt:formatDate type="date"
                                                    value="${comment.creationDate}"/></div><br/>
            <div class=comment-text>
                <c:out value="${comment.commentText}"/>
            </div>
        </div>
    </c:forEach>
    <c:url var="addUrl" value="/controller?command=post_comment"/>
    <form action="${addUrl}" class="comment-form" method="post">
        <input type="hidden" name="newsId" value="${newsDTO.news.id}"/>
        <textarea name="text" class="input-comment" rows="5" maxlength="100"></textarea>
        <br/>
        <input type="submit" value="Post" class="submit-button"/>
    </form>
    <c:url value="/controller?command=view_news&id=${newsDTO.news.previousId}" var="prev"/>
    <c:if test="${not empty newsDTO.news.previousId && newsDTO.news.previousId != 0}"><div class="link prev"><a href="${prev}">PREVIOUS</a></div></c:if>
    <c:url value="/controller?command=view_news&id=${newsDTO.news.nextId}" var="next"/>
    <c:if test="${not empty newsDTO.news.nextId && newsDTO.news.nextId != 0}"><div class="link prev next"><a href="${next}">NEXT</a></div></c:if>
</div>