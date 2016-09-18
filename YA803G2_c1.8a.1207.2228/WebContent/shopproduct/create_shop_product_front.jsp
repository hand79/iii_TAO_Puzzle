<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.shopproduct.model.*"%>
<%@ include file="/f/frag/f_login_redirect.file" %>
<%
	ShopproductVO spVO = (ShopproductVO) request.getAttribute("spVO");
%>
<%	pageContext.setAttribute("context", request.getContextPath()); %>
<jsp:useBean id="subCategorySvc" scope="page" class="com.tao.category.model.SubCategoryService" />
<jsp:useBean id="shopSvc" scope="page" 	class="com.tao.shop.model.ShopService" />
<jsp:include page="/f/frag/f_header1.jsp"/>
<style> 
    section{
		height: 1400px;
    }
 </style>
<style>
 #preview1 ,#preview2,#preview3
    {
        /*width:80%;
        height:80%;*/
        width: 100px !important;
        width: 100px;
        max-width: 100px;
        height: 100px !important;
        height: 100px;
        max-height: 100px;
        border: 1px dashed #d1d0d0;
        text-align: center;
        display: none;
    }
</style>  
<title>�إ߰ө��ӫ~ | Ź���a��</title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<head></head>
<section>
	<div class="container">
		<div class="row">
			<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
				<c:if test="${not empty errorMsgs}">
					<font color='red'>�Эץ��H�U���~:
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li>${message}</li>
						</c:forEach>
					</ul>
					</font>
				</c:if>
				
				<div class="col-sm-9" style="height:1000px">
					<div class="contact-form">
	    				<h2 class="title text-center">�إ߰ө��ӫ~</h2>
	    				<div class="status alert alert-success" style="display: none"></div>
				    	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shopproduct/shopproduct.do" name="form1" enctype="multipart/form-data">
				            <div class="form-group col-md-12">
								<label for="shopno"><i class="fa fa-fw fa-check-square"></i>�ө��s��</label>
				                <input type="text" name="shopno" class="form-control" required="required" 
				                 value="${param.shopno}" readonly>
				            </div>
				            <div class="form-group col-md-12">
								<label for="name"><i class="fa fa-fw fa-check-square"></i>�ӫ~�W��</label>
				                <input type="text" name="name" class="form-control" required="required" placeholder="���ᱲ" 
				                 value="<%= (spVO==null)? "" : spVO.getName()%>">
				            </div>
				            <div class="form-group col-md-6">
								<label for="unitprice"><i class="fa fa-fw fa-check-square"></i>���</label>
				                <input type="text" name="unitprice" class="form-control" required="required" placeholder="100.00" 
				                 value="<%= (spVO==null)? "" : spVO.getUnitprice()%>">
				            </div>
				            <div class="form-group col-md-6">
								<label for="subcatno"><i class="fa fa-fw fa-check-square"></i>�ӫ~����</label>
						        <select size="1" name="subcatno" class="form-control" required="required">
						         	<c:forEach var="subCategoryVO" items="${subCategorySvc.all}" > 
						         		<option value="${subCategoryVO.subcatno}">${subCategoryVO.subcatname}
						         	</c:forEach>     
						        </select>
				            </div>
				            <div class="form-group col-md-4">
								<label for="spec1"><i class="fa fa-fw fa-check-square"></i>�W��1</label>	
				                <input type="text" name="spec1" class="form-control" required="required" placeholder="���� "<%= (spVO==null)? "" : spVO.getSpec1()%>">
				            </div> 
							<div class="form-group col-md-4">
								<label for="spec2">�W��2</label>	
				                <input type="text" name="spec2" class="form-control"  placeholder="���J�O" 
				                 value="<%= (spVO==null)? "" : spVO.getSpec2()%>">
				            </div> 
				            <div class="form-group col-md-4">
								<label for="spec3">�W��3</label>	
				                <input type="text" name="spec3" class="form-control"  placeholder="���Y" 
				                 value="<%= (spVO==null)? "" : spVO.getSpec3()%>">
				            </div> 
				            <div class="form-group col-md-4">
								<label for="pic1"><i class="fa fa-fw fa-check-square"></i>�ӫ~�Ϥ�1</label>	
								<input type="file" name="pic1" accept="image/*" class="form-control" placeholder="�ӫ~�Ϥ�1 " required="required"
								 value="<%= (spVO==null)? "" : spVO.getPic1()%>"  onchange="preview1(this)"/>
							</div>

				            <div class="form-group col-md-4">
								<label for="pic2"><i class="fa fa-fw fa-check-square"></i>�ӫ~�Ϥ�2</label>	
								<input type="file" name="pic2" accept="image/*" class="form-control" placeholder="�ӫ~�Ϥ�2 " required="required"
								 value="<%= (spVO==null)? "" : spVO.getPic2()%>"  onchange="preview2(this)"/>
							</div>

				            <div class="form-group col-md-4">
								<label for="pic3"><i class="fa fa-fw fa-check-square"></i>�ӫ~�Ϥ�3</label>	
								<input type="file" name="pic3" accept="image/*" class="form-control" placeholder="�ӫ~�Ϥ�3 " required="required"
								 value="<%= (spVO==null)? "" : spVO.getPic3()%>"  onchange="preview3(this)"/>
							</div>
							<div class="form-group col-md-4">
								<div id="preview1" ></div>
							</div>
							<div class="form-group col-md-4">
								<div id="preview2" ></div>
							</div>
							<div class="form-group col-md-4">
								<div id="preview3" ></div>
							</div>

							<div class="form-group col-md-12">
								<label for="pro_desc"><i class="fa fa-fw fa-check-square"></i>�ӫ~����</label>	
				                <textarea class="form-control" name="pro_desc" rows="10" cols="40"  placeholder="�¤ѵM���L�K�[�I���o~"><%= (spVO==null)? "" : spVO.getPro_desc()%></textarea>
				            </div> 
				            <div class="form-group col-md-3">
								<label for="isrecomm"><i class="fa fa-fw fa-check-square"></i>�O�_���˦��ӫ~</label>
								<select name="isrecomm" class="form-control" required="required">
									<option value="" <%= (spVO==null)?"selected":"" %>>�п��</option>
									<option value="1" <%= (spVO==null)?"":"" %>>�O</option>
									<option value="2" <%= (spVO==null)?"":"" %>>�_</option>
								</select>
				            </div>
				            <div class="form-group col-md-3">
				            	<br/>
				            	<label><font color="#d43f3a"><i class="fa fa-fw fa-check-square"></i>�����������</font></label>
				            </div>
				             <div class="form-group col-md-6">
				             	<div class="pull-right">
				                <span style="margin-top: 16px; margin-right: 20px;" class=" btn btn-default" id="createMagic"><i class="fa fa-fw fa-magic fa-lg"></i>���_�p���s</span>
				               <input type="submit" class="btn btn-primary " value="�إ�">
				                </div>
				                <input type="hidden" name="action" value="insert">
				            </div>
				        </form>
	    			</div>
				</div><!--class="col-sm-9"-->
		</div>
	</div>
</section>
<jsp:include page="/f/frag/f_footer1.jsp"/>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/b/res/js/jquery-1.11.0.js"></script> --%>
<script>      
//���J�����Ϥ�
function preview1(file) {

    if (file.files && file.files[0]) {
        var reader = new FileReader();
        reader.onload = function (evt) {
            $("#preview1").html( '<img width="80px" height="80px" src="' + evt.target.result + '" />');
        }
        $("#preview1").show();
        reader.readAsDataURL(file.files[0]);
    }
}
function preview2(file) {

    if (file.files && file.files[0]) {
        var reader = new FileReader();
        reader.onload = function (evt) {
            $("#preview2").html( '<img width="80px" height="80px" src="' + evt.target.result + '" />');
        }
        $("#preview2").show();
        reader.readAsDataURL(file.files[0]);
    }
}
function preview3(file) {

    if (file.files && file.files[0]) {
        var reader = new FileReader();
        reader.onload = function (evt) {
            $("#preview3").html( '<img width="80px" height="80px" src="' + evt.target.result + '" />');
        }
        $("#preview3").show();
        reader.readAsDataURL(file.files[0]);
    }
}

$('#createMagic').click(function(){
	$( "input[name='name']" ).val( "���E��������" );
	$( "input[name='unitprice']" ).val( "66" );
	$( "input[name='spec1']" ).val( "������ѱ�" );
	$( "input[name='spec2']" ).val( "�饻����" );
	$( "input[name='spec3']" ).val( "�״I�t�ƥ]" );	
	$( "select[name='subcatno']" ).val( "59" );
	$( "select[name='isrecomm']" ).val( "1" );
	$( "textarea[name='pro_desc']" ).val( "�饻��t�s�y�����ѡA�f�P�۵M�B��Q�ƲӡA�i�իD�`�e��\n"+
			"�u�n���u����ѩ��P�U��A�θ_�l�Ͱʤ@�U�A��2~3���Y�i�I\n"+
			"���ެO�f�t�����馡�����A�άO�U�����(���N���סB�q�j�Q����)\n"+
			"���D�`�n�Y�A���A�b�a�ɥΤ馡����~\n"+
			"\n"+
			"*���E�������Y:\n"+
			"���e�q: 40g *3�]\n"+
			"���: ���������A��o�A���Q�A��}�A�J�}�A�ުo�C\n"+
			"���k: ���Y�[�J400cc�����}���A��J�t�ƵN�u�C\n"+
			"GMP���~�{�Ҥu�t�C\n"+
			"\n"+
			"*�t�ƥ]:�]�ݧN��O�s�^\n"+
			"���e�� : 2���L�פ�+�n�߸z+���O+����+���a��+�ɦ̡C\n"+
			"\n"+
			"*�O�s�����P��k:�ȥ��N��O�s\n"+
			"\n"+
			"�j�������ѱ����O�ϥ��ѯ��]�p�����B�j�O���^�B���B�Q�A�H�Ρu�P���v�]Ư��ƽƨ�A�S�Q��Ķ���u�̯��v�^����ơA�C��j�h�O����C�P���O���һĹ[�M�һĶu���V�X���]���ɤ]�|�[�J�C�ġ^�C�o�O�ѩ󴿦��H�ϥΤ��X�j������ӻs�@�ѱ��A���G�o�{�ѱ��ܱo��[�n�Y�A�]����s�F�������������A�o�i�X�F�o�˪��t��C�P���O�ݩ��P�ʡA�|���ѯ������\�J���H���貣�ͩʽ��ܤơA���ѱ��㦳���A�P�M�W�[�u�ʡA�]�|���ѯ�����������ܦ�����A���ѱ��㦳�W�S���C��C\n"+
			"�饻�ԫᦳ�@�q�ɴ��A�X�{�F�\�h�~��c�H���u�P���v�A�åB�ﰷ�d�i��|�����}���v�T�C�{�b�饻�A�L�W��]JAS�^��w�F�������W�w�A�P���w�g���A���w���ʪ����D�F�C���~�A�]���]�������w�P�����ؿW�S�����D�A�ӧ�����J���N�����k�C���t�q�]�[���v�^�������ѱ����X�n�A�@���w�F���t�q�C���ѱ�������l�A�����h�a�l���������D�A���e���w��C�@���ѱ���������Ҥj����20%��40%�C\n" );
});
</script>  
<jsp:include page="/f/frag/f_footer2.jsp"/>