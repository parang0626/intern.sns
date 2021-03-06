<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>search</title>

<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/search.css">
<link rel="stylesheet" href="resources/css/jquery-ui.css" type="text/css" />

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

		<div class="row searchLine">
			<div class="col-md-6 searchinputline">
				<div class="input-group">
					<div class="input-group-btn">
						<button type="button" class="btn btn-default dropdown-toggle" id="dropSearch" data-toggle="dropdown" aria-expanded="false">
							작성자 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
						    <li><a class="serachTypeDrop" href="#" id="searchWriter">작성자</a></li>
							<li><a class="serachTypeDrop" href="#"id="searchDate">작성날짜</a></li>
						</ul>
					</div>
					<input type="text" class="form-control " id="inputKeyword" aria-label="...">
				</div>
			</div>
			<div class="col-md-1 searchbutton">
			 <button type="button" class="btn btn-default " id="btn-search">검색</button>
			</div>
			<div class="col-md-11 col-md-offset-1">
			 <p >날짜검색은 '20160101' 형식으로 연도,월,일을 적어주세요 </p>
			</div>
		</div>
		<div id="serchConent"></div>
	</section>


<div class="modal fade detailBoard" id="detailBoards">
		<div class="modal-dialog">
			<div class="modal-content">
				<div id="hiddenBoardNum"></div>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<div class="col-md-12 detailTopLine">
					<div class="col-md-2 ">
						<div class="detailProfileImageBox">
							<img id="detailProfileImage" />
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
				<div class="col-md-12 replylistPlus">
					<button class="btn btn-default btn-sm" id="plusReplys">댓글 더보기</button>
				</div>
			</div>
		</div>
	</div>
	<script src="resources/js/jquery-ui.min.js"></script>
	<script src="resources/js/search.js"></script>

</body>
</html>