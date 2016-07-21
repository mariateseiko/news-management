<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="info-list">
    <c:if test="${not empty hasErrors}">
        <div style="text-align: center"><span style="display: inline-block; color:red">
            <spring:message code="author.failed.update"/>
        </span></div>
    </c:if>
    <c:forEach var="author" items="${authors}">
        <c:url var="authorUrl" value="/author"/>
        <form:form action="${authorUrl}" modelAttribute="author" method="post" style="margin-bottom: 0">
            <form:hidden path="id" value="${author.id}"/>
            <div class="info-label" style="margin-left:20%"><spring:message code="authors.author"/>: </div>
            <form:input class="float-input" id="info-input-${author.id}" required="required" maxlength="30"
                        path="name" disabled="true" value="${author.name}"/>
            <div class="edit-info-label div-link" id="info-button-${author.id}">
                <spring:message code="label.edit"/>
            </div>
            <div id="edit-panel-${author.id}" hidden style="display:none">
                <div class="edit-info-label div-link">
                    <button name="update" value="update" class="button-link">
                        <spring:message code="label.update"/>
                    </button>
                </div>
                <div class="edit-info-label div-link">
                    <button type="submit" name="expire" value="expire" class="button-link">
                        <spring:message code="label.expire"/>
                    </button>
                </div>
                <div class="edit-info-label div-link" id="cancel-info-button-${author.id}">
                    <spring:message code="label.cancel"/>
                </div>
            </div>
            <br/>
            <br/>
        </form:form>
    </c:forEach>
    <form:form action="${authorUrl}" modelAttribute="author" method="post" style="margin-bottom: 0">
        <div class="info-label" style="margin-left:20%"><spring:message code="authors.add"/>: </div>
        <form:input path="name" class="float-input" required="required" maxlength="30"/>
        <div class="edit-info-label div-link">
            <form:button type="submit" name="save" value="save" class="button-link">
                <spring:message code="label.save"/>
            </form:button>
        </div>
        <div style="text-align: center"><form:errors path="name" cssClass="error" style="display: inline-block"/></div>
    </form:form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src=<c:url value="/assets/js/info-management.js"/>></script>