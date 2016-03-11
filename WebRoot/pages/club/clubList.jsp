  <%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="public/charts/highcharts.js"></script>
<script src="public/charts/modules/exporting.js"></script>
</head>
<body style="display: none;" onload="javascript:$('body').fadeIn(1000)">
<div class="container-fluid">
	<ol class="breadcrumb">
      <li><a href="javascript:void(0)">首页</a></li>
      <li><a href="javascript:void(0)">家庭管理</a></li>
      <li class="active">家庭列表</li>
    </ol>
    <div class="userListbox channel-list" style="margin-right:20px;">
        <div style="margin-top: 15px;">
    	<form class="retrieval" id="searchForm" action="javascript:void(null);">
    		<div class="form-group pull-left" style="width:350px;">
	    		<div class="input-group">
	    			<div class="input-group-addon">关键字</div>
                  	<input class="form-control keyWords" name="param['keyWord']" data-valid="[{rule:'not_empty'}]" type="text" maxlength="100" placeholder="家庭号/家庭呢称/创建人手机号">
	    		</div>
           </div>
           &nbsp;<button type="submit" id="searchBtn" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 搜索</button>
		</form>
		</div>
		
		<table id="clubTable" class="table table-bordered table-hover" cellpadding="0px" cellspacing="0px" width="100%">
			<thead>
   				<tr>
   					<td>NO.</td>
					<td>家庭号</td>
					<td>家庭呢称</td>
					<td>家庭头像</td>
					<td>成员数</td>
					<td>创建人号码</td>
					<td>操作</td>
   				</tr>
   			</thead>
			<tbody class="bg-white"></tbody>
			<tfoot><tr><td colspan="7">&nbsp;</td></tr></tfoot>
		</table>
	</div>
</div>

<div id="family-info" class="modal" tabindex="-1" style="display:none; background-color:rgba(0,0,0,0.4); overflow:hidden;">
    <div class="modal-dialog" style="width:700px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4>家庭信息</h4>
            </div>
            <div class="modal-body" style="padding:0px;">
                <table class="table table-bordered" style="margin:0px;">
                	<tr>
                    	<td style="border-top:none; width:120px;">头像</td>
                    	<td style="border-top:none;"><img src="" width="60" class="imgPortrait decodeImg"></td>
                    </tr>
                	<tr>
                    	<td>家庭昵称</td>
                    	<td class="clubNickname decodeTd"></td>
                    </tr>
                	<tr>
                    	<td>创建者</td>
                    	<td class="creater decodeTd"></td>
                    </tr>
                	<tr>
                    	<td>家庭成员</td>
                    	<td>
                        	<ul class="member-list clearfix" style="max-height:300px; overflow-x:auto;"></ul>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer text-center-xs" style="margin-top:0px;">
                <button type="button" class="btn-close btn btn-default r-lg btn-sm" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove" ></span> 关　闭
                </button>
            </div>
        </div>
    </div>
</div>

<div id="clubMemberVest" class="modal" tabindex="-1" style="display:none; background-color:rgba(0,0,0,0.4); overflow:hidden;">
    <div class="modal-dialog" style="width:700px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">成员分布</h4>
            </div>
            <div class="modal-body" style="padding:20px;max-height: 400px;" >
              	<div id="clunMemberVestPie"></div>
            </div>
            <div class="modal-footer text-center-xs" style="margin-top:0px;">
                <button type="button" class="btn-close btn btn-default r-lg btn-sm" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span> 关　闭
                </button>
            </div>
        </div>
    </div>
</div>    

<script>
var W = window.top;
var actionObj = null,clubTableObj = null,formObj = null;
$(function(){
	formObj = $("#searchForm").inputValid(valid_message_options);
	
	clubTableObj = $("#clubTable").tableList({
        url:"json/club_clubList",
        param:"paramMap['keyWord']="+$(".keyWord").val(),
        pageSize:12,
        paging:true,
        finishCallBack:initClubTable,
        template:htmlTemplate
    });
	
	$("#searchBtn").click(function(){
		if(formObj.validate_all()){
			clubTableObj.setOptions({
	            param:$("#searchForm").serialize(),
	            url:'json/club_clubList'
	        });
		}
	});
	
	//限定图片宽高
	limitSize();
	
	//绑定显示成员分布
	bindMemebrVest();
});

function htmlTemplate(index, data, recordIndex){
	
	var html = "<tr id="+data.id+">";
	html += '<td style="width:50px">'+recordIndex+'</td>';
	html += '<td>'+data.numId+'</td>';
	html += '<td>'+data.nickname+'</td>';
	html += '<td><img class="img" title="'+data.portrait+'"></td>';
	html += '<td>'+data.memberNum+'</td>';
	if(null != data.createrPhone){
		html += '<td>'+data.createrPhone+'</td>';
	}else html += '<td></td>';
	html += '<td><div class="btn-group"><button class="btn btn-info btn-xs btn-search"><span class="glyphicon glyphicon-zoom-in"></span>&nbsp;查看</button><button class="btn btn-primary btn-xs btn-showVest"><span class="glyphicon glyphicon-search"></span>&nbsp;成员分布</button></div></td>';
	html += '</tr>';
	return html;
}

function initClubTable(){

	$(".btn-search").click(function(){
		var id = $(this).parents("tr").attr("id");
		console.info(id);
		$.getFamilyInfo(id);
	});
	
	$(".btn-close").livequery(function(){
		$(this).click(function(){
			$(this).parents(".modal").hide();
		});
	});
}

function limitSize(){
	$(".img").livequery(function(){
		$(this).each(function(){
		var title = $(this).attr("title");
		var suf = "@80w_50h_2o.png";
		var src = null;
		if(title.trim().length != 0){
			if(title.indexOf("@") != -1){
				src = title.split("@")[0]+suf;
			}else src = title+suf;
			$(this).attr("src",src);
		}
	});
	});
}

function bindMemebrVest(){
	$(".btn-showVest").livequery(function(){
		$(this).click(function(){
			var clubId = $(this).parents("tr").attr("id");
			$.ajax({
				type:'get',
				dataType:'json',
				data:{"paramMap['clubId']":clubId},
				url:'json/dataStatistics_QueryClubMemberVest',
				success:function(json){
					if(json.result == '00000000'){
						drawClubMemberVestPie(handleData(json.data));
					}else W.$.alert("获取成员数据错误！");
				},
				error:function(e){
					W.$.alert("获取成员分布数据异常！");
				}
			});
		});
	});
}

//处理(""和 未知)
function handleData(data){
	var num = 0;
	var arrs = new Array();
	for ( var i in data) {
		var element = data[i];
		if(element[0].trim().length == 0){
			num += element[1];
		}else if(-1 != element[0].indexOf('未知')){
			num += element[1];
		}else arrs.push(element);
	}
	arrs.push(['未知',num]);
	
	return arrs;
	
}

//画饼图
function drawClubMemberVestPie(_data){
	$('#clunMemberVestPie').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '家庭成员地区分布'
           
        },
        tooltip: {
            pointFormat: '<b>数量：{point.y}个</b><br> <b>百分比：{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                }
            }
        },
        series: [{
            type: 'pie',
            name: '用户百分比',
            data: _data
        }],
         credits:{enabled:false}
    });
    
    //显示模态框
    $("#clubMemberVest").fadeIn();
}

$.extend({
	getFamilyInfo  : function(_id){
		$.ajax({
			type:"POST",
			dataType:"json",
			data:{
				familyId : _id
			},
			url:'json/user_getFamilyInfo', //API
			success:function(json) {
				try{
					if(json.result == "00000000"){
						var club = json.club;
						var member = json.member;
						$(".imgPortrait").attr("src",decodeURIComponent(club.portrait)).addClass("decodeImg");
						$(".clubNickname").html(club.nickname);
						$(".creater").html(json.creater);
						$(".member-list").html("");
						$.each(member,function(key,value){
							$(".member-list").append("<li style='height:100px;'><img class='decodeImg' src=\""+value.portrait+"\"><span class='name decodeName'>"+decodeURIComponent(value.nickname)+"</span><span class=\"account\">"+value.account+"</span></li>");
						});
						
						$("#family-info").show();
					}else{
						W.$.alert("查看失败！");
					}
				}catch(e){
					
				}
			},
			error: function(data) {
				W.$.alert("查看失败！");
			}
		});
	}
});
</script>
</body>
</html>