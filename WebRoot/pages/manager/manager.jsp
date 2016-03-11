<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body>
	<div class="row padder-lx">
		<div class="col-xs-12 no-padder bg-light" style="position:fixed;z-index:99;">
			<h2 class="padder-lm">管理员列表</h2>
			<div class="col-xs-12 no-padder bg-dark lt" style="height:30px;line-height:30px;">
				<span class="no-padder bg-dark lter" style="width:15px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
				管理员列表
				<span></span>
			</div>
			<div class="col-xs-12 padder-tm bg-empty" >
				<div id="successTip" class="alert text-black alert-success hide fade in"></div>
				<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
			</div>
		</div>
		<div class="col-xs-12 padder-tm bg-light" style="top:95px; min-height:465px;">
			<table id="sysTable" class="table table-bordered" >
				<thead>
					<tr><th colspan="5"><span class="pull-left">系统管理员列表</span></th></tr>
				</thead>
				<tbody class="bg-white">
					<tr class="font-bold">
						<td style="width:150px;">管理组</td>
						<td style="width:135px;">姓名</td>
						<td>部门</td>
						<td style="width:380px;">账号[关联邮箱]</td>
						<td>使用状态</td>
					</tr>
				</tbody>
			</table>
			<table id="operateTable" class="table table-bordered" >
				<thead>
					<tr><th colspan="5"><span class="pull-left">运营管理员列表</span></th></tr>
				</thead>
				<tbody class="bg-white">
					<tr class="font-bold">
						<td style="width:150px;">管理组</td>
						<td style="width:135px;">姓名</td>
						<td>部门</td>
						<td style="width:380px;">账号[关联邮箱]</td>
						<td>使用状态</td>
					</tr>
				</tbody>
			</table>
			<div class="row">&nbsp;&nbsp;</div>
		</div>
	</div>
	  
	<div id="managerDialog" class="modal" tabindex="-1">
		<div class="modal-dialog" style="width:500px;">
			<div class="modal-content">
				<div class="modal-header">
					<h4 id="managerDialogHeader">添加管理员</h4>
				</div>
				<div class="modal-body">
					<form id="managerForm" class="form-horizontal" action="javascript:void(null);">
						<input class="hide" name="managerRoleList[0].id">
						<div class="form-group">
							<label class="col-xs-3 control-label">姓名</label>
							<div class="col-xs-8"><input class="form-control input-sm r-lg" type="text" name="managerList[0].name" maxlength="20" data-valid="[{rule:'not_empty'}]" placeholder="真实姓名"></div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">所属部门</label>
							<div class="col-xs-8"><select name="managerList[0].departmentId" class="form-control input-sm r-lg"></select></div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">管理组</label>
							<div class="col-xs-8"><input class="form-control input-sm r-lg" disabled type="text" name="managerList[0].roleName" maxlength="20" placeholder="管理组"></div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">账号</label>
							<div class="col-xs-8"><input class="form-control input-sm r-lg" type="text" name="managerList[0].account" maxlength="20" data-valid="[{rule:'not_empty'},{rule:'id'}]" placeholder="只能由数字、字母、下划线组成"></div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">初始密码</label>
							<div class="col-xs-8"><input class="form-control input-sm r-lg" type="password" name="managerList[0].password" data-valid="[{rule:'not_empty'},{rule:'id'}]" maxlength="16" placeholder="只能由数字、字母、下划线组成"></div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">密码期限</label>
							<div class="col-xs-8">
								<select name="managerList[0].passwordLimitUtc" class="form-control input-sm r-lg">
									<option value=86400000>有效期：24小时</option>
									<option value=2592000000>有效期：30天</option>
									<option value=31536000000>有效期：一年</option>
									<option value=-1>有效期：永久</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">关联邮箱</label>
							<div class="col-xs-8"><input class="form-control input-sm r-lg" type="text" name="managerList[0].email" maxlength="50" data-valid="[{rule:'email'}]" placeholder="关联后，可利用邮箱进行登录"></div>
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

	<div id="alertDialog" class="modal" tabindex="-1">
		<div class="modal-dialog" style="width:300px;">
			<div class="modal-content">
				<div class="modal-header"><h4></h4></div>
				<div class="modal-body"></div>
				<div class="modal-footer" style="text-align: center;">
					<button class="btn btn-default">
						<span class="glyphicon glyphicon-remove"></span> 取消
					</button>
					<button class="btn btn-primary">
						<span class="glyphicon glyphicon-ok"></span> 确认
					</button>
				</div>
			</div>
		</div>
	</div>
	
</body>
<script src="public/js/jquery.ui.widget.js"></script>
<script type="text/javascript">
var mapObj = new HashMap(),formObj = null,actionObj = null,managerArray = new Array(),departs = new Array(),adminAccount="${admin.account}";
$(function(){
	formObj = $("#managerForm").inputValid(valid_message_options);
	actionObj = new Actions({ successCallBack:function() {allRoleRequest();} });
	initDepartment();
	$("#managerDialog").find(".modal-footer .btn-sub").click(function(){
		if(formObj.validate_all()){
        	actionObj.submit({ param:$("#managerForm").serializeArray() });
			$("#managerDialog").modal("hide");
		}
	});
});

function initDepartment(){
	$.post("json/department_findDeparts",function(data){
		if(data!=null&&data.list!=null){
			departs = data.list;
			var html = "";
			$.each(departs,function(i,value){
				//alert(value.id+","+value.name);
				mapObj.put("depart_"+value.id, value.name);
				html += "<option value="+value.id+">"+value.name+"</option>";
			});
			$("select[name='managerList[0].departmentId']").html(html);
			allRoleRequest();
		}
	});
}

function allRoleRequest(){
	$.post("json/manager_findAllRole",function(data){
		managerArray = null;
		managerArray = new Array();
		$("#sysTable").find("tbody tr").each(function(index){
			if(index!=0) $(this).remove();
		});
		$("#operateTable").find("tbody tr").each(function(index){
			if(index!=0) $(this).remove();
		});
		if(data!=null){
			var ophtml = "";
			$.each(data.list,function(jndex){
				var managerList = this.managerList;
				var rowspan = 0,$roleName = this.name;
				mapObj.put(this.id, this.name);
				if(managerList!=null&&managerList.length!=0) rowspan = managerList.length;
				if(this.id==1){		//系统管理员
					var syshtml = managerHtmlTemplate(managerList,$roleName,rowspan,this.id);
					$("#sysTable").find("tbody tr").after(syshtml);
				}else{
					var opsubhtml = managerHtmlTemplate(managerList,$roleName,rowspan,this.id);
					ophtml += opsubhtml;
				}
			});
			$("#operateTable").find("tbody tr").after(ophtml);
		}
	});
}

function managerHtmlTemplate(__managerList,roleName,rowspan,roleId){
	var manager_html = "";
	if(__managerList!=null&&__managerList.length!=0){
		//console.info(mapObj.keySet());
		//console.log(mapObj.get('depart_1')+","+mapObj.get('depart_2')+","+mapObj.get('depart_3')+","+mapObj.get('depart_4')+","+mapObj.get('depart_5'));
		$.each(__managerList,function(index){
			managerArray.push(this);
			if(index==0) {
				manager_html += '<tr><td rowspan="'+rowspan+'">'+roleName+' <a href="javascript:addAccount('+roleId+');" title="添加" role="'+roleName+'" class="glyphicon glyphicon-plus-sign pull-right m-t-xxs th-sortable"></a></td>';
			}else manager_html += '<tr>';
			var status = this.status==0?"正常":((this.status==1&&this.status!=3)?"冻结中！":"不可用！");
			//alert(this.departmentId);
			department = mapObj.get("depart_"+this.departmentId);
			if(department == undefined){
				initDepartment();
				department = mapObj.get("depart_"+this.departmentId);
			}
			var statusClass = "";
			if(this.status==0) statusClass="text-success";
			else if(this.status==1) statusClass = "text-warning";
			else statusClass = "text-danger";
			var editstr = '<a href="javascript:setStatus(\''+this.id+'\');" title="编辑" class="glyphicon glyphicon-cog pull-right m-t-xxs th-sortable"></a>';
			if(this.account=="admin") editstr = "";
			if(this.account!="admin"&&this.account==adminAccount) editstr = ""; 
			manager_html += '<td>'+this.name+'</td><td>'+department+'</td><td>'+this.account+'</td>'+
					   '<td><span class="pull-left '+statusClass+'">'+status+'</span> '+editstr+'</td></tr>';
		});
	}else{
		manager_html += '<tr><td rowspan="'+rowspan+'">'+roleName+' <a href="javascript:addAccount('+roleId+');" title="添加" role="'+roleName+'" class="glyphicon glyphicon-plus-sign pull-right m-t-xxs th-sortable"></a></td>'+
					 	'<td></td><td></td><td></td><td></td></tr>';
	}
	return manager_html;
}

function addAccount(__roleId){
	actionObj.setOptions({url:"json/manager_addManager"});
	$("#managerDialog").find("input[name='managerList[0].roleName']").val(mapObj.get(__roleId));
	$("#managerDialog").find("input[name='managerRoleList[0].id']").val(__roleId);
	$("#managerDialog").modal("show");
}

function setStatus(__id){
	window.location.href = "managerAction_initEditPage?paramMap['type']=0&paramMap['id']="+__id;
}

</script>
</html>