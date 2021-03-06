<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>openit_beginner_sns_lee</title>

<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/signin.css">

<script src="resources/js/jquery-3.1.1.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

<![endif]-->
</head>
<body>


	<div class="container">
	
		<form class="form-signin">
			<div class=col-md-8>
				<img alt="openitCI" class="openitCi" src="/resources/image/openitCI.gif">
			</div>
			<div class=col-md-8>
				<label class="col-md-4 text">ID</label>
				<div class="col-md-8">
					<input type="text" id="inputId" class="form-control" placeholder="Id" autofocus="">
				</div>
				
				<label class="col-md-4 text">Password</label>
				<div class="col-md-8">
					<input type="password" id="inputPassword" class="form-control" placeholder="Password">
				</div>
				<div class="col-md-12">
				<p  id = "loginInfo" >아이디와 비밀번호를 입력해주세요 !@</p>
				</div>
				
			</div>
			<div class=col-md-4>
				<button class="btn btn-md btn-primary btn-block " id="btn-signin" type="button">로그인</button>
				<button class="btn btn-md btn-primary btn-block " id ="btn-signup" type="button">회원가입</button>
			</div>
		</form>
	</div>

	<script src="resources/js/signin.js"></script>



</body>
</html>