<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<% 
pageContext.setAttribute("context", request.getContextPath());
request.setAttribute("showBreadcrumb", new Object()); 

LinkedHashMap<String, String> breadmap = new LinkedHashMap<String, String>(); 
breadmap.put("教學說明", ""); 
request.setAttribute("breadcrumbMap", breadmap); 

%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<title>教學說明 | 饕飽地圖</title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	<section data-spy="scroll" data-target="#myScrollspy" >
		<div class="container">
			<div class="row">
				<div class="col-sm-3">
					<div class="brands_products"><!--brands_products-->
						<h2 class="title text-center">教學說明</h2>
						<div class="brands-name" id="myScrollspy">
							<ul class="nav nav-pills nav-stacked alsidetag" id="myNav">
								<li ><a href="#openShop"> 如何開商店</a></li>
								<li><a href="#openCase"> 如何發起合購</a></li>
								<li><a href="#joinCase"> 如何參加合購</a></li>
								<li><a href="#store"> 如何儲值</a></li>
								<li><a href="#bonus"> 積分獎勵</a></li>
								<li><a href="#question"> 常見問題</a></li>

							</ul>
						</div>
					</div><!--/brands_products-->
				</div>
				<div class="col-sm-9 padding-right">
					<div><!--最新消息-->
						<h2 class="title text-center" id="openShop">如何開商店</h2>
						<div class="row">
							<div class="col-sm-12">
								<img src="${context}/f/res/images/read_doc/openShop.PNG" alt="" />
							</div>							
						</div>
						
						<h2 class="title text-center" id="openCase">如何發起合購</h2>
						<div class="row">
							<div class="col-sm-12">
								<img src="${context}/f/res/images/read_doc/openCase.PNG" alt="" />
							</div>							
						</div>
						
						<h2 class="title text-center" id="joinCase">如何參加合購</h2>
						<div class="row">
							<div class="col-sm-12">
								<img src="${context}/f/res/images/read_doc/joinCase.PNG" alt="" />
							</div>							
						</div>
						
						<h2 class="title text-center" id="store">如何儲值</h2>
						<div class="row">
							<div class="col-sm-12">
								<img src="${context}/f/res/images/read_doc/store.PNG" alt="" />
							</div>							
						</div>
						
						<h2 class="title text-center" id="bonus">積分獎勵</h2>
						<div class="row">
							<div class="col-sm-12">
								<img src="${context}/f/res/images/read_doc/bonus.PNG" alt="" />
							</div>							
						</div>
						
						<h2 class="title text-center" id="question">常見問題</h2>
						<div class="row">
							<div class="col-sm-12">
								<img src="${context}/f/res/images/read_doc/question.PNG" alt="" />
							</div>							
						</div>		
						
					</div>
				</div>
			</div>
		</div>
	</section>
<jsp:include page="/f/frag/f_footer1.jsp"/>
		
</body>
</html>