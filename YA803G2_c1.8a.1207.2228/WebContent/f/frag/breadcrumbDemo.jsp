<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ page import="java.util.*" %>

<jsp:include page="/f/frag/f_header1.jsp"/>
<% 
request.setAttribute("showBreadcrumb", new Object());

LinkedHashMap<String, String> breadmap = new LinkedHashMap<String, String>();
breadmap.put("���I�氮", request.getContextPath() + "/Search.do?catno=9999");
breadmap.put("���J�O", "");
request.setAttribute("breadcrumbMap", breadmap);

%>

<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<section >
<div class="container">
	<div class="row"><div class="col-sm-12">
		<h1 class="title text-center">Breadcrumb Demo</h1>
<pre>
<b>��ܻP�_�G</b>

�Yrequest Scope���� "showBreadcrumb"��attribute�A�B��attribute������(�H&#36;{not empty requestScope.showBreadcrumb}�P�_��)�h��ܡA�Y�L�h���|�C

�򥻤W��EL������F��N�n�A�ҥHattribute���ȥi�H�H�K���A�Ҧpnew Object(), ���O���ର�Ŧr��


<b>��ܤ��e�G</b>

�ݦbrequest Scope��J�@Attribute�A��W�٬�breadcrumbMap�A���A�i�����j��LinkedHashMap&lt;String, String&gt;

�䤤map��key��"�n��ܪ��r��"�Avalue��"�W�s���r��"�C

��ܮɨ̷ӥ[�Jmap�����ǡA���[�J������ܡC

map���̫�@�����|��ܶW�s���A�]��value�i�H���Ŧr��N�n�C

�Ymap���Ū��Ϊ̨S���o��attriubte�h�u�|��̤ܳW�h��Home

</pre>

<h3>�d�ҡG </h3>
<hr>
<code>
&lt;% <br>
request.setAttribute("showBreadcrumb", new Object()); <br>
 <br>
LinkedHashMap&lt;String, String&gt; breadmap = new LinkedHashMap&lt;String, String&gt;(); <br>
breadmap.put("���I�氮", request.getContextPath() + "/Search.do?catno=9999"); <br>
breadmap.put("���J�O", ""); <br>
request.setAttribute("breadcrumbMap", breadmap); <br>
 <br>
%&gt; <br>
 <br>
 <br>
&lt;jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/&gt;
</code>
<hr>

<p>��ܵ��G���G</p>
<p></p>
<p>Home &gt; ���I�氮  &gt; ���J�O</p>  
<p></p>
<p>�䤤Home�P���I�氮�i�H�I��C</p>
<p></p>


	</div></div>
	<div class="row"><div class="col-sm-12">&nbsp;</div></div>
</div>

</section>
<jsp:include page="/f/frag/f_footer1.jsp"/>
</body>
</html>