
<%@ page import="cn.broadin.oms.util.PaginationBean"%>
<%@ page import="java.lang.Math" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>美家秀后台管理平台</title>
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
      	<li class="active">用户列表</li>
    </ol>	
    <div class="userListbox">
    	<form action="userAction_userList" method="post" name="keyWordsForm">
    		<s:token></s:token>
    		 <div class="form-group pull-left" style="width:200px;">
                <div class="input-group">
                  <div class="input-group-addon">筛选</div>
    				<select class="form-control option" name="paramMap['status']" onchange="document.keyWordsForm.submit()">
    				<option value="2" <s:if test='option=="2"'>selected</s:if>>所有用户</option>
    				<option value="1" <s:if test='option=="1"'>selected</s:if>>正常用户</option>
    				<option value="0" <s:if test='option=="0"'>selected</s:if>>禁用用户</option>
    				</select>
    			</div>
    		</div>
    		<div class="form-group pull-left" style="width:250px;">
                <div class="input-group">
                  <div class="input-group-addon">关键字</div>
                  <input type="text" class="form-control keyWords" name="keyWords" value="${keyWords }" onfocus="this.value=''" maxlength="11" placeholder="请输入手机号/呢称">
                </div>
              </div>            
              <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;搜索</button>
              
              <div class="btn-group pull-right">
              		<!--  <a class="btn btn-info" href="userAction_userList?paramMap['orderColumn']=monthActivityNum"><span class="glyphicon glyphicon-arrow-down"></span>月活跃排序</a>-->
              		<a href="userAction_recycleList" class="btn btn-default"><span class="glyphicon glyphicon-trash"></span>&nbsp;回收站</a>
    		  </div>
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
    				<td>状态</td>
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
    				<td class="tStatus"><s:if test='status=="1"'>正常</s:if><s:else><font color="#ff0000">禁用</font></s:else> </td>
    				 <td>
    					<a class="btn btn-info btn-sm" href="userAction_selectUserBean?paramMap['id']=${id }" >编辑</a>
    					<s:if test='status=="1"'>
    						<a class="btn btn-warning btn-sm" href="javascript:$.unable('${id }')" >禁用</a>
    					</s:if>
    					<s:else>
    						<a class="btn btn-success btn-sm" href="javascript:$.enabled('${id }')" >启用</a>
    					</s:else>
    					<a class="btn btn-danger btn-sm" href="javascript:$.deleteUser('${id }')" >删除</a>
    				</td>
    				 
    			</tr>
    			</s:iterator>
    		</tbody>
    			
    	</table>
    	 <nav class="pages_number">${pageLinks } </nav>   	  
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
	deleteUser : function(_id){
		W.$.confirm(
			"是否要删除用户:"+_id+"？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"paramMap['id']":_id
					},
					url:'json/user_delUserBean', //API
					success:function(json) {
						//删除成功回调
						
						if(json.result === "00000000" ){
							W.$.alert("删除成功！");
							
							$(".totalCount").html(parseInt($(".totalCount").html())-1);
							$("tr#" + _id).remove(); //删除行
						}else{
							W.$.alert("删除失败！");
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
						"paramMap['id']":_id
					},
					url:'json/user_startOrForbid', //API
					success:function(json) {
						//启用成功回调
						
						if(json.result === "00000000"){
							
							$("tr#" + _id + " .tStatus").html("正常");
							$("tr#" + _id + " .btn-success").removeClass("btn-success").addClass("btn-warning").text("禁用").attr("href" , "javascript:$.unable('"+_id+"')");
							W.$.alert("启用成功！");
						}else {
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
						"paramMap['id']":_id,
						"paramMap['cause']" : cause //禁用的原因
					},
					url:'json/user_startOrForbid', //API
					success:function(json) {
						//启用成功回调
						if(json.result === "00000000"){
							
							$("tr#" + _id + " .tStatus").html('<font color="#ff0000">禁用</font>');
							$("tr#" + _id + " .btn-warning").removeClass("btn-warning").addClass("btn-success").text("启用").attr("href" , "javascript:$.enabled('"+_id+"')");
							W.$.alert("禁用成功！");
						}else{
							W.$.alert("禁用失败！");
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
	},
	//跳转至某页
	skip:function(){
		var page = $(".skip").val();
		var totalPage = parseInt($(".totalPage").html());
		var regex = new RegExp("^-?\\d+$");
		if(regex.test(page) && parseInt(page)<= parseInt(totalPage) && parseInt(page)>= 1){
			window.location.href="userAction_userList?paramMap['status']="+$(".option").val()+"&&keyWords="+$(".keyWords").val()+"&&page="+page;
		}else{
			W.$.alert("页数为数字,且不超过"+totalPage);
		}
	}
});
</script>
</body>
</html>
