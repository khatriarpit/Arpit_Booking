<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!--BEGIN CONTENT-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-header">
					<div class="caption">Pending Verification(Tanent Name)</div>
				</div>

				<!-- <div data-wizard-init>

					<ul class="steps">
						<li data-step="1">Your Details</li>
						<li data-step="2">Bank Details</li>
					</ul>

					<div class="steps-content">
						<div data-step="1"> -->
							<div class="portlet-body">
								<form:form action="#" class="form-horizontal"
									modelAttribute="command" id="pending-verification-form"
									ondragstart="return false;" ondrop="return false;">
									<input type="hidden" id="tanentPackageId" name="id"
										value="${pendingVerification.id}" />
									<div class="form-body pal">
										<div class="col-md-12">
											<br>
											<div class="form-group col-md-6 flot_for_hour_left">
												<label for="inputAddress" class="col-md-4 control-label">Tenant
													Name <span class='require'>*</span>
												</label>
												<div class="col-md-8 ">
													<div class="input-icon right">
														<input class="clr-blc form-control"
															value="${pendingVerification.tenant.companyname}"
															disabled="" type="text" />
													</div>
												</div>
											</div>
											<div class="form-group col-md-6 flot_for_hour_left">
												<label for="inputAddress"
													class="col-md-4 control-label flot_for_hour_right">Contact
													Person 1 <span class='require'>*</span>
												</label>
												<div class="col-md-8 ">
													<div class="input-icon right">
														<input class="clr-blc form-control"
															value="${pendingVerification.tenant.firstName1} ${pendingVerification.tenant.lastName1}"
															disabled="" type="text" />
													</div>
												</div>
											</div>
											<div class="form-group col-md-6 flot_for_hour_left">
												<label for="inputAddress" class="col-md-4 control-label">Creation
													Date <span class='require'>*</span>
												</label>
												<div class="col-md-8 ">
													<div class="input-icon right">
														<fmt:formatDate value='${pendingVerification.createdDate}'
															var='createdDate' type='date'
															pattern='<%=Constants.PORTAL_DATE_FORMAT%>' />
														<input class="clr-blc form-control" value="${createdDate}"
															disabled="" type="text" />
													</div>
												</div>
											</div>
											<div class="form-group col-md-6 flot_for_hour_left">
												<label for="inputAddress"
													class="col-md-4 control-label flot_for_hour_right">
													Mobile 1 <span class='require'>*</span>
												</label>
												<div class="col-md-8 ">
													<div class="input-icon right">
														<input class="clr-blc form-control"
															value="${pendingVerification.tenant.mobileNo1}"
															disabled="" type="text">
													</div>
												</div>
											</div>
											<div class="form-group col-md-6 flot_for_hour_left">
												<label for="inputAddress"
													class="col-md-4 control-label flot_for_hour_right">
													Package Name <span class='require'>*</span>
												</label>
												<div class="col-md-8 ">
													<div class="input-icon right">
														<input class="clr-blc form-control"
															value="${pendingVerification.name}" disabled=""
															type="text">
													</div>
												</div>
											</div>
											<div class="form-group col-md-6 flot_for_hour_left">
												<label for="inputAddress"
													class="col-md-4 control-label flot_for_hour_right">Email
													Id <span class='require'>*</span>
												</label>
												<div class="col-md-8 ">
													<div class="input-icon right">
														<input class="clr-blc form-control"
															value="${pendingVerification.tenant.emailid}" disabled=""
															name="car type" type="text">
													</div>
												</div>
											</div>
											<div class="form-group col-md-12">
												<%@include file="../CommonPage/common-add-car-driver.jsp"%>
											</div>
											<div class="form-group col-md-12 flot_for_hour_left">
												<label for="inputAddress" class="col-md-2 control-label">Remarks
													<span class='require'>*</span>
												</label>
												<div class="col-md-10 flot_for_hour_left_mar ">
													<div class="input-icon right">
														<textarea class="clr-blc form-control" id="remarks"
															placeholder="Enter Remarks">${pendingVerification.remarks}</textarea>
														<label id="remarks-error-msg" class="error control-label"
															style="display: none;"></label>
													</div>
												</div>
											</div>
										</div>
										<hr>
										<div class="form-group">
											<div class="col-md-11 col-md-offset-1">
												<div class="input-icon">
													<button class="btn orange_bg" id="approve" type="button">Approve</button>
													<a id="rejected"
														href="<%=UrlConstants.PENDING_VERIFICATION_DELETE %>?id=${pendingVerification.id}"
														onclick="return confirm('Are you sure want to Reject this request?')"
														class="btn grey_bg">Reject</a> <a
														href="<%=UrlConstants.PENDING_VERIFICATION_LIST%>"
														class="btn grey_bg">Cancel</a>
												</div>
											</div>
										</div>
									</div>
								</form:form>
							</div>
							<div class="modal  animated bounceInDown" id="confirm-modal"
								tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
								<div class="modal-dialog" role="document">
									<div class="modal-content modal-center">
										<div class="modal-body">
											<center>
												<h4>Are you sure you have verified all details and want to
													approve the request?</h4>
												<br>
												<button type="button" class="btn orange_bg" id="approveButton" data-dismiss="modal">Confirm</button>
												<input type="hidden" id="typeOfSubmit"/>
												<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
											</center>
										</div>
									</div>
								</div>
							</div>
						<%-- </div>
						<div data-step="2">
							<form:form action="<%=UrlConstants.SAVE_SELLER%>"
								id="sellerform" modelAttribute="command1"
								class="form-horizontal">
								<div class="form-body">
									<div class="col-md-12">
										<h5>
											<b>Fill the details associated with your bank account</b>
										</h5>
										<br>
										<div class="form-group col-md-6 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_right control-label">First
												Name<span class='require'>*</span>
											</label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:input class="form-control"
														placeholder="Enter First Name" path="firstName"
														maxlength="20" />
												</div>
											</div>
										</div>

										<div class="form-group col-md-6 flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Website<span
												class='require'>*</span></label>

											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control"
														placeholder="www.companyname.com" path="businessurl" />
												</div>
											</div>
										</div>

										<div class="form-group col-md-6 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_right control-label">Last
												Name<span class='require'>*</span>
											</label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:input class="form-control"
														placeholder="Enter Last Name" path="lastName"
														maxlength="20" />
												</div>
											</div>
										</div>

										<div class="form-group col-md-6 flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Mobile
												No.<span class='require'>*</span>
											</label>

											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control"
														placeholder="Enter Mobile No." path="mobileNo"
														maxlength="10" onkeypress="return onlyNos(event,this);" />
												</div>
											</div>
										</div>

										<div class="form-group col-md-6 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_right control-label">Address<span
												class='require'>*</span></label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:input class="form-control"
														placeholder="Enter Address" path="address1"
														maxlength="100" />
												</div>
											</div>
										</div>
										<div class="form-group col-md-6 flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Bank<span
												class='require'>*</span></label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:select class="form-control select2" path="payoutmode"
														style="width: 100%;">
														<form:option value="0" selected="selected">Selct Bank</form:option>
														<form:option value="Alaska">Alaska</form:option>
														<form:option value="California">California</form:option>
														<form:option value="Delaware">Delaware</form:option>
														<form:option value="Tennessee">Tennessee</form:option>
													</form:select>
												</div>
											</div>
										</div>
										<div class="form-group col-md-6 flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Address
												2<span class='require'>*</span>
											</label>

											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control"
														placeholder="Enter Address 2" path="address2"
														maxlength="200" />
												</div>
											</div>
										</div>

										<div class="form-group col-md-6 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_right control-label">IFSC
												Code<span class='require'>*</span>
											</label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:input class="form-control" placeholder="Pin/Zip Code"
														path="seller_ifsc_code" maxlength="15" />
												</div>
											</div>
										</div>
										<div class="form-group col-md-6 flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Country<span
												class='require'>*</span></label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:select class="form-control" id="countryID"
														path="country.id">
														<form:option value="0">Select Country</form:option>
														<c:forEach var="countryLst" items="${countryLst}">
															<form:option value="${countryLst.id}">${countryLst.countryName}</form:option>
														</c:forEach>
													</form:select>
													<label id="error-country" style="color: red;"></label>
												</div>
											</div>
										</div>


										<div class="form-group col-md-6 flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Acoount
												No.<span class='require'>*</span>
											</label>

											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control"
														placeholder="Enter Acoount No." pattern="[A-Za-z]{3}"
														path="seller_account_number" maxlength="18" />
												</div>
											</div>
										</div>

										<div class="form-group col-md-6 flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">State<span
												class='require'>*</span></label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:select class="form-control" id="stateID"
														path="state.id" itemValue="stateID">
														<form:option value="0">Select State</form:option>
														<c:forEach var="stateLst" items="${stateLst}">
															<form:option value="${stateLst.id}">${stateLst.stateName}</form:option>
														</c:forEach>
													</form:select>
													<label id="error-state" style="color: red;"></label>
												</div>
											</div>
										</div>

										<div class="form-group col-md-6 flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Email
												Id<span class='require'>*</span>
											</label>

											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control"
														placeholder="Enter Email Id" path="selleremailId"
														maxlength="50" />
												</div>
											</div>
										</div>

										<div class="form-group col-md-6 flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">City<span
												class='require'>*</span></label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:select class="form-control" id="cityID"
														path="city.id" itemValue="cityID">
														<form:option value="0">Select City</form:option>
														<c:forEach var="cityLst" items="${cityLst}">
															<form:option value="${cityLst.id}">${cityLst.cityName}</form:option>
														</c:forEach>
													</form:select>
													<label id="error-city" style="color: red;"></label>
												</div>
											</div>
										</div>
										<div class="form-group col-md-6 flot_for_hour_left">
											<label for="inputAddress" class="col-md-4 control-label">Zip
												Code<span class='require'>*</span>
											</label>

											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control" placeholder="Pin Code"
														path="pinCode" maxlength="9" />
												</div>
											</div>
										</div>
										<button class="btn orange_bg" type="submit">Submit</button>
									</div>
								</div>
							</form:form>
						</div>
					</div>
				</div> --%>
			</div>
		</div>
	</div>
</div>
<!--END CONTENT-->
<script type="text/javascript">
$(document).ready(function() {
	 jQuery.validator.addMethod("alphanumeric", function(value, element) {
         return this.optional(element) || /^[a-zA-Z0-9]{1,11}$/.test(value)
     },"AlphaNumeric only");
	 jQuery.validator.addMethod("ifsc", function(value, element) {
         return this.optional(element) ||/^[A-Za-z]{4}\d{7}$/.test(value)
     },"IFSC only");
    jQuery.validator.addMethod("bankRequired", function(
            value, element) {
        var val = value;
        if (val === "0") {
            return false;
        } else {
            return true;
        }
    }, "Please Select Car Type");

    $("#sellerform").validate({
        rules : {
        	firstName : {
                required : true
            },
            businessurl : {
                required : true
            },
            lastName : {
                required : true
            },
            mobileNo : {
            	minlength : "10",
            	required : true
            },
            address1 : {
            	required : true
            },
            payoutmode : {
            	required : true,
            	bankRequired : true
            },
            seller_ifsc_code : {
            	ifsc : true,
            	required : true
            },
            seller_account_number : {
            	alphanumeric : true,
            	required : true
            },
            selleremailId : {
            	email : true,
            	required : true
            },
            pinCode : {
            	required : true
            }
        },
        // Specify the validation error messages
        messages : {
        	firstName : "Please Enter FirstName",
        	businessurl : "Please Enter WEB site Name",
        	lastName : "Please Enter Last Name",
        	mobileNo : "Please Enter Mobile No",
        	address1 : "Please Enter Address",
        	payoutmode : "Please Select Bank",
        	seller_ifsc_code : "Please Enter IFSC",
        	seller_account_number : "Please Enter Account Number",
        	selleremailId : "Please Enter Email",
        	pinCode : "Please Enter pin"
        },
        submitHandler : function(form) {
        	if(validation()){
            $("#sellerform")
                    .attr("action", "<%=UrlConstants.SAVE_SELLER%>?${_csrf.parameterName}=${_csrf.token}");
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
    
    function validation(){
    	var country = $("#countryID").val();
    	var state = $("#stateID").val();
    	var city = $("#cityID").val();
    	var valid = true;
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

	$("body").delegate("#remarks","change",function(){
		var remarks = $(this).val();
		if(remarks != '') {
			$("#remarks-error-msg").hide();
		} else {
			$("#remarks-error-msg").html("Please Enter Some Remarks !!!");
			$("#remarks-error-msg").show();
		}
		
	});
	
	$('#approve').click(function() {
		$("#typeOfSubmit").val("approved");
		getValidationCheck();
	});
	
	$('#rejected').click(function() {
		$("#typeOfSubmit").val("rejected");
		getValidationCheck();
	});
	
	function onlyNos(evt, t) {
		 var charCode = (evt.which) ? evt.which : event.keyCode
			     if (charCode > 31 && (charCode < 48 || charCode > 57) && !(charCode == 46 || charCode == 8))
			       return false;
			     else {
			       var len = $(contactNo).val().length;
			       var index = $(contactNo).val().indexOf('.');
			       if (index > 0 && charCode == 46) {
			         return false;
			       }
			       if (index > 0) {
			         var CharAfterdot = (len + 1) - index;
			         if (CharAfterdot > 3) {
			           return false;
			         }
			       }
			     }
			     return true;
	}
	
	function getValidationCheck() {
		carDriverAdd();
		if(validation) {
			var remarks = $("#remarks").val();
	        if(remarks != '') {
	        	$("#remarks-error-msg").hide();
				$('#confirm-modal').modal('show');
	        } else {
	        	$("#remarks-error-msg").html("Please Enter Some Remarks !!!");
				$("#remarks-error-msg").show();
	        }
		}
	}
	//approve button
	$("#approveButton").click(function(){
        var carNo = $('#carlist').val();
        var driver = $('#driverlist').val();
        var carListRowWithComma = $('#carListRowWithComma').val();
        var driverListRowWithComma = $('#driverListRowWithComma').val();
        var tenantPackageID = $('#tenantPackageID').val();
        var remarks = $("#remarks").val();
        var tanentPackageId = $("#tanentPackageId").val();
        callblock();
        var typeOfSubmit = $("#typeOfSubmit").val();
        if(typeOfSubmit === "approved") {
	        $.ajax({
	            type : 'POST',
	            url : '<%=UrlConstants.PENDING_VERIFICATION_APPROVED%>',
	            data : '${_csrf.parameterName}=${_csrf.token}&id='+tanentPackageId+'&car='+carNo+'&driver='
	            		+driver+'&remarks='+remarks+'&carIndex='+carListRowWithComma+'&driverIndex='+driverListRowWithComma+'&tenantPackageID='+tenantPackageID,
	            success:function(data){
	            	endblockUI();
	            	var error = data["error"];
	            	if(error === "true") {
	            		carDriverError(data);
	            	} else {
	            		location.href="<%=UrlConstants.PENDING_VERIFICATION_LIST%>";	
	            	}
	            }
	        });
        } else {
          	location.href = "<%=UrlConstants.PENDING_VERIFICATION_DELETE%>?id=${pendingVerification.id}";
        }
	});
</script>