<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title id="pageTitle"><spring:message code="reset.password.title"/></title>
    <link rel="stylesheet" href="../../assets/styles/style.css" type="text/css"/>
</head>
<body>
<div class="contentpanel" element="div">
    <h1><spring:message code="reset.password.title"/>
    </h1>
    <br/>
    <form:form method="POST" commandName="customer">

        <form:errors path="*" cssClass="errorblock" element="div"/>
        <div>
            <label for="password"><spring:message code="customer.password"/></label>
            <form:password id="password" path="password"/>
        </div>
        <div>
            <label for="confirmPassword"><spring:message code="customer.password.confirm"/></label>
            <form:password id="confirmPassword" path="confirmPassword"/>
        </div>

        <div id="buttons">
            <div class="login">
                <input type="submit" value="<spring:message code="change.password.set.new"/>"/>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>