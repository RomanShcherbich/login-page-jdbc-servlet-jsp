<%@ page import="model.UserAccount" %>
<%@ page import="static constant.IRequestAttribute.AUTHENTICATED_USER_KEY" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<div style="background: #E0E0E0; height: 55px; padding: 5px;">
    <div style="float: left">
        <h1>Login page - education application</h1>
    </div>
    <div style="float: right; padding: 10px; text-align: right;">
        <%
            UserAccount loginedUser = (UserAccount) request.getSession().getAttribute(AUTHENTICATED_USER_KEY);
            String userName = loginedUser != null ? loginedUser.getUserName() : "Visitor";
        %>
        Hello, <b><%=userName%></b>
    </div>
</div>