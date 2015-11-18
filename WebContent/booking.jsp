<%@page import="com.replad.utils.StringUtilities"%>
<%@page import="com.replad.init.InitConfiguration"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= InitConfiguration.commonPropertiesMap.get("page.title")%></title>

<link rel="stylesheet" type="text/css" href="./font-awesome-4.3.0/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="./bootstrap-3.2.0/dist/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="./bootstrap-3.2.0/dist/css/bootstrap-theme.css" />

<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css">
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css">

<link rel="stylesheet" type="text/css" href="./css/common.css">

<!-- Bootsrap form validation : https://github.com/nghuuphuoc/bootstrapvalidator -->
<link rel="stylesheet" type="text/css" href="./bootstrapvalidator-master/dist/css/bootstrapValidator.css">


<style>
@import
	"//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css";

.body {
	background-color: #f2f2f2;
}

.icon-c0 {
	color: #5cb85c;
}

.icon-c1 {
	color: #ffc0c0;
	padding-left: 230px;
}

.fa-stack-1x, .fa-stack-2x {
	left: 30px;
	top: 40px;
}

.icon-background4 {
    color: #81DAF5;
}

.icon-background6 {
    color: #40c040;
}

section.home-info {
	padding: 30px;
	background-color: #fbfbf9;
	border: 2px solid #dbdcd9;
	box-sizing: border-box;
	font-size: 14px;
	line-height: 1.42857143;
	width:40%;
	text-align: left;
  	margin: 0 auto;
}

</style>
</head>

<script type="text/javascript" src="./js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="./bootstrap-3.2.0/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/jquery-ui.min.js"></script>
<script type="text/javascript" src="./js/common.js"></script>
<!-- Bootsrap form validation : https://github.com/nghuuphuoc/bootstrapvalidator -->
<script type="text/javascript" src="./bootstrapvalidator-master/dist/js/bootstrapValidator.js"></script>

<body>
<%
String userName = (null!=session.getAttribute("username")?session.getAttribute("username").toString() : "");
	boolean isSignIn = (StringUtilities.isBlank(userName));
	System.out.println("isSignIn=="+isSignIn);
	if(isSignIn) {
		response.sendRedirect("/index.jsp");
	} 
%>
	<div class="row">
		<span class="fa-stack fa-2x icon-c0"> <i
			class="fa fa-circle fa-stack-2x"></i> <i
			class="fa fa-flag fa-stack-1x fa-inverse"></i>
		</span> 
		
		<span class="fa-stack fa-2x icon-c1"> <i
			class="fa fa-circle fa-stack-2x"></i> <i
			class="fa fa-flag fa-stack-1x fa-inverse"></i>
		</span> 
		<br/>
		<span class="fa-stack fa-4x"> <i
			class="fa fa-circle fa-stack-2x icon-background4"></i> <i
			class="fa fa-circle-thin fa-stack-2x icon-background6"></i> <i
			class="fa fa-lock fa-stack-1x"></i>
		</span>
		
		<span class="fa-stack fa-4x" style="font-size: 0.9em;"> <i
			class="fa fa-circle fa-stack-2x icon-background4" style="font-size: 0.3em;"></i> <i
			class="fa fa-circle-thin fa-stack-2x icon-background6"></i> <i
			class="fa fa-check fa-stack-1x"></i>
		</span>
	</div>
	
	<section class="home-info">
		<div class="row">
			<div class="form-group">
                <label for="bedrooms" class="col-xs-2 control-label">Rooms</label>
                <div class="col-xs-5 select">
                    <select name="" id="bedrooms" class="form-control input-sm">
                        <option value="0">0 Bedroom</option>
                        <option value="1" selected="selected">1 Bedroom</option>
                        <option value="2">2 Bedroom</option>
                        <option value="3">3 Bedroom</option>
                        <option value="4">4 Bedroom</option>
                        <option value="5">5+ Bedroom</option>
                    </select>
                    <i></i>
                </div>
		</div>
	</section>
</body>
</html>