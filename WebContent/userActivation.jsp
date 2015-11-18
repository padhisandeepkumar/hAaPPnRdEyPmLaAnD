<%@page import="com.replad.init.InitConfiguration"%>
<%@page import="com.replad.utils.StringUtilities"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.replad.bean.work.WorkHelper"%>
<%@page import="com.replad.sub.work.SubworkHelper"%>
<%@page import="com.replad.bean.work.WorkBean"%>
<%@page import="java.util.Map"%>
<%-- <%@page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<%@page import="net.tanesha.recaptcha.ReCaptcha"%> --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

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

<!-- Bootsrap calendar date picker -->
<link href="./css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link rel="stylesheet" href="css/fontello.css">
<style>
blockquote{ font-size:0.8em; padding-left:5px; text-align: left; line-height: 1.0; border-left: 2px solid #0A7C0A; padding: 5px 5px; }
blockquote:p{ margin: 0 0 1px; }
cite{ padding-left:1px; color: #428bca; }
.col-md-4>h4{ color: #428bca; font-size:1.2em; }
.col-md-4>p{ color: #777; font-size:1.0em; }
.col-md-4>p>a{ color: #777; font-size:1.0em; }
.col-md-4>p>a:hover {color: #428bca;font-weight: bold;}
.col-md-4>p>a:active {color: #F4F2F5;}

.col-md-8>h4{ color: #428bca; font-size:1.2em; }
.col-md-8>p{ color: #777; font-size:1.0em; }
.col-md-8>p>a{ color: #777; font-size:1.0em; }
.col-md-8>p>a:hover {color: #428bca;font-weight: bold;}
.col-md-8>p>a:active {color: #F4F2F5;}

.btn:hover {color:#0C0C0C;}

.panel-body {padding: 5px;}
.icon-background4 {color: #81DAF5;}
.icon-background6 {color: #0A7C0A;}
.hew{
	padding-left:0px;
	padding-top:7px;
	/* color:#F4F2F5; */
	float: none;
	margin: 0 auto;
	font-size:12px;
	text-align:justified;
	line-height: 16px; 
}

.hew1{
	padding-left:0px;
	color:#F4F2F5;
	float: left;
	font-size:0.6em;
	line-height: 0px;
}

.iconFriends{
background: url("G:/Rasmit/Workspace/Worx/images/icon/plumbing.ico") no-repeat;
width: 56px;
height: 56px;
border: none;
display:inline-block;
}


.signInBtn {
	color: #F4F2F5;
	background-color: transparent;
	text-decoration: none;
	display: inline-block;
	position: relative;
	outline: 0;
	border: 1px solid #F4F2F5;
	text-align: center;
	vertical-align: middle;
	cursor: pointer;
	white-space: nowrap;
	font-size: 0.55em;
	border-radius: 4px;
	font-family: Calibri, Candara, Segoe, "Segoe UI", Optima, Arial, sans-serif;
	height:30px;
	font-weight:bold;
}

.signInBtn:hover{
	border-color: rgb(248,66,66);
	box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1), 0 0 8px rgb(248,66,66);
}

.signInBtnActive {
	color:#FFFF88;
}

.icon-background4 {
    color: #81DAF5;
}

.icon-background6 {
    color: #0A7C0A;
}
.round {
    display: block;
    height: 60px;
    width: 60px;
    line-height: 60px;

    -moz-border-radius: 30px; /* or 50% */
    border-radius: 30px; /* or 50% */

    background-color: #26ADA1;
    color: white;
    text-align: center;
    font-size: 2em;
}
.jumbotron {background-color: #26ADA1;}
.headerfontclass{color:#3C5B45;font-size:20px;font-weight:bold;}
.checked{color:#3C5B45;font-size:1.3em;}
.checkedfontclass{color:#3C5B45;font-size:1.2em;font-weight:bold;}
.termsclass{color:#3C5B45;}
.divclass1{background-color:#ffc000;}
.divclass2{background-color:#ABC147;}
.divclass3{background-color:#85794D;}
.divclass4{background-color:#fc2e5a;width:50%;height:150px;float:right;}
.bottomiconcolor{color: #26ADA1;}
.bottomfontcolor1{color:#3C5B45;}
.bottomfontcolor2{}

</style>

</head>

<script type="text/javascript" src="./js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="./bootstrap-3.2.0/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/jquery-ui.min.js"></script>

<!-- Bootsrap form validation : https://github.com/nghuuphuoc/bootstrapvalidator -->
<script type="text/javascript" src="./bootstrapvalidator-master/dist/js/bootstrapValidator.js"></script>
<!-- Bootstrap Calendar Date Picker -->
<script type="text/javascript" src="./js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="./js/bootstrap-filestyle.js"></script>

<script type="text/javascript" src="./js/common.js"></script>
<script type="text/javascript">
</script>
<%
String un = (StringUtilities.isNotEmpty(request.getParameter("userName"))?request.getParameter("userName"):"");
String ac = (StringUtilities.isNotEmpty(request.getParameter("activationCode"))?request.getParameter("activationCode"):"0");
//String pw = (StringUtilities.isNotEmpty(request.getParameter("password"))?request.getParameter("password"):"");
%>
<body>
<input type='hidden' id='username' value='<%= un%>' />
<input type='hidden' id='activationCode' value='<%= ac%>' />
<%-- <inout type='hidden' id='password' value='<%= pw%>' /> --%>

	<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %>
	
	<div class="container-fluid" style="padding-top: 85px;" id='activated'>
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal" aria-hidden="true" onClick="gotoHome();"><i class="fa fa-home"></i></button>
					<h4 class="modal-title">Activated your account</h4>
				</div>
				<div class="modal-body">
					<div class="input-group">
						<span class="input-group-addon"><i class="fa fa-envelope"></i></span>
						<input type="text" class="form-control" id="uname" placeholder="enter email id" value='<%= un%>' readonly="readonly">
					</div>
					<span class="help-block"></span>
					<div class="input-group">
						<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-key"></i></span>
						<input type="password" class="form-control" id="password1" placeholder="enter password" autofocus></input>
						<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-key"></i></span>
						<input type="password" class="form-control" id="password2" placeholder="re-enter password"></input>
					</div>
					
				</div>
				<div class="modal-footer">
					<span id="activationerrorMsg" class='pull-left errormsg'></span>
					<button id="loginBtn" type="button" class="btn btn-danger" onclick='javascript:activate();'>
						<i class="fa fa-sign-in fa-lg"></i>&nbsp;&nbsp;Activate
					</button>
				</div>
		</div>
	</div>	
	
	
	<div class="container-fluid" style="padding-top: 85px; display:none" id='nonactivated'>
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Failed to activate your account.</h4>
				</div>
			</div>
		</div>
	</div>	
	 
	
</body>


<script type="text/javascript">

function validatePassword(){
	var flag = false;
	var errorMsg = "";
	
	password1 = $('#password1').val().trim();
	password2 = $('#password2').val().trim();
	
	if(password1==password2){
		if(password1.length<8 || password2.length<8){
			errorMsg = "Password must have minimum 8 character length.";
		}
	}else{
		errorMsg = "Password are not matching.";
	}
	
	if(errorMsg){
		failureMessage('activationerrorMsg',  errorMsg);
		flag = false;
	}else{
		flag = true;
	}
	return flag;
}

function activate(){
	var flag = validatePassword();
	if(Boolean(flag)){
		var url = "action";
		var params = {"nav_action" : "activateUser", "username" : $('#uname').val(), "password1" : $('#password1').val(), "password2" : $('#password2').val(), "activationCode" : $('#activationCode').val()};
		$.ajax({
			url : url,
			type : "POST",
			data : params,
			dataType: "text",
		    success: function(data, textStatus, jqXHR){
		    	//alert('User Activation--->'+data);
		    	if(parseInt(data)==1){
		    		//$("#activated").show();
		    		successModal("User activation is done, Please login now.");
		    		$('#successAlertModal').on('hidden.bs.modal', function () {
		    			$(window).attr("location","index.jsp");
		    		});
		    		//
		    		/* $('#successAlertModal').on('hidden.bs.modal', function () {
		    			$(window).attr("location","index.jsp");
		    			}); */
		    	}else{
		    		$("#nonactivated").show();
		    	}
		    	$("#modal-title").text(msg);
		    },
		    error: function (jqXHR, textStatus, errorThrown){
		    	errorMessage('activationerrorMsg', 'error in activation user');
		    }
		});
	}
}

function gotoHome(){
	$(window).attr("location","index.jsp");
}




</script>

</html>