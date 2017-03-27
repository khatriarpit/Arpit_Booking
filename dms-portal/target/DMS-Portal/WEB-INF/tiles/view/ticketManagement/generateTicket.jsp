<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://cdn.ckeditor.com/4.5.7/standard/ckeditor.js"></script>
<!--BEGIN CONTENT-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-body">
					<h4><c:out value="${message}" ></c:out></h4>
					<div class="portlet-header">
					</div>

					<div class="portlet-body">
						<div class="box col-md-6">
							<div class="box-header">
								<div class="caption box-caption">Generate Ticket</div>
								<!--<div class="tools"><i class="fa fa-chevron-up"></i><i data-toggle="modal" data-target="#modal-config" class="fa fa-cog"></i><i class="fa fa-refresh"></i><i class="fa fa-times"></i></div>-->
							</div>
							<div class="box-body col-md-6">
								<form:form method="POST" action="<%=UrlConstants.GENERATE_SUPPORT_TIKCKET%>" class="form-horizontal"
										   ondragstart="return false;" ondrop="return false;">
								<div class="form-body pal">
									<div class="form-group"> <label for="inputAddress" class="col-md-3 control-label">Ticket Type <span class="require">*</span></label>
										<div class="col-md-9">
											<div class="input-icon right">

												<form:select path = "departmentType.id" class="form-control select2" id="department">
													<form:option value="0">Select Department Type</form:option>
													<c:forEach var="department" items = "${departments}">
														<form:option value="${department.id}">${department.departmentName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</div>
									<input type="hidden" name="${_csrf.parameterName}"
										   value="${_csrf.token}" />

									<div class="form-group"><label for="inputAddress" class="col-md-3 control-label">Topic Name<span class="require">*</span></label>

										<div class="col-md-9">
											<div class="input-icon right">
												<form:input type="text" name="topicName" path="topicName"
															value="" required = "true" class="form-control" placeholder="Enter Topic Name" />
											</div>
										</div>
									</div>

									<div class="form-group"><label for="inputAddress" class="col-md-3 control-label">Description<span class="require">*</span></label>

										<div class="col-md-9">
											<div class="input-icon right"><form:textarea type="text" name="description" path="description" id = "description" class="form-control" placeholder="Enter Description"
																						 value="" required = "true"/></div>
										</div>
									</div>

									<div class="form-group">
										<div class="col-md-6 col-md-offset-3">
											<div class="input-icon"><button class="btn orange_bg" type="submit" onclick = "return validation();"> Submit</button>
											</div>
										</div>
										<div class="col-md-6 col-md-offset-3">
											<a href="<%=UrlConstants.DASHBOARD%>" class="btn grey_bg">Cancel</a>
										</div>
									</div>
									<div class="col-md-6 col-md-offset-3"></div>
									<input type = "button" class="btn orange_bg" type="button" value="Clear" onclick="cleardata();"/>
									</form:form>
								</div>


							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
    CKEDITOR.replace('description');

</script>
<script>
    function cleardata() {
        CKEDITOR.instances['description'].setData('');
        document.getElementById("topicName").value = "";
        document.getElementById("department").selectedIndex = 0;
    }
</script>
<script>
    function validation(){
        textbox_data = CKEDITOR.instances.description.getData();
        if(document.getElementById('department').selectedIndex==0) {
            alert('Please select the department');
            return false;
        } else if(document.getElementById("topicName").value==''){
            alert('Please Enter the Topic');
            return false;
        }
        else if(textbox_data==='') {
            alert('Enter Comment');
            return false;
        }else    {
            return true;
        }
    }
</script>
