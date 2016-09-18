<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.member.model.*"%>
<%@ page import="com.tao.location.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
	MemberVO memberVO = (MemberVO) request.getAttribute("memberVO"); // MemberServlet.java (Controller), �s�Jreq��memberVO���� (�]�A�������X��memberVO, �]�]�A��J��ƿ��~�ɪ�memberVO����)
%>
<jsp:useBean id="locSvc" scope="page" class="com.tao.location.model.LocationService" />


<c:forEach var="locVO" items="${locSvc.all}">
<c:if test="${memberVO.locno==locVO.locno}">
    <c:set var="iCounty" value="${locVO.county}" />
    <c:set var="iTown" value="${locVO.town}" />
</c:if>
</c:forEach>


<jsp:include page="/b/frag/b_header1.jsp"/>



<title> SB Admin 2 - Bootstrap Admin Theme </title>

<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">��ƭק�</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default" style="border-width:1px; border-color : #34B2E5;">
                        <div class="panel-heading">
                                                                  ��Ʃ���
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                            <%-- ���~��C --%>
							<c:if test="${not empty errorMsgs}">
								<font color='red'>�Эץ��H�U���~:
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li>${message}</li>
									</c:forEach>
								</ul>
								</font>
							</c:if>
                            <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/memberC" name="form1">
                                <table class="table table-striped table-bordered table-hover">
	                                <tr>
										<td>�|���s��:</td>
										<td> <%=memberVO.getMemno()%></td>
									</tr>
									
									<tr>
										<td>�j�Y��:</td>
										<td> <img src="<%=request.getContextPath()%>/DBImgReader?memno=<%=memberVO.getMemno()%>" height="100px" width="100px"> </td>
									</tr>

									<tr>
										<td>�|��ID:</td>
										<td><%=memberVO.getMemid()%></td>
									</tr>
									
									<tr>
										<td>�|���m��:</td>
										<td><%=memberVO.getLname()%></td>
									</tr>
									
									<tr>
										<td>�|���W�r:</td>
										<td><%=memberVO.getFname()%></td>
									</tr>
									
									
									
									<tr>
										<td>�����Ҹ�:</td>
										<td><%=memberVO.getIdnum()%></td>
									</tr>
									
									<tr>
										<td>�|���ʧO:</td>
										<td>${(memberVO.gender == "M")? "�k��":"�k��" }</td>
									</tr>		
											
									<tr>
										<td>�ϰ�:</td>
										<td>
							                    ${memberVO.locno}�i${iCounty} - ${iTown}�j
						                    
										</td>
									</tr>	
									
									
										
									<tr>
										<td>�a�}:</td>
										<td><%=memberVO.getAddr()%></td>
									</tr>	
										
										
									<tr>
										
										<td align="center" colspan="2"><div id="map_canvas" style="width: 700px; height: 400px"></div></td>
									</tr>	
										
									<tr>
										<td>�q��:</td>
										<td><%=memberVO.getTel()%></td>
									</tr>	
										
									<tr>
										<td>Email:</td>
										<td><%=memberVO.getEmail()%></td>
									</tr>			

									<tr>
										<td>�I��:</td>
										<td><%=memberVO.getPoint()%></td>
									</tr>	
									
									<tr>
										<td>�N��:</td>
										<td><%=memberVO.getMoney()%></td>
									</tr>	
									
									<tr>
										<td>�s�i�Ѽ�:</td>
										<td><%=memberVO.getAddays()%></td>
									</tr>	
									
									<tr>
										<td>���A:</td>
										<td>
											<% if ((memberVO.getStatus()) == 0) {%> �f�֤� <%} %> 
											<% if ((memberVO.getStatus()) == 1) {%> ���`    <%} %>
											<% if ((memberVO.getStatus()) == 2) {%> ���v�� <%} %>
										
										
											<%-- <input type="TEXT" name="status" size="45" value="<%=memberVO.getStatus()%>"/>  --%> 
																									
											   <%-- <select name="status" class="statusvalue">
										         <option value="0"  <% if ((memberVO.getStatus()) == 0) { %>  selected  <%  }	%> >�f�֤�
										         <option value="1"  <% if ((memberVO.getStatus()) == 1) { %>  selected  <%  }	%> >���`
										         <option value="2"  <% if ((memberVO.getStatus()) == 2) { %>  selected  <%  }	%> >���v��
										       </select>	 --%>																							
										</td>
									</tr>	
									
									<tr>
										<td>����:</td>
										<td><%=memberVO.getType()%></td> 
									</tr>	
									
									<tr>
										<td>�Ȧ���:</td>
										<td><%=memberVO.getWithhold()%></td>
									</tr>
								
								</table>
								<br>
								<input type="hidden" name="action" value="update_back">
								<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> <!--������e�X�ק諸�ӷ��������|��,�A�e��Controller�ǳ���椧��-->
								<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">  <!--�u�Ω�:listAllMember.jsp-->
								<input type="hidden" name="memno" value="<%=memberVO.getMemno()%>">
								<input class="btn btn-success btn-lg col-lg-offset-3"  
									type="button" data-toggle="modal"	id="btnApprove" data-target="#btnApproveModal" value="�f�ֳq�L" 
									<% if ((memberVO.getStatus()) == 1) {%> disabled <%} %>
									
									>
								<input class="btn btn-danger btn-lg col-lg-offset-3" 	 
									type="button" data-toggle="modal"	id="btnCancel" data-target="#btnCancelModal" value="&nbsp; ���v &nbsp;" 
									<% if ((memberVO.getStatus()) == 2) {%> disabled <%} %>
									>
							</FORM>

                                
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
			<!-- /.row -->
            
        </div>
        <!-- /#page-wrapper -->

	<div class="modal fade" id="btnApproveModal" role="dialog"	aria-labelledby="ApproveModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="ApproveModal">�ק�|�����A</h4>
				</div>
				<form role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/back/memberC" name="form1">
					<div class="modal-body">
						<div class="form-group">
							<p id="AppModalLines">  </p> 
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
						<button type="submit" class="btn btn-success">�T�w</button>
						<input type="hidden" name="action" value="update_back">
						<input type="hidden" name="status" value="1"> 
						<input type="hidden" id="ApproveModalMemno" name="memno" value="">												
						<input type="hidden" name="requestURL"	value="<%=request.getParameter("requestURL")%>"><!--�e�X�����������|��Controller-->
			   			<input type="hidden" name="whichPage"	value="<%=request.getParameter("whichPage")%>"><!--�e�X��e�O�ĴX����Controller-->
					</div>
				</form>
			</div>
		</div>
	</div>

<div class="modal fade" id="btnCancelModal" role="dialog"	aria-labelledby="CancelModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="CancelModal">�ק�|�����A</h4>
				</div>
				<form role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/back/memberC" name="form1">
					<div class="modal-body">
						<div class="form-group">
							<p id="CanModalLines"> </p> 
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
						<button type="submit" class="btn btn-success">�T�w</button>
						<input type="hidden" name="action" value="update_back">
						<input type="hidden" name="status" value="2"> 
						<input type="hidden" id="CancelModalMemno" name="memno" value="">												
						<input type="hidden" name="requestURL"	value="<%=request.getParameter("requestURL")%>"><!--�e�X�����������|��Controller-->
			   			<input type="hidden" name="whichPage"	value="<%=request.getParameter("whichPage")%>"><!--�e�X��e�O�ĴX����Controller-->
					</div>
				</form>
			</div>
		</div>
	</div>
	
 <jsp:include page="/b/frag/b_footer1.jsp"/>
 <jsp:include page="/b/frag/b_footer2.jsp"/>
 <script>
		$(document).ready(function() {
			$("input[id^='btnApp']").click(function(){
				var memnoValue=$(this).prev().val();
				//alert(memnoValue);
				$("#ApproveModalMemno").attr("value", memnoValue);
				$("#AppModalLines").html("�T�w�n�N�|��, <font color='red'>�s��: "+memnoValue+"</font> �q�L�f�ֶ�?");
					
			});
			
			$("input[id^='btnCan']").click(function(){
				var memnoValue=$(this).prev().prev().val();
				//alert(memnoValue);
				$("#CancelModalMemno").attr("value", memnoValue);
				$("#CanModalLines").html("�T�w�n�N�|��, <font color='red'>�s��: "+memnoValue+"</font> ���v��?");
			});

		});
		
</script>

<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&language=zh_TW"></script>
<script>
var map;
var cCenter;
function initialize() {
var api="https://maps.googleapis.com/maps/api/geocode/json";
var location = "${iCounty} ${iTown} <%=memberVO.getAddr()%>";
//alert(location);

   jQuery.get(api,{"language":"zh_tw","address":location}, function(data){
   	var dataSet = data;
   	var locAry = dataSet.split("location");
   	var resultAry = locAry[1].split(":");
   	
   	var massLatAry = resultAry[2].split(",");
   	//alert(massLatAry[0]);
   	var iLat = massLatAry[0].trim();
   	//alert(iLat);
   	
   	var massLngAry = resultAry[3].split("}");
   	//alert(massLngAry[0]);
   	var iLng = massLngAry[0].trim();
   	//alert(iLng);
   	
   	iCenter = new google.maps.LatLng(iLat, iLng); 
   	oCenter = new google.maps.LatLng(25.048215, 121.517123); //�x�_�������g��,�n��25.048215, 121.517123
   	
    if (iCenter != null){
 	   cCenter = iCenter;
    }else {
 	   cCenter = oCenter;
    }
   	
    var mapOptions = {
    	    panControl: true, 
    	    zoomControl: true, 
    	    mapTypeControl: true, 
    	    scaleControl: true, 
    	    streetViewControl: true,  
    	    overviewMapControl: true, 
    	    center: cCenter, 
    	    zoom: 17 //�Y�񪺤j�p�A0-18�A0�̤p�A18�̤j
    	  };
    	  
    	var map = new google.maps.Map(document.getElementById('map_canvas'),
    	      mapOptions);
    	      
    	//����marker
    	var marker = new google.maps.Marker({
    	    map: map,
    	    position: map.getCenter()
    	  });
    	var infowindow = new google.maps.InfoWindow();
    	  infowindow.setContent('<b>�|�� <%=memberVO.getLname()%><%=memberVO.getFname()%> ���a�}<b>');
    	  google.maps.event.addListener(marker, 'click', function() {
    	      infowindow.open(map, marker);
    	  });
   	
   },"text");


}

google.maps.event.addDomListener(window, 'load', initialize);
</script>
