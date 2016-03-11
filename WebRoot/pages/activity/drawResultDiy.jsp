  <%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="public/book/double-page.css" rel="stylesheet">
<style>
/* CSS Document */
.pic_area,
.pic_area1,.bg {
	position:absolute; top:0px; left:0px; width:800px; height:450px;
}
.bg {}
.bgon1, .pic_area1{
	left:-400px; 
}
.double,.page { overflow:hidden;}

.first_page, .end_page {width:800px; height:450px; position:absolute; overflow:hidden;}
.pic_area { overflow:hidden;}
.pic_area .area { position:absolute; overflow:hidden;}
.pic_area .pic{ position:absolute;}
.end_page { display:none;}

.flipbook-viewport .container{
	left:41px; top:26px;
}

.flipbook-viewport {
	width:auto;
	height:auto;
}
.flipbook-viewport .bookLoading {
	z-index:1;
	position:absolute;
	left:452px; 
	top:252px; 
}
.flipbook-viewport .bookBg {
	position:absolute;
	left:30px;
	-webkit-transition: -webkit-box-shadow 0.5s;
	-moz-transition: -moz-box-shadow 0.5s;
	-o-transition: -webkit-box-shadow 0.5s;
	-ms-transition: -ms-box-shadow 0.5s;

	-webkit-box-shadow:0 0 20px rgba(0,0,0,0.5);
	-moz-box-shadow:0 0 20px rgba(0,0,0,0.5);
	-o-box-shadow:0 0 20px rgba(0,0,0,0.5);
	-ms-box-shadow:0 0 20px rgba(0,0,0,0.5);
	box-shadow:0 0 20px rgba(0,0,0,0.5);
}

.decorate { position:absolute;}


.flipbook-viewport .container{
	top:27px;
}

.flipbook-viewport .left,
.flipbook-viewport .right{
	position:absolute;
	top:220px;
	cursor:pointer;
}

.flipbook-viewport .left{
	left:-6px;
	display:none;
}
.flipbook-viewport .right{
	left:1018px;
}
.flipbook-viewport { overflow:visible; margin-left:20px;}
.previewbody .pop_close{
	 background-color:rgba(255,255,255,.6);
	 width:20px; height:20px;
	 position:absolute;
	 right:22px;top:10px;
	 z-index:8888;
}
.gradient {
	position:absolute; top:0px; left:0px;
	background-image:url(public/book/images/gradient-page-left.png);
	background-position:right;
	background-repeat:repeat-y;
}
.decorate { position:absolute;}

img.bookLoading {
	left:432px !important; 
}
.flipbook-viewport .pageNum { width:80px; height:36px; text-align: center; background:rgba(0,0,0,0.5); color:#fff; z-index:9999; position:absolute; border-radius:10px; line-height:36px; font-size:18px;
	top:520px; left:495px;
}
</style>
<script type="text/javascript" src="public/book/extras/modernizr.2.5.3.min.js"></script>

<head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">

    <ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="activityPages_activityList">活动列表</a></li>
      <li><a href="activityPages_activityWinRecord">转盘活动</a></li>
      <li class="active">精美相簿</li>
    </ol>
    
    <div class="channel-show row">
    	<!---------基本信息------>
      <div class="clearfix">
      	  <button class="btn btn-lg btn-info btn-concat" type="button">图片生成</button>
      	  <button class="btn btn-lg getPagePicture" type="button">相册图片</button>
      </div>
      <div class="container-full">
        <div class="flipbook-viewport clearfix">
            <img class="bookBg" src="public/book/images/preview_bg.png" >
            <div class="container shadow1">
            </div>
            <img class="bookLoading" src="public/book/images/loading.gif?v=1" >
            
            <img class="left" src="public/book/images/lt1.png" >
            <img class="right" src="public/book/images/rt1.png" >
            <div class="pageNum"></div>
        </div>
     </div>
         
        
    </div>
</div>

<div style="height:100px;">
	
</div>



<script>
var W = window.top;

var dpic = "public/book/images/tm_bg.png";
var xmldata = null;
var sW = 960;
var sH = 540;

var pageLen = 1;
var prizeId = GetQueryString('prizeId');
$(function() {
	$.getTheme();
	
	
	//$.getAblumInfo();
	//绑定相册合成事件
	bindConcat(".btn-concat");
	
	$(".getPagePicture").click(function(){
		window.open("activityPages_activityPageDiy?id="+prizeId);
	});
});

function bindConcat(selector){
	
	$(selector).livequery(function(){
		$(this).click(function(){
			$.ajax({
				type:'post',
				dataType:'json',
				data:{"paramMap['prizeId']":prizeId},
				url:'json/activityTurn_concatThemeAlbumPrize',
				success:function(json){
					if(json.result == '00000000'){
						//创建a元素,设定下载
						var a = document.createElement('a');
						a.href = json.url;
						a.download = json.url;
						a.id='download';
						document.body.appendChild(a);
						var alink = document.getElementById('download');
						alink.click();
						alink.parentNode.removeChild(a);
						
					}else{
						W.$.alert("下载失败!");
						
					}
					$(selector).html("图片生成");
					$(selector).removeAttr("disabled");
				},
				error:function(e){
					W.$.alert("相册合成异常！");
				},
				//返回响应前事件
				beforeSend:function(){
					$(selector).html("正在处理,请稍等");
					$(selector).attr("disabled","disabled");
				},
				error:function(){
					W.$.alert("下载失败!");
					$(selector).html("图片生成");
					$(selector).removeAttr("disabled");
				}
				
			
				
			});
		});
	});
}


function pageInit(){
	
	
	var xml = xmldata;
	var r_w = xmldata.resolution.w;
	var r_h = xmldata.resolution.h;
	var wk = sW/r_w;
	var hk = sH/r_h;
	
	var bg_img_hz = "@"+sW+"w_"+sH+"h_90Q.png";
	
	var pages  = xml.pages.page;
	pageLen = pages.length;
	
	var firstPage = pages[0];
	var lastPage = pages[pageLen-1];
	
	//alert(pages[0].fields.field.pic.resource.url)
	var html = "";
	
	/*==============第一页=[重复]=为了兼容插件=================*/
	var firstHTML = "";
	if(!(pages[0].fields.field instanceof Array)){
		var tempArr = pages[0].fields.field;
		pages[0].fields.field = new Array();
		pages[0].fields.field[0] = tempArr;
	}
	if(pages[0].decorates!="" && !(pages[0].decorates.decorate instanceof Array)){
		var tempArr = pages[0].decorates.decorate;
		pages[0].decorates.decorate = new Array();
		pages[0].decorates.decorate[0] = tempArr;
	}
	//装饰品
	var decorHTML = "";
	if(pages[0].decorates!=""){
		for(var d=0; d<pages[0].decorates.decorate.length; d++){
			var PIC = decorAttr(pages[0].decorates.decorate[d]);
			decorHTML += '<img class="decorate" src="'+ PIC.url +'" style="left:'+PIC.x+'px; top:'+PIC.y+'px; width:'+PIC.w+'px; height:'+PIC.h+'px;">';
			
		}
	}
	//多个 图片域
	var pageBg = pages[0].background.url + bg_img_hz;
	firstHTML = '<div class="first_page" style="width:'+sW+'px; height:'+sH+'px;">'
		   +'<div class="pic_area" style="width:'+sW+'px; height:'+sH+'px;">';
	for(var i=0; i<pages[0].fields.field.length; i++){
		var PIC = picAttr(pages[0].fields.field[i]);
		firstHTML += '<div class="area" style="left:'+PIC.areaX+'px; top:'+PIC.areaY+'px; width:'+PIC.areaW+'px; height:'+PIC.areaH+'px; ">'
		+'<img class="pic" src="'+ PIC.url +'" style="left:'+PIC.x+'px; top:'+PIC.y+'px; width:'+PIC.w+'px; height:'+PIC.h+'px;"></div>';
	}
	firstHTML += '</div>'
			+'<img class="bg" style="width:'+sW+'px; height:'+sH+'px;" src="'+ pageBg +'">'
			+ decorHTML
			+'<div class="gradient" style="width:' + (sW/2)
			+ 'px; height:'+sH+'px;"></div>'
		+'</div>';
		
	html += firstHTML;
	
	/*==============最后一页=[重复]=为了兼容插件=================*/
	//最后一页
	var endHTML = "";
	if(!(pages[pageLen-1].fields.field instanceof Array)){
		var tempArr = pages[pageLen-1].fields.field;
		pages[pageLen-1].fields.field = new Array();
		pages[pageLen-1].fields.field[0] = tempArr;
	}
	if(pages[pageLen-1].decorates!="" && !(pages[pageLen-1].decorates.decorate instanceof Array)){
		var tempArr = pages[pageLen-1].decorates.decorate;
		pages[pageLen-1].decorates.decorate = new Array();
		pages[pageLen-1].decorates.decorate[0] = tempArr;
	}
	//装饰品
	var decorHTML = "";
	if(pages[pageLen-1].decorates!=""){
		for(var d=0; d<pages[pageLen-1].decorates.decorate.length; d++){
			var PIC = decorAttr(pages[pageLen-1].decorates.decorate[d]);
			decorHTML += '<img class="decorate" src="'+ PIC.url +'" style="left:'+PIC.x+'px; top:'+PIC.y+'px; width:'+PIC.w+'px; height:'+PIC.h+'px;">';
			
		}
	}
	//多个 图片域
	var pageBg = pages[pageLen-1].background.url + bg_img_hz;
	endHTML = '<div class="end_page" style="width:'+sW+'px; height:'+sH+'px;">'
		   +'<div class="pic_area" style="width:'+sW+'px; height:'+sH+'px;">';
	
	for(var i=0; i<pages[pageLen-1].fields.field.length; i++){
		var PIC = picAttr(pages[pageLen-1].fields.field[i]);
		endHTML += '<div class="area" style="left:'+PIC.areaX+'px; top:'+PIC.areaY+'px; width:'+PIC.areaW+'px; height:'+PIC.areaH+'px; ">'
		+'<img class="pic" src="'+ PIC.url +'" style="left:'+PIC.x+'px; top:'+PIC.y+'px; width:'+PIC.w+'px; height:'+PIC.h+'px;"></div>';
	}
	endHTML += '</div>'
			+'<img class="bg" style="width:'+sW+'px; height:'+sH+'px;" src="'+ pageBg +'">'
			+ decorHTML
			+'<div class="gradient" style="width:' + (sW/2)
			+ 'px; height:'+sH+'px;"></div>'
		+'</div>';

	html += endHTML;
	
	/*==============插件各个页面=================*/
	filpHTML = '<div class="flipbook" style="width:'+sW+'px; height:'+sH+'px;">';
	for(var j=0; j<pages.length; j++){
		//if(j!=1)continue;
		var className = (j==0 || j==pages.length-1) ? "page" : "double";
		var area_className = j == 0 ? "pic_area1" : "";
		var bg_className = j == 0 ? "bgon1" : "";
		var first_left = j == 0 ? "left:-"+(sW/2)+"px;" : "";
		
		if(!(pages[j].fields.field instanceof Array)){
			var tempArr = pages[j].fields.field;
			pages[j].fields.field = new Array();
			pages[j].fields.field[0] = tempArr;
		}
		if(pages[j].decorates!="" && !(pages[j].decorates.decorate instanceof Array)){
			var tempArr = pages[j].decorates.decorate;
			pages[j].decorates.decorate = new Array();
			pages[j].decorates.decorate[0] = tempArr;
		}
		//装饰品
		var decorHTML = "";
		if(pages[j].decorates!=""){
			for(var d=0; d<pages[j].decorates.decorate.length; d++){
				var PIC = decorAttr(pages[j].decorates.decorate[d]);
				decorHTML += '<img class="decorate" src="'+ PIC.url +'" style="left:'+PIC.x+'px; top:'+PIC.y+'px; width:'+PIC.w+'px; height:'+PIC.h+'px;">';
				
			}
		}
		//多个 图片域
		var pageBg = pages[j].background.url + bg_img_hz;
		filpHTML += '<div class="'+className+'">'
			   +'<div class="pic_area '+area_className+'" style="width:'+sW+'px; height:'+sH+'px;'+first_left+'">';
		
		for(var i=0; i<pages[j].fields.field.length; i++){
			var PIC = picAttr(pages[j].fields.field[i]);
			filpHTML += '<div class="area" style="left:'+PIC.areaX+'px; top:'+PIC.areaY+'px; width:'+PIC.areaW+'px; height:'+PIC.areaH+'px; ">'
			+'<img class="pic" src="'+ PIC.url +'" style="left:'+PIC.x+'px; top:'+PIC.y+'px; width:'+PIC.w+'px; height:'+PIC.h+'px;"></div>';
		}
		filpHTML += '</div>'
				+'<img class="bg '+bg_className+'" style="width:'+sW+'px; height:'+sH+'px; '+first_left+'" src="'+ pageBg +'">'
				+ decorHTML
				+'<div class="gradient" style="width:' + (sW/2)
				+ 'px; height:'+sH+'px;"></div>'
			+'</div>';
	}

	filpHTML += "</div>";
	html += filpHTML;
	
	//$("#s textarea").text(html);
	//return;
	/*==============创建BOOK的HTML=================*/
	$(".flipbook-viewport .container").html(html);
	
	
	var loaded = 0;
	var picLen = $(".pic_area img").length;
	
	$(".pic_area img").each(function(index, element) {
        var img = new Image();
		var src = $(this).attr("src");
		img.src = src;
		var self = this;
		img.onload = function(){
			loaded++;
			var imgW = img.width;
			var imgH = img.height;
			if(index == 0)console.log(imgW + "," + imgH);
			$(this).css({width:imgW + "px", height:imgH + "px"});
			if(loaded == picLen-1){
				setTimeout(function(){
					$(".container").show();
					$(".bookLoading").hide();
					$(".left").click(function(){
						$('.flipbook').turn("previous");
					});
					
					$(".right").click(function(){
						$('.flipbook').turn("next");
					});
				},1000);
			}
		}
		img.onerror = function(){
			loaded++;
		}
    });
	
	//图片显示属性计算器
	function picAttr(_obj){
		//document.write(JSON.stringify(_obj));return;
		var picW = parseInt(_obj.pic.w * wk);
		var picH = parseInt(_obj.pic.h * hk);
		var areaX = _obj.x * wk;
		var areaY = _obj.y * hk;
		
		var areaW = _obj.w * wk;
		var areaH = _obj.h * hk;
		var areaR = _obj.r;
		
		var picX = _obj.pic.x * wk - areaX;
		var picY = _obj.pic.y * hk - areaY;
		var picR = parseInt(_obj.pic.r);
		if(picR < 0){
			picR = 360 + picR;
		}
		var picUrl = dpic;
		//alert(JSON.stringify(_obj.pic.resource));
		if(typeof(_obj.pic.resource) == "undefined" || typeof(_obj.pic.resource.url) == "undefined" || _obj.pic.resource.url == ""){
			picUrl = dpic;
		}else {
			picUrl = _obj.pic.resource.url == "" ? dpic : _obj.pic.resource.url;
			//alert(picW + "," + picH)
			if(picW > picH){
				picUrl = _obj.pic.resource.url + "@" + picW + "w_1o_2e" + "|" + picR + "r" ;
			}else{
				picUrl = _obj.pic.resource.url + "@" + picH + "h_1o_2e" + "|" + picR + "r";
			}
		}
		return {
			"w" : picW,"h" : picH,"x" : picX,"y" : picY,"r" : picR,"url": picUrl,
			"areaX" : areaX,
			"areaY" : areaY,
			"areaW" : areaW,
			"areaH" : areaH,
			"areaR" : areaR
		}
	}
	
	//饰物 显示属性计算器
	function decorAttr(_obj){
		var picW = parseInt(_obj.w * wk);
		var picH = parseInt(_obj.h * hk);
		var picX = _obj.x * wk;
		var picY = _obj.y * hk;
		var picR = parseInt(_obj.r);
		if(picR < 0){
			picR = 360 + picR;
		}
		var picUrl = "";
		if(typeof(_obj.resource) == "undefined" || typeof(_obj.resource.url) == "undefined"){
			picUrl = "";
		}else {
			picUrl = _obj.resource.url + "@" + picR + "r.png";
			//picUrl = _obj.resource.url + "@" + picH + "h_1o_2e.png";
		}
		return {"w" : picW,"h" : picH,"x" : picX,"y" : picY,"r" : picR,"url": picUrl}
	}
	
	//return;
	for(var i=0; i<1000; i++){
		if(i%2 != 0){
			$(".p"+i+" .double .bg").livequery(function(){
				$(this).css({"left": "-" + sW/2 + "px"});
			});
			$(".p"+i+" .double .pic_area").livequery(function(){
				$(this).css({"left": "-" + sW/2 + "px"});
			});
			$(".p"+i+" .decorate:not(.ed), .p"+i+" .decorate:not(.ed)").livequery(function(){
				var d_left = parseInt($(this).css("left")) - sW/2;
				$(this).css({"left": d_left + "px"}).addClass("ed");
			});
			$(".p"+i+" .gradient").livequery(function(){
				$(this).css({
					"left": "-" + (sW/2 - 35) + "px",
					"background-image" : "url(public/book/images/gradient-page-right.png)"
				});
			});
		}
	}
	
	yepnope({
		test : Modernizr.csstransforms,
		yep: ['public/book/lib/turn.min.js'],
		nope: ['public/book/lib/turn.html4.min.js'],
		both: ['public/book/lib/scissor.min.js'],
		complete: function(){
			loadApp();

		}
	});

	
}

function loadApp() {

	var flipbook = $('.flipbook');
 	// Check if the CSS was already loaded
	if (flipbook.width()==0 || flipbook.height()==0) {
		
		setTimeout(loadApp, 10);
		return;
	}
	$('.flipbook .double').scissor();
	// Create the flipbook
	$('.flipbook').turn({
			// Elevation
			//page: 1,
			elevation: 100,
			gradients: true,
			autoCenter: true,
			width:sW,
			height:sH,
			display:"double",
			autoCenter: false,
			duration:1500,
			when : {
				turn: function(e,page,pageObj){
					
					if(page == 1 || page == 3){
						$(".first_page").show();
						$(".end_page").hide();
					}else if(page > 3 ){
						
						$(".end_page").show();
						$(".first_page").hide();
					}
					var pageN = parseInt(page/2 + 1);
					if(pageN == pageLen){
						$(".flipbook-viewport .right").hide();
					}else if(pageN == 1){
						$(".flipbook-viewport .left").hide();
					}else {
						$(".flipbook-viewport .right").show();
						$(".flipbook-viewport .left").show();
					}
				},
				turning: function(e,page,pageObj){
					$(".flipbook-viewport .pageNum").fadeOut(500);
				},
				turned:function(e,page,pageObj){
					var pageN = parseInt(page/2 + 1);
					$(".flipbook-viewport .pageNum").text( pageN + "/" + pageLen).fadeIn(500);
				}
			}
	});
}



$.extend({
	
	getTheme : function(){
		
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "http://www.meijx.cn/index.php/web/xmlToJson",
			data: { 
				"prizeId" : GetQueryString('prizeId')
			},
			success: function (json) {
				
				xmldata = json;
				
				pageInit();
			},
			error: function(){
				
			}
		});
	}

});






function GetQueryString(name)   {   
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");   
	var r = window.location.search.substring(1).match(reg);
	if (r != null) {
		return decodeURI(r[2]);
	}else {
		return null; 
	}
}






</script>
</body>
</html>
