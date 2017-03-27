<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
        $("#company-master").validate({
            rules : {
            	comapanyName : "required",
                companyId : "required",
                contactPersonName : "required",
                emailId : {
                	required : true
                }
            },
            messages : {
            	comapanyName : "Please Enter Company Name",
            	companyId : "Please Enter Comapany Id",
            	contactPersonName : "Please Enter Contact Person Name",
            	emailId : "Please enter a valid email address"
            },
            submitHandler : function(form) {
                $("#company-master").attr("action", "<%=UrlConstants.SAVE_COMAPANY_MASTER%>?${_csrf.parameterName}=${_csrf.token}");
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
        } else {
            return true;
        }
        if (charCode > 31 && (charCode < 48 || charCode > 57 || (charCode === 46 && dotvalue === false) )) {
           if(charCode === 46){
            dotvalue = true;
            return true;
           } else{
                return false;
           }
        //return false;
        } else {
         return true;
        }
    } catch (err) {
        alert(err.Description);
    }
}
function onlyAlphabets(e, t) {
    try {
        if (window.event) {
            var charCode = window.event.keyCode;
        }
        else if (e) {
            var charCode = e.which;
        }
        else { return true; }
        if (charCode == 8 || (charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123))
            return true;
        else
            return false;
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
				<div class="portlet-body">
					<div class="box col-md-12">
						<div class="box-header">
							<div class="caption box-caption">${empty companyMasterDetails.id ? 'Add' : 'Update'}
								Company Master</div>
						</div>
					</div>
					<div class=" ">	
						<form:form class="form-horizontal" modelAtttributte="command"
							method="POST" id="company-master" ondragstart="return false;" ondrop="return false;">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<form:input path="id" type="hidden"
								value="${companyMasterDetails.id}" />
							<form:input type="hidden" path="tanentID"
								value="${companyMasterDetails.tanentID}"></form:input>
							<div class="form-body pal">
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label">Company
										Name <span class='require'>*</span>
									</label>
									<div class="col-md-6">
										<div class="input-icon right">
											<form:input class="form-control" path="comapanyName"
												maxlength="50" value="${companyMasterDetails.comapanyName}"
												placeholder="Enter Company Name" />
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label">Company
										Id <span class='require'>*</span>
									</label>
									<div class="col-md-6">
										<div class="input-icon right">
											<form:input class="form-control" path="companyId"
												maxlength="50" value="${companyMasterDetails.companyId}"
												placeholder="Enter Company Id" />
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label">Contact
										Person Name <span class='require'>*</span>
									</label>
									<div class="col-md-6">
										<div class="input-icon right">
											<form:input class="form-control" path="contactPersonName"
												maxlength="50"
												value="${companyMasterDetails.contactPersonName}"
												placeholder="Enter Contact Person Name" />
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label">Email
										ID <span class='require'>*</span>
									</label>
									<div class="col-md-6">
										<div class="input-icon right">
											<form:input class="form-control" path="emailId" id="emailId"
												maxlength="100" type="email"
												value="${companyMasterDetails.emailId}"
												placeholder="Enter Email Id." />
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-11 col-md-offset-1">
										<div class="input-icon">
											<button class="btn orange_bg" type="submit">${companyMasterDetails.id != null ? 'Update' : 'Save'}</button>
											<a href="<%=UrlConstants.LIST_COMAPANY_MASTER%>" class="btn grey_bg">Cancel</a>
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