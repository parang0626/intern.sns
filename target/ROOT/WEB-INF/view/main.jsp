<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>메인 화면</title>

<link rel="stylesheet" href="resources/css/main.css">

<script src="resources/js/jquery-3.1.1.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

<![endif]-->
</head>
<%@include file="/WEB-INF/view/gnb.jsp"%>
<script type="text/javascript">
	var userNum = '${userInfo.userNum}';
</script>
<body>

	<aside id="lnbMenu">
		<%@include file="/WEB-INF/view/lnb.jsp"%>
	</aside>
	<section id="contents"></section>
	
	<%@include file="/WEB-INF/view/detailboard.jsp"%>

	<script src="resources/js/main.js"></script>

</body>
</html>