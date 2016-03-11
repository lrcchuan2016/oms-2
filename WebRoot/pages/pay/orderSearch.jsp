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
      <li class="active">订单查询</li>
    </ol>
    
    <div class="userListbox">
    	<form id="keyWordsForm" class="retrieval" action="javascript:void(null);" method="post">
        	<div class="form-group pull-left" style="width:500px;">
                <div class="input-group">
                  <div class="input-group-addon">关键字</div>
                  <input type="text" class="form-control" data-valid="[{rule:'not_empty'}]" placeholder="输入单号/收件人手机号" name="paramMap['keyWords']" value="" onfocus="this.value=''">
                </div>
              </div>
              
              <button type="submit" class="btn btn-primary btn-search">搜索</button>
        </form>
        <table class="orderTable table table-bordered table-hover" style="margin-top:30px;">
           	  <thead>
               	  <tr>
                      <th>订单号</th>
                      <th>产品名称</th>
                      <th>生成时间</th>
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
                <h4>订单跟踪</h4>
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
var formObj = null,orderTabObj = null;mapObj = new HashMap();
$(function(){
	formObj = $("#keyWordsForm").inputValid(valid_message_options);
	
	//初始化表格
	initOrderTable();
	
	//绑定搜索事件
	bindSearch();
	
	//显示订单跟踪
	showOrderTrack();
});

function initOrderTable(){
	orderTabObj = $(".orderTable").tableList({
        url:"json/pay_orderList",
        param:"paramMap['orderBy']=startUtc",
        pageSize:12,
        paging:true,
        finishCallBack:function(){},
        template:htmlTemplate
    });
}

function htmlTemplate(index, data, recordIndex){
	mapObj.put(data.id, data);
	var html = "";
	html += '<tr id="'+data.id+'">';
	html += '<td>'+data.id+'</td>';
	html += '<td>'+data.productName+'</td>';
	html += '<td>'+formatUTC(data.startUtc,"yyyy/MM/dd hh:mm:ss")+'</td>';
	html += '<td><a class="userId" userId="'+data.userId+'" href="javascript:void(0)" style><span class="text-info">'+data.logstNumber+'</span></a></td>';
	html += '<td>'+data.recvPhone+'</td>';
	html += '<td>'+data.recvAddr+'</td>';
	html += '<td><a href="payPages_orderDetails?id='+data.id+'" class="btn btn-info btn-sm">详情</a></td>';
	html += '<tr>';
	
	return html;
}

function bindSearch(){
	$(".btn-search").livequery(function(){
		$(this).click(function(){
			if(formObj.validate_all()){
				var param = $("#keyWordsForm").serialize()+"&orderBy=startUtc";
				orderTabObj.setOptions({param:param});
			}
		});
	});
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
	if(data.status == '200' && data.message == 'ok'){
		var arr = data.data;
		var timeline = $("#cd-timeline");
		var html = "";
		for ( var i in arr) {
			html += '<div class="cd-timeline-block" style="margin-left: -20px;"><div class="cd-timeline-content">';
			html +=	'<span class="cd-date text-info">'+formatUTC(arr[i].time, "yyyy/MM/dd hh:mm:ss")+'</span><p style="margin-left: 40px;" class="detail">'+arr[i].context+'</p>';
			html += '<div></div></div></div>';	
		}
		timeline.html(html);
		$("#orderTrack").show();
	}else W.$.alert("获取的数据有异！");
}

function getType(data){
	var type = "";
	if(data == "圆通"){
		type = "yuantong";
	}else if(data == "中通"){
		type = "zhongtong";
	}else if(data == "顺丰"){
		type = "shunfeng";
	}else if(data == "韵达"){
		type = "yunda";
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
