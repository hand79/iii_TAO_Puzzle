/**
 * 
 */

$(function() {
	var controller = OrderEnv.controller;
	var msg = OrderEnv.msg;
	var lang = OrderEnv.lang;
	var color = $('#MsgTitle').css('color');
	var disable = {
		bLengthChange : false,
		// bPaginate : false,
		bInfo : false,
		bFilter : false,
		"language" : lang
	};
	var args = [ {
		"language" : lang
	}, disable, disable ];
	var activeTable = function(arg) {
		$('#order-table').dataTable(arg);
		$('#order-table').on('draw.dt', function(){
			registerLink(controller);
		});
	};
	activeTable(args[1]);
	var callback = function(url, status, xhr) {
		// var resText = $('#ajaxMsg').text();
		// console.log(resText);
		// var resObj = JSON.parse(resText);
		var resObj = ajaxMsg;
		var target = $('#MsgTitle');
		target.text(msg[resObj.msg]);
		if (resObj.msg != 1) {
			target.css('color', color);
		} else {
			target.css('color', 'red');
		}
		activeTable(args[resObj.msg]);
		registerLink(controller);
	};

	$('#listAll').click(function() {
		$('#resultDiv').load(controller, {
			"action" : "ajax",
			"what" : "getAll"
		}, callback);
		return false;
	});// end of #listAll

	$('#doSearch').click(function() {
		var formData = {};

		var inputs = $('#queryForm input');
		var selects = $('#queryForm select');

		for (var i = 0; i < inputs.length; i++) {
			var obj = inputs[i];
			if (obj.name && obj.value)
				formData[String(obj.name)] = obj.value;
		}
		for (var j = 0; j < selects.length; j++) {
			var obj = selects[j];
			if (obj.name && obj.value)
				formData[String(obj.name)] = obj.value;
		}
		formData['action'] = 'compositeQuery';
		console.log('FormData Before Submit:' + JSON.stringify(formData));
		$('#resultDiv').load(controller, formData, callback);
		return false;
	});// end of #doSearch

	$('#queryForm').on('submit', function() {
		return false;
	});// disable form submit

	$("input[name=osday]").datepicker(
			{
				changeYear : true,
				changeMonth : true,
				numberOfMonths : 1,
				dateFormat : 'yy-mm-dd',
				onClose : function(selectedDate) {
					$("input[name=oeday]").datepicker("option", "minDate",
							selectedDate);
				}
			});
	$("input[name=oeday]").datepicker(
			{
				changeYear : true,
				changeMonth : true,
				numberOfMonths : 1,
				dateFormat : 'yy-mm-dd',
				onClose : function(selectedDate) {
					console.log(selectedDate);
					$("input[name=osday]").datepicker("option", "maxDate",
							selectedDate);
				}
			});
	$("#finishOrder").click(function() {
		$("#finishOrderModal").modal();
	});
	$('#finish-order-confirm').click(function() {
		$('#ajaxMsgModal').modal({
			'backdrop' : 'static'
		});
		$.getJSON(controller, 'action=triggerFinishOrder', function(json) {
			if (json.status == 'success') {
				$('#ajaxMsgModal .modal-body').html(json.resHtml);
				$('#doSearch').click();
				window.setTimeout(function() {
					$('#ajaxMsgModal').modal('hide');
				}, 1000);
			}
			if (json.status == 'error') {
				$('#ajaxMsgModal .modal-body').html(json.resHtml);
				$('#doSearch').click();
				window.setTimeout(function() {
					$('#ajaxMsgModal').modal('hide');
				}, 1000);
			}
		});
		return false;
	});
	$('#ajaxMsgModal').bind('hidden.bs.modal', function() {
		if ($('#conflic-modal').hasClass('in')) {
			$('#conflic-modal').modal('hide');
		}
		if ($("#finishOrderModal").hasClass('in')) {
			$("#finishOrderModal").modal('hide');
		}
		$('#ajaxMsgModal .modal-body').html(oriHtml);
	});

});
var oriHtml = $('#ajaxMsgModal .modal-body').html();
function registerLink(controller) {
	$('.ordno').click(
			function() {
				console.log("onclick");
				var ordno = $(this).text();
				$.getJSON(controller, 'action=ajax&what=getOrdDetail&ordno='
						+ ordno, function(json) {
					// console.log(json);
					if (!json || json.status == 'error') {
						// may show error msg/********************/
						console.log('ajax response error');
						return;
					}
					if (json.status == 'success') {
						var map = json.info;
						$('#ordDeatil-ordno').text(ordno);
						$('#ordDeatil-title').html(
								map.title
										+ ' <i class="fa fa-fw fa-share"></i>')
								.attr(
										'href',
										OrderEnv.frontController
												+ '?action=caseDetail&caseno='
												+ map.caseno);
						$('#ordDeatil-unitprice').text(
								'$' + ((map.price - map.shipcost) / map.qty));
						$('#ordDeatil-qty').text(map.qty);
						$('#ordDeatil-shipcost').text('$' + map.shipcost);
						$('#ordDeatil-ship').text(map.ship);
						$('#ordDeatil-price').text('$' + map.price);
						$('#ordDeatil-ordtime').text(map.ordtime);
						$('#ordDeatil-status').text(map.status);
						$('#detail-modal').modal();
					}
				});// end of getJSON callback

				return false;
			});
	$('.conflic').click(
			function() {
				$('#conflic-modal').modal();
				$('#modify-confirm').attr(
						'data-ordno',
						$(this).parent().parent().children().first().children()
								.text());
				return false;
			});
	$('#modify-confirm').click(
			function() {
				$('#ajaxMsgModal').modal({
					'backdrop' : 'static'
				});
				var ordno = $(this).attr('data-ordno');
				var status = $('#conflic-modal .status').val();
				$.getJSON(controller, 'action=handleConflic&ordno=' + ordno
						+ "&status=" + status, function(json) {
					if (json.status == 'success') {
						$('#ajaxMsgModal .modal-body').html(json.resHtml);
						$('#doSearch').click();
						window.setTimeout(function() {
							$('#ajaxMsgModal').modal('hide');
						}, 1000);
					}
					if (json.status == 'error') {
						$('#ajaxMsgModal .modal-body').html(json.resHtml);
						$('#doSearch').click();
						window.setTimeout(function() {
							$('#ajaxMsgModal').modal('hide');
						}, 1000);
					}
				});
				return false;
			});
}
