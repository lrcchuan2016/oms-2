<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body>
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0);">首页</a></li>
      <li><a href="javascript:void(0);">版本管理</a></li>
      <li class="active">终端编辑</li>
    </ol>
    
    <div class="row channel-add" style="padding:0px 20px;">
       
        
            <div class="panel panel-default clearfix">
              <div class="panel-heading">终端编辑</div>
              <form id="terminalForm" action="javascript:void(0);">
              <input type="hidden" value="${terminal.id }" name="terminal.id">
              <input type="hidden" value="${terminal.createUtc }" name="terminal.createUtc">
              <div class="panel-body col-md-8">
                  <div class="form-group">
                    <label for="terminal_name">终端名称</label>
                    <input type="text" class="form-control" id="terminal_name" value="${terminal.name }" name="terminal.name" data-valid="[{rule:'not_empty'}]" placeholder="如：iOS , android TV等">
                  </div>
                  <div class="form-group">
                    <label>终端说明</label>
                    <textarea class="form-control" id="terminal_description" rows="4"  name="terminal.description" data-valid="[{rule:'not_empty'}]" placeholder="terminal introduce">${terminal.description }</textarea>
                  </div>
              </div>
              </form>
            </div>
            
            <hr>
    
    			<button type="submit" class="btn btn-primary btn-save">更新</button>
    			<button onClick="javascript:history.back()" type="button" class="btn btn-default">返回</button>
        </div>
    
</div>

<div style="height:200px;">
</div>
<script>

var W = window.top;
var formObj = null;
$(function(){
	formObj = $("#terminalForm").inputValid(valid_message_options);
	//更新编辑
	$(".btn-save").livequery(function(){
		$(this).click(function(){
			if(formObj.validate_all()){
				$.ajax({
					type:'POST',
					dataType:'json',
					data:$("#terminalForm").serializeArray(),
					url:'json/version_terminalEdit',
					success:function(json){
						if(json.result == '00000000'){
							W.$.alert("编辑软件终端成功");
							window.location.href = "versionAction_terminalList";
						}else{
							W.$.alert("编辑软件终端失败");
						}
					},
					error:function(){
						W.$.alert("编辑软件终端失败");
					}
				});
			}
		});
	});
});


$.extend({
	
});
</script>
</body>
</html>
