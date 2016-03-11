<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="public/uploadify/uploadify.css" rel="stylesheet">
<link href="public/iconfont/iconfont.css" rel="stylesheet">
<script src="public/uploadify/jquery.uploadify.min.js"></script>
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
        	<li role="presentation"><a href="activityPages_activityDelivered">已发奖</a></li>
        	<li role="presentation" class="active"><a href="activityPages_activityGiftList">查看奖品</a></li>
        </ul>
    	<div style="margin-top: 15px;">
    	<form class="retrieval" id="searchForm" action="javascript:void(null);">
    		<div class="form-group pull-left" style="width:350px;">
	    		<div class="input-group">
	    			<div class="input-group-addon">关键字</div>
                  	<input class="form-control" name="paramMap['keyWords']" data-valid="[{rule:'not_empty'}]" type="text" maxlength="30" placeholder="奖品名称">
	    		</div>
           </div>
           &nbsp;<button type="submit" id="searchBtn" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 搜索</button>
			<div class="pull-right">
	        	<button class="btn btn-default btn-add"><span class="glyphicon glyphicon-plus"></span>新增奖品</button>
	        </div>
		</form>
		</div>
		
		<table id="giftTable" class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
			<thead>
   				<tr>
   					<td>NO.</td>
   					<td>物品名称</td>
   					<td>图片路径</td>
   					<td>数量</td>
   					<td>中奖概率</td>
   					<td>添加时间</td>
   					<td>修改时间</td>
   					<td>操作</td>
   				</tr>
   			</thead>
			<tbody class="bg-white"></tbody>
			<tfoot><tr><td colspan="8">&nbsp;</td></tr></tfoot>
		</table>
	</div>
</div>

<!-- modal -->
<div id="insertModal" class="modal fade" style="display:none; background-color:rgba(0,0,0,0.4); overflow:hidden;">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">新增奖品</h4>
      </div>
      <div class="modal-body">
        	<form id="insertForm" action="javascript:void(null)" class="form-horizontal">
                 <div class="form-group">
                     <label class="col-xs-3 control-label">奖品名称：</label>
                     <div class="col-xs-9">
                     	<input class="form-control input-sm r-lg" type="text" name="gift['name']" data-valid="[{rule:'not_empty'}]" maxlength="20" placeholder="奖品名称">
                     </div>
                 </div>
                 
                 <div class="form-group">
                 	<label class="col-xs-3 control-label"></label>
                 	<div class="col-xs-9">
	                 	<input class="form-control" type="file" id="uploadGiftPicture">
	                	<div id="fileQueue"></div>
                	</div>
                 </div>
                 
                  <div class="form-group">
                     <label class="col-xs-3 control-label">图片路径：</label>
                     <div class="col-xs-9">
                     	<input id="insertUrl" class="form-control input-sm r-lg" type="text" readonly name="gift['url']" data-valid="[{rule:'not_empty'},{rule:'url'}]" maxlength="100" placeholder="【上传后自动获得】">
                     </div>
                 </div>
                  <div class="form-group">
                     <label class="col-xs-3 control-label">奖品数量：</label>
                     <div class="col-xs-9">
                     	<input class="form-control input-sm r-lg" type="text" name="gift['giftNumber']" data-valid="[{rule:'not_empty'},{rule:'positive'}]" maxlength="20" placeholder="请输入正整数">
                     </div>
                 </div>
                  <div class="form-group">
                     <label class="col-xs-3 control-label">中奖概率：</label>
                     <div class="col-xs-9">
                     	<input class="form-control input-sm r-lg" type="text" name="paramMap['probability']" data-valid="[{rule:'not_empty'},{rule:'toFixed',value:6}]" maxlength="8" placeholder="0~1之间的小数，最多6位小数">
                     </div>
                 </div>
                  <div class="form-group">
                     <label class="col-xs-3 control-label">描述：</label>
                     <div class="col-xs-9">
                     	<textarea  class="form-control" name="gift['description']" data-valid="[{rule:'not_empty'}]" maxlength="200" placeholder="描述信息，少于200个字"></textarea>
                     </div>
                 </div>
        	</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary btn-submit">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<!-- modal -->
<div id="editModal" class="modal fade" style="display:none; background-color:rgba(0,0,0,0.4); overflow:hidden;">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">编辑奖品信息</h4>
      </div>
      <div class="modal-body">
        	<form id="editForm" action="javascript:void(null)" class="form-horizontal">
                 <div class="form-group">
                     <label class="col-xs-3 control-label">奖品名称：</label>
                     <div class="col-xs-9">
                     	<input id="name" class="form-control input-sm r-lg" type="text" name="gift['name']" data-valid="[{rule:'not_empty'}]" maxlength="20" placeholder="奖品名称">
                     </div>
                 </div>
                 
                 <div class="form-group">
                 	<label class="col-xs-3 control-label"></label>
                 	<div class="col-xs-9">
	                 	<input class="form-control" type="file" id="editUpload">
	                	<div id="editQueue"></div>
                	</div>
                 </div>
                 
                  <div class="form-group">
                     <label class="col-xs-3 control-label">图片路径：</label>
                     <div class="col-xs-9">
                     	<input id="url" class="form-control input-sm r-lg" type="text" readonly name="gift['url']" data-valid="[{rule:'not_empty'},{rule:'url'}]" maxlength="100" placeholder="【上传后自动获得】">
                     </div>
                 </div>
                  <div class="form-group">
                     <label class="col-xs-3 control-label">奖品数量：</label>
                     <div class="col-xs-9">
                     	<input id="giftNumber" class="form-control input-sm r-lg" type="text" name="gift['giftNumber']" data-valid="[{rule:'not_empty'},{rule:'positive'}]" maxlength="20" placeholder="请输入正整数">
                     </div>
                 </div>
                  <div class="form-group">
                     <label class="col-xs-3 control-label">中奖概率：</label>
                     <div class="col-xs-9">
                     	<input id="probability" class="form-control input-sm r-lg" type="text" name="paramMap['probability']" data-valid="[{rule:'not_empty'},{rule:'toFixed',value:6}]" maxlength="8" placeholder="0~1之间的小数，最多6位小数">
                     </div>
                 </div>
                  <div class="form-group">
                     <label class="col-xs-3 control-label">描述：</label>
                     <div class="col-xs-9">
                     	<textarea id="description" class="form-control" name="gift['description']" data-valid="[{rule:'not_empty'}]" maxlength="200" placeholder="描述信息，少于200个字"></textarea>
                     </div>
                 </div>
                 <input  class="hide form-control" id="id" name="gift['id']" data-valid="[{ruel:'not_empty'}]">
                 <input class="hide form-control"  id="createUtc" name="gift['createUtc']" data-valid="[{ruel:'not_empty'}]">
        	</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary btn-update">更新</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
var W = window.top;
var tableObj = null,formObj = null,mapObj = new HashMap(),insertFormObj = null;
var editFormObj = null;
$(function(){
	
    initDat();
   
   	//搜索
   	bindSearch();
   	
   	//新增奖品触发
   	bindInsert();
   	
   	//奖品新增提交
   	bindPrizeSubmit();
   	
   	//编辑
   	bindEdit();
   	
   	//删除
   	bindDelete();
   	
   	//上传图片获得url
   	bindUpload();
   	
   	//更新
   	bindUpdate();
   	
   
   
});

function bindUpdate(){
	$("#editModal .btn-update").click(function(){
		if(editFormObj.validate_all()){
			var param = $("#editForm").serialize();
			$.ajax({
				type:'get',
				dataType:'json',
				data:param,
				url:'json/activityTurn_updateGift',
				success:function(json){
						if(json.result == '00000000'){
							$("#editModal").fadeOut();
							tableObj.refresh();
							W.$.alert("更新信息成功！");
						}else W.$.alert("更新失败！");
				},
				error:function(e){
					W.$.alert("更新异常！");
				}
			});
		}
	});
}

function bindUpload(){
	$("#uploadGiftPicture").uploadify({
			'formData': {},
			'swf': 'public/uploadify/uploadify.swf',
			'cancelImg': 'public/uploadify/images/cancel.png',
			'queueID': 'fileQueue',
			'buttonText' : '上传奖品图片',
			'fileObjName' : 'image',
			'fileTypeExts' : '*.png',
			'uploader' : 'json/resourceImage_uploadOSSImage',
			'auto': true,
			'multi': false,
			'onUploadSuccess' : function(file, data , response) {
				try{
					var data = eval("(" + data + ")");
					if(data.result == '00000000'){
						$("#insertUrl").val(data.imageUrl);
					}else W.$.alert("上传失败！");
				}catch(e){
					W.$.alert("返回有误");
				}
				
			}
			
		});
}

function editUpload(){
	$("#editUpload").uploadify({
			'formData': {},
			'swf': 'public/uploadify/uploadify.swf',
			'cancelImg': 'public/uploadify/images/cancel.png',
			'queueID': 'editQueue',
			'buttonText' : '上传奖品图片',
			'fileObjName' : 'image',
			'fileTypeExts' : '*.png',
			'uploader' : 'json/resourceImage_uploadOSSImage',
			'auto': true,
			'multi': false,
			'onUploadSuccess' : function(file, data , response) {
				try{
					var data = eval("(" + data + ")");
					if(data.result == '00000000'){
						$("#editForm #url").val(data.imageUrl);
					}else W.$.alert("上传失败！");
				}catch(e){
					W.$.alert("返回有误");
				}
				
			}
			
		});
}

function bindDelete(){
	$("#giftTable .del").livequery(function(){
		$(this).click(function(){
			var tr = $(this).parents("tr");
			var id = tr.attr("id");
			$.confirm("是否要删除奖品?",function(){
				$.ajax({
					type:'get',
					dataType:'json',
					data:{"paramMap['id']":id},
					url:'json/activityTurn_delGift',
					success:function(json){
						if(json.result == '00000000'){
							tr.remove();
							W.$.alert("已成功删除！");
						}else W.$.alert("删除失败！");
					},
					error:function(e){
						W.$.alert("删除异常！");
					}
				});
			});
		});
	});
}

function bindEdit(){
	$("#giftTable .edit").livequery(function(){
		$(this).click(function(){
			var id = $(this).parents("tr").attr("id");
			var gift = mapObj.get(id);
			
			for ( var i in gift) {
				if(i == 'probability'){
					$("#"+i).val(gift[i]/1000000);
				}else $("#"+i).val(gift[i]);
			}
			var modal = $("#editModal");
			modal.removeClass("fade").fadeIn();
			editUpload();
			modal.find(".close").click(function(){
				modal.fadeOut();
			});;
		});
	});
}

function bindPrizeSubmit(){
	$("#insertModal .btn-submit").bind('click',function(){
		if(insertFormObj.validate_all()){
			var param = $("#insertForm").serialize();
			$.ajax({
				type:'post',
				dataType:'json',
				data:param,
				url:'json/activityTurn_addGift',
				success:function(json){
					if(json.result == '00000000'){
						$("#insertModal").fadeOut();
						tableObj.refresh();
						W.$.alert("新增奖品成功");
					}else W.$.alert("新增奖品失败");
						
				},
				error:function(e){
					W.$.alert("新增奖品异常");
				}
			});
		}
	});
}

function bindInsert(){
	$("#searchForm .btn-add").click(function(){
		$("#insertForm input[type='text']").val("");
		
		
		var insertModal = $("#insertModal");
		insertModal.removeClass("fade").fadeIn();
		var close = insertModal.find(".close");
		close.click(function(){
			insertModal.fadeOut();
		});
	});
	
	
}

function bindSearch(){
	$("#searchBtn").click(function(){
		if(formObj.validate_all()){
			tableObj.setOptions({
	            param:$("#searchForm").serialize(),
	            url:'json/activityTurn_findGiftList'
	        });
		}
	});
}

function initDat(){
	//验证
	formObj = $("#searchForm").inputValid(valid_message_options);
	insertFormObj = $("#insertForm").inputValid(valid_message_options);
	editFormObj = $("#editForm").inputValid(valid_message_options);
	
	tableObj = $("#giftTable").tableList({
        url:"json/activityTurn_findGiftList",
       // param:"&paramMap['isDeliver']=0",
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
	html += '<td>'+data.name+'</td>';
	html += '<td>'+fixSize(data.url)+'</td>';
	html += '<td>'+data.giftNumber+'</td>';
	html += '<td>'+(data.probability/1000000)+'</td>';
	html += '<td>'+formatUTC(data.createUtc,"yyyy-MM-dd hh:mm")+'</td>';
	html += '<td>'+formatUTC(data.modifyUtc,"yyyy-MM-dd hh:mm")+'</td>';
	html += '<td>';
	html += '<button class="btn btn-warning btn-xs edit"><span class="glyphicon glyphicon-edit">编辑</span></button>';
	html += '<button class="btn btn-danger btn-xs del"><span class="glyphicon glyphicon-minus-sign">删除</span></button>';
	html += '</td>';
	html += '</tr>';
	return html;
}

function fixSize(url){
	if(null != url && 0 != url.trim().length){
		var s = "";
		if(-1 == url.indexOf("@")){
			s = url+"@170w_100h_2o.png";
		}else{
			s = url.substring(0,url.indexOf("@"))+"@170w_100h_2o.png";
		}
		return '<image src="'+s+'">';
	}
	return "";
}

</script>
</body>

</html>