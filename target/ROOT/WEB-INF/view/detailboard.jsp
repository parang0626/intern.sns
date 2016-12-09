<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>메인 화면</title>

<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/detailboard.css">

<script src="resources/js/jquery-3.1.1.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

<![endif]-->
</head>
<script type="text/javascript">
	var userNum = '${userInfo.userNum}';
</script>
<body>

	<!--  게시물 상세 모달 -->
	<div class="modal fade detailBoard" id="detailBoards">
		<div class="modal-dialog">
			<div class="modal-content">
			
			
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<div class="col-md-12 detailTopLine">
						<div class="col-md-2 ">
							<div class="detailProfileImageBox">
								<img class="detailProfileImage" />
							</div>
						</div>
						<div class="col-md-4 uploadUserNameBox">
							<p class="detailUserName"></p>
						</div>
						<div class="col-md-4 uploadUserNameBox">
							<p class="detailDate detailRegDate"></p>
							<p class="detailDate detailmodified"></p>
						</div>
					</div>
				</div>
				<div class="modal-body">
					<div class="col-md-12 buttonLine">
						<button class="btn btn-default btn-sm boardbutton" id="btn-detailModify">수정</button>
						<button class="btn btn-default btn-sm boardbutton" id="btn-detailDelete">삭제</button>
					</div>

					<div class="col-md-12 imageLine"></div>

					<div class="col-md-12 boardContentBox"></div>

					<div class="col-md-12 fileDownloadBox">
						<ul class="fileDownList">

						</ul>
					</div>

					<div class="col-md-12 userCommentBox">
						<div class="col-md-12 userComment">

							<p class="userCommnetText"></p>
						</div>
					</div>

					<div class="col-md-12 inputReplyLine">
						<p class="inputReply">댓글 쓰기</p>
						<input type="text inputReply" id="inputReplyText"></input>
						<button class="btn btn-default btn-sm" id="createReply">등록</button>
					</div>
					<div class="col-md-12 replylistLine"></div>
				</div>
				<div class="modal-footer">
					<div class="col-md-12 replylistPlus">
						<button class="btn btn-default btn-sm" id="plusReplys">댓글 더보기</button>
					</div>
				</div>

			</div>
		</div>
	</div>

	<!--  게시물 수정 모달 -->
	<div class="modal fade updateBoard" id="updateBoard">
		<div class="modal-dialog">
			<div class="modal-content">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<div class="col-md-12 detailTopLine">
					<div class="col-md-2 ">
						<div class="detailProfileImageBox">
							<img class="detailProfileImage" />
						</div>
					</div>
					<div class="col-md-2 uploadUserNameBox">
						<p class="detailUserName"></p>
					</div>
					<div class="col-md-4 col-md-offset-2 uploadUserNameBox">
						<p class="detailDate detailRegDate"></p>
						<p class="detailDate detailmodified"></p>
					</div>
				</div>

				<div class="col-md-12" id="boardContentModifyBox">
					<textarea id="boardContentModify"></textarea>
				</div>

				<div class="col-md-12" id="boardContentModifyBox"></div>

				<div class="col-md-12 fileModifyBox">
					<div id="uploadPrintMessageBox">
						<p id="uploadPrintMessage">기존 파일 삭제</p>
					</div>
					<ul class="fileModify">
					</ul>
				</div>

				<div class="col-md-12 uploadBottomLine">
					<div id="uploadPrintMessageBox">
						<p id="uploadPrintMessage">이미지,음원,동영상 파일만 가능합니다.</p>
					</div>

					<div class="col-md-11 col-md-offset-1 fileUploadBox">
						<input type="file" class="uploadFile" />
						<p class="uploadFileSize">0 KB</p>
					</div>
				</div>
				<div class="col-md-12">
					<div class="col-md-2 col-md-offset-4 ">
						<button class="btn btn-default btn-modify">수정</button>
					</div>
					<div class="col-md-2 ">
						<button class="btn btn-default btn-modifyCancel">취소</button>
					</div>
				</div>
			</div>
		</div>
	</div>


	<script src="resources/js/detailboard.js"></script>

</body>
</html>