<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    ${error}
    <c:url var="loginUrl" value="/j_spring_security_check"/>
    <form class="centered-form" action="${loginUrl}" method="post">
        <div class="form-element">
            <div class="form-label">Login</div>
            <input type="text" name="username"/>
        </div>
        <div class="form-element">
            <div class="form-label">Password</div>
            <input type="password" name="password"/>
        </div>
        <input class="submit-button" type="submit" />
    </form>
</div>