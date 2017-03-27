<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">

	$(document).ready(function() {
		if("${message}" != "") {
			$(".alert-error-message").fadeIn("fast").delay("3000").fadeOut("fast");
		}
		
		changeTenantPackageID("${Driver.tenantPackageID}", "edit");
		$("body").delegate("#tenantPackageID", "change", function() {
			var tenantPackageID = $(this).val();
			changeTenantPackageID(tenantPackageID, "change");
		});
		
		function changeTenantPackageID(tenantPackageID, type) {
			var typeVal =  '';
			if(type === 'edit') {
				typeVal = 'false';
			} else {
				typeVal = 'true';
			}
			if (type === 'edit') {
				$('#licenseNo').html("<option value='${Driver.licenseNo}'>${Driver.licenseNo}</option>");
			}
			if (type != 'edit') {
				if(tenantPackageID != '' && tenantPackageID != 0) {
					$.ajax({
	                    type:'POST',
	                    url:'<%=UrlConstants.LIST_TENANT_PACKAGE_CAR_OR_DRIVER_LIST_AJAX%>',
	                    data:'${_csrf.parameterName}=${_csrf.token}&type=driver&packageID='+tenantPackageID+'&typeCheck='+typeVal,
	                    success:function(data){
                    		$('#licenseNo').html('<option value="">Select License No</option>');
	                        for ( var i = 0, len = data.length; i < len; ++i) {
	                            var licenseNo = data[i];
	                            var selectedValueEqual = '';
	                            if('${Driver.licenseNo}' != '' && licenseNo === '${Driver.licenseNo}') {
	                            	selectedValueEqual = 'selected=selected';
	                            } else {
	                            	selectedValueEqual = '';
	                            }
	                            $('#licenseNo').append("<option value=\"" + licenseNo + "\" "+ selectedValueEqual +">" + licenseNo + "</option>");
	                        }
	                    }
	                });
				}
			}
		}
		
			//Driver.password filed validation method.
			jQuery.validator.addMethod("noSpace", function(value, element) {
				return value.indexOf(" ") < 0 && value != "";
			}, "No space please and don't leave it empty");
	
			$(".my-colorpicker2").colorpicker();
			$("body").delegate("#color","change", function() {
				var driColor = $(this).val();
				var driid=$('#id').val();
				$.ajax({
					type : "POST",
					url : "checkDriverColor",
					data : "${_csrf.parameterName}=${_csrf.token}&driColor=" + driColor+"&driid="+driid,
					success : function(data) {
						if(data === true){
							$("#errordricolor").show();
							$("#color").val("");
							return false;
						} else {
							$("#errordricolor").hide();
							return true;
						}
					},
					error : function(e) {
					}
				});
			});

			$('#aadharCardNO').keyup(function() {
				var ss, se, obj;
				obj = $(this); 
				ss = obj[0].selectionStart;
				se = obj[0].selectionEnd;
				var curr = obj.val();
				var foo = $(this).val().split("-").join(""); 
				if (foo.length > 0) {
					foo = foo.match(new RegExp('.{1,4}', 'g')).join("-");
				}
				if(( (curr.length % 5 == 0) && ss == se && ss == curr.length ) || (ss == se && (ss % 5 == 0))){
					ss += 1; se += 1;
				} if (curr != foo){
					$(this).val(foo);
					obj[0].selectionStart = ss;
					obj[0].selectionEnd = se;
				} if(curr.length == 15){
					alert("only 14 digit is enough"); 
				}
			});
			
			jQuery.validator.addMethod("cityRequiredID", function(value, element) {
				var val = value;
				if (val === "0") {
					return false;
				} else {
					return true;
				}
			}, "Please Select City Name"); 
			jQuery.validator.addMethod("licenceRequire", function(value, element) {
				var val = value;
				if (val === "Select LicenseNo") {
					return false;
				} else {
					return true;
				}
			}, "Please Select LicenseNo"); 
				jQuery.validator.addMethod("stateRequired", function(value, element) {
					var val = value;
					if (val === "0") {
						return false;
					} else {
						return true;
					}
				}, "Please Select State Name"); 
					jQuery.validator.addMethod("counteryRequired", function(value, element) {
						var val = value;
						if (val === "0") {
							return false;
						} else {
							return true;
						}
					}, "Please Select Country Name"); 
					jQuery.validator.addMethod("packageRequired", function(value, element) {
						var val = value;
						if (val === "0") {
							return false;
						} else {
							return true;
						}
					}, "Please Select Package Name");
			$("#saveDriver").validate({
				rules : {
					firstName : {
						required : true
					},
					middleName : {
						required : true
					},
					lastName : {
						required : true
					},
					password : {
						required : true,
						noSpace : true
					},
					contactNo : {
						required : true,
						digits : true
					},
					imeino : {
						required : true
					},
					address : {
						required : true
					},
					cityID : {
						required : true,
						cityRequiredID : true
					},
					tenantPackageID : {
						required : true,
						packageRequired : true
					},
					stateID : {
						required : true,
						stateRequired : true
					},
					
					countryID : {
						required : true,
						counteryRequired : true
					},
					
					aadharCardNO : {
						required : true
					},
					 licenseNo : {
						required : true,
						licenceRequire : true
					},
					adharCardImage : {
						required : true,
					},
					licenseImage : {
						required : true,
					},
					color : "required"
				},
				// Specify the validation error messages
				messages : {
					firstName : "Please Enter First Name",
					middleName : "Please Enter Middle Name",
					lastName : "Please Enter Last Name",
					contactNo : "Please Enter Driver Contact Number",
					address : "Please Enter Driver Address",
					imeino : "Please Enter IMEI No",
					cityID : {
						required : "Please Select City",
						cityRequiredID : "Please Select City"
					},
					stateID : {
						required :"Please Select State",
						stateRequired : "Please Select State"
					},
					countryID: {
						required : "Please Select Country",
						counteryRequired : "Please Select Country"
					}, 
					aadharCardNO : "Please Enter Adhar Card Number",
					tenantPackageID : "Please Select Tenant Package",
					licenseNo : {
						required : "Please Select License No",
						licenceRequire : "Please Select License No"
					},
					color : "Please Select color",
					adharCardImage : "Please Upload Adhar Card",
					licenseImage : "Please Upload License"
				},
				submitHandler : function(form) {
					$("#saveDriver").attr("action", "<%=UrlConstants.SAVE_DRIVER%>?${_csrf.parameterName}=${_csrf.token}");
			        form.submit();
				}
			});
		});

		$(document).ready(function(){
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
		    
		});

		function onlyNos(e, t) {
		    try {
		        if (window.event) {
		            var charCode = window.event.keyCode;
		        } else if (e) {
		            var charCode = e.which;
		        }
		        else { return true; }
		        if (charCode > 31 && (charCode < 48 || charCode > 57 || (charCode === 46 && dotvalue === false) )) {
		           if(charCode === 46){
		           	dotvalue = true;
		           	return true;
		           }
		           else{
		           	return false;}
		       	//return false;
		        }else{
		       	 return true;
		        }
		    }
		    catch (err) {
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
<div>
	<!--BEGIN CONTENT-->
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
					<div class="portlet-header">
						<div class="caption">${Driver.id != null ? 'Update' : 'Add'}
							Driver</div>
					</div>
					<div class="box-body">
						<form:form method="POST" class="form-horizontal"
							modelAttribute="command" enctype="multipart/form-data"
							id="saveDriver" ondragstart="return false;" ondrop="return false;">

							<form:input type="hidden" path="id" value="${Driver.id}" />
							<form:input path="tanentID" type="hidden"
								value="${Driver.tanentID}" />
							<form:input type="hidden" path="status" value="true" />
							<div class="form-body pal">
								<div class="col-md-9">
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">First
											Name <span class='require'>*</span>
										</label>
										<div class="col-md-9">
											<div class="input-icon right">
												<form:input class="form-control" path="firstName" maxlength="50" onkeypress="return onlyAlphabets(event,this);"
													value="${Driver.firstName}" placeholder="Enter First Name"></form:input>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Middle
											Name <span class='require'>*</span>
										</label>
										<div class="col-md-9">
											<div class="input-icon right">
												<form:input class="form-control" path="middleName" maxlength="50" onkeypress="return onlyAlphabets(event,this);"
													value="${Driver.middleName}" placeholder="Enter Middle Name"></form:input>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Last
											Name <span class='require'>*</span>
										</label>

										<div class="col-md-9">
											<div class="input-icon right">
												<form:input class="form-control" path="lastName" maxlength="50" onkeypress="return onlyAlphabets(event,this);"
													value="${Driver.lastName}" placeholder="Enter Last Name"></form:input>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Password<span
											class='require'>*</span>
										</label>

										<div class="col-md-9">
											<div class="input-icon right">
												<form:input class="form-control" path="password" type="password"
														value="${Driver.password}" maxlength="20"
														placeholder="Enter Password" />
											</div>
										</div>
									</div>

									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Contact
											No <span class='require'>*</span>
										</label>

										<div class="col-md-9">
											<div class="input-icon right">
												<form:input class="form-control" path="contactNo"
													value="${Driver.contactNo }" maxlength="10"
													onkeypress="return onlyNos(event,this);" />
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">IMEINO<span class='require'>*</span>
										</label>

										<div class="col-md-9">
											<div class="input-icon right">
												<form:input class="form-control" path="imeino"
													value="${Driver.imeino }" maxlength="16"
													onkeypress="return onlyNos(event,this);" />
											</div>
										</div>
									</div>
								
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Address
											<span class='require'>*</span>
										</label>

										<div class="col-md-9">
											<div class="input-icon right">
												<textarea class="form-control" id="address" name="address">${Driver.address}</textarea>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Select
											Country<span class='require'>*</span>
										</label>
										<div class="col-md-9">
											<div class="input-icon right">
												<form:select class="form-control" path="countryID">
													<form:option value="0">Select Country</form:option>
													<c:forEach var="countryLst" items="${countryLst}">
														<form:option value="${countryLst.id}"
															selected="${countryLst.id eq Driver.countryID ?'selected': ''} ">${countryLst.countryName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Select
											State<span class='require'>*</span>
										</label>

										<div class="col-md-9">
											<div class="input-icon right">
												<form:select class="form-control" path="stateID"
													itemValue="stateID">
													<form:option value="0">Select State</form:option>
													<c:forEach var="stateLst" items="${stateLst}">
														<form:option value="${stateLst.id}"
															selected="${stateLst.id eq Driver.stateID ? 'selected' : ''} ">${stateLst.stateName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Select
											City<span class='require'>*</span>
										</label>
										<div class="col-md-9">
											<div class="input-icon right">
												<form:select class="form-control" path="cityID"
													itemValue="cityID">
													<form:option value="0">Select City</form:option>
													<c:forEach var="cityLst" items="${cityLst}">
														<form:option value="${cityLst.id}"
															selected="${cityLst.id eq Driver.cityID ? 'selected' : ''} ">${cityLst.cityName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress"
											class="col-md-3 control-label">Package<span class='require'>*</span>
										</label>
										<div class="col-md-9">
											<div class="input-icon right">
												<form:select class="form-control" path="tenantPackageID"
															 itemValue="tenantPackageID">
													<c:if test="${empty Driver.id || empty Driver.tenantPackageID}">
														<form:option value="0">Select Package</form:option>
													</c:if>
													<c:forEach var="tenantPackage" items="${tenantPackageList}">
														<c:if test="${empty Driver.id || empty Driver.tenantPackageID}">
															<form:option value="${tenantPackage.id}"
																selected="${tenantPackage.id eq Driver.tenantPackageID ? 'selected' : ''} ">${tenantPackage.name}</form:option>
														</c:if>
														<c:if test="${not empty Driver.id}">
															<form:option value="${tenantPackage.id}"
																selected="${tenantPackage.id eq Driver.tenantPackageID ? 'selected' : ''} ">${tenantPackage.name}</form:option>
														</c:if>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">License
											no <span class='require'>*</span>
										</label>
										<div class="col-md-9">
											<div class="col-md-10 flot_for_hour_left">
												<div class="input-icon right">
													<form:select class="form-control" path="licenseNo"
														itemValue="licenseNo">
														<form:option value="Select LicenseNo">Select License No</form:option>
													</form:select>
												</div>
											</div>
											<div class="col-md-2 flot_for_hour_left">
												<c:if test="${Driver.licenseImage != null}">
													<img width="15%" id="licenseImage"
														alt="${Driver.licenseImage != '' ? Driver.licenseImage : 'License Image Not Found'}"
														src="<%=UrlConstants.FETCH_IMAGE%>?imageName=${Driver.licenseImage}"
														style="margin-bottom: 10px;" />
												</c:if>
												<form:input type="file" class="browse-btn-clck" path="imgFile1"
													value="${Driver.licenseImage}" />
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Aadhar
											No. <span class='require'>*</span>
										</label>

										<div class="col-md-9">
											<div class="col-md-10 flot_for_hour_left">
												<div class="input-icon right">
													<form:input class="form-control" type="text"
														path="aadharCardNO" maxlength="14" onkeypress="return onlyNos(event,this);" 
														value="${Driver.aadharCardNO}" />
												</div>
											</div>
											<div class="col-md-2 flot_for_hour_left">
												<c:if test="${Driver.adharCardImage != null}">
													<img width="15%" id="adharCardImage"
														alt="${Driver.adharCardImage != '' ? Driver.adharCardImage : 'Aadhar card Image Not Found'}"
														src="<%=UrlConstants.FETCH_IMAGE%>?imageName=${Driver.adharCardImage}"
														style="margin-bottom: 10px;" />
												</c:if>

												<form:input  type="file" class="browse-btn-clck" path="imgFile2"
													value="${Driver.adharCardImage}" />
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">Color</label>
										<div class="col-md-6">
											<div class="input-group my-colorpicker2 colorpicker-element">
												<form:input class="form-control" path="color"
													value="${Driver.color}" type="text" />
												<div class="input-group-addon">
													<i
														style="background-color: ${Driver.color != null ? Driver.color : '#000000'}"></i>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-11 col-md-offset-1">
											<hr>
											<div class="input-icon">
												<button type="submit" class="btn orange_bg">${Driver.id != null ? 'Update' : 'Save'}</button>
												<a href="<%=UrlConstants.DRIVER_LIST%>" class="btn grey_bg"
													type="button">cancel</a>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<center>
									<div class="form-group">
										<img
											alt="${Driver.image != null && Driver.image != '' ? Driver.image : 'Image Not Found'}"
											src="${Driver.image != null && Driver.image != '' ? '/fetch-image?imageName=' : ''}${Driver.image != null ? Driver.image : 'assets/images/ban123.png'}" />
										<div class="col-md-6 col-md-offset-3">
											<form:input type="file" path="imgFile" id="imgFile"
												class="browse-btn-clck"></form:input>
										</div>
									</div>
								</center>
							</div>
						</form:form>
					</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>