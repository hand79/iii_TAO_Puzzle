$(function() {
	regDelBtn();
	regEditLink();
	
	$('#sendDelRequest').on('click', function() {
		var url = ViewCaseProductEnv.controller;
		var msg = ViewCaseProductEnv.progessMsg;
		$('#delCP .modal-footer button').hide();
		$('#list-zone').load(url, {
			"action" : "del",
			"cpno" : $(this).attr('data-delno')
		}, delloadOver);
		$('#dia').html(msg);
	});
	
	$('#iknowthat').click(function(){
		var href = $(this).attr('data-link');
		window.location.href = href;
		return false;
	});
	

});//end of on ready
var delloadOver = function() {
	var delMsg = ViewCaseProductEnv.delMsg;
	$('#dia').text(delMsg);
	$('#delCP').modal('hide');
	regDelBtn();
	regEditLink();
	$('#delCP .modal-footer button').show();
};

var regDelBtn = function() {
	$('.delLink').on(
			'click',
			function() {
				var msgHeading = ViewCaseProductEnv.msgHeading;
				var askMsg = ViewCaseProductEnv.askMsg;
				var tds = $(this).parent().siblings();
				var no = tds.first().text();
				// console.log(no);
				var name = tds.first().next().text();
				// console.log(name);
				$('#delCP .modal-footer button[data-delno]').attr('data-delno',
						$(this).attr('data-cpno'));
				$('#delMsg').text(msgHeading + no + ' ' + name);
				$('#dia').text(askMsg);
				$('#delCP').modal();
				return false;
			});
};

var regEditLink = function() {
	$('.editLink').click(function() {
		if (parseInt($(this).attr('data-lock')) != 0) {
			$('#iknowthat').attr('data-cpno', $(this).attr('data-cpno')).attr('data-link', $(this).attr('href'));
			$('#notify-lock').modal();
			return false;
		}
	});
};