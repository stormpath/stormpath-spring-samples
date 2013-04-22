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
    <title id="pageTitle"><spring:message code="profile.title"/></title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" type="image/vnd.microsoft.icon" href="<c:url value='/assets/img/favicon.ico'/>"/>
</head>
<body style="padding-top: 55px;">
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <a class="brand" style="margin-left: 0px;" href="https://www.stormpath.com">Tooter</a>
        <ul class="nav">
            <li><a href="<c:url value='/tooter'/>">Home</a></li>
            <li class="active"><a href="<c:url value='/profile'/>">Profile</a></li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle"
                   data-toggle="dropdown">${sessionScope.sessionUser.firstName} ${sessionScope.sessionUser.lastName} <b
                        class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="#" id="showAccountTypes"><spring:message
                            code="customer.account.type"/>: ${sessionScope.sessionUser.groupName}</a></li>
                    <li class="divider"></li>
                    <li class="nav-header"></li>
                    <li><a href="<c:url value='/logout'/>"><spring:message code="tooter.logout"/></a></li>
                </ul>
            </li>
        </ul>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="span12">
            <c:if test="${messageKey == 'updated'}">
                <div class="alert alert-success">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    Your profile was successfully updated!
                </div>
            </c:if>
            <form:form method="POST" commandName="user">
                <c:set var="controlGroupClass" value="control-group" scope="page"/>
                <spring:hasBindErrors name="user">
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
                        <button type="submit" class="btn btn-primary"><spring:message code="profile.update"/></button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<div class="modal" id="accountTypeModalContent" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 id="myModalLabel">Account Types</h3>
    </div>
    <div class="modal-body">
        <p>
        <ul>
            <li><b>Basic</b>: Create Toot. Toots are not highlighted.</li>
            <li><b>Premium</b>: Create Toot. Toots are highlighted soft yellow.</li>
            <li><b>Admin</b>: Create Toot. Delete Toot. Toots are highlighted soft blue.</li>
        </ul>
        </p>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">Close</button>
    </div>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#accountTypeModalContent').modal();
        $('#accountTypeModalContent').modal('hide');
        $('#showAccountTypes').on('click', function () {
            $('#accountTypeModalContent').modal('show');
        });
    });
</script>

</body>
</html>