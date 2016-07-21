<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="info-list">
    <c:if test="${not empty hasErrors}">
        <div style="text-align: center"><span style="display: inline-block; color:red">
            <spring:message code="tag.failed.update"/>
        </span></div>
    </c:if>
    <c:forEach var="tag" items="${tags}">
        <c:url var="tagUrl" value="/tag"/>
        <form:form action="${tagUrl}" modelAttribute="tag" method="post" style="margin-bottom: 0">
            <form:hidden path="id" value="${tag.id}"/>
            <div class="info-label" style="margin-left:20%"><spring:message code="label.tag"/>: </div>
            <form:input class="float-input" id="info-input-${tag.id}" path="name" disabled="true" value="${tag.name}" required="required" maxlength="30"/>
            <div class="edit-info-label div-link" id="info-button-${tag.id}">
                <spring:message code="label.edit"/>
            </div>
            <div id="edit-panel-${tag.id}" hidden style="display:none">
                <div class="edit-info-label div-link">
                    <button name="update" value="update" class="button-link">
                        <spring:message code="label.update"/>
                    </button>
                </div>
                <div class="edit-info-label div-link">
                    <button type="submit" name="expire" value="expire" class="button-link">
                        <spring:message code="label.delete"/>
                    </button>
                </div>
                <div class="edit-info-label div-link" id="cancel-info-button-${tag.id}"><spring:message code="label.cancel"/></div>
            </div>
            <br/>
            <br/>
        </form:form>
    </c:forEach>
    <form:form action="${tagUrl}" modelAttribute="author" method="post" style="margin-bottom: 0">
        <div class="info-label" style="margin-left:20%">
            <spring:message code="tags.add"/>:
        </div>
        <form:input path="name" class="float-input" required="required" maxlength="30"/>
        <div class="edit-info-label div-link">
            <button type="submit" name="save" value="save" class="button-link">
                <spring:message code="label.save"/>
            </button>
        </div>
        <div style="text-align: center"><form:errors path="name" cssClass="error" style="display: inline-block"/></div>
    </form:form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src=<c:url value="/assets/js/info-management.js"/>></script>