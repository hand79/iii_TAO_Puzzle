<%@ page import="com.tao.cathy.util.DateFormater"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.news.model.*"%>
<%
	String path=request.getContextPath()+"/f/res";
	pageContext.setAttribute("res", path);
%>
<% 
	NewsService newsSvc = new NewsService();
	List<NewsVO> list = newsSvc.getAll();
	java.util.Date currentTime= new java.util.Date() ;
	for(int i = 0 ; i<list.size(); i++){
		if(list.get(i).getPubtime().after(currentTime)){
			list.remove(i);
			i--;
		}else{
			break;
		}
	}
	pageContext.setAttribute("list", list);
%>
<% 
	request.setAttribute("showBreadcrumb", new Object()); 
	
	LinkedHashMap<String, String> breadmap = new LinkedHashMap<String, String>(); 
	breadmap.put("�̷s����", ""); 
	request.setAttribute("breadcrumbMap", breadmap); 

%>
<jsp:include page="/f/frag/f_header1.jsp"/>
    <title>Home | Ź���a��</title>
	
	<STYLE>

		<!----------- �̷s�����O�c------------->
		 body { background-color:white; } 
		   .modal-title {
			font-weight:bold; 
			color: #FE980F; 
			font-family: �L�n������;
		}
		.modal-body p span {
			font-size: 1.5em;
		
		}
		.modal-body p .total {		
			color:#FE980F; 
		}
		.modal-open{
			overflow: initial;
		}

		.newslist a {
			color:#363432;
		}

	</STYLE>
</head><!--/head-->
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>

				

<section>
		<div class="container">
			<div class="row">
				<jsp:include page="/f/frag/f_catMenu.jsp"/>
				
				<div class="col-sm-9 padding-right">
					<div><!--�̷s����-->
						<h2 class="title text-center">�̷s����</h2>
						<div class="row">
							<div class="col-sm-12">

								<div class="list-zone">
									<table class="table table-hover table-condensed newslist" style="font-size: 1.1em; font-family:�L�n������;"></tr>
										<tr class="list-header info">
											<th><i class="fa fa-list"></i></th>
											<th>���D</th>
											<th  class="text-center">�o�G�ɶ�</th>
										</tr>
										<%@ include file="page_count_front.file"%>
										<c:forEach var="newsVO" items="${list}" begin="<%=pageIndex%>" 	end="<%=pageIndex+rowsPerPage-1%>">
											<tr class="mainrow" id="news">										
												<td><i class="fa fa-chevron-circle-right"></i></td>
												<td><a data-toggle="modal" data-target="#detail-modal${newsVO.newsno}" style="cursor: pointer;">${newsVO.title}</a></td>
												<td class="text-center">${newsVO.formatPubtime}</td>
											</tr>
										</c:forEach>
									</table>
									
									<%@ include file="page_next_back.file" %>
								</div><!--class="list-zone"-->
								
							</div>							
						</div>
					</div>			
				</div>
			</div>
		</div>
	</section>
	

	<c:forEach var="newsVO" items="${list}">
	<div class="modal fade" id="detail-modal${newsVO.newsno}"  tabindex="-1" role="dialog" aria-labelledby="view-detail" aria-hidden="true" >
		<div class="modal-dialog">
		    <div class="modal-content">
			 <div class="modal-header">		
				<h4 class="modal-title title" id="view-detail"><i class="fa fa-bullhorn"></i>
				  ${newsVO.title}
				</h4>
			 </div>
			 <div class="modal-body">
				<p>
					<i class="fa fa-file-text-o"></i><b> ���e</b>
					<br>${newsVO.text}
					
			 </div>
			 <div class="modal-footer">
				<div class="text-left">
				<p>
					<i class="fa fa-calendar"></i><b> �o�G�ɶ�</b>
					<br>${newsVO.formatPubtime}
					
			 </div>
				<button type="button" class="btn btn-default" data-dismiss="modal">
				   �T�w
				</button>
			 </div>
		    </div><!-- /.modal-content -->
		</div>
	</div>
	</c:forEach>
	<jsp:include page="/f/frag/f_footer1.jsp"/>
		
</body>
</html>