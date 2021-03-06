


/**게시물 상세 시작*/

// 모달 처음 실행시 게시물 초기화
$(document).on('show.bs.modal','.detailBoard',function(e){
	var bn = $('.detailBoard').data('boardNum');
	console.log("상세보기 게시물번호  :"+bn);
	$.ajax({
		url:'/board/'+bn,
		type:'get',
		success : function(data){
			if(data.code == 1) {
				var $image = $('.detailProfileImage').attr('src','/user/'+data.data.userNum+'/image');
				var $imageBox = $('.detailProfileImageBox');
				$('.detailUserName').text(data.data.userNick);
				profileCrop($image,$imageBox);
				var year = data.data.boardRegDate.substr(0,4);
				var month = data.data.boardRegDate.substr(4,2);
				var day = data.data.boardRegDate.substr(6,2);
				var hour = data.data.boardRegDate.substr(8,2);
				var min = data.data.boardRegDate.substr(10,2);
				$('.detailRegDate').text(year+' 년 '+month+' 월 '+day+' 일 '+hour+':'+min);
				if(data.data.boardRegDate != data.data.boardUdtDate) {
					$('.detailmodified').text(' 수정됨 ');
				}
				if(userNum == data.data.userNum) {
					$('.buttonLine').css('display','inline-block');
				} else {
					$('.buttonLine').css('display','none');
				}
				$('.boardContentBox').text(data.data.boardContent);
				$.ajax({
					url:'/user/'+data.data.userNum,
					type:'get',
					success : function(data){
						$('.userCommnetText').text(data.data.userComment);
					}
				});
				getFileList(bn);
				getreplyListFirst(bn);
				
			}
		}
	});
	
});

// 모달 데이터들 다 불러온후 화면 리사이징
$(document).on('shown.bs.modal','.detailBoard',function(e){
});


// 모달 종료시 각 동적 html 초기화
$(document).on('hide.bs.modal','.detailBoard',function(e){
	$('.replylistLine').html('');
	$('.fileDownList').html('');
	$('.imageLine').html('');
	$('.userCommnetText').text('');
});


// 댓글 등록
$(document).on('click','#createReply',function(){
	var $replyContent = $('#inputReplyText').val();
	var $boardNum = $('.detailBoard').data('boardNum');
	console.log('등록할 댓글내용'+$replyContent);
	
	$.ajax({
		url : '/board/'+$boardNum+'/reply',
		type : 'post',
		data : {'replyContent':$replyContent},
		success:function(data) {
			console.log('댓글 등록 : '+data.code+'//'+data.message);
			if(data.code == 1) {
				
				$('.replylistLine').html('');
				getreplyListFirst($boardNum);
				var replyCnt = $('.detailBoard').data('board').find('.replyCount').text().substr(6);
				replyCnt = Number(replyCnt);;
				replyCnt += 1;
				$('.detailBoard').data('board').find('.replyCount').text('댓글 수 :'+replyCnt);
				
			}
		}
	});
});

// 댓글 리스트 더받아오기
$(document).on('click','#plusReplys',function(){
	var $boardNum = $('.detailBoard').data('boardNum');
	var url = '/board/'+$boardNum+'/replys?pageNum=3&lastReplyNum='+lastReplyNum;
	getReplyAjax(url);
});

// 댓글 삭제
$(document).on('click','.replyremove',function(){
	var bn = $('.detailBoard').data('boardNum');
	var rn = $(this).parents('.replyContainer').data('replyNum');
	console.log('삭제할 댓글번호 : '+rn);
	$(this).parents('.replyContainer').fadeOut(300,function(){$(this).remove()});
	
	$.ajax({
		url:'board/'+bn+'/reply/'+rn,
		type:'delete',
		success : function(data) {
			console.log(data.code + " // "+ data.message);
			if(data.code == 1) {
				var replyCnt = $('.detailBoard').data('board').find('.replyCount').text().substr(6);
				replyCnt = Number(replyCnt);;
				replyCnt -= 1;
				$('.detailBoard').data('board').find('.replyCount').text('댓글 수 :'+replyCnt);
			}
		}
	});
});

// 댓글 수정
$(document).on('click','.replymodify',function(){
	var rn = $(this).parents('replyContainer').data('replyNum');
	var bn = $('.detailBoard').data('boardNum');
	var $reply = $(this).parents('.replyContainer');
	var bc = $reply.find('.replyContent').text();
	
	var el='';
	
	el += '<input type="text" class="modiyReplyText" />';
	
	var $replyContent = $(this).parent().children('.replyBottomLine');
	$replyContent.html(el);
	$('.modiyReplyText').val(bc);
	$('.modiyReplyText').css('width','100%');
	$(document).on('keyup','.modiyReplyText',function(e){
		if (e.keyCode == 13) {
			var newbc = $('.modiyReplyText').val();
			var el='';
			el += ' 		 <p class="replyContent">'+newbc +'</p>';
			$replyContent.html(el);
			$.ajax({
				url:'board/'+bn+'/reply/'+rn,
				type:'post',
				data:{'replyContent' : newbc },
				success : function(data) {
					console.log(data.code + " // "+ data.message);
					if(data.code == 1) {
						$('.replylistLine').html('');
						getreplyListFirst(bn);
					}
				}
			});
		}
	});
	
});

// 게시물 삭제
$(document).on('click','#btn-detailDelete',function(){
	bn = $('.detailBoard').data('boardNum');
	var $board = $('.detailBoard').data('board');
	$board.fadeOut(300,function(){$board.remove()});
	$.ajax({
		url:'/board/'+bn,
		type:'delete',
		success:function(data){
			console.log('게시물 삭제'+data.code+data.message);
			if(data.code = 1) {
				$('.detailBoard').modal('hide');
			}
		}
	})
});


// 게시물 수정
$(document).on('click','#btn-detailModify',function(){
	$('.updateBoard').modal();
}); 

//게시물 수정 창 초기화
$(document).on('show.bs.modal','.updateBoard',function(e){
	var bn = $('.detailBoard').data('boardNum');
	var $board = $('.detailBoard').data('board');
	var $detailBoard = $('.detailBoard');
	
	console.log("게시물 수정 게시물번호  :"+bn);
	$.ajax({
		url:'/board/'+bn,
		type:'get',
		success : function(data){
			if(data.code == 1) {
				var $image = $('.detailProfileImage').attr('src','/user/'+data.data.userNum+'/image');
				var $imageBox = $('.detailProfileImageBox');
				$('.detailUserName').text(data.data.userNick);
				profileCrop($image,$imageBox);
				var year = data.data.boardRegDate.substr(0,4);
				var month = data.data.boardRegDate.substr(4,2);
				var day = data.data.boardRegDate.substr(6,2);
				var hour = data.data.boardRegDate.substr(8,2);
				var min = data.data.boardRegDate.substr(10,2);
				$('.detailRegDate').text(year+' 년 '+month+' 월 '+day+' 일 '+hour+':'+min);
				if(data.data.boardRegDate != data.data.boardUdtDate) {
					$('.detailmodified').text(' 수정됨 ');
				}
				
				$('#boardContentModify').text(data.data.boardContent);
			
				$.ajax({
					url : '/board/'+bn+'/files',
					tpye :'get',
					success: function(data) {
						if(data.code == 1) {
							
							var len = data.data.length;
							console.log("게시물 수정 파일리스트 : "+bn);
							for(var i=0; i<len; i++) {
								var filesizse = data.data[i].fileSize.toFixed(2); 
								var el ='';
								
								el +='<div class="col-md-12 fileModifyItem"><p class="filetitle">'+data.data[i].fileName+'\t\t'+filesizse+' KB</p><button class=" btn-delFileList">삭제</button> </div>';
				
								$('.fileModify').append(el);
								$('.fileModifyItem').last().data('fileNum',data.data[i].fileNum);
								
							}							
						}
					}
				});				
			}
		}
	});
});

//게시물 수정 - 파일 업로드
$(document).on('change','.uploadFile',function(){
	var $file = $(this).val();
	if ($file != null) {
		var fileType = this.files[0].type;
		if (fileType.startsWith('image')
			|| fileType.startsWith('audio')
			|| fileType.startsWith('video')) {
				var $fileSize = this.files[0].size / 1024.0;
				console.log("Type: " + this.files[0].type);
				var $fileSizeFix = $fileSize.toFixed(2);
				$(this).parent().children('.uploadFileSize').text($fileSizeFix + ' KB');
						if ($(this).parent().children('.fileDelete').length == 0) {
							var delBtn = '<button class=\'fileDelete\'>삭제</button>';
							$(this).parent().append(delBtn);
							var uploadbox = '';
							uploadbox += '<div class=\"col-md-11 col-md-offset-1 fileUploadBox\">';
							uploadbox += '  <input type=\"file\" class = \"uploadFile\" />';
							uploadbox += '   <p class=\"uploadFileSize\">0 KB</p>';
							uploadbox += '  </div>';
							$(this).parent().parent().append(uploadbox);
							$('.updateBoard').height($('.updateBoard').height()+$('.fileUploadBox').outerHeight());
						}
						
					} else {
						$(this).replaceWith($(this).clone(true));
						$file = null;
						$('#uploadPrintMessage').css('font-color','red');
					}
				} 
}) ;

//게시물 수정 - 업로드 취소 
$(document).on('click','.fileDelete',function() {
			var $fileCount = $(this).parent().parent().children(
					'.fileUploadBox').length;
			if ($fileCount > 1) {
				$(this).parent().remove();
			} else {
				$(this).parent().children('.uploadFile').val(null);
			}
			
});

var $delFiles =[];
// 게시물 수정 - 파일 삭제 선택
$(document).on('click','.btn-delFileList',function() {
	console.log("삭제선택 = ");
	$(this).parent().children('.filetitle').css('color','red');
	
	var el ='';
	el += '<button class="btn-delFileListCancel">취소</button>'
	$(this).parent().append(el);
	console.log('데이터 확인',$(this).parent().data('fileNum'))
	$delFiles.push($(this).parent().data('fileNum'));
	console.log("delfiles = "+$delFiles.toString());
	$(this).remove();
});

//게시물 수정 - 파일 삭제 취소
$(document).on('click','.btn-delFileListCancel',function() {
	console.log("취소선택");
	$(this).parent().children('.filetitle').css('color','black');
	
	var el ='';
	el += '<button class="btn-delFileList">삭제</button>'
	$(this).parent().append(el);
	
	var removeItem = $(this).parent().data('fileNum');
	$delFiles = jQuery.grep($delFiles, function(value) {
		  return value != removeItem;
		});
	console.log("delfiles = "+$delFiles.toString());
	$(this).remove();
});

//수정 완료 버튼
$(document).on('click','.btn-modify',function() {
	var bn = $('.detailBoard').data('boardNum');
	var NumBn = Number(bn)+1;

	var $newBoardContent = $('#boardContentModify').val();
	
	var fileNum = $('.uploadFile').length-1;
	var $files = $('.uploadFile');
	
	
	var formData = new FormData();
	
	var len = $delFiles.length;
	for(var i = 0; i<len; i++) {
		var value = String($delFiles[i]);
		formData.append('delFiles', value);
	}

	formData.append('boardContent', $newBoardContent);
	for( var i = 0 ; i < fileNum ;i++) {
		formData.append('files', $files[i].files[0]);
		console.log('fileappend'+i+' // '+$files.eq(i).val()+' // '+$files[0].files[0]);
	}

	$.ajax({
		url : '/board/'+bn,
		type : 'post',
		processData : false,
		contentType : false,
		data : formData,
		success : function(data) {
			console.log(data.code+" / "+data.message);
			if(data.code == 1){		
				$('.updateBoard').modal('hide');
				
				$.ajax({
					url : '/boards?pageNum=1&lastBoardNum='+NumBn,
					type : 'get',
					success : function(data) {
						
						var board = data.data[0];
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
				
						$('.detailBoard').data('board').after(el);
						$('.detailBoard').data('board').next().data('boardNum',board.boardNum);
						$('.detailBoard').data('board').remove();
						setContentsCss();
					}
				});
			}
		}

	});
});

// 수정 취소버튼
$(document).on('click','.btn-modifyCancel',function() {
	$('.updateBoard').modal('hide');
});



// 모달 종료시 각 동적 데이터 초기화
$(document).on('hide.bs.modal','.updateBoard',function(e){
	$('.fileModify').html('');
	$('#boardContentModify').text('');
	$('.detailBoard').modal('hide');
	$('.fileDelete').click();
	
});


// 게시물 파일리스트 받아오기
function getFileList(boardNum) {
	$.ajax({
		url : '/board/'+boardNum+'/files',
		tpye :'get',
		success: function(data) {
			if(data.code == 1) {
				var len = data.data.length;
				var imageNum = 0;
				for(var i=0; i<len; i++) {
					var fType = data.data[i].fileType;
					if(fType.startsWith('image')) {
						var img = '';
						img +='<img class="showImage" src="'+data.data[i].fileUrl+'" />';
						
						$('.imageLine').append(img);
				
					}
					var filesizse = data.data[i].fileSize.toFixed(2); 
					var el ='';
					
					el +='<li class="fileDownItem"><button class="btn btn-default><a href="'+data.data[i].fileUrl+'?view=down'+'">'+data.data[i].fileName+'\t\t'+filesizse+' KB </a></button></li>';
					
					$('.fileDownList').append(el);
				}
				
			}
		}
	});
}


// 게시물 첫 댓글리스트 받아오기
function getreplyListFirst(boardNum){
	$('.replylistLine').html('');
	var url = '/board/'+boardNum+'/replys';
	getReplyAjax(url);
	
}


//댓글 받아오는 ajax
function getReplyAjax(url) {
	
	$.ajax({
		url : url,
		type:'get',
		success: function(data) {
			if(data.code ==1) {
				var len = data.data.length;
				console.log('댓글  '+data.data);
				for( var i=0;i<len;i++) {
					var el = '';
					el += '<div class="col-md-12 replyContainer">';
					el += ' 	<div class="col-md-2 replyProfileBox">';
					el += ' 		<img class="replyProfile" src="/user/'+data.data[i].userNum+'/image" />';
					el += ' 	</div>';
					el += ' 	<div class="col-md-10 replyContentBox">';
					el += ' 		<div class="col-md-12 replyTopLine">';
					el += ' 		 <p class="replyTop replyUserName">'+data.data[i].userNick+'</p>';
					var year = data.data[i].replyRegDate.substr(0,4);
					var month = data.data[i].replyRegDate.substr(4,2);
					var day = data.data[i].replyRegDate.substr(6,2);
					var hour = data.data[i].replyRegDate.substr(8,2);
					var min = data.data[i].replyRegDate.substr(10,2);
					el += ' 		 <p class="replyTop replydata">'+year+' 년 '+month+' 월 '+day+' 일 '+hour+':'+min+' </p>';
					if(data.data[i].replyRegDate != data.data[i].replyUdtDate) {
						el += ' 		 <p class="replyTop replymodifyed"> 수정됨 </p>';
					}
					if(userNum == data.data[i].userNum) {
						el += ' 		 <a class="replyTopright replymodify"> 수정 </a>';
						el += ' 		 <p class="replyTopright "> / </p>';
						el += ' 		 <a class="replyTopright replyremove"> 삭제 </a>';
					}
					el += ' 		<div class="col-md-12 replyBottomLine">';
					el += ' 		 <p class="replyContent">'+data.data[i].replyContent +'</p>';
					el += ' 	 	</div>';
					el += ' 	 	</div>';	
					el += ' 	</div>';
					el += '</div>';
					
					lastReplyNum = data.data[i].replyNum;
					$('.replylistLine').append(el);
					$('.replyContainer').last().data('replyNum',data.data[i].replyNum);
					console.log('추가된 댓글 번호 : '+$('.replyContainer').last().data('replyNum'));	
					setReplyCss();
				}
			} 
		}
	});
}

// 데이터가 추가될때 모달창 리사이즈
function resizeModalHeight() {
	$('.modal-dialog').height($('.detailBoard').height());
	
	$('.modal-dialog').height($('.modal-backdrop').height());
	var check = $('.modal-backdrop').height()+80;
	$('.modal-backdrop').height(check);
	
	
}

function setReplyCss() {
	$('.replyTop').css('font-size', '7px');
	$('.replyTop').css('float', 'left');
	$('.replyTop').css('margin-right', '3px');
	$('.replyUserName').css('font-size', '12px');
	$('.replyUserName').css('margin-right', '5px');
	$('.replyTopright').css('float', 'right');

	$('.replyProfile').css('width', '100%');
	$('.replyProfile').css('height', '100%');

	$('.replyProfileBox').css('padding', '0px');
	$('.replyProfileBox').css('border-radius', '20px');
	$('.replyProfileBox').css('overflow', 'hidden');
	$('.replyProfileBox').css('border', '3px solid #eff');
	$('.replyProfileBox').css('width', '40px');
	$('.replyProfileBox').css('height', '40px');
	profileCrop($('.replyProfile'), $('.replyProfileBox'));

}










