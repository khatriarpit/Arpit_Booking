<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')">
	<div class="page-footer">
		<div class="copyright">
			<div class="drop dbgrey-font">
				<ul class="drop_menu">
					<security:authorize access="hasRole('ROLE_ADMIN')">
						<li><a href='<%=UrlConstants.CAR %>'>CAR</a></li>
						<li><a href='#'>||</a></li>
						<li><a href="<%=UrlConstants.DRIVER_LIST%>">DRIVER</a></li>
						<li><a href="#">||</a></li>
						<li><a href="<%=UrlConstants.CHANGE_DRIVER %>">CHANGE DRIVER</a></li>
						<li><a href="#">||</a></li>
						<li><a href="<%=UrlConstants.CAR_DRIVER %>">CAR DRIVER</a></li>
					</security:authorize>
					<security:authorize access="hasRole('ROLE_SUPERADMIN')">
						<li><a href='<%=UrlConstants.CARNAME_LIST%>'>CAR NAME</a></li>
						<li><a href='#'>||</a></li>
						<li><a href='<%=UrlConstants.CARTYPE_LIST%>'>CAR TYPE</a></li>
						<li><a href="#">||</a></li>
						<li><a href="#">ADD TAX CATEGORY</a></li>
						<li><a href="#">||</a></li>
						<li><a href="#">ADD TAX SLAB</a></li>
					</security:authorize>
					
				</ul>
			</div>
		</div>
	</div>
</security:authorize>