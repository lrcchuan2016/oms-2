<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="public/uploadify/uploadify.css"  rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="container-fluid">
	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">模版管理</a></li>
      <li><a href="templatePages_groupList">模版分组</a></li>
      <li class="active">编辑分组</li>
    </ol>
    <div class="userListbox channel-list" style="margin-right:20px;">
		<div id="successTip" class="alert text-black alert-success hide fade in"></div>
		<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
		<div class="panel panel-default clearfix">
			<div class="panel-heading">
					<h4>编辑分组&nbsp;&nbsp;<span class="badge"><span class="text-danger">*</span> 号开头的为必填项. &nbsp;&nbsp;&nbsp;</h4>
				</div>
				<div class="panel-body">
					<form id="templateForm" class="form-horizontal" action="javascript:void(null);">
						<input type="hidden" name="templateGroupList[0].createUtc" value="${templateGroupBean.createUtc }">
						<div class="row col-xs-8">
							<div class="col-xs-12">
								<div class="form-group">
									<label class=""><span class="text-danger">*</span>图标URL</label>
									<input class="form-control" readonly name="templateGroupList[0].iconUrl" value="${templateGroupBean.iconUrl }" data-valid="[{rule:'not_empty'}]" type="text" placeholder="上传后图片相对路径 [不可更改]">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group">
									<label class=""><span class="text-danger">*</span>分组编码</label>
									<input class="form-control editKey" readonly type="text" name="templateGroupList[0].id" value="${templateGroupBean.id }" readonly maxlength="20" data-valid="[{rule:'not_empty'}]" placeholder="上传图标后获得分组编码">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group">
									<label class=""><span class="text-danger">*</span>分组名称</label>
									<input class="form-control" type="text" name="templateGroupList[0].name" value="${templateGroupBean.name }" maxlength="20" data-valid="[{rule:'not_empty'}]" placeholder="请输入分组名称">
								</div>
							</div>
							
							<div class="col-xs-12">
								<div class="form-group">
									<label class="">分组详情</label>
									<textarea class="form-control" name="templateGroupList[0].detail" value="${templateGroupBean.detail }" rows="4" maxlength="1000">${templateGroupBean.detail }</textarea>
								</div>
							</div>
						</div>
						<div class="row col-xs-4">
							<div class="col-xs-12" style="padding-left:0px; padding-right:0px;">
								<label class=""><span class="text-danger">*</span> 上传模块</label>
	      						<div class="thumbnail imgClip">
					        		<img class="iconUrl" src="${templateGroupBean.iconUrl }"/>
					        	</div>
						        <div id="fileQueue"></div>
						        <input type="file" name="image" id="uploadify" />
						        <div class="templateUpload">
						            <button  class="btn btn-primary" disabled>上传</button>
						            <button class="btn btn-default" disabled>取消上传</button>
						        </div>
							</div>
						</div>
					</form>
					
				</div>
				<div class="panel-footer col-xs-12 pull-left">
					<button type="button" class="btn btn-primary subBtn" > &nbsp;<span class="glyphicon glyphicon-ok"></span> 提    交 &nbsp; </button>
					<button type="button" class="btn btn-default backBtn"> &nbsp;<span class="glyphicon glyphicon-remove"></span> 关    闭&nbsp; </button>
				</div>
		</div>
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
<script type="text/javascript" src="public/uploadify/swfobject.js" ></script> 
<script type="text/javascript" src="public/uploadify/jquery.uploadify.js" ></script>  
<script type="text/javascript">
var mapObj = new HashMap(),formObj = null,actionObj = null,uploadProgressTimeout = -1,uploadTime = 0;
var $uploadBtn = $(".templateUpload").find("button.btn-primary"),$cancelUploadBtn = $(".templateUpload").find("button.btn-default");

$(function(){
	formObj = $("#templateForm").inputValid(valid_message_options);
	initUploadify();
	actionObj = new Actions({
		url:"json/template_editTemplateGroup", 
		successCallBack:function() {}
	});
	
	$(".subBtn").click(function(){
		if(formObj.validate_all()){
			var $editParam = $("#templateForm").serialize();
			if(typeof(editKey)=='undefined') var editKey = $(".editKey").val();
        	actionObj.submit({ 
        		param:$editParam
        	});
		}
	});
	$(".backBtn").click(function(){ window.location.href = "templatePages_groupList"; });
});

function initUploadify(){
	$("#uploadify").uploadify({  
         'swf': 'public/uploadify/uploadify.swf',
         'uploader': 'interface/resourceImage_uploadOSSImage',
         'buttonText': '选择图标',
         'cancelImg': 'public/uploadify/uploadify-cancel.png',  
         'fileObjName': 'image',  
         'fileDesc': '支持格式：jpg,gif,png,bmp',  
         'fileTypeExts': '*.jpg;*.gif;*.jpeg;*.png;*.bmp',
         'formData': { 'typeKey': 'templateGroup'},
         'queueID': 'fileQueue',
         'auto': false,
         'multi': false,
		 'simUploadLimit':1,
		 'fileSizeLimit':0,
		 'successTimeout':60*60*24,
         'onUploadSuccess' : function(file,data,response) {//上传完成时触发（每个文件触发一次）
			console.log(' - 服务器端消息: ' + data+ ' - 是否上传成功: ' + response);
			$cancelUploadBtn.attr("disabled",false);
		 	if(data!=null&&response!=null){
		 		if(data!=""){
			 		var resultData = eval("("+data+")");
			 		if(response==true && resultData.result=="00000000"){
			 			$uploadBtn.html("上传成功...");
			 			$(".iconUrl").attr({src:resultData.imageUrl});
			 			$("input[name='templateGroupList[0].iconUrl']").val(resultData.imageUrl);
			 			//mid = $(".editKey").val();
			 			//editKey = resultData.productK ey;
			 			//$(".editKey").val(editKey);
			 			tipsSlideOut("success"," 上传图片成功, 文件名:"+file.name+", 文件大小："+file.size+", 类型："+file.type);
			 		}else{
			 			$uploadBtn.html("上传失败...");
			 			tipsSlideOut("failed"," 上传图片失败, 文件名:"+file.name+", 文件大小："+file.size+", 类型："+file.type);
			 		}
		 		}else{
		 			if(response==true){
			 			$uploadBtn.html("上传成功...");
			 			tipsSlideOut("success"," 上传图片成功, 文件名:"+file.name+", 文件大小："+file.size+", 类型："+file.type);
			 		}else{
			 			$uploadBtn.html("上传失败...");
			 			tipsSlideOut("failed"," 上传图片失败, 文件名:"+file.name+", 文件大小："+file.size+", 类型："+file.type);
			 		}
		 		}
		 	}
		 },
		 onQueueComplete: function(queueData){
		 	$uploadBtn.html("图片上传完成");
		 },
		 onDialogClose : function(swfuploadifyQueue) {//当文件选择对话框关闭时触发
			if( swfuploadifyQueue.filesSelected > 0 ){
				$uploadBtn.html("点击上传");
				$uploadBtn.attr("disabled",false);
				$uploadBtn.unbind("click").bind("click",function(){
					$('#uploadify').uploadify('upload');
					$(this).attr("disabled",true);
					$cancelUploadBtn.attr("disabled",false);
					$cancelUploadBtn.unbind("click").bind("click",function(){
						$('#uploadify').uploadify('cancel');
						$(this).attr("disabled",true);
					});
				});
			}else{
				$uploadBtn.html("上传");
				$uploadBtn.attr("disabled",true);
			}
		 },
		 onUploadStart : function(file){
		 	$uploadBtn.html("正在上传,请稍后...");
		 	$uploadBtn.attr("disabled",true);
		 },
		 onClearQueue: function(queueItemCount){
		 	$uploadBtn.html("点击上传");
			$uploadBtn.attr("disabled",true);
         },
		 onUploadProgress : function(file,fileBytesLoaded,fileTotalBytes,
					queueBytesLoaded,swfuploadifyQueueUploadSize) {//上传进度发生变更时触发
			console.log("fileBytesLoaded:"+fileBytesLoaded+",fileTotalBytes:"+fileTotalBytes);
			uploadTime = 0;
			var percentTage = Math.ceil(fileBytesLoaded/fileTotalBytes)*100;
			if(percentTage==100){
				//这里无法监控到服务器上传阿里云OSS服务器的上传进度，只好做个假的了,假设上传网络环境为4M宽带,最大速率为512KB/s，这里取200KB/s
				uploadTime = Math.ceil(fileTotalBytes/204800);		//大概需要这么长的时间上传
				$uploadBtn.html("正在上传阿里云,预计需要"+uploadTime+"S,请耐心等待...");
			}else $uploadBtn.html("正在上传至服务器,已上传:"+percentTage+"% ...");
		 }
    });  
}

function tipsSlideOut(__result,__tip){
	if(__result=="success"){
		$("#successTip").html(__tip);
		$("#successTip").removeClass("hide");
		$("#successTip").slideDown();
		window.setTimeout(function() { $("#successTip").slideUp();$uploadBtn.html("上传"); }, 3e3);
	}else{
		$("#failedTip").html(__tip);
		$("#failedTip").removeClass("hide");
		$("#failedTip").slideDown();
		window.setTimeout(function() { $("#failedTip").slideUp();$uploadBtn.html("上传");}, 3e3);
	}
}
</script>
</html>