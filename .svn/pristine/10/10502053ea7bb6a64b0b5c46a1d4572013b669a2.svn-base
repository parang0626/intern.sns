<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>메인 화면</title>

<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/update.css">

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
	<section id="contents">
		<div class="col-md-12 checkPassLine">
			<div class="col-md-5 checkPassContainer">
				<div class="col-md-12 inputPassBox">
					<p id="passwordText">비밀번호 :</p>
					<input type="password" id="inputPassword" />
				</div>
				<div class="col-md-12 checkPassMessage">
					<p id="CheckPrintMessage">비밀번호를 한번 더 입력해 주세요</p>
				</div>
			</div>
		</div>
		<div class="col-md-2 col-md-offset-1 checkPassLine">
			<button class="btn btn-primary" id="btn-checkPass">확인</button>
		</div>
		<div class="col-md-1 buttonline checkPassLine">
			<button class="btn btn-primary btn-cancel">취소</button>
		</div>

		<div class="col-md-12 updateContainer">
			<div class="col-md-12">
				<div class="col-md-7 updateInputBox">
					<form class="form-updatauser">
						<div class="col-md-12">
							<div class="col-md-5 profile">
								<div id="updateProfileImageBox">
									<img id="updateProfileImage" />
								</div>
								<div class="updateProfileUpload">
									<button class="btn btn-default "type="button" id="fakeFileButton">파일 선택</button>
									<button class="btn btn-default" type="button"id="profileDefaultButtom">파일 삭제</button>
									<input id="profileUploadButton" type="file" />
								</div>
							</div>
							<div class="col-md-7">
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
										새로운 비밀번호 <sub> 6자 이상 입력해주세요</sub>
									</p>
									<input class="form-control inputTextBox" type="password" id="inputPass"></input>
								</div>
								<div class="col-md-12 inputbox">
									<p>비밀번호 확인</p>
									<input class="form-control inputTextBox" type="password" id="inputCheckPass"></input>
								</div>
							</div>
							<div class="col-md-12" id="updateComment">
							 <p id="userCommnet">유저 코멘트</p>
							  <textarea id="inputUserComment" ></textarea>
							</div>
							<div class="col-md-12 printMessage">
								<p id="printMessage">수정할 정보만 입력해주세요</p>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="col-md-12">
				<div class="col-md-1 col-md-offset-2">
					<button class="btn btn-primary" id="btn-update">수정</button>
				</div>
				<div class="col-md-1">
					<button class="btn btn-primary" id="btn-remove">탈퇴</button>
				</div>
				<div class="col-md-1">
					<button class="btn btn-primary" id="btn-cancel">취소</button>
				</div>
			</div>
		</div>
	</section>


	<script src="resources/js/update.js"></script>

</body>
</html>