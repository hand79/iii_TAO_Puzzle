<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ include file="/f/frag/f_login_redirect.file" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tao.shop.model.*"%>
<%@ page import="com.tao.jimmy.location.*"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<%
    String resPath = request.getContextPath() + "/f/res";
    pageContext.setAttribute("resPath", resPath);

    String pageHome = request.getContextPath() + "/shop";
    pageContext.setAttribute("pageHome", pageHome);
%>
<%
	ShopVO shopVO = (ShopVO) request.getAttribute("shopVO");
	if(shopVO == null){
		response.sendRedirect(request.getContextPath() + "/shop/shop_member_center.jsp");
		return;
	}
%>
<%	pageContext.setAttribute("context", request.getContextPath()); %>
<%	
	CountyService countyService = new CountyService();
	Set<CountyVO> counties = countyService.findCounties();
	pageContext.setAttribute("counties", counties);
%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<style> 
    section{
		height: 1400px;
    }
 </style>
<style>
 #preview
    {
        /*width:80%;
        height:80%;*/
        width: 80px !important;
        width: 80px;
        max-width: 80px;
        height: 80px !important;
        height: 80px;
        max-height: 80px;
        border: 1px dashed #d1d0d0;
        text-align: center;

    }
</style>
<title>編輯商店| 饕飽地圖</title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<head></head>
<section>
	<div class="container">
		<div class="row">
			<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
				<c:if test="${not empty errorMsgs}">
					<font color='red'>請修正以下錯誤:
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li>${message}</li>
						</c:forEach>
					</ul>
					</font>
				</c:if>
				
				<div class="col-sm-9" style="height:1000px">
					<div class="contact-form">
	    				<h2 class="title text-center">編輯商店</h2>
	    				<div class="status alert alert-success" style="display: none"></div>
				    	<form  METHOD="post" ACTION="<%=request.getContextPath()%>/shop/shop.do" name="form1" enctype="multipart/form-data">
				            <div class="form-group col-md-12">
								<label for="title"><i class="fa fa-fw fa-check-square"></i>商店名稱</label>
				                <input type="text" name="title" class="form-control" required="required" placeholder="商店名稱" 
				                 value="<%= (shopVO.getTitle()==null)? "" : shopVO.getTitle()%>">
				            </div>
				            <div class="form-group col-md-2">
								<label for="county"><i class="fa fa-fw fa-check-square"></i>所在地區</label>
							 <select class="form-control" id="county-list" required="required" >
									<option value="">-</option>
								<c:forEach var="countyVO" items="${counties}">
									<option value="${countyVO.value}" ${requestScope.countyVO.name == countyVO.name ? 'selected':'' }>${countyVO.name}</option>
								</c:forEach>
							</select>
				            </div>
 							<div class="form-group col-md-2"> 
								<label for="town">&nbsp;</label>
				                <select class="form-control" name="locno" id="town-list" required="required">
									<c:if test="${empty towns}">
									<option value="">-</option>
									</c:if>
									<c:if test="${not empty towns }">
										<jsp:include page="/cases/Ajax_findTown.jsp"/>
									</c:if>
								</select>
 				            </div> 
				            <div class="form-group col-md-8">
								<label for="addr"><i class="fa fa-fw fa-check-square"></i>地址</label>
				                <input type="text" name="addr" class="form-control" required="required" placeholder="地址" id="address" onchange="codeAddress()"
				                 value="<%= (shopVO.getAddr()==null)? "" : shopVO.getAddr()%>">
				            </div> 
				            <div class="form-group col-md-3">
								<label for="lng"><i class="fa fa-fw fa-check-square"></i>經度</label>	
				                <input type="text" name="lng" class="form-control"required="required" id="lng"  readonly
				                 value="<%= (shopVO.getLng()==null)? "" : shopVO.getLng()%>">
				            </div> 			
							<div class="form-group col-md-3">
								<label for="lat"><i class="fa fa-fw fa-check-square"></i>緯度</label>	
				                <input type="text" name="lat" class="form-control" required="required" id="lat" readonly
				                 value="<%= (shopVO.getLat()==null)? "" : shopVO.getLat()%>">
				            </div>  
							<div class="form-group col-md-3">
								<label for="phone"><i class="fa fa-fw fa-check-square"></i>連絡電話</label>	
				                <input type="text" name="phone" class="form-control" required="required" placeholder="03-123456789"
				                 value="<%= (shopVO.getPhone()==null)? "" : shopVO.getPhone()%>">
				            </div> 			
							<div class="form-group col-md-3">
								<label for="fax">傳真</label>	
				                <input type="text" name="fax" class="form-control" placeholder="04-123456789"
				                 value="<%= (shopVO.getFax()==null)? "" : shopVO.getFax()%>">
				            </div> 
				            <div class="form-group col-md-6">
								<label for="email"><i class="fa fa-fw fa-check-square"></i>電子信箱</label>	
				                <input type="text" name="email" class="form-control" required="required" placeholder="XXX@mail.com"
				                 value="<%= (shopVO.getEmail()==null)? "" : shopVO.getEmail()%>" >
				            </div>
				            <div class="form-group col-md-4">
								<label for="pic"><i class="fa fa-fw fa-check-square"></i>商店標誌圖片</label>	
								<input type="file" name="pic" accept="image/*" class="form-control" placeholder="商店圖片 " 
								value="<%= (shopVO.getPic()==null)? "" : shopVO.getPic()%>"  onchange="preview(this)"/>
							</div>
							<div class="form-group col-md-2">
								<div id="preview" ><img src="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getPic" width="80px" height="80px"/></div>
							</div>
							<div class="form-group col-md-6">
								<label for="ship_desc">運費說明</label>	
				                <textarea  rows="5" cols="10" name="ship_desc" class="form-control" placeholder="例如：滿兩千免運，未滿則需付150元運費"><%= (shopVO.getShip_desc()==null)? "" : shopVO.getShip_desc()%></textarea>
				            </div> 
							<div class="form-group col-md-6">
								<label for="other_desc">其他說明</label>	
				                <textarea rows="5" cols="10"  name="other_desc" class="form-control" placeholder="例如：注意事項"><%= (shopVO.getOther_desc()==null)? "" : shopVO.getOther_desc()%></textarea>
				            </div> 
							<div class="form-group col-md-12">
								<label for="shop_desc">商店介紹</label>	
				                <textarea class="form-control" name="shop_desc" id="editor1"><%= (shopVO.getShop_desc()==null)? "" : shopVO.getShop_desc()%></textarea>
				            </div> 
				            <div class="form-group col-md-6">
				            	</br>
				            	<label><font color="#d43f3a"><i class="fa fa-fw fa-check-square"></i>號為必填欄位</font></label>
				            </div>
				             <div class="form-group col-md-6">
				                <input type="submit" class="btn btn-primary pull-right" value="修改">
				                <input type="hidden" name="action" value="update">
				                <input type="hidden" name=shopno value="<%= shopVO.getShopno() %>" />
				                <input type="hidden" name="memno" size="45" value="${CurrentUser.memno}" />
				                <input type="hidden" name="status" size="45" value="<%= (shopVO.getStatus()==null)? "" : shopVO.getStatus()%>" />
				                <input type="hidden" name="hits" size="45" value="<%= (shopVO.getHits()==null)? "" : shopVO.getHits()%>" />
				                <input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
				            </div>
				        </form>
	    			</div>
				</div><!--class="col-sm-9"-->
		</div>
	</div>
</section>
<jsp:include page="/f/frag/f_footer1.jsp"/>
<jsp:include page="/f/frag/f_footer2.jsp"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/b/res/js/jquery-1.11.0.js"></script>
<script>      
//載入本機圖片
function preview(file) {

    if (file.files && file.files[0]) {
        var reader = new FileReader();
        reader.onload = function (evt) {
            $("#preview").html( '<img width="80px" height="80px" src="' + evt.target.result + '" />');
        }
        $("#preview").show();
        reader.readAsDataURL(file.files[0]);
    }
}
</script> 
<script>
$('#county-list').on('change', function() {
	var index = this.selectedIndex;
	if (index != 0) {
		$(this).removeClass('invalidText');
		var val = $(this).children()[index].value;
		$('#town-list').load('<%= request.getContextPath()%>/cases/cases.do', {
			"county" : val,
			"action" : "ajax",
			"what" : "town"
		});
	}else{
		$(this).addClass('invalidText');
	}
});// end of #county-list
</script>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script>
function codeAddress(){
	var county = $("#county-list :selected").text();
	var town = $("#town-list :selected").text();
	var add = document.getElementById("address").value;
	var fullAddress = county+town+add;
		console.log(fullAddress);
	var Longitude = document.getElementById("lng");
	var Latitude = document.getElementById("lat");
	var geocoder = new google.maps.Geocoder();
	geocoder.geocode( { address: fullAddress}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			Longitude.value = results[0].geometry.location.lng();
			Latitude.value = results[0].geometry.location.lat();
		} else {
			alert("地址轉換經緯度失敗:" + status);
		}
	});
}
</script>
<ckeditor:replace replace="editor1" basePath="${context}/f/res/ckeditor/" />