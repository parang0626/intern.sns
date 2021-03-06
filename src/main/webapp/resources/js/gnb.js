
$(function() {
	console.log("gnb ready , userNum = " + userNum);
	$.ajax({
		url : '/user/' + userNum,
		type : 'get',
		success : function(data) {
			if (data.code == 1) {
				var userName = data.data.userNick;
				$('.uploadUserName').text(userName);
				$('#userName').text(data.data.userNick + " 님 안녕하세요").css(
						'color', 'white');
				var $image = $('#gnb_profile_img').attr('src',
						data.data.imageUrl);
				var $imageBox = $('.crop');
				profileCrop($image, $imageBox);
			}
		},
		error : function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:"
					+ request.responseText + "\n" + "error:" + error);
		}
	});
});

// 로그아웃
$(document).on('click', '#btn-logout', function() {
	$.ajax({
		url : '/logout',
		type : 'get',
		success : function(data) {
			location.replace('/signin');
		}
	});
});

function profileCrop($image, $imageBox) {
	var imgAspect = $image.height() / $image.width();

	if (imgAspect <= 1) {
		var imgWidth = $imageBox.outerHeight();
		var imgWidthReal = $imageBox.outerHeight() / imgAspect;
		var margin = -(Math.round((imgWidthReal - imgWidth) / 2));
		$image.css('width', 'auto');
		$image.css('height', '100%');
		$image.css('margin-left', margin + 'px');

	} else {
		$image.css('width', '100%');
		$image.css('height', 'auto');
	}
}

function setContentsCss() {
	$('.boardContent').css('margin', '20px');
	$('.boardContent').css('background-color', '#eee');
	$('.boardContent').css('border-radius', '20px');
	$('.boardContent').css('height', '25%');
	$('.boardContent').css('width', '90%');
	$('.boardContent').css('margin-right', '0px');

	$('.boardContentTopLine').css('height', '20%');
	$('.boardContentTopLine').css('margin', '5px');
	$('.boardContentTopLine').css('line-height', '40px');
	$('.boardContentTopLine').css('margin-bottom', '0px');

	$('.contentProfileImage').css('width', '100%');
	$('.contentProfileImage').css('height', '100%');

	$('.contentProfileBox').css('padding', '0px');
	$('.contentProfileBox').css('border-radius', '25px');
	$('.contentProfileBox').css('overflow', 'hidden');
	$('.contentProfileBox').css('border', '3px solid #eff');
	$('.contentProfileBox').css('width', '50px');
	$('.contentProfileBox').css('height', '50px');
	profileCrop($('.contentProfileImage'), $('.contentProfileBox'));

	$('.contentUserNameBox').css('display', 'lnline-block');
	$('.contentUserNameBox').css('vertical-align', 'middle');
	$('.contentUserNameBox').css('height', '100%');

	$('.boardContentMiddleLine').css('background-color', '#f9f9f9');
	$('.boardContentMiddleLine').css('border-radius', '10px');
	$('.boardContentMiddleLine').css('height', '50%');
	$('.boardContentMiddleLine').css('min-height', '80px');
	$('.boardContentMiddleLine').css('padding', '0px');
	$('.boardContentMiddleLine').css('padding-right', '5px');

	$('.contentFirstImageBox').css('margin', '5px');
	$('.contentFirstImageBox').css('padding', '0px');
	$('.contentFirstImageBox').css('height', '70px');
	$('.contentFirstImageBox').css('width', '70px');
	$('.contentFirstImageBox').css('overflow', 'hidden');
	$('.contentFirstImageBox').css('border-radius', '10px');
	$('.contentFirstImage').css('padding', '0px');
	$('.contentFirstImage').css('height', '100%');
	$('.contentFirstImage').css('width', 'auto');
	$('.contentFirstImage').css('margin', '0px');

	$('.contentModify').css('font-size', '10px');
	$('.contentModify').css('text-align', 'right');

	$('aside').css('height', $(document).height() + 'px');
}


