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
      <li class="active">制作时间统计</li>
    </ol>
    
     <div class="userListbox">
    	<form action="javascript:void(null);" id="albumMakeForm">
    	
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
              
    		 <div class="form-group pull-left" style="width:200px;">
                <div class="input-group">
                  <div class="input-group-addon">终端类型</div>
    				<select class="form-control reSearch" name="paramMap['terminalType']">
    					<option value="-1">全部</option>
    					<option value="0">未知</option>
    					<option value="1">机顶盒</option>
    					<option value="2">WEB公版</option>
    					<option value="3">Android手机</option>
    					<option value="4">Android Pad</option>
    					<option value="5">IOS手机</option>
    					<option value="6">IOS Pad</option>
    					<option value="7">PC</option>
    				</select>
    			</div>
    		</div>
    		
    	</form>
    </div>
    
	    <div class="row">
		      <div class="col-md-12" id="makeTime" style="min-width:400px;height:400px"></div>
	     </div>
	     <br><br>
	     <div class="row">
	    	<div class="col-md-12" id="albumPage" style="min-width:200px;height:400px"></div>
	    </div>
    
   
</div>

<script>
var W = window.top;
$(function () {
	
	//设置highcharts全局设置
	setHightCharts();
	
	 //初始化Date
    initDate();

	initAlbumMakeData();
	
	//绑定改变条件事件
	bindChangeCondition(".reSearch");
});

//设置全局hightCharts
function setHightCharts(){
	Highcharts.setOptions({
		global: {
			useUTC: false
		}
	});	

}

function initDate(){
	//绑定年月日选择事件
	bindChangeDate("#condition");
	
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

function bindChangeDate(selector){
	$(selector).on("change",function(){
		changeDate($(this).val());
	});
}

function initAlbumMakeData(){
	var param = filter($("#albumMakeForm").serialize(),"-1");

	loadData(param);

}

function drawRangeLines(_data){

	
	//####################
	$('#makeTime').highcharts({
		title: {
			text: '相册制作时间统计'
		},
		
		xAxis: {
			type: 'datetime',
		},
		
		yAxis: {
			title: {
				text: null
			}
		},
		
		tooltip: {
			crosshairs: true,
			shared: true,
			valueSuffix: '分钟'
		},
		
		legend: {
		},
		
		series: [{
			name: 'time',
			data: _data.averages,
			zIndex: 1,
			marker: {
				fillColor: 'white',
				lineWidth: 2,
				lineColor: Highcharts.getOptions().colors[0]
			}
			
		}, {
			name: 'Range',
			data: _data.ranges,
			type: 'arearange',
			lineWidth: 0,
			linkedTo: ':previous',
			color: Highcharts.getOptions().colors[0],
			fillOpacity: 0.3,
			zIndex: 0
		}],
		credits:{enabled:false}
	
	});
}

function drawPageNumPie(_data){
	$('#albumPage').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: '相册制作页数'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
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
            name: '百分比',
            data: _data
        }],
        credits:{enabled:false}
    });
}

//改变条件事件
function bindChangeCondition(selector){
	$(selector).livequery(function(){
		$(this).change(function(){
			var param = filter($("#albumMakeForm").serialize(),"-1");
			loadData(param);
		});
	});
}

//过滤值为-1的参数（即值为全部）
function filter(param,str){
	var arrs = param.split("&");
	var sParam = "";
	for ( var i in arrs) {
		var nameValue = arrs[i].split("=");
		if(nameValue[1] != str)
		 	sParam += "&"+arrs[i]; 
	}
	
	return sParam.substring(1);
}

function loadData(param){
	$.ajax({
		type:'post',
		dataType:'json',
		data:param,
		url:'json/albumDataStatistics_QueryAlbumMakeDatatistics',
		success:function(json){
			if(json.result == '00000000'){
			
				//console.info(json.albumMakeTimes);
				//分离封装数据
				
				drawRangeLines(packagingData(json.albumMakeTimes));
				
				if(json.albumPageNums.length != 0)
					drawPageNumPie(json.albumPageNums);
				
			}else W.$.alert("加载数据失败！");
		
		},
		error:function(e){
			W.$.alert("加载数据异常！");
		}
	});
}

//打包数据成区域范围图所需的数据结构
function packagingData(data){
	var avgs = new Array();
	var ranges = new Array();
	for(var i in data){
		var albumMakeData = data[i];
		for(var j in albumMakeData){
			var albumData = albumMakeData[j]
			
			for(var k in albumData){
				if(k>0){
					if(typeof(albumData[k]) != 'number')
						albumData[k] = 0;
					else albumData[k] = albumData[k]/(1000*60);   //ms转换成minute
				}
			}
			
			var avg = new Array();
			avg[0] = albumData[0];
			avg[1] = albumData.pop();
			
			avgs.push(avg);
			ranges.push(albumData);
			
			var date = new Date();
			date.setTime(albumData[0]);
			//console.info(date.toString());
		}
		
	}
	
	var obj = new Object();
	obj.averages = avgs;
	obj.ranges = ranges;
	
	return obj;
}

</script>
</body>
</html>
