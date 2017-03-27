<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">

	$(document).ready(function() {
		if("${message}" != "") {
			$(".alert-error-message").fadeIn("fast").delay("3000").fadeOut("fast");
		}
		
		changeTenantPackageID("${car.tenantPackageID}", 'edit');
		$("body").delegate("#tenantPackageID", "change", function() {
			var tenantPackageID = $(this).val();
			changeTenantPackageID(tenantPackageID, 'change');
		});
		
		function changeTenantPackageID(tenantPackageID, type) {
			var typeVal =  '';
			if(type === 'edit') {
				$('#carNo').html("<option value='${car.carNo}'>${car.carNo}</option>");
				typeVal = 'false';
			} else {
				typeVal = 'true';
			}
			if (type != 'edit') {
				if(tenantPackageID != '' && tenantPackageID != 0) {
					$.ajax({
	                    type:'POST',
	                    url:'<%=UrlConstants.LIST_TENANT_PACKAGE_CAR_OR_DRIVER_LIST_AJAX%>',
	                    data:'${_csrf.parameterName}=${_csrf.token}&type=carNo&packageID='+tenantPackageID+'&typeCheck='+typeVal,
	                    success:function(data){
                    		$('#carNo').html('<option value="Select CarNo">Select CarNo</option>');
	                        for ( var i = 0, len = data.length; i < len; ++i) {
	                            var carNo = data[i];
	                            alert(carNo);
	                            var selectedValueEqual = '';
	                            if('${car.carNo}' != '' && carNo === '${car.carNo}') {
	                            	selectedValueEqual = 'selected=selected';
	                            } else {
	                            	selectedValueEqual = '';
	                            }
	                            $('#carNo').append("<option value=\"" + carNo + "\" "+ selectedValueEqual +">" + carNo+ "</option>");
	                        }
	                    }
	                });
				}
			}
		}
	});

	$(document).ready(function() {
	
    	jQuery.validator.addMethod("carTypeRequired", function(value, element) {
            var val = value;
            if (val === "Select Type") {
                return false;
            } else {
                return true;
            }
        }, "Please Select Car Type");

        jQuery.validator.addMethod("carNameRequired", function(value, element) {
            var val = value;
            if (val === "Select Name") {
                return false;
            } else {
                return true;
            }
        }, "Please Select Car Name");

        jQuery.validator.addMethod("carNoValidate", function(value, element) {
        	 var val = value;
             if (val === "Select CarNo") {
                 return false;
             } else {
                 return true;
             }
        }, "Please Select Car No.");

        jQuery.validator.addMethod("sizeValidation", function(value, element) {
            var val = value;
            if (val >15) {
                return false;
            } else if (val == 0) {
                return false;
            } else {
                return true;
            }
        }, "Please Enter Size Between 1 to 15");
        jQuery.validator.addMethod("packageRequired", function(value, element) {
			var val = value;
			if (val === "0") {
				return false;
			} else {
				return true;
			}
		}, "Please Select Package Name");

        $("#saveCar").validate({
            rules : {
                carTypeId : {
                    required : true,
                    carTypeRequired : true
                },
                carNameId : {
                    required : true,
                    carNameRequired : true
                },
                carNo : {
                    required : true,
                    carNoValidate : true
                },
                carModel : "required",
                seatingCap : {
                    required : true,
                    sizeValidation :true
                },
                tenantPackageID : {
					required : true,
					packageRequired : true
				},
                color : "required"
            },
            // Specify the validation error messages
            messages : {
                carTypeId : "Please Select CarType",
                carNameId : "Please Slect Car Model",
                carNo : "Please Select Car No",
                carModel : "Please Enter Car Model",
                seatingCap : {
                    required : "Please Enter Seating Capacity",
                    sizeValidation :"Please Enter Below 15 Capacity "
                },
                tenantPackageID : "Please Select Tenant Package",
                color : "Please select Color",
            },
            submitHandler : function(form) {
                $("#saveCar").attr("action","<%=UrlConstants.SAVE_CAR%>?${_csrf.parameterName}=${_csrf.token}");
                form.submit();
            }
        });

	    $('#carTypeId').change(function(){
            var carTypeId = $(this).val();
            if(carTypeId != '0'){
                $.ajax({
                    type:'POST',
                    url:'<%=UrlConstants.GET_CARNAME%>',
                    data:'${_csrf.parameterName}=${_csrf.token}&carTypeId='+carTypeId,
                    success:function(data){
                        $('#carNameId').html('<option value="">Select Car Name</option>');
                        for ( var i = 0, len = data.length; i < len; ++i) {
                            var carName = data[i];
                            $('#carNameId').append("<option value=\"" + carName.id + "\">" + carName.carName+ "</option>");
                        }
                    }
                });
            } else {
                $('#carNameId').html('<option value="">Select Car Type First</option>');
            }
        });

	    $('.my-colorpicker2').colorpicker();

		$("#carModelValidation").datepicker({
			changeMonth: true,
		    changeYear: true,
		    dateFormat: 'MM-yy',
		    maxDate: new Date(),
		    onClose: function() {
                var iMonth = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                var iYear = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                $(this).datepicker('setDate', new Date(iYear, iMonth, 1));
		    },
		    beforeShow: function() {
                if ((selDate = $(this).val()).length > 0) {
                    iYear = selDate.substring(selDate.length - 4, selDate.length);
                    iMonth = jQuery.inArray(selDate.substring(0, selDate.length - 5),
                    $(this).datepicker('option', 'monthNames'));
                    $(this).datepicker('option', 'defaultDate', new Date(iYear, iMonth, 1));
                    $(this).datepicker('setDate', new Date(iYear, iMonth, 1));
                }
		    }
		});

		$("#carDetails").DataTable();

		$("body").delegate("#color","change", function() {
			var color = $(this).val();
			var carid= $('#id').val();
			$.ajax({
				type : "POST",
				url : "<%=UrlConstants.CAR_CHECK_COLOR%>",
				data : "${_csrf.parameterName}=${_csrf.token}&color=" + color+"&carid="+carid,
				success : function(data) {
					if(data === true){
						$("#errordricolor").show();
						$("#color").val('');
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

	    //Disable cut copy paste
	    $('body').bind('cut copy paste', function (e) {
	        e.preventDefault();
	    });
	    
	    //Disable mouse right click
	    $("body").on("contextmenu",function(e){
	        return false;
	    });
    });

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
</script>
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
				<div class="caption">
					<a class="btn btn-success" href="<%=UrlConstants.LIST_CAR%>">Back</a>
				</div>
				<div class="portlet-header">
					<div class="caption">${empty car.id ? 'Add' : 'Update'}Car</div>
				</div>
				<div class="portlet-body">
					<form:form method="POST" class="form-horizontal" id="saveCar"
						ondragstart="return false;" ondrop="return false;">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<form:input type="hidden" path="id" value="${car.id}"></form:input>
						<div class="form-body pal">
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label"></label>
							<c:if test="${not empty message}">
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label"></label>
									<div class="col-md-12">
										 <div class="alert col-md-6 alert-success-message" role="alert">${message}</div>
									</div>
								</div>
							</c:if>
							</div>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">Type
									of Car<span class='require'>*</span>
								</label>
								<div class="col-md-6">
									<div class="input-icon right">
										<form:select class="form-control" path="carTypeId"
											itemValue="carTypeId">
											<form:option value="Select Type">Select Type</form:option>
											<c:forEach var="CarTypeList" items="${CarTypeList}">
												<form:option value="${CarTypeList.id}"
													selected="${CarTypeList.id eq car.carTypeId ? 'selected': ''} ">${CarTypeList.carType}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">Model
									of Car<span class='require'>*</span>
								</label>
								<div class="col-md-6">
									<div class="input-icon right">
										<form:select class="form-control" path="carNameId"
											itemValue="carNameId" id="carNameId">
											<form:option value="Select Name">Select Name</form:option>
											<c:forEach var="carLst" items="${carLst}">
												<form:option id="${carNameId}" value="${carLst.id}"
													selected="${carLst.id eq car.carNameId ?'selected': ''} ">${carLst.carName}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="inputAddress"
									class="col-md-2 control-label">Package<span class='require'>*</span>
								</label>
								<div class="col-md-6">
									<div class="input-icon right">
										<form:select class="form-control" path="tenantPackageID"
											itemValue="tenantPackageID" id="tenantPackageID">
											<c:if test="${empty car.id || empty car.tenantPackageID}">
												<form:option value="0">Select Package</form:option>
											</c:if>
											<c:forEach var="tenantPackage" items="${tenantPackageList}">
												<c:if test="${empty car.id || empty car.tenantPackageID}">
													<form:option value="${tenantPackage.id}"
														selected="${tenantPackage.id eq car.tenantPackageID ? 'selected' : ''} ">${tenantPackage.name}</form:option>
												</c:if>
												<c:if test="${not empty car.id}">
													<form:option value="${tenantPackage.id}"
														selected="${tenantPackage.id eq car.tenantPackageID ? 'selected' : ''} ">${tenantPackage.name}</form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">Car
									No<span class='require'>*</span>
								</label>
								<div class="col-md-6">
									<form:select class="form-control" path="carNo" itemValue="carNo">
										<form:option value="Select CarNo">Select Car No</form:option>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">Car
									Model<span class='require'>*</span>
								</label>
								<div class="col-md-6">
									<div class="input-icon right">
										<form:input class="form-control" path="carModel"
											value="${car.carModel}" id="carModelValidation" />
									</div>
									<font color="red"><form:label path="carModel"
											id="errorcarModel" Class="error"></form:label></font>
								</div>
							</div>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">Seating
									Capacity<span class='require'>*</span>
								</label>
								<div class="col-md-6">
									<div class="input-icon right">
										<form:input class="form-control" maxlength="2"
											path="seatingCap" value="${car.seatingCap}"
											id="seatingCapValidation"
											onkeypress="return onlyNos(event,this);" />
									</div>
									<font color="red"><form:label path="seatingCap"
											id="errorseatingCap" Class="error"></form:label></font>
									<form:errors path="seatingCap" cssClass="error"></form:errors>
								</div>
							</div>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">Colour<span
									class='require'>*</span></label>
								<div class="col-md-6">
									<div class="input-group my-colorpicker2 colorpicker-element">
										<form:input class="form-control" path="color"
											value="${car.color}" type="text" />
										<div class="input-group-addon">
											<i style="background-color: ${car.color != null && car.color != '' ? car.color : ''}"></i>
										</div>
										<div class="col-md-6">
											<div class="input-icon right">
												
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">&nbsp;</label>
								<div class="col-md-6">
									<input class="btn btn-success" class="submit" type="submit"
										value="Submit" />
								</div>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>