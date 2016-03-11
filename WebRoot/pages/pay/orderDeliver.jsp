<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.nav .badge{ background-color:#F00;}
</style>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
       <li><a href="javascript:void(0)">商城管理</a></li>
      <li><a href="javascript:void(0)">订单管理</a></li>
      <li class="active">待发货</li>
    </ol>
    
    <div class="userListbox">
    	<ul class="nav nav-tabs">
           <li role="presentation"><a href="payPages_order">订单统计</a></li>
          <li role="presentation"><a href="payPages_orderPayment">待付款</a></li>
          <li role="presentation"  class="active"><a href="payPages_orderDeliver">待发货<span class="badge"></span></a></li>
          <li role="presentation"><a href="payPages_orderComplete">已发货</a></li>
       		<li role="presentation"><a href="payPages_orderCancel">已取消</a></li>
        </ul>
    	
        <table class="orderTable table table-bordered table-hover" style="margin-top:30px;">
           	  <thead>
               	  <tr>
                      <th>订单号</th>
                      <th>产品名称</th>
                      <th>付款时间</th>
                      <th>UserID</th>
                      <th>收货人（phone）</th>
                      <th>数量</th>
                      <th>金额</th>
                      <th>操作</th>
                  </tr>
              </thead>
              <tbody class="bg-white"></tbody>
              <tfoot><tr><td colspan="8"></td></tr></tfoot>
        </table>
       
        
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
                          <option value="顺丰">顺丰</option>
                          <option value="圆通">圆通</option>
                          <option value="中通">中通</option>
                          <option value="韵达">韵达</option>
                          <option value="EMS">EMS</option>
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
 
 <div id="family-info" class="modal" tabindex="-1" style="display:none; background-color:rgba(0,0,0,0.4); overflow:hidden;">
    <div class="modal-dialog" style="width:700px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4>用户信息</h4>
            </div>
            <div class="modal-body" style="padding:0px;">
                <table class="table table-bordered" style="margin:0px;">
                	<tr>
                    	<td style="border-top:none; width:120px;">头像</td>
                    	<td style="border-top:none;" class="portrait"><img src="http://a.broadin.cn/common/h5.png" width="60"></td>
                    </tr>
                	<tr>
                    	<td>昵称</td>
                    	<td class="nickname">老樊家</td>
                    </tr>
                	<tr>
                    	<td>userID</td>
                    	<td class="userId">dsdhfSDHYdfehdfhsdjfT</td>
                    </tr>
                	<tr>
                    	<td>手机</td>
                    	<td class="phone">13418668907</td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer text-center-xs" style="margin-top:0px;">
                <button type="button" class="btn btn-default r-lg btn-sm" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span> 关　闭
                </button>
            </div>
        </div>
    </div>
</div>
<script>

var W = window.top;
var orderTable = null;
$(function(){
	
	//显示用户信息
	showUserInfo();
	
	//初始化模态框
	initModel();
	
	//初始化待发货表格
	initOrderTable();
});

function initOrderTable(){
	orderTable = $(".orderTable").tableList({
        url:"json/pay_conditionOrderList",
        param:"paramMap['orderBy']=endUtc&paramMap['column']=deliverState&paramMap['value']=1",
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
	var html = "";
	html += '<tr id="'+data.id+'">';
	html += '<td>'+data.id+'</td>';
	html += '<td>'+data.productName+'</td>';
	html += '<td>'+formatUTC(data.endUtc,"yyyy/MM/dd hh:mm:ss")+'</td>';
	html += '<td><a class="userId" userId="'+data.userId+'" href="javascript:void(0)" style><span class="text-info">'+data.userId+'</span></a></td>';
	html += '<td>'+data.userPhone+'</td>';
	html += '<td>'+data.productCount+'</td>';
	html += '<td>'+data.totalFee/100+'</td>';
	html += '<td><a href="payPages_orderDetails?id='+data.id+'" class="btn-detail btn btn-info btn-sm">详情</a><a href="javascript:void(0)" class="btn btn-success btn-sm">发货</a></td>';
	html += '<tr>';
	return html;
}

function initModel(){
	$("#addMonthDateDialog").find(".btn-sm").eq(0).click(function(){
		$("#addMonthDateDialog").hide();
	});
	$(".table .btn-success").livequery(function(){
		$(this).click(function(){
			var tr = $(this).parents("tr");
			$.deliver(tr);
		});
	});
}

function showUserInfo(){
	$("td .userId").livequery(function(){
		$(this).click(function(){
			var id = $(this).attr("userId");
			$.ajax({
				type:'post',
				dataType:'json',
				data:{"paramMap['id']":id},
				url:'json/pay_getUserInfo',
				success:function(json){
					if(json.result == '00000000'){
						fillModel(json.user);
						$("#family-info").show();
					}else W.$.alert("获取用户信息失败！");
				}
			});
			
		});
	});
	
	$("#family-info .modal-footer .btn").livequery(function(){
		$(this).click(function(){
			$("#family-info").hide();
		});
	});
}

function fillModel(user){
	$(".portrait").find("img").attr({src:user.portrait});
	$(".nickname").html(user.nickname);
	$(".userId").html(user.id);
	$(".phone").html(user.account);
}


$.extend({
	//发货
	deliver : function($tr){
		$.showDialog(function(){
			var logistics =  $("#monthDateForm").find("select").val();
			$.ajax({
				type:"POST",
				dataType:"json",
				data:{
					"paramMap['logistics']": logistics,
					"paramMap['id']" : $tr.attr("id"),
					"paramMap['orderId']":$("#content").val()
				},
				url:'json/pay_devliverOrder', //API
				success:function(json) {
					if(json.result == "00000000"){
						W.$.alert("发货成功！！");
						$tr.remove();
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
