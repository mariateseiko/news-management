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
        <form:button name="page" value="1" type="submit"><spring:message code="label.filter"/></form:button>
        <form:button type="submit" name="reset" value="reset"><spring:message code="label.reset"/></form:button>
    </div>
</form:form>
<div class="content">
    <c:if test="${not empty emptyList}"><div class="error"><spring:message code="${emptyList}"/></div></c:if>
    <form:form action="${newsDelete}"  method="post">
    <c:forEach var="newsDTO" items="${newsDTOList}">
        <div class="news">
            <spring:url value="/news/message/${newsDTO.news.id}" var="viewMessage"/>
            <div class="float-left" style="font-weight: bold">
                <a href="${viewMessage}"><c:out value="${newsDTO.news.title}"/></a>
            </div>
            <div class="float-left author"><spring:message code="label.by"/> ${newsDTO.author.name}</div>
            <div class="float-right"><fmt:formatDate type="date"
                                                     value="${newsDTO.news.creationDate}"/></div>
            <br/>
            <div class="news-short">
                    <c:out value="${newsDTO.news.shortText}"/>
            </div>
            <spring:url value="/news/edit/${newsDTO.news.id}" var="editMessage"/>
            <input class="float-right link" type = "checkbox" name = "selectedNews" value="${newsDTO.news.id}" />
            <div class="float-right link" ><a href="${editMessage}">
                <spring:message code="button.edit"/>
            </a></div>
            <div class="float-right comments"><spring:message code="label.comments"/>(${newsDTO.commentCount})</div>
            <div class="float-right tags">
                <c:forEach var="tag" items="${newsDTO.tags}" varStatus="loop">
                    <c:out value="${tag.name}"/>
                    <c:if test="${!loop.last}">,</c:if>
                </c:forEach>
            </div>
        </div>
    </c:forEach>
        <c:if test="${newsDTOList.size() > 0}">
            <button type="submit" class="submit-button" style="margin-right: 15px">Delete</button>
        </c:if>
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
                <form:button name="page" value="1" type="submit">
                    <spring:message code="label.first"/>
                </form:button>
            </c:if>
            <c:if test="${numPages > 1}">
                <c:if test="${page>1 && page<=numPages}">
                    <form:button name="page" value="${page-1}" type="submit">${page-1}</form:button>
                </c:if>
                <c:if test="${page>0 && page<=numPages}">
                    <form:button name="page" value="${page}" type="submit">${page}</form:button>
                </c:if>
                <c:if test="${numPages>page}">
                    <form:button name="page" value="${page+1}" type="submit">${page+1}</form:button>
                    <c:if test="${numPages>page+1}">
                        <form:button name="page" value="${numPages}" type="submit">
                            <spring:message code="label.last"/>(${numPages})
                        </form:button>
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
            <button><a href="${first}"><spring:message code="label.first"/></a></button>
        </c:if>
        <c:if test="${numPages>1}">
            <c:if test="${page > 1 && page <= numPages}">
                <c:url value="/news/list/${page-1}" var="prev"/>
                <button><a href="${prev}">${page-1}</a></button>
            </c:if>
            <c:url value="/news/list/${page}" var="current"/>
            <c:if test="${page>0 && page <= numPages}">
                <button><a href="${current}">${page}</a></button>
            </c:if>
            <c:if test="${numPages>page}">
                <c:url value="/news/list/${page+1}" var="next"/>
                <button><a href="${next}">${page+1}</a></button>
                <c:if test="${numPages>page+1}">
                    <c:url value="/news/list/${numPages}" var="last"/>
                    <button><a href="${last}"><spring:message code="label.last"/>(${numPages})</a></button>
                </c:if>
            </c:if>
        </c:if>
    </div>
</c:if>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src=<c:url value="/assets/js/multiple-select.js"/>></script>
<script>
    $('#dropdown').multipleSelect();
    $(".hidden > select").hide();
</script>