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
    
    <title>回收站</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="public/img/logo2.png" rel="shortcut icon">
	<link href="public/css/bootstrap.min.css" rel="stylesheet">
	<link href="public/css/style_coo.css" rel="stylesheet">
		
	<script type="text/javascript" src="public/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="public/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="public/js/slimscroll/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="public/js/jquery.livequery.js"></script>
	
	

  </head>
  
  <body>
  <div class="container-fluid bg-white height-md">

    <ol class="breadcrumb">
      	<li><a href="javascript:void(0)">首页</a></li>
      	<li><a href="javascript:void(0)">用户管理</a></li>
      	<li class="active">回收站</li>
    </ol>	
    <div class="userListbox">
    	<form action="userAction_recycleList" method="post" name="keyWordsForm">
    		<s:token></s:token>
    		<div class="form-group pull-left" style="width:250px;">
                <div class="input-group">
                  <div class="input-group-addon">关键字</div>
                  <input type="text" class="form-control keyWords" name="keyWords" value="${keyWords }" onfocus="this.value=''" placeholder="请输入手机号码/呢称" maxlength="11">
                </div>
              </div>
              
              <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;搜索</button>
              <button onClick="javascript:location.href='userAction_userList'" type="button" class="btn btn-danger  pull-right">返回</button>
    		
    	</form>
    	
    	<table class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
    		<thead>
    			<tr>
    				<td>NO.</td>
    				<td>手机号</td>
    				<td>email</td>
    				<td>呢称</td>
    				<td>活跃家庭号</td>
    				<!-- 
    				<td>相册数</td>
    				<td>图片数</td>
    				<td>视频数</td>
    				-->
    				<td>操作</td>
    				
    			</tr>
    		</thead>
    		<tbody>
    			<s:iterator value="paginationBean.records" status="st">
    			<tr id='<s:property value="id"/>'>
    				<td><span class="badge" style="background-color: #0099FF;">${(page-1)*pageSize+st.count }</span></td>
    				<td><s:property value="account"/></td>
    				<td><s:property value="email"/></td>
    				<td><s:property value="nickname"/></td>
    				<td><s:property value="active_club_id"/> </td>
    				<!--  
    				<td><s:property value="photoTag"/></td>
    				<td><s:property value="photoNum"/></td>
    				<td><s:property value="videoNum"/></td>
    				-->
    				 <td>
                    	<a class="btn btn-success btn-sm" href="javascript:$.restore('${id }')">还原</a>
    					<a class="btn btn-danger btn-sm" href="javascript:$.thoroughDelete('${id }')" >彻底删除</a>
    				</td>

    				 
    			</tr>
    			</s:iterator>
    		</tbody>
    			
    	</table>
    	 <nav class="pages_number">${pageLinks }</nav>   	  
   	</div>
  </div>
  
 <script>

var W = window.top;

$(function(){
	
	//绑定跳转页面
	$(".pageSkip").livequery(function(){
		$(this).bind("click",function(){
			$.skip();
		});
		
	});
});


$.extend({
	thoroughDelete : function(_id){
		W.$.confirmInput(
			"请您输入管理员密码？",
			function(pwd){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"paramMap['id']":_id,
						"paramMap['password']":pwd
					},
					url:'json/user_delThorough', //API
					success:function(json) {
						//删除成功回调
						if(json.result == "00000000"){
							$(".totalCount").html(parseInt($(".totalCount").html())-1);
							$("tr#" + _id).remove(); //删除行
							W.$.alert("删除成功！");
						}else if(json.result == '00000002'){
							W.$.alert("密码错误！");
						}else W.$.alert("删除失败！");
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
	//还原
	restore  : function(_id){
		W.$.confirm(
			"是否要还原用户:"+_id+"？",
			function(){
				//此处加入还原的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"paramMap['id']" : _id
					},
					url:'json/user_restore', //API
					success:function(json) {
						//启用成功回调
						if(json.result == "00000000"){
							$("tr#" + _id).remove(); //删除行
							W.$.alert("还原成功！");
							$(".totalCount").html($(".totalCount").html()-1);
						}else {
							W.$.alert("还原失败！");
						}
					},
					error: function(data) {
						W.$.alert("还原失败！");
					}
				});

				
			},
			function(){
				//alert("取消按钮");
			}
		);
	},
	
	//跳转至某页
	skip:function(){
		var page = $(".skip").val();
		var totalPage = parseInt($(".totalPage").html());
		var regex = new RegExp("^-?\\d+$");
		if(regex.test(page) && parseInt(page)<= parseInt(totalPage) && parseInt(page)>= 1){
			window.location.href="userAction_recycleList?keyWords="+$(".keyWords").val()+"&page="+page;
		}else{
			W.$.alert("页数为数字,且不超过"+totalPage);
		}
	}
});
</script>
 
</body>
</html>
