<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <script src="assets/scripts/jquery-1.9.1.js"></script> -->
<script src="assets/scripts/jquery-1.9.1.min.js"></script>
<script src="assets/scripts/jquery-migrate-1.2.1.min.js"></script>
<script src="assets/scripts/jquery-ui.js"></script>
<!--loading bootstrap js-->
<script src="assets/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendors/metisMenu/jquery.metisMenu.js"></script>
<script src="assets/vendors/slimScroll/jquery.slimscroll.js"></script>
<script src="assets/vendors/jquery-cookie/jquery.cookie.js"></script>
<script src="assets/scripts/jquery.menu.js"></script>
<script src="assets/vendors/jquery-pace/pace.min.js"></script>
<!--LOADING SCRIPTS FOR PAGE-->

<script src="assets/scripts/jquery.wizard.js"></script>
<script src="assets/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="assets/vendors/moment/moment.js"></script>
<script src="assets/vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<!--<script src="//www.malot.fr/bootstrap-datetimepicker/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js?t=20130302"></script>-->
<script src="assets/vendors/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
<script src="assets/vendors/bootstrap-clockface/js/clockface.js"></script>
<script src="assets/vendors/bootstrap-colorpicker/js/bootstrap-colorpicker.min.js"></script>
<script src="assets/vendors/bootstrap-switch/js/bootstrap-switch.min.js"></script>
<script src="assets/vendors/jquery-maskedinput/jquery-maskedinput.js"></script>
<script src="assets/vendors/datetimepicker/jquery.datetimepicker.full.min.js"></script>
<script src="assets/scripts/form-components.js"></script>
<script src="assets/scripts/jquery.dataTables.min.js"></script>
<script src="assets/scripts/dataTables.bootstrap.min.js"></script>
<script src="assets/scripts/jquery.validate.min.js"></script>
<script src="assets/scripts/jquery.blockUI.js"></script>
<script src="assets/morris/js/raphael-min.js"></script>
<script src="assets/morris/js/morris.min.js"></script>
<!--<script src="assets/scripts/jquery.datetimepicker.full.min.js"></script> -->
<!--CORE JAVASCRIPT-->
<script src="assets/donutchart/donut_hart.min.js"></script>
	<!-- <script src="assets/donutchart/donut_charts.js"></script> -->
<script src="assets/scripts/main.js"></script>
<script src="assets/scripts/socket-0.3.4.js"></script>
<script src="assets/scripts/stomp.js"></script>
<script type="text/javascript">
	$(function(){
		$(".themes-click").click(function(){
			var color = $(this).data("rawColor");
			window.location.href="changeColor.htm?color="+color;
		});
	});

	function dateCalenderValidation(fromID, toID) {
		$("#"+fromID).datepicker({
			dateFormat : "dd/mm/yy",
			minDate : 0,
			onSelect : function(date) {
				var dt2 = $("#"+toID);
				var startDate = $(this).datepicker('getDate');
				var minDate = $(this).datepicker('getDate');
				//dt2.datepicker('setDate', minDate);
				dt2.datepicker('option', 'minDate', minDate);
				$(this).datepicker('option', 'minDate', minDate);
			}
		});
		$("#"+toID).datepicker({
			dateFormat : "dd/mm/yy"
		});
	}

	// unblock when ajax activity stops
    function callblock(){
	    $.blockUI({ message: '<img src="assets/images/icons/loading.gif" />' });
    }
	
 	// unblock when ajax activity stops 
	function endblockUI() {
	    $.unblockUI(); // previously was $.unblockUI;
	}
</script>
<c:if test="${pageContext.request.userPrincipal.name != null}">
	<script type="text/javascript">
		/**
		 * Open the web socket connection and subscribe the "/notify" channel.
		 */
		function connect() {
			// Create and init the SockJS object
			var socket = new SockJS('/ws');
			var stompClient = Stomp.over(socket);
	
			// Subscribe the '/notify' channell
			stompClient.connect({}, function(frame) {
				stompClient.subscribe('/user/queue/notify', function(notification) {
					var status = false;
					callWebsocket(status);
				});
			});
			return;
		} // function connect
	
		/**
		 * Display the notification message.
		 */
		function callWebsocket(status) {
			$("#dynamicIDForNotification").html("");
			$.ajax({
	            type:'POST',
	            url:'<%=UrlConstants.GET_NOTIFICATIONS_BY_USER%>',
	            data:'${_csrf.parameterName}=${_csrf.token}&status='+status,
	            success:function(data){
	            	$("#notification").remove();
	            	var len = data.length;
	            	var notidiv = document.createElement('div');
	    			notidiv.id = "notification";
	    			var img = document.createElement("img");
	    			/* img.src = 'assets/images/chat.png';
	    			notidiv.appendChild(img); */
	    			notidiv.className ="fa fa-bell fa-fw";
	    			if(len > 0) {
		    			var notispan = document.createElement('span');
		    			notispan.id = "notification_count";
		    			notidiv.appendChild(notispan);
		    			var notia = document.createElement('a');
		    			notia.id = "notificationLink";
		    			var valueMsg = len;
		    			var linkText = document.createTextNode(valueMsg);
		    			notia.appendChild(linkText);
		    			notia.title = "my title text";
		    			notia.href = "#";
		    			notispan.appendChild(notia);
		            }
	    			var tempdiv = document.getElementById('topbar-main_not');
	    			tempdiv.appendChild(notidiv);
	    			var notidivContainer = document.createElement('div');
	    			notidivContainer.id = "notificationContainer";
	    			var notiDivTitle = document.createElement('div');
	    			notiDivTitle.id = "notificationTitle";
	    			var titleText = document.createTextNode("You have " + len
	    					+ " unread messages");
	    			notiDivTitle.appendChild(titleText);
	    			notidivContainer.appendChild(notiDivTitle);
	    			var notiDivBody = document.createElement('div');
	    			notiDivBody.id = "notificationsBody";
	                for (var i = 0;i < len; i++) {
	                	var dataSet = data[i];
	    				var dispa = document.createElement('a');
	    				dispa.className = "disp_not";
	    				dispa.id = dataSet.id;
	    				var linkText = document.createTextNode(dataSet.description);
	    				dispa.textAlign = "center";
	    				dispa.appendChild(linkText);
	    				notiDivBody.appendChild(dispa);
	                }
	    			notidivContainer.appendChild(notiDivBody);
	    			notidiv.appendChild(notidivContainer);
	            },
	            error: function(){
	            	alert("error");
	            }
	        });
		}
		
		/**
		 * Init operations.
		 */
		$(document).ready(function() {
			$("body").delegate("#notification","click", function() {
				$("#notificationContainer").fadeToggle(300);
				//$("#notification_count").fadeOut("slow");
				return false;
			})
			
			// Start the web socket connection.
			connect();
			callWebsocket(false);

			//Document Click hiding the popup 
			$(document).click(function() {
				$("#notificationContainer").hide();
			});

			//Popup on click
			$("#notificationContainer").click(function() {
				return false;
			});

			$("body").delegate(".disp_not", "click", function(e) {
				var notificationID = $(this).attr("id");
				$.ajax({
		            type:'POST',
		            url: '<%=UrlConstants.UPDATE_NOTIFICATION_BY_ID%>',
		            data: '${_csrf.parameterName}=${_csrf.token}&id='+notificationID,
		            success:function(data){
		            	if(data === true) {
		            		callWebsocket(false);
		            	}
		            },
		            error: function() {
		            	alert("error");
		            }
				});
			});
		});
	</script>
</c:if>
