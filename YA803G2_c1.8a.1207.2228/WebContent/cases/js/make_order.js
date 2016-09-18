/**
 * 
 */

$(function() {
	var dispPrice = /\d+/.exec($('#dispPrice').text());
	var calPrice = function() {
		$('#orderComputedPrice').text(
				dispPrice * $('#orderSelectAmount').val()
						+ $('#shipment').val() * 1);
	};
	$('#joinCase')
			.click(
					function() {
						var callback = function(data) {

							if (data && data.status == 'success') {
								// console.log(data.info.amount);
								var amount = parseInt(data.info.amount);
								$('#currentOrderAmount').text(
										$('#currentOrderAmount+b+b').text()
												- amount);
								var opts = '';
								for (var i = 1; i <= amount; i++) {
									opts += '<option>' + i + '</option>';
								}
								$('#orderSelectAmount').html($(opts));
								var qty = $('#dispPrice+label+input').val();
								if (Number(qty) != NaN && qty > 0) {
									$('#orderSelectAmount')[0].selectedIndex = qty - 1;
								}
								$('#orderSelectAmount').change(calPrice);
								$('#shipment').change(calPrice);
								$('#orderWindow img').attr(
										'src',
										$('.carousel-inner>div>img:first')
												.attr('src'));
								calPrice();
								$('#orderPopup').modal();
							} else if (data && data.status == 'redirect') {
								window.location.href = data.resUrl;
							}

						}

						$.getJSON(OrderEnv.controller, 'action=ask&caseno='
								+ OrderEnv.caseno, callback);
					});

	$('#orderConfirm').click(function() {
		var param = {};
		param['action'] = 'order';
		param['caseno'] = OrderEnv.caseno;
		param['qty'] = $('#orderSelectAmount').val();
		param['ship'] = $('#shipment')[0].selectedIndex + 1;
		console.log(param)
		$('#msgModal').modal({
			backdrop : 'static'
		});

		$.getJSON(OrderEnv.controller, param, function(json) {
			if (json) {
				var div = $('#msgModalWindow');
				div.html(json.resHtml);
				console.log(json.resHtml);
				var sts = json.status;
				console.log(sts);
				if (sts == 'success') {
					window.setTimeout(function() {
						window.location.reload();
					}, 2000);
				} else if (sts == 'error') {
					window.setTimeout(function() {
						window.location.reload();
					}, 1500);
				} else if (sts == 'deposit') {
					window.setTimeout(function() {
						window.location.href = json.resUrl;
					}, 1500);
				} else if (sts == 'redirect') {
					window.setTimeout(function() {
						window.location.href = json.resUrl;
					}, 1500);
				}
			}
		});

	});

});