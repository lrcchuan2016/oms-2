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
      <li class="active">家庭成员数量统计</li>
    </ol>
    
    <div class="channel-show row">
    	<!---------基本信息------>
    	<div class="panel panel-default clearfix">
              <div class="panel-heading">成员数量统计显示</div>
              <div class="row panel-body">
	      		  <div class="col-md-12 col-sm-12" id="clubMember" style="min-width:600px;min-height:400px"></div>
     		  </div>
     	</div>
     	
    </div>
</div>

<script>
var W = window.top;
$(function () {


    //初始化饼图
    initClubMemeberPie();
    
});

function initClubMemeberPie(){
	$.ajax({
		type:'post',
		dataType:'json',
		url:'json/dataStatistics_QueryClubMemebrNums',
		success:function(json){
			if(json.result == '00000000'){
				var data = handleData(json.data);
				drawClubMemberPie(data);
			}else 
				W.$.alert("获取数据发生错误！");
		},
		error:function(e){
			W.$.alert("获取数据发生异常！");
		}
	});
}

function handleData(data){
	var num = 0;
	var arrs = new Array();
	for ( var i in data) {
		var element = data[i];
		if(parseInt(element[0])>5){
			num += element[1];
		}else{
			element[0] = element[0].toString();
			arrs.push(element);
		}
	}
	
	arrs.push(['大于5',num]);
	
	return arrs;
}

function drawClubMemberPie(_data){
	$('#clubMember').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '家庭成员数量比例'
           
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
                    format: '<b>{point.name}个</b>: {point.percentage:.1f} %',
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



</script>
</body>
</html>
