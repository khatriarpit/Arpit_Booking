<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form:input path="tanentID" type="hidden" value="${tanentID}" />
<div class="form-group">
	<label class="col-md-2 control-label">Users<span
		class='require'>*</span></label>
	<div class="col-md-6 media-right-pad">
		<div class="col-md-4 flot_for_hour_left  media_bottom_pad">
			<form:input class="form-control" maxlength="5" path="users"
				value="${packages.users}" onchange="mulusers();"
				placeholder="Add No.Of Users"
				onkeypress="return onlyNos(event,this);"></form:input>
		</div>
		<div
			class="col-md-4 input-icon media_float_left left number media_float_left media_bottom_pad">
			<i class="fa fa-inr"></i>
			<form:input class="form-control" maxlength="7" path="userrate"
				placeholder="Add Rate Per User" onchange="mulusers();"
				value="${packages.userrate}" />
		</div>
		<div
			class="col-md-4 input-icon media_float_right_none number left media_float_left flot_for_hour_right media_bottom_pad">
			<i class="fa fa-inr"></i> <input type="text" class="form-control" id="usersamount" readonly="true" placeholder="0000"
				value="${amount2}" />
		</div>
	</div>
</div>
<div class="form-group">
	<label class="col-md-2 control-label">Cars<span class='require'>*</span></label>
	<div class="col-md-6 media-right-pad">
		<div class="col-md-4 flot_for_hour_left  media_bottom_pad">
			<form:input class="form-control" maxlength="5" path="cars"
				value="${packages.cars}" onchange="mulcars();"
				onkeypress="return onlyNos(event,this);"
				placeholder="Add No.Of Cars"></form:input>
		</div>
		<div
			class="col-md-4 input-icon media_float_left left number media_float_left media_bottom_pad">
			<i class="fa fa-inr"></i>
			<form:input type="text" class="form-control" maxlength="7"
				path="carrate" onchange="mulcars();" placeholder="Add Rate Per car"
				value="${packages.carrate }" />
		</div>
		<div
			class="col-md-4 input-icon media_float_right_none number left media_float_left flot_for_hour_right media_bottom_pad">
			<i class="fa fa-inr"></i> <input type="text" class="form-control" id="caramount" readonly="readonly" placeholder="0000"
				value="${amount1}" />
		</div>
	</div>
</div>
<div class="form-group">
	<label class="col-md-2 control-label">Drivers<span
		class='require'>*</span></label>
	<div class="col-md-6 media-right-pad">
		<div class="col-md-4 flot_for_hour_left  media_bottom_pad">
			<form:input class="form-control" maxlength="5" path="drivers"
				value="${packages.drivers}" onchange="muldrivers();"
				onkeypress="return onlyNos(event,this);"
				placeholder="Add No.of Drivers"></form:input>
		</div>
		<div
			class="col-md-4 input-icon left media_float_left number media_bottom_pad">
			<i class="fa fa-inr"></i>
			<form:input type="text" class="form-control" maxlength="7"
				path="driverrate" name="driverrate" onchange="muldrivers();"
				placeholder="Add Rate Per Driver" value="${packages.driverrate }" />
		</div>
		<div
			class="col-md-4 media_float_right_none input-icon left media_float_left number flot_for_hour_right media_bottom_pad">
			<i class="fa fa-inr"></i> <input type="text" class="form-control" id="driversamount" readonly="true" value="${amount3}"
				placeholder="0000" />
		</div>
	</div>
</div>
<div class="form-group">
	<label for="inputAddress"
		class="col-md-2 control-label col-md-offset-4">Additional
		Charges</label>
	<div class="col-md-2 side-pad-10">
		<div class="input-icon left">
			<i class="fa fa-inr"></i>
			<form:input class="form-control" path="additionalCharges"
				maxlength="5" onchange="sumofamount();"
				value="${packages.additionalCharges}" placeholder="Add Amount" />
		</div>
	</div>
</div>
<div class="form-group">
	<label for="inputAddress"
		class="col-md-2 control-label col-md-offset-4"> Total </label>
	<div class="col-md-2 side-pad-10">
		<div class="input-icon left">
			<i class="fa fa-inr"></i>
			<form:input class="form-control" maxlength="10" readonly="true"
				path="totalAmount" placeholder="0000"
				value="${packages.totalAmount}"></form:input>
		</div>
	</div>
</div>
<div class="form-group">
	<label for="inputAddress" class="col-md-2 control-label">
		Validity<span class='require'>*</span>
	</label>
	<div class="col-md-6">
		<div class="input-icon right">
			<form:input class="form-control" maxlength="3"
				placeholder="Enter Days" path="validity"
				value="${packages.validity}"
				onkeypress="return onlyNos(event,this);"></form:input>
			<small>Enter Validity in Days Only</small>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		mulusers();
		mulcars();
		muldrivers();		
	});

	function onlyNos(e, t) {
        try {
            if (window.event) {
                var charCode = window.event.keyCode;
            } else if (e) {
                var charCode = e.which;
            } else { return true; }
            if(charCode === 8) {
                return true;
            }
            if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                return false;
            }
            return true;
        }
        catch (err) {
            alert(err.Description);
        }
    }

	function mulcars() {
        var cars = $('#cars').val();
        var carrate = $('#carrate').val();
        var result = parseInt(cars) * parseFloat(carrate);
        if (!isNaN(result)) {
            $('#caramount').val(result);
            sumofamount();
        }
    }

    function mulusers() {
        var users = $('#users').val();
        var userrate = $('#userrate').val();
        //var result1 = parseInt(users) * parseInt(userrate);
        var result1= parseInt(users) * parseFloat(userrate);
        if (!isNaN(result1)) {
            $('#usersamount').val(result1);
            sumofamount();
        }
    }

    function muldrivers() {
        var drivers = $('#drivers').val();
        var driverrate = $('#driverrate').val();
        var result2 = parseInt(drivers) * parseFloat(driverrate);
        if (!isNaN(result2)) {
            $('#driversamount').val(result2);
            sumofamount();
        }
    }

    function sumofamount() {
    	var additionalCharge = $('#additionalCharges').val();
        if(additionalCharge === ""){
            additionalCharge = 0;
        }
        var caramount = $('#caramount').val();
        if(caramount === ""){
            caramount = 0;
        }
        var usersamount = $('#usersamount').val();
        if(usersamount === ""){
            usersamount = 0;
        }
        var driversamount = $('#driversamount').val();
        if(driversamount === ""){
            driversamount = 0;
        }
        var result3 = parseFloat(caramount) + parseFloat(usersamount) + parseFloat(driversamount) + parseFloat(additionalCharge);
        if(!isNaN(result3)) {
            $('#totalAmount').val(result3);
        }
    }
    
    $('.number').keypress(function(event) {
        if(event.which == 8 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 46)
             return true;
        else if((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57))
             event.preventDefault();
   });
    
    if("${packages.id}" != null && "${packages.id}" != "") {
    	$("input").attr("readonly", true);
    	$("select").attr("readonly", true);
    }
</script>