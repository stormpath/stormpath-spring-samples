<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title id="pageTitle"><spring:message code="account.login"/></title>
    <link rel="stylesheet" href="../../assets/styles/style.css" type="text/css"/>
</head>
<body>
<div class="contentpanel" element="div">
    <h1><spring:message code="tooter.title"/>
    </h1>
    <label><spring:message code="customer.account.type"/>:</label>
    <br/>
    <spring:message code="welcome.sample.app"/>
    <form:form method="POST" commandName="customer">

        <form:errors path="*" cssClass="errorblock" element="div"/>
        <div>
            <form:input id="tootMessage" path="tootMessage"/>
            <input type="submit" value="<spring:message code="tooter.toot"/>"/>
        </div>
        <br/>
        <spring:message code="tooter.toot"/>

    </form:form>
</div>
</body>
</html>