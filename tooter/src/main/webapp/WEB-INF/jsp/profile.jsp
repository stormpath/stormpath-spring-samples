<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title id="pageTitle"><spring:message code="profile.title"/></title>
    <link rel="stylesheet" href="../../assets/styles/style.css" type="text/css"/>
</head>
<body>
<div class="contentpanel" element="div">
    <h2><spring:message code="profile.title"/>
    </h2>
    <br/>
    <form:form method="POST" commandName="customer">

        <form:errors path="*" cssClass="errorblock" element="div"/>
        <c:if test="${not empty messageKey}">
            <div class="messageblock">
                <spring:message code="profile.message.${messageKey}"/>
            </div>
        </c:if>
        <div>
            <label for="firstName"><spring:message code="customer.first.name"/></label>
            <form:input id="firstName" path="firstName"/>
        </div>
        <div>
            <label for="lastName"><spring:message code="customer.last.name"/></label>
            <form:input id="lastName" path="lastName"/>
        </div>
        <div>
            <label for="email"><spring:message code="customer.email"/></label>
            <form:input id="email" path="email"/>
        </div>
        <%--   Stormpath's Account-Groups association functionality comming soon
        <div>
            <label><spring:message code="customer.account.type"/></label>
            <form:radiobutton path="accountType" value="${customer.accountType}"/>${customer.accountType}
        </div>--%>
        <div id="buttons">
            <div class="help">
                <a href="<c:url value='/tooter?accountId=${customer.userName}'/>"><spring:message
                        code="profile.back"/></a>
                <span style="padding-left:2px">
                   <input type="submit" value="<spring:message code="profile.update"/>"/>
                </span>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>