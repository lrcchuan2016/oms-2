<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body>
<div class="container-fluid">
	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">模版管理</a></li>
      <li><a href="javascript:void(0)">推荐音乐</a></li>
      <li class="active">主题音乐推荐</li>
    </ol>
    
    <div class="userListbox channel-list" style="margin-right:20px;">
    	<ul class="nav nav-tabs">
          <li role="presentation"><a href="mediaPages_musicList">普通推荐音乐</a></li>
          <li role="presentation" class="active"><a href="mediaPages_themeMusic">主题推荐音乐</a></li>
        	<li role="presentation"><a href="mediaPages_defaultMusic">默认音乐</a></li>
        </ul>
    	<div style="margin-top: 15px;">
    	<form class="retrieval" id="searchForm" action="javascript:void(null);">
    		<input class="hide" name="paramMap['searchMode']" value="1">
    		<input class="hide" name="paramMap['searchType']" value="1">
    		<div class="form-group pull-left" style="width:350px;">
	    		<div class="input-group">
	    			<div class="input-group-addon">关键字</div>
                  	<input class="form-control" name="paramMap['searchKeyWord']" data-valid="[{rule:'not_empty'}]" type="text" maxlength="100" placeholder="名称">
	    		</div>
           </div>
           &nbsp;<button type="submit" id="searchBtn" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 搜索</button>
           <div class="pull-right btn-group">
           		<a href="mediaPages_mediaAdd?option=1&&type=2" class="btn btn-default"><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;上传音乐</a>
           		<a class="btn btn-info btn-showAll" href="javascript:void(0);"><span class="glyphicon glyphicon-th-list"></span>&nbsp;显示全部</a>
           </div>
		</form>
		</div>
		<div class="col-xs-12 padder-tm bg-empty">
			<div id="successTip" class="alert text-black alert-success hide fade in"></div>
			<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
		</div>
		<table id="musicTable" class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
			<thead>
   				<tr>
   					<td data-sort="int" style="width:50px;">ID</td>
					<td style="width:120px;">操作</td>
					<td style="width:220px;">音乐名称</td>
					<td style="width:140px;">音乐时长</td>
					<td>试听下载</td>
					<td style="width:220px;">创建时间</td>
					<td>加入默认</td>
   				</tr>
   			</thead>
			<tbody id="musicTbody" class="bg-white"></tbody>
			<tfoot><tr><td colspan="7">&nbsp;</td></tr></tfoot>
		</table>
	</div>
</div>

	<div id="alertDialog" class="modal" tabindex="-1">
		<div class="modal-dialog" style="width:300px;">
			<div class="modal-content">
				<div class="modal-header"><h4></h4></div>
				<div class="modal-body"></div>
				<div class="modal-footer" style="text-align: center;">
					<button class="btn btn-default btn-sm r-lg btn-cancel">
						<span class="glyphicon glyphicon-remove "></span> 取消
					</button>
					<button class="btn btn-primary btn-sm r-lg btn-confirm">
						<span class="glyphicon glyphicon-ok"></span> 确认
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var W = window.top;
var actionObj = null,musicTableObj = null,mapObj = new HashMap();
var arrs = new Array();
$(function(){
	formObj = $("#searchForm").inputValid(valid_message_options);
	musicTableObj = $("#musicTable").tableList({
        url:"json/media_findMusicPage",
        param:"paramMap['searchType']=1",
        pageSize:12,
        paging:true,
        finishCallBack:initMusic,
        template:htmlTemplate
    });
	actionObj = new Actions({ 
		successCallBack:function() { musicTableObj.refresh(); } 
	});
	alertObj = new Alert({});
	$("#searchBtn").click(function(){
		if(formObj.validate_all()){
			musicTableObj.setOptions({
	            param:$("#searchForm").serialize(),
	            url:'json/media_findMusicPage'
	        });
		}
	});
	
	//绑定显示所有
	bindShowAll();
});

function htmlTemplate(index, data, recordIndex){
	mapObj.put("t_"+data.id,data);
	arrs[index]=data;
	var html = "<tr id="+data.id+" style='cursor:move;'>";
	html += '<td>'+recordIndex+'</td>';
	html += '<td><div class="btn-group"><button class="btn btn-danger btn-xs del" id="'+data.id+'"><span class="glyphicon glyphicon-minus-sign"></span> 删除</button><button class="btn btn-warning btn-xs edit" id="'+data.mediaId+'"><span class="glyphicon glyphicon-edit"></span> 编辑</button></div></td>';
	html += '<td>'+data.title+'</td>';
	if(data.timeLen ==0){
		html += '<td>未知<//td>';
	}else{
	html += '<td>'+formatUTC(data.timeLen-8*60*60*1000,"hh:mm:ss")+'</td>';
	}
	html += '<td><a href="'+data.url+'"><span class="text-info">点击下载试听</span></a></td>';
	html += '<td>'+formatUTC(data.utc,"yyyy-MM-dd hh:mm:ss")+'</td>';
	html += '<td><span class="glyphicon glyphicon-thumbs-up" style="cursor:pointer;"></span></td></tr>';
	
	return html;
}

function initMusic(){
	$(".edit").unbind("click").bind("click",function(){
		var $editParam = "paramMap['mediaType']=mediaEdit&paramMap['mediaId']="+$(this).attr("id");
			$editParam += "&paramMap['id']="+$(this).parents("tr").attr("id");
		window.location.href = "mediaAction_initMediaPage?"+$editParam;
	});
	
	$(".del").unbind("click").bind("click",function(){
		var $param = "mediaList[0].id="+$(this).parents("tr").attr("id")+"&mediaList[0].content="+mapObj.get("t_"+$(this).attr("id")).url+"&mediaList[0].type=2";
		alertObj.cancel();
		alertObj.check({
            needConfirm:true,
            confirmTip:"您确认删除当前音乐文件吗？",
            confirmParam:$param,
            confirmAction:delAction
        });
	});
	
	//拖动排序
	dragUI();
}

//显示全部推荐音乐
function bindShowAll(){
	$(".userListbox .btn-showAll").click(function(){
		musicTableObj.setOptions({pageSize:1000});
		
	});
}

function dragUI(){
	var container = document.getElementById("musicTbody");
	var sort = Sortable.create(container, {
	  animation: 150,  
	  onUpdate: function (evt){
	     var item = evt.item;
	     var newIndex = evt.newIndex;
	     var oldIndex = evt.oldIndex;
	     var newElement = arrs[oldIndex];   //要移动的元素
	     var oldElement = arrs[newIndex];	//原来位置上的元素
	     var flag = "";
	     var arrIds = new Array();
	     
	     if(newIndex<oldIndex){
	     	arrs.splice(oldIndex,1);   //删除指定位置元素
	     	arrs.splice(newIndex,0,newElement);   //插入
	     	flag = "0"   //前拖置0
	     	
	     	for(var i=newIndex;i<=oldIndex;i++){
	     		console.info(i);
	     		arrIds.push(arrs[i].id);
	     	}
	     }else{
	     	arrs.splice(newIndex+1,0,newElement);   //后拖一定要先加1
	     	arrs.splice(oldIndex,1);
	     	flag = "1";   //后拖置1
	     	
	     	for(var i=oldIndex;i<=newIndex;i++){
	     		console.info(i);
	     		arrIds.push(arrs[i].id);
	     	}
	     }
		
	     var param = {"paramMap['ids']":arrIds.join(","),"paramMap['flag']":flag};
		sortSubmit(param);
	  }
	});
}

function sortSubmit(param){
	$.ajax({
		type:'post',
		dataType:'json',
		data:param,
		url:'json/media_sortMusic',
		success:function(json){
			if(json.result != "00000000"){
	    		W.$.alert("拖动排序错误！");
	     	}
		},
		error:function(e){
			W.$.alert("排序发生异常！");
		}
	});
}

function delAction($param){
	actionObj.setOptions({ url:"json/media_del" });
	actionObj.submit({param:$param});
}

 $(".glyphicon-thumbs-up").livequery(function(){
 	$(this).click(function(){
 		var tr = $(this).parents("tr");
 		var id = tr.attr("id");
			$.recommend(id,tr.children("td").eq(2).html());
 	});
 });

$.extend({
	recommend : function(_id,name){
		W.$.confirm(
			"是否要添加到推荐:"+name+"？",
			function(){
				//此处加入添加的AJAX
				$.ajax({
					type:"POST",
					dataType:"json",
					data:{
						"paramMap['id']" : _id,
						"paramMap['option']" : '0'
					},
					url:'json/media_recommendHandler', //API
					success:function(json) {
						//添加成功回调
						if(json.result == "00000000"){
							W.$.alert("加入默认音乐成功！");
							//$("#" + _id).remove(); //删除行
							//$(".badge").html(parseInt($(".badge").html())+1);
						}else {
							W.$.alert("失败！"+json.tip);
						}
					},
					error: function(data) {
						W.$.alert("失败！");
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
</html>