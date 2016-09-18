<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.member.model.*"%>
<%@ page import="com.tao.location.model.*"%>
<%
	MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");

	LocationService dao = new LocationService();
	List<String[]> list = dao.getUniqCounty();
	pageContext.setAttribute("list",list);
	
%>

<jsp:useBean id="locSvc" scope="page" class="com.tao.location.model.LocationService" />

<jsp:include page="/f/frag/f_header1.jsp"/>

    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta http-equiv="expires" content="0">
    <title> 註冊會員 | 饕飽拚圖 </title>

<style> 
input[type=text], input[type=password], input[type=number], textarea {
  -webkit-transition: all 0.30s ease-in-out;
  -moz-transition: all 0.30s ease-in-out;
  -ms-transition: all 0.30s ease-in-out;
  -o-transition: all 0.30s ease-in-out;
  outline: none;
  padding: 3px 0px 3px 3px;
  border: 1px solid #DDDDDD;
}
 
input[type=text]:focus, input[type=password]:focus, input[type=number]:focus, textarea:focus {
  box-shadow: 0 0 5px rgba(81, 203, 238, 1);
  padding: 3px 0px 3px 3px;
  border: 1px solid rgba(81, 203, 238, 1);
}
</style>



<script>  
var xhr = null;
var idArry = null;
var firstPW = null;

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
	
function checkAvaId(idInput){
/* 	document.getElementById("idShow").innerHTML= idInput; */
	
	
	if ((idInput.length<3) || (idInput.length>10)){ 
		//document.getElementById("MEMID").innerHTML="";
		//document.getElementById("idShow").innerHTML="";
		$("#MEMID").html("");
		$("#idShowNG").hide();
		$("#idShowOK").hide();
		$("#idLengNG").show();
		return;
	}

	xhr = createXHR();
	if( xhr == null ){
		alert("Does not support Ajax....");
		return;
	}		   
	  
	xhr.onreadystatechange = function(){
		
		if( xhr.readyState == 4){
			    if( xhr.status == 200){
			    	if (idArry == null){
			    	   var idArry = JSON.parse(xhr.responseText);
			    	}
			    	
			    	//document.getElementById("idShow1").innerHTML = idArry.idList[0].memid;
			    	
 			    	for (var i = 0; i < idArry.idList.length; i++){
			    		if ((idArry.idList[i].memid).trim() == idInput.trim()){
			    			//document.getElementById("idShow").innerHTML= "<font color='red'>本帳號已被註冊</font>";
			    			$("#idShowNG").show();
			    			$("#idShowOK").hide();
			    			$("#idLengNG").hide();
			    			return;
			    		}else{
				    		//document.getElementById("idShow").innerHTML= "本帳號未被註冊";
			    			$("#idShowNG").hide();
				    		$("#idShowOK").show();
				    		$("#idLengNG").hide();
			    		}
			    	} 
			    	
/* 			    	if (isIdExist == true) {
			    		document.getElementById("idShow").innerHTML= "本帳號已被註冊";
			    	}else{
			    		document.getElementById("idShow").innerHTML= " <i class="fa fa-check"></i> 本帳號未被註冊";
			    	} */
				}else{
				  alert( xhr.status );
				}
	    }
	 }

	 var url= "<%=request.getContextPath()%>/member/front/idChecker.jsp?userInput="+ idInput;
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

function tickCheck(){
	if (!document.getElementById("TERMS").checked){ 
		//document.getElementById("MEMID").innerHTML="";
		//document.getElementById("idShow").innerHTML="";
		
		alert("請勾選同意使用條款");
		return false;
	} else {
		return true;
	}
}

function shopMagic(){
	document.getElementById("LNAME").value = "李"; 
	document.getElementById("FNAME").value = "會員";
	document.getElementById("IDNUM").value = "A123456789";
	document.getElementById("ADDR").value = "中正路300號";
	document.getElementById("TEL").value = "0988133364";
	document.getElementById("EMAIL").value = "ya803g2@gmail.com";
	document.getElementById("TERMS").checked = true;
}

</script>

<% 
request.setAttribute("showBreadcrumb", new Object());

LinkedHashMap<String, String> breadmap = new LinkedHashMap<String, String>();
breadmap.put("會員註冊", "");
request.setAttribute("breadcrumbMap", breadmap);

%>

<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	<section id="form" style="margin:60px; font-family: 微軟正黑體;"><!--form-->
		<div class="container">
			<div class="row">
			
				<div class="login-form">
					<h2 style="font-size: 2.5em; font-family: 微軟正黑體;" class="text-center title">一般會員註冊</h2>
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
					<form class="form-horizontal" role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/memberC" name="form1" enctype="multipart/form-data" onsubmit="return tickCheck()">
					   		
					   <div class="form-group">
						  <label for="MEMID" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 帳號:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="MEMID" name="memid"
								placeholder="輸入帳號" onblur="checkAvaId(this.value);" value="<%= (memberVO==null)? "" : memberVO.getMemid()%>">	 
								<!--<p class="help-block">至少6個英文或數字，可包含符號</p>-->
						  </div>
						  
						  <div id="idShowOK" class="col-sm-3" style="color: green; display:none;" >
							 <!-- --> <i class="fa fa-check"></i> 帳號未被註冊
						  </div>
						  <div id="idShowNG" class="col-sm-3" style="color: red; display:none;">
							 <!-- -->  <i class="fa fa-times" ></i> 帳號已被註冊
						  </div>
						  <div id="idLengNG" class="col-sm-3" style="color: red; display:none;">
							 <!-- -->  <i class="fa fa-times" ></i> 請填寫介於3~10字元的帳號
						  </div>
						  
					   </div>
					   
					   <div class="form-group">
						  <label for="MEMPW" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 密碼:</label>
						  <div class="col-sm-6">
							 <input type="password" class="form-control" id="MEMPW" name="mempw" onblur="checkPW(this.value);"
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
							 <input id="repeatPW" type="password" class="form-control" id="MEMPW" onblur="checkRepeat(this.value);"
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
							 <input type="text" class="form-control" id="LNAME" name="lname" value="<%= (memberVO==null)? "" : memberVO.getLname()%>"
								placeholder="輸入姓氏">
						  </div>
					   </div>
						
					
					   <div class="form-group">
						  <label for="FNAME" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 名字:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="FNAME" name="fname" value="<%= (memberVO==null)? "" : memberVO.getFname()%>"
								placeholder="輸入名字">
						  </div>
					   </div>
					
					
	
					   <div class="form-group">
						  <label for="IDNUM" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 身分證字號:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="IDNUM" name="idnum" value="<%= (memberVO==null)? "" : memberVO.getIdnum()%>"
								placeholder="輸入身分證字號">
						  </div>
					   </div>

					   <div class="form-group">
						  <label for="GENDER" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 性別:</label>
						  <div class="col-sm-6">
							<div class="col-sm-2">
							    <span>
								  <input type="radio" name="gender" id="MALE" value="M" class="checkbox" checked>男
							  </span>
							 </div>
							 <div class="col-sm-2">
							   <span>
								  <input type="radio" name="gender" id="FEMALE" 
									 value="F" class="checkbox"> 女
							   </span>
							   </div>
						  </div>
					   </div>

					   <div class="form-group">
						   <label for="ADDR" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 地址:</label>
						   <div class="col-sm-3">
						         <select id="countySelectList" class="form-control" style="background-color: #F0F0E9;" onchange="getTownUpdated();">						         
						         	<c:forEach var="county" items="${list}">
					                   <option value="${county[1]}-${county[2]}">${county[0]}</option>
					                </c:forEach>
								  </select>
						   </div> 
						   <div class="col-sm-3">
						         <select id="townSelectList" class="form-control" style="background-color: #F0F0E9;" name="locno">
									 <option value="1">仁愛區</option>
									 <option value="2">信義區</option>
									 <option value="3">中正區</option>
									 <option value="4">中正區</option>
									 <option value="5">安樂區</option>
									 <option value="6">暖暖區</option>
									 <option value="7">七堵區</option>
								  </select>
						  </div> 						 
					   </div>
					   
					    <div class="form-group">
						<label for="ADDR" class="col-sm-3 control-label">&nbsp;</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="ADDR" name="addr" value="<%= (memberVO==null)? "" : memberVO.getAddr()%>"
								placeholder="輸入地址">
						  </div>
					   </div>

					   <div class="form-group">
						  <label for="TEL" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> 連絡電話:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="TEL" name="tel" value="<%= (memberVO==null)? "" : memberVO.getTel()%>"
								placeholder="輸入連絡電話 e.g. 0912345678">
						  </div>
					   </div>

					   <div class="form-group">
						  <label for="EMAIL" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> EMAIL:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="EMAIL" name="email" value="<%= (memberVO==null)? "" : memberVO.getEmail()%>"
								placeholder="輸入EMAIL">
						  </div>
<!-- 					  <div class="col-sm-3" style="color: #f0ad4e;">
							 <i class="fa fa-exclamation-triangle" ></i> 請輸入email
						  </div> -->
						  
					   </div>

					   <div class="form-group">
						  <label for="PHOTO" class="col-sm-3 control-label">大頭照:</label>
						  <div class="col-sm-6">
							 <input type="file" class="form-control" id="PHOTO" name="photo"
								placeholder="上傳大頭照">
						  </div>
					   </div>
					   
					   <div class="form-group">
						  <label for="AGREE" class="col-sm-3 control-label"><i class="fa fa-check-square"></i>使用條款:</label>
						  
						  <div class="col-sm-4" style="padding-top: 6px; ">
						  
							 <span >
								<a data-toggle="modal"  data-target="#case_control_form">  我已經閱讀服務條款並且同意註冊為會員 </a>
								  <input type="checkbox" name="TERMS" id="TERMS" 
									 value="CHECKED" > 
							 </span>
						  </div>
<!-- 						  <div class="col-sm-2">
							<button class="btn btn-warning btn-block" >按此檢視本站服務條款</button>
						  </div> -->
					   </div>
					   
					   <div class="form-group">
						  <div class="col-sm-offset-3 col-sm-6">
							<div class=" col-sm-2" style="padding:0px;">
								<div style="color: #DD5555; margin-top: 23px;"><i class="fa fa-check-square" ></i> 為必填</div>
							 </div>
							  <div class="col-sm-offset-8 col-sm-2" style="padding:0px;">
								<button type="submit" class="btn btn-success pull-right">註冊</button>
							 </div>	
						  </div>
					   </div>
					   
					   <div class="col-sm-offset-3 col-sm-1" style="padding:0px;">
								<button id="btnShopMagic" type="button" class="btn" onclick="shopMagic();"><i class="fa fa-magic"></i>
									神奇小按鈕</button>
					   </div>	
					   
					    <input type="hidden" name="type" value="0">
					   <input type="hidden" name="action" value="insert">
					</form>

				</div>
			</div>
		</div>
	</section><!--/form-->
	
	<div class="modal fade" id="case_control_form" role="dialog" aria-labelledby="AdModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"  data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="AdModal">服務條款</h4>
				</div>
				<div class="modal-body">
					<table>
						<tr><td> 1. 為確保兒童及青少年使用網路的安全，並避免隱私權受到侵犯，家長（或監護人）應盡到下列義務：未滿十二歲之兒童使用「饕飽地圖」線上購物時，應全程在旁陪伴，十二歲以上未滿十八歲之青少年上網前亦應斟酌是否給予同意。	</td></tr>
						<tr><td> 2. 本約定書之解釋與適用，以及與本約定書有關的爭議，均應依照中華民國法律予以處理，並以台灣台北地方法院為第一審管轄法院。		</td></tr>
						<tr><td> 3. 當您在會員資料登記的電子郵件信箱中收到 饕飽拼圖 所發出會員資料確認信並確認加入會員之後，表示您已經閱讀、瞭解且同意接受本服務條款所有的內容與約定，並完全接受本服務現有與未來衍生的服務項目及內容。		</td></tr>
						<tr><td> 4. 會員應完成註冊程序，包括填寫會員資料、以及提供註冊流程中所要求之相關文件或資料。會員欲使用本服務之所有功能者，並應依本服務當時所訂之方式完成會員認證；註冊成為買家會員者，應完成Email認證；進階成為商店會員者，應完成不同層級之手機認證。 </td></tr>
						<tr><td> 5. 會員應擔保其所提供的所有資料，均為正確且即時的資料，且不得以第三人之名義註冊為會員；如會員所提供的資料事後有變更，會員應即時更新其資料。如會員未即時提供資料、未按指定方式提供資料、所提供之資料不正確或與事實不符、或未即時更新資料， 饕飽拼圖  得不經事先通知，隨時拒絕、或暫停對該會員提供本服務之全部或一部。 </td></tr>
						<tr><td> 6. 會員有自行妥善保管其帳號及密碼之義務，不得提供或透露予第三人保管或使用；對於使用特定帳號及密碼登入本服務系統後之所有行為，推定為該帳號持有人自己之行為。會員如果發現或懷疑其帳號或密碼被第三人冒用或不當使用，會員應立即通知 饕飽拼圖 。  </td></tr>
						<tr><td> 7. ihergo上所刊載之物件、說明內容、及相關訊息，包括所刊載之廣告，均係由會員自行提供、上載、及發布，並由本服務系統自動刊載於網站， 饕飽拼圖 並未事先過濾或審查其內容，對於其內容之真實性、合法性、即時性等，不負任何明示或默示之承諾或擔保。但會員所刊載之物件、說明內容、或相關訊息等，如有違反法令、違背公序良俗、侵害第三人權益、破壞賣場秩序或違反會員合約之虞之情形， 饕飽拼圖 得不經事先通知，直接加以移除、使之無法被存取、或採取其他限制性措施。 </td></tr>
						<tr><td> 8. 經由 饕飽拼圖 所進行之交易，應由買賣雙方自行負責交易之磋商及履行，對於買賣家履行交易之意願及能力、以及其所交易商品或服務之品質、安全性及合法性等，饕飽拼圖 不負任何明示或默示之承諾或擔保。若交易雙方對於交易之磋商或履行發生爭議，應由交易雙方自行相互協調、解決爭議。 </td></tr>
						<tr><td> 9. 饕飽拼圖  認為必要時，得就會員註冊資料、會員所刊載之物件及相關訊息、以及與交易相關之事項等，要求會員就其所涉及之疑義或爭議，及時提出說明及有關資料。  </td></tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
				</div>	
			</div>
		</div>
	</div>

<jsp:include page="/f/frag/f_footer1.jsp"/>
  
</body>
</html>

