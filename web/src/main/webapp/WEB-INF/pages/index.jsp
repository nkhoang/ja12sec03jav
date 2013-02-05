<%@ include file="/common/taglibs.jsp" %>
<html>
<head><title><fmt:message key="login.title"/></title>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/login-form.css' />"/>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/login-layout.css' />"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
</head>
<body>
<div id="content">
    <div class="main">
        <table class="m">
            <tbody>
            <tr>
                <td>
                    <div id="login-form">
                        <label for="username" title="<fmt:message key="login.username" />"><fmt:message
                                key="login.username"/>:</label>
                        <input type="text" name="username" id="username" size="25"/>
                        <label for="username" title="<fmt:message key="login.password" />"><fmt:message
                                key="login.password"/>:</label>
                        <input type="password" name="password" id="password" size="25"/>
                        <input type="button" name="submit" value="<fmt:message key="login.submit" />"/>
                        <br/>

                        <div style="padding-top: 5px;">
                            <fmt:message key="login.register">
                                <fmt:param><a href="<c:url value="/user/register.html"/>">&#273;&#226;y</a></fmt:param>
                            </fmt:message>
                        </div>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>