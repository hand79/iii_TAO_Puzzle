/**
 * 
 */
$(function() {
	var controller = CasesEnv.controller;
	var msg = CasesEnv.msg;
	var errMeg = CasesEnv.e;
	var lang = CasesEnv.lang;
	var color = $('#MsgTitle').css('color');
	var disable = {
		bLengthChange : false,
		bPaginate : false,
		bInfo : false,
		bFilter : false
	};
	var args = [ {
		"language" : lang
	}, disable, disable ];

	var activeTable = function(arg) {
		$('#cases-table').dataTable(arg);
		$('#cases-table').on('draw.dt', function(){
			registerLinks(controller);
		});
	};
	
	activeTable(args[1]);
	
	var callback = function(url, status, xhr) {
		var resObj = ajaxMsg;
		var target = $('#MsgTitle');
		target.text(msg[resObj.msg]);
		if (resObj.msg != 1) {
			target.css('color', color);
		} else {
			target.css('color', 'red');
		}
		activeTable(args[resObj.msg]);
		registerLinks(controller);
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
		console.log('formData:' + JSON.stringify(formData));
		$('#resultDiv').load(controller, formData, callback);
		return false;
	});// end of #doSearch

	$('#queryForm').on('submit', function() {
		return false;
	});// disable form submit

	$('#county-list').on('change', function() {
		var index = this.selectedIndex;
		if (index != 0) {
			var val = $(this).children()[index].value;
			$('#town-list').load(controller, {
				"county" : val,
				"action" : "ajax",
				"what" : "town"
			});
		}
	});// end of #county-list

	$("input[name=stimefrom]").datepicker(
			{
				changeYear : true,
				changeMonth : true,
				numberOfMonths : 1,
				dateFormat : 'yy-mm-dd',
				onClose : function(selectedDate) {
					$("input[name=stimeto]").datepicker("option", "minDate",
							selectedDate);
				}
			});

	$("input[name=stimeto]").datepicker(
			{
				changeYear : true,
				changeMonth : true,
				numberOfMonths : 1,
				dateFormat : 'yy-mm-dd',
				onClose : function(selectedDate) {
					console.log(selectedDate);
					$("input[name=stimefrom]").datepicker("option", "maxDate",
							selectedDate);
				}
			});

	$("input[name=etimefrom]").datepicker(
			{
				changeYear : true,
				changeMonth : true,
				numberOfMonths : 1,
				dateFormat : 'yy-mm-dd',
				onClose : function(selectedDate) {
					$("input[name=etimeto]").datepicker("option", "minDate",
							selectedDate);
				}
			});
	$("input[name=etimeto]").datepicker(
			{
				changeYear : true,
				changeMonth : true,
				numberOfMonths : 1,
				dateFormat : 'yy-mm-dd',
				onClose : function(selectedDate) {
					console.log(selectedDate);
					$("input[name=etimefrom]").datepicker("option", "maxDate",
							selectedDate);
				}
			});
	$('button[type=reset]').click(function(){
		$('#town-list').html('<option value="">-</option>');
	});
	
	var oriHtml = $('#ajaxMsgModal .modal-body').html();
	var isCancel = false;
	var callback2 = function(json){
		if(!json){
			$('#ajaxMsgModal .modal-body').html(json.resHtml);
			window.setTimeout(function(){
				$('#doSearch').click();
				$('#ajaxMsgModal').modal('hide');
			}, 1000);
			return;
		}
		if(json.status == 'success' || json.status=='error'){
			$('#ajaxMsgModal .modal-body').html(json.resHtml);
			window.setTimeout(function(){
				$('#ajaxMsgModal').modal('hide');
				$('#doSearch').click();
			}, 1000);
		}
	};
	
	$('#ajaxMsgModal').bind('hidden.bs.modal', function(){
		if($('#cancel-modal').hasClass('in'))
			$('#cancel-modal').modal('hide');
		else if($('#delete-modal').hasClass('in'))
			$('#delete-modal').modal('hide');
		else if($('#overCaseModal').hasClass('in'))
			$('#overCaseModal').modal('hide');
		$('.modal-body',$(this)).html(oriHtml);
	});
	
	
	
	$('#cancel-confirm').click(function(){
		$('#ajaxMsgModal').modal({backdrop: 'static'});
//		isCancel = true;
		var caseno = $(this).attr('data-caseno');
		$.getJSON(controller, 'action=cancel&caseno=' + caseno, callback2);
		return false;
	});
	$('#delete-confirm').click(function(){
		$('#ajaxMsgModal').modal({backdrop: 'static'});
//		isCancel = false;
		var caseno = $(this).attr('data-caseno');
		$.getJSON(controller, 'action=delete&caseno=' + caseno, callback2);
		return false;
	});
	
	
	$('#overCase').click(function(){
		$('#overCaseModal').modal();
		return false;
	})
	$('#over-case-confirm').click(function(){
		$('#ajaxMsgModal').modal({backdrop: 'static'});
		$.getJSON(controller, 'action=triggerOverCase', callback2);
		return false;
	});

});
var isCancel = false;

var registerLinks = function(controller){
	$('.cancel-case').click(function(){
		$('#cancel-confirm').attr('data-caseno', $(this).parent().attr('data-caseno'));
		$('#cancel-modal').modal();
		return false;
	});
	
	$('.delete-case').click(function(){
		$('#delete-confirm').attr('data-caseno', $(this).parent().attr('data-caseno'));
		$('#delete-modal').modal();
		return false;
	});
};