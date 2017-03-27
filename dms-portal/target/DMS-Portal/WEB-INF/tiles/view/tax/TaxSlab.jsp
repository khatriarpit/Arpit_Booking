<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#taxslab").DataTable();
	});
	$(document).ready(function() {
        jQuery.validator.addMethod("taxCategory", function(
                value, element) {
            var val = value;
            if (val === "0") {
                return false;
            } else {
                return true;
            }
        }, "Please Select Tax Category");
        
        $(document).ready(function() {
            jQuery.validator.addMethod("invoicePackage", function(
                    value, element) {
                var val = value;
                if (val === "0") {
                    return false;
                } else {
                    return true;
                }
            }, "Please Select Invoice Package");
        $("#taxSlabForm").validate({
            rules : {
            	taxcategoryid : {
                    required : true,
                    taxCategory : true
                },
                
                invoicecatid : {
                    required : true,
                    invoicePackage : true
                },
                slabName : "required",
                rate : "required"
            },
            messages : {
            	taxcategoryid : "Please Select Category",
                invoicecatid : {
                    required : "Please Select Invoice Package",
                    invoicePackage : "Please Select Invoice Package"
                },
                slabName : "Please enter Slab Name",
                rate : "Please Enter rate",
            },
            submitHandler : function(form) {
                $("#taxSlabForm")
                        .attr("action",
                                "<%=UrlConstants.SAVE_TAXSLAB%>?${_csrf.parameterName}=${_csrf.token}");
                form.submit();
            }
        });
    });
});
</script>
<!--BEGIN CONTENT-->
<div class="page-content">
    <div class="row">

        <div class="col-lg-12">
            <div class="portlet">
                <div class="portlet-header">
                    <div class="caption">Add Tax Slab</div>
                </div>
                <div class="portlet-body">
                    <form:form modelAttribute="command" method="POST" id="taxSlabForm"
                        class="form-horizontal" ondragstart="return false;" ondrop="return false;">
                        <form:input class="form-control" type="hidden" path="id"
                            value="${taxSlab.id}" />
                        <div class="form-group">
                            <label for="inputAddress" class="col-md-2 control-label">Tax
                                Name</label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <form:select class="form-control" path="taxcategoryid"
                                        itemValue="taxcategoryid">
                                        <form:option value="0">Select category</form:option>
                                        <c:forEach var="nameList" items="${TaxDetails}">
                                            <form:option value="${nameList.id}"
                                                selected="${nameList.id eq taxSlab.taxcategoryid ? 'selected' : ''}">${nameList.name}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                        </div>
                         <div class="form-group">
                            <label for="inputAddress" class="col-md-2 control-label">Invoice
                                Category</label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <form:select class="form-control" path="invoicecatid"
                                        itemValue="invoicecatid">
                                        <form:option value="0">Select Invoice category</form:option>
                                        <c:forEach var="invoiceList" items="${invoiceCategory}">
                                            <form:option value="${invoiceList.id}"
                                                selected="${invoiceList.id eq taxSlab.invoicecatid ? 'selected' : ''}">${invoiceList.categoryname}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputAddress" class="col-md-2 control-label">Tax
                                Rate</label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <form:input class="form-control" path="rate" type="text"
                                        value="${taxSlab.rate}" />
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputAddress" class="col-md-2 control-label">&nbsp;</label>
                            <div class="col-md-6">
                                <input class="btn btn-success" class="submit" type="submit"
                                    value="Submit" />
                            </div>
                        </div>
                    </form:form>
                    <div class="form-body pal">
						<div class="form-group">
	                        <h2>Tax Category List</h2>
	                        <div>
	                            <table class="table table-striped table-bordered table-hover"
	                                id="taxslab" align="left" border="1">
	                                <thead>
	                                    <tr>
	                                        <th>Serial No</th>
	                                        <th>Tax Name</th>
	                                        <th>Invoice</th>
	                                        <th>Rate</th>
	                                        <th>Edit</th>
	
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                    <c:forEach items="${SlabDetails}" var="SlabDetails"
	                                        varStatus="loopcounter">
	                                        <tr>
	                                            <td>${loopcounter.count}</td>
	                                            <td>${SlabDetails.taxCategory.name}</td>
	                                            <td>${SlabDetails.invoicePackage.categoryname}</td>
	                                            <td>${SlabDetails.rate}</td>
	                                            <td align="center"><a class="glyphicon glyphicon-pencil"  title="Edit"
												href="<%=UrlConstants.TAX_SLAB%>?taxslabId=${SlabDetails.id}"></a><!-- &nbsp;|&nbsp; -->
												<%--Updated By-Johnson Chunara On 22-03-2017 As Per SRS--%>
												<%-- <a class="glyphicon glyphicon-trash" title="Delete"
												href="<%=UrlConstants.DELETE_TAXSLAB%>?taxslabId=${SlabDetails.id}"></a></td> --%>
	                                        </tr>
	                                    </c:forEach>
	                                </tbody>
	                            </table>
	                        </div>
	                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>