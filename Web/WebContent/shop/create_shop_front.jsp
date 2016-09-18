<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tao.jimmy.location.*"%>
<%@ page import="com.tao.shop.model.*"%>
<%@ include file="/f/frag/f_login_redirect.file" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<%
    String resPath = request.getContextPath() + "/f/res";
    pageContext.setAttribute("resPath", resPath);

    String pageHome = request.getContextPath() + "/shop";
    pageContext.setAttribute("pageHome", pageHome);
%>
<%
	ShopVO shopVO = (ShopVO) request.getAttribute("shopVO");
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
<title>�إ߰ө� | Ź���a��</title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<head></head>
<section>
	<div class="container">
		<div class="row">
			<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
				<c:if test="${not empty errorMsgs}">
					<font color='red'>�Эץ��H�U���~:
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li>${message}</li>
						</c:forEach>
					</ul>
					</font>
				</c:if>
				
				<div class="col-sm-9" style="height:1000px">
					<div class="contact-form">
	    				<h2 class="title text-center">�إ߰ө�</h2>
	    				<div class="status alert alert-success" style="display: none"></div>
				    	<form  METHOD="post" ACTION="<%=request.getContextPath()%>/shop/shop.do" name="form1" enctype="multipart/form-data">
				            <div class="form-group col-md-12">
								<label for="title"><i class="fa fa-fw fa-check-square"></i>�ө��W��</label>
				                <input type="text" name="title" class="form-control" required="required" placeholder="�ө��W��" 
				                value="<%= (shopVO==null)? "" : shopVO.getTitle()%>">
				            </div>
				            <div class="form-group col-md-2">
								<label for="county"><i class="fa fa-fw fa-check-square"></i>�Ҧb�a��</label>
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
								<label for="addr"><i class="fa fa-fw fa-check-square"></i>�a�}</label>
				                <input type="text" name="addr" class="form-control" required="required" placeholder="�a�}" id="address" onchange="codeAddress()"
				                value="<%= (shopVO==null)? "" : shopVO.getAddr()%>">
				            </div> 
				            <div class="form-group col-md-3">
								<label for="lng"><i class="fa fa-fw fa-check-square"></i>�g��</label>	
				                <input type="text" name="lng" class="form-control"required="required" id="lng" readonly
				                value="<%= (shopVO==null)? "0.0" : shopVO.getLng()%>">
				            </div> 			
							<div class="form-group col-md-3">
								<label for="lat"><i class="fa fa-fw fa-check-square"></i>�n��</label>	
				                <input type="text" name="lat" class="form-control" required="required" id="lat" readonly
				                value="<%= (shopVO==null)? "0.0" : shopVO.getLat()%>">
				            </div>  
							<div class="form-group col-md-3">
								<label for="phone"><i class="fa fa-fw fa-check-square"></i>�s���q��</label>	
				                <input type="text" name="phone" class="form-control" required="required" placeholder="03-123456789"
				                value="<%= (shopVO==null)? "" : shopVO.getPhone()%>">
				            </div> 			
							<div class="form-group col-md-3">
								<label for="fax">�ǯu</label>	
				                <input type="text" name="fax" class="form-control" placeholder="04-123456789"
				                value="<%= (shopVO==null)? "" : shopVO.getFax()%>">
				            </div> 
				            <div class="form-group col-md-6">
								<label for="email"><i class="fa fa-fw fa-check-square"></i>�q�l�H�c</label>	
				                <input type="text" name="email" class="form-control" required="required" placeholder="XXX@mail.com"
				                value="<%= (shopVO==null)? "" : shopVO.getEmail()%>" >
				            </div>
				            <div class="form-group col-md-4">
								<label for="pic"><i class="fa fa-fw fa-check-square"></i>�ө��лx�Ϥ�</label>	
								<input type="file" name="pic" accept="image/*" class="form-control" placeholder="�ө��Ϥ� " required="required"
								value="<%= (shopVO==null)? "" : shopVO.getPic()%>"  onchange="preview(this)"/>
							</div>
							<div class="form-group col-md-2">
								<div id="preview" ></div>
							</div>
							<div class="form-group col-md-6">
								<label for="ship_desc">�B�O����</label>	
				                <textarea rows="5" cols="10"  name="ship_desc" class="form-control" placeholder="�Ҧp�G����d�K�B�A�����h�ݥI150���B�O"><%= (shopVO==null)? "" : shopVO.getShip_desc()%></textarea>
				            </div> 
							<div class="form-group col-md-6">
								<label for="other_desc">��L����</label>	
				                <textarea rows="5" cols="10" name="other_desc" class="form-control" placeholder="�Ҧp�G�`�N�ƶ�"><%= ( shopVO==null)? "" : shopVO.getOther_desc()%></textarea>
				            </div> 
							<div class="form-group col-md-12">
								<label for="shop_desc">�ө�����</label>	
				                <textarea rows="10" cols="40" id="editor1" name="shop_desc" ><%= (shopVO==null)? "" : shopVO.getShop_desc()%></textarea>
				            </div> 
				            <div class="form-group col-md-6">
				            	<br/>
				            	<label><font color="#d43f3a"><i class="fa fa-fw fa-check-square"></i>�����������</font></label>
				            </div>
				             <div class="form-group col-md-6">
				                <div class="pull-right">
				                <span style="margin-top: 16px; margin-right: 20px;" class="btn btn-default" id="createMagic" ><i class="fa fa-fw fa-magic fa-lg"></i>���_�p���s</span>
				                <a style="margin-top: 16px; margin-right: 20px;" class="btn btn-default" href="<%=request.getContextPath() %>/shop/shop_member_center.jsp">��^</a>
				                <input type="submit" class="btn btn-primary" value="�إ�">
				                </div>
				                <input type="hidden" name="action" value="insert">
				             	<input type="hidden" name="hits" size="45" value="<%= (shopVO==null)? "0" : shopVO.getHits()%>" />
				                <input type="hidden" name="memno" size="45" value="${CurrentUser.memno}" />
				                <input type="hidden" name="status" size="45" value="0" />
				            </div>
				        </form>
	    			</div>
				</div><!--class="col-sm-9"-->
		</div>
	</div>
</section>
<jsp:include page="/f/frag/f_footer1.jsp"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/b/res/js/jquery-1.11.0.js"></script>
<script>      
//���J�����Ϥ�
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
			alert("�a�}�ഫ�g�n�ץ���:" + status);
		}
	});
}

	$('#createMagic').click(function(){
		$( "input[name='title']" ).val( "�p����" );
		$( "input[name='addr']" ).val( "�_�����G�q157��" );
		$( "input[name='phone']" ).val( "0930-042497" );
		$( "input[name='fax']" ).val( "06-65531012" );
		$( "input[name='email']" ).val( "chingchingfood@gmail.com" );
		$( "textarea[name='ship_desc']" ).val( "��1500���K�B�O" );
		$( "textarea[name='other_desc']" ).val( "1.�I�ڸ�T�Цܡu�q��v���d�ߡA�è̷ӫ��w��������I�ڡA�H�K�����ǳƥX�f�Ʃy�]�I�ڴ����G�Щ��f��4�ѫe�I�ڡA�Y��f��Z���U��鬰4�Ѥ�(�t4��)�A�ݩ�U���駹���I�ڡ^�C"+
				"2.�Y�S���̳W�w�ɶ��I�ڡA�������v����f��C"+
				"3.�p�פJ�ڶ����~�]�t�h�סB�ֶ׵��I�ڥ��ѱ��p�^�A�����|�P�z�s���ڶ��Ʃy��G�^" );
		$( "select[id='county-list']" ).val( "204-240" ).change();
		$( "select[id='town-list']" ).val( "208" );
		codeAddress();
	});

</script>

<ckeditor:replace replace="editor1" basePath="${context}/f/res/ckeditor/" />
<script>
CKEDITOR.config.height="600px";
CKEDITOR.config.resize_enabled =false;
</script>
<jsp:include page="/f/frag/f_footer2.jsp"/>