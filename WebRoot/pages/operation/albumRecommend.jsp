<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@taglib prefix="s" uri="/struts-tags"%>
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

<head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">运营管理</a></li>
      <li class="active">相册推荐</li>
    </ol>
    
    <div class="userListbox album-recom-list row">
        <!------------左边--------------->
        <div class="col-md-6" style="border-right:2px solid #CCC;">
        	<div class="form-group pull-left" style="width:200px;">
                <div class="input-group">
                  <div class="input-group-addon">查找字段</div>
    				<select class="form-control option" name="option">
    					<option value="0">相册名称</option>
    					<option value="1">创建人</option>
    				</select>
    			</div>
    			</div>
                 <div class="form-group pull-left" style="width:250px;">
                    <div class="input-group">
                      <div class="input-group-addon">关键字</div>
                      <input type="text" class="form-control" id="keyWords" name="keyWords" placeholder="创建人/相册名称" onfocus="this.value=''">
                    </div>
                  </div>
                  <button type="submit" class="btn btn-primary search">搜索</button>
            
            <table class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
                <thead>
                    <tr>
                        <td style="display: none">相册ID</td>
                        <td>封面</td>
                        <td class="albumName">名称</td>
                        <td>相册类型</td>
                        <td>创建人</td>
                        <td>操作</td>
                    </tr>
                </thead>
                <tbody class="tbodyAlbum">
                    
                </tbody>
            </table>
            
            <nav class="pages_number">
              <ul class="pagination pull-right leftUl"></ul>
              <div class="pull-right">
                <span>共<strong><span class="sAlbumPage"></span></strong>页&nbsp;&nbsp;<strong class="sTotalAlbum"></strong>张</span>
              </div>
            </nav>
        </div>
        
        <!------------右边--------------->
        <div class="col-md-6">
        	<div class="right-title">
            	推荐相册
            </div>
        	<table class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
                <thead>
                    <tr>
                        <td style="display: none">相册ID</td>
                        <td>封面</td>
                        <td>名称</td>
                        <td>相册类型</td>
                        <td>创建人</td>
                        <td>操作</td>
                    </tr>
                </thead>
                <tbody class="tbodyRec">
                    
                </tbody>
            </table>
             <!--  <nav class="pages_number">
              <ul class="pagination pull-right leftUl"></ul>
              <div class="pull-right">
                <span>共<strong><span class="recommendPage"></span></strong>页&nbsp;&nbsp;<strong class="TotalRecommend"></strong>张</span>
              </div>
            </nav>
            -->
        </div>
        
        
	</div><!---userListbox-end-->
    
    </div>
<script>

var W = window.top;

$(function(){
	
	$("td .btn-info").livequery(function(){
		$(this).click(function(){
			var id = $(this).parents("tr").attr("id");
			$.recommend(id);
		});
	});
	
	$(".pagination").find("a").livequery(function(){
		$(this).click(function(){
			var v = $(this).attr("name");
			var option = $(".option").val();
			var keyWords = $("#keyWords").val();
			var data = {"page":v.split(",")[0],"type":v.split(",")[1],"keyWords":keyWords,"option":option};
			$.ajax({
				type:"post",
				dataType:"json",
				data:data,
				url:"json/album_albumList",
				success:$.callback
			});
		});
	});
	
	$(".search").click(function(){
		var option = $(".option").val();
		var keyWords = $("#keyWords").val().trim();
		$.post("json/album_albumList",{"type":"0","keyWords":keyWords,"option":option},$.searchCallback);
		
	});
	
	$.initAlbum();
	
	//强制换行
	$(".albumName").livequery(function(){
		$(this).css({"word-break":"break-all","word-wrap":"break-word","text-overflow":"ellipsis"});
	});
	
	//相册封面限定宽高
	$(".album_cover").livequery(function(){
		$(this).each(function(){
			var url = $(this).attr("src");
			if(url != ''){
				if(url.indexOf('@') == -1){
					$(this).attr("src",$(this).attr("src")+'@1e_150w_150h_1c_0i_1o_100Q_1x.jpg');
				}
			}
		});
	});
	
	$(".dropdown-menu").livequery(function(){
		$(this).each(function(){
			$(this).css({"min-width":"60px"});
		});
	});
	
	//置顶
	$(".Top").livequery(function(){
		$(this).click(function(){
			var id = $(this).parents("tr").attr("id");
			$.firstRecommend(id);
		});
	});
	
	//取消
	$(".cancel").livequery(function(){
		$(this).click(function(){
			var id = $(this).parents("tr").attr("id");
			$.cancelRecommend(id);
		});
	});
});



$.extend({
	recommend : function(_id){
		W.$.confirmInput(
			"推荐原因：",
			function(inputVal){
				//此处加入删除的AJAX
				var reason = inputVal || "";
				if(reason === ""){
					W.$.alert("原因不能为空！");
					return;
				}
				//此处加入添加的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"paramMap['id']" : _id,
						"paramMap['reason']" : reason
					},
					url:'json/album_recommendAlbum', //API
					success:function(json) {
						//添加成功回调
						if(json.result == "00000000"){
							W.$.alert("添加成功！");
							$("#" + _id).find("td").last().html("<span class='Top' style='cursor:pointer;'><img src='public/img/top.png' style='width:64px;height:26px;'></span><br><br><span class='cancel' style='cursor:pointer;'><img src='public/img/cancel.png' style='width:64px;height:26px;'></span>");
							var r = "<tr id='"+_id+"'>"+$("#" + _id).html()+"</tr>";
							$("#" + _id).remove(); //删除行
							var tr = $(".tbodyRec").find("tr");	//添加行
							if(tr.length == 0){
								$(".tbodyRec").html(r);
							}else tr.first().before(r);
							$(".sTotalAlbum").html(parseInt($(".sTotalAlbum").html())-1);
						}else if(json.result == "00000002"){
							W.$.alert(json.tip);
						}else{
							W.$.alert("失败！");
						}
					},
					error: function(data) {
						W.$.alert("失败！");
					}
				});
			},
			function(){
				//alert("取消按钮");
			}
		);
	},
	cancelRecommend : function(_id){
		W.$.confirm(
			"是否要取消推荐:"+_id+"？",
			function(){
				//此处加入取消的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"paramMap['album_id']" : _id
					},
					url:'json/album_recommendAlbumCancel', //API
					success:function(json) {
						//删除成功回调
						if(json.result == "00000000"){
							W.$.alert("取消成功！");
							var tr = $(".tbodyRec").find("tr[id='"+_id+"']");
							tr.remove(); //删除行
						}else {
							W.$.alert("失败！");
						}
					},
					error: function(data) {
						W.$.alert("失败！");
					}
				});
			},
			function(){
				//alert("取消按钮");
			}
		);
	},
	
	firstRecommend : function(_id){
				//此处加入取消的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"paramMap['album_id']" : _id
					},
					url:'json/album_stickAlbum', //API
					success:function(json) {
						//删除成功回调
						if(json.result == "00000000"){
							var oldTr = $(".tbodyRec").find("tr[id='"+_id+"']");
							var r = "<tr id='"+_id+"'>"+oldTr.html()+"</tr>";
							oldTr.remove(); //删除行
							var firstTr = $(".tbodyRec").find("tr");	//添加行
							if(firstTr.length == 0){
								$(".tbodyRec").html(r);
							}else firstTr.first().before(r);
						}else {
							W.$.alert("失败！");
						}
					},
					error: function(data) {
						W.$.alert("失败！");
					}
				});
	},
	
	initAlbum : function(){
		$.post("json/album_albumList",function(data){
			//页数
			if(null!=data.pageAlbum){
				$(".sAlbumPage").html(data.pageAlbum);
			}else{
				$(".sAlbumPage").html(0);
			}
			//总记录数
			if(null!=data.totalAlbum){
				$(".sTotalAlbum").html(data.totalAlbum);
			}else{
				$(".sTotalAlbum").html(0);
			}
			//链接
			if(null!=data.pageLinksAlbum && ""!=data.pageLinksAlbum){
				$(".leftUl").html(data.pageLinksAlbum);
			}
			
			//表格数据
			if(null!=data.pBeanAlbum && data.pBeanAlbum.length!=0){
				$(".tbodyAlbum").html("");
				$.each(data.pBeanAlbum,function(){
					var pBean = this;
					var account = pBean.account;
					if(null == account) account="";
					var type;
					if(pBean.type=="0"){
						type = "普通相册";
					}else if(pBean.type=="1"){
						type = "主题相册";
					}else{
						type = "时间轴相册";
					}
					$(".tbodyAlbum").append("<tr id="+pBean.id+"><td style=\"display: none;\">"+pBean.id+"</td><td><img style='width:150px;height:auto;' class=\"album_cover\" src=\""+pBean.cover+"\"></td><td class=\"albumName\">"+pBean.name+"</td><td>"+type+"</td><td>"+account+"</td><td><a class=\"btn btn-info btn-sm\" href=\"javascript:void(0)\">>></a></td></tr>");
				});
			}
			
			if(null!=data.pBeanRecommend && data.pBeanRecommend.length!=0){
				console.in
				$(".tbodyRec").html("");
				$.each(data.pBeanRecommend,function(){
					var pBean = this;
					var type;
					if(pBean.type=="0"){
						type = "普通相册";
					}else if(pBean.type=="1"){
						type = "主题相册";
					}else{
						type = "时间轴相册";
					}
					$(".tbodyRec").append("<tr id="+pBean.id+"><td style=\"display: none;\">"+pBean.id+"</td><td><img style='width:150px;height:auto;' class=\"album_cover\" src=\""+pBean.cover+"\"></td><td >"+pBean.name+"</td><td>"+type+"</td><td>"+pBean.account+"</td><td><span class='Top' style='cursor:pointer;'><img src='public/img/top.png' style='width:64px;height:26px;'></span><br><br><span class='cancel' style='cursor:pointer;'><img src='public/img/cancel.png' style='width:64px;height:26px;'></span></td></tr>");
				});
			}
		});
	},
	
	callback : function(data){
		if(null!=data.pBeanAlbum && data.pBeanAlbum.length!=0){
			if(null!=data.pageAlbum){
				$(".sAlbumPage").html(data.pageAlbum);
			}else{
				$(".sAlbumPage").html(0);
			}
			if(null!=data.totalAlbum){
				$(".sTotalAlbum").html(data.totalAlbum);
			}else{
				$(".sTotalAlbum").html(0);
			}
			if(null!=data.pageLinksAlbum && ""!=data.pageLinksAlbum){
				$(".leftUl").html(data.pageLinksAlbum);
			}
			if(null!=data.pBeanAlbum && data.pBeanAlbum.length!=0){
				$(".tbodyAlbum").html("");
				$.each(data.pBeanAlbum,function(){
					var pBean = this;
					var account = pBean.account;
					if(null == account) account="";
					var type;
					if(pBean.type=="0"){
						type = "普通相册";
					}else if(pBean.type=="1"){
						type = "主题相册";
					}else{
						type = "时间轴相册";
					}
					$(".tbodyAlbum").append("<tr id="+pBean.id+"><td style=\"display: none;\">"+pBean.id+"</td><td><img style='width:150px;height:auto;' class=\"album_cover\" src=\""+pBean.cover+"\"></td><td>"+pBean.name+"</td><td>"+type+"</td><td>"+account+"</td><td><a class=\"btn btn-info btn-sm\" href=\"javascript:void(0)\">>></a></td></tr>");
				});
			}
		}
	},
	
	searchCallback : function(data){
				if(null!=data.pageAlbum){
					$(".sAlbumPage").html(data.pageAlbum);
				}else{
					$(".sAlbumPage").html(0);
				}
				if(null!=data.totalAlbum){
					$(".sTotalAlbum").html(data.totalAlbum);
				}else{
					$(".sTotalAlbum").html(0);
				}
				$(".leftUl").html(data.pageLinksAlbum);
				$(".tbodyAlbum").html("");
				$.each(data.pBeanAlbum,function(){
					var pBean = this;
					var account = pBean.account;
					if(null == account) account="";
					var type;
					if(pBean.type=="0"){
						type = "普通相册";
					}else if(pBean.type=="1"){
						type = "主题相册";
					}else{
						type = "时间轴相册";
					}
					$(".tbodyAlbum").append("<tr id="+pBean.id+"><td style=\"display: none;\">"+pBean.id+"</td><td><img style='width:150px;height:auto;' class=\"album_cover\" src=\""+pBean.cover+"\"></td><td>"+pBean.name+"</td><td>"+type+"</td><td>"+account+"</td><td><a class=\"btn btn-info btn-sm\" href=\"javascript:void(0)\">>></a></td></tr>");
				});
	}
});
</script>
</body>
</html>
