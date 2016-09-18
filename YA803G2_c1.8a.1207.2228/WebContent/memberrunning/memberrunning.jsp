<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@page import="com.tao.runningad.model.RunningAdVO"%>
<%@page import="com.tao.member.model.MemberVO"%>
<%@page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<jsp:include page="/f/frag/f_header1.jsp"/>

<title>�n�����y</title>
<link href='<%=request.getContextPath()%>/memberrunning/css/case_control.css' rel='stylesheet'>
<link href='<%=request.getContextPath()%>/memberrunning/css/dataTables.bootstrap.css' rel='stylesheet'>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("�|������","");
request.setAttribute("breadcrumbMap", map); 
%>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>   
 	
<jsp:useBean id="memberSvc"  class="com.tao.member.model.MemberService"/>
<jsp:useBean id="casesSvc"  class="com.tao.cases.model.CasesService"/>
<jsp:useBean id="runningAdSvc"  class="com.tao.runningad.model.RunningAdService"/>
<c:set var="CurrentUser" value="<%=memberSvc.findByPrimaryKeyNoPic(((MemberVO)session.getAttribute(\"CurrentUser\")).getMemno()) %>" scope="session"/>
<c:set var="memberVO" value="${CurrentUser }" scope="page"/>
<c:set var="runningAdList" value="${runningAdSvc.getAllByMemno(memberVO.memno)}"/>
	
<section>
	<div class="container">
		<div class="row">
<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>  

 			<div class="col-sm-9">
 				<h2 class="title text-center">�n�����y</h2> 
 			<table id="case_point_control"  class="table">
						<tr>
							<th id="point_control" >
							<p>
								<span id="pointTitle">�z�ثe�ֿn���n��</span>
								
							</p>
							<p>
								<span id="point">${memberVO.point}</span>��
							</p>	
							</th>
							<th>
								<p>
									�i�ϥΤѼ�:<span style="color:red;">${memberVO.addays}</span>��
								</p>
									<button class="btn btn-primary btn-1g btn-block" data-toggle="modal"  data-target="#case_control_form">�U���s�i</button>
							</th>
						</tr>
					</table>
				
								
					<div class="table-responsive">
						<table id="case_point_record" class="table" >
							<caption style="color:#FE980F;">�U���O��</caption>
							<thead>
								<tr>
								<th>�s��</th>
								<th>�Ϥ�</th>
								<th>�X�ʮצW��</th>
								<th>�ӽЮɶ�</th>
								<th>�_�l���</th>
								<th>�פ���</th>
								<th>�Ѽ�</th>
								<th>���A</th>
								</tr>
							</thead>
							<tbody>
								<%
								List<RunningAdVO> runningAdList=(List<RunningAdVO>)pageContext.getAttribute("runningAdList");
								Collections.reverse(runningAdList);
								%>
								<c:forEach var="runningAdVO" items="${runningAdList}"  varStatus="s" >
									<c:set var="runningAdVO" value="${runningAdVO}"/>
									<%
										RunningAdVO runningAdVO =(RunningAdVO)pageContext.getAttribute("runningAdVO");
									
									%>
									<tr 
									
										<c:if test="${runningAdVO.tst==1}">	
											class="success"
										</c:if>
										<c:if test="${runningAdVO.tst==2}">
											class="warning"
										</c:if>
										<c:if test="${runningAdVO.tst==3}">	
											class="danger"
										</c:if>
									>	<th>${runningAdVO.adno}</th>
										<th ><input id="picture_${s.index}" type="button" class="btn btn-link btn-xs" data-toggle="modal"  data-target="#case_pic" value="��ܹϤ�">
											<input type="hidden" value="${runningAdVO.adno}">
										</th>
										<th >${casesSvc.getByPrimaryKey(runningAdVO.caseno).title}
										
										</th>
										<th >${runningAdVO.reqtime}</th>
										<th >${runningAdVO.sdate==null? "�|���}�l":runningAdVO.sdate}</th>
										<th >${runningAdVO.edate==null? "�|������":runningAdVO.edate}</th>
										<th >${runningAdVO.dtime}</th>
										<th >
												
											<c:if test="${runningAdVO.tst==0}">										
												�ݼf��
											</c:if>
											<c:if test="${runningAdVO.tst==1}">	
												�w�W�[
											</c:if>
											<c:if test="${runningAdVO.tst==2}">	
												�ɶ���
											</c:if>
											<c:if test="${runningAdVO.tst==3}">	
												�f�֥��q�L
											</c:if>
																									
										</th>
									</tr>
									</c:forEach>
								</tbody>
						</table>
					</div>
 			</div>
		</div>
	</div>
</section>

<div class="modal fade" id="case_control_form" role="dialog" aria-labelledby="AdModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"  data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="AdModal">�U���s�i</h4>
				</div>
				<form id="adform" method="post" role="form" enctype="multipart/form-data" action="<%=request.getContextPath()%>/MemberRunningServlet.do">
				<div class="modal-body">
					
						<div class="form-group">
						�X�ʮ�: <select id="caseAd" name="caseno"  class="form-control">
									<option value="0">�п�ܤw���}�W�[���X��</option>
									<c:forEach var="caseAdVO" items="${casesSvc.getByCreator(memberVO.memno)}">
										<c:if test="${caseAdVO.status==1}">
											<option value="${caseAdVO.caseno}">${caseAdVO.title}</option> 
										</c:if>
									</c:forEach>
								</select>
						�Ѽ�:	<select id="addaysAd" name="addays" class="form-control">
									<c:if test="${memberVO.addays==0}">
										<option value="0">�L�i�ϥΤѼ�</option>
									</c:if>
									<c:forEach var="s" begin="1" end="${memberVO.addays}">
										<option value="${s}">${s}</option>
									</c:forEach>
								</select>
						</div>
						<div style=" width:565px; height:300px;">
                            <img id="ImgPr" width="565" height="300" style="display: inherit;" />
                   		 </div>
                            <input type="file" name="adpic" id="upload" />
                            
				</div>
				<div class="modal-footer">
					<ul id="error" style="float:left;color:red;">
				
					</ul>					
					<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
					<button id="runAdcase"type="button" class="btn btn-success">�U��</button>
				</div>	
				</form>	
			</div>
			
		</div>
	</div>
	<div class="modal fade" id="case_pic" role="dialog" aria-labelledby="AdPic" aria-hidden="true">
		<div class="modal-dialog" style="width:940px;height:440px; top: 140px;">
			<div class="modal-content">
				<div class="modal-body">
					<img id="recordpic" style="width:900px;height:400px;">
				</div>
			</div>
		</div>
	</div>

	
<jsp:include page="/f/frag/f_footer1.jsp"/>    		
<script src="<%=request.getContextPath()%>/memberrunning/js/uploadPreview.min.js"></script>
<script src="<%=request.getContextPath()%>/memberrunning/js/case_control.js"></script>
<script src="<%=request.getContextPath()%>/memberrunning/js/jquery.dataTables.js"></script>
<script src="<%=request.getContextPath()%>/memberrunning/js/dataTables.bootstrap.js"></script>

<script>
	$(document).ready(function(){
		var okpic=false;
		var file; 
		var fileName; 
		var fileSize; 
		var fileType ;
		var allowedFileTypes = ["image/png", "image/jpeg", "image/gif"];
		$("#case_point_record").dataTable(
			{ 	bLengthChange:false,     
								
				bFilter:false,
				bProcessing:true,
				oLanguage: {
				    sLengthMenu: "��� _MENU_ ���O��",
				    sZeroRecords: "�L�ŦX���",
				    sFirst:"����",
				    sLast:"����",
				    sPrevious:"�W�@��",
				    sNext:"�U�@��",
				    sInfo: "�ثe�����O���G_START_ �� _END_��, �`���ơG_TOTAL_",
				  },  
			}
		);
		
		$("input[id^='picture_']").click(function(){
			var adpic=$(this).next().val();
		
			$("#recordpic").attr("src","<%=request.getContextPath()%>/RunningImage.do?adno="+adpic);
		});		
		
		
		$(':file').change(function(){
			 file = this.files[0];
			 fileName = file.name;
 			 fileSize = file.size;
 			 fileType = file.type;	
			 $("#error").html("");
			if(file==null||fileName==null||fileSize==null){
				$("#error").append("<li>�ФW�ǹϤ�</li> ");
				okpic=false;
			}else if(allowedFileTypes.indexOf(file.type) <= -1){
				$("#error").append("<li>�Ϥ��榡:png�Bjpeg�Bgif</li>");
				okpic=false;
			}else{
				okpic=true;
			}
			
		});
		
		$("#runAdcase").click(function(){
			var caseno=$("#caseAd").val();
 			var addays=$("#addaysAd").val();
 			 
			$("#error").html("");
			if(caseno<=0){
				$("#error").append("<li>�п�ܦX�ʮ�</li>");
			}
			if(addays<=0){
				$("#error").append("<li>�п�ܤѼ�</li> ");
			}
			
			if(file==null||fileName==null||fileSize==null){
				$("#error").append("<li>�ФW�ǹϤ�</li> ");
				okpic=false;
			}else if(allowedFileTypes.indexOf(file.type) <= -1){
				$("#error").append("<li>�Ϥ��榡:png�Bjpeg�Bgif</li>");
				okpic=false;
			}else{
				okpic=true;
			}
						
			if((caseno>0)&&(addays>0)&&(okpic==true)){
				$("#adform").submit();
			}

			
		});
			
		
	});
</script>

<jsp:include page="/f/frag/f_footer2.jsp"/>    				
