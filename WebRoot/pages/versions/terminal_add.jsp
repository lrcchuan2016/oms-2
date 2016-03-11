<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0);">首页</a></li>
      <li><a href="javascript:void(0);">版本管理</a></li>
      <li class="active">新建终端</li>
    </ol>
    
    <div class="row channel-add" style="padding:0px 20px;">
       
        
            <div class="panel panel-default clearfix">
              <div class="panel-heading">新建终端</div>
              <form id="terminalForm" action="javascript:void(0);">
              <div class="panel-body col-md-8">
                  <div class="form-group">
                    <label for="terminal_name">终端名称</label>
                    <input type="text" class="form-control" id="terminal_name" name="terminal.name" data-valid="[{rule:'not_empty'}]" placeholder="如：iOS , android TV等">
                  </div>
                  <div class="form-group">
                    <label>终端说明</label>
                    <textarea class="form-control" id="terminal_description" rows="4" name="terminal.description" data-valid="[{rule:'not_empty'}]" placeholder="terminal introduce"></textarea>
                  </div>
              </div>
              </form>
            </div>
            
            <hr>
    
    			<button type="submit" class="btn btn-primary btn-save">保存</button>
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
	$(".btn-save").livequery(function(){
		$(this).click(function(){
			if(formObj.validate_all()){
				$.ajax({
					type:'POST',
					dataType:'json',
					data:$("#terminalForm").serializeArray(),
					url:'json/version_terminalAdd',
					success:function(json){
						if(json.result == '00000000'){
							W.$.alert("新增软件终端成功");
							$("#terminal_name").val("");
							$("#terminal_description").val("");
						}else{
							W.$.alert("新增软件终端失败");
						}
					},
					error:function(){
						W.$.alert("新增软件失败");
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
