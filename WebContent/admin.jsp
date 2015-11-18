<%@page import="com.replad.utils.StringUtilities"%>
<%@page import="com.replad.sub.work.SubworkHelper"%>
<%@page import="com.replad.bean.work.WorkBean"%>
<%@page import="java.util.Map"%>
<%@page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<%@page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="./font-awesome-4.3.0/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="./bootstrap-3.2.0/dist/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="./bootstrap-3.2.0/dist/css/bootstrap-theme.css" />

<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css">
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css">

<link rel="stylesheet" type="text/css" href="./css/common.css">

<!-- Bootsrap form validation : https://github.com/nghuuphuoc/bootstrapvalidator -->
<link href="./css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<style type="text/css">
th {background-color: #56C7AB;color:#F4F2F5;text-align:center;v-align:center;font-weight:bold;font-size:11px;}
.fa-check-circle-o{color: #00CC00;font-size:15px;}
.fa-spinner{color:#B40404;font-size:15px;}
.fa-close{color:#FE2E2E;font-size:15px;}
tr{font-size:11px;font-weight:normal;}
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
<script type="text/javascript" src="./js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript">
</script>
<body>
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
			<%@ include file="loginUserDetails.jsp"%>
			<!--/.nav-collapse -->
		</div>
	</div>
	<div class="container-fluid" style="padding-top: 80px;width:100%;padding-left:1px;padding-right:1px;padding-bottom:1px;">
		<div class="bottom alert alert-info" style='font-size: 13px; margin-bottom:0px;font-weight:margin-left:1px;padding-left:1px; bold;width:100%;'>
			<button type="button" class="btn btn-danger btn-xs" onclick='javascript:ticketDetails();'>Ticket Management</button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-danger btn-sm" onclick='javascript:'>View Invoices</button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-danger btn-sm" onclick='javascript:employeeManagement();'>View Employees</button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-danger btn-sm" onclick='javascript:getWorkDetails();'>View Services</button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-danger btn-sm" onclick='javascript:downloadServiceDetails();'>Download Services</button>&nbsp;&nbsp;&nbsp;
 		</div>
 		
 		<div class="panel panel-default" style='font-size: 13px; font-weight:margin-bottom:0px;margin-left:1px;padding-left:1px; bold;width:100%;'>
			<div class="panel-heading">
				<h3 class="panel-title" style='font-size:12px;font-weight:bold;'>Filters</h3>
			</div>
			<div class="panel-body" id="servicespb">
				<div class="row" style="text-align: center;">
					<div class="col-xs-12 col-md-12">
						<div class='form-group' style='margin-bottom: 0px;font-size:5px;margin-top:5px;'>
							<div class="col-md-3">
								<div style='width:300px' id='startDateDiv' class='input-group date form_date' data-date='' data-date-format='dd MM yyyy' data-link-field='startDate' data-link-format='yyyy-mm-dd'>
									<span class='input-group-addon' style='font-size:12px;font-weight:bold;color:#AB0606;'>Start Date</span>
									<input class='form-control datepicker' type='text' value='' readonly placeholder='Select Start Date'> 
									<span class='input-group-addon'>
										<span class='glyphicon glyphicon-calendar' style='color: #ff3535;'></span>
									</span>
								</div>
								<input type='hidden' id='startDate' value='' /><br />
							</div>
							<div class='separator1'></div>
							<div class="col-md-3">
								<div style='width:300px;' id='endDateDiv' class='input-group date form_date' data-date='' data-date-format='dd MM yyyy' data-link-field='endDate' data-link-format='yyyy-mm-dd'>
									<span class='input-group-addon' style='font-size:12px;font-weight:bold;color:#AB0606;'>End Date</span>
									<input class='form-control datepicker' type='text' value='' readonly placeholder='Select End Date'> 
									<span class='input-group-addon'>
										<span class='glyphicon glyphicon-calendar' style='color: #ff3535;'></span>
									</span>
								</div>
								<input type='hidden' id='endDate' value='' /><br />
							</div>
						</div>
						<script>
							$('#startDateDiv').datetimepicker({ weekStart : 1, todayBtn : 1, autoclose : 1, todayHighlight : 1, startView : 2, minView : 2, forceParse : 0 });
							$('#endDateDiv').datetimepicker({ weekStart : 1, todayBtn : 1, autoclose : 1, todayHighlight : 1, startView : 2, minView : 2, forceParse : 0 });
						</script>
						
						<div class="col-md-2">
							<select class="form-control btn-success" onchange='populateSubWorks(this);' id='workCmbBx' style='font-size:12px;padding:2px 2px;height:25px;'>
								<option id='0'>Select Service</option>
								<%
									Map<String, WorkBean> map = new SubworkHelper().getWorkDetails();
									WorkBean bean = null;
									String workname = "", iconFile = "";
									int i = 0;
									for (String workId : map.keySet()) {
										bean = map.get(workId);
										workname = bean.getWorkname();
										iconFile = bean.getIconfile();
								%>
								<option id='<%=workId%>'><%=workname%></option>
								<%
									}
								%>
							</select>
						</div>
						<div class='separator1'></div>
						<div class="col-md-4" id='subWorkDiv'>
							<select class="form-control btn-success" id='subworkCmbBx' style='font-size:12px;padding:2px 2px;height:25px;'>
								<option id='0'>Select Sub Works</option>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			<div class='panel-footer'>
				<span id="guestServiceErrorMsg" class='pull-left errormsg'></span>
				<button type="button" class="btn btn-danger btn-xs" onClick="getTicketsDetails();">
					Get Tickets
				</button>
			</div>
 		</div>

		<table class="table table-bordered table-hover" id='ticketTable'>
			<thead>
				<tr>
					<th width='2%'>Select</th>
					<th width='10%'>Ticket Id</th>
					<th width='15%'>Work Group Details</th>
					<th width='37%'>Sub-Work Details</th>
					<th width='12%'>Created Date and Time</th>
					<th width='12%'>Closed Date</th>
					<th width='12%'>Cancel Date and Time</th>
				</tr>
			</thead>
		</table>

	<div>	
 		<table class="table table-bordered table-hover" id='ordertable1'>
		</table>
	</div>
	</div>
	
	<!-- Modals starts from here -->
</body>
<script>
	function getTicketsDetails(){
		var startDate = $.trim($("#startDate").val());
		var endDate = $.trim($("#endDate").val());
		var work_group_id = $("#workCmbBx").children(":selected").attr("id");
		var sub_work_id = $("#subworkCmbBx").children(":selected").val();
		
		if(startDate=='' && endDate=='' && work_group_id==0 && sub_work_id==0){
			if(!confirm('You have not selected any filter criteria. Will you want to pull all the data.')){
				return false;
			}
		}

		var tbody = $('#ticketTable');
		$('#ticketTable tbody').html('');
		var tr = $("<tr row-key="+ctr+">");
		var td = $('<td />');
		var chkbxTD ='', ticketTD ='', workGroupTD ='', subWorkTD='', createdDateTD ='', closedDateTD ='', cancelTD='';
		var ctr = 1;
		var url = "action";
		var params = {"nav_action" : "getAllTickets","startDate":startDate,"endDate":endDate,"work_group_id":work_group_id,"sub_work_id":sub_work_id};
		$.ajax({
			async : "false",
			url : url,
			type : "GET",
			data : params,
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				$.each(data, function() {
					tr = $("<tr row-key="+ctr+" id='TR_"+data[ctr-1].id+"'>");
					$.each(this, function(idx, value) {
						if(idx=='chkbx'){
							chkbxTD = td.clone().css('text-align','center').html(value);
						}else if(idx=='ticket_id'){
							ticketTD = td.clone().text(value);
						}else if(idx=='work_group'){
							workGroupTD = td.clone().text(value);
						}else if(idx=='sub_work_desc'){
							subWorkTD = td.clone().text(value);
						}else if(idx=='created_date'){
							createdDateTD = td.clone().text(value);
						}else if(idx=='closed_date'){
							closedDateTD = td.clone().text(value);
						}else if(idx=='cancel_date'){
							cancelTD = td.clone().text(value);
						}
					});
					ctr=ctr+1;
					
					tr.append(chkbxTD);
					tr.append(ticketTD);
					tr.append(workGroupTD);
					tr.append(subWorkTD);
					tr.append(createdDateTD);
					tr.append(closedDateTD);
					tr.append(cancelTD);
					
					tbody.append(tr);
				});
			},
			error : function(){
				alert('Failed to get ticket details.')
			}
		});
	}
	
	function getWorkDetails(){
		var startDate = $.trim($("#startDate").val());
		var endDate = $.trim($("#endDate").val());
		var work_group_id = $("#workCmbBx").children(":selected").attr("id");
		var sub_work_id = $("#subworkCmbBx").children(":selected").attr("id");
		if(work_group_id==0 && sub_work_id==0){
			if(!confirm('You have not selected any filter criteria. Will you want to pull all the data.')){
				return false;
			}
		}
		
		var url = "action";
		var params = {"nav_action" : "getWorkDetails","selectedServiceId":work_group_id,"selectedSubServiceId":sub_work_id};
		var ctr = 1;
		var descTD ='',groupTD ='',priceTD ='';
		$.ajax({
			async : "false",
			url : url,
			type : "GET",
			data : params,
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				$('#ticketTable').empty();
				$('#ordertable1').empty();
				
				var tr = $("<tr row-key="+ctr+">");
				var td = $('<td />');
				$.each(data, function() {
					tr = $("<tr row-key="+ctr+" id='TR_"+data[ctr-1].id+"'>");
					$.each(this, function(idx, value) {
						if(idx=='id'){
							groupTD = td.clone().css('text-align','center').html(value);
						}else if(idx=='desc'){
							descTD = td.clone().addClass("ellipsis50").text(value);
						}else if(idx=='price'){
							priceTD = td.clone().css('text-align','center').html(value);
						}
					});
					ctr=ctr+1;
					
					tr.append(groupTD);
					tr.append(descTD);
					tr.append(priceTD);
					tbody.append(tr);
				});
			},
			error : function(){
				alert('Failed to get ticket details.')
			}
		});
	}
	
	function downloadServiceDetails(){
		alert(111111);
		var url = "action";
		var params = {"nav_action" : "downloadServiceDetails"};
		$.ajax({
			async : "false",
			url : url,
			type : "GET",
			data : params,
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				alert("data==="+data);
				alert("textStatus==="+textStatus);
			},
			error : function(err){
				alert('Failed to get ticket details.')
			}
		});
	}
</script>
</html>