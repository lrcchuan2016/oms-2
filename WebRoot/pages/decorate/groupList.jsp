<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body  style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">
	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">模版管理</a></li>
      <li class="active">装饰品分组</li>
    </ol>
    <div class="userListbox channel-list" style="margin-right:20px;">
    	<form class="retrieval" id="searchForm" action="javascript:void(null);">
    		<!--  
    		<div class="form-group pull-left" style="width:350px;">
	    		<div class="input-group">
	    			<div class="input-group-addon">关键字</div>
                  	<input class="form-control" name="paramMap['searchKeyWord']" data-valid="[{rule:'not_empty'}]" type="text" maxlength="100">
	    		</div>
           </div>
           &nbsp;<button type="submit" id="searchBtn" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 搜索</button>
           -->
           <div class="pull-right">
           		<a href="decoratePages_groupAdd" class="btn btn-default"><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;创建分组</a>
           </div>
		</form><br>&nbsp;
        <div class="col-xs-12 padder-tm bg-empty">
			<div id="successTip" class="alert text-black alert-success hide fade in"></div>
			<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
		</div>
        <table id="decorateGroupTable" class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
			<thead>
   				<tr>
   					<td data-sort="int" style="width:50px;">ID</td>
					<td style="width:120px;">操作</td>
					<td>分组名称</td>
					<td style="width:220px;">分组图标</td>
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
var actionObj = null,decorateGroupTableObj = null,mapObj = new HashMap();
$(function(){
	decorateGroupTableObj = $("#decorateGroupTable").tableList({
        url:"json/decorate_findDecorateGroupPage",
        paging:true,
        pageSize:4,
        finishCallBack:initDecorateGroup,
        template:htmlTemplate
    });
	actionObj = new Actions({ 
		successCallBack:function() { decorateGroupTableObj.refresh(); } 
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
	html += '<td class="imageClip"><img src="'+data.iconUrl+'@200w_100h_90Q.'+arr[arr.length-1]+'" title="'+data.name+'"/></td>';
	html += '<td>'+data.detail+'</td>';
	html += '<td>'+formatUTC(data.createUtc,"yyyy-MM-dd hh:mm:ss")+'</td></tr>';
	
	return html;
}

function initDecorateGroup(){
	$(".edit").unbind("click").bind("click",function(){
		var $editParam = "paramMap['decorateType']=decorateGroupEdit&paramMap['decorateGroupId']="+$(this).attr("id");
		window.location.href = "decorateAction_initDecoratePage?"+$editParam;
	});
	
	$(".del").unbind("click").bind("click",function(){
		var $param = "decorateGroupList[0].id="+$(this).attr("id")+"&decorateGroupList[0].iconUrl="+mapObj.get("t_"+$(this).attr("id")).iconUrl;
		alertObj.cancel();
		alertObj.check({
            needConfirm:true,
            confirmTip:"删除当前分组，会级联删除当前分组下的所有装饰品，确认删除？",
            confirmParam:$param,
            confirmAction:delAction
        });
	});
}

function delAction($param){
	actionObj.setOptions({ url:"json/decorate_delDecorateGroup" });
	actionObj.submit({param:$param});
}

</script>
</html>