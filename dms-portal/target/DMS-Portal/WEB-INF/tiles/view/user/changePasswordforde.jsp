<%@page import="com.emxcel.dms.portal.constants.UrlConstants" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  $(function(){
    	$("body").delegate(".changepassword-form-submit", "click", function(){
    	
    	if(validate()){
        var userId= $("#loginId").val();
         $("#userId").val(userId);
         }else{
        	 //$("#error-alldetail").html("Please fill all User details!!!");
        	 return false;
         }
                })
        });
  
  function validate(){
	  var newPassword=$("#newPassword").val();
	 
	  var conpass = $("#conpass").val();
	  var valid = false;
	  var selectId=$("#username").val();
	  var loggeduser=$("#loggeduser").val();
	    
       if(selectId > 0 ){
            $("#error-user").html("");
            valid=true;    
       }else{
            $("#error-user").html("Please select User!!!");
            valid=false;
            return valid;
          
       }
       if(newPassword === conpass){
    	   if(newPassword.length < 6 || conpass.length < 6){
    		   alert("inside if length");
		  	$("#error-password").html("Both Password have atleast 6 characters!");
		  	valid = false;
		  	return valid;
    	   }else{
    		   var pattern = /^[a-zA-Z0-9_-]{5,15}$/;
    			    if((pattern.test(newPassword)) && (pattern.test(conpass))){
    	    		   $("#error-password").html("Atleast 1 numaric, 1 Uppercase and 1 Special character! Ex:Abc@123");
    			  		valid = false;
    			  		return valid;
    		    }else{
      			   $("#error-password").html("");
      			  	valid = true;
      			  	return valid;  
    		    }
    		  }
	  }
	  else{
		  $("#error-password").html("Password doesn't match!!!");
		  valid=false;
         return valid;
	  } 
       
        /* if(newPassword.length < 6 || conpass.length < 6){
        	$("#error-password").html("Both Password have atleast 6 characters!");
  		  valid=false;
  		  return valid;
        }else{
        	$("#error-password").html("");
  		  valid = true;
  		  return valid;
        } */
       
  			return false;
  
    
	  
	  //return valid;
  }

  $("body").delegate(".form-submitpopUp", "click", function(){
  var userId=$("#userId").val();
  var selectId=$("#username").val();
  var newPassword=$("#newPassword").val();
  var conpass = $("#conpass").val;
  var loggeduser=$("#loggeduser").val();
  
  if(loggeduser === ""){
	  	$("#error-logedpassword").html("Please insert password!!!");
	  	 valid=false;
	  }else{
	  	$("#error-logedpassword").html("");
	  	valid=true;
	  } 
				if(valid === true){
					
				
                     var tag = '';
                     tag += '<form style="display:none;" method="POST" class="form-horizontal" action="<%=UrlConstants.CHANGEPASSWORDFORDE_ACCEPTED%>" >'+
                         '<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />'+
                         '<input type="hidden" id="userId" name="userId" value="'+userId+'" />'+
                            '<input type="hidden" id="selectId" name="selectId" value="'+selectId+'" />'+
                               '<input type="hidden" id="newPassword" name="newPassword" value="'+newPassword+'" />'+
                               '<input type="hidden" id="	" name="loggeduser" value="'+loggeduser+'" />'+
                         '<input type="submit" id="submit" />'+
                         '</form>';
                     $("#dynamicForm").html(tag);
                     $('#submit').trigger("click");
				}
        });

</script>


<!--BEGIN CONTENT-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			
				<div class="box ">
                            <div class="box-header">
                               <div class="caption box-caption">Change Password For Other User</div>
                               </div>
                               <div class="box-body col-md-8">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<div class="form-group text-center">
							<c:if test="${message != ''}">
								<label for="inputAddress" class="col-md-12"> <font
									color="green" style="font-weight: bold" size="4">${message}</font>
								</label>
								<label id="error-alldetail" style="color:red;"></label>
							</c:if>
						</div>

<!--                         <div class="form-group"> -->

                        <input type="hidden" value="${loginId}" id="loginId">
						<div class="form-group">
                        							<label for="inputAddress" class="col-md-3 control-label">Users
                        								</label>
                        							<div class="col-md-9">
                        								<div class="input-icon right">
                                                                <select class="form-control"
                                                                    itemValue="username" id="username">
                                                                    <option value="0">Select User<option>
                                                                    <c:forEach var="userlst" items="${userlists}">
                                                                            <option value="${userlst.id}"
                                                                                >${userlst.username}</option>
                                                                    </c:forEach>
                                                                </select>
                                                                <label id="error-user" style="color:red;"></label>
                        								</div>
                        							</div>
                        						</div>
						<div class="form-group">
							<label for="inputAddress" class="col-md-3 control-label">New
								Password</label>
							<div class="col-md-9">
								<div class="input-icon right">
									<input type="password" class="form-control" path="newPassword"
										id="newPassword" />
									<label id="error-password" style="color:red;"></label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="inputAddress" class="col-md-3 control-label">Confirm
								Password</label>
							<div class="col-md-9">
								<div class="input-icon right">
									<input class="form-control" type="password" name="cpass"
										id="conpass" /> <label style="display: none;"
										id="conpassError" class="error">Enter Confirm Password
										Same as Password</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="inputAddress" class="col-md-2 control-label">&nbsp;</label>
							<div class="col-md-9 col-md-offset-3">
								<input class="btn btn-success changepassword-form-submit" class="submit" type="submit"
									value="Submit" data-toggle="modal" data-target="#myModal">
							</div>
						</div>
						</div>
						
<!-- 				</div> -->
			
		</div>
	</div>

	<div class="modal fade" id="myModal" role="dialog">
            			<div class="modal-dialog">
            				<!-- Modal content-->
            				<div class="modal-content">
            					<div class="modal-header">
            						<button type="button" class="close" data-dismiss="modal">&times;</button>
            						<h4 class="modal-title">Enter Your Password For Verification</h4>
            					</div>
            					<div class="modal-body">
            					<div class="portlet">

            						<div class="col-md-5">
            						 <input type="password" name="password" id="loggeduser" /><br>
            							<label id="error-logedpassword" style="color:red;"></label>
            						</div>
            						<input type="hidden" id="userId" />
            						<div class="col-md-12">
            						</div>
            						<div class="col-md-4">
            							<input class="btn btn-success form-submitpopUp" type="submit"
            								value="submit" />
            						<%--	 <input class="btn btn-cancel form-submitpopUp" type="submit"
                                                    								value="Cancel" />--%>
            						</div>
            						</div>		</div>
            					<div class="modal-footer"></div>
    							<div id="dynamicForm"></div>
            				</div>
            			</div>
            		</div>
    	</div>
</div>
<!--END CONTENT-->