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
tr>th {
	background-color: #3c763d;
	color: #F4F2F5;
}

.fa-check-circle-o {
	color: #00CC00;
}

.fa-spinner {
	color: #B40404;
}

.bottom {
	font-size: 0.8em;
}

.fa-close {
	color: #FE2E2E;
}

table>thead>tr>th {
	text-align: center;
}

table#ordertable {
	font-size: 0.8em;
}

.panel-heading.contains-buttons { .clearfix; .panel-title { .pull-left;
	padding-top: 1px;
}

}
.nav-pills>li.active>a, .nav-pills>li.active>a:focus {
	color: black;
	background-color: #fcd900;
}

.nav-pills>li.active>a:hover {
	background-color: #fcd900;
	color: black;
}

.nav-pills>li {
	color: #F4F2F5;
}

.nav-pills>li {
	background-color: #FCE75D;
	color: #0C0C0C;
}
</style>

<script type="text/javascript" src="./js/jQuery.succinct.min.js"></script>
<script type="text/javascript" src="./js/ticket.js"></script>
<%
String userName = (null!=session.getAttribute("username")?session.getAttribute("username").toString() : "");
	boolean isSignIn = (StringUtilities.isBlank(userName));
	if(isSignIn) {
		response.sendRedirect("/adminLogin.jsp");
	} 
%>
<div class="container-fluid" style="padding-top: 85px;">
	<div class="panel panel-default">
		<div class="panel-heading" style='height: 60px;'>
			<div class="pull-left">
				<h4>Manage Tickets</h4>
			</div>
		</div>
		<div class="panel-body">
			<div class='form-group'>
				<div class="col-sm-4" style='padding-left: 0px;'>
					<label class="form-control-label col-sm-4" for="text"
						style='padding-left: 0px;'>Ticket No.</label>
					<div class="col-sm-8" style='padding-left: 0px;'>
						<input type="text" id="ticketNumber" value='' />
					</div>
				</div>
				<div class="col-sm-4" style='padding-left: 0px;'>
					<label class="form-control-label col-sm-4" for="text"
						style='padding-left: 0px;'>Category</label>
					<div class="col-sm-8" style='padding-left: 0px;'>
						<select class="form-control input-sm" id="categoryCombo">
							<option value="0">Select</option>
						</select>
					</div>
				</div>

			<div class="col-sm-4" style='padding-left: 0px;'>
					<label class="form-control-label col-sm-4" for="text"
						style='padding-left: 0px;'>Employee</label>
					<div class="col-sm-8" style='padding-left: 0px;'>
						<select class="form-control input-sm" id="employeeCombo">
							<option value="-1">Select</option>
						</select>
					</div>
				</div>
				
			</div>
		</div>

		<div class="panel-body">
			<div class='form-group'>
				<div class="col-sm-4" style='padding-left: 0px;'>
					<label class="form-control-label col-sm-4" for="text"
						style='padding-left: 0px;'>From Date</label>
					<div class="col-sm-8" style='padding-left: 0px;'>
						<input type="text" id="fromDate" value='' />
					</div>
				</div>
				<div class="col-sm-4" style='padding-left: 0px;'>
					<label class="form-control-label col-sm-4" for="text"
						style='padding-left: 0px;'>To Date</label>
					<div class="col-sm-8" style='padding-left: 0px;'>
						<input type="text" id="toDate" value='' />
					</div>
				</div>
			</div>
		</div>
		<div class="panel-footer clearfix" style='padding: 5px;'>
			<span id='messageSpan'></span>
			<div class="pull-right" onclick='javascript: loadTicketGrid();'>
				<a href="#" class="btn btn-primary btn-sm">Get Ticket details</a>
			</div>
		</div>

	</div>

	<div class="panel panel-default">
		<div class="panel-body">
			<table class="table table-bordered table-hover" id='ordertable'>
				<thead>
					<tr>
						<th>Select</th>
						<th>Ticket No.</th>
						<th>Name</th>
						<th>Address</th>
						<th>Mobile</th>
						<th>Email</th>
						<th>Created Date</th>
						<th>Requested Date</th>
						<th>Work Group</th>
						<th>Price/hr</th>
						<th>Discount(%)</th>
						<th>Total</th>
						<th>Commission</th>
						<th>Total Work Hour</th>
						<th>Employee</th>
						<th>Promo Code</th>
						<th>Ticket Status</th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="panel-footer clearfix">
			<span id='messageSpan'></span>
			<div class="pull-right">
				<a href="#" onclick='javascript: deleteSelectedTickets();' class="btn btn-primary btn-sm">Delete</a>
				<a href="#" onclick='javascript: cancelTicket();' class="btn btn-primary btn-sm">Cancel</a> 
				<a href="#" onclick='javascript: editTicket();' class="btn btn-primary btn-sm">Edit</a> 
				<a href="#" class="btn btn-primary btn-sm">Reschedule</a>
				<a href="#" class="btn btn-primary btn-sm" onclick='javascript: closeSelectedTickets();'>Closed</a>
			</div>
		</div>
	</div>

</div>

	<!-- Service/Ticket details modal -->
   <div id="ticketDtlsModal" class="modal fade">
      <div class="modal-dialog">
         <div class="modal-content">
            <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal"
                  aria-hidden="true">&times;</button>
               <h4 class="modal-title" id='ticketDtlsModalTitle'>Customer Details</h4>
            </div>
            <div class="modal-body" id="ticketDtlsModalBody" style='font-size:1.2rem;'>
               <!-- <img id='servicemanPhoto' style='width: 200px; height: 150px;'> -->
               <span class='pull-left'><i class="fa fa-user fa-sm"></i><span style='margin:10px;font-weight:bold;' class='text-danger'>Customer Name</span><span id='customerName'></span></span><br/><div class='separator1'></div>
               <span class='pull-left'><i class="fa fa-mobile fa-sm"></i><span style='margin:10px;font-weight:bold;' class='text-danger'>Mobile Number</span><span id='customerMobile'></span></span><br/><div class='separator1'></div>
               <span class='pull-left'><i class="fa fa-envelope fa-sm"></i><span style='margin:10px;font-weight:bold;' class='text-danger'>Email Id</span><span id='customerEmail'></span></span><br/><div class='separator1'></div>
               <span class='pull-left'><i class="fa fa-map-marker fa-sm"></i><span style='margin:10px;font-weight:bold;' class='text-danger'>Address</span><span id='customerAddress'></span></span><br/><div class='separator1'></div>
               <span class='pull-left'><i class="fa fa-wrench fa-sm"></i><span style='margin:10px;font-weight:bold;' class='text-danger'>Service Details</span><span id='customerRequestedService'></span></span>
               <span style='padding-bottom:20px;'>&nbsp;</span>
            </div>
            <div class="modal-footer">
               <button type="button" class="btn btn-danger" onClick="copyToClipboard();">
               Copy Customer Data to Clipboard
               </button>
            </div>
         </div>
      </div>
   </div>
   <!-- Service/Ticket details modal end -->

<script>
	var statusGV = '';
	var empTypeGV = '';

	
	function openCustomerDetails(){
		$("#customerName").text(custNa);
		$("#customerMobile").text(custMo);
		$("#customerEmail").text(custEm);
		$("#customerAddress").text(custAd);
		
		showModal('ticketDtlsModal');
	}
	
	function closeSelectedTickets(){
		var chkId = '';
		var checkId = [];
		var key = '';
		var container = '';
		$("tbody tr td:nth-child(1)").each(function(i, val) {
			key = $(val).html();
			container = $($.parseHTML($(val).html()));
			if (container.is(":checked")) {
				chkId = container.attr("id").split("_")[1];
				if (parseInt(chkId) > 0) {
					checkId.push(chkId);
				}
			}
		});

		if(checkId.length==0){
			alert("No ticket selected to close");
			return;
		}
		
		if (confirm("Are you sure you want to Closing the ticket ?") != true) {
	        return;
	    } 
		var url = "action";
		var params = {
			"nav_action" : "closeTicket",
			"selectedId" : checkId.toString()
		};

		$.ajax({
			url : url,
			async : false,
			type : "POST",
			data : params,
			dataType : "text",
			success : function(data, textStatus, jqXHR) {
				$("#messageSpan").removeClass();

				if (parseInt(data) == 1) {
					$("#messageSpan").addClass('successMsg');
					$('#messageSpan').text(
							'Selected ticket(s) closed successfully.');
					loadTicketGrid();
				} else if (data != 1) {
					$("#messageSpan").addClass('failureMsg');
					$('#messageSpan').text('Failed to close the ticket.');
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error while closing the ticket.')
			}
		});
	}
	
	function deleteSelectedTickets(){
		var chkId = '';
		var checkId = [];
		var key = '';
		var container = '';
		$("tbody tr td:nth-child(1)").each(function(i, val) {
			key = $(val).html();
			container = $($.parseHTML($(val).html()));
			if (container.is(":checked")) {
				chkId = container.attr("id").split("_")[1];
				if (parseInt(chkId) > 0) {
					checkId.push(chkId);
				}
			}
		});

		if(checkId.length==0){
			alert("Please Select at least one Ticket to delete");
			return;
		}
		
		/* if(checkId.length>1){
			alert("Please Select only one Ticket to Delete");
			return;
		} */
		
		if (confirm("Are you sure you want to delete a ticekt!") != true) {
	        return;
	    } 
		var url = "action";
		var params = {
			"nav_action" : "deleteTicket",
			"selectedId" : checkId.toString()
		};

		$.ajax({
			url : url,
			async : false,
			type : "POST",
			data : params,
			dataType : "text",
			success : function(data, textStatus, jqXHR) {
				$("#messageSpan").removeClass();

				if (parseInt(data) == 1) {
					$("#messageSpan").addClass('successMsg');
					$('#messageSpan').text(
							'Successfully deleted the selected ticket.');
					loadTicketGrid();
				} else if (data != 1) {
					$("#messageSpan").addClass('failureMsg');
					$('#messageSpan').text('Failed to deleted the ticket.');
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error while cancelling the ticket.')
			}
		});
	}
	
	function cancelTicket() {
		var chkId = '';
		var checkId = [];
		var key = '';
		var container = '';
		$("tbody tr td:nth-child(1)").each(function(i, val) {
			key = $(val).html();
			container = $($.parseHTML($(val).html()));
			if (container.is(":checked")) {
				chkId = container.attr("id").split("_")[1];
				if (parseInt(chkId) > 0) {
					checkId.push(chkId);
				}
			}
		});

		if(checkId.length==0){
			alert("Please Select at least one Ticket to cancel");
			return;
		}
		if(checkId.length>1){
			alert("Please Select only one Ticket to Cancel");
			return;
		}
		
		if (confirm("Are you sure you want to cancel a ticekt!") != true) {
	        return;
	    } 
		var url = "action";
		var params = {
			"nav_action" : "cancelTicket",
			"selectedId" : checkId.toString()
		};

		$.ajax({
			url : url,
			async : false,
			type : "POST",
			data : params,
			dataType : "text",
			success : function(data, textStatus, jqXHR) {
				$("#messageSpan").removeClass();

				if (parseInt(data) == 1) {
					$("#messageSpan").addClass('successMsg');
					$('#messageSpan').text(
							'Successfully cancelled the selected ticket.');
					loadTicketGrid();
				} else if (data != 1) {
					$("#messageSpan").addClass('failureMsg');
					$('#messageSpan').text('Failed to cancel the ticket.');
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error while cancelling the ticket.')
			}
		});
	}

	var custNa = '';
	var custMo = '';
	var custEm = '';
	var custAd = '';
	$(function() {
		populateCombo("categoryCombo");
		populateCombo("employeeCombo");
		
		loadTicketGrid();
		
		$("#ordertable tr td i").click(function() {
			var rowId = $(this).attr("id");
		    custNa = $('#'+rowId+' td:nth-child(3)').html();
		    custMo = $('#'+rowId+' td:nth-child(5)').html();
		    custEm = $('#'+rowId+' td:nth-child(6)').html();
		    custAd = $('#'+rowId+' td:nth-child(4)').html();
		    
			openCustomerDetails(rowId);
		});
		
		$('.table-hover tr').click(function() {
			removeRowStyle();
			var rowId = $(this).attr("row-key");
			if (typeof rowId != 'undefined') {
				$(this).css('background-color', '#777');
				openWorkDetails(rowId);
			}
		});

		$('.truncate').succinct({
			size : 75
		});

		// on click of status button initializing the value to the global variable statusGV
		$("div.btn-group[id='statusBtnValue']").each(function() {
			var group = $(this);
			$('button', group).each(function() {
				var button = $(this);
				//$(this).removeClass();
				button.removeClass('active');//button.addClass('btn btn-default');
				button.click(function() {
					statusGV = $(this).val();
					$(this).addClass('active');
				});
			});
		});
		// on click of employee type button initializing the value to the global variable empTypeGV
		$("div.btn-group[id='emptypeBtnValue']").each(function() {
			var group = $(this);
			$('button', group).each(function() {
				var button = $(this);
				button.removeClass('active');//button.addClass('btn btn-default');
				button.click(function() {
					empTypeGV = $(this).val();
					$(this).addClass('active');
				});
			});
		});

	});

	var editTicketId = '';
	var editTicketNumber = '';
	var editMobileNumber = '';
	var editName = '';
	var editEmail = '';
	var editRequestedDate = '';
	var editTotalWorkHours = '';
	var editPricePerHour = '';
	var editDiscountPercenatge = '';
	var editTotalPrice = '';
	var editWorkGroup = '';
	var editSubWork = '';
	var editEmployeeCombo = '';
	var editCommision = '';
	var editWorkDesc = '';
	var subWorkDesc = '';
	
	function editTicket(){
		var chkId = '';
		var checkId = [];
		var key = '';
		var container = '';
		
		/* $("#ordertable tr").each(function(i){
			colValues[i] = $('tr:nth-child('+(i+1)+')&gt;td:nth-child(1)').html();
		});
		alert(colValues);
		return; */
		$("#ordertable tr td:nth-child(1)").each(function(i, val) {
			key = $(val).html();
			container = $($.parseHTML($(val).html()));
			if (container.is(":checked")) {
				chkId = container.attr("id").split("_")[1];
				if (parseInt(chkId) > 0) {
					checkId.push(chkId);
				}
			}
		});
		if(checkId.length==0){
			alert("Please select a Ticket");
			return;
		}
		if(checkId.length>1){
			alert("Please Select only one Ticket");
			return;
		}
		$('#contentDiv').load('editTicket.jsp');
		var url = "action";
		var params = { "nav_action" : "populateTicketToEdit", "selectedTicketId":checkId[0]};
		$.ajax({
			async: false,
			url : url,
			type : "GET",
			data : params,
			dataType : "json",
			async: false,
			success : function(data, textStatus, jqXHR) {
				$.each(data, function() {
					$.each(this, function(idx, value) {
						if (idx == 'id') {
							editTicketId = value;
						} else if (idx == 'workGroupId') {
							editWorkGroup = value;
						} else if (idx == 'workDesc') {
							editWorkDesc = value;
						} else if (idx == 'subWorkGroup') {
							editSubWork = value;
						} else if (idx == 'subWorkDesc') {
							subWorkDesc = value;
						} else if (idx == 'email') {
							editEmail = value;
						} else if (idx == 'mobile') {
							editMobileNumber = value;
						} /* else if (idx == 'createdDate') {
							$("#").val(value);
						} else if (idx == 'moreInfo') {
							$("#").val(value);
						}  */else if (idx == 'ticketId') {
							editTicketNumber = value;
						} else if (idx == 'name') {
							editName = value;
						} else if (idx == 'scheduleDate') {
							editRequestedDate = value;
						}else if (idx == 'pricePerHour') {
							editPricePerHour = value;
						}else if (idx == 'discount') {
							editDiscountPercenatge = value;
						}else if (idx == 'totalPrice') {
							editTotalPrice = value;
						} else if (idx == 'totalWorkHour') {
							editTotalWorkHours = value;
						}else if (idx == 'employeeId') {
							editEmployeeCombo = value;
						}else if (idx == 'promoCode') {
							//$("#").val(value);
						}else if (idx == 'commision') {
							editCommision = value;
						}
					});
				});
			}
		});
	}

</script>