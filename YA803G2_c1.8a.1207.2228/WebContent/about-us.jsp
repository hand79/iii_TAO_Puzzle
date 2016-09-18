<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<% 
request.setAttribute("showBreadcrumb", new Object()); 

LinkedHashMap<String, String> breadmap = new LinkedHashMap<String, String>(); 
breadmap.put("關於我們", ""); 
request.setAttribute("breadcrumbMap", breadmap); 

%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<title>關於我們 | 饕飽地圖</title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	 <div id="contact-page" class="container">
    	<div class="bg">
	    	<div class="row">    		
	    		<div class="col-sm-12">    			   			  			    				    				
						<section id="cart_items">
							<div class="container">

								<h2 class="title text-center">About <strong>Us</strong></h2>   
								

								<div class="table-responsive cart_info">
									<table class="table table-condensed"　>

										<tbody>
											<tr style="font-family: 微軟正黑體;">
												<td class="cart_product" style="color:#FE980F;">
													<h2>緣起</h2> 
												</td>
												<td class="cart_product" style="font-size: 1.2em;">
													我們對於生活的體驗往往是第二輪的 －張愛玲〈童言無忌〉
												</td>
												<td class="cart_product" style="font-size: 1.2em;">
													上班生活煩悶壓抑，碌碌終日，好不容易下班卻已經累得哪都不想去。</br>
													那就讓饕飽拼圖成為你調劑身心的好幫手，給你新鮮豐富完整的一手資訊，進一步創造第二輪的體驗與記憶。

												</td>
											</tr>


											<tr style="font-family: 微軟正黑體;">
												<td class="cart_product" style="color:#FE980F;">
													<h2>目標</h2> 
												</td>
												<td class="cart_product" style="font-size: 1.2em;">
													讓喜歡合購美食的上班族能夠有個網路平台來進行合購，不必自己處理繁瑣的合購細節。
												</td>
												<td class="cart_product" style="font-size: 1.2em;">
													以合購為主的銷售方式，店家可以在平台上開設商店並且提供產品清單，方便使用者建立合購案。

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
	    				<address style="font-family: 微軟正黑體;">
	    					<p>TaoBao Puzzle Inc.</p>
							<p>Class: YA803</p>
							<p>Group: No.2</p>
							<p>Memeber: 廖駿銘、李孟儒、曾欽緹、張寧馨、鄒岡諺、陳冠翰</p>
	    				</address>
						<h2>　</h2>
	    			</div>
    			</div>    			
	    	</div>  
    </div><!--/#contact-page-->
	<jsp:include page="/f/frag/f_footer1.jsp"/>
		
</body>
</html>