<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!--BEGIN CONTENT-->
<div class="page-content">
    <div class="row">
        <div class="col-lg-12">
            <div class="portlet">
                <div class="caption">
                    <c:if test="${not empty message}">
                    	<div class="alert alert-success-message">${message}</div>
                    </c:if>
<%--                     <a class="btn btn-success" title="Add Tenant" href="<%=UrlConstants.TENANT%>">Add Tenant</a> --%>
                </div>
                <div class="form-group">
                    <h2>Sell All Seller</h2>
                    <table id="tenantList" class="table table-striped table-bordered table-hover" border="1">
                        <thead>
                            <tr>
                                <th>Serial No</th>
                                <th>Tenant ID</th>
                                <th>Tenant Name</th>
                                <th>Seller ID</th>
                                <th>Phone Number</th>
                                <th>Status</th>
                                <th>Balance</th>
<!--                                 <th>Status Of Tenant</th> -->
                                <!-- <th class="text-center" colspan="2">Action Button</th> -->
<!--                                 <th class="text-center">Action Button</th> -->
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${not empty tenantDetailsList}">
                             <c:forEach items="${tenantDetailsList}" var="tenant" varStatus="loopcounter">
                                <tr>
                                    <td>${loopcounter.count}</td>
                                    <td>${tenant.tanentid}</td>
                                     <td>${tenant.companyname}</td>
                                   <td>${tenant.sellerModel.sellerId }</td> 
                                    <td>${tenant.contactno}</td>
                                    <td>
                                    	<c:if test="${tenant.status eq '0'}">Active</c:if>
                                    	<c:if test="${tenant.status eq '1'}">Inactive</c:if>
                                    	<c:if test="${tenant.status eq '2'}">Draft</c:if>
                                    </td> 
                                     <td>${tenant.sellerModel.balance }</td> 
                                    <td width="16%" align="center" class="tenant-action">
                                    	<%-- <div class="pull-left">
	                                    	<a title="Edit" class="btn glyphicon glyphicon-pencil" href="<%=UrlConstants.TENANT%>?id=${tenant.id}"></a>
	                                    </div>	 --%>
	                                    <%-- <div class="pull-left">
	                                    	<a title="Delete" class="btn glyphicon glyphicon-trash" onclick="return confirm('Are you sure want to Delete this Tenant?')" href="<%=UrlConstants.DELETE_TENANT%>?id=${tenant.id}" ${tenant.packageEnable eq true ? 'disabled=' : ''}></a>
                                    	</div> --%>
                                    	<%-- <div class="pull-left">	
                                    		<a ${tenant.status eq '2' ? 'disabled': ''} type="submit" title="Assign Package" class="btn fa fa-dropbox " href="<%=UrlConstants.CREATE_PACKAGE_LIST%>?tanentID=${tenant.id}"></a>
                                   		</div> --%>
                                    </td>
                                </tr>
                            </c:forEach> </c:if>
                        </tbody>
                    </table>
            	</div>
            </div>
        </div>
    </div>
</div>