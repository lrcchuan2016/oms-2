<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
       <li><a href="javascript:void(0)">商城管理</a></li>
      <li class="active">商品列表</li>
    </ol>
    
    <div class="userListbox channel-list">
    	<form class="retrieval" action="javascript:void(null)" method="post" id="keyWordsForm" >
             <div class="form-group pull-left" style="width:250px;">
                <div class="input-group">
                  <div class="input-group-addon">关键字</div>
                  <input type="text" class="form-control keyWords"  name="paramMap['keyWords']" placeholder="商品名" data-valid="[{rule:'not_empty'}]"> 
                </div>
              </div>
              <button type="submit" class="btn btn-primary btn-search">搜索</button>
    	</form>
        
    	<table class="productTable table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
    		<thead>
    			<tr>
    				<td>NO.</td>
    				<td>商品名</td>
    				<td>图片</td>
    				<td>价格</td>
    				<td>折扣</td>
    				<td>积分</td>
    				<td>库存</td>
    				<td>介绍</td>
    				<td>上架时间</td>
    				<td>下架时间</td>
    				<td>操作</td>
    			</tr>
    		</thead>
    		<tbody class="bg-white">
    				
    		</tbody>
    		<tfoot><tr><td colspan="11">&nbsp;</td></tr></tfoot>
    	</table>
       
        
	</div><!---userListbox-end-->
    
    </div>
<script>
var W = window.top;
var productTableObj = null,formObj = null;
$(function(){

	formObj = $("#keyWordsForm").inputValid(valid_message_options);
	//初始化商品表
	initProductTable();
	
	//商品下架事件
	bindDelProduct();
	
	//查找商品
	search();
	
	
	
	
});

function search(){
	$(".btn-search").livequery(function(){
		$(this).click(function(){
			if(formObj.validate_all()){
				var $param = $("#keyWordsForm").serialize();
				productTableObj.setOptions({
		            param:$param,
		            url:'json/pay_productList'
	        	});
			}
		});
	});
}

function initProductTable(){
	productTableObj = $(".productTable").tableList({
        url:"json/pay_productList",
        pageSize:12,
        paging:true,
        finishCallBack:function(){},
        template:htmlTemplate
    });
}

function htmlTemplate(index, data, recordIndex){
	var html = '<tr id="'+data.id+'">';
	html +='<td>'+recordIndex+'</td>';
	html +='<td>'+data.name+'</td>';
	html +='<td align="center">'+showCarousel(data.pictures,data.id)+'</td>';
	html +='<td>'+(data.price/100)+'</td>';
	html +='<td>'+(100-data.discount)+'</td>';
	html += '<td>'+data.integral+'</td>'
	if(data.count>=0){
		html +='<td>'+data.count+'</td>';
	}else html +='<td>无限</td>';
	html +='<td>'+data.introduction+'</td>';
	html +='<td>'+formatUTC(data.startSaleUtc,"yyyy-MM-dd hh:mm")+'</td>';
	html +='<td>'+formatUTC(data.endSaleUtc,"yyyy-MM-dd hh:mm")+'</td>';
	html += '<td><a class="btn btn-success btn-sm btn-edit" href="payPages_productEdit?id='+data.id+'">编辑</a><br><a class="btn btn-danger btn-sm btn-del" href="javascript:void(0);">下架</a></td>';
	html +='</tr>';
	
	return html;
}

function bindDelProduct(){
	$(".btn-del").livequery(function(){
		$(this).click(function(){
			var tr = $(this).parents("tr");;
			$.ajax({
				type:'post',
				dataType:'json',
				data:{"paramMap['id']":tr.attr("id")},
				url:'json/pay_delProduct',
				success:function(json){
					if(json.result == "00000000"){
						W.$.alert("商品下架成功！！");
						tr.remove();
					}else W.$.alert("商品下架失败！！");
				},
				error:function(e){
					W.$.alert("商品下架异常！！");
				}
			});
		});
	});
}

function showCarousel(pictures,id){
	var pics = pictures.split("|");
	var html = '<div style="width:200px;height:150px;" id="carousel'+id+'" class="carousel slide" data-ride="carousel"><div class="carousel-inner showPreviews" role="listbox">';
	for ( var i in pics) {
		if(i == 0){
			html += '<div class="item active"><img src="'+pics[i]+'@1e_200w_150h_1c_0i_1o_100Q_1x.jpg"></div>';
		}else html += '<div class="item"><img src="'+pics[i]+'@1e_200w_150h_1c_0i_1o_100Q_1x.jpg"></div>';
	}
	//html += '<div class="item active"><img src="public/pic/template/1.jpg"></div><div class="item"><img src="public/pic/template/2.jpg"></div><div class="item"><img src="public/pic/template/3.jpg"></div>';
	html += '</div><a class="left carousel-control '+id+'" href="#carousel'+id+'" role="button" data-slide="prev"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span><span class="sr-only">Previous</span></a><a class="right carousel-control '+id+'" href="#carousel'+id+'" role="button" data-slide="next"><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><span class="sr-only">Next</span></a></div>';

	$('div[id^=carousel]').collapse({
  		toggle: false
	});

	return html;
}

$.extend({
	
});
</script>
</body>
</html>
