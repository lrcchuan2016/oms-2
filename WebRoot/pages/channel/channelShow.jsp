<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="public/charts/highcharts.js"></script>
<script src="public/charts/modules/exporting.js"></script>
</head>
<body>
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="channelAction_channelList">渠道列表</a></li>
      <li class="active">查看渠道</li>
    </ol>
    
    <div class="channel-show row">
    	<!---------基本信息------>
    	<div class="panel panel-default clearfix">
              <div class="panel-heading">基本信息</div>
              <table class="table">
              	<tbody>
                	<tr id="${channel.id }" class="channelId">
                    	<td class="col-md-3">
                        	<img src="${channel.logoUrl }<%="@1e_320w_200h_1c_0i_1o_100Q_1x.jpg" %>" width="80%">
                        </td>
                    	<td class="col-md-4">
                        	<h4><strong>${channel.name }</strong></h4>
                             <ul>
                                <li><span class="option">渠道全称：</span><span class="info">${channel.companyName }</span></li>
                                <li><span class="option">地　　址：</span><span class="info">${channel.address }</span></li>
                                <li><span class="option">创建时间：</span><span class="info createUtc">${channel.createUtc }</span></li>
                                <li><span class="option">创&nbsp;建&nbsp;者：</span><span class="info">${channel.creater }</span></li>
                             </ul>
                        </td>
                    	<td class="col-md-5">
                		<p>${channel.description }</p>
                        </td>
                    </tr>
                </tbody>
              </table>
              
              
              <table class="table">
              	<tbody>
              		<tr>
                    	<td>联系人</td>
                    	<td>姓名</td>
                    	<td>部门</td>
                    	<td>职务</td>
                    	<td>手机号码</td>
                    	<td>电话号码</td>
                    	<td>邮箱</td>
                    </tr>
                	<tr>
                    	<td>联系人1</td>
                    	<td>${channel.contacts[0].name }</td>
                    	<td>${channel.contacts[0].department }</td>
                    	<td>${channel.contacts[0].jobPosition }</td>
                    	<td>${channel.contacts[0].mobile }</td>
                    	<td>${channel.contacts[0].phone }</td>
                    	<td>${channel.contacts[0].email }</td>
                    </tr>
                	<tr>
                    	<td>联系人2</td>
                    	<td>${channel.contacts[1].name }</td>
                    	<td>${channel.contacts[1].department }</td>
                    	<td>${channel.contacts[1].jobPosition }</td>
                    	<td>${channel.contacts[1].mobile }</td>
                    	<td>${channel.contacts[1].phone }</td>
                    	<td>${channel.contacts[1].email }</td>
                    </tr>
                </tbody>
              </table>
        </div><!---------基本信息END------>
        
        <!---------运营情况------>
        <div class="panel panel-default clearfix">
              <div class="panel-heading">运营情况</div>
              	<!--左边表格-->
                <div class="col-md-6" style="padding:0px; border-right:2px solid #ddd;">
                    <table class="table">
                        <thead>
                            <tr>
                                <td style="width:20%">指标</td>
                                <td style="width:30%">数据</td>
                                <td style="width:50%">排名</td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="operateData">
                                <td>覆盖用户</td>
                                <td>${channel.coverNum }</td>
                                <td>1</td>
                            </tr>
                            <tr class="operateData">
                                <td>安装率</td>
                                <td>8.9%</td>
                                <td>1</td>
                            </tr>
                            <tr class="operateData">
                                <td>注册用户数</td>
                                <td>1574245</td>
                                <td>2</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <!--右边表格-->
                <div class="col-md-6" style="padding:0px;">
                    <table class="table">
                        <thead>
                            <tr>
                                <td style="width:20%">指标</td>
                                <td style="width:30%">数据</td>
                                <td style="width:50%">排名</td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="operateData">
                                <td>转化率</td>
                                <td>15.54%</td>
                                <td>1</td>
                            </tr>
                            <tr class="operateData">
                                <td>转化增长率</td>
                                <td>0.75%</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
        </div><!---------运营情况-END----->
        
        
        <!---------覆盖用户------>
        <div id="coverUserBox" class="panel panel-default clearfix">
              <div class="panel-heading">
              	&nbsp;<span class="pull-left">覆盖用户</span>
                <select id="coverUserYear" class="form-control pull-left" style="width:100px; margin:-6px auto auto 30px;">
                  <option>2015</option>
                  <option>2016</option>
                  <option>2017</option>
                </select>
              </div>
              <div id="coverTheUserCharts" class="charts-box">
              	
              </div>
              <div class="row table_charts_data">
              <table class="table">
                <tbody>
                    <tr>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                    </tr>
                </tbody>
             </table>
            </div>
        </div><!---------覆盖用户-END----->
        
        <!---------注册用户数------>
        <div id="regUsers" class="panel panel-default clearfix">
              <div class="panel-heading">
              	&nbsp;<span class="pull-left">注册用户数</span>
                <select id="regUsersYear" class="form-control pull-left" style="width:100px; margin:-6px auto auto 30px;">
                  <option>2015</option>
                  <option>2016</option>
                  <option>2017</option>
                </select>
              </div>
              <div id="regUsersCharts" class="charts-box">
              	
              </div>
              <div class="row table_charts_data">
              <table class="table">
                <tbody>
                    <tr>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                    </tr>
                </tbody>
             </table>
            </div>
        </div><!---------注册用户数-END----->
        
        <!---------安装用户数------>
        <div id="instUsers" class="panel panel-default clearfix">
              <div class="panel-heading">
              	&nbsp;<span class="pull-left">安装用户数</span>
                <select id="instUsersYear" class="form-control pull-left" style="width:100px; margin:-6px auto auto 30px;">
                  <option>2015</option>
                  <option>2016</option>
                  <option>2017</option>
                </select>
              </div>
              <div id="instUsersCharts" class="charts-box">
              	
              </div>
              <div class="row table_charts_data">
              <table class="table">
                <tbody>
                    <tr>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                    </tr>
                </tbody>
             </table>
            </div>
        </div><!---------安装用户数-END----->
    </div>
</div>

<div style="height:100px;">
</div>


<div id="addMonthDateDialog" class="modal" tabindex="-1" style="display:none; background-color:rgba(0,0,0,0.4); overflow:hidden;">
    <div class="modal-dialog" style="width:500px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4></h4>
            </div>
            <div class="modal-body">
                <form id="monthDateForm" class="form-horizontal" action="javascript:void(null);">
                	<!--年-->
                	<input id="year" name="year" type="hidden">
                    <!--月-->
                	<input id="month" name="month" type="hidden">
                    
                    <div class="form-group">
                        <label class="col-xs-3 control-label">智能电视</label>
                        <div class="col-xs-8"><input id="tv_number" class="form-control input-sm r-lg" data-valid="[{rule:'not_empty'},{rule:'integer',message:'请输入整数'}]" type="text"  placeholder="请输入整数" maxlength="11"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">　机顶盒</label>
                        <div class="col-xs-8"><input id="stb_number" class="form-control input-sm r-lg" data-valid="[{rule:'not_empty'},{rule:'integer',message:'请输入整数'}]" type="text" placeholder="请输入整数" maxlength="11"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">广电双模</label>
                        <div class="col-xs-8"><input id="dvb_number" class="form-control input-sm r-lg" data-valid="[{rule:'not_empty'},{rule:'integer',message:'请输入整数'}]" type="text" placeholder="请输入整数" maxlength="11"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">应用商店</label>
                        <div class="col-xs-8"><input id="store_number" class="form-control input-sm r-lg" type="text" data-valid="[{rule:'not_empty'},{rule:'integer',message:'请输入整数'}]" placeholder="请输入整数" maxlength="11"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">其它</label>
                        <div class="col-xs-8"><input id="other_number" class="form-control input-sm r-lg" type="text" data-valid="[{rule:'not_empty'},{rule:'integer',message:'请输入整数'}]" placeholder="请输入整数" maxlength="11"></div>
                    </div>
                </form>
            </div>
            <div class="modal-footer text-center-xs">
                <button type="button" class="btn btn-default r-lg btn-sm" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span> 取&nbsp;消
                </button>
                <button type="button" class="btn btn-default r-lg btn-sm btn-sub">
                    <span class="glyphicon glyphicon-ok"></span> 确&nbsp;认
                </button>
            </div>
        </div>
    </div>
</div>


<script>

var W = window.top;
var formObj = null;
/*=================初始化的变量========================*/
var channelId = "${channel.id}" ;
var yearRegUsersData = [0,0,0,0,0,0,0,0,0,0,0,0]; //注册用户数初始化数据
var yearInstUsersData = [0,0,0,0,0,0,0,0,0,0,0,0];
var yearUserCoversData = [0,0,0,0,0,0,0,0,0,0,0,0];
$(function () {
	formObj = $("#monthDateForm").inputValid(valid_message_options);
	/*==============初始化本年数据=====================*/
	$.initOperate();
	$.initData();
	/*===============格式化时间================*/
	$(".createUtc").html(formatUTC($(".createUtc").html(),"yyyy-MM-dd"));
	/*=============================月覆盖数填写弹出框，取消按钮===============================*/
	$("#addMonthDateDialog").find(".btn-sm").eq(0).click(function(){
		$("#addMonthDateDialog").hide();
	});

	
	/*=============================覆盖用户数===============================*/
	//覆盖用户数初始化
    //$.initCharts('#coverTheUserCharts',"覆盖用户数",yearUserCoversData);
	
	//改变覆盖用户数的年份
	$("#coverUserYear").change(function(){
		var year = $(this).val();
		$.getCoverUserYear(year);
	});
	
	$("#coverUserBox .table_charts_data td").click(function(){
		var year = $("#coverUserYear").val();
		var month = $(this).index("#coverUserBox .table_charts_data td");
		$.uploadCoverUserMonth(year , month);
	});
	/*=============================覆盖用户数=END===========================*/
	
	/*=============================注册用户数===============================*/
	//注册用户数初始化
    //$.initCharts('#regUsersCharts',"注册用户数",yearRegUsersData,["#79b86c"]);
	
	//改变注册用户数的年份
	$("#regUsersYear").change(function(){
		var year = $(this).val();
		$.getRegUsersYear(year);
	});
	
	$("#regUsers .table_charts_data td").click(function(){
		//alert(0);
		var year = $("#regUsersYear").val();
		var month = $(this).index("#regUsers .table_charts_data td");
		$.uploadRegUsersMonth(year , month);
	});
	/*=============================注册用户数=END===========================*/
	
	/*=============================安装用户数===============================*/
	//安装用户数初始化
    //$.initCharts('#instUsersCharts',"安装用户数",yearInstUsersData,["#CC0033"]);
	
	//改变注册用户数的年份
	$("#instUsersYear").change(function(){
		var year = $(this).val();
		$.getInstUsersYear(year);
	});
	
	$("#instUsers .table_charts_data td").click(function(){
		var year = $("#instUsersYear").val();
		var month = $(this).index("#instUsers .table_charts_data td");
		$.uploadInstUsersMonth(year , month);
	});
	/*=============================安装用户数=END===========================*/
});


$.extend({
	initData : function(){
		var date = new Date();
		var year = date.getFullYear();
		$.getCoverUserYear(year);
		$.getRegUsersYear(year);
		$.getInstUsersYear(year);
	},
	//初始化运营的参数
	initOperate : function(){
		$.post("json/channel_Operate",{channelId:channelId},function(json){
			if(json.result == '00000000'){
				var array = json.array;
				var tr = $(".operateData");
				for(var i in array){
					//alert(array[i][0]);
					if(i == 1 || i == 3 || i == 4){
						tr.eq(i).children("td").eq(1).html(Math.round(array[i][0]*10000)/100+"%");
					}else{
						tr.eq(i).children("td").eq(1).html(array[i][0]);
					}
					tr.eq(i).children("td").eq(2).html(array[i][1]);
				}
			}else{
				W.$.alert("请求失败");
			}
		},"json");
	},
	//用户覆盖数- 年份查询
	getCoverUserYear : function(_year){
		$.ajax({
			type:"POST",
			dataType:"json",
			data:{
				keyWords:'0',
				year : _year,
				channelId : $(".channelId").attr("id")
			},
			url:'json/channel_searchChannelData', //API
			success:function(json) {
				if(json.result == "00000000"){
					var yearData = json.yearData;
					if(null != yearData){
						for(var i in yearData){
							yearUserCoversData[i] = yearData[i];
						}
					}else{
						yearUserCoversData = [0,0,0,0,0,0,0,0,0,0,0,0];
					}
					//画图表
					$.initCharts('#coverTheUserCharts',"覆盖用户数",yearUserCoversData);
				}else {
					W.$.alert("错误！");
				}
			},
			error: function(data) {
				W.$.alert("查询失败！");
			}
		});
	},
	//更新某月的覆盖用户数量
	uploadCoverUserMonth : function(_year,_month){
		//alert(formObj.validate_all());
			$.showDialog(0,_year,_month,function(_count){
			if(formObj.validate_all()){
			$.ajax({
				type:"POST",
				dataType:"json",
				data:{
					keyWords : '0',
					year : _year,
					month : _month,
					channelId : channelId,
					tvNumber : $("#tv_number").val(),
					stbNumber: $("#stb_number").val(),
					dvbNumber: $("#dvb_number").val(),
					storeNumber: $("#store_number").val(),
					otherNumber:$("#other_number").val()
				},
				url:'json/channel_updateChannelData', //API
				success:function(json) {
					if(json.result == "00000000"){
						//更新月数据成功后，查询该年的数据，并显示
						yearUserCoversData[_month] = parseInt(_count);
						$.initCharts('#coverTheUserCharts',"覆盖用户数",yearUserCoversData);
						$.initOperate();
					}else {
						W.$.alert("错误！");
					}
				},
				error: function(data) {
					W.$.alert("查询失败！");
				},
				complete: function(){
					$("#addMonthDateDialog").hide();
				}
			});
			}
		});
	},
	
	//注册用户数- 年份查询
	getRegUsersYear : function(_year){
		$.ajax({
			type:"POST",
			dataType:"json",
			data:{
				keyWords:'1',
				year : _year,
				channelId : $(".channelId").attr("id")
			},
			url:'json/channel_searchChannelData', //注册用户数API
			success:function(json) {
				if(json.result == "00000000"){
					var yearData = json.yearData;
					if(null != yearData){
						for(var i in yearData){
							yearRegUsersData[i] = yearData[i];
						}
					}else{
						yearRegUsersData = [0,0,0,0,0,0,0,0,0,0,0,0];
					}
					//画图表
					$.initCharts('#regUsersCharts',"注册用户数",yearRegUsersData,["#79b86c"]);
				}else {
					W.$.alert("错误！");
				}
			},
			error: function(data) {
				W.$.alert("查询失败！");
			}
		});
	},
	//更新某月的注册用户数量
	uploadRegUsersMonth : function(_year,_month){
		$.showDialog(1,_year,_month,function(_count){
		if(formObj.validate_all()){
			$.ajax({
				type:"POST",
				dataType:"json",
				data:{
					keyWords : '1',
					year : _year,
					month : _month,
					channelId : channelId,
					tvNumber : $("#tv_number").val(),
					stbNumber: $("#stb_number").val(),
					dvbNumber: $("#dvb_number").val(),
					storeNumber: $("#store_number").val(),
					otherNumber: $("#other_number").val(),
				},
				url:'json/channel_updateChannelData', //API
				success:function(json) {
					if(json.result == "00000000"){
						//更新月数据成功后，查询该年的数据，并显示
						yearRegUsersData[_month] = parseInt(_count);
						$.initCharts('#regUsersCharts',"注册用户数",yearRegUsersData,["#79b86c"]);
						$.initOperate();
					}else {
						W.$.alert("错误！");
					}
				},
				error: function(data) {
					W.$.alert("查询失败！");
				},
				complete: function(){
					$("#addMonthDateDialog").hide();
				}
			});
			}
		});
	},
	//安装用户数
	getInstUsersYear : function(_year){
		$.ajax({
			type:"POST",
			dataType:"json",
			data:{
				keyWords:'2',
				year : _year,
				channelId : $(".channelId").attr("id")
			},
			url:'json/channel_searchChannelData', //注册用户数API
			success:function(json) {
				if(json.result == "00000000"){
					var yearData = json.yearData;
					if(null != yearData){
						for(var i in yearData){
							yearInstUsersData[i] = yearData[i];
						}
					}else{
						yearInstUsersData = [0,0,0,0,0,0,0,0,0,0,0,0];
					}
					//画图表
					$.initCharts('#instUsersCharts',"安装用户数",yearInstUsersData,["#CC0033"]);
				}else {
					W.$.alert("错误！");
				}
			},
			error: function(data) {
				W.$.alert("查询失败！");
			}
		});
	},
	
	//更新某月的安装用户数量
	uploadInstUsersMonth : function(_year,_month){
		$.showDialog(2,_year,_month,function(_count){
		if(formObj.validate_all()){
			$.ajax({
				type:"POST",
				dataType:"json",
				data:{
					keyWords : '2',
					year : _year,
					month : _month,
					channelId : channelId,
					tvNumber : $("#tv_number").val(),
					stbNumber: $("#stb_number").val(),
					dvbNumber: $("#dvb_number").val(),
					storeNumber: $("#store_number").val(),
					otherNumber:$("#other_number").val()
				},
				url:'json/channel_updateChannelData', //API
				success:function(json) {
					if(json.result == "00000000"){
						//更新月数据成功后，查询该年的数据，并显示
						yearInstUsersData[_month] = parseInt(_count);
						$.initCharts('#instUsersCharts',"注册用户数",yearInstUsersData,["#CC0033"]);
						$.initOperate();
					}else {
						W.$.alert("错误！");
					}
				},
				error: function(data) {
					W.$.alert("查询失败！");
				},
				complete: function(){
					$("#addMonthDateDialog").hide();
				}
			});
			}
		});
	},
	
	//弹出框
	showDialog : function(_type , _year , _month ,_fun){
		//type 0:覆盖用户  1:注册用户,2:安装用户
		$("#addMonthDateDialog input").val("");
		$("#type").val(_type);
		$("#year").val(_year);
		$("#month").val(_month);
		var winH = $(window).height();
		$("#addMonthDateDialog .modal-content").css("margin-top",(winH - 450)/2 + "px");
		$("#addMonthDateDialog").show();
		$("#addMonthDateDialog .modal-header h4").html( _year + "年" +　(_month+1) + "月&nbsp;" + (_type==0?"覆盖用户数":_type==1?"注册用户数":"安装用户数"));
		$("#addMonthDateDialog .btn-sub").unbind("click").click(function(){
			var count = 0;
			$("#addMonthDateDialog .form-group input").each(function(index, element) {
				var val  = $(element).val();
                if(!val.match(/^\d+$/g) && val != ""){
					W.$.alert("请输入数字");
					return false;
				}else {
					count += parseInt(val);
				}
            });
			_fun(count);
		});
	},
	
	//初始化图表
	initCharts : function(_element, _title, _data, _color){
		$.charts(
			_element
			, _title || ""
			, _data
			, _color
		);
		for(var i=0; i<_data.length; i++){
			$(_element).next(".table_charts_data").find("td").eq(i).text(_data[i]);
		}
		
	},
	//图表对象
	charts : function(_element, _title, _data , _color){
		$(_element).highcharts({
			colors: _color || ["#7cb5ec"],
			chart: {
			  type: 'spline'
			},
			title: {
				text: _title || '',
				x: -20 //center
			},
			xAxis: {
				categories: ['1', '2', '3', '4', '5', '6','7', '8', '9', '10', '11', '12']
			},
			yAxis: {
				title: {
					text: ''
				},
				plotLines: [{
					value: 0,
					width: 1,
					color: '#808080'
				}]
			},
			plotOptions: {
				series: {        
					cursor: 'pointer',        
					events: {            
					click: function(event) {
						//#coverTheUserCharts #regusersCharts
						if(_element == "#coverTheUserCharts"){
							var year = $("#coverUserYear").val();
							$.uploadCoverUserMonth(year , event.point.x);
						}else if(_element == "#regUsersCharts"){
							var year = $("#regUsersYear").val();
							$.uploadRegUsersMonth(year , event.point.x);
						}else{
							var year = $("#instUsersYear").val();
							$.uploadInstUsersMonth(year , event.point.x);
						}
						//alert('index = '+ event.point.x+ 'x = '+event.point.category+' ,y = '+event.point.y);
					}   
				  }   
				}
			},
			//egend
			legend: {
			   enabled:false
			},
			//redit
			credits:{
			   enabled:false
			},
			
			series: [{
				name: 'NUM',
				data: _data
			}]
		});
	}
});
</script>
</body>
</html>
