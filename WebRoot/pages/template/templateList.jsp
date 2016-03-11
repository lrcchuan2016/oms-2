<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>美家秀后台管理系统</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<style type="text/css">
	.hidden{display:none}
	.cor_bs,.cor_bs:hover{color:#ffffff;}
	.keBody{background:url(public/img/bodyBg.jpg) repeat #333;}
	.msKeimgBox{margin:0px auto;width:965px;overflow:hidden;position:relative;padding-top:30px;height:93px;}
	.mskeLayBg{background:#000;width:100%;position:absolute;left:0px;top:0px;z-index:10;opacity:0.7;filter:alpha(opacity=70);display:none;_display:none!important }
	.mskelayBox{width:974px;margin-top:-251px;margin-left:-494px;position:fixed;left:50%;top:50%;border:7px solid #FFF;z-index:20;background:#FFF;display:none;_display:none!important }
	.mskeImgBg{height:57px;width:100%;position:absolute;left:0px;bottom:0px;}
	.mskeClaose{position:absolute;top:-17px;right:-17px;cursor:pointer;}
	.mske_imgDown{background:url(public/img/mke_imgMbg.png) repeat;height:57px;width:818px;position:absolute;right:0px;bottom:0px;font:13px/57px "微软雅黑";color:#FFF;}
	.mske_imgDown a{margin-left:34px;}
</style>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">
	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">模版管理</a></li>
      <li class="active">模版列表</li>
    </ol>
    
    <div class="userListbox channel-list" style="margin-right:20px;">
    	<form class="retrieval" id="searchForm" action="javascript:void(null);">
    		<div class="form-group pull-left" style="width:150px;">
    			<div class="input-group">
                  <div class="input-group-addon">类别</div>
    				<select class="form-control type" name="paramMap['type']" onchange="javascript:selectType()">
    					<option value="0">所有</option>
    				</select>
    			</div>
    		</div>
    		<div class="form-group pull-left" style="width:250px;">
	    		<div class="input-group">
	    			<div class="input-group-addon">关键字</div>
                  	<input class="form-control" name="paramMap['searchKeyWord']" type="text" maxlength="100" placeholder="模版名称">
	    		</div>
           </div>
           &nbsp;<button type="submit" id="searchBtn" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 搜索</button>
           <div class="pull-right btn-group">
           		<a href="templatePages_templateAdd" class="btn btn-default"><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;创建模版</a>
          		<a class="btn btn-info btn-all"><span class="glyphicon glyphicon glyphicon-th-list"></span>&nbsp;全部模板</a>
           </div>
		</form>
		<div class="col-xs-12 padder-tm bg-empty">
			<div id="successTip" class="alert text-black alert-success hide fade in"></div>
			<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
		</div>
		<table id="templateTable" class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
			<thead>
    			<tr>
    				<td data-sort="int" style="width:50px;">ID</td>
					<td style="width:220px;">操作</td>
					<td style="width:160px;">模版名称</td>
					<td style="width:140px;">模版类别</td>
					<td>模版描述</td>
					<td style="width:220px;">创建时间</td>
    			</tr>
    		</thead>
			<tbody class="bg-white" id="templateTbody"></tbody>
			<tfoot><tr><td colspan="6">&nbsp;</td></tr></tfoot>
		</table>
	</div>
	<!--图库弹出层 开始-->
	 
	<div class="outbox">
		<div class="mskeLayBg"></div>
		<div class="mskelayBox">
			<div class="mske_html">
				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
			  
			  <!-- Wrapper for slides -->
			  <div class="carousel-inner showPreviews" role="listbox">
			      	<div class='item active'><img src='public/pic/template/1.jpg'><div class='carousel-caption'><span class='badge text-warning'>1</span><span class='badge'>3</span></div></div>
			  		<div class='item'><img src='public/pic/template/2.jpg'><div class='carousel-caption'><span class='badge text-warning'>2</span><span class='badge'>3</span></div></div>
			  		<div class='item'><img src='public/pic/template/3.jpg'><div class='carousel-caption'><span class='badge text-warning'>3</span><span class='badge'>3</span></div></div>
			  </div>

			  <!-- Controls -->
			  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
			    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
			    <span class="sr-only">Previous</span>
			  </a>
			  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
			    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			    <span class="sr-only">Next</span>
			  </a>
		</div>
			</div>
			<img class="mskeClaose" src="public/img/mke_close.png" width="47" height="47" />
		</div>
	</div>
	<!--图库弹出层 结束-->
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
var actionObj = null,templateTableObj = null,mapObj = new HashMap();
var arrs = new Array();
$(function(){
	initTemplateGroupList();
	$(".mskeLayBg").height($(document).height());
	//$(".mskeLayBg").width($(document).width());
	//alert($(document).width()+","+$(document).height());
	$(".mskeClaose").click(function(){
		$(".mskeLayBg,.mskelayBox").hide();
		$(".mskeLayBg").children("audio")[0].pause();  //关闭按钮时取消播放
	});
	formObj = $("#searchForm").inputValid(valid_message_options);
	templateTableObj = $("#templateTable").tableList({
        url:"json/template_findTemplatePage",
        paging:true,
        pageSize:12,
        finishCallBack:initTemplate,
        template:htmlTemplate
    });
	actionObj = new Actions({ 
		successCallBack:function() { templateTableObj.refresh(); } 
	});
	alertObj = new Alert({});
	
	$("#searchBtn").click(function(){
		if(formObj.validate_all()){
			templateTableObj.setOptions({
	            param:$("#searchForm").serialize(),
	            url:'json/template_findTemplatePage'
	        });
		}
	});
	
	//collapse定时滑动
	$('#myCollapsible').collapse({
  		toggle: false
	});
	
	//初始化类别
	function initTemplateGroupList(){
		$.post("json/template_findTemplateGroupPage","page.startIndex=0&page.pageSize=10000",function(data){
			if(data!=null&&data.list!=null){
				var html = "";
				$.each(data.list.records,function(){
					html += "<option value="+this.id+">"+this.name+"</option>";
				});
				$("select.type").append(html);
			}
		});
	}
	
	//绑定下载模板事件
	bindTemplateDownload();
	
	//绑定显示全部模板事件（只显示一页，方便进入拖动排序）
	bindShowAllTemplate();
});

function bindShowAllTemplate(){
	$(".btn-all").livequery(function(){
		$(this).click(function(){
			templateTableObj.setOptions({pageSize:1000});
		});
	});
}

function dragUI(){
	var container = document.getElementById("templateTbody");
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
	    //console.info("old:"+oldIndex);
		//console.info("new:"+newIndex);
	     
	     if(newIndex<oldIndex){
	     	arrs.splice(oldIndex,1);   //删除指定位置元素
	     	arrs.splice(newIndex,0,newElement);
	     	flag = "0"   //前拖置0
	     	
	     	for(var i=newIndex;i<=oldIndex;i++){
	     		arrIds.push(arrs[i].id);
	     	}
	     }else{
	     	arrs.splice(newIndex+1,0,newElement);
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
		url:'json/template_sortTemplate',
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

function htmlTemplate(index, data, recordIndex){
	mapObj.put("t_"+data.id,data);
	arrs[index] = data;
	var html = "<tr id='"+data.id+"' style='cursor:move;'>";
	html += '<td>'+recordIndex+'</td>';
	html += '<td><div class="btn-group"><button class="btn btn-primary btn-xs show" id="'+data.id+'"><span class="glyphicon glyphicon-zoom-in"></span></button>';
	html += '<button class="btn btn-warning btn-xs edit" id="'+data.id+'"><span class="glyphicon glyphicon-edit"></span></button>';
	html += '<button class="btn btn-danger btn-xs del" id="'+data.id+'"><span class="glyphicon glyphicon-minus-sign"></span></button>';
	html += '<button class="btn btn-success btn-xs diy" id="'+data.id+'"><span class="glyphicon glyphicon-plus-sign"></span></button>';
	html += '<button class="btn btn-info btn-xs download" id="'+data.id+'">下载&nbsp;<span class="glyphicon glyphicon-download-alt"></span></button>';
	html += '</div></td>';
	html += '<td>'+data.name+'</td>';
	html += '<td>'+data.groupName+'</td>';
	html += '<td>'+data.introduction+'</td>';
	html += '<td>'+formatUTC(data.createUtc,"yyyy-MM-dd hh:mm:ss")+'</td></tr>';
	
	return html;
}

function initTemplate(){
	
	//
	dragUI();

	$(".show").unbind("click").bind("click",function(){
		var showId = $(this).attr("id");
		
		$.post("json/template_findTemplateInfo",{"paramMap['templateId']":showId},function(json){
			if(json.result == '00000000' &&json.url.length>0){
				var html = "";
				var len = json.url.length;
				var vs = json.url[len-1].split(".");
				var vs_length = vs.length;
				if(vs[vs_length-1] != 'jpg' && vs[vs_length-1] != 'jpeg' && vs[vs_length-1] != 'png' && vs[vs_length-1] != 'gif' && vs[vs_length-1] != 'bmp'){
					len--;
				}
				$.each(json.url,function(i,value){
					if(i!=0){
						if(i!=len){
							html += "<div class='item'><img src='"+value+"'><div class='carousel-caption'><span class='badge text-warning'>"+(i+1)+"</span><span class='badge'>"+len+"</span></div></div>";
						}else{
							if('null' != value){
								if(vs[vs.length-1] == 'jpg' || vs[vs.length-1] == 'jpeg' || vs[vs.length-1] == 'png' || vs[vs.length-1] == 'gif' || vs[vs.length-1] == 'bmp'){
									html += "<div class='item'><img src='"+value+"'><div class='carousel-caption'><span class='badge text-warning'>"+(i+1)+"</span><span class='badge'>"+len+"</span></div></div>";
								}else{
									$(".mskeLayBg").html("<audio autoplay loop  src='"+value+"'></audio>");
								}
							}
						}
					}else{
						html += "<div class='item active'><img src='"+value+"'><div class='carousel-caption'><span class='badge text-warning'>"+(i+1)+"</span><span class='badge'>"+len+"</span></div></div>";
					}
					
				});
				$(".showPreviews").html(html);
				$(".mskeLayBg").show();
				$(".mskelayBox").fadeIn(300);
			}else{
				W.$.alert("模版未制作完成或加载预览图失败！");		
			}
		},"json");
	});
	
	$(".edit").unbind("click").bind("click",function(){
		var $editParam = "paramMap['tempType']=tempEdit&paramMap['templateId']="+$(this).attr("id");
		window.location.href = "templateAction_initTemplatePage?"+$editParam;
	});
	
	$(".del").unbind("click").bind("click",function(){
		var $param = "templateList[0].id="+$(this).attr("id")+"&templateList[0].coverUrl="+mapObj.get("t_"+$(this).attr("id")).coverUrl;
		alertObj.cancel();
		alertObj.check({
            needConfirm:true,
            confirmTip:"您确认删除当前主题相册模版吗？",
            confirmParam:$param,
            confirmAction:delAction
        });
	});
	
	$(".diy").unbind("click").bind("click",function(){
		var url = "albumAction_templateMake?templateId="+$(this).attr("id");
		window.open(url);
		//window.location.href = "albumAction_templateMake?templateId="+$(this).attr("id");
	});
}

function delAction($param){
	actionObj.setOptions({ url:"json/template_delTemplate" });
	actionObj.submit({param:$param});
}

function selectType(){
		if(formObj.validate_all()){
			templateTableObj.setOptions({
	            param:$("#searchForm").serialize(),
	            url:'json/template_findTemplatePage'
	        });
		}
}

function bindTemplateDownload(){
	$(".download").livequery(function(){
		$(this).click(function(){
		var $this = $(this);
			var tid = $(this).attr("id");
			$.ajax({
				type:'post',
				dataType:'json',
				data:{"paramMap['id']":tid},
				url:'json/template_templateDownload',
				success:function(json){
					if(json.result =='00000000'){
						var _url = json.path;
						var a = document.createElement('a');
						a.href = _url;
						a.download = _url;
						a.id='download';
						document.body.appendChild(a);
						var alink = document.getElementById('download');
						alink.click();
						alink.parentNode.removeChild(a);
						
						$this.html('点击下载&nbsp;<span class="glyphicon glyphicon-download-alt"></span>');
						$this.removeAttr("disabled");
					}else W.$.alert("下载失败！！");
				},
				error:function(e){
					W.$.alert("下载请求异常！");
				},
				//返回响应前事件
				beforeSend:function(){
					$this.html('正在下载&nbsp;<span class="glyphicon glyphicon-download-alt"></span>');
					$this.attr("disabled","disabled");
				}
			});
		});
	});
}

</script>
</html>