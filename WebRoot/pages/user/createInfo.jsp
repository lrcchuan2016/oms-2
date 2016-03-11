<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
  <head>
    <base href="<%=basePath%>">
    <title>美家秀后台管理系统</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
    
    <link href="public/img/logo2.png" rel="shortcut icon">
	<link href="public/css/bootstrap.min.css" rel="stylesheet">
	<link href="public/css/style_coo.css" rel="stylesheet">
	
	<script src="public/js/jquery-1.10.2.min.js"></script>
	<script src="public/js/bootstrap.min.js"></script>
	<script src="public/js/slimscroll/jquery.slimscroll.min.js"></script>
	<script src="public/js/jquery.livequery.js"></script>

  </head>
  
  <body>
  		<div class="container-fluid bg-white height-md">
 			<ol class="breadcrumb">
      			<li><a href="javascript:void(0)">首页</a></li>
      			<li><a href="javascript:void(0)">用户管理</a></li>
      			<li class="active">创建提示</li>
    		</ol>
    		<div class="row"><p class="text-center" style="font-size: 20px;"><strong>${result }</strong></p></div>
    		<div class="row">
    			<table class="table table-striped">
	 				<tr>
						<td>手机号码：</td>
						<td><s:property value="userBean.account"/></td>
	 				</tr>
	 				<tr>
						<td>用户呢称：</td>
						<td><s:property value="userBean.nickname"/></td>
	 				</tr>
	 				<tr>
						<td>用户邮箱：</td>
						<td><s:property value="userBean.email"/></td>
	 				</tr>
				</table>
			</div>
    </div>
  </body>
</html>
