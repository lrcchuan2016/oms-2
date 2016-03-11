<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">
	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">活动管理</a></li>
      <li><a href="activityPages_activityList">活动列表</a></li>
      <li class="active">转盘活动</li>
    </ol>
    
    <div class="userListbox channel-list" style="margin-right:20px;">
    	<ul class="nav nav-tabs">
          <li role="presentation" class="active"><a href="activityPages_activityWinRecord">未领奖</a></li>
          <li role="presentation"><a href="activityPages_activityNonDeliver">待发奖</a></li>
        	<li role="presentation"><a href="activityPages_activityDelivered">已发奖</a></li>
        	<li role="presentation"><a href="activityPages_activityGiftList">查看奖品</a></li>
        </ul>
    	<div style="margin-top: 15px;">
    	<form class="retrieval" id="searchForm" action="javascript:void(null);">
    		<div class="form-group pull-left" style="width:350px;">
	    		<div class="input-group">
	    			<div class="input-group-addon">关键字</div>
                  	<input class="form-control" name="paramMap['keyWords']" data-valid="[{rule:'not_empty'}]" type="text" maxlength="30" placeholder="家庭号/手机号/呢称">
	    		</div>
           </div>
           &nbsp;<button type="submit" id="searchBtn" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 搜索</button>
		</form>
		</div>
		
		<table id="activityTurntable" class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
			<thead>
   				<tr>
   					<td>NO.</td>
   					<td>中奖物品</td>
   					<td>中奖号码</td>
   					<td>呢称</td>
   					<td>家庭号</td>
   					<td>中奖日期</td>
   					
   				</tr>
   			</thead>
			<tbody class="bg-white"></tbody>
			<tfoot><tr><td colspan="6">&nbsp;</td></tr></tfoot>
		</table>
	</div>
</div>
<script type="text/javascript">
var W = window.top;
var activityId = '${param.activityId}';
var activityTableObj = null,formObj = null;
$(function(){
	
    initDat();
   
   	//搜索
   	bindSearch();
});

function bindSearch(){
	$("#searchBtn").click(function(){
		if(formObj.validate_all()){
			activityTableObj.setOptions({
	            param:"&paramMap['isDeliver']=0&"+$("#searchForm").serialize(),
	            url:'json/activityTurn_getTurntablWinRecordList'
	        });
		}
	});
}

function initDat(){
	//验证
	formObj = $("#searchForm").inputValid(valid_message_options);
	
	activityTableObj = $("#activityTurntable").tableList({
        url:"json/activityTurn_getTurntablWinRecordList",
        param:"&paramMap['isDeliver']=0",
        pageSize:12,
        paging:true,
        finishCallBack:function(){},
        template:htmlTemplate
    });
}

function htmlTemplate(index,data,recordIndex){
	var html = "";
	html += '<tr id="'+data.id+'">';
	html += '<td>'+recordIndex+'</td>';
	html += '<td>'+data.name+'</td>';
	html += '<td>'+data.account+'</td>';
	html += '<td>'+data.nickname+'</td>';
	html += '<td>'+data.clubNum+'</td>';
	html += '<td>'+formatUTC(data.winningUtc,"yyyy-MM-dd hh:mm")+'</td>';
	html += '</tr>';
	return html;
}
</script>
</body>

</html>