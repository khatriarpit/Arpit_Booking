<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script type="text/javascript">
	$(document).ready(
			function() {
				$("#cartypeDetails").DataTable();
				if ("${message}" != "") {
					$(".alert-success-message").fadeIn("fast").delay("3000")
							.fadeOut("fast");
				}
			});
</script>

<div class="col-lg-12">
	<div class="row">

		<div class="col-lg-3 col-xs-6">
			<!-- small box -->
			<div class="small-box bg-dark-blue">
				<div class="car-small-img">
					<img src="assets/images/car-small_img.png">
				</div>
				<div class="inner">
					<h4>CURRENT RIDES</h4>
					<h3>${liveTrips}</h3>

				</div>

			</div>
		</div>
		<!-- ./col -->
		<div class="col-lg-3 col-xs-6">
			<!-- small box -->
			<div class="small-box bg-dark-red">
				<div class="car-small-img">
					<img src="assets/images/car-small_img_rides.png">
				</div>
				<div class="inner">
					<h4>UPCOMING RIDES</h4>
					<h3>${totalFutureTrips }</h3>

				</div>

			</div>
		</div>
		<!-- ./col -->
		<div class="col-lg-3 col-xs-6 clear-fix">
			<!-- small box -->
			<div class="small-box bg-dark-yellow">
				<div class="car-small-img">
					<img src="assets/images/car-small_img_end_rides.png">
				</div>
				<div class="inner">
					<h4>END RIDES</h4>
					<h3>${totalEndTrips}</h3>

				</div>
			</div>
		</div>
		<!-- ./col -->
		<div class="col-lg-3 col-xs-6">
			<!-- small box -->
			<div class="small-box bg-dark-green">
				<div class="car-small-img">
					<img src="assets/images/car-small_img_cancel.png">
				</div>
				<div class="inner">
					<h4>CANCEL RIDES</h4>
					<h3>${totalCanceledTrips}</h3>

				</div>
			</div>
		</div>
		<!-- ./col -->
		<div class="col-lg-12 col-xs-12  flot_for_hour_right">
			<div class="col-md-4 top-margin">
				<div class="box-header with-border">
					<h3 class="box-title">Pending Cancel Request</h3>
				</div>
				<div class="col-md-12">
					<div class="box box-primary">
						<div class="box-body chart-responsive">
							<div id="chart-line-1" data-width="${totalDriverCancelTrips}"
								data-tooltip="Driver"></div>
							<div id="chart-line-2" data-width="${totalCustomerCancelTrips}"
								data-tooltip="Customer"></div>
								<div id="chart-line-5" data-width="1" data-tooltip="Customer"></div>
								
							<div class="center-text"></div>
							<div id="sales-chart"></div>

						</div>
					</div>
				</div>

				<div class="col-md-12 box-side123">
					<div class="col-md-4">
						<div>
							<span class="box-side"></span><span>&nbsp;Total:${totalCancelRequests}</span>
						</div>
					</div>
					<div class="col-md-4">
						<div>
							<span class="box-side-1"></span><span>&nbsp;Customer:${totalCustomerCancelTrips}</span>
						</div>
					</div>
					<div class="col-md-4">
						<div>
							<span class="box-side-2"></span><span>&nbsp;Driver:${totalDriverCancelTrips}</span>
						</div>
					</div>
				</div>

			</div>
			<div class="col-md-5 top-margin">
				<div class="col-md-12">
					<div class="box box-primary">
						<div class="box-header with-border">
							<h3 class="box-title">Ride status</h3>
						</div>
						<div class="box-body chart-responsive">
							<div id="chart-line-3" data-width="3" data-tooltip="Driver"></div>
							<div id="chart-line-4" data-width="7" data-tooltip="Customer"></div>
							<div id="chart-line-5" data-width="1" data-tooltip="Customer"></div>

							<!-- <canvas id="doughnutChartsecond" height="300" width="300" style="display: block; width: 300px; height: 300px;"></canvas> -->
							<div id="revenue-chart" style="width: 100%; height: 400px;"></div>


						</div>
						<!-- /.box-body -->
					</div>
				</div>
			</div>

			<div class="col-md-3 top-margin">
				<div class="box box-solid bg-light-grey">
					<div class="box-header">
						<h4>
							<b>Pending Booking Request:${totalPrebooking}</b>
						</h4>
						<h4 class="imm-text">
							<b>Immediate Attentions</b>
						</h4>
					</div>
					<div class="box-body">
						<c:if test="${!empty prebooking}">
							<table class="table table-striped table-bordered" id="prebooking">
								<thead>
									<tr>
										<th>No</th>
										<th>Name</th>
										<th>PickUp Date</th>
										<th>Drop Date</th>
										<th>Id</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${prebooking}" var="prebooking"
										varStatus="loopCounter">
										<tr>
											<td>${loopCounter.count}</td>
											<td><c:out value="${prebooking.guest.personName}" /></td>
											<td><fmt:formatDate value='${prebooking.pickUpDateTime}'
													var='pickUpDate' type='date'
													pattern='<%=Constants.PORTAL_DATE_FORMAT%>' />
												<c:out value="${pickUpDate}" /></td>
											<td><fmt:formatDate value='${prebooking.dropDateTime}'
													var='dropDate' type='date'
													pattern='<%=Constants.PORTAL_DATE_FORMAT%>' />
												<c:out value="${dropDate}" /></td>
											<td>${prebooking.requestedBookingId}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>

					</div>

				</div>
			</div>
		</div>

	</div>
</div>
</div>
<!--END CONTENT-->
<script type="text/javascript">
	//Donut Chart
	var firstWidth = $('#chart-line-1').attr('data-width');
	var secondWidth = $('#chart-line-2').attr('data-width');
	var thirdWidth = $('#chart-line-5').attr('data-width');
	if (thirdWidth == 0) {
		var donut = new Morris.Donut({
			element : 'sales-chart',
			resize : true,
			colors : [ "#e5e5e5", "#37c6d4", "#e5e5e5" ],
			data : [
			// {label: "Total Request", value: firstWidth},
			// {label: "Total Request", value: secondWidth},
			{
				label : "Total Request",
				value : 10
			} ],
			hideHover : 'auto'
		});
	} else {
		var donut = new Morris.Donut({
			element : 'sales-chart',
			resize : true,
			colors : [ "#3c8dbc", "#ff925a", "#e5e5e5" ],
			data : [ {
				label : "Total Request",
				value : firstWidth
			}, {
				label : "Total Request",
				value : secondWidth
			},
			// {label: "Total Request", value: thirdWidth}
			],
			hideHover : 'auto'
		});

	}
	// Sales chart
	var area = new Morris.Area({
		element : 'revenue-chart',
		resize : true,
		data : [ {
			y : '2011 Q1',
			item1 : 2666,
			item2 : 2666
		}, {
			y : '2011 Q2',
			item1 : 2778,
			item2 : 2294
		}, {
			y : '2011 Q3',
			item1 : 4912,
			item2 : 1969
		}, {
			y : '2011 Q4',
			item1 : 3767,
			item2 : 3597
		}, {
			y : '2012 Q1',
			item1 : 6810,
			item2 : 1914
		}, {
			y : '2012 Q2',
			item1 : 5670,
			item2 : 4293
		}, {
			y : '2012 Q3',
			item1 : 4820,
			item2 : 3795
		}, {
			y : '2012 Q4',
			item1 : 15073,
			item2 : 5967
		}, {
			y : '2013 Q1',
			item1 : 10687,
			item2 : 4460
		}, {
			y : '2013 Q2',
			item1 : 8432,
			item2 : 5713
		} ],
		xkey : 'y',
		ykeys : [ 'item1', 'item2' ],
		labels : [ 'Item 1', 'Item 2' ],
		lineColors : [ '#fc706f', '#37c6d4' ],
		hideHover : 'auto'
	});
</script>
