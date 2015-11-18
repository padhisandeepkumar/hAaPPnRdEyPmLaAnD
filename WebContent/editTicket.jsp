<%@page import="com.replad.utils.StringUtilities"%>
<style>
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
<script type="text/javascript" src="./js/bootstrap-filestyle.min.js"></script>
<script type="text/javascript" src="./js/ticket.js"></script>

<%
	String userName = (null != session.getAttribute("username") ? session.getAttribute("username").toString()
			: "");
	boolean isSignIn = (StringUtilities.isBlank(userName));
	System.out.println("isSignIn==" + isSignIn);
	if (isSignIn) {
		response.sendRedirect("/adminLogin.jsp");
	}
%>

<script>
function displayInput(){
	$('#employeeDetailsInput').css('display', 'block');
	$('#employeeDetailsUpload').css('display', 'none');
}

function displayUpload(){
	$('#employeeDetailsInput').css('display', 'none');
	$('#employeeDetailsUpload').css('display', 'block');
}
</script>
<div id='editTicket'>
    <div class="container-fluid" style="padding-top: 85px;">
        <div class="panel panel-default">
            <div class="panel-heading" style='height:60px;'>
            	<div class="pull-left">
            	<h4>Edit Ticket</h4></div>
				<div class="btn-group pull-right">
					<ul class="nav nav-pills">
						<li onclick='javascript: ticketManagement();'><a href="#"
							data-toggle="tab">Manage Ticket</a>
					</ul>
				</div>

			</div>
            <div class="panel-body" id='ticketDetailsInput' style='display:block'>
            <input type="hidden" id="editTicketId" value="">
                <div class='form-group'>
                    <div class="col-sm-4" style='padding-left:0px;'>
                        <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Ticket Number</label>
                        <div class="input-group col-sm-8">
                            <input type="text" class="form-control" id="editTicketNumber" style='height:2.5rem;' disabled/>
                            </div>
                        </div>
                        <div class="col-sm-4" style='padding-left:0px;'>
                            <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Name</label>
                            <div class="input-group col-sm-8">
                                <input type="text" class="form-control" id="editName"  style='height:2.5rem;' disabled/>
                                </div>
                            </div>
                            <div class="col-sm-4" style='padding-left:0px;'>
                                <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Mobile</label>
                                <div class="input-group col-sm-8">
                                    <input type="text" class="form-control" id="editMobileNumber"  style='height:2.5rem;' disabled/>
                                    </div>
                                </div>
                            </div>
                            <br/>
                            <div class="form-group">
                                <div class="col-sm-4" style='padding-left:0px;'>
                                    <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Email</label>
                                    <div class="input-group col-sm-8">
                                        <input type="text" class="form-control" id="editEmail" maxlength="10" style='height:2.5rem;' disabled/>
                                    </div>
                                </div>
                                <div class="col-sm-4" style='padding-left:0px;'>
                                    <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>RequestedDate</label>
                                    <div class="input-group col-sm-8">
                                        <input type="text" class="form-control" id="editRequestedDate" maxlength="10" style='height:2.5rem;' disabled/>
                                    </div>
                                </div>
                              
                             <div class="col-sm-4" style='padding-left:0px;'>
                                 <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Total Work Hour</label>
                                 <div class="input-group col-sm-8">
                                     <input type="text" class="form-control" id="editTotalWorkHours" maxlength="10" style='height:2.5rem;'/>
                                 </div>
                             </div>
                                </div>
                              
                                <br/>
                                <div class="form-group">
                                    <div class="col-sm-4" style='padding-left:0px;'>
                                        <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Price/hr</label>
                                        <div class="col-sm-8" style='padding-left:0px;'>
                                            <input type="text" class="form-control" id="editPricePerHour" maxlength="10" style='height:2.5rem;' disabled/>
                                        </div>
                                    </div>
                                    <div class="col-sm-4" style='padding-left:0px;'>
                                        <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Discount(%)</label>
                                        <div class="col-sm-8" style='padding-left:0px;'>
                                            <input type="text" class="form-control" id="editDiscountPercenatge" maxlength="10" style='height:2.5rem;' disabled/>
                                        </div>
                                    </div>
                                   <div class="col-sm-4" style='padding-left:0px;'>
                                        <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Total Price</label>
                                        <div class="col-sm-8" style='padding-left:0px;'>
                                           <input type="text" class="form-control" id="editTotalPrice" maxlength="10" style='height:2.5rem;'/>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="form-group">
                                    <div class="col-sm-4" style='padding-left:0px;'>
                                        <label for="sort" class="col-sm-4 control-label" style='padding-left:0px;'>Work Group</label>
                                        <div class="col-sm-8" style='padding-left:0px;'>
                                            <input type="text" class="form-control" id="editWorkGroup" maxlength="10" style='height:2.5rem;' disabled/>
                                        </div>
                                    </div>
                                    <div class="col-sm-4" style='padding-left:0px;'>
                                        <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Sub Work</label>
                                        <div class="col-sm-8" style='padding-left:0px;'>
                                            <input type="text" class="form-control" id="editSubWork" maxlength="10" style='height:2.5rem;' disabled/>
                                        </div>
                                    </div>
                                    <div class="col-sm-4" style='padding-left:0px;'>
                                        <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Employee</label>
                                        <div class="col-sm-8" style='padding-left:0px;'>
                                            <select class="form-control input-sm" id="editEmployeeCombo" style='height: 2.4rem!important;'>
                                                <option value="0">Select</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                 <br/>
                                <div class="form-group">
                                    <div class="col-sm-4" style='padding-left:0px;'>
                                        <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Commission</label>
                                        <div class="col-sm-8" style='padding-left:0px;'>
                                            <input type="text" class="form-control" id="editCommision" maxlength="10" style='height:2.5rem;'/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="panel-footer clearfix" style='padding:5px;'>
                                <span id='messageSpan'></span>
                                
                                <div class="pull-right">
									<a href="#" onclick='javascript: back2Ticket();' class="btn btn-primary btn-sm"><i class='fa fa-angle-double-left fa-lg'></i>&nbsp;&nbsp;Back</a> 
									<a href="#" class="btn btn-primary btn-sm" onClick='updateTicketDetails();'>Update Ticket Details</a>
								</div>
                            </div>
                        </div>
                    </div>
                </div>
<script>
			$(document).ready(function() {
				populateCombo("editEmployeeCombo");

				$("#editTicketId").val(editTicketId);
				$("#editTicketNumber").val(editTicketNumber);
				$("#editMobileNumber").val(editMobileNumber);
				$("#editName").val(editName);
				$("#editEmail").val(editEmail);
				$("#editRequestedDate").val(editRequestedDate);
				$("#editTotalWorkHours").val(editTotalWorkHours);
				$("#editPricePerHour").val(editPricePerHour);
				$("#editDiscountPercenatge").val(editDiscountPercenatge);
				$("#editTotalPrice").val(editTotalPrice);
				$("#editWorkGroup").val(editWorkDesc);
				$("#editSubWork").val(subWorkDesc);
				$("#editEmployeeCombo").val(editEmployeeCombo);
				$("#editCommision").val(editCommision);
				$('select[id^="editEmployeeCombo"] option[value='+editEmployeeCombo+']').attr("selected","selected");
			});
			
		function updateTicketDetails(){
			var ticketId = $("#editTicketId").val();
			var discountPercenatge = $("#editDiscountPercenatge").val();
			var totalPrice = $("#editTotalPrice").val();
			var totalWorkHours = $("#editTotalWorkHours").val();
			var employeeCombo = $("#editEmployeeCombo").val();
			var commision = $("#editCommision").val();
			
			var url = "action";
			var params = {"nav_action":"updateTicketDetails", "ticketId":ticketId , "discountPercenatge":discountPercenatge , "totalPrice":totalPrice , 
					"totalWorkHours":totalWorkHours , "employeeId":employeeCombo, 'commision':commision};

			$.ajax({
				url : url, type: "POST", data: params, dataType: "text",
				success: function(data, textStatus, jqXHR){
					var msg = '', classname='';
					if(parseInt(data)==1){
		   	    		msg = 'Successfully updated Ticket Details.';
		   	    		classname = 'successMsg';
		   	    	}else if(data!=1){
		   	    		msg = 'Failed to update Ticket Details. Please try again.';
		   	    		classname = 'failureMsg';
		   	    	}
					$("#messageSpan").removeClass();
					$("#messageSpan").addClass(classname);
					
					$('#messageSpan').text(msg);
					ticketManagement();
				},
				error: function(){
					alert('Error while updating ticket profile.')
				}
			});
		}
		
		function back2Ticket(){
			$('#contentDiv').load('manageTickets.jsp');
		}
			
</script>	