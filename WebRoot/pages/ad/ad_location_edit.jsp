<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
  <body>
  		<div class="container-fluid">
 			<ol class="breadcrumb">
      			<li><a href="javascript:void(0)">首页</a></li>
      			<li><a href="adAction_adList">广告管理</a></li>
      			<li class="active">广告栏位编辑</li>
    		</ol>
    		<div class="userListbox ad-edit-box row">
    	
        <div class="panel panel-default clearfix"  style="padding-bottom:20px;">
            <div class="panel-heading">
            	编辑广告位
            </div>
            
            <form id="adLocationForm" action="javascript:void(null);" method="post">
              <input  class="form-control hide" name="adLocationBean.id" value="${adLocationBean.id }">
              <div class="input-area">
              	  <div class="form-group" style="width:200px;">
                    <label for="text_upload">广告位名称</label>
                    <input type="text" class="form-control" name="adLocationBean.ad_name" data-valid="[{rule:'not_empty'}]" id="text_upload" value='<s:property value="adLocationBean.ad_name"/>' onfocus="javascript:$(this).val('')" maxlength="10">
                    <p id="pname"></p>
                  </div>
                  <div class="form-group" style="width:200px;">
                    <label for="text_upload">发布终端</label>
                    <select id="terminal" class="form-control" disabled name="adLocationBean.terminal_type">
                      <option value="0">全局</option>
                      <option value="1">手机</option>
                      <option value="2">电视</option>
                      <option value="3">网站</option>
                    </select>
                    <input class="form-control hide" name="adLocationBean.terminal_type" value="${adLocationBean.terminal_type }" data-valid="[{rule:'not_empty'}]">
                  </div>
                  <div class="form-group" style="width:200px;">
                    <label for="note">媒资类型</label>
                  	<select id="type" class="form-control" disabled name="adLocationBean.type">
                      <option value="0">图片</option>
                      <option value="1">视频</option>
                      <option value="2">文本</option>
                      <option value="3">音频</option>
                    </select>
                    <input class="form-control hide" name="adLocationBean.type" value="${adLocationBean.type }" data-valid="[{rule:'not_empty'}]">
                  </div>
                  <s:if test='adLocationBean.type=="0"||adLocationBean.type=="1"'>
                  <div class="form-group d1" style="width:100px;">
                    <label for="em_widht">宽</label>
                    <input type="text" id="em_widht" name="adLocationBean.width" data-valid="[{rule:'not_empty'},{rule:'non_negative',message:'请输入大于0的整数'}]" class="form-control" placeholder="px" value="${adLocationBean.width }" onfocus="javascript:$(this).val('')" maxlength="10">
                  </div>
                  <div class="form-group d2" style="width:100px;">
                    <label for="em_height">高</label>
                    <input type="text" id="em_height" name="adLocationBean.height" data-valid="[{rule:'not_empty'},{rule:'non_negative',message:'请输入大于0的整数'}]" class="form-control" placeholder="px" value="${adLocationBean.height }" onfocus="javascript:$(this).val('')" maxlength="10"/>
                  </div>
                  </s:if>
                  <s:if test='adLocationBean.type=="1"||adLocationBean.type=="3"'>
                  <div class="form-group d3" style="width:100px;">
                    <label for="code_rate">码率</label>
                    <input type="text" id="code_rate" name="adLocationBean.bitrate" data-valid="[{rule:'not_empty'},{rule:'non_negative',message:'请输入大于0的整数'}]" class="form-control" placeholder="Hz" value="${adLocationBean.bitrate }" onfocus="javascript:$(this).val('')" maxlength="10">
                  </div>
                  </s:if>
                  <button type="submit" class="btn btn-primary btn-save">提交</button>
    				<a href="javascript:history.back()" class="btn btn-default"> 返 回 </a>
              </div>
            </form>
        
    	</div>
    </div>
<script>
var W = window.top;
var formObj;
var terminal_type = '${adLocationBean.terminal_type}';
var type = '${adLocationBean.type}';
$(function(){
	formObj = $("#adLocationForm").inputValid(valid_message_options);
	$("#terminal>option[value='"+terminal_type+"']").attr("selected","selected");
	$("#type>option[value='"+type+"']").attr("selected","selected");
	
	$(".btn-save").click(function(){
		var $param = $("#adLocationForm").serialize();
		if(formObj.validate_all()){
			$.ajax({
				type : 'POST',
				dataType : 'json',
				data : $param,
				url : 'json/ad_editLocation',
				success : function(json){
					if(json.result == '00000000'){
						W.$.alert("编辑广告位成功");
						window.location.href = "adAction_locationList";
					}else{
						W.$.alert("编辑广告位失败！    "+json.tip);
					}
				},
				error : function(e){
					W.$.alert("编辑广告位失败！");
				}
			});
		}
	});
});



</script>    
   
</body>
</html>
