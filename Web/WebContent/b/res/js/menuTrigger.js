var menuTrigger = function(arg) {
	var menu = [ "#homepageMenu", "#shopMenu", "#caseMenu", "#moneyMenu" ];
	var type = typeof arg;
	if (type == 'number') {
		$(menu[arg]).addClass('active');
	} else if (type == 'string') {
		$(arg).addClass('active');
	}
};