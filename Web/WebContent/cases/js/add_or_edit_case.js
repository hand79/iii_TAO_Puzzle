/**
 * for AddOrEditCase.jsp
 * 
 */

$(function() {
	$('#discount')[0].disabled = true;
	var discountPlaceholder = CurrentPageEnv.discountPlaceholder;
	var oriPlaceholder = $('#discount').attr('placeholder');
	$('#case-product')
			.on(
					'change',
					function() {
						var price = $(this.options[this.selectedIndex]).attr(
								'data-price');
						if (!price) {
							price = '-';
							$('#discount').attr('placeholder', oriPlaceholder)
									.val('')[0].disabled = true;
							$('#final-price').text('-');
							$(this).addClass('invalidText');
						} else {
							$('#discount').attr('placeholder',
									discountPlaceholder)[0].disabled = false;
							$(this).removeClass('invalidText');
						}
						$('#product-price').text(price);
						var discount = $('#discount').val();
						if (discount) {
							$('#discount').trigger('blur');
						}
					}); // end of #case-product onchange

	var errltzero = CurrentPageEnv.errltzero;
	var errNaN = CurrentPageEnv.errNaN;
	$('#discount').on('blur', function() {
		var discount = parseInt(this.value);
		console.log(discount);
		var price = $('#product-price').text() - discount;
		if (isNaN(price) || discount < 0) {
			$(this).val('').addClass('invalidText');
			$('#final-price').text(errNaN);
		} else if (price < 0) {
			$(this).val('').addClass('invalidText');
			$('#final-price').text(errltzero);
		} else {
			$('#final-price').text(price);
			$(this).removeClass('invalidText');
		}
	});

	var controller = CurrentPageEnv.controller;
	$('#county-list').on('change', function() {
		var index = this.selectedIndex;
		if (index != 0) {
			$(this).removeClass('invalidText');
			var val = $(this).children()[index].value;
			$('#town-list').load(controller, {
				"county" : val,
				"action" : "ajax",
				"what" : "town"
			});
		} else {
			$(this).addClass('invalidText');
		}
	});// end of #county-list

	$('#town-list').change(function() {
		if ($(this).val() < 1) {
			$(this).addClass('invalidText');
		} else {
			$(this).removeClass('invalidText');
		}
	});

	$('#minqty').on('blur', function() {
		var val = $(this).val();
		if (val > 999) {
			$(this).val('999');
		}
		if (!val || isNaN(val) || val < 0) {
			$(this).addClass('invalidText').val('');
		} else {
			$(this).removeClass('invalidText');
		}
		if ($('#maxqty').val() < $(this).val()) {
			$('#maxqty').val('').addClass('invalidText');
		}
	});
	$('#maxqty').on('blur', function() {
		var val = $(this).val();
		if (val > 999) {
			$(this).val('999');
		}
		if (parseInt($('#minqty').val()) <= parseInt(val)) {
			$(this).removeClass('invalidText');
		} else {
			$(this).addClass('invalidText').val('');
		}
	});

	$('#shipcost1').on('blur', function() {
		var val = $(this).val();
		if (!val || isNaN(val) || val < 0) {
			$(this).addClass('invalidText').val('');
		} else {
			$(this).removeClass('invalidText');
		}
	});

	if (CurrentPageEnv.caseno > 5000) {
		$('#case-product').change();
		$('#discount').trigger('blur');
	}else if(CurrentPageEnv.spvo == 'true'){
		$('#case-product').change();
	}

	var completeHandler = function(data, textStatus, jqXHR) {
		console.log('success');
		console.log(data);
		if (data.status == 'success') {
			$('#ajaxMsgModal .modal-body').html(data.resHtml);
			window.setTimeout(function() {
				window.location.href = data.resUrl;
			}, 1800);
		}
		if (data.status == 'redirect') {
			$('#ajaxMsgModal .modal-body').html(data.resHtml);
			window.setTimeout(function() {
				window.location.href = data.resUrl;
			}, 1800);
		}

		if (data.status == 'error') {
			$('#ajaxMsgModal .modal-body').html(data.resHtml);
			window.setTimeout(function() {
				$('#ajaxMsgModal').modal('hide');
			}, 1800);
		}
	};
	var errorHandler = function(jqXHR, textStatus, errorThrown) {
		console.log('error');
		$('#ajaxMsgModal').modal('hide');
	};
	$('#submitForm').click(function(e) {
		e.preventDefault;
		var form = $("#main-contact-form");
		var data = {};
		data.append = function(name, value) {
			this[name] = value;
		}
		var success = true;
		var title = $('input[name="title"]', form);
		if (!/\S+/.test(title.val())) {
			success = false;
			title.addClass('invalidText');
		} else {
			data.append('title', title.val());
			title.removeClass('invalidText');
		}

		if (document.getElementsByName('cpno').length > 0) {
			var cpno = $('select[name="cpno"]', form);
			if (parseInt(cpno.val()) < 4001) {
				success = false;
				cpno.addClass('invalidText');
			} else {
				data.append('cpno', cpno.val());
				cpno.removeClass('invalidText');
				data.append("cpsp", "cp");
			}
		} else {
			var spno = $('select[name="spno"]', form);
			if (parseInt(spno.val()) < 3001) {
				success = false;
				spno.addClass('invalidText');
			} else {
				data.append('spno', spno.val());
				spno.removeClass('invalidText');
				data.append("cpsp", "sp");
			}
		}

		var discount = $('input[name="discount"]', form);
		if (!discount.val()) {
			success = false;
			discount.addClass('invalidText');
		} else {
			data.append('discount', discount.val());
			discount.removeClass('invalidText');
		}

		var countylist = $('#county-list', form);
		if (!countylist.val()) {
			success = false;
			countylist.addClass('invalidText');
		} else {
			countylist.removeClass('invalidText');
		}

		var locno = $('select[name="locno"]', form);
		if (!locno.val() || locno.val() < 1) {
			success = false;
			locno.addClass('invalidText');
		} else {
			data.append('locno', locno.val());
			locno.removeClass('invalidText');
		}

		var ship1 = $('#ship1', form);
		if (!/\S+/.test(ship1.val())) {
			success = false;
			ship1.addClass('invalidText');
		} else {
			data.append('ship1', ship1.val());
			ship1.removeClass('invalidText');
		}

		var shipcost1 = $('#shipcost1', form);
		if (!shipcost1.val()) {
			success = false;
			shipcost1.addClass('invalidText');
		} else {
			data.append('shipcost1', shipcost1.val());
			shipcost1.removeClass('invalidText');
		}

		var minqty = $('#minqty', form);
		if (!minqty.val()) {
			success = false;
			minqty.addClass('invalidText');
		} else {
			data.append('minqty', minqty.val());
			minqty.removeClass('invalidText');
		}

		var maxqty = $('#maxqty', form);
		if (!maxqty.val()) {
			success = false;
			maxqty.addClass('invalidText');
		} else {
			data.append('maxqty', maxqty.val());
			maxqty.removeClass('invalidText');
		}

		var ship2 = $('#ship2', form);
		if (ship2.val()) {
			data.append('ship2', ship2.val());
		}
		var shipcost2 = $('#shipcost2', form);
		if (shipcost2.val()) {
			data.append('shipcost2', shipcost2.val());
		}
		var casedesc = $('#casedesc', form);
		if (!/\S+/.test(casedesc.val())) {
			casedesc.addClass('invalidText');
		} else {
			data.append('casedesc', casedesc.val());
			casedesc.removeClass('invalidText');
		}

		if (!success) {
			// $('#scrollUp').click();
			return false;
		}
		$('#ajaxMsgModal').modal({
			'backdrop' : 'static'
		});
		
		data.append("action", "ajax");
		data.append("what", "addOrEdit");
		data.append("caseno", CurrentPageEnv.caseno);
		// console.log(data);
		$.ajax({
			url : controller,
			type : 'POST',
			success : completeHandler,
			error : errorHandler,
			'data' : data,
			dataType : 'json'
		});
		return false;
	});
	$('form').submit(function() {
		return false;
	});

});