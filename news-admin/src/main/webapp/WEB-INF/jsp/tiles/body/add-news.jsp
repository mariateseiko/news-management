<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
    <c:url var="newsUrl" value="/news/save"/>
    <form:form action="${newsUrl}" method="post" modelAttribute="newsDTO" class="add-form">
        <form:hidden path="news.id"/>
        <div style="text-align: center">
            <form:errors path="news.title" style="display: inline-block; color:red"/>
        </div>
        <div class="info-label"><spring:message code="news.add.title"/>:</div>
        <form:input path="news.title" class="float-input add" />
        <br/>
        <div style="text-align: center">
            <form:errors path="news.shortText" style="display: inline-block; color:red"/>
        </div>
        <div class="info-label"><spring:message code="news.add.brief"/>:</div>
        <form:textarea path="news.shortText" class="float-input add-area" rows="7"/>
        <br/>
        <div style="text-align: center">
            <form:errors path="news.fullText" style="display: inline-block; color:red"/>
        </div>
        <div class="info-label"><spring:message code="news.add.content"/>:</div>
        <form:textarea path="news.fullText" class="float-input add-area" rows="20"/>
        <div class="filter">
            <form:select path="author.id">
                <form:options items="${authors}"  itemValue="id" itemLabel="name"/>
            </form:select>
            <form:select id="dropdown" class="dropdown" multiple="multiple" path="tags" varStatus="iterIndex">
               <form:options items="${tags}"  itemValue="id" itemLabel="name"/>
            </form:select>
        </div>
        <button class="submit-button" style="margin-right: 37px"><spring:message code="news.add.save"/></button>
    </form:form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src=<c:url value="/assets/js/multiple-select.js"/>></script>
<script>
    $('#dropdown').multipleSelect();
</script>