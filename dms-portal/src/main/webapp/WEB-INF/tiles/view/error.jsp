<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="wrapper">
	<!--BEGIN CONTENT-->
	<div class="page-content">
		<div class="row">
			<div class="col-lg-12">
				<div class="portlet">
					<div class="portlet-header">
						<div class="caption">HTTP Status Error - ${not empty error ? error : 'Access Denied'}</div>
					</div>
					<div class="portlet-body">
						<h2>${content}</h2>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--END CONTENT-->
</div>