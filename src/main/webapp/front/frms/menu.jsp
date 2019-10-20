<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
 <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
		* {
            margin: 0;
            padding: 0;
            font-family:"微软雅黑";
        }
        a{
		text-decoration:none;
		}
        body {
            max-width: 1920px;
        }
        #btn {
            position: absolute;
            top: 22%;
            left: 298px;
            height: 150px;
            border: 0;
            border-left: 1px solid rgb(23, 32, 43, 0.2);
            background: #000000;
            color: #fff;
            cursor: pointer;
        }
        .box {
            float: left;
            position: relative;
            left: 0;
            height: 100%;
            width: 300px;
            margin-left: 0;
            display: block;
            background-color: #D4D4D4;
            -moz-transition: margin-left 2s;
            transition: margin-left 2s;
        }     
        aside ul li {
            height: 40px;
            line-height: 40px;
            border-top: 1px solid #fff;
            border-bottom: 1px solid #fff;
            list-style-type: none;
            text-align: center;
        }     
        aside ul li a {
            width: 100%;
            height: 100%;
            text-decoration-line: none;
            color: #000000;
            display: block;
        }   
        aside ul li a:hover {
            background: #EBEBEB;
        } 
        section {
            width: 100%;
            height: 100%;
            background: #d4d4d4;
            height: 1000px;
        }
        section h1 {
            text-align: center;
            border-bottom: 1px solid #fff;
        }
</style>
</head>
<body>
<aside class="box" id="test">
        <button id="btn">三</button>
        <ul>
            <li>
                <h3>菜单</h3>
            </li>
            <li><a href="../FindAllStaffServlet?url=addTask.jsp" target="mainWin">添加任务</a></li>
            <li><a href="../addEmp.jsp" target="mainWin">添加人物</a></li>
            <li><a href="../addPlan.jsp?taskid=1" target="mainWin">添加计划</a></li>
            <li><a href="../FindAllEmpServlet" target="mainWin">查询员工</a></li>
            <li><a href="../FindAllEmpServlet" target="mainWin">计划管理</a></li>
            <li><a href="../TaskManagementServlet?username=${emp.username}" target="mainWin">计划管理</a></li>
            <li><a href="../TaskListServlet" target="mainWin">查询计划</a></li>
        </ul>
    </aside>
</body>
<script type="text/javascript">
	var box = document.getElementById("test")
	var btn = document.getElementById("btn")
	btn.onclick = function() {	
 	   if (box.offsetLeft == 0) {
 	       box.style['margin-left'] = -300 + "px"
 		}
 	  else {
 	       box.style['margin-left'] = 0 + "px"
   		 }
}
</script>
</html>