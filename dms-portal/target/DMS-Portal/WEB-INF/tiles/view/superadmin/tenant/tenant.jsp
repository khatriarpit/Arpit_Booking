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
							<div class="caption box-caption">Tenant Details of : ${tenant.tanentid}</div>
						</div>
						<div class="box-body">
							<form:form id="save_CompanyDetails" name="save_CompanyDetails"
								modelAttribute="command" method="POST" class="form-horizontal" ondragstart="return false;" ondrop="return false;">
								<form:input type="hidden" path="id" value="${tenant.id}"></form:input>
								<form:input type="hidden" path="tanentid"
									value="${tenant.tanentid}"></form:input>
								<form:input type="hidden" path="status" value="${tenant.status}"></form:input>

								<div class="form-body pal">
								<div>
								</div>
									<h5>
										<b>Company Details</b>
									</h5>
									<br>
									<div class="col-md-6">
										<div class="form-group flot_for_hour_left ">
											<label for="inputAddress" class="col-md-4 control-label">Company
											Type <span class='require'>*</span>
											</label>
											<div class="col-md-8 ">
												<div class="input-icon right">
											<form:select class="form-control" path="companytypename.id" id="companyTypeID"
												itemValue="companyType">
												<form:option value="0">Select Type</form:option>
												<c:forEach var="companytype" items="${companylist}">
													<form:option id="${companylist}" value="${companytype.id}" 
													selected="${companytype.id eq tenant.companytypename.id ? 'selected' : ''}">${companytype.companyType}</form:option>
												</c:forEach>
											</form:select>
												<label id="error-companyType" style="color: red;"></label>	
												</div>
											</div>
										</div>
										
										<div class="form-group flot_for_hour_left ">
											<label for="inputAddress" class="col-md-4 control-label">Company
												Name <span class='require'>*</span>
											</label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="50"
														path="companyname" value="${tenant.companyname}"
														id="companyname" />
													<c:if test="${not empty companyNameErrorMsg}">
														<label class="error">${companyNameErrorMsg}</label>
													</c:if>
												</div>
											</div>
										</div>

										<div class="form-group flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Owner
												Name <span class='require'>*</span>
											</label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="50"
														path="ownerName" value="${tenant.ownerName}"
														id="ownerName" />
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Service
												Tax No.<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="10"
														path="servicetaxno" placeholder="ABCDE1234F"
														value="${tenant.servicetaxno}" id="servicetaxno" />
													<c:if test="${not empty serviceErrorMsg}">
														<label class="error">${serviceErrorMsg}</label>
													</c:if>
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Pan
												No.<span class='require'>*</span>
											</label>

											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="10"
														path="panno" value="${tenant.panno}"
														placeholder="ABCDE1234F" id="panno" />
													<c:if test="${not empty panErrorMsg}">
														<label class="error">${panErrorMsg}</label>
													</c:if>
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress"
												class="col-md-4 control-label  flot_for_hour_left">Company
												Mobile 1<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="10"
														path="contactno" value="${tenant.contactno}"
														onkeypress="return onlyNos(event,this);" />
												</div>
												<c:if test="${not empty contactNoErrorMsg}">
													<label class="error">${contactNoErrorMsg}</label>
												</c:if>
											</div>
										</div>
										<div class="form-group flot_for_hour_left clear-f-both "
											style="clear: both">
											<label for="inputAddress"
												class="col-md-4  flot_for_hour_left control-label">Company
												Mobile 2</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="10"
														path="contactno1" value="${tenant.contactno1}"
														id="contactno1" onkeypress="return onlyNos(event,this);" />
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left clear-f-both "
											style="clear: both">
											<label for="inputAddress"
												class="col-md-4  flot_for_hour_left control-label">Company
												Landline Number</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="11"
														path="landlineNumber" value="${tenant.landlineNumber}"
														onkeypress="return onlyNos(event,this);" />
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left clear-f-both">
											<label for="inputAddress" class="col-md-4 control-label">Company
												Email<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="50"
														path="emailid" value="${tenant.emailid}" id="emailid"
														onchange="return getEmailCheck(1);" />
													<c:if test="${not empty emailIdErrorMsg}">
														<label class="error">${emailIdErrorMsg}</label>
													</c:if>
													<label class="error" style="display: none" id="unique1">Please
														Select Unique Email Address</label>
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left clear-f-both">
											<label for="inputAddress" class="col-md-4 control-label">Website</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="50"
														path="website" value="${tenant.website}" id="website" />
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Address
												1<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="50"
														path="address1" value="${tenant.address1}" />
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Address
												2</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="50"
														path="address2" value="${tenant.address2}" />
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Landmark</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="50"
														path="landmark" value="${tenant.landmark}" id="landmark" />
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Pin/Zip
												Code<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="col-md-6 flot_for_hour_left">
													<form:input class="form-control" maxlength="6"
														placeholder="457896" path="pinCode"
														value="${tenant.pinCode}" id="pinCode"
														onkeypress="return onlyNos(event,this);" />
												</div>
												<div class="col-md-6 flot_for_hour_right flot_for_hour_left">
													<input class="form-control" maxlength="6" value="${cityCode}" id="cityCode" readOnly="true"/>
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Select
												Country<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:select class="form-control" id="countryID"
														path="country.id">
														<form:option value="0">Select Country</form:option>
														<c:forEach var="countryLst" items="${countryLst}">
															<form:option value="${countryLst.id}"
																selected="${countryLst.id eq tenant.country.id ?'selected': ''} ">${countryLst.countryName}</form:option>
														</c:forEach>
													</form:select>
													<label id="error-country" style="color: red;"></label>
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Select
												State<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:select class="form-control" id="stateID"
														path="state.id" itemValue="stateID">
														<form:option value="0">Select State</form:option>
														<c:forEach var="stateLst" items="${stateLst}">
															<form:option value="${stateLst.id}"
																selected="${stateLst.id eq tenant.state.id ? 'selected' : ''} ">${stateLst.stateName}</form:option>
														</c:forEach>
													</form:select>
													<label id="error-state" style="color: red;"></label>
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Select
												City<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:select class="form-control" id="cityID"
														path="city.id" itemValue="cityID">
														<form:option value="0">Select City</form:option>
														<c:forEach var="cityLst" items="${cityLst}">
															<form:option value="${cityLst.id}"
																selected="${cityLst.id eq tenant.city.id ? 'selected' : ''} ">${cityLst.cityName}</form:option>
														</c:forEach>
													</form:select>
													<label id="error-city" style="color: red;"></label>
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-12">
										<hr>
										<h5>
											<b>Contact Person 1</b>
										</h5>
									</div>
									<br>
									<div class="col-md-6">
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_left flot_for_hour_right control-label">First
												Name<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control alpha-only" maxlength="50"
														path="firstName1" value="${tenant.firstName1}"
														id="firstName1" />
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_left flot_for_hour_right control-label">Last
												Name<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control alpha-only" maxlength="50"
														path="lastName1" value="${tenant.lastName1}"
														id="lastName1" />
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_left flot_for_hour_right control-label">Mobile
												Number 1<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="10"
														path="mobileNo1" value="${tenant.mobileNo1}"
														onkeypress="return onlyNos(event,this);" id="mobileNo1" />
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_left flot_for_hour_right control-label">Mobile
												Number 2</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="10"
														path="mobileNo2" value="${tenant.mobileNo2}"
														onkeypress="return onlyNos(event,this);" id="mobileNo2" />
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_left flot_for_hour_right control-label">Email
												Address</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="50"
														path="email1" onchange="return getEmailCheck(2);"
														value="${tenant.email1}" id="email1" />
													<c:if test="${not empty emailId1ErrorMsg}">
														<label class="error">${emailId1ErrorMsg}</label>
													</c:if>
													<label class="error" style="display: none" id="unique2">Please
														Select Unique Email Address</label>

												</div>
											</div>
										</div>
									</div>
									<div class="col-md-12">
										<hr>
										<h5>
											<b>Contact Person 2</b>
										</h5>
									</div>
									<br>
									<div class="col-md-6">
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_left flot_for_hour_right control-label">First
												Name </label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control alpha-only" maxlength="50"
														path="firstName2" value="${tenant.firstName2}"
														id="firstName2" />
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_left flot_for_hour_right control-label">Last
												Name </label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control alpha-only" maxlength="50"
														path="lastName2" value="${tenant.lastName2}"
														id="lastName2" />
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_left flot_for_hour_right control-label">Mobile
												Number 1 </label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="10"
														path="mobileNo3" value="${tenant.mobileNo3}"
														onkeypress="return onlyNos(event,this);" />
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_left flot_for_hour_right control-label">Mobile
												Number 2</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="10"
														path="mobileNo4" value="${tenant.mobileNo4}"
														onkeypress="return onlyNos(event,this);" />
												</div>
											</div>
										</div>
										<div class="form-group flot_for_hour_left">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_left flot_for_hour_right control-label">Email
												Address</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" maxlength="50"
														path="email2" onchange="return getEmailCheck(3);"
														value="${tenant.email2}" id="email2" />
													<c:if test="${not empty emailId1ErrorMsg}">
														<label class="error">${emailId2ErrorMsg}</label>
													</c:if>
													<label class="error" style="display: none" id="unique3">Please
														Select Unique Email Address</label>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-11 col-md-offset-1">
											<hr>
											<div class="input-icon">
												<c:if test="${empty tenant.id}">
													<button class="btn orange_bg" type="submit" name="submit"
														value="SaveDraft">SaveDraft</button>
													<button class="btn orange_bg" type="submit" name="submit">Create</button>
													<a href="<%=UrlConstants.TENANT_LIST%>" class="btn grey_bg"
														type="button">Cancel</a>
												</c:if>
												<c:if test="${ not empty tenant.id}">
													<c:if test="${tenant.status eq '2'}">
														<button class="btn orange_bg" type="submit" name="submit"
															value="Create">Create</button>
													</c:if>
													<button class="btn orange_bg" type="submit" name="submit">Update</button>
													<a href="<%=UrlConstants.TENANT_LIST%>" class="btn grey_bg"
														type="button">Cancel</a>
												</c:if>
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
<script src="/assets/scripts/jquery.validate.min.js"></script>
<script type="text/javascript">
    jQuery.validator.addMethod("panNoValidate", function(value, element)
            {
                return this.optional(element) || /[a-zA-z]{5}\d{4}[a-zA-Z]{1}/i.test(value);
            }, "Please enter a valid Pan No.");
    jQuery.validator.addMethod("servicetaxNoValidate", function(value, element)
            {
                return this.optional(element) || /[a-zA-z]{5}\d{4}[a-zA-Z]{1}/i.test(value);
            }, "Please enter a valid Pan No.");
    $(function() {
    $("#save_CompanyDetails").validate({
        rules : {
            companyname : "required",
            address1 : "required",
            ownerName : "required",
            contactno : {
                required : true,
                maxlength : 10,
                minlength: 10
            },
            emailid : {
            	required : true,
            	email : true
            },
            servicetaxno : {
                required : true,
                servicetaxNoValidate :  true,
                maxlength : 10,
                minlength : 10
            },
            panno : {
                required : true,
                panNoValidate :  true,
                maxlength : 10
            },
            pinCode : {
                required : true
            },
            firstName1 : {
                required : true
            },
            lastName1 : {
                required : true
            },
            mobileNo1 : {
                required : true,
                maxlength: 10,
                minlength: 10
            }
        },
        // Specify the validation error messages
        messages : {
            companyname : "Please Enter Company Name",
            address1 : "Please Enter Address",
            ownerName : "Please enter Owner Name",
            contactno : {
                required : "Please Enter Contact No.",
                maxlength : "Please Enter Contact No. of 10 Digit.",
                minlength : "Please Enter Contact No. of 10 Digit."
            },
            emailid : {
            	required:"Please Enter Email Address",
            	email: "Invalid Format"
            },
            servicetaxno : {
                required : "Please Enter Service Tax No.",
                servicetaxNoValidate :"Please Enter correct Service Tax No.",
                maxlength : "Max Length 10 Digit"
            },
            panno : {
                required : "Please Enter Pan No.",
                panNoValidate :"Please Enter Correct Pan No.",
                maxlength : "Max Length 10 Digit"
            },
            pinCode : {
                required : "Please Enter Pin Code",
            },
            firstName1 : {
                required : "Please Enter Your First Name."
            },
            lastName1 : {
                required : "Please Enter Your Last Name."
            },
            mobileNo1 : {
                required : "Please Enter Mobile No.",
                maxlength : "Please Enter Contact No. of 10 Digit",
                minlength : "Please Enter Contact No. of 10 Digit."
            }
        },
        submitHandler : function(form) {
        	if(validation()){
            $("#save_CompanyDetails").attr("action", "<%=UrlConstants.SAVE_TENANT%>?${_csrf.parameterName}=${_csrf.token}");
            form.submit();
        	}
        }
    });
    $("body").delegate("#cityID", "change", function(){
		$("#error-city").hide();
	    });
    $("body").delegate("#countryID", "change", function(){
		$("#error-country").hide();
	    });
    $("body").delegate("#stateID", "change", function(){
		$("#error-state").hide();
	    });
    $("body").delegate("#companyTypeID", "change", function(){
		$("#error-companyType").hide();
	    });
 });
    $('#countryID').on('change',function(){
        var countryId = $(this).val();
        if(countryId != '0'){
            $.ajax({
                type:'POST',
                url:'getState',
                data:'${_csrf.parameterName}=${_csrf.token}&countryId='+countryId,
                success:function(data){
                    $('#stateID').html('<option value="0">Select State</option>');
                    for ( var i = 0; i < data.length; i++) {
                        var state = data[i];
                        $('#stateID').append("<option value=\"" + state.id + "\">" + state.stateName+ "</option>");
                    }
                    $('#cityID').html('<option value="">Select State First</option>');
                }
            });
        }else{
            $('#stateID').html('<option value="">Select Country First</option>');
            $('#cityID').html('<option value="">Select State First</option>');
        }
    });

    $('#stateID').on('change',function(){
        var stateId = $(this).val();
        if(stateId !='0'){
            $.ajax({
                type:'POST',
                url:'getCity',
                data:'${_csrf.parameterName}=${_csrf.token}&stateId='+stateId,
                success:function(data){
                    $('#cityID').html("<option value='0'>Select City</option>");
                    for ( var i = 0; i < data.length; i++) {
                        var city = data[i];
                        $('#cityID').append("<option value=\"" + city.id + "\">" + city.cityName+ "</option>");
                    }
                }
            });
        }else{
            $('#cityID').html('<option value="">Select State First</option>');
        }
    });
    
    $("body").delegate("#cityID", "change", function(){
    	var cityId = $(this).val();
    	if(cityId != '0'){
    		$.ajax({
    			type:'POST',
    			url:'<%=UrlConstants.CITY_CODE%>',
    			 data:'${_csrf.parameterName}=${_csrf.token}&cityId='+cityId,
    			 success:function(data){
    				 $('#cityCode').val(data.cityCode);
    			 }
    		});
    	}
    });

	function getEmailCheck(val){
        var emailid = $('#emailid').val();
        var email1 = $('#email1').val();
        var email2 = $('#email2').val();

        var checkNullflagEmail1=false;
        var checkNullflagEmail2=false;
        var checkNullflagEmail3=false;
        if(checkNull(emailid)){

            checkNullflagEmail1=true;
        }
        if(checkNull(email1)){

            checkNullflagEmail2=true;
        }
        if(checkNull(email2)){

            checkNullflagEmail3=true;
        }

        if((emailid!=="" && emailid===email1) || (email2!=="" && emailid===email2) ||(email1!=="" && email1===email2)){

            if(val===1){

            $('#unique1').show();

            }
            else if(val===2){

            $('#unique2').show();

            }
            else if(val===3){
            $('#unique3').show();

            }
            else{
                $('#unique1').hide();
                $('#unique2').hide();
                $('#unique3').hide();
            }
            return false;
        } else{
            $('#unique1').hide();
            $('#unique2').hide();
            $('#unique3').hide();

            return true;
        }
    }
	
	function validation(){
		var companyType = $("#companyTypeID").val();
		var country = $("#countryID").val();
		var state = $("#stateID").val();
		var city = $("#cityID").val();
		var valid = true;
		if(companyType === "0"){
			$("#error-companyType").html("Please Select CompanyType");
			 valid = false;
			 return valid;
		}
		else{
			$("#error-companyType").html("");
			valid = true;
		}
		if(country === "0"){
			$("#error-country").html("Please Select Country");
			 valid = false;
			 return valid;
		}
		else{
			$("#error-country").html("");
			valid = true;
		}
		if(state === "0"){
			$("#error-state").html("Please Select State");
			 valid = false;
			 return valid;
		}
		else{
			$("#error-state").html("");
			valid = true;
		}
		if(city === "0"){
			$("#error-city").html("Please Select City");
			 valid = false;
			 return valid;
		}
		else{
			$("#error-city").html("");
			valid = true;
		}
		return valid;
	}

	function checkNull(str) {

		if(str==='')
			{

			return true;
			}else{

			return false;
			}
	}
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
	
	$(".alpha-only").on("input", function(){
  	  	var regexp = /[^a-zA-Z]/g;
  	  	if($(this).val().match(regexp)){
  	    	$(this).val( $(this).val().replace(regexp,'') );
  	  	}
  	});
</script>