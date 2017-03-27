<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-header">
					<div class="caption">Renew Package (${tenantCompanyName})</div>
					<!-- <div class="tools"><i class="fa fa-chevron-up"></i><i data-toggle="modal" data-target="#modal-config" class="fa fa-cog"></i><i class="fa fa-refresh"></i><i class="fa fa-times"></i></div>-->
				</div>
				<div class="portlet-body">
					<form:form id="renewTenantPackageSave" class="form-horizontal" method="POST">
						<div class="form-group">
							<div class="col-md-12 text-center">
								<label class="error" id="dateError"></label>
							</div>
						</div>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<form:input path="id" type="hidden" value="${tenantPackage.id}" />
						<form:input path="tanentID" type="hidden" value="${tenantPackage.tanentID}" />
						<div class="form-body pal">
							<div class="form-group">
								<div class="col-md-12">
									<label for="inputAddress" class="col-md-2 control-label">Package
										Name</label>
									<div class="col-md-2">
										<form:input type="text" class="form-control"
											placeholder="Package Name" path="name" readonly="true" value="${tenantPackage.name}"/>
									</div>
									<label class="col-md-1 control-label">From Date</label>
									<div class="col-md-3">
										<div
											class="col-md-12 input-group flot_for_hour">
											<fmt:formatDate value="${tenantPackage.fromDate}"
												pattern="dd/MM/yyyy" var="fromDate" />
											<input class="form-control" name="fromDate" id="fromDate" type="text"
												value="${fromDate}" />
										</div>
									</div>
									<label class="col-md-1 control-label">To Date</label>
									<div class="col-md-3">
										<div
											class="col-md-12 input-group flot_for_hour">
											<fmt:formatDate value="${tenantPackage.toDate}"
												pattern="dd/MM/yyyy" var="toDate" />
											<input class="form-control" name="toDate" id="toDate" type="text"
												value="${toDate}" />
										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
                                <div class="col-md-12">
                                    <label for="inputAddress" class="col-md-2 control-label">Rate/Car</label>
                                    <div class="col-md-2">
                                        <form:input type="text" path="carrate" class="form-control"
                                            value="${tenantPackage.carrate}" placeholder="Rate/Car" />
                                    </div>
                                    <label for="inputAddress" class="col-md-1 control-label">Rate/Driver</label>
                                    <div class="col-md-3">
                                    	<div class="col-md-12 input-group flot_for_hour">
	                                        <form:input type="text" path="driverrate" class="form-control"
	                                            value="${tenantPackage.driverrate}" placeholder="Driver" />
                                       	</div>
                                    </div>
                                    <label for="inputAddress" class="col-md-1 control-label">Additional cost</label>
                                    <div class="col-md-3">
                                    	<div class="col-md-12 input-group flot_for_hour">
                                        	<form:input type="text" class="form-control"
                                            	value="${tenantPackage.additionalCharges}" placeholder="Cost"
                                            	path="additionalCharges" />
                                        </div> 
                                    </div>
                                </div>
                            </div>
							<div class="form-group">
								<div class="col-md-12">
									<div class="col-md-5 col-md-offset-2">
										<input type="checkbox" id="selectall" class="pointer" />
										<span class="check-padding">All Car Number</span><br />
										<c:forTokens var="car" items="${tenantPackage.oldCarList}" delims=",">
											<input type="checkbox" class="car-check case pointer" name="car" value="${car}" />
											<span class="check-padding">${car}</span><br />
										</c:forTokens>
										<label class="error" id="carError" style="display: none;">Please Check Atleast One Car</label>
									</div>
									<div class="col-md-5">
										<input type="checkbox" id="selectall1" class="pointer" />
										<span class="check-padding">All Driver License Number</span><br />
										<c:forTokens var="driver" items="${tenantPackage.oldDriverList}" delims=",">
											<input type="checkbox" class="driver-check case1 pointer" name="driver" value="${driver}" />
											<span class="check-padding">${driver}</span><br />
										</c:forTokens>
										<label class="error" id="driverError" style="display: none;">Please Check Aleast One Driver</label>
									</div>
								</div>
							</div>
							<form:input type="hidden" path="carList" />
							<form:input type="hidden" path="driverList" />
							<div class="form-group">
								<div class="col-md-6">
									<div class="col-md-4"></div>
									<!-- Button trigger modal -->
									<button type="button"
										class="btn orange_bg" id="myModal">Submit</button>
									<a href="<%=UrlConstants.CREATE_PACKAGE_LIST%>?tanentID=${tenantPackage.tanentID}"
										class="btn grey_bg">Back</a>
									<!-- Modal -->
									<div class="modal fade" id="myModal-Modal" tabindex="-1"
										role="dialog" aria-labelledby="myModalLabel">
										<div class="modal-dialog" role="document">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"
														aria-label="Close">
														<span aria-hidden="true">&times;</span>
													</button>
													<h4 class="modal-title" id="myModalLabel">Title</h4>
												</div>
												<div class="modal-body"></div>
												<div class="modal-footer">
													<button type="submit" class="btn btn-default">Save</button>
													<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
												</div>
											</div>
										</div>
									</div>
									<!-- Button trigger modal -->
								</div>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		// add multiple select / deselect functionality
	    $("#selectall").click(function () {
	    	$('.case').attr('checked', this.checked);
	    });
	
	    // if all checkbox are selected, check the selectall checkbox
	    // and viceversa
	    $(".case").click(function(){
	        if($(".case").length == $(".case:checked").length) {
	            $("#selectall").attr("checked", "checked");
	        } else {
	            $("#selectall").removeAttr("checked");
	        }
	    });
	 // add multiple select / deselect functionality
	    $("#selectall1").click(function () {
	    	$('.case1').attr('checked', this.checked);
	    });
	
	    // if all checkbox are selected, check the selectall checkbox
	    // and viceversa
	    $(".case1").click(function(){
	        if($(".case1").length == $(".case1:checked").length) {
	            $("#selectall1").attr("checked", "checked");
	        } else {
	            $("#selectall1").removeAttr("checked");
	        }
	    });
	});
	var validation;
	$(document).ready(function() {
		$('#myModal').click(function() {
			var carVal = '';
			var carCount;
			var driverVal = '';
			var driverCount;
			var carCountInc = 0;
			$.each($('.car-check'), function(i, item) {
				if($(this).is(':checked')) {
			        if (carVal != '') {
			        	carVal += ",";
			        }
			        carVal += $(this).val();
			        carCount = (i + 1);
			        carCountInc++;
				}
	        });
			var driCount = 0;
			$.each($('.driver-check'), function(i, item) {
				if($(this).is(':checked')) {
			        if (driverVal != '') {
			        	driverVal += ",";
			        }
			        driverVal += $(this).val();
			        driverCount = (i + 1);
			        driCount++;
				}
	        });
			if (typeof carCountInc === "undefined") {
				carCount = 0;
			}
			if (typeof driCount === "undefined") {
				driverCount = 0;
			}
			if ($("#fromDate").val() === "${fromDate}" && $("#toDate").val() === "${toDate}") {
				$("#dateError").html("Please Select Different From and To Date !!!");
				validation = false;
			} else {
				$("#dateError").html("");
				validation = true;
			}
			if(validation) {
				if (carVal === "") {
					$("#dateError").html("Please Check Aleast One Car Number !!!");
					validation = false;
				} else {
					$("#dateError").html("");
					validation = true;
				}
			}
			if(validation) {
				if (driverVal === "") {
					$("#dateError").html("Please Check Aleast One Driver License Number !!!");
					validation = false;
				} else {
					$("#driverError").html("");
					validation = true;
				}
			}
			$("#myModalLabel").html("Package Name : " + $("#name").val());
			var tag = "<strong>Car Count</strong> : " + carCountInc + "<br>";
			tag += "<strong>Driver Count</strong> : " + driCount;
			$("#carList").val(carVal);
			$("#driverList").val(driverVal);
			$('.modal-body').html(tag);
			if(validation) {
				$('#myModal-Modal').modal('show');
			}
		});
		
		var nextMonthDate;
		$('#fromDate').datepicker({
			dateFormat : 'dd/mm/yy',
			minDate : 0,
			onSelect : function(date) {
				updateAb();
			}
		});
		
		function toValidDate(datestring){
		    return datestring.replace(/(\d{2})(\/)(\d{2})/, "$3$2$1");
		}
		
		$('#toDate').datepicker({
			dateFormat : 'dd/mm/yy',
			minDate : 0,
			maxDate : nextMonthDate
		});
		
		function updateAb(){
			var pickupdate = new Date(toValidDate($('#fromDate').val()));
            var validity = parseInt("${tenantPackage.validity}");
            if(validity === "0") {
            	validity = 0;
            }
            var month = pickupdate.getMonth() + 1;
            if(month > 9) {
            	month = month;
            } else {
            	month = "0" + month;
            }
            nextMonthDate = ((pickupdate.getDate() + parseInt(validity)) + '/' + month + '/' + pickupdate.getFullYear());
            var dt2 = $('#toDate');
			var minDate = $('#fromDate').val();
			dt2.datepicker('setDate', minDate);
			dt2.datepicker('option', 'minDate', minDate);
			dt2.datepicker('option', 'maxDate', nextMonthDate);
        }
		
		jQuery.validator.addMethod("errorZero", function(value, element) {
	        var val = value;
	        if (val === "0") {
	            return false;
	        } else {
	            return true;
	        }
	    }, "Please Select Tax Type");
	});
	
	 $("#renewTenantPackageSave").validate({
			rules : {
				fromDate : {
					required : true
				},
				toDate : {
					required : true
				},
				carrate : {
	            	required : true,
	                errorZero : true
	            },
				driverrate : {
	            	required : true,
	                errorZero : true
	            }
			},
			messages : {
				carrate : {
	                required:"Please enter Rate per car!",
	                errorZero: "Please Greater then Zero Value!"
	            },
	            driverrate : {
	                required:"Please enter Rate per Driver!",
	                daysvalidataion:"Please Enter Days Below 365"
	            },
	            fromDate : {
	            	required:"Please Select From Date"
	            },
	            toDate : {
	            	required:"Please Select To Date"
	            } 
			 },
			 submitHandler : function(form) {
				if(validation) {
					$("#renewTenantPackageSave").attr("action","<%=UrlConstants.SAVE_RENEW_PACKAGE%>?${_csrf.parameterName}=${_csrf.token}");
					form.submit();
				}
	         }
		});	
</script>