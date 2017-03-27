<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- External CSS -->
<link href="assets/css/star-rating.min.css" rel="stylesheet">
<!-- External script -->
<script src="assets/scripts/star-rating.min.js"></script>
<!-- Jquery and css include file end here -->
<style type="text/css">
	.rating-md {
	   		font-size: 0.94em;
	}
	.clear-rating {
		display: none !important;
	}
	.rating-container .caption {
	    font-size: 90%;
	}
</style>
<!--BEGIN CONTENT-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-header">
					<div class="caption">FEEDBACK REPORTS</div>
				</div>
				<div class="portlet-body">
					<div class="row">
						<div class="col-mol-md-offset-2">
							<div class="form-group">
								<div class="col-md-12">
									<label class="col-lg-1 col-md-1 control-label">Filter</label>
									<div class="col-lg-4 col-md-4">
										<select class="form-control" id="sortingType">
											<option value="no">Select Type</option>
											<option value="car-wise">Car No</option>
											<option value="driver-wise">Driver Name</option>
										</select>
									</div>
								</div>
								<div class="col-md-12">
									<div id="typeByOption" style="margin-top:10px;">
										<input type="hidden" name="typeOfOption" id="typeOfOption" value="SelectType">
									</div>
									<div class="col-md-1" id="filterOptionButton" style="display: none;margin-bottom:10px;">
										<div class="btn btn-info text-center" id="filterOption">Filter</div>
									</div>
								</div>
							</div>
							<div class="table-responsive" id="feedbackList">
								<table class="table table-striped table-bordered" id="feedbackReportList">
									<thead>
								        <tr>
								           	<th><strong>Sr. No.</strong></th>
											<th><strong>Trip ID</strong></th>
											<th><strong>Date</strong></th>
											<th id="header-by-type"><strong>Car Name & car No.</strong></th>
											<th><strong>Driving</strong></th>
											<th><strong>Driver Behaviour</strong></th>
											<th><strong>Driver on performance time</strong></th>
											<th><strong>Car Condition</strong></th>
											<th><strong>Overall Service</strong></th>
											<th><strong>Average Rating</strong></th>
								        </tr>
								    </thead>
								    <tbody>
								   		<tr>
								           	<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
								        </tr>
								    </tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--END CONTENT-->
<script type="text/javascript">
	$(document).on('focus',"#startDate, #endDate", function(){
		//alert('Hello---dvl');
		if($(this).attr('id') == 'startDate') {
			$("#errorMessageStartDate").hide();
		} else {
			$("#errorMessageEndDate").hide();
		}
		$("#startDate").datepicker({
			dateFormat: 'dd/mm/yy',
            onSelect: function (date) {
                var dt2 = $('#endDate');
                var startDate = $(this).datepicker('getDate');
                var minDate = $(this).datepicker('getDate');
                dt2.datepicker('option', 'minDate', minDate);
            }
		});
		$("#endDate").datepicker({
			dateFormat: 'dd/mm/yy'
		});
	});
	
	$(function(){
		init(false);
		/* $("#feedbackReportList").Datatable(); */
		$("body").delegate("#sortingType", "change", function() {
			var tag = '';
			var typeOfOperation = '';
			var startDate = '';
			var endDate = '';
			var carNo = '';
			if($(this).val() === 'no') {
				$("#filterOptionButton").hide();
				$("#typeByOption").html('<input type="hidden" name="typeOfOption" id="typeOfOption" value="SelectType" />');
				return false;
			} else if($(this).val() === 'car-wise') {
				//alert('Hello---car-wise');
				$("#filterOptionButton").show();
				tag = "";
				typeOfOperation = "";
				$.ajax({
					type : "GET",
					url : "<%=UrlConstants.LIST_CAR_LIST_AJAX%>",
					success : function(data) {
						tag = tag + '<label class="col-lg-1 col-md-1 control-label">Car No.</label>'+
							  '<div class="col-lg-2 col-md-2">'+
								'<select id="carNo" class="form-control">';
										for (var i = 0; i < data.length; i++) {
											var dataset = data[i];
											tag = tag + '<option value="'+dataset.carNo+'">'+dataset.carNo+'</option>';			
										}
									tag += '</select></div>';
									tag += '<label class="col-lg-2 col-md-2 control-label">Start Date</label>'+
								  	'<div class="col-lg-2 col-md-2"><input class="form-control" name="startDate" id="startDate" /><div style="color:red;" id="errorMessageStartDate"></div>'+
								  	'</div><label class="col-lg-2 col-md-2 control-label">End Date</label>'+
								  	'<div class="col-lg-2 col-md-2"><input class="form-control" name="endDate" id="endDate" /><div style="color:red;" id="errorMessageEndDate"></div>'+
								  	'<input type="hidden" name="typeOfOption" id="typeOfOption" value="CarNo" />'+
								  	'</div>';
						$("#typeByOption").html(tag);
						typeOfOperation = 'CarNo';
					},
					error : function(e) {
						//alert('Hello---1');
					}
				});
			} else if($(this).val() === 'driver-wise') {
				//alert('Hello---driver-wise');
				$("#filterOptionButton").show();
				tag = "";
				typeOfOperation = "";
				$.ajax({
					type : "GET",
					url : "<%=UrlConstants.LIST_DRIVER_LIST_AJAX%>",
					success : function(data) {
						tag = tag + '<label class="col-lg-1 col-md-1 control-label">Driver Name</label>'+
							  '<div class="col-lg-2 col-md-2">'+
								'<select id="driName" class="form-control">';
										for (var i = 0; i < data.length; i++) {
											var dataset = data[i];
											tag = tag + '<option value="'+dataset.id+'">'+dataset.fullName+'</option>';			
										}
								tag += '</select></div>';
								tag += '<label class="col-lg-2 col-md-2 control-label">Start Date</label>'+
							  	'<div class="col-lg-2 col-md-2"><input class="form-control" name="startDate" id="startDate" /><div style="color:red;" id="errorMessageStartDate"></div>'+
							  	'</div><label class="col-lg-2 col-md-2 control-label">End Date</label>'+
							  	'<div class="col-lg-2 col-md-2"><input class="form-control" name="endDate" id="endDate" /><div style="color:red;" id="errorMessageEndDate"></div>'+
							  	'<input type="hidden" name="typeOfOption" id="typeOfOption" value="DriName" />'+
							  	'</div>';
						$("#typeByOption").html(tag);
						typeOfOperation = 'BookingDate';
					},
					error : function(e) {
						alert('Hello---2');
					}
				});
			}
		});
		$("body").delegate("#filterOption", "click", function() {
			init(true);
		});
	});

	function init(type) {
		//alert("hello init");
		$("#feedbackList").html("");
		//alert("hello init_1");
		var tag =  '';
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var driName = $("#driName").val();
		var carNo = $("#carNo").val();
		var typeOfOperation = $("#typeOfOption").val();
		//alert(startDate);
		if(typeof startDate == 'undefined') {
			startDate = '';
		}
		if(typeof endDate == 'undefined') {
			endDate = '';
		}
		if(typeof carNo == 'undefined') {
			carNo = '';
		}
		if(typeof driName == 'undefined') {
			driName = '';
		}
	   	if(type == true && (startDate == '' || endDate == '') && typeOfOperation == 'CarNo') {
			if(startDate == '') {
				$("#errorMessageStartDate").html("Select Start Date");
				$("#errorMessageStartDate").show();
			} else if(endDate == '') {
				$("#errorMessageEndDate").html("Select End Date");
				$("#errorMessageEndDate").show();
			}
			return false;
		} else {
			$("#errorMessageStartDate").html("");
			$("#errorMessageStartDate").hide();
		}
	   	if(type == true && (startDate == '' || endDate == '') && typeOfOperation == 'DriName') {
	   		if(startDate == '') {
				$("#errorMessageStartDate").html("Select Start Date");
				$("#errorMessageStartDate").show();
			} else if(endDate == '') {
				$("#errorMessageEndDate").html("Select End Date");
				$("#errorMessageEndDate").show();
			}
			return false;
		} else {
			$("#errorMessageDriName").html("");
			$("#errorMessageDriName").hide();
		}
	   	tag = '<table class="table table-striped table-bordered" cellspacing="0" width="100%" id="feedbackReportList">'+
		'<thead>'+
		'<tr>'+
			'<th><strong>Sr. No.</strong></th>'+
			'<th><strong>Trip ID</strong></th>'+
			'<th><strong>Booking Date</strong></th>'+
			'<th id="header-by-type"><strong>'+((typeOfOperation == "DriName") ? "Car Name & car No." : "Driver Name")+' </strong></th>'+
			'<th><strong>Driving</strong></th>'+
			'<th><strong>Driver Behaviour</strong></th>'+
			'<th><strong>Driver on performance time</strong></th>'+
			'<th><strong>Car Condition</strong></th>'+
			'<th><strong>Overall Service</strong></th>'+
			'<th><strong>Average Rating</strong></th>'+
		'</tr>'+
	'</thead>'+
	'<tbody>';
	   	ajaxFunctionClientDetailList(tag, typeOfOperation, startDate, endDate, carNo, driName);
	}
	//create table dynamically
	function ajaxFunctionClientDetailList(tag, typeOfOperation, startDate, endDate, carNo, driName){
		var tripIDJson = [];
		$.ajax({
			type : "POST",
			url : "<%=UrlConstants.AJAX_FEEDBACK_LIST%>",
			data: "${_csrf.parameterName}=${_csrf.token}&typeOfOperation="+ typeOfOperation +"&startDate=" + startDate + "&endDate=" + endDate + "&carNo="+ carNo +"&driName="+ driName,
			success : function(data) {
				var tripID = '';
				if(data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						var dataset = data[i];
					    tripID = ((dataset.tripID != null) ? dataset.tripID : '');
					    tripIDJson.push(tripID);
					    var bookingDateVal = '';
					    if(dataset.clientModel != null && dataset.clientModel.createdDate != null) {
							var pickDate = new Date(dataset.clientModel.createdDate);
							bookingDateVal = (pickDate.getDate()) + '/' + (pickDate.getMonth() + 1) + '/' + pickDate.getFullYear();
						}
						tag += '<tr>'+
									'<td>'+(i+1)+'</td>'+
									"<td><a href='feedback.htm?tripID="+tripID+"' style='text-decoration: underline;'>"+tripID+"</a></td>"+
									'<td>'+bookingDateVal+'</td>';
						if(typeOfOperation == 'DriName') {
							tag += '<td style="color:'+((dataset.clientModel.car != null) ? dataset.clientModel.car.color : 'black')+'">'+((dataset.clientModel != null && dataset.clientModel.car != null) ? dataset.clientModel.car.carName.carName + ' (' + dataset.clientModel.car.carNo +')' : '')+'</td>';
						} else {
							tag += '<td  style="color:'+((dataset.clientModel != null && dataset.clientModel.driver != null) ? dataset.clientModel.driver.color : 'black')+'">'+((dataset.clientModel != null && dataset.clientModel != null && dataset.clientModel.driver != null) ? dataset.clientModel.driver.fullName : '')+'</td>';
						}
						tag += '<td><input readonly="true" id="drivingRating-'+tripID+'" value="'+((dataset.drivingRating != null) ? dataset.drivingRating : '')+'" /></td>'+
									'<td><input readonly="true" id="driverBehaviourRating-'+tripID+'" value="'+((dataset.driverBehaviourRating != null) ? dataset.driverBehaviourRating : '')+'"/></td>'+
									'<td><input readonly="true" id="driverTestingRating-'+tripID+'" value="'+((dataset.driverTestingRating != null) ? dataset.driverTestingRating : '')+'"/></td>'+
									'<td><input readonly="true" id="carConditionRating-'+tripID+'" value="'+((dataset.carConditionRating != null) ? dataset.carConditionRating : '')+'"/></td>'+
									'<td><input readonly="true" id="overallServiceRating-'+tripID+'" value="'+((dataset.overallServiceRating != null) ? dataset.overallServiceRating : '')+'"/></td>'+
									'<td><input readonly="true" id="averageRating-'+tripID+'" value="'+((dataset.averageRating != null) ? dataset.averageRating : '')+'" /></td>'+
						   	   '</tr>';
					}
					tag += '</tbody></table>';
					$("#feedbackList").html(tag);
					$("#feedbackReportList").DataTable();
					$.each(tripIDJson, function( i, val ) {
						$('#drivingRating-'+val).rating({
					    	min: 0,
					    	max: 5,
					    	step: 0.1,
					    	stars: 5
					    });
					    $('#driverBehaviourRating-'+val).rating({
					    	min: 0,
					    	max: 5,
					    	step: 0.1,
					    	stars: 5
					    });
					    $('#driverTestingRating-'+val).rating({
					    	min: 0,
					    	max: 5,
					    	step: 0.1,
					    	stars: 5
					    });
					    $('#carConditionRating-'+val).rating({
					    	min: 0,
					    	max: 5,
					    	step: 0.1,
					    	stars: 5
					    });
					    $('#overallServiceRating-'+val).rating({
					    	min: 0,
					    	max: 5,
					    	step: 0.1,
					    	stars: 5
					    });
					    $('#averageRating-'+val).rating({
					    	min: 0,
					    	max: 5,
					    	step: 0.1,
					    	stars: 5
					    });
					});
				}
			},
			error : function(e) {
				alert('Hello---3');
			}
		});
	}
</script>