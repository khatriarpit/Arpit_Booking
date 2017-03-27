<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>

<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
   
    <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 100%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>
  </head>
  <body>
<div id="map"></div>
<!-- <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script> -->
<script type="text/javascript">
    var myLatLong;
    $(document).ready(function () {
    	
        var timer, delay = 15000; //5 minutes counted in milliseconds.
        var i=0;
        var backData = 263;
        var intzoom = 0;
        var tripId = document.getElementById("tripId").value;
      
        timer = setInterval(function(){
            $.ajax({
              type: 'GET',
              url: "<%=UrlConstants.FETCH_CAR_LOCATION%>",
              data: {id : i+1,backData : backData,tripId:tripId},
              
              success: function(data){
            	           	i++;
            	           
             	           	if(intzoom == 0){
            	           		map.setZoom(11);
            	           		map.setCenter(new google.maps.LatLng(parseFloat("23.0225"), parseFloat("72.5714")));
            	           		intzoom++;
            	           	}
            	
            	if(data!= null){
            		
            		for(var j=0;j<data["latitude"].length;j++) {
            				myLatLong = new google.maps.LatLng(parseFloat(data["latitude"][j]), parseFloat(data["longitude"][j])); 
            		       	addLatLng(intzoom);
            		}
            	            	  
               }else{
            	  
            	   clearTimeout(timer);
       			}
              },
              error: function () {
                  alert("An error has occured!!!");
              }
            });
        }, delay)
        });


      var poly;
      var map;

      function initMap() {
	        map = new google.maps.Map(document.getElementById('map'), {
	          zoom: 5,
	          center: {lat: 22.2587, lng: 71.1924} 
	        });
	       
	        var iconCar = {
		         path: 'm 213.56538,281.96297 c -21.26228,0 -32.54145,11.60781 -37.74859,25.19519 l -27.64104,71.37164 c -10.95741,1.40503 -30.38157,14.267 -30.38157,38.63263 l 0,90.76162 26.90434,0 0,29.02605 c 0,35.71056 50.53771,35.28999 50.53772,0 l 0,-29.02605 90.90897,0 0.0295,0 90.90892,0 0,29.02605 c 0,35.28999 50.53748,35.71056 50.53777,0 l 0,-29.02605 26.90429,0 0,-90.76162 c 0,-24.36561 -19.42402,-37.2276 -30.38162,-38.63263 l -27.67045,-71.37164 c -5.20713,-13.58738 -16.48619,-25.19519 -37.74863,-25.19519 l -39.07456,0 -66.53899,0 -39.54608,0 z m -0.32416,25.99083 72.84506,0 0.0589,0 0.0295,0 72.87447,0 c 9.11198,0.0849 13.01451,5.78327 15.61806,13.67316 l 20.83388,56.16614 -109.32641,0 -0.0295,0 -0.0589,0 -109.29704,0 20.83391,-56.16614 c 2.60358,-7.88989 6.50513,-13.58831 15.61807,-13.67316 z m -42.46346,97.15619 c 12.89334,0 23.3387,10.75748 23.3387,24.04593 -1e-5,13.2894 -10.44536,24.07541 -23.3387,24.07541 -12.8924,0 -23.33871,-10.78603 -23.33871,-24.07541 0,-13.28842 10.44631,-24.04593 23.33871,-24.04593 z m 230.76437,0 c 12.89335,0 23.3388,10.75751 23.33871,24.04593 0,13.28938 -10.44536,24.07541 -23.33871,24.07541 -12.89238,0 -23.36822,-10.78601 -23.36822,-24.07541 0,-13.28845 10.47584,-24.04593 23.36822,-24.04593 z',
		        anchor: new google.maps.Point(60, 10),
		        scale: 0.05,
		        strokeColor: '#000000',
		        strokeWeight: 1,
		       
		        fillColor: '#0000FF',
		        fillOpacity: 0.6, 
		    };
	        poly = new google.maps.Polyline({
	          strokeColor: '#1E90FF',
	          strokeOpacity: 1.0,
	          icons: [{
	              icon: iconCar,
	              offset: '100%'
	            }],
	          strokeWeight: 2
	        });
	        poly.setMap(map);
       
      }

      function addLatLng(intzoom) {
        var path = poly.getPath();
        path.push(myLatLong);
        moveCar(intzoom);
      }
      
      function moveCar(intzoom) {
    	    var counter = 0;
    	   
    	 /*  if (intzoom != 1){ */
    		  //alert(intzoom);
    	    window.setInterval(function() {
    	       /*  counter = (counter + 1) % 200;

    	        var icons = polyline.get('icons');
    	        icons[0].offset = (counter / 2) + '%';
    	        poly.set('icons', icons); */
    	    	var marker = new google.maps.Marker({
    	            position: event.latLng,
    	            map: map,
    	            draggable: false,
    	            icon: icons[0]
    	    });
    	    }, 1500);
    	    
    	 // }
      }
    </script> 
	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB2EDhmpZ45GFBtgxfxnotXIkcLV1jQcQA&callback=initMap">
    </script> 
    <input type="hidden" value="${tripId}" id="tripId" />
     	<form:form method="GET" class="form-horizontal" action="<%=UrlConstants.LIST_BOOKEDCARS%>" ondragstart="return false;" ondrop="return false;">
								
									<input type="submit" name="submit" value="Back" class="btn btn-info text-center submeet_btn" /> 
									</form:form>
    
   <%--  <form class="form-horizontal cascde-forms" method="GET" action="getscreen.htm" >
    <input type="submit" name="submit" value="Capture Me" class="btn btn-info text-center submeet_btn" /> 
    </form> --%>
 </body>
</html>
   
