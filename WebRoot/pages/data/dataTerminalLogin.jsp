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
      <li class="active">终端登录状况</li>
    </ol>
    
    <div class="channel-show row">
    	<!---------基本信息------>
    	<div class="panel panel-default clearfix">
              <div class="panel-heading">
				    <div class="input-group" style="width: 200px;">
				      <div class="input-group-addon">时间</div>
				      <input type="text" id="datetimepicker1" readonly class="form-control" style="cursor: pointer;background-color: #ffffff;">
				      <div class="input-group-addon">
				      	<i class="glyphicon glyphicon-th"></i>
				      </div>
				  </div>
              </div>
              <div class="row">
	      		  <div class="col-md-12" id="terminalLogin" style="min-width:600px;height:400px"></div>
     		  </div>
     	</div>
     	
     	<div class="panel panel-default clearfix">
              <div class="panel-heading">
				   <div>
					   <span class="pull-left" style="height:34px;padding:6px;">条件</span>
					   <select id="condition" class="form-control pull-left" style="width:100px;">
	             			<option value="day">按日</option>
	             			<option value="month">按月</option>
	             			<option value="year">按年</option>
	             		</select>
             		</div>
             		<div class="input-group" style="width: 150px;">
				      <input type="text" id="datetimepicker2" readonly class="form-control" style="cursor:pointer;background-color: #ffffff;">
				      <div class="input-group-addon">
				      	<i class="glyphicon glyphicon-th"></i>
				      </div>
				    </div>
              </div>
              <div class="row">
	      		  <div class="col-md-12" id="terminalPie" style="min-width:600px;height:400px"></div>
     		  </div>
     	</div>
    </div>
</div>

<script>
var W = window.top;
$(function () {
	//初始化日历选择
	initDate();
	//初始化终端登录状况
    $.initTerminalLoginData();

	//初始化饼图的时间选择
	//initCondition();

	//初始化各终端用户分布
	$.initTerminalData();
    
    //筛选条件改变事件
    $("#condition").livequery(function(){
    	$(this).change(function(){
    		var newValue = $(this).val();
    		changeDate(newValue);
    	});
    });
      
});

function initDate(){
	//选择年月日
	$("#datetimepicker1").datetimepicker({
		format: "yyyy-mm-dd",
         weekStart: 1,  
         autoclose: true,  
         startView: 2,  
         minView: 2,  
         forceParse: false,  
         language: 'zh_CN',
    }).on('changeDate',function(event){
    	$.reload($(this).val());
    });
    
    changeDate("day");
    
    $("#datetimepicker1").val(getCurrentDate());
    $("#datetimepicker2").val(getCurrentDate());
}
//获取当前年月日
function getCurrentDate(){
	var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
   return year+"-"+month+"-"+day;
}

//（年月日）改变
function changeDate(dateStr){
	var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
	if(dateStr == 'day'){
		$("#datetimepicker2").datetimepicker("remove");
		$("#datetimepicker2").val("");
		$("#datetimepicker2").datetimepicker({
		format: "yyyy-mm-dd",
         weekStart: 1,  
         autoclose: true,  
         startView: 2,  
         minView: 2,  
         forceParse: false,  
         language: 'zh_CN',
	    }).on('changeDate',function(event){
	    	$.reloadPie($(this).val());
	    });
	    $("#datetimepicker2").val(getCurrentDate());
	    var d = year+"-"+month+"-"+day;
	    $.reloadPie(d);
	}else if(dateStr == 'month'){
		$("#datetimepicker2").datetimepicker("remove");
		$("#datetimepicker2").val("");
		$("#datetimepicker2").datetimepicker({
		format: "yyyy-mm",  
         weekStart: 1,  
         autoclose: true,  
         startView: 3,  
         minView: 3,  
         forceParse: false,  
         language: 'zh-CN' 
	    }).on('changeDate',function(event){
	    	$.reloadPie($(this).val());
	    });
	    var d = year+"-"+month;
	    $("#datetimepicker2").val(d);
	    $.reloadPie(d);
	}else if(dateStr == 'year'){
		$("#datetimepicker2").datetimepicker("remove");
		$("#datetimepicker2").val("");
		$("#datetimepicker2").datetimepicker({
		format: "yyyy",  
         weekStart: 1,  
         autoclose: true,  
         startView: 4,  
         minView: 4,  
         forceParse: false,  
         language: 'zh-CN'
	    }).on('changeDate',function(event){
	    	$.reloadPie($(this).val());
	    });
	     $("#datetimepicker2").val(year);
	     $.reloadPie(year);
	}else{
		W.$.alert('异常');
	}
}

//绘制线形图
function drawLines(_data){
    $('#terminalLogin').highcharts({
        title: {
            text: '终端用户登录线性图',
            x: -20 //center
        },
        subtitle: {
            text: '',
            x: -20
        },
        xAxis: {
            categories: [0,1, 2, 3, 4, 5, 6,7, 8, 9,10, 11, 12, 13,14,15,16,17,18,19,20,21,22,23]
        },
        yAxis: {
            title: {
                text: 'num'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            formatter: function() {
                return '<b>'+ this.series.name +'</b><br/>'+this.x +'点： '+ this.y +'次';
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: _data,
         credits:{enabled:false}
    });
}
//画饼图
function drawTerminalPie(_data){
	$('#terminalPie').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '各终端用户分布图'
        },
        tooltip: {
            pointFormat: '<b>数量：{point.y}个</b><br><b>百分比：{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: '终端登录数',
            data: _data
        }],
        credits:{enabled:false}
    });
}

$.extend({
	initTerminalLoginData : function(){
		$.ajax({
			type:'post',
			dataType:'json',
			url:'json/dataStatistics_TerminalLoginShow',
			success:function(json){
				if(json.result == '00000000'){
					var data = json.data;
					drawLines(data);
				}else W.$.alert("获取终端登录数据失败！");
			},
			error:function(){
				W.$.alert("获取终端登录数据异常！");
			}
		});
	},
	
	reload : function(_date){
		$.ajax({
			type:'post',
			dataType:'json',
			url:'json/dataStatistics_TerminalLoginShow',
			data:{"paramMap['date']":_date},
			success:function(json){
				if(json.result == '00000000'){
					var data = json.data;
					drawLines(data);
				}else W.$.alert("获取终端登录数据失败！");
			},
			error:function(){
				W.$.alert("获取终端登录数据异常！");
			}
		});
	},
	
	initTerminalData : function(){
		$.ajax({
			type:'post',
			dataType:'json',
			data : {"paramMap['date']":getCurrentDate()},
			url:'json/dataStatistics_QueryTeminalLogin',
			success:function(json){
				if(json.result == '00000000'){
					var data = json.data;
					drawTerminalPie(data);
				}else W.$.alert("获取终端分布数据失败！");
			},
			error:function(){
				W.$.alert("获取终端分布数据异常！");
			}
		});
	},
	
	reloadPie : function(_date){
		$.ajax({
			type:'post',
			dataType:'json',
			url:'json/dataStatistics_QueryTeminalLogin',
			data:{"paramMap['date']":_date},
			success:function(json){
				if(json.result == '00000000'){
					var data = json.data;
					drawTerminalPie(data);
				}else W.$.alert("获取终端登录分布数据失败！");
			},
			error:function(){
				W.$.alert("获取终端登录分布数据异常！");
			}
		});
	}
});
</script>
</body>
</html>
