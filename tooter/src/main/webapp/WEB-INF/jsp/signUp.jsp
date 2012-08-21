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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title id="pageTitle"><spring:message code="signUp.now"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/style.css" type="text/css"/>
</head>
<body>
<div class="contentpanel" element="div">
    <h3><spring:message code="signUp.now"/>
    </h3>
    <br/>
    <form:form method="POST" commandName="customer">

        <form:errors path="*" cssClass="errorblock" element="div"/>
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
        <div>
            <label for="password"><spring:message code="customer.password"/></label>
            <form:password id="password" path="password"/>
        </div>
        <div>
            <label for="confirmPassword"><spring:message code="customer.password.confirm"/></label>
            <form:password id="confirmPassword" path="confirmPassword"/>
        </div>
        <div>
            <label><spring:message code="customer.account.type"/></label>
            <form:radiobuttons path="accountType" items="${groupMap}"/>
        </div>
        <div id="buttons">
            <div class="help">
                <a href="<c:url value='/login'/>"><spring:message code="return.message"/></a>
                <span style="padding-left:2px">
                   <input type="submit" value="<spring:message code="customer.register"/>"/>
                </span>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>