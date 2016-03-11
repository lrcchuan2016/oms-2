<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="public/css/bootstrap-datetimepicker.css" rel="stylesheet">
<link href="public/uploadify/uploadify.css" rel="stylesheet">
<script src="public/uploadify/jquery.uploadify.js"></script>
<script src="public/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="public/js/comm.js"></script>
</head>
<body>
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="adAction_adList">广告管理</a></li>
      <li class="active">广告编辑</li>
    </ol>
    
    <div class="userListbox ad-edit-box row">
    	
    	<div class="panel panel-default clearfix"  style="padding-bottom:20px;">
            <div class="panel-heading">
            	广告编辑
            </div>
            <form id="adResourceForm" action="javascript:void(null);" method="post">
            <s:if test='adResourceBean.contentType!="2"'>
              <div class="pic-area col-md-4" style="height: 500px;text-align: center;">
              	<s:if test='adResourceBean.contentType=="0"'>
              		<img class="img-responsive img-thumbnail" style="margin-top: 100px;" src="${adResourceBean.content }" width="300px">
              	</s:if>
              	<s:if test='adResourceBean.contentType=="1"'>
              		<video class="img-responsive img-thumbnail" style="margin-top: 100px;" controls src="${adResourceBean.content  }" width="300px">
              	</s:if>
              	<s:if test='adResourceBean.contentType=="3"'>
              		<audio class="img-responsive img-thumbnail" style="margin-top: 200px;" controls src="${adResourceBean.content  }">
              	</s:if>
              </div>
            </s:if>
            <input type="hidden" name="adResourceBean.id" class="form-control" data-valid="[{rule:'not_empty'}]" value="${adResourceBean.id}">
            <input type="text" class="from-control hide" name="adLocationBean.type" value="${adLocationBean.type }"> 
              <div class="input-area col-md-8">
              	  <s:if test='adResourceBean.adLocationBean.type=="2"'>
              	  <div class="form-group">
                    <label for="text_upload">文本</label>
                    <input type="text" name="adResourceBean.content" class="form-control" id="text_upload" data-valid="[{rule:'not_empty'}]" placeholder="文本内容[不超过100个字]" value="${adResourceBean.content }" onfocus="this.value=''" maxlength="100">
                  </div>
                  </s:if>
                  <s:else>
                  <div class="form-group">
                    <label for="file_upload">
                    	<s:if test='adResourceBean.adLocationBean.type=="0"'>上传图片</s:if>
                    	<s:if test='adResourceBean.adLocationBean.type=="1"'>上传视频</s:if>
                    </label>
                    <input type="file" id="file_upload">
                    	<p class="tip"></p>
                    	<s:if test='adResourceBean.adLocationBean.type=="0"'>
	                    	<p class="help-block fmt" name="*.jpeg;*.jpg">(启动广告图片的格式为：jpg 图片的尺寸为：640*1136)</p>
	                    	<p class="help-block">(相册顶部广告的格式为：jpg 图片的尺寸为：640*448)</p>
	                    </s:if>
	                    <s:elseif test='adResourceBean.adLocationBean.type=="1"'>
	                    	<p class="help-block fmt" name="*.mp4;*.avi">(视频广告的格式为：MP4)</p>
	                    </s:elseif>
	                    <div class="form-group">
	                    	<label for="file_hidden">上传路径</label>
	                    	<input id="file_hidden" type="text" class="form-control" readonly name="adResourceBean.content" data-valid="[{rule:'not_empty'}]" value="${adResourceBean.content }">
	                    	<input id="resourceKey" type="hidden" name="adResourceBean.aliyunKey" value="${adResourceBean.aliyunKey }">
                  		</div>
                  </div>
                  </s:else>
                  
                  <div class="form-group">
                    <label for="startTime">开始时间</label>
                    <input type="datetime" name="startTime" id="startTime" readonly class="form_datetime form-control" data-valid="[{rule:'not_empty'}]" value="${adResourceBean.start_utc }">
                  </div>
                  <div class="form-group">
                    <label for="endTime">结束时间</label>
                    <input type="datetime" name="endTime" id="endTime" readonly class="form_datetime form-control" data-valid="[{rule:'not_empty'}]" value="${adResourceBean.end_utc }">
                  	<p id="adTime"></p>
                  </div>
                  <div class="form-group">
                    <label for="url">跳转URL</label>
                    <input type="text" name="adResourceBean.url" class="form-control" id="url" placeholder="跳转的URL" data-valid="[{rule:'not_empty'}]" value="${adResourceBean.url }" maxlength="100">
                  </div>
                  <div class="form-group">
                    <label for="note">广告备注</label>
                    <input type="text" name="adResourceBean.remark" class="form-control" id="note" placeholder="备注[不超过100个字]" data-valid="[{rule:'not_empty'}]" value="${adResourceBean.remark }" onfocus="this.value=''" maxlength="100">
                  </div>
                  <button type="submit" class="btn btn-primary btn-save">提交</button>
    				<a href="javascript:history.back()" class="btn btn-default back"> 返 回 </a>
              </div>
            </form>
        </div>
        
    	
	</div><!---userListbox-end-->
    
    </div>
<script>

var W = window.top;
var formObj;
var adType = '${adResourceBean.terminal_type}';
$(function(){
	formObj = $("#adResourceForm").inputValid(valid_message_options);
	 $(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii'});
	
	
	$('#file_upload').uploadify({
		'formData'     : {
			'typeKey': 'ad',
			'timestamp' : '',
			'token'     : ''
		},
		'swf'      : 'public/uploadify/uploadify.swf',
		'uploader' : 'interface/resourceImage_uploadOSSImage',
		'buttonText' : '选择文件',
		'fileObjName': 'image', 
		'fileTypeExts' : $(".fmt").attr("name"),
		'fileSizeLimit':0,
		'successTimeout':60*60*24,
		//上传成功触发
		'onUploadSuccess': function (file, data, response)
		{	
		if(data!=null&&response!=null){
 			if(data!=""){
				var dataResult = eval("("+data+")");
				if(dataResult.result == "00000000"){
					var imageUrl = "adResource/"+dataResult.imageUrl.split("adResource/")[1];
					$("#file_hidden").val(dataResult.imageUrl);
					$("#resourceKey").val(imageUrl);
					$(".pic-area").children().attr("src",dataResult.imageUrl);
					W.$.alert("上传成功!");
				}else{
					W.$.alert("上传失败!");
				}
				
			}else{
				if(response==true){
					W.$.alert("上传成功！");
				}
			}
		}
		},
		//开始上传时触发
		onUploadStart : function(file){
		 	//$(".tip").html("<font color='#00000ff'>正在上传</font>");
		 },
	});
	
	$(".form_datetime").livequery(function(){
		$(this).each(function(){
			var time = $(this).val();
			var t = formatUTC(time,"yyyy-MM-dd hh:mm");
			$(this).val(t);
		});
	});
	
	//编辑保存
	$(".btn-save").click(function(){
		if(formObj.validate_all()){
			var $param = $("#adResourceForm").serialize();
			$.ajax({
				type : 'POST',
				dataType : 'json',
				data : $param,
				url : 'json/ad_updateAdResource',
				success : function(json){
					if(json.result == '00000000'){
						W.$.alert("编辑保存成功！");
						window.location.href="adAction_adList?adType="+adType;
					}else{
						W.$.alert("编辑保存失败！");
					}
				},
				error : function(e){
					W.$.alert("保存失败！");
				}
			});
		}
	});
	
});


$.extend({
			
});
</script>
</body>
</html>
