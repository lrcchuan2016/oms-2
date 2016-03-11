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
      <li class="active">订单统计</li>
    </ol>
    
    <div class="userListbox">
    	<ul class="nav nav-tabs">
          <li role="presentation" class="active"><a href="payPages_order">订单统计</a></li>
          <li role="presentation"><a href="payPages_orderPayment">待付款</a></li>
          <li role="presentation"><a href="payPages_orderDeliver">待发货<span class="badge"></span></a></li>
          <li role="presentation"><a href="payPages_orderComplete">已发货</a></li>
       		<li role="presentation"><a href="payPages_orderCancel">已取消</a></li>
        </ul>
    	
        <table class="table table-hover" style="margin-top:30px; max-width:600px;">
           	  <thead>
               	  <tr>
                      <th>项目</th>
                      <th>订单数量</th>
                  </tr>
              </thead>
              <tbody>
               	  <tr>
                      <td>待付款</td>
                      <td><a href="payPages_orderPayment" class="nonPaymentNum"></a></td>
               	  </tr>

               	  <tr>
                      <td>待发货</td>
                      <td><a href="payPages_orderDeliver" class="nonDeliverNum"></a></td>
               	  </tr>

               	  <tr>
                      <td>已发货</td>
                      <td><a href="payPages_orderComplete" class="completeNum"></a></td>
               	  </tr>
                  
               	  <tr>
                      <td>总计收款</td>
                      <td>￥<span class="sum"></span>元</td>
               	  </tr>
              </tbody>
        </table>
        
        <table class="productSaleTable table table-hover" style="margin-top:30px; max-width:600px;">
           	  <thead>
               	  <tr>
                      <th>产品名称</th>
                      <th>售出数量</th>
                      <th>金额</th>
                  </tr>
              </thead>
              <tbody>
               	 
               	  
              </tbody>
        </table>
        
	</div><!---userListbox-end-->
    
    </div>
<script>

var W = window.top;

$(function(){
	//获得badge
    initBadge();
    
    //获得初始化的统计数据
    initStaticsData();
});

function initBadge(){
	$.post("json/pay_getDeliverNum",function(json){
    	if(json.result == "00000000"){
    		$(".badge").html((json.deliverNum[0]));
    	}else W.$.alert("获取数据失败");
    },"json");
}

function initStaticsData(){
	$.ajax({
		type:'post',
		dataType:'json',
		data:{"paramMap['orderBy']":"startUtc"},
		url:'json/pay_orderList',
		success:function(json){
			var data = json.list;
			var records = data.records;
			if(records != null){
				calData(records);
			}
		},
		error:function(e){
			W.$.alert("获取统计数据异常！");
		}
	});
}

function calData(records){
	var nonPaymentNum = 0;   //未付款
	var nonDeliverNum = 0;		//待发货
	var completeNum = 0;		//发货完成
	var sum = 0; 				//总金额
	var productName = "";		//产品名称
	var saleNum = 0;			//已售数量
	var ProductSaleSum = 0;		//已售此产品的总金额
	var json = new Object();
	var arr = new Array();
	json.products = arr;
	for ( var i in records) {
		var record = records[i];
		if(record.deliverState == 0){
			nonPaymentNum += record.productCount;
			sum += record.totalFee;
		}else if(record.deliverState == 1){
			nonDeliverNum += record.productCount;
			sum += record.totalFee;
		}else if(record.deliverState == 2){
			completeNum += record.productCount;
			sum += record.totalFee;
			
			productName = record.productName;
			if(arr.productName != undefined){
				arr.productName[1] += record.productCount;
				arr.productName[2] += (record.totalFee/100).toFixed(2);
			}else{ 
				arr.productName = [productName,record.productCount,(record.totalFee/100).toFixed(2)];
			}
		}
	}
	
	json.nonPaymentNum = nonPaymentNum;
	json.nonDeliverNum = nonDeliverNum;
	json.completeNum = completeNum;
	json.sum = (sum/100).toFixed(2);
	
	for ( var j in json) {
		if(j == 'products'){
			var products = json[j];
			var tbody = $(".productSaleTable").find("tbody");
			tbody.empty();
			for ( var k in products) {
				tbody.append(fillTable(products[k]));
				//console.info(fillTable(products[k]));
			}
		}else $("."+j).html(json[j]);
	}
	
	return json;
}

/*
*填充各产品销售情况表表格
*/
function fillTable(arr){
	var html = '<tr>';
	html += '<td>'+arr[0]+'</td>';
	html += '<td>'+arr[1]+'</td>';
	html += '<td>￥<span>'+arr[2]+'</span></td>';
    html += '</tr>';
    return html;
}

$.extend({
	
});
</script>
</body>
</html>
