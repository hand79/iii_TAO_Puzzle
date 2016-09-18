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
    <title> ���U�|�� | Ź����� </title>

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
			    			//document.getElementById("idShow").innerHTML= "<font color='red'>���b���w�Q���U</font>";
			    			$("#idShowNG").show();
			    			$("#idShowOK").hide();
			    			$("#idLengNG").hide();
			    			return;
			    		}else{
				    		//document.getElementById("idShow").innerHTML= "���b�����Q���U";
			    			$("#idShowNG").hide();
				    		$("#idShowOK").show();
				    		$("#idLengNG").hide();
			    		}
			    	} 
			    	
/* 			    	if (isIdExist == true) {
			    		document.getElementById("idShow").innerHTML= "���b���w�Q���U";
			    	}else{
			    		document.getElementById("idShow").innerHTML= " <i class="fa fa-check"></i> ���b�����Q���U";
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
		
		alert("�ФĿ�P�N�ϥα���");
		return false;
	} else {
		return true;
	}
}

function shopMagic(){
	document.getElementById("LNAME").value = "��"; 
	document.getElementById("FNAME").value = "�|��";
	document.getElementById("IDNUM").value = "A123456789";
	document.getElementById("ADDR").value = "������300��";
	document.getElementById("TEL").value = "0988133364";
	document.getElementById("EMAIL").value = "ya803g2@gmail.com";
	document.getElementById("TERMS").checked = true;
}

</script>

<% 
request.setAttribute("showBreadcrumb", new Object());

LinkedHashMap<String, String> breadmap = new LinkedHashMap<String, String>();
breadmap.put("�|�����U", "");
request.setAttribute("breadcrumbMap", breadmap);

%>

<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	<section id="form" style="margin:60px; font-family: �L�n������;"><!--form-->
		<div class="container">
			<div class="row">
			
				<div class="login-form">
					<h2 style="font-size: 2.5em; font-family: �L�n������;" class="text-center title">�@��|�����U</h2>
					<div class="error handling">
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
					</div>				
					<form class="form-horizontal" role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/memberC" name="form1" enctype="multipart/form-data" onsubmit="return tickCheck()">
					   		
					   <div class="form-group">
						  <label for="MEMID" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> �b��:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="MEMID" name="memid"
								placeholder="��J�b��" onblur="checkAvaId(this.value);" value="<%= (memberVO==null)? "" : memberVO.getMemid()%>">	 
								<!--<p class="help-block">�ܤ�6�ӭ^��μƦr�A�i�]�t�Ÿ�</p>-->
						  </div>
						  
						  <div id="idShowOK" class="col-sm-3" style="color: green; display:none;" >
							 <!-- --> <i class="fa fa-check"></i> �b�����Q���U
						  </div>
						  <div id="idShowNG" class="col-sm-3" style="color: red; display:none;">
							 <!-- -->  <i class="fa fa-times" ></i> �b���w�Q���U
						  </div>
						  <div id="idLengNG" class="col-sm-3" style="color: red; display:none;">
							 <!-- -->  <i class="fa fa-times" ></i> �ж�g����3~10�r�����b��
						  </div>
						  
					   </div>
					   
					   <div class="form-group">
						  <label for="MEMPW" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> �K�X:</label>
						  <div class="col-sm-6">
							 <input type="password" class="form-control" id="MEMPW" name="mempw" onblur="checkPW(this.value);"
								placeholder="��J�K�X">
						  </div>
						  <div id="pwShowNG" class="col-sm-3" style="color: red; display:none;">
							 <i class="fa fa-times" ></i> �K�X�ӵu, �п�J�ܤ�6�Ӧr�����K�X
						  </div>
						  <div id="pwShowOK" class="col-sm-3" style="color: green; display:none;">
							 <i class="fa fa-check"></i> OK
						  </div>
					   </div>
					   
					   <div class="form-group">
						  <label for="MEMPW" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> �A����J�K�X:</label>
						  <div class="col-sm-6">
							 <input id="repeatPW" type="password" class="form-control" id="MEMPW" onblur="checkRepeat(this.value);"
								placeholder="��J�K�X">
						  </div>
						  <div id="repeatShowNG" class="col-sm-3" style="color: red; display:none;">
							 <i class="fa fa-times" ></i> �K�X�P�W���J���ۦP
						  </div>
						  <div id="repeatShowOK" class="col-sm-3" style="color: green; display:none;">
							 <i class="fa fa-check"></i> OK
						  </div>
					   </div>
					
					  <div class="form-group ">
						  <label for="LNAME" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> �m��:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="LNAME" name="lname" value="<%= (memberVO==null)? "" : memberVO.getLname()%>"
								placeholder="��J�m��">
						  </div>
					   </div>
						
					
					   <div class="form-group">
						  <label for="FNAME" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> �W�r:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="FNAME" name="fname" value="<%= (memberVO==null)? "" : memberVO.getFname()%>"
								placeholder="��J�W�r">
						  </div>
					   </div>
					
					
	
					   <div class="form-group">
						  <label for="IDNUM" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> �����Ҧr��:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="IDNUM" name="idnum" value="<%= (memberVO==null)? "" : memberVO.getIdnum()%>"
								placeholder="��J�����Ҧr��">
						  </div>
					   </div>

					   <div class="form-group">
						  <label for="GENDER" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> �ʧO:</label>
						  <div class="col-sm-6">
							<div class="col-sm-2">
							    <span>
								  <input type="radio" name="gender" id="MALE" value="M" class="checkbox" checked>�k
							  </span>
							 </div>
							 <div class="col-sm-2">
							   <span>
								  <input type="radio" name="gender" id="FEMALE" 
									 value="F" class="checkbox"> �k
							   </span>
							   </div>
						  </div>
					   </div>

					   <div class="form-group">
						   <label for="ADDR" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> �a�}:</label>
						   <div class="col-sm-3">
						         <select id="countySelectList" class="form-control" style="background-color: #F0F0E9;" onchange="getTownUpdated();">						         
						         	<c:forEach var="county" items="${list}">
					                   <option value="${county[1]}-${county[2]}">${county[0]}</option>
					                </c:forEach>
								  </select>
						   </div> 
						   <div class="col-sm-3">
						         <select id="townSelectList" class="form-control" style="background-color: #F0F0E9;" name="locno">
									 <option value="1">���R��</option>
									 <option value="2">�H�q��</option>
									 <option value="3">������</option>
									 <option value="4">������</option>
									 <option value="5">�w�ְ�</option>
									 <option value="6">�x�x��</option>
									 <option value="7">�C����</option>
								  </select>
						  </div> 						 
					   </div>
					   
					    <div class="form-group">
						<label for="ADDR" class="col-sm-3 control-label">&nbsp;</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="ADDR" name="addr" value="<%= (memberVO==null)? "" : memberVO.getAddr()%>"
								placeholder="��J�a�}">
						  </div>
					   </div>

					   <div class="form-group">
						  <label for="TEL" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> �s���q��:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="TEL" name="tel" value="<%= (memberVO==null)? "" : memberVO.getTel()%>"
								placeholder="��J�s���q�� e.g. 0912345678">
						  </div>
					   </div>

					   <div class="form-group">
						  <label for="EMAIL" class="col-sm-3 control-label"><i class="fa fa-check-square"></i> EMAIL:</label>
						  <div class="col-sm-6">
							 <input type="text" class="form-control" id="EMAIL" name="email" value="<%= (memberVO==null)? "" : memberVO.getEmail()%>"
								placeholder="��JEMAIL">
						  </div>
<!-- 					  <div class="col-sm-3" style="color: #f0ad4e;">
							 <i class="fa fa-exclamation-triangle" ></i> �п�Jemail
						  </div> -->
						  
					   </div>

					   <div class="form-group">
						  <label for="PHOTO" class="col-sm-3 control-label">�j�Y��:</label>
						  <div class="col-sm-6">
							 <input type="file" class="form-control" id="PHOTO" name="photo"
								placeholder="�W�Ǥj�Y��">
						  </div>
					   </div>
					   
					   <div class="form-group">
						  <label for="AGREE" class="col-sm-3 control-label"><i class="fa fa-check-square"></i>�ϥα���:</label>
						  
						  <div class="col-sm-4" style="padding-top: 6px; ">
						  
							 <span >
								<a data-toggle="modal"  data-target="#case_control_form">  �ڤw�g�\Ū�A�ȱ��ڨåB�P�N���U���|�� </a>
								  <input type="checkbox" name="TERMS" id="TERMS" 
									 value="CHECKED" > 
							 </span>
						  </div>
<!-- 						  <div class="col-sm-2">
							<button class="btn btn-warning btn-block" >�����˵������A�ȱ���</button>
						  </div> -->
					   </div>
					   
					   <div class="form-group">
						  <div class="col-sm-offset-3 col-sm-6">
							<div class=" col-sm-2" style="padding:0px;">
								<div style="color: #DD5555; margin-top: 23px;"><i class="fa fa-check-square" ></i> ������</div>
							 </div>
							  <div class="col-sm-offset-8 col-sm-2" style="padding:0px;">
								<button type="submit" class="btn btn-success pull-right">���U</button>
							 </div>	
						  </div>
					   </div>
					   
					   <div class="col-sm-offset-3 col-sm-1" style="padding:0px;">
								<button id="btnShopMagic" type="button" class="btn" onclick="shopMagic();"><i class="fa fa-magic"></i>
									���_�p���s</button>
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
					<h4 class="modal-title" id="AdModal">�A�ȱ���</h4>
				</div>
				<div class="modal-body">
					<table>
						<tr><td> 1. ���T�O�ൣ�ΫC�֦~�ϥκ������w���A���קK���p�v����I�ǡA�a���]�κ��@�H�^���ɨ�U�C�q�ȡG�����Q�G�����ൣ�ϥΡuŹ���a�ϡv�u�W�ʪ��ɡA�����{�b�ǳ���A�Q�G���H�W�����Q�K�����C�֦~�W���e�����r�u�O�_�����P�N�C	</td></tr>
						<tr><td> 2. �����w�Ѥ������P�A�ΡA�H�λP�����w�Ѧ�������ĳ�A�����̷Ӥ��إ���k�ߤ��H�B�z�A�åH�x�W�x�_�a��k�|���Ĥ@�f���Ҫk�|�C		</td></tr>
						<tr><td> 3. ��z�b�|����Ƶn�O���q�l�l��H�c������ Ź������ �ҵo�X�|����ƽT�{�H�ýT�{�[�J�|������A��ܱz�w�g�\Ū�B�A�ѥB�P�N�������A�ȱ��کҦ������e�P���w�A�ç����������A�Ȳ{���P���ӭl�ͪ��A�ȶ��ؤΤ��e�C		</td></tr>
						<tr><td> 4. �|�����������U�{�ǡA�]�A��g�|����ơB�H�δ��ѵ��U�y�{���ҭn�D���������θ�ơC�|�����ϥΥ��A�Ȥ��Ҧ��\��̡A�����̥��A�ȷ�ɩҭq���覡�����|���{�ҡF���U�����R�a�|���̡A������Email�{�ҡF�i�������ө��|���̡A���������P�h�Ť�����{�ҡC </td></tr>
						<tr><td> 5. �|������O��Ҵ��Ѫ��Ҧ���ơA�������T�B�Y�ɪ���ơA�B���o�H�ĤT�H���W�q���U���|���F�p�|���Ҵ��Ѫ���ƨƫᦳ�ܧ�A�|�����Y�ɧ�s���ơC�p�|�����Y�ɴ��Ѹ�ơB�������w�覡���Ѹ�ơB�Ҵ��Ѥ���Ƥ����T�λP�ƹꤣ�šB�Υ��Y�ɧ�s��ơA Ź������  �o���g�ƥ��q���A�H�ɩڵ��B�μȰ���ӷ|�����ѥ��A�Ȥ������Τ@���C </td></tr>
						<tr><td> 6. �|�����ۦ槴���O�ި�b���αK�X���q�ȡA���o���ѩγz�S���ĤT�H�O�ީΨϥΡF���ϥίS�w�b���αK�X�n�J���A�Ȩt�Ϋᤧ�Ҧ��欰�A���w���ӱb�������H�ۤv���欰�C�|���p�G�o�{���h�è�b���αK�X�Q�ĤT�H�_�ΩΤ���ϥΡA�|�����ߧY�q�� Ź������ �C  </td></tr>
						<tr><td> 7. ihergo�W�ҥZ��������B�������e�B�ά����T���A�]�A�ҥZ�����s�i�A���Y�ѷ|���ۦ洣�ѡB�W���B�εo���A�åѥ��A�Ȩt�Φ۰ʥZ��������A Ź������ �å��ƥ��L�o�μf�d�䤺�e�A���䤺�e���u��ʡB�X�k�ʡB�Y�ɩʵ��A���t������ܩ��q�ܤ��ӿթξ�O�C���|���ҥZ��������B�������e�B�ά����T�����A�p���H�Ϫk�O�B�H�I���Ǩ}�U�B�I�`�ĤT�H�v�q�B�}�a������ǩιH�Ϸ|���X�����������ΡA Ź������ �o���g�ƥ��q���A�����[�H�����B�Ϥ��L�k�Q�s���B�αĨ���L����ʱ��I�C </td></tr>
						<tr><td> 8. �g�� Ź������ �Ҷi�椧����A���ѶR������ۦ�t�d������R�Ӥμi��A���R��a�i�������N�@�ί�O�B�H�Ψ�ҥ���ӫ~�ΪA�Ȥ��~��B�w���ʤΦX�k�ʵ��AŹ������ ���t������ܩ��q�ܤ��ӿթξ�O�C�Y��������������R�өμi��o�ͪ�ĳ�A���ѥ������ۦ�ۤ���աB�ѨM��ĳ�C </td></tr>
						<tr><td> 9. Ź������  �{�����n�ɡA�o�N�|�����U��ơB�|���ҥZ��������ά����T���B�H�λP����������ƶ����A�n�D�|���N��үA�Τ��øq�Ϊ�ĳ�A�ήɴ��X�����Φ�����ơC  </td></tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
				</div>	
			</div>
		</div>
	</div>

<jsp:include page="/f/frag/f_footer1.jsp"/>
  
</body>
</html>

