  
<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="public/js/bootstrap-datetimepicker.min.js"></script>
</head>
<body>
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="adAction_adList">广告管理</a></li>
      <li class="active">新建广告位</li>
    </ol>
    
    <div class="userListbox ad-edit-box row">
    	
        <ul class="nav nav-tabs">
          <li role="presentation"><a href="adAction_adList">广告管理</a></li>
          <li role="presentation"><a href="adAction_locationList">广告位管理</a></li>
          <li role="presentation" class="active"><a href="pages/ad/ad_location_add.jsp">新建广告位</a></li>
        </ul>
        	
            <form id="adLocationForm" action="javascript:void(null);" method="post">
              
              <div class="input-area">
              	  <div class="form-group" style="width:260px;">
                    <label for="ad_name">名称</label>
                    <input type="text" id="ad_name" name="adLocationBean.ad_name" class="form-control" data-valid="[{rule:'not_empty'}]" placeholder="输入广告位名称"  onfocus="javascript:$(this).val('')" maxlength="10">
                    <p id="sname"></p>
                  </div>
              	  <div class="form-group" style="width:100px;">
                    <label for="terminal">终端设备 * </label>
                    <select id="terminal" class="form-control" name="adLocationBean.terminal_type">
                      <option value="0">全局</option>
                      <option value="1">手机</option>
                      <option value="2">电视</option>
                      <option value="3">网站</option>
                    </select>
                  </div>
                  <div class="form-group" style="width:100px;">
                    <label for="type">媒资类型 * </label>
                    <select id="type" class="form-control" name="adLocationBean.type">
                      <option value="0">图片</option>
                      <option value="1">视频</option>
                      <option value="2">文本</option>
                      <option value="3">音频</option>
                    </select>
                  </div>
                  <div class="form-group d1" style="width:100px;">
                    <label for="em_widht">宽</label>
                    <input type="text" id="em_widht" name="adLocationBean.width" data-valid="[{rule:'not_empty'},{rule:'non_negative',message:'请输入大于0的整数'}]" class="form-control" placeholder="px" maxlength="10">
                  </div>
                  <div class="form-group d2" style="width:100px;">
                    <label for="em_height">高</label>
                    <input type="text" id="em_height" name="adLocationBean.height" data-valid="[{rule:'not_empty'},{rule:'non_negative',message:'请输入大于0的整数'}]" class="form-control" placeholder="px" maxlength="10">
                  </div>
                  <div class="form-group d3 hide" style="width:100px;">
                    <label for="code_rate">码率</label>
                    <input type="text" id="code_rate" name="adLocationBean.bitrate" class="form-control" placeholder="Hz" maxlength="10">
                  </div>
                  
                  <button type="submit" class="btn btn-primary btn-save">提交</button>
    				<a href="javascript:history.back()" class="btn btn-default"> 返 回 </a>
              </div>
            </form>
        
    
    </div>
<script>

var W = window.top;
var formObj;
//var adType = '${adLocationBean.terminal_type}';
$(function(){
	
	formObj = $("#adLocationForm").inputValid(valid_message_options);
	
	// $(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii'});
	
	//显示隐藏以及加上判断
	$("#type").livequery(function(){
		$(this).change(function(){
			var type = $(this).val(); 
			if(type == '0'){
				$(".d1").removeClass("hide").addClass("show").children("input").attr("data-valid","[{rule:'not_empty'},{rule:'non_negative',message:'请输入大于0的整数'}]");
				$(".d2").removeClass("hide").addClass("show").children("input").attr("data-valid","[{rule:'not_empty'},{rule:'non_negative',message:'请输入大于0的整数'}]");
				$(".d3").removeClass("show").addClass("hide").children("input").removeAttr("data-valid");;
			}else if(type == '1'){
				$(".d1").removeClass("hide").addClass("show").children("input").attr("data-valid","[{rule:'not_empty'},{rule:'non_negative',message:'请输入大于0的整数'}]");
				$(".d2").removeClass("hide").addClass("show").children("input").attr("data-valid","[{rule:'not_empty'},{rule:'non_negative',message:'请输入大于0的整数'}]");
				$(".d3").removeClass("hide").addClass("show").children("input").attr("data-valid","[{rule:'not_empty'},{rule:'non_negative',message:'请输入大于0的整数'}]");
			}else if(type == '2'){
				$(".d1").removeClass("show").addClass("hide").children("input").removeAttr("data-valid");
				$(".d2").removeClass("show").addClass("hide").children("input").removeAttr("data-valid");
				$(".d3").removeClass("show").addClass("hide").children("input").removeAttr("data-valid");
			}else{
				$(".d1").removeClass("show").addClass("hide").children("input").removeAttr("data-valid");
				$(".d2").removeClass("show").addClass("hide").children("input").removeAttr("data-valid");
				$(".d3").removeClass("hide").addClass("show").children("input").attr("data-valid","[{rule:'not_empty'},{rule:'non_negative',message:'请输入大于0的整数'}]");
			}
		});
	});
	
	//提交请求
	$(".btn-save").click(function(){
		var $param = $("#adLocationForm").serialize();
		if(formObj.validate_all()){
			$.ajax({
				type : 'POST',
				dataType : 'json',
				data : $param,
				url : 'json/ad_createLocation',
				success : function(json){
					if(json.result == '00000000'){
						W.$.alert("新增广告位成功");
						$("input").val("");
						window.location.href = "adAction_locationList";
					}else{
						W.$.alert("新增广告位失败！    "+json.tip);
					}
				},
				error : function(e){
					W.$.alert("新增广告位失败！");
				}
			});
		}
	});
	
});

</script>
</body>
</html>
