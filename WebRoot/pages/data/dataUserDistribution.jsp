<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="public/charts/highcharts.js"></script>
<script src="public/charts/modules/exporting.js"></script>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">数据分析</a></li>
      <li class="active">用户分布</li>
    </ol>
    
    <div class="channel-show row">
    	<!---------基本信息------>
    	<div class="panel panel-default clearfix">
              <div class="panel-heading">分布显示</div>
              <div class="row">
	      		  <div class="col-md-8" id="province" style="min-width:600px;height:400px"></div>
	      		  <div class="col-md-4" id="cityname" style="min-width:150px;height:400px"></div>
     		  </div>
     	</div>
     	<div>
     		<a href="http://tongji.baidu.com/web/welcome/login" target="_blank"><span class="glyphicon glyphicon-log-in text-lg">&nbsp;</span><span class="text-info text-lg">登录百度统计查看</span></span></a>
     	</div>
    </div>
</div>

<script>
var W = window.top;
$(function () {
    $.initProvinceData();
    
});

function drawProvincePie(_data){
	$('#province').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '全国用户分布'
        },
        tooltip: {
            pointFormat: '<b>数量：{point.y}个</b><br> <b>百分比：{point.percentage:.1f}%</b>'
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
            name: '用户百分比',
            events:{
           		 click:function(event){
           		 	$.loadCityName(event.point.name);
           		 }
            },
            data: _data
        }],
         credits:{enabled:false}
    });
}

function drawCityPie(_data){
	$('#cityname').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '用户市级分布'
        },
        tooltip: {
            pointFormat: '<b>数量：{point.y}个</b><br> <b>百分比：{point.percentage:.1f}%</b>'
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
            name: '用户百分比',
            data: _data
        }],
        credits:{enabled:false}
    });
	
}

function calData(data){
	var num = 0;
	var arrs = new Array();
	for ( var i in data) {
		var element = data[i];
		if(element[0].trim().length == 0){
			num += element[1];
		}else if(-1 != element[0].indexOf('未知')){
			num += element[1];
		}else arrs.push(element);
		
	}
	arrs.push(['未知',num]);
	return arrs;
}

$.extend({
	initProvinceData : function(){
	
		$.ajax({
			type:'post',
			dataType:'json',
			url:'json/dataStatistics_userDistributionSta',
			success:function(json){
				if(json.result == '00000000'){
					var data = json.data;
					data = calData(data);
					drawProvincePie(data);
				}else W.$.alert("获取用户分布数据失败！");
			},
			error:function(){
				W.$.alert("获取用户分布数据异常！");
			}
		});
	},
	
	loadCityName : function(_province){
		$.ajax({
			type:'POST',
			dataType:'json',
			data:{"paramMap['province']" : _province},
			url:'json/dataStatistics_userCityDistribution',
			success:function(json){
				if(json.result == '00000000'){
					var data = json.data;
					drawCityPie(data);
				}else W.$.alert("载入"+_province+"地区的用户城市分布失败！");
			},
			error:function(e){
				W.$.alert("载入"+_province+"地区的用户城市分布失败！");
			}
		});
	}
	
	
		
})
</script>
</body>
</html>
