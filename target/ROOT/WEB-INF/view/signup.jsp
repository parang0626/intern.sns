<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회원 가입</title>

<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/signup.css">

<script src="resources/js/jquery-3.1.1.min.js"></script></script>
<script src="resources/js/bootstrap.min.js"></script>
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

<![endif]-->
</head>
<body>


	<div class="container">

		<div class="col-md-6 col-md-offset-3">
			<h3>회원 가입</h3>
			<form class="form-signup">
				<div class="row">
					<div class="col-md-12">
						<div class="row">
							<div class="col-md-5 profile">
								<div class="crop">
									<img id="profile_img" src="/resources/image/default.jpg" alt="your image" />
								</div>
								<div class="profileUpload">
								    <buttom class="btn btn-default " id="fakeFileButton">파일 선택</buttom>
									
									<button class="btn btn-default" id="profileDefaultButtom" >파일 삭제</button>
									<input id="profileUploadButtom" type="file" />
								</div>
								
							</div>
							<div class="col-md-7">
								<div class="col-md-12 inputbox">
									<p>
										ID <sub> 6자 이상,영어와 숫자로만 입력해주세요</sub>
									</p>
									<div class="col-md-8 nopadding ">
										<input class="form-control inputTextBox" type="text" id="inputId"></input>
									</div>
									<div class="col-md-4">
										<button class="btn btn-default" id="btn-checkId" type="button">중복확인</button>
									</div>


								</div>
								<div class="col-md-12 inputbox">
									<p>
										닉네임 <sub> 2자 이상 입력해주세요</sub>
									</p>
									<div class="col-md-8 nopadding ">
										<input class="form-control inputTextBox" type="text" id="inputNick"></input>
									</div>
									<div class="col-md-4">
										<button class="btn btn-default" id="btn-checkNick" type="button">중복확인</button>
									</div>
								</div>
								<div class="col-md-12 inputbox">
									<p>
										비밀번호 <sub> 6자 이상 입력해주세요</sub>
									</p>
									<input class="form-control inputTextBox" type="password" id="inputPass"></input>
								</div>
								<div class="col-md-12 inputbox">
									<p>비밀번호 확인</p>
									<input class="form-control inputTextBox" type="password" id="inputCheckPass"></input>
								</div>
							</div>

							<div class="col-md-12">
								<p id="printMessage">회원 가입 정보를 입력해주세요</p>
							</div>
						</div>


					</div>

				</div>
			</form>
			<div class="col-md-12 buttonBox">
				<button class="btn btn-md btn-primary" id="btn-signup" type="button">가입</button>
				<button class="btn btn-md btn-primary" id="btn-cancel" type="button">취소</button>
			</div>
		</div>

	</div>

	<script src="resources/js/signup.js"></script>

</body>
</html>