<%@page import="com.tao.jimmy.util.AreaNames"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	pageContext.setAttribute("context", request.getContextPath());
	pageContext.setAttribute("resPath", request.getContextPath() + "/f/res");
%>
</head>
<!--/head-->
<body style="overflow: initial;">
<header id="header">
		<!--header-->
		<!-- <div class="header-top"></div> -->
		<!--/header_top-->

		<div class="header-middle">
			<!--header-middle-->
			<div class="container">
				<div class="row" style="border-bottom: 0px;">
					<div class="col-sm-4">
						<div class="logo pull-left">
							<a href="${context}"><img src="${resPath}/images/Logo.png"/></a>
						</div>
<!-- 						<div class="btn-group pull-right"> -->
<!-- 							<div class="btn-group"></div> -->

<!-- 							<div class="btn-group"></div> -->
<!-- 						</div> -->
					</div>
					<div class="col-sm-8" id="header-middle-nav-zone">
						<div class="shop-menu pull-right">
							<ul class="nav navbar-nav">
								<li><a href="${context}/MemberCenter"><i class="fa fa-user"></i> �|������</a></li>
								<li><a href="${context}/wishList/wishList.do?action=listWishList"><i class="fa fa-star"></i> �l�ܲM��</a></li>
								<li><a href="${context}/select_page_wallet.jsp"><i class="fa fa-usd"></i> �q�l���]</a></li>
								<c:if test="${empty sessionScope.CurrentUser}">
								<li><a href="<%=request.getContextPath()%>/member/front/mainMemberSignup.jsp"><i class="fa fa-puzzle-piece"></i> ���U</a></li>
								<li><a href="#" id="i-want-to-login"><i class="fa fa-sign-in"></i> �n�J</a></li>
								</c:if>
								<c:if test="${not empty sessionScope.CurrentUser}">
								<li><a><i class="fa fa-bookmark-o"></i> �A�n�A${CurrentUser.lname} ${CurrentUser.fname}</a></li>
								<li><a href="${context}/MemberLogin?action=logout" id="i-want-to-logout">�n�X <i class="fa fa-sign-out"></i></a></li>
								</c:if>
							</ul>
						</div>
						<c:if test="${empty sessionScope.CurrentUser}">
						<div class="modal fade" id="loginModal"  tabindex="-1" role="dialog" aria-labelledby="loginWindow" aria-hidden="true">
							<div class="modal-dialog" style="top:120px; width: 300px; height:200px;">
							    <div class="modal-content">
									 <div class="modal-header" style="display:none">
										<h4 class="modal-title" id="loginWindow"></h4>
									 </div>
									 <form id="login-form">
									 <div class="modal-body">
									 <h2 class="text-center title">�n�J�|��</h2>
									 <div class="msgArea" style="display: none; color: red; font-size: bolder;">�o�Ϳ��~</div>
									 <div class="form-group">
										 <label for="username">�b�� �� E-Mail</label>
										 <input class="form-control" name="username" type="text"/>
									 </div>
									 <div class="form-group">
										 <label for="password">�K�X</label>
										 <input class="form-control" name="password" type="password"/>
									 </div>
									 <p><a href="<%=request.getContextPath()%>/member/front/mainMemberSignup.jsp">���U�s�b��</a></p>
									 </div>
									 <div class="text-center" style="margin-bottom: 40px;"><button class="btn btn-primary" id="login-confirm" style="width: 80%;">�n�J</button></div></form>
							    </div><!-- /.modal-content -->
							</div>
						</div><!-- /.modal -->
						<div class="modal fade" id="loadingModal"  tabindex="-1" role="dialog" aria-labelledby="loadingModalWindow" aria-hidden="true">
							<div class="modal-dialog" style="top:200px; width: 200px; height:80px;">
							    <div class="modal-content">
									 <div class="modal-header" style="display:none">
										<h4 class="modal-title" id="loadingModalWindow"></h4>
									 </div>
									 <div class="modal-body text-center">
									 	<i class="fa fa-2x fa-check" style="font-weight: bold; color:#FE980F;"></i> �n�J���\
									 </div>
							    </div><!-- /.modal-content -->
							</div>
						</div><!-- /.modal -->	
						</c:if>
						<script>
							var LoginEnv = {
								controller: '<%=request.getContextPath() + "/MemberLogin"%>'<%--,
								currentPage: '<%=request.getRequestURL() + (request.getQueryString()==null? "" : "?" + request.getQueryString()) %>'--%>	
							};
						</script>
					</div>
				</div>
			</div>
		</div><!--/header-middle-->
		
		
		<div class="header-bottom">
			<!--header-bottom-->
			<div class="container">
				<div class="row">
					<div class="col-sm-8">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle"
								data-toggle="collapse" data-target=".navbar-collapse">
								<span class="sr-only">Toggle navigation</span> <span
									class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
						</div>
						<div class="mainmenu pull-left" >
							<ul class="nav navbar-nav collapse navbar-collapse">
								<% 	
									Integer areaCode = (Integer)session.getAttribute("area"); 
									if(areaCode == null){areaCode = 10;}
								%>
								<li><a class="active" style="cursor: default; font-weight: bold;" id="areaShowing" ><c:out value="<%=com.tao.jimmy.util.AreaNames.getAreaName(areaCode)%>"/></a></li>
								<li><a>�_ ��<i class="fa fa-angle-down"></i></a>
									<ul role="menu" class="sub-menu">
										<li><a class="LocationArea" style="cursor: pointer;" data-ChangeArea="1">�_�_��</a></li>
										<li><a class="LocationArea" style="cursor: pointer;" data-ChangeArea="2">�� ��</a></li>
										<li><a class="LocationArea" style="cursor: pointer;" data-ChangeArea="3">�s ��</a></li>
									</ul>
								</li>
								<li class="dropdown"><a>�� ��<i
										class="fa fa-angle-down"></i></a>
									<ul role="menu" class="sub-menu">
										<li><a class="LocationArea" style="cursor: pointer;" data-ChangeArea="4">�] ��</a></li>
										<li><a class="LocationArea" style="cursor: pointer;" data-ChangeArea="5">�x ��</a></li>
										<li><a class="LocationArea" style="cursor: pointer;" data-ChangeArea="6">�� �� ��</a></li>
									</ul>
								</li>
								<li class="dropdown"><a >�n ��<i
										class="fa fa-angle-down"></i></a>
									<ul role="menu" class="sub-menu">
										<li><a class="LocationArea" style="cursor: pointer;" data-ChangeArea="7">�� �n</a></li>
										<li><a class="LocationArea" style="cursor: pointer;" data-ChangeArea="8">�� ��</a></li>
									</ul>
								</li>
								<li class="dropdown"><a>�F ��<i
										class="fa fa-angle-down"></i></a>
									<ul role="menu" class="sub-menu">
										<li><a class="LocationArea" style="cursor: pointer;" data-ChangeArea="9">�y��F�Ψ�L</a></li>
									</ul>
								</li>
<%-- 								<c:if test="<%=areaCode != 10%>"> --%>
								<li class="dropdown"><a class="LocationArea" style="cursor: pointer;" data-ChangeArea="10">������</a></li>
<%-- 								</c:if> --%>
								<li><a href="${context}/about-us.jsp">����ڭ�</a></li>
							</ul>
						</div>
						<script>
							var AreaEnv = {
								controller: '<%=request.getContextPath() + "/MemberLogin"%>'
							};
						</script>
					</div>
					<div class="col-sm-4">
						<form action="${context}/search/search.do">
							<div class="col-sm-7" style="padding-right: 5px; padding-left: 0px;">
							<% String keyword = request.getParameter("keyword"); 
								if(keyword == null){
									keyword = "";
								}else{
									keyword = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
								}
							%>
							<input type="text" class="form-control"  name="keyword"  placeholder="�j�M" value="<%=keyword%>"/>
							</div>
							<div class="col-sm-3" style="padding-right: 5px;padding-left: 0px;">
							<select class="form-control" style="margin: 0px; font-size: 0.7em;"  name="action">
								<option value="searchKeyCase" <%-- ${param.action=='searchKeyCase'?'selected':'' }--%>>��X��</option>
								<option value="searchKeyShop" ${param.action=='searchKeyShop'?'selected':'' }>��ө�</option>
							</select>
							</div>
							<div class="col-sm-2" style="padding: 0px;">
								<button type="submit" class="btn btn-warning" style="margin: 0px;"><i class="fa fa-lg fa-search"></i></button>
							</div>
							<input type="hidden" name="whichPage" value="1">
						</form>
					</div>
				</div>
				<c:if test="${not empty requestScope.showBreadcrumb}">
				<div class="row">
					<div class="col-sm-12">
						<div class="breadcrumbs">
							<ol class="breadcrumb" style="margin-bottom: 30px">
								<li><a href="${context}">Home</a></li>
								<c:forEach var="entry" items="${requestScope.breadcrumbMap}" varStatus="sts">
								<c:choose>
								<c:when test="${sts.last}">
								<li class="active">${entry.key}</li>
								</c:when>
								<c:otherwise>
								<li><a href="${entry.value }">${entry.key}</a></li>
								</c:otherwise>
								</c:choose>
								</c:forEach>
							</ol>
						</div>
					</div>
				</div>
				</c:if>
			</div>
		</div>
		<!--/header-bottom-->
	</header>
	<!--/header-->

