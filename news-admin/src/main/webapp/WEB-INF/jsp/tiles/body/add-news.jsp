<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <c:url var="newsUrl" value="/news/add"/>
    <form:form action="${newsUrl}" method="post" modelAttribute="newsDTO" class="add-form">
        <div class="info-label">Title:</div>
        <form:input path="news.title" class="float-input add"/>
            <br/>
        <!--<div class="info-label">Date:</div>
        <form:input path="news.creationDate" class="float-input add creation-date" disabled="true"
                    value = "<fmt:formatDate value='${news.creationDate}'" pattern="dd/mm/yyyy"/>
            <br/>-->
        <div class="info-label">Brief:</div>
        <form:textarea path="news.shortText" class="float-input add-area" rows="7"/>
            <br/>
        <div class="info-label">Content:</div>
        <form:textarea path="news.fullText" class="float-input add-area" rows="20"/>
        <div class="filter">
            <form:select path="author.id">
                <form:options items="${authors}"  itemValue="id" itemLabel="name"/>
            </form:select>
            <form:select id="dropdown" class="dropdown" multiple="multiple" path="tags" varStatus="iterIndex">
               <form:options items="${tags}"  itemValue="id" itemLabel="name"/>
            </form:select>
        </div>
        <button class="submit-button" style="margin-right: 37px">Save</button>
    </form:form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src=<c:url value="/assets/js/multiple-select.js"/>></script>
<script>
    $('#dropdown').multipleSelect();
</script>