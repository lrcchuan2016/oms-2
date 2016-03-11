<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.nav .badge{ background-color:#F00;}
.panel-body img{ width:100%; height:auto;}
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
</style>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
       <li><a href="javascript:void(0)">商城管理</a></li>
      <li><a href="javascript:void(0)">订单管理</a></li>
      <li class="active">订单详情</li>
    </ol>
    
    <div class="row" style="padding:0px 20px;">
    	
    	<div class="panel panel-default">
          <div class="panel-heading">订单号：<span class="id"></span></div>
          <div class="panel-body">
             <ul class="user-info">
             	<li class="col-md-4">
                	<img class="coverUrl" src='http://cdn.meijx.cn/images/4882aa8b5b60ce156279db20d456d1ae1442372017662.jpg@1e_318w_165h_1c_0i_1o_100Q_1x.jpg'>
                </li>
             	<li class="col-md-4">
                
                    <p>
                        <span class="t"><strong>名称：</strong></span>
                        <span class="productName"></span>
                    </p>
                	<p>
                        <span class="t"><strong>数量：</strong></span>
                        <span class="productCount"></span>
                    </p>
                    <hr>
                    <p>
                        <span class="t"><strong>购买者UserID：</strong></span>
                        <span class="userId"></span>
                    </p>
                    <hr>
                	<p>
                        <span class="t"><strong>下单时间：</strong></span>
                        <span class="startUtc"></span>
                    </p>
                	<p>
                        <span class="t"><strong>付款时间：</strong></span>
                        <span class="endUtc"></span>
                    </p>
                </li>
             	<li class="col-md-4">
                    <p>
                		<span class="t"><strong>收件人电话:</strong></span>
                    	<span class="recvPhone"></span>
                    </p>
                    <p>
                		<span class="t"><strong>收件人地址:</strong></span>
                    	<span class="recvAddr"></span>
                    </p>
                	<hr>
                	<p>
                		<span class="t"><strong>备注信息:</strong></span>
                    </p>
                    <p>
                    	<span class="remark">--</span>
                    </p>
                </li>
             	
             </ul>
          </div>
        </div>

		<div class="panel panel-default">
          <div class="panel-heading">订单状态： <span class="deliverState"></span></div>
          <div class="panel-body">
          <form id="formObj" action="javascript:void(null)" method="get">
             <ul class="user-info">
             	
             	<li class="col-md-4">
                
                    <p>
                        <span class="t"><strong>物流单号：</strong></span>
                        <span class="logstNumber"></span>
                        <span class="edit text-info hide" style="margin-left: 20px;cursor: pointer;">修改</span>
                    </p>
                	<p>
                        <span class="t"><strong>物流公司：</strong></span>
                        <span class="logistics"></span>
                        <span class="edit text-info hide" style="margin-left: 20px;cursor: pointer;">修改</span>
                    </p>
                
                </li>
             	
             </ul>
             </form>
          </div>
        </div>
        <button type="button" class="btn btn-success btn-deliver">发货</button>
        <button type="button" class="btn btn-info" onClick="javascript:history.back();">返回</button>
	</div><!---userListbox-end-->
    
    
</div>


<div id="addMonthDateDialog" class="modal" tabindex="-1" style="display:none; background-color:rgba(0,0,0,0.4); overflow:hidden;">
    <div class="modal-dialog" style="width:600px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4></h4>
            </div>
            <div class="modal-body">
                <form id="monthDateForm" class="form-horizontal" action="javascript:void(null);">
                	<div class="form-group">
                        <label class="col-xs-3 control-label">快递公司：</label>
                        <div class="col-xs-4"><select class="form-control">
                          <option>顺丰</option>
                          <option>圆通</option>
                          <option>中通</option>
                          <option>韵达</option>
                          <option>EMS</option>
                        </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">订单号码：</label>
                        <div class="col-xs-9"><input id="content" class="form-control input-sm r-lg" type="text" name="" maxlength="20"></div>
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
var orderId = '${param.id}';
var formObj = null;
$(function(){
	
	formObj = $("#formObj").inputValid(valid_message_options);
	
	//获取订单详情
	initOrderData();
	
	//初始化绑定发货事件
	bindDeliver();
	
	//绑定修改事件
	bindEditOrder();
});

function bindEditOrder(){
	$(".edit").livequery(function(){
		$(this).click(function(){
			var id = $(".id").html(); //订单id
			var $this = $(this);
			$this.addClass("hide");
			var pre = $(this).prev();
			if(pre.attr("class") == "logstNumber"){
				var str = '<input class="logstNumber2" type="text" data-valid="[{rule:\'not_empty\'}]" maxlength="50" value="'+pre.html()+'">';
				pre.html(str);
				$(".logstNumber2").focus();
				$(".logstNumber2").blur(function(){
					var value = $(this).val();
					var logistics = $(".logistics2").val() != undefined?$(".logistics2").val():$(".logistics").html();
					saveEdit("paramMap['id']="+id+"&paramMap['logistics']="+logistics+"&paramMap['orderId']="+value);
				});
				
			}else if(pre.attr("class") == "logistics"){
				var str = '<select class="logistics2"><option value="顺丰">顺丰</option><option value="圆通">圆通</option><option value="中通">中通</option><option value="ems">EMS</option><option value="韵达">韵达</option></select>';
				var html = pre.html();
				pre.html(str);
				pre.find("option").each(function(){
					if($(this).val() == html) $(this).attr({selected:"selected"});
				});
				$(".logistics2").focus();
				$(".logistics2").blur(function(){
					var value = $(this).val();
					var logstNumber = $(".logstNumber2").find("input").val() != undefined?$(".logstNumber2").find("input").val():$(".logstNumber").html();
					saveEdit("paramMap['id']="+id+"&paramMap['logistics']="+value+"&paramMap['orderId']="+logstNumber);
				});
			}

		});
	});
}

function saveEdit(param){
	if(formObj.validate_all()){
		$.ajax({
			type:'post',
			dataType:'json',
			data:param,
			url:'json/pay_editDevierInfo',
			success:function(json){
				if(json.result == "00000000"){
					W.$.alert("修改成功！");
					var order = json.order;
					$(".logistics").html(order.logistics).next().removeClass("hide");
					$(".logstNumber").html(order.logstNumber).next().removeClass("hide");
				}else W.$.alert("修改失败！");
			},
			error:function(e){
				W.$.alert("修改失败！");
			}
		});
	}
}

function initOrderData(){
	$.ajax({
		type:'post',
		dataType:'json',
		data:{"paramMap['id']":orderId},
		url:'json/pay_getOrderInfo',
		success:function(json){
			if(json.result == '00000000'){
				fillData(json.order);
				$(".coverUrl").attr({src:json.coverUrl+"@1e_318w_165h_1c_0i_1o_100Q_1x.jpg"});
			}else W.$.alert("获取订单信息失败！");
		},
		error:function(e){
			W.$.alert("获取订单数据异常");
		}
	});
}

function fillData(order){
	order.startUtc = formatUTC(order.startUtc,"yyyy/MM/dd hh:mm:ss");
	order.endUtc = formatUTC(order.endUtc,"yyyy/MM/dd hh:mm:ss");
	var status = order.deliverState;
	if(status == '0') {
		order.deliverState = "支付未完成";
		$(".btn-deliver").remove();
	}else if(status == '1'){
		order.deliverState = "待发货";
	}else if(status == '2'){
		order.deliverState = "已发货";
		$(".btn-deliver").remove();
		$(".edit").removeClass("hide");
	}else if(status == '3'){
		order.deliverState = "已完成";
		$(".btn-deliver").remove();
		$(".edit").removeClass("hide");
	}
	for ( var i in order) {
		$("."+i).html(order[i]);
	}
}

function bindDeliver(){
	$("#addMonthDateDialog").find(".btn-sm").eq(0).click(function(){
		$("#addMonthDateDialog").hide();
	});
	$(".btn-success").livequery(function(){
		$(this).click(function(){
			$.deliver();
		});
	});
}


$.extend({
	//发货
	deliver : function(){
		$.showDialog(function(){
			var logistics =  $("#monthDateForm").find("select").val();
			$.ajax({
				type:"POST",
				dataType:"json",
				data:{
					"paramMap['logistics']": logistics,
					"paramMap['id']" : $(".id").html(),
					"paramMap['orderId']":$("#content").val()
				},
				url:'json/pay_devliverOrder', //API
				success:function(json) {
					if(json.result == "00000000"){
						W.$.alert("发货成功！！");
						setTimeout(function(){window.location.href="payPages_orderDeliver";}, 2000);
					}else {
						W.$.alert("错误！");
					}
				},
				error: function(data) {
					W.$.alert("发货失败！");
				},
				complete: function(){
					$("#addMonthDateDialog").hide();
				}
			});
		});
	},
	//弹出框
	showDialog : function(_fun){
		//type 0:覆盖用户  1:注册用户
		$("#addMonthDateDialog input").val("");

		var winH = $(window).height();
		$("#addMonthDateDialog .modal-content").css("margin-top",(winH - 450)/2 + "px");
		$("#addMonthDateDialog").show();
		
		$("#addMonthDateDialog .btn-sub").unbind("click").click(function(){
			var text = "";
			
			text  = $("#addMonthDateDialog #content").val();
			if(text == ""){
				W.$.alert("请输入内容");
				return false;
			}
            
			_fun(text);
		});
	},
	
});
</script>
</body>
</html>
