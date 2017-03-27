<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<div class="modal fade" id="myModal" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Modal Header</h4>
			</div>

			<div class="modal-body">
				<div class="portlet">

					<div class="col-md-5">
						<input type="radio" name="driverrequest" value="changeNow" checked>Approve & Change Now<br>
						<input type="radio" name="driverrequest" value="changeLater">Approve & Change Later<br>
						<input type="radio" name="driverrequest" value="reject"> Reject  <br>
					</div>
					<input type="hidden" id="clientId" value="${clientId}" />
					<div class="col-md-12">
					</div>
					<div class="col-md-4">
						<input class="btn btn-success form-submit" type="submit"
							   value="Ok" />
						<%--	 <input class="btn btn-cancel form-submit" type="submit"
                                                                        value="Cancel" />--%>
					</div>
				</div>		</div>
			<div class="modal-footer"></div>
			<div id="dynamicForm"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$("#myModal").trigger("click");
		$("#myModal").addClass("in");
		$("#myModal").attr("aria-hidden","false");
		$("#myModal").css({"display":"block"});
	})
    $("body").delegate(".form-submit", "click", function(){
        var clientId = $("#clientId").val();
        var choiceSelection= $('input[name=driverrequest]:radio:checked').val();
        var url;
        if(choiceSelection==='reject'){
            $.ajax({
                type : "POST",
                url : "<%=UrlConstants.REJECT_DRIVER_CANCEL_REQUEST%>",
                data : "${_csrf.parameterName}=${_csrf.token}&clientId=" + clientId ,
                success : function(data) {
                    location.href = "<%=UrlConstants.CANCEL_REQUEST_LIST%>";
                },
                error : function(e) {
                    alert("error in Rejection");
                }
            });
        }else if(choiceSelection ==='changeLater'){
            $.ajax({
                type : "POST",
                url : "<%=UrlConstants.APPROVE_CHANGE_LATER%>",
                data : "${_csrf.parameterName}=${_csrf.token}&clientId=" + clientId ,
                success : function(data) {
                    location.href = "<%=UrlConstants.CANCEL_REQUEST_LIST%>";
                },
                error : function(e) {
                    alert("error in Approval");
                }
            });
        }else
        {

            var tag = '';
            var changeBy='driver';
            tag += '<form style="display:none;" method="POST" class="form-horizontal" action="<%=UrlConstants.CHANGE_REQUEST%>">'+
                '<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />'+
                '<input type="hidden" id="clientId" name="clientId" value="'+clientId+'" />'+
                '<input type="submit" id="submit" />'+
                '</form>';
            $("#dynamicForm").html(tag);
            $('#submit').trigger("click");
        }

    });
</script>