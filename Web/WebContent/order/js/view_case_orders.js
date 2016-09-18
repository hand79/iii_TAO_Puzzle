/**
 * 
 */

$(function(){
	var controller = CurrentPageEnv.controller;
	var caseno;
	var showAjaxMsg = function(json){
		var errMsg = CurrentPageEnv.errMsg;
		if(!json){
			console.log('no json');
			return;
		}
		if(json.status == 'error'){
			console.log('ajax error');
			console.log('json = ' + json);
			$('#ajaxMsgModal .modal-body').html(errMsg);
		};
		if(json.status == 'success'){
			$('#ajaxMsgModal .modal-body').html(json.resHtml);
		};
//		$('#ajaxMsgModal').modal({'backdrop':'static'});
		var url = controller + '?action=viewCaseOrders';
		if(caseno){
			url = url +  '&caseno=' + caseno; 
		}
		window.setTimeout(function(){
			window.location.href=url;
		}, 1000);
	};
	/************************************************************************************************/
	$('.report-conflic').click(function(){
		var tr = $(this).parent().parent();
		caseno = tr.attr('data-caseno');
		$('#report-confirm').attr('data-ordno', tr.attr('data-ordno'));
		$('#report-modal').modal();
		return false;
	});
	$('#report-confirm').click(function(){
		$('#report-insist').attr('data-ordno', $(this).attr('data-ordno'));
		$('#report-dbcheck-modal').modal();
		return false;
	});
	$('#report-insist').click(function(){
		$('#ajaxMsgModal .modal-body').html(oriHtml);
		$('#ajaxMsgModal').modal({'backdrop':'static'});
		var ordno = $(this).attr('data-ordno');
		$.getJSON(controller, 'action=ajax&what=report&ordno=' + ordno, showAjaxMsg);
		return false;
	});
	
	/************************************************************************************************/
	$('.send-product').click(function(){
		var tr = $(this).parent().parent();
		caseno = tr.attr('data-caseno');
		$('#send-confirm').attr('data-ordno', tr.attr('data-ordno'));
		$('#send-modal').modal();
	});
	$('#send-confirm').click(function(){
		$('#ajaxMsgModal .modal-body').html(oriHtml);
		$('#ajaxMsgModal').modal({'backdrop':'static'});
		var ordno = $(this).attr('data-ordno');
		$.getJSON(controller, 'action=ajax&what=confirmOrder&who=creator&ordno=' + ordno, showAjaxMsg);
		return false;
	});
	
	/************************************************************************************************/
	$('.give-rate').click(function(){
		var tr = $(this).parent().parent();
		caseno = tr.attr('data-caseno');
		$('#rate-confirm').attr('data-ordno', tr.attr('data-ordno'));
		
		$('#rate-modal-mem').html(tr.children('.mem-info').html());
		
		$('#rate-modal').modal();
	});
	var oriHtml =$('#ajaxMsgModal .modal-body').html() ;
	$('#rate-confirm').click(function(){
		var data = {
				action:'ajax', 
				what:'rate', 
				who:'buyer'
			};
		data.rate = -1;
		var radio = $('#rate-radio-select input[type="radio"]');
		for(var i = 0; i<radio.length; i++){
			if(radio[i].checked){data.rate = radio[i].value;}
		}
		if(data.rate == -1){
			$('#ajaxMsgModal .modal-body').html(CurrentPageEnv.rateErr);
			$('#ajaxMsgModal').modal();
			window.setTimeout(function(){$('#ajaxMsgModal').modal('hide');}, 800);
			return false;
		}
		data.ratedesc = $('#ratedesc').val();
		if(/\s+/.test(data.ratedesc) || data.ratedesc.length == 0){
			$('#ajaxMsgModal .modal-body').html(CurrentPageEnv.descErr);
			$('#ajaxMsgModal').modal();
			window.setTimeout(function(){$('#ajaxMsgModal').modal('hide');}, 800);
			return false;
		}
		data.ordno = $(this).attr('data-ordno');
		$('#ajaxMsgModal .modal-body').html(oriHtml);
		$('#ajaxMsgModal').modal({'backdrop':'static'});
		$.ajax({
		  dataType: "json",
		  url: controller,
		  data: data,
		  success: showAjaxMsg,
		  method:'POST'
		});
		return false;
	});
	
	/************************************************************************************************/
	$('#rate-modal').on('hidden.bs.modal', function(){
		$('#ratedesc').val('');
		$('#rate-radio-select input').each(function(){
			this.checked = false;
		});
	});	
	
	
	
	
	
	
	
	
	
	
	
	
});