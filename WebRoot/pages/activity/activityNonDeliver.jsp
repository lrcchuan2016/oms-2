<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
          <li role="presentation" class="active"><a href="activityPages_activityNonDeliver">待发奖</a></li>
        	<li role="presentation"><a href="activityPages_activityDelivered">已发奖</a></li>
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
   					<td>中奖号码</td>
   					<td>收件人</td>
   					<td>收件人电话</td>
   					<td>收件人地址</td>
   					<td>中奖日期</td>
   					<td>操作</td>
   					
   				</tr>
   			</thead>
			<tbody class="bg-white"></tbody>
			<tfoot><tr><td colspan="8">&nbsp;</td></tr></tfoot>
		</table>
	</div>
</div>

<!-- 模态框 -->
<div id="orderModal" class="modal" tabindex="-1" style="display:none; background-color:rgba(0,0,0,0.4); overflow:hidden;">
    <div class="modal-dialog" style="width:700px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4>填单信息</h4>
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
var activityTableObj = null,formObj = null,orderFormObj = null;
$(function(){
	
    initDat();
   
   	//搜索
   	bindSearch();
   	
   	//发货触发事件
   	bindDeliver();
});

function bindDeliver(){
	$(".btn-deliver").livequery(function(){
		$(this).bind("click",function(){
			var modal = $("#orderModal");
			var tr = $(this).parents("tr");
			modal.fadeIn();
			
			var winRecordId = tr.attr("id");
			
			//绑定关闭模态框的事件
			$(".btn-close").bind("click",function(){
				modal.fadeOut();
			});
			
			$(".btn-submit").bind("click",function(){
				if(orderFormObj.validate_all()){
					var param = "paramMap['id']="+winRecordId;
					param += "&"+$("#orderForm").serialize();
					submit(modal,tr,param);
				}
			});
		});
	});
}

function submit(modal,tr,param){
	$.ajax({
		type:'post',
		dataType:'json',
		data:param,
		url:'json/activityTurn_saveDeliverInfo',
		success:function(json){
			if(json.result == '00000000'){
				modal.fadeOut();
				tr.remove();
				W.$.alert("已保存发货信息");
			}else W.$.alert("保存发货信息失败！");
		},
		error:function(e){
			W.$.alert("发货信息提交异常！");
		}
	});
}

function bindSearch(){
	$("#searchBtn").click(function(){
		if(formObj.validate_all()){
			activityTableObj.setOptions({
	            param:"&paramMap['isDeliver']=1&"+$("#searchForm").serialize(),
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
        param:"&paramMap['isDeliver']=1",
        pageSize:12,
        paging:true,
        finishCallBack:function(){},
        template:htmlTemplate
    });
}

function htmlTemplate(index,data,recordIndex){
	var html = "";
	html += '<tr id="'+data.id+'">';
	html += '<td>'+recordIndex+'</td>';
	//判断相簿还是台历（跳转的页面和显示不同）
	if(data.name.indexOf("相簿") != -1){
		html += '<td><a href="activityPages_drawResultDiy?prizeId='+data.id+'"><span class="text-info">'+data.name+'</span></a></td>';
	}else if(data.name.indexOf("台历") != -1){
		html += '<td><a href="activityPages_drawResultCalendar?prizeId='+data.id+'"><span class="text-info">'+data.name+'</span></a></td>';
	}else html += '<td>'+data.name+'</td>';
	
	html += '<td>'+data.account+'</td>';
	html += '<td>'+data.receiver+'</td>';
	html += '<td>'+data.receiverPhone+'</td>';
	html += '<td>'+data.receiverAddr+'</td>';
	html += '<td>'+formatUTC(data.winningUtc,"yyyy-MM-dd hh:mm")+'</td>';
	html += '<td><button class="btn btn-info btn-deliver"><span class="glyphicon glyphicon-ok"></span>&nbsp;确认发货</button></td>';
	html += '</tr>';
	return html;
}
</script>
</body>

</html>