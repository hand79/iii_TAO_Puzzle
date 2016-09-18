        <%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.cases.model.*"%>
<%@ page import="com.tao.location.model.*"%>
<%@ page import="java.util.*" %>
<<jsp:useBean id="memberSvc" class="com.tao.member.model.MemberService" />

<%
	Set<CasesVO> set = (Set<CasesVO>) request.getAttribute("set");
	List<CasesVO> list = null;
	if(set != null){
		list = new ArrayList<CasesVO>(set);
		pageContext.setAttribute("list", list);
	}
		Map<Integer, LocationVO> map = new HashMap<Integer, LocationVO>();
		for(LocationVO vo : new LocationService().getAll()){
			map.put(vo.getLocno(), vo);
		}
		pageContext.setAttribute("locMap", map);
	
	
%>
<% pageContext.setAttribute("contextPath", request.getContextPath()); %>
<jsp:include page="/f/frag/f_header1.jsp"/>
<title> �j�M�X�� </title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>


	<section><!--form-->
		<div class="container">
			<div class="row">
			<jsp:include page="/f/frag/f_catMenu.jsp"/>
			
				<div class="col-sm-8 col-sm-offset-1">
					<h2 class="text-center title">�j�M�X�ʮ�</h2>
					<div class="error handling" style="color: red">
						
						<%-- ������~��C --%>
						<c:if test="${not empty errorMsgs}">
							<div class="text-center">
								<c:forEach var="message" items="${errorMsgs}">
									<p><i>(${message})</i></p>
								</c:forEach>
							</div>
							
						</c:if>
					</div>		
				
					<div class="search_results"><!--search_results-->
						<c:if test="${not empty list}">
							<!--search_results rows-->
							<%@ include file="/search/page1.file" %>
							<c:forEach var="caseVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
								<table class="table table-striped table-bordered table-hover table-condensed table-responsive" style="font-family: �L�n������;">
									<tr>
										<td colspan="2"><span style="background-color: #FE980F; color: white;">&nbsp;&nbsp;<i class="fa fa-fw fa-users"></i>�X�� <span>${caseVO.caseno}</span>&nbsp;</span><a href="<%=request.getContextPath()%>/SurfMemberServlet.do?action=memberView&memno=${caseVO.memno}" style="color: #444;font-weight: bolder;"><i class="fa fa-user fa-fw"></i>${memberSvc.findByPrimaryKey(caseVO.memno).memid}</a> �o�_���X��<span class="pull-right">${locMap.get(caseVO.locno).county} &gt; ${locMap.get(caseVO.locno).town}</span></td>
									</tr>
									<tr>
										<td rowspan="2" style="width:120px;"><img src="${contextPath}/HotCaseImage.do?caseno=${caseVO.caseno}" style="width:120px; height:120px;" /></td>
										<td><a href="${contextPath}/cases/cases.do?action=caseDetail&caseno=${caseVO.caseno}">${caseVO.title}</a></td>
									</tr>
									<tr>
										<td >${caseVO.casedesc}</td>
									</tr>
								</table>
							<hr/>
							</c:forEach>	
							<%@ include file="/search/page2.file" %>
							<!--more search_results rows-->				
						</c:if>
					</div><!--search_results-->

				</div>
			</div>
		</div>
	</section><!--/form-->
	
	
<jsp:include page="/f/frag/f_footer1.jsp"/>
</body>
</html>
