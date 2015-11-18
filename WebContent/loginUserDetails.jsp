<%@page import="com.replad.utils.StringUtilities"%>
<%@page import="com.replad.sub.work.UserTicketHelper"%>
<style>
[id^=uDtls]:hover {
	color: #0C0C0C;
}

.icon-size {
	font-size: 10px;
}

.badge-warning {
  background-color: #f89406;
}
</style>
<script type="text/javascript">
$( document ).ready(function() {
	changeStyleClass($(".fa-power-off"));
	changeStyleClass($(".fa-cog"));
	changeStyleClass($(".fa-shopping-cart"));
	changeStyleClass($(".fa-user-secret"));
	
	$('[data-toggle="tooltip"]').tooltip();
});

function changeStyleClass(obj){
	obj.mouseover(function(e){
		obj.toggleClass("rediconcolor");
	});
	obj.mouseout(function(e){
		obj.removeClass("rediconcolor");
	});
}
</script>
<%
	int serviceCount = new UserTicketHelper().getUserTicketCount(request);
%>
<link rel="stylesheet" type="text/css" href="./font-awesome-4.3.0/css/font-awesome.css">
<%
	String userName = (null!=session.getAttribute("username")?session.getAttribute("username").toString() : "");
	boolean isSignIn = (StringUtilities.isBlank(userName));
	System.out.println("loginUserDetails----->isSignIn=="+isSignIn);
	if(isSignIn) {
		response.sendRedirect("/index.jsp");
	} 
%>
<input type='hidden' name='userName' id='userName' value='${sessionScope.username}' />
<div class="navbar-collapse collapse-fluid">
   <% if(serviceCount>0){ %>
   <span class="glyphicon glyphicon-bell" style='font-size:2.5rem;color:#f1c40f;' data-toggle="tooltip" title='Pending Services'></span>
   <span class="badge badge-warning"><%= serviceCount%></span>
   <% } %>
   <ul class="nav navbar-nav navbar-right">
      <li class="dropdown" style="padding-right: 20px;">
         <ul class="nav navbar-nav">
            <li class="dropdown">
               <a href="#" class="dropdown-toggle"
                  data-toggle="dropdown" role="button" aria-expanded="false"><i
                  class="glyphicon glyphicon-user icon-size"
                  style='font-size: 1.5rem; color: #0C0C0C;'></i> &nbsp;<strong>${sessionScope.username}</strong>
               <span class="caret"></span> </a>
               <ul class="dropdown-menu" role="menu" style='width: 100%;' id='userSettingUL'>
                  <li onClick="orderDetails();"><i id='userSetting' class="fa fa-shopping-cart fa-sm"
                     title="Your Basket"></i>&nbsp;&nbsp;&nbsp;Order
                     Details
                  </li>
                  <li class="divider"></li>
                  <li onClick="showModal('userSettingModal'); loadUSerDetails();"><i id='userSetting' class="fa fa-cog fa-sm"
                     title="Profile Setting"></i>&nbsp;&nbsp;&nbsp;Manage
                     Profile
                  </li>
                  <li onClick="javascript:logOut('index.jsp')"><i id='louout' class="fa fa-power-off fa-sm"
                     title="logout;"></i>&nbsp;&nbsp;&nbsp;Logout
                  </li>
                  <li class="divider"></li>
                  <li onClick="loadAdminDashboard();"><i id='userSetting' class="fa fa-user-secret fa-sm"
                     title="Admin Services"></i>&nbsp;&nbsp;&nbsp;Administrator
                  </li>
               </ul>
            </li>
         </ul>
      </li>
   </ul>
   <form class="navbar-form navbar-left" role="search"
      style="padding-left: 200px">
      <div class="input-group">
         <input type="text" class="form-control" name="searchBox" id="searchBox" placeholder="Search Services" style="width: 350px;" onclick="searchAutoComplete(this, $('#userName').val());"> 
         <span class="input-group-addon"> 
         <i class="glyphicon glyphicon-search"></i>
         </span>
      </div>
   </form>
</div>
<script>
function loadAdminDashboard(){
	var userName = '<%= userName%>';
	var url = "adminMgmt.jsp?userName="+userName;
	window.location.replace(url);
}

function orderDetails(){
	var userName = '<%= userName%>';
	var url = "welcome.jsp?userName="+userName;
	window.location.replace(url);
}
</script>
