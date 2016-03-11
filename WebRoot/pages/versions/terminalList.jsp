<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="versionAction_terminalList">版本管理</a></li>
      <li class="active">终端列表</li>
    </ol>
    
    <div class="userListbox channel-list">
    	<form class="retrieval" action="versionAction_terminalList" method="post" name="keyWordsForm" >
             <div class="form-group pull-left" style="width:250px;">
                <div class="input-group">
                  <div class="input-group-addon">关键字</div>
                  <input type="text" class="form-control" name="keyWords" value="${keyWords }" onfocus="this.value=''" placeholder="终端名称">
                </div>
              </div>
              <button type="submit" class="btn btn-primary">搜索</button>
    	</form>
        
    	<table class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
    		<thead>
    			<tr>
    				<td>NO.</td>
    				<td>终端名称</td>
    				<td>终端说明</td>
    				<td>操作</td>
    			</tr>
    		</thead>
    		<tbody>
    		<c:if test="${pagination.totalCount!=0 }">
    			<c:forEach items="${pagination.records }" var="terminal" varStatus="i">
    			<tr id="${terminal.id }">
    				<td>${(page-1)*pageSize+i.count }</td>
    				<td>${terminal.name }</td>
    				<td>${terminal.description }</td>
    				<td>
                    	<a class="btn btn-info btn-sm" href="versionAction_initPage?param=0&&id=${terminal.id }">编辑</a>
    					<a class="btn btn-success btn-sm" href="versionAction_initPage?param=1&&id=${terminal.id }">版本
    						<c:if test="${terminal.versionNum!=null }"><span class="badge text-info" style="background-color: #ffffff;">${terminal.versionNum }</span></c:if>
    						<c:if test="${terminal.versionNum==null }"><span class="badge text-info" style="background-color: #ffffff;">0</span></c:if>
    					</a>
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
          	<span>&nbsp;&nbsp;共${totalPage }页&nbsp;&nbsp;<strong class="totalCount">${pagination.totalCount }</strong>个软件终端&nbsp;&nbsp;</span>
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
			var id = $(this).parents("tr").attr("id");
			$.deleteChannel(id);
		});
	});
});


$.extend({
	deleteChannel : function(_id){
		W.$.confirm(
			"是否要删除软件终端:"+_id+"？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						id : _id,
						param : '0'
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
					error: function(json) {
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
