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
      <li class="active">相片浏览统计</li>
    </ol>
    
     <div class="userListbox">
    	<form action="javascript:void(null);" id="photoBrowseForm">
    	
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
    	
    	<div id="photoBrowse"></div>
    </div>
    
</div>

<script>
var W = window.top;
$(function () {
   
   //初始化日期
	initDate();
	
	//初始化照片浏览
	initPhotoBrowse();
   
    //绑定改变条件事件
     bindReSearch();
});

function bindReSearch(){
	$(".reSearch").livequery(function(){
		$(this).change(function(){
			var param = filter($("#photoBrowseForm").serialize(),"-1");
			loadData(param);
		});
	});
}

function loadData(param){
	$.ajax({
		type:'post',
		dataType:'json',
		data:param,
		url:'json/albumDataStatistics_QueryPhotoSurfedDatatistics',
		success:function(json){
			if(json.result == '00000000'){
				drawLines(json.data);
			}else W.$.alert("加载失败！");
		},
		error:function(e){
			W.$.alert("加载数据异常！");
		}
	});
}

function initDate(){
	//绑定日期改变事件
	bindChangeCondition();

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

function bindChangeCondition(){
	$("#condition").on("change",function(){
		changeDate($(this).val());
	});
}

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

function initPhotoBrowse(){
	
	var param = filter($("#photoBrowseForm").serialize(),"-1");
	loadData(param);
}

function drawLines(_data){
	$("#photoBrowse").highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: '相片浏览时间统计'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: _data.categories
        },
        yAxis: {
            title: {
                text: 'Num (次数)'
            }
        },
        tooltip: {
            enabled: true,
            formatter: function() {
                return '<b>'+ this.series.name +'</b><br/>'+this.x +'点： '+ this.y +'次';
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true
            }
        },
        series: _data.series,
        credits:{enabled:false}
    });
}


</script>
</body>
</html>
