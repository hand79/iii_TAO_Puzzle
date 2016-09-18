<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.caseRep.model.*"%>
<%@ page import="com.tao.shopRep.model.*"%>
<%	
	String resPath = request.getContextPath() + "/b/res";
	pageContext.setAttribute("resPath", resPath);

	String pageCaseRep = request.getContextPath() + "/caseRep";
	pageContext.setAttribute("pageCaseRep", pageCaseRep);

	String pageShopRep = request.getContextPath() + "/shopRep";
	pageContext.setAttribute("pageShopRep", pageShopRep);

	CaseRepVO caseRepVO = (CaseRepVO) request.getAttribute("caseRepVO");

	ShopRepVO shopRepVO = (ShopRepVO) request.getAttribute("shopRepVO");
%>

<jsp:include page="/b/frag/b_header1.jsp" flush="true" />
<title>���|�B�z | ��ݺ޲z�t��</title>
<jsp:include page="/b/frag/b_header2AndMenu.jsp" />

<div id="page-wrapper">

	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">���|�B�z</h1>

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
		<div class="col-lg-12">
			<div class="panel panel-primary">
				<div class="panel-heading">�X�ʮץ����|</div>
				<div class="panel-body">
					<div class="col-sm-4">
						<button class="btn btn-outline btn-primary" data-toggle="modal"
							data-target="#myModalC">�s�W�X�ʮ����|</button>
						<!-- Modal -->
						<div class="modal fade" id="myModalC" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
										<h4 class="modal-title" id="myModalLabel">
											�s�W�X�ʮ����|
											<c:if test="${not empty errorMsgs}">
												<font color='red'>�Эץ��H�U���~:
													<ul>
														<c:forEach var="message" items="${errorMsgs}">
															<li>${message}</li>
														</c:forEach>
													</ul>
												</font>
											</c:if>
										</h4>
									</div>
									<div class="modal-body">
										<FORM METHOD="post"
											ACTION="<%=request.getContextPath()%>/back/caseRep/caseRep.do"
											name="form1">
											<div class="col-sm-4 form-group">
												<label>���|�|���s��:</label><input type="TEXT"
													class="form-control" name="repno"
													value="<%=(caseRepVO == null) ? "1003" : caseRepVO.getRepno()%>"
													required="required" />
											</div>
											<div class="col-sm-4 form-group">
												<label> �����|�D�ʽs��:</label><input type="TEXT"
													class="form-control" name="susno"
													value="<%=(caseRepVO == null) ? "1001" : caseRepVO.getSusno()%>"
													required="required" />
											</div>
											<div class="col-sm-4 form-group">
												<label> �����|�X�ʮ׽s��:</label><input type="TEXT"
													class="form-control" name="repcaseno"
													value="<%=(caseRepVO == null) ? "5001" : caseRepVO.getRepcaseno()%>"
													required="required" />
											</div>

											<div class="form-group">
												<label>���|��]:</label>
												<textarea class="form-control" rows="5" name="creprsn"
													value="<%=(caseRepVO == null) ? "" : caseRepVO.getCreprsn()%>"
													required="required"></textarea>
											</div>
											<div class="modal-footer">
												<button type="reset" class="btn btn-default">Reset</button>
												<input type="hidden" name="action" value="insert"> <input
													type="submit" class="btn btn-outline btn-primary" value="�e�X�s�W">
											</div>
										</FORM>
									</div>

								</div>
								<!-- /.modal-content -->
							</div>
							<!-- /.modal-dialog -->
						</div>
						<!-- /.modal -->
					</div>
					<div class="col-sm-4">
						<a class="btn btn-outline btn-primary" href='<%=pageCaseRep%>/listAllCaseRep.jsp'>List</a>
						all CaseReports.
					</div>

					<div class="col-sm-4">
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/back/caseRep/caseRep.do">

							<label>��J�s�� :</label>

							<div class="col-sm-8 form-group">
								<input type="text" class="form-control" name="crepno">
							</div>
							<div class="col-sm-4 form-group">
								<input type="submit" class="btn btn-outline btn-primary" value="�d��">
							</div>
							<input type="hidden" name="action" value="getOne_For_Display">
						</FORM>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-green">
				<div class="panel-heading">�ө����|</div>
				<div class="panel-body">
					<div class="col-sm-4">
						<button class="btn btn-success btn-outline" data-toggle="modal"
							data-target="#myModal">�s�W�ө����|</button>
						<!-- Modal -->
						<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
										<h4 class="modal-title" id="myModalLabel">
											�s�W�ө����|
											<c:if test="${not empty errorMsgs}">
												<font color='red'>�Эץ��H�U���~:
													<ul>
														<c:forEach var="message" items="${errorMsgs}">
															<li>${message}</li>
														</c:forEach>
													</ul>
												</font>
											</c:if>
										</h4>
									</div>
									<div class="modal-body">
										<FORM METHOD="post"
											ACTION="<%=request.getContextPath()%>/back/shopRep/shopRep.do"
											name="form1">
											<div class="col-sm-6 form-group">
												<label>���|�|���s��:</label><input type="TEXT"
													class="form-control" name="repno"
													value="<%=(shopRepVO == null) ? "1003" : shopRepVO.getRepno()%>"
													required="required" />
											</div>
											<div class="col-sm-6 form-group">
												<label> �����|�ө��s��:</label><input type="TEXT"
													class="form-control" name="shopno"
													value="<%=(shopRepVO == null) ? "2003" : shopRepVO.getShopno()%>"
													required="required" />
											</div>

											<div class="form-group">
												<label>���|��]:</label>
												<textarea class="form-control" rows="5" name="sreprsn"
													value="<%=(shopRepVO == null) ? "" : shopRepVO.getSreprsn()%>"
													required="required"></textarea>
											</div>
											<div class="modal-footer">
												<button type="reset" class="btn btn-default">Reset</button>
												<input type="hidden" name="action" value="insert"> <input
													type="submit" class="btn btn-success btn-outline" value="�e�X�s�W">
											</div>
										</FORM>
									</div>

								</div>
								<!-- /.modal-content -->
							</div>
							<!-- /.modal-dialog -->
						</div>
						<!-- /.modal -->
					</div>
					<div class="col-sm-4">
						<a class="btn btn-outline btn-success"
							href='<%=pageShopRep%>/listAllShopRep.jsp'>List</a> all
						ShopReports.
					</div>
					<div class="col-sm-4">
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/back/shopRep/shopRep.do">

							<label>��J�s�� :</label>

							<div class="col-sm-8 form-group">
								<input type="text" class="form-control" name="srepno">
							</div>
							<div class="col-sm-4 form-group">
								<input type="submit" class="btn btn-success btn-outline" value="�d��">
							</div>
							<input type="hidden" name="action" value="getOne_For_Display">
						</FORM>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /.row -->
	
	
</div>
<!-- /#page-wrapper -->
<jsp:include page="/b/frag/b_footer1.jsp" />

<jsp:include page="/b/frag/b_footer2.jsp" />