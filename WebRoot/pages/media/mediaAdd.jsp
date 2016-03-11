<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="public/uploadify/uploadify.css"  rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="container-fluid">
	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">模版管理</a></li>
      <li><a href="mediaPages_musicList">推荐音乐</a></li>
      <li class="active">上传音乐</li>
    </ol>
    <div class="userListbox channel-list" style="margin-right:20px;">
		<div id="successTip" class="alert text-black alert-success hide fade in"></div>
		<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
	    <div class="col-xs-12 no-padder">
			<div class="panel panel-default no-padder">
				<div class="panel-heading">
					<h4>添加媒资&nbsp;&nbsp;<span class="badge"><span class="glyphicon glyphicon-star"></span>号开头的为必填项. &nbsp;&nbsp;&nbsp;<span class="text-warning">[注]:</span>上传较大媒资封面可能耗时比较长，请耐心等待</span></h4>
				</div>
				<div class="panel-body">
					<form id="mediaForm" class="form-horizontal" action="javascript:void(null);">
						<input class="form-control hide" name="mediaList[0].timeLen">
						<input class="form-control hide" name="mediaList[0].byteLen">
						<input class="form-control hide" name="mediaList[0].width">
						<input class="form-control hide" name="mediaList[0].height">
						<input class="form-control hide" name="mediaList[0].audit_status">
						<div class="row col-xs-8">
							<div class="col-xs-12">
								<div class="form-group">
									<label class=""><span class="glyphicon glyphicon-star"></span>存储路径</label>
									<input class="form-control " readonly name="mediaList[0].content" data-valid="[{rule:'not_empty'}]" type="text" placeholder="云存储路径 [不可更改]">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group">
									<label class=""><span class="glyphicon glyphicon-star"></span>媒资编码</label>
									<input class="form-control " type="text" readonly name="mediaList[0].id" maxlength="20" data-valid="[{rule:'not_empty'}]" placeholder="上传文件后自动获取到媒资编码">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group">
									<label class=""><span class="glyphicon glyphicon-star"></span>媒资名称</label>
									<input class="form-control " type="text" name="commonMedias[0].title" maxlength="20" data-valid="[{rule:'not_empty'}]" placeholder="填写媒资名称">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group">
									<label class=""><span class="glyphicon glyphicon-star"></span>媒资类型</label>
									<select name="mediaList[0].type" class="form-control mediaType"></select>
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group">
									<label class=""><span class="glyphicon glyphicon-star"></span>背景音乐类型</label>
									<select name="commonMedias[0].type" class="form-control ">
										<c:if test="${param.option==0 }">
											<option value="0" selected>普通音乐</option>
											<option value="1">主题音乐</option>
										</c:if>
										<c:if test="${param.option==1 }">
											<option value="0">普通音乐</option>
											<option value="1" selected>主题音乐</option>
										</c:if>
									</select>
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group">
									<label class="">媒资备注</label>
									<textarea class="form-control" name="commonMedias[0].detail" rows="4" placeholder="不超过100个字" maxlength="100"></textarea>
								</div>
							</div>
						</div>
						<div class="row col-xs-4">
							<div class="col-xs-12" style="padding-left:0px; padding-right:0px;">
								<label><span class="text-danger">*</span> 上传资源模块</label>
								<div id="fileQueue"></div>
								<div class="thumbnail">
							        <img src="public/img/music.png" class="hide getImageInfo" />
							        <audio src="" class="hide getAudioInfo"></audio>
						        </div>
						        <input type="file" name="image" id="uploadify" />
						        <p><div class="bg-light lter mediaUpload">
						            <button  class="btn btn-sm btn-primary" disabled>上传</button>
						            <button class="btn btn-sm btn-default" disabled>取消上传</button>
						        </div></p>
							</div>
						</div>
					</form>
					
				</div>
				<div class="panel-footer pull-left col-xs-12">
					<button type="button" class="btn btn-primary subBtn" > &nbsp;<span class="glyphicon glyphicon-ok"></span> 提    交 &nbsp; </button>
					<button type="button" class="btn btn-default btn-sm backBtn"> &nbsp;<span class="glyphicon glyphicon-remove"></span> 关    闭&nbsp; </button>
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
var mapObj = new HashMap(),formObj = null,actionObj = null,uploadProgressTimeout = -1,uploadTime = 0,resultKey = "",mediaType=location.search.split("=")[2];
var $uploadBtn = $(".mediaUpload").find("button.btn-primary"),$cancelUploadBtn = $(".mediaUpload").find("button.btn-default");
$(function(){
	formObj = $("#mediaForm").inputValid(valid_message_options);
	initUploadify();
	initMediaTypeList();
	actionObj = new Actions({
		url:"json/media_add", 
		successCallBack:function() {}
	});
	$(".subBtn").click(function(){
		if(formObj.validate_all()){
        	actionObj.submit({ 
        		param:$("#mediaForm").serializeArray(),
        		//successCallBack:function() {reset();}
        		successCallBack:function() {
        			setTimeout(function(){location.href="mediaPages_musicList";}, 2000);
        		}
        	});
        	
		}
	});
	$(".backBtn").click(function(){ window.location.href = "mediaPages_musicList"; });
});

function initUploadify(){
	var typeLimit = "";
	if(mediaType==0){
		typeLimit = "*.jpg;*.gif,*.jpeg,*.png,*.bmp";
	}else if(mediaType==1){
		typeLimit = "*.mp4;*.wma;*.rmvb;*.rm;*.flash;*.avi";
	}else if(mediaType==2){
		typeLimit = "*.mp3;*.m4a";
	}else if(mediaType==3){
		typeLimit = "*.xml";
	}else if(mediaType==4){
		typeLimit = "*.txt;*.doc;*.xls";
	}
	$("#uploadify").uploadify({  
         'swf': 'public/uploadify/uploadify.swf',
         'uploader': 'interface/resourceImage_uploadOSSImage',
         'buttonText': '选择文件',
         'cancelImg': 'public/uploadify/uploadify-cancel.png',  
         'fileObjName': 'image',  
         'fileDesc': '支持格式：'+typeLimit,  
         'fileTypeExts': typeLimit,
         'formData': { 'typeKey': 'music'},
         'queueID': 'fileQueue',
         'auto': false,
         'multi': false,
		 'simUploadLimit':1,
		 'fileSizeLimit':0,
		 'successTimeout':60*60*24,
         'onUploadSuccess' : uploadFinished,//上传完成时触发（每个文件触发一次）
		  onQueueComplete: function(queueData){
		 	$uploadBtn.html("文件上传完成");
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
		 	$uploadBtn.html("正在上传");
		 	$uploadBtn.attr("disabled",true);
		 },
    });  
}

function uploadFinished(file,data,response){
	//console.log(' - 服务器端消息: ' + data+ ' - 是否上传成功: ' + response);
	$cancelUploadBtn.attr("disabled",false);
 	if(data!=null&&response!=null){
 		if(data!=""){
	 		var resultData = eval("("+data+")");
	 		//console.log(resultData);
	 		if(response==true && resultData.result=="00000000"){
	 			$uploadBtn.html("上传成功...");
	 			var resultUrl = resultData.imageUrl;
	 			//resultUrl = resultUrl.replace("cdn","res");
	 			$("input[name='mediaList[0].content']").val(resultUrl);
	 			$("input[name='mediaList[0].id']").val(resultData.productKey);
	 			$("input[name='mediaList[0].byteLen']").val(file.size);
	 			$("input[name='mediaList[0].timeLen']").val(resultData.duration);
	 			$("input[name='mediaList[0].audit_status']").val("0");
	 			$("input[name='commonMedias[0].title']").val(resultData.fileName);
	 			if(file.type==".jpg"||file.type==".gif"||file.type==".jpeg"||file.type==".bmp"||file.type==".png"){
		 			$(".getImageInfo").attr("src", resultUrl).load(function() {
		 				$(".getImageInfo").removeClass("hide");
		 				$("input[name='mediaList[0].width']").val(this.width);
		 				$("input[name='mediaList[0].height']").val(this.height);
		 			});
	 			}
	 			if(file.type==".mp3"||file.type==".m4a"){
	 				$(".getImageInfo").attr("src", "public/img/music.png");
	 				$(".getImageInfo").removeClass("hide");
	 				$(".getAudioInfo").attr("src",resultUrl).load(function(){
	 					$("input[name='mediaList[0].timeLen']").val(this.duration);
	 				});
	 			}
	 			if(file.type==".mp4"||file.type==".avi"||file.type==".wma"
	 				||file.type==".rmvb"||file.type==".rm"||file.type==".flash"){
	 			}
	 			tipsSlideOut("success","上传文件成功,文件名:"+file.name+",文件大小："+file.size+",类型："+file.type);
	 		}else{
	 			$uploadBtn.html("上传失败...");
	 			tipsSlideOut("failed","上传文件失败,文件名:"+file.name+",文件大小："+file.size+",类型："+file.type);
	 		}
 		}else{
 			if(response==true){
	 			$uploadBtn.html("上传成功...");
	 			tipsSlideOut("success","上传文件成功,文件名:"+file.name+",文件大小："+file.size+",类型："+file.type);
	 		}else{
	 			$uploadBtn.html("上传失败...");
	 			tipsSlideOut("failed","上传文件失败,文件名:"+file.name+",文件大小："+file.size+",类型："+file.type);
	 		}
 		}
 		
 	}
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

function initMediaTypeList(){
	$.post("json/media_findTypeList",function(data){
		if(data!=null&&data.list!=null){
			var html = "";
			$.each(data.list,function(){
				if(this.id==mediaType){
					html += "<option value="+this.id+">"+this.description+"</option>";
				}
			});
			$("select[name='mediaList[0].type']").append(html);
		}
	});
}

function reset(){
	$("input").each(function(){
		$(this).val("");
	});
	
	$("textArea").html("");
}
</script>
</html>