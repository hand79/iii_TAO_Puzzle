<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tao.jimmy.location.*"%>
<%	
	CountyService countyService = new CountyService();
	Set<CountyVO> counties = countyService.findCounties();
	pageContext.setAttribute("counties", counties);
%>

            <div class="row">
                <div class="col-lg-12">
					<div class="panel panel-default">
                        <div class="panel-heading">
							<h4 style="font-family: �L�n������; margin: 0px"><b>�j�M</b></h4>
                        </div>
                        <div class="panel-body">		
                        <div class="row">
                         <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/shop/shop.do" name="form1"  role="form">					
								<div class="col-sm-2">
									<div class="form-group">
										<label>�ө��|���s��</label>
										<input type="text"  class="form-control" name="memno" value=""  id="checknum" onkeyup="numcheck(this.id,this)">
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>�ө��s��</label>
										<input type="text"  class="form-control" name="shopno" value=""  id="checknum" onkeyup="numcheck(this.id,this)">
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>�ө��W��</label>
										<input type="text" name="title" value="" class="form-control" >
									</div>
								</div>
								
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>����</label>

									       
							<select class="form-control" id="county-list" size="1"  >
									<option value="">-</option>
								<c:forEach var="countyVO" items="${counties}">
									<option value="${countyVO.value}" ${requestScope.countyVO.name == countyVO.name ? 'selected':'' }>${countyVO.name}</option>
								</c:forEach>
							</select>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>�m����</label>
				                <select class="form-control" name="locno" id="town-list" >
									<c:if test="${empty towns}">
									<option value="">-</option>
									</c:if>
									<c:if test="${not empty towns }">
										<jsp:include page="/cases/Ajax_findTown.jsp"/>
									</c:if>
								</select>
									</div>
								</div>								
								<div class="col-sm-2">
									<div class="form-group">
										<label>�ө����A</label>
											<select size="1" name="status"  class="form-control" >
													<option value=""  selected>�п��</option>
													<option value="0" >��</option>
													<option value="1">�f�֤�</option>
													<option value="2">�W�[��</option>
													<option value="3">�w�U�[</option>
													<option value="5">���q�L�f��</option>
									       </select>
									</div>
								</div>
								</div>
								<div class="row">
								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label>
										<button class="form-control btn btn-success"><i class="fa fa-search"></i> �j�M</button>
										<input type="hidden" name="action" value="listShop_ByCompositeQuery">
									</div>
								
								</div>	
								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label>
										<button type="reset" class="form-control btn btn-warning">�M��</button>
									</div>
								</div>	
							</FORM>
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/shop/shop.do" name="form1">
								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label>
										<button class="form-control btn btn-info">�C�X����</button>
										<input type="hidden" name="action" value="queryAll">
									</div>
								</div>	
							</FORM>
							</div>
						</div>
                      
                    </div><!--/.panel .panel-info-->
				</div>
			</div>
			
			
			