<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="public/uploadify/uploadify.css" rel="stylesheet">
<link href="public/iconfont/iconfont.css" rel="stylesheet">
<script src="public/uploadify/jquery.uploadify.js"></script>
<style>
.piclist li { float:left; position:relative;}
.piclist li i{ position:absolute; right:5px; top:5px; background-color:#F30; color:#FFF; border-radius:5px; padding:5px; font-size:13px; border:1px solid #900; cursor:pointer;}

.piclist li img{ width:160px;}
#fileBox {  display:none;}
</style>
</head>
<body>
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="versionAction_terminalList">版本管理</a></li>
      <li><a href="javascript:history.back();">${softVersion.vt.name }</a></li>
      <li class="active">版本编辑</li>
    </ol>
    
    <div class="row channel-add" style="padding:0px 20px;">
            <div class="panel panel-default clearfix">
              <div class="panel-heading">版本编辑</div>
              <form action="javascript:void(null)" id="softVersionForm">
              <div class="panel-body col-md-8">
              	  <div class="form-group">
                    <input type="hidden" class="form-control" value="${softVersion.vt.id }" name="id" data-valid="[{rule:'not_empty'}]">
                  	<input type="hidden" class="form-control" value="${softVersion.createUtc }" name="softVersion.createUtc" data-valid="[{rule:'not_empty'}]">
                  </div>
                  <div class="form-group">
                    <label for="terminal_name">版本号</label>
                    <input type="text" class="form-control" value="${softVersion.versionNum }" name="softVersion.versionNum" data-valid="[{rule:'not_empty'}]" placeholder="如： v1.0.11">
                  </div>
                  
                  <div class="form-group">
                    <label for="terminal_name">文件上传</label>
                    <input type="file" id="uploadify" />
                    <div id="fileQueue"></div>
                    <div id="fileBox">
                    	<a href="${softVersion.softUrl }" class="btn btn-lg btn-default"><i class="iconfont">&#xf00bd;&nbsp;</i><span class="fileName">${softVersion.softUrl }</span></a>
                        <a href="javascript:$('#fileBox').hide()"><i class="iconfont">&#xf013f;</i></a>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="versionMd5">版本编码</label>
                    <input type="text" class="form-control" readonly id="versionMd5" value="${softVersion.id }" name="softVersion.id" data-valid="[{rule:'not_empty'}]" placeholder="上传后获得">
                  	<input type="hidden" class="form-control" id="url" value="${softVersion.softUrl }" name="softVersion.softUrl">
                  </div>
                  
                  <div class="form-group">
                    <label for="terminal_name">SVN URL</label>
                    <input type="text" class="form-control" value="${softVersion.svnUrl }" name="softVersion.svnUrl" data-valid="[{rule:'not_empty'}]" placeholder="如：http://">
                  </div>
                  
                  <div class="form-group">
                    <label>版本说明</label>
                    <textarea class="form-control"  rows="2" placeholder="版本说明" data-valid="[{rule:'not_empty'}]" name="softVersion.description">${softVersion.description }</textarea>
                  </div>
                  
                  <div class="form-group">
                    <label>截图</label>
                    <input type="file" name="uploadifyPic" id="uploadifyPic" />
                    <div id="picQueue"></div>
                    <ul class="piclist">
                    	<c:forEach items="${softVersion.screenShots }" var="shot">
                    		<li><a href="javascript:void(0)"><img id="${shot.id }" src="${shot.url }"></a></li>
                   		</c:forEach>
                    </ul>
                  </div>
                  
                  <div class="form-group shot"></div>
 
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
var terminal_id = '${softVersion.vt.id }';
var W = window.top;
var k = 0,formObj = null;
$(function(){
	formObj = $("#softVersionForm").inputValid(valid_message_options);
	$("#fileBox").show();
	$(".fileName").html($(".fileName").html().split("version/")[1]);
	
	$(".btn-save").click(function(){
		if(formObj.validate_all()){
			$.submitSoftVersion();
		}
	});
	
	$(".piclist li").livequery(function(){
		$(this).hover(function(){
			$(this).append('<i class="iconfont">&#xf013f;</i>');
		},function(){
			$(this).children("i").remove();
		});
	});
	
	$(".piclist li i").livequery(function(){
		$(this).click(function(){
			var mId = $(this).siblings("a").children("img").attr("id");
			W.$.confirm("确定删除?",function(){
				if($("."+mId).val() == undefined){
					$.ajax({
						type:'POST',
						dataType:'json',
						data:{param:'2',id:mId},
						url:'json/version_Del',
						success:function(){
							$("#"+mId).parents("li").remove();
						}
					});
				}else{
					$("."+mId).remove();
					$("#"+mId).parents("li").remove();
				}
			},function(){});
				
			
		});
	});
	
	
	$("#uploadify").uploadify({
		'formData'     : {
			//'type'		:  'ver'
		},
		'swf': 'public/uploadify/uploadify.swf',
		'cancelImg': 'public/uploadify/images/cancel.png',
		'queueID': 'fileQueue',
		'buttonText' : '选择文件',
		'fileObjName' : 'file',
		'fileTypeExts' : '*.rar;*.zip;*.apk;*.ipa,*.pxl,*.deb',
		'uploader' : 'interface/version_upload',
		'auto': true,
		'multi': false,
		'fileSizeLimit':0,
		'successTimeout':60*60*24,
		'onUploadSuccess' : function(file, data , response) {
			var data = eval("(" + data + ")");
			if(data.result == "00000001"){
				W.$.alert("上传失败"+data.tip);
				return;
			}
			try{
				$("#fileBox").show();
				$("#fileBox a.btn span").text(data.name);
				$("#url").val(data.url);
				$("#fileBox a").eq(0).attr("href",data.url);
				$("#fileBox a").eq(1).click(function(){
					$('#fileBox').hide();
					$('#versionMd5').val('');
				});
			}catch(e){
				alert("返回有误");
			}
			
		}
		
	});
	
	
	$("#uploadifyPic").uploadify({
		'formData'     : {
			'typeKey'		:  'vs'
		},
		'swf': 'public/uploadify/uploadify.swf',
		'cancelImg': 'public/uploadify/images/cancel.png',
		'queueID': 'picQueue',
		'buttonText' : '选择图片',
		'fileObjName' : 'image',
		'fileTypeExts' : '*.jpg;*.jpeg;*.png;*.gif',
		'uploader' : 'json/resourceImage_uploadOSSImage',
		'auto': true,
		'multi': true,
		'onUploadSuccess' : function(file, data , response) {
			var data = eval("(" + data + ")");
			if(data.result == "00000001"){
				W.$.alert("上传失败!");
				return;
			}
			try{
				//1、加入返回的数据
					$(".shot").append($.addHtml(data));
				//2、显示截图
				var li = '<li><a href="javascript:void(0)"><img id="'+data.productKey+'" src="'+data.imageUrl+'"></a></li>';
				$("ul.piclist").append(li);
				k++;
			}catch(e){
				alert("返回有误");
			}
		}
		
	});
});


$.extend({
	addHtml : function(data){
		var html = "";
		html += "<input type='hidden' class='form-control "+data.productKey+"' value='"+data.productKey+"' name='screenshots["+k+"].id'>";
		html += "<input type='hidden' class='form-control "+data.productKey+"' value='"+data.imageUrl+"' name='screenshots["+k+"].url'>";
		html += "<input type='hidden' class='form-control "+data.productKey+"' value='"+data.imageUrl.split("versionShot/")[1]+"' name='screenshots["+k+"].name'>";
		return html;
	},
	
	submitSoftVersion : function(){
		$.ajax({
			type:'POST',
			dataType:'json',
			data:$("#softVersionForm").serializeArray(),
			url:'json/version_versionEdit',
			success:function(json){
				if(json.result == '00000000'){
					W.$.alert("更新软件版本成功");
					window.location.href = "versionAction_initPage?param=1&&id="+terminal_id;
				}else{
					W.$.alert("更新软件版本 失败！");
				}
			},
			
			error:function(e){
				W.$.alert("更新软件版本失败！");
			}
		});
	}
});
</script>
</body>
</html>
