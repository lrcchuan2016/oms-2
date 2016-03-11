<%@ include file="/base.jsp"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</head>
<body>
	<div class="container">
		<div class="text-center" style="margin-top: 25%;">
			<div>
				<h4><b><i>错误页面</i></b></h4>
				<div style="margin-top: 20px;">
					<span><i>程序正在调试或者加载错误，请您尝试刷新。</i></span>
				</div>
			</div>
			<div style="margin-top: 20px;">
				<a href="javascript:void(0)"><span class="glyphicon glyphicon-arrow-left"></span>反馈信息</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:history.go(-1)"><span class="glyphicon glyphicon-share-alt"></span>返回上一页</a>&nbsp;&nbsp;&nbsp;
				<span><kbd class="badge" style="background-color: #000000">F5</kbd>刷新</span>
			</div>
		</div>
	</div>
</body>
<script>
</script>
</html>
