<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="public/uploadify/uploadify.css" rel="stylesheet">
<link href="public/iconfont/iconfont.css" rel="stylesheet">
<script src="public/uploadify/jquery.uploadify.min.js"></script>
<style>
.panel-body li{
	float:left;
	padding:5px;
	border:1px solid #eee;
	border-radius:5px;
	width:100px; height:100px;
	margin:5px;
	position:relative;
}
.panel-body li img{
	width:100%; height:100%;
}
.panel-body li .glyphicon{
	position:absolute;
	right:0px; top:0px;
	font-size:18px; color:#F30;
	cursor:pointer;
}
</style>
<head>
<body>
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">公用头像</a></li>
      <li>头像列表</li>
    </ol>
    
    
    <div class="row channel-add" style="padding:0px 20px;">
            <div class="panel panel-default clearfix">
              <div class="panel-body col-md-8">
              	<input type="file" id="uploadAvt">
                <div id="fileQueue"></div>
                <p class="help-block">（最大200px*200px,png,图片）</p>
              </div>
            </div>
    </div>
              
    <div class="container-fluid" style="padding:20px;">
    	<div class="panel panel-default clearfix">
              <div class="panel-heading">头像列表</div>
              <div class="panel-body col-md-8">
              		<ul id="list" class="list">
                    	
                    </ul>
              </div>
        </div>
	</div>
    
   </div>
<script>

var W = window.top;
var arrs = new Array();//做排序用
$(function(){

	$(".panel-body li").livequery(function(){
		$(this).hover(function(){
			$(this).append('<i class="glyphicon glyphicon-remove"></i>');
			
			$(this).css({"cursor":"move"});
		},function(){
			$(this).children("i").remove();

		});
	});
	$(".panel-body li i").livequery(function(){
		$(this).click(function(){
			var id  = $(this).parents("li").attr("id");
			$.deleteAvt(id);
		});
	});
	function initUpload(){
		$("#uploadAvt").uploadify({
			'formData'     : {
				//'typeKey'	:  10,
			},
			'swf': 'public/uploadify/uploadify.swf',
			'cancelImg': 'public/uploadify/images/cancel.png',
			'queueID': 'fileQueue',
			'buttonText' : '上传头像',
			'fileObjName' : 'image',
			'fileTypeExts' : '*.png',
			'uploader' : '/json/resourceImage_uploadOSSImage',
			'auto': true,
			'multi': true,
			'onUploadSuccess' : function(file, data , response) {
				try{
					var data = eval("(" + data + ")");
					//上传回调
					if(data.result == '00000000'){
						$.ajax({
							type:'POST',
							dataType:'json',
							data:{"param['url']":data.imageUrl},
							url:'/json/club_AddClubIcon',
							success:function(json){
								if(data.result === "00000000"){
									$(".panel-body ul").append('<li id="'+json.clubIcon.id+'"><img src="' + json.clubIcon.url + '"></li>');
								}else{
									W.$.alert("上传失败！");
								}
							},
							error:function(e){
								W.$.alert("返回有误");
							}
						});
					}
				}catch(e){
					W.$.alert("返回有误");
				}
				
			}
			
		});
	}
	
	setTimeout(function(){initUpload();},200);
	
	$.post("json/club_ClubIconList",function(json){
		if(json.result == '00000000'){
			var list = json.clubIcons;
			var html = "";
			$.each(list,function(i,data){
				arrs[i] = data;
				html += "<li id='"+data.id+"'><img src='"+data.url+"'></li>";
			});
			$(".list").html(html);
			
			//拖动排序
			dragUI();
			
		}else{
			W.$.alert("加载失败！");
		}
	},"json");
	
	
	
});

function dragUI(){
	var container = document.getElementById("list");
	var sort = Sortable.create(container, {
	  animation: 150,  
	  onUpdate: function (evt){
	     var item = evt.item;
	     var newIndex = evt.newIndex;
	     var oldIndex = evt.oldIndex;
	     var newElement = arrs[oldIndex];   //要移动的元素
	     var oldElement = arrs[newIndex];	//原来位置上的元素
	     var flag = "";
	     var arrIds = new Array();
	     
	     if(newIndex<oldIndex){
	     	arrs.splice(oldIndex,1);   //删除指定位置元素
	     	arrs.splice(newIndex,0,newElement);
	     	flag = "0"   //前拖置0
	     	
	     	for(var i=newIndex;i<=oldIndex;i++){
	     		arrIds.push(arrs[i].id);
	     	}
	     }else{
	     	arrs.splice(newIndex+1,0,newElement);
	     	arrs.splice(oldIndex,1);
	     	flag = "1";   //后拖置1
	     	
	     	for(var i=oldIndex;i<=newIndex;i++){
	     		console.info(i);
	     		arrIds.push(arrs[i].id);
	     	}
	     }
		
		var param = {"param['ids']":arrIds.join(","),"param['flag']":flag};
		
		
		sortSubmit(param);
		
	  }
	});
}

function sortSubmit(param){
	$.ajax({
		type:'post',
		dataType:'json',
		data:param,
		url:'json/club_sortIcon',
		success:function(json){
			if(json.result != "00000000"){
	    		W.$.alert("拖动排序错误！");
	     	}
		},
		error:function(e){
			W.$.alert("排序发生异常！");
		}
	});
}


$.extend({
	deleteAvt : function(_id){
		W.$.confirm(
			"是否要删除头像:"+_id+"？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"param['id']" : _id
					},
					url:'json/club_DelIcon', //API
					success:function(json) {
						//删除成功回调
						if(json.result == "00000000"){
							W.$.alert("删除成功！");
							$("#" + _id).remove(); //删除行
						}else {
							W.$.alert("删除失败！");
						}
					},
					error: function(data) {
						W.$.alert("删除失败！");
					}
				});
			},
			function(){
				//alert("取消按钮");
			}
		);
	}
});
</script>
</body>
</html>
