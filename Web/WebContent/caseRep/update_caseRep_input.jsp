<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.caseRep.model.*"%>

<%
	CaseRepVO caseRepVO = (CaseRepVO) request.getAttribute("caseRepVO");
%>

<jsp:include page="/b/frag/b_header1.jsp" flush="true" />
<title>���|�B�z | ��ݺ޲z�t��</title>
<jsp:include page="/b/frag/b_header2AndMenu.jsp" />

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header">�X�ʮץ����|</h3>
			<a href="<%=request.getContextPath()%>/caseRep/listAllCaseRep.jsp"><button
					type="button" class="btn btn-link">�^�X�ʮץ����|�C��</button></a>
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
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-6">
			<div class="panel panel-default">
				<div calss="panel-heading">
					<label>���|�ץ�s��:<%=caseRepVO.getCrepno()%></label>
				</div>

				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/back/caseRep/caseRep.do">
					<div class="panel-body">
						<div class="col-sm-6 form-group">
							<label>���|�H�|���s��:</label><input type="TEXT" name="repno"
								class="form-control" value="<%=caseRepVO.getRepno()%>" />
						</div>
						<div class="col-sm-6 form-group">
							<label>�����|�|���s��:</label><input type="TEXT" name="susno"
								class="form-control" value="<%=caseRepVO.getSusno()%>" />
						</div>
						<div class="col-sm-6 form-group">
							<label>�����|�X�ʮ׽s��:</label><input type="TEXT" name="repcaseno"
								class="form-control" value="<%=caseRepVO.getRepcaseno()%>" />
						</div>
						<div class="col-sm-12 form-group">
							<label>���|��]:</label><input type="TEXT" name="creprsn"
								class="form-control" size="50"
								value="<%=caseRepVO.getCreprsn()%>" />
						</div>
					</div>
					<div class="panel-footer">
						<input type="hidden" name="action" value="update"> <input
							type="hidden" name="crepno" value="<%=caseRepVO.getCrepno()%>">
						<input type="hidden" name="requestURL"
							value="<%=request.getParameter("requestURL")%>"> <input
							type="submit" class="btn btn-warning" value="�ק�">
					</div>
				</FORM>

			</div>
			<!-- /.panel -->
		</div>
	</div>


</div>
<jsp:include page="/b/frag/b_footer1.jsp" />

<jsp:include page="/b/frag/b_footer2.jsp" />