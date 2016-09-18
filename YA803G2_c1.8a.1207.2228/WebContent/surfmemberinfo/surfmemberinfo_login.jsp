<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@page import="com.tao.member.model.MemberVO"%>
<%@page import="com.tao.order.model.OrderVO"%>
<%@ page import="java.util.*"%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<jsp:include page="/f/frag/f_header1.jsp"/>

<title>�|����T</title>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("�|������","");
request.setAttribute("breadcrumbMap", map); 
%>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>    	
<jsp:useBean id="memberSvc" class="com.tao.member.model.MemberService"/>
<jsp:useBean id="locationSvc" class="com.tao.location.model.LocationService"/>
<jsp:useBean id="orderSvc"  class="com.tao.order.model.OrderService"/>
<jsp:useBean id="querymemberVO"  scope="request" class="com.tao.member.model.MemberVO"/>
<c:set var="memberVO"  value="${requestScope.querymemberVO}"/>

<c:set var="memberGoodPoint"  value="0"/>
<c:set var="memberNormalPoint"  value="0"/>
<c:set var="memberBadPoint"  value="0"/>

<c:forEach var="bpointvalue" items="${orderSvc.findByBuyer(memberVO.memno)}">
	<c:if test="${bpointvalue.brate==2}">
		<c:set var="memberGoodPoint" value="${memberGoodPoint+1}"/>	
	</c:if>
	<c:if test="${bpointvalue.brate==1}">
		<c:set var="memberNormalPoint" value="${memberNormalPoint+1}"/>	
	</c:if>
	<c:if test="${bpointvalue.brate==0}">
		<c:set var="memberBadPoint" value="${memberBadPoint+1}" />	
	</c:if>
</c:forEach>
<c:forEach var="cpointvalue" items="${orderSvc.findByCreator(memberVO.memno)}">
	<c:if test="${cpointvalue.crate==2}">
		<c:set var="memberGoodPoint" value="${memberGoodPoint+1}"/>	
	</c:if>
		<c:if test="${cpointvalue.crate==1}">
		<c:set var="memberNormalPoint" value="${memberNormalPoint+1}"/>	
	</c:if>
		<c:if test="${cpointvalue.crate==0}">
		<c:set var="memberBadPoint" value="${memberBadPoint+1}"/>
	</c:if>
</c:forEach>


<section>
	<div class="container">
		<div class="row">
<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>  
 			<div class="col-sm-9">
				<h2 class="title text-center">��������</h2>
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row">
 						<div  class="col-sm-2">
 							<img src="${pageContext.request.contextPath}/MemberInfoImage.do?memno=${memberVO.memno}" style="width: 120px; height: 120px;"/>
 						</div>
						<div class="col-sm-10">				
							<p style="font-size: 2em;font-family:�L�n������;"><span style="color:#FE980F;"><i class="fa fa-fw fa-trophy"></i> �����G ${memberGoodPoint*2+memberNormalPoint*1}</span> </p>
							<p><i class="fa fa-user fa-fw "></i>�|���b��	<a href="${pageContext.request.contextPath}//SurfMemberServlet.do?action=memberView&memno=${memberVO.memno}">${memberVO.memid}</a></p>
							<p ><i class="fa fa-user fa-fw"></i>�|���s��
							<input id="memberno" type="hidden" value="${memberVO.memno}">${memberVO.memno} </p>

						</div> 
					</div>
					<div class="row" style="margin-top: 20px;">
						<div class="col-sm-4 text-center">
							<img src="${pageContext.request.contextPath}/surfmemberinfo/pointimg/good.gif">�n��:${memberGoodPoint}��</div>	
						<div class="col-sm-4 text-center"><img src="${pageContext.request.contextPath}/surfmemberinfo/pointimg/normal.gif">����:${memberNormalPoint }��</div>
						<div class="col-sm-4 text-center"><img src="${pageContext.request.contextPath}/surfmemberinfo/pointimg/bad.gif">�a��:${memberBadPoint}��</div>
					</div>
				</div>
			</div> 
				
						
					
					<ul id="myTab" class="nav nav-pills nav-justified" style="margin-bottom: 40px;">
						<li class="dropdown in active">
							<a href="#" id="myTabDrop1" class="dropdown-toggle" data-toggle="dropdown">�[�J�X�ʵ�������
								<b class="caret"></b>
							</a>
							<ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop1">
								<li class="in active"><a href="#allAddRecord" tabindex="0" data-toggle="pill">�Ҧ�����</a></li>
								<li><a href="#goodAddRecord" tabindex="1" data-toggle="pill">�}������</a></li>
								<li><a href="#normalAddRecord" tabindex="2" data-toggle="pill">��������</a></li>
								<li><a href="#badAddRecord" tabindex="3" data-toggle="pill">�t������</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" id="myTabDrop2" class="dropdown-toggle" data-toggle="dropdown">�o�_�X�ʵ�������
								<b class="caret"></b>
							</a>
							<ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop1">
								<li><a href="#AllLaunchRecord" tabindex="0" data-toggle="pill">�Ҧ�����</a></li>
								<li><a href="#GoodlaunchRecord" tabindex="1" data-toggle="pill">�}������</a></li>
								<li><a href="#NormalLaunchRecord" tabindex="2" data-toggle="pill">��������</a></li>
								<li><a href="#BadLaunchRecord" tabindex="3" data-toggle="pill">�t������</a></li>
							</ul>
						</li>
					</ul>
					
					<div id="myTabContent" class="tab-content">
					
						<div class="tab-pane fade in active" id="allAddRecord">
						
					
						</div>
						
						<div class="tab-pane fade" id="goodAddRecord">
						
						</div>
						
						<div class="tab-pane fade" id="normalAddRecord">
							
						</div>
			
						<div class="tab-pane fade" id="badAddRecord">
							
						</div>
						<div class="tab-pane fade" id="AllLaunchRecord">
							
							
						</div>
						<div class="tab-pane fade" id="GoodlaunchRecord">
						
						</div>
						<div class="tab-pane fade" id="NormalLaunchRecord">
						
						</div>
						<div class="tab-pane fade" id="BadLaunchRecord">
						
						</div>
					</div>
 			</div>
		</div>
	</div>
</section>
	

	
<jsp:include page="/f/frag/f_footer1.jsp"/>    		

<script>
	$(document).ready(function(){
		var memberno=$("#memberno").val();
		
		var callback=function(){
			$(".page").click(function(){
				var	url = $(this).attr('data-url');
				$("#allAddRecord").load(url,callback);
				return false;
			});
			$(".pagenext").click(function(){
				var	url = $(this).attr('data-url');
				$("#allAddRecord").load(url,callback);
				return false;
			});
			$(".pagepre").click(function(){
				var	url = $(this).attr('data-url');
				$("#allAddRecord").load(url,callback);
				return false;
			});
		}
	
		var goodcallback=function(){
			$(".page").click(function(){
				var	url = $(this).attr('data-url');
				$("#goodAddRecord").load(url,goodcallback);
				return false;
			});
			$(".pagenext").click(function(){
				var	url = $(this).attr('data-url');
				$("#goodAddRecord").load(url,goodcallback);
				return false;
			});
			$(".pagepre").click(function(){
				var	url = $(this).attr('data-url');
				$("#goodAddRecord").load(url,goodcallback);
				return false;
			});
		}
		
		var normalcallback=function(){
			$(".page").click(function(){
				var	url = $(this).attr('data-url');
				$("#normalAddRecord").load(url,normalcallback);
				return false;
			});
			$(".pagenext").click(function(){
				var	url = $(this).attr('data-url');
				$("#normalAddRecord").load(url,normalcallback);
				return false;
			});
			$(".pagepre").click(function(){
				var	url = $(this).attr('data-url');
				$("#normalAddRecord").load(url,normalcallback);
				return false;
			});
		}
		
		var badcallback=function(){
			$(".page").click(function(){
				var	url = $(this).attr('data-url');
				$("#badAddRecord").load(url,badcallback);
				return false;
			});
			
			$(".pagenext").click(function(){
				var	url = $(this).attr('data-url');
				$("#badAddRecord").load(url,badcallback);
				return false;
			});
			
			$(".pagepre").click(function(){
				var	url = $(this).attr('data-url');
				$("#badAddRecord").load(url,badcallback);
				return false;
			});
		}
		
		
		$("#allAddRecord").load("SurfMemberServlet.do", {action:'queryAddData',edata:'allAddRecord',memno:memberno,indexPage:1}, callback);
		
		
		$("a[href='#allAddRecord']").click(function(){
			$("#allAddRecord").load("SurfMemberServlet.do", {action:'queryAddData',edata:'allAddRecord',memno:memberno,indexPage:1},callback);
		});
		
		
		$("a[href='#goodAddRecord']").click(function(){
			$("#goodAddRecord").load("SurfMemberServlet.do", {action:'queryAddData',edata:'goodAddRecord',memno:memberno,indexPage:1},goodcallback);
		});
		
		$("a[href='#normalAddRecord']").click(function(){
			$("#normalAddRecord").load("SurfMemberServlet.do", {action:'queryAddData',edata:'normalAddRecord',memno:memberno,indexPage:1},normalcallback);
		});
		
		$("a[href='#badAddRecord']").click(function(){
			$("#badAddRecord").load("SurfMemberServlet.do", {action:'queryAddData',edata:'badAddRecord',memno:memberno,indexPage:1},badcallback);
		});
		
		
		
		
		var laucallback=function(){
			$(".page").click(function(){
				var	url = $(this).attr('data-url');
				$("#AllLaunchRecord").load(url,laucallback);
				return false;
			});
			$(".pagenext").click(function(){
				var	url = $(this).attr('data-url');
				$("#AllLaunchRecord").load(url,laucallback);
				return false;
			});
			$(".pagepre").click(function(){
				var	url = $(this).attr('data-url');
				$("#AllLaunchRecord").load(url,laucallback);
				return false;
			});
		}
	
		var laugoodcallback=function(){
			$(".page").click(function(){
				var	url = $(this).attr('data-url');
				$("#GoodlaunchRecord").load(url,laugoodcallback);
				return false;
			});
			$(".pagenext").click(function(){
				var	url = $(this).attr('data-url');
				$("#GoodlaunchRecord").load(url,laugoodcallback);
				return false;
			});
			$(".pagepre").click(function(){
				var	url = $(this).attr('data-url');
				$("#GoodlaunchRecord").load(url,laugoodcallback);
				return false;
			});
		}
		
		var launormalcallback=function(){
			$(".page").click(function(){
				var	url = $(this).attr('data-url');
				$("#NormalLaunchRecord").load(url,launormalcallback);
				return false;
			});
			$(".pagenext").click(function(){
				var	url = $(this).attr('data-url');
				$("#NormalLaunchRecord").load(url,launormalcallback);
				return false;
			});
			$(".pagepre").click(function(){
				var	url = $(this).attr('data-url');
				$("#NormalLaunchRecord").load(url,launormalcallback);
				return false;
			});
		}
		
		var laubadcallback=function(){
			$(".page").click(function(){
				var	url = $(this).attr('data-url');
				$("#BadLaunchRecord").load(url,laubadcallback);
				return false;
			});
			
			$(".pagenext").click(function(){
				var	url = $(this).attr('data-url');
				$("#BadLaunchRecord").load(url,laubadcallback);
				return false;
			});
			
			$(".pagepre").click(function(){
				var	url = $(this).attr('data-url');
				$("#BadLaunchRecord").load(url,laubadcallback);
				return false;
			});
		}
		
		
		
		
		
		
		
		$("a[href='#AllLaunchRecord']").click(function(){
			$("#AllLaunchRecord").load("SurfMemberServlet.do", {action:'querylauData',edata:'alllauRecord',memno:memberno,indexPage:1},laucallback);
		});
		$("a[href='#GoodlaunchRecord']").click(function(){
			$("#GoodlaunchRecord").load("SurfMemberServlet.do", {action:'querylauData',edata:'goodlauRecord',memno:memberno,indexPage:1},laugoodcallback);
		});
		$("a[href='#NormalLaunchRecord']").click(function(){
			$("#NormalLaunchRecord").load("SurfMemberServlet.do", {action:'querylauData',edata:'normallauRecord',memno:memberno,indexPage:1},launormalcallback);
		});
		$("a[href='#BadLaunchRecord']").click(function(){
			$("#BadLaunchRecord").load("SurfMemberServlet.do", {action:'querylauData',edata:'badlauRecord',memno:memberno,indexPage:1},laubadcallback);
		});
	
	
		
	});

</script>

<jsp:include page="/f/frag/f_footer2.jsp"/>    				
