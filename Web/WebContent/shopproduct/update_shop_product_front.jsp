<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.shopproduct.model.*"%>
<%@ include file="/f/frag/f_login_redirect.file" %>
<%
	ShopproductVO spVO = (ShopproductVO) request.getAttribute("spVO");
%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<%  pageContext.setAttribute("context", request.getContextPath()); %>
<jsp:useBean id="shopSvc" scope="page" class="com.tao.shop.model.ShopService" />
<jsp:useBean id="subCategorySvc" scope="page" class="com.tao.category.model.SubCategoryService" />
<jsp:include page="/f/frag/f_header1.jsp"/>
<style> 
    section{
		height: 1400px;
    }
 </style>
<style>
 #preview1 ,#preview2,#preview3
    {
        /*width:80%;
        height:80%;*/
        width: 80px !important;
        width: 80px;
        max-width: 80px;
        height: 80px !important;
        height: 80px;
        max-height: 80px;
        border: 1px dashed #d1d0d0;
        text-align: center;
        
    }
</style> 
<title>編輯商店商品 | 饕飽地圖</title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<head></head>
<section>
	<div class="container">
		<div class="row">
			<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
				<c:if test="${not empty errorMsgs}">
					<font color='red'>請修正以下錯誤:
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li>${message}</li>
						</c:forEach>
					</ul>
					</font>
				</c:if>
				
				<div class="col-sm-9" style="height:1000px">
					<div class="contact-form">
	    				<h2 class="title text-center">
								編輯商店商品
	    				</h2>
	    				<div class="status alert alert-success" style="display: none"></div>
				    	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shopproduct/shopproduct.do" name="form1" enctype="multipart/form-data">
				           	<div class="form-group col-md-6">
								<label for="shopno">商店編號</label>
				                <input type="text" name="shopno" class="form-control" required="required" placeholder="商店編號" 
				                 value="${spVO.shopno}"  readonly>
				            </div>
				            <div class="form-group col-md-6">
								<label for="spno">商品編號</label>
				                <input type="text" name="spno" class="form-control" required="required" placeholder="商店編號" 
				                 value="<%= (spVO.getSpno()==null)? "" : spVO.getSpno()%>"  readonly>
				            </div>
				            <div class="form-group col-md-12">
								<label for="name"><i class="fa fa-fw fa-check-square"></i>商品名稱</label>
				                <input type="text" name="name" class="form-control" required="required" placeholder="商品名稱" 
				                 value="<%= (spVO.getName()==null)? "" : spVO.getName()%>">
				            </div>
				            <div class="form-group col-md-6">
								<label for="unitprice"><i class="fa fa-fw fa-check-square"></i>單價</label>
				                <input type="text" name="unitprice" class="form-control" required="required" placeholder="商店單價" 
				                 value="<%= ( spVO.getUnitprice()==null)? "" : spVO.getUnitprice()%>">
				            </div>
				            <div class="form-group col-md-6">
								<label for="subcatno"><i class="fa fa-fw fa-check-square"></i>商品分類</label>
						        <select size="1" name="subcatno" class="form-control" required="required">
							         <c:forEach var="subCategoryVO" items="${subCategorySvc.all}" > 
							          <option value="${subCategoryVO.subcatno}" ${(spVO.subcatno==subCategoryVO.subcatno)?'selected':'' }>${subCategoryVO.subcatname}
							         </c:forEach>      
						        </select>
				            </div>
				            <div class="form-group col-md-4">
								<label for="spec1"><i class="fa fa-fw fa-check-square"></i>規格1</label>	
				                <input type="text" name="spec1" class="form-control" required="required" placeholder="" 
				                 value="<%= (spVO.getSpec1()==null)? "" : spVO.getSpec1()%>">
				            </div> 
							<div class="form-group col-md-4">
								<label for="spec2">規格2</label>	
				                <input type="text" name="spec2" class="form-control"  placeholder="" 
				                  value="<%= (spVO.getSpec2()==null)? "" : spVO.getSpec2()%>">
				            </div> 
				            <div class="form-group col-md-4">
								<label for="spec3">規格3</label>	
				                <input type="text" name="spec3" class="form-control"  placeholder="" 
				                  value="<%= (spVO.getSpec3()==null)? "" : spVO.getSpec3()%>">
				            </div> 
				            <div class="form-group col-md-4">
								<label for="pic1"><i class="fa fa-fw fa-check-square"></i>商品圖片1</label>	
								<input type="file" name="pic1" accept="image/*" class="form-control" placeholder="商品圖片1 " 
								 value=""  onchange="preview1(this)"/>
							</div>

				            <div class="form-group col-md-4">
								<label for="pic2"><i class="fa fa-fw fa-check-square"></i>商品圖片2</label>	
								<input type="file" name="pic2" accept="image/*" class="form-control" placeholder="商品圖片2 " 
								 value=""  onchange="preview2(this)"/>
							</div>

				            <div class="form-group col-md-4">
								<label for="pic3"><i class="fa fa-fw fa-check-square"></i>商品圖片3</label>	
								<input type="file" name="pic3" accept="image/*" class="form-control" placeholder="商品圖片3 " 
								 value=""  onchange="preview3(this)"/>
							</div>
							<div class="form-group col-md-4">
								<div id="preview1" ><img src="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getPic1" width="80px" height="80px"/></div>
							</div>
							<div class="form-group col-md-4">
								<div id="preview2" ><img src="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getPic2" width="80px" height="80px"/></div>
							</div>
							<div class="form-group col-md-4">
								<div id="preview3" ><img src="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getPic3" width="80px" height="80px"/></div>
							</div>

							<div class="form-group col-md-12">
								<label for="pro_desc"><i class="fa fa-fw fa-check-square"></i>商品介紹</label>	
				                <textarea class="form-control" name="pro_desc" rows="10" cols="40"><%= (spVO.getPro_desc()==null)? "" : spVO.getPro_desc()%></textarea>
				            </div> 
				            <div class="form-group col-md-3">
								<label for="isrecomm"><i class="fa fa-fw fa-check-square"></i>是否推薦此商品</label>
								<select name="isrecomm" class="form-control" required="required">
									<option value="" <%= (spVO.getIsrecomm()==null)?"selected":"" %>>請選擇</option>
									<option value="1"<%= (spVO.getIsrecomm()==1)?"selected":"" %>>是</option>
									<option value="2"<%= (spVO.getIsrecomm()==2)?"selected":"" %>>否</option>
								</select>
				            </div>
				            <div class="form-group col-md-3">
				            	</br>
				            	<label><font color="#d43f3a"><i class="fa fa-fw fa-check-square"></i>號為必填欄位</font></label>
				            </div>
				             <div class="form-group col-md-6">
				                <input type="submit" class="btn btn-primary pull-right" value="修改">
				                <input type="hidden" name="action" value="update"/>
								<input type="hidden" name="spno"  value="<%= spVO.getSpno() %>" />						
				            </div>
				        </form>
	    			</div>
				</div><!--class="col-sm-9"-->
		</div>
	</div>
</section>
<jsp:include page="/f/frag/f_footer1.jsp"/>
<jsp:include page="/f/frag/f_footer2.jsp"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/b/res/js/jquery-1.11.0.js"></script>
<script>      
//載入本機圖片
function preview1(file) {

    if (file.files && file.files[0]) {
        var reader = new FileReader();
        reader.onload = function (evt) {
            $("#preview1").html( '<img width="80px" height="80px" src="' + evt.target.result + '" />');
        }
        $("#preview1").show();
        reader.readAsDataURL(file.files[0]);
    }
}
function preview2(file) {

    if (file.files && file.files[0]) {
        var reader = new FileReader();
        reader.onload = function (evt) {
            $("#preview2").html( '<img width="80px" height="80px" src="' + evt.target.result + '" />');
        }
        $("#preview2").show();
        reader.readAsDataURL(file.files[0]);
    }
}
function preview3(file) {

    if (file.files && file.files[0]) {
        var reader = new FileReader();
        reader.onload = function (evt) {
            $("#preview3").html( '<img width="80px" height="80px" src="' + evt.target.result + '" />');
        }
        $("#preview3").show();
        reader.readAsDataURL(file.files[0]);
    }
}
</script> 