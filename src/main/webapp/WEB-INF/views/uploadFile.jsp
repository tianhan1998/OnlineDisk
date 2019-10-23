<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tianhan
  Date: 2019/10/20
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/front/js/bootstrap.min.js"></script>
</head>
<body>
<form action="${pageContext.request.contextPath}/UploadFile" method="post" enctype="multipart/form-data">
    <div class="form-group">
        <label for="UploadFile">选择文件</label>
        <input type="file" id="UploadFile" name="files" multiple="multiple">
        <c:if test="${Message!=null}">
        <p class="help-block">${Message}</p>
        </c:if>
        <label style="color:red"><c:if test="${Error!=null}">${Error}</c:if></label>
    </div>
    <button type="submit" class="btn btn-default">上传</button>
</form>
</body>
</html>
