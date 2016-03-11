<%@ include file="/base.jsp"%>
  
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="public/book/double-page.css" rel="stylesheet">
<style>
body{ overflow-y:auto}
#imgBox img{ width:300px; border:6px solid #FFF; border-radius:10px; -webkit-box-shadow: 0px 0px 5px #999; margin:5px;}
</style>
<script type="text/javascript" src="public/book/extras/modernizr.2.5.3.min.js"></script>
<head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
     	<li><a href="javascript:void(0)">首页</a></li>
      <li><a href="activityPages_activityList">活动列表</a></li>
      <li><a href="activityPages_activityWinRecord">转盘活动</a></li>
      <li class="active">精美台历</li>
    </ol>
    <div class="userListbox">
    <div class="row">
    	<!---------基本信息------>
    	
    	<a class="btn btn-info download" href="javascript:void(0);" style="margin-bottom:20px;">
    	<span class="glyphicon glyphicon-save"></span>下载台历图片</a>
    	
      <div id="imgBox" class="clearfix">
      	  
      </div>
     
         
        
    </div>
    </div>
</div>

<div style="height:100px;">
	
</div>


<script>
var W = window.top;
$(function(){
	$.getTheme();
	//pageInit();
	
	//绑定下载图片触发事件
	bindDownLoad(".download");
});

//下载请求
function bindDownLoad(selector){
	$(selector).livequery(function(){
		$(this).click(function(){
			var prizeId = GetQueryString("prizeId");
			$.ajax({
				type:'post',
				dataType:'json',
				data:{"paramMap['prizeId']":prizeId},
				url:'json/activityTurn_downloadCalendar',
				success:function(json){
					if(json.result == '00000000'){
						//创建a元素,设定下载
						var a = document.createElement('a');
						a.href = json.path;
						a.download = json.path;
						a.id='download';
						document.body.appendChild(a);
						var alink = document.getElementById('download');
						alink.click();
						alink.parentNode.removeChild(a);
						
					}else{
						W.$.alert("下载失败!");
						
					}
					$(selector).html("下载台历图片");
					$(selector).removeAttr("disabled");
				},
				error:function(e){
					W.$.alert("台历图片下载异常！");
				},
				//返回响应前事件
				beforeSend:function(){
					$(selector).html("正在下载,请稍等");
					$(selector).attr("disabled","disabled");
				},
				error:function(){
					W.$.alert("下载失败!");
					$(selector).html("下载台历图片");
					$(selector).removeAttr("disabled");
				}
				
			
				
			});
		});
	});
}

function pageInit(){
	//document.write(JSON.stringify(xmldata));
	for(var i=0; i<xmldata.media.length; i++){
		$("#imgBox").append('<img src="'+xmldata.media[i].url+'">');
	}
}

function GetQueryString(name)   {   
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");   
	var r = window.location.search.substring(1).match(reg);
	if (r != null) {
		return decodeURI(r[2]);
	}else {
		return null; 
	}
}

$.extend({
	
	getTheme : function(){
		
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "http://www.meijx.cn/index.php/web/xmlToJson",
			data: { 
				"prizeId" : GetQueryString('prizeId')
			},
			success: function (json) {
				
				xmldata = json;
				
				pageInit();
			},
			error: function(){
				$("#imgBox").html("<h1>未制作</h1>");
			}
		});
	}

});

</script>
</body>
</html>
