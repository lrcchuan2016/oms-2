<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<title>美家秀后台管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<style>
*{ font-family:"微软雅黑";}
</style>
<link href="public/img/logo2.png" rel="shortcut icon">
<link href="public/css/bootstrap.min.css" rel="stylesheet">

<script src="public/js/jquery-1.10.2.min.js"></script>
<script src="public/js/bootstrap.min.js"></script>
<link href="public/apprise/apprise.css" rel="stylesheet">
<script src="public/apprise/apprise-1.5.full.js"></script>
<script src="public/layer/layer.min.js"></script>
<script>

;!function(){
	layer.use('extend/layer.ext.js', function(){});
}();


$(function(){
	
});

$.extend({
	confirm : function(_str , _Yesfun , _Nofun){
		apprise(
			_str || "是否要进行此操作？",
			{
				'confirm'		:	true, 		// Ok and Cancel buttons
				'verify'		:	true,		// Yes and No buttons
				'input'			:	false, 		// Text input (can be true or string for default text)
				'animate'		:	false,		// Groovy animation (can true or number, default is 400)
				'textOk'		:	'确认',		// Ok button default text
				'textCancel'	:	'取消',	// Cancel button default text
				'textYes'		:	'Yes',		// Yes button default text
				'textNo'		:	'No'		// No button default text
			},
			function(e){
				if(e){
					_Yesfun() || function(){};
				}else {
					_Nofun() || function(){};
				}
			}
		);
	},
	confirmInput : function(_str , _Yesfun , _Nofun){
		apprise(
			_str || "是否要进行此操作？",
			{
				'confirm'		:	true, 		// Ok and Cancel buttons
				'verify'		:	true,		// Yes and No buttons
				'input'			:	true, 		// Text input (can be true or string for default text)
				'animate'		:	false,		// Groovy animation (can true or number, default is 400)
				'textOk'		:	'确认',		// Ok button default text
				'textCancel'	:	'取消',	// Cancel button default text
				'textYes'		:	'Yes',		// Yes button default text
				'textNo'		:	'No'		// No button default text
			},
			function(e){
				if(typeof(e) === "string"){
					_Yesfun(e);
				}else {
					_Nofun();
				}
			}
		);
	},
	alert : function(_str , _Yesfun){
		 apprise(
			_str ||　"",
			{
				'confirm'		:	false, 		// Ok and Cancel buttons
				'verify'		:	false,		// Yes and No buttons
				'input'			:	false, 		// Text input (can be true or string for default text)
				'animate'		:	false,		// Groovy animation (can true or number, default is 400)
			
			},
			function(e){
				if(e){
					//if(!_Yesfun)_Yesfun();
				}
			}
		);
	}
});


$(function(){
	if(GetQueryString("page") == "templateList"){
		$("frame[name='menu']").attr("src","pages/index/menu.jsp?page=templateList");
	}
})

function GetQueryString(name)   {   
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");   
	var r = window.location.search.substring(1).match(reg);
	if (r != null) {
		return decodeURI(r[2]);
	}else {
		return null; 
	}
}
</script>

</head>
<!-- 主页面，通过Frame框架，加载整个页面的三个部分 -->
<frameset framespacing="0" border="0" rows="50,*">
	<frame src="pages/index/top.jsp" name="top" frameborder="0" noresize="" scrolling="no" marginwidth="0" marginheight="0">
	<frameset framespacing="0" border="0" cols="250,*">
		<frame src="pages/index/menu.jsp" frameborder="1" marginwidth="10" name="menu" scrolling="yes">
		<frame src="pages/index/right.jsp" frameborder="0" marginwidth="10" name="main">
	</frameset>
</frameset><noframes></noframes>
</html>