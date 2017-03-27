<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

    $("#editsellerform").validate({
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
            $("#editsellerform")
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
</script>
<!--BEGIN CONTENT-->
            <div class="page-content">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="portlet">
                            <div class="portlet-body">
                                <div class="box box-bottom col-md-12 media-right-pad media_float_left">
                                    <div class="box-header">
                                        <div class="caption box-caption">Edit seller account</div>
                                    </div>
                                    <div class="box-body">
                                                <p><b>Seller Id :</b><span class="padding-left-10">&nbsp;&nbsp;ABCD</span></p>
                                                <p><b>Tenant Id :</b><span class="padding-left-10">&nbsp;&nbsp;${tanentID}</span></p>
                                                <p><b>Tenant Name :</b><span class="padding-left-10">&nbsp;&nbsp;${tenantName}</span></p>
                                                <form:form class="form-horizontal" id="editsellerform">
                                                    <form:input type="hidden" path="id" value="${sellerModel.id}"></form:input>
                                                    <hr>
                                                    <div class="col-md-2"><b>Status : &nbsp;</b>
                                                        <form:radiobutton path="status" name="active" value="0"
                                                        checked="${sellerModel.status eq '0' ? 'checked' : ''}"></form:radiobutton>Active
                                                    </div>
                                                    <div class="col-md-2">
                                                        <form:radiobutton path="status" name="active" value="1"
                                                        checked="${sellerModel.status eq '1' ? 'checked' : ''}"></form:radiobutton>Inactive
                                                    </div>   
                                                        <br>                                 
                                                         <hr>               
                                                    <div class="col-md-12">
                                                        <div class="col-md-12">
                                                             <div class="form-group col-md-6 flot_for_hour_left "><label for="inputAddress" class="col-md-4 flot_for_hour_right control-label">First Name<span class='require'>*</span></label>
                                                            <div class="col-md-8 ">
                                                                <div class="input-icon right"><form:input class="form-control" placeholder="Enter First Name" path="firstName" value="${sellerModel.firstName}" maxlength="20"/></div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group col-md-6 flot_for_hour_left"><label for="inputAddress" class="col-md-4 control-label">Website<span class='require'>*</span></label>

                                                            <div class="col-md-8">
                                                                <div class="input-icon right"><form:input class="form-control" placeholder="www.companyname.com" path="businessurl" value="${sellerModel.businessurl}"/></div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group col-md-6 flot_for_hour_left "><label for="inputAddress" class="col-md-4 flot_for_hour_right control-label">Last Name<span class='require'>*</span></label>
                                                            <div class="col-md-8 ">
                                                                <div class="input-icon right"><form:input class="form-control" placeholder="Enter Last Name" path="lastName" value="${sellerModel.lastName}" maxlength="20"/></div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group col-md-6 flot_for_hour_left"><label for="inputAddress" class="col-md-4 control-label">Mobile No.<span class='require'>*</span></label>

                                                            <div class="col-md-8">
                                                                <div class="input-icon right"><form:input class="form-control" placeholder="Enter Mobile No." path="mobileNo" value="${sellerModel.mobileNo}" maxlength="10" onkeypress="return onlyNos(event,this);" /></div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group col-md-6 flot_for_hour_left "><label for="inputAddress" class="col-md-4 flot_for_hour_right control-label">Address<span class='require'>*</span></label>
                                                            <div class="col-md-8 ">
                                                                <div class="input-icon right"><form:input class="form-control" placeholder="Enter Address" path="address1" value="${sellerModel.address1}" maxlength="100"/></div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group col-md-6 flot_for_hour_left"><label for="inputAddress" class="col-md-4 control-label">Bank<span class='require'>*</span></label>
                                                            <div class="col-md-8 ">
                                                                <div class="input-icon right">
                                                                    <form:select class="form-control select2" path="payoutmode" style="width: 100%;">
                                                                        <form:option value="0" selected="selected">Selct Bank</form:option>
                                                                        <form:option value="Alaska">Alaska</form:option>
                                                                        <form:option value="California">California</form:option>
                                                                        <form:option value="Delaware">Delaware</form:option>
                                                                        <form:option value="Tennessee">Tennessee</form:option>
                                                                    </form:select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group col-md-6 flot_for_hour_left"><label for="inputAddress" class="col-md-4 control-label">Address 2<span class='require'>*</span></label>

                                                            <div class="col-md-8">
                                                                <div class="input-icon right"><form:input class="form-control" placeholder="Enter Address 2" path="address2"  value="${sellerModel.address2}" maxlength="200"/></div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group col-md-6 flot_for_hour_left "><label for="inputAddress" class="col-md-4 flot_for_hour_right control-label">IFSC Code<span class='require'>*</span></label>
                                                            <div class="col-md-8 ">
                                                                <div class="input-icon right"><form:input class="form-control" placeholder="Pin/Zip Code" path="seller_ifsc_code" value="${sellerModel.seller_ifsc_code}" maxlength="15"/></div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group col-md-6 flot_for_hour_left"><label for="inputAddress" class="col-md-4 control-label">Country<span class='require'>*</span></label>
                                                            <div class="col-md-8 ">
                                                                <div class="input-icon right">
                                                                    <form:select class="form-control" id="countryID"
														path="country.id">
														<form:option value="0">Select Country</form:option>
														<c:forEach var="countryLst" items="${countryLst}">
															<form:option value="${countryLst.id}"
																selected="${countryLst.id eq sellerModel.country.id ?'selected': ''} ">${countryLst.countryName}</form:option>
														</c:forEach>
													</form:select>
													<label id="error-country" style="color: red;"></label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        

                                                        <div class="form-group col-md-6 flot_for_hour_left"><label for="inputAddress" class="col-md-4 control-label">Acoount No.<span class='require'>*</span></label>

                                                            <div class="col-md-8">
                                                                <div class="input-icon right"><form:input class="form-control" placeholder="Enter Acoount No."   path="seller_account_number" value="${sellerModel.seller_account_number}" maxlength="18"/></div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group col-md-6 flot_for_hour_left"><label for="inputAddress" class="col-md-4 control-label">State<span class='require'>*</span></label>
                                                            <div class="col-md-8 ">
                                                                <div class="input-icon right">
                                                                    <form:select class="form-control" id="stateID"
														path="state.id" itemValue="stateID">
														<form:option value="0">Select State</form:option>
														<c:forEach var="stateLst" items="${stateLst}">
															<form:option value="${stateLst.id}"
																selected="${stateLst.id eq sellerModel.state.id ? 'selected' : ''} ">${stateLst.stateName}</form:option>
														</c:forEach>
													</form:select>
													<label id="error-state" style="color: red;"></label>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group col-md-6 flot_for_hour_left"><label for="inputAddress" class="col-md-4 control-label">Email Id<span class='require'>*</span></label>

                                                            <div class="col-md-8">
                                                                <div class="input-icon right"><form:input class="form-control" placeholder="Enter Email Id" path="selleremailId" value="${sellerModel.selleremailId}" maxlength="50"/></div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group col-md-6 flot_for_hour_left"><label for="inputAddress" class="col-md-4 control-label">City<span class='require'>*</span></label>
                                                            <div class="col-md-8 ">
                                                                <div class="input-icon right">
                                                                    <form:select class="form-control" id="cityID"
														path="city.id" itemValue="cityID">
														<form:option value="0">Select City</form:option>
														<c:forEach var="cityLst" items="${cityLst}">
															<form:option value="${cityLst.id}"
																selected="${cityLst.id eq sellerModel.city.id ? 'selected' : ''} ">${cityLst.cityName}</form:option>
														</c:forEach>
													</form:select>
													<label id="error-city" style="color: red;"></label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                         <div class="form-group col-md-6 flot_for_hour_left"><label for="inputAddress" class="col-md-4 control-label">Zip Code<span class='require'>*</span></label>

                                                            <div class="col-md-8">
                                                                <div class="input-icon right"><form:input class="form-control" placeholder="Pin Code" path="pinCode" value="${sellerModel.pinCode}" maxlength="9"/></div>
                                                            </div>
                                                        </div>
                                                       <div class="col-md-12 col-md-offset-1"> <button class="btn orange_bg" type="submit">Submit</button></div>
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
            <!--END CONTENT-->