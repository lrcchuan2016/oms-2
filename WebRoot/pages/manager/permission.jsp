<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<link href="public/jstree/themes/default/style.min.css" rel="stylesheet">
<style>
<!--
.jstree-default.jstree-focused {background: #ffffff !important}
-->
</style>
</head>
<body>
	<div class="row padder-lx">
		<div class="col-xs-12 no-padder bg-light" style="position:fixed;z-index:99;">
			<h2 class="padder-lm">权限设置</h2>
			<div class="col-xs-12 no-padder bg-dark lt" style="height:30px;line-height:30px;">
				<span class="no-padder bg-dark lter" style="width:15px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
				管理组设置  | <span class="text-white">权限设置</span>  &nbsp;&nbsp;<a class="glyphicon glyphicon-plus-sign addBtn" href="javascript:void(0);"></a> &nbsp;添加
				<span></span>
			</div>
			<div class="col-xs-12 padder-tm bg-empty">
				<div id="successTip" class="alert text-black alert-success hide fade in"></div>
				<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
			</div>
		</div>
		
		<div class="col-xs-12 padder-tm bg-light" style="top:95px; min-height:465px;">
			<table id="roleTable" class="table table-bordered" >
				<tbody class="bg-white">
					<tr class="bg-light lter text-black">
						<td data-sort="int" style="width:60px;">ID</td>
						<td style="width:180px;">操作</td>
						<td>管理组名称</td>
						<td>使用状态</td>
					</tr>
				</tbody>
				<tfoot><tr><td colspan="4">&nbsp;</td></tr></tfoot>
			</table>
		</div>
		<div class="col-xs-12 padder-tm bg-light">
			<div class="row">&nbsp;</div>
		</div>
	</div>
	  
	<div id="roleDialog" class="modal" tabindex="-1">
		<div class="modal-dialog" style="width:450px">
			<div class="modal-content">
				<div class="modal-header">
					<h4 id="roleHeader">添加管理组</h4>
				</div>
				<div class="modal-body">
					<form id="roleForm" action="javascript:void(null);">
						<input type="hidden" name="roleList[0].id">
						<div class="form-group">
							<label>管理组</label>
							<input class="form-control input-sm r-lg" type="text" name="roleList[0].name" maxlength="20" data-valid="[{rule:'not_empty'},{rule:'regex',value:'^[\u4e00-\u9fa5a-zA-Z0-9]+[\u4e00-\u9fa5a-zA-Z0-9-_]*$',message:'输入的内容不合法!'}]" 
							 placeholder="只能是中英文字符、数字、横杠、下划线">
						</div>
						<div class="form-group">
							<label>权限</label>
							<input class="form-control input-sm r-lg" name="roleList[0].permission" type="text" readonly>
						</div>
						<div class="form-group">
							<div id="permissionDiv"></div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default btn-sm r-lg" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove"></span> 关  闭
					</button>
					<button class="btn btn-default btn-sm r-lg btn-sub">
						<span class="glyphicon glyphicon-ok"></span> 提  交
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
					<button class="btn btn-default btn-cancle btn-sm r-lg" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove"></span> 取消
					</button>
					<button class="btn btn-default btn-confirm btn-sm r-lg">
						<span class="glyphicon glyphicon-ok"></span> 确认
					</button>
				</div>
			</div>
		</div>
	</div>
	
</body>
<script src="public/jstree/jstree.min.js"></script>
<script type="text/javascript">
var formObj = null,actionObj = null,mapObj = new HashMap(),actionStatus=0,permissionList = new Array(),actionId = -1;
$(function(){
	formObj = $("#roleForm").inputValid(valid_message_options);
	actionObj = new Actions({ successCallBack:initRoles });
	alertObj = new Alert({
        checkTable:"#roleTable",
        needSelected:false,
        confirmTip:'删除管理组后，管理组下的管理员也会被级联删除，您确认删除当前管理组吗？',
        confirmAction:delAction
    });
	initRoles();
	$.post("json/managerRole_findPermissionList", function(data) {
        permissionList = data.list;
        $("#permissionDiv").bind("changed.jstree", function() {
            var checkedIdList = new Array();
            $.each(jstreeObj.get_checked(true), function() {
        		checkedIdList.push(this.id);
            });
            var j = 0;
            for ( var i in checkedIdList) {
            	if(checkedIdList[i]%1000 == 0){
            		j = checkedIdList[i];
            	}else{
            		console.info(checkedIdList[i]);
            		var a = Math.floor(checkedIdList[i]/1000)*1000;
            		if(j != a){
            			checkedIdList.splice(i,0,a+'');
            			j = a;
            		}
            	}
			}
			checkedIdList.shift();
			sort(checkedIdList);
            $("input[name='roleList[0].permission']").val(checkedIdList.join("/"));
        });
    });
	$(".addBtn").click(function(){
		actionObj.setOptions({
            url:"json/managerRole_add"
        });
        $("#roleHeader").html('添加管理组');
        $("#roleDialog input").val("");
        $("#roleDialog input").popover("destroy");
        $("#roleDialog .form-group").removeClass("has-error");
        $("#roleDialog").modal("show");
        jstreeObj = $.jstree.create("#permissionDiv", {
            plugins:["checkbox"],
            core:{
                data:permissionList
            }
        });
	});
	$("#roleDialog").find(".modal-footer .btn-sub").click(function(){
		if(formObj.validate_all()){
        	actionObj.submit({ param:$("#roleForm").serializeArray() });
			$("#roleDialog").modal("hide");
		}
	});
});

function initRoles(){
	$.post("json/manager_findAllRole",function(data){
		if(data!=null&&data.list!=null){
			$("#roleTable").find("tbody tr").each(function(index){
				if(index!=0) $(this).remove();
			});
			var html = "";
			$.each(data.list,function(index){
				if(mapObj.get("d_"+this.id)!=null) mapObj.remove("d_"+this.id);
				mapObj.put("d_"+this.id,this);
				html += '<tr><td><span class="checkId">'+this.id+'</span></td>';
				var editstr = "";
				if(this.id==1){
					html += '<td><div class="btn-group"><button class="btn btn-danger btn-xs del" disabled index="'+this.id+'"><span class="glyphicon glyphicon-minus-sign"></span> 删除</button>';
					editstr = '<a href="javascript:void(0);" title="编辑" class="glyphicon glyphicon-cog pull-right m-t-xxs th-sortable"></a>'; 
				}else{
					html += '<td><div class="btn-group"><button class="btn btn-danger btn-xs del" index="'+this.id+'"><span class="glyphicon glyphicon-minus-sign"></span> 删除</button>';
					editstr = '<a href="javascript:setStatus(\''+this.id+'\');" title="编辑" class="glyphicon glyphicon-cog pull-right m-t-xxs th-sortable"></a>'; 
				}
				var status = this.status==0?"正常":"不可用！",statusClass = "";
				if(this.status==0) statusClass="text-success";
				else if(this.status==1) statusClass = "text-warning";
				else statusClass = "text-danger";	
				html += '<button class="btn btn-warning btn-xs edit" index="'+this.id+'"><span class="glyphicon glyphicon-edit"></span> 编辑权限</button></div></td>'
					 +  '<td><a class="edit" index="'+this.id+'" href="javascript:void(0);">'+this.name+'</a></td>'
					 +  '<td class="'+statusClass+'">'+status+editstr+'</td></tr>';
			});
			$("#roleTable").find("tbody tr").after(html);
			$(".edit").unbind("click").bind("click",function(){
				
				var index = $(this).attr("index");
				$("#roleDialog").modal("show");
				actionObj.setOptions({
		            url:"json/managerRole_edit"
		        });
		        var roleObj = mapObj.get("d_"+index);
		        var arr = roleObj.permission.split("/");
		        sort(arr);   //排序
		        var permissionStr = arr.join("/");
		        $("#roleHeader").html('编辑管理组信息');
		        $("input[name='roleList[0].id']").val(roleObj.id);
		        $("input[name='roleList[0].name']").val(htmlDecode(roleObj.name));
		        $("input[name='roleList[0].permission']").val(permissionStr);
		        $("#roleDialog input").popover("destroy");
		        $("#roleDialog .form-group").removeClass("has-error");
		        $("#roleDialog").modal("show");
		        jstreeObj = $.jstree.create("#permissionDiv", {
		            plugins:["checkbox"],
		            core:{
		                data:permissionList
		            }
		        });
		        $("#permissionDiv").bind("loaded.jstree", function() {
		            jstreeObj.open_all();
		            var permissionArray = roleObj.permission.split("/");
		            sort(permissionArray);
		           
		            $("li").each(function() {
		                var self = this;
		                $.each(permissionArray, function() {
		                	//排除父节点
		            		if ($(self).attr("id") == this && this.toString()%1000!=0) {
		            			jstreeObj.check_node($(self));
		            		}
		                });
		            });
		            jstreeObj.close_all();
		            $("#permissionDiv").unbind("loaded.jstree");
		        });
		        
			});
			$(".del").unbind("click").bind("click",function() {
				actionId = $(this).attr("index");
		        alertObj.cancel();
		        alertObj.check();
		    });
		}
	});
}

function delAction($checkBoxs) {
    var param = "&roleList[0].id=" + actionId;
    actionObj.submit({
        url:"json/managerRole_del",
        param:param
    });
}

function setStatus(__id){
	var param = "&roleList[0].id=" + __id;
    actionObj.submit({
        url:"json/managerRole_updateStatus",
        param:param
    });
	
}

//数组去重复元素
Array.prototype.unique = function(){
 var res = [];
 var json = {};
 for(var i = 0; i < this.length; i++){
  if(!json[this[i]]){
   res.push(this[i]);
   json[this[i]] = 1;
  }
 }
 return res;
}

/*
*数组排序
*/
function sort(arr){	
	arr.sort(function(v1,v2){
		v1 = parseInt(v1);
		v2 = parseInt(v2);
		if(v1<v2){return -1;}
		else if(v1>v2){return 1;}
		else{return 0;}
	})
}
</script>
</html>