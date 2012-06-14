<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title id="pageTitle"><spring:message code="tooter.title"/></title>
    <link rel="stylesheet" href="../../assets/styles/style.css" type="text/css"/>
    <SCRIPT TYPE="text/javascript">
        function showWindow() {
            var url = '/accountTypeMsg.html';
            someWindow = window.open(url, 'Account Type Message', 'width=400,height=200,scrollbars=yes');
        }
        function setVisibility(id) {
            if (document.getElementById(id).style.display == 'inline') {
                document.getElementById(id).style.display = 'none';
            } else {
                document.getElementById(id).style.display = 'inline';
            }
        }
    </SCRIPT>
</head>
<body>
<div class="contentpanel" element="div">
    <span style="font-size: 25px">
        <spring:message code="tooter.title"/>
    </span>

    <span style="padding-left: 400px">
        <label><spring:message code="customer.account.type"/>:</label>
        <a href="#" onclick="return showWindow()">${toot.customer.accountType}</a>
     </span>
     <span style="padding-left: 25px">
         <input type=button name=type value='${toot.customer.firstName} ${toot.customer.lastName}'
                onclick="setVisibility('accOptions');">
     </span>
    <br/>
    <spring:message code="welcome.sample.app"/>

    <div id="accOptions" style="display:none; padding-left: 462px">
        <a href="<c:url value='/profile?accountId=${toot.customer.userName}'/>"><spring:message
                code="profile.title"/></a>

        <div style="padding-left: 640px"><a href="<c:url value='/logout'/>"><spring:message code="tooter.logout"/></a>
        </div>
    </div>
    <form:form method="POST" commandName="toot">

        <form:errors path="*" cssClass="errorblock" element="div"/>
        <div>
            <form:input id="tootMessage" path="tootMessage" cssStyle="height: 50px; width: 700px; font-size: 14px;"
                        maxlength="160"/>
            <input type="submit" value="<spring:message code="tooter.toot"/>"
                   style="height: 400px; width: 70px; font-size: 18px"/>
        </div>
        <br/>
        <spring:message code="tooter.toots"/>
        <br/>
        <c:forEach items="${toot.customer.tootList}" var="tootItem">
            <div class="${tootItem.customer.accountType}">
                <a href="<c:url value='/profile?accountId=${tootItem.customer.userName}'/>">${tootItem.customer.userName}</a>
                    ${tootItem.tootMessage}
                <a href="<c:url value='/tooter/remove?accountId=${tootItem.customer.userName}&removeTootId=${tootItem.tootId}'/>">
                    <spring:message code="tooter.remove.toot"/></a>

                <div style="padding-left: 650px">
                        ${tootItem.customer.accountType} <spring:message code="customer.account"/>
                </div>
            </div>
            <br/>
        </c:forEach>

    </form:form>
</div>
</body>
</html>