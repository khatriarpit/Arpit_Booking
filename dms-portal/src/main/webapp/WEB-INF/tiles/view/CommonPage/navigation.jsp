<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@page import="com.emxcel.dms.core.model.user.User"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	HttpSession currentSession = request.getSession();
	User userBeanVO = (User) currentSession.getAttribute("user");
%>
<nav id="topbar" role="navigation" style="margin-bottom: 0;"
	class="navbar navbar-default navbar-static-top">
	<div class="navbar-header body-background">
		<button type="button" data-toggle="collapse"
			data-target=".sidebar-collapse" class="navbar-toggle">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a id="logo" href="<%=UrlConstants.INDEX%>" class="navbar-brand">
			<span class="fa fa-rocket"></span><span class="logo-text"><img
				src="assets/images/logo_home.png"></span>
		</a>
	</div>
	<div id="topbar-main_not" class="topbar-main body-background">
		<a id="menu-toggle" href="#" class="btn hidden-xs"><i
			class="fa fa-bars"></i></a>
		<ul class="nav navbar-top-links navbar-right">
			<li class="dropdown"><a data-toggle="dropdown" href="#"
				class="dropdown-toggle"> <img
					src="<%=userBeanVO != null && userBeanVO.getUserImage() != null ? "fetch-image?imageName=user/tenants/" + userBeanVO.getTanentID() + "/" + userBeanVO.getUserImage() : "assets/images/ban123.png"%>"
					title="${pageContext.request.userPrincipal.name}" alt=""
					class="img-responsive img-circle" /> &nbsp;
					${pageContext.request.userPrincipal.name} &nbsp; <span
					class="caret"></span>
			</a> <%-- ${pageContext.request.userPrincipal.name} --%>
				<ul class="dropdown-menu dropdown-user pull-right">
					<li>
						<div class="navbar-content">
							<div class="row">
								<div class="col-md-5 col-xs-5">
									<img
										src="<%=userBeanVO != null && userBeanVO.getUserImage() != null
					? "fetch-image?imageName=user/tenants/" + userBeanVO.getTanentID() + "/" + userBeanVO.getUserImage()
					: "assets/images/ban123.png"%>"
										alt="" class="img-responsive img-circle" />
								</div>
								<div class="col-md-7 col-xs-5">
									<span>${pageContext.request.userPrincipal.name}</span>
									<p class="text-muted small"><%=userBeanVO != null && userBeanVO.getEmailId() != null ? userBeanVO.getEmailId() : ""%></p>
									<div class="divider"></div>
									<a
										href="<%=UrlConstants.USER%>?userId=<%=userBeanVO != null && userBeanVO.getId() != null ? userBeanVO.getId() : ""%>"
										class="btn btn-primary btn-sm">View Profile</a>
								</div>
							</div>
						</div>
						<div class="navbar-footer">
							<div class="navbar-footer-content">
								<div class="row">
									<div class="col-md-6 col-xs-6">
										<a href="<%=UrlConstants.CHANGE_PASSWORD%>"
											class="btn btn-default btn-sm">Change Password</a>
									</div>
									<div class="col-md-6 col-xs-6">
										<a href="<%=UrlConstants.LOGIN%>?logout"
											class="btn btn-default btn-sm pull-right">Sign Out</a>
									</div>
								</div>
							</div>
						</div>
					</li>
				</ul></li>
			<li class="clock"><strong id="get-date"></strong>
				<div class="digital-clock">
					<div id="getHours" class="get-time"></div>
					<span>:</span>
					<div id="getMinutes" class="get-time"></div>
					<span>:</span>
					<div id="getSeconds" class="get-time"></div>
				</div></li>
		</ul>
	</div>
</nav>
<!--BEGIN MODAL CONFIG PORTLET-->
<nav id="sidebar" role="navigation"
	class="navbar-default navbar-static-side">
	<div class="sidebar-collapse menu-scroll">
		<ul id="side-menu" class="nav dgrey-font">
			<li></li>
			<li><a href="<%=UrlConstants.PERMISSION_LIST%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">User Permission List</span></a></li>
			<security:authorize access="hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')">
				<li><a href="<%=UrlConstants.USER_LOGS%>"><i
					class="fa fa-home fa-fw"></i><span class="menu-title">User Logs</span></a></li>
				<li><a href="<%=UrlConstants.EMAIL_TEMPLATE_LIST%>"><i
					class="fa fa-home fa-fw"></i><span class="menu-title">Email Templates</span></a></li>
				<li><a href="<%=UrlConstants.SMS_TEMPLATE_LIST%>"><i
					class="fa fa-home fa-fw"></i><span class="menu-title">SMS Templates</span></a></li>
			</security:authorize>
			<security:authorize access="hasRole('ROLE_ADMIN')">
				<li><a href="<%=UrlConstants.SUPPORT_TIKCKET%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Ticket</span></a></li>
				<li><a href="<%=UrlConstants.USER_GROUP%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">User Group</span></a></li>
				<li><a href="<%=UrlConstants.VIEW_SUPPORT_TICKET%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">View
							Tickets</span></a></li>
				<li><a href="<%=UrlConstants.LIST_COMAPANY_MASTER%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Company
							Master</span></a></li>
				<li><a href="<%=UrlConstants.USER_LIST%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">User
					</span></a></li>
				<li><a href="<%=UrlConstants.DESTINATION_MASTER%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Source
							And Destination </span></a></li>
				<li><a href="<%=UrlConstants.LIST_CAR%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Car </span></a></li>
				<li><a href="<%=UrlConstants.DRIVER_LIST%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Driver
					</span></a></li>
				<li><a href="<%=UrlConstants.CAR_DRIVER_MAPPING%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Car
							Driver Mapping </span></a></li>
				<li><a href="<%=UrlConstants.CHANGEPASSWORDFORDE_PAGE%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Change
							Users password </span></a></li>
			</security:authorize>

			<security:authorize access="hasRole('ROLE_SUPERADMIN')">
				<li><a href="<%=UrlConstants.SELL_ALL_SELLER%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Sell
							All Seller </span></a></li>

				<li><a href="<%=UrlConstants.USER_GROUP%>"><i class="fa fa-home fa-fw"></i><span class="menu-title">User Group</span></a></li>
			</security:authorize>

			<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
				<li><a href="<%=UrlConstants.GET_TRIP_DETAIL%>"> <i
						class="fa fa-home fa-fw"></i> <span class="menu-title">Enter
							Trip Id OR Car No.</span>
				</a></li>
				<li><a href="<%=UrlConstants.SEARCH_CAR%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Book
							Car </span></a></li>
				<li><a href="<%=UrlConstants.PRE_BOOKING%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Pre
							Booking </span></a></li>
				<li><a href="<%=UrlConstants.LIST_BOOKEDCARS%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Booked
							Car List</span></a></li>
				<li><a href="<%=UrlConstants.LIST_PRE_BOOKING%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Pre
							Booking Car List</span></a></li>
				<li><a href="<%=UrlConstants.CUSTOMER_CANCEL_REQUEST%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Customer
							Cancel Requested List</span></a></li>
				<li><a href="<%=UrlConstants.CANCEL_REQUEST_LIST%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Driver
							Cancel Requested List</span></a></li>
				<li><a href="#"><i class="fa fa-home fa-fw"></i><span
						class="menu-title">Calendar </span></a></li>
				<li><a href="<%=UrlConstants.ENTER_FEEDBACK%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Feedback
					</span></a></li>
				<li><a href="<%=UrlConstants.LIST_INVOICE_STATIC%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Invoice
							Static </span></a></li>
				<%-- <li><a href="<%=UrlConstants.USER_LIST%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">User</span></a></li>
				<li><a href="#"> <i class="fa fa-home fa-fw"></i> <span
						class="menu-title">Cancel Booking</span><span class="fa arrow"></span>
				</a>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="fa fa-home fa-fw"></i><span
								class="menu-title">Cancel Trip Request</span> </a></li>

						<li><a href="#"><i class="fa fa-home fa-fw"></i><span
								class="menu-title">List Of Cancel Trip</span> </a></li>
					</ul></li>
				<li><a href="#"> <i class="fa fa-home fa-fw"></i> <span
						class="menu-title">Car</span> <span class="fa arrow"></span>
				</a>
					<ul class="nav nav-second-level">
						<li><a href="<%=UrlConstants.CARNAME_LIST%>">Car Name</a></li>
						<li><a href="<%=UrlConstants.LIST_CAR%>">Car</a></li>
						<li><a href="<%=UrlConstants.CARTYPE_LIST%>">Car Type</a></li>
						<li><a href="<%=UrlConstants.CAR_DRIVER%>">Car Driver</a></li>
					</ul></li>
				<li><a href="#"> <i class="fa fa-home fa-fw"></i> <span
						class="menu-title">Driver</span> <span class="fa arrow"></span>
				</a>
					<ul class="nav nav-second-level">
						<li><a href="<%=UrlConstants.DRIVER_LIST%>">Driver List</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="<%=UrlConstants.DRIVER_CHANGE%>">Change
								Driver</a></li>
					</ul></li>
				<li><a href="#"> <i class="fa fa-home fa-fw"></i> <span
						class="menu-title">feedback</span> <span class="fa arrow"></span>
				</a>
					<ul class="nav nav-second-level">
						<li><a href="<%=UrlConstants.ENTER_FEEDBACK%>">Feedback</a></li>
					</ul></li> --%>


			</security:authorize>
			<security:authorize access="hasRole('ROLE_SUPERADMIN')">
				<li><a href="<%=UrlConstants.CHANGEPASSWORDFORDE_PAGE%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Change
							Users password </span></a></li>
				<li><a href="<%=UrlConstants.COMPANY_TYPE_LIST%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Company
							Type</span></a></li>
				<li><a href="<%=UrlConstants.USER_LIST%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Create
							SuperAdmin</span></a></li>
				<li><a href="<%=UrlConstants.TENANT_LIST%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Tenant</span></a></li>
				<li><a href="<%=UrlConstants.CARTYPE_LIST%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Car
							Type</span></a></li>
				<li><a href="<%=UrlConstants.CARNAME_LIST%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Car
							Model</span></a></li>
				<li><a href="#"><i class="fa fa-home fa-fw"></i><span
						class="menu-title">Ticket</span></a></li>
				<li><a href="<%=UrlConstants.PACKAGE_LIST%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Package
					</span></a></li>
				<li><a href="<%=UrlConstants.PENDING_VERIFICATION_LIST%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Pending
							Verification </span></a></li>
				<li><a href="<%=UrlConstants.TAX_CATEGORY%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Tax
							Category</span></a></li>
				<li><a href="<%=UrlConstants.TAX_SLAB%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Tax
							Slab</span></a></li>
							<li><a href="<%=UrlConstants.USER_ROLE%>"><i
					class="fa fa-home fa-fw"></i><span class="menu-title">User Role</span></a></li>
				<li><a href="<%=UrlConstants.BOOKED_STATUS%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Booked
							Car Status</span></a></li>
			</security:authorize>
			<security:authorize access="hasRole('ROLE_SUPERADMIN')">
			<%--Updated By-Johnson Chunara On 22-03-2017 As Per SRS--%>
				<%--<li><a href="<%=UrlConstants.LIST_RATE_OF_CONTRACT%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Rate
							Of Contract</span></a></li>--%>
				<li><a href="<%=UrlConstants.LIST_IINVOICE_PACKAGE%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Invoice
							Package</span></a></li>
				<!--super admin can create the departments to handle the tickets --->
				<li><a href="<%=UrlConstants.ADD_DEPARTMENT_TYPE%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Add
							Department Type</span></a></li>
				<li><a href="<%=UrlConstants.VIEW_DEPARTMENT_TYPE%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title"><View></View>
							Department Type</span></a></li>

			</security:authorize>

			<!-- <li><a href="addcompany.htm"><i class="fa fa-home fa-fw"></i><span
						class="menu-title">Add Company</span></a></li>
				<li><a href="addPaymentmode.htm"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Add
							Payment Mode</span></a></li> -->

			<!--Role Ticket Manager Starts----------- -->
			<security:authorize access="hasRole('ROLE_TICKET_HANDLER')">
				<li><a href="<%=UrlConstants.VIEW_TICKET_TICKET_HANDLER%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">View
							Tickets</span></a></li>
				<li><a href="<%=UrlConstants.VIEW_TICKET_ASSIGNMENT_HISTORY%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">View
							Ticket Transfer History</span></a></li>
				<li><a href="<%=UrlConstants.USER_TICKET_STATUS%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Assigned Tickets - MY Status</span></a></li>
			</security:authorize>

			<!-- Role Ticket manager Ends --------------------->

			<!--Role Ticket Department Handler Starts------------->
			<security:authorize access="hasRole('ROLE_SALE')">
				<li><a href="<%=UrlConstants.VIEW_TICKET_TICKET_HANDLER%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">View
							Tickets</span></a></li>
				<li><a href="<%=UrlConstants.USER_TICKET_STATUS%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Assigned Tickets - MY Status</span></a></li>
			</security:authorize>

			<!-- Role Department Handler Ends--------------------->


			<!--Role Account Handler Starts------------->
			<security:authorize access="hasRole('ROLE_ACCOUNTANT')">
				<li><a href="<%=UrlConstants.VIEW_TICKET_TICKET_HANDLER%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">View
							Tickets</span></a></li>
				<li><a href="<%=UrlConstants.USER_TICKET_STATUS%>"><i
						class="fa fa-home fa-fw"></i><span class="menu-title">Assigned Tickets - MY Status</span></a></li>
			</security:authorize>

			<!-- Role Account Handler Ends--------------------->

		</ul>
	</div>
</nav>