<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.caseRep.model.*"%>

<%
	CaseRepVO caseRepVO = (CaseRepVO) request.getAttribute("caseRepVO");
%>

<jsp:include page="/b/frag/b_header1.jsp" flush="true" />
<title>檢舉處理 | 後端管理系統</title>
<jsp:include page="/b/frag/b_header2AndMenu.jsp" />

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header">合購案件檢舉</h3>
			<a href="<%=request.getContextPath()%>/caseRep/listAllCaseRep.jsp"><button
					type="button" class="btn btn-link">回合購案件檢舉列表</button></a>
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
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-6">
			<div class="panel panel-default">
				<div calss="panel-heading">
					<label>檢舉案件編號:<%=caseRepVO.getCrepno()%></label>
				</div>

				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/back/caseRep/caseRep.do">
					<div class="panel-body">
						<div class="col-sm-6 form-group">
							<label>檢舉人會員編號:</label><input type="TEXT" name="repno"
								class="form-control" value="<%=caseRepVO.getRepno()%>" />
						</div>
						<div class="col-sm-6 form-group">
							<label>受檢舉會員編號:</label><input type="TEXT" name="susno"
								class="form-control" value="<%=caseRepVO.getSusno()%>" />
						</div>
						<div class="col-sm-6 form-group">
							<label>受檢舉合購案編號:</label><input type="TEXT" name="repcaseno"
								class="form-control" value="<%=caseRepVO.getRepcaseno()%>" />
						</div>
						<div class="col-sm-12 form-group">
							<label>檢舉原因:</label><input type="TEXT" name="creprsn"
								class="form-control" size="50"
								value="<%=caseRepVO.getCreprsn()%>" />
						</div>
					</div>
					<div class="panel-footer">
						<input type="hidden" name="action" value="update"> <input
							type="hidden" name="crepno" value="<%=caseRepVO.getCrepno()%>">
						<input type="hidden" name="requestURL"
							value="<%=request.getParameter("requestURL")%>"> <input
							type="submit" class="btn btn-warning" value="修改">
					</div>
				</FORM>

			</div>
			<!-- /.panel -->
		</div>
	</div>


</div>
<jsp:include page="/b/frag/b_footer1.jsp" />

<jsp:include page="/b/frag/b_footer2.jsp" />