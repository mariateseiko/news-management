<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<c:url value="/news/filter" var="newsFilter"/>
<c:url value="/news/delete" var="newsDelete"/>
<form:form action="${newsFilter}"  modelAttribute="searchCriteria" method="get" id="filter" >
    <div class="filter">
        <form:select path="authors" modelAttribute="authors" multiple="1" id="authors" >
            <form:options items="${allAuthors}"  itemValue="id" itemLabel="name"/>
        </form:select>
        <form:select id="dropdown" modelAttribute="tags" class="dropdown"  multiple="multiple" path="tags">
            <form:options items="${allTags}"  itemValue="id" itemLabel="name"/>
        </form:select>
        <form:button name="page" value="1" type="submit">Filter</form:button>
        <input type="submit" name="reset" value="Reset"/>
    </div>
</form:form>
<div class="content">
    <form:form action="${newsDelete}"  method="post">
    <c:forEach var="newsDTO" items="${newsDTOList}">
        <div class="news">
            <spring:url value="/news/message/${newsDTO.news.id}" var="viewMessage"/>
            <div class="float-left" style="font-weight: bold">
                <a href="${viewMessage}">${newsDTO.news.title}</a>
            </div>
            <div class="float-left author">by ${newsDTO.author.name}</div>
            <div class="float-right"><fmt:formatDate type="date"
                                                     value="${newsDTO.news.creationDate}"/></div>
            <br/>
            <div class="news-short">
                    ${newsDTO.news.shortText}
            </div>
            <spring:url value="/news/edit/${newsDTO.news.id}" var="editMessage"/>
            <input class="float-right link" type = "checkbox" name = "selectedNews" value="${newsDTO.news.id}" />
            <div class="float-right link" ><a href="${editMessage}">Edit</a></div>
            <%--<form:checkbox path="selectedNews" value="${newsDTO.news.id}"/>--%>
            <div class="float-right comments">Comments(${newsDTO.commentCount})</div>
            <div class="float-right tags">
                <c:forEach var="tag" items="${newsDTO.tags}" varStatus="loop">
                    ${tag.name}
                    <c:if test="${!loop.last}">,</c:if>
                </c:forEach>
            </div>
        </div>
    </c:forEach>
        <button type="submit" class="submit-button" style="margin-right: 15px">Delete</button>
    </form:form>
</div>
<c:url value="/news/filter" var="newsFilters"/>
<c:if test="${filtered}">
<div class="pagination">
    <form:form action="${newsFilters}"  modelAttribute="searchCriteria" method="get" id="filter" >
        <div class="hidden">
            <form:select path="authors" modelAttribute="authors" multiple="1" id="authors" type="hidden">
                <form:options items="${allAuthors}"  itemValue="id" itemLabel="name"/>
            </form:select>
            <form:select id="dropdown" modelAttribute="tags" class="dropdown"  multiple="multiple" path="tags" type="hidden">
                <form:options items="${allTags}"  itemValue="id" itemLabel="name"/>
            </form:select>
            <c:set var="page" value="${searchCriteria.page}"/>
            <c:if test="${page > 2}">
                <form:button name="page" value="1" type="submit">First</form:button>
            </c:if>
            <c:if test="${numPages>1}">
                <c:if test="${page > 1}">
                    <form:button name="page" value="${page-1}" type="submit">Prev</form:button>
                </c:if>
                <form:button name="page" value="${page}" type="submit">${page}</form:button>
                <c:if test="${numPages>page}">
                    <form:button name="page" value="${page+1}" type="submit">Next</form:button>
                    <c:if test="${numPages>page+1}">
                        <form:button name="page" value="${numPages}" type="submit">Last</form:button>
                    </c:if>
                </c:if>
            </c:if>
        </div>
    </form:form>
</div>
</c:if>
<c:if test="${not filtered}">
    <div class="pagination">
        <c:set var="page" value="${page}"/>
        <c:if test="${page > 2}">
            <c:url value="/news/list/1" var="first"/>
            <button><a href="${first}">First</a></button>
        </c:if>
        <c:if test="${numPages>1}">
            <c:if test="${page > 1}">
                <c:url value="/news/list/${page-1}" var="prev"/>
                <button><a href="${prev}">Prev</a></button>
            </c:if>
            <c:url value="/news/list/${page}" var="current"/>
            <button><a href="${current}">${page}</a></button>
            <c:if test="${numPages>page}">
                <c:url value="/news/list/${page+1}" var="next"/>
                <button><a href="${next}">Next</a></button>
                <c:if test="${numPages>page+1}">
                    <c:url value="/news/list/${numPages}" var="last"/>
                    <button><a href="${current}">Last(${numPages})</a></button>
                </c:if>
            </c:if>
        </c:if>
    </div>
</c:if>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src=<c:url value="/assets/js/multiple-select.js"/>></script>
<script>
    $('#dropdown').multipleSelect();
    $(".hidden > select").hide();//for hide
</script>