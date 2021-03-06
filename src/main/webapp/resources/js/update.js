
var tempNick;
var originalNick;
var nickCheck = true;

// 기본 입력자 확인
$(function(){
	var userProfileUrl = '/user/'+userNum+'/image';
	$('#inputPassword').focus();
	$.ajax({
		url:'/user/'+userNum,
		type:'get',
		success : function(data){
			$('#updateProfileImage').attr('src',data.data.imageUrl);
			$('#inputNick').val(data.data.userNick);
			tempNick = data.data.userNick;
			originalNick = data.data.userNick;
			nickCheck = true;
			$('#inputUserComment').text(data.data.userComment);
		}
	});
	
	
	profileCrop($('#updateProfileImage'),$('#updateProfileImageBox'));
});

// 처음 비밀번호 재확인 버튼
$(document).on('click','#btn-checkPass',function(){	
	var $inputPass = $('#inputPassword').val();
	
	$.ajax({
		url:'/user/checkpass',
		type:'post',
		data:{'userPass': $inputPass},
		success: function(data){
			console.log(data.code+" : "+data.message);
			if(data.code == 1) {
				$('.checkPassLine').remove();
				$('.updateContainer').css('display','block');
			} else {
				$('#CheckPrintMessage').text('비밀번호가 일치하지 않습니다.')
			}
		}
	})
});

//비밀번호 재확인시 엔터 누름
$(document).on('keyup','#inputPassword',function(e){
	if (e.keyCode == 13) {
		$('#btn-checkPass').click();
	}
});

// 취소버튼시 메인으로 이동
$(document).on('click','#btn-cancel',function(){	
	$(location).attr('href','/main');
});



// 이미지 파일 선택 버튼
var tempImage;
$(document).on('change','#profileUploadButton',function(){
	if($(this) != null) {
		var fileType = this.files[0].type;
		if (fileType.match(/image/gi)) {
			var reader = new FileReader();
			reader.onload = function(e){
				$('#updateProfileImage').attr('src',e.target.result);
				profileCrop($('#updateProfileImage'),$('#updateProfileImageBox'));
			}
			profileImageChanged = true;
			
			reader.readAsDataURL(this.files[0]);
		} else {
			$(this).replaceWith($(this).clone(true));
			$(this).val('');
			$('#printMessage').text('이미지만 선택해 주세요').css('color','red');
		}
	}
	
});

//파일선택 클릭
$(document).on('click','#fakeFileButton',function(){
	$('#profileUploadButton').click();
});

// 프로파일 기본으로 돌리기 버튼
$(document).on('click','#profileDefaultButtom',function(){
	var userProfileUrl = '/user/'+userNum+'/image';
	$('#updateProfileImage').attr('src',userProfileUrl);
	profileCrop($('#updateProfileImage'),$('#updateProfileImageBox'));
	
	$('#profileUploadButton').val('');
	$('#printMessage').text('기존 이미지를 유지합니다.');
	profileCrop($('#updateProfileImage'),$('#updateProfileImageBox'));
	profileImageChanged=false;
	alert('이미지 수정 버그 체크3 : '+$('#profileUploadButton').val()+" // changed : "+profileImageChanged);
});

// 수정 버튼 클릭


var nickChanged = false;
var profileImageChanged = false;
var passwordChanged = false;
var commentChaged = false;
$(document).on('click','#btn-update',function(){
	var inputNick = $('#inputNick').val();
	
	if( inputNick.length > 1) {
		if(nickCheck) {
			nickChanged = true;
		} else {
			$('#inputNick').css('background-color','rgba(255,0,0,0.25)');
			$('#printMessage').text('아이디 중복 확인을 해주세요').css('color','red');
			nickChanged = false;
			return false;
		}
	}
	
	
	var inputPass = $('#inputPass').val();
	var inputCheckPass = $('#inputCheckPass').val();
	if( inputPass.length >= 6) {
		if( inputPass == inputCheckPass ) {
			passwordChanged = true;
		} else {
			$('#inputPass').css('background-color','rgba(255,0,0,0.25)');
			$('#inputCheckPass').css('background-color','rgba(255,0,0,0.25)');
			$('#printMessage').text('비밀번호가 일치하지 않습니다.').css('color','red');
			passwordChanged = false;
		}
	} else if (inputPass.length > 1) {
		passwordChanged = false;
		$('#inputPass').css('background-color','rgba(255,0,0,0.25)');
		$('#printMessage').text('비밀번호는 6자 이상 입력해야 수정됩니다.').css('color','red');
		return false;
	}
	
	if($('#profileUploadButton').val()=='') {
		profileImageChanged=false;
	}
	
	var comment = $('#inputUserComment').val();
	if(comment.length > 0){
		commentChaged =true;
	}
	
	var isEmpty = false;
	if(inputNick==''  && inputPass=='' && !profileImageChanged && comment=='') {
		$('#printMessage').text('수정 사항이 없습니다.').css('color','red');
		isEmpty = true;
	} 
	
	if(!isEmpty) {
		var formData = new FormData();
		if(nickChanged) {
			formData.append('userNick',inputNick);
		}
		if(passwordChanged) {
			formData.append('userPass',inputPass);
		}
		if(profileImageChanged) {
			formData.append('userFile',$('#profileUploadButton')[0].files[0]);
		}
		if(commentChaged) {
			formData.append('userComment',comment);
		}
		
		$.ajax({
			url:'/user/modify',
			type:'post',
			processData : false,
			contentType : false,
			data : formData,
			success : function(data){
				console.log("유저정보 수정 성공");
				if(data.code == 1) {
					$(location).attr('href','/main');
				} else {
					alert('유저 수정에 실패 했습니다.');
				}
			},
		     error:function(request,status,error){
		     alert('서버와의 연결 오류');
	         $location.replace('/signin');
	       }
		});
	}
	
});

// 닉네임 중복체크
$(document).on('click','#btn-checkNick',function(){
	var nick = $('#inputNick').val();
	if( nick.length > 1) {
		if( nick == originalNick) {
			$('#printMessage').text('원래 사용하던 닉네임입니다.').css('color','black');
			nickCheck = true;
			nickChanged = false;
		} else {
			$.ajax ({
				url : '/user/checkname/'+nick,
				type : 'get',
				success : function(data) {
					console.log('닉네임 중복 체크 code : '+data.code+' / '+data.message);
					if(data.code == 1 ){
						nickCheck = true;
						$('#printMessage').text('사용 가능한 닉네임입니다.').css('color','black')
						$('#inputNick').css('background-color','rgb(255,255,255)');
						tempNick = nick;
					} else if (data.code == 0) {
						nickCheck = false;
						$('#printMessage').text('중복되는 닉네임이 존재합니다.').css('color','red');
					}
				}
			});
		}
		
	} else {
		nickCheck = false;
		$('#inputNick').css('background-color','rgba(255,0,0,0.25)');
		$('#printMessage').text('닉네임을 2자이상 입력해주세요').css('color','red');
	}
});

// 입력사항 포커스시
$(document).on('focus','.inputTextBox',function(){
	$(this).css('background-color','rgb(255,255,255)');
});

//입력사항 포커스아웃
$(document).on('focusout','#inputNick',function(){
	var newNick = $(this).val();
	if( newNick != tempNick) {
		nickCheck =false;
	}
});

//탈퇴버튼
$(document).on('click','#btn-remove',function(){
	var del = confirm('정말 탈퇴하시겠습니까?');
	if(del) {
		$.ajax({
			url:'/user',
			type:'delete',
			success: function(data) {
				console.log('유저 탈퇴 -> '+data.code+data.message);
				location.replace('/signin');
				
			},
		     error:function(request,status,error){
		         $location.replace('/signin');
		      }
		})
	} 
}) ;


