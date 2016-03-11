<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">
	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">模版管理</a></li>
      <li class="active">装饰品列表</li>
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
			<div class="form-group pull-left">
				<input class="form-control keyWords" name="paramMap['searchKeyWord']" type="text" maxlength="100" placeholder="饰品名">
			</div>
           &nbsp;<button type="submit" id="searchBtn" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 搜索</button>
           <div class="pull-right btn-group">
           		<a href="decoratePages_decorateBatch" class="btn btn-default"><span class="glyphicon glyphicon-export"></span>&nbsp;批量上传</a>
           		<a href="decoratePages_decorateAdd" class="btn btn-default"><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;创建装饰品</a>
           		<a href="javascript:void(0);" class="btn btn-info btn-showAll"><span class="glyphicon glyphicon-th-list"></span>&nbsp;显示全部</a>
           </div>
		</form>
		<div class="col-xs-12 padder-tm bg-empty">
			<div id="successTip" class="alert text-black alert-success hide fade in"></div>
			<div id="failedTip" class="alert text-black alert-danger hide fade in"></div>
		</div>
		<div class="col-xs-12 no-padder" style="min-height:450px;">
			<div class="tab-pane fade in">
				<div class="panel panel-default">
					<div class="panel-body" id="decorateBody" style="padding:10px 5px;"></div>
					<div class="panel-foot pull-right" id="pagination"></div>
				</div>
				
			</div>
		</div>
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
var formObj = null,actionObj = null,decorateTableObj = null,mapObj = new HashMap(),groupList = new Array();
var arrs = new Array();
var page_options = {
    size:"small",
    alignment:"center",
    currentPage:1,
    totalPages:1,
    pageSize:12,
    totalCount:0,
    itemContainerClass:function(type, page, current) {
        return page === current ? "active" :"pointer-cursor";
    },
    onPageClicked:function(e, originalEvent, type, page) {
        if (page >= page_options.totalPages) page_options.currentPage = page_options.totalPages; else page_options.currentPage = page;
        //var param = {"paramMap['type']":$("select.type").val(),"paramMap['searchKeyWord']":$(".keyWords").val()};
       	var param = "&paramMap['type']="+$("select.type").val()+"&paramMap['searchKeyWord']="+$(".keyWords").val();
        initDecorateList((page_options.currentPage - 1) * page_options.pageSize, page_options.pageSize,param);
    },
    tooltipTitles:function(type, page, current) {
        switch (type) {
          case "first":
            return page_message_options.first;
          case "prev":
            return page_message_options.prev;
          case "next":
            return page_message_options.next;
          case "last":
            return page_message_options.last;
          case "page":
            return page === current ? page_message_options.current_page + page :page_message_options.goto_page + page + page_message_options.page;
        }
    }
};
$(function(){
	//初始化饰品分组
	initDecorateGroupList();

	formObj = $("#searchForm").inputValid(valid_message_options);
	//初始化数据
    var param = "&paramMap['type']="+$("select.type").val()+"&paramMap['searchKeyWord']="+$(".keyWords").val();
    initDecorateList(page_options.currentPage-1,page_options.pageSize,param);
	//
	actionObj = new Actions({ 
		successCallBack:function() { initDecorateList(page_options.currentPage-1,page_options.pageSize,param); } 
	});
	//
	alertObj = new Alert({});
	$("#searchBtn").click(function(){
		if(formObj.validate_all()){
			param = "&paramMap['type']="+$("select.type").val()+"&paramMap['searchKeyWord']="+$(".keyWords").val();
			page_options.currentPage = 1;
			initDecorateList(page_options.currentPage-1,page_options.pageSize,param);
		}
	});
	
	//绑定显示全部饰品列表
	bindShowAllDecorate();
});

function initDecorateList(__startIndex,__pageSize,__plusParam){
	var $pageParam = "&page.startIndex="+__startIndex+"&page.pageSize="+__pageSize+"&"+__plusParam;
	$(".decorateBody").html("");
	$.post("json/decorate_findDecoratePage",$pageParam,function(__data){
		if(__data != null){
			var pageObj = __data["list"],dataList = pageObj.records,$htmlstr = "";
			page_options.totalPages = pageObj.totalCount % page_options.pageSize == 0 ? Math.floor(pageObj.totalCount / page_options.pageSize) :Math.floor(pageObj.totalCount / page_options.pageSize) + 1;
            page_options.totalCount = pageObj.totalCount;
            if (page_options.totalPages > 1) {
                $("#pagination").bootstrapPaginator(page_options);
            } else {
                $("#pagination").html("&nbsp;");
            }
	        $.each(dataList,function(index){
	        	$htmlstr += htmlTemplate(index,this,(page_options.currentPage - 1) * page_options.pageSize + index + 1);
	        });
	        $htmlstr += '<div class="clearfix visible-xs"></div>';
	        $("#decorateBody").html($htmlstr);
	        $(".alt").tooltip({
                placement:"bottom"
            });
            initDecorateEvent();
            //初始化拖动排序事件
            dragUI();
        }
	});
}

function htmlTemplate(index, data, recordIndex){
	//alert(data.width+","+data.height);
	if(mapObj.get("t_"+data.id)==null) {
		mapObj.put("t_"+data.id,data);
		arrs[index] = data;
	}
	var html = '<div class="col-xs-6 col-sm-4 col-md-2 col-lg-2" style="cursor:move;">';
		html += '<div class="item">';
		html += '<div class="pos-rlt">';
		html += '<div class="top"><span class="pull-right m-t-sm m-r-sm badge bg-info"></span></div>';
		html += '<div class="item-overlay r r-2x bg-black">';
		html += '<div class="bottom opacity padder" style="height:30px; padding-top:5px;">';
		html += '<a href="javascript:void(0)" id="'+data.id+'" class="pull-right edit"><span class="glyphicon glyphicon-edit"></span> 编辑</a>';
		html += '<a href="javascript:void(0)" id="'+data.id+'" class="del"><span class="bg-danger glyphicon glyphicon-minus-sign"></span> 删除</a>';
		html += '</div></div>';
		html += '<a href="#"><img src="'+data.url+'" alt="" class="r r-2x img-full"></a></div>';
		html += '<div class="padder-vm">';
		html += '<a href="javascript:void(0)" class="text-ellipsis">名称：'+data.name+'</a>';
		html += '<a href="javascript:void(0)" class="text-ellipsis text-xs text-muted">分组：'+data.groupName+'</a>';
		html += '</div></div></div>';
	return html;
}

function initDecorateEvent(){
	$(".edit").unbind("click").bind("click",function(){
		var $editParam = "paramMap['decorateType']=decorateEdit&paramMap['decorateId']="+$(this).attr("id");
		window.location.href = "decorateAction_initDecoratePage?"+$editParam;
	});
	
	$(".del").unbind("click").bind("click",function(){
		var decorateObj = mapObj.get("t_"+$(this).attr("id"));
		var $param = "decorateList[0].id="+$(this).attr("id")+"&decorateList[0].url="+decorateObj.url;
		alertObj.cancel();
		alertObj.check({
            needConfirm:true,
            confirmTip:"您确认删除当前装饰品吗？",
            confirmParam:$param,
            confirmAction:delAction
        });
	});
	
}

//初始化类别
	function initDecorateGroupList(){
		$.post("json/decorate_findDecorateGroupPage","page.startIndex=0&page.pageSize=10000",function(data){
			if(data!=null&&data.list!=null){
				var html = "";
				$.each(data.list.records,function(){
					html += "<option value="+this.id+">"+this.name+"</option>";
				});
				$("select.type").append(html);
			}
		});
	}
	
	function selectType(){
		if(formObj.validate_all()){
			page_options.currentPage = 1;
			initDecorateList(page_options.currentPage-1,page_options.pageSize,$("#searchForm").serialize());
		}
	}

function delAction($param){
	actionObj.setOptions({ url:"json/decorate_delDecorate" });
	actionObj.submit({param:$param});
}

function bindShowAllDecorate(){
	$(".userListbox .btn-showAll").click(function(){
		page_options.pageSize = 10000;
		var param = "&paramMap['type']="+$("select.type").val()+"&paramMap['searchKeyWord']="+$(".keyWords").val();
    	initDecorateList(page_options.currentPage-1,page_options.pageSize,param);
	});
}

function dragUI(){
	var container = document.getElementById("decorateBody");
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
		url:'json/decorate_sortDecorate',
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
</script>
</html>