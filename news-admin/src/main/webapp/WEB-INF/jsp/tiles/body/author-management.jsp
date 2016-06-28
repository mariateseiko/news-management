<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="info-list">
    <c:forEach var="author" items="${authors}">
        <c:url var="authorUrl" value="/author"/>

        <form:form action="${authorUrl}" modelAttribute="author" method="post" style="margin-bottom: 0">
            <input hidden name="id" value="${author.id}"/>
            <div class="info-label" style="margin-left:20%">Author: </div>
            <input class="float-input" id="info-input-${author.id}" name="name" disabled value="${author.name}"/>
            <div class="edit-info-label div-link" id="info-button-${author.id}">edit</div>
            <div id="edit-panel-${author.id}" hidden style="display:none">
                <div class="edit-info-label div-link"><input type="submit" name="update" value="update"/></div>
                <div class="edit-info-label div-link"><input type="submit" name="expire" value="expire"/></div>
                <div class="edit-info-label div-link" id="cancel-info-button-${author.id}">cancel</div>
            </div>
            <br/>
            <br/>
        </form:form>
    </c:forEach>
    <form:form action="${authorUrl}" modelAttribute="author" method="post" style="margin-bottom: 0">
        <div class="info-label" style="margin-left:20%">Add Author: </div>
        <input name="name" class="float-input"/>
        <div class="edit-info-label div-link"><input type="submit" name="save" value="save"/></div>
    </form:form>


</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src=<c:url value="/assets/js/info-management.js"/>></script>