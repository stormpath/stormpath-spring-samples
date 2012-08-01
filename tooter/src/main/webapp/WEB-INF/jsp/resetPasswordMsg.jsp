<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title id="pageTitle"><spring:message code="signUp.now"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/style.css" type="text/css"/>
</head>
<body>
<div class="contentpanel" element="div">
    <h3><spring:message code="reset.password.title"/>
    </h3>
    <br/>
    <spring:message code="reset.password.submission.message"/>
    <div id="buttons">
        <div class="help">
            <a href="<c:url value='/login'/>"><spring:message code="reset.password.return.to.login"/></a>
        </div>
    </div>
</div>
</body>
</html>