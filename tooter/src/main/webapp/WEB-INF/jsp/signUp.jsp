<%--
  ~ Copyright 2013 Stormpath, Inc. and contributors.
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
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" type="image/vnd.microsoft.icon" href="<c:url value='/assets/img/favicon.ico'/>"/>
</head>
<body style="padding-top: 15px;">
<div class="container-fluid">
    <div class="hero-unit">
        <h3><spring:message code="signUp.now"/></h3>
        <form:form method="POST" commandName="customer">

            <c:set var="controlGroupClass" value="control-group" scope="page"/>
            <spring:hasBindErrors name="customer">
                <c:set var="controlGroupClass" value="control-group error" scope="page"/>
            </spring:hasBindErrors>

            <div class="<c:out value="${controlGroupClass}"/>">
                <form:errors path="*" cssClass="help-block" element="span" htmlEscape="false"/>
            </div>

            <div class="<c:out value="${controlGroupClass}"/>">
                <label class="control-label" for="firstName"><spring:message code="customer.first.name"/></label>
                <form:input id="firstName" path="firstName"/>
            </div>
            <div class="<c:out value="${controlGroupClass}"/>">
                <label class="control-label" for="lastName"><spring:message code="customer.last.name"/></label>
                <form:input id="lastName" path="lastName"/>
            </div>
            <div class="<c:out value="${controlGroupClass}"/>">
                <label class="control-label" for="email"><spring:message code="customer.email"/></label>
                <form:input id="email" path="email"/>
            </div>
            <div class="<c:out value="${controlGroupClass}"/>">
                <label class="control-label" for="password"><spring:message code="customer.password"/></label>
                <form:password id="password" path="password"/>
            </div>
            <div class="<c:out value="${controlGroupClass}"/>">
                <label class="control-label" for="confirmPassword"><spring:message
                        code="customer.password.confirm"/></label>
                <form:password id="confirmPassword" path="confirmPassword"/>
            </div>
            <div class="<c:out value="${controlGroupClass}"/>">
                <label class="radio inline" style="margin-left: -18px !important;"><spring:message
                        code="customer.account.type"/>:</label>&nbsp;
                <label class="radio inline">
                    <form:radiobutton path="groupUrl" cssClass="radio" value="${ADMINISTRATOR_URL}"/> Administrator
                </label>
                <label class="radio inline">
                    <form:radiobutton path="groupUrl" cssClass="radio" value="${PREMIUM_URL}"/> Premium
                </label>
                <label class="radio inline">
                    <form:radiobutton path="groupUrl" cssClass="radio" value="${null}"/> Basic
                </label>
            </div>
            <div class="control-group">
                <div class="controls">
                    <a class="btn" href="<c:url value='/login'/>"><spring:message code="return.message"/></a>
                    <button class="btn btn-primary" type="submit"><spring:message code="customer.register"/></button>
                </div>
            </div>
        </form:form>
    </div>
</div>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
</body>
</html>