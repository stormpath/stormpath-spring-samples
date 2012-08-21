<%--
  ~ Copyright 2012 Stormpath, Inc. and contributors.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title id="pageTitle"><spring:message code="account.login"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/style.css" type="text/css"/>
</head>
<body>
<div class="contentpanel" element="div">
    <h1>
        <spring:message code="welcome.message"/>
    </h1>

    <div>
        <spring:message code="welcome.sample.app"/>
    </div>
    <form:form method="POST" commandName="customer" action="/login">

        <form:errors path="*" cssClass="errorblock" element="div" htmlEscape="false"/>
        <c:if test="${not empty messageKey}">
            <div class="messageblock">
                <spring:message code="login.message.${messageKey}"/>
            </div>
        </c:if>
        <div>
            <label for="userName"><spring:message code="customer.userName"/></label>
            <form:input id="userName" path="userName"/>
        </div>
        <div>
            <label for="password"><spring:message code="customer.password"/></label>
            <form:password id="password" path="password"/>
        </div>
        <div id="buttons">
            <div class="login" style="padding-left: 150px">
                <input type="submit" value="<spring:message code="account.login"/>"/>
            </div>
            <div class="help">
                <a href="<c:url value='/password/forgot'/>"><spring:message code="password.forgot"/></a><br>
                <spring:message code="account.dont.have.one"/>
                <span style="padding-left:2px">
                    <a href="<c:url value='/signUp'/>"><spring:message code="account.signup"/></a>
                </span>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>