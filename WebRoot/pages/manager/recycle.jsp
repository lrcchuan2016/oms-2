<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body>
	<div class="row padder-lx">
		<div class="col-xs-12 no-padder bg-light" style="position:fixed;z-index:99;">
			<h2 class="padder-lm">回收站</h2>
			<div class="col-xs-12 no-padder bg-dark lt" style="height:30px;line-height:30px;">
				<span class="no-padder bg-dark lter" style="width:15px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<a href="managerPages_permission">管理组设置</a>  &nbsp;|&nbsp; <span class="text-white">回收站</span>
				<span></span>
			</div>
			<div class="col-xs-12 padder-tm bg-empty">
				<div id="successTip" class="alert text-black alert-success hide fade in"></div>
				<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
			</div>
		</div>
		<div class="col-xs-12 padder-tm bg-light" style="top:95px; min-height:465px;">
			<table id="sysTable" class="table table-bordered bg-white" style="min-heigth:400px;">
				<tbody>
					<tr class="bg-light lter">
						<td colspan="6">
							<span class="pull-left">
								<a class="text-white bg-info type2" href="javascript:findByType(2);">&nbsp;显示全部&nbsp;</a>    |
								<a class="text-white type0" href="javascript:findByType(0);">&nbsp;<span class="glyphicon glyphicon-user"></span> &nbsp;已删除的用户&nbsp;</a>   &nbsp;|&nbsp;
								<a class="text-white type1" href="javascript:findByType(1);">&nbsp;<span class="glyphicon glyphicon-book"></span> &nbsp;已删除的用户组&nbsp;</a>
							</span>
						</td>
					</tr>
					<tr class="font-bold table-head">
						<td>类型</td>
						<td>名称</td>
						<td>所属</td>
						<td>删除日期</td>
						<td>操作者</td>
						<td></td>
					</tr>
				</tbody>
				<tfoot ><tr><td colspan="6">&nbsp;</td></tr></tfoot>
			</table>
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
var mapObj = new HashMap(),formObj = null,actionObj = null,globalType = 2;
$(function(){
	actionObj = new Actions({ successCallBack:initRecycleList });
	initRecycleList();
	$("#managerDialog").find(".modal-footer .btn-sub").click(function(){
		if(formObj.validate_all()){
        	actionObj.submit({ 
        		param:$("#managerForm").serializeArray() 
        	});
			$("#managerDialog").modal("hide");
		}
	});
});
	
function initRecycleList(){
	var rType = globalType;
	$.post("json/recycle_findRecycleList","recycleType="+rType,function(data){
		if(data!=null && data.list!=null){
			$("#sysTable").find("tbody tr").each(function(index){
				if(index>1) $(this).remove();
			});
			var html = "",userList = new Array(),userGroupList = new Array();
			$.each(data.list,function(){
				mapObj.put("r_"+this.id,this);
				if(this.type==0) userList.push(this);
				else userGroupList.push(this);
			});
			html += htmlTemplate(userList,0);
			html += '<tr height="15"><td colspan="6" class="text-center-xs">.  .  .</td></tr>';
			html += htmlTemplate(userGroupList,1);
			$(".table-head").after(html);
			//恢复
			$(".recover").unbind("click").bind("click",function(){
				var id = $(this).attr("index"),recycleObj = mapObj.get("r_"+id);
				var param = "recycleList[0].id="+recycleObj.id+"&recycleList[0].refId="+recycleObj.refId+"&recycleList[0].type="+recycleObj.type;
				actionObj.setOptions({url:"json/recycle_recover"});
				actionObj.submit({ param:param });
			});
			//删除
			$(".del").unbind("click").bind("click",function(){
				var id = $(this).attr("index"),recycleObj = mapObj.get("r_"+id);
				var param = "recycleList[0].id="+recycleObj.id+"&recycleList[0].refId="+recycleObj.refId+"&recycleList[0].type="+recycleObj.type;
				actionObj.setOptions({url:"json/recycle_del"});
				actionObj.submit({ param:param });
			});
		}
	});
}

function htmlTemplate(__list,__type){
	var html = "",type = __type==0?"用户":"用户组";
	$.each(__list,function(){
		html += '<tr><td>'+type+'</td><td>'+this.name+'</td>'
			 + '<td>'+this.managerRole+'</td><td>'+formatUTC(this.delUtc,"yyyy-MM-dd")+'</td><td>'+this.operateName+'</td>'
			 + '<td><a class="recover" href="javascript:void(0);" index="'+this.id+'">恢复</a>'
			 + '&nbsp;&nbsp;&nbsp;&nbsp;<a class="del" href="javascript:void(0);" index="'+this.id+'">删除</a></td></tr>';
	});
	return html;
}

function findByType(__type){
	globalType = __type;
	$(".type"+__type).parents("tr").find("a").each(function(){$(this).removeClass("bg-info");});
	$(".type"+__type).addClass("bg-info");
	initRecycleList();
}
</script>
</html>