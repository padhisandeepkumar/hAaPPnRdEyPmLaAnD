<%@page import="com.replad.utils.StringUtilities"%>
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

<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css">
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css">

<!-- Bootsrap form validation : https://github.com/nghuuphuoc/bootstrapvalidator -->
<link rel="stylesheet" type="text/css" href="./bootstrapvalidator-master/dist/css/bootstrapValidator.css">


<style>
.navbar-collapse,.navbar-header {
	padding-top: 15px;
}
textarea{
	resize:none;
}

/* Used to make the icon color green. */
.greeniconcolor {
	color: green;
}

.rediconcolor{
	color: red;
}

/* on mouse over change colors for icons under button */
button:hover i {
	color: #0C0C0C;
}
.container-fluid {
  padding-top: 15px;
  }
  
  html, body{height:100%; margin:0;padding:0}
 
.container-fluid{
  height:100%;
  display:table;
  width: 100%;
  padding: 0;
}
 
.row-fluid {height: 100%; display:table-cell; vertical-align: middle;}
 
.centering {
  float:none;
  margin:0 auto;
}

#errorMsg{
	color:red;
	font-size: x-small;
	word-wrap: break-word;
}

  
</style>
</head>

<script type="text/javascript" src="./js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="./bootstrap-3.2.0/dist/js/bootstrap.min.js"></script>

<!-- Bootsrap form validation : https://github.com/nghuuphuoc/bootstrapvalidator -->
<script type="text/javascript" src="./bootstrapvalidator-master/dist/js/bootstrapValidator.js"></script>

<script type="text/javascript">
	

</script>

<body>
<%
String userName = (null!=session.getAttribute("username")?session.getAttribute("username").toString() : "");
boolean isSignIn = (StringUtilities.isBlank(userName));
System.out.println("isSignIn=="+isSignIn);
if(isSignIn) {
	response.sendRedirect("/index.jsp");
} 
%>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="panel panel-primary center-block" style="width:30%">
					<div class="panel-heading">
						<h3 class="panel-title text-center">Change Password</h3>
					</div>
					
					<div class="panel-body">
						<!-- <div class="input-group">
							<span class="input-group-addon"><i class="fa fa-user"></i></span>
							<input type="text" class="form-control" id="regUname" placeholder="enter username"></input>
						</div>
						<span class="help-block"></span> -->
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-envelope"></i></span>
							<input type="text" class="form-control" id="regUname" placeholder="enter username" readonly></input>
						</div>
						<span class="help-block"></span>
						<div class="input-group">
							<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-key" style='color:green'></i></span>
							<input type="text" class="form-control" name="pwd1" id="pwd1" placeholder="enter password" required></input>
						</div>
						<span class="help-block"></span>
						<div class="input-group">
							<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-key"></i></span>
							<input type="text" class="form-control" name="pwd1" id="pwd1" placeholder="enter new password" required></input>
						</div>
						<span class="help-block"></span>
						<div class="input-group">
							<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-key"></i></span>
							<input type="text" class="form-control" name="pwd2" id="pwd2" placeholder="re-enter new assword" required></input>
						</div>
						<span class="help-block"></span>
					</div>
					
					<div class="modal-footer">
						<span class="pull-left" id='errorMsg'></span>
						<button type="button" class="btn btn-danger" onClick="changePassword();">
							Change Password
						</button>
					</div>
			</div>
		</div>
	</div>		
</body>
<script>
	function changePassword(){alert();
	$('.registerForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	pwd1: {
                validators: {
                    notEmpty: {
                        message: 'The password is required and cannot be empty'
                    },
                    different: {
                        field: 'username',
                        message: 'The password cannot be the same as username'
                    },
                    stringLength: {
                        min: 8,
                        message: 'The password must have at least 8 characters'
                    }
                }
            }
        }
    });
	}
</script>
</html>