<%@ page import="model.UserAccount" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<jsp:include page="internalView/_header.jsp"></jsp:include>
<jsp:include page="internalView/_menu.jsp"></jsp:include>
<%
    String errorString = (String) request.getSession().getAttribute("errorString");
    errorString = errorString != null ? errorString : "";
    String contextPath = request.getServletContext().getContextPath();
    UserAccount loginedUser = (UserAccount) request.getSession().getAttribute("user");
    String userName = "";
    String userPassword = "";
    if(loginedUser != null) {
        userName = loginedUser.getUserName();
        userPassword = loginedUser.getUserName();
    }
%>

<h3>Login Page</h3>
<p style="color: red;"><%=errorString%></p>
<form method="POST" action="<%=contextPath%>/login">
    <table border="0">
        <tr>
            <td>User Name</td>
            <td><input type="text" name="userName" value="<%=userName%>"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="text" name="password" value="<%=userPassword%>"/></td>
        </tr>
        <tr>
            <td>Remember me</td>
            <td><input type="checkbox" name="rememberMe" value="Y"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Submit"/>
                <a href="<%=contextPath%>/">Cancel</a>
            </td>
        </tr>
    </table>
</form>
<p style="color:blue;">User Name: tom, password: tom001 or jerry/jerry001</p>
<jsp:include page="internalView/_footer.jsp"></jsp:include>
</body>
</html>