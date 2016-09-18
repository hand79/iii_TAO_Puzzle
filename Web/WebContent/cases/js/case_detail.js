/**
 * 
 */
$(function() {
	var controller = CurrentPageEnv.caseController;
	$(".carousel-inner > div > img").click(function() {
		var src = $(this).attr('src');
		var target = $('#currentImg');
		if (target.attr('src') != src) {
			target.fadeOut(300, function() {
				$(this).attr('src', src).fadeIn(300);
			});
		}
		return false;
	});
	/**
	 * ******************************DISABLE
	 * BUTTON***************************************
	 */
	var disableBtn = function(btnId) {
		$('#' + btnId).attr('id', '').attr('data-caseno', '').attr('disabled',
				true)
	};
	/**
	 * ******************************SHOW AJAX
	 * MSG***************************************
	 */
	var showAjaxMsg = function(json) {
		if (!json) {
			console.log('ajax failed, no json recieved.');
			$('#ajaxMsgModal').modal('hide');
			return;
		}
		if (json.status == 'redirect') {
			$('#ajaxMsgModal .modal-body').html(json.resHtml);
			window.setTimeout(function() {
				window.location.href = json.resUrl;
			}, 1400);
			return;
		}
		if (json.status == 'success' || json.status == 'error') {
			if (json.status == 'success') {
				disableBtn(btnId);
			}

			$('#ajaxMsgModal .modal-body').html(json.resHtml);
			window.setTimeout(function() {
				$('#ajaxMsgModal').modal('hide');
			}, 1400);
		}
	};

	/**
	 * ******************************ADD TO
	 * FOLLOW***************************************
	 */
	$('#addToFollow').click(
			function() {
				$('#ajaxMsgModal').modal({
					backdrop : 'static'
				});
				var caseno = $(this).attr('data-caseno');
				btnId = $(this).attr('id');
				$.getJSON(controller, 'action=ajax&what=addToWishlist&caseno='
						+ caseno, showAjaxMsg);
				return false;
			});
	/**
	 * ******************************REPORT
	 * IT***************************************
	 */
	$('#reportCase').click(function() {
		var caseno = $(this).attr('data-caseno');
		$('#report-confirm').attr('data-caseno', caseno);
		btnId = $(this).attr('id');
		$('#report-modal').modal();
		return false;
	});
	$('#report-confirm').click(
			function() {
				$('#ajaxMsgModal').modal({
					backdrop : 'static'
				});
				var caseno = $(this).attr('data-caseno');
				var reason = $('#report-content').val();
				$.getJSON(controller, 'action=ajax&what=report&caseno='
						+ caseno + "&reason=" + reason, showAjaxMsg);
				return false;
			});
	$('#report-modal').bind('hidden.bs.modal', function() {
		$('#report-content').val('');
	});
	$('#ajaxMsgModal').bind('hidden.bs.modal', function() {
		if ($('#report-modal').hasClass('in')) {
			$('#report-modal').modal('hide');
		}
	});

});
var btnId = '';