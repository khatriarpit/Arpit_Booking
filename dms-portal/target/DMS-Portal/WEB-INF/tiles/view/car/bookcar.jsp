<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page import="com.emxcel.dms.core.business.constants.Constants" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
var d = new Date(), date = (d.getUTCFullYear())+'/'+(d.getUTCMonth()+1)+'/'+(d.getUTCDate());
			var currentMonthDate = new Date();
			var actualDate = new Date(currentMonthDate); // convert to actual date
			var nextMonthDate = new Date(actualDate.getFullYear(), actualDate.getMonth()+1, actualDate.getDate());
			$(document).ready(
            			function() {
   				var d = new Date(), date = (d.getUTCFullYear()) + '/'
   						+ (d.getUTCMonth() + 1) + '/' + (d.getUTCDate());
   				var pickUpDateTime = "${pickUpDateTime != null ? pickUpDateTime : '+date+'}";
   				var dropdate = "${dropDate != null ? dropDate : '+date+'}";
   				$('#pickUpDate').datetimepicker({
   				    value : date,
   					format : 'd/m/Y H:i:s',
   					minDate : currentMonthDate,
                    onChangeDateTime : updateAb
   				});
   				$('#dropDateTime').datetimepicker({
   				    value : date,
   					format : 'd/m/Y H:i:s',
   					minDate : currentMonthDate,
   					maxDate : nextMonthDate,
  				});

               	function updateAb(){
                   var pickupdate = new Date(toValidDate($('#pickUpDate').val()));
                   nextMonthDate = new Date(pickupdate.getFullYear(), pickupdate.getMonth()+1, pickupdate.getDate());
                   $("#dropDateTime").datetimepicker({
                       format : 'd/m/Y H:i:s',
                       minDate : pickupdate,
                       maxDate : nextMonthDate,
                   });
               	}
   			});

			function toValidDate(datestring){
			    return datestring.replace(/(\d{2})(\/)(\d{2})/, "$3$2$1");
			}
			$("body").delegate("#button-onsubmit", "click", function(){
			        hideMessage();
            		var pickUpDateTime = $("#pickUpDate").val()
            		var dropUpDateTime = $("#dropDateTime").val()
            		var pickDateTime = new Date(toValidDate(pickUpDateTime));
            		var dropDateTime = new Date(toValidDate(dropUpDateTime));
				if( pickDateTime != 'Invalid Date' &&  dropUpDateTime != 'Invalid Date'){
            		 var diff = new Date(dropDateTime - pickDateTime);
                     var days = (diff)/1000/60/60/24;
                   if(pickDateTime.getTime() >= dropDateTime.getTime()){
            		         			$('#dropDateerror').show();
            		}else if(days > 31){
            			$('#dropDateerror1').show();
            		}
                   else {
            			$('#dropDateerror').hide();
            			$('#dropDateerror1').hide();
            			$("#search-book-car").attr("action", "<%=UrlConstants.BOOK_CAR_LIST%>");
            			$("#search-book-car").submit();
            		}
                }else
				{

                    $("#error-dateSelection").html('Please Select Pickup and Drop Date..!!');
                    $("#error-dateSelection").show();
				}

            	});

            function getSelectedDriverId(){
                var driverId=$("option:selected", $("#driId")).val();
                $('#selectedDriver').val(driverId);
            }
            $("body").delegate("#button-bookCar", "click", function(){
                var _bookId=$(this).data("rowId");
                var carId=$("#car-"+_bookId).val();
                var driverId=$("option:selected", $("#driId-"+_bookId)).val();
                if(driverId<=0){
                    $('#selectDriver-'+_bookId).show();
                    return false;
				}else {
                    $('#driverId').val(driverId);
                    $('#preBooking').val($("#preBooking").val());
                    $('#carId').val(carId);
                    $("#submit-booked-car").attr("action", "<%=UrlConstants.CLIENT_PAGE%>");
                    $("#submit-booked-car").submit();
                }
            });

            function hideMessage(){
                $('#dropDateerror').hide();
                $('#dropDateerror1').hide();


            }




</script>

<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-body">
					<div class="box col-md-12 media-right-pad media_float_left">
						<div class="box-header">
							<div class="caption box-caption">Book Car</div>

							<!--<div class="tools"><i class="fa fa-chevron-up"></i><i data-toggle="modal" data-target="#modal-config" class="fa fa-cog"></i><i class="fa fa-refresh"></i><i class="fa fa-times"></i></div>-->
						</div>
						<div class="box-body">
							<div class="row">
								<form:form method="POST"  id="search-book-car" ondragstart="return false;" ondrop="return false;">
								<div class="col-md-12 cars-top-margin">
									<label id="error-dateSelection" style="color: red;"></label>
	                                <label id="dropDateerror" class="pull-left" style="display: none; color:#FF0000; "
                                        											for="inputAddress" class="col-md-12 control-label" >
                                        											To Date should not be same or Less then From Date ..!!</label>

                                        											   <label id="dropDateerror1" class="pull-left" style="display: none; color:#FF0000; "
                                        											for="inputAddress" class="col-md-12 control-label" >
                                        											Gap Between DropDate and Pickup date no more than 31...</label>
                                        </div>
									<div class="col-md-2">
										<label><b>Car Type</b></label>
										<form:select class="form-control" path="car.carType.id"
											itemValue="car.carType.id" ondragstart="return false;" ondrop="return false;">
											<form:option selected="selected" value="0">All</form:option>
											<c:forEach var="carType" items="${CarTypeDTL}">
												<form:option value="${carType.id}"
													selected="${carTypeId eq carType.id ? 'selected' : ''}">${carType.carType}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<div class="col-md-4">
										<label><b>From</b></label>
										<div class="input-group ">

											<form:input class="form-control" id="pickUpDate"
												path="pickUpDateTime" value="${pickUpDateTime}" />
											<span class="input-group-addon "> <i
												class="fa fa-calendar"></i>
											</span>
										</div>
									</div>
									<div class="col-md-4">
										<label><b>To</b></label>
										<div class="input-group ">
											<form:input class="form-control datetimepicker"
												value="${dropDate}" path="dropDateTime" />
											<span class="input-group-addon "> <i
												class="fa fa-calendar"></i>
											</span>
										</div>

									</div>
									<div class="col-md-2">
										<label style="display: block;"><b>&nbsp;</b></label>
										<button type="button" id="button-onsubmit" class="btn orange_bg">Search</button>
									</div>
								</form:form>
								<div class="col-md-12 cars-top-margin">

									<label class="pull-left"><h4>
											<c:if test="${fn:length(listOfCarDetails) gt 0}">
												<b>Cars Available on from ${pickUpDateTime} to ${dropDate}</b>
											</c:if>
											<c:if test="${listOfCarDetails != null && fn:length(listOfCarDetails) lt 1}">
												<c:if test="${fn:length(listOfCarDetails) eq 0}">
													<b>Sorry ,No Car Available in selected duration..!!</b>
												</c:if>
											</c:if>
											<c:if test="${listOfCarDetails eq null}">
                                            	<b>Please , Search the Car..!!</b>
											</c:if>
										</h4></label>
									<div class="row">
										<div class="car_time_details col-md-12">
											<div class="row">
												<c:forEach items="${listOfCarDetails}" var="carDtls" varStatus="loop">
													<input type="hidden" id="car-${loop.count}" name="car" value="${carDtls.carId}"  />
													<!-- for Loop -->
													<div class="car-details-wrp">
														<div class="col-md-2">
															<h4>${carDtls.carNumber}</h4>
															<label>(${carDtls.carType},${carDtls.carName})</label>
														</div>
														<div class="col-md-6">
															<ul>
																<li>

                                                                            <c:if test="${not empty carDtls.previousBufferHours}">
																	<div class="data-booked data-line" id="data-line"
																		data-attr="2">
																		<span class="date-time date-time-start">
																			<h5>${carDtls.previousStartDate}</h5> <label>${carDtls.previousStartTime}</label>
																		</span> <span class="date-time date-time-end">
																			<h5>${carDtls.previousEndDate}</h5> <label>${carDtls.previousEndTime}</label>
																		</span>
																	</div>

																	<div class="data-available data-line text-center"
																		id="data-line" data-attr="2">
																		<label>${carDtls.previousBufferHours} Hrs</label>
																	</div>
                                                                      </c:if>
																	<div class="data-not-available data-line"
																		id="data-line" data-attr="1">
																		<span class="date-time date-time-start">
																			<h5>${carDtls.pickupDate}</h5> <label>${carDtls.pickupTime}</label>
																		</span> <span class="date-time date-time-end">
																			<h5>${carDtls.dropDate}</h5> <label>${carDtls.dropTime}</label>
																		</span>
																	</div>

                                                                           <c:if test="${not empty carDtls.nextBufferHours}">
																		<div class="data-available data-line text-center"
																			id="data-line" data-attr="2">
																			<label>${carDtls.nextBufferHours} Hrs</label>
																		</div>
																		<div class="data-booked data-line" id="data-line"
																			data-attr="2">
																			<span class="date-time date-time-start">
																				<h5>${carDtls.nextStartDate}</h5> <label>${carDtls.nextStartTime}</label>
																			</span> <span class="date-time date-time-end">
																				<h5>${carDtls.nextEndDate}</h5> <label>${carDtls.nextEndTime}</label>
																			</span>
																		</div>

                                                                         </c:if>
																</li>
															</ul>
														</div>


														<div class="col-md-4">
															<form:form method="POST"  id="submit-booked-car" >
																<input type="hidden" id="preBooking" name="preBooking" value="${preBooking.id}"  />
																<label id="selectDriver-${loop.count}"  class="pull-left" style="display: none; color:#FF0000; "
																	   for="inputAddress" class="col-md-6 control-label" >
																	Please Select Driver First..!!</label>
                                                                <div class="col-md-7">
													           <select class="form-control" name="driId" id="driId-${loop.count}" onchange="getSelectedDriverId();" >
                                                                    <option value="0">Select Driver</option>
                                                                    <c:forEach var="driver" items="${carDtls.listOfDriver}">
                                                                     	   <option value="${driver.id}" ${carDtls.driverId == driver.id ? 'selected' : ''}>${driver.fullName}</option>

                                                                    </c:forEach>
                                                                </select>
                                                                </div>
                                                                <div class="col-md-2">
																<input type="hidden" name="${_csrf.parameterName}"
																	value="${_csrf.token}" />
																	   <input type="hidden" name="pickUpDateTime1" value="${pickUpDateTime}" />
																	<input type="hidden"
																	name="dropDateTime1" value="${dropDate}" />
																	<input type="hidden"
																	name="clientId" value="a" />
																	<input type="hidden"
																		   name="selectedDriver" id="selectedDriver" />
																	<input type="hidden"  name="driverId" id="driverId" >
																	<input type="hidden" name="carId"  id="carId"/>
																<button type="button" class="btn btn-default" id="button-bookCar" data-row-id="${loop.count}">Book
																	Now</button>
                                                                </div>
															</form:form>

														</div>
													</div>
												</c:forEach>

											</div>
										</div>
									</div>
								</div>
								<br>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>