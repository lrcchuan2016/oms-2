<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
  <title>Head Setting</title>
  <meta http-equiv="Content-type" content="text/html;charset=UTF-8" />
  <link rel="stylesheet" href="public/css/cropbox.css" type="text/css" />
</head>
<body>

<div class="row padder-lx">
	<div class="col-xs-12 no-padder bg-light" style="position:fixed;z-index:99;">
		<h2 class="padder-lm">管理员头像设置</h2>
		<div class="col-xs-12 no-padder bg-dark lt" style="height:30px;line-height:30px;">
			<span class="no-padder bg-dark lter" style="width:15px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
			管理员头像设置
			<span></span>
		</div>
	</div>
	<div class="col-xs-12 no-padder bg-light" style="top:80px; min-height:465px;">
		<div class="container" style="padding-top:20px;">
		  <div class="imageBox">
		    <div class="thumbBox"></div>
		    <div class="spinner" style="display: none">Loading...</div>
		  </div>
		  <div class="action"> 
		    <!-- <input type="file" id="file" style=" width: 200px">-->
		    <div class="new-contentarea tc"> <a href="javascript:void(0)" class="upload-img">
		      <label for="upload-file">选择图片</label>
		      </a>
		      <input type="file" class="" name="upload-file" id="upload-file" />
		    </div>
		    <input type="button" id="btnCrop"  class="Btnsty_peyton" value="裁切">
		    <input type="button" id="btnZoomIn" class="Btnsty_peyton" value="+"  >
		    <input type="button" id="btnZoomOut" class="Btnsty_peyton" value="-" >
		  </div>
		  <div class="cropped" style="margin-top:20px;"></div>
		  <div class="croppedup" style="margin-top:20px;"><input type="button" id="upload"  class="upload_peyton" value="&nbsp;上&nbsp;传&nbsp;"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>
</body>
<script type="text/javascript" src="public/js/cropbox.js"></script>
<script type="text/javascript">
var imgData = null;
$(window).load(function() {
	var options ={
		thumbBox: '.thumbBox',
		spinner: '.spinner',
		imgSrc: 'public/img/AAAAAA.gif'
	};
	var cropper = $('.imageBox').cropbox(options);
	$('#upload-file').on('change', function(){
		var reader = new FileReader();
		reader.onload = function(e) {
			options.imgSrc = e.target.result;
			cropper = $('.imageBox').cropbox(options);
		};
		reader.readAsDataURL(this.files[0]);
		this.files = [];
	});
	$('#btnCrop').on('click', function(){
		var img = cropper.getDataURL("image/png");
		$('.cropped').html('');
		imgData = img;
		console.log(imgData);
		$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:43px;margin-top:4px;border-radius:43px;box-shadow:0px 0px 12px #7E7E7E;" ><p>43px*43px</p>');
		$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:96px;margin-top:4px;border-radius:96px;box-shadow:0px 0px 12px #7E7E7E;"><p>96px*96px</p>');
		$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:139px;margin-top:4px;border-radius:139px;box-shadow:0px 0px 12px #7E7E7E;"><p>139px*139px</p>');
	});
	$('#btnZoomIn').on('click', function(){
		cropper.zoomIn();
	});
	$('#btnZoomOut').on('click', function(){
		cropper.zoomOut();
	});
	$("#upload").on('click', function(){
		/*
		 $.ajax({
	        type: 'POST',
	        url: 'json/manager_uploadHeadset',
	        data: '{ "imageData" : "' + imgData + '" }',
	        contentType: 'application/json; charset=utf-8',
	        dataType: 'json',
	        success: function (msg) {
	            alert("Done, Picture Uploaded.");
	        }
	    });
	    */
	    $.post("json/manager_uploadHeadset","imageData="+imgData,function(){
	    	alert("done");
	    });
	});
});
</script>
</html>

