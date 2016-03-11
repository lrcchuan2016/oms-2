<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="public/charts/highcharts.js"></script>
<script src="public/charts/highcharts-more.js"></script>
<script src="public/charts/highcharts-3d.js"></script>
<script src="public/charts/modules/exporting.js"></script>
<link href="public/css/bootstrap-datetimepicker.css" rel="stylesheet">
<script src="public/js/bootstrap-datetimepicker.min.js"></script>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">数据分析</a></li>
      <li class="active">相册分享统计</li>
    </ol>
   
   <div class="channel-show row">
    	<!---------基本信息------>
    	<div class="panel panel-default clearfix">
              <div class="panel-heading">分享统计</div>
              
              <form action="javascript:void(null);" id="albumShareForm" style="margin: 20px 20px;">
    	
	    		<div class="form-group pull-left" style="width:300px;;">
	               <div>
						<select id="condition" class="form-control pull-left reSearch" style="width:100px;">
		             		<option value="day">按日</option>
		             		<option value="month">按月</option>
		             		<option value="year">按年</option>
		             	</select>
	             	</div>
	             	<div class="input-group" style="width: 150px;">
					    <input type="text" id="datetimepicker1" name="paramMap['date']" readonly class="form-control reSearch" style="cursor:pointer;background-color: #ffffff;">
					    <div class="input-group-addon">
					      <i class="glyphicon glyphicon-th"></i>
					     </div>
					   </div>
	            </div>            
              </form><br><br><br>
              <div class="row">
	      		  <div class="col-md-4" id="albumType" style="min-width:150px;height:400px"></div>
	      		  <div class="col-md-8" id="platformType" style="min-width:600px;height:400px"></div>
     		  </div>
     	</div>
     </div>
</div>

<script>
var W = window.top;
var albumShareData = null;
$(function () {
    
    //初始化Date
    initDate();
    
    //初始化相册分享饼图
    initAlbumShare();
    
    //绑定改变条件重绘事件
    bindReSearch();
});    

function initDate(){
	//绑定年月日选择事件
	bindChangeCondition("#condition");
	
	//选择年月日
	$("#datetimepicker1").datetimepicker({
		format: "yyyy-mm-dd",
         weekStart: 1,  
         autoclose: true,  
         startView: 2,  
         minView: 2,  
         forceParse: false,  
         language: 'zh_CN',
    });
    
    changeDate("day");
    
    $("#datetimepicker1").val(getCurrentDate());
}

function initAlbumShare(){
	var param = $("#albumShareForm").serialize();
	$.ajax({
		type:'post',
		dateType:'json',
		data:param,
		url:'json/albumDataStatistics_QueryAlbumShareDataStatistics',
		success:function(json){
			if(json.result == '00000000'){
				var albumTypeDatas = json.data.albumTypeDatas;
				if(albumTypeDatas.length != 0){
					replace(json.data);
					drawAlbumTypePies(json.data.albumTypeDatas);
					//drawPlatformPies(albumShareData.platformDatas);
				}
			}else W.$.alert("获取数据失败！");
		},
		error:function(e){
			W.$.alert("加载数据异常！");
		}
	})
}

//（年月日）改变
function changeDate(dateStr){
	var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
	if(dateStr == 'day'){
		$("#datetimepicker1").datetimepicker("remove");
		$("#datetimepicker1").val("");
		$("#datetimepicker1").datetimepicker({
		format: "yyyy-mm-dd",
         weekStart: 1,  
         autoclose: true,  
         startView: 2,  
         minView: 2,  
         forceParse: false,  
         language: 'zh_CN',
	    });
	    
	    $("#datetimepicker1").val(getCurrentDate());
	    var d = year+"-"+month+"-"+day;
	    //$.reloadPie(d);
	}else if(dateStr == 'month'){
		$("#datetimepicker1").datetimepicker("remove");
		$("#datetimepicker1").val("");
		$("#datetimepicker1").datetimepicker({
		format: "yyyy-mm",  
         weekStart: 1,  
         autoclose: true,  
         startView: 3,  
         minView: 3,  
         forceParse: false,  
         language: 'zh-CN' 
	    });
	    
	    var d = year+"-"+month;
	    $("#datetimepicker1").val(d);
	    //$.reloadPie(d);
	}else if(dateStr == 'year'){
		$("#datetimepicker1").datetimepicker("remove");
		$("#datetimepicker1").val("");
		$("#datetimepicker1").datetimepicker({
		format: "yyyy",  
         weekStart: 1,  
         autoclose: true,  
         startView: 4,  
         minView: 4,  
         forceParse: false,  
         language: 'zh-CN'
	    });
	    
	     $("#datetimepicker1").val(year);
	     //$.reloadPie(year);
	}else{
		W.$.alert('异常');
	}
}

//获取当前年月日
function getCurrentDate(){
	var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
   return year+"-"+month+"-"+day;
}

function bindChangeCondition(selector){
	$(selector).on("change",function(){
		changeDate($(this).val());
	});
}



function drawAlbumTypePies(_data){

	$('#albumType').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: '相册类型饼图'
        },
        tooltip: {
            pointFormat: '<b>{point.y}本<b><br/><b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Album Type',
            events:{
            	//点击饼图事件
           		 click:function(event){
           		 	var name = event.point.name;
           		 	
           		 	name = name=='普通相册'?'commonAlbums':(name=='主题相册'?'themeAlbums':'timerAlbums');
           		 	//alert(name);
           		 	drawPlatformPies(albumShareData.platformDatas[name]);
           		 	//console.info(albumShareData.platformDatas[event.point.name]);
           		 }
            },
            data: _data
        }],
        credits:{enabled:false}
    });
}

function drawPlatformPies(_data){
	$('#platformType').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: '相册分享平台饼图'
        },
        tooltip: {
            pointFormat: '<b>{point.y}次</b><br><b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Album Share',
            data: _data
        }],
        credits:{enabled:false}
    });
}

function bindReSearch(){
	$(".reSearch").livequery(function(){
		$(this).change(function(){
			initAlbumShare();
			$("#platformType").empty();
		});
	});
}

function replace(json){
	//相册类型数据
	var albumTypeDatas = json.albumTypeDatas;
	for ( var i in albumTypeDatas) {
		if(albumTypeDatas[i][0] == '0') albumTypeDatas[i][0] = '普通相册';
		else if(albumTypeDatas[i][0] == '1') albumTypeDatas[i][0] = '主题相册';
		else albumTypeDatas[i][0] = '时间轴相册';
	}
	json.albumTypeDatas = albumTypeDatas;
	
	/////////////////////////////////////////////
	//相册分享的平台数据
	var platformDatas = json.platformDatas;
	for ( var i in platformDatas) {
		if(platformDatas[i][0] != undefined){
			for(var j in platformDatas[i]){
				if(platformDatas[i][j][0] == '0') platformDatas[i][j][0] = 'QQ';
				else if(platformDatas[i][j][0] == '1') platformDatas[i][j][0] = '微信';
				else platformDatas[i][j][0] = '微博';
			}
		}
	}
	json.platformDatas = platformDatas;
	
}


</script>
</body>
</html>
