<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<% 
request.setAttribute("showBreadcrumb", new Object()); 

LinkedHashMap<String, String> breadmap = new LinkedHashMap<String, String>(); 
breadmap.put("����ڭ�", ""); 
request.setAttribute("breadcrumbMap", breadmap); 

%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<title>����ڭ� | Ź���a��</title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	 <div id="contact-page" class="container">
    	<div class="bg">
	    	<div class="row">    		
	    		<div class="col-sm-12">    			   			  			    				    				
						<section id="cart_items">
							<div class="container">

								<h2 class="title text-center">About <strong>Us</strong></h2>   
								

								<div class="table-responsive cart_info">
									<table class="table table-condensed"�@>

										<tbody>
											<tr style="font-family: �L�n������;">
												<td class="cart_product" style="color:#FE980F;">
													<h2>�t�_</h2> 
												</td>
												<td class="cart_product" style="font-size: 1.2em;">
													�ڭ̹��ͬ������穹���O�ĤG���� �бi�R�¡q�����L�ҡr
												</td>
												<td class="cart_product" style="font-size: 1.2em;">
													�W�Z�ͬ��дe����A�L�L�פ�A�n���e���U�Z�o�w�g�ֱo�������Q�h�C</br>
													���N��Ź�����Ϧ����A�վ����ߪ��n����A���A�s�A�״I���㪺�@���T�A�i�@�B�гy�ĤG��������P�O�СC

												</td>
											</tr>


											<tr style="font-family: �L�n������;">
												<td class="cart_product" style="color:#FE980F;">
													<h2>�ؼ�</h2> 
												</td>
												<td class="cart_product" style="font-size: 1.2em;">
													�����w�X�ʬ������W�Z�گ�����Ӻ������x�Ӷi��X�ʡA�����ۤv�B�z�c�����X�ʲӸ`�C
												</td>
												<td class="cart_product" style="font-size: 1.2em;">
													�H�X�ʬ��D���P��覡�A���a�i�H�b���x�W�}�]�ө��åB���Ѳ��~�M��A��K�ϥΪ̫إߦX�ʮסC

												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</section> <!--/#cart_items-->
				</div>
			</div>			 		
		</div>    	
    		<div class="row">  	
	    		<div class="col-sm-12" >
	    			<div class="contact-info">
	    				<h2 class="title text-center">Contact Info</h2>
	    				<address style="font-family: �L�n������;">
	    					<p>TaoBao Puzzle Inc.</p>
							<p>Class: YA803</p>
							<p>Group: No.2</p>
							<p>Memeber: ���@�ʡB���s���B���ܽ{�B�i���ɡB�Q���ΡB���a��</p>
	    				</address>
						<h2>�@</h2>
	    			</div>
    			</div>    			
	    	</div>  
    </div><!--/#contact-page-->
	<jsp:include page="/f/frag/f_footer1.jsp"/>
		
</body>
</html>