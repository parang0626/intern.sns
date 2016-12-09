var loadContentSw = false;

// 첫 실행시 데이터 초기화
$(function() {
	console.log("main ready, userNum = "+userNum);
	getBoardAjax('/boards');
});


// 상세보기 버튼
$(document).on('click', '.btn-detailboard', function(){
	var boardNum = $(this).data('boardnum');
	$('.detailBoard').data('boardNum',boardNum);
	$('.detailBoard').data('board',$(this).parents('.boardContent'));
	$('.detailBoard').modal();
});


// 무한 스크롤링으로 추가데이터 받아오기
$(document).on('scroll',document,function(){
	if(loadContentSw){
		var maxHeight = $(document).height();
		var currentScroll = $(window).scrollTop() + $(window).height();
		var url ='/boards?pageNum=5&lastBoardNum='+$('.boardContent').last().data('boardNum');
		if (maxHeight <= currentScroll+10 ) {
			getBoardAjax(url);
	     }	
	}
});


// 게시물 받아오는 ajax
function getBoardAjax(url) {
	loadContentSw = false;
	$.ajax({
		url:url,
		type:'get',
		success : function(data){
			var len = data.data.length;
			
			for(var i=0 ; i<len ;i++) {
				var board = data.data[i];
				var el='';
				
				
				el += '<div class = "col-md-12 boardContent">';
				el += ' <div class = "col-md-12 boardContentTopLine">'		;
				el += '  <div class="col-md-2 contentProfileBox" >';
				el += '   <img class="contentProfileImage" src="/user/'+board.userNum+'/image"/>';
				el += '  </div>';
				el += '  <div class = "col-md-2 contentUserNameBox">';
				el += '   <h4 class="contentUserName">'+board.userNick+'</h4>';
				el += '  </div>';
				
				var modify = '';
				if(board.boardRegDate != board.boardUdtDate) {
					modify = '수정됨';
				}
				el += '  <div class = "col-md-2 col-md-offset-3 contentModifyBox">';
				el += '   <p class="contentModify">'+modify+'</p>';
				el += '  </div>';
				
				var year = board.boardRegDate.substr(0,4);
				var month = board.boardRegDate.substr(4,2);
				var day = board.boardRegDate.substr(6,2);
				var hour = board.boardRegDate.substr(8,2);
				var min = board.boardRegDate.substr(10,2);
				
				el += '  <div class = "col-md-3 contentRegDate">';
				el += '   <p class="contentModify">'+year+' 년 '+month+' 월 '+day+' 일 '+hour+':'+min+' </p>';
				el += '  </div>';
				el += ' </div>'; // boardContentTopLine
				el += ' <div class = "col-md-12 boardContentMiddleLine">';
				
				if(board.firstImageUrl != null) {
				el += '  <div class = "col-md-4  contentFirstImageBox">';
				el += '<img class="contentFirstImage" src="'+board.firstImageUrl+'" />';
				el += '  </div>';
				}
				el += '  <div class = "col-md-8 contentText">';
				
				if(board.boardContent.length > 100){
					var contentText = board.boardContent.substr(0,100)+ '...';
				} else {
					var contentText = board.boardContent;
				}
				
				el += '   <p class="contentText">'+contentText +'</p>';
				el += '  </div>'; 
				el += ' </div>'; // boardContentMiddleLine
				el += ' <div class = "col-md-12 boardContentBottomLine">';
				el += '   <div class = "col-md-2 col-md-offset-6">';
				
				var fileCount = board.boardImgCnt + board.boardAudCnt +board.boardVidCnt;
				if(fileCount != 0) {
					el += '     <p class="fileCount">파일 수 :'+fileCount+'</p>';	
				}
				
				el += '    </div>';
				el += '   <div class = "col-md-2 ">';
				el += '     <p class="replyCount">댓글 수 :'+board.boardReplyCnt+'</p>';
				el += '    </div>';
				el += '   <div class = "col-md-2 ">';
				el += '     <button class="btn btn-default btn-detailboard" data-boardnum ="'+board.boardNum+'">더보기 </button>';
				el += '    </div>';
				el += ' </div>'; // boardContentBottomLine
				el += '</div>';
				$('#contents').append(el);
				$('.boardContent').last().data('boardNum',data.data[i].boardNum);
				console.log('추가된 게시물번호  : '+$('.boardContent').last().data('boardNum'));
			}
			setContentsCss();
			loadContentSw = true;
		},
		
	});
}