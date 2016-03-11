<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">渠道管理</a></li>
      <li class="active">渠道列表</li>
    </ol>
    
    <div class="userListbox channel-list bg-white">
    	<form class="retrieval" action="channelAction_channelList" method="post" name="keyWordsForm">
      		<s:token></s:token>
             <div class="form-group pull-left" style="width:250px;">
                <div class="input-group">
                  <div class="input-group-addon">关键字</div>
                  <input type="text" class="form-control keyWords" value="${keyWords }" name="keyWords"  onfocus="this.value=''" placeholder="渠道名称">
                </div>
              </div>
              
              <button type="submit" class="btn btn-primary search"><span class="glyphicon glyphicon-search"></span>&nbsp;搜索</button>
              <a href="channelAction_channelList?status=1" class="btn btn-default pull-right"><span class="glyphicon glyphicon-trash"></span>&nbsp;渠道回收站</a>
              <!---->
    	</form>
        
    	<table class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
    		<thead>
    			<tr>
    				<td>排名</td>
    				<td class="hide">渠道ID</td>
    				<td>logo</td>
    				<td>名称</td>
    				<td class="coverNum"><a href="channelAction_channelList?sortFiled=coverNum&&sortMod=desc">覆盖用户</a></td>
    				<td class="installRate" ><a href="channelAction_channelList?sortFiled=installRate&&sortMod=desc">安装率</a></td>
    				<td class="registerNum"><a  href="channelAction_channelList?sortFiled=registerNum&&sortMod=desc">注册用户</a></td>
    				<td class="conversionRate"><a href="channelAction_channelList?sortFiled=conversionRate&&sortMod=desc">转换率</a></td>
    				<td class="growthRate"><a href="channelAction_channelList?sortFiled=growthRate&&sortMod=desc">增长率</a></td>
                    
    				<td>操作</td>
    			</tr>
    		</thead>
    		<c:if test="${pagination.totalCount!=0 }">
    		<tbody>
    		<c:if test="${null!=pagination }">
    				<c:forEach items="${pagination.records }" var="ch" varStatus="i">
    			
    			<tr id="${ch.id }">
    				<td><span>${(page-1)*pageSize+i.count }</span></td>
    				<td class="hide">${ch.id }</td>
    				<td><img class="channel_logo" src="${ch.logoUrl }"></td>
    				<td>${ch.name }</td>
    				<td><c:if test="${ch.coverNum!=null }">${ch.coverNum }</c:if><c:if test="${ch.coverNum==null }">0</c:if></td>
    				<td class="rate">
    					${ch.installRate }
    				</td>
    				<td><c:if test="${ch.registerNum!= null }">${ch.registerNum }</c:if><c:if test="${ch.registerNum==null }">0</c:if></td>
    				<td class="rate">
    					${ch.conversionRate }
    				<td class="rate">
    					${ch.growthRate }
    				</td>
    				<td>
    					<a class="btn btn-info btn-sm" href="channelAction_initChannel?keyWords=edit&&channelId=${ch.id }">编辑</a>
    					<a class="btn btn-success btn-sm" href="channelAction_initChannel?channelId=${ch.id }">查看</a>
    					<a class="btn btn-danger btn-sm" >删除</a>
    				</td>
    			</tr>
    			</c:forEach>
    		</c:if>
    		</tbody>
    		 </c:if>	
    	</table>
    	 <c:if test="${totalPage>1 }">
        <nav class="pages_number">
          <ul class="pagination pull-right">
            ${pageLinks }
          </ul>
          <div class="pull-right">
          	<span>共${totalPage }页<strong>${pagination.totalCount }</strong>个渠道&nbsp;&nbsp;</span>
          </div>
        </nav>
        </c:if>
	</div><!---userListbox-end-->
    </div>
<script>

var W = window.top;
var sortFiled = '${sortFiled}';
var sortMod = '${sortMod}';
var keyWords = '${keyWords}';
$(function(){
	$("td .btn-danger").livequery(function(){
		$(this).click(function(){
			var id = $(this).parents("tr").attr("id");
			$.deleteChannel(id);
		});
	});
	//改变排名的颜色
	$("tbody > tr >td >span").livequery(function(){
		$(this).each(function(){
			var html = $(this).html();
			if(html == '1'){
				$(this).addClass("label-danger").addClass("label");
			}else if(html == '2'){
				$(this).addClass("label-warning").addClass("label");
			}else if(html == '3'){
				$(this).addClass("label-success").addClass("label");
			}
		});
	});
	//计算比率
	$(".rate").livequery(function(){
		$(this).each(function(){
			var v = $(this).html();
			$(this).html(Math.round(v*10000)/100+"%");
		});
	});
	//控制排序图标
	if(sortMod == 'desc'){
		var a = $("."+sortFiled).children("a");
		var href = a.attr("href");
		a.attr("href",href.replace('desc','asc'));
		a.html(a.html()+"<span class='glyphicon glyphicon-arrow-up'></span>");
	}else{
		var a = $("."+sortFiled).children("a");
		var href = a.attr("href");
		a.attr("href",href.replace('asc','desc'));
		a.html(a.html()+"<span class='glyphicon glyphicon-arrow-down'></span>");
	};
	if(keyWords != null){
		$("thead > tr > td:has(a)").children("a").each(function(){
			$(this).attr("href",$(this).attr("href")+"&&keyWords="+keyWords);
		});
	}
});


$.extend({
	deleteChannel : function(_id){
		W.$.confirm(
			"是否要删除渠道:"+_id+"？",
			function(){
				//此处加入删除的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						channelId : _id
					},
					url:'json/channel_updateChannel', //API
					success:function(json) {
						//删除成功回调
						if(json.result == "00000000"){
							W.$.alert("删除成功！");
							$("#" + _id).remove(); //删除行
						}else {
							W.$.alert("启用失败！");
						}
					},
					error: function(data) {
						W.$.alert("删除失败！");
					}
				});
			},
			function(){
				//alert("取消按钮");
			}
		);
	}
});
</script>
</body>
</html>
