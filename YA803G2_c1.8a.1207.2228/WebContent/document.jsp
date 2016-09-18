<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<% 
pageContext.setAttribute("context", request.getContextPath());
request.setAttribute("showBreadcrumb", new Object()); 

LinkedHashMap<String, String> breadmap = new LinkedHashMap<String, String>(); 
breadmap.put("�оǻ���", ""); 
request.setAttribute("breadcrumbMap", breadmap); 

%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<title>�оǻ��� | Ź���a��</title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	<section data-spy="scroll" data-target="#myScrollspy" >
		<div class="container">
			<div class="row">
				<div class="col-sm-3">
					<div class="brands_products"><!--brands_products-->
						<h2 class="title text-center">�оǻ���</h2>
						<div class="brands-name" id="myScrollspy">
							<ul class="nav nav-pills nav-stacked alsidetag" id="myNav">
								<li ><a href="#openShop"> �p��}�ө�</a></li>
								<li><a href="#openCase"> �p��o�_�X��</a></li>
								<li><a href="#joinCase"> �p��ѥ[�X��</a></li>
								<li><a href="#store"> �p���x��</a></li>
								<li><a href="#bonus"> �n�����y</a></li>
								<li><a href="#question"> �`�����D</a></li>

							</ul>
						</div>
					</div><!--/brands_products-->
				</div>
				<div class="col-sm-9 padding-right">
					<div><!--�̷s����-->
						<h2 class="title text-center" id="openShop">�p��}�ө�</h2>
						<div class="row">
							<div class="col-sm-12">
								<img src="${context}/f/res/images/read_doc/openShop.PNG" alt="" />
							</div>							
						</div>
						
						<h2 class="title text-center" id="openCase">�p��o�_�X��</h2>
						<div class="row">
							<div class="col-sm-12">
								<img src="${context}/f/res/images/read_doc/openCase.PNG" alt="" />
							</div>							
						</div>
						
						<h2 class="title text-center" id="joinCase">�p��ѥ[�X��</h2>
						<div class="row">
							<div class="col-sm-12">
								<img src="${context}/f/res/images/read_doc/joinCase.PNG" alt="" />
							</div>							
						</div>
						
						<h2 class="title text-center" id="store">�p���x��</h2>
						<div class="row">
							<div class="col-sm-12">
								<img src="${context}/f/res/images/read_doc/store.PNG" alt="" />
							</div>							
						</div>
						
						<h2 class="title text-center" id="bonus">�n�����y</h2>
						<div class="row">
							<div class="col-sm-12">
								<img src="${context}/f/res/images/read_doc/bonus.PNG" alt="" />
							</div>							
						</div>
						
						<h2 class="title text-center" id="question">�`�����D</h2>
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