<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<body>
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">推送消息</a></li>
    </ol>
    
    <div class="userListbox">
    	<ul class="nav nav-tabs">
          <li role="presentation"><a href="pages/push/iosMsg.jsp">iOS信鸽配置信息</a></li>
          <li role="presentation"><a href="pages/push/iosMsgSingle.jsp">推送消息给单个设备</a></li>
          <li role="presentation" class="active"><a href="pages/push/iosMsgMulti.jsp">推送消息给所有设备</a></li>
        </ul>
    	
        <form id="msgForm" style="max-width:500px; margin-top:30px;" action="javascript:void(null);">
           <div class="form-group">
            <label for="title">标题</label>
            <input type="text" class="form-control" id="title" placeholder="消息标题" name="paramMap['title']" maxlength="30" data-valid="[{rule:'not_empty'}]">
          </div>
          <div class="form-group">
            <label for="content">消息内容</label>
            <textarea type="text" class="form-control" rows="5" id="content" placeholder="请输入您要发送的内容（不超过200）" name="paramMap['content']" data-valid="[{rule:'not_empty'}]" maxlength="200"></textarea>
          </div>
           <div class="form-group">
            <label for="datetime">发送时间</label>
            <input type="datetime-local" class="form-control" id="datetime" name="paramMap['sendTime']" maxlength="20" data-valid="[{rule:'not_empty'}]">
          </div>
          <div class="form-group">
            <label for="psw">操作密码</label>
            <input type="password" class="form-control" id="psw" placeholder="操作员密码" name="paramMap['psw']" maxlength="20" data-valid="[{rule:'not_empty'}]">
          </div>
          
          <button type="button" class="btn btn-info btn-lg btn-push">发送给所有用户</button>
        </form>
        
	</div><!---userListbox-end-->
    
    </div>
    
    
    
<script>

var W = window.top;
var formObj = null;
/*=============================JSP获取===============================*/

$(function(){
	formObj = $("#msgForm").inputValid(valid_message_options);
	
	//绑定事件
	initBind();
});

function initBind(){
	$(".btn-push").livequery(function(){
		$(this).click(function(){
			if(formObj.validate_all()){
				var param = $("#msgForm").serialize();
				$.pushMessage(param);
			}
		});
	});
}

$.extend({

	pushMessage : function(param){
		$.ajax({
			type:"POST",
			dataType:"json",
			data:param,
			url:'json/pushDevice_pushAllDevice', //API
			success:function(json) {
				if(json.result == "00000000"){
					W.$.alert("发送成功！！");
					$("input").val();
					$("textarea").html("");
				}else if(json.result == "00000001"){
					W.$.alert("操作员密码错误！");
					$("#psw").focus();
				}else{
					W.$.alert("发送失败！");
				}
			},
			error: function(data) {
				W.$.alert("发送失败！");
			}
			
		});
		
	},
	
});
</script>
</body>
</html>
