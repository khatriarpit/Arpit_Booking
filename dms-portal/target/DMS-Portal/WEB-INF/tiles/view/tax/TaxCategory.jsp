<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#taxcategory").DataTable();
		$("#TaxCat").validate({
	        rules : {
	            name : "required"
	        },
	        messages : {
	            name : "Please enter your Tax Categary"
	        },
	        submitHandler : function(form) {
	            $("#TaxCat").attr("action", "<%=UrlConstants.SAVE_TAXCATEGORY%>?${_csrf.parameterName}=${_csrf.token}");
	            form.submit();
	        }
	    });
	});
</script>
<div>
	<!--BEGIN CONTENT-->
	<div class="page-content">
		<div class="row">
			<div class="col-lg-12">
				<div class="portlet">
					<div class="portlet-header">
						<div class="caption">Add Tax Category</div>
					</div>
					<div class="portlet-body">
						<form:form method="POST" class="form-horizontal" id="TaxCat" ondragstart="return false;" ondrop="return false;">
							<form:input type="hidden" path="id" value="${Tax.id}"></form:input>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">Tax
									Category</label>
								<div class="col-md-6">
									<div class="input-icon right">
										<form:input class="form-control" path="name"
											value="${Tax.name}" />
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">&nbsp;</label>
								<div class="col-md-6">
									<button class="btn orange_bg" type="submit">Submit</button>
												<a href="<%=UrlConstants.INVOICE_TAX_LIST%>" class="btn grey_bg">Cancel</a>
								</div>
							</div>
						</form:form>
						<div class="form-body pal">
							<div class="form-group">
								<h2>Tax Category</h2>
								<div>
									<table class="table table-striped table-bordered table-hover"
										border="1" id="taxcategory">
										<thead>
											<tr>
												<th>Serial No</th>
												<th>Tax Name</th>
												<th>Edit</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${TaxDetails}" var="taxDetail"
												varStatus="loopcounter">
												<tr>
													<td>${loopcounter.count}</td>
													<td>${taxDetail.name}</td>
													<td align="center"><a class="glyphicon glyphicon-pencil"  title="Edit"
														href="<%=UrlConstants.TAX_CATEGORY%>?taxId=${taxDetail.id}"></a><!-- &nbsp;|&nbsp; -->
														<%--Updated By-Johnson Chunara On 22-03-2017 As Per SRS--%>
													<%-- <a class="glyphicon glyphicon-trash" title="Delete"
														href="<%=UrlConstants.DELETE_TAXCATEGORY%>?taxId=${taxDetail.id}"></a> --%></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>