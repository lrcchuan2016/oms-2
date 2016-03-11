<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
<link href="public/iconfont/iconfont.css" rel="stylesheet">
<link href="public/css/style_coo.css" rel="stylesheet">
<script src="public/js/jquery-1.10.2.min.js"></script>
<script src="public/js/bootstrap.min.js"></script>
<script src="public/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="public/js/inputvalid.js"></script>
<script src="public/js/app.plugin.js"></script>
<script src="public/js/actions.js"></script>
<script src="public/js/comm.js"></script>
<style type="text/css">
body {
	background-color: #20272e;
	padding: 0 10px;
}

h2 {
	color: #FFFFFF;
	font-size: 22px;
	margin-top:10px;
}

h2 small {
	font-size: 35%;
}

.navbar-header > img{ position:absolute; top:8px; left:13px;}
.navbar-header .navbar-brand{ padding-left:56px; color:#fff;}
.navbar-nav .iconfont { margin-right:5px;}

.navbar-inverse { background-color:#20272e;}
</style>
</head>
<body>
<nav class="navbar  navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <img src="public/img/logo2.png" width="36" height="36">
      <a class="navbar-brand" href="javascript:void(0)">美家秀运营管理系统</a>
    </div>
    
	<!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav" style="margin-left:16px;">
        <li class="active"><a target="_top" href="http://oms.meijx.cn/oms/main">OMS</a></li>
        <li><a target="_blank" href="http://bbs.meijx.cn/admin.php">BBS</a></li>
        
      </ul>
      
      <ul class="nav navbar-nav navbar-right">
        <li><a href="managerAction_initEditPage?paramMap['type']=1&paramMap['id']=${admin.id }" target="main"><i class="iconfont">&#x3437;</i>${admin.name } [${admin.roleName }]</a></li>
        <li><a href="javascript:$.logout()"><i class="iconfont logout">&#xf013a;</i>退出</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
	
</body>
<script>
var W = window.top;
$.extend({
	logout : function(){
		W.$.confirm("确定要注销吗？",function(){
			top.location.href = "managerAction_logout";
		});
	}
});
</script>
</html>
