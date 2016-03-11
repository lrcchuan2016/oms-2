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
      <li class="active">待付款</li>
    </ol>
    
    <div class="userListbox">
    	<ul class="nav nav-tabs">
          <li role="presentation"><a href="payPages_order">订单统计</a></li>
          <li role="presentation" class="active"><a href="payPages_orderPayment">待付款</a></li>
          <li role="presentation"><a href="payPages_orderDeliver">待发货<span class="badge"></span></a></li>
          <li role="presentation"><a href="payPages_orderComplete">已发货</a></li>
        	<li role="presentation"><a href="payPages_orderCancel">已取消</a></li>
        </ul>
    	
        <table class="orderTable table table-bordered table-hover" style="margin-top:30px;">
           	  <thead>
               	  <tr>
                      <th>订单号</th>
                      <th>产品名称</th>
                      <th>数量</th>
                      <th>金额（元）</th>
                      <th>UserID</th>
                      <th>下单人（phone）</th>
                      <th>下单时间</th>
                      <th>操作</th>
                  </tr>
              </thead>
              <tbody class="bg-white"></tbody>
              <tfoot><tr><td colspan="8"></td></tr></tfoot>
        </table>
       
        
	</div><!---userListbox-end-->
    
    
    
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
	
	//初始化表格数据
	initOrderTable();
	
});

function initOrderTable(){
	orderTable = $(".orderTable").tableList({
        url:"json/pay_conditionOrderList",
        param:"paramMap['orderBy']=startUtc&paramMap['column']=deliverState&paramMap['value']=0",
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
	html += '<td>'+data.productCount+'</td>';
	html += '<td>'+data.totalFee/100+'</td>';
	html += '<td><a class="userId" userId="'+data.userId+'" href="javascript:void(0)" style><span class="text-info">'+data.userId+'</span></a></td>';
	html += '<td>'+data.userPhone+'</td>';
	html += '<td>'+formatUTC(data.startUtc,"yyyy/MM/dd hh:mm:ss")+'</td>';
	html += '<td><a href="payPages_orderDetails?id='+data.id+'" class="btn-detail btn btn-info btn-sm">详情</a></td>';
	html += '<tr>';
	
	return html;
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

$.extend({});
	
</script>
</body>
</html>
