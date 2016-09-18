<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String resPath = request.getContextPath() + "/b/res";
	pageContext.setAttribute("resPath", resPath);

	String contextPath = request.getContextPath();
	pageContext.setAttribute("contextPath", contextPath);

	boolean[] permissionFlagArray = (boolean[]) session
			.getAttribute("permissionFlagArray");
	if (permissionFlagArray == null) {
		permissionFlagArray = new boolean[7];
	}
%>

</head>

<body>
	<div id="wrapper">
		<!-- Close tag is in b_footer1.jsp -->

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0;">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<%=request.getContextPath()%>/back/BackLoginServlet.do"
					style="font-family: �L�n������; font-weight: bold; font-size: 1.5em;">��ݺ޲z�t��</a>
			</div>
			<!-- /.navbar-header -->

			<ul class="nav navbar-top-links navbar-right">

				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a style="cursor: default;"><i class="fa fa-user fa-fw"></i> �A�n�A ${bAccount.nick}</a></li>
						<li class="divider"></li>
						<li><a
							href="<%=request.getContextPath()%>/BackLoginServlet.do?action=logout"><i
								class="fa fa-sign-out fa-fw"></i> Logout</a></li>
					</ul> <!-- /.dropdown-user --></li>
				<!-- /.dropdown -->
			</ul>
			<!-- /.navbar-top-links -->

			<div class="navbar-default sidebar" role="navigation">
				<div class="sidebar-nav navbar-collapse">
					<ul class="nav" id="side-menu">
						<li class="sidebar-search" style="background-color: #FcFcFc">
							<img src="${resPath}/images/Logo.png" style="height: 55px">
							<!-- /input-group -->
						</li>
						<li><a href="${contextPath }/b/backindex.jsp"><i
								class="fa fa-dashboard fa-fw"></i> ��T���O</a></li>
						<c:if test="<%=permissionFlagArray[6 - 1]%>">
							<li id="homepageMenu"><a href="#"><i
									class="fa fa-home fa-fw"></i> �����޲z<span class="fa arrow"></span></a>
								<ul class="nav nav-second-level">
									<li><a href="${contextPath}/back/news/news.do?action=news"><i
											class="fa fa-tags fa-fw"></i>&nbsp;&nbsp;�̷s�����޲z</a></li>
									<li><a
										href="${contextPath}/runningAd/back/backRunningAdListAll.jsp"><i class="fa fa-repeat fa-fw"></i></i>&nbsp;&nbsp;�����s�i�޲z</a></li>
								</ul> <!-- /.nav-second-level --></li>
						</c:if>
						<c:if test="<%=permissionFlagArray[3 - 1]%>">
							<li id="shopMenu"><a href="#"><i
									class="fa fa-gift fa-fw"></i> �ө��޲z<span class="fa arrow"></span></a>
								<ul class="nav nav-second-level">
									<li><a href="${contextPath}/shop/shop.do?action=search"><i class="fa fa-university fa-fw"></i></i>&nbsp;&nbsp;�j�M�ө�</a></li>
									<li><a href="${contextPath}/shop/shop.do?action=approve"><i class="fa fa-upload fa-fw"></i></i>&nbsp;&nbsp;�f�֤W�[</a></li>
									<li><a href="${contextPath}/shopRep/shopRep.do?action=default"><i class="fa fa-comment-o fa-fw"></i>&nbsp;&nbsp;���|�B�z</a></li>
								</ul> <!-- /.nav-second-level --></li>
						</c:if>
						<c:if test="<%=permissionFlagArray[7 - 1]%>">
							<li id="caseMenu"><a href="#"><i
									class="fa fa-users fa-fw"></i> �X�ʺ޲z<span class="fa arrow"></span></a>
								<ul class="nav nav-second-level">
									<li><a href="${contextPath}/back/cases/cases.do?view=default"><i class="fa fa-align-left fa-fw"></i>&nbsp;�j�M�X�ʮ�</a></li>
									<li><a href="${contextPath}/back/order/order.do?view=default"><i class="fa fa-list-ul fa-fw"></i>&nbsp;�j�M�q��</a></li>
									<li><a href="${contextPath}/back/BackCalendarServlet.do?action=default"><i class="fa fa-calendar fa-fw"></i> �����I���{��</a></li>
									<li><a href="${contextPath}/caseRep/caseRep.do?action=default"><i class="fa fa-comment-o fa-fw"></i> ���|�B�z</a></li>
								</ul> <!-- /.nav-second-level --></li>
						</c:if>

						<c:if test="<%=permissionFlagArray[5 - 1]%>">
							<li id="moneyMenu"><a href="#"><i
									class="fa fa-usd fa-fw"></i> ���y�޲z<span class="fa arrow"></span></a>
								<ul class="nav nav-second-level">
									<li><a
										href="${contextPath}/back/dpsOrd/dpsOrd.do?action=default"><i class="fa fa-money fa-fw"></i>  ���ڽT�{</a></li>
									<li><a
										href="${contextPath}/back/wtdReq/wtdReq.do?action=default"><i class="fa fa-money fa-fw"></i> �I���{���f��</a></li>
								</ul> <!-- /.nav-second-level --></li>
						</c:if>
						<c:if test="<%=permissionFlagArray[2 - 1]%>">
							<li id="memberMenu"><a
								href="${contextPath}/member/back/backMemberManage.jsp"><i
									class="fa fa-user fa-fw"></i> �|���޲z</a>
						</c:if>
						<c:if test="<%=permissionFlagArray[1 - 1]%>">
							<li><a
								href="<%=request.getContextPath()%>/back/Category.do?action=default"><i
									class="fa fa-list-alt fa-fw"></i> �ӫ~�����޲z</a></li>
						</c:if>
						<c:if test="<%=permissionFlagArray[4 - 1]%>">
							<li><a
								href="<%=request.getContextPath()%>/back/Account.do?action=default"><i
									class="fa fa-wrench fa-fw"></i> ��ݱb���޲z</a></li>
						</c:if>
					</ul>
				</div>
				<!-- /.sidebar-collapse -->
			</div>
			<!-- /.navbar-static-side -->
		</nav>