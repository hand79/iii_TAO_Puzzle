<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ include file="/f/frag/f_login_redirect.file" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.member.model.*"%>
<%@ page import="com.tao.location.model.*"%>
<%@ page import="java.util.*" %>
<%
	MemberVO memberVO = (MemberVO) session.getAttribute("CurrentUser");
	LocationService dao = new LocationService();
	List<String[]> list = dao.getUniqCounty();
	pageContext.setAttribute("list",list);
%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<title> 修改會員資料 </title>
<script>  
var xhr = null;

function createXHR(){	
	if( window.XMLHttpRequest ){	
	  xhr = new XMLHttpRequest();
	}else if( window.ActiveXObject ){		
	  xhr = new ActiveXObject("Microsoft.XMLHTTP");
	} else
	  xhr = null;  
	return xhr;
}	
	
function getTownUpdated(){	
	xhr = createXHR();
	if( xhr == null ){
	   alert("Does not support Ajax....");
	return;
	}		   
	 
	xhr.onreadystatechange =function(){
	   if( xhr.readyState == 4){
		    if( xhr.status == 200){
		    	var townArry = JSON.parse(xhr.responseText);
		    	document.getElementById("townSelectList").options.length = 0;
		    	
		    	for (var i = 0; i < townArry.townsList.length; i++) {
		    		textNode = document.createTextNode(townArry.townsList[i].town);
		    		option = document.createElement("option");		 		
		    	 	option.appendChild(textNode);
		    	 	option.setAttribute("value", townArry.townsList[i].locno);
		    	 	document.getElementById("townSelectList").appendChild(option);
		    	}		
			}else{
			  alert( xhr.status );
			}
		}
	}	 
	 <%-- var url= "<%=request.getContextPath()%>/locationC?action=gettown&value=123"; --%>
	var url= "<%=request.getContextPath()%>/member/front/GetTownList.jsp?countyrange="+ document.getElementById("countySelectList").value;
	xhr.open("GET",url,true);
	xhr.send(null);
}

function checkPW(pwInput){
	firstPW = pwInput;
	if (firstPW.length<6){ 
		//document.getElementById("MEMID").innerHTML="";
		//document.getElementById("idShow").innerHTML="";
		$("#MEMPW").html("");
		$("#pwShowNG").show();
		$("#pwShowOK").hide();
		return;
	}else{
		$("#pwShowNG").hide();
		$("#pwShowOK").show();
	}
}

function checkRepeat(rpInput){
	if (rpInput.length == 0){
		return;
	}
	if (rpInput != firstPW){ 
		//document.getElementById("MEMID").innerHTML="";
		//document.getElementById("idShow").innerHTML="";
		$("#repeatPW").html("");
		$("#repeatShowNG").show();
		$("#repeatShowOK").hide();
		return;
	}else{
		$("#repeatShowNG").hide();
		$("#repeatShowOK").show();
	}
	
}

</script>

<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	<section id="form" style="margin:60px; font-family: 微軟正黑體;"><!--form-->
		<div class="container">
			<div class="row">
			
				<div class="login-form">
					<h2 style="font-size: 2.5em; font-family: 微軟正黑體; color:#FE980F;" class="text-center title">修改會員資料</h2>
					<div class="error handling">
					<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<font color='red'>請修正以下錯誤:
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li>${message}</li>
								</c:forEach>
							</ul>
							</font>
						</c:if>
					</div>		
					<form class="form-horizontal" role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/memberC" name="form1" enctype="multipart/form-data">		
					   <div class="form-group">
						  <label for="MEMNO" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 會員編號:</label>
						  <div class="col-sm-6" style="margin-top: 8px;">
						     ${CurrentUser.memno}
						  </div>
					   </div>
					   
					   <div class="form-group">
						  <label for="PHOTO" class="col-sm-3 control-label">大頭照:</label>
						  <div class="col-sm-6">
							  <img src="<%=request.getContextPath()%>/DBImgReader?memno=${CurrentUser.memno}" height="100px" width="100px">	
							   <input type="file" class="form-control" id="PHOTO" name="photo"
								placeholder="上傳大頭照">						 
						  </div>
					   </div>

					   <div class="form-group">
						  <label for="MEMID" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 帳號:</label>
						  <div class="col-sm-6" style="margin-top: 8px;">
						     ${CurrentUser.memid}
						  </div>
					   </div>

					   <div class="form-group">
						  <label for="MEMPW" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 密碼:</label>
						  <div class="col-sm-6">
							 <input type="password" class="form-control" id="MEMPW" name="mempw" value="${CurrentUser.mempw}" onblur="checkPW(this.value);"
								placeholder="輸入密碼">
						  </div>
						  <div id="pwShowNG" class="col-sm-3" style="color: red; display:none;">
							 <i class="fa fa-times" ></i> 密碼太短, 請輸入至少6個字元的密碼
						  </div>
						  <div id="pwShowOK" class="col-sm-3" style="color: green; display:none;">
							 <i class="fa fa-check"></i> OK
						  </div>
					   </div>
					   
					   <div class="form-group">
						  <label for="MEMPW" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 再次輸入密碼:</label>
						  <div class="col-sm-6">
							 <input type="password" class="form-control" id="MEMPW" name="re-mempw" value="${CurrentUser.mempw}" onblur="checkRepeat(this.value);"
								placeholder="輸入密碼">
						  </div>
						  <div id="repeatShowNG" class="col-sm-3" style="color: red; display:none;">
							 <i class="fa fa-times" ></i> 密碼與上方輸入不相同
						  </div>
						  <div id="repeatShowOK" class="col-sm-3" style="color: green; display:none;">
							 <i class="fa fa-check"></i> OK
						  </div>
					   </div>
					   
					   <div class="form-group ">
						  <label for="LNAME" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 姓氏:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="LNAME" name="lname" value="${CurrentUser.lname}"
								placeholder="輸入姓氏">
						  </div>
					   </div>
						
					
					   <div class="form-group">
						  <label for="FNAME" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 名字:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="FNAME" name="fname" value="${CurrentUser.fname}"
								placeholder="輸入名字">
						  </div>
					   </div>

					   <div class="form-group">
						  <label for="IDNUM" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 身分證字號或統編:</label>
						  <div class="col-sm-6" style="margin-top: 8px;">
							  ${CurrentUser.idnum}
						  </div>
					   </div>
					   
					   <div class="form-group">
						  <label for="GENDER" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 性別:</label>
						  <div class="col-sm-6" style="margin-top: 8px;">
						     <c:if test="${CurrentUser.gender == 'M'}">
						                     男性
						     </c:if>
						     <c:if test="${CurrentUser.gender == 'F'}">
						     	女性
						     </c:if>
						  </div>
					   </div>
					   <% LocationService locSvc = new LocationService();
					   LocationVO locVO = locSvc.findByPrimaryKey(memberVO.getLocno());
					   String cCounty = locVO.getCounty();
					   String cTown = locVO.getTown();
					   pageContext.setAttribute("cCounty",cCounty);
					   pageContext.setAttribute("cTown",cTown);
					   %>
					   
					   
					   <div class="form-group">
						   <label for="ADDR" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 地址:</label>
						   <div class="col-sm-3">
						       <select id="countySelectList" class="form-control" style="background-color: #F0F0E9;" onchange="getTownUpdated();">						         
						         	<c:forEach var="county" items="${list}">
					                   <option value="${county[1]}-${county[2]}" <c:if test="${county[0]==cCounty}"> selected </c:if> >${county[0]}</option>
					                </c:forEach>
					                <%-- <option>${cCounty}</option> --%>
							   </select>  								  
						   </div> 
						   <div class="col-sm-3">
						         <select id="townSelectList" class="form-control" style="background-color: #F0F0E9;" name="locno" >
						         	<option value="${CurrentUser.locno}"><%=cTown%></option>
								  </select>
						  </div> 
					   </div>
					   
					    <div class="form-group">
						<label for="ADDR" class="col-sm-3 control-label">&nbsp;</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="ADDR" name="addr" value="${CurrentUser.addr}"
								placeholder="輸入地址">
						  </div>
					   </div>
				
					   <div class="form-group">
						  <label for="TEL" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 連絡電話:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="TEL" name="tel" value="${CurrentUser.tel}"
								placeholder="輸入連絡電話">
						  </div>
					    </div>

					   <div class="form-group">
						  <label for="EMAIL" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> EMAIL:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="EMAIL" name="email" value="${CurrentUser.email}"
								placeholder="輸入EMAIL">
						  </div>
					   </div>
					   
					   <div class="form-group">
						  <div class="col-sm-offset-3 col-sm-6">
							<div class=" col-sm-2" style="padding:0px;">
								<div style="color: #DD5555; margin-top: 23px;"><i class="fa fa-check-square" ></i> 為必填</div>
							 </div>
							  <div class="col-sm-offset-7 col-sm-2" style="padding:0px;">
								<button type="submit" class="btn btn-success pull-right">修改</button>
							 </div>	
						  </div>
					   </div>
					<input type="hidden" name="action" value="update_front">
					<input type="hidden" name="memno" value="${CurrentUser.memno}">
				  </FORM>

				</div>
			</div>
		</div>
	</section><!--/form-->
<jsp:include page="/f/frag/f_footer1.jsp"/>
</body>
</html>

