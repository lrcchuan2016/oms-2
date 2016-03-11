$(document).ready(function(){
	$("span[name='spStart']").click(judge);
});

/**
 * 根据点击的文本判断[启用]/[禁用]来局部刷新  
 * 【状态】
 */
function judge(){
	var html = $(this);
	
	var tStatus = html.parent().parent("td").parent("tr").children("td[class='tStatus']");
	var uid = html.parent().parent("td").parent("tr").children("td")[0].innerHTML;
	
		if(html.html() == "[启用]"){
			if(confirm("请确认是否要启用此用户？")){
				alert("用户被启用！");
				html.html("[禁用]");
				tStatus.html("正常");
				$.post("userAction_startOrForbid",{"uid":uid,"enable":1});
			}
		}else{
			if(confirm("请确认是否要禁用此用户？")){
				alert("用户被禁用！");
				html.html("[启用]");
				tStatus.html("<font color='#ff0000'>禁用</font>");
				$.post("userAction_startOrForbid",{"uid":uid,"enable":0});
			}
		}
}

//function checkOk(){
//	if(confirm("请确认是否删除此用户？删除后，无法恢复")){
//		$(this).attr("href","userAction_delUserBean?uid="+$(this).attr("name"));
//	}
//}

