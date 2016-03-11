<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="public/charts/highcharts.js"></script>
<script src="public/charts/modules/exporting.js"></script>
<link href="public/css/bootstrap-datetimepicker.css" rel="stylesheet">
<script src="public/js/bootstrap-datetimepicker.min.js"></script>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">数据分析</a></li>
      <li class="active">相片点击统计</li>
    </ol>
    
     <div class="userListbox channel-list">
    	<form class="retrieval" action="javascript:void(null)" method="post" id="keyWordsForm" >
             
             <div class="form-group pull-left" style="width:200px;">
    			<div class="input-group">
                  <div class="input-group-addon">类型相册</div>
    				<select class="form-control type" name="paramMap['type']" onchange="javascript:selectType()">
    					<option value="-1">所有</option>
    					<option value="0">普通相册</option>
    					<option value="1">主题相册</option>
    					<option value="2">时间轴相册</option>
    				</select>
    			</div>
    		</div>
             <div class="form-group pull-left" style="width:250px;">
                <div class="input-group">
                  <div class="input-group-addon">关键字</div>
                  <input type="text" class="form-control keyWords"  name="paramMap['keyWords']" placeholder="家庭号/昵称" data-valid="[{rule:'not_empty'}]"> 
                </div>
              </div>
              <button type="submit" class="btn btn-primary btn-search">搜索</button>
    	</form>
        
    	<table class="photoTable table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
    		<thead>
    			<tr>
    				<td>NO.</td>
    				<td>相册名</td>
    				<td>相片</td>
    				<td>相册类型</td>
    				<td>所属家庭</td>
    				<td>家庭呢称</td>
    				<td>浏览次数</td>
    			</tr>
    		</thead>
    		<tbody class="bg-white">
    				
    		</tbody>
    		<tfoot><tr><td colspan="7">&nbsp;</td></tr></tfoot>
    	</table>
       
        
	</div><!---userListbox-end-->
    
    </div>
    
</div>

<script>
var W = window.top;
var formObj = null,photoTableObj=null
$(function () {
   
   formObj = $("#keyWordsForm").inputValid(valid_message_options);
   
   //初始化照片浏览表格
   initPhotoTableObj();
   
   //改变关键词查询
   changeCondition();
   
   //改变排名前三的颜色
   changeColor();   

});

//改变关键词条件
function changeCondition(){
	$("#keyWordsForm .btn-search").click(function(){
		if(formObj.validate_all()){
			var param = filter($("#keyWordsForm").serialize());
			reSearch(param);
		}
	});
}

/*
*类型变换时触发
*/
function selectType(){
	var param = filter($("#keyWordsForm").serialize(),"-1");
	//重新搜索
	reSearch(param);
}

function filter(param){
	var p = new Array();
	var arrs = param.split("&");
	for ( var i in arrs) {
		var str = arrs[i];
		var s = str.split("=")[1];
		if( s!= "-1" && s.trim().length != 0)
			p.push(str);
	}
	return p.join("&");
}

function initPhotoTableObj(){
	
	photoTableObj = $(".photoTable").tableList({
        url:"json/albumDataStatistics_QueryPhotoClickDatatistics",
        pageSize:12,
        paging:true,
        finishCallBack:function(){},
        template:htmlTemplate
    });
}

function htmlTemplate(index, data, recordIndex){

	var html = '<tr id="'+data[0]+'">';
	html += '<td class="index">'+recordIndex+'</td>';
	html += '<td>'+data[1]+'</td>';
	
	//相片格式化高宽
	html += '<td><img src="'+data[2]+'@170w_100h_2o"></td>';
	html += '<td>'+data[3]+'</td>';
	html += '<td>'+data[4]+'</td>';
	html += '<td>'+data[5]+'</td>';
	html += '<td>'+data[6]+'</td>';
	html += '</tr>';
	return html;
}

function reSearch(param){
	
	photoTableObj.setOptions({param:param});
}

function changeColor(){
	
	$(".index").livequery(function(){
		$(this).each(function(i){
			var td = $(this);
			var html = td.html();
			if(html == '1') 
				td.html("<span class='label label-danger'>1</span>");
			else if(html == '2')
				td.html("<span class='label label-warning'>2</span>");
			else if(html == '3')
				td.html("<span class='label label-success'>3</span>");
		});
	});
	
}

</script>
</body>
</html>
