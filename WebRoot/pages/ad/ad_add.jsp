<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="public/css/bootstrap-datetimepicker.css" rel="stylesheet">
<link href="public/uploadify/uploadify.css" rel="stylesheet">
<script src="public/uploadify/jquery.uploadify.js"></script>
<script src="public/js/bootstrap-datetimepicker.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="adAction_adList">广告管理</a></li>
      <li class="active">新建广告位</li>
    </ol>
    
    <div class="userListbox ad-edit-box row">
    	
        <div class="panel panel-default clearfix"  style="padding-bottom:20px;">
            <div class="panel-heading">
            	创建广告
            </div>
            
            <form action="javascript:void(null);" method="post" id="adResourceForm">
              <div class="input-area">
              	  <div class="form-group" style="width:200px;">
                    <label for="text_upload">所在广告位</label>
                    <input type="text" class="from-control hide" name="adLocationBean.id" value="${adLocationBean.id }"> 
                    <input type="text" class="from-control hide" name="adLocationBean.ad_name" value="${adLocationBean.ad_name }">
                    <input type="text" class="from-control hide" name="adLocationBean.type" value="0">  
                    <input type="text" class="form-control" id="text_upload" readonly value='<s:property value="adLocationBean.ad_name"/> '>
                  </div>
                  <div class="form-group" style="width:200px;">
                    <label for="text_upload">发布终端</label>
                    <input type="text" class="from-control hide" name="adLocationBean.terminal_type" value="${adLocationBean.terminal_type }"> 
                    <input type="text" class="form-control" id="text_upload" readonly value='<s:if test='adLocationBean.terminal_type=="0"'>全局</s:if><s:if test='adLocationBean.terminal_type=="1"'>手机</s:if><s:if test='adLocationBean.terminal_type=="2"'>电视</s:if><s:if test='adLocationBean.terminal_type=="3"'>网站</s:if>'>
                  </div>
                  <s:if test='adLocationBean.type=="2"'>
                  <div class="form-group">
                    <label for="text_upload">文本</label>
                    <input type="text" name="adResourceBean.content" class="form-control text_upload clear" data-valid="[{rule:'not_empty'}]" id="text_upload" placeholder="请输入[不超过100个字]" maxlength="100">
                  </div>
                  </s:if>
                   <s:else>
                  <div class="form-group">
                  	<div class="pic-area">
                        <!--<img src="public/pic/ad/30.jpg" width="150">-->
                    </div>
                    <label for="file_upload">上传图片/视频/音频</label>
                    <input type="file" id="file_upload">
                    <p class="tip"></p>
                    <s:if test='adLocationBean.type=="0"'>
                    	<p class="help-block fmt" name="*.jpeg;*.jpg">(启动广告图片的格式为：jpg 图片的尺寸为：640*1136)</p>
                    	<p class="help-block">(相册顶部广告的格式为：jpg 图片的尺寸为：640*448)</p>
                    </s:if>
                    <s:elseif test='adLocationBean.type=="1"'>
                    	<p class="help-block fmt" name="*.mp4;*.avi">(视频广告的格式为：MP4)</p>
                    </s:elseif>
                    <s:elseif test='adLocationBean.type=="3"'>
                    	<p class="help-block fmt" name="*.mp3">(音频广告的格式为：MP3)</p>
                    </s:elseif>
                  </div>
                  	<div class="form-group">
                    	<label for="url">上传路劲</label>
                    	<input type="text" name="adResourceBean.content" data-valid="[{rule:'not_empty'}]" readonly class="form-control clear" id="url" placeholder="资源上传路径[上传后获得]">
                  	</div>
                    <input id="resourceKey" type="hidden" class="from-control clear" name="adResourceBean.aliyunKey">
                  </s:else>
                  <div class="form-group">
                    <label for="startTime">开始时间</label>
                    <input type="datetime" name="startTime" id="startTime" data-valid="[{rule:'not_empty'}]" readonly class="form_datetime form-control clear">
                  </div>
                  <div class="form-group">
                    <label for="endTime">结束时间</label>
                    <input type="datetime" name="endTime" id="endTime" data-valid="[{rule:'not_empty'}]" readonly class="form_datetime form-control clear">
                  </div>
                  <div class="form-group">
                    <label for="url">跳转URL</label>
                    <input type="text" name="adResourceBean.url" data-valid="[{rule:'not_empty'}]" class="form-control clear" id="url" placeholder="跳转的URL" maxlength="100">
                  </div>
                  <div class="form-group">
                    <label for="note">广告备注</label>
                    <input type="text" name="adResourceBean.remark" data-valid="[{rule:'not_empty'}]" class="form-control clear" id="note" placeholder="备注[不超过100个字]" maxlength="100">
                  </div>
                  <button type="submit" class="btn btn-primary btn-save">提交</button>
    				<a href="javascript:void(0);" class="btn btn-default back"> 返 回 </a>
              </div>
            </form>
        
    	</div>
    </div>
<script>

var W = window.top;
var formObj;
var adType = '${adLocationBean.terminal_type}';
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
					$("#url").val(dataResult.imageUrl);
					$("#resourceKey").val(imageUrl);
					var id = $(".back").attr("name");
					$(".back").attr("href","adAction_createBack?id="+id+"&&resourceKey="+imageUrl);
					W.$.alert("上传成功！");
				}else{
					W.$.alert("上传失败！");
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
	
	//提交
	$(".btn-save").click(function(){
		var $param = $("#adResourceForm").serialize();
		if(formObj.validate_all()){
			$.ajax({
				type : 'POST',
				dataType : 'json',
				data : $param,
				url : 'json/ad_addResource',
				success : function(json){
					if(json.result == '00000000'){
						W.$.alert("新建广告成功");
						$(".clear").each(function(){$(this).val("")});
						window.location.href="adAction_adList?adType="+adType;
					}else{
						W.$.alert("新增广告失败！    "+json.tip);
					}
				},
				error : function(e){
					W.$.alert("新增广告位失败！   "+json.tip);
				}
			});
		}
	});
	
	//返回
	$(".back").livequery(function(){
		$(this).click(function(){
			var resourceKey = $("#resourceKey");
			if(resourceKey.length<=0){
				window.history.back();
			}else{
				var $param = resourceKey.val();
				if($param != ''){
					$.post("json/ad_createBack",{"adResourceBean.aliyunKey":$param},"json");
				}
				window.history.back();
			}
		});
	});
	
});


$.extend({
	
});
</script>
</body>
</html>
