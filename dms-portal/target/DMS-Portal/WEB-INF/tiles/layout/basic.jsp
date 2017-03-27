<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title><tiles:getAsString name="title" /></title>
	<tiles:insertAttribute name="fileinclude" />
</head>
<body>
	<div>
		<!-- Header -->
		<tiles:insertAttribute name="header" />
		<!-- wrapper -->
		<div id="wrapper">
			<!-- Navigation -->
			<tiles:insertAttribute name="navigation" />
			<!--BEGIN PAGE WRAPPER-->
			<div id="page-wrapper">
				<tiles:insertAttribute name="body" />
				<!-- Footer -->
				<tiles:insertAttribute name="footer" />
			</div>
		</div>
	</div>
</body>
</html>