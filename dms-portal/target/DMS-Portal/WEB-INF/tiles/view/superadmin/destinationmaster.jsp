<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#destinationList").DataTable();
		if("${message}" != "") {
			$(".alert-success-message").fadeIn("fast").delay("3000").fadeOut("fast");
		}
        $("#adddestination").validate({
            rules : {
            	sourcePlace : {
                    required : true
                },
                destinationPlace : {
                    required : true
                },
                price : {
                    required : true
                }
            },
            messages : {
            	sourcePlace : "Please Select CarType",
            	destinationPlace : "Please Enter Car Model",
            	price : "Please Enter Car Model"
            },
            submitHandler : function(form) {
                $("#adddestination")
                        .attr("action", "<%=UrlConstants.SAVE_DESTINATION%>?${_csrf.parameterName}=${_csrf.token}");
					form.submit();
				}
			});
		});
	function onlyNos(e, t) {
	    try {
	        if (window.event) {
	            var charCode = window.event.keyCode;
	        } else if (e) {
	            var charCode = e.which;
	        } else { return true; }
	        if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	            return false;
	        }
	        return true;
	    }
	    catch (err) {
	        alert(err.Description);
	    }
	}
</script>
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<c:if test="${not empty message}">
					<div class="form-group">
						<label for="inputAddress" class="col-md-2 control-label"></label>
						<div class="col-md-12">
							<div class="alert col-md-6 alert-success-message" role="alert">${message}</div>
						</div>
					</div>
				</c:if>
				<div class="portlet-body">
					<div class="box col-md-12">
						<div class="box-header">
							<div class="caption box-caption">${empty destination.id ? 'Add' : 'Update'}
								Source And Destination</div>
						</div>
					</div>
					<div class="box-body">
						<form:form method="POST" class="form-horizontal"
							id="adddestination" ondragstart="return false;" ondrop="return false;">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<div class="form-body pal">
								<form:input path="id" type="hidden" value="${destination.id}" />
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label">
										Source Place<span class='require'>*</span>
									</label>
									<div class="col-md-6">
										<div class="input-icon right">
											<form:input class="form-control" maxlength="50"
												path="sourcePlace" value="${destination.sourcePlace}"></form:input>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label">
										Destination Place<span class='require'>*</span>
									</label>
									<div class="col-md-6">
										<div class="input-icon right">
											<form:input class="form-control" maxlength="50"
												path="destinationPlace"
												value="${destination.destinationPlace}"></form:input>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label">
										Price<span class='require'>*</span>
									</label>
									<div class="col-md-6">
										<div class="input-icon right">
											<form:input class="form-control" maxlength="10"
												onkeypress="return onlyNos(event,this);" path="price"
												value="${destination.price}"></form:input>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-6 col-md-offset-2">
										<div class="input-icon">
											<button class="btn orange_bg" type="submit">${empty destination.id ? 'Submit' : 'Update'}</button>
											<a href="<%=UrlConstants.DESTINATION_MASTER%>"
												class="btn grey_bg">Cancel</a>
										</div>
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div>
				<c:if test="${not empty destinationList}">
					<h2>List Of Source And Destinations</h2>
					<table id="destinationList"
						class="table table-striped table-bordered table-hover" border="1">
						<thead>
							<tr>
								<th>Serial No</th>
								<th>Source Place</th>
								<th>Destination Place</th>
								<th>Price</th>
								<th class="text-center">Edit / Delete</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${destinationList}" var="destination"
								varStatus="loopcounter">
								<tr>
									<td><c:out value="${loopcounter.count}" /></td>
									<td><c:out value="${destination.sourcePlace}" /></td>
									<td><c:out value="${destination.destinationPlace}" /></td>
									<td><c:out value="${destination.price}" /></td>
									<td align="center"><a class="glyphicon glyphicon-pencil"
										href="<%=UrlConstants.DESTINATION_MASTER%>?destinationId=${destination.id}"
										title="Edit"> </a> | <a class="glyphicon glyphicon-trash"
										title="Delete"
										href="<%=UrlConstants.DELETE_DESTINATION%>?destinationId=${destination.id }"
										onclick="return confirm('Are you sure want to Delete this Destination?')">
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</div>
		</div>
	</div>
</div>
