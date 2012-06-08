<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title id="pageTitle">Login</title>
    <style>
        .error {
            color: #ff0000;
        }

        .errorblock {
            color: #000;
            background-color: #ffEEEE;
            border: 3px solid #ff0000;
            padding: 8px;
            margin: 16px;
        }

        .contentpanel {
            margin-top: 15px;
            background: white;
            border: 1px solid #999;
            padding: 20px;
            box-shadow: 0 0 6px rgba(0, 0, 0, 0.8);
            -moz-box-shadow: 0 0 6px rgba(0, 0, 0, 0.8);
            -webkit-box-shadow: 0 0 6px rgba(0, 0, 0, 0.8);
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            line-height: 25px !important
        }

        .contentpanel ul {
            list-style: disc
        }

        .contentpanel ul li {
            line-height: 25px !important
        }

        .contentpanel ol {
            list-style-type: decimal
        }

        .contentpanel ol li {
            line-height: 25px !important
        }

        .contentpanel h2 {
            font-size: 25px;
            line-height: 30px
        }

        a.actionbutton {
            display: inline-block;
            padding: 5px 10px 5px 10px;
            color: white !important;
            text-decoration: none !important;
            background-color: #e45a43;
            border: 1px solid #d65543 !important;
            -webkit-box-shadow: 1px 1px 2px 1px rgba(135, 117, 115, 0.75);
            -moz-box-shadow: 1px 1px 2px 1px rgba(135, 117, 115, 0.75);
            box-shadow: 1px 1px 2px 1px rgba(135, 117, 115, 0.75);
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px
        }


    </style>
</head>
<body>
<div class="contentpanel" element="div">
    <form:form id="customer" action="login" modelAttribute="customer">

        <form:errors path="*" cssClass="errorblock" element="div"/>
        <div>
            <label for="userName"><spring:message code="customer.userName"/></label>
            <form:input id="userName" path="userName"/>
        </div>
        <div>
            <label for="password"><spring:message code="customer.password"/></label>
            <form:password id="password" path="password"/>
        </div>
        <div id="buttons">
            <div class="login">
                <a href="#" id="loginbutton" class="actionbutton">Log In</a>
            </div>
            <div class="help">
                <a href="<c:url value='/forgotLogin'/>"><spring:message code="password.forgot"/></a>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>