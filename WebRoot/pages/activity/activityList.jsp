  <%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
table{
table-layout:fixed;/* 只有定义了表格的布局算法为fixed，下面td的定义才能起作用。 */
}
td{
width:100%;
word-break:keep-all;/* 不换行 */
white-space:nowrap;/* 不换行 */
overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */
text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
}
</style>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">
	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">活动管理</a></li>
      <li class="active">活动列表</li>
    </ol>
    <div class="userListbox channel-list" style="margin-right:20px;">
        <div style="margin-top: 15px;">
    	<form class="retrieval" id="searchForm" action="javascript:void(null);">
    		<div class="form-group pull-left" style="width:350px;">
	    		<div class="input-group">
	    			<div class="input-group-addon">关键字</div>
                  	<input class="form-control keyWords" name="paramMap['keyWords']" data-valid="[{rule:'not_empty'}]" type="text" maxlength="100" placeholder="活动名称">
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
					<td style="width:140px;">ID</td>
					<td style="width:120px;">活动名称</td>
					<td style="width:140px;" class="url">跳转url</td>
					<td style="width:140px;">开始时间</td>
					<td style="width:140px;">结束时间</td>
					<td style="width:160px;">操作</td>
   				</tr>
   			</thead>
			<tbody class="bg-white"></tbody>
			<tfoot><tr><td colspan="6">&nbsp;</td></tr></tfoot>
		</table>
	</div>
</div>

	<div id="alertDialog" class="modal" tabindex="-1">
		<div class="modal-dialog" style="width:300px;">
			<div class="modal-content">
				<div class="modal-header"><h4></h4></div>
				<div class="modal-body"></div>
				<div class="modal-footer" style="text-align: center;">
					<button class="btn btn-default btn-sm r-lg btn-cancel">
						<span class="glyphicon glyphicon-remove "></span> 取消
					</button>
					<button class="btn btn-primary btn-sm r-lg btn-confirm">
						<span class="glyphicon glyphicon-ok"></span> 确认
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var W = window.top;
var actionObj = null,activityTableObj = null,mapObj = new HashMap();
$(function(){
	formObj = $("#searchForm").inputValid(valid_message_options);
	
	activityTableObj = $("#activityTable").tableList({
        url:"json/activity_activityList",
        param:"paramMap['keyWords']="+$(".keyWords").val(),
        pageSize:12,
        paging:true,
        finishCallBack:initActivity,
        template:htmlTemplate
    });
    
	actionObj = new Actions({ 
		successCallBack:function() { activityTableObj.refresh(); } 
	});
	alertObj = new Alert({});
	$("#searchBtn").click(function(){
		if(formObj.validate_all()){
			activityTableObj.setOptions({
	            param:$("#searchForm").serialize(),
	            url:'json/activity_activityList'
	        });
		}
	});
});

function htmlTemplate(index, data, recordIndex){
	mapObj.put("t_"+data.id,data);
	var html = "<tr id="+data.id+">";
	html += '<td><input style="border:0px;" value="'+data.id+'"></td>';
	html += '<td class="content">'+data.content+'</td>';
	html += '<td class="url"><a href="'+data.url+'" ><span class="text-info">'+data.url+'</span></a></td>';
	html += '<td>'+formatUTC(data.start_utc,"yyyy-MM-dd hh:mm")+'</td>';
	html += '<td>'+formatUTC(data.end_utc,"yyyy-MM-dd hh:mm")+'</td>';
	html += '<td>';
	html +=	'<button class="btn btn-success btn-xs search"><span class="glyphicon glyphicon-zoom-in">查看</span></button>';
	html += '<button class="btn btn-warning btn-xs edit"><span class="glyphicon glyphicon-edit">编辑</span></button>';
	html += '<button class="btn btn-danger btn-xs del"><span class="glyphicon glyphicon-minus-sign">删除</span></button>';
	html += '</td></tr>';
	
	return html;
}

function initActivity(){
	$(".edit").unbind("click").bind("click",function(){
		var $editParam = "paramMap['id']="+$(this).parents("tr").attr("id")
		+"&paramMap['param']=edit";
		window.location.href = "activityAction_initActivityPage?"+$editParam;
	});
	
	$(".search").unbind("click").bind("click",function(){
		var $editParam = "paramMap['id']="+$(this).parents("tr").attr("id");
		
		var content = $(this).parent("td").siblings(".content").html();
		console.info(content);
		if(content == "做相册赢好礼"){
			$editParam += "&paramMap['param']=searchActorPhoto";
		}else if(content == "幸运大转盘"){
			$editParam += "&paramMap['param']=searchTurntable";
		}
		
		window.location.href = "activityAction_initActivityPage?"+$editParam;
	});
	
	$(".del").unbind("click").bind("click",function(){
		var id = $(this).parents("tr").attr("id");
		var $param = "paramMap['id']="+id;
		alertObj.cancel();
		alertObj.check({
            needConfirm:true,
            confirmTip:"您确认删除本活动吗？",
            confirmParam:$param,
            confirmAction:delAction
        });
	});
}

function delAction($param){
	actionObj.setOptions({ url:"json/activity_activityDel" });
	actionObj.submit({param:$param});
}


$.extend({
	
});
</script>
</html>