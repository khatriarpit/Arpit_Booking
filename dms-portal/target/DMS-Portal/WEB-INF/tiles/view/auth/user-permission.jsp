<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(".select2").select2();
</script>
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-body">
					<div class="box">
						<div class="box-header">
							<div class="caption box-caption">User Permission</div>
							<!--<div class="tools"><i class="fa fa-chevron-up"></i><i data-toggle="modal" data-target="#modal-config" class="fa fa-cog"></i><i class="fa fa-refresh"></i><i class="fa fa-times"></i></div>-->
						</div>
						<div class="box-body col-md-9">
							<form:form action="<%=UrlConstants.SAVE_PERMISSION%>"
								modelAttribute="command" method="POST" id="taxSlabForm"
								class="form-horizontal" ondragstart="return false;"
								ondrop="return false;">
								<%-- <form action="#" class="form-horizontal">
								<div class="form-body pal"> --%>
								<div class="form-group">
									<label for="inputAddress" class="col-md-3 control-label">Name
										<span class='require'>*</span>
									</label>

									<div class="col-md-9">
										<input type="text" name="name" path="name"
											class="form-control" placeholder="Name">
									</div>
								</div>
								<div class="col-md-12 col-md-offset-3 flot_for_hour_left">
									<table class="table table-bordered">
										<tr>
											<th>Series</th>
											<!-- <th>Package</th>
												<th>Entity</th> -->
											<th>Create</th>
											<th>Export</th>
											<th>Read</th>
											<th>Remove</th>
											<th>Write</th>
										</tr>
										<c:forEach begin="0" end="2" varStatus="loopcounter">
											<tr>
												<td>${loopcounter.count}</td>
												<!-- <td class="text-center"><input type="text" name=""
														path=""></td>
													<td class="text-center"><input type="text" name="read"
														path=""></td> -->
												<td class="text-center">
												<input type="checkbox"
													name="canCreate"></td>
												<td class="text-center">
												<input type="checkbox"
													name="canExport"></td>
												<td class="text-center"><input type="checkbox"
													name="canRead"></td>
												<td class="text-center"><input type="checkbox"
													name="canRemove"></td>
												<td class="text-center"><input type="checkbox"
													name="canWrite"></td>
											</tr>
										</c:forEach>
									</table>
								</div>
								<div class="form-group">
									<div class="col-md-6 col-md-offset-3">
										<button class="btn orange_bg" type="submit">Save</button>
									</div>
								</div>
							</form:form>
						</div>
						</form>

					</div>
				</div>

			</div>
		</div>
	</div>
</div>
</div>
