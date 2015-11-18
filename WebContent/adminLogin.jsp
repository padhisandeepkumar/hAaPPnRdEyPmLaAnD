<%@page import="com.replad.init.InitConfiguration"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= InitConfiguration.commonPropertiesMap.get("page.title")%></title>

<link rel="stylesheet" type="text/css" href="./font-awesome-4.3.0/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="./bootstrap-3.2.0/dist/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="./bootstrap-3.2.0/dist/css/bootstrap-theme.css" />

<link rel="stylesheet" type="text/css" href="./css/common.css">

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
<body>
	<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %>
	
	<div class="container-fluid" style="padding-top: 85px;" id='activated'>
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal" aria-hidden="true" onClick="gotoHome();"><i class="fa fa-home"></i></button>
					<h4 class="modal-title">Administrator Login</h4>
				</div>
				<div class="modal-body">
					<div class="input-group">
						<span class="input-group-addon"><i class="fa fa-envelope"></i>&nbsp;<span class='errormsg'>*</span></span>
						<input type="text" class="form-control" id="uname" placeholder="enter email id" value='' autofocus>
					</div>
					<span class="help-block"></span>
					<div class="input-group">
						<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-key"></i>&nbsp;<span class='errormsg'>*</span></span>
						<input type="password" class="form-control" id="password" placeholder="enter password" value=""></input>
					</div>
					
				</div>
				<div class="modal-footer">
					<span id="errorMsg" class='pull-left errormsg' style='color:#fff'>Mandatory fields are marked with &nbsp;<span class='errormsg'>*</span></span>
					<button id="loginBtn" type="button" class="btn btn-danger" onclick='javascript:adminLogin();'>
						<i class="fa fa-sign-in fa-lg"></i>&nbsp;&nbsp;Login
					</button>
				</div>
		</div>
	</div>	
</body>


<script type="text/javascript">

function adminLogin(){
		if($.trim($('#uname').val())=='' || $.trim($('#password').val())==''){
			$('#errorMsg').text('Please Enter valid admin user name and password');return;
		}
		var url = "action";
		var params = {"nav_action" : "adminLogin", "username" : $('#uname').val(), "password" : $('#password').val()};
		$.ajax({
			url : url,
			type : "GET",
			data : params,
			dataType: "text",
		    success: function(data, textStatus, jqXHR){
		    	if(parseInt(data)==-1){
		    		$("errorMsg").text("Invalid user details");
		    	}else if(parseInt(data)==1){
		    		$(window).attr("location","adminMgmt.jsp");
		    	}
		    },
		    error: function (jqXHR, textStatus, errorThrown){
		    	errorMessage('adminLogin', 'error in administrator login');
		    }
		});
}


$(document).keypress(function(event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode == '13') {
    	adminLogin();
    }
});
</script>

</html>