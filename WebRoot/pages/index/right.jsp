<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href=<%=basePath %>/>
<title>美家秀后台管理系统</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link href="public/img/logo2.png" rel="shortcut icon">
<link href="public/css/bootstrap.min.css" rel="stylesheet">
<link href="public/css/style_coo.css" rel="stylesheet">
<script src="public/js/jquery-1.10.2.min.js"></script>
<script src="public/js/bootstrap.min.js"></script>
<script src="public/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="public/js/inputvalid.js"></script>
<script src="public/js/app.plugin.js"></script>
<script src="public/js/actions.js"></script>
<script src="public/js/comm.js"></script><style>
<!--
body {
	margin:0px;
	padding:15px;
	text-align: center;
}

h1 {
color:#333;
margin-top: 100px;
margin-left: auto;
margin-right: auto;
}
-->
</style>
<meta charset="utf-8">
</head>
<body>
	<div class="row padder-lx">
		<div class="col-xs-9 bg-light" style="height:555px;">
			<h1 class="text-black">欢迎进入美家秀运营管理系统</h1>
		</div>
	</div>
</body>
</html>