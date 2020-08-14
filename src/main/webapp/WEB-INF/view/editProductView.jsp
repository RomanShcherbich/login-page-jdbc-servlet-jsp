<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Product</title>
</head>
<body>
<jsp:include page="internalView/_header.jsp"></jsp:include>
<jsp:include page="internalView/_menu.jsp"></jsp:include>
<%
    String contextPath = request.getServletContext().getContextPath();
    String errorString = (String) request.getAttribute("errorString");
    errorString = errorString != null ? errorString : "";
    String code = (String) request.getAttribute("code");
    code = code != null ? code : "";
    String name = (String) request.getAttribute("name");
    name = name != null ? name : "";
    String price = (String) request.getAttribute("price");
    price = price != null ? price : "";
%>
<h3>Edit Product</h3>
<p style="color: red;"><%=errorString%></p>
    <form method="POST" action="<%=contextPath%>/editProduct">
        <table border="0">
            <tr>
                <td>Code</td>
                <td><input type="text" name="newCode" value="<%=code%>" /></td>
                <td><input type="hidden" name="code" value="<%=code%>" /></td>
            </tr>
            <tr>
                <td>Name</td>
                <td><input type="text" name="name" value="<%=name%>" /></td>
            </tr>
            <tr>
                <td>Price</td>
                <td><input type="text" name="price" value="<%=price%>" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Submit" />
                    <a href="productList">Cancel</a>
                </td>
            </tr>
        </table>
    </form>
<jsp:include page="internalView/_footer.jsp"></jsp:include>
</body>
</html>