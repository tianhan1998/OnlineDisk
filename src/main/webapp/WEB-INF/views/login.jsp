<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1"></meta>
	<title>登录</title>
	<style type="text/css">
		body {
			margin: 0;
			background-color: #fbfbfb;
			text-align: center;
		}

		.lowin {
			/* variables */
			--color-primary: #44a0b3;
			--color-grey: rgba(68, 160, 179, .06);
			--color-dark: rgba(68, 160, 179, .5);
			--color-semidark: rgba(68, 160, 179, .5);

			text-align: center;
			margin: 60px 0 0 0;
			font-family: 'Segoe UI';
			font-size: 14px;
		}

		.lowin .lowin-wrapper {
			-webkit-transition: all 1s;
			-o-transition: all 1s;
			transition: all 1s;
			-webkit-perspective: 1000px;
			perspective: 1000px;
			position: relative;
			height: 100%;
			width: 360px;
			margin: 0 auto;
		}

		.lowin.lowin-red {
			--color-primary: #ff6464;
			--color-grey: rgba(255, 100, 100, .06);
			--color-dark: rgba(255, 100, 100, .8);
			--color-semidark: rgba(255, 100, 100, .55);
		}

		.lowin.lowin-green {
			--color-primary: #D0EF84;
			--color-grey: rgba(208, 239, 132, .15);
			--color-dark: rgba(208, 239, 132, 1);
			--color-semidark: rgba(208, 239, 132, .6);
		}

		.lowin.lowin-purple {
			--color-primary: #6C567B;
			--color-grey: rgba(108, 86, 123, .08);
			--color-dark: rgba(108, 86, 123, .7);
			--color-semidark: rgba(108, 86, 123, .45);
		}

		.lowin.lowin-blue {
			--color-primary: #0081C6;
			--color-grey: rgba(0, 129, 198, .05);
			--color-dark: rgba(0, 129, 198, .7);
			--color-semidark: rgba(0, 129, 198, .45);
		}

		.lowin a {
			color: var(--color-primary);
			text-decoration: none;
			border-bottom: 1px dashed var(--color-semidark);
			margin-top: -3px;
			padding-bottom: 2px;
		}

		.lowin * {
			-webkit-box-sizing: border-box;
			box-sizing: border-box;
		}

		.lowin .lowin-brand {
			overflow: hidden;
			width: 100px;
			height: 100px;
			margin: 0 auto -50px auto;
			border-radius: 50%;
			-webkit-box-shadow: 0 4px 40px rgba(0, 0, 0, .07);
			box-shadow: 0 4px 40px rgba(0, 0, 0, .07);
			padding: 10px;
			background-color: #fff;
			z-index: 1;
			position: relative;
		}

		.lowin .lowin-brand img {
			width: 100%;
		}

		.lowin .lowin-box {
			width: 100%;
			position: absolute;
			left: 0;
		}

		.lowin .lowin-box-inner {
			background-color: #fff;
			-webkit-box-shadow: 0 7px 25px rgba(0, 0, 0, .08);
			box-shadow: 0 7px 25px rgba(0, 0, 0, .08);
			padding: 60px 25px 25px 25px;
			/*text-align: left;*/
			border-radius: 3px;
		}

		.lowin .lowin-box::after {
			content: ' ';
			-webkit-box-shadow: 0 0 25px rgba(0, 0, 0, .1);
			box-shadow: 0 0 25px rgba(0, 0, 0, .1);
			-webkit-transform: translate(0, -92.6%) scale(.88);
			-ms-transform: translate(0, -92.6%) scale(.88);
			transform: translate(0, -92.6%) scale(.88);
			border-radius: 3px;
			position: absolute;
			top: 100%;
			left: 0;
			width: 100%;
			height: 100%;
			background-color: #fff;
			z-index: -1;
		}

		.lowin .lowin-box.lowin-flip {
			-webkit-transform: rotate3d(0, 1, 0, -180deg);
			transform: rotate3d(0, 1, 0, -180deg);
			display: none;
			opacity: 0;
		}

		.lowin .lowin-box p {
			color: var(--color-semidark);
			font-weight: 700;
			margin-bottom: 20px;
			text-align: center;
		}

		.lowin .lowin-box .lowin-group {
			margin-bottom: 30px;
		}

		.lowin .lowin-box label {
			margin-bottom: 5px;
			display: inline-block;
			width: 100%;
			color: var(--color-semidark);
			font-weight: 700;
		}

		.lowin .lowin-box label a {
			float: right;
		}

		.lowin .lowin-box .lowin-input {
			background-color: var(--color-grey);
			color: var(--color-dark);
			border: none;
			border-radius: 3px;
			padding: 15px 20px;
			width: 100%;
			outline: 0;
		}

		.lowin .lowin-box .lowin-btn {
			display: inline-block;
			width: 100%;
			border: none;
			color: #fff;
			padding: 15px;
			border-radius: 3px;
			background-color: var(--color-primary);
			-webkit-box-shadow: 0 2px 7px var(--color-semidark);
			box-shadow: 0 2px 7px var(--color-semidark);
			font-weight: 700;
			outline: 0;
			cursor: pointer;
			-webkit-transition: all .5s;
			-o-transition: all .5s;
			transition: all .5s;
		}

		.lowin .lowin-box .lowin-btn:active {
			-webkit-box-shadow: none;
			box-shadow: none;
		}

		.lowin .lowin-box .lowin-btn:hover {
			opacity: .9;
		}

		.lowin .text-foot {
			text-align: center;
			padding: 10px;
			font-weight: 700;
			margin-top: 20px;
			color: var(--color-semidark);
		}

		.lowin .lowin-footer {
			text-align: center;
			margin: 30px 0;
			font-size: 12px;
			color: var(--color-semidark);
			font-weight: 700;
		}

		.lowin .login-back-link {
			-webkit-transition: all 1s;
			-o-transition: all 1s;
			transition: all 1s;
			display: none;
			opacity: 0;
		}

		/* animation */
		.lowin .lowin-box.lowin-animated {
			-webkit-animation-name: LowinAnimated;
			animation-name: LowinAnimated;
			-webkit-animation-duration: 1s;
			animation-duration: 1s;
			-webkit-animation-fill-mode: forwards;
			animation-fill-mode: forwards;
			-webkit-animation-timing-function: ease-in-out;
			animation-timing-function: ease-in-out;
		}

		.lowin .lowin-box.lowin-animatedback {
			-webkit-animation-name: LowinAnimatedBack;
			animation-name: LowinAnimatedBack;
			-webkit-animation-duration: 1s;
			animation-duration: 1s;
			-webkit-animation-fill-mode: forwards;
			animation-fill-mode: forwards;
			-webkit-animation-timing-function: ease-in-out;
			animation-timing-function: ease-in-out;
		}

		.lowin .lowin-box.lowin-animated-flip {
			-webkit-animation-name: LowinAnimatedFlip;
			animation-name: LowinAnimatedFlip;
			-webkit-animation-duration: 1s;
			animation-duration: 1s;
			-webkit-animation-fill-mode: forwards;
			animation-fill-mode: forwards;
			-webkit-animation-timing-function: ease-in-out;
			animation-timing-function: ease-in-out;
		}

		.lowin .lowin-box.lowin-animated-flipback {
			-webkit-animation-name: LowinAnimatedFlipBack;
			animation-name: LowinAnimatedFlipBack;
			-webkit-animation-duration: 1s;
			animation-duration: 1s;
			-webkit-animation-fill-mode: forwards;
			animation-fill-mode: forwards;
			-webkit-animation-timing-function: ease-in-out;
			animation-timing-function: ease-in-out;
		}

		.lowin .lowin-brand.lowin-animated {
			-webkit-animation-name: LowinBrandAnimated;
			animation-name: LowinBrandAnimated;
			-webkit-animation-duration: 1s;
			animation-duration: 1s;
			-webkit-animation-fill-mode: forwards;
			animation-fill-mode: forwards;
			-webkit-animation-timing-function: ease-in-out;
			animation-timing-function: ease-in-out;
		}

		.lowin .lowin-group.password-group {
			-webkit-transition: all 1s;
			-o-transition: all 1s;
			transition: all 1s;
		}

		.lowin .lowin-group.password-group.lowin-animated {
			-webkit-animation-name: LowinPasswordAnimated;
			animation-name: LowinPasswordAnimated;
			-webkit-animation-duration: 1s;
			animation-duration: 1s;
			-webkit-animation-fill-mode: forwards;
			animation-fill-mode: forwards;
			-webkit-animation-timing-function: ease-in-out;
			animation-timing-function: ease-in-out;
			-webkit-transform-origin: 0 0;
			-ms-transform-origin: 0 0;
			transform-origin: 0 0;
		}

		.lowin .lowin-group.password-group.lowin-animated-back {
			-webkit-animation-name: LowinPasswordAnimatedBack;
			animation-name: LowinPasswordAnimatedBack;
			-webkit-animation-duration: 1s;
			animation-duration: 1s;
			-webkit-animation-fill-mode: forwards;
			animation-fill-mode: forwards;
			-webkit-animation-timing-function: ease-in-out;
			animation-timing-function: ease-in-out;
			-webkit-transform-origin: 0 0;
			-ms-transform-origin: 0 0;
			transform-origin: 0 0;
		}

		/* keyframes */
		@-webkit-keyframes LowinAnimated {
			0% {
				-webkit-transform: rotate3d(0);
				transform: rotate3d(0);
			}
			99% {
				opacity: 1;
			}
			100% {
				opacity: 0;
				-webkit-transform: rotate3d(0, 1, 0, 180deg);
				transform: rotate3d(0, 1, 0, 180deg);
			}
		}
		@keyframes LowinAnimated {
			0% {
				-webkit-transform: rotate3d(0);
				transform: rotate3d(0);
			}
			99% {
				opacity: 1;
			}
			100% {
				opacity: 0;
				-webkit-transform: rotate3d(0, 1, 0, 180deg);
				transform: rotate3d(0, 1, 0, 180deg);
			}
		}

		@-webkit-keyframes LowinAnimatedBack {
			0% {
				opacity: 0;
				-webkit-transform: rotate3d(0, 1, 0, 180deg);
				transform: rotate3d(0, 1, 0, 180deg);
			}
			99% {
				opacity: 1;
			}
			100% {
				opacity: 1;
				-webkit-transform: rotate3d(0);
				transform: rotate3d(0);
			}
		}

		@keyframes LowinAnimatedBack {
			0% {
				opacity: 0;
				-webkit-transform: rotate3d(0, 1, 0, 180deg);
				transform: rotate3d(0, 1, 0, 180deg);
			}
			99% {
				opacity: 1;
			}
			100% {
				opacity: 1;
				-webkit-transform: rotate3d(0);
				transform: rotate3d(0);
			}
		}

		@-webkit-keyframes LowinAnimatedFlip {
			0% {
				-webkit-transform: rotate3d(0, 1, 0, -180deg);
				transform: rotate3d(0, 1, 0, -180deg);
				opacity: 0;
			}
			99% {
				opacity: 1;
			}
			100% {
				opacity: 1;
				-webkit-transform: rotate3d(0, 0, 0, 180deg);
				transform: rotate3d(0, 0, 0, 180deg);
			}
		}

		@keyframes LowinAnimatedFlip {
			0% {
				-webkit-transform: rotate3d(0, 1, 0, -180deg);
				transform: rotate3d(0, 1, 0, -180deg);
				opacity: 0;
			}
			99% {
				opacity: 1;
			}
			100% {
				opacity: 1;
				-webkit-transform: rotate3d(0, 0, 0, 180deg);
				transform: rotate3d(0, 0, 0, 180deg);
			}
		}

		@-webkit-keyframes LowinAnimatedFlipBack {
			0% {
				opacity: 1;
				-webkit-transform: rotate3d(0, 0, 0, 180deg);
				transform: rotate3d(0, 0, 0, 180deg);
			}
			95% {
				opacity: 0;
			}
			100% {
				-webkit-transform: rotate3d(0, 1, 0, -180deg);
				transform: rotate3d(0, 1, 0, -180deg);
				opacity: 0;
			}
		}

		@keyframes LowinAnimatedFlipBack {
			0% {
				opacity: 1;
				-webkit-transform: rotate3d(0, 0, 0, 180deg);
				transform: rotate3d(0, 0, 0, 180deg);
			}
			95% {
				opacity: 0;
			}
			100% {
				-webkit-transform: rotate3d(0, 1, 0, -180deg);
				transform: rotate3d(0, 1, 0, -180deg);
				opacity: 0;
			}
		}

		@-webkit-keyframes LowinBrandAnimated {
			0% {
				-webkit-transform: translate(0, 0);
				transform: translate(0, 0);
			}
			50% {
				-webkit-transform: translate(0, -120px);
				transform: translate(0, -120px);
			}
			100% {
				-webkit-transform: translate(0, 0);
				transform: translate(0, 0);
			}
		}

		@keyframes LowinBrandAnimated {
			0% {
				-webkit-transform: translate(0, 0);
				transform: translate(0, 0);
			}
			50% {
				-webkit-transform: translate(0, -120px);
				transform: translate(0, -120px);
			}
			100% {
				-webkit-transform: translate(0, 0);
				transform: translate(0, 0);
			}
		}

		@-webkit-keyframes LowinPasswordAnimated {
			0% {
				-webkit-transform: rotate3d(0, 0, 0, 0);
				transform: rotate3d(0, 0, 0, 0);
			}
			30% {
				opacity: 1;
			}
			60% {
				opacity: 0;
			}
			100% {
				opacity: 0;
				-webkit-transform: rotate3d(1, 0, 0, -180deg);
				transform: rotate3d(1, 0, 0, -180deg);
				z-index: -1;
			}
		}

		@keyframes LowinPasswordAnimated {
			0% {
				-webkit-transform: rotate3d(0, 0, 0, 0);
				transform: rotate3d(0, 0, 0, 0);
			}
			30% {
				opacity: 1;
			}
			60% {
				opacity: 0;
			}
			100% {
				opacity: 0;
				-webkit-transform: rotate3d(1, 0, 0, -180deg);
				transform: rotate3d(1, 0, 0, -180deg);
				z-index: -1;
			}
		}

		@-webkit-keyframes LowinPasswordAnimatedBack {
			0% {
				opacity: 0;
				-webkit-transform: rotate3d(1, 0, 0, -180deg);
				transform: rotate3d(1, 0, 0, -180deg);
			}
			40% {
				opacity: 0;
			}
			60% {
				opacity: 1;
			}
			100% {
				-webkit-transform: rotate3d(0, 0, 0, 0);
				transform: rotate3d(0, 0, 0, 0);
			}
		}

		@keyframes LowinPasswordAnimatedBack {
			0% {
				opacity: 0;
				-webkit-transform: rotate3d(1, 0, 0, -180deg);
				transform: rotate3d(1, 0, 0, -180deg);
			}
			40% {
				opacity: 0;
			}
			60% {
				opacity: 1;
			}
			100% {
				-webkit-transform: rotate3d(0, 0, 0, 0);
				transform: rotate3d(0, 0, 0, 0);
			}
		}

		@media screen and (max-width: 320px) {
			.lowin .lowin-wrapper {
				width: 100%;
			}
			.lowin .lowin-box {
				padding: 0 10px;
			}
		}
	</style>
	<script src="${pageContext.request.contextPath}/front/lib/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/front/css/bootstrap.min.css">
	<script src="${pageContext.request.contextPath}/front/js/bootstrap.min.js"></script>
</head>

<body>
<div class="lowin">
	<div class="lowin-brand">
		<img src="front/kodinger.jpg" alt="logo">
	</div>
	<div class="lowin-wrapper">
		<div class="lowin-box lowin-login">
			<div class="lowin-box-inner">
				<form action="SignIn" method="post" onsubmit="return check(this)">
					<p>网盘beta1</p>
					<div class="lowin-group">
						<label>姓名</label>
						<input type="text"  name="username" class="lowin-input" placeholder="输入您的姓名">
					</div>
					<div class="lowin-group password-group">
						<label>密码</label>
						<input type="password"  name="password" class="lowin-input" placeholder="输入您的密码">
					</div>
					<input type="checkbox" name="autologin" value="true" style="margin-bottom:20px">30天自动登录
					<c:if test="${Error!=null}">
						<label style="color:red">${Error}</label>
						${sessionScope.remove("Error")}
					</c:if>
					<button class="lowin-btn login-btn">
						登录
					</button>
				</form>
				<div class="text-foot">
					<a href="" class="register-link">网盘测试</a>
				</div>
				<div class="text-foot">
					<a href="" class="update-detail" style="!important;color: red">更新历史</a>
				</div>
				<div class="text-foot">
					<a href="signUp" class="register-link">注册</a>
				</div>
<%--				<div class="text-foot">--%>
<%--					<a target="_blank" href="tutorial/tutorial.jsp" class="register-link">偷题教程</a>--%>
<%--				</div>--%>
			</div>
		</div>
		<div class="lowin-box lowin-update">
			<div class="lowin-box-inner">
				<div>
					<button style="" class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
						更新历史
					</button>
					<div class="collapse" id="collapseExample">
						<div class="well" style="overflow: scroll;height: 400px">
							<label style="!important; color:black">版本1.7</label>
							<ul>
								<li>--------------------------------------</li>
								<li style="!important;text-align: left;font-weight: bold">添加文件目录系统（仅适配Windows服务器)</li>
								<li>--------------------------------------</li>
							</ul>
							<label style="!important; color:black">版本1.6</label>
							<ul>
								<li>--------------------------------------</li>
								<li style="!important;text-align: left;font-weight: bold">把提交评论修改为ajax实现</li>
								<li>--------------------------------------</li>
							</ul>
							<label style="!important; color:black">版本1.5</label>
							<ul>
								<li>--------------------------------------</li>
								<li style="!important;text-align: left">增加评论删除功能</li>
								<li style="!important;text-align: left">实现了ajax刷新评论点赞取消功能</li>
								<li style="!important;text-align: left">修复了超过了8小时会掉链接出500错误的bug</li>
								<li>--------------------------------------</li>
							</ul>
							<label style="!important; color:black">版本1.4</label>
							<ul>
								<li>--------------------------------------</li>
								<li style="!important;text-align: left">增加评论功能</li>
								<li style="!important;text-align: left">增加了总上传文件大小上限</li>
								<li style="!important;text-align: left">数据库更换时区</li>
								<li style="!important;text-align: left">文件大小修改为MB显示</li>
								<li>--------------------------------------</li>
							</ul>
							<label style="!important; color:black">版本1.3</label>
							<ul>
								<li>--------------------------------------</li>
								<li style="!important;text-align: left">添加多任务上传</li>
								<li style="!important;text-align: left">在登录界面添加更新历史</li>
								<li style="!important;text-align: left">修复可以上传空文件的bug</li>
								<li style="!important;text-align: left">给上传文件添加了事务(虽然上传一般不出错)</li>
								<li style="!important;text-align: left">前端页面描述修改</li>
								<li>--------------------------------------</li>
							</ul>
							<label style="!important; color:black">版本1.2</label>
							<ul>
								<li>--------------------------------------</li>
								<li style="!important;text-align: left">ajax判断注册用户名是否重复</li>
								<li style="!important;text-align: left">修复第一次访问/路径下会报500的bug（cookie没有判断null导致空指针）</li>
								<li>--------------------------------------</li>
							</ul>
							<label style="!important; color:black">版本1.1</label>
							<ul>
								<li>--------------------------------------</li>
								<li style="!important;text-align: left">文件的删除功能</li>
								<li style="!important;text-align: left">上传下载的重写</li>
								<li style="!important;text-align: left">前端页面修正</li>
								<li>--------------------------------------</li>
							</ul>
							<label style="!important; color:black">版本1.0</label>
							<ul>
								<li>--------------------------------------</li>
								<li style="!important;text-align: left">项目的登录和注册（包括cookie保持登陆状态）</li>
								<li style="!important;text-align: left">文件的上传和下载</li>
								<li style="!important;text-align: left">前端页面基本完成</li>
								<li>--------------------------------------</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="text-foot">
					<a href="" class="update-link">返回</a>
				</div>
			</div>
		</div>
		<div class="lowin-box lowin-register">
			<div class="lowin-box-inner">
				<p>制作人员</p>
				<p>田涵</p>
				<div class="text-foot">
					<a href="" class="login-link">返回</a>
				</div>
			</div>
		</div>
	</div>
		<footer class="lowin-footer">
	</footer>
</div>

<script src="front/js/auth.js"></script>
<script>
	Auth.init({
		login_url: '#login',
		forgot_url: '#forgot'
	});

	function check(form){
		if(form.username.value=="") {
			alert("用户名和密码不能为空");
			return false;
		}else if(form.password.value==""){
			alert("用户名和密码不能为空");
			return false;
		}
	}
</script>

</body>
</html>