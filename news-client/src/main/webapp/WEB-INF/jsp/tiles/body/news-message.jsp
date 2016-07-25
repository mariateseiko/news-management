<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>

<div class="link">BACK</div>
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
        <textarea name="text" class="input-comment" rows="5"></textarea>
        <br/>
        <input type="submit" value="Post" class="submit-button"/>
    </form>
    <c:url value="/news/message/${newsDTO.news.previousId}" var="prev"/>
    <c:if test="${not empty newsDTO.news.previousId && newsDTO.news.previousId != 0}"><div class="link prev"><a href="${prev}">PREVIOUS</a></div></c:if>
    <c:url value="/news/message/${newsDTO.news.nextId}" var="next"/>
    <c:if test="${not empty newsDTO.news.nextId && newsDTO.news.nextId != 0}"><div class="link prev next"><a href="${next}">NEXT</a></div></c:if>
</div>