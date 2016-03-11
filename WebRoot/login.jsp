<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
<!--
body {
	background-color: #737373;
}

h2 {
	color: #FFFFFF;
	font-size: 22px;
}

h2 small {
	font-size: 35%;
}

.line {
	border-bottom: 1px solid #eaeef1;
	height: 2px;
	margin: 5px 0 15px 0;
	font-size: 0;
	overflow: hidden;
}

.login-box {
	max-width: 400px;
	min-width: 400px;
	position:absolute;/*层漂浮*/  
    top:50%;  
    left:50%;  
    margin-top:-110px;
    margin-left:-200px;
    background:#fff;  
    border:1px solid red;
    border-radius: 4px;
}

.login-box header {
	height: 36px;
	line-height: 36px;
	border-color: #555;
	padding: 5px 15px;
	border-radius: 4px;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	background: #fff;
	box-shadow: inset 0 1px 0 0 rgba(255, 255, 255, 0.1);
}
.login-box header i {
	float:left;
	margin:6px 5px 0px 0px;
}
.login-box header h2 {
	float:left;
	font-size: 16px;
	color: #5E5C5C;
	margin: 0;
	line-height: 30px;
	font-family: 'Lato', Arial, Helvetica, sans-serif;
	text-shadow: none;
}

.control-group {
	margin-bottom: 0;
	padding: 10px 10px 15px 30px;
}

.control-btn {
	margin:10px 0 20px 0px;
	text-align:center;
}
.control-btn .btn{
	background: #E7E4E4;
	height: 24px;
	width: 150px;
	line-height: 8px;
	font-size: 8px;
	border-radius: 50px;
	-moz-border-radius: 50px;
	-webkit-border-radius: 50px;
	border: 1px solid #DADADA;
	box-shadow: inset 0 1px 0 0 rgba(255, 255, 255, 0.1);
	-webkit-box-shadow: none;
	outline: none;
}
.control-btn .btn:hover {
	background: #87C6F9;
}

.control-title{margin:0 auto;
	text-align: center;
	color: #999999;
	margin-top:-5px;
	display: inline-block;
	vertical-align: middle;
	font:normal 10px/12px 方正兰亭准黑_GBK,Microsoft YaHei,Tahoma, Geneva, sans-serif;
}
.control-title input {
	margin-right:20px;
	width:180px;
	height:25px;
	font-size:12px;
	line-height:25px;
	padding-left:10px;
	border: 1px solid #ccc;
	border-radius: 15px;
	box-shadow: none;
	-webkit-box-shadow: none;
}

input:focus{
	outline:none;
	border:#87C6F9 1px solid;
	box-shadow: 0 0 8px rgba(103, 166, 217, 1);
}
.memberFont{
	color: #fff;
	font:normal 26px/28px 方正兰亭准黑_GBK,Microsoft YaHei,Tahoma, Geneva, sans-serif;
}
.memberFont i {
  width: 36px;
  height: 36px;
  margin-top:-5px;
  display: inline-block;
  vertical-align: middle;
  position: relative;
  background-image: url(public/img/logo2.png);
}
-->
</style>
</head>
<body>
	<div style="background-color: #080808; height:50px;position:absolute;top:0px;width:100%;">
		<h2 class="memberFont" style="margin-top:10px;">
			&nbsp;&nbsp;&nbsp;<i class="memberFont"></i>
			美家秀后台管理系统&nbsp;&nbsp;<small><em>version:1.0.3</em></small>
		</h2>
	</div>
	<div class="login-box">
		<header>
			<h2 style="font-weight:bold;"><i class="glyphicon glyphicon-home"></i>Admin Log In</h2>
		</header>
		<div class="line"></div>
		<div id="tipMsg" class="control-group alert alert-danger">${tipMsg}</div>
		<form class="form-signin" id="loginForm" name="loginForm" method="post" onsubmit="return checkInput();">
			<div class="control-group">
				<span class="glyphicon glyphicon-user"></span>
				<span class="control-title">| Entry Your Username&nbsp; <input type="text" placeholder="Username" maxlength="20" data-valid="[{rule:'not_empty'}]" name="managerList[0].account"></span>
			</div>
			<div class="control-group">
				<span class="glyphicon glyphicon-asterisk"></span>
				<span class="control-title">| Entry Your Password&nbsp; <input type="password" placeholder="Password" maxlength="16" data-valid="[{rule:'not_empty'}]" name="managerList[0].password"></span>
			</div>
			<div class="line"></div>
			<div class="control-btn">
				<input class="btn" type="submit" value="Log &nbsp; In!">
			</div>
		</form>
			
	</div>
</body>
<script type="text/javascript">
var formObj = null;

if (top.location != self.location) {
    top.location = self.location;
}

$(function() {
    $("#loginForm").get(0).action = "main";
    if ($.trim($("#tipMsg").html()).length > 0) {
        $("#tipMsg").slideDown();
        window.setTimeout(function() {$("#tipMsg").slideUp();}, 3e3);
    } else { $("#tipMsg").hide(); }
    formObj = $("#loginForm").inputValid(valid_message_options);
    $("input[name='managerList[0].account']").focus();
});

function checkInput() {
    return formObj.validate_all();
}
</script>
</html>
