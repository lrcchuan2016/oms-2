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
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
       <li><a href="javascript:void(0)">商城管理</a></li>
      <li class="active">商品上架</li>
    </ol>
    
    <div class="row channel-add" style="padding:0px 20px;">
            <div class="panel panel-default clearfix">
              <div class="panel-heading">上架商品</div>
              <form action="javascript:void(null)" id="productForm">
              <div class="panel-body col-md-8">
                  <div class="form-group">
                    <label for="productName">商品名称</label>
                    <input type="text" class="form-control" id="productName" name="product.name" data-valid="[{rule:'not_empty'}]" placeholder="商品名称" maxlength="30">
                  </div>

                  <div class="form-group">
                    <label for="price">单价</label>
                    <input type="text" class="form-control" id="price" name="price" data-valid="[{rule:'not_empty'},{rule:'min_value','value':0}]" placeholder="（单位：元，保留两位小数）" maxlength="20">
                  </div>
                  
                  <div class="form-group">
                    <label for="discount">折扣</label>
                    <input type="text" class="form-control" name="product.discount" data-valid="[{rule:'not_empty'},{rule:'integer','message':'请输入整数'},{rule:'min_value','value':0},{rule:'max_value','value':100}]" placeholder="折扣（0-100，如95表示9.5折）" maxlength="3">
                  </div>
                  
                   <div class="form-group">
                    <label for="integral">积分</label>
                    <input type="text" class="form-control" id="integral" name="product.integral" data-valid="[{rule:'not_empty'},{rule:'min_value','value':0}]" placeholder="积分为整数" maxlength="20">
                  </div>
                  
                <div class="form-group">
                    <label for="count">库存</label>
                    <input type="text" class="form-control" name="product.count" data-valid="[{rule:'not_empty'},{rule:'integer','message':'请输入整数'},{rule:'min_value','value':-1}]" placeholder="库存数量(-1位无限量)" maxlength="10">
                  </div>
                  
                  <div class="form-group">
                    <label for="startSaleUtc">上架时间</label>
                    <input type="datetime-local" class="form-control" name="startSaleUtc" data-valid="[{rule:'not_empty'}]" placeholder="上架时间">
                  </div>
                  
                  <div class="form-group">
                    <label for="endSaleUtc">下架时间</label>
                    <input type="datetime-local" class="form-control" name="endSaleUtc" data-valid="[{rule:'not_empty'}]" placeholder="下架时间">
                  </div>
                  
                  <div class="form-group">
                    <label for="introduce">详情</label>
                    <textarea class="form-control" rows="2" placeholder="详情说明" data-valid="[{rule:'not_empty'}]" name="product.introduction"></textarea>
                  </div>
                  
                  <div class="form-group">
                    <label>实物图</label>
                    <input type="file" name="uploadifyPic" id="uploadifyPic" />
                    <div id="picQueue"></div>
                    <ul class="piclist">
                    	
                    </ul>
                  </div>
                  <div class="form-group">
                  		<div class="form-group shot"></div>
 				</div>
 				
              </div>
             </form> 
            </div>
            <div class="form-group">
 					<button type="submit" class="btn btn-primary btn-save">添加</button>
    				<input type="reset" class="btn btn-default" value="重置" onclick="reset()">
 			</div>
            <hr>
    
    			
        </div>
    
</div>

<div style="height:200px;">
</div>
<script>

var W = window.top;
var k = 0,formObj = null;
$(function(){
	formObj = $("#productForm").inputValid(valid_message_options);
	
	$(".btn-save").click(function(){
		if(formObj.validate_all()){
			$.submitProduct();
		}
	});
	
	//数遍悬停
	$(".piclist li").livequery(function(){
		$(this).hover(function(){
			$(this).append('<i class="iconfont">&#xf013f;</i>');
		},function(){
			$(this).children("i").remove();
		});
	});
	
	//删除
	$(".piclist li i").livequery(function(){
		$(this).click(function(){
			var mId = $(this).siblings("a").children("img").attr("id");
			W.$.confirm("确定删除?",function(){
				$("."+mId).remove();
				$("#"+mId).parents("li").remove();
			},function(){});
		});
	});
	
	$("#uploadifyPic").uploadify({
		'formData'     : {
			'typeKey'		:  ''
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
		'fileSizeLimit':0,
		'successTimeout':60*60*60,
		'onUploadSuccess' : function(file, data , response) {
			var data = eval("(" + data + ")");
			if(data.result == "00000001"){
				alert("上传失败!");
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

function reset(){
	document.getElementById('productForm').reset();
	$(".shot").html("");
	$(".piclist").html("");
}

//图片url拼接
function queryPictures(){
	var $this = $(".shot").find("input");
	var url;
	if($this.length>0){
		$this.each(function(i,n){
			if(i==0){
				url = $(this).val();
			}else url += "|"+$(this).val();
		});
	}
	return url;
}


$.extend({
	addHtml : function(data){
		var html = "";
		html += "<input type='hidden' class='form-control' value='"+data.imageUrl+"'>";
		return html;
	},
	
	submitProduct : function(){
		var pictures = queryPictures();
		if(pictures != undefined){
		var param = $("#productForm").serialize()+"&product.pictures="+pictures;
		$.ajax({
			type:'POST',
			dataType:'json',
			data:param,
			url:'json/pay_addProduct',
			success:function(json){
				if(json.result == '00000000'){
					W.$.alert("商品上架成功！");
				}else{
					W.$.alert("商品上架失败！");
				}
			},
			
			error:function(e){
				W.$.alert("商品上架异常！");
			}
		});
		}else W.$.alert("请先上传实物图");
	}
});
</script>
</body>
</html>
