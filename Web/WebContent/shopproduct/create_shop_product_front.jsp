<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.shopproduct.model.*"%>
<%@ include file="/f/frag/f_login_redirect.file" %>
<%
	ShopproductVO spVO = (ShopproductVO) request.getAttribute("spVO");
%>
<%	pageContext.setAttribute("context", request.getContextPath()); %>
<jsp:useBean id="subCategorySvc" scope="page" class="com.tao.category.model.SubCategoryService" />
<jsp:useBean id="shopSvc" scope="page" 	class="com.tao.shop.model.ShopService" />
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
        width: 100px !important;
        width: 100px;
        max-width: 100px;
        height: 100px !important;
        height: 100px;
        max-height: 100px;
        border: 1px dashed #d1d0d0;
        text-align: center;
        display: none;
    }
</style>  
<title>建立商店商品 | 饕飽地圖</title>
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
	    				<h2 class="title text-center">建立商店商品</h2>
	    				<div class="status alert alert-success" style="display: none"></div>
				    	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shopproduct/shopproduct.do" name="form1" enctype="multipart/form-data">
				            <div class="form-group col-md-12">
								<label for="shopno"><i class="fa fa-fw fa-check-square"></i>商店編號</label>
				                <input type="text" name="shopno" class="form-control" required="required" 
				                 value="${param.shopno}" readonly>
				            </div>
				            <div class="form-group col-md-12">
								<label for="name"><i class="fa fa-fw fa-check-square"></i>商品名稱</label>
				                <input type="text" name="name" class="form-control" required="required" placeholder="奶凍捲" 
				                 value="<%= (spVO==null)? "" : spVO.getName()%>">
				            </div>
				            <div class="form-group col-md-6">
								<label for="unitprice"><i class="fa fa-fw fa-check-square"></i>單價</label>
				                <input type="text" name="unitprice" class="form-control" required="required" placeholder="100.00" 
				                 value="<%= (spVO==null)? "" : spVO.getUnitprice()%>">
				            </div>
				            <div class="form-group col-md-6">
								<label for="subcatno"><i class="fa fa-fw fa-check-square"></i>商品分類</label>
						        <select size="1" name="subcatno" class="form-control" required="required">
						         	<c:forEach var="subCategoryVO" items="${subCategorySvc.all}" > 
						         		<option value="${subCategoryVO.subcatno}">${subCategoryVO.subcatname}
						         	</c:forEach>     
						        </select>
				            </div>
				            <div class="form-group col-md-4">
								<label for="spec1"><i class="fa fa-fw fa-check-square"></i>規格1</label>	
				                <input type="text" name="spec1" class="form-control" required="required" placeholder="香草 "<%= (spVO==null)? "" : spVO.getSpec1()%>">
				            </div> 
							<div class="form-group col-md-4">
								<label for="spec2">規格2</label>	
				                <input type="text" name="spec2" class="form-control"  placeholder="巧克力" 
				                 value="<%= (spVO==null)? "" : spVO.getSpec2()%>">
				            </div> 
				            <div class="form-group col-md-4">
								<label for="spec3">規格3</label>	
				                <input type="text" name="spec3" class="form-control"  placeholder="芋頭" 
				                 value="<%= (spVO==null)? "" : spVO.getSpec3()%>">
				            </div> 
				            <div class="form-group col-md-4">
								<label for="pic1"><i class="fa fa-fw fa-check-square"></i>商品圖片1</label>	
								<input type="file" name="pic1" accept="image/*" class="form-control" placeholder="商品圖片1 " required="required"
								 value="<%= (spVO==null)? "" : spVO.getPic1()%>"  onchange="preview1(this)"/>
							</div>

				            <div class="form-group col-md-4">
								<label for="pic2"><i class="fa fa-fw fa-check-square"></i>商品圖片2</label>	
								<input type="file" name="pic2" accept="image/*" class="form-control" placeholder="商品圖片2 " required="required"
								 value="<%= (spVO==null)? "" : spVO.getPic2()%>"  onchange="preview2(this)"/>
							</div>

				            <div class="form-group col-md-4">
								<label for="pic3"><i class="fa fa-fw fa-check-square"></i>商品圖片3</label>	
								<input type="file" name="pic3" accept="image/*" class="form-control" placeholder="商品圖片3 " required="required"
								 value="<%= (spVO==null)? "" : spVO.getPic3()%>"  onchange="preview3(this)"/>
							</div>
							<div class="form-group col-md-4">
								<div id="preview1" ></div>
							</div>
							<div class="form-group col-md-4">
								<div id="preview2" ></div>
							</div>
							<div class="form-group col-md-4">
								<div id="preview3" ></div>
							</div>

							<div class="form-group col-md-12">
								<label for="pro_desc"><i class="fa fa-fw fa-check-square"></i>商品介紹</label>	
				                <textarea class="form-control" name="pro_desc" rows="10" cols="40"  placeholder="純天然絕無添加餿水油~"><%= (spVO==null)? "" : spVO.getPro_desc()%></textarea>
				            </div> 
				            <div class="form-group col-md-3">
								<label for="isrecomm"><i class="fa fa-fw fa-check-square"></i>是否推薦此商品</label>
								<select name="isrecomm" class="form-control" required="required">
									<option value="" <%= (spVO==null)?"selected":"" %>>請選擇</option>
									<option value="1" <%= (spVO==null)?"":"" %>>是</option>
									<option value="2" <%= (spVO==null)?"":"" %>>否</option>
								</select>
				            </div>
				            <div class="form-group col-md-3">
				            	<br/>
				            	<label><font color="#d43f3a"><i class="fa fa-fw fa-check-square"></i>號為必填欄位</font></label>
				            </div>
				             <div class="form-group col-md-6">
				             	<div class="pull-right">
				                <span style="margin-top: 16px; margin-right: 20px;" class=" btn btn-default" id="createMagic"><i class="fa fa-fw fa-magic fa-lg"></i>神奇小按鈕</span>
				               <input type="submit" class="btn btn-primary " value="建立">
				                </div>
				                <input type="hidden" name="action" value="insert">
				            </div>
				        </form>
	    			</div>
				</div><!--class="col-sm-9"-->
		</div>
	</div>
</section>
<jsp:include page="/f/frag/f_footer1.jsp"/>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/b/res/js/jquery-1.11.0.js"></script> --%>
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

$('#createMagic').click(function(){
	$( "input[name='name']" ).val( "札幌味噌拉麵" );
	$( "input[name='unitprice']" ).val( "66" );
	$( "input[name='spec1']" ).val( "正港拉麵條" );
	$( "input[name='spec2']" ).val( "日本湯底" );
	$( "input[name='spec3']" ).val( "豐富配料包" );	
	$( "select[name='subcatno']" ).val( "59" );
	$( "select[name='isrecomm']" ).val( "1" );
	$( "textarea[name='pro_desc']" ).val( "日本原廠製造的拉麵，口感自然、香Q滑細，烹調非常容易\n"+
			"只要水滾後把麵拉鬆下鍋，用筷子攪動一下，約2~3分即可！\n"+
			"不管是搭配晴晴日式高湯，或是各式醬料(紅燒牛肉、義大利麵醬)\n"+
			"都非常好吃，讓你在家享用日式美味~\n"+
			"\n"+
			"*札幌味噌湯頭:\n"+
			"內容量: 40g *3包\n"+
			"原料: 豬雞高湯，醬油，食鹽，砂糖，焦糖，豬油。\n"+
			"做法: 湯頭加入400cc的水稀釋，放入配料煮滾。\n"+
			"GMP食品認證工廠。\n"+
			"\n"+
			"*配料包:（需冷凍保存）\n"+
			"內容物 : 2片腿肉片+好心腸+魚板+筍乾+海帶牙+玉米。\n"+
			"\n"+
			"*保存期限與方法:務必冷凍保存\n"+
			"\n"+
			"大部分的麵條都是使用麵粉（小麥粉、強力粉）、水、鹽，以及「鹼水」（かんすい，又被音譯為「甘素」）為原料，顏色大多是黃色。鹼水是指碳酸鉀和碳酸鈉的混合物（有時也會加入磷酸）。這是由於曾有人使用內蒙古的湖水來製作麵條，結果發現麵條變得更加好吃，因此研究了湖水的成份之後，發展出了這樣的配方。鹼水是屬於鹼性，會讓麵粉中的穀蛋白黏膠質產生性質變化，讓麵條具有光澤感和增加彈性，也會讓麵粉中的黃酮類變成黃色，讓麵條具有獨特的顏色。\n"+
			"日本戰後有一段時期，出現了許多品質惡劣的「鹼水」，並且對健康可能會有不良的影響。現在日本農林規格（JAS）制定了成份的規定，鹼水已經不再有安全性的問題了。此外，也有因為不喜歡鹼水那種獨特的味道，而改用雞蛋取代的做法。水含量（加水率）較高的麵條較柔軟，耐浸泡；水含量低的麵條比較有勁，能夠更多地吸收湯的味道，但容易泡爛。一般麵條中水的比例大約為20%到40%。\n" );
});
</script>  
<jsp:include page="/f/frag/f_footer2.jsp"/>