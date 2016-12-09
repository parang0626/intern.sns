$(function() {
	var $pathname = $(location).attr('pathname');
	oldHeight = $(document).height();
	console.log("document height =" +oldHeight);
	$('aside').height(oldHeight);
	
	
	console.log('lnb ready , pathname = ' + $pathname);

	switch ($pathname) {
	case '/main': {
		$('#sidemenu-main').parent().css('background-color','#fff');
		$('#sidemenu-main').css('font-color','#000');
		break;
	}
	case '/search': {
		$('#sidemenu-search').parent().css('background-color','#fff');
		$('#sidemenu-search').css('font-color','#000');
		break;
	}
	case '/upload': {
		$('#sidemenu-upload').parent().css('background-color','#fff');
		$('#sidemenu-upload').css('font-color','#000');
		break;
	}
	case '/update': {
		$('#sidemenu-update').parent().css('background-color','#fff');
		$('#sidemenu-update').css('font-color','#000');
		break;
	}

	}
});

$(document).on('click','.menuitems',function(){
	var $child = $(this).children().attr('id');
	switch ($child) {
	case 'sidemenu-main' : {
		$(location).attr('href','/main');
		break;
	}
	case 'sidemenu-upload' : {
		$(location).attr('href','/upload');
		break;
	}
	case 'sidemenu-update' : {
		$(location).attr('href','/update');
		break;
	}
	case 'sidemenu-search' : {
		$(location).attr('href','/search');
		break;
	}
	}
});

var oldHeight;
$(document).on('change',this,function(){
	var newHeight = $(this).height();
	console.log("document oldheight =" +oldHeight);
	console.log("document newheight =" +newHeight);
	if(newHeight != oldHeight) {
		$('aside').height($(this).height());
	}
	oldHeight = newHeight;
});
