$(function() {
	var $btnSignIn = $('#btn-signin');
	var $btnSignUp = $('#btn-signup');
	var $printMessage = $('#loginInfo');
	var $inputIdText = $('#inputId');
	var $inputPasswordText = $('#inputPassword');

	var isMessage; // 가장 상단메세지가 나오도록 정하는 스위치
	


});

// 로그인 버튼 클릭
$(document).on('click','#btn-signin',function() {

			var isPass = true;
			var $inputedId = $('#inputId').val();
			var $inputedPass = $('#inputPassword').val();
			var isMessage = false;

			if ($inputedId == '') {
				if (!isMessage) {
					$('#loginInfo').text('아이디가 비어 있습니다.').css('color', 'red');
					isMessage = true;
				}
				$('#inputId').css('background-color', 'rgba(255,0,0,0.25)');
				$('#inputId').focusout();
				isPass = false;
			}

			if ($inputedPass == '') {
				if (!isMessage) {
					$('#loginInfo').text('비밀번호가 비어 있습니다.').css('color', 'red');
					isMessage = true;
				}
				$('#inputPassword').css('background-color',
						'rgba(255,0,0,0.25)');
				$('#inputPassword').focusout();
				isPass = false;
			}
			isMessage = false;

			if (isPass) {
				$.ajax({
					url : '/login',
					type : 'post',
					data : {
						'userId' : $inputedId,
						'userPass' : $inputedPass,
						'userType' : 'user',
						'pnsToken' : ''
					},
					success : function(data) {
						console.log(data.code + ' // ' + data.message);
						if (data.code == 1) {
							$(location).attr('href', '/main');

						} else {
							$('#loginInfo').text('아이디와 비밀번호를 확인해주세요').css(
									'color', 'red');
							$('#inputPassword').css('background-color',
									'rgba(255,0,0,0.25)');
							$('#inputId').css('background-color',
									'rgba(255,0,0,0.25)');
							$('#inputPassword').focusout();
							$('#inputId').focusout();
						}
					},
					error : function(data) {
						$('#loginInfo').text('서버와의 오류').css('color', 'red');
					}
				});
			}

			isPass = true;
		});

// 회원가입 버튼 클릭
$(document).on('click', '#btn-signup', function() {
	var url = '/signup';
	console.log('sss');
	$(location).attr('href', url);
});

// 아이디 박스 포커스
$(document).on('focus', '#inputId', function() {
	$('#inputId').css('background-color', 'rgb(255,255,255)');
	$('#inputPassword').css('background-color', 'rgb(255,255,255)');
	$('#inputId').keydown(function() {
		$('#inputPassword').text(null).css('color', 'black');
	})
	isPass = true;
});



// 비밀번호 입력시 포커스
$(document).on('focus', '#inputPassword',function() {
	$('#inputId').css('background-color', 'rgb(255,255,255)');
	$('#inputPassword').css('background-color', 'rgb(255,255,255)');
	$('#inputPassword').keydown(function() {
		$('#inputPassword').text(null).css('color', 'black');
	})
	isPass = true;
})

// 아이디에 입력사항이 있을경우
$(document).on('keyup', '#inputId',function(e) {
	if (e.keyCode == 13) {
		$('#btn-signin').click();
	}
});

$(document).on('keyup', '#inputPassword',function(e) {
	if (e.keyCode == 13) {
		$('#btn-signin').click();
	}
});
