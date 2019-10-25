<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="/front/lib/html5shiv.js"></script>
    <script type="text/javascript" src="/front/lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/front/css/comment.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/front/static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/front/static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/front/lib/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/front/static/h-ui.admin/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/front/static/h-ui.admin/css/style.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="/front/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> ${sessionScope.username} <span class="c-gray en">&gt;</span> 文件列表 </nav>
<div class="page-container">

    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="1">
            <a style="margin-right: 5px;"href="${pageContext.request.contextPath}\Exit" class="btn btn-danger radius"><i class="Hui-iconfont"></i> 退出</a>
            <a onclick="member_show('上传文件','${pageContext.request.contextPath}/upload','10001','500','300')" target="_blank" class="btn btn-primary radius"><i class="icon-trash"></i>上传文件</a>
            <label id="tip" style="color: red">
            <c:if test="${Error!=null||Message!=null}">
                ${Error==null?Message:Error}
                ${requestScope.remove("Error")}
                ${requestScope.remove("Message")}
                ${sessionScope.remove("Error")}
                ${sessionScope.remove("Message")}
            </c:if>
            <label/>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="40">ID</th>
                <th width="170">文件名</th>
                <th width="50">存储人</th>
                <th width="70">存储大小(MB)</th>
                <th width="50">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list }" var="list" varStatus="stat">
                <tr class="text-c">
                    <td>${list.id }</td>
                    <td>${list.fakename}</td>
                    <td>${list.username}</td>
                    <td><fmt:formatNumber type="number" value="${list.size/1048576}" maxFractionDigits="2"></fmt:formatNumber></td>
                    <td class="td-manage">
                        <a title="下载" href="${pageContext.request.contextPath}/DownLoad/${list.id}" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6de;</i></a>
                        <a title="删除" href="${pageContext.request.contextPath}/DeleteFile/${list.id}" onclick="return checkDelete()" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div class="update_comment">
    <div class="comment-wrap">
        <div class="comment-block">
        <form action="${pageContext.request.contextPath}/insertCommon" method="post" onsubmit="return checkText(this);">
            <textarea name="text" id="" cols="30" rows="3" placeholder="Say somthing..."></textarea>
            <input name="username" type="hidden" value="${sessionScope.username}"/>
            <input name="good_number" value="0" type="hidden"/>
            <jsp:useBean id="now" class="java.util.Date"/>
            <input name="comment_day" value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="hidden"/>
            <input class="btn" type="submit" value="提交评论"/>
        </form>
    </div>
</div>
</div>
<div class="comment">
    <c:forEach items="${commons}" var="common">
    <div class="comment-wrap">
        <div class="comment-block">
            <label class="comment-user">${common.username}</label>
            <p class="comment-text">${common.text}</p>
            <div class="bottom-comment">
                <div class="comment-date"><fmt:formatDate value="${common.comment_day}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
                <ul class="comment-actions">
                    <li id="good" class="good">
                        <c:choose>
                            <c:when test="${common.good!=true}">
                                <div style="display: inline" id="good${common.id}">
                                <p id="goodnumber" style="display: inline" class="goodnumber">(${common.good_number})</p>
                                <a href="javascript:good(${common.id},${common.good_number});">
                                <span class="glyphicon glyphicon-thumbs-up"></span></a>
                                </div>
                            </c:when>
                                <c:otherwise>
                                <div style="display: inline" id="good${common.id}">
                                <p id="goodnumber" style="display: inline" class="goodnumber">(${common.good_number})</p>
                                <a href="javascript:ungood(${common.id},${common.good_number});">
                                <span class="glyphicon glyphicon-thumbs-up isgood"></span></a>
                                </div>
                                </c:otherwise>
                        </c:choose>
                    </li>
                    <c:if test="${common.username==sessionScope.username}">
                        <li class="complain"><a onclick="return checkDelete();" href="${pageContext.request.contextPath}/deleteCommon/${common.id}">Delete</a></li>
                    </c:if>
                    <li class="complain">Complain</li>
                    <li class="reply">Reply</li>
                </ul>
            </div>
        </div>
    </div>
    </c:forEach>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/front/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/front/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/front/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/front/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${pageContext.request.contextPath}/front/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/front/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/front/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
    function good(id,goodnumber){
        var tip=$('#tip');
        var good=$('#good'+id);
        var targeturl='${pageContext.request.contextPath}/goodCommon';
        $.ajax({
            url:targeturl,
            type:'post',
            data:{'id':id},
            dataType:'json',
            success:function (result) {
                if("true"==result.success){
                    tip.html(result.Message);
                    good.html("");
                    var targetnumber=parseInt(goodnumber)+1;
                    var html1="<p id=\"goodnumber\" style=\"display: inline\" class=\"goodnumber\""+">("+targetnumber+")</p>";
                    var html2="<a href=\"javascript:ungood("+id+","+targetnumber+");\">";
                    var html3="<span class=\"glyphicon glyphicon-thumbs-up isgood\"></span></a>";
                    good.html(html1+html2+html3);
                }else{
                    tip.html(result.Error);
                }
            }
        });
    }
    function ungood(id,goodnumber){
        var tip=$('#tip');
        var good=$('#good'+id);
        var targeturl='${pageContext.request.contextPath}/unGoodCommon';
        $.ajax({
            url:targeturl,
            type:'post',
            data:{'id':id},
            dataType:'json',
            success:function (result) {
                if("true"==result.success){
                    tip.html(result.Message);
                    good.html("");
                    var targetnumber=parseInt(goodnumber)-1;
                    html1="<p id=\"goodnumber\" style=\"display: inline\" class=\"goodnumber\">("+targetnumber+")</p>";
                    html2="<a href=\"javascript:good("+id+","+targetnumber+");\">";
                    html3="<span class=\"glyphicon glyphicon-thumbs-up\"></span></a>";
                    good.html(html1+html2+html3);
                }else{
                    tip.html(result.Error);
                }
            }
        });
    }
    $(function(){
        $('.table-sort').dataTable({
            "aaSorting": [[ 0, "desc" ]],//默认第几个排序
            "bStateSave": true,//状态保存
            "aoColumnDefs": [
                //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
                {"orderable":false,"aTargets":[1,2,3,4]}// 制定列不参与排序
            ]
        });

    });
    function checkText(form){
        if(form.text.value==""){
            alert("评论不能为空");
            return false;
        }
    }
    function checkDelete(){
        if(confirm("您真的要删除吗?")==true){
            return true;
        }else{
            return false;
        }
    }
    /*用户-添加*/
    function member_add(title,url,w,h){
        layer_show(title,url,w,h);
    }
    /*用户-查看*/
    function member_show(title,url,id,w,h){
        layer_show(title,url,w,h);
    }

    /*用户-编辑*/
    function member_edit(title,url,id,w,h){
        layer_show(title,url,w,h);
    }
    /*密码-修改*/
    function change_password(title,url,id,w,h){
        layer_show(title,url,w,h);
    }

</script>
</body>
</html>