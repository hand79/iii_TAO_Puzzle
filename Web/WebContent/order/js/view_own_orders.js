$(function(){
	var controller = CurrentPageEnv.controller;
	var casesController = CurrentPageEnv.casesController;
	$('.viewOrdDetail').click(function(){
		var ordno = $(this).parent().attr('data-ordno');
		$.getJSON(controller, 'action=ajax&what=getOrdDetail&ordno=' + ordno, 
			function(json){
//				console.log(json);
				if(!json || json.status == 'error'){
					// may show error msg/********************/
					console.log('ajax response error');
					return;
				}
				if(json.status == 'success'){
					var map = json.info; 
					$('#ordDeatil-ordno').text(ordno);
					$('#ordDeatil-title').html(map.title + ' <i class="fa fa-mail-forward"></i>').attr('href', casesController + '?action=caseDetail&caseno=' + map.caseno);
					$('#ordDeatil-unitprice').text('$' + ((map.price - map.shipcost) / map.qty));
					$('#ordDeatil-qty').text(map.qty);
					$('#ordDeatil-shipcost').text('$' + map.shipcost);
					$('#ordDeatil-ship').text(map.ship);
					$('#ordDeatil-price').text('$' + map.price);
					$('#ordDeatil-ordtime').text(map.ordtime);
					$('#ordDeatil-status').text(map.status);
					$('#detail-modal').modal();
				}
			});//end of getJSON callback
		return false;
	});// end of viewOrdDetail onclick register
	
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
		window.setTimeout(function(){
			window.location.reload();
		}, 1000);
	};
	
	/************************************************************************************************/
	$('.cancel-order').click(function(){
		$('#cancel-confirm').attr('data-ordno', $(this).parent().parent().attr('data-ordno'));
		$('#cancel-modal').modal();
		return false;
	});
	$('#cancel-confirm').click(function(){
		$('#ajaxMsgModal .modal-body').html(oriHtml);
		$('#ajaxMsgModal').modal({'backdrop':'static'});
		var ordno = $(this).attr('data-ordno');
		$.getJSON(controller, 'action=ajax&what=cancel&ordno=' + ordno, showAjaxMsg);
		return false;
	});// end of cancel-confirm onclick register
	
	/************************************************************************************************/
	$('.report-conflic').click(function(){
		$('#report-confirm').attr('data-ordno', $(this).parent().parent().attr('data-ordno'));
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
	$('.recieve-product').click(function(){
		$('#recieve-confirm').attr('data-ordno', $(this).parent().parent().attr('data-ordno'));
		$('#recieve-modal').modal();
	});
	$('#recieve-confirm').click(function(){
		$('#ajaxMsgModal .modal-body').html(oriHtml);
		$('#ajaxMsgModal').modal({'backdrop':'static'});
		var ordno = $(this).attr('data-ordno');
		$.getJSON(controller, 'action=ajax&what=confirmOrder&who=buyer&ordno=' + ordno, showAjaxMsg);
		return false;
	});
	
	/************************************************************************************************/
	$('.give-rate').click(function(){
		$('#rate-confirm').attr('data-ordno', $(this).parent().parent().attr('data-ordno'));
		$('#rate-modal').modal();
	});
	var oriHtml =$('#ajaxMsgModal .modal-body').html() ;
	$('#rate-confirm').click(function(){
		var data = {
				action:'ajax', 
				what:'rate', 
				who:'creator'
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
		if(data.ratedesc.length == 0){
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