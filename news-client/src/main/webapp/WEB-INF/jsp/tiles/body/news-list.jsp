<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>

<%--<c:url value="/news/filter" var="newsFilter"/>--%>
<%--<c:url value="/news/delete" var="newsDelete"/>--%>
<form action="${newsFilter}"  modelAttribute="searchCriteria" method="get" id="filter" >
    <div class="filter">
        <select name="authors" title="authors">
            <c:forEach var="author" items="${allAuthors}">
                <option value="${author.id}">${author.name}</option>
            </c:forEach>
        </select>
        <select id="dropdown"  class="dropdown"  multiple="multiple" title="tags">
            <c:forEach var="tag" items="${allTags}">
                <option value="${tag.id}"><c:out value="${tag.name}"/></option>
            </c:forEach>
        </select>
        <button name="page" value="1" type="submit"><fmt:message key="label.filter"/></button>
        <button type="submit" name="reset" value="reset"><fmt:message key="label.reset"/></button>
    </div>
</form>
<div class="content">
    <c:if test="${not empty emptyList}"><div class="error"><fmt:message key="${emptyList}"/></div></c:if>
    <c:forEach var="newsDTO" items="${newsDTOList}">
        <div class="news">
            <c:url value="/news/message/${newsDTO.news.id}" var="viewMessage"/>
            <div class="float-left" style="font-weight: bold">
                <a href="${viewMessage}"><c:out value="${newsDTO.news.title}"/></a>
            </div>
            <div class="float-left author"><fmt:message key="label.by"/> ${newsDTO.author.name}</div>
            <div class="float-right"><fmt:formatDate type="date"
                                                     value="${newsDTO.news.creationDate}"/></div>
            <br/>
            <div class="news-short">
                    <c:out value="${newsDTO.news.shortText}"/>
            </div>
            <c:url value="/news/edit/${newsDTO.news.id}" var="editMessage"/>
            <div class="float-right link" ><a href="${editMessage}">
                <fmt:message key="button.edit"/>
            </a></div>
            <div class="float-right comments"><fmt:message key="label.comments"/>(${newsDTO.commentCount})</div>
            <div class="float-right tags">
                <c:forEach var="tag" items="${newsDTO.tags}" varStatus="loop">
                    <c:out value="${tag.name}"/>
                    <c:if test="${!loop.last}">,</c:if>
                </c:forEach>
            </div>
        </div>
    </c:forEach>
    </form>
</div>
<%--<c:url value="/news/filter" var="newsFilters"/>--%>
<c:if test="${filtered}">
<div class="pagination">
    <form action="${newsFilters}"  modelAttribute="searchCriteria" method="get" id="filter" >
        <div class="hidden">
            <select path="authors" modelAttribute="authors" multiple="1" id="authors" type="hidden">
                <options items="${allAuthors}"  itemValue="id" itemLabel="name"/>
            </select>
            <select id="dropdown" modelAttribute="tags" class="dropdown"  multiple="multiple" path="tags" type="hidden">
                <options items="${allTags}"  itemValue="id" itemLabel="name"/>
            </select>
            <c:set var="page" value="${searchCriteria.page}"/>
            <c:if test="${page > 2}">
                <button name="page" value="1" type="submit">
                    <fmt:message key="label.first"/>
                </button>
            </c:if>
            <c:if test="${numPages > 1}">
                <c:if test="${page>1 && page<=numPages}">
                    <button name="page" value="${page-1}" type="submit">${page-1}</button>
                </c:if>
                <c:if test="${page>0 && page<=numPages}">
                    <button name="page" value="${page}" type="submit">${page}</button>
                </c:if>
                <c:if test="${numPages>page}">
                    <button name="page" value="${page+1}" type="submit">${page+1}</button>
                    <c:if test="${numPages>page+1}">
                        <button name="page" value="${numPages}" type="submit">
                            <fmt:message key="label.last"/>(${numPages})
                        </button>
                    </c:if>
                </c:if>
            </c:if>
        </div>
    </form>
</div>
</c:if>
<c:if test="${not filtered}">
    <div class="pagination">
        <c:set var="page" value="${page}"/>
        <c:if test="${page > 2}">
            <%--<c:url value="/news/list/1" var="first"/>--%>
            <button><a href="${first}"><fmt:message key="label.first"/></a></button>
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
                    <button><a href="${last}"><fmt:message key="label.last"/>(${numPages})</a></button>
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