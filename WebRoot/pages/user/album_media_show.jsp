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
                <li><a href="userAction_selectUserBean?paramMap['id']=${userBean.id }">基本信息</a></li>
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
            <s:if test='null!=paginationBean.records'>
            <div class="user-album-list clearfix albumID" id='<s:property value="id"/>'>
                <ul class="clearfix">
                   <s:iterator value="paginationBean.records">
                   <s:if test='audit_status=="2"'><li class="locked" id='<%="album" %><s:property value="id"/>'></s:if>
                   <s:else><li id='<%="album" %><s:property value="id"/>'></s:else>
                    	<a href="javascript:void(0)">
                    		<s:if test='type==0'><img class="imgSrc" src='<s:property value="content"/>'></s:if>
                    		<s:if test='type==1'><video controls width="160" height="160" src='<s:property value="content"/>'></video></s:if>
                    		<s:if test='type==2'><audio controls  src='<s:property value="content"/>'></audio></s:if>
                    	</a>
                        <div class="info">
                            <!--  <span class="name"><s:property value="title"/></span>-->
                        </div>
                    </li>
                    </s:iterator>
                </ul>
                <!------分页------>
                <nav class="pages_number">
                 ${pageLinks }
                </nav><!------分页-end----->
            </div>
            </s:if>
        </div>
        
  </div>
</div>

<script>

var W = window.top;
var option = "${paramMap['option']}";
$(function(){

	//初始化 相册列表
	$(".user-album-list > ul > li").livequery(function(){
		$(this).hover(function(){
			var lockFlag = $(this).hasClass("locked");
			var src = $(this).find("img").attr("src").split("@")[0];
			var html = '<div class="dropdown pull-right">'
		+'<button class="btn btn-default btn-sm dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true"><span class="caret"></span>'
		+'</button>'
		+'<ul class="dropdown-menu album_xiala" role="menu" aria-labelledby="dropdownMenu1" style="min-width:80px;">'
			+'<li role="presentation" class="'+(lockFlag?"unlock":"lock")+'"><a role="menuitem" tabindex="-1" href="javascript:void(0)">'+(lockFlag?"解锁":"锁定")+'</a></li>'
			+'<li role="presentation" class=""><a role="menuitem" tabindex="-1" href="'+src+'" download="'+src+'">下载</a></li></ul>'
	+'</div>';
			$(this).append(html);
		},function(){
			$(this).children(".dropdown").remove();
		});
	});
	
	//初始化已经被 锁定的相册
	$(".user-album-list > ul > li.locked").livequery(function(){
		$(this).children("a").append('<div class="lockCover"><i class="iconfont">&#xe67b;</i></div>');
	});
	
	//初始化解锁
	$(".album_xiala li.unlock a").livequery(function(){
		$(this).click(function(){
			var ablumID = $(this).parents("li").parents("li").attr("id").replace(/album/,"");
			$.unlockMedia(ablumID);
		});
	});
	
	//初始化锁定
	$(".album_xiala li.lock a").livequery(function(){
		$(this).click(function(){
			var ablumID = $(this).parents("li").parents("li").attr("id").replace(/album/,"");
			$.lockMedia(ablumID);
		});
	});
	
	//初始化相片高宽等参数
	$(".imgSrc").livequery(function(){
		$(this).each(function(){
			var src = $(this).attr("src");
			if(src.trim() != ""){
				//已处理过的图片截取重新处理
				if(src.indexOf("@")==-1){
					$(this).attr("src",src.trim()+"@1e_160w_160h_1c_0i_1o_100Q_1x.jpg");
				}else{
					$(this).attr("src",src.trim().split("@")[0]+"@1e_160w_160h_1c_0i_1o_100Q_1x.jpg");
				}
			}
		});
	});
	
	if(option == '1'){$(".video").addClass("active");}else if(option == '0'){$(".picture").addClass("active");}else{$(".audio").addClass("active");};

	//绑定跳转页面
	$(".pageSkip").livequery(function(){
		$(this).bind("click",function(){
			$.skip();
		});
		
	});
});


$.extend({
	//解锁图片
	unlockMedia : function(_id){
		W.$.confirm(
			"是否解锁此资源？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"paramMap['mediaId']" : _id
					},
					url:'json/user_unlockOrLockMedia', //API
					success:function(json) {
						//锁定成功回调
						if(json.result == "00000000"){
							W.$.alert("解锁成功！");
							$("#album" + _id).removeClass("locked").find(".lockCover").remove();
							$("#album" + _id).find(".unlock").removeClass("unlock").addClass("lock").children("a").text("锁定");
						}else{
							W.$.alert("解锁失败！");
						}
					},
					error: function(data) {
						W.$.alert("解锁失败！");
					}
				});
			},
			function(){
				//alert("取消按钮");
			}
		);
	},
	//锁定资源
	lockMedia : function(_id){
		W.$.confirm(
			"是否锁定此资源？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"paramMap['mediaId']" : _id
					},
					url:'json/user_unlockOrLockMedia', //API
					success:function(json) {
						//锁定成功回调
						if(json.result == "00000000"){
							W.$.alert("锁定成功！");
							$("#album" + _id).addClass("locked");
							$("#album" + _id).find(".lock").removeClass("lock").addClass("unlock").children("a").text("解锁");
						}else{
							W.$.alert("锁定失败！");
						}
					},
					error: function(data) {
						W.$.alert("锁定失败！");
					}
				});
			},
			function(){
				//alert("取消按钮");
			}
		);
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
	},
	
	//跳转至某页
	skip:function(){
		var page = $(".skip").val();
		var totalPage = parseInt($(".totalPage").html());
		var regex = new RegExp("^-?\\d+$");
		//var option = "${paramMap['option']}";
		var id = "${paramMap['id']}";
		if(regex.test(page) && parseInt(page)<= parseInt(totalPage) && parseInt(page)>= 1){
			window.location.href="userAction_getAlbumMedia?paramMap['option']="+option+"&paramMap['id']="+id+"&page="+page;
		}else{
			W.$.alert("页数为数字,且不超过"+totalPage);
		}
	}
});
</script>
  </body>
</html>
