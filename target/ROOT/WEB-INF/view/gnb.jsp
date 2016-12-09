<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>메인 화면</title>

<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/gnb.css">

<script src="resources/js/jquery-3.1.1.min.js"></script></script>
<script src="resources/js/bootstrap.min.js"></script>
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

<![endif]-->
</head>
<body>
	<div class="container-fluid">
		<div class="col-md-12" id="gnbArea">
			<div class="col-md-2">
				<img alt="openitCI" class="openitCi" src="/resources/image/openitCI.gif">
			</div>
			<div class="col-md-1 col-md-offset-6" id="gnb-profile-box">
				<div class="crop">
					<img id="gnb_profile_img" src="" />
				</div>
			</div>
			<div class="col-md-2">
				<h5 id="userName"></h5>
			</div>
			<div class="col-md-1">
				<button class="btn btn-default" id="btn-logout" type="button">로그아웃</button>
			</div>
		</div>
	</div>

	<script src="resources/js/gnb.js"></script>

</body>
</html>