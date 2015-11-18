<%@page import="com.replad.init.InitConfiguration"%>
<%@page import="java.util.Map"%>
<%@page import="com.replad.landingPage.LandingPageHelper"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= InitConfiguration.commonPropertiesMap.get("page.title")%></title>

</head>
<script>
jQuery(document).ready(function($) {
    $('.counter').counterUp({
        delay: 10,
        time: 1000
    });
});
</script>
<%
		LandingPageHelper landingPageHelper = new LandingPageHelper();
		Map<String, String> allCountDetails = landingPageHelper.getLandingPageCountDetails();
		String satisfiedCustomer = allCountDetails.get("totalCustomer");
		String repeatededCustomer = allCountDetails.get("repeatedCustomer");
		String noOfServices = allCountDetails.get("allWorkDetails");
		String workforce = allCountDetails.get("totalEmployee");
%>
<body>
	<div class="container" id="ourstatsContainer">
		<div class="col-lg-3">
			<div class="input-group">
				<div class="input-group-addon">
					<i class="fa fa-thumbs-up fa-3x shadow fontcolor009" style='font-size:8rem;'></i>
					<div class='separator1'></div>
					<div style='font-size:6rem;' class='fontcolor009'><span class='counter'><%= satisfiedCustomer%></span><span>+</span></div>
					<div class='separator1'></div>
					<span class='statstitle fontcolor009'>Number of Satisfied Customers</span>
				</div>
			</div>
		</div>
		
		<div class="col-lg-3">
			<div class="input-group">
				<div class="input-group-addon">
					<i class="fa fa-male shadow fontcolor009" style='font-size:8rem;'></i>
					<div class='separator1'></div>
					<div style='font-size:6rem;' class='fontcolor009'><span class='counter'><%= repeatededCustomer%></span><span>+</span></div>
					<div class='separator1'></div>
					<span class='statstitle fontcolor009'>Number of Repeated Customers</span>
				</div>
			</div>
		</div>
		
		<div class="col-lg-3">
			<div class="input-group">
				<div class="input-group-addon">
					<i class="fa fa-cogs fa-3x shadow fontcolor009" style='font-size:8rem;'></i>
					<div class='separator1'></div>
					<div style='font-size:6rem;' class='fontcolor009'><span class='counter'><%= noOfServices%></span><span>+</span></div>
					<div class='separator1'></div>
					<span class='statstitle fontcolor009'>Number of Services we have now</span>
				</div>
			</div>
		</div>
		
		<div class="col-lg-3">
			<div class="input-group">
				<div class="input-group-addon">
					<i class="fa fa-users fa-3x shadow fontcolor009" style='font-size:8rem;'></i>
					<div class='separator1'></div>
					<div style='font-size:6rem;' class='fontcolor009'><span class='counter'><%= workforce%></span><span>+</span></div>
					<div class='separator1'></div>
					<span class='statstitle fontcolor009'>Number of Work Force we have</span>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Below 2 files required for auto increment of numbers -->
   	<script src="./js/waypoints.min.js"></script>
	<script type="text/javascript" src="./js/jquery.counterup.min.js"></script>
</body>
</html>