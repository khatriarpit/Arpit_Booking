<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page import="com.emxcel.dms.core.business.constants.Constants" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="//maps.googleapis.com/maps/api/js?key=AIzaSyBNnuOJY8u-8LZckoiOzGrHyTHPogo6Gq8&libraries=places"></script>
<script type="text/javascript">
         google.maps.event.addDomListener(window, 'load', function () {
             var places = new google.maps.places.Autocomplete(document.getElementById('pickUpLocation'));
             google.maps.event.addListener(places, 'place_changed', function () {
            	var place = places.getPlace();
                var address = place.formatted_address;
                var latitude = place.geometry.location.lat();
                var longitude = place.geometry.location.lng();
              	$("pickUpLocation").val(address);
              	$("#pickUpLatLong").val(latitude + "," + longitude);
             });
         });
         
        $("body").delegate("#pickUpLocation, #drop_location ", "change", function(){
        	 var places = $(this).val();
        	 $(this).val('');
        });

            google.maps.event.addDomListener(window, 'load', function () {
                      var places = new google.maps.places.Autocomplete(document.getElementById('drop_location'));
                      google.maps.event.addListener(places, 'place_changed', function () {
                          var place = places.getPlace();
                          var address = place.formatted_address;
                          var latitude = place.geometry.location.lat();
                          var longitude = place.geometry.location.lng();
                          $("drop_location").val(address);
                          $("#dropLatLong").val(latitude + "," + longitude);
                      });
                  });
            function emptyMinimumKm(){
                $("#sourcePlace").val("");
                 $("#destinationPlace").val("");
              	 $("#hnkHours").val("");
            	 $("#hnkKms").val("");
            	 $("#amouunt").val("");
            	 $("#additionalHours").val("");
            	 $("#additionalKms").val("");
                 $("#sndPrice").val("");
            }
            function emptySourceDestination(){
              	         $("#hnkHours").val("");
               			 $("#hnkKms").val("");
               			 $("#amouunt").val("");
               			 $("#additionalHours").val("");
                	     $("#additionalKms").val("");
                         $("#minkms").val("");
              			 $("#minrate").val("");
              			 $("#graceHours").val("");

            }
            function emptyHoursKMFields(){
            $("#sourcePlace").val("");
            $("#destinationPlace").val("");
            $("#sndPrice").val("");
            $("#minkms").val("");
            $("#minrate").val("");
            $("#graceHours").val("");
            }
            </script>
<script type="text/javascript">
 function ShowAlert() {
  var email = $("#emailId").val();
  var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (!filter.test(email)) {
    	$("#error-emailId").html('Please provide a valid email address');
        email.focus;
        return false;
    }
    else {
        return true;
    }
 }
 </script>
<script type="text/javascript">

function changeRateOfContract(){
	var rateOfContract=$("option:selected", $("#rateOfContractId")).text();
  	if(rateOfContract==="Minimum Km"){
		emptyMinimumKm();
	        $("#HoursAndKM").hide();
	        $("#HoursAndKMRow2").hide();
			$("#SourceAndDestination").hide();
			$("#minimumKm").show();
	  }else if(rateOfContract==="Hours And Km"){
            emptyHoursKMFields();
			$("#SourceAndDestination").hide();
			$("#minimumKm").hide();
			  $("#HoursAndKM").show();
			  $("#HoursAndKMRow2").show();
		} else if(rateOfContract==="Source And Destination") {
                emptySourceDestination();
	             $("#minimumKm").hide();
		         $("#HoursAndKM").hide();
		         $("#HoursAndKMRow2").hide();
		         $("#SourceAndDestination").show();
		      }
		  else
		   {
		    hideRateContractFields();
		   }

	
}
	function hideRateContractFields()
	{
		$("#comapanyname").hide()
		$("#HoursAndKM").hide();
		$("#HoursAndKMRow2").hide();
    	  $("#SourceAndDestination").hide();
          $("#minimumKm").hide();
          $("#debitCheck").hide();
	}
function getInvoiceCategories(){ 
		var InvoiceCategoryValue=$("option:selected", $("#invoiceCategories")).text();
		 if(InvoiceCategoryValue==="Company") { 
			$("#comapanyname").show(); }	
		else 
		{ $("#comapanyname").hide();
		  $("#company").val("");
		} 
		
		}
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
function toValidDate(datestring){
    return datestring.replace(/(\d{2})(\/)(\d{2})/, "$3$2$1");
}
	function validation(){
        var pickupDate= $("#pickUpDateTime1").val();
        var dropdate= $("#dropDateTime1").val();
         pickupDate = pickupDate.split(' ')[0];
         var pickupStartDate =pickupDate+" 00:00:00";
        var pickDate = new Date(toValidDate(pickupStartDate));
        var dropDate = new Date(toValidDate(dropdate));
        var diff = new Date(dropDate - pickDate);
        var days = (diff)/1000/60/60/24;


		var modes = $('input[name=invoiceMode]:radio:checked').val();
		var customerName = $("#personName").val();
		var mobileNo = $("#contactNo").val();
		var city = $("#cityId").val();
		var rateContract = $("#rateOfContractId").val();
		var invoiceCategory = $("#invoiceCategories").val();
		var InvoiceCategoryValue=$("option:selected", $("#invoiceCategories")).text();
		var RatecontractValue = $("option:selected", $("#rateOfContractId")).text();
		var companyValue = $("#company").val();
		var contracthours = $("#hnkHours").val();
		var contractkms = $("#hnkKms").val();
		var contractamount = $("#hnkAmount").val();
		var addhours = $("#additionalHours").val();
		var addkm = $("#additionalKms").val();
		var minkm = $("#minkms").val();
		var MinRate = $("#minrate").val();
		var mingroce = $("#graceHours").val();
	    var source = $("#sourcePlace").val();
	    var dest = $("#destinationPlace").val();
	    var price = $("#sndPrice").val();
	    var driverAllownce = $("#driverAllownce").val();
		var valid = true;
		if(days>1 && RatecontractValue==='<%=Constants.HOURS_AND_KM%>'){
            $("#error-rateContractSelection").html("You can not Select Hours & KM for more then 1 days");
            $("#error-rateContractSelection").show();
            valid = false;
            return valid;
        }else{
            valid = true;
            $("#error-rateContractSelection").html("");
        }


		if(customerName != ""){
            var reg =/<(.|\n)*?>/g;
            if (reg.test(customerName) == true) {
                $("#error-customer").html("Please enter Valid Customer name");
                $("#error-customer").show();
                valid = false;
                return valid;
            }else {
                valid = true;
                $("#error-customer").html("");

            }
		} else {
			$("#error-customer").html("Please enter Customer Name");
            $("#error-customer").css("display","");
			valid = false;
			return valid;
		}
		if(mobileNo === ""){
			$("#error-contactNo").html("Please enter Contact Number");
			 valid = false;
			 return valid;
		}else if(mobileNo.length != 10){
		$("#error-contactNo").html("Please enter minimum 10 digit Contact Number");
		$("#error-contactNo").show();
                			valid = false;
                			return valid;
		}
		else if(mobileNo === "0000000000"){
		$("#error-contactNo").html("Please enter correct Contact Number");
		$("#error-contactNo").show();
        			valid = false;
        			return valid;
		}
		else{
			$("#error-contactNo").html("");
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
		if(invoiceCategory === "0"){
			$("#error-invoice").html("Please Select Invoice");
			 valid = false;
			 return valid;
		}
		else{
			$("#error-invoice").html("");
			valid = true;
		}
		if(modes === "Auto"){
			if(rateContract === "0"){
				$("#error-contract").html("Please Select RateOfContract");
				 valid = false;
				 return valid;
			}else{
				$("#error-contract").html("");
				 valid = true;
			}
		}
		if(InvoiceCategoryValue === "Company"){
			if(companyValue != ""){
				$("#error-company").html("");
				valid = true;
			}else{
				$("#error-company").html("Please Enter Company Name");
                $("#error-company").css("display","");
				valid = false;
				return valid;
			}
		}
		if(RatecontractValue === "Hours And Km"){
			if(contracthours != ""){
				$("#error-Hours").html("");
				valid = true;
			}else{
				$("#error-Hours").html("Please Enter Hours");
				valid = false;
				return valid;
			}
			if(contractkms != ""){
				$("#error-kms").html("");
				valid = true;
			}else{
				$("#error-kms").html("Please Enter Km");
				valid = false;
				return valid;
			}
			if(contractamount != ""){
				$("#error-amount").html("");
				valid = true;
			}else{
				$("#error-amount").html("Please Enter Amount");
				valid = false;
				return valid;
			}
			if(addhours != ""){
				$("#error-addhours").html("");
				valid = true;
			}else{
				$("#error-addhours").html("Please Enter Additional Hours");
				valid = false;
				return valid;
			}
			if(addkm != ""){
				$("#error-addkms").html("");
				valid = true;
			}else{
				$("#error-addkms").html("Please Enter Additional kms");
				valid = false;
				return valid;
			}
		}else if(RatecontractValue === "Minimum Km"){
			if(minkm != ""){
				$("#error-minkms").html("");
				valid = true;
			}else{
				$("#error-minkms").html("Please Enter Minimum kms");
				valid = false;
				return valid;
			}
			if(MinRate != ""){
				$("#error-minrate").html("");
				valid = true;
			}else{
				$("#error-minrate").html("Please Enter Minimum rate");
				valid = false;
				return valid;
			}
			if(mingroce != ""){
				$("#error-graceHours").html("");
				valid = true;
			}else{
				$("#error-graceHours").html("Please Enter Minimum GraceHours");
				valid = false;
				return valid;
			}
			if(driverAllownce != ""){
				$("#error-driverAllownce").html("");
				valid = true;
			}else{
				$("#error-driverAllownce").html("Please Enter Driver Allownce");
				valid = false;
				return valid;
			}
		}else if(RatecontractValue === "Source And Destination"){
			if(source != ""){
				$("#error-source").html("");
				valid = true;
			}else{
				$("#error-source").html("Please Enter Source Place");
				valid = false;
				return valid;
			}
			if(dest != ""){
				$("#error-destination").html("");
				valid = true;
			}else{
				$("#error-destination").html("Please Enter Destination Place");
				valid = false;
				return valid;
			}
			if(price != ""){
				$("#error-price").html("");
				valid = true;
			}else{
				$("#error-price").html("Please Enter Price");
				valid = false;
				return valid;
			}
		}
		return valid;
	}

	  function split(val) {
	        return val.split(/,\s*/);
	     }
	     function extractLast(term) {
	        return split(term).pop();
	     }

	        $(function() {
	                $("#company").autocomplete({
                        select: function( event, ui ) {
                            $( "#company" ).val( ui.item.value );
                            return false;
                        },
                        focus: function( event, ui ) {
                            $( "#company" ).val( ui.item.label );
                            return false;
                        },
                        change: function(event, ui) {
                            if (!ui.item) {
                                $("#company").val("");
                            }
                        },
	                source : function(request, response) {
	                $.ajax({
	                        url : "<%=UrlConstants.GETCOMPANY_LIST%>",
	                        type : "GET",
	                        data : {
	                                term : request.term
	                        },
	                        dataType : "json",
	                        success : function(data) {
	                        console.log(data);
	                               response($.map(data, function(v,i){
	                                   console.log(data);
	                               return {
	                               label: v.comapanyName  ,
	                               value: v.comapanyName
	                               };
	                               }));
	                        }
	                });
	        }
	});
	});
	  $(function() {
	                $("#sourcePlace").autocomplete({
	                source : function(request, response) {
	                $.ajax({
	                        url : "<%=UrlConstants.SOURCEPLACE_LIST%>",
	                        type : "GET",
	                        data : {
	                                term : request.term
	                        },
	                        dataType : "json",
	                        success : function(data) {
	                        console.log(data);
	                               response($.map(data, function(v,i){
	                               return {
	                               label: v.sourcePlace  ,
	                               value: v.sourcePlace
	                               };
	                               }));
	                        }
	                });
	        }
	});
	});
	  $(function() {
	                $("#destinationPlace").autocomplete({
	                source : function(request, response) {
	                $.ajax({
	                        url : "<%=UrlConstants.DESTINATIONPLACE_LIST%>",
	                        type : "GET",
	                        data : {
	                                term : request.term
	                        },
	                        dataType : "json",
	                        success : function(data) {
	                        console.log(data);
	                               response($.map(data, function(v,i){
	                               return {
	                               label: v.destinationPlace  ,
	                               value: v.destinationPlace
	                               };
	                               }));
	                        }
	                });
	        }
	});
	});


	  $(function() {
          $("#contactNo").autocomplete({
          source : function(request, response) {
          $.ajax({
                  url : "<%=UrlConstants.GET_CONTACT_LIST%>",
                  type : "GET",
                  data : {
                          term : request.term
                  },
                  dataType : "json",
                  success : function(data) {
                  console.log(data);
                         response($.map(data, function(v,i){
                         return {
                         label: v.contactNo,
                         value: v.contactNo
                         };
                         }));
                  }
          });
  }
});
});

	  //taxslab
	     function getTax(){
	       var invoiceCategories = $('#invoiceCategories').val();
	       $("#ID_listOfTopics").html("");
	       if(invoiceCategories != '0'){
	           $.ajax({
	               type:'POST',
	               url:"<%=UrlConstants.INVOICE_TAX_LIST%>",
	               data:'${_csrf.parameterName}=${_csrf.token}&invoiceCategories='+invoiceCategories,
	               success:function(data){
	               	for(i=0;i<data.length;i++){
	               	if(i==0){
	               	$("#taxes_applied").show();
	               	}
   		  $("#ID_listOfTopics").append('<div class="col-md-12" align="right"><label id="lable1">' +data[i].taxCategory.name + ' ' + data[i].rate + '%</label><br /></div>')
//$("#ID_listOfTopics").append('<input type="text" disabled="" class="form-control" value=' +data[i].taxCategory.name + ' ' + data[i].rate + '>')
	               }
	               }
	           });
	       }
	   }

	     $("body").delegate("#contactNo", "change", function(){
	     	var contactNo = $(this).val();
	     	if(contactNo != null){
	     		$.ajax({
	     			type:'POST',
	     			url:'<%=UrlConstants.GET_GUEST_DETAIL%>',
	     			 data:'${_csrf.parameterName}=${_csrf.token}&contactNo='+contactNo,
	     			 success:function(data){
	     				 $('#personName').val(data.personName);
	     				$('#emailId').val(data.emailId);
	     			 }
	     		});
	     	}
	     });
	    //
	$(document).ready(function() {
	  hideRateContractFields();
     $('input[type="radio"]').click(function(){
       if($(this).attr("value")==="Auto"){
                 $("#rateOfContract").show();
                 $("#debitCheck").hide();
           }else if($(this).attr("value")==="Manual"){
           $("#rateOfContract").hide();
           $("#minimumKm").hide();
           $("#HoursAndKM").hide();
           $("#HoursAndKMRow2").hide();
           $("#SourceAndDestination").hide();
           $("#debitCheck").show();
         $(".debitCheckBox").attr('checked', true);
         }
         });
	});
	$(function() {
        jQuery('#pickUpDateTime1').datetimepicker({value:"${pickUpDateTime1}",step:10,format:'d/m/Y H:i:s'});
        jQuery('#dropDateTime1').datetimepicker({value:"${dropDateTime1}",step:10,format:'d/m/Y H:i:s'});
		jQuery.validator.addMethod("paymentRequired", function(value, element) {
			var val = value;
			if (val === "0") {
				return false;
			} else {
				return true;
			}
		}, "Please Select Invoice category");
		//form validation rules
		$("#save_client").validate({
			rules : {
				pickUpDateTime1 : "required",
				pickUpLocation : "required",
				dropDateTime1 : "required",
				paymentMode  : {
                    required : true,
                    paymentRequired : true
                },
				drop_location : "required"
			},
			messages : {
				pickUpDateTime1 : "Please provide a PickUp Date",
				pickUpLocation : "Please provide a PickUp Place",
				dropDateTime1 : "Please provide a Drop date",
				paymentMode : "Please Select Mode",
				drop_location : "Please provide a Place Name"
			},
			submitHandler : function(form) {
				if(validation()){
					var clientID = $("#id").val();

					if(clientID != '' && clientID!=0){
                        if(confirm("Are you sure you want to update this trip Details?")){
                            $("#delete-button").attr("href", "query.php?ACTION=delete&ID='1'");
						$("#save_client").attr("action", "<%=UrlConstants.UPDATE_CLIENT%>?${_csrf.parameterName}=${_csrf.token}");
						}else{
                            return false;
						}
					} else {
                        if(confirm("Are you sure you want to Add this trip Details?")){
						$("#save_client").attr("action", "<%=UrlConstants.SAVE_CLIENT%>?${_csrf.parameterName}=${_csrf.token}");
                        }else{
                            return false;
                        }
					}
					form.submit();
				}
			}
		});

		 $("body").delegate("#personName", "change", function(){
			$("#error-customer").hide();
		});
		$("body").delegate("#contactNo", "change", function(){
			$("#error-contactNo").hide();
		});
		$("body").delegate("#emailId", "change", function(){
			$("#error-emailId").hide();
		});
		$("body").delegate("#cityId", "change", function(){
			$("#error-city").hide();
		});
		$("body").delegate("#invoiceCategories", "change", function(){
			$("#error-invoice").hide();
		});
		$("body").delegate("#company", "change", function(){
			$("#error-company").hide();
		});
		$("body").delegate("#rateOfContractId", "change", function(){
			$("#error-contract").hide();
            $("#error-rateContractSelection").hide();
		});
		$("body").delegate("#minkms", "change", function(){
			$("#error-minkms").hide();
		});
		$("body").delegate("#minrate", "change", function(){
			$("#error-minrate").hide();
		});
		$("body").delegate("#driverAllownce", "change", function(){
			$("#error-driverAllownce").hide();
		});
		$("body").delegate("#graceHours", "change", function(){
			$("#error-graceHours").hide();
		});
		$("body").delegate("#hnkHours", "change", function(){
			$("#error-Hours").hide();
		});
		$("body").delegate("#hnkKms", "change", function(){
			$("#error-kms").hide();
		});
		$("body").delegate("#hnkAmount", "change", function(){
			$("#error-amount").hide();
		});
		$("body").delegate("#additionalHours", "change", function(){
			$("#error-addhours").hide();
		});
		$("body").delegate("#additionalKms", "change", function(){
			$("#error-addkms").hide();
		});
		$("body").delegate("#sourcePlace", "change", function(){
			$("#error-source").hide();
		});
		$("body").delegate("#destinationPlace", "change", function(){
			$("#error-destination").hide();
		});
		$("body").delegate("#sndPrice", "change", function(){
			$("#error-price").hide();
		});
	});
</script>
<!--BEGIN CONTENT-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-body">
					<div
						class="box box-bottom col-md-12 media-right-pad media_float_left">
						<div class="box-header">
							<div class="caption box-caption">Book Car</div>
						</div>
						<div class="box-body">
							<form:form id="save_client"
								modelAttribute="command" method="POST" class="form-horizontal" ondragstart="return false;" ondrop="return false;">
								<input type="hidden" name="preBookingId" value="${preBookingId}"></input>
								<form:input type="hidden" path="id" value="${clientModel.id}"></form:input>
								<form:input type="hidden" path="car.id" value="${carDtls.id}"></form:input>
								<form:input type="hidden" path="driver.id" value="${driverDtlS.id}"></form:input>
								<a href="<%=UrlConstants.SEARCH_CAR%>" class="btn orange_bg">Back</a>
								<p>Booking for ${carNameDtls.carName}-${carTypeDtls.carType}
									${carDtls.carNo}</p>
								<p>Driver Name : ${driverDtlS.fullName}</p>
								<c:if test="${not empty clientModel.id && clientModel.id ne '0'}">
									<p>
										<label>Trip ID : ${clientModel.tripId}</label>
									</p>
								</c:if>
								<hr>
								<div class="col-md-3">
									<form:radiobutton path="invoiceMode" id="Auto" class="ready-class"
										value="Auto"
										checked="${clientModel.id eq null || clientModel.invoiceMode eq 'Auto' ? 'checked' : ''}"></form:radiobutton>
									Automatic Invoice
								</div>
								<div class="col-md-3">
									<form:radiobutton path="invoiceMode" id="Manual" class="ready-class"
										value="Manual" checked="${clientModel.invoiceMode eq 'Manual' ? 'checked' : ''}"></form:radiobutton>
									Manual Invoice
								</div>
								<script type="text/javascript">
   									$(document).ready(function(){
   										if("${clientModel.id}" != null && "${clientModel.id}" != '') {
   											$('#${clientModel.invoiceMode}').trigger("click");
   											$(".ready-class").attr("disabled", "");
   											$('#invoiceCategories').trigger("click");
   											if("${clientModel.invoiceMode}" === "Auto") {
   												changeRateOfContract();
   											}
   											$('.readOnly').attr("readonly", "");
   										}
   									});
   								</script>
								<br>
								<hr>
								<div class="col-md-6">
									<div class="col-md-12">
										<h5>
											<b>Customer Details</b>
										</h5>
										<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_right control-label">Customer
												Number<span class='require'>*</span>
											</label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:input class="form-control" placeholder="Mobile"
														value="${clientModel.guest.contactNo}" id="contactNo"
														path="guest.contactNo" maxlength="10"
														onkeypress="return onlyNos(event,this);" />
													<label id="error-contactNo" style="color: red;"></label>
												</div>
											</div>
										</div>
										<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_right control-label">Customer
												Name<span class='require'>*</span>
											</label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:input class="form-control" id="personName"
														placeholder="Customer Name" path="guest.personName"
														value="${clientModel.guest.personName}" />
													<label id="error-customer" style="color: red;"></label>
												</div>
											</div>
										</div>
										<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_right control-label">Email
											</label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:input class="form-control" placeholder="Email"
														id="emailId" onchange="ShowAlert();"
														value="${clientModel.guest.emailId}" path="guest.emailId" />
													<label id="error-emailId" style="color: red;"></label>
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-12">
										<h5>
											<b>Travel Information</b>
										</h5>
										<div class="form-group col-md-12 flot_for_hour_left">
											<label class="col-md-4 control-label ">Pickup Date &
												Time<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-group">
                                                    <form:input class="form-control"
														path="pickUpDateTime" id="pickUpDateTime1"  value="${pickUpDateTime}" readonly="true" />
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>
										</div>
										<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_right control-label">Pickup
												Location <span class='require'>*</span>
											</label>
											<div class="col-md-8 ">
												<div class="input-icon right">
													<form:input class="form-control"
														value="${clientModel.pickUpLocation}"
														placeholder="Enter Pick Up Location" path="pickUpLocation" />
													<form:input class="form-control" type="hidden" value="${clientModel.pickUpLatLong}" path="pickUpLatLong" />
												</div>
											</div>
										</div>
										<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_right control-label">Landmark</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control readOnly"
														value="${clientModel.landmark}" placeholder="Enter Landmark" path="landmark" />
												</div>
											</div>
										</div>
										<div class="form-group col-md-12 flot_for_hour_left">
											<label for="inputAddress"
												class="flot_for_hour_right col-md-4 control-label">City<span
												class='require'>*</span></label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:select
														class="RateContract-change form-control select2 readOnly"
														id="cityId" path="city.id" itemValue="city.id"
														style="width: 100%;">
														<c:if test="${clientModel.id eq null}">
														    <form:option value="0">Select Name Of City</form:option>
														</c:if>
														<c:forEach var="city" items="${CityDTL}">
															<c:if test="${(clientModel.id != null) && (city.id eq clientModel.city.id)}">
																<form:option value="${city.id}"
																	selected="${city.id eq clientModel.city.id ? 'selected' : ''}">${city.cityName}</form:option>
															</c:if>
															<c:if test="${clientModel.id eq null}">
																<form:option value="${city.id}">${city.cityName}</form:option>
															</c:if>
														</c:forEach>
													</form:select>


													<label id="error-city" style="color: red;"></label>
												</div>
											</div>
										</div>
										<div class="form-group col-md-12 flot_for_hour_left">
											<label class="col-md-4 control-label">Drop Date &
												Time<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-group ">
													<form:input class="form-control datetimepicker"
														path="dropDateTime" id="dropDateTime1" value="${dropDateTime}" readonly="true"/>
													<span class="input-group-addon "><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>
										</div>
										<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-4 flot_for_hour_right control-label">Drop
												Location<span class='require'>*</span>
											</label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:input class="form-control"
														placeholder="Enter Drop Location" path="drop_location"
														value="${clientModel.drop_location}"/>
													<form:input class="form-control" type="hidden" value="${clientModel.dropLatLong}" path="dropLatLong" />
												</div>
											</div>
										</div>
										<div class="form-group col-md-12 flot_for_hour_left">
											<label for="inputAddress"
												class="flot_for_hour_right col-md-4 control-label">Payment Mode<span
												class='require'>*</span></label>
											<div class="col-md-8">
												<div class="input-icon right">
													<form:select
														class="RateContract-change form-control select2 "
														 path="paymentMode" itemValue="paymentMode"
														style="width: 100%;">
														<c:if test="${clientModel.id eq null}">
														    <form:option value="0">Select Mode</form:option>

														</c:if>
																    <form:option value="Cash"
																	selected="${clientModel.paymentMode eq 'Cash' ? 'selected' : ''}">Cash</form:option>
																	<form:option value="Cheque"
																	selected="${clientModel.paymentMode eq 'Cheque'? 'selected' : ''}">Cheque</form:option>
																	<form:option value="Bank"
																	selected="${clientModel.paymentMode eq 'Bank' ? 'selected' : ''}">Bank Transfer</form:option>
																	<form:option value="Online"
																	selected="${clientModel.paymentMode eq 'Online' ? 'selected' : ''}">Online</form:option>
													</form:select>
													<label id="error-paymentMode" style="color: red;"></label>
												</div>
											</div>
										</div>
										<%-- <div class="form-group col-md-12 flot_for_hour_left ">
											<label class="col-md-4 control-label">Pincode<span
												class='require'>*</span></label>
											<div class="col-md-8  media_bottom_pad ">
												<form:input placeholder="Pin/Zip Code" value="${clientModel.pincode}"
													class="form-control readOnly" path="pincode" maxlength="6"
													onkeypress="return onlyNos(event,this);" />
											</div>
										</div> --%>
									</div>
								</div>

								<div class="col-md-6">
									<h5>
										<b>Account Information</b>
									</h5>
									<div class="form-group col-md-12 flot_for_hour_left">
										<label for="inputAddress"
											class="flot_for_hour_right col-md-4 control-label">Invoice
											Category<span class='require'>*</span>
										</label>
										<div class="col-md-8">
											<div class="input-icon right">
												<form:select
													class="invoice-change form-control select2 readOnly"
													path="invoicePackage.id" itemValue="invoicePackage.id"
													style="width: 100%;"
													onchange="getInvoiceCategories();getTax();"
													id="invoiceCategories">
													<c:if test= "${clientModel.id eq null}">
													    <form:option value="0">Select Invoice Category</form:option>
													</c:if>
													<c:forEach var="invoice" items="${InvoiceDTL}">
														<c:if test="${(clientModel.id != null) && (invoice.id eq clientModel.invoicePackage.id)}">
															<form:option value="${invoice.id}"
																selected="${invoice.id eq clientModel.invoicePackage.id ? 'selected' : ''}">${invoice.categoryname}</form:option>
														</c:if>
														<c:if test="${clientModel.id eq null}">
															<form:option value="${invoice.id}">${invoice.categoryname}</form:option>
														</c:if>
													</c:forEach>
												</form:select>
												<label id="error-invoice" style="color: red;"></label>
											</div>
										</div>
									</div>

									<div id="taxes_applied"  style="display: none;" class="form-group col-md-12 flot_for_hour_left ">
									<label for="inputAddress"
											class="col-md-4 flot_for_hour_right control-label">Applied
											Taxes
										</label>
									<div id="ID_listOfTopics" class="col-md-8"></div>
						            </div>
									<div id="comapanyname"
										class="form-group col-md-12 flot_for_hour_left ">
										<label for="inputAddress"
											class="col-md-4 flot_for_hour_right control-label">Company
											Name<span class='require'>*</span>
										</label>
										<div class="col-md-8 ">
											<div class="input-icon right">
												<form:input class="form-control" placeholder="Company Name"
													id="company" value="${clientModel.companyMaster.comapanyName}" path="companyMaster.comapanyName" />
												<label id="error-company" style="color: red;"></label>
											</div>
										</div>
									</div>
									<div id="rateOfContract"
										class="form-group col-md-12 flot_for_hour_left">
										<label for="inputAddress"
											class="flot_for_hour_right col-md-4 control-label">Rate
											Contract<span class='require'>*</span>
										</label>
										<div class="col-md-8">
											<div class="input-icon right">
												<form:select
													class="RateContract-change form-control select2 readOnly"
													path="rateOfContract.id" itemValue="rateOfContract.id"
													style="width: 100%;" onchange="changeRateOfContract();"
													id="rateOfContractId">
													<c:if test= "${clientModel.id eq null}">
													    <form:option value="0">Select Rate Contract</form:option>
													</c:if>
													<c:forEach var="ratecontract" items="${RateContract}">
														<c:if test="${(clientModel.id != null) && (ratecontract.id eq clientModel.rateOfContract.id)}">
															<form:option value="${ratecontract.id}"
																selected="${ratecontract.id eq clientModel.rateOfContract.id ? 'selected' : '' }">${ratecontract.rateOfContract}</form:option>
														</c:if>
														<c:if test="${clientModel.id eq null}">
															<form:option value="${ratecontract.id}">${ratecontract.rateOfContract}</form:option>
														</c:if>
													</c:forEach>
												</form:select>
                                                <label id="error-rateContractSelection" style="color: red;"></label>
												<label id="error-contract" style="color: red;"></label>
											</div>
										</div>
									</div>
									<div id="minimumKm"
										class="form-group col-md-12 flot_for_hour_left ">
										<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-6 flot_for_hour_right control-label">Minimum
												Km<span class='require'>*</span>
											</label>
											<div class="col-md-1 flot_for_hour_left media_bottom_pad"></div>
											<div class="col-md-5 flot_for_hour_left media_bottom_pad">
												<form:input placeholder="KM" class="form-control readOnly"
													path="minkms" maxlength="10" value="${clientModel.minkms}" onkeypress="return onlyNos(event,this);" />
												<label id="error-minkms" style="color: red;"></label>
											</div>
											</div>
											<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-6 flot_for_hour_right control-label">Minimum
												Rate<span class='require'>*</span>
											</label>
											<div class="col-md-1 flot_for_hour_left media_bottom_pad"></div>
											<div class="col-md-5 flot_for_hour_left media_bottom_pad">
												<form:input placeholder="Rate" class="form-control readOnly"
													path="minrate" maxlength="10" value="${clientModel.minrate}" onkeypress="return onlyNos(event,this);" />
												<label id="error-minrate" style="color: red;"></label>
											</div>
											</div>
											<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-6 flot_for_hour_right control-label">Grace Hours
												<span class='require'>*</span>
											</label>
											<div class="col-md-1 flot_for_hour_left media_bottom_pad"></div>
											<div class="col-md-5 flot_for_hour_left media_bottom_pad">
												<form:input placeholder="Grace Hours" class="form-control readOnly"
													path="graceHours" maxlength="10" value="${clientModel.graceHours}" onkeypress="return onlyNos(event,this);" />
												<label id="error-graceHours" style="color: red;"></label>
											</div>
											</div>
											<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-6 flot_for_hour_right control-label">Driver
												Allownce<span class='require'>*</span>
											</label>
											<div class="col-md-1 flot_for_hour_left media_bottom_pad"></div>
											<div class="col-md-5 flot_for_hour_left media_bottom_pad">
												<form:input placeholder="Driver Allownce" class="form-control readOnly"
													path="driverAllownce" maxlength="10" value="${clientModel.driverAllownce}" onkeypress="return onlyNos(event,this);" />
												<label id="error-driverAllownce" style="color: red;"></label>
											</div>
											</div>
										</div>
									<div id="HoursAndKM"
										class="form-group col-md-12 flot_for_hour_left ">
										<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-6 flot_for_hour_right control-label">Hours
												<span class='require'>*</span>
											</label>
											<div class="col-md-1 flot_for_hour_left media_bottom_pad"></div>
											<div class="col-md-5 flot_for_hour_left media_bottom_pad">
												<form:input placeholder="Hours" class="form-control readOnly"
													path="hnkHours" value="${clientModel.hnkHours}" maxlength="10" onkeypress="return onlyNos(event,this);"/>
												<label id="error-Hours" style="color: red;"></label>
											</div>
											</div>
											<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-6 flot_for_hour_right control-label">Kms
												<span class='require'>*</span>
											</label>
											<div class="col-md-1 flot_for_hour_left media_bottom_pad"></div>
											<div class="col-md-5 flot_for_hour_left media_bottom_pad">
												<form:input placeholder="KM" class="form-control readOnly"  maxlength="10" path="hnkKms" value="${clientModel.hnkKms}"
													onkeypress="return onlyNos(event,this);" />
												<label id="error-kms" style="color: red;"></label>
											</div>
                                             </div>
											<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-6 flot_for_hour_right control-label">Amount
												<span class='require'>*</span>
											</label>
											<div class="col-md-1 flot_for_hour_left media_bottom_pad"></div>
											<div class="col-md-5 flot_for_hour_left media_bottom_pad">
												<form:input placeholder="Amount" class="form-control readOnly" maxlength="10"
													path="hnkAmount" onkeypress="return onlyNos(event,this);" value="${clientModel.hnkAmount}" />
												<label id="error-amount" style="color: red;"></label>
											</div>
											</div>
										<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-6 flot_for_hour_right control-label">Additional
												Hours Charges<span class='require'>*</span>
											</label>
											<div class="col-md-1 flot_for_hour_left media_bottom_pad"></div>
											<div class="col-md-5 flot_for_hour_left media_bottom_pad">
												<form:input placeholder="Additional Hours/Rs"
													class="form-control readOnly" path="additionalHours" maxlength="10"
													onkeypress="return onlyNos(event,this);" value="${clientModel.additionalHours}" />
												<label id="error-addhours" style="color: red;"></label>
											</div>
											</div>
											<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-6 flot_for_hour_right control-label">Additional
												Km Charges<span class='require'>*</span>
											</label>
											<div class="col-md-1 flot_for_hour_left media_bottom_pad"></div>
											<div class="col-md-5 flot_for_hour_left media_bottom_pad">
												<form:input placeholder="Additional KM/Rs" class="form-control readOnly"
													path="additionalKms" maxlength="10" value="${clientModel.additionalKms}"
													onkeypress="return onlyNos(event,this);" />
												<label id="error-addkms" style="color: red;"></label>
											</div>
											</div>
									</div>
									<div id="SourceAndDestination"
										class="form-group col-md-12 flot_for_hour_left ">
										<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-6 flot_for_hour_right control-label">Source
												Place<span class='require'>*</span>
											</label>
											<div class="col-md-1 flot_for_hour_left media_bottom_pad"></div>
											<div class="col-md-5 flot_for_hour_left media_bottom_pad">
												<form:input placeholder="Source" class="form-control readOnly"
													value="${clientModel.sourcePlace}" path="sourcePlace" />
												<label id="error-source" style="color: red;"></label>
											</div>
											</div>
											<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-6 flot_for_hour_right control-label">Destination
												Place<span class='require'>*</span>
											</label>
											<div class="col-md-1 flot_for_hour_left media_bottom_pad"></div>
											<div class="col-md-5 flot_for_hour_left media_bottom_pad">
												<form:input placeholder="Destination" class="form-control readOnly"
												value="${clientModel.destinationPlace}"	path="destinationPlace" />
												<label id="error-destination" style="color: red;"></label>
											</div>
											</div>
											<div class="form-group col-md-12 flot_for_hour_left ">
											<label for="inputAddress"
												class="col-md-6 flot_for_hour_right control-label">Price
											<span class='require'>*</span>
											</label>
											<div class="col-md-1 flot_for_hour_left media_bottom_pad"></div>
											<div class="col-md-5 flot_for_hour_left media_bottom_pad">
												<form:input placeholder="Amount" class="form-control readOnly"
												value="${clientModel.sndPrice}" maxlength="10" path="sndPrice" onkeypress="return onlyNos(event,this);" />
												<label id="error-price" style="color: red;"></label>
											</div>
										</div>
									</div>
									<div id="debitCheck"
										class="form-group col-md-12 flot_for_hour_left">
										<label for="inputAddress"
											class="flot_for_hour_right col-md-4 control-label">Debit</label>

										<div class="col-md-8">
											<form:checkbox class="debitCheckBox" onclick="return false;"
												path="debit" />
										</div>
									</div>
									<div class="form-group col-md-12 flot_for_hour_left">
										<label for="inputAddress"
											class="flot_for_hour_right col-md-4 control-label">Remarks
										</label>
										<div class="col-md-8">
											<textarea class="form-control  readOnly" maxlength="254" name="remarks" id="remarks"
												placeholder="Remarks">${clientModel.remarks}</textarea>
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<hr>
								</div>
								<div class="col-md-12">
									<button class="btn orange_bg" type="submit">${empty clientModel.id  || clientModel.id eq '0' ? 'Book Now' : 'Update Now'}</button>
									<a href="<%=UrlConstants.SEARCH_CAR%>" class="btn grey_bg">Cancel</a>
								</div>
								<div class="col-md-12">
									<br>
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
