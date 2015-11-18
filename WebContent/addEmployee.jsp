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
<div id='addemployee'>
    <div class="container-fluid" style="padding-top: 85px;">
        <div class="panel panel-default">
            <div class="panel-heading" style='height:60px;'>
            	<div class="pull-left">
            	<h4>Add employee</h4></div>
				<div class="btn-group pull-right">
					<ul class="nav nav-pills">
						<li onclick='javascript:displayUpload();'><a href="#"
							data-toggle="tab">Upload Details</a>
						<li onclick='javascript: employeeManagement();'><a href="#"
							data-toggle="tab">Manage Employee</a>
					</ul>
				</div>

			</div>
            <div class="panel-body" id='employeeDetailsInput' style='display:block'>
            <input type="hidden" id="employeeId" value="">
                <div class='form-group'>
                    <div class="col-sm-4" style='padding-left:0px;'>
                        <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>First Name</label>
                        <div class="input-group col-sm-8">
                            <input type="text" class="form-control" id="fname" placeholder="enter worker first name" autofocus style='height:2.5rem;' />
                            </div>
                        </div>
                        <div class="col-sm-4" style='padding-left:0px;'>
                            <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Middle Name</label>
                            <div class="input-group col-sm-8">
                                <input type="text" class="form-control" id="mname" placeholder="enter worker middle name" autofocus style='height:2.5rem;' />
                                </div>
                            </div>
                            <div class="col-sm-4" style='padding-left:0px;'>
                                <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Last Name</label>
                                <div class="input-group col-sm-8">
                                    <input type="text" class="form-control" id="lname" placeholder="enter worker last name" autofocus style='height:2.5rem;' />
                                    </div>
                                </div>
                            </div>
                            <br/>
                            <div class="form-group">
                                <div class="col-sm-4" style='padding-left:0px;'>
                                    <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Mobile Number</label>
                                    <div class="input-group col-sm-8">
                                        <input type="text" class="form-control" id="primaryMobile" placeholder="enter mobile number" maxlength="10" style='height:2.5rem;' />
                                    </div>
                                </div>
                                <div class="col-sm-4" style='padding-left:0px;'>
                                    <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Alternate Number</label>
                                    <div class="input-group col-sm-8">
                                        <input type="text" class="form-control" id="secondaryMobile" placeholder="enter mobile number" maxlength="10" style='height:2.5rem;' />
                                    </div>
                                </div>
                                <div class="col-sm-4" style='padding-left:0px;'>
                                    <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Joining Date</label>
                                    <div class='input-group date form_date col-sm-8' data-date='' data-date-format='dd MM yyyy' data-link-field='joiningDate' data-link-format='yyyy-mm-dd'>
                                        <input class='form-control datepicker' type='text' value='' readonly placeholder='Joining Date and Time' style='height:2.5rem;' />
                                            <span class='input-group-addon' style='padding: 4px 12px;font-size: 17px;'>
                                                <span class='glyphicon glyphicon-calendar' style='color:#ff3535;'></span>
                                            </span>
                                        </div>
                                        <input type='hidden' id='joiningDate' value='' />
                                    </div>
                                </div>
                                <script>
						$('.form_date').datetimepicker({
					        weekStart: 1,
					        todayBtn:  1,
							autoclose: 1,
							todayHighlight: 1,
							startView: 2,
							minView: 2,
							forceParse: 0,
							startDate: new Date(),
							endDate:'+1w'
					    });
						</script>
                                <br/>
                                <div class="form-group">
                                    <div class="col-sm-4" style='padding-left:0px;'>
                                        <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>ID Proof</label>
                                        <div class="col-sm-8" style='padding-left:0px;'>
                                            <select class="form-control input-sm" id="idCombo" style='height: 2.4rem!important;'>
                                                <option value="0">Select</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-sm-4" style='padding-left:0px;'>
                                        <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Address Proof</label>
                                        <div class="col-sm-8" style='padding-left:0px;'>
                                            <select class="form-control input-sm" id="addressCombo" style='height: 2.4rem!important;'>
                                                <option value="0">Select</option>
                                            </select>
                                        </div>
                                    </div>
                                   <div class="col-sm-4" style='padding-left:0px;'>
                                        <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Status</label>
                                        <div class="col-sm-8" style='padding-left:0px;'>
                                            <select class="form-control input-sm" id="employeeStatus" style='height: 2.4rem!important;'>
                                                <option value="-1">Select</option>
												<option value="1">Active</option>
												<option value="0">In-Active</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="form-group">
                                    <div class="col-sm-4" style='padding-left:0px;'>
                                        <label for="sort" class="col-sm-4 control-label" style='padding-left:0px;'>Grade Of Employment</label>
                                        <div class="col-sm-8" style='padding-left:0px;'>
                                            <select class="form-control input-sm" id="gradeCombo" style='height: 2.4rem!important;'>
                                                <option value="0">Select</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-sm-4" style='padding-left:0px;'>
                                        <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Type Of Employment</label>
                                        <div class="col-sm-8" style='padding-left:0px;'>
                                            <select class="form-control input-sm" id="typeCombo" style='height: 2.4rem!important;'>
                                                <option value="0">Select</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-sm-4" style='padding-left:0px;'>
                                        <label class="form-control-label col-sm-4"  for="text" style='padding-left:0px;'>Category</label>
                                        <div class="col-sm-8" style='padding-left:0px;'>
                                            <select class="form-control input-sm" id="categoryCombo" style='height: 2.4rem!important;'>
                                                <option value="0">Select</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="panel-footer clearfix" style='padding:5px;'>
                                <span id='messageSpan'></span>
                                <div class="pull-right" onclick='javascript:createNewEmployeeProfile();'>
                                    <a href="#" class="btn btn-primary btn-sm">Save employee details</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <script>
			$( document ).ready(function() {
				populateCombo("idCombo");
				populateCombo("addressCombo");
				populateCombo("gradeCombo");
				populateCombo("typeCombo");
				populateCombo("categoryCombo");
			});
			
		function createNewEmployeeProfile(){
			var fname = $('#fname').val();
			var mname = $('#mname').val()
			var lname = $('#lname').val()
			var primaryMobile = $('#primaryMobile').val()
			var secondaryMobile = $('#secondaryMobile').val()
			var joiningDate = $('#joiningDate').val()
			var idProof = $('#idCombo').val()
			var addressProof = $('#addressCombo').val()
			var gradeOfEmployment = $('#gradeCombo').val()
			var typeOfEmployment = $('#typeCombo').val()
			var categoryOfEmployment = $('#categoryCombo').val()
			var employeeId = $('#employeeId').val()
			var employeeStatus = $('#employeeStatus').val()
			
			var url = "action";
			var params = {"nav_action":"saveEmployeeData", "fname":fname , "fname":fname , "mname":mname , "lname":lname , "primaryMobile":primaryMobile , 
					"secondaryMobile":secondaryMobile , "joiningDate":joiningDate , "idProof":idProof , "addressProof":addressProof , 
					"gradeOfEmployment":gradeOfEmployment , "typeOfEmployment":typeOfEmployment , "categoryOfEmployment":categoryOfEmployment,
					"employeeId":employeeId , "employeeStatus":employeeStatus};

			$.ajax({
				url : url, type: "POST", data: params, dataType: "text",
				success: function(data, textStatus, jqXHR){
					var msg = '', classname='';
					if(parseInt(data)==1){
		   	    		msg = 'Successfully created the employee profile.';
		   	    		classname = 'successMsg';
		   	    	}else if(data!=1){
		   	    		msg = 'Failed to create the employee profile.';
		   	    		classname = 'failureMsg';
		   	    	}
					$("#messageSpan").removeClass();
					$("#messageSpan").addClass(classname);
					
					$('#messageSpan').text(msg);
					location.reload();
				},
				error: function(){
					alert('Error while creating the employee profile.')
				}
		});
					
		}
			
                </script>	