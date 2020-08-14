<%@ page import="static constant.IRequestAttribute.PRODUCT_LIST_HTML_KEY" %>
<%@ page import="static constant.IRequestAttribute.ERROR_MESSAGE_KEY" %>
<%@ page import="static constant.IRequestAttribute.PRODUCT_LIST_HTML_KEY" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
</head>
<body>
<jsp:include page="internalView/_header.jsp"></jsp:include>
<jsp:include page="internalView/_menu.jsp"></jsp:include>
<%
    String errorString = (String) request.getAttribute(ERROR_MESSAGE_KEY);
    errorString = errorString != null ? errorString : "";
    String productListHtml = (String) request.getAttribute(PRODUCT_LIST_HTML_KEY);
    productListHtml = productListHtml != null ? productListHtml : "";
%>
<h3>Product List</h3>

<p style="color: red;"><%=errorString%></p>

<table border="1" cellpadding="5" cellspacing="1">
    <tr>
        <th>Code</th>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <%=productListHtml%>
</table>
<jsp:include page="internalView/_footer.jsp"></jsp:include>
</body>
</html>