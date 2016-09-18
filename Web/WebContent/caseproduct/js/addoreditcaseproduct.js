/**
 * #responseArea
 */

$(function() {
	var controller = CurrentPageEnv.controller;
	var msgarea = $('#responseArea p');
	var beforeSendHandler = function(xhr, optionObj) {
		console.log('beforeSendHandler');
		msgarea.html(CurrentPageEnv.beforeSendMsg);
	};
	var completeHandler = function(data, jquerystatus, xhr) {
		console.log('completeHandler');
		if (data) {
			var resObj = JSON.parse(data);
			if (resObj.relogin) {
				msgarea.html(CurrentPageEnv.relogin);
				window.setTimeout(function() {
					window.location.href = CurrentPageEnv.loginPage;
				}, 1500);
				return;
			} else {
				if (resObj.url) {
					msgarea.html(CurrentPageEnv.completedMsg);
					window.setTimeout(function() {
						// $('.modal-footer button').show();
						window.location.href = resObj.url;
					}, 2000);
					return;
				}
			}
		}
		errorHandler(xhr);

	};
	var errorHandler = function(xhr, jquerystatus) {
		console.log('errorHandler');
		msgarea.html(CurrentPageEnv.errMsg);
		window.setTimeout(function() {
			$('#addCP').modal('hide');
		}, 1500);
	};
	$('#submitForm').click(function() {
		$('.modal-footer button').hide();
		var formData = new FormData($('#main-contact-form')[0]);
		var value = CKEDITOR.instances['cp-desc'].getData();
		formData.append('cp-cpdesc', value);
		$.ajax({
			url : controller,
			type : 'POST',
			beforeSend : beforeSendHandler,
			success : completeHandler,
			error : errorHandler,
			// Form data
			data : formData,
			// Options to tell jQuery not to process data or worry about
			// content-type.
			cache : false,
			contentType : false,
			processData : false
		});
	});
	var color = $('#cp-name').css('border-color');
	$('#popConfirm').bind('click', function() {
		// do Validation
		var catno = parseInt($('#catList').val());
		var subcatno = parseInt($('#subcatList').val());
		var cpname = $('#cp-name').val();
		var cpprice = $('#cp-price').val();
		var idList = [ '#catList', '#subcatList', '#cp-name', '#cp-price' ];
		var errList = [ 0, 0, 0, 0 ];
		if (!(catno > 0))
			errList[0] = 1;
		if (!(subcatno > 0))
			errList[1] = 1;
		if (cpname.length == 0)
			errList[2] = 1;
		if (cpprice.length == 0)
			errList[3] = 1;
		var count = 0;
		for (var i = 0; i < idList.length; i++) {
			if (errList[i] == 1) {
				$(idList[i]).css('border-color', 'red');
				count++;
			} else {
				$(idList[i]).css('border-color', color);
			}

		}

		if (count == 0) {
			$('.modal-footer button').show();
			msgarea.html(CurrentPageEnv.oriMsg);
			$('#addCP').modal({
				'backdrop' : 'static'
			});
		} else {
			$('#scrollUp').trigger('click');
		}
		return false;
	});

	$('#catList').change(function() {
		$('#subcatList').load(controller, {
			"action" : "ajax",
			"what" : "subcatList",
			"catno" : $(this).val()
		});
	});

	$('#cancel-page').click(function() {
		$('#cancelAsk').modal();
		return false;
	});
	$('#cancel-confirm').click(function() {
		$('#cancelAsk').modal('hide');
		window.setTimeout(function() {
			window.location.href = CurrentPageEnv.cancel;
		}, 400);

	});
	$('input[type="file"]').change(preview);
	
});
var chekImgSize = function() {
	$('img.preview').each(function(idx) {
		var self = $(this);
		var changeHeight = self.height() > self.width();
		if (changeHeight) {
			if (self.height() > 220)
				self.height(220);
		} else {
			if (self.width() > 220)
				self.width(220);
		}
	});
};
var preview = function() {
	if (this.files && this.files[0]) {
		var reader = new FileReader();
		var name = $(this).attr('name');
		console.log('img .' + name);
		reader.onload = function(evt) {
			console.log('onload');
			var img = $('img.' + name);
			if (img.attr('src')) {
				img.siblings('label').text(CurrentPageEnv.preview);
			}
			img.attr('src', evt.target.result).parent().show();

			img.parent().siblings('.no-pic').hide();
			chekImgSize();
		};
		// $("#preview").show();
		reader.readAsDataURL(this.files[0]);
	}
};


