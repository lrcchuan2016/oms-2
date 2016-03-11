<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body>
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="versionAction_terminalList">版本管理</a></li>
      <li><a href="javascript:void(0)">${terminal.name }</a></li>
      <li class="active">版本列表</li>
    </ol>
    <div class="row channel-add" style="padding:0px 20px;">
            <div class="panel panel-default clearfix">
              <div class="panel-body col-md-8">
              	<a class="btn btn-info btn-sm" href="versionAction_initPage?param=3&&id=${terminal.id }">上传新版本</a>
              </div>
            </div>
    </div>
              
    <div class="userListbox channel-list">
    <c:if test="${pagination.totalCount!=0 }">
    	<table class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
    		<thead>
    			<tr>
    				<td>NO.</td>
    				<td>版本号</td>
    				<td>说明</td>
    				<td>渠道</td>
    				<td>创建时间</td>
    				<td>修改时间</td>
    				<td>操作</td>
    			</tr>
    		</thead>
    		<tbody>
    		<c:forEach items="${pagination.records }" var="softVersion" varStatus="i">
    			<tr id="${softVersion.id }">
    				<td>${(page-1)*pageSize+i.count }</td>
    				<td>${softVersion.versionNum }</td>
    				<td>${softVersion.description }</td>
    				<td>
                    	<span class="label label-info">乐视TV</span>
                    	<span class="label label-info">小米TV</span>
                    </td>
                    <td class="createUtc">${softVersion.createUtc }</td>
                    <td class="modifyUtc">${softVersion.modifyUtc }</td>
    				<td>
                    	<a class="btn btn-info btn-sm" href="versionAction_initPage?param=2&&id=${softVersion.id }">编辑</a>
    					<a class="btn btn-success btn-sm"  href="${softVersion.softUrl }">版本下载&nbsp;&nbsp;<span class="glyphicon glyphicon-save"></span></a>
    					<a class="btn btn-danger btn-sm"  href="javascript:void(0)">删除</a>
                    </td>
                </tr>
            </c:forEach>
  			
    		</tbody>
    			
    	</table>
        <c:if test="${totalPage>1 }">
        <nav class="pages_number">
          <ul class="pagination pull-right">
            ${pageLinks }
          </ul>
          <div class="pull-right">
          	<span>&nbsp;&nbsp;共${totalPage }页&nbsp;&nbsp;<strong class="totalCount">${pagination.totalCount }</strong>个渠道&nbsp;&nbsp;</span>
          </div>
        </nav>
        </c:if>
        </c:if>
        
	</div><!---userListbox-end-->
    
    </div>
<script>

var W = window.top;

$(function(){
	$("td .btn-danger").livequery(function(){
		$(this).click(function(){
			var id = $(this).parents("tr").attr("id")
			$.deleteVersion(id);
		});
	});
	
	//格式化时间
	$("td[class$='Utc']").livequery(function(){
		$(this).html(formatUTC($(this).html(),"yyyy-MM-dd hh:mm:ss"));
	});
});


$.extend({
	deleteVersion : function(_id){
		W.$.confirm(
			"是否要删除版本:"+_id+"？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						id : _id,
						param : '1'
					},
					url:'json/version_Del', //API
					success:function(json) {
						//删除成功回调
						if(json.result == "00000000"){
							W.$.alert("删除成功！");
							$("#" + _id).remove(); //删除行
							$(".totalCount").html($(".totalCount").html()-1);
						}else {
							W.$.alert("删除失败！"+json.tip);
						}
					},
					error: function(data) {
						W.$.alert("删除失败！"+json.tip);
					}
				});
			},
			function(){
				//alert("取消按钮");
			}
		);
	}
});
</script>
</body>
</html>
