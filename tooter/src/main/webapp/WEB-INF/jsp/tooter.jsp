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

<!DOCTYPE html>
<html lang="en">
<head>
    <title id="pageTitle"><spring:message code="tooter.title"/></title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" type="image/vnd.microsoft.icon" href="<c:url value='/assets/img/favicon.ico'/>"/>
</head>
<body style="padding-top: 55px;">
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <a class="brand" style="margin-left: 0px;" href="https://www.stormpath.com">Tooter</a>
        <ul class="nav">
            <li class="active"><a href="<c:url value='/tooter'/>">Home</a></li>
            <li><a href="<c:url value='/profile'/>">Profile</a></li>
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
            <form:form method="POST" commandName="toot">

                <form:errors path="*" cssClass="errorblock" element="div"/>
                <c:if test="${not empty sessionScope.sessionUser}">
                    <div class="control-group">
                        <form:textarea id="tootMessage" path="tootMessage" maxlength="160"
                                       placeholder="Compose your toot here..." rows="3"/>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <button type="submit" class="btn btn-primary"><spring:message code="tooter.toot"/></button>
                        </div>
                    </div>
                </c:if>
            </form:form>
        </div>
    </div>
    <div class="row">
        <div class="span12">
            <h3><spring:message code="tooter.toots"/></h3>
            <c:forEach items="${toot.customer.tootList}" var="tootItem">
                <c:choose>
                    <c:when test="${sessionScope.permissionUtil.hasRole(tootItem.customer, 'ADMINISTRATOR')}">
                        <c:set var="accountTypeClass" value="alert alert-info admin"/>
                    </c:when>
                    <c:when test="${sessionScope.permissionUtil.hasRole(tootItem.customer, 'PREMIUM_USER')}">
                        <c:set var="accountTypeClass" value="alert alert-warning premium"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="accountTypeClass" value="basic"/>
                    </c:otherwise>
                </c:choose>
                <div class="well well-small ${accountTypeClass}" title="Poster's Account Type"
                     data-content="${tootItem.customer.groupName}">
                    <c:choose>
                        <c:when test="${not empty sessionScope.sessionUser}">
                            <code><a
                                    href="<c:url value='/profile'/>">${tootItem.customer.firstName} ${tootItem.customer.lastName}</a>: ${tootItem.tootMessage}
                            </code>
                            <c:if test="${sessionScope.permissionUtil.hasRole(sessionScope.sessionUser, 'ADMINISTRATOR')}">
                                <button class="close" title="Delete the toot permanently"
                                        onclick="window.location.href='<c:url
                                                value='/tooter/remove?id=${tootItem.tootId}'/>'">&times;</button>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            ${tootItem.customer.userName}&nbsp;${tootItem.tootMessage}
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
            <c:if test="${empty toot.customer.tootList}">
                <div class="alert">
                    There are no toots!
                </div>
            </c:if>
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
        $('.close').tooltip();
        $('.admin, .premium, .basic').popover({trigger: 'hover', placement: 'top'});
        $('#accountTypeModalContent').modal();
        $('#accountTypeModalContent').modal('hide');
        $('#showAccountTypes').on('click', function () {
            $('#accountTypeModalContent').modal('show');
        });
    });
</script>
</body>
</html>