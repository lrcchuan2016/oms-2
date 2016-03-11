<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
     double number=Math.random()*100; 
%>
<link href="public/uploadify/uploadify.css"  rel="stylesheet" type="text/css"/>
<style>
#queque .uploadify-queue-item{ float:left; width:22%;}
</style>
</head>
<body>
<div class="container-fluid">
	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">模版管理</a></li>
      <li><a href="decoratePages_decorateList">装饰品列表</a></li>
      <li class="active">批量上传</li>
    </ol>
    <div class="userListbox channel-list" style="margin-right:20px;">
    		<form class="form-inline" action="javascript:void(null);">
    		
			<div class="input-group" style="width:220px;">
			  <span class="input-group-addon">装饰品类别</span>
			  <select id="select" class="form-control select" name="groupName"></select>
			  
			</div>
			
			<div class="clearfix"></div>
			<div class="form-group" style="margin-top: 10px; width:100%;">
                  <input type="file" id="uploadify">
                  <div id="queque">
                  </div>
             </div>
              
            <button onClick="javascript:location.href='decoratePages_decorateList'" type="button" class="btn btn-default  pull-right hide">返回</button>    
		</form>
			
			
		<div class="col-xs-12 padder-tm bg-empty">
			<div id="successTip" class="alert text-black alert-success hide fade in"></div>
			<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
		</div>
		
		<div class="col-xs-12 no-padder" >
			<div class="tab-pane fade in">
				<div class="panel panel-default" style="min-height:450px;">
					<div class="panel-body" id="decorateBody" style="padding:10px 5px;"></div>
				</div>
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
	
	$('#uploadify').uploadify({
		'formData'     : {
			'typeKey': 'decorate',
			'timestamp' : '',
			'token'     : ''
		},
		'swf'      : 'public/uploadify/css/uploadify.swf?v=<%=number%>',
		'uploader' : 'interface/resourceImage_uploadOSSImage',
		'buttonText' : '批量上传',
		'fileObjName': 'image', 
		'fileTypeExts' : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',
		'fileSizeLimit':0,
		'successTimeout':60*60*24,
		'queueID': "queque",
		//上传成功触发
		'onUploadSuccess': function (file, data, response)
		{	
		if(data!=null&&response!=null){
 			if(data!=""){
				var dataResult = eval("("+data+")");
				if(dataResult.result == "00000000"){
					//保存
					var d = {"decorateList[0].id":dataResult.productKey,"decorateList[0].url":dataResult.imageUrl,"decorateList[0].width":dataResult.width,"decorateList[0].height":dataResult.height,"decorateList[0].byteLen":0,"decorateList[0].groupName":$("#select").val(),"decorateList[0].name":dataResult.imageUrl.split("decorate/")[1]};
					$.post("json/decorate_addDecorate",d,function(json){
						if(json.result == '00000000'){
							//上传成功显示图片
							$("#decorateBody").append(htmlTemplate(dataResult));
						}
					},"json");
				}else{
					//$(".tip").html("<font color='ff0000'>上传失败</font>");
				}
				
			}else{
				if(response==true){
					$(".tip").html("<font color='#00ff00'>上传成功</font>");
				}
			}
		}
		},
		//开始上传时触发
		onUploadStart : function(file){
		 	//$(".tip").html("<font color='#00000ff'>正在上传</font>");
		 },
	});
	
	//初始化装饰品类别
	$.post("json/decorate_findDecorateGroupPage","page.startIndex=0&page.pageSize=10000",function(data){
		if(data!=null&&data.list!=null){
			var html = "";
			$.each(data.list.records,function(){
				html += "<option value="+this.id+">"+this.name+"</option>";
			});
			$("#select").append(html);
		}
	});

function htmlTemplate(data){
	var html = '<div id="'+data.productKey+'" class="col-xs-6 col-sm-4 col-md-2 col-lg-2">';
		html += '<div class="item">';
		html += '<div class="pos-rlt">';
		html += '<div class="top"><span class="pull-right m-t-sm m-r-sm badge bg-info"></span></div>';
		html += '<div><img src="'+data.imageUrl+'" alt="" class="r r-2x img-full"></div></div>';
		html += '<div class="padder-vm">';
		html += '</div></div></div>';
	return html;
}

});

	
	


</script>
</html>