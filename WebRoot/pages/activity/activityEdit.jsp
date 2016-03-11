<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="public/css/bootstrap-datetimepicker.css" rel="stylesheet">
<script src="public/js/bootstrap-datetimepicker.min.js"></script>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0);">首页</a></li>
      <li><a href="javascript:void(0);">活动管理</a></li>
      <li><a href="activityPages_activityList">活动列表</a></li>
      <li class="active">编辑活动</li>
    </ol>
    
    <div class="row channel-add" style="padding:0px 20px;">
       
        
            <div class="panel panel-default clearfix">
              <div class="panel-heading">编辑活动</div>
              <form id="activityForm" action="javascript:void(0);">
              <div class="panel-body col-md-8">
              		<input type="text" class="hide" value="1" name="activity.type" data-valid="[{rule:'not_empty'}]">
              		<input type="text" class="hide" value="${activity.id }" name="activity.id" data-valid="[{rule:'not_empty'}]">
                  	<input type="text" class="hide" value="${activity.create_utc }" name="activity.create_utc" data-valid="[{rule:'not_empty'}]">
                  	<input type="text" class="hide" value="${activity.publisher_id }" name="activity.publisher_id" data-valid="[{rule:'not_empty'}]">
                  	<input type="text" class="hide" value="${activity.adLocation_id }" name="activity.adLocation_id" data-valid="[{rule:'not_empty'}]">
                  <div class="form-group">
                    <label for="activityName">活动名称</label>
                    <input type="text" class="form-control" id="activityName" name="activity.content" value="${activity.content }" data-valid="[{rule:'not_empty'}]" placeholder="不能为空" maxlength="30">
                  </div>
                  <div class="form-group">
                    <label for="activityURL">活动URL</label>
                    <input type="text" class="form-control" id="activityURL" name="activity.url" value="${activity.url }" data-valid="[{rule:'not_empty'}]" placeholder="不能为空" maxlength="120">
                  </div>
                  <div class="form-group">
                    <label for="startTime">开始时间</label>
                    <input type="datetime" name="startTime" id="startTime" value="${activity.start_utc }" data-valid="[{rule:'not_empty'}]" readonly class="form_datetime form-control clear">
                  </div>
                  <div class="form-group">
                    <label for="endTime">结束时间</label>
                    <input type="datetime" name="endTime" id="endTime"  value="${activity.end_utc }" data-valid="[{rule:'not_empty'}]" readonly class="form_datetime form-control clear">
                  </div>
                  <div class="form-group">
                    <label for="detail">详情</label>
                    <textarea class="form-control" id="detail" rows="4" name="activity.remark" data-valid="[{rule:'not_empty'}]" placeholder="活动详情介绍">${activity.remark }</textarea>
                  </div>
              </div>
              </form>
            </div>
            
            <hr>
    
    			<button type="submit" class="btn btn-primary btn-save">保存</button>
    			<button onClick="javascript:history.back()" type="button" class="btn btn-default">返回</button>
        </div>
    
</div>

<div style="height:200px;">
</div>
<script>

var W = window.top;
var formObj = null;
$(function(){
	formObj = $("#activityForm").inputValid(valid_message_options);
	$(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii'});
	
	$(".btn-save").livequery(function(){
		$(this).click(function(){
			if(formObj.validate_all()){
				$.ajax({
					type:'POST',
					dataType:'json',
					data:$("#activityForm").serializeArray(),
					url:'json/activity_activityUpdate',
					success:function(json){
						if(json.result == '00000000'){
							W.$.alert("修改活动成功！");
							setTimeout(function(){location.href="activityPages_activityList";},2000);
						}else{
							W.$.alert("修改活动失败！");
						}
					},
					error:function(){
						W.$.alert("新增活动失败！");
					}
				});
			}
		});
	});
	
	$(".form_datetime").livequery(function(){
		$(this).each(function(){
			var time = $(this).val();
			var t = formatUTC(time,"yyyy-MM-dd hh:mm");
			$(this).val(t);
		});
	});
	
});


$.extend({
	
});
</script>
</body>
</html>
