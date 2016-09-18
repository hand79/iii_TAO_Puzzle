<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ page import="java.util.*" %>

<jsp:include page="/f/frag/f_header1.jsp"/>
<% 
request.setAttribute("showBreadcrumb", new Object());

LinkedHashMap<String, String> breadmap = new LinkedHashMap<String, String>();
breadmap.put("甜點餅乾", request.getContextPath() + "/Search.do?catno=9999");
breadmap.put("巧克力", "");
request.setAttribute("breadcrumbMap", breadmap);

%>

<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<section >
<div class="container">
	<div class="row"><div class="col-sm-12">
		<h1 class="title text-center">Breadcrumb Demo</h1>
<pre>
<b>顯示與否：</b>

若request Scope當中有 "showBreadcrumb"之attribute，且該attribute不為空(以&#36;{not empty requestScope.showBreadcrumb}判斷的)則顯示，若無則不會。

基本上讓EL拿的到東西就好，所以attribute的值可以隨便給，例如new Object(), 但是不能為空字串


<b>顯示內容：</b>

需在request Scope放入一Attribute，其名稱為breadcrumbMap，型態【必須】為LinkedHashMap&lt;String, String&gt;

其中map的key為"要顯示的字串"，value為"超連結字串"。

顯示時依照加入map的順序，先加入的先顯示。

map的最後一筆不會顯示超連結，因此value可以給空字串就好。

若map為空的或者沒有這個attriubte則只會顯示最上層的Home

</pre>

<h3>範例： </h3>
<hr>
<code>
&lt;% <br>
request.setAttribute("showBreadcrumb", new Object()); <br>
 <br>
LinkedHashMap&lt;String, String&gt; breadmap = new LinkedHashMap&lt;String, String&gt;(); <br>
breadmap.put("甜點餅乾", request.getContextPath() + "/Search.do?catno=9999"); <br>
breadmap.put("巧克力", ""); <br>
request.setAttribute("breadcrumbMap", breadmap); <br>
 <br>
%&gt; <br>
 <br>
 <br>
&lt;jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/&gt;
</code>
<hr>

<p>顯示結果為：</p>
<p></p>
<p>Home &gt; 甜點餅乾  &gt; 巧克力</p>  
<p></p>
<p>其中Home與甜點餅乾可以點選。</p>
<p></p>


	</div></div>
	<div class="row"><div class="col-sm-12">&nbsp;</div></div>
</div>

</section>
<jsp:include page="/f/frag/f_footer1.jsp"/>
</body>
</html>