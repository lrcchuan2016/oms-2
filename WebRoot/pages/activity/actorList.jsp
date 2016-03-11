  <%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">
	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">活动管理</a></li>
      <li><a href="activityPages_activityList">活动列表</a></li>
      <li class="active">参与者列表</li>
    </ol>
    <div class="userListbox activity-list" style="margin-right:20px;">
        <div style="margin-top: 15px;">
    	<form class="retrieval" id="searchForm" action="javascript:void(null);">
    		<div class="form-group pull-left" style="width:350px;">
	    		<div class="input-group">
	    			<div class="input-group-addon">关键字</div>
                  	<input class="form-control keyWords" name="paramMap['keyWords']" data-valid="[{rule:'not_empty'}]" type="text" maxlength="100" placeholder="手机号码">
	    		</div>
           </div>
           &nbsp;<button type="submit" id="searchBtn" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 搜索</button>
		</form>
		</div>
		<div class="col-xs-12 padder-tm bg-empty">
			<div id="successTip" class="alert text-black alert-success hide fade in"></div>
			<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
		</div>
		<table id="activityTable" class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
			<thead>
   				<tr>
   					<td data-sort="int" style="width:30px;">ID</td>
   					<td style="width:20%;">相册封面</td>
					<td style="width:15%;">相册名称</td>
					<td style="width:10%;">手机号码</td>
					<td style="width:30%;">地址</td>
					<td style="width:15%;">参与时间</td>
   				</tr>
   			</thead>
			<tbody class="bg-white"></tbody>
			<tfoot><tr><td colspan="6">&nbsp;</td></tr></tfoot>
		</table>
	</div>
</div>
</body>
<script type="text/javascript">
var W = window.top;
var actionObj = null,activityTableObj = null,mapObj = new HashMap();
var activityId = '${activityId}';
$(function(){
	formObj = $("#searchForm").inputValid(valid_message_options);
	
	//getTimeInMillis(1);
	
	activityTableObj = $("#activityTable").tableList({
        url:"json/activity_actorList",
        param:"paramMap['keyWords']="+$(".keyWords").val()+"&paramMap['id']="+activityId,
        pageSize:12,
        paging:true,
        finishCallBack:function(){},
        template:htmlTemplate
    });
    
	actionObj = new Actions({ 
		successCallBack:function() { activityTableObj.refresh(); } 
	});
	
	$("#searchBtn").click(function(){
		if(formObj.validate_all()){
			activityTableObj.setOptions({
	            param:$("#searchForm").serialize(),
	            url:'json/activity_actorList'
	        });
		}
	});
	
	
});

function htmlTemplate(index, data, recordIndex){
	var html = "";
	var phone = data.phone;
	if(phone.indexOf("$") != -1){
		data.phone = phone.substring(1);
		html = "<tr class='info' id="+data.id+">"
	}else{
		html = "<tr class='danger' id="+data.id+">"
	}
	mapObj.put("t_"+data.id,data);

	html += '<td>'+recordIndex+'</td>';
	var albumCover = data.albumCover+"@170w_100h_2o";
	if(albumCover == "" 
		|| albumCover == 'undefined' 
		|| albumCover == 'null'
		|| -1!=albumCover.indexOf("null")){
		html += '<td><canvas width="200px" height="100px"></canvas></td>';
		drawCover();//绘制暂无封面
	}else{
		html += '<td><img src="'+albumCover+'"></td>';
	}
	if(data.link != null && data.link != ""){
		html += '<td><a id="'+data.link+'" href="activityPages_showBook?id='+data.link+'&name='+data.albumName+'"><span class="text-info">'+data.albumName+'</span></a></td>';
	}else{
		html += '<td>'+data.albumName+'</td>';
	}
	html += '<td>'+data.phone+'</td>';
	html += '<td>'+data.address+'</td>';
	html += '<td>'+formatUTC(data.participateTime,"yyyy-MM-dd hh:mm")+'</td>';
	html += '</tr>';
	
	return html;
}

function drawCover(){
	$("canvas").livequery(function(){
		$(this).each(function(){
			var canvas = $(this).get(0);
			var context = canvas.getContext("2d");
			context.font="30px Arial";
			context.fillText('暂无封面',35,50);
			
		});
	});
}
</script>
</html>