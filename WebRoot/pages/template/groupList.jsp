<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">
	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">模版管理</a></li>
      <li class="active">模版分组</li>
    </ol>
    
    <div class="userListbox channel-list" style="margin-right:20px;">
    	<form class="retrieval" id="searchForm" action="javascript:void(null);">
           <div class="pull-right">
           		<a href="templatePages_groupAdd" class="btn btn-default"><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;创建分组</a>
           </div>
		</form><br>&nbsp;
		<div class="col-xs-12 padder-tm bg-empty">
			<div id="successTip" class="alert text-black alert-success hide fade in"></div>
			<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
		</div>
		<table id="templateGroupTable" class="table table-bordered">
			<thead>
   				<tr>
   					<td data-sort="int" style="width:50px;">ID</td>
					<td style="width:120px;">操作</td>
					<td style="width:140px;">分组名称</td>
					<td style="width:220px;">图标</td>
					<td>分组详情</td>
					<td style="width:220px;">创建时间</td>
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
var actionObj = null,templateGroupTableObj = null,mapObj = new HashMap();
$(function(){
	templateGroupTableObj = $("#templateGroupTable").tableList({
        url:"json/template_findTemplateGroupPage",
        paging:true,
        pageSize:4,
        finishCallBack:initTemplateGroup,
        template:htmlTemplate
    });
	actionObj = new Actions({ 
		successCallBack:function() { templateGroupTableObj.refresh(); } 
	});
	alertObj = new Alert({});
	
});

function htmlTemplate(index, data, recordIndex){
	mapObj.put("t_"+data.id,data);
	var arr = data.iconUrl.split(".");
	var html = "<tr>";
	html += '<td>'+recordIndex+'</td>';
	html += '<td><div class="btn-group"><button class="btn btn-danger btn-xs del disabled" id="'+data.id+'"><span class="glyphicon glyphicon-minus-sign"></span> 删除</button><button class="btn btn-warning btn-xs edit" id="'+data.id+'"><span class="glyphicon glyphicon-edit"></span> 编辑</button></div></td>';
	html += '<td>'+data.name+'</td>';
	html += '<td><img src="'+data.iconUrl+'@200w_100h_90Q.'+arr[arr.length-1]+'" title="'+data.name+'"/></td>';
	html += '<td>'+data.detail+'</td>';
	html += '<td>'+formatUTC(data.createUtc,"yyyy-MM-dd hh:mm:ss")+'</td></tr>';
	
	return html;
}

function initTemplateGroup(){
	$("a.atooltip").unbind("mouseover").bind("mouseover",function (e) {
		this.myTitle = this.title;
		this.title = "";
		var tooltip = "<div class='hoverGrouptip'><img src='" + this.href + "' alt='分组图标' width='180' height='180' /></div>";
		$("body").append(tooltip);
		$(".hoverGrouptip").css({
			"top": (e.pageY-15) + "px",
			"left": (e.pageX-15) + "px"
		}).show("fast");
		$(".hoverGrouptip").mouseover(function(e){$(".hoverGrouptip").show();}).mouseout(function(e){$(".hoverGrouptip").remove();});
	});
	
	$(".edit").unbind("click").bind("click",function(){
		var $editParam = "paramMap['tempType']=tempGroupEdit&paramMap['templateGroupId']="+$(this).attr("id");
		window.location.href = "templateAction_initTemplatePage?"+$editParam;
	});
	
	$(".del").unbind("click").bind("click",function(){
		var $param = "templateGroupList[0].id="+$(this).attr("id")+"&templateGroupList[0].iconUrl="+mapObj.get("t_"+$(this).attr("id")).iconUrl;
		alertObj.cancel();
		alertObj.check({
            needConfirm:true,
            confirmTip:"删除当前分组，会级联删除当前分组下的所有主题相册模版，确认删除？",
            confirmParam:$param,
            confirmAction:delAction
        });
	});
}

function delAction($param){
	actionObj.setOptions({ url:"json/template_delTemplateGroup" });
	actionObj.submit({param:$param});
}

</script>
</html>