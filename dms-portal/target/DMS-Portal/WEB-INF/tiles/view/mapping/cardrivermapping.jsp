<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#carDriver").DataTable();
	});

    $(function(){
    	$("body").delegate(".car-form-submit", "click", function(){
            var carId = $(this).data("rowId");
            var mapId = $(this).data("mapId");
            $.ajax({
                type : "POST",
                url : "<%=UrlConstants.GET_CARNO%>",
                data : "${_csrf.parameterName}=${_csrf.token}&carId=" + carId,
                success : function(data) {
                    $(".modal-title").html(data.carNo);
                    $('#carName').html(data.carName.carName);
                    $('#carType').html(data.carType.carType);
                    $('#carColor').html(data.color);
                    $('#carId').val(data.id);
                    $('#mapId').val(mapId);
                },
                error : function(e) {
                    alert("error in car number");
                }
            });
        });

        $("body").delegate(".car-mapping-remove-submit", "click", function(){
            var mapId = $(this).data("mapId");
            $.ajax({
                type : "POST",
                url : "<%=UrlConstants.REMOVE_CAR_DRIVER_MAPPING%>",
                data : "${_csrf.parameterName}=${_csrf.token}&mapId=" + mapId,
                success : function(data) {
                	if(data===true){
                		location.href = "<%=UrlConstants.CAR_DRIVER_MAPPING%>";	
                	}
                	else{
                		$("driverError").show();
                	}
                   
                },
                error : function(e) {
                    alert("error in car number");
                }
            });
        });

        $("body").delegate(".form-submit", "click", function(){
            $("#errordriId").hide();
            var carId = $("#carId").val();
            var driId = $("#driId").val();
            var mapId = $('#mapId').val();
            if(driId === '0'){
                $("#errordriId").html('Please select Driver Name');
                $("#errordriId").show();
                return false;
            } else {
                $.ajax({
                    type : "POST",
                    url : "<%=UrlConstants.UPDATE_CAR_DRIVER_MAPPING%>",
                    data : "${_csrf.parameterName}=${_csrf.token}&carId=" + carId + "&driId="+driId+"&mapId="+mapId,
                    success : function(data) {
                        location.href = "<%=UrlConstants.CAR_DRIVER_MAPPING%>";
                    },
                    error : function(e) {
                        alert("error in car number form submit");
                    }
                });
            }
        });

        $("body").delegate("#driId", "change", function(){
            var driId = $("#driId").val();
            if(driId === '0'){
                $("#errordriId").html("");
                $(".form-submit").show();
                return false;
            } else {
                $.ajax({
                    type : "POST",
                    url : "<%=UrlConstants.CHECK_CAR_DRIVER_MAPPING%>",
					data : "${_csrf.parameterName}=${_csrf.token}&driId="
							+ driId,
					success : function(data) {
						if (data === "true") {
							$("#errordriId")
									.html(
											"Driver is Already Assign !!!");
							$(".form-submit").hide();
						} else {
							$("#errordriId").html("");
							$(".form-submit").show();
						}
					},
					error : function(e) {
						alert("error in car number form submit");
					}
				});
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
					<div class="caption">Car Driver Mapping</div>
					<div class="tools">
						<i class="fa fa-chevron-up"></i> <i class="fa fa-refresh"></i><i
							class="fa fa-times"></i>
					</div>
				</div>
				<div class="portlet-body">
					<div class="caption">
					<label for="inputAddress" style="display:none" id="driverError"class="error"><strong>You
											Can not Unassign this Driver</strong></label>
						<label for="inputAddress" class="error"><strong>${msg}</strong></label>
						<c:if test="${empty msg}">
							<c:if test="${add_restrict eq 'true'}">
								<div class="caption">
									<a class="btn btn-success" title="Add New Car"
										href="<%=UrlConstants.CAR%>">Add New Car</a>
								</div>
							</c:if>
							<c:if test="${add_restrict eq 'false'}">
								<div class="caption">
									<label for="inputAddress" class="error"><strong>You
											Can not Add More Car</strong></label>
								</div>
							</c:if>
						</c:if>
					</div>
				</div>
				<div class="form-body pal">
					<div class="form-group">
						<table class="table table-striped table-bordered table-hover"
							id="carDriver" border="1">
							<thead>
								<tr>
									<th>Serial No</th>
									<th>Car No</th>
									<th>Car Name</th>
									<th>Car Type</th>
									<th>Driver Name</th>
									<th>Assigned</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${cardriverlst}" var="mappingVo"
									varStatus="loopcounter">
									<tr>
										<td><c:out value="${loopcounter.count}" /></td>
										<td><div class="clr-box"
												style="background-color:${mappingVo.car.color}"></div>
											<c:out value="${mappingVo.car.carNo}" /></td>
										<td>
											<c:out value="${mappingVo.car.carName.carName}" /></td>
										<td><c:out value="${mappingVo.car.carType.carType}" /></td>
										<td><div class="clr-box"
												style="background-color:${mappingVo.driver.color}"></div>
											<c:out value="${mappingVo.driver.fullName}" /></td>
										<td align="center"><button type="button"
												class="btn btn-success ${empty mappingVo.driId ? 'car-form-submit' : 'car-mapping-remove-submit'}"
												data-toggle="modal" data-target="#myModal"
												data-row-id="${mappingVo.carId}"
												data-map-id="${mappingVo.id}">${empty mappingVo.driId ? 'Assign' : 'Un Assign'}</button></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Modal Header</h4>
					</div>
					<div class="modal-body">
						<div class="col-md-5">
							<div class="col-md-4">
								<div id="carName"></div>
							</div>
							<div class="col-md-4">
								<div id="carType"></div>
							</div>
							<div class="col-md-4">
								<div id="carColor"></div>
							</div>
						</div>
						<input type="hidden" id="carId" /> <input type="hidden"
							id="mapId" />
						<div class="col-md-5">
							<select class="form-control" name="driId" id="driId">
								<option value="0">Select Driver Name</option>
								<c:forEach var="driver" items="${driverlst}">
									<option value="${driver.id}">${driver.fullName}</option>
								</c:forEach>
							</select> <label id="errordriId" class="error"></label>
						</div>
						<div class="col-md-2">
							<input class="btn btn-success form-submit" type="submit"
								value="confirm" />
						</div>
					</div>
					<div class="modal-footer"></div>
				</div>
			</div>
		</div>
	</div>
</div>