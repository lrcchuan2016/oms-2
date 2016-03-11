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
  
  <body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
 <div class="container-fluid">
 	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">用户管理</a></li>
      <li><a href="javascript:void(0)">用户编辑</a></li>
      <li><a href="userAction_familyInfo?paramMap['id']=${param.uid }">家庭信息</a></li>
    </ol>
    <div class="row user-edit-box" style="padding-left:10px; padding-right:10px;">
        	<ol class="breadcrumb">
              <li class="family"><a href="javascript:history.back()"></a></li>
              <li class="albumName"><a href="javascript:void(0)"></a></li>
              <li class="active">相片列表<li>
            </ol>
         <div class="user-content">
         	
         </div>
       
  </div>
</div>

<script>

var W = window.top;
var albumId = '${param.albumId}';
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
	
	$(".utc").livequery(function(){
		$(this).each(function(){
			var t = $(this).html();
			$(this).html(formatUTC(t,"yyyy-MM-dd hh:mm:ss"));
		});
	});
	
	//li元素匹配高宽
	$("audio").livequery(function(){
		$(this).each(function(){
			$(this).parents("li").css({"height": "0px","width":"320px"});
		});
		
	});
	
	//初始化数据
	initPicData();
	
});

function initPicData(){
	$.ajax({
		type:'post',
		dataType:'json',
		data:{"paramMap['albumId']":albumId},
		url:'json/user_showFamilyAlbumMedia',
		success:function(json){
			if(json.result == '00000000'){
				console.info(json.albumMedias,json.mediaMap);
				fillTimerList(json.albumMedias,json.mediaMap);
				$(".breadcrumb > .family > a").html(json.club.nickname);
				$(".breadcrumb > .albumName > a").html(json.albumName);
			}else W.$.alert("获取数据失败！");
		},
		error:function(e){
			W.$.alert("获取数据异常！");
		}
	});
}

function fillTimerList(albumMedias,mediaMap){
	///if(medias.size()>0){
		var list = $(".user-content");
		$.each(albumMedias,function(i,albumMedia){
			var html = '';
			html += '<div class="user-album-list clearfix albumID" id="'+albumMedia.id+'">';
            html += '<ul class="clearfix list-inline">';
            html += '<div><h4><p class="utc font-weight">'+albumMedia.utc+'</p></h4>';
            html += '<span><b>标题：</b>'+albumMedia.title+'</span>';
            html += '<p class="text-overflow"><i>描述：</i>'+albumMedia.detail+'</p></div>';
            var medias = mediaMap[albumMedia.id];
            $.each(medias,function(i,media){
            	if(media.type == 0 ){
            		html += '<li id=""><a href="javascript:void(0)"><img src="'+media.content+'@1e_160w_160h_1c_0i_1o_100Q_1x.jpg"></a></li>';
            	}else if(media.type == 1){
            		html += '<li id=""><a href="javascript:void(0)"><video width="160" height="160" src="'+media.content+'"></video></a></li>';
            	}else{
            		html += '<li id=""><a href="javascript:void(0)"><audio width="160" src="'+media.content+'"></audio></a></li>';
            	}
            });
            //html += '<li id=""><a href="javascript:void(0)"></a></li>';
            html += '</ul><hr></div>';
	        list.append(html);              //放入ul
		});
	//}
}

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
						mediaID : _id
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
						mediaID : _id
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
	
	//删除用户
	deleteUser : function(_id){
		W.$.confirm(
			"是否要删除用户:"+_id+"？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						uid : _id
					},
					url:'json/user_delUserBean', //API
					success:function(json) {
						//删除成功回调
						if(json.result == "00000000"){
							W.$.alert("删除成功！");
							$("tr#" + _id).remove(); //删除行
							window.location.href = "userAction_userList";
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
						uid : _id
					},
					url:'json/user_startOrForbid', //API
					success:function(json) {
						//启用成功回调
						if(json.result == "00000000"){
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
						uid : _id,
						cause : cause //禁用的原因
					},
					url:'json/user_startOrForbid', //API
					success:function(json) {
						//启用成功回调
						if(json.result == "00000000"){
							W.$.alert("禁用成功！");
							$(".tStatus").html("<span style='color:red'>禁用</span>");
							$(".user-info li .btn").eq(0).removeClass("btn-warning").addClass("btn-success").text("启用").attr("href" , "javascript:$.enabled('"+_id+"')");
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
	}
});
</script>
  </body>
</html>
