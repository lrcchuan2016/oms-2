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
      <li><a href="javascript:void(0)">渠道管理</a></li>
      <li class="active">渠道编辑</li>
    </ol>
    
    <div class="row channel-add" style="padding:0px 20px;">
            <div class="panel panel-default clearfix">
              <div class="panel-heading">基本信息</div>
              <div class="panel-body col-md-8">
              	<form id="channelForm" action="javascript:void(null);">
                  <div class="form-group clearfix">
                    <label for="uploadLogo">渠道LOGO</label>
                    <input class="form-control hide logoUrl" value="${channel.logoUrl }" name="channel.logoUrl" type="text">
                    <input type="hidden" name="channel.id" value="${channel.id }">
                    <img class="channel_logo" src="${channel.logoUrl }<%="@1e_320w_200h_1c_0i_1o_100Q_1x.jpg" %>" style="display:block;">
                    <input type="file" name="image" id="uploadify">
                    <p class="tip"></p>
                    <p class="help-block">（最大300px*200px，jpg,png,gif）</p>
                    <div class="form-group">
                   		<label for="logoKey">logo编码</label>
                    	<input id="logoKey" class="form-control logoKey"  value="${channel.logoKey }" readonly name="channel.logoKey" type="text" data-valid="[{rule:'not_empty'}]" placeholder="上传logo后获得"/>
                    </div>
                    <!--  
                    <p><div class="bg-light lter templateUpload">
			            <button  class="btn btn btn-primary">上传</button>
			        </div></p>
			        -->
                  </div>
              	
                  <div class="form-group">
                    <label for="channel_name">渠道名称</label>
                    <input type="text" class="form-control" name="channel.name" value="${channel.name }" data-valid="[{rule:'not_empty'}]" maxlength="36" placeholder="渠道商名称">
                  </div>
                  <div class="form-group">
                    <label for="company ">渠道公司全名</label>
                    <input type="text" class="form-control" name="channel.companyName" value="${channel.companyName }" data-valid="[{rule:'not_empty'}]" maxlength="255" placeholder="渠道商公司全名">
                  </div>
                  <div class="form-group">
                    <label for="address ">地址</label>
                    <input type="text" class="form-control" name="channel.address" value="${channel.address }" maxlength="255" placeholder="渠道商公司地址">
                  </div>
                 <div class="form-group">
                    <label for="creater">创建者</label>
                    <input type="text" class="form-control" value="${channel.creater }" name="channel.creater" data-valid="[{rule:'not_empty'}]" maxlength="36" placeholder="创建者">
                  </div>
                  <div class="form-group">
                    <label for="createUtc">创建时间</label>
                    <input type="datetime" id="createUtc" value="${channel.createUtc }" readonly class="form_datetime form-control" data-valid="[{rule:'not_empty'}]" maxlength="255" placeholder="公司成立时间">
                  </div>
                  <div class="form-group">
                    <label>渠道介绍</label>
                    <textarea class="form-control" name="channel.description" rows="4" placeholder="渠道简介">${channel.description }</textarea>
                  </div>
                  
                  <div class="form-group row">
                    <label for="address " style="display:block; line-height:40px;">联系人</label>
                    <input type="hidden" name="contacts[0].id" value="${channel.contacts[0].id }">
                    <input type="hidden" name="contacts[1].id" value="${channel.contacts[1].id }">
                    <div class="linkman clearfix">
                        <div class="input-group col-md-6">
                          <span class="input-group-addon">姓名</span>
                          <input type="text" name="contacts[0].name" value="${channel.contacts[0].name }" data-valid="[{rule:'not_empty'}]" class="form-control">
                        </div>
                        <div class="input-group col-md-6">
                          <span class="input-group-addon">部门</span>
                          <input type="text" name="contacts[0].department" value="${channel.contacts[0].department }" class="form-control">
                        </div>
                        <div class="input-group col-md-6">
                          <span class="input-group-addon">职务</span>
                          <input type="text" name="contacts[0].jobPosition" value="${channel.contacts[0].jobPosition }" class="form-control">
                        </div>
                        <div class="input-group col-md-6">
                          <span class="input-group-addon">邮箱</span>
                          <input type="text" name="contacts[0].email" value="${channel.contacts[0].email }" data-valid="[{rule:'email'}]" class="form-control">
                        </div>
                        <div class="input-group col-md-6">
                          <span class="input-group-addon">手机</span>
                          <input type="text" name="contacts[0].mobile" value="${channel.contacts[0].mobile }" data-valid="[{rule:'not_empty'}]" class="form-control">
                        </div>
                        <div class="input-group col-md-6">
                          <span class="input-group-addon">电话</span>
                          <input type="text" name="contacts[0].phone" value="${channel.contacts[0].phone }" class="form-control">
                        </div>
                    </div>
                    
                    <hr>
                    
                    <div class="linkman clearfix">
                        <div class="input-group col-md-6">
                          <span class="input-group-addon">姓名</span>
                          <input type="text" name="contacts[1].name"  value="${channel.contacts[1].name }" data-valid="[{rule:'not_empty'}]" class="form-control">
                        </div>
                        <div class="input-group col-md-6">
                          <span class="input-group-addon">部门</span>
                          <input type="text" name="contacts[1].department" value="${channel.contacts[1].department }" class="form-control">
                        </div>
                        <div class="input-group col-md-6">
                          <span class="input-group-addon">职务</span>
                          <input type="text" name="contacts[1].jobPosition" value="${channel.contacts[1].jobPosition }" class="form-control">
                        </div>
                        <div class="input-group col-md-6">
                          <span class="input-group-addon">邮箱</span>
                          <input type="text" name="contacts[1].email" value="${channel.contacts[1].email }" data-valid="[{rule:'email'}]" class="form-control">
                        </div>
                        <div class="input-group col-md-6">
                          <span class="input-group-addon">手机</span>
                          <input type="text" name="contacts[1].mobile" value="${channel.contacts[1].mobile }" class="form-control">
                        </div>
                        <div class="input-group col-md-6">
                          <span class="input-group-addon">电话</span>
                          <input type="text" name="contacts[1].phone" value="${channel.contacts[1].phone }" class="form-control">
                        </div>
                    </div>
                  </div>
                </form>
              </div>
            </div>
            <hr>
    			<button type="submit" class="btn btn-primary btnUpdate">更新</button>
    			<button onClick="javascript:history.back()" type="button" class="btn btn-default">返回</button>
        </div>
</div>

<div style="height:200px;"></div>
<script>

var W = window.top;
var actionObj = null,formObj = null;
$(function(){
	$(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii'});
	$("#createUtc").val(formatUTC($("#createUtc").val(),'yyyy-MM-dd hh:mm'));
	formObj = $("#channelForm").inputValid(valid_message_options);
	//更新
	$(".btnUpdate").livequery(function(){
		$(this).click(function(){
			$.updateChannel();
		});
	});

	$('#uploadify').uploadify({
		'formData'     : {
			//'typeKey': 'ad',
			'timestamp' : '',
			'token'     : ''
		},
		'swf'      : 'public/uploadify/uploadify.swf',
		'uploader' : 'interface/resourceImage_uploadOSSImage',
		'buttonText' : '选择文件',
		'fileObjName': 'image', 
		'fileTypeExts' : '*.jpeg;*.jpg;*.png;*.gif',
		'successTimeout':60*60*24,
		//上传成功触发
		'onUploadSuccess': function (file, data, response)
		{	
		if(data!=null&&response!=null){
 			if(data!=""){
				var dataResult = eval("("+data+")");
				if(dataResult.result == "00000000"){
					//成功
					$(".tip").html("<font color='#00ff00'>上传成功</font>");
					$(".logoUrl").val(dataResult.imageUrl);
					$(".logoKey").val(dataResult.imageUrl.split("http://cdn.meijx.cn/")[1]);
					$(".channel_logo").attr("src",dataResult.imageUrl+"@1e_320w_200h_1c_0i_1o_100Q_1x.jpg");
				}else{
					//失败
					$(".tip").html("<font color='#ff0000'>上传失败</font>");
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
		 	//上传中
		 	$(".tip").html("<font color='#0000ff'>正在上传...</font>");
		 },
	});
});


$.extend({
	//更新渠道
	updateChannel : function(){
		if(formObj.validate_all()){
			var data = $("#channelForm").serializeArray();
			$.ajax({
				type:'POST',
				dataType:'json',
				data:data,
				url:'json/channel_edit?createUtc='+$("#createUtc").val(),
				success:function(json){
					if(json.result == "00000000"){
						W.$.alert("更新成功！");
						window.location.href = "channelAction_channelList";
					}else{
						W.$.alert("更新渠道失败");
					}
				},
				
				error : function(){
					W.$.aleret("更新渠道失败");
				}
			});
		}
	}
});
</script>
</body>
</html>
