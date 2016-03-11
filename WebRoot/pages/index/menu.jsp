<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href=<%=basePath %>/>
<title>美家秀后台管理系统</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link href="public/img/logo2.png" rel="shortcut icon">
<link href="public/css/bootstrap.min.css" rel="stylesheet">
<link href="public/css/style_coo.css" rel="stylesheet">
<link rel="stylesheet" href="public/css/menu_coo.css" type="text/css" />
<script src="public/js/jquery-1.10.2.min.js"></script>
<script src="public/js/bootstrap.min.js"></script>
<script src="public/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="public/js/inputvalid.js"></script>
<script src="public/js/app.plugin.js"></script><html>

</head>
<body>

<div class="panel panel-default" style="display:block;">
	<section class="scrollable" id="sidebar" style="position:relative;">
     <div class="panel-group" id="accordion">
     
     	<ul class="menuList">
     	<c:if test="${null!=pMap['1000']}">
        	<li class="active">
            	<a href="javascript:void(0);"><span class="glyphicon glyphicon-cog"></span>管理员管理</a>
            	<c:forEach items="${pMap['1000'] }" var="p" varStatus="vs">
                <c:if test="${vs.count==5 }"><div class="panel panel-success hide"></c:if>
                <c:if test="${vs.count!=5 }"><div class="panel panel-success"></c:if>
                    <a type="button" href=${p.url } target="main" class="btn btn-success btn-sm btn-block"
                        data-toggle="collapse" data-target="#collapse0" data-parent="#accordion">${p.description }
                    </a>
                </div>
              	</c:forEach>	
        	</li>
         </c:if>
         
         <c:if test="${null!=pMap['2000']}">
        	<li>
            	<a href="javascript:void(0);"><span class="glyphicon glyphicon-user"></span>用户管理</a>
            	<c:forEach items="${pMap['2000'] }" var="p">
                <div class="panel panel-success">
                    <a type="button" href=${p.url } target="main" class="btn btn-success btn-sm btn-block"
                        data-toggle="collapse" data-target="#collapse0" data-parent="#accordion">${p.description }
                    </a>
                </div>
              	</c:forEach>
        	</li>
         </c:if>
         
         <c:if test="${null!=pMap['3000']}">
        	<li>
            	<a href="javascript:void(0);"><span class="glyphicon glyphicon-retweet"></span>渠道管理</a>
            	<c:forEach items="${pMap['3000'] }" var="p" varStatus="vs">
                <c:if test="${vs.count==2 || vs.count==3 }"><div class="panel panel-success hide"></c:if>
                <c:if test="${vs.count!=2 && vs.count!=3 }"><div class="panel panel-success"></c:if>
                    <a type="button" href=${p.url } target="main" class="btn btn-success btn-sm btn-block"
                        data-toggle="collapse" data-target="#collapse0" data-parent="#accordion">${p.description }
                    </a>
                </div>
              	</c:forEach>	
        	</li>
         </c:if>
         
         <c:if test="${null!=pMap['9000']}">
        	<li>
            	<a href="javascript:void(0);"><span class="glyphicon glyphicon-th-large"></span>家庭管理</a>
            	<c:forEach items="${pMap['9000'] }" var="p">
                <div class="panel panel-success">
                    <a type="button" href=${p.url } target="main" class="btn btn-success btn-sm btn-block"
                        data-toggle="collapse" data-target="#collapse0" data-parent="#accordion">${p.description }
                    </a>
                </div>
              	</c:forEach>	
        	</li>
         </c:if>
         
         <c:if test="${null!=pMap['8000']}">
        	<li>
            	<a href="javascript:void(0);"><span class="glyphicon glyphicon-hdd"></span>版本管理</a>
            	<c:forEach items="${pMap['8000'] }" var="p">
                <div class="panel panel-success">
                    <a type="button" href=${p.url } target="main" class="btn btn-success btn-sm btn-block"
                        data-toggle="collapse" data-target="#collapse0" data-parent="#accordion">${p.description }
                    </a>
                </div>
              	</c:forEach>	
        	</li>
         </c:if>
         
         <c:if test="${null!=pMap['4000']}">
        	<li>
            	<a href="javascript:void(0);"><span class="glyphicon glyphicon-cloud"></span>数据分析</a>
            	<c:forEach items="${pMap['4000'] }" var="p">
                <div class="panel panel-success">
                    <a type="button" href=${p.url } target="main" class="btn btn-success btn-sm btn-block"
                        data-toggle="collapse" data-target="#collapse0" data-parent="#accordion">${p.description }
                    </a>
                </div>
              	</c:forEach>	
        	</li>
         </c:if>
         
         <c:if test="${null!=pMap['5000']}">
        	<li>
            	<a href="javascript:void(0);"><span class="glyphicon glyphicon-asterisk"></span>运营管理</a>
            	<c:forEach items="${pMap['5000'] }" var="p" varStatus="vs">
                <div class="panel panel-success">
                    <a type="button" href=${p.url } target="main" class="btn btn-success btn-sm btn-block"
                        data-toggle="collapse" data-target="#collapse0" data-parent="#accordion">${p.description }
                    </a>
                </div>
              	</c:forEach>	
        	</li>
         </c:if>
         
         <c:if test="${null!=pMap['10000']}">
        	<li>
            	<a href="javascript:void(0);"><span class="glyphicon glyphicon-qrcode"></span>活动管理</a>
            	<c:forEach items="${pMap['10000'] }" var="p" varStatus="vs">
                <div class="panel panel-success">
                    <a type="button" href=${p.url } target="main" class="btn btn-success btn-sm btn-block"
                        data-toggle="collapse" data-target="#collapse0" data-parent="#accordion">${p.description }
                    </a>
                </div>
              	</c:forEach>	
        	</li>
         </c:if>
         
         <c:if test="${null!=pMap['6000']}">
        	<li id="templateList">
            	<a href="javascript:void(0);"><span class="glyphicon glyphicon-heart"></span>模版管理</a>
            	<c:forEach items="${pMap['6000'] }" var="p">
                <div class="panel panel-success" >
                    <a type="button" href=${p.url } target="main" class="btn btn-success btn-sm btn-block"
                        data-toggle="collapse" data-target="#collapse0" data-parent="#accordion">${p.description }
                    </a>
                </div>
              	</c:forEach>	
        	</li>
         </c:if>
         
         <c:if test="${null!=pMap['7000']}">
        	<li class="hide">
            	<a href="javascript:void(0);"><span class="glyphicon glyphicon-th-list"></span>资源管理</a>
            	<c:forEach items="${pMap['7000'] }" var="p">
                <div class="panel panel-success">
                    <a type="button" href=${p.url } target="main" class="btn btn-success btn-sm btn-block"
                        data-toggle="collapse" data-target="#collapse0" data-parent="#accordion">${p.description }
                    </a>
                </div>
              	</c:forEach>	
        	</li>
         </c:if>
         
         <c:if test="${null!=pMap['11000']}">
        	<li>
            	<a href="javascript:void(0);"><span class="glyphicon glyphicon-envelope"></span>消息推送</a>
            	<c:forEach items="${pMap['11000'] }" var="p">
                <div class="panel panel-success">
                    <a type="button" href=${p.url } target="main" class="btn btn-success btn-sm btn-block"
                        data-toggle="collapse" data-target="#collapse0" data-parent="#accordion">${p.description }
                    </a>
                </div>
              	</c:forEach>	
        	</li>
         </c:if>
         
         <c:if test="${null!=pMap['12000']}">
        	<li>
            	<a href="javascript:void(0);"><span class="glyphicon glyphicon-shopping-cart"></span>商城管理</a>
            	<c:forEach items="${pMap['12000'] }" var="p">
                <div class="panel panel-success">
                    <a type="button" href=${p.url } target="main" class="btn btn-success btn-sm btn-block"
                        data-toggle="collapse" data-target="#collapse0" data-parent="#accordion">${p.description }
                    </a>
                </div>
              	</c:forEach>	
        	</li>
         </c:if>
         
          </ul>
	  </div>
	</section>
</div><!--
<div class="divider"></div>-->
</body>
<script type="text/javascript">
$(function() {
	//滑入滑出
	initMenu();
	
    $("a").click(function() {
        $("a").removeClass("active");
        $(this).addClass("active");
        //iFrameHeight();
    });
	/*
	$(".menuList li > a").click(function(){
		$(".menuList li").removeClass("active");
        $(this).parent().addClass("active");
	});
	*/
	//$(".menuList li > a").click(function(){
		//$(".menuList li > div > a").slideUp();
	//	$(".menuList li").removeClass("active");
	//	$(this).nextAll("div").children("a").slideDown(500);
     //   $(this).parent().addClass("active");
	//});
});

$(function(){
	if(GetQueryString("page") == "templateList"){
		$("#templateList a").click();
		$("#templateList > div").eq(1).find("a").click();
		window.top.$("frame[name='main']").attr("src","templatePages_templateList");
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

function initMenu() {
	var a = $(".menuList>li>a");
	//初始化显示菜单
	a.nextAll("div").hide();

	//绑定点击事件
	a.click(function(){
		var $this = $(this);
		a.nextAll("div").slideUp();
		a.parent().removeClass("active");
		setTimeout(function(){
			$this.nextAll("div").slideDown();
			$this.parent().addClass("active");
		}, 200);
	});
}
</script>
</html>