<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ page import="com.tao.cases.model.*" %>
<%@ page import="com.tao.category.model.*" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="catSvc" class="com.tao.category.model.CategoryService"/>
<%pageContext.setAttribute("resPath", request.getContextPath() + "/f/res"); %>
<%pageContext.setAttribute("picPath", request.getContextPath() + "/caseproduct/Picture"); %>
<%pageContext.setAttribute("pageHome", request.getContextPath() + "/caseproduct"); %>
<c:set var="addOrEdit" value="${(CaseProductToEdit.lockflag == 0) ? '編輯':'建立' }"/>
<% 
	CaseProductVO vo = (CaseProductVO) request.getAttribute("CaseProductToEdit");
	if(vo != null){
		SubCategoryService subcatSvc = new SubCategoryService();
		request.setAttribute("SelectedSubCat", subcatSvc.getOneSubCategory(vo.getSubcatno()));
		Integer catno = subcatSvc.getOneSubCategory(vo.getSubcatno()).getCatno();
		request.setAttribute("SubCategorySet", subcatSvc.getAllByCategory(catno));
	}
%>


<jsp:include page="/f/frag/f_header1.jsp"/>
  <STYLE>
	.modal-open{
		overflow: initial;
	}
	/*.preview{
		width: 220px;
	}*/
  </STYLE>
	
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	<section>
		<div class="container">
			<div class="row">
<jsp:include page="/f/frag/f_memCenterMenu.jsp" />
				<div class="col-sm-9" style="height:${(not empty CaseProductToEdit) ? '1500px': '1500px'}">
					
					<div class="contact-form">
	    				<h2 class="title text-center">${addOrEdit}合購商品</h2>
	    				<div class="status alert alert-success" style="display: none"></div>
				    	<form id="main-contact-form" class="contact-form row" name="contact-form" method="get" action="/index.htm" enctype="multipart/form-data">
				            <div class="form-group col-md-12">
								<label for="cp-name"><i class="fa fa-fw fa-check-square"></i>商品名稱 </label>	
								<input type="text" name="cp-name" class="form-control" id="cp-name" placeholder="商品名稱" required="required" value="${CaseProductToEdit.name}"/>
							</div>
							<div class="form-group col-md-4">
								<label for="cp-price"><i class="fa fa-fw fa-check-square"></i>單價</label>	
								<input type="number" name="cp-price" class="form-control" id="cp-price" placeholder="商品單價" min="0" required="required" value="${CaseProductToEdit.unitprice}"/>
							</div> 
							<div class="form-group col-md-4">
								<label for="cp-desc"><i class="fa fa-fw fa-check-square"></i>商品分類</label>	
								<select class="form-control" id="catList">
									<option value="-1">(請先選擇分類)</option>
				<c:forEach var="catvo" items="${catSvc.all}">
									<option value="${catvo.catno}" ${(SelectedSubCat.catno == catvo.catno)?'selected':'' }>${catvo.catname}</option>
				</c:forEach>
								</select>
							</div>
							<div class="form-group col-md-4">
								<label for="cp-desc"><i class="fa fa-fw fa-check-square"></i>商品子分類</label>
								<c:if test="${empty SelectedSubCat}">	
									<select class="form-control" name="cp-subcatno" id="subcatList">
									</select>
								</c:if>
								<c:if test="${not empty SelectedSubCat}">
									<select class="form-control" name="cp-subcatno" id="subcatList"">
										<jsp:include page="/caseproduct/Ajax_findSubcategory.jsp"/>
									</select>
								</c:if>
							</div> 
							<div class="form-group col-md-4">
								<label for="cp-pic1">商品圖片1<br>顯示尺寸：330 x 380 像素<br>大小限制：800KB</label>	
								<input type="file" name="pic1" class="form-control pic1" id="cp-pic1"/>
							</div>
							<div class="form-group col-md-4">
								<label for="cp-pic2">商品圖片2<br>顯示尺寸：330 x 380 像素<br>大小限制：800KB</label>	
								<input type="file" name="pic2" class="form-control pic2" id="cp-pic2" />
							</div>
							<div class="form-group col-md-4">
								<label for="cp-pic3">商品圖片3<br>顯示尺寸：330 x 380 像素<br>大小限制：800KB</label>	
								<input type="file" name="pic3" class="form-control pic3" id="cp-pic3"/>
							</div>
							
							<!-- Picture Preview Araa -->
							
							<div class="form-group col-md-4">
								<c:choose>
								<c:when test="${empty CaseProductToEdit}">
									<div style="display:none">
									<label style="margin-left: 24px;">預覽</label>
									<img class="preview pic1" style="margin-left: 24px;"/>
									</div>
								</c:when>
								<c:when test="${not empty CaseProductToEdit.pmime1}">
									<label style="margin-left: 24px;">圖片1</label>
									<img src="${picPath}?cpno=${CaseProductToEdit.cpno}&pic=1&resize=220" class="preview pic1" style="margin-left: 24px;"/>
								</c:when>
								<c:when test="${empty CaseProductToEdit.pmime1}">
									<div style="display:none">
									<label style="margin-left: 24px;">預覽</label>
									<img class="preview pic1" style="margin-left: 24px;"/>
									</div>
									<div style="text-align: center; color: #BBB; margin-top: 60px; font-weight: bold; font-size: 2em;" class="no-pic"><p>尚未上傳圖片</p></div>
								</c:when> 
								</c:choose>
							</div> 
							<div class="form-group col-md-4">
								<c:choose>
								<c:when test="${empty CaseProductToEdit}">
									<div style="display:none">
									<label style="margin-left: 24px;">預覽</label>
									<img class="preview pic2" style="margin-left: 24px;"/>
									</div>
								</c:when>
								<c:when test="${not empty CaseProductToEdit.pmime2}">
									<label style="margin-left: 24px;">圖片2</label>
									<img src="${picPath}?cpno=${CaseProductToEdit.cpno}&pic=2&resize=220" class="preview pic2" style="margin-left: 24px;"/>
								</c:when>
								<c:when test="${empty CaseProductToEdit.pmime2}">
									<div style="display:none">
									<label style="margin-left: 24px;">預覽</label>
									<img class="preview pic2" style="margin-left: 24px;"/>
									</div>
									<div style="text-align: center; color: #BBB; margin-top: 60px; font-weight: bold; font-size: 2em;" class="no-pic"><p>尚未上傳圖片</p></div>
								</c:when> 
								</c:choose>
							</div>
							<div class="form-group col-md-4">
								<c:choose>
								<c:when test="${empty CaseProductToEdit}">
									<div style="display:none">
									<label style="margin-left: 24px;">預覽</label>
									<img class="preview pic3" style="margin-left: 24px;"/>
									</div>
								</c:when>
								<c:when test="${not empty CaseProductToEdit.pmime3}">
									<label style="margin-left: 24px;">圖片3</label>
									<img src="${picPath}?cpno=${CaseProductToEdit.cpno}&pic=3&resize=220" class="preview pic3" style="margin-left: 24px;"/>
								</c:when>
								<c:when test="${empty CaseProductToEdit.pmime3}">
									<div style="display:none">
									<label style="margin-left: 24px;">預覽</label>
									<img class="preview pic3" style="margin-left: 24px;"/>
									</div>
									<div style="text-align: center; color: #BBB; margin-top: 60px; font-weight: bold; font-size: 2em;" class="no-pic"><p>尚未上傳圖片</p></div>
								</c:when> 
								</c:choose>
							</div>
							
							<div class="form-group col-md-12">
								<label for="cp-desc" style="font-size: 1.3em; font-family: 微軟正黑體">商品內容敘述</label>	
								<textarea name="cp-cpdesc" class="form-control" id="cp-desc">${CaseProductToEdit.cpdesc}</textarea>
							</div> 
				             
				            <div class="form-group col-md-12">
				            	<a style="cursor: help;" id="magicBtn"><span style="color: #d43f3a; font-weight: bolder;"><i class="fa fa-fw fa-check-square"></i>為必填</span></a>
				                <span class="pull-right">
				                <button style="margin-top:20px; margin-bottom: 15px;"class="btn btn-default" id="cancel-page">取消</button>
				                <button style="margin-left:20px"class="btn btn-primary" id="popConfirm">${addOrEdit }</button>
				                </span>
				            </div>
							<div style="clear:both"><input type="hidden" name="action" value="AddOrUpdate"/><input type="hidden" name="cpno" value="${CaseProductToEdit.cpno}"/></div>
				        </form>
	    			</div>
				</div><!--class="col-sm-9"-->
			</div>
		</div>
	</section>
	<div class="modal fade " id="addCP"  tabindex="-1" role="dialog" aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 400px;">
		    <div class="modal-content">
				 <div class="modal-header">
					<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button> -->
					<h4 class="modal-title title" id="add" style="color:#FE980F; font-family: 微軟正黑體; font-weight: bold; font-size: 1.8em;">
					   ${addOrEdit}合購商品
					</h4>
				 </div>
				 <div class="modal-body" id="responseArea">
				 <p>確定要${addOrEdit}商品？</p>
				 </div>
				 <div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						取消
					</button>
					<button type="submit" class="btn btn-primary" style="margin-top: 0px;" id="submitForm">
					   確定
					</button>
				 </div>
		    </div><!-- /.modal-content -->
		</div>
	</div>
	<div class="modal fade " id="cancelAsk"  tabindex="-1" role="dialog" aria-labelledby="ttt" aria-hidden="true">
		<div class="modal-dialog">
		    <div class="modal-content">
				 <div class="modal-header">
					<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button> -->
					<h4 class="modal-title title" id="ttt" style="color:#FE980F; font-family: 微軟正黑體; font-weight: bold; font-size: 1.8em;">
					   取消${addOrEdit}合購商品
					</h4>
				 </div>
				 <div class="modal-body" id="responseArea">
				 <p>確定要取消${addOrEdit}？</p>
				 </div>
				 <div class="modal-footer">
					<button type="submit" class="btn btn-default" id="cancel-confirm">
					   確定取消
					</button>
					<button type="button" class="btn btn-primary" style="margin-top: 0px;" data-dismiss="modal">
						不要取消
					</button>
				 </div>
		    </div><!-- /.modal-content -->
		</div>
	</div>
<jsp:include page="/f/frag/f_footer1.jsp"/>
<script>
	var CurrentPageEnv = {
		controller:'${pageHome}/caseProduct.do',
		beforeSendMsg:'執行中...&nbsp;&nbsp;<i class="fa fa-spin fa-spinner" style="font-size:2em; color:#FE980F"></i>',
		completedMsg:'<i class="fa fa-check" style="font-size:1.5em; color:#5D5;"></i>${addOrEdit}成功，頁面將在2秒後返回合購商品列表',
		oriMsg:'確定要${addOrEdit}商品？',
		errMsg:'<span style="color: #D55; font-size:1.5em;"><i class="fa fa-exclamation-triangle"></i> Oops...發生錯誤</span>',
		cancel:'${pageHome}/caseProduct.do?action=viewOwnCPs',
		relogin:'登入已逾時，請重新登入。',
		loginPage:'<%=request.getContextPath()%>/LoginPortal.jsp',
		preview:'預覽'
	};
</script>

<script src="${pageHome }/js/addoreditcaseproduct.js" ></script>

<ckeditor:replace replace="cp-desc" basePath="${resPath}/ckeditor/" />
<script>
	CKEDITOR.config.height="660px";
	CKEDITOR.config.resize_enabled =false;
</script>
<script>
	menuTrigger(0);
	$('#magicBtn').click(function(){
		$('#cp-name').val('江南名蟹 新鮮肥美秋令大閘蟹！一年必吃一次！');
		$('#cp-price').val('300');
		CKEDITOR.instances['cp-desc'].setData('<br /><p>每年中秋時分，菊黃蟹肥！就是大閘蟹當造之時！<br />* 隻隻新鮮！蟹黃濃郁！江南大閘蟹！<br />* 足斤足兩出貨！絕不含繩重！</p><p>商品規格<br />產地：台灣<br />重量 ：3.0兩 &plusmn;10兩<br />保存期限：生鮮活體請於貨到立級烹調或2日內食用。<br />包裝方式：多層續溫紙盒。<br />注意事項：收貨時應持續保持冷藏。</p>');
	});
</script>
</body>
</html>