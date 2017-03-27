<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#rateOfContractList").DataTable();
	});
</script>
<!--BEGIN CONTENT123-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-header">
					<div class="caption">
						<a class="btn btn-success" href="<%=UrlConstants.RATE_OF_CONTRACT%>">Add
							Rate Of Contract</a>
					</div>
				</div>
				<c:if test="${!empty message}">
					<div class="input-icon right">
						<label class="error"><strong>${message}</strong></label>
					</div>
				</c:if>
				<c:if test="${!empty rateOfContractList}">
					<h2>List Of Contracts
					</h2>
					<div>
						<table class="table table-striped table-bordered table-hover"
							align="left" border="1" id="rateOfContractList">
							<thead>
								<tr>
									<th>Serial No</th>
									<th>Rate Of Contract</th>
									<th align="center">Edit / Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${rateOfContractList}" var="rate"
									varStatus="LoopCounter">
									<tr>
										<td>${LoopCounter.count}</td>
										<td>${rate.rateOfContract }</td>
										<td align="center"><a class="glyphicon glyphicon-pencil"
											href="<%=UrlConstants.RATE_OF_CONTRACT %>?rateOfContractId=${rate.id}" title="Edit"></a>  |
										<a class="glyphicon glyphicon-trash" title="Delete"
											href="<%=UrlConstants.DELETE_RATE_OF_CONTRACT %>?rateOfContractId=${rate.id}"
											onclick="return confirm('Are you sure want to Delete this Rate Of Contract?')"></a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
			</div>
		</div>
	</div>
