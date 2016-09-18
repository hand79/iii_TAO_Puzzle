/**
 * for ViewOwnCases.jsp
 */
$(function() {
	initTable();
});

var initTable = function() {
	var controller = CurrentPageEnv.controller;
	var caseDetailFullUrl = CurrentPageEnv.caseDetailFullUrl;
	/**
	 * ****************************HIDE
	 * CANCELED*****************************************
	 */
	$('#hide-all').change(function() {
		if (this.checked)
			$('.hide-case').parent().parent().hide();
		else
			$('.hide-case').parent().parent().show();
	});
	$('.hide-case').click(function() {
		$(this).parent().parent().hide();
	});

	/** ****************************BUBBLE***************************************** */
	$('#case-status').balloon({
		offsetX : -20,
		contents : $('#caseStatusBubble').html()
	});
	$('#case-amount').balloon({
		offsetX : -20,
		contents : $('#caseAmountBubble').html()
	});

	/**
	 * ******************************VIEW CASE
	 * DETAIL***************************************
	 */
	$('.viewCaseDetail')
			.click(
					function() {
						var caseno = $(this).parent().attr('data-caseno');
						$
								.getJSON(
										controller,
										'action=ajax&what=getCaseDetail&caseno='
												+ caseno,
										function(json) {
											if (!json || json.status == 'error') {
												// may show error
												// msg/********************/
												console
														.log('ajax response error');
												return;
											}
											if (json.status == 'success') {
												var map = json.info;
												$('#caseDeatil-caseno-status')
														.text(
																caseno
																		+ ' - '
																		+ map.status);
												$('#caseDeatil-title').text(
														map.title);
												$('#caseDeatil-cpsp').text(
														'C' + map.cpsp).attr(
														'href', map.cpspUrl);
												$('#caseDeatil-time')
														.html(
																'<i class="fa fa-clock-o"></i> '
																		+ map.stime
																		+ '&nbsp;&nbsp;/&nbsp;&nbsp;<i class="fa fa-clock-o"></i> '
																		+ map.etime);
												$('#caseDeatil-qtys')
														.html(
																'<span style="color:#5bc0de">'
																		+ map.currentqty
																		+ '</span> / <span style="color:#5cb85c">'
																		+ map.minqty
																		+ '</span> / <span style="color:#d9534f">'
																		+ map.maxqty
																		+ '</span>');
												$('#caseDeatil-price')
														.text(
																map.unitprice
																		+ ' - '
																		+ map.discount
																		+ ' = '
																		+ (map.unitprice - map.discount));
												$('#caseDeatil-loc').text(
														map.loc);
												$('#caseDeatil-ship1').text(
														map.ship1);
												$('#caseDeatil-ship2').text(
														map.ship2);
												$('#caseDeatil-casedesc').text(
														map.casedesc);
												$('#detail-modal').modal();
											}
										});// end of getJSON callback
						return false;
					});// end of viewCaseDetail onclick register

	/**
	 * ******************************OPEN
	 * CASE***************************************
	 */
	$('.open-case').click(function() {
		var caseno = $(this).parent().parent().attr('data-caseno');
		var firstTd = $(this).parent().siblings().first();
		var cno = firstTd.text();
		var ctitle = firstTd.next().text();
		$('#open-case-modal .ctitle').text(ctitle);
		$('#open-case-modal .cno').text(cno);
		$('#open-case-confirmed').attr('data-caseno', caseno);
		$('#open-url-info input').val(caseDetailFullUrl + caseno);
		$('#open-case-modal').modal();
		return false;
	});
	$('#open-type').change(function() {
		if (this.selectedIndex == 1) {
			$(this).next().show();
		} else {
			$(this).next().hide();
		}
	});
	$('#open-case-confirmed').click(function() {
		if ($('#open-date').val() == '') {
			$('#open-date').trigger('focus');
			return false;
		}
		var caseno = $(this).attr('data-caseno');
		$('#ajaxMsgModal').modal({
			'backdrop' : 'static'
		});
		var data = {
			"action" : "ajax",
			"what" : "open",
			"caseno" : caseno,
			"type" : $('#open-type').val(),
			"edate" : $('#open-date').val(),
			"etime" : $('#open-time').val()
		};
		console.log(data);
		$.getJSON(controller, data, showAjaxMsg);
		return false;
	});
	/**
	 * ******************************CANCEL
	 * CASE***************************************
	 */
	$('.cancel-case').click(function() {
		var caseno = $(this).parent().parent().attr('data-caseno');
		var firstTd = $(this).parent().siblings().first();
		var cno = firstTd.text();
		var ctitle = firstTd.next().text();
		$('#cancel-case-modal .ctitle').text(ctitle);
		$('#cancel-case-modal .cno').text(cno);
		$('#cancel-case-confirmed').attr('data-caseno', caseno);
		$('#cancel-case-modal').modal();
		return false;
	});
	$('#cancel-case-confirmed').click(
			function() {
				$('#ajaxMsgModal').modal({
					'backdrop' : 'static'
				});
				var caseno = $(this).attr('data-caseno');
				$.getJSON(controller, 'action=ajax&what=cancel&caseno='
						+ caseno, showAjaxMsg);
				return false;
			});
	/**
	 * ******************************DELETE
	 * CASE***************************************
	 */
	$('.delete-case').click(function() {
		var caseno = $(this).parent().parent().attr('data-caseno');
		var firstTd = $(this).parent().siblings().first();
		var cno = firstTd.text();
		var ctitle = firstTd.next().text();
		$('#delete-case-modal .ctitle').text(ctitle);
		$('#delete-case-modal .cno').text(cno);
		$('#delete-case-confirmed').attr('data-caseno', caseno);
		$('#delete-case-modal').modal();
		return false;
	});
	$('#delete-case-confirmed').click(
			function() {
				$('#ajaxMsgModal').modal({
					'backdrop' : 'static'
				});
				var caseno = $(this).attr('data-caseno');
				$.getJSON(controller, 'action=ajax&what=delete&caseno='
						+ caseno, showAjaxMsg);
				return false;
			});
	/**
	 * ******************************OVER
	 * CASE***************************************
	 */
	$('.over-case').click(function() {
		var caseno = $(this).parent().parent().attr('data-caseno');
		var firstTd = $(this).parent().siblings().first();
		var cno = firstTd.text();
		var ctitle = firstTd.next().text();
		$('#over-case-modal .ctitle').text(ctitle);
		$('#over-case-modal .cno').text(cno);
		$('#over-case-confirmed').attr('data-caseno', caseno);
		$('#over-case-modal').modal();
		return false;
	});
	$('#over-case-confirmed').click(
			function() {
				$('#ajaxMsgModal').modal({
					'backdrop' : 'static'
				});
				var caseno = $(this).attr('data-caseno');
				$.getJSON(controller, 'action=ajax&what=over&caseno=' + caseno,
						showAjaxMsg);
				return false;
			});
	/**
	 * ****************************** PAGING
	 * ***************************************
	 */
	var callback = function(data) {
		if (data.indexOf('<') == -1) {
			window.location.href = data;
			return;
		} else {
			$('.list-zone').html(data);
			initTable();
		}
	};

	var controller = CurrentPageEnv.controller;
	$('#prev-page').click(
			function() {
				var pageNum = parseInt($(this).parent().attr('data-pageNum'));
				pageNum = (pageNum - 1) >= 1 ? (pageNum - 1) : 1;
				$.get(controller, 'action=ajax&what=changePage&pageNum='
						+ pageNum, callback);
				return false;
			});
	$('#next-page').click(
			function() {
				var pageNum = parseInt($(this).parent().attr('data-pageNum'));
				pageNum = pageNum + 1;
				$.get(controller, 'action=ajax&what=changePage&pageNum='
						+ pageNum, callback);
				return false;
			});

};

var refresh = function() {
	var controller = CurrentPageEnv.controller;
	var pageNum = parseInt($('#changePagePanel').attr('data-pageNum'));
	$.get(controller, 'action=ajax&what=changePage&pageNum=' + pageNum,
			initTable.callback);
	return false;
};

/**
 * ******************************SHOW AJAX
 * MSG***************************************
 */
var showAjaxMsg = function(json) {
	if (!json) {
		console.log('ajax failed, no json recieved.');
		return;
	}
	if (json.status == 'redirect') {
		$('#ajaxMsgModal .modal-body').html(json.resHtml);
		window.setTimeout(function() {
			window.location.href = json.resUrl;
		}, 1200);
		return;
	}
	if (json.status == 'success' || json.status == 'error') {
		$('#ajaxMsgModal .modal-body').html(json.resHtml);
		window.setTimeout(function() {
			var href = window.location.href;
			var pageNum = parseInt($('#changePagePanel').attr('data-pageNum'));
			var idx = href.indexOf('pageNum');
			if (!isNaN(pageNum)) {
				if (idx != -1) {
					href = href.substring(0, idx) + 'pageNum=' + pageNum;
				} else {
					href += '&pageNum=' + pageNum;
				}
				window.location.href = href;
			}else{
				window.location.reload();
			}
		}, 1000);
	}
};
