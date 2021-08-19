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
<style type="text/css">
    .file-box {
        position: relative;
        width: 340px
    }

    .txt {
        height: 22px;
        border: 1px solid #cdcdcd;
        width: 180px;
        vertical-align: middle;
        margin: 0;
        padding: 0
    }

    .btn {
        border: 1px solid #CDCDCD;
        height: 24px;
        width: 70px;
        vertical-align: middle;
        margin: 0;
        padding: 0
    }

    .file {
        position: absolute;
        top: 0;
        right: 80px;
        height: 24px;
        filter: alpha(opacity :  0);
        opacity: 0;
        width: 260px;
        vertical-align: middle;
        margin: 0;
        padding: 0
    }
</style>
<head>
    <title>上传</title>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/front/css/bootstrap.min.css">--%>
<%--    <script src="${pageContext.request.contextPath}/front/js/bootstrap.min.js"></script>--%>

</head>
<body style="width: 80%;height: 80%;margin: 0px auto;">
<%--<form action="${pageContext.request.contextPath}/UploadFile" method="post" enctype="multipart/form-data">--%>
<%--    <div class="form-group">--%>
<%--        <label for="UploadFile">选择文件</label>--%>
<%--        <input type="file" id="UploadFile" name="files" multiple="multiple">--%>
<%--        <input type="hidden" name="path" value="${param.path}"/>--%>
<%--        <c:if test="${Message!=null}">--%>
<%--        <p class="help-block">${Message}</p>--%>
<%--        </c:if>--%>
<%--        <label style="color:red"><c:if test="${Error!=null}">${Error}</c:if></label>--%>
<%--    </div>--%>
<%--    <button type="submit" class="btn btn-default">上传</button>--%>
<%--</form>--%>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">文件上传进度</h4>
                </div>
                <div class="modal-body">
                    <progress id="progressBar" value="0" max="100"
                              style="width: 100%;height: 20px; "> </progress>
                    <span id="percentage" style="color:blue;"></span> <br>
                    <br>
                    <div class="file-box">
                        <input type='text' name='textfield' id='textfield' class='txt' />
                        <input id="path" type="hidden" name="path" value="${param.path}"/>
                        <input type='button' class='btn' value='浏览...' /> <input
                            type="file" multiple="multiple" name="file" class="file" id="file" size="28"
                            onchange="document.getElementById('textfield').value=this.value" />
                        <input type="submit" name="submit" class="btn" value="上传"
                               onclick="UploadFile()" />

                    </div>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal -->
    </div>
<script type="text/javascript"
        src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
<script>
    function UploadFile() {
        var fileObj = document.getElementById("file").files; // js 获取文件对象
        var FileController = "${pageContext.request.contextPath}/UploadFile"; // 接收上传文件的后台地址
        // FormData 对象---进行无刷新上传
        var form = new FormData();
        form.append("path", document.getElementById("path").value); // 可以增加表单数据
        for(var i=0;i<fileObj.length;i++){
            form.append("files",fileObj[i]);
        }
        // form.append("files", fileObj); // 文件对象
        // XMLHttpRequest 对象
        var xhr = new XMLHttpRequest();
        xhr.open("post", FileController, true);
        xhr.onload = function() {
            alert("上传完成!");
        };
        //监听progress事件
        xhr.upload.addEventListener("progress", progressFunction, false);
        xhr.send(form);
    }
    function progressFunction(evt) {

        var progressBar = document.getElementById("progressBar");

        var percentageDiv = document.getElementById("percentage");

        if (evt.lengthComputable) {

            progressBar.max = evt.total;

            progressBar.value = evt.loaded;

            percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100)
                + "%";

        }

    }
</script>
</body>
</html>
