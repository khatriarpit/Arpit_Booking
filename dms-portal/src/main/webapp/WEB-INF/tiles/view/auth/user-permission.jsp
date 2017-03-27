<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		if ("${message}" != "") {
			$(".alert-success-message").fadeIn("fast").delay("3000").fadeOut("fast");
		}
	});
</script>
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="caption">
					<c:if test="${not empty message}">
						<div class="form-group">
							<label for="inputAddress" class="col-md-2 control-label"></label>
							<div class="col-md-12">
								<div class="alert col-md-6 alert-success-message" role="alert">${message}</div>
							</div>
						</div>
					</c:if>
				</div>
				<div class="portlet-body">
					<div class="box">
						<div class="box-header">
							<div class="caption box-caption">User Permission</div>
							<!--<div class="tools"><i class="fa fa-chevron-up"></i><i data-toggle="modal" data-target="#modal-config" class="fa fa-cog"></i><i class="fa fa-refresh"></i><i class="fa fa-times"></i></div>-->
						</div>
						<div class="box-body col-md-9">
							<form:form modelAttribute="command" method="POST" id="user-permission-form"
								class="form-horizontal" ondragstart="return false;"
								ondrop="return false;">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<form:input type="hidden" path="id" value="${userPermission.id}" />
								<form:input type="hidden" path="tanentID"
									value="${userPermission.tanentID}" />
								<div class="form-group">
									<label for="inputAddress" class="col-md-3 control-label">Name
										<span class='require'>*</span>
									</label>
									<div class="col-md-9">
										<form:input type="text" path="name" class="form-control"
											placeholder="Permission Name" value="${userPermission.name}" />
									</div>
								</div>
								<div class="col-md-12 col-md-offset-3 flot_for_hour_left">
									<%-- <table class="table table-bordered">
										<tr>
											<th>Serial No.</th>
											<!-- <th>Package</th>
												<th>Entity</th> -->
											<th>Create</th>
											<th>Export</th>
											<th>Read</th>
											<th>Remove</th>
											<th>Write</th>
										</tr>
										<c:forEach items="${userPermission.listOfPermissionAssign}"
											var="permissionAssign" varStatus="loopcounter">
											<tr>
												<td>${loopcounter.count}</td>
												<!--
													<td class="text-center"><input type="text" name="" path=""></td>
													<td class="text-center"><input type="text" name="read" path=""></td>
													 <c:if test="${permissionAssign.canCreate}">checked="checked"</c:if> 
												-->
												<td class="text-center"><input type="checkbox"
													name="canCreate"
													${permissionAssign.canCreate eq true ? 'checked' : ''} /></td>
												<td class="text-center"><input type="checkbox"
													name="canExport"
													${permissionAssign.canExport eq true ? 'checked' : ''} /></td>
												<td class="text-center"><input type="checkbox"
													name="canRead"
													${permissionAssign.canRead eq true ? 'checked' : ''} /></td>
												<td class="text-center"><input type="checkbox"
													name="canRemove"
													${permissionAssign.canRemove eq true ? 'checked' : ''} /></td>
												<td class="text-center"><input type="checkbox"
													name="canWrite"
													${permissionAssign.canWrite eq true ? 'checked' : ''} /></td>
											</tr>
										</c:forEach>
									</table> --%>
									<table id="listOperating" class="table table-bordered">
										<thead>
											<tr>
												<th>Serial No.</th>
												<th>Package Name</th>
												<th>Entity Name</th>
												<th>Create</th>
												<th>Export</th>
												<th>Read</th>
												<th>Remove</th>
												<th>Write</th>
											</tr>
										</thead>
										<tbody id="userPackageBody">
											<c:forEach items="${listOUserPackage}" var="userPackage" varStatus="loopcounter">
												<tr class="rowUserPackage">
													<td>${loopcounter.count}</td>
													<td><input type="hidden" value="${userPackage.id}" />${userPackage.packagename}</td>
													<td>${userPackage.packagepath}</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
												</tr>
												<c:forEach items="${userPackage.listOfuserEntityPackage}"
													var="userEntityPackage" varStatus="loopcounter1">
													<tr class="rowUserEntity" data-entity-name="${loopcounter.count}-${loopcounter1.count}" data-entity-id="${loopcounter1.count}">
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>${userEntityPackage.entityname}</td>
														<td><input class="pointer checkbox1-${loopcounter.count}-${loopcounter1.count}" type="checkbox" name="canCreate" /></td>
														<td><input class="pointer checkbox2-${loopcounter.count}-${loopcounter1.count}" type="checkbox" name="canExport" /></td>
														<td><input class="pointer checkbox3-${loopcounter.count}-${loopcounter1.count}" type="checkbox" name="canRead" /></td>
														<td><input class="pointer checkbox4-${loopcounter.count}-${loopcounter1.count}" type="checkbox" name="canRemove" /></td>
														<td><input class="pointer checkbox5-${loopcounter.count}-${loopcounter1.count}" type="checkbox" name="canWrite" /></td>
													</tr>
												</c:forEach>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<div class="form-group">
									<div class="col-md-6 col-md-offset-3">
										<button class="btn orange_bg" id="save-button" type="button">Save</button>
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
<script type="text/javascript">
	$("body").delegate("#save-button","click",function(){
		var lengthVal = $('#userPackageBody tr.rowUserEntity').length;
		var arrayList = [];
		var id = $("#id").val();
		var name = $("#name").val();
		var tanentID = $("#tanentID").val();
		arrayList.push({id : id});
		arrayList.push({name : name});
		arrayList.push({tanentID : tanentID});
		arrayList.push({count : lengthVal});
       	$('#listOperating > #userPackageBody tr.rowUserEntity').each(function(index, value) {
			var dataID = $(this).data("entityName");
			var ID = $(this).data("entityId");
			var checkbox1 = $(".checkbox1-"+dataID).is(':checked');
			var checkbox2 = $(".checkbox2-"+dataID).is(':checked');
			var checkbox3 = $(".checkbox3-"+dataID).is(':checked');
			var checkbox4 = $(".checkbox4-"+dataID).is(':checked');
			var checkbox5 = $(".checkbox5-"+dataID).is(':checked');
			arrayList.push({
				index : ID + "," + checkbox1 + "," + checkbox2 + "," + checkbox3 + "," + checkbox4 + "," + checkbox5
			});
       	});
       	console.log(arrayList);
       	callblock();
       	$.ajax({
       		type : 'POST',
            url : "<%=UrlConstants.SAVE_PERMISSION%>",
            data: JSON.stringify(arrayList),
            dataType: 'json',
			success : function(data) {
				endblockUI();
				alert("success")
			},
			error : function() {
				endblockUI();
				alert("failed");
			}
		});
	});
</script>