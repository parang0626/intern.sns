<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>upload</title>

<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/upload.css">

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
	<section class = "container" id="contents ">
		<div class="hiddenUserName"></div>
		<div class="col-md-12 uploadContainer">
			<div class="col-md-12 uploadTopLine">
				<div class="col-md-2 ">
					<div class="uploadProfileImageBox">
						<img id="uploadProfileImage" />
					</div>
				</div>
				<div class="col-md-2 uploadUserNameBox">
					<p class="uploadUserName"></p>
				</div>
			</div>
			<div class="col-md-12 uploadMiddleLine">
				<textarea id="inputBoardContent">게시물 내용을 올려주세요</textarea>
			</div>
			<div class="col-md-12 ">
			<div id="uploadPrintMessageBox">
					<p id="uploadPrintMessage">이미지,음원,동영상 파일만 가능합니다.</p>
				</div>
				</div>
			<div class="col-md-12 uploadBottomLine">
				
				<div class="col-md-12 fileUploadBox">
					<div class="col-md-2 fileBtnBox">
						<button class="btn btn-default fileAdd">파일 추가</button>
					</div>
					<div class="col-md-2 fileNameBox">
						<p class="fileUploadName"></p>
					</div>
					<div class="col-md-2 fileSizeBox">
						<p class="fileUploadSize"></p>
					</div>
					<div class="col-md-6 filehiddenBox">
						<input type="file" class="uploadFile" />
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-1 col-md-offset-3">
			<buttom class="btn btn-primary" id="btn-upload">등록</buttom>
		</div>
		<div class="col-md-1 ">
			<buttom class="btn btn-primary" id="btn-uploadCancel">취소</buttom>
		</div>

	</section>


	<script src="resources/js/upload.js"></script>

</body>
</html>