<%--
  Created by IntelliJ IDEA.
  User: tianhan
  Date: 2019/10/20
  Time: 18:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>退出</title>
</head>
<body>
<script>
    alert("您已成功退出");
    window.location.href="${pageContext.request.contextPath}/login";
</script>
</body>
</html>
