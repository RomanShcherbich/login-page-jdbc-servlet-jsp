<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>Server error</title>
</head>
<jsp:include page="internalView/_header.jsp"></jsp:include>
<jsp:include page="internalView/_menu.jsp"></jsp:include>
<body>
<h2>Exception occurred while processing the request</h2>
<p>Error message: <%=exception.getMessage()%></p>
<jsp:include page="internalView/_footer.jsp"></jsp:include>
</body>
</html>
