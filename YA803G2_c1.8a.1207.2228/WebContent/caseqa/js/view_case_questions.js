/**
 * 
 */

$(function() {
	var controller = CurrentPageEnv.controller;
	var oriMsg = $('#ajaxMsgModal .modal-body').html();
	var showAjaxMsg = function(json) {
		var errMsg = CurrentPageEnv.errMsg;
		if (!json) {
			$('#ajaxMsgModal .modal-body').html(errMsg);
			window.setTimeout(function() {
				$('#ajaxMsgModal').modal('hide');
			}, 1500);
			window.setTimeout(function() {
				$('#ajaxMsgModal .modal-body').html(oriMsg);
			}, 1800);
			return;
		}
		if (json.status == 'error') {
			console.log('ajax error');
			console.log('json = ' + json);
			$('#ajaxMsgModal .modal-body').html(json.resHtml);
		}
		if (json.status == 'success') {
			$('#ajaxMsgModal .modal-body').html(json.resHtml);
		}
		if (json.status == 'redirect') {
			$('#ajaxMsgModal .modal-body').html(json.resHtml);
			window.setTimeout(function() {
				window.location.href = json.resUrl;
			}, 1800);
		}
		// $('#ajaxMsgModal').modal({'backdrop':'static'});
		window.setTimeout(function() {
			var href = window.location.href;
			var idx = href.indexOf("caseno");
			if(idx != -1){
				href = href.substring(0, idx) + "caseno=" + currentCase;
				window.location.href = href;
			}else{
				window.location.href += ("&caseno=" + currentCase);
			}
		}, 1000);
	};
	
	/** ****************** ANSWER QUESTION ********************** */
	$('.answer-qa').click(function() {
		var tr = $(this).parent().parent();
		var qano = tr.attr('data-qano');
		var callback = function(json) {
			if (json.status == 'success') {
				var modal = $('#answer-modal');
				$('.q-user', modal).text($('.q-user', tr).text());
				$('.q-time', modal).text($('.q-time', tr).text());
				$('.question-content', modal).text(json.info.question);
				$('#answer-confirm').attr('data-qano', qano);
				$('#answer-modal').modal();
			} else if (json.status == 'redirect') {
				$('#ajaxMsgModal .modal-body').html(json.resHtml);
				$('#ajaxMsgModal').modal();
				window.setTimeout(function() {
					window.location.href = json.resUrl;
				}, 1800);
			} else if (json.status == 'error') {
				$('#ajaxMsgModal .modal-body').html(json.resHtml);
				$('#ajaxMsgModal').modal();
				window.setTimeout(function() {
					var href = window.location.href;
					var idx = href.indexOf("caseno");
					if(idx != -1){
						href = href.substring(0, idx) + "caseno=" + currentCase;
						window.location.href = href;
					}else{
						window.location.href += ("&caseno=" + currentCase);
					}
				}, 1800);
			} else {
				alert('connection error');
			}
		};

		$.getJSON(controller, 'action=detail&qano=' + qano, callback);
	});

	$('#answer-confirm').click(function() {
		$('#ajaxMsgModal').modal({
			'backdrop' : 'static'
		});
		var qano = $(this).attr('data-qano');
		var data = {
			"action" : 'answerQuestion',
			"qano" : qano,
			"answer" : $('#answer-modal .answer-content').val()
		};
		$.getJSON(controller, data, showAjaxMsg);
	});

	/** ****************** EDIT ANSWER ********************** */

	$('.edit-qa').click(function() {
		var tr = $(this).parent().parent();
		var qano = tr.attr('data-qano');
		var callback = function(json) {
			if (json.status == 'success') {
				console.log('json success');
				console.log(json);
				var modal = $('#edit-modal');
				console.log(modal);
				$('.q-user', modal).text($('.q-user', tr).text());
				$('.q-time', modal).text($('.q-time', tr).text());
				//
				$('.question-content', modal).text(json.info.question);
				$('.answer-content', modal).text(json.info.answer);
				$('.answer-time', modal).text(json.info.atime);
				//
				$('#edit-confirm').attr('data-qano', qano);
				$('#edit-modal').modal();
			} else if (json.status == 'redirect') {
				$('#ajaxMsgModal .modal-body').html(json.resHtml);
				$('#ajaxMsgModal').modal();
				window.setTimeout(function() {
					window.location.href = json.resUrl;
				}, 1800);
			} else if (json.status == 'error') {
				$('#ajaxMsgModal .modal-body').html(json.resHtml);
				$('#ajaxMsgModal').modal();
				window.setTimeout(function() {
					var href = window.location.href;
					var idx = href.indexOf("caseno");
					if(idx != -1){
						href = href.substring(0, idx) + "caseno=" + currentCase;
						window.location.href = href;
					}else{
						window.location.href += ("&caseno=" + currentCase);
					}
				}, 1800);
			} else {
				$('#ajaxMsgModal .modal-body').html(errMsg);
				$('#ajaxMsgModal').modal();
				window.setTimeout(function() {
					$('#ajaxMsgModal').modal('hide');
				}, 1500);
				window.setTimeout(function() {
					$('#ajaxMsgModal .modal-body').html(oriMsg);
				}, 1800);
			}
		};
		$.getJSON(controller, 'action=detail&qano=' + qano, callback);
	});

	$('#edit-confirm').click(function() {
		$('#ajaxMsgModal').modal({
			'backdrop' : 'static'
		});
		var qano = $(this).attr('data-qano');
		var data = {
			"action" : 'answerQuestion',
			"qano" : qano,
			"answer" : $('#edit-modal .answer-content').val()
		};
		$.getJSON(controller, data, showAjaxMsg);
	});
	/**************************** DELETE ******************************/
	$('.delete-qa').click(function(){
		var qano = $(this).parent().parent().attr('data-qano');
		$('#delete-confirm').attr('data-qano', qano);
		$('#delete-modal').modal();
	});
	
	$('#delete-confirm').click(function(){
		$('#ajaxMsgModal').modal({
			'backdrop' : 'static'
		});
		var data = {
			"action": "delete",
			"qano": $(this).attr('data-qano')
		};
		$.getJSON(controller, data, showAjaxMsg);
	});
	/************************************************************************/
	
	$('a[href^="#case"]').click(function(){
		currentCase = $(this).attr('data-caseno');
	});
});