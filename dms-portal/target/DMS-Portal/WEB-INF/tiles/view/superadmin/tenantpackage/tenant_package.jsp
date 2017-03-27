<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
	$(document).ready(function() {
		$('#fromDate').datepicker({
			dateFormat : 'dd/mm/yy',
			minDate : 0,
			onSelect : function(date) {
				updateAb();
			}
		});

		function toValidDate(datestring){
		    return datestring.replace(/(\d{2})(\/)(\d{2})/, "$3$2$1");
		}


		$("body").delegate("#validity", "change",function(){
			if($('#fromDate').val() != "") {
				updateAb();
			}
		});

		function updateAb(){
			var pickupdate = new Date(toValidDate($('#fromDate').val()));
            var validity = $("#validity").val() - 1;
            if(validity === "0") {
            	validity = 0;
            }
			var date = new Date();
            date.setTime( pickupdate.getTime() + validity * 86400000 );
			var month = parseInt(date.getMonth());
			month = month +1;
			var d1 = date.getDate() + "/" +  month + "/" + date.getFullYear();
			document.getElementById('toDate').value = d1;
          
        }

	    checkPackage();
        jQuery.validator.addMethod("daysvalidataion", function(
                value, element) {
            var val = value;
            if (val > 365) {
                return false;
            } else {
                return true;
            }
        }, "Please Enter Days Below 365");

        jQuery.validator.addMethod("errorZero", function(value, element) {
            var val = value;
            if (val === "0") {
                return false;
            } else {
                return true;
            }
        }, "Please Select Tax Type");

        $("#addPackage").validate({
            rules : {
                cars : {
                    required : true,
                    errorZero : true
                },
                carrate : {
                    required : true,
                    errorZero : true
                },
                users : {
                    required : true,
                    errorZero : true
                },
                userrate : {
                    required : true,
                    errorZero : true
                },
                drivers : {
                    required : true,
                    errorZero : true
                },
                driverrate : {
                    required : true,
                    errorZero : true
                },
                validity :{
                    required:true,
                    daysvalidataion: true,
                    maxlength : 3
                },
                fromDate :{
                	required:true
                },
                toDate :{
                	required:true
                }
            },
            messages : {
                cars : {
                    required : "Please enter Cars!",
                    errorZero: "Please Greater then Zero Value!"
                },
                carrate : {
                    required:"Please enter Rate per car!",
                    errorZero: "Please Greater then Zero Value!"
                },
                users : {
                    required: "Please enter Users!",
                    errorZero: "Please Greater then Zero Value!"
                },
                userrate : {
                    required: "Please enter Rate per User!",
                    errorZero: "Please Greater then Zero Value!"
                },
                drivers : {
                    required: "Please enter Drivers!",
                    errorZero: "Please Greater then Zero Value!"
                },
                driverrate : {
                    required:"Please enter Rate per Driver!",
                    daysvalidataion:"Please Enter Days Below 365"
                },
                validity : {
                    required:"Please Enter Validity",
                    daysvalidataion:"Please Enter Days Below 365",
                    maxlength : "Max Length 3 Digit"
                },
                fromDate :{
                	required:"Please Select Date"
                },
                toDate :{
                	required:"Please Select Date"
                }
            },
            submitHandler : function(form) {
            	if(fromValidation()){
                    $("#addPackage").submit();
            	}
            }
        });

        $("body").delegate(".package-name", "change", function(){
        	var packageName = $(this).val();
        	if(packageName != '' || packageName != 0){
        		$(".package-name-error").hide();
        	} else {
        		$(".package-name-error").show();
        	}
        });

        function fromValidation(){
        	var packages = $("#packageTypeId").val();
        	if(packages != 0){
        		$("#package-selected-type").hide();
       			var packageId = $(".package-name").val();
       			if(packageId != '') {
       				$(".package-name-error").hide();
       			} else {
       				$(".package-name-error").show();
       				return false;
       			}
        	}else{
        		$("#package-selected-type").show();
        		return false;
        	}
        	return true;
        }

        $("body").delegate("#packageNameID", "change", function(){
        	var packageName = $(this).val();
        	$.ajax({
                type : "POST",
                url : "<%=UrlConstants.GET_PACKAGE%>",
    			data : "${_csrf.parameterName}=${_csrf.token}&packageName="
    					+ packageName,
    			success : function(data) {
    				if (data != '') {
    					$("#validity").val(data.validity);
    					$("#cars").val(data.cars);
    					$("#carrate").val(data.carrate);
    					$("#users").val(data.users);
    					$("#userrate").val(data.userrate);
    					$("#drivers").val(data.drivers);
    					$("#driverrate").val(data.driverrate);
    					$("#additionalCharges").val(data.additionalCharges);
    					$("#totalAmount").val(data.totalAmount);
    					mulcars();
    					mulusers();
    					muldrivers();
    					sumofamount();
    					return true;
    				} else {
    					clearData();
    					return false;
    				}
    			},
    			error : function(e) {
    			}
    		});
        });
    });

    function checkPackage(){
        var isTrue = false;
        var packageId = $("#packageTypeId").val();
        if(packageId != 0){
        	$("#packageNameDiv").show();
            $(".portlet-body-list").show();
            if(packageId != 1) {
            	isTrue = true;
                $(".portlet-body-list :input").attr("readonly", "readonly");
                var tag = '';
                tag += '<div class="input-icon right">'+
							'<select class="form-control package-name" name="name" id="packageNameID" itemValue="name">'+
								'<option value="0">Select Package Name</option>'+
								'<c:forEach var="packageList" items="${packageList}">'+
									'<option value="${packageList.name},${packageList.id}" <c:if test="${packageList.name eq packages.name}"> selected="selected"</c:if>>${packageList.name}</option>'+
								'</c:forEach>'+
							'</select>'+
							'<label class="error package-name-error" style="display:none;">Please Select Package Name</label>'+
							'<c:if test="${not empty message}">'+
								'<label class="error">${message}</label>'+
							'</c:if>'+
						'</div>';
                $("#nameID").html(tag);
            } else {
            	isTrue = false;
            	$(".portlet-body-list :input").removeAttr("readonly");
            	$("#fromDate").removeAttr("readonly");
                $("#toDate").removeAttr("readonly");
                $("#usersamount").attr("readonly", "readonly");
                $("#caramount").attr("readonly", "readonly");
                $("#driversamount").attr("readonly", "readonly");
                $("#totalAmount").attr("readonly", "readonly");
                var tag = '';
                tag += '<div class="input-icon right">'+
							'<input class="form-control package-name" maxlength="50" name="name" value="${packages.name}" placeholder="Enter Name" />'+
							'<label class="error package-name-error" style="display:none;">Please Enter Package Name</label>'+
							'<c:if test="${not empty message}">'+
								'<label class="error">${message}</label>'+
							'</c:if>'+
						'</div>';
                $("#nameID").html(tag);
            }
        } else {
            isTrue = false;
            $(".portlet-body-list").hide();
            $("#packageNameDiv").hide();
        }
        if(isTrue){
            if(packageId != 1) {
                var validator = $("#addPackage").validate();
                validator.resetForm();
                $("#addPackage > input[type=text]").each(function() {
                    $(this).removeClass("error");
                });
            }
		}
	}

    function clearData(){
    	$("#validity").val('');
		$("#cars").val('');
		$("#carrate").val('');
		$("#users").val('');
		$("#userrate").val('');
		$("#drivers").val('');
		$("#driverrate").val('');
		$("#additionalCharges").val('');
		$("#totalAmount").val('');
		$("#usersamount").val('');
		$("#caramount").val('');
		$("#driversamount").val('');
		$("#fromDate").val('');
		$("#toDate").val('');
    }

    $("body").delegate("#packageTypeId", "change", function(){
    	var packages = $(this).val();
    	if(packages != 0){
    		checkPackage();
    		clearData();
    		if(packages === 1) {
    			$(".package-name").val('');
    		} else {
    			var temp = 0;
    			$('#packageNameID').val(temp).attr("selected", "selected");
    			$("#packageNameID option[value='"+ temp +"']").attr("selected", "selected");
    			$("#packageNameID").val(temp);
    		}
    		$("#package-selected-type").hide();
    	} else {
    	    clearData();
    	    checkPackage();
    		$("#package-selected-type").show();
    	}
    })

    function toValidDate(datestring){
        return datestring.replace(/(\d{2})(\/)(\d{2})/, "$3$2$1");
    }

        $("body").delegate("#addPackage", "submit", function(){

            var validity = $("#validity").val();
               var pickupDate= $("#fromDate").val();
               var dropdate= $("#toDate").val();

                    var pickDate = new Date(toValidDate(pickupDate));
                    var dropDate = new Date(toValidDate(dropdate));
                    var diff = new Date(dropDate - pickDate);
                    var days = (diff)/1000/60/60/24;
                    days=days+1;

            if(days > validity){
                $('#diffError').show();
                return false;
            }else{
                $('#diffError').hide();
                return true;
            }
        });

</script>
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-header">
					<div class="caption">(${companyName}) : Master create package/subscription</div>
				</div>
				<div class="portlet-body">
					<form:form method="POST" class="form-horizontal" action="<%=UrlConstants.SAVE_CREATE_PACKAGE%>" id="addPackage" ondragstart="return false;" ondrop="return false;">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<div class="form-body pal">
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label"></label>
								<div class="col-md-6">
									<div class="input-icon right">
										<label for="inputAddress" class="error"><strong>${msg}</strong></label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="inputAddress" class="col-md-2 control-label">Package
									Type</label>
								<div class="col-md-6">
									<div class="input-icon right">
										<form:select class="form-control" itemValue="id"
											path="packageStatus" id="packageTypeId">
											<option value="0">Select Package</option>
											<option value="1" <c:if test="${packages.packageStatus eq '1'}"> selected="selected"
												</c:if>>Flexible</option>
											<option value="2" <c:if test="${packages.packageStatus eq '2'}"> selected="selected"
												</c:if>>Fixed</option>
										</form:select>
										<label class="error" id="package-selected-type" style="display: none;">Please Select Package Type</label>
									</div>
								</div>
							</div>
							<div class="form-group" id="packageNameDiv" style="display: none;">
								<label for="inputAddress" class="col-md-2 control-label">Package
									Name</label>
								<div class="col-md-6" id="nameID"></div>
							</div>
							<div class="portlet-body-list" id="hd" style="display: none;">
								<form:input path="id" type="hidden" value="${packages.id}" />
								<form:input path="status" type="hidden" value="${packages.status}" />
								<%@include file="../common-package-page.jsp"%>
							</div>

							<div class="form-group col-md-8">
								<div class="col-md-6">
									<label for="inputAddress" class="col-md-6 control-label">Subscription From</label>
									<div class="col-md-6" id="nameID">
									<fmt:formatDate value="${packages.fromDate}" pattern="dd/MM/yyyy" var="fromDate" />
									<form:input path="fromDate" value="${fromDate}" class="form-control"/>
									</div>
								</div>
								<div class="col-md-6">
									<label for="inputAddress" class="col-md-6 control-label">Subscription To</label>
									<div class="col-md-6" id="nameID">
									<fmt:formatDate value="${packages.toDate}" pattern="dd/MM/yyyy" var="toDate" />
									<form:input path="toDate" readonly = "true" value="${toDate}" class="form-control readOnly" />
									</div>
								</div>
								<div class="col-md-12">
									<label id="diffError" style="display: none; color:#FF0000;" class="col-md-12 control-label" >
									Please Enter Subscription To Date and Subscription From Date As Per Validity  </label>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<div class="col-md-4"></div>
									<c:if test="${packages.id eq null}">
										<button type="submit" id="submit-form" class="btn orange_bg">Submit</button>&nbsp;<a
											href="<%=UrlConstants.CREATE_PACKAGE_LIST%>?tanentID=${tanentID}"
											class="btn grey_bg">Cancel</a>
									</c:if>
									<c:if test="${packages.id != null}">
										<a href="<%=UrlConstants.CREATE_PACKAGE_LIST%>?tanentID=${tanentID}"
											class="btn grey_bg">Back</a>
									</c:if>
								</div>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>