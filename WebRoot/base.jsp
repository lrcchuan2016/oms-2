<%@ page trimDirectiveWhitespaces="true" %> 
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 引入JSTL的C标签库 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
	request.getSession().setAttribute("basePath", basePath);
	System.out.println(basePath);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="${basePath}">
<title>美家秀后台管理系统</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link href="public/img/logo2.png" rel="shortcut icon">
<link href="public/css/bootstrap.min.css" rel="stylesheet">
<link href="public/apprise/apprise.css" rel="stylesheet">
<link href="public/css/style.css" rel="stylesheet">
<link href="public/css/style_coo.css" rel="stylesheet">
<script type="text/javascript">
var valid_message_options={not_empty:'输入不能为空',min_length:'字符串最小长度为：:value',max_length:'字符串最大长度为：:value',
exact_length:'字符串长度必须为：:value',min_value:'允许的最小值为：:value',max_value:'允许的最大值为：:value',
equals:'内容不为：:value',regex:'内容无效',email:'输入的E-mail地址不合法',url:'输入的 URL地址不合法',ip:'输入的IP地址不合法',
mac:'输入的MAC地址不合法',year:'请输入合法年份，例如:\"2014\"',date:'日期格式不合法（1970-01-01）',id:'ID 不合法',chinese:'输入的不是中文字符',
version:'版本号不合法',domain:'域名不合法',path:'路径不合法',integer:'不是整数',positive:'不是正整数',non_negative:'不是非负整数',first_letter:'首字母必须为：:value',
mobile:'手机格式不正确',toFixed:'0-1之间,小数位数不大于：:value'};
var page_message_options={first:'首页',prev:'上一页',next:'下一页',last:'尾页',current_page:'当前页是',goto_page:'前往第',page:'页'};
var tip_message={tip:'提示',warning:'警告',select_at_least_one:'至少选择一项',confirm_del:'确认删除?'};
</script>
<script src="public/js/jquery-1.10.2.min.js"></script>
<script src="public/js/bootstrap.min.js"></script>
<script src="public/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="public/js/bootstrap-paginator.js"></script>
<script src="public/js/stupidtable.min.js"></script>
<script src="public/js/dragtable.min.js"></script>
<script src="public/js/tablelist.js"></script>
<script src="public/js/inputvalid.js"></script>
<script src="public/js/app.plugin.js"></script>
<script src="public/js/actions.js"></script>
<script src="public/js/comm.js"></script>
<script src="public/apprise/apprise-1.5.full.js"></script>
<script src="public/js/jquery.livequery.js"></script>
<script src="public/js/Sortable.min.js"></script>
