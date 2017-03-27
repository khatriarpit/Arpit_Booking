<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
    $("body").delegate("#button-AcceptedId", "click", function(){
        var driverId=$("option:selected", $("#driId")).val();
        var carId=$("option:selected", $("#carid")).val();
        if(driverId==0 && carId==0 ){
            $('#selectOption').show();
            return false;
        }
        else {
            $('#driverId').val(driverId);
            $('#carId').val(carId);
            $("#changeRequestForm").attr("action", "<%=UrlConstants.CHANGE_REQUEST_ACCEPT%>");
            $("#changeRequestForm").submit();
        }
    });

    $("body").delegate("#button-RejectedId", "click", function(){
            $("#changeRequestForm").attr("action", "<%=UrlConstants.CHANGE_REQUEST_REJECT%>");
            $("#changeRequestForm").submit();
    });

    function changeDriver(selectObject) {
        var carId = $(selectObject).val();
            $.ajax({
                type:'POST',
                url:"<%=UrlConstants.GET_DRIVER_BY_CAR%>",
                data:'${_csrf.parameterName}=${_csrf.token}&carId='+carId,
                success:function(data){
                    if (data != '' ) {
                        $('#driId').val(data.id);
                    }
                },error : function(e) {
                }
            });
	}


</script>
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-body">
					<div class="box ">

						<div class="box-header">
							<div class="caption box-caption">Change Request </div>
						</div>

						<div class="form-group">
                        						<label for="inputAddress" class="col-md-2 control-label"></label>
                        						<div class="col-md-12">
                        						<label id="selectOption" class="pull-left" style="display: none; color:#FF0000; "
                                                						   for="inputAddress" class="col-md-6 control-label" >
                                                						Please Select Driver Or Car First..!!</label>
                        						</div>
                        					</div>
						<div class="box-body col-md-6">
			<form:form modelAttribute="command" method="POST" id="changeRequestForm" class="form-horizontal" ondragstart="return false;" ondrop="return false;">
								<div class="form-body pal">
									<c:if test="${changeRequestBy == null}">
										<div class="form-group" id=""><label for="inputAddress" class="col-md-3 control-label">Car</label>
                                             <input type="hidden" name="changeRequestBy" value="${changeRequestBy}"> 
											<div class="col-md-9 flot_for_hour_left">
												<select class="form-control" name="carid" id="carid" onchange="changeDriver(this);">
													<option value="0">Select Car</option>
													<c:forEach var="car" items="${listOfBookCar}">
														<option value="${car.carId}" selected="${carNo ne null ? 'selected' : ''}">${car.carName}-${car.carType}-${car.carNumber}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</c:if>
									<div class="form-group" id="change_driver"><label for="inputAddress" class="col-md-3 control-label">Driver</label>
										<div class="col-md-9 flot_for_hour_left">
											<select class="form-control" name="driId" id="driId" >
												<option value="0">Select Driver</option>
												<c:forEach var="driver" items="${driverList}">
													<option value="${driver.id}" >${driver.fullName}</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<input type="hidden"  name="driverId" id="driverId" >
									<input type="hidden"  name="carId" id="carId" >
									<input type="hidden"  name="clientId" id="clientId" value="${clientId}">
									<div class="form-group">
										<div class="col-md-6 col-md-offset-3">
											<div>

												<input class="btn orange_bg" type="submit" value="Accept" id="button-AcceptedId" />
												<input class="btn grey_bg" type="submit" value="Reject" id="button-RejectedId"/>
											</div>
										</div>
									</div>

								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div></div>
	</div>
</div>

