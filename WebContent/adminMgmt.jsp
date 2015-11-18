<%@page import="com.replad.utils.StringUtilities"%>
<%@page import="com.replad.init.InitConfiguration"%>
<%@page import="java.util.HashMap"%><%@page
	import="com.replad.bean.work.WorkHelper"%><%@page
	import="com.replad.sub.work.SubworkHelper"%><%@page
	import="com.replad.bean.work.WorkBean"%><%@page import="java.util.Map"%><%-- <%@page import="net.tanesha.recaptcha.ReCaptchaFactory"%><%@page import="net.tanesha.recaptcha.ReCaptcha"%> --%><%@ page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= InitConfiguration.commonPropertiesMap.get("page.title")%></title>
<link rel="stylesheet" type="text/css"
	href="./font-awesome-4.3.0/css/font-awesome.css" />
<link rel="stylesheet" type="text/css"
	href="./bootstrap-3.2.0/dist/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="./bootstrap-3.2.0/dist/css/bootstrap-theme.css" />
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="./css/jquery-ui.structure.css" />
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css" />
<link rel="stylesheet" type="text/css" href="./css/common.css" />
<!-- Bootsrap form validation : https://github.com/nghuuphuoc/bootstrapvalidator -->
<link rel="stylesheet" type="text/css"
	href="./bootstrapvalidator-master/dist/css/bootstrapValidator.css" />
<!-- Bootsrap calendar date picker -->
<link href="./css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen" />
<style>
.btn-file {
	position: relative;
	overflow: hidden;
}

.btn-file input[type=file] {
	position: absolute;
	top: 0;
	right: 0;
	min-width: 100%;
	min-height: 100%;
	font-size: 100px;
	text-align: right;
	filter: alpha(opacity = 0);
	opacity: 0;
	outline: none;
	background: white;
	cursor: inherit;
	display: block;
}

label {
	font-size: 0.8em;
	padding-top: 5px;
}

.col-sm-2 {
	padding-left: 0px;
}

.col-sm-2>select {
	width: 220px;;
}
</style>
</head>
<script type="text/javascript" src="./js/jquery-2.1.4.js"></script>
<script type="text/javascript"
	src="./bootstrap-3.2.0/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/jquery-ui.min.js"></script>
<!-- Bootsrap form validation : https://github.com/nghuuphuoc/bootstrapvalidator -->
<script type="text/javascript"
	src="./bootstrapvalidator-master/dist/js/bootstrapValidator.js"></script>
<!-- Bootstrap Calendar Date Picker -->
<script type="text/javascript"
	src="./js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="./js/bootstrap-filestyle.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-filestyle.js"></script>
<script type="text/javascript" src="./js/common.js"></script>
<BODY>
	<%
 String userName = (StringUtilities.isNotBlank(request.getParameter("userName")) ? request.getParameter("userName") : null);
 if(StringUtilities.isBlank(userName)){
	 userName = (null!=session.getAttribute("username")?session.getAttribute("username").toString() : "");
 }
	boolean isSignIn = (StringUtilities.isBlank(userName));
	System.out.println("am--->isSignIn=="+isSignIn);
	if(isSignIn) {
		response.sendRedirect("/adminLogin.jsp");
	} 
	String requestFor = (StringUtilities.isNotBlank(request.getParameter("requestFor")) ? request.getParameter("requestFor") : "");
%>
	<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c'%>
	<input type='hidden' name='requestFor' id='requestFor' value='<%= requestFor%>' />
	<!-- navigation menu starts from here -->
	<nav class="navbar navbar-default navbar-fixed-top" style="height: 80px;">
	<div class="pull-left" style='width: 99.8%'>
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> 
				<span class="icon-bar"></span> <span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#"> <strong class='title'></strong>
			</a>
		</div>
		
		<div id="navbar" class="navbar-collapse collapse pull-left"
			style="padding-left: 170px; width: 820px;">
			<ul class="nav navbar-nav">
				<li class="dropdown" onclick="javascript: employeeManagement();">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
						<strong>Employee Management</strong> 
					</a>
				</li>
				<li class="dropdown" onclick="javascript: ticketManagement();"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-expanded="false"><strong>Ticket
							Management</strong></a>
					</li>
				<li class="dropdown" onclick="javascript: workDetailsRendering();"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-expanded="false"><strong>Revenue
							Management</strong></a>
					 
				</li>
				<li class="dropdown" onclick="javascript: workDetailsRendering();"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-expanded="false"><strong>Work
							Management</strong></a>
				</li>
			</ul>
		</div>

		<!-- Logged in user profile -Starts -->
		<div id="navbar" class="navbar-collapse collapse pull-right" style="width: 180px;">
			<ul class="nav navbar-nav" style='background-color: #FCD900;'>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"> 
						<i class="glyphicon glyphicon-user icon-size" style='font-size: 1.5rem;'></i> 
						&nbsp; 
						<strong>${sessionScope.username}</strong>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu" role="menu" style='width: 100%;' id='userSettingUL'>
						<li onClick="orderDetails();">
							<i id='userSetting' class="fa fa-user-plus fa-sm" title="Your Basket"></i>
							&nbsp;&nbsp;&nbsp;Create User
						</li>
						<li class="divider"></li>
						<li onClick="showModal('userSettingModal'); loadUSerDetails();">
							<i id='userSetting' class="fa fa-cog fa-sm" title="Profile Setting"></i>
							&nbsp;&nbsp;&nbsp;Manage Profile
						</li>
						<li onClick="javascript:logOut('index.jsp')">
							<i id='louout' class="fa fa-power-off fa-sm" title="logout;"></i>
							&nbsp;&nbsp;&nbsp;Logout
						</li>
					</ul>
				</li>
			</ul>
		</div>
		<!-- Logged in user profile -Starts -->
		</div>
	</nav>
	<!-- navigation menu ends here -->
	
	<div id='contentDiv'></div>
</BODY>
</HTML>

<SCRIPT LANGUAGE="JavaScript">
    $( document ).ready(function() {
    	if(requestFor && $.trim($("#requestFor").val()).length>0 && $.trim($("#requestFor").val())=='workDetails'){
    		workDetailsRendering()
    	}else{
    		employeeManagement();
    	}
    });
        </SCRIPT>
