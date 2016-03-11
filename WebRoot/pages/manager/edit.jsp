<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
</head>
<body>
	<div class="row padder-lx">
		<div class="col-xs-12 no-padder bg-light" style="position:fixed;z-index:99;">
			<h2 class="padder-lm">管理员列表</h2>
			<div class="col-xs-12 no-padder bg-dark lt" style="height:30px;line-height:30px;">
				<span class="no-padder bg-dark lter" style="width:15px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<a href="managerPages_manager">管理员列表</a>  | <span class="text-white">编辑管理员</span>
				<span></span>
			</div>
			<div class="col-xs-12 padder-tm bg-empty">
				<div id="successTip" class="alert text-black alert-success hide fade in">修改用户状态成功</div>
				<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
			</div>
		</div>
		<div class="col-xs-12 padder-tm bg-light" style="top:95px; min-height:465px;">
			<div class="col-xs-12 no-padder">
				<div class="col-xs-5 no-padder">
					<div class="col-xs-4 no-padder">
						<img class="b b-2x b-black r-3x" width="76px" height="76px" id="userpic" src="public/img/user00.png" alt="">
					</div>
					<div class="col-xs-8 no-padder m-l-n-sm">
						<h4 class="font-bold text-black" style="margin-top:5px; padding-bottom:20px;">${managerBean.name}</h4>
						<span>${managerBean.roleName }</span>
					</div>	
				</div>
				<div class="col-xs-7">
					<div class="no-padder"><label>创建日期：</label><span id="create_time"></span></div>
					<div class="padder-xsss">
						<label>账号状态：</label><span class="bg-success padder-x-xs padder" id="account_status">正常</span>
						<a id="account_operator0" class="text-danger-lter" href="javascript:setAccoutStatus(0);">[冻结帐户]</a>&nbsp;&nbsp;<a id="account_operator1" class="text-danger-dker " href="javascript:setAccoutStatus(1);">[删除用户]</a>
					</div>
					<div class="padder-xsss"><label>操作日志：<a href="javascript:void(0);">[查 看]</a></label></div>
				</div>
			</div>
			
			<form id="editForm" class="form-horizontal" action="javascript:void(null);">
				<table id="sysTable" class="padder-tm table table-bordered">
					<tbody class="bg-white">
						<tr>
							<td width="400px">姓名</td>
							<td width="200px" class="realname">${managerBean.name}</td>
							<td></td>
						</tr>
						<tr>
							<td>部门</td>
							<td class="department">${managerBean.departmentName }</td>
							<td class="text-left-xs"><a href="javascript:modifyInfo('department');">[修改]</a></td>
						</tr>
						<tr>
							<td>管理组</td>
							<td class="rolename">${managerBean.roleName }</td>
							<td class="text-left-xs"><a href="javascript:modifyInfo('rolename');">[修改]</a></td>
						</tr>
						<tr>
							<td>账号</td>
							<td class="account">${managerBean.account }</td>
							<td class="text-left-xs"><a href="javascript:modifyInfo('account');">[修改]</a></td>
						</tr>
						<tr>
							<td>初始密码</td>
							<td class="password">${managerBean.password }</td>
							<td class="text-left-xs"><a href="javascript:modifyInfo('password');">[重置初始密码]</a></td>
						</tr>
						<tr>
							<td>密码期限</td>
							<td class="passwordlimit"></td>
							<td class="text-left-xs"><a href="javascript:modifyInfo('passwordlimit');">[选择重置有效期]</a></td>
						</tr>
						<tr>
							<td>关联邮箱</td>
							<td class="relateemail">${managerBean.email }</td>
							<td class="text-left-xs"><a href="javascript:modifyInfo('relateemail');">[修改]</a></td>
						</tr>
					</tbody>
				</table>
			</form>
			<div class="col-xs-12 text-left-xs">
				<button type="button" class="btn btn-default r-lg btn-sm subBtn" > &nbsp;<span class="glyphicon glyphicon-ok"></span> 提 交 修 改&nbsp; </button>
				<button type="button" class="btn btn-default r-lg btn-sm backBtn"> &nbsp;&nbsp;返 回 上 一 页&nbsp;&nbsp; </button>
			</div>
			<div class="col-xs-12 text-left-xs"><span>&nbsp;</span></div>
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
var createUtc = ${managerBean.passwordCreateUtc}; var accountStatus = ${managerBean.status}; var id = ${managerBean.id}; var passwordlimit = ${managerBean.passwordLimitUtc}; 
var departName = "${managerBean.departmentName }", roleName = "${managerBean.roleName }", tipMsg = "${tipMsg}", recordlimit = "";
var currentUtc = new Date().getTime(), mapObj = new HashMap(),formObj = null,actionObj = null,managerArray = new Array(),departs = new Array(),departstr="",rolestr="";
$(function(){
	if(tipMsg!=null&&tipMsg!=""){
		$("#successTip").html(tipMsg);
		$("#successTip").removeClass("hide");
		$("#successTip").slideDown();
		window.setTimeout(function() { $("#successTip").slideUp(); }, 3e3);
	}
	actionObj = new Actions({ successCallBack:function(){} });
	if(createUtc==0) $("#create_time").html("系统搭建时间");
	else $("#create_time").html(formatUTC(createUtc,"yyyy-MM-dd hh:mm:ss"));
	if(0!=passwordlimit && -1!=passwordlimit &&(createUtc+passwordlimit)<currentUtc) {
		$(".passwordlimit").html("已过期");
		$(".passwordlimit").addClass("text-danger");
	}else{
		if(0==passwordlimit || -1==passwordlimit){
		 	$(".passwordlimit").html("永久有效");
		 	$(".passwordlimit").parents("tr").find(".text-left-xs a").html("[选择重置有效期]");
		 	$(".passwordlimit").addClass("text-success");
		}else{
			$(".passwordlimit").html("有效");
		 	$(".passwordlimit").parents("tr").find(".text-left-xs a").html("[修改为过期]");
		 	$(".passwordlimit").addClass("text-success");
		}
	}
	recordlimit = $(".passwordlimit").html();
	initAccountStatus();
	initDepartment();
	initRoles();
	hidePassword();
	formObj = $("#editForm").inputValid(valid_message_options);
	$(".subBtn").click(function(){
		actionObj.setOptions({
			url:"json/manager_editManager",
			successCallBack:function(){
				window.location.href = "managerAction_initEditPage?paramMap['id']="+id+"&paramMap['tipMsg']=修改管理员信息成功!&paramMap['type']=0";
			}
		});
		if(formObj.validate_all()){
			var param = "paramMap['id']="+id;
			if(typeof $("select[name='roleId']").val() != "undefined") param += "&paramMap['roleId']="+$("select[name='roleId']").val();
			if(typeof $("select[name='departmentId']").val() != "undefined") param += "&paramMap['departmentId']="+$("select[name='departmentId']").val();
			if(typeof $("input[name='account']").val() != "undefined") param += "&paramMap['account']="+$("input[name='account']").val();
        	if(typeof $("input[name='recordpassword']").val() != "undefined") param += "&paramMap['password']="+$("input[name='recordpassword']").val();
        	if(typeof $("input[name='email']").val() != "undefined") param += "&paramMap['email']="+$("input[name='email']").val();
        	//alert($(".passwordlimit").children("select").val());
        	if($(".passwordlimit").html()!=recordlimit){
        		if($(".passwordlimit").html()=="已过期"){
        			param += "&paramMap['passwordLimitUtc']="+(currentUtc-createUtc-604800000);
        		}else{
        			var v = $(".passwordlimit").children("select").val();
        			if(v == '0'){
        				param += "&paramMap['passwordLimitUtc']="+(currentUtc+86400000*7);
        			}else if(v == '1'){
        				param += "&paramMap['passwordLimitUtc']="+(currentUtc+86400000*30);
        			}else if(v == '2'){//一年
        				param += "&paramMap['passwordLimitUtc']="+(currentUtc+86400000*365);
        			}else{			//永久
        				param += "&paramMap['passwordLimitUtc']="+(-1);
        			}
        			
        		}
        	}
        	actionObj.submit({ param:param });
		}
	});
	$(".backBtn").click(function(){window.location.href="managerPages_manager";});
});

function initAccountStatus(){
	if(accountStatus==0){
		$("#account_status").html("正常");
		$("#account_operator0").html("[冻结帐户]");
	}else if(accountStatus==1){
		$("#account_status").html("冻结!");
		$("#account_operator0").html("[恢复正常]");
	}else if(accountStatus==2){
		$("#account_status").html("不可用!");
		$("#account_operator0").html("[恢复正常]");
		//if($("#account_operator1")!=null&&typeof $("#account_operator1") != "undefined") $("#account_operator1").remove();
	}else if(accountStatus==3){
		$("#account_status").html("已删除!");
		$("#account_operator0").html("[进入回收站]");
		$("#account_operator0").attr("href","javascript:setAccoutStatus(3);");
		if($("#account_operator1")!=null&&typeof $("#account_operator1") != "undefined") $("#account_operator1").remove();
		
	}
}

function initRoles(){
	$.post("json/manager_findAllRole",function(data){
		if(data!=null&&data.list!=null){
			rolestr += "<select class='form-control input-xs r-lg' name='roleId'>";
			$.each(data.list,function(jndex){
				mapObj.put(this.id,this.name);
				if(this.name==roleName) rolestr += "<option value="+this.id+" selected='selected'>"+this.name+"</option>";
				else rolestr += "<option value="+this.id+">"+this.name+"</option>";
			});
			rolestr += "</select>";
		}
	});
}

function initDepartment(){
	$.post("json/department_findDeparts",function(data){
		if(data!=null&&data.list!=null){
			departs = data.list;
			var html = "<select class='form-control input-xs r-lg' name='departmentId'>";
			$.each(departs,function(){
				mapObj.put("depart_"+this.id, this.name);
				if(this.name==departName) html += "<option value="+this.id+" selected='selected'>"+this.name+"</option>";
				html += "<option value="+this.id+">"+this.name+"</option>";
			});
			html += "</select>";
			departstr = html;
		}
	});
}

function initUpdateParam(__enum){
	var param = "paramMap['id']="+id;
	if(__enum=="status_0"){
		var status = 0;
		if(accountStatus!=2&&accountStatus!=3) status = accountStatus==0?1:0;
		return param+"&paramMap['status']="+status;
	}else if(__enum=="status_1"){
		if(accountStatus==3) return "recycle";
		else return param+"&paramMap['status']=3";
	}
}

function modifyInfo(__enum){
	var writting_str = "";
	if(__enum=="department"){
		writting_str = departstr;
		$("."+__enum).parents("tr").find("a").remove();
	}
	if(__enum=="rolename"){
		writting_str = rolestr;
		$("."+__enum).parents("tr").find("a").remove();
	}
	if(__enum=="account") {
		writting_str = '<input class="form-control input-xs r-lg" type="text" name="account" data-valid="[{rule:\'not_empty\'},{rule:\'id\'}]" maxlength="20" value="'+$("."+__enum).html()+'">';
		$("."+__enum).parents("tr").find("a").remove();
	}
	if(__enum=="password") {
		writting_str = '<input class="form-control input-xs r-lg" type="password" name="recordpassword" data-valid="[{rule:\'not_empty\'},{rule:\'id\'}]" maxlength="16" value="'+$("."+__enum).html()+'">';
		$("."+__enum).parents("tr").find("a").remove();
	}
	if(__enum=="passwordlimit"){
		if($(".passwordlimit").html()=="已过期" || $(".passwordlimit").html()=="永久有效") {
			writting_str = passwordLimitOption();
			$(".passwordlimit").addClass("text-success");
			$("."+__enum).parents("tr").find("a").html("[修改为过期]");
		}else{
			writting_str = "已过期";
			$(".passwordlimit").addClass("text-danger");
			$("."+__enum).parents("tr").find("a").html("[选择重置有效期]");
		}
	}
	if(__enum=="relateemail"){
		writting_str = '<input class="form-control input-xs r-lg" type="text" name="email" data-valid="[{rule:\'not_empty\'},{rule:\'email\'}]" maxlength="16" value="'+$("."+__enum).html()+'">';
		$("."+__enum).parents("tr").find("a").remove();
	}
	console.log(writting_str);
	$("."+__enum).html(writting_str);
}

function setAccoutStatus(__type){
	actionObj.setOptions({
		url:"json/manager_editManager",
		successCallBack:function(){window.location.href = "managerAction_initEditPage?paramMap['id']="+id+"&paramMap['tipMsg']=修改管理员状态成功.&paramMap['type']=0";}
	});
	//alert(__type);
	var params = initUpdateParam("status_"+__type);
	if(__type=="3") {
		window.location.href = "managerPages_recycle"
	}else{
		actionObj.submit({ param:params});
	}
}

//密码期限
function passwordLimitOption(){
	var sel = "<select class='r-lg'>";
	sel += "<option value='0'>有效期:1周</option>";
	sel += "<option value='1'>有效期:30天</option>";
	sel += "<option value='2'>有效期:1年</option>";
	sel += "<option value='3'>有效期:永久</option>";
	sel += "</select>";
	return sel;
}
//隐藏密码
function hidePassword(){
	var len = $(".password").html().length;
	var $p = "";
	for(var i =0;i<len;i++){
		$p +="*"; 
	};
	$(".password").html($p);
};
</script>
</html>