<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>

            <div class="row">
                <div class="col-lg-12">
					<div class="panel panel-default">
                        <div class="panel-heading">
							<h4 style="font-family: �L�n������; margin: 0px"><b>�j�M</b></h4>
                        </div>
                        <div class="panel-body">		
							<form role="form" ACTION="<%=request.getContextPath()%>/back/news/news.do"  method="post" name="form1">
	 						<div class="row">	
								<div class="col-sm-1">
									<div class="form-group">
										<label>�s��</label>
										<input class="form-control" name="newsno" value="">
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>���D</label>
										<input class="form-control" name="title" value="">
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>�o�����(�_)</label>
										<input type="date" class="form-control" name="pubtimeStart" value="">
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>�o�����(�W)</label>
										<input type="date" class="form-control" name="pubtimeEnd" value="">
									</div>
								</div>
							
							
								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label>
										<button class="form-control btn btn-success"><i class="fa fa-search"></i>�j�M</button>
										<input type="hidden" name="action" value="listNews_ByCompositeQuery">
									</div>
								</div>	
								
								
								<div class="col-sm-1">
									<div class="form-group">
										<label>&nbsp;</label>
										<button type="reset" class="form-control btn btn-warning">�M��</button>
									</div>
								</div>	
				
								<div class="col-sm-1">
									<div class="form-group">
										<label>&nbsp;</label>
										<button class="form-control btn btn-info" id="queryAjax">�C�X����</button>
										<input type="hidden" name="action" value="">
									</div>
								</div>
								</div>	
							</form>
						</div>
						</div>
                      
                    </div><!--/.panel .panel-info-->
				</div>
			
			
			
			