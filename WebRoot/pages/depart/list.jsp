<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body>
	<div class="row padder-lx">
		<div class="col-xs-12 no-padder bg-light" style="position:fixed;z-index:99;">
			<h2 class="padder-lm">部门列表</h2>
			<div class="col-xs-12 no-padder bg-dark lt" style="height:30px;line-height:30px;">
				<span class="no-padder bg-dark lter" style="width:15px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
				部门列表  | &nbsp;&nbsp;<a class="glyphicon glyphicon-plus-sign addBtn" href="javascript:void(0);"></a> &nbsp;&nbsp;添加
				<span></span>
			</div>
			<div class="col-xs-12 padder-tm bg-empty">
				<div id="successTip" class="alert text-black alert-success hide fade in"></div>
				<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
			</div>
		</div>
		
		<div class="col-xs-12 padder-tm bg-light" style="top:95px; min-height:465px;">
			<table id="departTable" class="table table-bordered" >
				<thead>
					<tr class="">
						<td data-sort="int" style="width:60px;">ID</td>
						<td style="width:180px;">操作</td>
						<td>部门名称</td>
					</tr>
				</thead>
				<tbody class="bg-white">
				</tbody>
				<tfoot><tr><td colspan="3">&nbsp;</td></tr></tfoot>
			</table>
		</div>
		<div class="col-xs-12 padder-tm bg-light">
			<div class="row">&nbsp;</div>
		</div>
	</div>
	  
	<div id="departDialog" class="modal" tabindex="-1">
		<div class="modal-dialog" style="width:400px;">
			<div class="modal-content">
				<div class="modal-header">
					<h4 id="managerDialogHeader">添加部门</h4>
				</div>
				<div class="modal-body">
					<form id="departForm" class="form-horizontal" action="javascript:void(null);">
						<input class="hide" name="departList[0].id">
						<div class="form-group">
							<label class="col-xs-3 control-label">部门名称</label>
							<div class="col-xs-8"><input class="form-control input-sm r-lg" type="text" name="departList[0].name" maxlength="20" data-valid="[{rule:'not_empty'}]" placeholder="部门名称"></div>
						</div>
					</form>
				</div>
				<div class="modal-footer text-center-xs">
					<button type="button" class="btn btn-default r-lg btn-sm" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove"></span> 取&nbsp;消
					</button>
					<button type="button" class="btn btn-default r-lg btn-sm btn-sub">
						<span class="glyphicon glyphicon-ok"></span> 提&nbsp;交
					</button>
				</div>
			</div>
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
<script src="public/js/jquery.ui.widget.js"></script>
<script type="text/javascript">
var formObj = null,actionObj = null, departTableObj = null,mapObj = new HashMap(),actionStatus=0;
$(function(){
	departTableObj = $("#departTable").tableList({
        url:"json/department_findDeparts",
        paging:false,
        finishCallBack:initDeparts,
        template:htmlTemplate
    });
	formObj = $("#departForm").inputValid(valid_message_options);
	actionObj = new Actions({ 
		successCallBack:function() { departTableObj.refresh();} 
	});
	alertObj = new Alert({});
	$(".addBtn").click(function(){
		actionStatus = 0;
		$("#departDialog").modal("show");
	});
	$("#departDialog").find(".modal-footer .btn-sub").click(function(){
		if(actionStatus==0){
			actionObj.setOptions({
				url:"json/department_add"
			});
		}else{
			actionObj.setOptions({
				url:"json/department_edit"
			});
		}
		if(formObj.validate_all()){
        	actionObj.submit({ param:$("#departForm").serializeArray() });
			$("#departDialog").modal("hide");
		}
	});
});
	     
function htmlTemplate(index, data, recordIndex){
	if(mapObj.get("d_"+data.id)==null) mapObj.put("d_"+data.id,data);
	var html = "<tr>";
	html += '<td>'+data.id+'</td>';
	if(data.id==1){
		html += '<td><div class="btn-group"><button class="btn btn-danger btn-xs del" disabled index="'+data.id+'"><span class="glyphicon glyphicon-minus-sign"></span> 删除</button><button class="btn btn-warning btn-xs edit" disabled index="'+data.id+'"><span class="glyphicon glyphicon-edit"></span> 编辑</button></div></td>'
	}else{
		html += '<td><div class="btn-group"><button class="btn btn-danger btn-xs del" index="'+data.id+'"><span class="glyphicon glyphicon-minus-sign"></span> 删除</button><button class="btn btn-warning btn-xs edit" index="'+data.id+'"><span class="glyphicon glyphicon-edit"></span> 编辑</button></div></td>'
	}
	html +=  '<td><a class="edit" index="'+data.id+'" href="javascript:void(0);">'+data.name+'</a></td>';
	html += "</tr>";
	
	return html;
}


function initDeparts(){
	$(".edit").unbind("click").bind("click",function(){
		actionStatus = 1;
		var index = $(this).attr("index");
		$("input[name='departList[0].id']").val(mapObj.get("d_"+index).id);
		$("input[name='departList[0].name']").val(mapObj.get("d_"+index).name);
		$("#departDialog").modal("show");
	});
	$(".del").unbind("click").bind("click",function(){
		var index = $(this).attr("index");
		var $param = "departList[0].id="+mapObj.get("d_"+index).id;
		alertObj.cancel();
		alertObj.check({
            needConfirm:true,
            confirmTip:"您确认删除当前部门吗？",
            confirmParam:$param,
            confirmAction:delAction
        });
	});
}

function delAction($param){
	actionObj.setOptions({ url:"json/department_del" });
	actionObj.submit({ param:$param });
}

</script>
</html>