<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/assets/scripts/jquery.validate.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    $("#manual-invoice").validate({
        rules : {
        	amount : "required"
        },
        messages : {
        	amount : "Please Enter amount",
        },
        submitHandler : function(form) {
            $("#manual-invoice").attr("action", "<%=UrlConstants.SAVE_MANUAL_INVOICE%>?${_csrf.parameterName}=${_csrf.token}");
				form.submit();
			}
	});
});

$("body").delegate("#amount", "change", function(){
	var amountId = $("#amount").val();
	var invoicecategoryId = $("#category").val();
	if(amountId != '0'){
		$.ajax({
			type:'POST',
			url:'<%=UrlConstants.MANUAL_TAX%>',
			data : "${_csrf.parameterName}=${_csrf.token}&amount=" + amountId+"&invoicecategory="+invoicecategoryId,
			success:function(data){
				 $('#tax').val(data);
				 var amount = $('#amount').val();
			     var tax = $('#tax').val();
			     var totalAmount = parseFloat(amount) + parseFloat(tax);
			     if (!isNaN(totalAmount)) {
			        $('#totalAmount').val(totalAmount);
			     }
			 }
		});
	}
});

</script>
<!--BEGIN CONTENT-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-body">
					<div class="portlet-header">
						<div class="caption box-caption">Manual Invoice</div>
					</div>
					<div class="portlet-body">
						<form:form class="form-horizontal" modelAtttributte="command"
							method="POST" id="manual-invoice" ondragstart="return false;" ondrop="return false;">
                            <input type="hidden" name="${_csrf.parameterName}"
                                value="${_csrf.token}" />
                            <form:input type="hidden" path="id"
                                  value="${InvoiceDetails.id}"></form:input>
                            <form:input type="hidden" path="tripId"
                                value="${clientModel.tripId}"></form:input>
                                <input type="hidden"  id="category" value="${clientModel.invoicePackage.id}"></input>
                                <form:input type="hidden" path="tanentID" value="${InvoiceDetails.tanentID}"></form:input>
                                <form:input type="hidden" path="invoiceNo" value="${InvoiceDetails.invoiceNo}"></form:input>
							<div class="form-group">
								<div class="col-md-12">
									<label class="col-md-6">Trip
										ID : ${clientModel.tripId}</label> <label
										class="col-md-6">Driver Name :
										${clientModel.driver.fullName}</label>
								</div>
								<div class="col-md-12">
									<label class="col-md-6">Customer
										Name : ${clientModel.guest.personName}</label> <label
										 class="col-md-6">Mobile
										: ${clientModel.guest.contactNo}</label>
								</div>
								<div class="col-md-12">
									<label class="col-md-6">Car
										Number : ${clientModel.car.carNo}</label> <label 
										class="col-md-6">Invoice Category :
										${clientModel.invoicePackage.categoryname}</label>
								</div>
								<div class="col-md-12">
									<label class="col-md-6">Booking
										from : ${clientModel.pickUpDateTime}</label> <label 
										class="col-md-6">Booking to:
										${clientModel.dropDateTime}</label>
								</div>
							</div>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">Km<span class='require'>*</span>
								</label>
								<div class="col-md-6">
									<div class="input-icon right">
										<form:input class="form-control" path="km"
											value="${InvoiceDetails.km}"></form:input>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">Amount<span class='require'>*</span>
								</label>
								<div class="col-md-6">
									<div class="input-icon right">
										<form:input class="form-control" path="amount"
											value="${InvoiceDetails.amount}"></form:input>
									</div>
								</div>
							</div>
							<div class="form-group">
                                <label class="col-md-2 control-label">Tax Inclusive</label>
                                <div class="col-md-1">
                                    	<form:checkbox class="form-control" path="taxInclusive" value = "" checked="checked"/>
                                </div>
                            </div>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">Tax<span class='require'>*</span>
								</label>
								<div class="col-md-6">
									<div class="input-icon right">
										<form:input class="form-control" path="tax" onchange="sum();" readonly="true"
											value="${InvoiceDetails.tax}" id="tax"></form:input>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">Total Amount<span class='require'>*</span>
								</label>
								<div class="col-md-6">
									<div class="input-icon right">
										<form:input class="form-control" path="totalAmount"
											value="${InvoiceDetails.totalAmount}" readonly="true"></form:input>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-3 col-md-offset-2">
									<div class="input-icon">
										  <button class="btn orange_bg" type="submit">Save Invoice</button>
										  <a href="<%=UrlConstants.LIST_BOOKEDCARS%>" class="btn grey_bg">Cancel</a>
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>