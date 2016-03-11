
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
<style type="text/css">
progress {
    border-radius: 2px;
    border-left: 1px #ccc solid;
    border-right: 1px #ccc solid;
    border-top: 1px #aaa solid;
    background-color: #eee;
}
  
progress::-webkit-progress-bar {
    background-color: #d7d7d7;
}
  
progress::-webkit-progress-value {
    background-color: #aadd6a;
}
</style>		
  <head>
  
  <body>
 <div class="container-fluid">
 	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">用户管理</a></li>
      <li><a href="javascript:void(0)">用户编辑</a></li>
      <li class=""><a href="userAction_familyInfo?paramMap['id']=${param.uid }">家庭信息</a></li>
    </ol>
    <div class="row user-edit-box" style="padding-left:10px; padding-right:10px;">
			<ol class="breadcrumb">
              <li class="family"><a href="javascript:void(0)"></a></li>
              <li class="active">相册列表</li>
            </ol>
        <div class="user-content">
            <div class="user-album-list clearfix">
                <ul class="clearfix"></ul>  <!-- 相册显示 -->
            </div>
        </div>
  </div>
</div>

<script>

var W = window.top;
var familyId = '${param.param}';
var userID = '${param.uid}';
var arr = new Array();
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
			+'<li role="presentation" class=""><a role="menuitem" tabindex="-1" href="javascript:void(0);" class="download">下载</a></li>'
			//+'<li role="presentation" class="del"><a role="menuitem" tabindex="-1" href="javascript:void(0)">删除</a></li>'
		+'</ul>'
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
			$.unlockAlbum(ablumID);
		});
	});
	
	//初始化锁定
	$(".album_xiala li.lock a").livequery(function(){
		$(this).click(function(){
			var ablumID = $(this).parents("li").parents("li").attr("id").replace(/album/,"");
			$.lockAlbum(ablumID);
		});
	});
	
	//初始化解锁
	$(".album_xiala li.del a").livequery(function(){
		$(this).click(function(){
			var ablumID = $(this).parents("li").parents("li").attr("id").replace(/album/,"");
			$.deleteAlbum(ablumID);
		});
	})
	
	//初始化相册数据
	initAlbumList();
	
	
	
	//下载
	$(".download").livequery(function(){
		$(this).each(function(){
			$(this).click(function(){
				var albumId = $(this).parents("li").parents("li").attr("id").replace(/album/,"");
				//初始化进度条
				initProgress();
				var progress = $(this).parents("li").parents("li").find("progress");
				progress.removeClass("hide");
				$.downloadAjax(albumId,progress);
			});
		});
	});
	
	
});

function initAlbumList(){
	$.ajax({
		type:'post',
		dataType:'json',
		data:{"paramMap['familyId']":familyId},
		url:'json/user_showFamilyAlbum',
		success:function(json){
			if(json.result == '00000000'){
				fillUl(json.albums);
				//console.info(json.club);
				fillBreadcrumb(json.club);
			}else W.$.alert("获取相册失败！");
		},
		error:function(e){
			W.$.alert("获取数据异常！");
		}
	});
}

function fillUl(albums){
	var ul = $(".user-album-list > ul");
	$.each(albums,function(i,album){
		var html = "";
		html += '<li id="album'+album.id+'">';
		 var type = album.type;
		if(album.cover != ""){
			if(type == 0){
				html += '<a href="userPages_familyComAlbum?albumId='+album.id+'&uid='+userID+'"><img src="'+album.cover+'@1e_160w_160h_1c_0i_1o_100Q_1x.jpg"></a>';
	        	html += '<div class="info">';
	        	 html += '<span class="glyphicon glyphicon-picture"></span>';
	        }else if(type == 1){
	        	html += '<a href="userPages_familyThemeAlbum?albumId='+album.id+'&uid='+userID+'"><img src="'+album.cover+'@1e_160w_160h_1c_0i_1o_100Q_1x.jpg"></a>';
	        	html += '<div class="info">';
	        	html += '<span class="glyphicon glyphicon-heart"></span>';
	        }else{
	        	html += '<a href="userPages_familyTimerAlbum?albumId='+album.id+'&uid='+userID+'"><img src="'+album.cover+'@1e_160w_160h_1c_0i_1o_100Q_1x.jpg"></a>';
	        	html += '<div class="info">';
	        	html += '<span class="glyphicon glyphicon-time"></span>';
	        }
      	}else html += '<a href="userPages_familyThemeAlbum?albumId='+album.id+'&uid='+userID+'"><img src=""></a>';
        
        html +=  '<span class="name">'+album.name+'</span>'
        html +=  '</div></li>';
        ul.append(html);            
	});
}

function fillBreadcrumb(club){
	var a = $(".breadcrumb > .family > a");
	a.html(club.nickname);
}

function download(_url){
		var a = document.createElement('a');
		a.href = _url;
		a.download = _url;
		a.id='download';
		document.body.appendChild(a);
		var alink = document.getElementById('download');
		alink.click();
		alink.parentNode.removeChild(a);
}

function initProgress(){
	var content = "<progress class='hide' style='position:absolute;margin-top: 80px;margin-left:10px;' value='0' max='100'></progress>";
	$(".user-album-list > ul > li>a").prepend(content);
}

$.extend({
	//锁定相册
	unlockAlbum : function(_id){
		W.$.confirm(
			"是否解锁此相册？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						albumID : _id
					},
					url:'pages/data/result.txt', //API
					success:function(json) {
						//锁定成功回调
						W.$.alert("解锁成功！");
						$("#album" + _id).removeClass("locked").find(".lockCover").remove();
						$("#album" + _id).find(".unlock").removeClass("unlock").addClass("lock").children("a").text("锁定");
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
	//锁定相册
	lockAlbum : function(_id){
		W.$.confirm(
			"是否锁定此相册？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						albumID : _id
					},
					url:'pages/data/result.txt', //API
					success:function(json) {
						//锁定成功回调
						W.$.alert("锁定成功！");
						$("#album" + _id).addClass("locked");
						$("#album" + _id).find(".lock").removeClass("lock").addClass("unlock").children("a").text("解锁");
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
	//删除相册
	deleteAlbum : function(_id){
		W.$.confirm(
			"是否删除此相册？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						albumID : _id
					},
					url:'pages/data/result.txt', //API
					success:function(json) {
						//删除成功回调
						W.$.alert("删除相册成功！");
						$("#album" + _id).hide(500,function(){
							$(this).remove();
						})
					},
					error: function(data) {
						W.$.alert("删除相册失败！");
					}
				});
			},
			function(){
				//alert("取消按钮");
			}
		);
	},
	
	downloadAjax:function(_albumId,obj){
		var currentValue = 0;
		var task = function(){}
		var len = arr.length;
		arr[len] = 0;
		$.ajax({
			type:"POST",
			dataType:"json",
			data:{
				id:_albumId
			},
			url:'json/album_downAlbumResource', //API
			success:function(json){
				if(json.result == "00000000"){
					download(json.path);
					currentValue = 100;
					obj.val(currentValue);
					clearInterval(arr[len]);
					setTimeout(function(){
						obj.addClass("hide");
						obj.val(0);
					}, 1500);
				}else{
					W.$.alert("下载失败");	
					clearInterval(arr[len]);
					obj.addClass("hide");
				}
			},
			error:function(){
				W.$.alert("下载异常");	
				clearInterval(arr[len]);
				obj.addClass("hide");	
			},
			beforeSend:function(XMLHttpRequest){
					currentValue = obj.val();
					//task(currentValue,obj);
					var val = currentValue;
					task = function(val,obj){
					val++;
					if(val > 99){
						val=99;
						clearInterval(arr[len]);
						return;
					}
					currentValue = val;
					obj.val(val);
					//console.info(val);
					}
					//执行定时队列
					arr[len] = setInterval(function(){task(currentValue,obj);}, 1000);
				}
				
		});
	}
});
</script>
  </body>
</html>
