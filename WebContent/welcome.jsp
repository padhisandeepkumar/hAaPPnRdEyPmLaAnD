<%@page import="com.replad.init.InitConfiguration"%>
<%@page import="com.replad.utils.StringUtilities"%>
<%@page import="com.replad.sub.work.SubworkHelper"%>
<%@page import="com.replad.bean.work.WorkBean"%>
<%@page import="java.util.Map"%>
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


<style>
.ErrMsg{color:#E96363;font-size:0.8em;font-weight:bold};

.services{
	width:20%;
	border:1px solid #848484;
	background:#FBF5EF;
  }
  
.panel-body {
padding: 5px;
}

.icon-background4 {
    color: #81DAF5;
}

.icon-background6 {
    color: #0A7C0A;
}
</style>
</head>

<script type="text/javascript" src="./js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="./bootstrap-3.2.0/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/jquery-ui.min.js"></script>

<!-- Bootsrap form validation : https://github.com/nghuuphuoc/bootstrapvalidator -->
<script type="text/javascript" src="./bootstrapvalidator-master/dist/js/bootstrapValidator.js"></script>

<script type="text/javascript" src="./js/bootstrap-filestyle.js"></script>

<script type="text/javascript" src="./js/common.js"></script>
<script type="text/javascript" src="./js/form-validation.js"></script>

<script type="text/javascript">
var arr = [];

function changeStyle(obj){
	$(obj).attr("style","color:red");
}

function checkAgreement(){
	var agreed = $("#agreementChkbx").is(":checked");
	if(Boolean(agreed)){
		showTab(1);
	}else{
		$("#ErrMsg").text('You are not selected the agreement. Please select to proceed.');
	}
}

function showTab(tabSeq){
	$('#orderDtlTab li:eq('+tabSeq+') a').tab('show');
}

function checkout(){
	var agreed = $("#agreementChkbx").is(":checked");
	if(!Boolean(agreed)){
		$("#ErrMsg").text('You are not selected the agreement. Please select to proceed.');
		showTab(0);
	}
}

function openServicemanDetails(){
	$("#servicemanName").text("Rasmit");
	$("#servicemanIdnum").text("( ID Number : 7878777 )");
	$("#servicemanPhoto").attr("src", "./images/11.JPG");
	$("#servicemanContactNo").text("+91-990205538811");
	$("#servicemanEmail").text("rasmit.parida@gmail.com");
	$("#servicemanAddress").text("As per our records.");
	
	showModal('servicesmanDtlsModal');
}
function enablePwd(){
	$('#pwd1').removeAttr("readonly");
}

function updateUserSettings(){
	$('#msgCntr').text('');	
	var userVal = "";
	var updateParamsArr = {};
	updateParamsArr["nav_action"] = "updateUserSettings";
	
	var url = "action";
	var params = {"nav_action" : "getUserDetails"};
	$.ajax({
		url : url,
		type : "post",
		data : params,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			$.each(data, function(key, value){
				for(var j=0; j<arr.length; j++){
					
					if(arr[j]=='pwd1' || arr[j]=='pwd2'){
						updateParamsArr["password"] = $("#pwd1").val();
					}
					else if(arr[j]==key && value!=$('#'+key).val()){
						updateParamsArr[key] = $('#'+key).val();
						break;
					}
				}
			});
			$.ajax({
				url : url,
				type: "post",
			    data: updateParamsArr,
			    dataType: "json",
			    success: function(data, textStatus, jqXHR){
			    	if(parseInt(data)==1){
			    		alert("User data updated successfully");
						disableAll();
			    	}else{
			    		alert("Failed to update the user data.");
			    		return;
			    	}
			    },
			    error: function (jqXHR, textStatus, errorThrown){
			    	alert('updateUserSettings >> 2 >> error');
			    }
			});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('updateUserSettings >> 1 >> error');
		}
	});
}


function disableAll(){
	$('.modal').modal('hide');
	
	$('#username').attr("readonly", true);
	$('#pwd1').val(''); $('#pwd1').attr("readonly", true);
	$('#pwd2').val(''); $('#pwd2').attr("readonly", true);
	$('#email').attr("readonly", true);
	$('#mobile').attr("readonly", true);
	$('#address').attr("readonly", true);
	$('#pincode').attr("readonly", true);
}
</script>

<%
	/* String userLoggedInFlag = (null!=session.getAttribute("userLoggedInFlag") ? session.getAttribute("userLoggedInFlag").toString() : "");
	System.out.println("index.jsp--->"+userLoggedInFlag);
	
	
	String selected_work_group_id = (null!=session.getAttribute("selected_work_group_id")? session.getAttribute("selected_work_group_id").toString() : null);
	String selected_work_group_name = (null!=session.getAttribute("selected_work_group_name")? session.getAttribute("selected_work_group_name").toString() : null);
	String selected_sub_work_ids = (null!=session.getAttribute("selected_sub_work_ids")? session.getAttribute("selected_sub_work_ids").toString() : null);
	String other_details_for_subwork = (null!=session.getAttribute("other_details_for_subwork")? session.getAttribute("other_details_for_subwork").toString() : null);
	
	System.out.println("selected_work_group_id--->"+selected_work_group_id);
	System.out.println("selected_sub_work_ids--->"+selected_sub_work_ids);
	System.out.println("other_details_for_subwork--->"+other_details_for_subwork);
	
	
	System.out.println("other_details_for_subwork--->"+session.getAttribute("username").toString()); */
%>

<body>
	<%-- <input type='hidden' id='swgId' value='<%= selected_work_group_id%>' />
	<input type='hidden' id='swgName' value='<%= selected_work_group_name%>' />
	<input type='hidden' id='sswIds' value='<%= selected_sub_work_ids%>' />
	<input type='hidden' id='odfsw' value='<%= other_details_for_subwork%>' /> --%>
	
   <div class="navbar navbar-default navbar-fixed-top"
      style="height: 80px;" role="navigation">
      <div class="container-fluid">
		<div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
               data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span> <span
               class="icon-bar"></span> <span class="icon-bar"></span> <span
               class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><strong class='title'></strong></a>
         </div>
         <%@ include file="loginUserDetails.jsp" %>
         <!--/.nav-collapse -->
      </div>
   </div>
   <div class="container-fluid" style="padding-top: 85px;">
      <%-- <div class="panel-body col-md-3">
         <div class="panel panel-primary">
            <div class="panel-heading">
               <h3 class="panel-title">Our Services</h3>
            </div>
            <div class="panel-body" id="servicespb">
					<div class="row" style="text-align: center;">
						<div class="col-xs-12 col-md-12">
						<table id="servicesTbl">
							<%
								Map<String, WorkBean> map = new SubworkHelper().getWorkDetails();
							
								WorkBean bean = null;
								String workname="", iconFile="";
								int i=0;
								
								for(String workId : map.keySet()){
									bean = map.get(workId);
									
									workname = bean.getWorkname();
									iconFile = bean.getIconfile();
									
									if(i>3){
										i=0;
									}
									if(i==3){
							%>
								<tr>
								<%} %>
									<td id="servicesTd_<%= workId%>">
										<div class="content" onclick="openServiceDialog('<%= workId%>','<%= workname%>','');">
												<%= workname %>
										</div>
									</td>
									<%
									if(i==3){
									%>
								</tr>
								<%}
								i++;
							} %>
						</table>
						</div>
					</div>
				</div>
         </div>
      </div> --%>
      <div class="panel-body col-md-12">
         <div class="panel panel-primary">
            <div class="panel-heading">Requested Service Details</div>
            <div class="panel-body">
               <%@ include file="orderDetails.jsp" %>
            </div>
         </div>
      </div>
	</div>
   <!-- Modals starts from here -->
   <!-- Service modal -->
   <div id="servicesModal" class="modal fade">
      <div class="modal-dialog">
         <div class="modal-content">
            <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal"
                  aria-hidden="true">&times;</button>
               <h4 class="modal-title" id='servicesModalTitle'>Services</h4>
            </div>
            <div class="modal-body" id="servicesModalBody">
            </div>
            <div class="modal-footer">
               <button type="button" class="btn btn-success btn-sm"
						onclick='javascript:closeAllModal();openCheckout();'>Checkout</button>
            </div>
         </div>
      </div>
   </div>
   <!-- Service modal end -->
   <!-- Serviceman details modal -->
   <div id="servicesmanDtlsModal" class="modal fade">
      <div class="modal-dialog">
         <div class="modal-content">
            <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal"
                  aria-hidden="true">&times;</button>
               <h4 class="modal-title" id='servicesmanDtlsModalTitle'><span style='color: #428bca;' id='servicemanName'></span>&nbsp;&nbsp;<span style='color: #c12e2a;font-size:0.8em;font-weight:bold;' id='servicemanIdnum'></span></h4>
            </div>
            <div class="modal-body" id="servicesmanDtlsModalBody">
               <img id='servicemanPhoto' style='width: 200px; height: 150px;'>
               <span class='pull-right' style='padding-right:10%;'>
               <i class="fa fa-mobile fa-lg"></i>&nbsp;&nbsp;<span id='servicemanContactNo'></span>
               <span class="help-block"></span>
               <i class="fa fa-envelope fa-lg"></i>&nbsp;&nbsp;<span id='servicemanEmail'></span>
               <span class="help-block"></span>
               <i class="fa fa-home fa-lg"></i>&nbsp;&nbsp;<span id='servicemanAddress'></span>
               <span class="help-block">&nbsp;</span>
               <span class="help-block" style='color: #c12e2a;font-size:1.1em;font-weight:bold;'>On Urgency&nbsp;&nbsp;<i class="fa fa-phone fa-2x"></i>&nbsp;&nbsp;+91-9902055388</span>
               </span>
            </div>
            <div class="modal-footer">
               <button type="button" class="btn btn-danger" onClick="showModal('signinModal');">
               <i class="fa fa-close fa-sm"></i>&nbsp;&nbsp;Close
               </button>
            </div>
         </div>
      </div>
   </div>
   <!-- Serviceman details modal end -->
   <!-- User Setting modal -->
   <div id="userSettingModal" class="modal fade">
      <div class="modal-dialog">
         <div class="modal-content">
            <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal"
                  aria-hidden="true">&times;</button>
               <h4 class="modal-title" id='userSettingModalTitle'>User Setting</h4>
            </div>
            <div class="modal-body" id="servicesModalBody">
               <div class="input-group" style="display:none">
                  <span class="input-group-addon"><i class="fa fa-user"></i></span>
                  <input type="text" class="form-control" id="regUname" readonly value=''></input>
               </div>
               <span class="help-block" style="display:none"></span>
               
               <div class="input-group">
                  <span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-envelope"></i></span>
                  <input type="text" class="form-control" id="email" readonly value=''/>
               </div>
               <span class="help-block"></span>
               <div class="input-group">
                  <span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-key"></i></span>
                  <input type="password" class="form-control" name="pwd1" id="pwd1" placeholder="enter new password" readonly></input>
                  <span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-key" id='22'></i></span>
                  <input type="password" class="form-control" name="pwd2" id="pwd2" placeholder="re-enter new assword" readonly />
                  <span class="input-group-addon" style="cursor: pointer;"> 
                  <i class="fa fa-pencil-square-o" onclick="javascript:enable2Edit(this); enablePwd(); arr.push('pwd1');arr.push('pwd2');"></i>
                  </span> 
               </div>
               <span class="help-block"></span>
               <span class="help-block"></span>
               <div class="input-group">
                  <span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-mobile"></i></span>
                  <input type="text" class="form-control" id="mobile" readonly value=''>
                  <span class="input-group-addon" style="cursor: pointer;"> 
                  <i class="fa fa-pencil-square-o" onclick="javascript:enable2Edit(this); arr.push('mobile');"></i>
                  </span> 
                  </input>
               </div>
               <span class="help-block"></span>
               <div class="input-group">
                  <span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-home"></i></span>
                  <textarea class='form-control' rows='3' id="address" readonly>
                  </textarea>
                  <span class="input-group-addon" style="cursor: pointer;"> 
                  <i class="fa fa-pencil-square-o" onclick="javascript:enable2Edit(this); arr.push('address');"></i>
                  </span> 
               </div>
               <span class="help-block"></span>
               <div class="input-group">
                  <span class="input-group-addon"><i class="fa fa-paper-plane"></i></span> 
                  <input type="pincode" class="form-control" id="pincode" readonly value=''>
                  <span class="input-group-addon" style="cursor: pointer;"> 
                  <i class="fa fa-pencil-square-o" onclick="javascript:enable2Edit(this); arr.push('pincode');"></i>
                  </span> 
                  </input>
               </div>
               <span class="help-block"></span>
            </div>
            <div class="modal-footer">
               <span id='msgCntr'></span>
               <button type="button" class="btn btn-danger" onClick="updateUserSettings();">
               <i class="fa fa-sign-in fa-lg"></i>&nbsp;&nbsp;Update
               </button>
            </div>
         </div>
      </div>
   </div>
   <!-- Modal ends here -->
</body>
<script>
$( document ).ready(function() {
	$("#agreementChkbx").click(function () {
		var agreed = $("#agreementChkbx").is(":checked");
		if(Boolean(agreed)){
			$("#ErrMsg").text('');
		}
	});
	
	/* var workGroupId = $('#swgId').val();
	var workGroupName = $('#swgName').val();
	var selectedSubworkIds = $('#sswIds').val();
	var otherDetails = $('#odfsw').val();
	
	if(parseInt(workGroupId)>0){
		openServiceDialog(workGroupId, workGroupName, selectedSubworkIds);
	} */
});
</script>
</html>