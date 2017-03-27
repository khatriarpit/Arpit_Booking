<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
    $(document).ready(
        function() {
            $("#tickets").DataTable();

        });
</script>
<!--BEGIN CONTENT-->
<div class="page-content">
    <div class="row">
        <div class="col-lg-12">
            <div class="portlet">
                <div class="portlet-body">
                    <div class="portlet-header">
                    </div>
                    <h4>${message}</h4>
                    <div class="portlet-body">
                        <div class="box col-md-6">

                            <div class="box-header">
                                <div class="caption box-caption">Generate Ticket</div>
                                <!--<div class="tools"><i class="fa fa-chevron-up"></i><i data-toggle="modal" data-target="#modal-config" class="fa fa-cog"></i><i class="fa fa-refresh"></i><i class="fa fa-times"></i></div>-->
                            </div>
                            <div class="box-body col-md-6">
                                <form:form method="POST" action="<%=UrlConstants.SAVE_DEPARTMENT_TYPE%>" class="form-horizontal"
                                           ondragstart="return false;" ondrop="return false;">
                                <div class="form-body pal">

                                    <input type="hidden" name="${_csrf.parameterName}"
                                           value="${_csrf.token}" />


                                        <div class="col-md-9">
                                            <div class="input-icon right">
                                                <form:input type="text" name="departmentName" path="departmentName"
                                                            value="" class="form-control" placeholder="Enter Department Name" />
                                            </div>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <div class="col-md-6 col-md-offset-3">
                                            <div class="input-icon"><button class="btn orange_bg" type="submit" onclick = "return validation();"> Submit</button>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-6 col-md-offset-3">
                                            <a href="<%=UrlConstants.DASHBOARD%>" class="btn grey_bg">Cancel</a>
                                        </div>
                                    </div>

                                    <div>
                                        <div class="col-md-6 col-md-offset-3"></div>
                                        <input type = "button" class="btn orange_bg" type="button" value="Clear" onclick="cleardata();"/>
                                    </div>

                                        </form:form>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>







<script>
    function cleardata() {
        document.getElementById("departmentName").value = '';
    }
</script>

<script>

    function validation(){

        var data = document.getElementById("departmentName").value;

        if(data ==''){
            alert('Enter Department Name');
            return false;
        } else {
            return true;
        }


    }

</script>