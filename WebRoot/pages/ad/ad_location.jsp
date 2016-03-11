<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>美家秀运营管理系统</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">

<link href="public/img/logo2.png" rel="shortcut icon">

<link href="public/css/bootstrap.min.css" rel="stylesheet">
<link href="public/css/style_coo.css" rel="stylesheet">

<script type="text/javascript">

</script>

<script src="public/js/jquery-1.10.2.min.js"></script>
<script src="public/js/bootstrap.min.js"></script>

<script src="public/js/slimscroll/jquery.slimscroll.min.js"></script>

<script src="public/js/jquery.livequery.js"></script>

<head>
<body>
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="adAction_adList">广告管理</a></li>
      <li class="active">广告位管理</li>
    </ol>
    
    <div class="userListbox ad-list">
    	
        <ul class="nav nav-tabs">
          <li role="presentation"><a href="adAction_adList">广告管理</a></li>
          <li role="presentation" class="active"><a href="adAction_locationList">广告位管理</a></li>
          <li role="presentation"><a href="pages/ad/ad_location_add.jsp">新建广告位</a></li>
        </ul>
		
   	  <div class="panel panel-default clearfix">
            
          <table class="table table-hover table-bordered">
           	  <thead>
               	  <tr>
                      <th>NO.</th>
                      <th>名称</th>
                      <th>发布终端</th>
                      <th>媒资类型</th>
                      <th>高</th>
                      <th>宽</th>
                      <th>码率</th>
                      <th style="width:230px;">操作</th>
              </thead>
              <tbody>
              	  <s:iterator value="alist" status="st">
               	  <tr id='<s:property value="id"/>'> 
               	  	  <td><s:property value="id"/></td>
                      <td><s:property value="ad_name"/></td>
                      <td>
                      	<s:if test='terminal_type=="0"'>全局</s:if>
                      	<s:if test='terminal_type=="1"'>手机</s:if>
                      	<s:if test='terminal_type=="2"'>电视</s:if>
                      	<s:if test='terminal_type=="3"'>网站</s:if>
                      </td>
                      <td>
                      	<s:if test='type=="0"'>图片</s:if>
                      	<s:if test='type=="1"'>视频</s:if>
                      	<s:if test='type=="2"'>文本</s:if>
                      	<s:if test='type=="3"'>音频</s:if>
                      </td>
                      <td><s:property value="width"/></td>
                      <td><s:property value="height"/></td>
                      <td><s:property value="bitrate"/></td>
                      <td>
                          <a href="adAction_createAd?id=${id }" class="btn btn-success btn-sm">新建广告</a>
                          <a href="adAction_editAd?id=${id }" class="btn btn-info btn-sm">编辑</a>
                          <a href="javascript:void(0)" class="btn btn-danger btn-sm">删除</a>
                      </td>
                  </tr>
                  </s:iterator>
              </tbody>
          </table>
        </div>
    

	</div><!---userListbox-end-->
    
    </div>
<script>

var W = window.top;

$(function(){
	//绑定删除广告
	$("td .btn-danger").livequery(function(){
		$(this).click(function(){
			var id = $(this).parents("tr").attr("id")
			$.deleteAd(id);
		});
	});
	
	//点击展开
	$(".ad-list .panel-heading").click(function(){
		if($(this).next().hasClass("hide")){
			$(".ad-list .panel .table:not(.hide)").prev(".panel-heading").click();
			$(this).next().removeClass("hide");
			$(this).find(".glyphicon").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
		}else {
			$(this).next().addClass("hide");
			$(this).find(".glyphicon").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
		}
	});
	
	//图片延时架在，并控制大小
	$("img.loadys").each(function(index, element) {
		var src_url = $(element).attr("data-src");
		var Img = new Image();
		Img.src = src_url;
		Img.onload = function(){
			var w = Img.width;
			var h = Img.height;
			if(w>h){
				w = "150px";
				h = "auto"
			}else if(h>w){
				h = "150px";
				w = "auto"
			}
			$(element).attr("src" , src_url).css({
				width : w,
				height : h
			});
		}
    });
});


$.extend({
	deleteAd : function(_id){
		W.$.confirm(
			"是否要删除广告:"+_id+"？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						id : _id
					},
					url:'json/ad_delAdLocation', //API
					success:function(json) {
						//删除成功回调
						if(json.result == "00000000"){
							W.$.alert("删除成功！");
							$("#" + _id).remove(); //删除行
						}else {
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
	}
});
</script>
</body>
</html>
