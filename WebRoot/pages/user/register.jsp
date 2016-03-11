<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
</head>
  <body>
  	<div class="container-fluid height-md">
 		<ol class="breadcrumb">
      		<li><a href="javascript:void(0)">首页</a></li>
      		<li><a href="javascript:void(0)">用户管理</a></li>
      		<li class="active">新建用户</li>
    	</ol>
    	<div class="userListbox channel-list" style="margin-right:20px;">
			<div class="panel panel-default clearfix">
			<div class="panel-heading">
				新建用户&nbsp;&nbsp;<span class="badge"><span class="glyphicon glyphicon-star"></span>号开头的为必填项. &nbsp;&nbsp;&nbsp;</span>
			</div>
			<div class="panel-body">
				<form id="registerForm" class="form-horizontal" action="javascript:void(null);">
					<div class="row col-xs-12">
						
						<div class="form-group has-feedback col-xs-8">
  							<div class="input-group">
    						<span class="input-group-addon">
    							<span class="glyphicon glyphicon-star"></span>手机号码
    						</span>
    						<input type="text" name="paramMap['account']" class="form-control account" data-valid="[{rule:'integer',message:'手机号码为11为数'},{rule:'min_length',value:11,message:'手机号码为11为数'},{rule:'not_empty',message:'手机号码为11为数'}]" aria-describedby="inputGroupSuccess1Status" placeholder="请输入11位数字" maxlength="11">
  							</div>
						</div>
						
						<div class="form-group has-feedback col-xs-8">
  							<div class="input-group">
    						<span class="input-group-addon">
    							<span class="glyphicon glyphicon-star"></span>用户呢称
    						</span>
    						<input type="text" name="paramMap['nickname']" class="form-control nickname" data-valid="[{rule:'not_empty'}]" aria-describedby="inputGroupSuccess1Status" placeholder="呢称不为空">
  							</div>
						</div>
						
						<div class="form-group has-feedback col-xs-8">
    						<div class="input-group">
    						<span class="input-group-addon">
    							<span class="glyphicon glyphicon-star"></span>用户密码
    						</span>
    						<input type="password" name="paramMap['password']" class="form-control password" data-valid="[{rule:'not_empty'}]" aria-describedby="inputGroupSuccess1Status" placeholder="密码长度小于6" maxlength="6">
  							</div>
						</div>
						
						<div class="form-group has-feedback col-xs-8">
    						<div class="input-group">
    						<span class="input-group-addon">
    							<span class="glyphicon glyphicon-star"></span>重复密码
    						</span>
    						<input type="password" class="form-control password2" data-valid="[{rule:'equals',message:'两次输入不一致'}]" aria-describedby="inputGroupSuccess1Status" placeholder="密码长度小于6" maxlength="6">
  							</div>
						</div>
						
						<div class="form-group has-feedback col-xs-8">
    						<div class="input-group">
    						<span class="input-group-addon">
    							<span class="glyphicon glyphicon-star"></span>安全邮箱
    						</span>
    						<input type="email" name="paramMap['email']" class="form-control email" data-valid="[{rule:'email',message:'邮箱格式不正确'},{rule:'not_empty',message:'不能为空'}]" aria-describedby="inputGroupSuccess1Status" placeholder="邮箱不为空" maxlength="50">
  							</div>
						</div>
					</div>
				</form>
				
			</div>
			<div class="panel-footer pull-left col-xs-12">
				<div class="col-xs-1"></div>
				<div class="col-xs-11">
					<button type="button" class="btn btn-primary subBtn"> &nbsp;<span class="glyphicon glyphicon-ok"></span> 提    交 &nbsp; </button>
					<button type="button" class="btn btn-default backBtn"> &nbsp;<span class="glyphicon glyphicon-remove"></span> 返回列表&nbsp; </button>
				</div> 
			</div>
     	</div>
     	
     	<div class="userListbox channel-list showInfo" style="margin-right:20px;">
			<div class="panel panel-default clearfix">
			<div class="panel-body">
				<table class="table">
					<thead class="text-center"><tr><td colspan="2"><h5 class="center"></h5></td></tr></thead>
					<tbody class="tb"></tbody>
				</table>
			</div>
			<div class="panel-footer pull-left col-xs-12">
				<div class="col-xs-12"></div> 
			</div>
     	</div>
  </div>
</body>
<script>
var W = window.top;
var actionObj = null, formObj = null;
$(function(){
	formObj = $("#registerForm").inputValid(valid_message_options);
	$(".showInfo").hide();
	actionObj = new Actions({url:"json/user_register",successCallBack:function() {}});
	$(".subBtn").livequery(function(){
		$(this).click(function(){
			if(formObj.validate_all()){
				$.ajax({
					type:'POST',
					//data:{account:$(".account").val(),nickname:$(".nickname").val(),password:$(".password").val(),email:$(".email").val()},
					data:$("#registerForm").serialize(),
					dataType:'json',
					url:'json/user_register',
					success:function(json){
						$.init();
						$(".showInfo").show();
						if(json.result == '00000000'){
							var table = $(".tb");
							var user = json.user;
							$("h5").html("<font color='#00ff00'>用户创建成功</font>");
							if(null != user){
								table.html("<tr><td>手机号</td><td>"+user.account+"</td></tr>");
								table.append("<tr><td>用户呢称</td><td>"+user.nickname+"</td></tr>");
								table.append("<tr><td>用户密码</td><td>"+user.password+"</td></tr>");
								table.append("<tr><td>安全邮箱</td><td>"+user.email+"</td></tr>");
							}
							
						}else if(json.result == '00000002'){
							var table = $(".tb");
							var user = json.user;
							$("h5").html("<font color='#0000ff'>用户还原成功</font>");
							if(null != user){
								table.html("<tr><td>手机号</td><td>"+user.account+"</td></tr>");
								table.append("<tr><td>用户呢称</td><td>"+user.nickname+"</td></tr>");
								table.append("<tr><td>用户密码</td><td>"+user.password+"</td></tr>");
								table.append("<tr><td>安全邮箱</td><td>"+user.email+"</td></tr>");
							}
						}else if(json.result == '00000003'){
							var table = $(".tb");
							var user = json.user;
							$("h5").html("<font color='#ff0000'>用户还原失败</font>");
							if(null != user){
								table.html("<tr><td>手机号</td><td>"+user.account+"</td></tr>");
								table.append("<tr><td>用户呢称</td><td>"+user.nickname+"</td></tr>");
								table.append("<tr><td>用户密码</td><td>"+user.password+"</td></tr>");
								table.append("<tr><td>安全邮箱</td><td>"+user.email+"</td></tr>");
							}
						}else if(json.result == '00000004'){
							var table = $(".tb");
							var user = json.user;
							$("h5").html("<font color='#ff0000'>手机号已被注册</font>");
							if(null != user){
								table.html("<tr><td>手机号</td><td>"+user.account+"</td></tr>");
								table.append("<tr><td>用户呢称</td><td>"+user.nickname+"</td></tr>");
								table.append("<tr><td>用户密码</td><td>"+user.password+"</td></tr>");
								table.append("<tr><td>安全邮箱</td><td>"+user.email+"</td></tr>");
							}
						}else{
							$("h5").html("<font color='#ff0000'>用户创建失败</font>");
						}
						
					},
					
					error:function(){
						$("h5").html("<font color='#ff0000'>用户创建失败</font>");
					}
				});
				
			}
		});
	});
	
	//返回列表
	$(".backBtn").livequery(function(){
		$(this).click(function(){
			window.location.href = "userAction_userList";
		});
	});
	//重复密码
	$(".password").livequery(function(){
		$(this).blur(function(){
			$(".password2").attr("data-valid","[{rule:'equals',value:'"+$(this).val()+"',message:'两次输入不一致'}]");
		});
	});
	
	//隐藏
	$("input").livequery(function(){
		$(this).focus(function(){
			$(".showInfo").hide();
		});
	});

});

$.extend({
	//清除input标签的值
	init:function(){
		$("input").livequery(function(){
			$(this).each(function(){
				$(this).val("");
			});
		});
	},
});
</script>
</html>
