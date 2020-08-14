<%@ page import="model.UserAccount" %>
<%@ page import="static constant.IRequestAttribute.AUTHENTICATED_USER_KEY" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="padding: 5px;">
    <%
        String contextPath = request.getServletContext().getContextPath();
        UserAccount authenticatedUser = (UserAccount) request.getSession().getAttribute(AUTHENTICATED_USER_KEY);
        String loginUrlPattern = authenticatedUser != null ? "/logout" : "/login";
        String loginButton = authenticatedUser != null ? "Logout" : "Login";
    %>
    <a href="<%=contextPath%>/">Home</a>
    |
    <a href="<%=contextPath%>/productList">Product List</a>
    |
    <a href="<%=contextPath%>/userInfo">My Account Info</a>
    |
    <a href="<%=contextPath%><%=loginUrlPattern%>"><%=loginButton%></a>

</div>