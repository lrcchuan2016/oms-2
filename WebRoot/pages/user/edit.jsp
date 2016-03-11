<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>美家秀后台管理系统</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<link href="public/img/logo2.png" rel="shortcut icon">
<link href="public/css/bootstrap.min.css" rel="stylesheet">
<link href="public/iconfont/iconfont.css" rel="stylesheet">
<link href="public/css/style_coo.css" rel="stylesheet">
<script src="public/js/jquery-1.10.2.min.js"></script>
<script src="public/js/bootstrap.min.js"></script>
<script src="public/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="public/js/jquery.livequery.js"></script>	
<script src="public/js/inputvalid.js"></script>	
</head>
  
  <body>
 <div class="container-fluid bg-white height-md">
 	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">用户管理</a></li>
      <li class="active">用户编辑</li>
    </ol>
    <div class="row user-edit-box" style="padding-left:10px; padding-right:10px;">

       <div class="panel panel-default">
          <div class="panel-heading">用户控制</div>
          <div class="panel-body">
             <ul class="user-info">
             	<li class="col-md-3">
                	<span class="t">用户ID</span>
                    <span class="n"><s:property value="userBean.id"/></span>
                </li>
             	<li class="col-md-3">
                	<span class="t">用户账号</span>
                    <span class="n"><s:property value="userBean.account"/> </span>
                </li>
             	<li class="col-md-3">
                	<span class="t">账号状态</span>
                    <span class="n tStatus"><s:if test='userBean.status=="1"'>正常</s:if><s:else><font color='#ff0000'>禁用</font></s:else></span>
                </li>
             	<li class="col-md-3">
             		<s:if test='userBean.status=="1"'>
                    	<a class="btn btn-warning btn-sm" href="javascript:$.unable('${userBean.id}')" >禁用</a>
                    </s:if>
                    <s:else>
                    	<a class="btn btn-success btn-sm" href="javascript:$.enabled('${userBean.id}')" >启用</a>
                    </s:else>
                    <a class="btn btn-danger btn-sm" href="javascript:$.deleteUser('${userBean.id }')" >删除</a>
                </li>
             </ul>
          </div>
        </div>
		
         <nav class="navbar navbar-default">
          <div class="container-fluid">
            
        
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
              <ul class="nav navbar-nav">
                <li class="active"><a href="userAction_selectUserBean?paramMap['id']=${userBean.id }">基本信息</a></li>
                <li><a href="userAction_familyInfo?paramMap['id']=${userBean.id }">家庭信息</a></li>
                <li><a href="userAction_showAlbum?paramMap['id']=${userBean.id }">全部相册[${userBean.photoTag }]</a></li>
                <li class="line"></li>
              	<li><a href="userAction_showAlbum?paramMap['option']=0&&paramMap['id']=${userBean.id }"><i class="iconfont">&#x3446;</i>&nbsp;<span class="commonNum">[${userBean.commonNum }]</span></a></li>
              	<li><a href="userAction_showAlbum?paramMap['option']=1&&paramMap['id']=${userBean.id }"><i class="iconfont">&#xe6cb;</i>&nbsp;<span class="timeNum">[${userBean.timeNum }]</span></a></li>
                <li><a href="userAction_showAlbum?paramMap['option']=2&&paramMap['id']=${userBean.id }"><i class="iconfont">&#xe738;</i>&nbsp;<span class="themeNum">[${userBean.themeNum }]</span></a></li>
                <li class="line"></li>
                <li class="audio"><a href="userAction_getAlbumMedia?paramMap['option']=2&&paramMap['id']=${userBean.id }">音频&nbsp;<%--[<s:if test='null!=userBean.audioCount'>${userBean.audioCount }</s:if><s:else>0</s:else>]--%></a></li>
                <li class="video"><a href="userAction_getAlbumMedia?paramMap['option']=1&&paramMap['id']=${userBean.id }">视频&nbsp;<%--[<s:if test='null!=userBean.videoCount'>${userBean.videoCount }</s:if><s:else>0</s:else>]--%></a></li>
                <li class="picture"><a href="userAction_getAlbumMedia?paramMap['option']=0&&paramMap['id']=${userBean.id }">图片&nbsp;<%--[<s:if test='null!=userBean.photoCount'>${userBean.photoCount }</s:if><s:else>0</s:else>]--%></a></li>
                <li class="line"></li>
                <li><a href="userAction_showLockAlbum?paramMap['id']=${userBean.id }">锁定的相册</a></li>
                <li><a href="userAction_getAlbumMedia?paramMap['option']=3&&paramMap['id']=${userBean.id }">锁定的图片</a></li>
              </ul>
            </div><!-- /.navbar-collapse -->
          </div><!-- /.container-fluid -->
        </nav>
        
        <div class="user-content">
        	<div class="user-base-info">
            <form id="userForm" action="javascript:void(0);" method="post" style=" width:300px; margin-left:30px;">
            	<s:token></s:token>
            	<s:hidden value="%{userBean.id}" name="userBean.id"></s:hidden>
              <div class="form-group">
                <label for="phone">手机号码</label>
                <input type="tel" name="userBean.account" class="form-control" id="phone" placeholder="mobile phone" value='<s:property value="userBean.account"/>' readonly>
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">安全邮箱</label>
                <input type="tel" name="userBean.email" class="form-control" data-valid="[{rule:'email'}]" id="exampleInputEmail1" readonly placeholder="Enter email" value='<s:property value="userBean.email"/>'>
              </div>
              <div class="form-group">
                <label for="exampleInputPassword1">密码</label>
                <input type="text" name="userBean.password" class="form-control" id="exampleInputPassword1" placeholder="Password" maxlength="6" value='<s:property value="userBean.password"/>' readonly>
              </div>
              <div class="form-group">
                <label for="nickname">昵称</label>
                <input type="text" name="userBean.nickname" class="form-control" id="nickname" placeholder="nickname" value='<s:property value="userBean.nickname"/>' maxlength="10" readonly>
              </div>
              <button type="button" class="btn btn-primary btn-save hide">保存</button>
            </form>
            </div>            
        </div> 
  </div>
</div>

<script>


var W = window.top,formObj = null;
var valid_message_options = {email:'您输入的email地址不合法！',not_empty:'输入不能为空！'};
$(function(){
	formObj = $("#userForm").inputValid(valid_message_options);
	//保存编辑的信息
	$(".btn-save").livequery(function(){
		$(this).click(function(){
			if(formObj.validate_all()){
				$.editUser();
			}
		});
	});
});


$.extend({
	deleteUser : function(_id){
		W.$.confirm(
			"是否要删除用户:"+_id+"？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"paramMap['id']" : _id
					},
					url:'json/user_delUserBean', //API
					success:function(json) {
						//删除成功回调
						if(json.result === "00000000"){
							W.$.alert("删除成功！");
							window.location.href = "userAction_userList";							
						}
					},
					error: function(data) {
						W.$.alert("删除失败！");
					}
				});
			},
			function(){
				//alert("取消按钮");
			}
		);
	},
	
	editUser : function(){
		$.ajax({
			type : "POST",
			dateType : "json",
			data : $("#userForm").serialize(),
			url : "json/user_editUserBean",
			success : function(json){
				if(json.result == '00000000'){
					W.$.alert("保存成功！");
					//window.location.href = "userAction_userList";
				}else{
					W.$.alert("保存失败！");
				}
			},
			
			error : function(){
				W.$.alert("保存失败");
			}
		});
			
		
	},
	
	//启用
	enabled  : function(_id){
		W.$.confirm(
			"是否要启用用户:"+_id+"？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"paramMap['id']" : _id
					},
					url:'json/user_startOrForbid', //API
					success:function(json) {
						//启用成功回调
						if(json.result === "00000000"){
							W.$.alert("启用成功！");
							$(".tStatus").html("正常");
							$(".user-info li .btn").eq(0).removeClass("btn-success").addClass("btn-warning").text("禁用").attr("href" , "javascript:$.unable('"+_id+"')");
						}else{
							W.$.alert("启用失败！");
						}
					},
					error: function(data) {
						W.$.alert("启用失败！");
					}
				});

				
			},
			function(){
				//alert("取消按钮");
			}
		);
	},
	//禁用
	unable  : function(_id){
		W.$.confirmInput(
			"请输入禁用的原因：",
			function(inputVal){
				//此处加入删除的AJAX
				var cause = inputVal || "";
				if(cause === ""){
					W.$.alert("原因不能为空！");
					return;
				}
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"paramMap['id']" : _id,
						"paramMa['cause']" : cause //禁用的原因
					},
					url:'json/user_startOrForbid', //API
					success:function(json) {
						//启用成功回调
						if(json.result === "00000000"){
							W.$.alert("禁用成功！");
							$(".tStatus").html("<span style='color:red'>禁用</span>");
							$(".user-info li .btn").eq(0).removeClass("btn-warning").addClass("btn-success").text("启用").attr("href" , "javascript:$.enabled('"+_id+"')");
						}
					},
					error: function(data) {
						W.$.alert("禁用失败！");
					}
				});
			},
			function(){
				//alert("取消按钮");
			}
		);
	}
});
</script>
  </body>
</html>
