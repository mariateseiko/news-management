<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <div class="float-left"><fmt:formatDate type="date"
                                                    value="${comment.creationDate}"/></div><br/>
            <div class=comment-text><button class="comment-delete-button">&#10006</button>${comment.commentText}</div>
        </div>
    </c:forEach>
    <form class="comment-form">
        <textarea class="input-comment" rows="5" ></textarea>
        <br/>
        <button class="submit-button">Post</button>
    </form>
</div>