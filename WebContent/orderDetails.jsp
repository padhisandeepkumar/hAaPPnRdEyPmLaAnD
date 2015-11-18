<%@page import="com.replad.utils.StringUtilities"%>
<%@page import="com.replad.utils.DateUtils"%>
<%@page import="java.io.File"%>
<%@page import="com.replad.utils.LoadProperties"%>
<%@page import="com.replad.utils.StringUtilities"%>
<%@page import="com.replad.bean.work.WorkHelper"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.replad.sub.work.UserTicketHelper"%>
<%@page import="com.replad.sub.work.UserTicketDetailBean"%>
<%@page import="java.util.List"%>
<style type="text/css">
th {background-color: #56C7AB;color:#F4F2F5;text-align:center;v-align:center;font-weight:bold;font-size:11px;}
.fa-check-circle-o{color: #00CC00;font-size:15px;}
.fa-spinner{color:#B40404;font-size:15px;}
.fa-close{color:#FE2E2E;font-size:15px;}
tr{font-size:11px;font-weight:normal;}
</style>

<script type="text/javascript" src="./js/jQuery.succinct.min.js"></script>
<table class="table table-bordered table-hover" id='ordertable'>
	<thead>
		<tr>
			<th width='2%'>Select</th>
			<th width='5%'>Status</th>
			<th width='25%'>Service Details</th>
			<th width='17%'>Created Date and Time</th>
			<th width='17%'>Schedule Date</th>
			<th width='17%'>Closed Date</th>
		</tr>
	</thead>
</table>

<div class="modal-footer">
	<span id="ErrMsgMain" class='pull-left errormsg'></span>
	<button type="button" class="btn btn-danger btn-xs" onclick='javascript:createService();' title='Add a Service'>Add</button>
	<button type="button" class="btn btn-danger btn-xs" onclick='javascript:rescheduleService();' title='Rescheduling the Service'>Reschedule</button>
	<button type="button" class="btn btn-danger btn-xs" onclick='javascript:cancelService();' title='Cancel the Service'>Cancel</button>
	<button type="button" class="btn btn-danger btn-xs" onclick='javascript:serviceDetails();' title='Details of the Service'>Details</button>
</div>

<!-- modal to display the details of the selected work -->
<div id="selectedOrderDetailModal" class="modal fade">
	<form id='tt'>
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true" id="signinBtn">&times;</button>
					<h4 class="modal-title" style='font-size: 12px; font-weight: bold;'>Order
						Detail</h4>
				</div>
				<div class="modal-body">
					<div class="panel panel-primary" style='padding:5px;margin:5px;'>
						<div class="panel-heading">
							<h3 class="panel-title"
								style='font-size: 12px; padding-top: 0px;'>Services Details</h3>
						</div>
						<div class="panel-body" id="servicespb" style='font-size: 12px;'>
							Service Details :
							<div class='separator1'></div>
							Requested On Date : &nbsp;&nbsp;|&nbsp;&nbsp; Completion On Date
							:<span id='closedDate'></span>
							<div class='separator1'></div>
							Requested at address :
						</div>
					</div>

					<div class="panel panel-primary" style='padding:5px;margin:5px;'>
						<div class="panel-heading">
							<h3 class="panel-title"
								style='font-size: 12px; padding-top: 0px;'>Services Man
								Details</h3>
						</div>
						<div class="panel-body" id="servicespb" style='font-size: 12px;'>
							<img id='servicemanPhoto' style='width: 200px; height: 150px;'>
							<span class='pull-right' style='padding-right: 10%;padding-top:15px;'> <i
								class="fa fa-mobile fa-lg"></i>&nbsp;&nbsp;<span
								id='servicemanContactNo'></span>
								<div class='separator1'></div> <i class="fa fa-envelope fa-lg"></i>&nbsp;&nbsp;<span
								id='servicemanEmail'></span>
								<div class='separator1'></div> <i class="fa fa-home fa-lg"></i>&nbsp;&nbsp;<span
								id='servicemanAddress'></span> <span class="help-block">&nbsp;</span>
								<span style='color: #c12e2a; font-size: 1.1em; font-weight: bold;'>On
									Urgency&nbsp;&nbsp;<i class="fa fa-phone fa-2x"></i>&nbsp;&nbsp;+91-9902055388
							</span>
							</span>
						</div>
					</div>

					<script>
						$("#servicemanName").text("Rasmit");
						$("#servicemanIdnum").text("( ID Number : 7878777 )");
						$("#servicemanPhoto").attr("src", "./images/11.JPG");
						$("#servicemanContactNo").text("+91-990205538811");
						$("#servicemanEmail").text("rasmit.parida@gmail.com");
						$("#servicemanAddress").text("As per our records.");
					</script>

					<!-- <div class="panel panel-primary" style='padding:5px;margin:5px;'>
						<div class="panel-heading">
							<h3 class="panel-title"
								style='font-size: 12px; padding-top: 0px;'>Your Feedback</h3>
						</div>
						<div class="panel-body" id="servicespb" style='font-size: 12px;'>
							<textarea class='form-control' rows='2' id="customerFeedback"
								placeholder="Please provide your feedback,suggestions. It help us to serve you better. &nbsp;&nbsp;&nbsp;&nbsp;(in 100 character)"
								maxlength="100" style='font-size: 12px;'></textarea>
						</div>
					</div> -->
				</div>
				<div class="modal-footer">
					<span id="ErrMsg" class='pull-left errormsg'></span>
					<!-- <button type="button" class="btn btn-danger btn-xs"
						onclick='javascript:updateFeedback();'>Update Feedback</button> -->
						
					<button type="button" class="btn btn-default btn-xs"
						onclick='javascript:closeAllModal();removeRowStyle();'>Cancel</button>
				</div>
			</div>
		</div>
	</form>
</div>
<script>

var workId = '';
/* function updateFeedback(){
	var feedback = $.trim($('#customerFeedback').val());
	// feedback can only excepted if the service is done and no feedback updated yet for the closed service
	if($.trim($('#closedDate').val()).length>0){
		if(feedback.length==0){
			msg = 'Please provide your feedback';
		}else{
			var url = "action";
			var params = {
				"nav_action" : "updateFeedback", "userFeedback":feedback, "subworkId": workId
			};
			$.ajax({
				url : url,
				type : "post",
				data : params,
				dataType : "text",
				success : function(data, textStatus, jqXHR) {
					
				},
				error : function(jqXHR, textStatus, errorThrown) {
					$("#ErrMsg").text('');
					$("#ErrMsg").text('error : Failed to Update feedback. Try after sometime.');
				}
			});
		}
	}
	else{
		msg='Please update your feedback after closing this service.';
	}
	$("#ErrMsg").text('');$("#ErrMsg").text(msg);
} */
$(function() {
	loadUSerTickets();
	
    $('.table-hover tr').click(function() {
    	removeRowStyle();
        var rowId = $(this).attr("row-key");
        if(typeof rowId!='undefined'){
       		$(this).css('background-color', '#777');
        	openWorkDetails(rowId);
        }
    });
    
    $('.truncate').succinct({
        size: 75
    });
});


function loadUSerTickets(){
	var url = "action";
	var params = {"nav_action" : "getUserTickets"};
	var ctr = 1;
	$.ajax({
		url : url,
		type : "GET",
		data : params,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			var tbody = $('#ordertable');
			var tr = $("<tr row-key="+ctr+">");
			var td = $('<td />');
			var descTD ='',statusTD ='',createdDateTD ='', scheduleDateTD='',expectedDateTD ='',closedDateTD ='',feedbackTD='', chkbxTD='';
			$.each(data, function() {
				/* tr = $("<tr row-key="+ctr+" onClick=\"openWorkDetails('"+data[ctr-1].workid+"');\">");ctr=ctr+1; */
				tr = $("<tr row-key="+ctr+" id='TR_"+data[ctr-1].id+"'>");
				$.each(this, function(idx, value) {
					if(idx=='chkbx'){
						chkbxTD = td.clone().css('text-align','center').html(value);
					}else if(idx=='servicedesc'){
						descTD = td.clone().addClass("ellipsis50").text(value);
					}else if(idx=='statusClass'){
						statusTD = td.clone().css('text-align','center').html(value);
					}else if(idx=='createdDate'){
						createdDateTD = td.clone().attr("id", "CD_"+data[ctr-1].id).text(value);
					}else if(idx=='scheduleDate'){
						scheduleDateTD = td.clone().text(value);
					}else if(idx=='closeddate'){
						closedDateTD = td.clone().text(value);
					}
				});
				ctr=ctr+1;
				
				tr.append(chkbxTD);
				tr.append(statusTD);
				tr.append(descTD);
				tr.append(createdDateTD);
				tr.append(scheduleDateTD);
				tr.append(expectedDateTD);
				tr.append(closedDateTD);
				tr.append(feedbackTD);
				
				tbody.append(tr);
			});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('error');
		}
	});
}

function removeRowStyle(){
	var rowCount = $('.table-hover tr').each(function(){
		var rowId = $(this).attr("row-key");
		 $(this).css('background-color', '');
	});
}
function openWorkDetails(workId){
	$('.modal').modal('hide');
	showModal('selectedOrderDetailModal');
}

function createService(){
	window.location.replace("checkout.jsp");
}

function rescheduleService(){
	var selectedRadio = $("input[name=serviceRadio]:checked").val();
	$("#ErrMsg").text('');
	if(selectedRadio){
		$('#ErrMsgMain').text('Our executive call you back to reschedule your service request.');
	}else{
		$('#ErrMsgMain').text('Please select a service to reschedule.');
	}
}

function cancelService(){
	$("#ErrMsg").text('');
	var selectedRadio = $("input[name=serviceRadio]:checked").val();
	if(selectedRadio){
		if(confirm('You are cancelling the selected service. Please confirm.')){
			var selectedid = selectedRadio;
			var createddate = $('#CD_'+selectedid).html();
			var url = "action";
			var params = {"nav_action" : "cancelService","id":selectedid,"createddate":createddate};
			$.ajax({
				url : url,
				type : "POST",
				data : params,
				success : function(data, textStatus, jqXHR) {
					if(parseInt(data)==1){
						$('#ordertable').html('');
						location.reload();
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					$("#ErrMsg").text('');
					$('#ErrMsgMain').text('error while cancelling the service');
				}
			});
		}
	}else{
		$('#ErrMsgMain').text('Please select a service to cancel.');
	}
}

function serviceDetails(){
	var selectedRadio = $("input[name=serviceRadio]:checked").val();
	$("#ErrMsg").text('');
	if(selectedRadio){
		var selectedSubworkId = selectedRadio.split("_")[1];
		openWorkDetails(selectedSubworkId);
	}else{
		$('#ErrMsgMain').text('Please select a service to view details.');
	}
}
</script>