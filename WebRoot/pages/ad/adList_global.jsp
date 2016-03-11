<%@page import="cn.broadin.oms.util.Tools"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib prefix="s" uri="/struts-tags"  %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<script src="public/js/jquery-1.10.2.min.js"></script>
<script src="public/js/bootstrap.min.js"></script>

<script src="public/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="public/js/jquery.livequery.js"></script>
<link href="public/uploadify/css/uploadify.css" rel="stylesheet">
<script src="public/uploadify/js/jquery.uploadify.min.js"></script>
<script src="public/js/comm.js"></script>


<head>
<body>
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="adAction_adList">广告管理</a></li>
      <li class="active">手机广告</li>
    </ol>
    
    <div class="userListbox ad-list">
    	
        <ul class="nav nav-tabs">
          <li role="presentation" class="active"><a href="adAction_adList">广告管理</a></li>
          <li role="presentation"><a href="adAction_locationList">广告位管理</a></li>
          <li role="presentation"><a href="pages/ad/ad_location_add.jsp">新建广告位</a></li>
        </ul>
        
        <ul class="nav nav-pills">
          <li role="presentation"><a href="adAction_adList">手机广告</a></li>
          <li role="presentation"><a href="adAction_adList?adType=2">电视广告</a></li>
          <li role="presentation"><a href="adAction_adList?adType=3">网站广告</a></li>
          <li role="presentation" class="active"><a href="adAction_adList?adType=0">全局广告</a></li>
        </ul>
      <s:iterator value="aMap" status="st">
   	  <div class="panel panel-default clearfix">
            <div class="panel-heading">
            	广告位 ：<s:property value="key"/>
                <div class="fold_icon">
                	<s:if test="#st.count==1"><span class="glyphicon glyphicon-chevron-down"></span></s:if>
                	<s:else><span class="glyphicon glyphicon-chevron-up"></span></s:else>
                </div>
            </div>
            <s:if test="#st.count==1"><table class="table table-hover table-bordered"></s:if>
            <s:else><table class="table table-hover table-bordered hide"></s:else>
           	  <thead>
               	  <tr>
                      <th>ID</th>
                      <th width="25%">媒资预览</th>
                      <th>开始时间</th>
                      <th>结束时间</th>
                      <th>发布人</th>
                      <th>备注</th>
                      <th>状态</th>
                      <th style="width:130px;">操作</th>
              </thead>
              <tbody>
              	<s:iterator value="value" status="st">
               	  <tr id="<s:property value="id"/>">
                      <td><s:property value="#st.count"/></td>
                      <td>
                      	<s:if test='contentType=="0"'>
                      		<img class="loadys img-responsive img-thumbnail" src="" data-src="<s:property value='content'/>">
                     	</s:if>
                     	<s:if test='contentType=="1"'>
                     		<video class="img-responsive img-thumbnail" controls width="200px" src="<s:property value='content'/>"></video>
                     	</s:if>
                     	<s:if test='contentType=="2"'>
                     		<s:property value="content"/>
                     	</s:if>
                     	<s:if test='contentType=="3"'>
                     		<audio controls src="<s:property value='content'/>"></audio>
                     	</s:if>
                      </td>
                      <td class="fmtDate"><s:property value="start_utc"/></td>
                      <td class="fmtDate"><s:property value="end_utc"/></td>
                      <td><s:property value="publisher"/></td>
                      <td><s:property value="remark"/></td>
                      <th> 
                      		<c:set var="now" value="<%=Tools.getNowUTC() %>"> </c:set>
                      		<c:if test="${now>start_utc}">
                      			<c:if test="${now<end_utc}">
                      			<span class="label label-success">使用中</span>
                      			</c:if>
                      		</c:if>
                      		<c:if test="${now<start_utc}">
                      			<span class="label label-danger">等待中</span>
                      		</c:if>
                      		<c:if test="${now>end_utc}">
                      			<span class="label label-default">已过期</span>
                      		</c:if>
                      		
                      </th>
                      <td>
                          <a href="adAction_editAdResource?id=${id }" class="btn btn-info btn-sm">编辑</a>
                          <a href="javascript:void(0)" class="btn btn-danger btn-sm">删除</a>
                      </td>
                  </tr>
                 
           		</s:iterator>
              </tbody>
          </table>
        </div>
      </s:iterator>
    	
	</div><!---userListbox-end-->
    
    </div>
<script>

var W = window.top;

$(function(){
	//绑定删除广告
	$("td .btn-danger").livequery(function(){
		$(this).click(function(){
			var id = $(this).parents("tr").attr("id");
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
				w = "200px";
				h = "auto";
			}else if(h>w){
				h = "200px";
				w = "auto";
			}
			$(element).attr("src" , src_url).css({
				width : w,
				height : h
			});
		}
    });
    
    //格式化时间
    $(".fmtDate").livequery(function(){
		$(this).each(function(){
			var t = formatUTC($(this).html(),"yyyy-MM-dd hh:mm");
			$(this).html(t);
		});
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
					url:'json/ad_delAdResource', //API
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
