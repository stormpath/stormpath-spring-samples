<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title id="pageTitle"><spring:message code="signUp.now"/></title>
    <link rel="stylesheet" href="../../assets/styles/style.css" type="text/css"/>
</head>
<body>
<div class="contentpanel" element="div">
    <h3><spring:message code="tooter.logout"/>
    </h3>
    <br/>

    <form:form method="POST" commandName="customer">

        <div>
            <spring:message code="logout.message"/>
        </div>
        <div id="buttons">
            <div class="help">
                <a href="<c:url value='/login'/>"><spring:message code="logout.login.page"/></a>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>