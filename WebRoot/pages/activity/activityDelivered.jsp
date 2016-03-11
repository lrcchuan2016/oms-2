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
      <li><a href="javascript:void(0)">活动管理</a></li>
      <li><a href="activityPages_activityList">活动列表</a></li>
      <li class="active">转盘活动</li>
    </ol>
    
    <div class="userListbox channel-list" style="margin-right:20px;">
    	<ul class="nav nav-tabs">
          <li role="presentation"><a href="activityPages_activityWinRecord">未领奖</a></li>
          <li role="presentation"><a href="activityPages_activityNonDeliver">待发奖</a></li>
        	<li role="presentation" class="active"><a href="activityPages_activityDelivered">已发奖</a></li>
       		<li role="presentation"><a href="activityPages_activityGiftList">查看奖品</a></li>
        </ul>
    	<div style="margin-top: 15px;">
    	<form class="retrieval" id="searchForm" action="javascript:void(null);">
    		<div class="form-group pull-left" style="width:350px;">
	    		<div class="input-group">
	    			<div class="input-group-addon">关键字</div>
                  	<input class="form-control" name="paramMap['keyWords']" data-valid="[{rule:'not_empty'}]" type="text" maxlength="30" placeholder="家庭号/手机号/呢称">
	    		</div>
           </div>
           &nbsp;<button type="submit" id="searchBtn" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 搜索</button>
		</form>
		</div>
		
		<table id="activityTurntable" class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
			<thead>
   				<tr>
   					<td>NO.</td>
   					<td>中奖物品</td>
   					<td>物流单号</td>
   					<td>物流公司</td>
   					<td>中奖号码</td>
   					<td>收件人</td>
   					<td>收件人号码</td>
   					<td>收件人地址</td>
   					<td>发奖日期</td>
   					<td>操作</td>
   					
   				</tr>
   			</thead>
			<tbody class="bg-white"></tbody>
			<tfoot><tr><td colspan="10">&nbsp;</td></tr></tfoot>
		</table>
	</div>
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

<!-- 模态框 -->
<div id="orderModal" class="modal" tabindex="-1" style="display:none; background-color:rgba(0,0,0,0.4); overflow:hidden;">
    <div class="modal-dialog" style="width:700px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4>修改订单</h4>
            </div>
            <div class="modal-body" style="padding:0px;">
                 <form id="orderForm" class="form-horizontal" action="javascript:void(null);">
                	<div class="form-group">
                        <label class="col-xs-3 control-label">快递公司：</label>
                        <div class="col-xs-4"><select class="form-control" name="paramMap['logistics']">
                          <option value="顺丰">顺丰</option>
                          <option value="圆通">圆通</option>
                          <option value="中通">中通</option>
                          <option value="韵达">韵达</option>
                          <option value="EMS">EMS</option>
                        </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">物流单号：</label>
                        <div class="col-xs-6"><input name="paramMap['logistNumber']" data-valid="[{rule:'not_empty'}]" class="form-control input-sm r-lg" type="text" name="" maxlength="64"></div>
                    </div>
                </form>
            </div>
            <div class="modal-footer text-center-xs" style="margin-top:0px;">
            	 <button type="button" class="btn btn-default r-lg btn-sm btn-submit" data-dismiss="modal">
                    <span class="glyphicon glyphicon-ok"></span> 确   定
                </button>
            
                <button type="button" class="btn btn-default r-lg btn-sm btn-close" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span> 关　闭
                </button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
var W = window.top;
var activityId = '${param.activityId}';
var activityTableObj = null,formObj = null,mapObj = new HashMap(),orderFormObj = null;
$(function(){
	
    initDat();
   
   	//搜索
   	bindSearch();
   	
   	//单号点击事件
   	showOrderTrack();
   	
   	//修改事件
   	bindEdit();
});

function bindEdit(){
	$(".btn-edit").livequery(function(){
		$(this).click(function(){
			var id = $(this).parents("tr").attr("id");
			var data = mapObj.get(id);
			var modal = $("#orderModal");
			//
			modal.find(".btn-close").click(function(){
				modal.fadeOut();
			});
			//
			modal.find(".btn-submit").click(function(){
				modal.fadeOut();
				if(orderFormObj.validate_all()){
					var param = "paramMap['id']="+id+"&"+$("#orderForm").serialize();
					editSubmit(param);
				}
			});
			
			modal.find("input").val(data.logistNumber);
			modal.find("select").val(data.logistics);
			
			modal.fadeIn();
		});
	});
}

function editSubmit(param){
	$.ajax({
		type:'post',
		dataType:'json',
		data:param,
		url:'json/activityTurn_editOrder',
		success:function(json){
			if(json.result == '00000000'){
				//W.$.alert("修改成功");
				activityTableObj.refresh();
				
			}else W.$.alert("保存修改失败");
		},
		error:function(e){
			W.$.alert("保存修改异常");
		}
	});
}

function bindSearch(){
	$("#searchBtn").click(function(){
		if(formObj.validate_all()){
			activityTableObj.setOptions({
	            param:"&paramMap['isDeliver']=2&"+$("#searchForm").serialize(),
	            url:'json/activityTurn_getTurntablWinRecordList'
	        });
		}
	});
}

function initDat(){
	//验证
	formObj = $("#searchForm").inputValid(valid_message_options);
	orderFormObj = $("#orderForm").inputValid(valid_message_options);
	
	activityTableObj = $("#activityTurntable").tableList({
        url:"json/activityTurn_getTurntablWinRecordList",
        param:"&paramMap['isDeliver']=2",
        pageSize:12,
        paging:true,
        finishCallBack:function(){},
        template:htmlTemplate
    });
}

function htmlTemplate(index,data,recordIndex){
	mapObj.put(data.id, data);
	var html = "";
	html += '<tr id="'+data.id+'">';
	html += '<td>'+recordIndex+'</td>';
	
	//判断相簿还是台历（跳转的页面和显示不同）
	if(data.name.indexOf("相簿") != -1){
		html += '<td><a href="activityPages_drawResultDiy?prizeId='+data.id+'"><span class="text-info">'+data.name+'</span></a></td>';
	}else if(data.name.indexOf("台历") != -1){
		html += '<td><a href="activityPages_drawResultCalendar?prizeId='+data.id+'"><span class="text-info">'+data.name+'</span></a></td>';
	}else html += '<td>'+data.name+'</td>';
	
	html += '<td><a class="logistNumber" href="javascript:void(0);"><span class="text-info">'+data.logistNumber+'</span></a></td>';
	html += '<td>'+data.logistics+'</td>';
	html += '<td>'+data.account+'</td>';
	html += '<td>'+data.receiver+'</td>';
	html += '<td>'+data.receiverPhone+'</td>';
	html += '<td>'+data.receiverAddr+'</td>';
	html += '<td>'+formatUTC(data.deliverUtc,"yyyy-MM-dd hh:mm")+'</td>';
	html += '<td><button class="btn btn-warning btn-xs btn-edit"><span class="glyphicon glyphicon-edit">编辑</span></button></td>';
	html += '</tr>';
	return html;
}

function showOrderTrack(){
	//点击单号事件
	$("td > .logistNumber").livequery(function(){
		$(this).click(function(){
			var order = mapObj.get($(this).parents("tr").attr("id"));
			console.info(order);
			var logistics = order.logistics;
			var type = getType(logistics);
			var logistNumber = order.logistNumber;
			$.ajax({
				type:'get',
				dataType:'json',
				data:{"paramMap['type']":type,"paramMap['postid']":logistNumber},
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
</script>
</body>

</html>