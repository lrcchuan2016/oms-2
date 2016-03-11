<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="public/charts/highcharts.js"></script>
<script src="public/charts/modules/exporting.js"></script>
 <link href="public/icheck/css/custom.css?v=1.0.2" rel="stylesheet">
  <link href="public/icheck/skins/all.css?v=1.0.2" rel="stylesheet">
  <script src="public/icheck/icheck.min.js?v=1.0.2"></script>
  <script src="public/icheck/custom.min.js?v=1.0.2"></script>
<style>
progress {
    border-radius: 2px;
    border-left: 1px #ccc solid;
    border-right: 1px #ccc solid;
    border-top: 1px #aaa solid;
    background-color: #eee;
}
  
progress::-webkit-progress-bar {
    background-color: #d7d7d7;
}
  
progress::-webkit-progress-value {
    background-color: #aadd6a;
}
</style>
</head>
<body>
	<div  style="width: 200px;height: 200px;border: 1px solid #ff0000;">
		<progress style="width: 200px;position:relative;margin-top: 100px;background-color: #ff0000;color: #ffffff" value="30" max="100"></progress>
	</div>
	
	
	<div>
	<input type="checkbox">
	</div>
</body>
<script>
var W = window.top;
$(function(){
	$('input').iCheck({ 
	  labelHover : false, 
	  cursor : true, 
	  checkboxClass : 'icheckbox_square-blue', 
	  radioClass : 'iradio_square-blue', 
	  increaseArea : '20%' 
	}); 
});


</script>
</html>