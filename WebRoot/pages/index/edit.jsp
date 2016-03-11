<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
</head>
<body>
	<div class="row padder-lx">
		<div class="col-xs-12 no-padder bg-light" style="position:fixed;z-index:99;">
			<h2 class="padder-lm">个人资料</h2>
			<div class="col-xs-12 no-padder bg-dark lt" style="height:30px;line-height:30px;">
				<span class="no-padder bg-dark lter" style="width:15px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<span class="text-white">查看</span>
				<span></span>
			</div>
			<div class="col-xs-12 padder-tm bg-empty">
				<div id="successTip" class="alert text-black alert-success hide fade in">修改用户信息成功</div>
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
					<div class=""><label>创建日期：</label><span id="create_time"></span></div>
				</div>
			</div>
			
			<form id="editForm" class="form-horizontal" action="javascript:void(null);">
				<table id="sysTable" class="padder-tm table table-bordered">
					<tbody class="bg-white">
						<tr>
							<td>姓名</td>
							<td width="200px" class="realname">${managerBean.name}</td>
							<td></td>
						</tr>
						<tr>
							<td>部门</td>
							<td class="department">${managerBean.departmentName }</td>
							<td class="text-left-xs"></td>
						</tr>
						<tr>
							<td>管理组</td>
							<td class="rolename">${managerBean.roleName }</td>
							<td class="text-left-xs"></td>
						</tr>
						<tr>
							<td>账号</td>
							<td class="account">${managerBean.account }</td>
							<td class="text-left-xs"></td>
						</tr>
						<tr>
							<td>密码</td>
							<td class="password">${managerBean.password }</td>
							<td class="text-left-xs"><a class="btn" data-toggle="modal" data-target="#passwordModal" href="#">[重新设置密码]</a></td>
						</tr>
						<tr>
							<td>密码期限</td>
							<td class="passwordlimit"></td>
							<td class="text-left-xs"></td>
						</tr>
						<tr>
							<td>关联邮箱</td>
							<td class="relateemail">${managerBean.email }</td>
							<td class="text-left-xs"><a href="javascript:modifyInfo('relateemail');">[修改]</a></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
	<%/*模态框*/%>
	<div id="passwordModal" class="modal" tabindex="-1">
		<div class="modal-dialog" style="width:500px;">
			<div class="modal-content">
				<div class="modal-header">
					<h4 id="managerDialogHeader">修改密码</h4>
				</div>
				<div class="modal-body">
				<form id="passwordForm" class="form-horizontal" action="javascript:void(null);">
					<div class="form-group">
					    <label class="col-xs-3 control-label" for="oldPassword1">原密码：</label>
					    <div class="col-xs-8"><input type="password" class="form-control input-sm r-lg" id="oldPassword1" data-valid="[{rule:'not_empty'},{rule:'id'}]" maxlength="20" placeholder="只能右数字、字母、下划线组成，长度不大于20"></div>
					 </div>
					 <div class="form-group">
					    <label class="col-xs-3 control-label" for="oldPassword2">重复密码：</label>
					    <div class="col-xs-8"><input type="password" class="form-control input-sm r-lg" id="oldPassword2" data-valid="[{rule:'not_empty'}]" maxlength="20" placeholder="只能右数字、字母、下划线组成，长度不大于20"></div>
					  </div>
					  <div class="form-group">
					    <label class="col-xs-3 control-label" for="newPassword">新密码：</label>
					    <div class="col-xs-8"><input type="password" class="form-control input-sm r-lg" id="newPassword" data-valid="[{rule:'not_empty'},{rule:'id'}]" maxlength="20" placeholder="只能右数字、字母、下划线组成，长度不大于20"></div>
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
	
</body>
<script src="public/js/jquery.ui.widget.js"></script>
<script type="text/javascript">
var createUtc = ${managerBean.passwordCreateUtc};  var id = ${managerBean.id}; var passwordlimit = ${managerBean.passwordLimitUtc}; 
 tipMsg = "${tipMsg}", recordlimit = "";
var currentUtc = new Date().getTime(), formObj = null,actionObj = null;
var formObjPassword = null;
var W = window.top;
$(function(){
	formObjPassword = $("#passwordForm").inputValid(valid_message_options);
	formObj = $("#editForm").inputValid(valid_message_options);
	
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
		 	$(".passwordlimit").parents("tr").find(".text-left-xs").html("");
		 	$(".passwordlimit").addClass("text-success");
		}else{
			$(".passwordlimit").html("有效");
		 	$(".passwordlimit").parents("tr").find(".text-left-xs a").html("[修改为过期]");
		 	$(".passwordlimit").addClass("text-success");
		}
	}

	$("#oldPassword1").blur(function(){
		var $this = $(this);
		$("#oldPassword2").attr("data-valid","[{rule:'equals',value:'"+$(this).val()+"',message:'两次输入不一致'}]");
		if(formObjPassword.validate()){
			$.ajax({
				url:'json/manager_editPassword',
				type:'POST',
				dataType:'json',
				data:{"paramMap['id']":id,"paramMap['password']":$this.val()},
				success:function(json){
					if(json.result != '00000000'){
						$this.parent("div").addClass("has-error");
						$this.popover({
                            	content:"密码错误",
                            	placement:'bottom'
                            })
                            $this.popover("show");
					};
				}
			});
		};
	});
	//保存修改密码
	$(".btn-sub").click(function(){
		var value = $("#newPassword").val();
		if(!$("#oldPassword1").parent("div").hasClass("has-error")&&formObjPassword.validate_all()){
			actionObj.setOptions({
				url:"json/manager_editManager",
				successCallBack:function(){
					window.location.href = "managerAction_initEditPage?paramMap['id']="+id+"&paramMap['type']=1&paramMap['tipMsg']=修改管理员信息成功";}
			});
			var param = "paramMap['id']="+id+"&paramMap['password']="+value;
			actionObj.submit({param:param});
		};
	});
	//密码隐藏为*号
	hidePassword();

});
//修改密码
function modifyInfo(__enum){
	if(__enum=="relateemail"){
		writting_str = '<input class="form-control input-xs r-lg email" type="text" name="email" data-valid="[{rule:\'not_empty\'},{rule:\'email\'}]" maxlength="50" value="'+$("."+__enum).html()+'">';
		$("."+__enum).parents("tr").find("a").remove();
		$("."+__enum).html(writting_str);
		$(".email").blur(function(){
			var value = $(this).val();
			saveEdit(value);
		});
		//键盘保存修改
		$(window).keydown(function(){
			var e = event || window.event;
			if(e.keyCode == 13){
				var value = $(".email").val();
				saveEdit(value);
			};
		});
	};
};

function saveEdit(value){
	if(formObj.validate_all()){
			actionObj.setOptions({
				url:"json/manager_editManager",
				successCallBack:function(){
					location.href = "managerAction_initEditPage?paramMap['id']="+id+"&paramMap['type']=1&paramMap['tipMsg']=修改管理员信息成功";
				}
			});
			var param = "paramMap['id']="+id+"&paramMap['email']="+value;
			actionObj.submit({param:param});
	};
};

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