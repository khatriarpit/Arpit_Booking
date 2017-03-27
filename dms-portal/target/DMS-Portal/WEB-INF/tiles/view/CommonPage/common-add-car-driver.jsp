<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<input type="hidden" name="tenantPackageID" id="tenantPackageID" value="${tenantPackageID}">
<input type="hidden" name="carlst" id="carlist">
<input type="hidden" name="driverlst" id="driverlist">
<input type="hidden" name="carListRowWithComma" id="carListRowWithComma">
<input type="hidden" name="driverListRowWithComma" id="driverListRowWithComma">
<hr>
<h4>
    <b>Register Your Car No's. 
    <span id="car-error-msg" class="error control-label" style="display: none;">(You Entered Same Car No. !!!)</span></b>
</h4>
<c:if test="${carList eq null}">
	<div class="input_fields_wrap">
	    <div id="remove-car-0" class="form-group col-md-12">
	        <label class="col-md-2 control-label">Car Number</label>
	        <div class="col-md-6">
	            <div class="col-md-2 flot_for_hour_left media_bottom_pad">
	                <input type="text" data-car-div-id="0" placeholder="GJ" data-raw-static-id="1" style="text-transform: uppercase;" maxlength="2" class="cartext1 alpha-only form-control">
	            </div>
	            <div class="col-md-2 flot_for_hour_left media_bottom_pad">
	                <input type="text" data-car-div-id="0" placeholder="111" data-raw-static-id="2" onkeypress="return onlyNos(event, this);" maxlength="3" class="cartext2 form-control">
	            </div>
	            <div class="col-md-2 flot_for_hour_left media_bottom_pad">
	                <input type="text" data-car-div-id="0" placeholder="ABC" data-raw-static-id="3" style="text-transform: uppercase;" maxlength="3" class="cartext3 form-control alpha-only">
	            </div>
	            <div class="col-md-4 flot_for_hour_left media_bottom_pad">
	                <input type="text" data-car-div-id="0" placeholder="1234" data-raw-static-id="4" onkeypress="return onlyNos(event, this);" maxlength="4" class="cartext4 form-control">
	            </div>
	        </div>
	        <label id="car-error-msg-0" class="error control-label" style="display: none;margin-left: 8.4%;">Car No. Not Valid !!!</label>
	    </div>
	</div>
</c:if>
<c:if test="${carList != null}">
	<div class="input_fields_wrap" style="height: 80px;">
		<c:forTokens var="car" items="${carList}" delims="," varStatus="loop">
		    <div id="remove-car-${loop.index}" class="form-group col-md-12">
		        <label class="col-md-2 control-label">Car Number</label>
		        <div class="col-md-6 media-right-pad">
		            <c:forTokens var="carNo" items="${car}" delims=" " varStatus="carNoloop">
			            <div class="col-md-2 flot_for_hour_left media_bottom_pad">
			                <input type="text" data-car-div-id="${loop.index}" <c:if test="${carNoloop.count eq 1}"> placeholder="GJ" </c:if> <c:if test="${carNoloop.count eq 2}"> placeholder="111" </c:if> <c:if test="${carNoloop.count eq 3}"> placeholder="ABC" </c:if> data-raw-static-id="${carNoloop.count}"  <c:if test="${carNoloop.count eq 2 || carNoloop.count eq 4}">onkeypress="return onlyNos(event, this);"</c:if> <c:if test="${carNoloop.count != 1 || carNoloop.count != 4}">maxlength="3"</c:if><c:if test="${carNoloop.count eq 1}">maxlength="2"</c:if><c:if test="${carNoloop.count eq 4}">maxlength="4"</c:if> <c:if test="${carNoloop.count eq 4}">maxlength="4" placeholder="1234"</c:if> value="${carNo}" style="text-transform: uppercase;" class="cartext${carNoloop.count} form-control ${carNoloop.count eq 1 || carNoloop.count eq 3 ? 'alpha-only' : ''}">
			            </div>
					</c:forTokens>
		        </div>
		        <a style="margin-top: 10px;" class="pointer col-md-1 glyphicon glyphicon-trash remove_field" data-car-div-id="${loop.index}"></a>
	            <label id="car-error-msg-${loop.index}" class="error control-label" style="display: none;${loop.index eq 0 ? 'margin-left: 8.4%;' : ''}">Car No. Not Valid !!!</label>
	       </div>
       </c:forTokens>
   	</div>
</c:if>
<div class="form-group">
    <button class="add_field_button btn grey_bg car-btn-left-align" type="button">
        Add More Cars <i class="fa fa-plus"></i>
    </button>
    <hr>
</div>
<h4>
    <b>Register Your Driver Licence No's.
    <span id="driver-error-msg" class="error control-label" style="display: none;">(You Entered Same Driver License No. !!!)</span></b>
</h4>
<c:if test="${driverList eq null}">
	<div class="input_fields_wrap_driver">
	    <div id="remove-driver-0" class="form-group col-md-12">
	        <label class="col-md-2 control-label flot_for_hour_left flot_for_hour_right media_float_left_none">Driver Licence Number</label>
	        <div class="col-md-6 media-right-pad">
	           <input type="text" class="form-control drivertext" maxlength="17" placeholder="GJ01/123456/78" data-driver-div-id="0" />
	        </div>
	        <label id="driver-error-msg-0" class="error control-label" style="display: none;margin-left: 8.4%;">Driver License No. Not Valid !!!</label>
	    </div>
	</div>
</c:if>
<c:if test="${driverList != null}">
	<div class="input_fields_wrap_driver">
		<c:forTokens var="driver" items="${driverList}" delims="," varStatus="loop">
		    <div id="remove-driver-${loop.index}" class="form-group col-md-12">
		        <label class="col-md-2 control-label flot_for_hour_left flot_for_hour_right media_float_left_none">Driver Licence Number</label>
		        <div class="col-md-6 media-right-pad">
		           <input type="text" value="${driver}" maxlength="17" class="form-control drivertext" placeholder="GJ01/123456/78" data-driver-div-id="${loop.index}" />
		        </div>
		        <a style="margin-top: 10px;" class="pointer col-md-1 glyphicon glyphicon-trash remove_field" data-driver-div-id="${loop.index}"></a>
		        <label id="driver-error-msg-${loop.index}" class="error control-label" style="display: none;${loop.index eq 0 ? 'margin-left: 8.4%;' : ''}">Driver License No. Not Valid !!!</label>
		    </div>
		</c:forTokens>
	</div>
</c:if>
<div class="form-group">
    <button class="add_field_button_driver btn grey_bg car-btn-left-align" type="button">
        Add More Driver&nbsp;<i class="fa fa-plus"></i>
    </button>
</div>
<hr>
<c:if test="${empty msg}">
	<script type="text/javascript">
		var car_max_fields = parseInt("${carLimit}"); // car maximum input boxes allowed
		var driver_max_fields = parseInt("${driverLimit}"); // driver maximum input boxes allowed
		var wrapper = $(".input_fields_wrap");
		var wrapperdriver = $(".input_fields_wrap_driver");
		var x = parseInt("${carStartRow}"); //initlal text box count
		var y = parseInt("${driverStartRow}");
		var carErrorMsg = "Car Number Not Valid !!!"
		var driverErrorMsg = "Driver License Number Not Valid !!!"
		var validation = true;
		var carRowId = parseInt("${carStartRow}"); //initlal text box count
		var driverRowId = parseInt("${driverStartRow}");
		$(document).ready(function() {
			// add dynamic row for car no
			$(".add_field_button").click(function() { //on add input button click
				if (car_max_fields >= (x+1)) { //max input box allowed
					if((x+1) > 4) {
						$(".input_fields_wrap").addClass("overflow-cars");
					} else {
						$(".input_fields_wrap").removeClass("overflow-cars");
					}
					$(".add_field_button").removeAttr("disabled", "");
					var tag = '';
					tag = '<div id="remove-car-'+(carRowId)+'" class="form-group col-md-12">'+
								'<label class="col-md-2 control-label">Car Number</label>'+
								'<div class="col-md-6 media-right-pad">'+
									'<div class="col-md-2 flot_for_hour_left media_bottom_pad">'+
										'<input type="text" placeholder="GJ" data-raw-static-id="1" style="text-transform: uppercase;" maxlength="2" class="cartext1 form-control alpha-only" data-car-div-id="'+(carRowId)+'">'+
									'</div>'+
									'<div class="col-md-2 flot_for_hour_left media_bottom_pad">'+
										'<input type="text" placeholder="111" data-raw-static-id="2" onkeypress="return onlyNos(event, this);" maxlength="2" class="cartext2 form-control" data-car-div-id="'+(carRowId)+'">'+
									'</div>'+
									'<div class="col-md-2 flot_for_hour_left media_bottom_pad">'+
										'<input type="text" placeholder="ABC" data-raw-static-id="3" style="text-transform: uppercase;" maxlength="2" class="cartext3 form-control alpha-only" data-car-div-id="'+(carRowId)+'">'+
									'</div>'+
									'<div class="col-md-4 flot_for_hour_left media_bottom_pad">'+
										'<input type="text" placeholder="1234" data-raw-static-id="4" onkeypress="return onlyNos(event, this);" maxlength="4" class="cartext4 form-control" data-car-div-id="'+(carRowId)+'">'+
									'</div>'+
								'</div>'+
								'<a style="margin-top: 10px;" class="pointer col-md-1 glyphicon glyphicon-trash remove_field" data-car-div-id="'+(carRowId)+'"></a>'+
								'<label id="car-error-msg-'+(carRowId)+'" class="error control-label" style="display: none;">Car No Not Valid !!!</label>'+
							'</div>'+
						'</div>';
					$(wrapper).append(tag);
					validation = false;
				}
				if (x === car_max_fields) {
					$(".add_field_button").attr("disabled", "");
				} else {
					x++; //text box increment
					carRowId++;
				}
			});

			// add dynamic row for Driver License No
			$(".add_field_button_driver").click(function() { //on add input button click
				if (driver_max_fields >= (y+1)) { //max input box allowed
					if((y+1)>4) {
						$(".input_fields_wrap_driver").addClass("overflow-cars");
					} else {
						$(".input_fields_wrap_driver").removeClass("overflow-cars");
					}
					var tag = '<div id="remove-driver-'+(driverRowId)+'" class="form-group col-md-12">'+
									'<label class="col-md-2 control-label flot_for_hour_left flot_for_hour_right media_float_left_none ">Driver Licence Number</label>'+
	                                '<div class="col-md-6 media-right-pad ">'+
	                                   '<input type="text" maxlength="17" class="form-control drivertext" placeholder="GJ01/123456/78" data-driver-div-id="'+(driverRowId)+'" />'+
	                                '</div>'+
	                                '<a style="margin-top: 10px;" class="pointer col-md-1 glyphicon glyphicon-trash remove_field" data-driver-div-id="'+(driverRowId)+'"></a>'+
	                                '<label id="driver-error-msg-'+(driverRowId)+'" class="error control-label" style="display: none;">Driver License Number Not Valid !!!</label>'+
                                '</div>';
					$(wrapperdriver).append(tag);
					validation = false;
				}
				if (y === driver_max_fields) {
					$(".add_field_button_driver").attr("disabled", "");
				} else {
					y++; //text box increment
					driverRowId++;
				}
			});

			/* $("body").delegate('.drivertext', 'input', function() {
				var driverCount = $(this).val();
				var driverErrorDivId = $(this).data("driverDivId");
					var regexp = /[A-Z]{2}[0-9]{2}(-|\s|\/)(\d|(-|\s|\/))+$/;
					$(this).val(driverCount.toUpperCase());
			  	  	if($(this).val().match(regexp)){
			  	  		//alert($(this).val()+'true');
			  	  	$("#driver-error-msg-"+driverErrorDivId).hide();
			  	  alert('true');
			  	  	}
			  	  	else{
			  	  	$("#driver-error-msg-"+driverErrorDivId).show();
			  	  	alert('false');
			  	  	}
			}); */

			$("body").delegate('.drivertext', 'change', function() {
				$("#driver-error-msg").hide();
				var driverErrorDivId = $(this).data("driverDivId");
				$(this).val($(this).val().toUpperCase());
				var driver = $(this).val();
				$("#driver-error-msg-"+driverErrorDivId).hide();
				if(driverDivVal(driver)) {
                	$("#driver-error-msg-"+driverErrorDivId).hide();
                	validation = true;
                } else {
                	$("#driver-error-msg-"+driverErrorDivId).html(driverErrorMsg);
                	$("#driver-error-msg-"+driverErrorDivId).show();
                	validation = false;
                }
			});

			$("body").delegate(".cartext1, .cartext2, .cartext3, .cartext4", "change", function() {
				$("#car-error-msg").hide();
				var carNumber = $(this).val();
				var carErrorDivId = $(this).data("carDivId");
				var rawStaticId = $(this).data("rawStaticId");
				if(rawStaticId === 1 || rawStaticId == 3) {
					carNumber = carNumber.toUpperCase();
				}
				var errorCar = 0;
				$("#car-error-msg-"+carErrorDivId).hide();
				if(rawStaticId === 1 || rawStaticId === 4) {
					if(rawStaticId === 1) {
						errorCar = 2;
					} else if(rawStaticId === 4) {
						errorCar = 4;
					}
					carFormatValTwoAndFourAlpha(carNumber, errorCar);
				} else {
					carFormatValThreeAlpha(carNumber);
				}
				if(carNumber != null && validation) {
					$("#car-error-msg-"+carErrorDivId).hide();
				} else {
					$("#car-error-msg-"+carErrorDivId).html(carErrorMsg);
                	$("#car-error-msg-"+carErrorDivId).show();
				}
			});
			
			function carFormatValThreeAlpha(carNumber){
				if (carNumber.length > 0 && carNumber.length < 4) {
					validation = true;
					return true;
				} else {
					validation = false;
					return false;
				}
			}

			// Remove Car No Div
			$("body").delegate(".remove_field", "click", function() { //user click on remove text
				var carDivID = $(this).data("carDivId");
				var driverDivID = $(this).data("driverDivId");
				if (typeof carDivID != "undefined") {
					$("#remove-car-" + carDivID).remove();
					$(".add_field_button").removeAttr("disabled","");
					x--;
					$.each($('.remove_field:last-child'), function(i, item) {
						var carDivIdRemoveLast = $(this).data("carDivId");
						if (typeof carDivID != "undefined") {
							carRowId = (carDivIdRemoveLast + 1);
						}
		            });
				} else {
					$("#remove-driver-"+ driverDivID).remove();
					$(".add_field_button_driver").removeAttr("disabled","");
					y--;
					$.each($('.remove_field:last-child'), function(i, item) {
						var driverDivIdRemoveLast = $(this).data("driverDivId");
						if (typeof carDivID != "undefined") {
							driverRowId = (driverDivIdRemoveLast + 1);
						}
		            });
				}
			});
		});

		function driverDivVal(number){
	        return /[A-Z]{2}[0-9]{2}(-|\s|\/)(\d|(-|\s|\/))+$/.test(number);
	    }

		function carDiv(indexElement){
			var carVal = '';
			$.each($('.cartext'+indexElement), function(i, item) {
				var carErrorDivId = $(this).data("carDivId");
				if ($(this).val() === '') {
					$("#car-error-msg-"+carErrorDivId).html(carErrorMsg);
                    $("#car-error-msg-"+carErrorDivId).show();
                    validation = false;
                } else {
                    $("#car-error-msg-"+carErrorDivId).hide();
                    validation = true;
                    if (carVal != '') {
                    	carVal += ",";
                    }
                    carVal += $(this).val();
                }
            });
			return carVal;
		}

		function driverDiv(){
			var driverVal = '';
			$.each($('.drivertext'), function(i, item) {
				var driverErrorDivId = $(this).data("driverDivId");
				if ($(this).val() === '') {
					$("#driver-error-msg-"+driverErrorDivId).html(driverErrorMsg);
                    $("#driver-error-msg-"+driverErrorDivId).show();
                    validation = false;
                } else {
                    $("#driver-error-msg-"+driverErrorDivId).hide();
                    validation = true;
                    if (driverVal != '') {
                    	driverVal += ",";
                    }
                    driverVal += $(this).val();
                }
            });
			equalNoExist((y+1), driverVal, 'driver');
			return driverVal;
		}

		function carFormatValTwoAndFourAlpha(carNumber, lengthVal){
			if (carNumber.length === lengthVal) {
				validation = true;
				return true;
			} else {
				validation = false;
				return false;
			}
	    }

		function carDriverAdd(){
            if(validation) {
                var carListVal = '';
                for(var i = 0; i < x; i++) {
                    if (carListVal != '') {
                        carListVal += ",";
                    }
                    var array1 = carDiv(1).toUpperCase().split(",");
                    var array2 = carDiv(2).split(",");
                    var array3 = carDiv(3).toUpperCase().split(",");
                    var array4 = carDiv(4).split(",");
                    carListVal += array1[i];
                    carListVal += " " + array2[i];
                    carListVal += " " + array3[i];
                    carListVal += " " + array4[i];
                }
                equalNoExist((x+1), carListVal, 'car');
                if(validation) {
					equalNoExist((y+1), driverDiv(), 'driver');
	                if(validation) {
	                	var carRowCountWithComma = '0';
	                	var driverRowCountWithComma = '0';
	                	$.each($('.remove_field'), function(i, item) {
							var carDivIdRemoveLast = $(this).data("carDivId");
							var driverDivIdRemoveLast = $(this).data("driverDivId");
							if (typeof carDivIdRemoveLast != "undefined") {
								if(carRowCountWithComma != '') {
									carRowCountWithComma += ",";
								}
								carRowCountWithComma += carDivIdRemoveLast;
							}
							if (typeof driverDivIdRemoveLast != "undefined") {
								if(driverRowCountWithComma != '') {
									driverRowCountWithComma += ",";
								}
								driverRowCountWithComma += driverDivIdRemoveLast;
							}
			            });
	                	$('#carListRowWithComma').val(carRowCountWithComma);
		                $('#driverListRowWithComma').val(driverRowCountWithComma);
		                $('#carlist').val(carListVal);
		                $('#driverlist').val(driverDiv());
	                } else {
						validation = false;
					}
                } else {
					validation = false;
				}
            } else {
				validation = false;
			}
		}

		function equalNoExist(xy, val, type){
			for(var i = 0; i < xy; i++) {
            	var array = val.split(",");
            	if(validation) {
                	for(var y = (i + 1); y < xy; y++) {
                		var array1 = val.split(",");
                		if(array[i] === array1[y]) {
                			if(type === "car") {
                				$("#car-error-msg").show();
                			} else {
                				$("#driver-error-msg").show();
                			}
                			validation = false;
                			break;
                		} else {
                			if(type === "car") {
                				$("#car-error-msg").hide();
                			} else {
                				$("#driver-error-msg").hide();
                			}
                		}
            		}
            	} else {
            		break;
            	}
            }
		}

		function carDriverError(data){
			var carError = data["car-error"];
   			if(carError != '') {
   				if (typeof carError != "undefined") {
	   				var array = carError.split(",");
	   				for(var i = 0;i<array.length;i++) {
	   					$("#car-error-msg-"+array[i]).html("Car No. is already used !!!");
	   					$("#car-error-msg-"+array[i]).show();
	   				}
   				}
   			}
   			var driverError = data["driver-error"];
   			if(driverError != '') {
   				if (typeof driverError != "undefined") {
	   				var array = driverError.split(",");
	   				for(var i = 0;i<array.length;i++) {
	   					$("#driver-error-msg-"+array[i]).html("Driver License No. is already used !!!");
	   					$("#driver-error-msg-"+array[i]).show();
	   				}
   				}
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

		$("body").delegate(".alpha-only", "input", function(){
	  	  	var regexp = /[^a-zA-Z]/g;
	  	  	if($(this).val().match(regexp)){
	  	    	$(this).val( $(this).val().replace(regexp,'') );
	  	  	}
	  	});
	</script>
</c:if>