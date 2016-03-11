<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="public/uploadify/uploadify.css"  rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="container-fluid">
	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">模版管理</a></li>
      <li><a href="decoratePages_decorateList">装饰品列表</a></li>
      <li class="active">添加装饰品</li>
    </ol>
    <div class="userListbox channel-list" style="margin-right:20px;">
        <div class="col-xs-12 padder-tm bg-empty" style="margin-top:-20px;">
			<div id="successTip" class="alert text-black alert-success hide fade in"></div>
			<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
		</div>
		<div class="col-xs-12 no-padder">
	        <div class="panel panel-default">
				<div class="panel-heading">
					<h4>添加装饰品&nbsp;&nbsp;
					<span class="badge"><span class="glyphicon glyphicon-star"></span>号开头的为必填项. &nbsp;&nbsp;&nbsp;</h4>
				</div>
				<div class="panel-body">
					<form id="decorateForm" class="form-horizontal" action="javascript:void(null);">
						<div class="row col-xs-8">
							<div class="col-xs-12">
								<div class="form-group">
									<label class=""><span class="glyphicon glyphicon-star"></span>装饰品图片</label>
									<input class="form-control " readonly name="decorateList[0].url" data-valid="[{rule:'not_empty'}]" type="text" placeholder="上传后图片相对路径 [不可更改]">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group">
									<label class=""><span class="glyphicon glyphicon-star"></span>装饰品编码</label>
									<input class="form-control " type="text" readonly name="decorateList[0].id" maxlength="20" data-valid="[{rule:'not_empty'}]" placeholder="上传封面图后自动获取到装饰品编码">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group">
									<label class=""><span class="glyphicon glyphicon-star"></span>装饰品名称</label>
									<input class="form-control " type="text" name="decorateList[0].name" maxlength="20" data-valid="[{rule:'not_empty'}]" placeholder="填写装饰品名称">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group">
									<label class=""><span class="glyphicon glyphicon-star"></span>装饰品类别</label>
									<select name="decorateList[0].groupName" class="form-control"></select>
								</div>
							</div>
						</div>
						<div class="row col-xs-4">
							<div class="col-xs-12" style="padding-left:0px; padding-right:0px;">
								<label><span class="text-danger">*</span> 上传图片模块</label>
	      						<div class="thumbnail imgClip">
					        		<img src="public/img/AAAAAA.gif"/>
					        	</div>
						        <div id="fileQueue"></div>
						        <input type="file" name="image" id="uploadify" />
						        <p class="help-block">（最大1920px*1080px，jpg,png,gif）</p>
						        <input type="hidden" name="decorateList[0].width">
						        <input type="hidden" name="decorateList[0].height">
						        <input type="hidden" name="decorateList[0].byteLen">
						        <div class="decorateUpload">
						            <button  class="btn btn-primary" disabled>上传</button>
						            <button class="btn btn-default" disabled>取消上传</button>
						        </div>
							</div>
						</div>
					</form>
					
				</div>
				<div class="panel-footer pull-left col-xs-12">
					<button type="button" class="btn btn-primary subBtn" > &nbsp;<span class="glyphicon glyphicon-ok"></span> 提    交 &nbsp; </button>
					<button type="button" class="btn btn-default backBtn"> &nbsp;<span class="glyphicon glyphicon-remove"></span> 关    闭&nbsp; </button>
				</div>
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
var mapObj = new HashMap(),formObj = null,actionObj = null,uploadProgressTimeout = -1,uploadTime = 0,resultKey = "";
var $uploadBtn = $(".decorateUpload").find("button.btn-primary"),$cancelUploadBtn = $(".decorateUpload").find("button.btn-default");

$(function(){
	formObj = $("#decorateForm").inputValid(valid_message_options);
	initUploadify();
	initDecorateGroupList();
	actionObj = new Actions({
		url:"json/decorate_addDecorate", 
		successCallBack:function() {}
	});
	$(".subBtn").click(function(){
		if(formObj.validate_all()){
        	actionObj.submit({ 
        		param:$("#decorateForm").serializeArray(),
        		successCallBack:function(){
        			$(':input','#decorateForm')  
					.not(':button, :submit, :reset, :hidden')  
					.val('')  
					.removeAttr('checked')  
					.removeAttr('selected');
					$(".imgClip").find("img").attr("src","public/img/AAAAAA.gif");
        		}
        	});
		}
	});
	$(".backBtn").click(function(){ window.location.href = "decoratePages_decorateList"; });
});

function initUploadify(){
	$("#uploadify").uploadify({  
         'swf': 'public/uploadify/uploadify.swf',
         'uploader': 'interface/resourceImage_uploadOSSImage',
         'buttonText': '选择图片',
         'cancelImg': 'public/uploadify/uploadify-cancel.png',  
         'fileObjName': 'image',  
         'fileDesc': '支持格式：jpg,gif,png,bmp',  
         'fileTypeExts': '*.jpg;*.gif;*.jpeg;*.png;*.bmp',
         'formData': { 'typeKey': 'decorate'},
         'queueID': 'fileQueue',
         'auto': false,
         'multi': false,
         'fileSizeLimit':0,
		 'simUploadLimit':1,
		 'successTimeout':60*60*24,
         'onUploadSuccess' : function(file,data,response) {//上传完成时触发（每个文件触发一次）
			console.log(' - 服务器端消息: ' + data+ ' - 是否上传成功: ' + response);
			$cancelUploadBtn.attr("disabled",false);
		 	if(data!=null&&response!=null){
		 		if(data!=""){
			 		var resultData = eval("("+data+")");
			 		if(response==true && resultData.result=="00000000"){
			 			$uploadBtn.html("上传成功...");
			 			$("input[name='decorateList[0].url']").val(resultData.imageUrl);
			 			$("input[name='decorateList[0].id']").val(resultData.productKey);
			 			$(".imgClip").find("img").attr("src",resultData.imageUrl);
			 			$("input[name='decorateList[0].name']").val(resultData.fileName);
			 			$("input[name='decorateList[0].width']").val(resultData.width);
			 			$("input[name='decorateList[0].height']").val(resultData.height);
			 			$("input[name='decorateList[0].byteLen']").val(file.size);
			 			tipsSlideOut("success","上传图片成功,文件名:"+file.name+",文件大小："+file.size+",类型："+file.type);
			 		}else{
			 			$uploadBtn.html("上传失败...");
			 			tipsSlideOut("failed","上传图片失败,文件名:"+file.name+",文件大小："+file.size+",类型："+file.type);
			 		}
		 		}else{
		 			if(response==true){
			 			$uploadBtn.html("上传成功...");
			 			tipsSlideOut("success","上传图片成功,文件名:"+file.name+",文件大小："+file.size+",类型："+file.type);
			 		}else{
			 			$uploadBtn.html("上传失败...");
			 			tipsSlideOut("failed","上传图片失败,文件名:"+file.name+",文件大小："+file.size+",类型："+file.type);
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

function initDecorateGroupList(){
	$.post("json/decorate_findDecorateGroupPage","page.startIndex=0&page.pageSize=10000",function(data){
		if(data!=null&&data.list!=null){
			var html = "";
			$.each(data.list.records,function(){
				html += "<option value="+this.id+">"+this.name+"</option>";
			});
			$("select[name='decorateList[0].groupName']").append(html);
		}
	});
}
</script>
</html>