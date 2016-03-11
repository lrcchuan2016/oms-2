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
      <li class="active">新建活动</li>
    </ol>
    
    <div class="row channel-add" style="padding:0px 20px;">
       
        
            <div class="panel panel-default clearfix">
              <div class="panel-heading">新建活动</div>
              <form id="activityForm" action="javascript:void(0);">
              <div class="panel-body col-md-8">
              		<input type="text" class="hide" value="1" name="activity.type" data-valid="[{rule:'not_empty'}]">
                  <div class="form-group">
                    <label for="activityName">活动名称</label>
                    <input type="text" class="form-control" id="activityName" name="activity.content" data-valid="[{rule:'not_empty'}]" placeholder="不能为空" maxlength="30">
                  </div>
                  <div class="form-group">
                    <label for="activityURL">活动URL</label>
                    <input type="text" class="form-control" id="activityURL" name="activity.url" data-valid="[{rule:'not_empty'}]" placeholder="不能为空" maxlength="120">
                  </div>
                  <div class="form-group">
                    <label for="startTime">开始时间</label>
                    <input type="datetime" name="startTime" id="startTime" data-valid="[{rule:'not_empty'}]" readonly class="form_datetime form-control clear">
                  </div>
                  <div class="form-group">
                    <label for="endTime">结束时间</label>
                    <input type="datetime" name="endTime" id="endTime" data-valid="[{rule:'not_empty'}]" readonly class="form_datetime form-control clear">
                  </div>
                  <div class="form-group">
                    <label for="detail">详情</label>
                    <textarea class="form-control" id="detail" rows="4" name="activity.remark" data-valid="[{rule:'not_empty'}]" placeholder="活动详情介绍"></textarea>
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
					url:'json/activity_addActivity',
					success:function(json){
						if(json.result == '00000000'){
							W.$.alert("新增活动成功！");
							$(".form-control").val("");
						}else{
							W.$.alert("新增活动失败！");
						}
					},
					error:function(){
						W.$.alert("新增活动失败！");
					}
				});
			}
		});
	});
	
});


$.extend({
	
});
</script>
</body>
</html>
