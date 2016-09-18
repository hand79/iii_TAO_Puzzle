<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.shopRep.model.*"%>

<%
	ShopRepVO shopRepVO = (ShopRepVO) request.getAttribute("shopRepVO");
%>

<%
	String resPath = request.getContextPath() + "/b/res";
	pageContext.setAttribute("resPath", resPath);

	String contextPath = request.getContextPath() + "/shopRep";
	pageContext.setAttribute("contextPath", contextPath);
%>

<jsp:include page="/b/frag/b_header1.jsp" flush="true" />
<title>檢舉處理 | 後端管理系統</title>
<jsp:include page="/b/frag/b_header2AndMenu.jsp" />

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header">商店檢舉</h3>
			<a href="<%=request.getContextPath()%>/shopRep/listAllShopRep.jsp"><button
					type="button" class="btn btn-link">回商店檢舉列表</button></a>
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
	</div>
	<div class="row">
		<div class="col-sm-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<label>檢舉案件編號: <%=shopRepVO.getSrepno()%></label>
				</div>

				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/back/shopRep/shopRep.do"
					name="form1">
					<div class="panel-body">
						<div class="col-sm-6 form-group">
							<label>檢舉人會員編號:</label> <input type="TEXT" class="form-control"
								name="repno" value="<%=shopRepVO.getRepno()%>" />
						</div>
						<div class="col-sm-6 form-group">
							<label>受檢舉商店編號:</label> <input type="TEXT" class="form-control"
								name="shopno" value="<%=shopRepVO.getShopno()%>" />
						</div>
						<div class="col-sm-12 form-group">
							<label>檢舉原因:</label> <input type="TEXT" class="form-control"
								size="50" name="sreprsn" value="<%=shopRepVO.getSreprsn()%>" />
						</div>
					</div>
					<div class="panel-footer">
						<button type="reset" class="btn btn-default">Reset</button>
						<input type="hidden" name="action" value="update"> <input
							type="hidden" name="srepno" value="<%=shopRepVO.getSrepno()%>">
						<input type="hidden" name="requestURL"
							value="<%=request.getParameter("requestURL")%>"> <input
							type="submit" class="btn btn-default" value="送出修改">
					</div>
				</FORM>


			</div>
		</div>
	</div>
</div>
<jsp:include page="/b/frag/b_footer1.jsp" />

<jsp:include page="/b/frag/b_footer2.jsp" />