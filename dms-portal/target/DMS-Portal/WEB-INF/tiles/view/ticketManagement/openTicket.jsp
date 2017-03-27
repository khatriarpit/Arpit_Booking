<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page import="com.emxcel.dms.core.business.constants.Constants" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://cdn.ckeditor.com/4.5.7/standard/ckeditor.js"></script>


<!--BEGIN CONTENT-->
<div class="page-content">
    <div class="row">
        <div class="col-lg-12">
            <div class="portlet">
                <div class="portlet-body">
                    <div class="box col-md-6">
                        <div class="box-header">
                            <div class="caption box-caption">
                                <div class="row">
                                    <div class="col-md-12">

                                        <c:out value="${message}" ></c:out>

                                    </div>
                                    <div class="col-md-6">
                                        <p class="text-left">Topic: ${ticket.topicName}</p>
                                        <p class="text-left">Description:${ticket.description}</p>
                                    </div>

                                    <div class="col-md-6 text-right">
                                        <c:if test="${ticket.status==1}">
                                            Status: OPEN


                                            <form:form method="POST" action="<%=UrlConstants.UPDATE_STATUS%>" class="form-horizontal"
                                                       ondragstart="return false;" ondrop="return false;" modelAttribute="ticket">


                                                <input type="hidden" name="${_csrf.parameterName}"
                                                       value="${_csrf.token}" />

                                                <form:hidden name="ticket" path="id"
                                                             value="${ticket.id}"  />

                                                <!-- if role = 5,6,7 put resolve button- -->
                                                <c:if test="${roleName=='ROLE_SALE' || roleName=='ROLE_ACCOUNTANT' ||roleName=='ROLE_TICKET_HANDLER'}">
                                                    <button class="btn orange_bg" type="submit"> Resolve </button>

                                                </c:if>
                                                <c:if test="${roleName=='ROLE_ADMIN'}">
                                                    <button class="btn orange_bg" type="submit"> Close Ticket</button>

                                                </c:if>
                                                <!--



                                                -->


                                            </form:form>


                                        </c:if>
                                        <c:if test="${ticket.status==0}">
                                            Status: CLOSE
                                        </c:if>
                                        <c:if test="${ticket.status==3}">
                                            Status: RESOLVED


                                                <form:form method="POST" action="<%=UrlConstants.UPDATE_STATUS%>" class="form-horizontal"
                                                           ondragstart="return false;" ondrop="return false;" modelAttribute="ticket">


                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                           value="${_csrf.token}" />

                                                    <form:hidden name="ticket" path="id"
                                                                 value="${ticket.id}"  />

                                                    <!-- if role = 5,6,7 put resolve button- -->

                                                    <c:if test="${roleName=='ROLE_ADMIN'}">
                                                        <button class="btn orange_bg " type="submit"> Close Ticket</button>

                                                    </c:if>


                                                </form:form>


                                        </c:if>
                                        <c:if test="${ticket.status==3 && roleName=='ROLE_ADMIN'}">

                                    <form:form method="POST" action="<%=UrlConstants.OPEN_CLOSED_TICKET%>" class="form-horizontal"
                                               ondragstart="return false;" ondrop="return false;" modelAttribute="ticket">


                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />

                                        <form:hidden name="ticket" path="id"
                                                     value="${ticket.id}"  />

                                        <!-- if role = 5,6,7 put resolve button- -->


                                        <button class="btn orange_bg " type="submit"> Reopen</button>




                                    </form:form>

                                </c:if>

                                    </div>
                                </div>
                            </div>
                        </div>





                                         <!--<div class="tools"><i class="fa fa-chevron-up"></i><i data-toggle="modal" data-target="#modal-config" class="fa fa-cog"></i><i class="fa fa-refresh"></i><i class="fa fa-times"></i></div>-->

                        <div class="box-body ">
                            <div class="col-md-6">
                                <c:if test="${ticket.status==1}">

                                        <form:form method="POST" action="<%=UrlConstants.TICKET_COMMENT_SUPPORTER%>" class="form-horizontal"
                                                   ondragstart="return false;" ondrop="return false;" modelAttribute="ticketComments">
                                            <div class="form-body pal">
                                                <input type="hidden" name="${_csrf.parameterName}"
                                                       value="${_csrf.token}" />

                                                <div class="form-group">
                                                    <div class="col-md-12">
                                                        <form:textarea type="text" name="comment" id="comment" path="comment"
                                                                       value="" required = "true" class="form-control" placeholder="Enter Your Comment" />
                                                        <div class="col-md-9">
                                                            <div class="input-icon right">
                                                                <form:hidden name="ticket" path="ticket.id"
                                                                             value="${ticket.id}"  />
                                                                <input type="hidden" name="${_csrf.parameterName}"
                                                                       value="${_csrf.token}" />
                                                            </div>
                                                        </div>
                                                        <button class="btn orange_bg pull-left top-margin" type="submit" onclick="return valid();"> Submit Button</button>
                                                        <input type = "button" class="btn orange_bg" type="button" value="Clear" onclick="cleardata();"/>
                                                        <div class="col-md-6 col-md-offset-3">
                                                            <a href="<%=UrlConstants.VIEW_SUPPORT_TICKET%>" class="btn grey_bg">Cancel</a>
                                                        </div>
                                                    </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form:form>

                                </c:if>
                            </div>
                            <div class="col-md-6">
                                <c:if test="${roleName=='ROLE_TICKET_HANDLER' && ticket.status==1}">
                                    <!-- Assign ticket to different user -->


                                    <form:form method="POST" action="<%=UrlConstants.ASSIGN_TICKET%>" class="form-horizontal"
                                               ondragstart="return false;" ondrop="return false;" modelAttribute="ticket">
                                        <div class="form-body pal">

                                            <input type="hidden" name="${_csrf.parameterName}"
                                                   value="${_csrf.token}" />
                                            <input type="hidden" name="${_csrf.parameterName}"
                                                   value="${_csrf.token}" />
                                            <form:hidden name="ticket" path="id"
                                                         value="${ticket.id}"  />
                                            <div class="form-group">
                                                <label >Assigned  : ${ticket.assignedUser.username}</label>
                                            </div>
                                            <div class="form-group"> <label >Assign <To></To>  :</label>
                                            <form:select path = "assignedUser.id" class="form-control select2" id="user">

                                                    <form:option value="0">Select Name Of User</form:option>

                                                <c:forEach var="usersToAssignTickets" items = "${usersToAssignTickets}">
                                                    <c:if test="${ticket.assignedUser.id!= usersToAssignTickets.id}" >
                                                        <form:option value="${usersToAssignTickets.id}">${usersToAssignTickets.username}</form:option>
                                                    </c:if>
                                                </c:forEach>
                                            </form:select>
                                            </div>
                                            <div class="input-icon pull-right"><button class="btn orange_bg" type="submit" onclick="return validation();"> Assign</button>

                                        </div>



                                    </form:form>
                                </c:if>

                            </div>
                            </div>



                            <div class="col-md-6">
                            <!-- Handle Ticket Priority Starts-->
                            <c:if test="${roleName=='ROLE_TICKET_HANDLER' && ticket.status==1}">

                                <form:form method="POST" action="<%=UrlConstants.UPDATE_TICKET_PRIORITY%>" class="form-horizontal"
                                           modelAttribute="ticket">

                                    <input type="hidden" name="${_csrf.parameterName}"
                                           value="${_csrf.token}" />

                                    <form:hidden name="ticket" path="id"
                                                 value="${ticket.id}"  />

                                    <label>Priority</label>
                                    <form:select path = "ticketPriority.id" class="form-control select2" id="ticketpriority" >



                                        <c:forEach var="ticketPriority" items = "${ticketPriority}" >
                                            <c:choose>
                                                <c:when test="${ticketPriority.id==ticket.ticketPriority.id}">
                                                    <form:option value="${ticketPriority.id}" selected="true">${ticketPriority.ticketPriority}</form:option>
                                                </c:when>


                                            <c:otherwise>
                                                <form:option value="${ticketPriority.id}" >${ticketPriority.ticketPriority}</form:option>
                                            </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </form:select>

                                <div class="input-icon pull-right"><button class="btn orange_bg" type="submit" > Change Priority</button>
                                </form:form>

                            </c:if>

                            <c:if test="${(roleName=='ROLE_TICKET_HANDLER' || roleName =='ROLE_SALE' || roleName=='ROLE_ACCOUNTANT') &&(ticket.status==2 || ticket.status==3 || ticket.status==1 )}">

                                <label>Priority : <c:out value="${ticket.ticketPriority.ticketPriority}"></c:out>


                                </label>

                            </c:if>
                            <!-- Handle Ticket Priority Ends -->
                            </div>

                            </div>

                            <div class="col-md-12">
                                <div class="panel panel-custom">
                                    <div class="panel-default panel-background">
                                        <div class="row">

                                        <c:forEach  var="comments" items = "${ticket.ticketComments}">
                                            <div class="col-md-12 inner-padding">
                                                <div class="col-md-6">

                                                        <img
                                                            alt="${comments.user.userImage != '' ? comments.user.userImage : 'Image Not Found'}"
                                                            src="<%=UrlConstants.FETCH_IMAGE%>?imageName=${comments.user.userImage!= ''? comments.user.userImage : 'default.jpg'}" class="col-md-4 img-responsive">
                                                    <h4>${comments.user.firstName}</h4>
                                                    <h4>${comments.user.emailId}</h4>
                                                    <h4>${comments.createdDate}</h4>
                                                </div>
                                                <div class="col-md-6">
                                                    <h4 class="text-left">
                                                            ${comments.comment}
                                                    </h4>
                                                </div>

                                            </div></section>
                                    </c:forEach>

                            </div>
                                    </div>
                                </div>

                            </div>



                            <%--<div class="comment-details-wrp">--%>
                                <%--<c:forEach  var="comments" items = "${ticket.ticketComments}">--%>

                                <%--<div class="row repeater">--%>
                                    <%--<div class="col-md-12">--%>
                                        <%--<div class="row">--%>
                                            <%--<div class="col-md-2">--%>

                                                <%--<img src="<%=UrlConstants.FETCH_IMAGE%>?imageName=${comments.user.userImage}" class="img-responsive"--%>
                                                     <%--/>--%>


                                            <%--</div>--%>
                                            <%--<div class="col-md-10">--%>
                                                <%--<div class="row">--%>
                                                    <%--<div class="col-md-12">--%>
                                                        <%--<p class="text-left">--%>

                                                        <%--</p>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>

                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>

                                <%--</c:forEach>--%>



                            <%--</div>--%>


                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
    <script>
        CKEDITOR.replace('comment');

    </script>
<script>
    function validation() {
        var e = document.getElementById("user");
        if(e.selectedIndex!=0)
            return true;
        else{
            alert("Please select User to Assign");
            return false;
        }

    }
</script>
<script type="text/javascript">

jQuery(document).ready(function($) {
var totalDiv = $('.panel-background .row > div').length;
if(totalDiv >= 3){
$('.panel-custom').addClass('section-overflow');
}else{
$('.panel-custom').removeClass('section-overflow');

}
});
</script>
<script>

    function cleardata() {
        CKEDITOR.instances['comment'].setData('');
    }


</script>
<script>
    function valid(){
        //alert("here");
        textbox_data = CKEDITOR.instances.comment.getData();

        if(textbox_data===''){
            alert('Enter Comment');
            return false;
        }else{
            return true;
        }
    }
</script>