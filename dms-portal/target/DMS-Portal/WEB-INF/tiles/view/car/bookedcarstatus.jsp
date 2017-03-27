<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--BEGIN CONTENT-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-body">
					<div class="box col-md-12">
						<div class="box-header">
							<div class="caption">Booked Car Status</div>
							<%-- <div class="caption box-caption">${empty cartypeVo.id ? 'Add' : 'Update'} Car Type</div> --%>
						</div>
						<hr>
					</div>
					<div class="box col-md-6">
						<div class="box-body">
							<form:form method="POST" class="form-horizontal"
								modelAttribute="command" id="bookedstatusvalidate"
								action = "<%=UrlConstants.SAVE_BOOKEDCARSTATUS%>" ondragstart="return false;" ondrop="return false;">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<form:input type="hidden" path="id" value="${bcsupdate.id}"></form:input>
								<div class="form-body pal">
								<div class="form-group text-center">
									<c:if test="${message != ''}">
										<div class="col-md-3"></div>
										<label for="inputAddress" class="col-md-8"><font
											color="green" style="font-weight: bold" size="4">${message}</font>
										</label>
									</c:if>
								</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">
											booking status <span class='require'>*</span>
										</label>
										<div class="col-md-9">
											<div class="input-icon right">
												<form:input class="form-control" path="bookingStatus"
													name="bookingStatus" value="${bcsupdate.bookingStatus}"
													maxlength="30"></form:input>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-6 col-md-offset-3">
											<div class="input-icon">
												<button class="btn orange_bg" type="submit">${empty cartypeVo.id ? 'Save' : 'Update'}</button>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<c:if test="${msg != ''}">
										<div class="col-md-3"></div>
										<label for="inputAddress" class="col-md-8"> <font
											color="red" style="font-weight: bold" size="4">${msg}</font>
										</label>
									</c:if>
								</div>
								<div class="portlet-body">
									<h2>Booked Car Status List</h2>
									<c:if test="${!empty bcs}">
										<table class="table table-striped table-bordered table-hover"
											border="1" id="bcs">
											<thead>
												<tr>
													<th>Serial No</th>
													<th>Status</th>
													<th class="text-center">Edit / Delete</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${bcs}" var="bcs" varStatus="LoopCounter">
													<tr>
														<td><c:out value="${LoopCounter.count }" /></td>
														<td><c:out value="${bcs.bookingStatus}" /></td>
														<td align="center"><a
															class="glyphicon glyphicon-pencil"
															href="<%=UrlConstants.BOOKED_STATUS%>?bookedsatausId=${bcs.id }"></a>
															| <a class="glyphicon glyphicon-trash" title="Delete"
															href="<%=UrlConstants.DELETE_BOOKEDCARSTATUS%>?bookedsatausId=${bcs.id }"
															onclick="return confirm('Are you sure want to Delete this Package?')"></a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
    $(function(){
        jQuery.validator.addMethod("alpha", function(value, element) {carType
            return this.optional(element) || /^[A-Za-z]+$/.test(value)
        },"Alpha only");

        $("#bookedstatusvalidate").validate({
            rules : {
            	bookingStatus : {
                    required : true,
                    alpha : true
                }
            },
            messages : {
            	bookingStatus: {
                	required : "Please Enter status"
                }
            },
            submitHandler : function(form) {
                $("#bookedstatusvalidate").attr("action", "<%=UrlConstants.SAVE_BOOKEDCARSTATUS%>?${_csrf.parameterName}=${_csrf.token}");
								form.submit();
							}
						});
	});
</script>