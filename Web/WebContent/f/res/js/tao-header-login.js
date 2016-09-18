/**
 * 
 */

$(function(){
	var modal = $('#loginModal');
	var loginController = LoginEnv.controller;
	$('#i-want-to-login').click(function(){
		modal.modal();
		return false;
	});
	
	$('#login-form').submit(function(){
		var data = {action:'ajax'};
		data.name = $('input[name="username"]', modal).val();
		data.pwd = $('input[name="password"]', modal).val();
		console.log(data);
		$.post(loginController, data, function(data){
			var json = $.parseJSON(data);
			$('.msgArea', modal).hide();
			if (!json){
				$('.msgArea', modal).show();
			}
			if(json.status == 'success'){
//				modal.modal('hide'); 
//				$('#header-middle-nav-zone').load(loginController, 'action=refreshHeader', function(){$('#loadingModal').hide().modal('hide');});
				$('#loadingModal').modal({backdrop:'static'});
				window.setTimeout(function(){window.location.reload();}, 600);
			}
			if(json.status == 'error'){
				$('.msgArea', modal).show().html(json.resHtml);
			}
		});
		return false;
	});
	$('#i-want-to-logout').click(function(e){
		e.preventDefault();
		window.location.href = loginController + "?action=logout&page=" + LoginEnv.currentPage;
		return false;
	});
	
});