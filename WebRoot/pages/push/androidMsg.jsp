<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
<body>
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">推送消息</a></li>
      <li class="active">android</li>
    </ol>
    
    <div class="userListbox">
    	<ul class="nav nav-tabs">
          <li role="presentation" class="active"><a href="pages/push/androidMsg.jsp">android信鸽配置信息</a></li>
          <li role="presentation"><a href="pages/push/androidMsgSingle.jsp">推送消息给单个设备</a></li>
          <li role="presentation"><a href="pages/push/androidMsgMulti.jsp">推送消息给所有设备</a></li>
        </ul>
    	
        <form  id="configForm" style="max-width:500px; margin-top:30px;" action="JavaScript:void(0);" >
          	<input type="hidden" value="1" name="type" >
          <div class="form-group">
            <label for="accessid">ACCESS ID</label>
            <input type="text" class="form-control" id="accessid" placeholder="ACCESS ID" name="accessId" data-valid="[{rule:'not_empty'},{rule:'integer',message:'access id为数字'}]">
          </div>
          
          <div class="form-group">
            <label for="accesskey">ACCESS KEY</label>
            <input type="text" class="form-control" id="accesskey" placeholder="ACCESS KEY" name="accessKey" data-valid="[{rule:'not_empty'}]">
          </div>
          
          
          <div class="form-group">
            <label for="secretkey">SECRET KEY</label>
            <input type="text" class="form-control" id="secretkey" placeholder="SECRET KEY" name="secretKey" data-valid="[{rule:'not_empty'}]">
          </div>
          
          
          <div class="form-group">
            <label for="mta app key">MTA APP KEY</label>
            <input type="text" class="form-control" id="MtaAppKey" placeholder="MTA APP KEY" name="MtaAppKey" >
          </div>
          
          <button type="button" class="btn btn-info btn-lg btn-save">保存</button>
        </form>
        
        
	</div><!---userListbox-end-->
    
    </div>
<script>
var W = window.top;
var formObj = null;
$(function(){
	//验证表单
	formObj = $("#configForm").inputValid(valid_message_options);
	//绑定点击事件
	initBind();
});

function initBind(){
	$(".btn-save").livequery(function(){
		$(this).click(function(){
			if(formObj.validate_all()){
				var param = $("#configForm").serialize();
				$.ajax({
					type:'post',
					dataType:'json',
					data:param,
					url:"json/pushDevice_saveXingeConfig",
					success:function(json){
						if(json.result == '00000000'){
							W.$.alert("保存成功！");
							$("input[type='text']").val("");
						}else{
							W.$.alert("保存失败！");
						}
					},
					error:function(e){
						W.$.alert("保存失败！");
					}
				});
			}
		});
	});
}
</script>
</body>
</html>
