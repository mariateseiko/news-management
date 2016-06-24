<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="filter">
    <select>
        <c:forEach var="author" items="${authors}">
            <option>${author.name}</option>
        </c:forEach>
    </select>
    <select id="dropdown" class="dropdown" multiple="multiple">
        <c:forEach var="tag" items="${tags}">
            <option>${tag.name}</option>
        </c:forEach>
    </select>
    <button>Filter</button>
    <button>Reset</button>
</div>
<div class="content">
    <c:forEach var="newsDTO" items="${newsDTOList}">
        <div class="news">
            <div class="float-left" style="font-weight: bold">${newsDTO.news.title}</div>
            <div class="float-left author">by ${newsDTO.author.name}</div>
            <div class="float-right"><fmt:formatDate type="date"
                                                     value="${newsDTO.news.creationDate}"/></div>
            <br/>
            <div class="news-short">
                ${newsDTO.news.shortText}
            </div>
            <div class="float-right link">View</div>
            <div class="float-right comments">Comments(99)</div>
            <div class="float-right tags">
                <c:forEach var="tag" items="${newsDTO.tags}"><
                    ${tag.name}
                </c:forEach>
            </div>
        </div>
    </c:forEach>
</div>
<div class="pagination">
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src=<c:url value="/assets/js/multiple-select.js"/>></script>
<script>
    $('#dropdown').multipleSelect();
</script>