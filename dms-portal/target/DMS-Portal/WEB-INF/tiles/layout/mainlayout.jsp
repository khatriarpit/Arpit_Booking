<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><tiles:getAsString name="title" /></title>
    <tiles:insertAttribute name="fileinclude" />
</head>
<body id="signin-page" class="light-grey">
	<tiles:insertAttribute name="header" />
	<!-- Body -->
	<tiles:insertAttribute name="body" />
</body>
</html>