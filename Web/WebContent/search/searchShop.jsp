<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.searchresult.model.*"%>
<%@ page import="com.tao.location.model.*"%>
<%@ page import="java.util.*" %>
<%-- <%
	shopResultService dao = new shopResultService();
	List<shopResultVO> list = dao.findShopByNameKey("包");
    pageContext.setAttribute("list",list);
%> --%>
<%
	List<shopResultVO> list = (List<shopResultVO>)request.getAttribute("list");
	//pageContext.setAttribute("list",list); 
	
	Map<Integer, LocationVO> map = new HashMap<Integer, LocationVO>();
	for(LocationVO vo : new LocationService().getAll()){
		map.put(vo.getLocno(), vo);
	}
	pageContext.setAttribute("locMap", map);
%>

<jsp:include page="/f/frag/f_header1.jsp"/>
<title> 搜尋合購 </title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>


	<section><!--form-->
		<div class="container">
			<div class="row">
			<jsp:include page="/f/frag/f_catMenu.jsp"/>
			
				<div class="col-sm-8 col-sm-offset-1">
					<h2 class="text-center title">搜尋店家</h2>
					<div class="error handling" style="color: red">
					
						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<div class="text-center">
								<c:forEach var="message" items="${errorMsgs}">
									<p><li>${message}</li></p>
								</c:forEach>
							</div>
							
						</c:if>
					</div>		
				
					<div class="search_results"><!--search_results-->
						<c:if test="${not empty list}">
					
							<!--search_results rows-->
							<%@ include file="/search/page1.file" %>
							<c:forEach var="shopResult" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
								<c:set var="shopResult" value="${shopResult}"/>
								<% String desc = ((shopResultVO) pageContext.getAttribute("shopResult")).getPro_desc(); %>
								<table class="table table-striped table-bordered table-hover table-condensed table-responsive" style="font-family: 微軟正黑體;">
			
									<tr>
										<td style="width:200px; height: 40px;"> <i class="fa fa-gift"></i> <!-- shop title --> ${shopResult.title}</td>
										
										<td ><font color="green"><i class="fa fa-bookmark"><!-- product name -->  ${shopResult.name} </i> </font> </td>	
										<td><span class="pull-right">${locMap.get(shopResult.locno).county} &gt; ${locMap.get(shopResult.locno).town}</span></td>
									</tr>
									
									<tr>
										<td style="width:200px; height: 80px;">
										<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopResult.shopno}&action=getOne_For_Display">
											<!-- shop logo pic --> <img style="width:200px; height: 80px;" src="<%=request.getContextPath()%>/DBShopImgReader?shopno=${shopResult.shopno}">
										</a>
										</td>
										<td rowspan="2" style="width:160px; height: 120px;"><!-- pic of product --> 
										<a href="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${shopResult.spno}&shopno=${shopResult.shopno}&action=getOne_For_Display">
											<img style="width:160px; height: 120px;" src="<%=request.getContextPath()%>/DBShopProdImgReader?spno=${shopResult.spno}">
										</a>
										</td>
										<td rowspan="2" style="padding: 25px 15px;"><!-- product name --> <!-- product desc --> <%= desc == null ? "" : desc.length() > 160 ? desc.substring(0,160) + "...": desc %></td>										
									</tr>
									
									<tr>
										<td style="width:200px; height: 40px;">HOT 人氣: <font color="red"> ${shopResult.hits}</font> 人 </td>
										
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
<script>$('a[href="#byShop"]').click();</script>
</body>
</html>
