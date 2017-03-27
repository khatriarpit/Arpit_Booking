<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--BEGIN CONTENT-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="col-lg-12 alert-success-message text-center" id="msg-div"
				style="font-size: 22px;${message != null && message != '' ? 'color:red !important;' : 'display: none;'}">
				${message}</div>
			<div class="portlet not-to-display">
				<c:if test="${empty message}">
					<div class="portlet-header">
						<div class="caption">Package Verification (${companyname})</div>
					</div>
					<div data-wizard-init>
						<c:if test="${sellerAccountFlag eq true}">
							<ul class="steps">
								<li data-step="1">Your Details</li>
								<li data-step="2">Bank Details</li>
							</ul>
						</c:if>
						<div class="steps-content">
							<%-- action="<%=UrlConstants.SAVE_SELLER%>" --%>
							<form:form id="sellerform" class="form-horizontal" ondragstart="return false;"
								ondrop="return false;">
								<div data-step="1">
									<div class="portlet-body">
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" /> <input type="hidden"
											name="tanentID" value="${tanentID}"></input>
										<c:if test="${tenantPackageAddon eq 'false'}">
											<div class="form-group">
												<label for="inputAddress" class="col-md-2 control-label">Password</label>
												<div class="col-md-6">
													<div class="input-icon right">
														<input class="form-control" name="password"
															id="newPassword" maxlength="20"
															placeholder="Enter Password" type="password" /> <label>Please
															Enter One Special and One Capital Character and One
															Number must be there, Like : 'Example@123'</label> <label
															style="display: none;" id="passwordError" class="error">Please
															Enter Password</label>
													</div>
												</div>
											</div>
											<div class="form-group"
												style="display:${empty userDetails.id ? 'block;' : 'none;'}">
												<label for="inputAddress" class="col-md-2 control-label">Confirm
													Password</label>
												<div class="col-md-6">
													<div class="input-icon right">
														<input class="form-control" type="password" name="cpass"
															minlength="8" maxlength="20" id="conpass"
															placeholder="Re-Enter Password"> <label
															style="display: none;" id="conpassError" class="error">Enter
															Confirm Password Same as Password</label>
													</div>
												</div>
											</div>
										</c:if>
										<%@include file="../CommonPage/common-add-car-driver.jsp"%>
										<div class="form-group">
											<div class="col-md-6 col-md-offset-2">
												<div class="checkbox">
													<input type="checkbox" name="termConditions"
														id="termConditions"> <label>&nbsp; <a
														target="_blank" href="<%=UrlConstants.TERM_CONDITIONS%>">Accept
															Terms & Conditions</a></label> <br> <label
														style="display: none;" id="termConditionsError"
														class="error">Please Check Term Conditions !!!</label>
												</div>
												<br> <input type="hidden" value="${tenantPackageAddon}"
													name="isAddOnPackage" />
												<input type="hidden" value="${sellerAccountFlag}"
													name="sellerAccountFlag" />
											</div>
										</div>
									</div>
								</div>
								<c:if test="${sellerAccountFlag eq true}">
								<div data-step="2">
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
														<form:select class="form-control select2"
															path="payoutmode" style="width: 100%;">
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
													2
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
														<form:input class="form-control"
															placeholder="IFSC Code" path="seller_ifsc_code"
															maxlength="15" />
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
														<label class="error" id="error-country"></label>
													</div>
												</div>
											</div>


											<div class="form-group col-md-6 flot_for_hour_left">
												<label for="inputAddress" class="col-md-4 control-label">Account
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
														<label class="error" id="error-state"></label>
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
														<label class="error" id="error-city"></label>
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
										</div>
									</div>
								</div>
								</c:if>
								<div class="form-group">
									<button class="btn orange_bg" id="submit" type="button">Confirm</button>
								</div>
								<div class="modal animated bounceInDown" id="confirm-modal"
									tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
									<div class="modal-dialog" role="document">
										<div class="modal-content modal-center">
											<div class="modal-body">
												<center>
													<h4>Are you sure you want to submit this request?</h4>
													<br>
													<button class="btn orange_bg" id="submit" type="submit">Submit</button>
													<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
												</center>
											</div>
										</div>
									</div>
								</div>
							</form:form>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
<c:if test="${empty message}">
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
		    
		    $.validator.addMethod("passwordRequired", function (value, element) {
	            return /[\@\#\$\%\^\&\*\(\)\_\+\!]/.test(value) && /[a-z]/.test(value) && /[0-9]/.test(value) && /[A-Z]/.test(value)
	        });
	    	$.validator.addMethod("conpassRequired", function (value, element) {
	    		var password = $("#password").val();
	            if(password === value) {
	    			return true;
	            } else {
	            	return false;
	            }
	        });
	
		    if("${sellerAccountFlag}" === 'true') {
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
			                saveMethod();
						}
			        }
				});
		    } else {
		    	$("#sellerform").validate({
		    		rules: {
			    		password: {
							required : true,
							minlength : 8,
							passwordRequired : true
			    		},
			    		conpass: {
							required : true,
							conpassRequired : true
			    		}
		    		},
		    		messages : {
		    			password : {
		                    required :"Please enter password",
		                    minlength : "Your password must be at least 8 characters long",
		                    passwordRequired : "Please Enter Password Including 1 Uppercase and 1 Special character and 1 Number must be there, Like : 'Example@123'"
		                },
		                conpass : {
		                    required :"Enter Select Confirm Password",
		                    conpassRequired : "Password Doesn't Match"
		                }
		    		},
			        submitHandler : function(form) {
		            	saveMethod();
			        }
		    	});
		    }
		    
		    function saveMethod(){
		    	$('#confirm-modal').modal('hide');
		    	callblock();
       	        $.ajax({
       	            type : 'POST',
       	            url : '<%=UrlConstants.PASSWORD_VERIFICATION%>',
					data : $("#sellerform").serialize(),
					success : function(data) {
						endblockUI();
						var error = data["error"];
						if (error === "true") {
							carDriverError(data);
						} else {
							$("#msg-div").html(data["message"]);
							$("#msg-div").show();
							$(".not-to-display").remove();
						}
					},
					error : function() {
						alert("error");
					}
				});
		    	
		    }

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
		
		function checkPassword(value) {
			return /[\@\#\$\%\^\&\*\(\)\_\+\!]/.test(value)
					&& /[a-z]/.test(value)
					&& /[0-9]/.test(value)
					&& /[A-Z]/.test(value);
		}

		$("body").delegate("#newPassword", "change", function() {
			var value = $(this).val();
			$("#conpass").val('');
			$("#conpassError").hide();
			if (checkPassword(value) && value.length >= 8) {
				$("#passwordError").hide();
				return true;
			} else {
				$("#passwordError").show();
				return false;
			}
		});

		$("body").delegate("#conpass", "change", function() {
			$("#conpassError").hide();
			var password = $("#newPassword").val();
			var confirmPassword = $(this).val();
			if (password === confirmPassword) {
				$("#conpassError").hide();
				return true;
			} else {
				$("#conpassError").show();
				return true;
			}
		});

		function validateForm() {
			if("${tenantPackageAddon}" === "false") {
				var password = $("#newPassword").val();
				var confirmPassword = $("#conpass").val();
				if (password != "") {
					$("#passwordError").hide();
					validate = true;
				} else {
					$("#passwordError").show();
					validate = false;
					return false;
				}
				if (confirmPassword != "" && password === confirmPassword) {
					$("#conpassError").hide();
					validate = true;
				} else {
					$("#conpassError").show();
					validate = false;
					return false;
				}
			}
			if (termConditions.checked) {
				$("#termConditionsError").hide();
				validate = true;
			} else {
				$("#termConditionsError").show();
				validate = false;
				return false;
			}
			return validate;
		}

		$(document).ready(function () {
            $('#termConditions').click(function(){
                if ($(this).is(':checked')) {
                	$("#termConditionsError").hide();
                } else {
                	$("#termConditionsError").show();
                }
            });
         	// user verification submit button
			$('#submit').click(function() {
				carDriverAdd();
				if(validateForm() && validation) {
					$('#confirm-modal').modal('show');
				}
			});
		});
	</script>
</c:if>