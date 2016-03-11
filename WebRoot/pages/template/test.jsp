<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cn" style="height:100%">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv=”Access-Control-Allow-Origin” content=”*”>
<title>美家秀-制作</title>

<style type="text/css" media="screen">
#flashContent {
	display: none;
}
html ,body { height: 100%; padding: 0; margin:0}
</style>

<script src="public/swf/jquery-1.9.1.min.js"></script>
<script src="public/swf/swfobject.js"></script>

<script type="text/javascript">

function initMake(){
	var swfVersionStr = "10.0.0";
	var xiSwfUrlStr = "public/swf/playerProductInstall.swf";
	
	//此处传参数
	var flashvars = {
		token : "${token }",
		templateId : "${templateBean.id}",  //templateBean对象
		templateName : "${templateBean.name}", //模版名称
		coverUrl : "${templateBean.coverUrl}", //模版封面
		remark:"${templateBean.introduction}",	//简介
		groupName :"${templateBean.groupName}",	//模版分类名
		userId : "${admin.id}"
	};
	
	var params = {};
	params.quality = "high";
	params.bgcolor = "#ffffff";
	params.allowscriptaccess = "sameDomain";
	params.allowfullscreen = "true";
	var attributes = {};
	attributes.id = "Album";
	attributes.name = "Album"; //定义对象名称
	attributes.align = "middle";
	swfobject.embedSWF(
			"public/swf/AlbumTemplate.swf", "flashContent", 
		"100%", "100%", 
		swfVersionStr, xiSwfUrlStr, 
		flashvars, params, attributes);
	swfobject.createCSS("#flashContent", "display:block;text-align:left;");
}

$(function(){
	initMake();
});	

//加载完成
function loadFinish(){
	//调用flex内部函数
	//Album.addFile();
	//Album.startUpload();
}

function gotoTemplateList(){
       window.location.href = "http://oms.meijx.cn/main?page=templateList";
}

</script>
</head>
<body>
<div id="flashContent"></div>
</body>
</html>
