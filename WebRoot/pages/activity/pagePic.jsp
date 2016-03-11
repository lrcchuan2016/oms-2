
<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

</head>
<body>
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="activityPages_activityList">活动列表</a></li>
      <li><a href="javascript:history.back()">参与者列表</a></li>
      <li class="active">参与相册</li>
    </ol>
    
    <div style="margin-left:20px;margin-right: 20px;">
    	<div class="col-xs-12 no-padder" style="min-height:450px;">
			<div class="tab-pane fade in">
				<div class="panel panel-default showList"></div>
			</div>
		</div>
    </div>   
</div>

<script>
var W = window.top;
var id = '${param.id}';
var page = '${param.page}';
$(function(){
	//获取相册图片
	initPagePic();
});

function initPagePic(){
	$.ajax({
			type:"POST",
			dataType:"json",
			data:{
				"paramMap['id']" : id,
				"paramMap['page']" : page
			},
			url:'json/activity_downPagePic', //API
			success:function(json) {
			//添加成功回调
				if(json.result == "00000000"){
					var urls = json.urls;
					if(urls.length>0){
						$(".showList").html("");
						for ( var i in urls) {
							var url = urls[i];
							var bgUrl = url.bgUrl;
							if(i==0) $(".showList").append("<div><h3>封面</h3>");
							else $(".showList").append("<div class='clearfix'><h3>第"+i+"页</h3>");
							$(".showList").append("<div class='clearfix'><ul ><li class='panel-body'><a href='"+bgUrl+"' download='"+bgUrl+"' ><img src='"+bgUrl+"@200h_2e_2o'/></a></li></ul></div><br>");
							var decorates = url.decorateUrls;
							var picUrls = url.picUrls;
							console.info(picUrls);
							if(picUrls != 'undefined' ||　picUrls.length != 'undefined'　||　picUrls.length != 0){
								listPic(picUrls,i);
							}
							if(decorates != 'undefined' || decorates.length != 'undefined' || decorates.length != 0){
								listPic(decorates,i);
							}
							$(".showList").append("</div>");
						}
					}
				}else{
					W.$.alert("获取数据失败！");
				}
			},
			error: function(data) {
				W.$.alert("获取数据失败！");
			}
		});
}

/*
*列表
*/
function listPic(arr){
	var obj = $(".showList");
	var html = "";
	 html += "<div><ul class='clearfix'>";
	 //console.info(arr);
	for ( var i in arr) {
		html += "<li class='panel-body pull-left'><a href='"+arr[i]+"' download='"+arr[i]+"' ><img src='"+arr[i]+"@200h_2e_2o.png'/></a><li>";
	}
	html +="</ul><div><br>"
	obj.append(html);
}
</script>
</body>
</html>
