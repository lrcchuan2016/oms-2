<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.nav .badge{ background-color:#F00;}
#cd-timeline { 
  position: relative; 
  padding: 2em 0; 
  margin-top: 2em; 
  margin-bottom: 2em; 
} 
#cd-timeline::before { 
  content: ''; 
  position: absolute; 
  top: 0; 
  left: 18px; 
  height: 100%; 
  width: 4px; 
  background: #d7e4ed; 
} 
.cd-timeline-block { 
  position: relative; 
  margin: 2em 0; 
} 
.cd-timeline-block:after { 
  content: ""; 
  display: table; 
  clear: both; 
} 
.cd-timeline-block:first-child { 
  margin-top: 0; 
} 
.cd-timeline-block:last-child { 
  margin-bottom: 0; 
} 
@media only screen and (min-width: 1170px) { 
  .cd-timeline-block:nth-child(even) .cd-timeline-content { 
    float: right; 
  } 
  .cd-timeline-block:nth-child(even) .cd-timeline-content::before { 
    top: 24px; 
    left: auto; 
    right: 100%; 
    border-color: transparent; 
    border-right-color: white; 
  } 
  .cd-timeline-block:nth-child(even) .cd-timeline-content .cd-read-more { 
    float: right; 
  } 
  .cd-timeline-block:nth-child(even) .cd-timeline-content .cd-date { 
    left: auto; 
    right: 122%; 
    text-align: right; 
  } 
} 
</style>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
       <li><a href="javascript:void(0)">商城管理</a></li>
      <li><a href="javascript:void(0)">订单管理</a></li>
      <li class="active">已发货</li>
    </ol>
    
    <div class="userListbox">
    	<ul class="nav nav-tabs">
           <li role="presentation"><a href="payPages_order">订单统计</a></li>
          <li role="presentation"><a href="payPages_orderPayment">待付款</a></li>
          <li role="presentation"><a href="payPages_orderDeliver">待发货<span class="badge"></span></a></li>
          <li role="presentation"  class="active"><a href="payPages_orderComplete">已发货</a></li>
       		<li role="presentation"><a href="payPages_orderCancel">已取消</a></li>
        </ul>
    	
        <table class="orderTable table table-bordered table-hover" style="margin-top:30px;">
           	  <thead>
               	  <tr>
                      <th>订单号</th>
                      <th>产品名称</th>
                      <th>发货时间</th>
                      <th>单号</th>
                      <th>收货人（phone）</th>
                      <th>地址</th>
                      <th>操作</th>
                  </tr>
              </thead>
              <tbody class="bg-white"></tbody>
              <tfoot><tr><td colspan="7"></td></tr></tfoot>
        </table>
        
	</div><!---userListbox-end-->
    
</div>
    
<div id="orderTrack" class="modal" tabindex="-1" style="display:none; background-color:rgba(0,0,0,0.4); overflow:hidden;">
    <div class="modal-dialog" style="width:700px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">订单跟踪<small class="state"></small></h4>
            </div>
            <div class="modal-body" style="padding:20px;max-height: 400px;overflow:auto;" >
               <section id="cd-timeline" class="cd-container" style="margin-left: 20px;">
					
				</section>
            </div>
            <div class="modal-footer text-center-xs" style="margin-top:0px;">
                <button type="button" class="btn-close btn btn-default r-lg btn-sm" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span> 关　闭
                </button>
            </div>
        </div>
    </div>
</div>    
    
<script>

var W = window.top;
var orderTable = null,mapObj = new HashMap();
$(function(){

	//初始化表格数据
	initOrderTable();
	
	//显示订单跟踪
	showOrderTrack();
});

function initOrderTable(){
	orderTable = $(".orderTable").tableList({
        url:"json/pay_conditionOrderList",
        param:"paramMap['orderBy']=deliverUtc&paramMap['column']=deliverState&paramMap['value']=2",
        pageSize:12,
        paging:true,
        finishCallBack:function(){},
        template:htmlTemplate
    });
    
    //获得badge
    $.post("json/pay_getDeliverNum",function(json){
    	if(json.result == "00000000"){
    		$(".badge").html((json.deliverNum[0]));
    	}else W.$.alert("获取数据失败");
    },"json");
}

function htmlTemplate(index,data,recordIndex){
	mapObj.put(data.id,data);
	var html = "";
	html += '<tr id="'+data.id+'">';
	html += '<td>'+data.id+'</td>';
	html += '<td>'+data.productName+'</td>';
	html += '<td>'+formatUTC(data.deliverUtc,"yyyy/MM/dd hh:mm:ss")+'</td>';
	html += '<td><a class="userId" userId="'+data.userId+'" href="javascript:void(0)" style><span class="text-info">'+data.logstNumber+'</span></a></td>';
	html += '<td>'+data.userPhone+'</td>';
	html += '<td>'+data.recvAddr+'</td>';
	html += '<td><a href="payPages_orderDetails?id='+data.id+'" class="btn-detail btn btn-info btn-sm">详情</a></td>';
	html += '<tr>';
	return html;
}

function showOrderTrack(){
	//点击单号事件
	$("td .userId").livequery(function(){
		$(this).click(function(){
			var order = mapObj.get($(this).parents("tr").attr("id"));
			var logistics = order.logistics;
			var type = getType(logistics);
			var logstNumber = order.logstNumber;
			$.ajax({
				type:'get',
				dataType:'json',
				data:{"paramMap['type']":type,"paramMap['postid']":logstNumber},
				url:'json/pay_getOrderTrackInfo',
				success:function(json){
					if(json.result == '00000000'){
						var data = json.data;
						fillModal(data);
					}else W.$.alert("获取订单发货信息失败！");
				},
				error:function(e){
					W.$.alert("获取订单发货信息异常！");
				}
			});
			
			
		});
	});
	//点击关闭事件
	$("#orderTrack .btn-close").livequery(function(){
		
		$(this).click(function(){
			$("#orderTrack").hide();
		});
	});
}

function fillModal(data){
	if(data.Success){
		var arr = data.Traces;
		var state = data.State;
		var timeline = $("#cd-timeline");
		var html = "";
		for ( var i in arr) {
			html += '<div class="cd-timeline-block" style="margin-left: -20px;"><div class="cd-timeline-content">';
			html +=	'<span class="cd-date text-info">'+arr[i].AcceptTime+'</span><p style="margin-left: 40px;" class="detail">'+arr[i].AcceptStation+'</p>';
			html += '<div></div></div></div>';	
		}
		timeline.html(html);
		//显示状态
		var stateSpan = $("#orderTrack .state");
		if(state == '2') {
			stateSpan.addClass("text-info");
			stateSpan.html("(在途中)");
		}else if(state == '3') {
			stateSpan.addClass("text-success");
			stateSpan.html("(已签收)");
		}else if(state == '4'){ 
			stateSpan.addClass("text-danger");
			stateSpan.html("(问题件)");
		}
		
		$("#orderTrack").show();
	}else W.$.alert("获取的数据有异！");
}

function getType(data){
	var type = "";
	if(data == "圆通"){
		type = "yto";
	}else if(data == "中通"){
		type = "zto";
	}else if(data == "顺丰"){
		type = "sf";
	}else if(data == "韵达"){
		type = "yd";
	}else{
		type = "ems";
	}
	return type;
}


$.extend({
	
});
</script>
</body>
</html>
