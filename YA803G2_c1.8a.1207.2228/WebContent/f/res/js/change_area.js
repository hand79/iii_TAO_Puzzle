/**
 * 
 */
$(function() {
	$('a.LocationArea').click(function(){
		console.log("changeArea clicked");
		var areaText = $(this).text();
		var area = $(this).attr('data-ChangeArea');
		$.get(AreaEnv.controller, 'action=changeArea&area=' + area, function(data){
			if(data == 'success')
			$('#areaShowing').text(areaText);
		});
		return false;
	});
});