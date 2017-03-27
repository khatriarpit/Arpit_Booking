<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#user-role").validate({
		rules : {
			description : {
				required : true,
				minlength: 2
			},
			role : {
				required : true,
				minlength: 2
			}, 
			permissions : {
				required : true
			}, 
			submitHandler : function(form) {
				form.submit();
			}
		}
	});
});
  </script>
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-body">
					<div class="box col-md-6">
						<div class="box-header">
							<div class="caption box-caption">User Role</div>
							<!--<div class="tools"><i class="fa fa-chevron-up"></i><i data-toggle="modal" data-target="#modal-config" class="fa fa-cog"></i><i class="fa fa-refresh"></i><i class="fa fa-times"></i></div>-->
						</div>
						<div class="box-body col-md-6">
							<form:form action="<%=UrlConstants.SAVE_USER_ROLE%>" class="form-horizontal" method="POST" id="user-role">
								<div class="form-body pal">
									 <div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Permission<span
											class='require'>*</span></label>
										<div class="col-md-9">
													<select name="permissions" multiple="true" id="permissions" class="form-control select2">
														<c:forEach var="userpermission" items="${Permissions}">

												<option value="${userpermission.id}"> ${userpermission.name}</option>
												</c:forEach></select>
												<small>Press Ctrl to select multiple</small>

										</div>
										
										
										<table class="table table-striped table-bordered table-hover"
										id="paymentdetails" border="1" style="display: none;">
										<thead>
											<tr>
												<th>Object</th>
												<th>canRead</th>
												<th>canWrite</th>
												<th>canCreate</th>
												<th>canRemove</th>
												<th>canExport</th>
											</tr>
										</thead>
									</table>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Enter
											Role<span class='require'>*</span>
										</label>
										<div class="col-md-9">
											<form:input path="role" class="form-control"
												placeholder="Enter Role"/>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Enter
											Description<span class='require'>*</span>
										</label>
										<div class="col-md-9">
										<form:textarea class="form-control" path="description"
                                        placeholder="Description"/>

										</div>
									</div>
									<div class="form-group">
										<div class="col-md-6 col-md-offset-3">
											<div class="input-icon">
												<input type="submit" class="btn orange_bg" value="submit">
												<button class="btn grey_bg" type="reset">Reset</button>
											</div>
										</div>
									</div>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
