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
<!--BEGIN CONTENT-->
<!--BEGIN PAGE WRAPPER-->
<div id="page-wrapper-1">
	<!--BEGIN PAGE HEADER & BREADCRUMB-->

	<div class="col-lg-12">
		<div class="row">
			<div class="col-lg-3 col-xs-6">
				<!-- small box -->
				<div class="small-box bg-dark-blue">
					<div class="inner">
						<h4>
							CURRENT RIDES
							</h4>
							<h3>${liveTrips}</h3>
					</div>

				</div>
			</div>
			<!-- ./col -->
			<div class="col-lg-3 col-xs-6">
				<!-- small box -->
				<div class="small-box bg-dark-red">
					<div class="inner">
						<h4>
							UPCOMING RIDES
							</h4>
							<h3>${totalFutureTrips }</h3>
					</div>

				</div>
			</div>
			<!-- ./col -->
			<div class="col-lg-3 col-xs-6 clear-fix">
				<!-- small box -->
				<div class="small-box bg-dark-yellow">
					<div class="inner">
						<h4>
							END RIDES
							</h4>
							<h3>${totalEndTrips}</h3>
					</div>
				</div>
			</div>
			<!-- ./col -->
			<div class="col-lg-3 col-xs-6">
				<!-- small box -->
				<div class="small-box bg-dark-green">
					<div class="inner">
						<h4>
							CANCEL RIDES
							</h4>
							<h3>${totalCanceledTrips}</h3>
					</div>
				</div>
			</div>
			<!-- ./col -->
			<div class="col-lg-12 col-xs-12  flot_for_hour_right">
				<div class="col-md-6 top-margin">
					<div class="box box-primary">
						<div class="box-header with-border">
							<h3 class="box-title">Area Chart</h3>

							<div class="box-tools pull-right">
								<button type="button" class="btn btn-box-tool"
									data-widget="collapse">
									<i class="fa fa-minus"></i>
								</button>
								<button type="button" class="btn btn-box-tool"
									data-widget="remove">
									<i class="fa fa-times"></i>
								</button>
							</div>
						</div>
						<div class="box-body chart-responsive">
							<div class="chart" id="area-example" style="height: 300px;"></div>
						</div>
						<!-- /.box-body -->
					</div>
				</div>
				<div class="col-md-6 top-margin">
					<div class="box box-solid bg-light-grey">
						<div class="box-header">
							<h4>
								<b>Pending Booking Request: ${totalPrebooking}
								</b></h4>
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
											<td><fmt:formatDate value='${prebooking.pickUpDateTime}' var='pickUpDate' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT%>' /><c:out value="${pickUpDate}" /></td>
										    <td><fmt:formatDate value='${prebooking.dropDateTime}' var='dropDate' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT%>' /><c:out value="${dropDate}" /></td> 
											<td>${prebooking.requestedBookingId}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
						</div>
					</div>
				</div>
				<!-- <script type="text/javascript">
					$(document).ready(function() {
						$("#prebooking").DataTable();
					})
				</script> -->
			</div>
		</div>
	</div>
</div>
<!--END CONTENT-->