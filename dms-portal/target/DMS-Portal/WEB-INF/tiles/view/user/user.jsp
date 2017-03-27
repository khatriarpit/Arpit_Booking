<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@page import="org.springframework.security.core.Authentication" %>
<%@page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@page import="com.emxcel.dms.core.model.user.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">

$(function () {
     $(":file").change(function () {
        if (this.files && this.files[0]) {
            var reader = new FileReader();
            reader.onload = imageIsLoaded;
            reader.readAsDataURL(this.files[0]);
        }
    });
});

function imageIsLoaded(e) {
    $('#myImg').attr('src', e.target.result);
};
    $(document).ready(function(){
    	if ("${message}" != "") {
			$(".alert-error-message").fadeIn("fast").delay("3000").fadeOut("fast");
		}

        $('#countryId').on('change',function(){
            var countryId = $(this).val();
            if(countryId != '0'){
                $.ajax({
                    type:'POST',
                    url:'getState',
                    data:'${_csrf.parameterName}=${_csrf.token}&countryId='+countryId,
                    success:function(data){
                        $('#stateId').html('<option value="0">Select State</option>');
                        for ( var i = 0; i < data.length; i++) {
                            var state = data[i];
                            $('#stateId').append("<option value=\"" + state.id + "\">" + state.stateName+ "</option>");
                        }
                        $('#cityId').html('<option value="">Select State First</option>');
                    }
                });
            }else{
                $('#stateId').html('<option value="">Select Country First</option>');
                $('#cityId').html('<option value="">Select State First</option>');
            }
        });
        $('#stateId').on('change',function(){
            var stateId = $(this).val();
            if(stateId !='0'){
                $.ajax({
                    type:'POST',
                    url:'getCity',
                    data:'${_csrf.parameterName}=${_csrf.token}&stateId='+stateId,
                    success:function(data){
                        $('#cityId').html("<option value='0'>Select City</option>");
                        for ( var i = 0; i < data.length; i++) {
                            var city = data[i];
                            $('#cityId').append("<option value=\"" + city.id + "\">" + city.cityName+ "</option>");
                        }
                    }
                });
            }else{
                $('#cityId').html('<option value="">Select State First</option>');
            }
        });
    });

    $(document).ready(function() {
        $.validator.addMethod("passwordRequired", function (value, element) {
            return /[\@\#\$\%\^\&\*\(\)\_\+\!]/.test(value) && /[a-z]/.test(value) && /[0-9]/.test(value) && /[A-Z]/.test(value)
        });
        jQuery.validator.addMethod("genderRequired", function(
                value, element) {
            var val = value;
            if (val === '-1') {
                return false;
            } else {
                return true;
            }
        }, "Please Select Gender");
        jQuery.validator.addMethod("roleRequired", function(
                value, element) {
            var val = value;
            if (val === "0") {
                return false;
            } else {
                return true;
            }
        }, "Please Select User Role");
        jQuery.validator.addMethod("packageRequired", function(
                value, element) {
            var val = value;
            if (val === "0") {
                return false;
            } else {
                return true;
            }
        }, "Please Select Package");
        jQuery.validator.addMethod("countryRequired", function(
                value, element) {
            var val = value;
            if (val === "0") {
                return false;
            } else {
                return true;
            }
        }, "Please Select Country");
        jQuery.validator.addMethod("stateRequired", function(
                value, element) {
            var val = value;
            if (val === "0") {
                return false;
            } else {
                return true;
            }
        }, "Please Select State");
        jQuery.validator.addMethod("cityRequired", function(
                value, element) {
            var val = value;
            if (val === "0") {
                return false;
            } else {
                return true;
            }
        }, "Please Select City");
        jQuery.validator.addMethod("conpassRequired", function(
                value, element) {
            var val = value;
            if (val === "0") {
                return false;
            } else {
                return true;
            }
        }, "Please Select Country");
        jQuery.validator.addMethod("enabledRequired", function(
                value, element) {
            var val = value;
            if (val === "-1") {
                return false;
            } else {
                return true;
            }
        }, "Please Select Active or Deactive");
        
        var confirmPassword = "${empty userDetails.id ? false : true}";
        $("#user-form").validate({
            rules : {
                firstName : "required",
                middleName : "required",
                lastName : "required",
                username : {
                    required : true
                },
                contactNo : {
                    required : true,
                    minlength : 10
                },
                roleID : {
                    required : true,
                    roleRequired : true
                },
                countryId : {
                    required : true,
                    countryRequired : true
                },
                stateId : {
                    required : true,
                    stateRequired : true
                },
                cityId : {
                    required : true,
                    cityRequired : true
                },
                password : {
                    required : true,
                    minlength : 8,
                    passwordRequired : true
                },
                gender : {
                    required : true,
                    genderRequired : true
                },
                address : "required",
                emailId : {
                    required : true
                },
                tenantPackageID : {
                	required : true,
                	packageRequired : true
                },
                enabled : {
                	required : true,
                    enabledRequired : true
                }
            },
            messages : {
                firstName  : "Please enter your First Name",
                middleName  : "Please enter your Middle Name",
                lastName  : "Please enter your Last Name",
                username : "Please enter User Name",
                roleID : "Please select User Role",
                countryId : "Please select Country",
                stateId : "Please select State",
                cityId : "Please select City",
                contactNo : {
                    required : "Please provide a contactNo",
                    minlength : "Your contactNo must be at least 10 characters long"
                },
                password : {
                    required :"Please enter password",
                    minlength : "Your password must be at least 8 characters long",
                    passwordRequired : "Please Enter Password Including 1 Uppercase and 1 Special character and 1 Number must be there, Like : 'Example@123'"
                },
                gender :  "Please select Gender",
                tenantPackageID : "Please Select Package",
                address : "Please enter Address",
                emailId : "Please enter a valid email address",
                enabled : "Please select Active or Deactive"
            },
            submitHandler : function(form) {
                if(confirmPassword) {
                	if("${fn:length(companies)}" > 0) {
                		alert("asdasd");
                		checkValNull();
                	}
                    $("#user-form").attr("action", "<%=UrlConstants.SAVE_USER%>?${_csrf.parameterName}=${_csrf.token}");
                    form.submit();
                }
            }
        });
        
        function checkValNull() {
        	var packageIDSelect = $("#tenantPackageID").val();
        	if(packageIDSelect != 0 || packageIDSelect != '') {
        		return true;
        	} else {
        		return false;
        	}
        }

        $("#conpass").change(function(){
            var confirm = $(this).val();
            var password = $("#password").val();
            if (password != confirm) {
                $("#conpassError").show();
                confirmPassword = false;
            } else {
                $("#conpassError").hide();
                confirmPassword = true;
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
<!--BEGIN CONTENT-->
<%
	HttpSession currentSession = request.getSession();
	User userBeanVO = (User) currentSession.getAttribute("user");
%>
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<c:if test="${not empty message}">
					<div class="form-group">
						<label for="inputAddress" class="col-md-2 control-label"></label>
						<div class="col-md-12">
							<div class="alert col-md-6 alert-error-message" role="alert">${message}</div>
						</div>
					</div>
				</c:if>
				<div class="portlet-body">
					<div class="box col-md-12">
						<div class="box-header">
							<security:authorize access="hasAnyRole('ROLE_SUPERADMIN')">
								<div class="caption box-caption">Add Super Admin</div>								
							</security:authorize>
							<security:authorize access="hasAnyRole('ROLE_ADMIN')">
								<div class="caption box-caption">${userDetails.id != null ? 'Update User' : 'Add User'}</div>								
							</security:authorize>
						</div>
						<div class="box-body">
							<form:form class="form-horizontal" modelAtttributte="command"
								method="POST" id="user-form" enctype="multipart/form-data" ondragstart="return false;" ondrop="return false;">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<form:input type="hidden" path="id" value="${userDetails.id}"></form:input>
								<form:input type="hidden" path="tanentID"
									value="${userDetails.tanentID}"></form:input>
								<div class="form-body pal">
									<div class="col-md-6">
										<div class="form-group">
											<label for="inputAddress" class="col-md-4 control-label">First
												Name <span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" path="firstName"
														readonly="${empty userDetails.id ? 'false': 'true'}"
														maxlength="50" value="${userDetails.firstName}"
														onkeypress="return onlyAlphabets(event,this);"
														placeholder="Enter First Name" />
												</div>
											</div>
										</div>
										<div class="form-group">
											<label for="inputAddress" class="col-md-4 control-label">Middle
												Name <span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" path="middleName"
														readonly="${empty userDetails.id ? 'false': 'true'}"
														maxlength="50" value="${userDetails.middleName}"
														onkeypress="return onlyAlphabets(event,this);"
														placeholder="Enter Middle Name" />
												</div>
											</div>
										</div>
										<div class="form-group">
											<label for="inputAddress" class="col-md-4 control-label">Last
												Name <span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" path="lastName"
														readonly="${empty userDetails.id ? 'false': 'true'}"
														maxlength="50" value="${userDetails.lastName}"
														onkeypress="return onlyAlphabets(event,this);"
														placeholder="Enter Last Name" />
												</div>
											</div>
										</div>
										<div class="form-group">
											<label for="inputAddress" class="col-md-4 control-label">User
												Name <span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" path="username"
														readonly="${empty userDetails.id ? 'false': 'true'}"
														maxlength="50" value="${userDetails.username}"
														placeholder="Enter User Name" />
													<c:if test="${errorMsg != null && errorMsg eq 'username'}">
														<label class="error">${errorMsg eq 'username' ? 'Username Already Exists' : ''}</label>
													</c:if>
												</div>
											</div>
										</div>
										<div class="form-group"
											style="display:${empty userDetails.id ? 'block;' : 'none;'}">
											<label for="inputAddress" class="col-md-4 control-label">Password
												<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:password class="form-control" path="password"
														readonly="${empty userDetails.id ? 'false': 'true'}"
														value="${userDetails.password}" maxlength="20"
														placeholder="Enter Password" />
												</div>
											</div>
										</div>
										<div class="form-group"
											style="display:${empty userDetails.id ? 'block;' : 'none;'}">
											<label for="inputAddress" class="col-md-4 control-label">Confirm
												Password<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<input class="form-control" value="${userDetails.password}"
														type="password" name="cpass"
														placeholder="Re-enter Password" id="conpass"> <label
														style="display: none;" id="conpassError" class="error">Enter
														Confirm Password Same as Password</label>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label for="inputAddress" class="col-md-4 control-label">Email
												ID <span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" path="emailId"
														id="emailId" maxlength="100" type="email"
														value="${userDetails.emailId}"
														readonly="${empty userDetails.id ? 'false': 'true'}"
														placeholder="Enter Email Id." />
													<c:if test="${errorMsg != null && errorMsg eq 'email'}">
														<label class="error">${errorMsg eq 'email' ? 'Email Already Existing' : ''}</label>
													</c:if>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label for="inputAddress" class="col-md-4 control-label">Contact
												No. <span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" path="contactNo"
														readonly="${empty userDetails.id ? 'false': 'true'}"
														maxlength="10" id="contactNo"
														value="${userDetails.contactNo}"
														onkeypress="return onlyNos(event,this);"
														placeholder="Enter Contact No." />
												</div>
											</div>
										</div>
										<div class="form-group">
											<label for="inputAddress" class="col-md-4 control-label">User
												Role<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<security:authorize access="hasRole('ROLE_ADMIN')">
														<form:select class="form-control" path="roleID"
															readonly="${empty userDetails.id ? '': 'readonly'}"
															itemValue="roleID" id="roleID">
															<form:option value="0">Select Role</form:option>
															<c:forEach var="userROLE" items="${userROLE}">
																<c:if test="${userROLE.role != 'ROLE_SUPERADMIN'}">
																	<form:option value="${userROLE.id}"
																		selected="${userROLE.id eq userDetails.roleID ? 'selected' : ''}">${userROLE.role}</form:option>
																</c:if>
															</c:forEach>
														</form:select>
													</security:authorize>
													<security:authorize access="hasAnyRole('ROLE_SUPERADMIN','ROLE_USER')">
														<c:if test="${not empty userDetails.id}">
															<form:input class="form-control" type="hidden"
																path="roleID" value="${userDetails.roleID}" />
															<input readonly="readonly" class="form-control"
																type="text" value="${userDetails.userRole.role}" />
														</c:if>
														<c:if test="${empty userDetails.id}">
															<security:authorize access="hasRole('ROLE_SUPERADMIN')">
																<c:forEach var="userROLE" items="${userROLE}">
																	<c:if test="${userROLE.role eq 'ROLE_SUPERADMIN'}">
																		<form:input class="form-control" type="hidden"
																			path="roleID" value="${userROLE.id}" />
																		<input readonly="readonly" class="form-control"
																			type="text" value="ROLE_SUPERADMIN" />
																	</c:if>
																</c:forEach>
															</security:authorize>
															<security:authorize access="hasRole('ROLE_USER')">
																<c:forEach var="userROLE" items="${userROLE}">
																	<c:if test="${userROLE.role eq 'ROLE_USER'}">
																		<form:input class="form-control" type="hidden"
																			path="roleID" value="${userROLE.id}" />
																		<input readonly="readonly" class="form-control"
																			type="text" value="ROLE_USER" />
																	</c:if>
																</c:forEach>
															</security:authorize>
														</c:if>
													</security:authorize>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label for="inputAddress"
												class="col-md-4 control-label flot_for_hour_left flot_for_hour_left_none">Select
												Gender<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:select class="form-control" path="gender"
														
														itemValue="gender">
														<form:option value="-1">Select Gender</form:option>
														<form:option value="0"
															selected="${userDetails.gender eq '0' ? 'selected': ''}">Male</form:option>
														<form:option value="1"
															selected="${userDetails.gender eq '1' ? 'selected': ''}">Female</form:option>
													</form:select>
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<center>
											<div class="form-group">
												<img id="myImg"
													src="${userDetails.userImage != null ? 'fetch-image?imageName=user/tenants/' : 'assets/images/image.jpg'}${userDetails.userImage != null ? userDetails.tanentID : ''}${userDetails.userImage != null ? '/' : ''}${userDetails.userImage != null ? userDetails.userImage : ''}"
													class="img-responsive" />
												<div class="col-md-4 col-md-offset-4">
													<input type="file" name="imgFile" class="button_only_3"
														multiple />
												</div>
											</div>
										</center>
										<br>
										<div class="form-group">
											<label for="inputAddress" class="col-md-4 control-label">
												Address. <span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" path="address"
														value="${userDetails.address}" maxlength="250"
														placeholder="Enter Address." />
												</div>
											</div>
										</div>
										<div class="form-group">
											<label for="inputAddress"
												class="col-md-4 control-label flot_for_hour_left">Country
												<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:select class="form-control" path="countryId"
														itemValue="countryId">
														<form:option value="0">Select Country</form:option>
														<c:forEach var="countryLst" items="${countryLst}">
															<form:option value="${countryLst.id}"
																selected="${countryLst.id eq userDetails.countryId ?'selected': ''} ">${countryLst.countryName}</form:option>
														</c:forEach>
													</form:select>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label for="inputAddress" class="col-md-4 control-label">State
												<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:select class="form-control" path="stateId"
														itemValue="stateId">
														<form:option value="0">Select State</form:option>
														<c:forEach var="stateLst" items="${stateLst}">
															<form:option value="${stateLst.id}"
																selected="${stateLst.id eq userDetails.stateId ? 'selected' : ''} ">${stateLst.stateName}</form:option>
														</c:forEach>
													</form:select>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label for="inputAddress" class="col-md-4 control-label">City<span
												class='require'>*</span></label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:select class="form-control" path="cityId"
														itemValue="cityId" placeholder="Enter City">
														<form:option value="0">Select City</form:option>
														<c:forEach var="cityLst" items="${cityLst}">
															<form:option value="${cityLst.id}"
																selected="${cityLst.id eq userDetails.cityId ? 'selected' : ''} ">${cityLst.cityName}</form:option>
														</c:forEach>
													</form:select>
												</div>
											</div>
										</div>
										 <div class="form-group">
											<!-- <label for="inputAddress"
												class="col-md-4 control-label flot_for_hour_left flot_for_hour_left_none">Enable<span
												class='require'>*</span>
											</label> -->
											
											<div class="col-md-8">
												<div class="input-icon right">
													<form:hidden path="enabled" value="${0}"/>
													 <%-- <form:select class="form-control" path="enabled"
														itemValue="enabled" id="enabled" >
														<form:option value="-1">Select Enabled</form:option>
														<form:option value="0"
															selected="${userDetails.enabled eq '0' ? 'selected': ''} ">Active</form:option>
														<c:if test="${userDetails.enabled eq '1' ? 'selected': ''}">
														<form:option value="1"
															selected="${userDetails.enabled eq '1' ? 'selected': ''} ">Deactive</form:option>
															</c:if>
													</form:select>  --%>
												</div>
											</div>
										</div>
										<c:if test="${fn:length(tenantPackageList) gt 0}">
											<div class="form-group">
												<label for="inputAddress"
													class="col-md-4 control-label flot_for_hour_left flot_for_hour_left_none">Package<span
													class='require'>*</span>
												</label>
												<div class="col-md-8">
													<div class="input-icon right">
														<form:select class="form-control" path="tenantPackageID"
															itemValue="tenantPackageID" id="tenantPackageID">
															<c:if test="${empty userDetails.id || empty userDetails.tenantPackageID}">
																<form:option value="0">Select Package</form:option>
															</c:if>
															<c:forEach var="tenantPackage" items="${tenantPackageList}">
																<c:if test="${empty userDetails.id || empty userDetails.tenantPackageID}">
																	<form:option value="${tenantPackage.id}"
																		selected="${tenantPackage.id eq userDetails.tenantPackageID ? 'selected' : ''} ">${tenantPackage.name}</form:option>
																</c:if>
																<c:if test="${not empty userDetails.id && tenantPackage.id eq userDetails.tenantPackageID}">
																	<form:option value="${tenantPackage.id}"
																		selected="${tenantPackage.id eq userDetails.tenantPackageID ? 'selected' : ''} ">${tenantPackage.name}</form:option>
																</c:if>
															</c:forEach>
														</form:select>
													</div>
												</div>
											</div>
										</c:if> 
									</div>
									<div class="form-group">
										<div class="col-md-11 col-md-offset-1">
											<hr>
											<div class="input-icon">
												<security:authorize access="hasAnyRole('ROLE_SUPERADMIN')">
													<button class="btn orange_bg" type="submit">${userDetails.id != null ? 'Update User' : 'Save Super Admin'}</button>								
												</security:authorize>
												<security:authorize access="hasAnyRole('ROLE_ADMIN')">
													<button class="btn orange_bg" type="submit">${userDetails.id != null ? 'Update User' : 'Save User'}</button>								
												</security:authorize>
												<a href="<%=UrlConstants.USER_LIST%>" class="btn grey_bg">Cancel</a>
											</div>
										</div>
									</div>
								</div>
							</form:form>
						</div>
					</div>
				</div>
				<!--END CONTENT-->
			</div>
		</div>
	</div>
</div>