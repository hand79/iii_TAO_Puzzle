/**
 * 
 */
var oriMsg;
$(function() {
	$('#submit-ask').click(function() {
		var content = $('#askForm textarea').val();
		console.log(content);
		console.log(/\S+/.test(content));
		if( /\S+/.test(content)){
			$('#sureToAsk-modal').modal();
		}
		return false;
	});
	$('#askConfirmed').click(
			function() {
				var formData = {};
				formData["action"] = "askQuestion";
				formData["question"] = $('#askForm textarea').val();
				formData["caseno"] = $('#askForm input[name="caseno"]').val();
				formData["action"] = "askQuestion";
				console.log(formData);
				$('#sureToAsk-modal .modal-footer button').hide();
				$('resArea').children('p').text = CurrentPageEnv.process;
				$.ajax({
					type : "POST",
					url : CurrentPageEnv.controller,
					data : formData,
					// processData: false,
					success : function(data) {
						console.log(data);
						var resObj = JSON.parse(data);
						oriMsg = $('#resArea').html();
						$('#resArea').html(resObj.resHtml);
						if (resObj.status != 'redirect') {
							window.setTimeout(function() {
								$('#sureToAsk-modal').modal('hide');
							}, 1500);
							window.setTimeout(resetArea, 1700);
						} else {
							window.setTimeout(function() {
								window.location.href = resObj.resLink;
							}, 1500);
						}
					}
				});
				return false;
			});

});
var resetArea = function(){
	$('#resArea').html(oriMsg);
	$('#sureToAsk-modal .modal-footer button')
	.show();
	$('#askForm textarea').val('');
};