<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/assets/scripts/jquery.validate.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#CarNameDTL").DataTable();
	});
	$(document).ready(function() {
        $("#addpayment").validate({
            rules : {
                paymentmode : {
                    required : true
                }
            },
            // Specify the validation error messages
            messages : {
                paymentmode : "Please Enter Payment Mode"
            },
            submitHandler : function(form) {
                $("#addpayment").attr("action", "savepaymentmode.htm?${_csrf.parameterName}=${_csrf.token}");
                form.submit();
            }
        });
    });
</script>
<!--BEGIN CONTENT-->
<div class="page-content">
    <div class="row">
        <div class="col-lg-12">
            <div class="portlet">
                <div class="portlet-header">
                    <div class="caption">Payment Mode</div>
                </div>
                <div class="portlet-body">
                    <form:form method="POST" class="form-horizontal" id="addpayment" ondragstart="return false;" ondrop="return false;">
                        <input type="hidden" name="${_csrf.parameterName}"
                            value="${_csrf.token}" />
                        <form:input type="hidden" path="id" value="${paymentmode.id}"></form:input>
                        <div class="form-body pal">
                            <div class="form-group">
                                <label for="inputAddress" class="col-md-2 control-label"></label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <label for="inputAddress"><strong>${msg}</strong></label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputAddress" class="col-md-2 control-label">Payment
                                    Mode</label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <form:input class="form-control" maxlength="50"
                                            path="paymentmode" id="carName"
                                            value="${paymentmode.paymentmode }"></form:input>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputAddress" class="col-md-2 control-label">&nbsp;</label>
                                <div class="col-md-6">
                                    <input type="submit" value="Submit" class="btn btn-success" />
                                </div>
                            </div>
                        </div>
                    </form:form>
                    <c:if test="${!empty paymentdetails}">
                        <h2>Car Details</h2>
                        <table class="table table-striped table-bordered table-hover"
                            border="1" id="CarNameDTL">
                            <thead>
                                <tr>
                                    <th>Serial No</th>
                                    <th>Payment Mode</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${paymentdetails}" var="payment"
                                    varStatus="LoopCounter">
                                    <tr>
                                        <td><c:out value="${LoopCounter.count }" /></td>
                                        <td><c:out value="${payment.paymentmode }" /></td>
                                        <td align="center"><a class="btn btn-success"
                                            href="addPaymentmode.htm?pid=${payment.id }">Edit</a></td>
                                        <td align="center"><a class="btn btn-danger"
                                            href="deletepayment.htm?pid=${payment.id }">Delete</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>