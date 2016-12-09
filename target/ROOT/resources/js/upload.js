var fileUploadBox;
$(function() {
	var $image = $('#uploadProfileImage').attr('src',
			'/user/' + userNum + '/image');
	var $imageBox = $('.uploadProfileImageBox');
	profileCrop($image, $imageBox)
	$('aside').css('height', $(document).height() + 'px');
	fileUploadBox = $('.uploadBottomLine').first().html();
});


// 파일 추가버튼 클릭
$(document).on('click', '.fileAdd', function() {
	$(this).parents('.fileUploadBox').find('.uploadFile').click();
});

// 파일 삭제버튼 클릭
$(document).on('click', '.fileRemove', function() {
	if($('.fileUploadBox').length > 1) {
		$(this).parents('.fileUploadBox').remove();
	}	
});



// 파일 업로드
$(document).on(
		'change',
		'.uploadFile',
		function() {
			var $file = $(this).val();
			if ($file != null) {
				var fileType = this.files[0].type;
				if (fileType.match(/image/gi) || fileType.match(/audio/gi)
						|| fileType.match(/video/gi)) {
					var $fileSize = this.files[0].size / 1024.0;
					var $fileSizeFix = $fileSize.toFixed(2);
					var $fileName = $file.substr($file.lastIndexOf('\\')+1);
					$(this).parents('.fileUploadBox').find('.fileUploadName').text($fileName);
					$(this).parents('.fileUploadBox').find('.fileUploadSize').text($fileSizeFix+' KB ');
					
					
					var delBtn = '<button class="btn btn-default fileRemove">파일 삭제</button>'

					$(this).parents('.fileUploadBox').find('.fileBtnBox').html(delBtn);
					$(this).parents('.uploadBottomLine').append(fileUploadBox);
				}

			}
		});

// 파일 삭제 버튼
$(document).on(
		'click',
		'.fileDelete',
		function() {
			var $fileCount = $(this).parent().parent().children(
					'.fileUploadBox').length;
			if ($fileCount > 1) {
				$(this).parent().remove();
			} else {
				$(this).parent().children('.uploadFile').val(null);
			}
		});

// 취소 버튼
$(document).on('click', '#btn-uploadCancel', function() {
	$(location).attr('href', '/main');
});

// 업로드 버튼
$(document).on(
		'click',
		'#btn-upload',
		function() {

			var fileNum = $('.uploadFile').length - 1;
			var $files = $('.uploadFile');
			var formData = new FormData();
			formData.append('boardContent', $('#inputBoardContent').val());
			for (var i = 0; i < fileNum; i++) {
				formData.append('files', $files[i].files[0]);
				console.log('fileappend' + i + ' // ' + $files.eq(i).val()
						+ ' // ' + $files[0].files[0]);
			}

			$.ajax({
				url : '/board',
				type : 'post',
				processData : false,
				contentType : false,
				data : formData,
				success : function(data) {
					console.log(data.code + " / " + data.message);
					if (data.code == 1) {
						$(location).attr('href', '/main');
					}
				}

			});

		});
