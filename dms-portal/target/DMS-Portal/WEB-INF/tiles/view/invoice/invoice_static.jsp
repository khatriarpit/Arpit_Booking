<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(document).ready(function() {
	  var max_fields      = 10;
      var wrapper         = $(".container1");
      var add_button      = $(".add_form_field");
      var x = 1;

       $(add_button).click(function(){
          if(x < max_fields){
               x++;
                $(wrapper).append('<div class="col-md-8 input-icon right"><input id="terms"  type="text" name="termsConditions['+x+']"/><a href="#" class="delete" data-delete-raw="'+x+'">Delete</a></div>'); //add input box
          }
          else {
             alert('You Reached the limits')
          }
       });

      $(wrapper).on("click",".delete", function(e){
          e.preventDefault(); $(this).parent('div').remove(); x--;
      });

	 $("#invoice-static").validate({
			rules : {
				companyName : "required",
	               address1 : "required",
	               address2 : "required",
	               address3 : "required",
	               emailid : {
	                   required : true
	               },
	               phoneNo : {
	                   required : true,
	                   minlength : 10
	               },
	               image : {
					required : true,
				}
	           },
	           messages : {
	           	companyName  : "Please enter your Company Name",
	           	address1  : "Please enter your Address",
	           	address2  : "Please enter your Address",
	           	address3  : "Please enter your Address",
	               phoneNo : {
	                   required : "Please provide a Mobile Number",
	                   minlength : "Your Mobile Number must be at least 10 characters long"
	               },
	               emailid : "Please enter a valid email address",
	               image : "Please Upload Company Logo"
	           },
			submitHandler : function(form) {
				$("#invoice-static").attr("action", "<%=UrlConstants.SAVE_INVOICE_STATIC%>?${_csrf.parameterName}=${_csrf.token}");
		        form.submit();
			}
		});
	});
		
	function onlyNos(e, t) {
	    try {
	        if (window.event) {
	            var charCode = window.event.keyCode;
	        } else if (e) {
	            var charCode = e.which;
	        }
	        else { return true; }
	        if (charCode > 31 && (charCode < 48 || charCode > 57 || (charCode === 46 && dotvalue === false) )) {
	           if(charCode === 46){
	           	dotvalue = true;
	           	return true;
	           }
	           else{
	           	return false;}
	       	//return false;
	        }else{
	       	 return true;
	        }
	    }
	    catch (err) {
	        alert(err.Description);
	    }
	}
	
	function onlyAlphabets(e, t) {
        try {
            if (window.event) {
                var charCode = window.event.keyCode;
            }
            else if (e) {
                var charCode = e.which;
            }
            else { return true; }
            if (charCode == 8 || (charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123))
                return true;
            else
                return false;
        }
        catch (err) {
            alert(err.Description);
        }
	}

	function preview(){
		//var imgFile = $("#imgFile").val();
		var companyName = $("#companyName").val();
		var address1 = $("#address1").val();
		var address2 = $("#address2").val();
		var address3 = $("#address3").val();
		var emailid = $("#emailid").val();
		var phoneNo = $("#phoneNo").val();
		var landlineNo = $("#landlineNo").val();
		var note = $("#note").val();
		/* alert($('input[name="termsConditions[]"]').val());
		$("#terms").each(function(){
           alert($(this).val());
        });
		var termsConditions = $('input[name="termsConditions[]"]').val(); */
		$.ajax({
		   type:'POST',
	       url:"<%=UrlConstants.PREVIEW%>",
	       data:'${_csrf.parameterName}=${_csrf.token}&companyName='+companyName+'&address1='+address1+'&address2='+address2+'&address3='+address3+'&emailid='+emailid+'&phoneNo='+phoneNo+'&landlineNo='+landlineNo+'&note='+note,//+'&termsConditions='+termsConditions,
	       error:function(){
	        alert("error");
	       }
		});
	}
</script>
<div>
	<!--BEGIN CONTENT-->
	<div class="page-content">
		<div class="row">
			<div class="col-lg-12">
				<div class="portlet">
					<div class="portlet-header">
						<div class="caption">Invoice Static</div>
					</div>
					<div class="box-body">
						<form:form method="POST" class="form-horizontal"
							modelAttribute="command" enctype="multipart/form-data"
							id="invoice-static" ondragstart="return false;"
							ondrop="return false;">
							<form:input type="hidden" path="id" value="${Invoice.id}" />
							<form:input path="tanentID" type="hidden"
								value="${Invoice.tanentID}" />
							<div class="form-body pal">
								<div class="col-md-9">
									<div class="form-group">
										<label for="inputAddress" class="col-md-4 control-label">Company
											Name <span class='require'>*</span>
										</label>
										<div class="col-md-8">
											<div class="input-icon right">
												<form:input class="form-control" path="companyName"
													maxlength="50"
													value="${Invoice.companyName}"
													placeholder="Enter Company Name"></form:input>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-4 control-label">Address<span
											class='require'>*</span></label>
										<div class="col-md-8">
											<div class="col-md-12">
												<div class="col-md-4">
													<div class="input-icon right">
														<form:input class="form-control" path="address1"
															value="${Invoice.address1}" />
													</div>
												</div>
												<div class="col-md-4">
													<div class="input-icon right">
														<form:input class="form-control" path="address2"
															value="${Invoice.address2}" />
													</div>
												</div>
												<div class="col-md-4">
													<div class="input-icon right">
														<form:input class="form-control" path="address3"
															value="${Invoice.address3}" />
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-4 control-label">Email
											ID <span class='require'>*</span>
										</label>
										<div class="col-md-8">
											<div class="input-icon right">
												<form:input class="form-control" path="emailid" id="emailid"
													maxlength="100" type="email" value="${Invoice.emailid}"
													placeholder="Enter Email ID" />
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-4 control-label">Mobile
											No <span class='require'>*</span>
										</label>
										<div class="col-md-8">
											<div class="input-icon right">
												<form:input class="form-control" path="phoneNo"
													value="${Invoice.phoneNo}" maxlength="10"
													placeholder="Enter Mobile No" />
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-4 control-label">LaneLine
											No </label>
										<div class="col-md-8">
											<div class="input-icon right">
												<form:input class="form-control" path="landlineNo"
													maxlength="10" id="landlineNo"
													value="${Invoice.landlineNo}"
													onkeypress="return onlyNos(event,this);"
													placeholder="Enter LaneLine No." />
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-4 control-label">Note</label>
										<div class="col-md-8">
											<div class="input-icon right">
												<form:input class="form-control" path="note"
													value="${Invoice.note}" placeholder="Enter Note" />
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-4 control-label">Company
											Logo<span class='require'>*</span>
										</label>
										<div class="col-md-8 flot_for_hour_left">
											<c:if test="${Invoice.image != null}">
												<img width="15%" id="image"
													alt="${Invoice.image != '' ? Invoice.image : 'Company Logo Not Found'}"
													src="<%=UrlConstants.FETCH_IMAGE%>?imageName=${Invoice.image}"
													style="margin-bottom: 10px;" />
											</c:if>

											<form:input class="form-control" type="file" path="imgFile"
												value="${Invoice.image}" />
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-4 control-label">Terms
											& Conditions<span class='require'>*</span>
										</label>
										<div class="col-md-4 container1">
											<div class="input-icon right">
												<c:if test="${Invoice.id != null}">
													<c:forEach var="termsConditions"
														items="${Invoice.termsConditions}" varStatus="loop">
														<input class="form-control"
															name="termsConditions[${loop.count}]"
															value="${termsConditions}"
															placeholder="Enter Term and Conditions" />
														<br>
													</c:forEach>
												</c:if>
												<c:if test="${empty Invoice.id}">
													<input id="terms" class="form-control" name="termsConditions[0]"
														value="${Invoice.termsConditions}"
														placeholder="Enter Term and Conditions" />
													<br>
												</c:if>
											</div>
										</div>
									</div>
									<input class="add_form_field btn btn-success"
										value="Add More +"><br>
									<div class="form-group">
										<div class="col-md-11 col-md-offset-1">
											<hr>
											<div class="input-icon">
												<button type="submit" class="btn orange_bg">${Invoice.id != null ? 'Update' : 'Save'}</button>
												<!-- <button type="button" class="btn orange_bg"
													onclick="return preview();">Preview</button> -->
												<a href="<%=UrlConstants.LIST_INVOICE_STATIC%>" class="btn grey_bg"
													type="button">cancel</a>
											</div>
										</div>
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