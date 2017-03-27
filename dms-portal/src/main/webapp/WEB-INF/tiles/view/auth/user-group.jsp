<%@page import="com.emxcel.dms.portal.constants.UrlConstants" %>
<%@ page import="com.emxcel.dms.core.business.constants.Constants" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(document).ready(function() {
	$("#user-group").validate({
		rules : {
			groupName : {
				required : true,
				minlength: 2
			},
			roles : {
				required : true
			}, 
			permissions : {
				required : true
			}, 
			submitHandler : function(form) {
				form.submit();
			}
		}
	});
});
  </script>
<div class="page-content">
    <div class="row">
        <div class="col-lg-12">
            <div class="portlet">
                <div class="portlet-body">
                    <div class="box col-md-6">
                        <div class="box-header">
                            <div class="caption box-caption">Group</div>
                            <!--<div class="tools"><i class="fa fa-chevron-up"></i><i data-toggle="modal" data-target="#modal-config" class="fa fa-cog"></i><i class="fa fa-refresh"></i><i class="fa fa-times"></i></div>-->
                        </div>
                        <div class="box-body col-md-6">
                        
                        <div class="form-group text-center">
							<c:if test="${message != ''}">
								<label for="inputAddress" class="col-md-12"> <font
									color="green" style="font-weight: bold" size="4">${message}</font>
								</label>
								<label id="error-alldetail" style="color:red;"></label>
							</c:if>
						</div>
                           <form action="<%=UrlConstants.SAVE_USER_GROUP%>" method='POST' class="form" id="user-group" modelAttribute="command">
                                <div class="form-body pal">
                                    <div class="form-group"><label for="inputAddress" class="col-md-3 control-label">Group
                                        Name <span class='require'>*</span></label>

                                        <div class="col-md-9">
                                            <input type="text" name="groupName" id="groupName" class="form-control" placeholder="Group Name">
                                        </div>
                                    </div>


                                    <div class="form-group"><label for="inputAddress" class="col-md-3 control-label">Select
                                        Roles<span class='require'>*</span></label>
                                        <div class="col-md-9">

                                            <select name="roles" multiple="true" id="roles" class="form-control select2">
                                                <c:forEach var="userrole" items="${userroles}">

                                                    <option value="${userrole.id}"> ${userrole.role}</option>
                                                </c:forEach></select>
                                            <small>Press Ctrl to select multiple</small>
                                        </div>
                                    </div>
                                    <div class="form-group"><label for="inputAddress" class="col-md-3 control-label">Permission<span
                                            class='require'>*</span></label>

                                        <div class="col-md-9">
                                           <select name="permissions" multiple="true" id="permissions" class="form-control select2">
                                         <c:forEach var="userpermission" items="${userpermission}">

                                         <option value="${userpermission.id}"> ${userpermission.name}</option>
                                         </c:forEach></select>
                                            <small>Press Ctrl to select multiple</small>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-6 col-md-offset-3">
                                            <div class="input-icon">
                                            	<input class="btn orange_bg" class="submit" type="submit"
											value="Submit" />
                                                <button class="btn grey_bg" type="reset">Reset</button>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </form>

                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
