<%@page import="com.replad.utils.StringUtilities"%>
<style>

</style>
<script type="text/javascript">

</script>

<%
String userName = (null!=session.getAttribute("username")?session.getAttribute("username").toString() : "");
boolean isSignIn = (StringUtilities.isBlank(userName));
System.out.println("isSignIn=="+isSignIn);
if(isSignIn) {
	response.sendRedirect("/index.jsp");
} 
%>
<div class="bottom alert alert-primary" style='font-size:13px;font-weight:bold'>
	<i class="fa fa-check-circle-o fa-lg"></i>&nbsp;&nbsp;Completed&nbsp;|&nbsp;
	<i class="fa fa-spinner fa-spin fa-lg"></i>&nbsp;&nbsp;Pending&nbsp;|&nbsp;
	<i class="fa fa-close fa-lg"></i>&nbsp;&nbsp;Cancelled
	<span style='padding-left:20px; color:#c12e2a'>|&nbsp;&nbsp;&nbsp;&nbsp;Click on service to view, cancel, edit details</span>
	<span style='padding-left:20px; color:#c12e2a'>|&nbsp;&nbsp;&nbsp;&nbsp;Maximum 10 service records retained, beyond that archived</span>
</div>