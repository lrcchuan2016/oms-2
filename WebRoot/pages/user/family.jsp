<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script src="public/js/comm.js"></script>
		
  </head>
  
  <body>
 <div class="container-fluid">
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
                    <span class="n userID"><s:property value="userBean.id"/></span>
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
                <li><a href="userAction_selectUserBean?paramMap['id']=${userBean.id }">基本信息</a></li>
                <li class="active"><a href="userAction_familyInfo?paramMap['id']=${userBean.id }">家庭信息</a></li>
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
        
        <div class="user-family clearfix">
        	<s:if test="null!=cbList">
        	<h4>我创建的家庭</h4>
        	<ul id="myFamily" class="fam-list clearfix">
        		<s:iterator value="cbList">
            	<li id='<s:property value="id"/>'>
                	<a href="javascript:void(0);">
                        <img class="decodeImg" src='<s:property value="portrait"/>'>
                    </a>
                    <p><s:property value="nickname"/></p>
                    <span class="hide"><s:property value="id"/></span>
                    <div class="btn-group pull-right">
                      <button type="button" class="btn btn-sm btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu" role="menu" style="min-width:80px;">
                        <li><a class="view" href="javascript:void(0)">查看</a></li>
                        <li><a class="showFamilyAlbum" href="userPages_familyAlbum?param=${id }&uid=${userBean.id }">显示相册</a></li>
                      </ul>
                    </div>
                </li>
                </s:iterator>
            </ul>
           </s:if>
          
            <s:if test="null!=ucSet">
            <h4>我加入的家庭</h4>
        	<ul id="inFamily" class="fam-list clearfix">
        	<s:iterator value="ucSet">
        		<s:if test='club_user_status=="1"'>
            	<li id='<s:property value="club.id"/>'>
                	<a href="javascript:void(0);">
                        <img class="decodeImg" src="${club.portrait }"/>
                    </a>
                     <p class="decodeP">${club.nickname }</p>
                     <span class="decodeSpan clubId hide">${club.id }</span>
                    
                </li>
               </s:if>
            </s:iterator>
            </ul>
            </s:if>
        </div>
  </div>
</div>

<div id="family-info" class="modal" tabindex="-1" style="display:none; background-color:rgba(0,0,0,0.4); overflow:hidden;">
    <div class="modal-dialog" style="width:700px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4>家庭信息</h4>
            </div>
            <div class="modal-body" style="padding:0px;">
                <table class="table table-bordered" style="margin:0px;">
                	<tr>
                    	<td style="border-top:none; width:120px;">头像</td>
                    	<td style="border-top:none;"><img src="" width="60" class="imgPortrait decodeImg"></td>
                    </tr>
                	<tr>
                    	<td>家庭昵称</td>
                    	<td class="clubNickname decodeTd"></td>
                    </tr>
                	<tr>
                    	<td>创建者</td>
                    	<td class="creater decodeTd"></td>
                    </tr>
                	<tr>
                    	<td>家庭成员</td>
                    	<td>
                        	<ul class="member-list clearfix" style="max-height:300px; overflow-x:auto;"></ul>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer text-center-xs" style="margin-top:0px;">
                <button type="button" class="btn btn-default r-lg btn-sm" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span> 关　闭
                </button>
            </div>
        </div>
    </div>
</div>

<script>

var userID = '${userBean.id}'; //通过传参获得
var W = window.top;
$(function(){
	

	$("#inFamily > li").livequery(function(){
		var clubId = $(this).find(".clubId").html();
		var menuHtml = '<div class="btn-group pull-right">'
		  +'<button type="button" class="btn btn-sm btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><span class="caret"></span>'
		  +'</button>'
		  +'<ul class="dropdown-menu" role="menu" style="min-width:80px;">'
			+'<li><a class="view" href="javascript:void(0)">查看</a></li>'
			+'<li><a class="showFamilyAlbum" href="userPages_familyAlbum?param='+clubId+'&uid='+userID+'">显示相册</a></li>'
			+'<li><a class="quit" href="javascript:void(0)">退出</a></li>'
		  +'</ul>'
		+'</div>';
		//alert($(this).attr("id"));
		$(this).append(menuHtml);
	});
	
	$(".fam-list li a.view").livequery(function(){
		$(this).click(function(){
			var id = $(this).parents("ul").parents("li").attr("id");
			//显示家庭
			$.getFamilyInfo(id);
			$("#family-info").show();
		});
	});
	
	$("#inFamily li a.quit").livequery(function(){
		$(this).click(function(){
			var id = $(this).parents("ul").parents("li").attr("id");
			//退出家庭
			$.quitFamily(id);
		});
	});
	
	$("#myFamily li a.quit").livequery(function(){
		$(this).click(function(){
			var id = $(this).parents("ul").parents("li").attr("id");
			//解散家庭
			alert(id);
		});
	});
	
	$("#family-info .modal-footer .btn").livequery(function(){
		$(this).click(function(){
			$("#family-info").hide();
		});
	});
	
	//图像反转码
	$(".decodeImg").livequery(function(){
		$(this).each(function(){
			$(this).attr("src",decodeURIComponent($(this).attr("src")));
		});
	});
	
	//文字反转码
	$("[class^='decode']").livequery(function(){
		$(this).each(function(){
			$(this).html(decodeURIComponent($(this).html()));
		});
	});
	
});


$.extend({
	quitFamily : function(_id){
		W.$.confirm(
			"是否要退出该家庭？",
			function(){
				//此处加入删除的AJAX
				userID = $(".userID").html();
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"paramMap['id']" : userID,
						"paramMap['familyId']" : _id
					},
					url:'json/user_quitFamily', //API
					success:function(json) {
						//删除成功回调
						if(json.result == "00000000"){
							$("#" + _id).remove();
							W.$.alert("退出成功！");
						}else{
							W.$.alert("退出失败！");
						}
					},
					error: function(data) {
						W.$.alert("退出失败！");
					}
				});
			},
			function(){
				//alert("取消按钮");
			}
		);
	},
	//查询家庭信息
	getFamilyInfo  : function(_id){
		$.ajax({
			type:"POST",
			dataType:"json",
			data:{
				"paramMap['familyId']" : _id
			},
			url:'json/user_getFamilyInfo', //API
			success:function(json) {
				try{
					if(json.result == "00000000"){
						var club = json.club;
						var member = json.member;
						$(".imgPortrait").attr("src",decodeURIComponent(club.portrait)).addClass("decodeImg");
						$(".clubNickname").html(club.nickname);
						$(".creater").html(json.creater);
						$(".member-list").html("");
						$.each(member,function(key,value){
							$(".member-list").append("<li style='height:100px;'><img class='decodeImg' src=\""+value.portrait+"\"><span class='name decodeName'>"+decodeURIComponent(value.nickname)+"</span><span class=\"account\">"+value.account+"</span></li>");
						});
					}else{
						W.$.alert("查看失败！");
					}
				}catch(e){
					
				}
			},
			error: function(data) {
				W.$.alert("查看失败！");
			}
		});
	},
	
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
						"paramMap['cause']" : cause //禁用的原因
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
