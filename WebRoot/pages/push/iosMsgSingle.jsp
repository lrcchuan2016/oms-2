<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
<body>
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">推送消息</a></li>
      <li class="active">用户列表</li>
    </ol>
    
    <div class="userListbox">
    	<ul class="nav nav-tabs">
          <li role="presentation"><a href="pages/push/iosMsg.jsp">iOS信鸽配置信息</a></li>
          <li role="presentation" class="active"><a href="pages/push/iosMsgSingle.jsp">推送消息给单个设备</a></li>
          <li role="presentation"><a href="pages/push/iosMsgMulti.jsp">推送消息给所有设备</a></li>
        </ul>
        <form class="retrieval" style="margin-top:20px;" action="javascript:void(null);" id="keyWordsForm">
        
            <div class="form-group pull-left" style="width:200px;">
                <div class="input-group">
                  <div class="input-group-addon">筛选</div>
                  <select class="form-control" name="option" id="selectUser">
                      <option value="2" >--所有用户--</option>
    				  <option value="1" >正常用户</option>
    				  <option value="0" >禁用用户</option>	
                    </select>
                </div>
             </div>
             
             <div class="form-group pull-left" style="width:250px;">
                <div class="input-group">
                  <div class="input-group-addon">关键字</div>
                  <input type="text" class="form-control keyWords" placeholder="手机号码/呢称" onfocus="this.value=''" data-valid="[{rule:'not_empty'}]">
                </div>
              </div>
              
              <button type="button" class="btn btn-primary btn-search">搜索</button>
              <button class="btn btn-primary pull-right" type="button">批量用户<span class="badge">0</span></button>
    	</form>
        
    	<table id="userTable" class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
    		<thead>
    			<tr>
    				<td>UserId</td>
    				<td>手机号</td>
    				<td>email</td>
    				<td>呢称</td>
    				<td>活跃家庭号</td>
    				<td>状态</td>
    				<td>操作</td>
    				
    			</tr>
    		</thead>
    		<tbody class="bg-white"></tbody>
    		<tfoot><tr><td colspan="7">&nbsp;</td></tr></tfoot>	
    	</table>  
        
	</div><!---userListbox-end-->
    
    </div>
    
    
<div id="pushMsgDialog" class="modal" tabindex="-1" style="display:none; background-color:rgba(0,0,0,0.4); overflow:hidden;">
    <div class="modal-dialog" style="width:600px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4>推送消息</h4>
            </div>
            <div class="modal-body">
                <form id="pushMsgForm" class="form-horizontal" action="javascript:void(null);">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">标题</label>
                        <div class="col-xs-10"><input id="title" class="form-control input-sm r-lg" type="text" name="paramMap['title']" maxlength="20" data-valid="[{rule:'not_empty'}]" placeholder="消息标题"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">内容</label>
                        <div class="col-xs-10"><textarea id="content" class="form-control" type="text" rows="5" name="paramMap['content']" maxlength="200" data-valid="[{rule:'not_empty'}]" placeholder="消息内容"></textarea></div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">发送时间</label>
                        <div class="col-xs-10"><input id="datetime" class="form-control input-sm r-lg" type="datetime-local" name="paramMap['sendTime']" maxlength="20" data-valid="[{rule:'not_empty'}]"></div>
                    </div>
                   <div class="form-group">
                        <label class="col-xs-2 control-label">操作密码</label>
                        <div class="col-xs-10"><input id="psw" class="form-control input-sm r-lg" type="password" name="paramMap['psw']" maxlength="20" data-valid="[{rule:'not_empty'}]" placeholder="操作员密码"></div>
                    </div>
                </form>
            </div>
            <div class="modal-footer text-center-xs">
                <button type="button" class="btn btn-default r-lg btn-sm" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span> 取&nbsp;消
                </button>
                <button type="button" class="btn btn-default r-lg btn-sm btn-sub">
                    <span class="glyphicon glyphicon-ok"></span> 确&nbsp;认
                </button>
            </div>
        </div>
    </div>
</div>    

<div id="pushBatchDialog" class="modal" tabindex="-1" style="display:none; background-color:rgba(0,0,0,0.4); overflow:hidden;">
    <div class="modal-dialog" style="width:600px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4>推送消息<span class="pull-right">共<strong></strong>个用户</span></h4>
            </div>
            <div class="model-body clearfix" style="max-height:200px; overflow-x:auto;">
	            <table class="table">
	            	<thead><tr><th>序号</th><th>手机号</th><th>昵称</th><th></th></tr></thead>
	            	<tbody>
	            		
	            	</tbody>
	            </table>
            </div>
            <div class="modal-footer">
                <form id="pushBatchMsgForm" class="form-horizontal" action="javascript:void(null);">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">标题</label>
                        <div class="col-xs-10"><input id="title" class="form-control input-sm r-lg" type="text" name="paramMap['title']" maxlength="20" data-valid="[{rule:'not_empty'}]" placeholder="消息标题"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">内容</label>
                        <div class="col-xs-10"><textarea id="content" class="form-control" type="text" rows="3" name="paramMap['content']" maxlength="200" data-valid="[{rule:'not_empty'}]" placeholder="消息内容"></textarea></div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">发送时间</label>
                        <div class="col-xs-10"><input id="datetime" class="form-control input-sm r-lg" type="datetime-local" name="paramMap['sendTime']" maxlength="20" data-valid="[{rule:'not_empty'}]"></div>
                    </div>
                   <div class="form-group">
                        <label class="col-xs-2 control-label">操作密码</label>
                        <div class="col-xs-10"><input id="psw" class="form-control input-sm r-lg" type="password" name="paramMap['psw']" maxlength="20" data-valid="[{rule:'not_empty'}]" placeholder="操作员密码"></div>
                    </div>
                </form>
            </div>
            <div class="modal-footer text-center-xs">
                <button type="button" class="btn btn-default r-lg btn-sm" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span> 取&nbsp;消
                </button>
                <button type="button" class="btn btn-default r-lg btn-sm btn-sub">
                    <span class="glyphicon glyphicon-ok"></span> 确&nbsp;认
                </button>
            </div>
        </div>
    </div>
</div>  
    
<script>

var W = window.top;
var userTablObj = null;
var keyWordsForm = null,pushMsgForm = null,mapObj = new HashMap(),selectMap = new HashMap();
var pushBatchMsgForm = null;
$(function(){
	keyWordsForm = $("#keyWordsForm").inputValid(valid_message_options);
	pushMsgForm = $("#pushMsgForm").inputValid(valid_message_options);
	pushBatchMsgForm = $("#pushBatchMsgForm").inputValid(valid_message_options);
	
	userTablObj = $("#userTable").tableList({
        url:"json/pushDevice_findUserPage",
        param:"paramMap['searchType']=2",
        pageSize:8,
        paging:true,
        finishCallBack:function(){},
        template:htmlTemplate
    });
	
	$("#pushMsgDialog").find(".btn-sm").eq(0).click(function(){
		$("#pushMsgDialog").hide();
	});
	
	$("#pushBatchDialog").find(".btn-sm").eq(0).click(function(){
		$("#pushBatchDialog").hide();
	});
	
	$(".table .btn-sm").livequery(function(){
		$(this).click(function(){
			var userId = $(this).parents("tr").attr("id");
			$.pushMessage(userId);
			
		});
	});
	
	//下拉重新列表
	$("#selectUser").livequery(function(){
		$(this).change(function(){
			var type = $(this).val();
			var keyWords = $(".keyWords").val();
			selectKeyWords(type,keyWords);
		});
	});
	
	//搜索关键字
	$(".btn-search").livequery(function(){
		$(this).click(function(){
			if(keyWordsForm.validate_all()){
				var type = $("#selectUser").val();
				var keyWords = $(".keyWords").val();
				selectKeyWords(type,keyWords);
			}
		});
	});
	
	//复选框选中事件
	$("tbody>tr>td>input").livequery(function(){
		$(this).click(function(){
			var badge = $(".badge");
			//选中与移除选中
			if($(this)[0].checked){
				badge.html(parseInt(badge.html())+1);
				var id = $(this).parents("tr").attr("id");
				selectMap.put(id, mapObj.get(id));
			}else{
				var num = parseInt(badge.html())-1;
				if(num<0) num=0;
				badge.html(num);
				selectMap.remove(id);
			}
		});
	});
	
	
	//查看批量用户事件
	$(".badge").parents("button").click(function(){
		var number = parseInt($(this).find(".badge").html());
		if(number <= 0){
			W.$.alert("请您先选择您要推送消息的用户");
		}else{
			$.pushBatchMessage();
		}
	});
		
	//移除事件
	$(".glyphicon-remove").livequery(function(){
		$(this).click(function(){
			var tr = $(this).parents("tr");
			selectMap.remove(tr.attr("id"));
			tr[0].remove();
			$("#userTable > tbody > tr").livequery(function(){
				$(this).each(function(){
					var obj = selectMap.get($(this).attr("id"));
					if(typeof(obj) == 'undefined') $(this).find("input[type='checkbox']").removeAttr("checked");
				});
			});
			var currentNum = parseInt($(".badge").html())-1;
			$(".badge").html(currentNum);
			$("#pushBatchDialog").find("strong").html(currentNum);
		});
	});
});



function htmlTemplate(index, data, recordIndex){
	mapObj.put(data.id, data);
	var html = '<tr id="'+data.id+'">';
	if(typeof(selectMap.get(data.id))!='undefined') html += '<td><input type="checkbox" checked>'+recordIndex+'</td>';
	else html += '<td><input type="checkbox">'+recordIndex+'</td>';
	html += '<td>'+data.account+'</td>';
	html += '<td>'+data.email+'</td>';
	html += '<td>'+data.nickname+'</td>';
	html += '<td>'+data.active_club_id+'</td>';
	if(data.status == '0'){
		html += '<td class="tStatus"><font color="#ff0000">禁用</font></td>';
	}else{
		html += '<td class="tStatus">正常</td>';
	}
	html += '<td><a class="btn btn-info btn-sm" href="javascript:void(0)">推送消息</a></td>';
	html += '</tr>';
	
	return html;
}

function selectKeyWords(_type,_keyWords){
	var param = "paramMap['searchType']="+_type;
	if(_keyWords != '0') param +="&paramMap['keyWords']="+_keyWords;
	userTablObj = $("#userTable").tableList({
        url:"json/pushDevice_findUserPage",
        param:param,
        pageSize:8,
        paging:true,
        finishCallBack:function(){},
        template:htmlTemplate
    });
    
}

function modelTable(recordIndex,data){
	var html = "";
	html +='<tr id="'+data.id+'">';
	html += '<td>'+recordIndex+'</td>';
	html += '<td>'+data.account+'</td>';
	html += '<td>'+data.nickname+'</td>';
	html += '<td><span style="cursor:pointer" class="glyphicon glyphicon-remove"></span></td>';
	html += '</tr>';
	return html;
}

$.extend({
	//更新某月的覆盖用户数量
	pushMessage : function(userId){
		$.showDialog(function(){
			if(pushMsgForm.validate_all()){
				var param = "paramMap['userIds']="+userId+"&"+$("#pushMsgForm").serialize();
				$.ajax({
					type:"POST",
					dataType:"json",
					data:param,
					url:'json/pushDevice_pushSingleDevice', //API
					success:function(json) {
						if(json.result == "00000000"){
							W.$.alert("发送成功！！");
							$("input").val("");
							$("textarea").html("");
						}else if(json.result == "00000002"){
							W.$.alert("密码错误！");
						}else{
							W.$.alert("发送消息失败！");
						}
					},
					error: function(data) {
						W.$.alert("发送失败！");
					},
					complete: function(){
						$("#pushMsgDialog").hide();
					}
				});
			}
		});
	},
	//弹出框
	showDialog : function(_fun){
		$("#pushMsgDialog input").val("");
		var winH = $(window).height();
		$("#pushMsgDialog .modal-content").css("margin-top",(winH-450)/2 + "px");
		$("#pushMsgDialog").show();
		
		$("#pushMsgDialog .btn-sub").unbind("click").click(function(){
			_fun();  //点击执行传进来的函数
		});
	},
	
	pushBatchMessage : function(){
		var keys = selectMap.keySet();
			var tbody = $("#pushBatchDialog").find("tbody");
			tbody.html("");
			var recordIndex = 1;
			for ( var i in keys) {
				var data = selectMap.get(keys[i]);
				var html = modelTable(recordIndex,data);
				tbody.append(html);
				recordIndex++;
			}
			$("#pushBatchDialog").find("strong").html(--recordIndex);
			$.showBatchDialog(function(){
				if(pushBatchMsgForm.validate_all()){
					keys = selectMap.keySet();
					if(keys.length>0){
					var param = "paramMap['userIds']="+keys.join(",")+"&"+$("#pushBatchMsgForm").serialize();
					$.ajax({
						type:'post',
						dataType:'json',
						data:param,
						url:'json/pushDevice_pushSingleDevice',
						success:function(json){
							if(json.result == '00000000'){
								W.$.alert("推送消息成功！");
							}else if(json.result == '00000002'){
								W.$.alert("密码错误！");
							}else{
								W.$.alert("发送失败！");
							}
						},
						error:function(e){
							W.$.alert("推送消息异常！");
						},
						complete:function(){
							$("#pushBatchDialog").hide();
						}			
					});	
					}else W.$.alert("请至少选择一个用户");
				}
			});
		
	},
	
	showBatchDialog : function(_fun){
		$("#pushBatchDialog input").val("");
		var winH = $(window).height();
		$("#pushBatchDialog .modal-content").css("margin-top",(winH-600)/2 + "px");
		$("#pushBatchDialog").show();
		
		$("#pushBatchDialog .btn-sub").unbind("click").click(function(){
				_fun();  //点击执行传进来的函数
		});
	},
});
</script>
</body>
</html>
