




// 가입 취소
$(document).on('click', '#btn-cancel', function() {
	var url = '/signin';
	$(location).attr('href', url);
});

$(document).on('focus', '.inputTextBox', function() {
	$(this).css('background-color', 'rgb(255,255,255)');
});


// 4개의 텍스트 입력 후 포커스 아웃
$(document).on('focusout', '.inputTextBox', function() {
	var id = $(this).attr('id');
	switch (id) {
	case 'inputId': {
		var inputId = $(this).val();
		var reg = /^[A-za-z0-9]{5,15}$/g;
		if (reg.test(inputId)) {
			passCheck[0] = true;
			$(this).css('background-color', 'rgb(255,255,255)');
			if (tempId != inputId) {
				passCheck[1] = false;
			}
			nextMessage();
		} else {
			passCheck[0] = false;
			$(this).css('background-color', 'rgba(255,0,0,0.25)');
			$('#printMessage').text(messageArray[0]).css('color', 'red');
		}
		
		break;

	}
	case 'inputNick': {
		var inputNick = $(this).val();
		if (inputNick.length >= 2) {
			passCheck[2] = true;
			$(this).css('background-color', 'rgb(255,255,255)');
			if (tempNick != inputNick) {
				passCheck[3] = false;
			}
			nextMessage();
		} else {
			passCheck[2] = false;
			$(this).css('background-color', 'rgba(255,0,0,0.25)');
			$('#printMessage').text(messageArray[2]).css('color', 'red');
		}
		break;
	}
	case 'inputPass': {
		var inputPass = $(this).val();
		if (inputPass.length >= 6) {
			passCheck[4] = true;
			$(this).css('background-color', 'rgb(255,255,255)');
			nextMessage();
		} else {
			passCheck[4] = false;
			$(this).css('background-color', 'rgba(255,0,0,0.25)');
			$('#printMessage').text(messageArray[4]).css('color', 'red');
		}
		break;
	}
	case 'inputCheckPass': {
		var inputCheckPass = $(this).val();
		var inputPass = $('#inputPass').val();
		if (inputCheckPass == inputPass) {
			passCheck[5] = true;
			$(this).css('background-color', 'rgb(255,255,255)');
			nextMessage();
		} else {
			passCheck[5] = false;
			$(this).css('background-color', 'rgba(255,0,0,0.25)');
			$('#printMessage').text(messageArray[5]).css('color', 'red');
		}
		break;
	}

	}
});

// 파일선택 클릭
$(document).on('click', '#fakeFileButton', function() {
	$('#profileUploadButtom').click();
});

// 아이디 중복확인 버튼 클릭
var tempId;
$(document)
		.on(
				'click',
				'#btn-checkId',
				function() {
					var $inputId = $('#inputId').val();
					var reg = /^[A-za-z0-9]{5,15}$/g;
					if (reg.test($inputId)) {
						$.ajax({
							url : '/user/checkid/' + $inputId,
							type : 'get',
							success : function(data) {
								console.log('코드 : ' + data.code + ' //  '
										+ data.message);
								if (data.code == 1) {
									$('#printMessage').text('사용가능한 아이디입니다.')
											.css('color', 'black');
									$('#inputId').css('background-color',
											'rgb(255,255,255)');
									passCheck[1] = true;
									tempId = $inputId;
								} else if (data.code == 0) {
									$('#printMessage').text('존재하는 아이디입니다.')
											.css('color', 'red');
									$('#inputId').css('background-color',
											'rgba(255,0,0,0.25)');
									passCheck[1] = false;
								}
							}
						});
					} else {
						$('#printMessage').text('올바른 아이디를 입력해주세요.').css(
								'color', 'red');
						$('#inputId').css('background-color',
								'rgba(255,0,0,0.25)');
					}
				});

// 닉네임 중복확인 버튼 클릭
var tempNick;
$(document).on(
		'click',
		'#btn-checkNick',
		function() {
			var $inputNick = $('#inputNick').val();
			if ($inputNick.length >= 2) {
				$.ajax({
					url : '/user/checkname/' + $inputNick,
					type : 'get',
					success : function(data) {
						console.log('코드 : ' + data.code + ' // 메세지 :  '
								+ data.message);
						if (data.code == 1) {
							$('#printMessage').text('사용가능한 닉네임입니다.').css(
									'color', 'black');
							$('#inputNick').css('background-color',
									'rgb(255,255,255)');
							passCheck[3] = true;
							tempNick = $inputNick;
						} else if (data.code == 0) {
							$('#printMessage').text('존재하는 닉네임입니다.').css(
									'color', 'red');
							$('#inputNick').css('background-color',
									'rgba(255,0,0,0.25)');
							passCheck[3] = false;
						}
					}
				});
			}
		});

// 가입 버튼 클릭

var allCheck = false;
$(document).on(
		'click',
		'#btn-signup',
		function() {
			var pass = true;
			noTouchMode = false;
			$.each(passCheck, function(index, value) {
				if (!value) {
					pass = false;
					switch (index) {
					case 0:
					case 1: {
						$('#inputId').css('background-color',
								'rgba(255,0,0,0.25)');
						break;
					}
					case 2:
					case 3: {
						$('#inputNick').css('background-color',
								'rgba(255,0,0,0.25)');
						break;
					}
					case 4: {
						$('#inputPass').css('background-color',
								'rgba(255,0,0,0.25)');
						break;
					}
					case 5: {
						$('#inputCheckPass').css('background-color',
								'rgba(255,0,0,0.25)');
						break;
					}
					}
					nextMessage(passCheck);
				}
			});
			if (pass) {
				var formData = new FormData();
				formData.append('userId', $('#inputId').val());
				formData.append('userPass', $('#inputPass').val());
				formData.append('userNick', $('#inputNick').val());
				formData.append('userType', 'user');
				formData.append('psnToken', '');
				if (!imageDefault) {
					formData.append('userFile',
							$('#profileUploadButtom')[0].files[0]);
				}

				$.ajax({
					url : '/user',
					type : 'post',
					processData : false,
					contentType : false,
					data : formData,
					success : function(data) {
						console.log(data.code + ' // ' + data.message);
						if (data.code == 1) {
							$.ajax({
								url : '/login',
								type : 'post',
								data : {
									'userId' : $('#inputId').val(),
									'userPass' : $('#inputPass').val(),
									'userType' : 'user',
									'pnsToken' : ''
								},
								success : function(data) {
									console.log(data.code + ' // '
											+ data.message);
									if (data.code == 1) {
										$(location).attr('href', '/main');
									} else {
										$(location).attr('href', '/signup');
									}
								},
								error : function(request, status, error) {
									alert("code:" + request.status + "\n"
											+ "message:" + request.responseText
											+ "\n" + "error:" + error);
									location.replace('/signup');
								}
							});
						}
					}
				});
			}
		});

// 다음 메세지를 확인해서 뿌려줌
var messageArray = [ '올바른 아이디를 입력해주세요', '아이디 중복을 확인해주세요', '올바른 닉네임을 입력해주세요',
	'닉네임 중복을 확인해주세요', '올바른 비밀번호를 입력해주세요', '비밀번호가 일치하지 않습니다.' ];
var passCheck = [ false, false, false, false, false, false ];
var noTouchMode = true;

function nextMessage() {
	if(noTouchMode) {
		$('#printMessage').text('가입정보를 입력해주세요').css('color', 'black');
	} else {
		var check = true;
		$.each(passCheck, function(index, value) {
			if (!value) {
				$('#printMessage').text(messageArray[index]).css('color', 'red');
				check = false;
				return false;
			}
		});
		if (check) {
			$('#printMessage').text('가입 버튼을 눌러주세요').css('color', 'black');
		}
	}
}

var imageDefault = true;
// 이미지 업로드

$(document).on('change', '#profileUploadButtom', function() {
	var input = $(this)[0];
	if($(this) != null) {
		var fileType = this.files[0].type;
		if (fileType.match(/image/gi)) {
			var reader = new FileReader();
			reader.onload = function(e){
				$('#profile_img').attr('src',e.target.result);
				profileCrop($('#profile_img'),$('.crop'));
			}
			imageDefault = false;
			reader.readAsDataURL(this.files[0]);
		} else {
			$(this).replaceWith($(this).clone(true));
			$('#profileDefaultButtom').click();
			$('#printMessage').text('이미지만 선택해 주세요').css('color','red');
		}
	}
	$('#profileDefaultButtom').click(function() {
		imageDefault = true;
		$image.attr('src', '/resources/image/default.jpg');
		$image.css('margin-left', '0');
	});
	
})
