$(document).ready(function(){
		
	$(".btn[class='btn btn-link btn-xs']").click(function(){
		
		$("#recordpic").attr("src","images/pic04.jpg");
			
	});
	
	$("#upload").change().uploadPreview({ Img: "ImgPr", Width: 565, Height: 300, ImgType: ["gif", "jpeg", "jpg", "bmp", "png"], Callback: function () { }});
});